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
<title>订单详情</title>
<%@include file="../../../operation/bestface/freeshop/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<link rel="stylesheet" type="text/css"
	href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css"
	href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css"
	href="${resourcePath }order/css/main.css">
</head>
<body>
	<div id="box">
		<div class="list-address">
			<ul data-address="">
				<li><span class="user">收货人:${order.receiverName }</span> <span class="mobile">${order.receiverPhone }</span>
				</li>
			</ul>
		</div>
		<div class="list-row" data-remark="">
			<div class="list-col">买家备注</div>
			<div class="list-col list-clearpadding">${order.buyerMemo } </div>
		</div>
		<div class="list-row">
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline">
					<i class="iconfont icon-right"></i>
				</div>
			</div>
			<div data-products="">
				<c:forEach items="${order.orderItemList }" var="orderItem">
				    <a class="list-col" href="javascript:;">
						<div class="list-inline">
							<img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70">
						</div>
						<div class="list-top box-flex">
							<ul>
								<li class="txt-rowspan2">${orderItem.goodName }</li>
								<li class="clr-light txt-rowspan1 fot-small">${orderItem.skuPropertiesName }</li>
							</ul>
						</div>
						<div class="list-top shop-other">
							<span>￥<fmt:formatNumber value="${orderItem.totalPrice/100 }" pattern="0.00" /></span> <span class="clr-light fot-small">X${orderItem.quantity }</span>
						</div>
					</a>
				</c:forEach>
			</div>
			<div class="list-col" style="padding: 0;">
				<div class="list-inline box-flex">
					<ul data-figure="">
						<li data-price="">商品总价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${(order.price) / 100}"/></span></li>
						<li data-freight="">运费：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${order.postFee / 100}"/></span></li>
						<li data-points="">积分抵价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${order.integral / 100}"/></span></li>
						<li data-seller="">卖家改价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${order.adjustPrice / 100}"/></span></li>
						<li data-allprice="">实付款（含运费）：<span class="flot-right clr-danger">￥<fmt:formatNumber pattern="0.00" value="${order.totalPrice / 100}"/></span></li>
					</ul>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">
					<ul class="block-dbtext" data-delivery="">
					</ul>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline clr-light">
					<ul data-status="">
						<li>订单编号：${order.orderNo }</li>
						<li>下单时间：<fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </li>
						<li>订单状态：${order.statusName }</li>
					</ul>
				</div>
			</div>
		</div>
		<div data-button="">
			<div class="list-right">
				<c:choose>
					<c:when test="${order.status == 10 || order.status == 30 }">
						<button type="button" class="btn btn-danger" onclick="toDeleteorder(this, '${order.orderNo}')">删除订单</button>
					</c:when>
					<c:when test="${order.status == 2}">
						<button class="btn btn-danger" type="button" onclick="openDeliverFram(this, '${order.orderNo}', '${order.deliveryWay }')">发货</button>
					</c:when>
					<c:when test="${order.status == 1}">
						<button type="button" class="btn btn-danger" onclick="toCloseorder(this,'${order.orderNo}')">关闭订单</button>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
	<!-- 关闭订单 -->
	<div id="j-closeorder" style="display: none;">
		<form id="j-form" action="" method="post">
			<h3>温馨提示</h3>
			<p style="padding: 5px 0;">您确定要关闭订单?</p>
			<div class="list-col">
				<div class="list-inline box-flex">
					<textarea class="form-textarea" cols="50" rows="5"
						name="refuseReason" id="refuseReason" placeholder="请填写关闭原因"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div id="jdispatch" style="display: none;"></div>
	<%@ include file="../../../common/freeNav.jsp" %>
	<script type="text/javascript">
	navbar('order');
	//关闭订单
	function toCloseorder(el, orderNo) {
		art.dialog({
			lock : true,
			width : '100%',
			title : '提示',
			content : document.getElementById("j-closeorder"),
			ok : function() {
				var formValid = $('#j-form').validate({
					rules:{
						refuseReason:{
			     			required:true,maxlength:256
						}
					},
					messages:{
						refuseReason:"请输入拒绝原因"
					}
				});
				if (!formValid.form()) {
					return false;
				}
				$.post("${ctx}/free/order/cancel${ext}", {"orderNo" : orderNo, "refuseReason" : $('#refuseReason').val()},
						function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						window.location.href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo}";
					} else {
						art.dialog.alert(data.info);
					}
				});
			},
			cancel : true
		});
	};
	//删除订单
	function toDeleteorder(el, orderNo) {
		art.dialog({
			lock : true,
			width : '100%',
			title : '提示',
			content : '确定要删除该订单?',
			ok : function() {
				$.post("${ctx}/free/order/del${ext}", {"orderNo" : orderNo}, function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						window.location.href="${ctx }/free/order/listordinary${ext}?status=${dto.status}";
					} else {
						art.dialog.alert(data.info);
					}
				});
			},
			cancel : true
		});
	};
	//发货
	function openDeliverFram(el, orderNo, deliveryWay) {
		$.post("${ctx}/free/order/openfram${ext}", {
			"orderNo" : orderNo, "deliveryWay" : deliveryWay
		}, function(htmlData) {
			$('#jdispatch').html(htmlData);
			openDialog(el);
		});
	}
	function getDeliverUrl() {
		return "${ctx}/free/order/deliver${ext}"
	}
	function afterDeliver(el) {
		window.location.href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo}";
	}
	</script>
</body>
</html>
