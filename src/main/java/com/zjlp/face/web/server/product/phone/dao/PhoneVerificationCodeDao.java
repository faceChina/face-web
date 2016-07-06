package com.zjlp.face.web.server.product.phone.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.product.phone.domain.PhoneVerificationCode;


public interface PhoneVerificationCodeDao {
	
	void addPhoneVerificationCode(PhoneVerificationCode phoneVerificationCode);
	
	PhoneVerificationCode selectByPhone(PhoneVerificationCode phoneVerificationCode);
	
	void editPhoneVerificationCode(PhoneVerificationCode phoneVerificationCode);
	
	PhoneVerificationCode queryByPhone(PhoneVerificationCode phoneVerificationCode);

	int getPhoneCodeCount(String cell,Date start,Date end);

	PhoneVerificationCode getValidMobilecode(PhoneVerificationCode code);

	void unvalidMobilecode(PhoneVerificationCode mobilecode);

	void unvalidMobilecodeById(PhoneVerificationCode mobilecode);

	List<PhoneVerificationCode> getPhoneCodeByCell(String cell, Date createTime);

	List<PhoneVerificationCode> getValidMobilecodeList(PhoneVerificationCode code);

	void minusCount(Long id);

	Integer getStatisticCount(PhoneVerificationCode code);
}
