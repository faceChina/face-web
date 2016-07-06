package com.zjlp.face.web.ctl.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.http.filter.PopularizeFilter;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchGoodRelationProducer;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.cart.business.CartBusiness;
import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;
import com.zjlp.face.web.server.trade.cart.domain.vo.CartOrderVo;
import com.zjlp.face.web.server.trade.cart.domain.vo.CartWithCouponVo;
import com.zjlp.face.web.server.trade.cart.domain.vo.SellerVo;
import com.zjlp.face.web.server.trade.coupon.bussiness.CouponBussiness;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.shop.producer.LogisticsProducer;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;

@Controller
@RequestMapping("/wap/{shopNo}/buy")
public class CartWapCtl extends WapCtl {
	
	@Autowired
	private CartBusiness cartBusiness;
	
	@Autowired
	private GoodProducer goodProduer;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	
	@Autowired
	private AddressProducer addressProducer;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private LogisticsProducer logisticsProducer;
	@Autowired
	private MarketingProducer marketingProducer;
	
	@Autowired
	private ClassificationBusiness classificationBusiness;
	@Autowired
	private SubbranchProducer subbranchProducer;
	@Autowired
	private SubbranchGoodRelationProducer subbranchGoodRelationProducer;
	@Autowired
	private CouponBussiness couponBussiness;
	
	/**
	 * 购物车添加商品
	 * @param cart
	 * @return
	 * @date: 2015年3月18日 上午9:57:56
	 * @author: zyl
	 */
	@RequestMapping(value = "/cart/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Cart cart,HttpSession httpSession){
		Shop buyShop = super.getShop();
		cart.setShopNo(buyShop.getNo());//购买店铺号
		cart.setUserId(super.getUserId());
		String subbranchId = SubbranchFilter.getSubbranchId(httpSession);
		if (StringUtils.isNotBlank(subbranchId)) {
			cart.setSubbranchId(Long.valueOf(subbranchId));
		}
		cart.setShareId(PopularizeFilter.getShareId(httpSession, super.getUserId()));
		cartBusiness.addCart(cart);
		Integer count = cartBusiness.getCount(super.getUserId());
		return getReqJson(true, count.toString());
	}
	
	/**
	 * 未登录情况下购物车添加商品添加商品
	 * @param cart
	 * @return
	 * @date: 2015年3月18日 上午9:57:56
	 * @author: dzq
	 */
	@RequestMapping(value = "/cart/addForLogin")
	public String addForLogin(@RequestParam Long gid,@RequestParam Long id,@RequestParam Long quantity,
			HttpServletRequest request){
		Shop buyShop = super.getShop();
		Cart cart = new Cart();
		cart.setShopNo(buyShop.getNo());//购买店铺号
		cart.setShopName(buyShop.getName());//购买店铺名称
		cart.setUserId(super.getUserId());
		cart.setGoodId(gid);
		cart.setGoodSkuId(id);
		cart.setQuantity(quantity);
		String subbranchId  = SubbranchFilter.getSubbranchId(request.getSession());
		if (StringUtils.isNotBlank(subbranchId)) {
			cart.setSubbranchId(Long.valueOf(subbranchId));
		}
		cart.setShareId(PopularizeFilter.getCookiesShareId(request));
		cartBusiness.addCart(cart);
		//普通商品跳转路径
		return super.getRedirectUrl("/wap/"+super.getShopNo()+"/any/item/"+cart.getGoodId());
	}
	
	
	/**
	 * @Description: 删除购物车商品
	 * @param id
	 * @return
	 * @date: 2015年3月18日 上午9:59:11
	 * @author: zyl
	 */
	@RequestMapping(value = "/cart/delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(Long id){
		cartBusiness.deleteCart(id);
		return "success";
	}
	
	/**
	 * @Description: 清除无效商品
	 * @return
	 * @date: 2015年3月26日 下午3:29:53
	 * @author: zyl
	 */
	@RequestMapping(value = "/cart/delNullity",method=RequestMethod.POST)
	@ResponseBody
	public String delNullity(){
		cartBusiness.delNullityByUserId(super.getUserId());
		return "success";
	}
	
	/**
	 * @Description: 查询购物车
	 * @return
	 * @date: 2015年3月18日 上午9:58:40
	 * @author: zyl
	 */
	@RequestMapping(value = "/cart/find")
	public String find(Model model, HttpServletRequest request){
		List<CartDto> list = cartBusiness.findCartListByUserId(super.getUserId());
		List<CartDto> expireList = new ArrayList<CartDto>();
		LinkedHashMap<String, List<CartDto>> map = new LinkedHashMap<String, List<CartDto>>();
		if (null != list && !list.isEmpty()) {
			map.put(super.getShopNo(), new ArrayList<CartDto>());
			for(CartDto dto : list){
				if(null == dto.getStatus() || Constants.VALID.intValue() != dto.getStatus()){
					expireList.add(dto);
				}else{
					String key = dto.getShopNo()+"@"+(dto.getSubbranchId()==null?"0":dto.getSubbranchId());
					List<CartDto> cartList = map.get(key);
					if(null == cartList){
						cartList = new ArrayList<CartDto>();
						map.put(key, cartList);
					}
					cartList.add(dto);
				}
			}
		}
		model.addAttribute("expireList", expireList);
		model.addAttribute("map", map);
		Map<String, CartWithCouponVo> cartWithCouponMap = new LinkedHashMap<String, CartWithCouponVo>();
		for (Entry<String, List<CartDto>> entry : map.entrySet()) {
			CartWithCouponVo vo = new CartWithCouponVo();
			String key = entry.getKey();
			if (key.indexOf("@") == -1) {
				cartWithCouponMap.put(key, vo);
				continue;
			}
			String shopNo = key.split("@")[0];
			List<CouponSetDto> coupon = couponBussiness.findAllCouponSetForBuyer(shopNo, getUserId());
			vo.setCouponSetList(coupon);
			vo.setCardDtoList(entry.getValue());
			cartWithCouponMap.put(key, vo);
		}
		String show = request.getParameter("show");
		model.addAttribute("show",show);
		model.addAttribute("cartWithCouponMap", cartWithCouponMap);
		
		return "/wap/trade/cart/cart";
	}
	
	@RequestMapping(value = "/cart/goodPage",method=RequestMethod.POST)
	public String goodPage(Long id, Model model){
		GoodDetailVo goodDetailVo = goodBusiness.getGoodDetailById(id);
		model.addAttribute("goodDetailVo", goodDetailVo);
		return "/wap/trade/cart/goodPage";
	}
	@RequestMapping(value = "/cart/save",method=RequestMethod.POST)
	@ResponseBody
	public String save(String json){
		List<Cart> list = JsonUtil.toArrayBean(json, Cart.class);
		CartVo cartVo=new CartVo();
		cartVo.setCartList(list);
		cartVo.setUserId(super.getUserId());
		cartBusiness.saveCart(cartVo);
		return "success";
	}
	
	/**
	 * 购物车购买订单组合分单
	 * @Title: _combinationOfOrders 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param 用户选择的购物车ID集合 
	 * @date 2015年5月14日 下午3:23:02  
	 * @author dzq
	 */
	private Map<String, List<CartDto>> _combinationOfOrders(String selectedJson){
		List<String> ids = JsonUtil.toArray(selectedJson);
		Set<Long> set = new HashSet<Long>();
		for(String id : ids){
			set.add(Long.valueOf(id));
		}
		LinkedHashMap<String, List<CartDto>> map = new LinkedHashMap<String, List<CartDto>>();
		//当前店铺置顶
		map.put(super.getShopNo(), new ArrayList<CartDto>());
		//查询购物车列表
		List<CartDto> list = cartBusiness.findCartListByUserId(super.getUserId());
		for(CartDto dto : list){
			if(set.contains(dto.getId()) && Constants.VALID.equals(dto.getStatus())){
				//根据商品所属店铺
				StringBuilder allShopbBuilder = new StringBuilder();
				allShopbBuilder.append(dto.getShopNo())//商品所属店铺
							   .append("@")
							   .append(dto.getSubbranchId()==null?"0":dto.getSubbranchId());//商品购买店铺
				List<CartDto> cartList = map.get(allShopbBuilder.toString());
				if(null == cartList){
					cartList = new ArrayList<CartDto>();
					map.put(allShopbBuilder.toString(), cartList);
				}
				cartList.add(dto);
			}
		}
		if(map.get(super.getShopNo()).size() == 0){
			map.remove(super.getShopNo());
		}
		return map;
	}
	
	@Token(save=true)
	@RequestMapping(value = "/cart/buy")
	public String buy3(String json, Model model){
		//分订单
		Map<String, List<CartDto>> map =this._combinationOfOrders(json);
		Address address = addressProducer.getDefaultAddress(getUserId());
		CartOrderVo cartOrderVo = new CartOrderVo();
		Map<Long,Set<SellerVo>> sellerMap = new LinkedHashMap<Long, Set<SellerVo>>();
		SellerVo sellerVo = null;
		Map<String, Object> deliveryMap = new HashMap<String, Object>();
		model.addAttribute("deliveryMap", deliveryMap);
		Map<String, Long> map2 = new HashMap<String, Long>();
		for (String allShop : map.keySet()) {
			String[] temp = allShop.split("@");
			String shopNo = temp[0];
			String subbranchId = temp[1];
			AssertUtil.hasLength(shopNo, "商品所属店铺号为空，本次购物失败");
			//获取商品所属店铺
			Shop shop = shopProducer.getShopByNo(shopNo);
			AssertUtil.notNull(shop, "商品购买店铺为空[shopNo="+shopNo+"]");
			List<CartDto> cartDtoList = map.get(allShop);
			//物流信息
			ShopVo shopVo = new ShopVo(shop);
			if(!"0".equals(subbranchId)){
				Subbranch subbranch = subbranchProducer.findSubbranchById(Long.valueOf(subbranchId));
				shopVo.setName(subbranch.getShopName());
			}
			shopVo.setBuyShopNo(allShop);
			if(1 == shopVo.getKdType() && null != address){
				deliveryMap.put(shopNo, 1);
				List<Good> goods = new ArrayList<Good>();
				for (CartDto cartDto : cartDtoList) {
					Good good = new Good();
					good.setId(cartDto.getGoodId());
					good.setInventory(cartDto.getQuantity());
					goods.add(good);
				}
				//注：计算快递费应使用商品所属店铺号
				Long postFee = salesOrderBusiness.calculatePostFee(goods, shopNo, address.getId());
				shopVo.setPostFee(postFee);
			}else if(2 == shopVo.getPsType()){
				deliveryMap.put(shopNo, 2);
			}else if(4 == shopVo.getZtType()){
				deliveryMap.put(shopNo, 3);
			}
			//处理会员和活动信息
			sellerVo = cartBusiness.proccessByShop(shopVo,super.getUserId(),cartDtoList);
			//如果出现多个订单属于同一家分店，则不能使用优惠券
			if (map2.containsKey(shopNo)) {
				Long sellerMapKey = map2.get(shopNo);
				Set<SellerVo> sellerVoNew = sellerMap.get(sellerMapKey);
				for (SellerVo sellerVo2 : sellerVoNew) {
					sellerVo2.setMatchCoupons(null);
				}
				sellerVo.setMatchCoupons(null);
			}
			map2.put(shopNo, shop.getUserId());
			//分组
			if (sellerMap.containsKey(shop.getUserId())) {//存在
				sellerMap.get(shop.getUserId()).add(sellerVo);
			}else{
				Set<SellerVo> sellers = new HashSet<SellerVo>();
				sellers.add(sellerVo);
				sellerMap.put(shop.getUserId(), sellers);
			}
		}
		cartOrderVo.setSellerMap(sellerMap);
		cartOrderVo.setAddress(address);
		List<Address> addressList = addressProducer.findAddressByUserId(getUserId());
		cartOrderVo.setAddressList(addressList);
		cartOrderVo.setBuyType(2);
		model.addAttribute("cartOrderVo", cartOrderVo);
		return "/wap/trade/cart/cart-order-new1";
	}
	
	
	@Token(save=true)
	@RequestMapping(value = "/good/buy")
	public String buySingle(@RequestParam Long id,@RequestParam Long quantity,Model model) throws Exception{
		//参数验证
 		AssertUtil.notNull(id, "提交参数错误[id]为空！");
		AssertUtil.notNull(quantity, "提交参数错误[quantity]为空！");
		Long userId =super.getUserId();
		GoodSku goodSku = null;
		goodSku = goodProduer.getGoodSkuById(id);
		if(Constants.VALID.intValue()!=goodSku.getStatus()){
			return getRedirectUrl("/wap/"+getShopNo()+"/any/list");
		}
		Address address = addressProducer.getDefaultAddress(userId);
		//参数初始化
		CartOrderVo cartOrderVo = new CartOrderVo();
		Map<Long,Set<SellerVo>> sellerMap = new LinkedHashMap<Long, Set<SellerVo>>(1);
		Set<SellerVo> sellers = new HashSet<SellerVo>(1);
		SellerVo sellerVo = null;
		List<CartDto> cartDtoList = new LinkedList<CartDto>();
		Map<String, Object> deliveryMap = new HashMap<String, Object>(1);
		model.addAttribute("deliveryMap", deliveryMap);
		//购买商品加入集合
		cartDtoList.add(new CartDto(goodSku,quantity));
		//查询店铺信息
		Shop shop=shopProducer.getShopByNo(getShopNo());
		//店铺物流信息
		ShopVo shopVo = new ShopVo(shop);
		if(1==shopVo.getKdType()){
			if(null==address){
				deliveryMap.put(getShopNo(), 1);
				shopVo.setPostFee(0l);
			}else{
				deliveryMap.put(getShopNo(), 1);
				List<Good> goods = new ArrayList<Good>();
				Good good = new Good();
				good.setId(goodSku.getGoodId());
				good.setInventory(quantity);
				goods.add(good);
				Long postFee = salesOrderBusiness.calculatePostFee(goods, super.getShopNo(), address.getId());
				shopVo.setPostFee(postFee);
			}
		}else if(2==shopVo.getPsType()){
			deliveryMap.put(getShopNo(), 2);
		}else if(4==shopVo.getZtType()){
			deliveryMap.put(getShopNo(), 3);
		}
		
//		//积分抵扣处理(设置了分店单品佣金率的商品不参与积分抵扣)
//		boolean deductFlag = true;
//		SubbranchGoodRelation subbranchGoodRelation = new SubbranchGoodRelation();
//		Long subbranchId = getSubbranchId();
//		if (null != subbranchId) {
//			subbranchGoodRelation.setSubbranchId(subbranchId);
//			subbranchGoodRelation.setGoodId(goodSku.getGoodId());
//			Integer profit = subbranchGoodRelationProducer.getSubbranchGoodRate(subbranchGoodRelation); //获得当前商品分店单品佣金率
//			if (null != profit) {
//				deductFlag = false;
//			}
//		}
//		model.addAttribute("deductFlag", deductFlag);
		
		//处理会员和活动信息
		sellerVo = cartBusiness.proccessByShop(shopVo,super.getUserId(),cartDtoList);
		sellers.add(sellerVo);
		sellerMap.put(shop.getUserId(), sellers);
		cartOrderVo.setSellerMap(sellerMap);
		//地址
		cartOrderVo.setAddress(address);
		//地址列表
		List<Address> addressList = addressProducer.findAddressByUserId(userId);
		cartOrderVo.setAddressList(addressList);
		cartOrderVo.setBuyType(1);
		model.addAttribute("cartOrderVo", cartOrderVo);
		Good g=goodBusiness.getGoodById(goodSku.getGoodId());
		Classification c=classificationBusiness.getClassificationById(g.getClassificationId());
		model.addAttribute("isProtocolGood", 2==c.getCategory());
		return "/wap/trade/cart/cart-order-new1";
	}
	
	/**
	 * @Description: 选择送货方式
	 * @param model
	 * @param shopNo
	 * @param json
	 * @param addressId
	 * @return
	 * @date: 2015年3月31日 下午3:40:57
	 * @author: zyl
	 */
	@RequestMapping(value = "/cart/delivery",method=RequestMethod.POST)
	public String delivery(HttpServletRequest request,Model model,String shopNo,String json,Long addressId,Integer deliveryWay){
		List<Good> goods=JsonUtil.toArrayBean(json, Good.class);
		Long postFee=salesOrderBusiness.calculatePostFee(goods,shopNo,addressId);
		model.addAttribute("postFee", postFee);
		List<PickUpStoreVo> storeList=logisticsProducer.findPickUpStoreVoList(shopNo);
		model.addAttribute("storeList", storeList);
		List<ShopDistribution> shopDistributionList=logisticsProducer.findShopDistributionList(shopNo);
		model.addAttribute("shopDistributionList", shopDistributionList);
		ShopVo shopVo=new ShopVo();
		Shop shop=shopProducer.getShopByNo(shopNo);
		shopVo.setLogisticsTypes(shop.getLogisticsTypes());
		model.addAttribute("shopVo", shopVo);
		model.addAttribute("deliveryWay", deliveryWay);
		model.addAttribute("pickUpId", request.getParameter("pickUpId"));
		return "/wap/trade/cart/delivery";
	}
	@RequestMapping(value = "/cart/getShopPostFee",method=RequestMethod.POST)
	@ResponseBody
	public String getShopPostFee(String shopNo,String json,Long addressId,Integer deliveryWay){
		try {
			List<Good> goods=JsonUtil.toArrayBean(json, Good.class);
			if(deliveryWay != null && 1 == deliveryWay){
				Long postFee = salesOrderBusiness.calculatePostFee(goods, shopNo, addressId);
				return super.getReqJson(true, String.valueOf(postFee));
			}
			return super.getReqJson(true, "0");
		} catch (Exception e) {
			return super.getReqJson(false, "获取邮费失败");
		}

	}
	
}
