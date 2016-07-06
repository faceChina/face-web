package com.zjlp.face.web.server.user.user.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AddressMapper;
import com.zjlp.face.web.server.user.user.dao.AddressDao;
import com.zjlp.face.web.server.user.user.domain.Address;

@Repository("addressDao")
public class AddressDaoImpl implements AddressDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long addAddress(Address address) {
		sqlSession.getMapper(AddressMapper.class).insertSelective(address);
		return address.getId();
	}

	@Override
	public List<Address> findAddressByUserId(Long userId) {
		return sqlSession.getMapper(AddressMapper.class).selectAddressByUserId(userId);
	}

	@Override
	public Address getAddressById(Long id) {
		return sqlSession.getMapper(AddressMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void removeAddressById(Long id) {
		sqlSession.getMapper(AddressMapper.class).removeAddressById(id);
	}

	@Override
	public Address getDefaultAddress(Long userId) {
		return sqlSession.getMapper(AddressMapper.class).selectDefaultAddress(userId);
	}

	@Override
	public void setAddressDefaultType(Address address) {
		sqlSession.getMapper(AddressMapper.class).updateAddressDefaultType(address);
	}

	@Override
	public void editAddress(Address address) {
		sqlSession.getMapper(AddressMapper.class).updateByPrimaryKeySelective(address);
	}

	@Override
	public Integer getMaxSortByUserId(Long userId) {
		return sqlSession.getMapper(AddressMapper.class).selectMaxSortByUserId(userId);
	}

}
