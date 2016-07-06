package com.zjlp.face.web.server.trade.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.cart.dao.CartDao;
import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;
import com.zjlp.face.web.server.trade.cart.service.CartService;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartDao cartDao;
	@Autowired
	private GoodProducer goodProducer;
	@Override
	public void addCart(Cart cart){
		Cart old = cartDao.getCart(cart.getShopNo(),cart.getSubbranchId(), cart.getUserId(), cart.getGoodSkuId());
		if(null == old){
			/** 购物车中不包含此商品 */
			cartDao.addCart(cart);
		}else{
			/** 购物车中包含此商品 */
			GoodSku goodSku=goodProducer.getGoodSkuById(old.getGoodSkuId());
			Cart editCart = new Cart();
			editCart.setId(old.getId());
			Long quantity=cart.getQuantity() + old.getQuantity();
			Long stock=goodSku.getStock();
			editCart.setQuantity(quantity>stock?stock:quantity);
			if(!old.getVersion().equals(cart.getVersion())){
				editCart.setUnitPrice(cart.getUnitPrice());
				editCart.setVersion(cart.getVersion());
				editCart.setShopName(cart.getShopName());
				editCart.setGoodName(cart.getGoodName());
				editCart.setSkuPicturePath(cart.getSkuPicturePath());
			}
			editCart.setShopName(cart.getShopName());
			editCart.setShopNo(cart.getShopNo());
			if (null != cart.getShareId()) {
				editCart.setShareId(cart.getShareId());//推广信息ID
			}
			editCart.setUpdateTime(new Date());
			cartDao.editCart(editCart);
		}
	}

	@Override
	public void editCart(Cart cart){
		cartDao.editCart(cart);
	}

	@Override
	public void deleteCart(Long cartId){
		cartDao.deleteCart(cartId);
	}

	@Override
	public Cart getCartById(Long cartId){
		return cartDao.getCartById(cartId);
	}

	@Override
	public List<CartDto> findCartListByUserId(Long userId){
		return cartDao.findCartListByUserId(userId);
	}
	
	@Override
	public List<CartDto> findCartListByUserIdAndShopNo(CartVo cartVo){
		return cartDao.findCartListByUserIdAndShopNo(cartVo);
	}
	
	@Override
	public Integer getCount(Long userId){
		return cartDao.getCount(userId);
	}
	
	@Override
	public void delNullityByUserId(Long userId){
		cartDao.delNullityByUserId(userId);
	}

	@Override
	public void clearSubbranchInfo(Long subbranchId) {
		Cart cart = new Cart();
		cart.setSubbranchId(subbranchId);
		cart.setUpdateTime(new Date());
		cartDao.clearSubbranchInfo(cart);
	}
}
