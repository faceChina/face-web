package com.zjlp.face.web.server.user.businesscard.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.BusinessCardException;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

public interface BusinessCardBusiness {

	/**
	 * 
	 * @Title: addBusinessCard 
	 * @Description: 增加名片
	 * @param businessCard
	 * @throws BusinessCardException
	 * @date 2015年8月26日 上午11:42:11  
	 * @author cbc
	 */
	Long addBusinessCard(BusinessCard businessCard) throws BusinessCardException;
	
	/**
	 * 
	 * @Title: updateBusinessCard 
	 * @Description: 更新用户名片 (必须将userId进行设值，保证更改权限)
	 * @param businessCard
	 * @throws BusinessCardException
	 * @date 2015年8月26日 上午11:42:34  
	 * @author cbc
	 */
	void updateBusinessCard(BusinessCard businessCard) throws BusinessCardException;
	
	/**
	 * 
	 * @Title: findBusinessCardByUserId 
	 * @Description: 通过userId查询用户名片
	 * @param userId
	 * @return
	 * @throws BusinessCardException
	 * @date 2015年8月26日 上午11:42:45  
	 * @author cbc
	 */
	BusinessCard getBusinessCardByUserId(Long userId) throws BusinessCardException;
	
	/**
	 * @Title getCardCaseByUserId
	 * @param userId
	 * @return
	 * @throws BusinessCardException
	 */
	CardCase getCardCaseByUserId(Long userId, Long cardId) throws BusinessCardException;
	
	/**
	 * 收藏名片到名片夹
	 * 
	 * <p>
	 * 
	 * @param cardCase
	 * @return
	 * @throws BusinessCardException<br>
	 *             1.该名片已被收藏 <br>
	 *             2.该名片属于该用户  <br>
	 *             3.参数为空 <br>
	 *             4.该用户或该名片不存在
	 */
	boolean addCardToCase(CardCase cardCase) throws BusinessCardException;
	
	/**
	 * @Title removeCardFromCase
	 * @param cardCase
	 * @return
	 * @throws BusinessCardException
	 * @date 2015.10.6 上午
	 * @author jushuang
	 */
	boolean removeCardFromCase(Long userId, Long cardId) throws BusinessCardException;
	
	/**
	 * @Title findCardPageByCase
	 * @param pagination
	 * @param businessCardVo
	 * @return
	 * @throws BusinessCardException
	 * @date 2015.10.6 上午
	 * @author jushuang
	 */
	Pagination<BusinessCardVo> findCardPageByCase(Pagination<BusinessCardVo> pagination, 
			BusinessCardVo businessCardVo) throws BusinessCardException;
	
	List<BusinessCardVo> findCardByCase(Long userId);
}
