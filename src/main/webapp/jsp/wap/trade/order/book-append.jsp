<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../common/base.jsp"%>
<c:forEach items="${pagination.datas }" var="bookOrder">
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">${bookOrder.shopName}</div>
			<div class="statusName">${bookOrder.statusName}</div>
		</div>
		<div class="list-col">
			<div class="list-inline box-flex clr-light">预约时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bookOrder.appointmentTime}"/></div>
		</div>
		<c:forEach items="${bookOrder.orderItemList}" var="orderItem" begin="0" end="1">
			<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'">
				<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName}</li>
					</ul>
				</div>
				<div class="list-top shop-other">
					<span>￥<fmt:formatNumber value="${orderItem.price/100}" pattern="0.00"/></span>
					<span class="clr-light fnt-12">X${orderItem.quantity}</span>
				</div>
			</a>
		</c:forEach>
		<c:forEach items="${bookOrder.orderItemList}" var="orderItem" begin="2" >
			<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'" data-id="${bookOrder.orderNo}" style="display:none;">
				<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName}</li>
					</ul>
				</div>
				<div class="list-top shop-other">
					<span>￥<fmt:formatNumber value="${orderItem.price/100}" pattern="0.00"/></span>
					<span class="clr-light fnt-12">X${orderItem.quantity}</span>
				</div>
			</a>
		</c:forEach>
		<c:if test="${bookOrder.orderItemSize > 2}" >
			<div class="list-col" style="cursor: pointer;">
				<div class="list-center box-flex clr-light" onclick="showhideLayer('${bookOrder.orderNo}')">显示更多商品</div>
			</div>
		</c:if>
		<div class="list-col ">
			<div class="list-right">
				<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'">查看详情</a>
				<c:if test="${40 == bookOrder.status}">
					<button class="btn btn-default" data-cancel="1" onclick="bookOrderCancel(this,'${bookOrder.orderNo}')">取消订单</button>
				</c:if>
			</div>
		</div>
	</div>
		</c:forEach>