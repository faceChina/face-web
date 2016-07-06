package com.zjlp.face.web.appvo;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateItemVo;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;

public class DeliveryWayVo {

	//快递运费
	private String express;
	
	//门店自取
	private String pickUp;
	
	//店铺配送
	private String shopDelivery;

	public String getExpress() {
		return express;
	}
	
	/**
	 *  初始化DeliveryWayVo对象
	 * @Title:  
	 * @Description:
	 * @param vo
	 * @param distributionList
	 * @param pickUpList
	 */
	public DeliveryWayVo(DeliveryTemplateVo vo, List<ShopDistribution> distributionList, List<PickUpStore> pickUpList) {
		this.setExpress(vo);
		this.setPickUp(pickUpList);
		this.setShopDelivery(distributionList);
	}

	public void setExpress(DeliveryTemplateVo vo) {
		if (null == vo) {
			this.express = "";
		} else {
			List<DeliveryTemplateItemVo> list = vo.getItemVoList();
			if (null == list || list.isEmpty()) {
				this.express = "";
			} else {
				StringBuilder sb = new StringBuilder();
				for (DeliveryTemplateItemVo item : list) {
					sb.append(item.getDestination()).append(item.getStartStandard()).append("件内").append(item.getStartPostageStr()).append("元，每增加").append(item.getAddStandard()).append("件，加")
					.append(item.getAddPostageStr()).append("元；"); 
				}
				sb.deleteCharAt(sb.length()-1);
				this.express = sb.toString();
			}
		}
	}

	public String getPickUp() {
		return pickUp;
	}

	public void setPickUp(List<PickUpStore> pickUpList) {
		if (null == pickUpList || pickUpList.isEmpty() ) {
			this.pickUp = "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (PickUpStore pickUpStore : pickUpList) {
				sb.append(pickUpStore.getName()).append(";");
			}
			sb.deleteCharAt(sb.length()-1);
			this.pickUp = sb.toString(); 
		}
	}

	public String getShopDelivery() {
		return shopDelivery;
	}

	public void setShopDelivery(List<ShopDistribution> distributionList) {
		if (null == distributionList || distributionList.isEmpty()) {
			this.shopDelivery = "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (ShopDistribution shopDistribution : distributionList) {
				sb.append(shopDistribution.getDistributionRange()).append("、");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("配送");
			this.shopDelivery = sb.toString(); 
		}
	}
	
	
	
}
