package com.zjlp.face.web.server.user.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.security.service.PermissionService;
import com.zjlp.face.web.server.user.user.dao.UserDao;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.server.user.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private UserDao userDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PermissionService permissionService;

	
	@Override
	public void wechatRegisterUser(User user) { 
		user.setRegisterSourceType(1);// TODO 与预约注册字段保持一致
		user.setNickname("微信用户"+this._setWechatNickname());
		user.setLoginAccount(user.getRegisterSourceUserId());// TODO 与预约注册字段保持一致
		user.setLev(this._checkPassword(Constants.WECHAT_PASSWORD));// TODO 与预约注册字段保持一致
		user.setPasswd(DigestUtils.jzShaEncrypt(Constants.WECHAT_PASSWORD));
		user.setRealType(Constants.USER_TYPE_REAL);
		user.setToken(UUID.randomUUID().toString().replaceAll("-", ""));//TODO 与预约注册字段保持一致
		user.setStatus(1);
		user.setSource(1);//TODO 与预约注册字段保持一致
	    user.setType(1);
	    user.setLanguage("cn");
		
		Date date = new Date();
		user.setCreateTime(date);
		user.setUpdateTime(date);
		
		userDao.wechatRegisterUser(user);
		if (user != null && user.getId() != null) {
			User updateUser = new User();
			updateUser.setId(user.getId());
			updateUser.setNickname("微信用户" + user.getId());
			userDao.edit(updateUser);
		}
	}
	
	private String _setWechatNickname() {
		Random random  = new Random();
		StringBuffer buffer = new StringBuffer();
		buffer.append((int)(Math.random()*9+1));
		buffer.append(random.nextInt(10));
		buffer.append(random.nextInt(10));
		buffer.append(random.nextInt(10));
		buffer.append(random.nextInt(10));
		return buffer.toString();
	}
	
	@Override
	public User getWechatUserByOpenId(String openId) {
		return userDao.getWechatUserByOpenId(openId);
	}
	
	
	@Override
	public User getUserByNameAndPasswd(String username, String password) {
		return 	userDao.getUserByNameAndPasswd(username,password);
	}


	@Override
	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}
	
	@Override
	public User getByLoginAccountRedis(String loginAccount) {
		User user = userDao.getByLoginAccountRedis(loginAccount);
		if (user != null) {
			user = userDao.getById(user.getId());
		}
		return user;
	}
	
	@Override
	public User getAllUserByLoginAccount(String loginAccount) {
		return userDao.getAllUserByLoginAccount(loginAccount);
	}

	@Override
	public Long add(UserVo userVo){
	    Date date = new Date();
        User user = new User();
        
	    user.setLoginAccount(userVo.getPhone());
	    user.setLev(this._checkPassword(userVo.getPwd()));
	    user.setPasswd(DigestUtils.jzShaEncrypt(userVo.getPwd()));
	    user.setNickname(userVo.getNickname());
	    user.setMyInvitationCode(UserVo.generateMyInvitationCode());
//	    user.setInvitationCode(userVo.getInvitationCode());
	    user.setInvitationUserId(userVo.getInvitationUserId());
	    user.setRealType(1);
	    user.setToken("1");
	    
		user.setSource(1); // TODO 正常流程注册为:0
	    user.setType(1);
	    user.setLanguage("cn");
	    user.setStatus(1);
	    user.setCell(userVo.getPhone());
	    
	    user.setCreateTime(date);
	    user.setUpdateTime(date);
		userDao.add(user);
		
		return user.getId();
	}
	
	
	private Integer _checkPassword(String passwd){
	    //密码解密后
	    Integer lev=0;
	    Pattern sz = Pattern.compile("[0-9]*"); 
	    Pattern zf = Pattern.compile("^[\\da-zA-Z]*$"); 
	    Pattern zm = Pattern.compile("^[A-Za-z]+$"); 
	    Pattern zmsz = Pattern.compile("^[A-Za-z0-9]*$"); 
	 	 if(sz.matcher(passwd).matches() || zm.matcher(passwd).matches()){
    		 lev=1;
	     }else if(zmsz.matcher(passwd).matches() ){
	    	lev=2;
	     }else if(!zf.matcher(passwd).matches()){
	    	lev=3;
	     }
		return lev;
	}
	
	@Override
	public void edit(User user){
		userDao.edit(user);
	}
	
	@Override
	public User getById(Long id){
		return userDao.getById(id);
	}
	
	@Override
	public boolean editPassWd(Long userId, String passwd) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(passwd, "Param[passwd] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[editPassWd] Edit user's password begin, Param[userId=")
					.append(userId).append(", passwd=").append(passwd));
			User user = new User(userId);
			user.setPasswd(passwd);
			user.setUpdateTime(new Date());
			userDao.editPassWdById(user);
			log.info("[editPassWd] Edit user's password end.");
			return true;
		} catch (Exception e) {
			log.error("[editPassWd] Edit user's password faild.", e);
			throw new UserException(e);
		}
	}

	@Override
	public boolean editCell(Long userId, String cell) throws UserException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(cell, "Param[cell] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[editCell] Edit user's cell begin, Param[userId=")
					.append(userId).append(", cell=").append(cell));
			User user = new User(userId);
			user.setCell(cell);
			user.setUpdateTime(new Date());
			userDao.editCellById(user);
			log.info("[editCell] Edit user's cell end.");
			return true;
		} catch (Exception e) {
			log.error("[editCell] Edit user's cell faild.", e);
			throw new UserException(e);
		}
	}


	@Override
	public User getUserByOpenId(String openId) {
		AssertUtil.notNull(openId, "参数用户openid为空");
		//根据openid查询用户
		return userDao.getUserByOpenId(openId);
	}


	@Override
	public void addAnonymousUser(User user) {
		Date date = new Date();
	 
		String password = "123456";// "123456";//TODO 与预约注册字段保持一致
	    user.setLoginAccount(user.getOpenId());
	    user.setLev(this._checkPassword(password));
	    user.setPasswd(DigestUtils.jzShaEncrypt(password));
	    user.setRealType(Constants.USER_TYPE_ANONYMOUS); //TODO 与预约注册类型保持一致
	    user.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
	    
	    user.setSource(1);
	    user.setType(1);
	    user.setLanguage("cn");
	    user.setStatus(1);
	    
	    user.setCreateTime(date);
	    user.setUpdateTime(date);
		userDao.add(user);
		// TODO 与微信免登陆注册保持一致 默认昵称为：微信用户+ID
//		if (user != null && user.getId() != null) {
//			User updateUser = new User();
//			updateUser.setId(user.getId());
//			updateUser.setNickname("微信用户" + user.getId());
//			userDao.edit(updateUser);
//		}
	}


	@Override
	public void add(User user) {
		Date date = new Date();
	 
	    user.setLev(this._checkPassword(user.getPasswd()));
	    user.setPasswd(DigestUtils.jzShaEncrypt(user.getPasswd()));
	    user.setRealType(1);
	    user.setToken("1");
	    
		user.setSource(1); // TODO 正常流程注册为:0
	    user.setType(1);
	    user.setLanguage("cn");
	    user.setStatus(1);
	    user.setCell(user.getCell());
	    
	    user.setCreateTime(date);
	    user.setUpdateTime(date);
		userDao.add(user);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void register(UserVo userVo){
		//新建用户
		Long userId = this.add(userVo);
		//赋予普通用户权限
		permissionService.addUserRoleRelation(userId,Constants.STRING_ROLE_U);
		//初始化钱包
		accountService.addAccount(userId,userVo.getPhone(), null, null);
	}


	@Override
	public void updateLpNoByUserId(Long userId, String lpNo) {
		userDao.updateLpNoByUserId(userId, lpNo);
	}


	@Override
	public Integer countLpNo(String lpNo, Long userId) {
		return userDao.countLpNo(lpNo, userId);
	}


	@Override
	public Pagination<User> getUserByLpNo(String phoneOrlpNo,Pagination<User> pagination) { 
		Map<String,Object> map = new HashMap<String,Object>();
		Integer types = 0; //0:手机号码精确查找或脸谱号模糊查找
		if (!phoneOrlpNo.matches(Constants.PHONE_RULE)) {
			types = 1; //1:脸谱号模糊查找
		}
		map.put("types", types);
		map.put("phoneOrlpNo", phoneOrlpNo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		
		List<User> users = userDao.getUserByLpNo(map);
		Integer total = userDao.getTotalCountByLpNo(map);
		pagination.setTotalRow(total);
		pagination.setDatas(users);
		return pagination;
	}


	@Override
	public Integer getCountByMyInvitationCode(String myInvitationCode) {
		return userDao.getCountByMyInvitationCode(myInvitationCode);
	}


	@Override
	public User getUserByMyInvitationCode(String myInvitationCode) {
		return userDao.getUserByMyInvitationCode(myInvitationCode);
	}

	@Override
	public List<User> findInvitedUsers(String myInvitationCode) {
		return this.userDao.findInvitedUsers(myInvitationCode);
	}

	@Override
	public UserVo getCountInvitationAmount(Long userId) {
		UserVo userVo = new UserVo();
		Integer myInvitationAmount = userDao.getCountMyInvitationAmount(userId);
		Integer bisInvitationAmount = userDao.getCountBisInvitationAmount(userId);
		userVo.setCountMyInvitationAmount(myInvitationAmount);
		userVo.setCountBisInvitationAmount(bisInvitationAmount);
		return userVo;
	}

	@Override
	public void updateShuaLianUser(User user) {
		User oldUser = this.getById(user.getId());
		if(user.getMyInvitationCode()!=null && !user.getMyInvitationCode().equals(oldUser.getMyInvitationCode()) && User.IS_NOT_UPDATE.equals(oldUser.getIsUpdateCode())){
			user.setIsUpdateCode(User.IS_UPDATE);
		}else{
			user.setMyInvitationCode(null);
		}
		
		userDao.edit(user);
	}
	
}
