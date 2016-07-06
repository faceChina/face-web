package com.zjlp.face.web.server.trade.cart.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.CartException;
import com.zjlp.face.web.server.trade.cart.producer.CartProducer;
import com.zjlp.face.web.server.trade.cart.service.CartService;

@Component
public class CartProducerImpl implements CartProducer {
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Integer getCount(Long userId){
		return cartService.getCount(userId);
	}

	@Override
	public void clearSubbranchInfo(Long subbranchId) throws CartException {
		try {
			AssertUtil.notNull(subbranchId, CErrMsg.NULL_PARAM.getErrCd(), 
					CErrMsg.NULL_PARAM.getErrorMesage(), "subbranchId");
			cartService.clearSubbranchInfo(subbranchId);
		} catch (Exception e) {
			throw new CartException(e);
		}
	}
}
