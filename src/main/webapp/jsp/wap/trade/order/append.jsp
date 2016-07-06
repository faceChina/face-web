<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../common/base.jsp"%>
<c:forEach items="${pagination.datas }" var="order">
		<div class="list-row" data-showhide >
			<div class="list-col">
				<div class="list-inline box-flex">${order.orderNo} ${order.shopName }</div>
				<div class="list-inline clr-warning statusName">
					<c:choose>
						<c:when test="${order.status==1 && (order.payStatus != 2 && order.payStatus != 3) }">
							支付中
						</c:when>
						<c:otherwise>
							${order.statusName }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<c:forEach items="${order.orderItemList }" var="orderItem" varStatus="status">
			<a class="list-col" href="/wap/${sessionShopNo}/buy/order/detail.htm?orderNo=${order.orderNo }" data-id="<c:if test='${status.index>1 }'>${order.orderNo }</c:if>" style="<c:if test='${status.index>1 }'>display:none;</c:if>">
				<div class="list-inline"><img src="${picUrl }${orderItem.skuPicturePath }" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">${orderItem.goodName }</li>
						<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertiesName }</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥<fmt:formatNumber pattern="0.00" value="${orderItem.price/100 }"/></span>
					<span class="clr-light fnt-12">X${orderItem.quantity }</span>
				</div>
			</a>
			</c:forEach>
			<div class="list-col">
				<div class="list-inline shop-info">
					<p>共${order.quantity }件商品</p>
					<c:if test="${order.hasPostFee==1 }"><p><span class="clr-light">运费：</span>￥<fmt:formatNumber pattern="0.00" value="${order.postFee/100 }"/></p></c:if>
					<p><span class="clr-light">实付：</span><span class="clr-danger">￥<span class="totalPrice"><fmt:formatNumber pattern="0.00" value="${order.totalPrice/100 }"/></span></span></p>
				</div>
			</div>
			<c:if test="${order.quantity>2 }">
			<div class="list-col" style="cursor: pointer;">
				<div class="list-center box-flex clr-light" onclick="showhideLayer('${order.orderNo}')">显示更多商品</div>
			</div>
			</c:if>
			<div class="list-col ">
				<div class="list-right">
					<c:choose>
						<c:when test="${order.status==1 && (order.payStatus != 2 && order.payStatus != 3) }">
							<button class="btn btn-default" data-cancel="${order.orderNo }" >取消订单</button>
							<a class="btn btn-danger" href="javascript:;" onclick="orderToPay('${order.orderNo}')">立即支付</a>
						</c:when>
						<c:when test="${order.status==3 }">
							<button class="btn btn-danger" data-take="${order.orderNo }">确认收货</button>
						</c:when>
						<c:when test="${order.status==10 || order.status==30 }">
							<button class="btn btn-default" onclick="deleteOrder(this,'${order.orderNo}')">删除订单</button>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
		</c:forEach>