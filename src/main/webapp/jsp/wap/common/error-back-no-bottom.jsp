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
		<%@ include file="../common/top.jsp"%>
		<link rel="stylesheet" type="text/css" href="${resourcePath}public/css/main.css">
		
		<style>
			html,body{
			background:#fff;
			}
		</style>
	</head>
	<body>
		<div id="box">
			<div class="errors-info">
				<div class="errors-info-tit">
					<p class="clr-danger title">很抱歉…</p>
					<p class="clr-light content"><c:if test="${empty message }">您查看的页面不存在可能被删除或转移</c:if>
					<c:if test="${not empty message }">${message }</c:if></p>
					<a href="javascript:history.go(-1);" class="btn btn-danger">返回</a>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${resourcePath }js/dpreview.js"></script>
	</body>
</html>

