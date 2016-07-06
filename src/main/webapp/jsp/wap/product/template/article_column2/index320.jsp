<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>栏目模板</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/article_column2/css/main.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/common/css/preview.css">

</head>
<body navbar="true">

<div id="box">
	
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<li><a href="#"><img src="${picUrl }${column.picPath}" alt=""></a></li>
		</ul>
	</div>
	
	<!-- 文章列表  -->
	
	<div class="list-rowspan">
		<div class="list-product">
		<c:forEach items="${acdList}" var="data">
			<a href="#" class="list-product-col">
				<span class="info"><span class="tit">${data.name}</span></span>
				<span><i class="iconfont icon-right"></i></span>
			</a>
		</c:forEach>
		</div>
	</div>
</div>
</body>
</html>

