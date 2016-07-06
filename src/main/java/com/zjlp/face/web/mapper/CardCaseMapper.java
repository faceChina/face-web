package com.zjlp.face.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

public interface CardCaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CardCase record);

    int insertSelective(CardCase record);

    CardCase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardCase record);

    int updateByPrimaryKey(CardCase record);
    
    CardCase getCardCaseByUserId(@Param("userId")Long userId, @Param("cardId")Long cardId);
    
    Integer selectCardCaseCount(BusinessCardVo vo);

    List<BusinessCardVo> queryCardCase(
    		BusinessCardVo vo);
    
    CardCase selectCardCase(@Param("userId")Long userId, @Param("cardId")Long cardid);
    
    List<BusinessCardVo> queryCardCase(Long userId);
}