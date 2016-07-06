package com.zjlp.face.web.ctl.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.producer.UserGisProducer;
import com.zjlp.face.web.util.AssConstantsUtil;
/**
 * 附近的老板功能控制类
* @ClassName: UserGisCtl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年4月29日 上午10:55:54
*
 */
@Controller
@RequestMapping("/assistant/ass/user/gis/")
public class UserGisAppCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	public static final String[] FINDMANNEAR_JSON_FIELDS ={"my_latitude","my_longitude","person.loginAccount","person.headimgurl","person.signature","person.nickname","person.longitude","person.latitude"};
	
	public static final String[] FINDMANNEAR_CARD_JSON_FIELDS = {"my_latitude","my_longitude","person.loginAccount","person.headimgurl","person.nickname","person.longitude","person.latitude", "person.position", "person.company"};
	
	public static final String[] LEIDA_USER_JSON_FIELD = {"person.loginAccount","person.headimgurl","person.nickname", "person.position", "person.company"};
	@Autowired
	private UserGisProducer userGisProducer;
	
	/**
	 * 新增用户地理信息
	* @Title: add
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param req_json
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月29日 上午10:56:16
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public String add(@RequestBody JSONObject req_json){
		try {
			Long userId = super.getUserId();
			UserGisVo userGisVo = JsonUtil.toBean(req_json.toString(), UserGisVo.class);
			userGisVo.setUserId(userId);
			userGisProducer.add(userGisVo);
			return outSucceedByNoData();
		} catch (UserException e) {
			_logger.error("保存用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("保存用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			
		}
	}
	
	/**
	 * 更新位置信息
	* @Title: update
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param req_json
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月29日 上午10:56:46
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(@RequestBody JSONObject req_json){
		try {
			Long userId = super.getUserId();
			UserGis userGis = JsonUtil.toBean(req_json.toString(), UserGis.class);
			UserGis oldUserGis = userGisProducer.getUserGisByUserId(userId);
			userGis.setId(oldUserGis.getId());
			userGisProducer.edit(userGis);
			return outSucceedByNoData();
		} catch (UserException e) {
			_logger.error("修改用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("修改用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			
		}
	}
	
	/***
	 * 位置信息对附近的人不可见
	* @Title: clear
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年4月29日 上午10:57:08
	 */
	@RequestMapping(value="clear")
	@ResponseBody
	public String clear(){
		try {
			Long userId = super.getUserId();
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			userGisProducer.updateStatus(userGis.getId(), 0);
			return outSucceedByNoData();
		}catch (Exception e) {
			_logger.error("清除用户地理位置信息失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			
		}
	}
	
	/**
	 * 查找附近的老板
	* @Title: findPersonNear
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月29日 上午10:57:37
	 */
	@RequestMapping(value="findPerson")
	@ResponseBody
	public String findPersonNear(){
		try {
			Long userId = super.getUserId();
			
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			if(null == userGis){
				return outFailure(AssConstantsUtil.UserCode.NO_USERGIS_CODE, "");
			}
			userGisProducer.updateStatus(userGis.getId(), 1);
			UserGisVo userGisVo = new UserGisVo();
			userGisVo.setUserId(userId);
			userGisVo.setLatitude(userGis.getLatitude());
			userGisVo.setLongitude(userGis.getLongitude());
			userGisVo.setNumber(200);
			List<UserGisDto> list  = userGisProducer.findUserInNear(userGisVo);
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("my_latitude",userGis.getLatitude() );
			data.put("my_longitude",userGis.getLongitude());
			data.put("person", list);
			return outSucceed(data, true, FINDMANNEAR_JSON_FIELDS);
		} catch (UserException e) {
			_logger.error("查询附件的老板失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询附件的老板失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * @Title: findPersonNearWithCard 
	 * @Description: 附近的人，与名片功能结合(新版本用)
	 * @return
	 * @date 2015年8月28日 上午9:35:47  
	 * @author cbc
	 */
	@RequestMapping(value="findPersonWithCard")
	@ResponseBody
	public String findPersonNearWithCard(){
		try {
			Long userId = super.getUserId();
			
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			if(null == userGis){
				return outFailure(AssConstantsUtil.UserCode.NO_USERGIS_CODE, "");
			}
			userGisProducer.updateStatus(userGis.getId(), 1);
			UserGisVo userGisVo = new UserGisVo();
			userGisVo.setUserId(userId);
			userGisVo.setLatitude(userGis.getLatitude());
			userGisVo.setLongitude(userGis.getLongitude());
			userGisVo.setNumber(200);
			List<UserGisDto> list  = userGisProducer.findUserInNear(userGisVo);
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("my_latitude",userGis.getLatitude() );
			data.put("my_longitude",userGis.getLongitude());
			data.put("person", list);
			return outSucceed(data, true, FINDMANNEAR_CARD_JSON_FIELDS);
		} catch (UserException e) {
			_logger.error("查询附件的老板失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询附件的老板失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	@RequestMapping(value="findUserByLeida")
	@ResponseBody
	public String findUserByLeida(){
		try {
			Long userId = super.getUserId();
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			if(null == userGis){
				return outFailure(AssConstantsUtil.UserCode.NO_USERGIS_CODE, "");
			}
			UserGisVo userGisVo = new UserGisVo();
			userGisVo.setId(userGis.getId());
			userGisVo.setUserId(userId);
			userGisVo.setStatus(userGis.getStatus());
			List<UserGisDto> list  = userGisProducer.findUserByLeida(userGisVo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("person", list);
			return outSucceed(map, true, LEIDA_USER_JSON_FIELD);
		} catch (UserException e) {
			_logger.error("查询附近的雷达开启者失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("查询附近的雷达开启者失败！",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	@RequestMapping(value="closeLeida")
	@ResponseBody
	public String closeLeida() {
		try {
			Long userId = super.getUserId();
			UserGis userGis = userGisProducer.getUserGisByUserId(userId);
			if(null == userGis){
				return outFailure(AssConstantsUtil.UserCode.NO_USERGIS_CODE, "");
			}
			UserGisVo userGisVo = new UserGisVo();
			userGisVo.setId(userGis.getId());
			userGisProducer.closeLeida(userGisVo);
			return outSucceedByNoData();
		} catch (UserException e) {
			_logger.error("关闭雷达",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE);
		} catch (Exception e) {
			_logger.error("关闭雷达",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE);
		}
	}
	
	
}
