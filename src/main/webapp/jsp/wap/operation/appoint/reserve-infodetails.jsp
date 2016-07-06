<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约订单详情</title>
<%@include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">
</head>
<body>
<div id="box">
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline"><img src="${picUrl }${goodSku.picturePath}" alt="" width="70" height="70"></div>
			<div class="list-top box-flex">
				<ul>
					<li class="txt-rowspan2">${goodSku.name }</li>
					<li class="clr-danger txt-rowspan1 fnt-14" style="padding-top:8px;">价格：￥<fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100 }"/></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="list-row"><div class="list-col">详情描述</div></div>
	<div class="group group-cleartop">
		${good.goodContent }
	</div>
	<div class="group group-cleartop group-justify">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan"></div>
				<div class="group-colspan">
					<a href="javascript:;" onclick="history.go(-1)" class="btn btn-default">返回</a>
				</div>
			</div>
		</div>
	</div>
	<style>
	.goback{
	  	display: -moz-box;
	  	display: -webkit-box;
		max-width:640px;
		min-width:320px;
		width:100%;
		margin:0 auto;
		padding:0.5em;
	}
	.goback a{
		display: block;
	  	-moz-box-flex: 1.0;
	    -webkit-box-flex: 1.0;
	    border-right:1px solid #c4c4c4;
	    color:#c4c4c4;
	    text-align: center;
	}
	.goback a:last-child{
		border-right:none;
	}
	</style>
	<div class="goback">
		<a href="/wap/${shop.no }/any/index.htm">店铺首页</a>
		<a href="/wap/${shop.no }/buy/personal/index.htm">个人中心</a>
	</div>
	<%@include file="../../common/foot.jsp" %>
	<%@include file="../../common/nav.jsp" %>
</div>
</body>
</html>
