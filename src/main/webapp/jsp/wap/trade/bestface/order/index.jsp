<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>卖家版-订单列表</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<%@include file="../../../operation/bestface/freeshop/top.jsp" %>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div id="box">
	<div class="group group-others">
		<div class="group-item">
			<a href="${ctx }/free/order/listordinary${ext}" class="group-rowspan">
				<span class="group-colspan">店铺订单</span>
				<span class="group-colspan clr-light">待发货：<var class="clr-danger">${ordinaryCount }</var></span>
			</a>
		</div>
		<div class="group-item">
			<a href="${ctx }/free/order/listagency${ext}" class="group-rowspan">
				<span class="group-colspan">代理订单</span>
				<span class="group-colspan clr-light">待发货：<var class="clr-danger">${agencyCount }</var></span>
			</a>
		</div>
		<div class="group-item">
			<a href="${ctx }/free/order/appointorders${ext}" class="group-rowspan">
				<span class="group-colspan">预约订单</span>
				<span class="group-colspan clr-light">待处理：<var class="clr-danger">${appointCount }</var></span>
			</a>
		</div>
	</div>
</div>
<%@ include file="../../../common/freeNav.jsp" %>
</body>
<script type="text/javascript">
	navbar('order');
</script>
</html>