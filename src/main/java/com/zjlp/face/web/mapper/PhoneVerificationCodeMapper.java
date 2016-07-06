package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;

public interface PhoneVerificationCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PhoneVerificationCode record);

    int insertSelective(PhoneVerificationCode record);

    PhoneVerificationCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PhoneVerificationCode record);

    int updateByPrimaryKey(PhoneVerificationCode record);
    
    PhoneVerificationCode selectByPhone(PhoneVerificationCode record);
    
    PhoneVerificationCode queryByPhone(PhoneVerificationCode record);
    
    int getPhoneCodeCount(Map<String, Object> map);

	PhoneVerificationCode selectValidMobilecode(PhoneVerificationCode code);

	void unvalidMobilecode(PhoneVerificationCode mobilecode);

	List<PhoneVerificationCode> selectPhoneCodeByCell(Map<String, Object> map);

	List<PhoneVerificationCode> selectValidMobilecodeList(
			PhoneVerificationCode code);

	void minusCount(Long id);

	Integer selectStatisticCount(PhoneVerificationCode code);

}