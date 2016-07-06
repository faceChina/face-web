package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.Address;

public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

	List<Address> selectAddressByUserId(Long userId);

	Address selectAddressById(Long id);

	void removeAddressById(Long id);

	Address selectDefaultAddress(Long userId);

	void updateAddressDefaultType(Address address);

	Integer selectMaxSortByUserId(Long userId);
}
