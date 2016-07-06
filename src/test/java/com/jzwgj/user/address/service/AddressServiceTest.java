package com.jzwgj.user.address.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.service.AddressService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
@Transactional
public class AddressServiceTest {

	@Autowired
	private AddressService addressService;
	
	private Long addAddress(Long userId, Integer sort) {
		Address address = new Address(userId, 110000, "Zip543216", "林亦生",
				"18655015835", "a村b街3院4户5号");
		address.setSort(sort);
		Long id = addressService.addAddress(address);
		return id;
	}

	@Test
	public void addAddressTest() {
		Long userId = 108L;
		Long id = this.addAddress(userId, 1);
	    Address newAddress = addressService.getAddressById(id);
	    Assert.assertNotNull(newAddress);
	}
	
	
	@Test
	public void getValidAddressTest() {
		Long userId = 108L;
		Long id = this.addAddress(userId, 1);
		id ++;
	    try {
			addressService.getValidAddressById(id);
			Assert.assertFalse(true);
		} catch (AddressException e) {
			Assert.assertEquals("Address[id="+id+"] is not exists.", e.getMessage());
		}
	}
	
	@Test
	public void removeAddressTest(){
		Long userId = 108L;
		Long id = this.addAddress(userId, 1);
		Address address = addressService.getValidAddressById(id);
		Assert.assertNotNull(address);
		addressService.removeAddressById(id);
		try {
			addressService.getValidAddressById(id);
			Assert.assertFalse(true);
		} catch (AddressException e) {
			Assert.assertEquals("Address[id="+id+"] is not exists.", e.getMessage());
		}
	}
	
	@Test
	public void defaultAddressTest() {
		Long userId = 108L;
		Integer sort = 0;
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		Address dfAddress = addressService.getDefaultAddress(userId);
		Assert.assertNotNull(dfAddress);
		Long olddf = dfAddress.getId();
		Long id = this.addAddress(userId, sort++);
		addressService.setDefaultAddress(id, userId);
		dfAddress = addressService.getDefaultAddress(userId);
		Assert.assertEquals(id, dfAddress.getId());
		Address oldAddress = addressService.getAddressById(olddf);
		Assert.assertEquals(Constants.NOTDEFAULT, oldAddress.getIsDefault());
	}
	
	
//	@Test
	public void getAreaByAreaCode() {
		VaearDto vaear = addressService.getAreaByAreaCode(110000);
		Assert.assertNotNull(vaear);
		System.out.println(vaear);
	}
	
	@Test
	public void findListTest() {
		Long userId = 108L;
		Integer sort = 0;
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		this.addAddress(userId, sort++);
		List<Address> addressList = addressService.findAddressByUserId(userId);
		Assert.assertNotNull(addressList);
		Assert.assertEquals(4, addressList.size());
	}
}
