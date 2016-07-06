<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>店铺简介</title>
<link rel="stylesheet" type="text/css"	href="${resourcePath}/public/css/main.css">
<%@ include file="../../common/top.jsp"%>
</head>
<body>
<div id="box">
	<div class="details">
		<div class="details-title">
			<h3>${shop.name}</h3>
		</div>
		<div class="details-center">
		${shop.shopContent}
		</div>
	</div>
</div>
</body>
</html>