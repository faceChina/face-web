package com.zjlp.face.web.server.trade.cart.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.cart.business.CartBusiness;
import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;
import com.zjlp.face.web.server.trade.cart.domain.vo.SellerVo;
import com.zjlp.face.web.server.trade.cart.service.CartService;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.Coupon;
import com.zjlp.face.web.server.trade.coupon.producer.CouponProcucer;
import com.zjlp.face.web.server.trade.coupon.service.CouponService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.shop.producer.LogisticsProducer;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;

@Component
public class CartBusinessImpl implements CartBusiness {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private GoodProducer goodProduer;
	@Autowired
	private LogisticsProducer logisticsProducer;
	@Autowired
	private AddressProducer addressProducer;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private MarketingProducer marketingProducer;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private CouponProcucer couponProducer;
	
	@Override
	public void addCart(Cart cart){
		GoodSku goodSku = null;
		goodSku = goodProduer.getGoodSkuById(cart.getGoodSkuId());
		AssertUtil.notNull(goodSku, "商品SKU为空");
		Shop shop=shopProducer.getShopByNo(cart.getShopNo());
		AssertUtil.notNull(shop, "店铺不存在");
		cart.setGoodId(goodSku.getGoodId());
		cart.setSkuPicturePath(goodSku.getPicturePath());
		cart.setUnitPrice(goodSku.getSalesPrice());
		cart.setGoodName(goodSku.getName());
		cart.setSkuPropertiesName(goodSku.getSkuPropertiesName());
		cart.setVersion(goodSku.getVersion());
		cart.setShopName(shop.getName());//设置商品所属店铺名称
		if(null!=cart.getSubbranchId()){
			Subbranch subbranch=subbranchProducer.findSubbranchById(cart.getSubbranchId());
			cart.setSubbranchShopName(subbranch.getShopName());
		}
		Date date = new Date();
		cart.setCreateTime(date);
		cart.setUpdateTime(date);
		cartService.addCart(cart);
	}
	
	@Override
	public void deleteCart(Long id){
		cartService.deleteCart(id);
	}
	
	@Override
	public void editCart(Cart cart){
		Date date = new Date();
		cart.setUpdateTime(date);
		cartService.editCart(cart);
	}
	
	@Override
	public List<CartDto> findCartListByUserId(Long userId){
		return cartService.findCartListByUserId(userId);
	}
	
	@Override
	public void saveCart(CartVo cartVo){
		List<Cart> list = cartVo.getCartList();
		if(list.size() > 0){
			cartVo.setShopNo(list.get(0).getShopNo());
		}
		List<CartDto> old = cartService.findCartListByUserIdAndShopNo(cartVo);
		Map<Long, Cart> map = new HashMap<Long, Cart>();
		for(Cart cart : list){
			map.put(cart.getId(), cart);
		}
		for(CartDto dto : old){
			Cart cart = map.get(dto.getId());
			if(null == cart){
//				cartService.deleteCart(dto.getId());
			}else if(dto.getGoodSkuId().longValue() == cart.getGoodSkuId()){
				/** 数量变更 */
				Cart edit = new Cart();
				edit.setId(dto.getId());
				edit.setQuantity(cart.getQuantity());
				this.editCart(edit);
			}else{
				GoodSku goodSku = null;
				/** sku变更 */
				goodSku = goodProduer.getGoodSkuById(cart.getGoodSkuId());
				AssertUtil.notNull(goodSku, "商品SKU为空");
				Good good = goodProduer.getGoodById(goodSku.getGoodId());
				cart.setGoodId(goodSku.getGoodId());
				cart.setGoodName(goodSku.getName());
				cart.setShopNo(good.getShopNo());
				cart.setShopName(good.getShopName());
				cart.setSkuPicturePath(goodSku.getPicturePath());
				cart.setSkuPropertiesName(goodSku.getSkuPropertiesName());
				cart.setUnitPrice(goodSku.getSalesPrice());
				cart.setVersion(goodSku.getVersion());
				this.editCart(cart);
			}
		}
	}
	
	
	@Override
	public Integer getCount(Long userId){
		return cartService.getCount(userId);
	}
	
	@Override
	public void delNullityByUserId(Long userId){
		cartService.delNullityByUserId(userId);
	}


	@Override
	public SellerVo proccessByShop(ShopVo shopVo,Long userId, List<CartDto> cartDtoList) {
		AssertUtil.notNull(shopVo, "参数[shopVo]不能为空");
		AssertUtil.notEmpty(cartDtoList,  "参数[cartDtoList]不能为空");
		//查询店铺信息
		AssertUtil.notNull(shopVo.getUserId(), "参数[shopNo]不能为空");
		//会员价优惠后的总价
		Long sumDiscountPrice = 0L;
		for (CartDto cartDto : cartDtoList) {
			GoodSku goodSku = goodProduer.getGoodSkuById(cartDto.getGoodSkuId());
			//无状态的商品(商品不存在 ，商品状态不正确，商品库存不足)
			if (null == goodSku || !Constants.GOOD_STATUS_DEFAULT.equals(goodSku.getStatus())
					||  0L >= goodSku.getStock().longValue())  {
				continue; 
			}
			if ( 0L >= goodSku.getStock().longValue()) {
				continue;
			}
			//购买数量大于库存,使用最大库存量购买
			if (cartDto.getQuantity().longValue() > goodSku.getStock().longValue()) {
				cartDto.setQuantity(goodSku.getStock());
			}
			cartDto.setStock(goodSku.getStock());
			//(TODO)
			Long discountPrice = goodSku.getSalesPrice();
			//引入会员折扣
			discountPrice = memberProducer.getDiscountPrice(userId,shopVo.getNo(), goodSku.getSalesPrice(), goodSku.getPreferentialPolicy());
			cartDto.setSalesPrice(goodSku.getSalesPrice());//设置原价
			cartDto.setUnitPrice(discountPrice);//设置折扣价格
			sumDiscountPrice += (cartDto.getUnitPrice()*cartDto.getQuantity());
		}
		//获取可以使用的优惠券
		List<Coupon> matchCoupons = couponProducer.findValidCouponsInThisShop(userId, shopVo.getNo());
		SellerVo sellerVo = null;
		sellerVo = marketingProducer.buyActivityInfo(shopVo.getNo(),shopVo.getUserId(), userId);
		sellerVo.setMatchCoupons(matchCoupons);
		AssertUtil.notNull(sellerVo, "查询店铺营销活动信息失败！");
		Map<ShopVo,List<CartDto>> shopMap  = new LinkedHashMap<ShopVo, List<CartDto>>();
		shopMap.put(shopVo, cartDtoList);
		sellerVo.setShopMap(shopMap);
		sellerVo.setSellerId(shopVo.getUserId());
		return sellerVo;
	}
}
