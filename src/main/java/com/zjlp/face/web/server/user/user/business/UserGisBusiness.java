package com.zjlp.face.web.server.user.user.business;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.user.domain.UserGis;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

public interface UserGisBusiness {
	
	Long add(UserGis userGis);
	
	void edit(UserGis userGis);
	
	void deleteByUserId(Long userId);
	
	UserGis getUserGisByUserId(Long userId);
	
	Pagination<UserGis> findSalesOrderPage(UserGisVo userGisVo, Pagination<UserGis> pagination);

}
