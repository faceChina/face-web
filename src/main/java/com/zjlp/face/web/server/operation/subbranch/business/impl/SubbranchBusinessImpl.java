package com.zjlp.face.web.server.operation.subbranch.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.metaq.producer.SubbranchMetaOperateProducer;
import com.zjlp.face.web.component.metaq.producer.SystemMetaOperateProducer;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.business.UnregisteredSubUserBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.UnregisteredSubUser;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.operation.subbranch.service.UnregisteredSubUserService;
import com.zjlp.face.web.server.trade.cart.producer.CartProducer;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopSubbranchDto;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:12:52
 *
 */
@Service("subbranchBusiness")
public class SubbranchBusinessImpl implements SubbranchBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private UserService userService;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private CartProducer cartProducer;
	@Autowired
	private SystemMetaOperateProducer systemMetaOperateProducer;
	@Autowired 
	private UnregisteredSubUserBusiness unregisteredSubUserBusiness;
	@Autowired
	private UnregisteredSubUserService unregisteredSubUserService;
	@Autowired
	private SubbranchMetaOperateProducer subbranchMetaOperateProducer;
	
	private static final String MAX_BRANCH_LEVEL = "maxBranchLevel";

	@Override
	public Subbranch findSubbranchById(Long id) throws SubbranchException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			Subbranch subbranch = this.subbranchService.findByPrimarykey(id);
			AssertUtil.notNull(subbranch, "Subbranch[id={}] can't be null.", subbranch.getId());
			boolean flag = this.checkIsMaxBranchLevel(id);
			subbranch.setIsAbleToRecruit(!flag);
			return subbranch;
		} catch (Exception e) {
			_log.error("通过主键查找分店失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public List<Subbranch> findSubbranchByUserId(Long userId) throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			return this.subbranchService.findByUserId(userId);
		} catch (Exception e) {
			_log.error("通过用户ID查找分店失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public Pagination<SubbranchVo> findPageSubbrach(SubbranchVo subbranchVo, Pagination<SubbranchVo> pagination)
			throws SubbranchException {
		try {
			AssertUtil.notNull(subbranchVo, "Param[subbranchVo] can not be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			Pagination<SubbranchVo> result = this.subbranchService.findPageSubbrache(subbranchVo, pagination);
			// 组装每一个item,取得分店头像RUL
//			List<SubbranchVo> list = new ArrayList<SubbranchVo>();
			if (result != null) {
				for (SubbranchVo current : result.getDatas()) {
					if (StringUtils.isNotBlank(current.getSupplierShopNo())) {
						Shop shop = this.shopService.getShopByNo(current.getSupplierShopNo());
						if (shop != null) {
							current.setShopLogo(shop.getShopLogoUrl());
						}
						User user = this.userService.getById(current.getUserId());
						if (user != null) {
							current.setSubPhone(user.getLoginAccount());
						}
//						list.add(current);
					}
				}
			}
//			result.setDatas(list);
			return result;
		} catch (Exception e) {
			_log.error("分页查找我的分店失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public List<ShopSubbranchDto> searchShopByPhoneNo(String phoneNo, Long userId) throws SubbranchException {
		try {
			AssertUtil.notNull(phoneNo, "Param[phoneNo] can not be null.");
			User searchUser = userService.getUserByName(phoneNo);
			List<ShopSubbranchDto> results = new ArrayList<ShopSubbranchDto>();
			String maxBranchLevel = PropertiesUtil.getContexrtParam(MAX_BRANCH_LEVEL);//配置的最大分店层级
			AssertUtil.hasLength(maxBranchLevel, "subbranchConfig.properties未配置字段maxBranchLevel");
			Integer max = Integer.valueOf(maxBranchLevel);
			if (searchUser != null && searchUser.getId() != null) {
				results = this.searchShopByUserId(searchUser.getId());
				// 如果分店的佣金比例为0，则不应该再被代理,在结果中去除
				if (CollectionUtils.isNotEmpty(results) && results.size() == 2) {
					Iterator<ShopSubbranchDto> it = results.iterator();
					while (it.hasNext()) {
						ShopSubbranchDto current = it.next();
						if(current.getStatus()!=null && current.getStatus()!=1){
							it.remove();
							continue;
						}
						/** 设置为不是历史分店*/
						current.setIsHistorySubbranch(0);
						if (current.getRole() == 2 ) {
							/**佣金比例为0仍然可以被搜索到*/
//							if (current.getProfit() == 0) {
//								it.remove();
//								break;
//							}
							//假如现在的分店已经是最大的店铺代理等级，则不能被代理，在结果中去除
							List<Subbranch> subbranchList = subbranchProducer.findSubbranchList(current.getSubbrachId());
							Integer length = 0;
							if (subbranchList != null) {
								length = subbranchList.size();
							}
							if (length >= max) {
								it.remove();
								break;
							}
						}
						this.initIsBF(current, userId);
					}

				}
			}
			return results;
		} catch (Exception e) {
			_log.error("根据手机号查找分店失败！");
			throw new SubbranchException(e);
		}
	}

	/**
	 * 
	 * @Title: initIsBF 
	 * @Description: 初始化是否是下级店铺字段
	 * @param current
	 * @param userId
	 * @date 2015年7月28日 下午4:59:28  
	 * @author cbc
	 */
	private void initIsBF(ShopSubbranchDto current, Long userId) {
		if (null == current.getSubbrachId()) {
			current.setIsBF(0);
		} else {
			Subbranch subbranch = subbranchService.findByPrimarykeyWhenStatusIsNormal(current.getSubbrachId());
			AssertUtil.notNull(subbranch, "查无此分店");
			Shop supplierShop = shopService.getShopByNo(subbranch.getSupplierShopNo());
			AssertUtil.notNull(supplierShop, "查无此分店的总店");
			if (supplierShop.getUserId().longValue() == userId.longValue()) {
				current.setIsBF(1);
			} else {
				current.setIsBF(0);
			}
		}
	}
	
	@Override
	public List<ShopSubbranchDto> searchShopByUserId(Long userId) throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<ShopSubbranchDto> results = new ArrayList<ShopSubbranchDto>();
			// 查找实体店铺
			List<Shop> shopList = this.shopService.findShopListByUserId(userId);
			if (shopList.isEmpty()) {
				_log.error("用户" + userId + "下没有实体店铺,操作失败！");
				return new ArrayList<ShopSubbranchDto>();
			}
			if (shopList.size() > 1) {
				_log.error("用户" + userId + "下有多个实体店铺,脏数据,操作失败！");
				//return new ArrayList<ShopSubbranchDto>();
				shopList=shopList.subList(0, 1);
			}
			if (shopList.size() == 1) {
				ShopSubbranchDto dto = new ShopSubbranchDto();
				dto.setNo(shopList.get(0).getNo());// ID
				dto.setRole(1);// 官网
				dto.setUserId(shopList.get(0).getUserId());// 用户ID
				dto.setContacts(shopList.get(0).getContacts());// 联系人
				dto.setCell(shopList.get(0).getCell());// 电话
				dto.setShopLogoUrl(shopList.get(0).getShopLogoUrl());// 店铺图片
				dto.setName(shopList.get(0).getName());// / 总店铺名
				dto.setStatus(shopList.get(0).getStatus());
				dto.setPermission(1);// 总店一直拥有招募权限
				results.add(dto);
			}
			// 查找下属分店
			List<Subbranch> subList = this.subbranchService.findByUserId(userId);
			if (!subList.isEmpty() && subList.size() == 1 && isInvalid(subList.get(0))) {
				ShopSubbranchDto dto = new ShopSubbranchDto();
				dto.setNo(subList.get(0).getSupplierShopNo());// 设置分享链接使用的P ID
				dto.setSubbrachId(subList.get(0).getId());// ID
				dto.setRole(2);// 分店
				dto.setContacts(subList.get(0).getUserName());// 联系人
				dto.setCell(subList.get(0).getUserCell());// 电话
				dto.setName(subList.get(0).getShopName());// 分店铺名
				if (StringUtils.isNotBlank(subList.get(0).getSupplierShopNo())) {
					Shop shop = this.shopService.getShopByNo(subList.get(0).getSupplierShopNo());
					if (shop != null) {
						dto.setShopName(shop.getName());// 总店名
						dto.setShopLogoUrl(shop.getShopLogoUrl());// 店铺图片,是总店的链接
						dto.setStatus(shop.getStatus());
					}
				}
				dto.setUserId(subList.get(0).getUserId());// 用户ID
				dto.setProfit(subList.get(0).getProfit());// 佣金比例
				/** role为最底层级BF时无法招募下级代理 */
				dto.setPermission(this.checkIsMaxBranchLevel(subList.get(0).getId()) ? 0 : 1);// 招募下级代理权限：0无,1有
				results.add(dto);
			}
			return results;
		} catch (Exception e) {
			_log.error("根据用户ID查找分店失败！");
			throw new SubbranchException(e);
		}
	}

	// userId为当前登陆ID, searchPhoneNo为查找店铺的手机号,role为要代理的店铺类型,1:P,2:BF
	@Override
	@Deprecated
	public Long applyAsSubByPhone(Long userId, String searchPhoneNo, Integer role, String submitNickName, String submitPhoneNo) throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(searchPhoneNo, "Param[searchPhoneNo] can not be null.");
			AssertUtil.notNull(role, "Param[role] can not be null.");
			// 根据手机号查出用户
			User searchUser = userService.getUserByName(searchPhoneNo);
			// P
			List<Shop> shopList = new ArrayList<Shop>();
			// BF
			List<Subbranch> subList = new ArrayList<Subbranch>();
			if (searchUser != null) {
				if (role == 1) {
					shopList = this.shopService.findShopListByUserId(searchUser.getId());
					if (CollectionUtils.isEmpty(shopList)) {
						_log.error("查找的店铺不存在！");
						return -1906L;// 总店不存在
					}
					if (shopList.size() > 1) {
						_log.error("账号" + searchPhoneNo + "下有多个实体店铺,脏数据,操作失败！");
						return -1L;
					}
				} else if (role == 2) {
					subList = this.subbranchService.findByUserId(searchUser.getId());
					if (shopList.size() > 1) {
						_log.error("账号" + searchPhoneNo + "下有多个分店铺,脏数据,操作失败！");
						return -2L;
					}
				}
			}
			Subbranch subbranch = new Subbranch();
			User user = this.userService.getById(userId);
			if (!shopList.isEmpty() && shopList.size() == 1 && role == 1) {// 如果代理的是P
				boolean exist = this.checkExistAsSub(1, userId, shopList.get(0).getUserId());
				if (exist) {
					return -1907L;// 已经是总店shopId的分店
				}
				if (userId == shopList.get(0).getUserId().longValue()) {
					_log.info("自己不能申请成为自己的分店" + userId);
					return -1911L;
				}
				subbranch.setPid(0L);// 上级ID为0
				subbranch.setType(1);// 上级类型：P
				subbranch.setSuperiorUserId(shopList.get(0).getUserId());// 上级用户Id
			} else if (!subList.isEmpty() && subList.size() == 1 && role == 2 && isInvalid(subList.get(0))) {// 如果代理BF
				Subbranch sub = this.subbranchService.findByPrimarykey(subList.get(0).getId());
				if (!isInvalid(sub)) {
					return -1909L;// 分店不存在
				}
				boolean exist = this.checkExistAsSub(2, userId, sub.getUserId());
				if (exist) {
					return -1908L;// 已经是分店的分店
				}
				if (userId == sub.getUserId().longValue()) {
					_log.info("自己不能申请成为自己的分店" + userId);
					return -1911L;
				}
				subbranch.setPid(subList.get(0).getId());// 上级ID
				subbranch.setType(2);//上级类型：BF
				subbranch.setSuperiorUserId(subList.get(0).getUserId());// 上级USerId
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setUserId(userId);// 分店用户ID
			subbranch.setUserName(StringUtils.isNotBlank(submitNickName) ? submitNickName : user.getNickname());// 分店用户名称
			subbranch.setUserCell(StringUtils.isNotBlank(submitPhoneNo) ? submitPhoneNo : user.getCell());// 分店用户联系方式
			subbranch.setShopName(user.getLoginAccount());// 分店名称
			subbranch.setDeliver(0);// 发货权限:0,无;1,有
			subbranch.setProfit(0);// 佣金比例(上级设置)
			if (!shopList.isEmpty() && shopList.size() == 1 && role == 1) {
				subbranch.setSupplierShopNo(shopList.get(0).getNo());// 供货商店铺号
			} else if (!subList.isEmpty() && subList.size() == 1 && role == 2 && isInvalid(subList.get(0))) {
				subbranch.setSupplierShopNo(subList.get(0).getSupplierShopNo());
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setStatus(1);// // 状态:1,有效;0,冻结;-1:删除
			subbranch.setTwoDimensionCode(null);// 二维码地址
			subbranch.setCreateTime(new Date());
			subbranch.setUpdateTime(new Date());
			return this.subbranchService.addSubbranch(subbranch);
		} catch (Exception e) {
			_log.error("申请成为分店操作失败！");
			throw new SubbranchException(e);
		}
	}
	
	@Override
	public Long applyAsSubById(Long userId, String shopId, Long subId, String submitNickName, String submitPhoneNo) throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			if (StringUtils.isEmpty(shopId) && subId == null) {
				AssertUtil.notNull(shopId, "总店ID：" + shopId + "为空,操作失败！");
				AssertUtil.notNull(subId, "分店ID：" + subId + "为空,操作失败！");
			}
			Subbranch subbranch = new Subbranch();
			User user = this.userService.getById(userId);
			if (user == null || user.getId() == null) {
				return -1905L;// 用户不存在
			}
			boolean isSubbranch = this.checkIsSubbranch(userId);
			if (isSubbranch) {
				_log.info("已经是分店，不能再申请成为分店");
				return -1907L;
			}
			if (StringUtils.isNotBlank(shopId) && subId == null) {
				Shop shop = this.shopService.getShopByNo(shopId);
				if (shop == null || !Constants.VALID.equals(shop.getStatus())) {
					return -1906L;// 总店不存在
				}
				if (userId == shop.getUserId().longValue()) {
					_log.info("自己不能申请成为自己的分店" + userId);
					return -1911L;
				}
				subbranch.setPid(0L);// 上级ID为0
				subbranch.setType(1);// 上级类型：P
				subbranch.setSuperiorUserId(shop.getUserId());// 上级用户Id
			} else if (StringUtils.isBlank(shopId) && subId != null) {
				Subbranch sub = this.subbranchService.findByPrimarykey(subId);
				if (!isInvalid(sub)) {
					return -1909L;// 分店不存在
				}
				if(!Constants.VALID.equals(sub.getStatus())){
					_log.info("上级分店无效," + subId);
					return -1909L;// 分店不存在
				}
				boolean flag = this.checkIsMaxBranchLevel(subId);
				if (flag) {
					_log.info("所申请分店已经是最大代理等级，暂时不能申请成为其代理");
					return -1920L;
				}
				if (userId == sub.getUserId().longValue()) {
					_log.info("自己不能申请成为自己的分店" + userId);
					return -1911L;
				}
				/** P不能申请BF代理的自己的分店，从而代理自己的分店 */
				Shop supplierShop = this.shopService.getShopByNo(sub.getSupplierShopNo());
				AssertUtil.notNull(supplierShop, "供货商店铺查询不存在,申请操作失败！");
				if (userId.longValue() == supplierShop.getUserId().longValue()) {
					_log.info("P不能申请BF代理的自己的分店，从而代理自己的分店!");
					return -1922L;
				}
				subbranch.setPid(sub.getId());// 上级ID
				subbranch.setType(2);// 上级类型：BF
				subbranch.setSuperiorUserId(sub.getUserId());// 上级USerId
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setUserId(userId);// 分店用户ID
			subbranch.setUserName(StringUtils.isNotBlank(submitNickName) ? submitNickName : StringUtils.isNotBlank(user
					.getNickname()) ? user.getNickname() : user.getLoginAccount());// 分店用户名称(默认是登陆账户名)
			subbranch.setUserCell(user.getLoginAccount());// 分店用户账号
			subbranch.setUserBindingCell(StringUtils.isNotBlank(submitPhoneNo) ? submitPhoneNo : user.getCell());// 分店用户绑定手机号
			subbranch.setShopName(user.getLoginAccount());// 分店名称.默认为分店账号
			subbranch.setDeliver(0);// 发货权限:0,无;1,有
			subbranch.setProfit(0);// 佣金比例(上级设置)
			Boolean isShopsSub = false;
			if (StringUtils.isNotBlank(shopId) && subId == null) {
				Shop shop = this.shopService.getShopByNo(shopId);
				subbranch.setSupplierShopNo(shop.getNo());// 供货商店铺号
				isShopsSub = true;
			} else if (StringUtils.isEmpty(shopId) && subId != null) {
				Subbranch sub = this.subbranchService.findByPrimarykey(subId);
				subbranch.setSupplierShopNo(sub.getSupplierShopNo());// 供货商店铺号
				isShopsSub = false;
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setStatus(1);// // 状态:1,有效;0,冻结;-1:删除
			subbranch.setTwoDimensionCode(null);// 二维码地址
			subbranch.setCreateTime(new Date());
			subbranch.setUpdateTime(new Date());
			Long subbranchId = this.subbranchService.addSubbranch(subbranch);
			/** 申请成为分店后给总店PUSH消息,10新分店加盟 */
			try {
				this.subbranchMetaOperateProducer.senderAnsy(subbranchId, ConstantsUtil.PUSH_NEW_SUBBRANCH_MESSAGE, isShopsSub);
			} catch (Exception e) {
				_log.error("申请成为分店MetaQ推送异常！", e);
			}
			return subbranchId;
		} catch (Exception e) {
			_log.error("申请成为分店操作失败！");
			throw new SubbranchException(e);
		}
	}

	/**
	 * 
	 * @Title: checkIsSubbranch 
	 * @Description: 查询是否已经是分店
	 * @param userId
	 * @return 如果已经是分店，返回true
	 * 			如果不是分店，返回false
	 * @date 2015年7月6日 下午3:33:37  
	 * @author cbc
	 */
	private boolean checkIsSubbranch(Long userId) {
		List<Subbranch> list = subbranchService.findByUserId(userId);
		if (null != list && list.size() >= 1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: checkIsMaxBranchLevel 
	 * @Description: 验证该分店已经是最大代理等级 
	 * @param subId
	 * @return 如果是最大代理等级，返回true
	 * 			如果不是最大代理等级，则可以继续申请，返回false
	 * @date 2015年7月6日 下午1:57:55  
	 * @author cbc
	 */
	private boolean checkIsMaxBranchLevel(Long subId) {
		String maxBranchLevel = PropertiesUtil.getContexrtParam(MAX_BRANCH_LEVEL);
		AssertUtil.hasLength(maxBranchLevel, "subbranchConfig.properties未配置maxBranchLevel字段");
		List<Subbranch> subbranchList = subbranchProducer.findSubbranchList(subId);
		if (null != subbranchList) {
			if (subbranchList.size() >= Integer.valueOf(maxBranchLevel)) {
				//说明已经是最大代理等级，返回true
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkExistAsSub(int type, Long thisUserId, Long preUserId) {
		try {
			AssertUtil.notNull(type, "Param[type] can not be null.");
			AssertUtil.notNull(thisUserId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(preUserId, "Param[preUserId] can not be null.");
			return this.subbranchService.checkExistAsSub(type, thisUserId, preUserId);
		} catch (Exception e) {
			_log.error("检查是否分店已经存在失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public void freezeSubbranch(Long subbranchId, Integer status) throws SubbranchException {
		try {
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(status, "Param[status] can not be null.");
			Subbranch updateSub = this.subbranchService.findByPrimarykey(subbranchId);
			if (isInvalid(updateSub)) {
				updateSub.setStatus(status);// 状态:1,有效;0,冻结;-1:删除
				updateSub.setUpdateTime(new Date());
				this.subbranchService.updateByPrimaryKey(updateSub);
				_log.info("冻结/解冻店铺" + subbranchId + "成功.");
			} else {
				_log.error("操作的店铺不存在！");
			}
		} catch (Exception e) {
			_log.error("冻结或者解冻分店操作失败！");
			throw new SubbranchException(e);
		}

	}

	@Override
	public void setSubbranchProfit(Long subbranchId, Integer profit) throws SubbranchException {
		try {
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(profit, "Param[profit] can not be null.");
			AssertUtil.isTrue(checkProfit(profit), "佣金比例必须在0到100之间");
			Subbranch updateSub = this.subbranchService.findByPrimarykey(subbranchId);
			if (isInvalid(updateSub)) {
				updateSub.setProfit(profit);// 佣金比例(上级设置)
				updateSub.setUpdateTime(new Date());
				this.subbranchService.updateByPrimaryKey(updateSub);
				_log.info("店铺" + subbranchId + "设置佣金比例成功.");
			} else {
				_log.error("操作的店铺不存在！");
			}
			_log.error("操作的店铺不存在");
		} catch (Exception e) {
			_log.error("设置分店佣金比例操作失败！");
			throw new SubbranchException(e);
		}

	}
	
	/**
	 * 
	 * @Title: checkProfit 
	 * @Description: 对佣金比例进行校验
	 * @param profit
	 * @return
	 * @date 2015年7月7日 上午11:51:33  
	 * @author cbc
	 */
	private boolean checkProfit(Integer profit) {
		return (profit.intValue()>=0 && profit.intValue()<=100);
	}
	
	@Override
	public void authorizedDeliver(Long subbranchId, Integer deliver) throws SubbranchException {
		try {
			AssertUtil.notNull(subbranchId, "Param[subbranch] can not be null.");
			AssertUtil.notNull(deliver, "Param[deliver] can not be null.");
			Subbranch updateSub = this.subbranchService.findByPrimarykey(subbranchId);
			if (isInvalid(updateSub)) {
				updateSub.setDeliver(deliver);// 发货权限:0,无;1,有
				updateSub.setUpdateTime(new Date());
				this.subbranchService.updateByPrimaryKey(updateSub);
			} else {
				_log.error("操作的店铺不存在！");
			}
		} catch (Exception e) {
			_log.error("授权发货操作失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public void updateSubbranchName(Long subbranchId, String shopName) throws SubbranchException {
		try {
			AssertUtil.notNull(subbranchId, "Param[subbranchId] can not be null.");
			AssertUtil.notNull(shopName, "Param[shopName] can not be null.");
			shopName = shopName.replaceAll("\\s*", ""); // 去除空格
			Subbranch updateSub = this.subbranchService.findByPrimarykey(subbranchId);
			if (isInvalid(updateSub)) {
				/** 需求变动，不要存成上级"点名-下级店名"的形式 */
				Subbranch subbranch = new Subbranch();
				subbranch.setId(subbranchId);
				subbranch.setShopName(shopName);
				subbranch.setUpdateTime(new Date());
				this.subbranchService.updateByPrimaryKey(subbranch);
			} else {
				_log.error("操作的店铺不存在！");
			}
		} catch (Exception e) {
			_log.error("更新店铺名失败！");
			throw new SubbranchException(e);
		}

	}

	@Override
	@Transactional
	public Long updateSubbranchInfo(Subbranch subbranch) throws SubbranchException {
		try {
			AssertUtil.notNull(subbranch, "Param[subbranch] can not be null.");
			AssertUtil.notNull(subbranch.getId(), "Param[id] can not be null.");
			AssertUtil.notNull(subbranch.getActionUserId(), "Param[actionUserId] can not be null.");
			Subbranch sub = this.findSubbranchById(subbranch.getId());
			Date date=new Date();
			if (sub != null && sub.getSuperiorUserId().intValue() == subbranch.getActionUserId().intValue()) {
				if(Integer.valueOf(1).equals(subbranch.getFreezeShop())){
					List<Shop> list=shopService.findShopListByUserId(sub.getUserId());
					for(Shop s:list){
						Shop edit=new Shop();
						edit.setNo(s.getNo());
						/** 冻结分店自有店铺*/
						edit.setStatus(3);
						edit.setUpdateTime(date);
						shopService.editShop(edit);
					}
				}else if(Integer.valueOf(0).equals(subbranch.getFreezeShop())){
					List<Shop> list=shopService.findShopListByUserId(sub.getUserId());
					for(Shop s:list){
						Shop edit=new Shop();
						edit.setNo(s.getNo());
						/** 解冻分店自有店铺*/
						edit.setStatus(1);
						edit.setUpdateTime(date);
						shopService.editShop(edit);
					}
				}
				this.subbranchService.updateByPrimaryKey(subbranch);
			} else {
				if (sub == null) {
					_log.error("要更新的分店不存在！");
					return -1909L;
				}
				if (!sub.getSuperiorUserId().equals(subbranch.getActionUserId())) {
					_log.error("当前操作者不是该分店的上级，无权限操作！");
					return -1916L;
				}
			}
			/** 总店更改了分店佣金和发货权限PUSH消息 */
			try {
				if (subbranch.getProfit().intValue() != sub.getProfit().intValue()) {
					this.subbranchMetaOperateProducer.senderAnsy(subbranch.getId(), ConstantsUtil.PUSH_PROFIT_MESSAGE, false);// 更新了佣金比例
				}
				if (subbranch.getDeliver().intValue() != sub.getDeliver().intValue()) {
					this.subbranchMetaOperateProducer.senderAnsy(subbranch.getId(), ConstantsUtil.PUSH_DELIVER_MESSAGE, false);// 更新了发货权限
				}
				if(Integer.valueOf(0).equals(sub.getFreezeShop())&&Integer.valueOf(1).equals(subbranch.getFreezeShop())){
					this.subbranchMetaOperateProducer.senderAnsy(subbranch.getId(),
							ConstantsUtil.PUSH_SUBBRANCH_FREEZE_SHOP_MESSAGE, false);
				}
				if(Integer.valueOf(1).equals(sub.getFreezeShop())&&Integer.valueOf(0).equals(subbranch.getFreezeShop())){
					this.subbranchMetaOperateProducer.senderAnsy(subbranch.getId(),
							ConstantsUtil.PUSH_SUBBRANCH_UNFREEZE_SHOP_MESSAGE, false);
				}
				
			} catch (Exception e) {
				_log.error("申请成为分店MetaQ推送异常！", e);
			}
			return subbranch.getId();
		} catch (Exception e) {
			_log.error("更新分店名失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public List<SubbranchVo> findMySubbrachs(Long userId, String keyword, Integer role) {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(keyword, "Param[keyword] can not be null.");
			keyword = keyword.replaceAll("\\s*", "");
			List<SubbranchVo> result = new ArrayList<SubbranchVo>();
			List<SubbranchVo> list = this.subbranchService.findMySubbrachs(userId, keyword, role);
			if (result != null) {
				for (SubbranchVo current : list) {
					if (StringUtils.isNotBlank(current.getSupplierShopNo())) {
						Shop shop = this.shopService.getShopByNo(current.getSupplierShopNo());
						if (shop != null) {
							current.setShopLogo(shop.getShopLogoUrl());
						}
						User user = this.userService.getById(current.getUserId());
						if (user != null) {
							current.setSubPhone(user.getLoginAccount());
						}
						result.add(current);
					}
				}
			}
			return result;
		} catch (Exception e) {
			_log.error("根据分店名，姓名或者分店账号查找我的分店失败！");
			throw new SubbranchException(e);
		}
	}

	private static boolean isInvalid(Subbranch subbranch) {
		if (subbranch != null) {
			if (subbranch.getId() == null) {
				return false;
			}
			if (subbranch.getType() == null) {
				return false;
			}
			if (subbranch.getSuperiorUserId() == null) {
				return false;
			}
			if (subbranch.getUserId() == null) {
				return false;
			}
			if (StringUtils.isEmpty(subbranch.getUserName())) {
				return false;
			}
//			if (StringUtils.isEmpty(subbranch.getShopName())) {
//				return false;
//			}
			if (subbranch.getDeliver() == null) {
				return false;
			}
			if (subbranch.getProfit() == null) {
				return false;
			}
			if (StringUtils.isEmpty(subbranch.getSupplierShopNo())) {
				return false;
			}
			if (subbranch.getStatus() == null) {
				return false;
			}
			if (subbranch.getCreateTime() == null) {
				return false;
			}
			if (subbranch.getUpdateTime() == null) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public Subbranch findByUserCell(String userCell) {
		try {
			AssertUtil.hasLength(userCell, "参数userCell为空");
			return subbranchService.findByUserCell(userCell);
		} catch (Exception e) {
			_log.error("根据分店手机号查询分店关系信息失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	@Transactional
	public boolean unBindSubbranch(Long userId, String cell)
			throws SubbranchException {
		try {
			//参数验证
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.cdByMsg(), "userId");
			AssertUtil.hasLength(cell, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.cdByMsg(), "cell");
			Subbranch subbranch = subbranchService.findByUserCell(cell);
			AssertUtil.notNull(subbranch, CErrMsg.NULL_RESULT.getErrCd(), CErrMsg.NULL_RESULT.cdByMsg(), "代理关系");
			return this.unBindSubbranch(userId, subbranch);
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}

	@Override
	@Transactional
	public boolean unBindSubbranch(Long userId, Long subbranchId)
			throws SubbranchException {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.cdByMsg(), "userId");
			AssertUtil.notNull(subbranchId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.cdByMsg(), "subbranchId");
			Subbranch subbranch = subbranchService.findByPrimarykey(subbranchId);
			AssertUtil.notNull(subbranch, CErrMsg.NULL_RESULT.getErrCd(), CErrMsg.NULL_RESULT.cdByMsg(), "代理关系");
			return this.unBindSubbranch(userId, subbranch);
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}
	/**
	 * 供货商解绑关系
	 * @Title: unBindSubbranch 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 供货商用户id
	 * @param subbranch 分店合约
	 * @return
	 * @throws SubbranchException
	 * @date 2015年7月9日 下午3:57:16  
	 * @author lys
	 */
	@Transactional
	private boolean unBindSubbranch(Long userId, Subbranch subbranch)
			throws SubbranchException {
		//用户权限验证
		AssertUtil.isTrue(userId.equals(subbranch.getSuperiorUserId()), CErrMsg.OPERATION_RIGHT.getErrCd(),
				CErrMsg.OPERATION_RIGHT.cdByMsg(), userId, "解除代理关系");
		//链式关系
		List<Subbranch> list = subbranchService.findSubbranchList(subbranch.getId());
		if (null == list || list.isEmpty()) {
			return false;
		}
		//购物车信息
		for (Subbranch data : list) {
			if (null == data) continue;
			cartProducer.clearSubbranchInfo(data.getId());
		}
		List<Shop> shopList=shopService.findShopListByUserId(subbranch.getUserId());
		Date date=new Date();
		/** 总店冻结分店自有店铺时恢复分店自有店铺*/
		for(Shop s:shopList){
			if(s.getStatus()==3){
				Shop edit=new Shop();
				edit.setNo(s.getNo());
				edit.setStatus(1);
				edit.setUpdateTime(date);
				shopService.editShop(edit);
			}
		}
		//链条式解除关系
		boolean istrue = subbranchService.unifyStatus(subbranch.getId(), Constants.UNVALID);
		
		try{
			systemMetaOperateProducer.ansyAll(list);
		}catch(Exception e){
			_log.error("分店关系解除MetaQ推送异常！", e);
		}
		
		return istrue;
	}
	@Override
	public Pagination<Subbranch> findMyHistorySubbrachs(SubbranchVo subbranchVo,Pagination<Subbranch> pagination) {
		try {	
			AssertUtil.notNull(subbranchVo.getUserId(), CErrMsg.NULL_PARAM.getErrCd(), "param [userId] can't be null.");
			return subbranchService.findMyHistorySubbrachs(subbranchVo,pagination);
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}
	@Override
	public boolean checkExistAsHistorySub(Long userId) {
		try {
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), "param [userId] can't be null.");
			return subbranchService.checkExistAsHistorySub(userId);
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}
	@Override
	public Pagination<Subbranch> findHistorySubbranchByUserIdPage(Long userId, Pagination<Subbranch> pagination)
			throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "参数userid不能为空");
			AssertUtil.notNull(pagination, "参数pagination不能为空");
			AssertUtil.notNull(pagination.getStart(), "pagination.start不能为空");
			AssertUtil.notNull(pagination.getPageSize(), "pagination.pageSize不能为空");
			Pagination<Subbranch> page = subbranchService.findHistorySubbranchByUserIdPage(userId, pagination);
			List<Subbranch> datas = page.getDatas();
			if (datas != null && !datas.isEmpty()) {
				for (Subbranch subbranch : datas) {
					boolean flag = this.checkHistorySubbanchIsMaxBranchLevel(subbranch.getId());
					subbranch.setIsAbleToRecruit(!flag);
					Shop shop = shopService.getShopByNo(subbranch.getSupplierShopNo());
					String shopName = subbranch.getShopName();
					shopName = shop.getName()+"-"+shopName;
					subbranch.setShopName(shopName);
					subbranch.setShopLogo(shop.getShopLogoUrl());
				}
			}
			return page;
		} catch (Exception e) {
			_log.error("分页查询历史分店失败");
			throw new SubbranchException(e);
		}
	}

	/**
	 * 
	 * @Title: checkHistorySubbanchIsMaxBranchLevel 
	 * @Description: 查询这个历史店铺当初能否招募下级代理
	 * @param subId
	 * @return
	 * @date 2015年7月14日 下午4:53:35  
	 * @author cbc
	 */
	private boolean checkHistorySubbanchIsMaxBranchLevel(Long subId) {
		String maxBranchLevel = PropertiesUtil.getContexrtParam(MAX_BRANCH_LEVEL);
		AssertUtil.hasLength(maxBranchLevel, "subbranchConfig.properties未配置maxBranchLevel字段");
		List<Subbranch> subbranchList = this.findHistorySubbranchList(subId);
		if (null != subbranchList) {
			if (subbranchList.size() >= Integer.valueOf(maxBranchLevel)) {
				//说明已经是最大代理等级，返回true
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: findHistorySubbranchList 
	 * @Description: 查询历史店铺的层级
	 * @param subId
	 * @return
	 * @date 2015年7月15日 上午10:41:42  
	 * @author cbc
	 */
	private List<Subbranch> findHistorySubbranchList(Long subId) {
		try {
			List<Subbranch> subbranchs = new ArrayList<Subbranch>();
			while (true) {
				Subbranch subbranch = subbranchService.findSubbranchByIdWithNoStatus(subId);
				if (null == subbranch) break;
				subbranchs.add(subbranch);
				subId = subbranch.getPid();
			}
			Collections.sort(subbranchs, new Comparator<Subbranch>() {
				@Override
				public int compare(Subbranch o1, Subbranch o2) {
					if (null ==o1 && null == o2) {
						return 0;
					}
					if (null == o1) {
						return 1;
					}
					if (null == o2) {
						return -1;
					}
					return o1.getPid().compareTo(o2.getPid());
				}
			});
			return subbranchs;
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}

	@Override
	public boolean isExistHistorySubbranch(Long userId) {
		try {
			AssertUtil.notNull(userId, "参数userId不能为空");
			Integer count = subbranchService.countHistorySubbranch(userId);
			return count > 0;
		} catch (RuntimeException e) {
			_log.error("查询是否存在历史分店失败");
			throw new SubbranchException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName={"SubbranchException"})
	public Long applyAsSubByIdWithoutUser(String shopId, Long subId,String submitNickName, String submitPhoneNo)
			throws SubbranchException {
		try {
			AssertUtil.notNull(submitPhoneNo, "分店手机：" + submitPhoneNo + "为空,操作失败！");
			AssertUtil.notNull(submitNickName, "用户昵称：" + submitPhoneNo + "为空,操作失败！");
			if (StringUtils.isEmpty(shopId) && subId == null) {
				AssertUtil.notNull(shopId, "总店ID：" + shopId + "为空,操作失败！");
				AssertUtil.notNull(subId, "分店ID：" + subId + "为空,操作失败！");
			}
			User user=userService.getAllUserByLoginAccount(submitPhoneNo);
			if (null == user || user.getId() == null ) {
				return -1900L;
			}
			if(user!=null&&user.getId()!=null){
				return this.applyAsSubById(user.getId(), shopId, subId, submitNickName, submitPhoneNo);
			} 
			List<UnregisteredSubUser> subUsers=unregisteredSubUserService.findByLoginAccount(submitPhoneNo);
			if(subUsers!=null&&subUsers.size()>0){
				return -1907L;//已经是分店，不能再申请成为分店
			}
			Long fakeId=~System.currentTimeMillis();//假用户ID 取时间戳的反
			Subbranch subbranch = new Subbranch();
			if (StringUtils.isNotBlank(shopId) && subId == null) {
				Shop shop = this.shopService.getShopByNo(shopId);
				if (shop == null || StringUtils.isEmpty(shop.getNo())) {
					return -1906L;// 总店不存在
				}
				subbranch.setPid(0L);// 上级ID为0
				subbranch.setType(1);// 上级类型：P
				subbranch.setSuperiorUserId(shop.getUserId());// 上级用户Id
			} else if (StringUtils.isBlank(shopId) && subId != null) {
				boolean flag = this.checkIsMaxBranchLevel(subId);
				if (flag) {
					_log.info("所申请分店已经是最大代理等级，暂时不能申请成为其代理");
					return -1920L;
				}
				Subbranch sub = this.subbranchService.findByPrimarykey(subId);
				if (!isInvalid(sub)) {
					return -1909L;// 分店不存在
				}
				subbranch.setPid(sub.getId());// 上级ID
				subbranch.setType(2);// 上级类型：BF
				subbranch.setSuperiorUserId(sub.getUserId());// 上级USerId
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setUserId(fakeId);// 分店用户ID
			subbranch.setUserName(submitNickName);// 分店用户名称(默认是登陆账户名)
			subbranch.setUserCell(submitPhoneNo);// 分店用户账号
			subbranch.setUserBindingCell(submitPhoneNo);// 分店用户绑定手机号
			subbranch.setShopName(submitPhoneNo);// 分店名称.默认为分店账号
			subbranch.setDeliver(0);// 发货权限:0,无;1,有
			subbranch.setProfit(0);// 佣金比例(上级设置)
			if (StringUtils.isNotBlank(shopId) && subId == null) {
				Shop shop = this.shopService.getShopByNo(shopId);
				subbranch.setSupplierShopNo(shop.getNo());// 供货商店铺号
			} else if (StringUtils.isEmpty(shopId) && subId != null) {
				Subbranch sub = this.subbranchService.findByPrimarykey(subId);
				subbranch.setSupplierShopNo(sub.getSupplierShopNo());// 供货商店铺号
			} else {
				return -1910L;// 查询结果条件不符合，操作失败
			}
			subbranch.setStatus(1);// // 状态:1,有效;0,冻结;-1:删除
			subbranch.setTwoDimensionCode(null);// 二维码地址
			subbranch.setCreateTime(new Date());
			subbranch.setUpdateTime(new Date());
			unregisteredSubUserBusiness.addUnregisteredSubUser(submitPhoneNo, submitNickName);
			return this.subbranchService.addSubbranch(subbranch);
		} catch (Exception e) {
			_log.error("申请成为分店操作失败！");
			throw new SubbranchException(e);
		}
	}
	
	@Override
	public Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(
			Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo) {
		try {
			AssertUtil.notNull(pagination, "参数pagination不能为空");
			AssertUtil.notNull(subbranchVo, "参数subbranchVo不能为空");
			AssertUtil.notNull(subbranchVo.getSuperiorUserId(), "subbranchVo的superiorUserId不能为空");
			AssertUtil.notNull(subbranchVo.getSupplierShopNo(), "subbranchVo的supplierShopNo不能为空");
			return subbranchService.findSubbranchWithNamePhoneDelievery(pagination, subbranchVo);
		} catch (Exception e) {
			_log.error("分页查询分店失败！");
			throw new SubbranchException(e);
		}
	}

	@Override
	public SubbranchVo getActiveSubbranchByUserId(Long userId) throws SubbranchException {
		try {
			AssertUtil.notNull(userId, "参数userId不能为空");
			return subbranchService.getActiveSubbranchByUserId(userId);
		} catch (Exception e) {
			throw new SubbranchException(e);
		}
	}
	

	
}
