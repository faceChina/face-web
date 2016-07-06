package com.zjlp.face.web.server.user.user.dao;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.Address;

public interface AddressDao {

	Long addAddress(Address address);

	List<Address> findAddressByUserId(Long userId);

	Address getAddressById(Long id);

	void removeAddressById(Long id);

	Address getDefaultAddress(Long userId);

	void setAddressDefaultType(Address address);

	void editAddress(Address address);

	Integer getMaxSortByUserId(Long userId);

}
