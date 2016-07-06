<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach items="${pagination.datas }" var="order">
     <div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">订单号：${dto.orderNo }</div>
			<div class="list-inline clr-warning">${dto.statusName }</div>
		</div>
		<c:forEach items="${order.purchaseOrderItemList }" var="orderItem">
		    <div class="list-col">
				<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName}</li>
						<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertyName }</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥<fmt:formatNumber value="${orderItem.salesPrice/100 }" pattern="0.00" /></span>
					<span class="clr-light fnt-12">X${order.quantity }</span>
				</div>
			</div>
		</c:forEach>
		<div class="list-col">
			<div class="list-inline box-flex clr-light">下单时间：<fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd HH:mm" /></div>
			<div class="list-inline"><span class="clr-light">佣金：</span><span class="clr-danger">￥<fmt:formatNumber value="${order.totalProfitPrice/100}" pattern="0.00" /></span></div>
		</div>
	</div>
</c:forEach>