package com.zjlp.face.web.server.user.customer.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.exception.ext.AppGroupException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.user.customer.business.CustomerBusiness;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerDetailDto;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerGroupRelationVo;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerVo;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;
import com.zjlp.face.web.server.user.customer.service.AppCustomerGroupRelationService;
import com.zjlp.face.web.server.user.customer.service.AppCustomerService;
import com.zjlp.face.web.server.user.customer.service.AppGroupService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.AddressService;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.AppDateUtils;
import com.zjlp.face.web.util.Identity;


/**
 * 好友、客户分组、标签
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:37:26
 *
 */
@Service
public class CustomerBusinessImpl implements CustomerBusiness {

	public static final String MEDIUM = "MEDIUM";

	public static final String ALL_NUMBER = "^[0-9]*$";

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private ShopService shopService;
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private AppGroupService appGroupService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private AppCustomerService appCustomerService;
	@Autowired
	private AppCustomerGroupRelationService appCustomerGroupRelationService;

	@Override
	public Pagination<MyCustomerVo> getMyCustomerByUserId(Integer shopType, Long userId, String customerName, String orderBy, Pagination<MyCustomerVo> pagination) {
		try {
			AssertUtil.notNull(userId, "userId为空，无法查询", "找不到客户");
			AssertUtil.notNull(shopType, "查找店铺类型化为空，操作失败！");
			Pagination<MyCustomerVo> mysCustomerPage = appCustomerService.getMyCustomerById(shopType, userId, orderBy, customerName, pagination);
			if (mysCustomerPage != null) {
				for (MyCustomerVo current : mysCustomerPage.getDatas()) {
					current.setTotalPriceStr(CalculateUtils.converFenToYuan(current.getPayPrice()));// payPrice已经包括邮费
					current.setPicture(current.getHearderPicture());
					User user = this.userService.getById(current.getCustomerId());
					if (user != null) {
						current.setCustomerName(StringUtils.isNotBlank(user.getNickname()) ? user.getNickname() : user.getLoginAccount());
						current.setCell(StringUtils.isBlank(current.getCell()) ? user.getCell() : user.getLoginAccount());
					}
				}
			}
			return mysCustomerPage;
		} catch (Exception e) {
			_log.error("获取客户列表失败！", e);
			throw new AppCustomerException(e.getMessage(), e);
		}
	}

	@Override
	public CustomerDetailDto getCustomerDetailByIds(Long userId, Long customerId) {
		try {
			AssertUtil.notNull(userId, "userId为空，无法查询", "找不到客户");
			AssertUtil.notNull(customerId, "customerId为空，无法查询", "找不到客户");
			CustomerDetailDto results = new CustomerDetailDto();
			User customerInfo = this.userService.getById(customerId);// 客户信息
			if (customerInfo != null) {
				results.setCustomerId(customerId);// 客户ID
				// 客户相关全部取最新昵称
				results.setCustomerName(customerInfo.getNickname());// 客户姓名
				results.setBirthday(coverToString(getBirthdayFromIdCard(customerInfo.getIdentity())));// 客户生日
				results.setCell(customerInfo.getCell());// 电话
				Address customerAdd = this.addressService.getDefaultAddress(customerId);
				if (customerAdd != null) {
					results.setAddress(customerAdd.getAddressDetail()); // 默认地址
				}
				results.setWeChat(customerInfo.getWechat());// 微信号
				// 先查出客户主键
				AppCustomer customer = this.appCustomerService.findAppCustomer(userId, customerId);
				// 一个客户只会在一个用户下的一个分组
				if (customer != null && customer.getId() != null) {
					AppCustomerGroupRelationVo relationVo = new AppCustomerGroupRelationVo();
					relationVo.setType(0);
					relationVo.setCustomerId(customer.getId());
					relationVo.setUserId(userId);
					// 查出关系
					AppCustomerGroupRelation currentRelation = this.appCustomerGroupRelationService.selectUserIsInGroup(relationVo);
					// 查出所在分组
					if (currentRelation != null && currentRelation.getId() != null) {
						AppCustomerGroupRelation result = this.appCustomerGroupRelationService.findAppGroupRelationShipById(currentRelation.getId());
						results.setAppGroupList(Arrays.asList(result));
					} else {
						// 查出所在未分组
						List<AppGroup> appgroups = this.appGroupService.getUngroups(userId, 0);
						if (appgroups != null && appgroups.size() == 1) {
							AppCustomerGroupRelation unGroup = new AppCustomerGroupRelation(0);
							unGroup.setId(appgroups.get(0).getId());
							unGroup.setGroupName(appgroups.get(0).getName());
							results.setAppGroupList(Arrays.asList(unGroup));
						}
					}
				}
				AppCustomer customerGrop = this.appCustomerService.findAppCustomer(userId, customerId);
				if (customerGrop != null) {
					results.setRemark(customerGrop.getRemark());// 客户购物喜好备注
					results.setId(customerGrop.getId());// 主键
				}
			}
			return results;
		} catch (Exception e) {
			throw new AppCustomerException(e.getMessage(), e);
		}
	}

	private Date getBirthdayFromIdCard(String idCard) {
		if (StringUtils.isNotBlank(idCard) && Identity.checkIDCard(idCard)) {
			String dateStr = Identity.getIDDate(idCard, true);
			return StringUtils.isNotBlank(dateStr) ? AppDateUtils.stringToDate(dateStr) : null;
		}
		return null;
	}

	private String coverToString(Date date) {
		if (date != null) {
			return AppDateUtils.dateToString(date, MEDIUM);
		}
		return StringUtils.EMPTY;
	}

	@Override
	public List<AppGroup> getMyCustomerGroupList(Long userId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法查询");
			return this.appGroupService.findAppGroupListByUserId(userId, type);
		} catch (Exception e) {
			throw new AppCustomerException(e.getMessage(), e);
		}
	}

	@Override
	public AppCustomer getMyAppCustomer(AppCustomer appCustomer) throws AppCustomerException {
		try {
			AssertUtil.notNull(appCustomer, "appCustomer为空，无法查询");
			return this.appCustomerService.findAppCustomer(appCustomer.getUserId(), appCustomer.getCustomerId());
		} catch (Exception e) {
			_log.error("获取分组列表失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public AppCustomer getMyAppCustomer(Long id, Long userId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(id, "appCustomer为空，无法查询");
			AssertUtil.notNull(userId, "appCustomer为空，无法查询");
			return this.appCustomerService.findMyAppCustomer(id, userId, type);
		} catch (Exception e) {
			_log.error("获取客户、朋友失败！", e);
			throw new AppCustomerException(e);
		}
	}

    //一个客户分到多个分组，新分组可选
	@Override
	public Long addCustomerToGroups(Long userId, Long customerId, List<Long> goalGroupIds, String groupName, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法查询");
			AssertUtil.notNull(customerId, "customerId为空，无法查询");
			AssertUtil.notEmpty(goalGroupIds, "goalGroupIds为空，无法查询");
			AppGroup newGroup = new AppGroup(type);
			AppCustomerGroupRelation groupRelation = new AppCustomerGroupRelation(type);
			// 多个分组
			boolean isUngroup = false;
			for (Long groupId : goalGroupIds) {
				// 如果移动目标组是"未分组" 则删除原有分组关系,客户自动进入未分组
				if (this._checkIsUngroup(userId, customerId, groupId, type)) {
					isUngroup = true;
					break;
				}
				Long code = this._setRelationFK(groupRelation, type, customerId);
				if (code < 0) {
					return code;
				}
				// 查出该分组
				AppGroup addGroup = this.appGroupService.findAppGroupById(groupId);
				// 维护关系表中的分组名
				if (addGroup != null) {
					groupRelation.setGroupName(addGroup.getName());
				}
				// 维护关系表中 分组ID外键
				groupRelation.setGroupId(addGroup.getId());
				groupRelation.setCreateTime(new Date());
				groupRelation.setUpdateTime(new Date());
				// 检查分组关系是否已经在
				if (!this._checkRelationExist(type, customerId, groupId, userId)) {
					AppCustomerGroupRelation oldRelation = this._getFullExistRelation(type, customerId, null, userId, false);
					if (oldRelation != null && oldRelation.getId() != null) {
						_log.info("删除分组关系:" + oldRelation.getId());
						this.appCustomerGroupRelationService.removeAppGroupRelationShip(oldRelation.getId());
					}
					Long id = this.appCustomerGroupRelationService.addAppGroupRelationShip(groupRelation);// 添加分组
					_log.info("成功创建分组关系:" + id);
				} else {
					AppCustomerGroupRelation updateRelation = new AppCustomerGroupRelation();
					AppCustomerGroupRelation existRelation = this._getFullExistRelation(type, customerId, groupId, userId, true);
					if (existRelation != null) {
						updateRelation.setId(existRelation.getId());
					} else {
						_log.error("分组关系不存在！");
						return -1905L;
					}
					updateRelation.setType(type);
					updateRelation.setGroupId(groupId);
					Long result = this._setRelationFK(groupRelation, type, customerId);
					if (result < 0) {
						return result;
					}
					updateRelation.setUpdateTime(new Date());
					this.appCustomerGroupRelationService.updateAppGroupRelationShip(updateRelation);// 更新分组
				}
			}
			// 新增分组 可选
			if (StringUtils.isNotBlank(groupName)) {
				newGroup.setUserId(userId);
				newGroup.setName(groupName);
				newGroup.setUngrouped(0);// 不是未分组
				newGroup.setCreateTime(new Date());
				newGroup.setUpdateTime(new Date());
				// 保证当前用户下分组不重名
				AppGroup duplicateName = this.appGroupService.findAppGroupByUserIdAndGroupName(newGroup);
				if (duplicateName == null || duplicateName.getId() == null) {
					groupRelation = new AppCustomerGroupRelation();
					// 新增分组
					this.appGroupService.addAppGroup(newGroup);
					// 查出刚刚新增分组
					AppGroup addJustNow = this.appGroupService.findAppGroupByUserIdAndGroupName(newGroup);
					if (this._checkCustomerExist(customerId) || this._checkUserExist(customerId)) {
						// 关系表 客户外键
						groupRelation.setCustomerId(customerId);
					} else {
						_log.error("客户或者朋:" + customerId + "友不存在！");
						return -2204L;
					}
					// 关系表 分组外键
					groupRelation.setGroupId(addJustNow.getId());
					// 关系表 新分组名
					groupRelation.setGroupName(addJustNow.getName());
					// 创建时间
					groupRelation.setCreateTime(new Date());
					groupRelation.setUpdateTime(new Date());
					// 创建关系 新建分组，无需检查
					return this.appCustomerGroupRelationService.addAppGroupRelationShip(groupRelation);
				} else {
					_log.error("分组已存在");
					return -2211L;
				}
			} else {
				if (isUngroup) {
					_log.error("分组名为空");
					return -2212L;
				}
			}
			return 0L;
		} catch (Exception e) {
			_log.error("添加新分组失败！", e);
			throw new AppCustomerException(e);
		}
	}
	
	/**
	 * @Title: _checkIsUngroup
	 * @Description: (检查是否是"未分组")
	 * @param groupId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午11:28:54
	 */
	private boolean _checkIsUngroup(Long userId, Long customerId, Long groupId, Integer type) {
		try {
			AssertUtil.notNull(groupId, "id不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(type, "type不能为空");
			AppGroup group = this.appGroupService.findAppGroupById(groupId);
			AssertUtil.notNull(group, "查询结果为空");
			if (group.getUngrouped().intValue() == 1 && group.getUserId().equals(userId) && group.getType().equals(type)) {
				AppCustomerGroupRelationVo consumerGroupRelation = new AppCustomerGroupRelationVo();
				consumerGroupRelation.setType(type);
				this._setRelationVoFK(consumerGroupRelation, type, customerId);
				consumerGroupRelation.setUserId(userId);
				// 查出客户原有分组关系
				AppCustomerGroupRelation relationResult = this.appCustomerGroupRelationService.selectUserIsInGroup(consumerGroupRelation);
				AssertUtil.notNull(relationResult, "关系结果为空！");
				// 通过主键移除分组关系
				_log.info("删除分组关系:" + relationResult.getId());
				this.appCustomerGroupRelationService.removeAppGroupRelationShip(relationResult.getId());
				return true;
			}
		} catch (Exception e) {
			_log.error("检查是否存在分组内失败！", e);
		}
		return false;
	}

	// 多个（或者一个）客户分到多一个分组，新分组必选
	@Override
	public Long addCustomersToGroup(Long userId, List<Long> goalCustomerIds, Long goalGroupId, String groupName, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(groupName, "groupName为空，无法查询");
			AssertUtil.notNull(type, "type为空，无法查询");
			if (goalGroupId == null && StringUtils.isNotBlank(groupName)) {
				// 首先新增分组
				Integer maxSort = this.appGroupService.getMaxGroupSort(userId, type);// 新建分组顺序
				AppGroup newGroup = new AppGroup(type, maxSort != null ? maxSort + 1 : 0);
				newGroup.setUserId(userId);
				newGroup.setName(groupName);
				newGroup.setUngrouped(0);// 不是未分组
				newGroup.setCreateTime(new Date());
				newGroup.setUpdateTime(new Date());
				// 保证当前用户下分组不重名
				AppGroup duplicateName = this.appGroupService.findAppGroupByUserIdAndGroupName(newGroup);
				AppCustomerGroupRelation groupRelation = new AppCustomerGroupRelation(type);
				if (duplicateName == null || duplicateName.getId() == null) {
					// 新增分组
					this.appGroupService.addAppGroup(newGroup);
					// 查出刚刚新增分组
					AppGroup addJustNow = this.appGroupService.findAppGroupByUserIdAndGroupName(newGroup);
					// 多个客户
					for (Long customerId : goalCustomerIds) {
						Long code = this._setRelationFK(groupRelation, type, customerId);
						if (code < 0) {
							return code;
						}
						// 关系表 分组外键
						groupRelation.setGroupId(addJustNow.getId());
						// 关系表 新分组名
						groupRelation.setGroupName(addJustNow.getName());
						// 创建时间
						groupRelation.setCreateTime(new Date());
						groupRelation.setUpdateTime(new Date());
						// 创建关系
						if (!this._checkRelationExist(type, customerId, addJustNow.getId(), userId)) {
							// 移除原有分组，此处不能用更新
							AppCustomerGroupRelation oldRelation = this._getFullExistRelation(type, customerId, null, userId, false);
							if (oldRelation != null && oldRelation.getId() != null) {
								_log.info("删除分组关系:" + oldRelation.getId());
								this.appCustomerGroupRelationService.removeAppGroupRelationShip(oldRelation.getId());
							}
							// 添加到新分组
							Long id = this.appCustomerGroupRelationService.addAppGroupRelationShip(groupRelation);
							_log.info("成功加入分组：" + id);
						}
					}
				}else{
					_log.error("分组已存在");
					return -2211L;
				}
			}
			return 0L;
		} catch (Exception e) {
			_log.error("客户、朋友分配分组失败失败！", e);
			throw new AppCustomerException(e);
		}
	}

	/**
	 * @Title: _setRelationFK
	 * @Description: (设置并检查关系表中的userId外键)
	 * @param relation
	 * @param type
	 * @param customerId
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午1:04:06
	 */
	private Long _setRelationFK(AppCustomerGroupRelation relation, Integer type, Long customerId) {
		if (type == 0) {
			if (this._checkCustomerExist(customerId)) {
				relation.setCustomerId(customerId);
			} else {
				_log.error("要维护的外键客户不存在！");
				return -2204L;
			}
		} else if (type == 1) {
			if (this._checkUserExist(customerId)) {
				relation.setFriendId(customerId);
			} else {
				_log.error("要维护的外键朋友不存在！");
				return -1905L;
			}
		} else {
			_log.info("暂不支持的类型:" + type);
			return -2206L;
		}
		return 0L;
	}

	/**
	 * @Title: _setRelationVoFK
	 * @Description: (设置并检查关系表中的userId外键)
	 * @param relation
	 * @param type
	 * @param customerId
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午1:25:35
	 */
	private Long _setRelationVoFK(AppCustomerGroupRelationVo relation, Integer type, Long customerId) {
		if (type == 0) {
			if (this._checkCustomerExist(customerId)) {
				relation.setCustomerId(customerId);
			} else {
				_log.error("要维护的外键客户不存在！");
				return -2204L;
			}
		} else if (type == 1) {
			if (this._checkUserExist(customerId)) {
				relation.setFriendId(customerId);
			} else {
				_log.error("要维护的外键朋友不存在！");
				return -1905L;
			}
		} else {
			_log.info("暂不支持的类型:" + type);
			return -2206L;
		}
		return 0L;
	}

	/**
	 * @Title: _checkCustomerExist
	 * @Description: (检查客户是否存在)
	 * @param customerId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午3:15:56
	 */
	private boolean _checkCustomerExist(Long id) {
		if (id != null) {
			AppCustomer appCustomer = this.appCustomerService.findAppCustomerById(id);
			if (appCustomer != null && appCustomer.getId() != null)
				return true;
		}
		return false;
	}

	/**
	 * @Title: _checkUserExist
	 * @Description: (检查用户是否存在)
	 * @param userId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午3:19:40
	 */
	private boolean _checkUserExist(Long userId) {
		if (userId != null) {
			User user = this.userService.getById(userId);
			if (user != null && user.getId() != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Title: _checkGroupExist
	 * @Description: (检查分组是否存在)
	 * @param groupId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午3:39:02
	 */
	private boolean _checkGroupExist(Long groupId) {
		if (groupId != null) {
			AppGroup appGroup = this.appGroupService.findAppGroupById(groupId);
			if (appGroup != null && appGroup.getId() != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Title: _checkRelationExist
	 * @Description: (检查用户是否已经存在分组内（包括userId）)
	 * @param type
	 * @param customerId
	 * @param groupId
	 * @param userId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午4:24:25
	 */
	private boolean _checkRelationExist(Integer type, Long customerId, Long groupId, Long userId) {
		AppCustomerGroupRelation existRelation = this._getFullExistRelation(type, customerId, groupId, userId, true);
		if (existRelation != null && existRelation.getId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: _getExistRelation
	 * @Description: (查出分组关系是否存在（不包括userId）)
	 * @param type
	 * @param customerId
	 * @param groupId
	 * @param userId
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午5:12:59
	 */
	@SuppressWarnings("unused")
	private AppCustomerGroupRelation _getExistRelation(Integer type, Long customerId, Long groupId, Long userId) {
		AppCustomerGroupRelation relation = new AppCustomerGroupRelation(type);
		if (this._checkGroupExist(groupId)) {
			relation.setGroupId(groupId);
		} else {
			_log.error("分组不存在！");
			return null;
		}
		this._setRelationFK(relation, type, customerId);
		return this.appCustomerGroupRelationService.selectRelationByUserIdAndGrpId(relation);
	}

	/**
	 * @Title: _getExistRelation
	 * @Description: (查找还有关系是否存在)
	 * @param type
	 * @param customerId
	 * @param groupId
	 * @param userId
	 * @return
	 * @return AppCustomerGroupRelation 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 下午4:32:09
	 */
	private AppCustomerGroupRelation _getFullExistRelation(Integer type, Long customerId, Long groupId, Long userId, boolean checkGroupId) {
		AppCustomerGroupRelation existRelation = new AppCustomerGroupRelation();
		if (type != null && customerId != null && userId != null) {
			AppCustomerGroupRelationVo relationVo = new AppCustomerGroupRelationVo();
			relationVo.setType(type);
			if (checkGroupId) {
				if (this._checkGroupExist(groupId)) {
					relationVo.setGroupId(groupId);
				} else {
					_log.error("分组不存在！");
					return null;
				}
			}
			this._setRelationVoFK(relationVo, type, customerId);
			relationVo.setUserId(userId);
			existRelation = this.appCustomerGroupRelationService.selectUserIsInGroup(relationVo);
		}
		return existRelation;
	}

	@Override
	public List<AppCustomer> getCurrentGroupCustomer(AppCustomer appcustomer) throws AppCustomerException {
		try {
			AssertUtil.notNull(appcustomer, "appcustomer为空，无法查询");
			List<AppCustomer> results = new ArrayList<AppCustomer>();
			List<AppCustomer> customerList = this.appCustomerService.findCurrentGroupCustomer(appcustomer);
			for (AppCustomer current : customerList) {
				User userInfo = new User();
				if (current.getUserId() != null) {
					userInfo = this.userService.getById(current.getCustomerId());
					current.setCustomerName(StringUtils.isNotBlank(userInfo.getNickname()) ? userInfo.getNickname() : userInfo.getLoginAccount());// 设置好友昵称
					current.setPicture(StringUtils.isNotBlank(userInfo.getHeadimgurl()) ? userInfo.getHeadimgurl() : StringUtils.EMPTY);// 设置客户头像
					current.setLoginAccount(userInfo.getLoginAccount());
				}
				results.add(current);
			}
			return results;
		} catch (Exception e) {
			_log.error("获取当前分组客户失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public void removeCurrentGroupCustomer(List<Long> deletedCustomerIds) throws AppCustomerException {
		try {
			AssertUtil.notNull(deletedCustomerIds, "deletedCustomerIds为空，无法查询");
			for (Long deletedId : deletedCustomerIds) {
				_log.info("删除分组关系:" + deletedId);
				this.appCustomerGroupRelationService.removeAppGroupRelationShip(deletedId);
			}
		} catch (Exception e) {
			_log.error("移除当前分组客户失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public Long addCurrentGroupCustomer(Long userId, List<Long> originalCustomerIds, Long groupId, List<Long> addCustomerIds, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(groupId, "appCustomer为空，无法添加");
			if (CollectionUtils.isNotEmpty(addCustomerIds)) {
				/** 移除原有数据 */
				for (Long customerId : originalCustomerIds) {
					AppCustomerGroupRelation relation = new AppCustomerGroupRelation(type);
					Long code = this._setRelationFK(relation, type, customerId);
					if (code < 0) {
						return code;
					}
					relation.setGroupId(groupId);
					AppCustomerGroupRelation result = appCustomerGroupRelationService.selectRelationByUserIdAndGrpId(relation);
					if (result != null) {
						_log.info("删除分组关系:" + result.getId());
						this.appCustomerGroupRelationService.removeAppGroupRelationShip(result.getId());
					}
				}
				/** 更新组内客户 **/
				AppGroup group = this.appGroupService.findAppGroupById(groupId);
				for (Long customerId : addCustomerIds) {
					AppCustomerGroupRelation groupRelation = new AppCustomerGroupRelation(type);
					Long code = this._setRelationFK(groupRelation, type, customerId);
					if (code < 0) {
						return code;
					}
					// 关系表 分组外键
					groupRelation.setGroupId(group.getId());
					// 关系表 新分组名
					groupRelation.setGroupName(group.getName());
					// 创建时间
					groupRelation.setCreateTime(new Date());
					groupRelation.setUpdateTime(new Date());
					// 创建关系
					if (!this._checkRelationExist(type, customerId, groupId, userId)) {
						Long id = this.appCustomerGroupRelationService.addAppGroupRelationShip(groupRelation);
						_log.info("成功创建分组关系:" + id);
					}
				}
			} else {
				/** 删除了组内全部客户 **/
				this.appCustomerGroupRelationService.removeAppGroupRelationShipByGroupId(groupId, type);
				_log.info("成功删除分组:" + groupId + "type=" + type);
				return 0L;
			}
			return 2213L;
		} catch (Exception e) {
			_log.error("添加当前分组客户失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public void removeCustomerGroup(List<Long> rollBackCustomerIds, Long groupId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(groupId, "groupId为空，无法查询");
			AssertUtil.notNull(rollBackCustomerIds, "rollBackCustomerIds为空，无法查询");
			// 移除关系表数据
			this.appCustomerGroupRelationService.removeAppGroupRelationShipByGroupId(groupId, type);
			_log.info("删除分组关系:" + groupId);
			// 移除分组表数据
			this.appGroupService.removeAppGroupByGroupId(groupId);
			_log.info("删除分组:" + groupId);
		} catch (Exception e) {
			_log.error("移除客户分组失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public Pagination<MyCustomerVo> findStaffCount(String shopNo, String orderBy, Pagination<MyCustomerVo> pagination)
			throws AppCustomerException {
		try {
			AssertUtil.notNull(shopNo, "shopNo为空，无法查询");
			AssertUtil.notNull(pagination, "pagination为空，无法查询");
			Pagination<MyCustomerVo> myStaffPage = this.appCustomerService.findStaffCount(shopNo, orderBy, pagination);
			return myStaffPage;
		} catch (Exception e) {
			_log.error("查询员工统计信息失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public void updateAppCustomer(AppCustomer appCustomer) throws AppCustomerException {
		try {
			AssertUtil.notNull(appCustomer.getId(), "appCustomerId为空，无法查询");
			this.appCustomerService.editAppCustomer(appCustomer);
		} catch (Exception e) {
			_log.error("更新客户信息失败！", e);
			throw new AppCustomerException(e);
		}
	}

	@Override
	public Long updateAppGroup(AppGroup appGroup) throws AppCustomerException {
		try {
			AssertUtil.notNull(appGroup, "appGroup为空，无法查询");
			AssertUtil.notNull(appGroup.getId(), "appGroupId为空，无法查询");
			AppGroup existGroup = this.appGroupService.findAppGroupById(appGroup.getId());
			if (existGroup != null) {
				AppGroup duplicateName = this.appGroupService.findAppGroupByUserIdAndGroupName(appGroup);
				if (duplicateName == null || duplicateName.getId() == null) {
					this.appGroupService.editAppGroup(appGroup);
					return 0L;
				}else{
					_log.error("分组已存在！");
					return -2211L;
				}
			} else {
				return -1L;
			}
		} catch (Exception e) {
			_log.error("更新分组名失败！", e);
			throw new AppGroupException(e);
		}
	}

	@Override
	public Long updateAppGroupSort(Object[] sortList, Long userId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "appGroup为空，无法操作");
			AssertUtil.notNull(type, "type为空，无法操作");
			if (sortList != null && sortList.length != 0) {
				for (int i = 0; i < sortList.length; i++) {
					// 分组存在性检查,权限检查
					AppGroup existGroup = this.appGroupService.findAppGroupById(Long.parseLong(sortList[i].toString()));
					if (existGroup != null && existGroup.getUserId().equals(userId)) {
						AppGroup appGroup = new AppGroup(type);
						appGroup.setId(Long.parseLong(sortList[i].toString()));
						appGroup.setSort(new Integer(i));
						this.appGroupService.editAppGroup(appGroup);
					}
				}
				return 0L;
			} else {
				return -1L;
			}
		} catch (Exception e) {
			_log.error("更新分组排序权重失败！", e);
			throw new AppGroupException(e);
		}
	}

	@Override
	public Long addAppGroup(AppGroup appGroup) throws AppCustomerException {
		try {
			AssertUtil.notNull(appGroup, "appGroup为空，无法操作");
			AssertUtil.notNull(appGroup.getUserId(), "userId为空，无法操作");
			AssertUtil.notNull(appGroup.getType(), "type为空，无法操作");
			return this.appGroupService.addAppGroup(appGroup);
		} catch (Exception e) {
			_log.error("添加未分组失败！", e);
			throw new AppGroupException(e);
		}
	}

	@Override
	public List<AppGroup> getUngroups(Long userId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法操作");
			AssertUtil.notNull(type, "type为空，无法操作");
			return this.appGroupService.getUngroups(userId, type);
		} catch (Exception e) {
			_log.error("查找未分组失败！", e);
			throw new AppGroupException(e);
		}
	}
	
	@Override
	public void removeUngroups(Long userId) {
		try {
			AssertUtil.notNull(userId, "userId为空，无法操作");
			this.appGroupService.removeUngroups(userId);
		} catch (Exception e) {
			_log.error("删除未分组失败！", e);
			throw new AppGroupException(e);
		}

	}

	@Override
	public List<AppCustomerVo> findUngroupCustomer(Long userId, Integer type) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法操作");
			List<AppCustomerVo> result = new ArrayList<AppCustomerVo>();
			List<AppCustomer> appCustomers = this.appCustomerService.findUngroupCustomer(userId, type);
			if (CollectionUtils.isNotEmpty(appCustomers)) {
				for (AppCustomer current : appCustomers) {
					AppCustomerVo vo = new AppCustomerVo();
					User user = this.userService.getById(current.getCustomerId());
					// AssertUtil.notNull(user, "user为空，无法操作");
					if (user != null) {
						vo.setId(current.getId());// 客户Id
						vo.setCustomerId(current.getCustomerId());// 客户的userId
						vo.setCustomerName(StringUtils.isNotBlank(user.getNickname()) ? user.getNickname() : user.getLoginAccount());// 客户名全部取昵称
						vo.setRemarkName(current.getRemarkName());// 客户备注名
						vo.setHeadImgUrl(user.getHeadimgurl());// 头像
						vo.setLoginAccount(user.getLoginAccount());// 登录号
						result.add(vo);
					} else {
						_log.error("客户对应的用户[" + current.getCustomerId() + "]为空，无法操作");
					}
				}
			} else {
				_log.info("客户列表为空");
			}
			return result;
		} catch (Exception e) {
			_log.error("查找我的客户列表失败！", e);
			throw new AppGroupException(e);
		}
	}

	@Override
	public Pagination<MyCustomerVo> findShopAndSubOrder(Long userId, Long customerId, Pagination<MyCustomerVo> pagination)throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法操作");
			AssertUtil.notNull(customerId, "customerId为空，无法操作");
			AssertUtil.notNull(pagination, "pagination为空，无法操作");
			// 先查总店
			MyCustomerVo shopOrder = this.appCustomerService.findMyShopOrder(userId, customerId);
			// 如果在总店下过单，第一页第一条数据为总店统计
			if (shopOrder != null && StringUtils.isNotBlank(shopOrder.getShopNo())) {
				// 设置店铺名和logo
				Shop shop = this.shopService.getShopByNo(shopOrder.getShopNo());
				shopOrder.setRole(1);// 总店
				shopOrder.setShopName(shop.getName());
				shopOrder.setShopLogoUrl(shop.getShopLogoUrl());
				shopOrder.setTotalPriceStr(CalculateUtils.converFenToYuan(shopOrder.getPayPrice()));
				// 设置条件少查一条
				int pageSize = pagination.getPageSize();
				if (pageSize > 1 && pagination.getStart() == 0) {
					pagination.setPageSize(pageSize - 1);
				}
			} else {
				shopOrder = null;// 避免数组有值
			}
			Pagination<MyCustomerVo> paginationOrder = this.appCustomerService.findMySubbranchOrder(userId, customerId, pagination);
			if (paginationOrder != null) {
				for (MyCustomerVo current : paginationOrder.getDatas()) {
					// 查出分店
					Subbranch subbranch = this.subbranchService.findByPrimarykey(current.getSubbranchId());
					if (subbranch != null) {
						// 查出此分店的总店
						Shop shop = this.shopService.getShopByNo(subbranch.getSupplierShopNo());
						if (shop != null) {
							// 总店-分店名
							current.setShopName(new StringBuilder(shop.getName()).append("-").append(subbranch.getShopName()).toString());
							// 总店Logo
							current.setShopLogoUrl(shop.getShopLogoUrl());
						}
					}
					// 成交总价统计
					current.setTotalPriceStr(CalculateUtils.converFenToYuan(current.getPayPrice()));
					if (current.getStatus().intValue() == 1) {
						current.setRole(2);// 当前分店
					} else if (current.getStatus().intValue() == -1) {
						current.setRole(3);// 历史分店
					}
				}
				if (shopOrder != null) {
					// 总店加入到第一行
					paginationOrder.getDatas().add(0, shopOrder);
				}
			}
			return paginationOrder;
		} catch (Exception e) {
			_log.error("查找我的总店、分店、历史分店订单列表失败！", e);
			throw new AppGroupException(e);
		}
	}

	@Override
	public MyCustomerVo countAllMyShopOrder(Long userId, Long customerId) throws AppCustomerException {
		try {
			AssertUtil.notNull(userId, "userId为空，无法操作");
			AssertUtil.notNull(customerId, "customerId为空，无法操作");
			MyCustomerVo result = new MyCustomerVo();
			Long totalPrice = 0L;
			int tradeNo = 0;
			// 先查总店
			MyCustomerVo shopOrder = this.appCustomerService.findMyShopOrder(userId, customerId);
			if (shopOrder != null) {
				totalPrice = shopOrder.getPayPrice();
				tradeNo = shopOrder.getTradeNo();
			}
			// 再查分店
			MyCustomerVo subOrder = this.appCustomerService.countAllMyShopOrder(userId, customerId);
			if (subOrder != null) {
				totalPrice += subOrder.getPayPrice();
				tradeNo += subOrder.getTradeNo();
			}
			result.setTotalPriceStr(CalculateUtils.converFenToYuan(totalPrice));
			result.setTradeNo(tradeNo);
			return result;
		} catch (Exception e) {
			_log.error("统计客户在我的所有分店、历史分下订单数和总金额失败！", e);
			throw new AppGroupException(e);
		}
	}

}
