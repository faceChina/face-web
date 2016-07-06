package com.zjlp.face.web.server.user.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.job.init.impl.LoadAddressJob;
import com.zjlp.face.web.server.user.user.dao.AddressDao;
import com.zjlp.face.web.server.user.user.dao.VaearDao;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;
import com.zjlp.face.web.server.user.user.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private VaearDao vaearDao;

	@Override
	public Long addAddress(Address address) throws AddressException {
		try {
			AssertUtil.notNull(address, "Param[address] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[addAddress] Add address begin, Param=")
					.append(String.valueOf(address)));
			Address dfAddress = this.getDefaultAddress(address.getUserCode());
			if (null == dfAddress) {
				address.setIsDefault(Constants.ISDEFAULT);
			} else {
				address.setIsDefault(Constants.NOTDEFAULT);
			}
			// 字段值验证
			this.check(address);
			Integer sort = addressDao.getMaxSortByUserId(address.getUserCode());
			if(null==sort){
				sort=0;
			}
			address.setSort(++sort);
			Date date = new Date();
			address.setCreateTime(date);
			address.setUpdateTime(date);
			Long id = addressDao.addAddress(address);
			log.info(sb.delete(0, sb.length()).append("[addAddress] Add address [")
					.append(address.toString()).append("] successful. "));
			return id;
		} catch (Exception e) {
			log.error("[addAddress] Add address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public List<Address> findAddressByUserId(Long userId)
			throws AddressException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			return addressDao.findAddressByUserId(userId);
		} catch (Exception e) {
			log.error("[findAddressByUserId] Find address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public Address getAddressById(Long id) throws AddressException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			return addressDao.getAddressById(id);
		} catch (Exception e) {
			log.error("[getAddressById] Get address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public Address getValidAddressById(Long id) throws AddressException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			Address address = this.getAddressById(id);
			AssertUtil.notNull(address, "Address[id={}] is not exists.", id);
			return address;
		} catch (Exception e) {
			log.error("[getValidAddressById] Get valid address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public boolean removeAddressById(Long id) throws AddressException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb
					.append("[removeAddressById] Remove address begin, Param[id=")
					.append(id).append("]"));
			Address address=addressDao.getAddressById(id);
			addressDao.removeAddressById(id);
			if(1==address.getIsDefault()){
				List<Address> list=addressDao.findAddressByUserId(address.getUserCode());
				if(list.size()>0){
					Address edit=new Address();
					edit.setId(list.get(0).getId());
					edit.setIsDefault(1);
					edit.setUpdateTime(new Date());
					addressDao.setAddressDefaultType(edit);
				}
			}
			log.info("[removeAddressById] Remove address successful.");
			return true;
		} catch (Exception e) {
			log.error("[removeAddressById] Remove address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public Address getDefaultAddress(Long userId) throws AddressException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			Address address = addressDao.getDefaultAddress(userId);
			return address;
		} catch (Exception e) {
			log.error("[getDefaultAddress] Get default address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	public boolean editAddress(Address address) throws AddressException {
		try {
			AssertUtil.notNull(address, "Param[address] can not be null.");
			AssertUtil.notNull(address.getId(), "Param[address.id] can not be null.");
			this.getValidAddressById(address.getId());
			//修改
			address.setUpdateTime(new Date());
			addressDao.editAddress(address);
			return true;
		} catch (Exception e) {
			log.error("[editAddress] Set address faild.", e);
			throw new AddressException(e);
		}
	}

	@Override
	@Transactional
	public boolean setDefaultAddress(Long id, Long userId)
			throws AddressException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb
					.append("[setDefaultAddress] Set default address faild, Param[id=")
					.append(id).append(", userId=").append(userId));
			Address df = this.getDefaultAddress(userId);
			if (null != df) {
				this.setDefaultAddress(df.getId(), Constants.NOTDEFAULT);
			}
			this.setDefaultAddress(id, Constants.ISDEFAULT);
			return true;
		} catch (Exception e) {
			log.error("[setDefaultAddress] Get default address faild.", e);
			throw new AddressException(e);
		}
	}
	
	@Override
    public VaearDto getAreaByAreaCode(Integer areaCode) throws AddressException {
        try {
			return vaearDao.getAreaByAreaCode(areaCode);
		} catch (Exception e) {
			throw new AddressException(e);
		}
    }

	private void setDefaultAddress(Long id, Integer isDefault) {
		Address address = new Address();
		address.setId(id);
		address.setIsDefault(isDefault);
		address.setUpdateTime(new Date());
		addressDao.setAddressDefaultType(address);
	}

	private void check(Address address) {
		AssertUtil.notNull(address.getUserCode(),
				"[ADDRESS CHECK ERROR] userCode can not be null.");
		AssertUtil.notNull(address.getvAreaCode(),
				"[ADDRESS CHECK ERROR] vAreaCode can not be null.");
//		AssertUtil.notNull(address.getZipCode(),
//				"[ADDRESS CHECK ERROR] zipCode can not be null.");
		AssertUtil.notNull(address.getName(),
				"[ADDRESS CHECK ERROR] name can not be null.");
		AssertUtil.notNull(address.getCell(),
				"[ADDRESS CHECK ERROR] cell can not be null.");
//		AssertUtil.notNull(address.getTelephone(),
//				"[ADDRESS CHECK ERROR] telephone can not be null.");
		AssertUtil.notNull(address.getAddressDetail(),
				"[ADDRESS CHECK ERROR] addressDetail can not be null.");
//		AssertUtil.notNull(address.getSort(),
//				"[ADDRESS CHECK ERROR] sort can not be null.");
		AssertUtil.notNull(address.getStatus(),
				"[ADDRESS CHECK ERROR] status can not be null.");
		AssertUtil.notNull(address.getIsDefault(),
				"[ADDRESS CHECK ERROR] isDefault can not be null.");
	}

	@Override
	public VaearTree getVaearTree() throws AddressException {
		return LoadAddressJob.getVaearTree();
	}

	@Override
	public VaearTree getProviceVaear() throws AddressException {
		return LoadAddressJob.getProviceVaear();
	}

	@Override
	public Integer getMaxSortByUserId(Long userId) throws AddressException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			Integer sort = addressDao.getMaxSortByUserId(userId);
			return ConstantsMethod.nvlInteger(sort);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	@Override
	public boolean isValidAddress(String provice, String city, String country)
			throws AddressException {
		try {
			// 验证
			AssertUtil.hasLength(provice, "Param[provice] can not be null.");
			AssertUtil.hasLength(city, "Param[city] can not be null.");
			AssertUtil.hasLength(country, "Param[country] can not be null.");
			// 判断
			VaearTree vaear = this.getVaearTree();
			return (null == (vaear = vaear.getByCode(provice))) ? false
					: (null == (vaear = vaear.getByCode(city)) ? false
							: (null == (vaear = vaear.getByCode(country)) ? false
									: true));
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}
	
}
