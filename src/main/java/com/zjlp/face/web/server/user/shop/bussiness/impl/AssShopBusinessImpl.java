package com.zjlp.face.web.server.user.shop.bussiness.impl;

import org.springframework.stereotype.Repository;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.shop.bussiness.AssShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.util.AssConstantsUtil;

@Repository("assShopBusiness")
public class AssShopBusinessImpl implements AssShopBusiness {

	@Override
	public int getShopRole(Shop shop) {
		/**
		 * 1.内部供应商
		 * 2.外部供应商
		 * 3.内部分销商
		 * 4.外部分销商
		 */
		if (Constants.SHOP_GW_TYPE.equals(shop.getType())) {
			/**官网 只有内部供应商 和内部分销商**/
			if (null ==  shop.getIsFree() ||1 == shop.getIsFree().intValue()) {
				//说明内部供货商
				return  AssConstantsUtil.ROLE_SUPPLIER_INTERNAL;
			} else {
				//说明内部分销商
				return AssConstantsUtil.ROLE_DISTRIBUTOR_INTERNAL;
			}
		}else if (Constants.SHOP_SC_TYPE.equals(shop.getType())) {
			if (null ==  shop.getIsFree() || 1 == shop.getIsFree().intValue()) {
				//说明是供货商,分为内部和外部
				if (null != shop.getProxyType()) {
					if (2== shop.getProxyType().intValue()) {
						//说明是外部
						return AssConstantsUtil.ROLE_SUPPLIER_EXTERIOR;
					}else{
						//内部供货商
						return AssConstantsUtil.ROLE_SUPPLIER_INTERNAL;
					} 
				}
			} else {
				//说明是分销商,分为内部和外部
				if (null != shop.getProxyType()) {
					if (null != shop.getProxyType() && 2== shop.getProxyType().intValue()) {
						//说明是外部分销商
						return AssConstantsUtil.ROLE_DISTRIBUTOR_EXTERIOR;
					}else{
						//内部分销商
						return AssConstantsUtil.ROLE_DISTRIBUTOR_INTERNAL;
					} 
				}else{
					return AssConstantsUtil.ROLE_FREE_SHOP;
				}
			}
		}
		return -1;
	}

}
