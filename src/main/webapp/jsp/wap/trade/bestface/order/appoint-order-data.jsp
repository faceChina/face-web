<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach items="${pagination.datas }" var="order">
     <div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">预约时间：<fmt:formatDate value="${order.appointmentTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </div>
		</div>
		<c:forEach items="${order.orderItemList }" var="orderItem">
		     <a class="list-col"  href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo }">
				<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName }</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥<fmt:formatNumber value="${orderItem.totalPrice/100 }" pattern="0.00" /></span>
					<span class="clr-light fnt-12">X ${orderItem.quantity }</span>
				</div>
			</a>
		</c:forEach>
		<c:if test="${empty order.orderItemList }">
			<a class="list-col"  href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo }">
				<div class="list-inline"><img src="${picUrl}${order.picturePath}" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">--</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥<fmt:formatNumber value="${order.totalPrice/100 }" pattern="0.00" /></span>
<%-- 					<span class="clr-light fnt-12">X ${order.quantity }</span> --%>
				</div>
			</a>
		</c:if>
		<div class="list-col ">
			<div class="list-inline  box-flex clr-warning">${order.statusName}</div>
			<div class="list-inline">
			    <c:if test="${order.status == 40}">
			    	<button class="btn btn-default" data-refuse="1" onclick="confimOrder(this, '${order.orderNo}')">确认订单</button>
			    	<button class="btn btn-default" data-refuse="1" onclick="refuse(this, '${order.orderNo}')">拒绝订单</button>
			    </c:if>
			</div>
		</div>
	</div>
</c:forEach>