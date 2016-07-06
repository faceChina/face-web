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
<link rel="stylesheet" type="text/css" href="${resourcePath }public/css/main.css">
<link rel="stylesheet" type="text/css"  href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath}about/css/invitecode.css?ver=010">
</head>
<body>
<div id="invitecode">
	<c:if test="${not empty user }">
	<div class="ic-box">
		<div class="ic-header">
			<div class="userheader">
				 <a class="uh-a-img"><c:if test="${not empty user.headimgurl}"><img class="" src="${picUrl}${user.headimgurl}"></c:if>
				 <c:if test="${empty user.headimgurl}"><img class="" src="${resourcePath}img/userheader.png"></c:if></a>
			</div>
			<div class="userinfo"><a class="ui-name">${user.nickname }</a><span class="ui-zw">${businessCard.position }</span></div>
			 <div class="userother">
			 	<p>“ 邀请您加入刷脸，在这里可以认识更多的老</p>
			 	<p>板，卖出更多的货，快来加入刷脸吧！”</p>
			</div>
		</div>
		<div class="ic-content">
			<div class="ic-c-title">您的邀请码</div>
			<a class="ic-c-bt-num">${myInvitationCode }</a>
			<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.zjlp.bestface" class="ic-c-bt-download">立即下载</a>
		</div>
	</div>
	</c:if>
	<c:if test="${empty user }">
	<div class="ic-box">
		<div class="ic-header">
			<div class="userheader">
				 <a class="uh-a-img"><c:if test="${not empty user.headimgurl}"><img class="" src="${picUrl}${user.headimgurl}"></c:if>
				 <c:if test="${empty user.headimgurl}"><img class="" src="${resourcePath}about/img/banner.jpg"></c:if></a>
			</div>
			<div class="userinfo"><a class="ui-name">${user.nickname }</a><span class="ui-zw">${businessCard.position }</span></div>
			 <div class="userother">
			 	<p>“ 邀请您加入刷脸，使用邀请码注册,开始一个阶</p>
			 	<p>层的交往和生意，一般人我不告诉TA. ”</p>
			</div>
		</div>
		<div class="ic-content">
			<a href="http://www.o2osl.com/app/download.htm" class="ic-c-bt-download">立即下载</a>
		</div>
	</div>
	</c:if>
	<div class="ic-opacitybg"></div>
</div>
<script type="text/javascript" src="${resourcePath}js/fsize.js"></script>
</body>
</html>