<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>刷脸-一个阶层的交往和生意</title>
<%@include file="../common/base.jsp"%>
<link rel="stylesheet" type="text/css"  href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath}about/css/invitecode.css?ver=010">
</head>
<body>
<div id="groupcode">
	<div class="ic-box">
		<div class="ic-header">
			<div class="ic-header-code">
			<img src="${ctx}${imgCpath}" alt="">
			</div>
			<div class="ic-header-font">
			<c:if test="${codeTpye == 0 }">
			 	<p class="big-font">将二维码保存到本地</p>
				<p>用刷脸APP扫一扫，加我刷脸一起玩耍吧！</p>
			</c:if>
			<c:if test="${codeTpye == 1 }">
				<p class="big-font">下载二维码到本地</p>
				<p>用刷脸APP扫一扫</p>
				<p>加入群聊，认识更多小伙伴吧！</p>
			</c:if>
				
			</div>
		</div>
		<div class="ic-content">
			<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.zjlp.bestface" class="ic-c-bt-download">立即下载APP</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="${resourcePath}js/fsize.js"></script>
</body>
</html>