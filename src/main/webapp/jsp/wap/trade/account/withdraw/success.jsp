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
<title>成功页面</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }public/css/main.css">
</head>
<body>
	<div id="box">
		<div class="success" id="success4">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">
				你已成功申请余额转出，<br />已提交银行处理。
			</p>
		</div>
	</div>
	<%@ include file="../../../common/nav.jsp"%>
</body>
</html>