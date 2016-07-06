package com.zjlp.face.web.server.user.user.producer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.dto.UserGisDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.server.user.user.producer.UserGisProducer;
import com.zjlp.face.web.server.user.user.service.UserGisService;
import com.zjlp.face.web.util.GisUtil;

@Repository("userGisProducer")
public class UserGisProducerImpl implements UserGisProducer {
	/**查找人数**/
	private static final int PERSON_NUMBER = 180;
	/**初始查找半径**/
	private static final long INITIAL_RADIUS= 1000L;
	/**最大查找半径**/
	private static final long MAX_RADIUS= 20000L;
	/**半径倍数**/
	private static final int MULTIPLE= 2;
	/**半径300米**/
	private static final long RADIO_300 = 300L;
	
	@Autowired
	private UserGisService userGisService;
	
	@Override
	public Long add(UserGisVo userGisVo) throws UserException {
		try {
			AssertUtil.notNull(userGisVo.getUserId(),"Param[userId] can not be null");
			AssertUtil.notNull(userGisVo.getLatitude(),"Param[latitude] can not be null");
			AssertUtil.notNull(userGisVo.getLongitude(),"Param[longitud] can not be null");
			UserGis gis = this.getUserGisByUserId(userGisVo.getUserId());
			if (null == gis) {
				return userGisService.add(userGisVo);
			}else{
				return gis.getId();
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public boolean edit(UserGis userGis)throws UserException {
		try {
			AssertUtil.notNull(userGis.getId(),"Param[Id] can not be null");
			AssertUtil.notNull(userGis.getLatitude(),"Param[latitude] can not be null");
			AssertUtil.notNull(userGis.getLongitude(),"Param[longitud] can not be null");
			Date date = new Date();
			userGis.setUpdateTime(date);
			userGisService.edit(userGis);
			return true;
		} catch (Exception e) {
			throw new UserException(e);
		}
	}

	@Override
	public boolean deleteByUserId(Long id) {
		AssertUtil.notNull(id,"Param[id] can not be null");
		userGisService.deleteById(id);
		return true;
	}

	@Override
	public UserGis getUserGisByUserId(Long userId) {
		return userGisService.getUserGisByUserId(userId);
	}

	@Override
	public List<UserGisDto> findUserInNear(UserGisVo userGisVo) {
		List<UserGisDto> list = new ArrayList<UserGisDto>();
		list = this.find(list, INITIAL_RADIUS, userGisVo);
		return list;
	}
	
	private List<UserGisDto> find(List<UserGisDto> list,long radius,UserGisVo userGisVo){
		double[] d = GisUtil.getAround(userGisVo.getLatitude().doubleValue(), userGisVo.getLongitude().doubleValue(), radius);
		userGisVo.setLeftLongitude(BigDecimal.valueOf(d[1]));
		userGisVo.setRightLongitude(BigDecimal.valueOf(d[3]));
		userGisVo.setDownLatitude(BigDecimal.valueOf(d[0]));
		userGisVo.setTopLatitude(BigDecimal.valueOf(d[2]));
		userGisVo.setNumber(200);
		list = userGisService.findUserInNear(userGisVo);
		if(list.size() < PERSON_NUMBER && radius < MAX_RADIUS){
			radius = radius*MULTIPLE;
			list = this.find(list, radius,userGisVo);
		}
		return list;
	}

	@Override
	public boolean updateStatus(Long id, Integer status)throws UserException {
		try {
			AssertUtil.notNull(id,"Param[Id] can not be null");
			AssertUtil.notNull(status,"Param[status] can not be null");
			return userGisService.updateStatus(id, status);
		} catch (Exception e) {
			throw new UserException();
		}
	}

	@Override
	public List<UserGisDto> findUserByLeida(UserGisVo userGisVo) {
		try {
			//验证参数
			AssertUtil.notNull(userGisVo,"Param[userGisVo] can not be null");
			AssertUtil.notNull(userGisVo.getId(), "userGisVo.id can't be null.");
			AssertUtil.notNull(userGisVo.getUserId(), "userGisVo.userId can't be null.");
			//修改状态(声波开启状态开启)
			UserGis userGis = this.editLeidaEnable(userGisVo.getId(), UserGisVo.LEIDA_OPEN);
			//半径50米的距离用户收索
			double[] d = GisUtil.getAround(userGis.getLatitude().doubleValue(), userGis.getLongitude().doubleValue(), RADIO_300);
			userGisVo.setLeftLongitude(BigDecimal.valueOf(d[1]));
			userGisVo.setRightLongitude(BigDecimal.valueOf(d[3]));
			userGisVo.setDownLatitude(BigDecimal.valueOf(d[0]));
			userGisVo.setTopLatitude(BigDecimal.valueOf(d[2]));
			userGisVo.setLongitude(userGis.getLongitude());
			userGisVo.setLatitude(userGis.getLatitude());
			return userGisService.findLeiDaUser(userGisVo);
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
	
	@Override
	public boolean closeLeida(UserGisVo userGisVo) {
		try {
			//参数验证
			AssertUtil.notNull(userGisVo,"Param[userGisVo] can not be null");
		    //修改状态(声波开启状态关闭)
			this.editLeidaEnable(userGisVo.getId(), UserGisVo.LEIDA_CLOSE);
			return true;
		} catch (Exception e) {
			throw new UserException();
		}
	}
	
	private UserGis editLeidaEnable(Long id, int radarEnable){
		UserGis userGis = userGisService.getUserGisById(id);
		userGis.setRadarEnable(radarEnable);
		userGis.setUpdateTime(new Date());
		userGisService.updateLeiDaStatusRadarEnable(userGis);
		return userGis;
	}

	@Override
	public UserGis getUserGisById(Long id) {
		return userGisService.getUserGisById(id);
	}
	
	
}
