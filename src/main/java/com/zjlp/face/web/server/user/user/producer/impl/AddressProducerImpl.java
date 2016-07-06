package com.zjlp.face.web.server.user.user.producer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;
import com.zjlp.face.web.server.user.user.domain.vo.AddressVo;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;
import com.zjlp.face.web.server.user.user.service.AddressService;

@Repository("addressProducer")
public class AddressProducerImpl implements AddressProducer {

	@Autowired
	private AddressService addressService;

	@Override
	public Long addAddress(Address address) throws AddressException {
		try {
			return addressService.addAddress(address);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public List<Address> findAddressByUserId(Long userId)
			throws AddressException {
		try {
			return addressService.findAddressByUserId(userId);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public Address getAddressById(Long id) throws AddressException {
		try {
			return addressService.getAddressById(id);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public Address getValidAddressById(Long id) throws AddressException {
		try {
			return addressService.getValidAddressById(id);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public boolean editAddress(Address address) throws AddressException {
		try {
			return addressService.editAddress(address);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public boolean removeAddressById(Long id) throws AddressException {
		try {
			return addressService.removeAddressById(id);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public Address getDefaultAddress(Long userId) throws AddressException {
		try {
			return addressService.getDefaultAddress(userId);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public boolean setDefaultAddress(Long id, Long userId)
			throws AddressException {
		try {
			return addressService.setDefaultAddress(id, userId);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public VaearDto getAreaByAreaCode(Integer areaCode) {
		try {
			return addressService.getAreaByAreaCode(areaCode);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public VaearTree getVaearTree() throws AddressException {
		try {
			return addressService.getVaearTree();
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public VaearTree getProviceVaear() throws AddressException {
		try {
			return addressService.getProviceVaear();
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public void addOrEditAddress(Long id, Long userId, Integer vAreaCode,
			String userName, String cell, String addressDetail)  throws AddressException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not null.");
			AssertUtil.notNull(vAreaCode, "Param[vAreaCode] can not null.");
			AssertUtil.notNull(userName, "Param[userName] can not null.");
			AssertUtil.notNull(cell, "Param[cell] can not null.");
			AssertUtil.notNull(addressDetail, "Param[addressDetail] can not null.");
			if (null == id) {
				Address address = new Address(userId, vAreaCode, null,
						userName, cell, addressDetail);
				this.addAddress(address);
			} else {
				Address address = new Address(id);
				address.setUserCode(userId);
				address.setvAreaCode(vAreaCode);
				address.setName(userName);
				address.setCell(cell);
				address.setAddressDetail(addressDetail);
				this.editAddress(address);
			}
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public AddressVo getAddressVoById(Long id) throws AddressException {
		try {
			AssertUtil.notNull(id, "Param[id] can not null.");
			Address address = this.getAddressById(id);
			VaearTree tree = this.getVaearTree();
			List<VaearTree> codelist = VaearTree.getInherits(address.getvAreaCode(), tree);
			AddressVo vo = new AddressVo(address);
			vo.setVArea(codelist);
			return vo;
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public boolean isValidAddress(String province, String city, String country)
			throws AddressException {
		try {
			// 验证
			AssertUtil.hasLength(province, "Param[provice] can not be null.");
			AssertUtil.hasLength(city, "Param[city] can not be null.");
			AssertUtil.hasLength(country, "Param[country] can not be null.");
			return addressService.isValidAddress(province, city, country);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

}
