<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach items="${pagination.datas }" var="order">
	<div class="list-row" data-pro-id="">
		<div class="list-col">
			<div class="list-inline box-flex">订单号：${order.orderNo }</div>
			<div class="list-inline clr-warning">${order.statusName }</div>
		</div>
		<c:forEach items="${order.orderItemList }" var="orderItem">
			<a class="list-col"
				href="${ctx }/free/order/ordinary/detail${ext}">
				<div class="list-inline">
					<img src="${picUrl}${orderItem.skuPicturePath}" alt=""
						width="70" height="70">
				</div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName }</li>
						<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertiesName }</li>
					</ul>
				</div>
				<div class="list-top shop-other">
					<span>￥<fmt:formatNumber
							value="${orderItem.totalPrice/100 }" pattern="0.00" />
					</span> <span class="clr-light fnt-12">X ${orderItem.quantity }</span>
				</div>
			</a>
		</c:forEach>
		<div class="list-col">
			<div class="list-inline shop-info">
				<p>共${order.quantity }件商品</p>
				<p>
					<span class="clr-light">运费：</span>￥${order.postFee }
				</p>
				<p>
					<span class="clr-light">实付：</span><span class="clr-danger">￥<fmt:formatNumber
							value="${order.payPrice/100 }" pattern="0.00" />
					</span>
				</p>
			</div>
		</div>
		<div class="list-col ">
			<div class="list-right">
				<c:choose>
					<c:when test="${order.status == 10 || order.status == 30 }">
						<button class="btn btn-danger" onclick="toDeleteorder(this, '${order.orderNo}')">删除订单</button>
					</c:when>
					<c:when test="${order.status == 2}">
						<button class="btn btn-danger"
							onclick="openDeliverFram(this, '${order.orderNo}', '${order.deliveryWay }')">发货</button>
					</c:when>
					<c:when test="${order.status == 1}">
						<button class="btn btn-danger" onclick="toCloseorder(this,'${order.orderNo}')">关闭订单</button>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
</c:forEach>