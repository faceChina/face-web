<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-恭喜,支付成功</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}public/css/main.css">
</head>
<body>
<div id="box">
	<div class="success" id="success5">
		<i class="iconfont icon-yuangou clr-danger"></i>
		<p class="success-info">支付已受理</p>
	</div>
	
	<div class="button">
		<a href="/wap/${sessionShopNo}/buy/member/index.htm" type="button" class="btn btn-danger btn-block">返回订单</a>
	</div>
</div>
<%@ include file="../../common/foot.jsp"%>
</body>
</html>