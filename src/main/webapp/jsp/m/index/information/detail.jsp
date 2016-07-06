<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>${information.seoTitle==null||information.seoTitle=='' ? information.title : information.seoTitle }</title>
		<meta name="Description" content="${information.seoDescription  }" />
		<meta name="keywords" content="${information.seoKeywords  }" />
		<script type="text/javascript" src="${resourceBasePath}js/jquery-1.8.3.min.js"></script>
		<link href="${resourcePath}/css/zjlp_index/common.css" rel="stylesheet" type="text/css" />
		<link href="${resourcePath}/css/zjlp_index/index.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${ctx }/company/img/login/favicon.ico" type="image/x-icon">
	</head>
	<body>
		<div class="layout login">
			<div class="tar main-body">
				<a href="/login.htm" target="_blank">登录</a><span>|</span>
				<a href="/company/join.jsp" target="_blank">注册</a>
			</div>
		</div>
		<div class="layout nav nav-border">
			<div class="mod-nav main-body">
				<div class="logo l">
					<a href="/"><img src="${resourcePath}/img/zjlp_index/logo.png" alt=""></a>
				</div>
				<div class="nav-list">
		            <a href="/">首页</a>
		            <a href="/idea.html">刷脸理念</a>
		            <a href="/product.html">刷脸产品</a>
		            <a href="/case.html">成功案例</a>
		            <a href="/information/index.htm" class="active">资讯中心</a>
		        </div>
		        <div class="phone r">
		        	<b class="phone-icon">400-000-3777</b>
		        </div>
			</div>
		</div>
		
		<div class="news">
			<div class="m-crumbs">
				您当前的位置：<a href="/information/index.htm">资讯中心</a> &gt;<a href="/information/index.htm">正文</a>
			</div>
			<div class="pages">
				<h2 class="title">${information.title }</h2>
				<div class="meta">
					<span><fmt:formatDate value="${information.publishTime }" pattern="yyyy年MM月dd日 HH:mm:ss"/></span>
					<span>作者：${information.announcer }</span>
					<span>来源：${information.source }</span>
				</div>
				<div class="info">
					${information.content }
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="foot">
				<div class="l mt05">
					<img src="${resourcePath}/img/zjlp_index/logo2.png" alt="">
				</div>
				<div class="l about ml15">
					<a href="/about.html">关于我们</a> | <a href="/protocol.html">服务协议</a>
					<p>浙江脸谱科技有限公司 @版权所有 浙ICP备14042086号</p>
					<p>地址：浙江省杭州市市民中心D座11楼</p>
				</div>
				<div class="r complain">
					<img src="${resourcePath}/img/zjlp_index/code1.jpg" class="l">
					<div class="l tel">
						<p>投诉建议</p>
						<h3>400-000-3777</h3>
					</div>
				</div>
			</div>
		</div>
		<script src="${resourcePath}/js/common.js"></script>
		<script type="text/javascript" src="${resourceBasePath}js/bdcnzztj.js"></script>
	</body>
</html>
