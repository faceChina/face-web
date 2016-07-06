<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-登录</title>
<%@ include file="base.jsp"%>
</head>
<body>
<div class="container" style="text-align:center;">
	<img src="${resourcePath}img/error.jpg" alt="错误error404" class="img-responsive" style="display:block;margin:3em auto;"/>
	<a href="javascript:history.go(-1);" class="btn btn-default btn-danger" style="padding:0.5em 2em;font-size:1.5em">${message}</a>
</div>
</body>
</html>

