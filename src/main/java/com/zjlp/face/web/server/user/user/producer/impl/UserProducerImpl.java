package com.zjlp.face.web.server.user.user.producer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.user.security.producer.RoleProducer;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.server.user.user.service.UserService;

@Repository("userProducer")
public class UserProducerImpl implements UserProducer {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private AccountProducer accountProducer;
	@Autowired
	private RoleProducer roleProducer;
	@Autowired(required=false)
	private ImageService imageService;
	
	@Override
	public boolean editNameAndIdentity(Long userId, String userName,
			String identity) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(userName, "Param[userName] can not be null.");
			AssertUtil.hasLength(identity, "Param[identity] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[editNameAndIdentity] begin, Param[userId=").append(userId)
					.append(", userName=").append(userName).append(", identity=").append(identity).append("]"));
			User user = new User(userId);
			user.setContacts(userName);
			user.setIdentity(identity);
			user.setUpdateTime(new Date());
			userService.edit(user);
			log.info("[editNameAndIdentity] end.");
			return true;
		} catch (Exception e) {
			log.error("[editNameAndIdentity] faild.", e);
			throw new UserException(e);
		}
	}

	@Override
	public boolean editCell(Long userId, String phone) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.hasLength(phone, "Param[phone] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[editCell] begin, Param[userId=")
					.append(userId).append(", phone=").append(phone));
			User user = new User(userId);
			user.setCell(phone);
			user.setUpdateTime(new Date());
			userService.edit(user);
			log.info("[editCell] end.");
			return true;
		} catch (Exception e) {
			log.error("[editCell] faild.", e);
			throw new UserException(e);
		}
	}

	@Override
	public User getUserById(Long userId) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			User user = userService.getById(userId);
			AssertUtil.notNull(user, "User[id={}] can't be null.", userId);
			return user;
		} catch (Exception e) {
			log.error("[getUserById] faild.", e);
			throw new UserException(e);
		}
	}

	@Override
	public User getUserByName(String username) {
		try {
			AssertUtil.notNull(username, "Param[username] can not be null.");
			User user = userService.getUserByName(username);
			return user;
		} catch (Exception e) {
			log.error("[getUserById] faild.", e);
			throw new UserException(e);
		}
	}

	@Override
	public User getUserByUserName(String username) throws UserException {
		AssertUtil.hasLength(username, "Param[username] can not be null.");
		return userService.getUserByName(username);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void registerForFreeShop(User user, byte[] pic) throws UserException {
        try {
        	AssertUtil.notNull(user.getNickname(), "用户昵称为空");
        	AssertUtil.notNull(user.getPasswd(), "用户密码为空");
        	AssertUtil.notNull(user.getCell(), "手机号（用户登陆名）为空");
			// 判断用户是否存在，防止重复注册
			User checkUser = userService.getAllUserByLoginAccount(user.getCell());
			AssertUtil.isTrue(null == checkUser, "请勿重复注册用户");
			user.setLoginAccount(user.getCell());
		    userService.add(user);
		    AssertUtil.notNull(user.getId(), "注册用户失败!");
		    // 初始化钱包
		    accountProducer.addUserAccount(user.getId(), user.getCell(), null, null);
		    // 给用户添加权限
		    roleProducer.addUserRoleRelation(user.getId());
		    // 上传图片
		    if(null != pic){
				String headPicture = imageService.upload(pic);
				headPicture = this.uploadPiceture(user.getId(), headPicture);
				user.setHeadimgurl(null == headPicture ? "" : headPicture);
				userService.edit(user);
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
	
	/**
     * 上传图片	
     * @Title: uploadPiceture 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param userId
     * @param headimgurl
     * @return
     * @date 2015年3月26日 下午3:23:15  
     * @author lys
     */
	@SuppressWarnings("all")
	@Transactional
	private String uploadPiceture(Long userId, String headimgurl) {
		//上传图片到TFS
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
        FileBizParamDto dto = new FileBizParamDto();
        dto.setImgData(headimgurl);
        dto.setZoomSizes("400_400");
        dto.setUserId(userId);
        dto.setShopNo(null);
        dto.setTableName("USER");
        dto.setTableId(String.valueOf(userId));
        dto.setCode(ImageConstants.HEAD_IMG_FILE);
        dto.setFileLabel(1);
        list.add(dto);
        String flag = imageService.addOrEdit(list);
        //数据解析
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
        return fbpDto.get(0).getImgData();
	}
}
