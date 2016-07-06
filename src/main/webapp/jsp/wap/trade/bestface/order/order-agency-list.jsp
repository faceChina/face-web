<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>代理订单</title>
<%@include file="../../../operation/bestface/freeshop/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }order/css/main.css">
</head>
<body>
<div id="box">
	<div class="tab">
		<c:if test="${isinner }">
			<div class="list-row">
				<ul class="tab-handle">
					<li <c:if test="${dto.gradeType != 2 }">class="active"</c:if>><a href="${ctx }/free/order/listagency${ext}" data-toggle="tab">代理订单</a></li>
					<li <c:if test="${dto.gradeType == 2 }">class="active"</c:if>><a href="${ctx }/free/order/listagency${ext}?gradeType=2" data-toggle="tab">下级代理订单</a></li>
				</ul>
			</div>
		</c:if>
		<div class="tab-content">
			<div class="tab-pane active" id="tabs1">
				<!-- 搜索  -->
				<div class="search">
					<i class="iconfont icon-search"></i>
					<span><input type="text" value="${dto.orderNo }" id="orderNo" placeholder="请输入您要搜索的订单"></span>
				</div>
				<!-- 订单列表  -->
				<div class="list-tabs" style="margin-top:10px;">
					<ul>
						<li><a href="${ctx }/free/order/listagency${ext}?gradeType=${dto.gradeType}"><span <c:if test="${empty dto.status}">class="active"</c:if>>全部</span></a></li>
						<li><a href="${ctx }/free/order/listagency${ext}?gradeType=${dto.gradeType}&status=1"><span <c:if test="${dto.status == 1}">class="active"</c:if>>待付款</span><sup class="icon-sup">${count.waitCount }</sup></a></li>
						<li><a href="${ctx }/free/order/listagency${ext}?gradeType=${dto.gradeType}&status=2"><span <c:if test="${dto.status == 2}">class="active"</c:if>>待发货</span><sup class="icon-sup">${count.payCount }</sup></a></li>
						<li><a href="${ctx }/free/order/listagency${ext}?gradeType=${dto.gradeType}&status=3"><span <c:if test="${dto.status == 3}">class="active"</c:if>>已发货</span></a></li>
					</ul>
				</div>
				<div class="content" id="content">
				<c:if test="${empty pagination.datas }">
					<div class="no-content" >
						<i class="iconfont icon-gouwuche clr-light"></i>
						<p>您还没有订单哦~</p>
					</div>
				</c:if>
				<c:if test="${not empty pagination.datas }">
					<c:forEach items="${pagination.datas }" var="order">
					     <div class="list-row">
							<div class="list-col">
								<div class="list-inline box-flex">订单号：${order.orderNo }</div>
								<div class="list-inline clr-warning">${order.statusName }</div>
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
										<span class="clr-light fnt-12">X ${order.quantity }</span>
									</div>
								</div>
							</c:forEach>
							<div class="list-col">
								<div class="list-inline box-flex clr-light">
								    <c:choose>
								        <c:when test="${dto.gradeType == 2 }">
								            <ul>
												<li>订单来源：${order.purchaserNick }</li>
												<li>下单时间：<fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd HH:mm" /></li>
											</ul>
								        </c:when>
								        <c:otherwise>
								        	下单时间：<fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd HH:mm" />
								        </c:otherwise>
								    </c:choose>
								</div>
								<div class="list-inline"><span class="clr-light">佣金：</span><span class="clr-danger">￥<fmt:formatNumber value="${order.totalProfitPrice/100}" pattern="0.00" /></span></div>
							</div>
						</div>
					</c:forEach>
				</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../../../common/freeNav.jsp" %>
</body>
<script type="text/javascript">
navbar('order');
$(document).ready(function(){
	//滚动加载 相关配置*/
	var loadObj = {
		elemt : ".list-row",
		url:"${ctx }/free/order/appenagency${ext}",
		totalRow :'${pagination.totalRow}',
		start:'${pagination.end}',
		param:{
	    	status:'${dto.status}', gradeType:'${dto.gradeType}'
	    },
		loadType:'html',
		idd : "#content"
	};
	rollLoad.init(loadObj);//触发滚动事件
});
$('#orderNo').keydown(function(e){
	if(e.keyCode==13){
		searchOrders(); //处理事件
	}
})
function searchOrders(){
	var orderNo = $('#orderNo').val();
    window.location.href="${ctx }/free/order/listagency${ext}?gradeType=${dto.gradeType}&orderNo="+orderNo;
}
</script>
</html>
