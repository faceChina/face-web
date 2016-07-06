<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<%@include file="../../../common/base.jsp"%>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>文章模板</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/article_topic1/css/main.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/common/css/preview.css">
</head>
<body>
<div class="wxhead"><p class="wxhead-title">文章模板</p></div>
<div class="outbox">
<div class="centbox">
	<div class="inbox">
		
		<div id="slider" data-width="320" data-height="59.375%">
			<ul>
				<li><a href="#"><img src="${picUrl }${category.picPath}" alt=""></a></li>
			</ul>
		</div>
		<div class="article-title"><a href="#">${category.name }</a></div>
		<div class="container">
			<c:forEach items="${newsDtoList}" var="data">
				<div class="row">
					<div  class="nav-cont">
						<h2>${data.title}</h2>
						<p class="small">${data.introduction}</p>
					</div>
				</div>
			</c:forEach>	
		</div>
	</div>
	</div>
</div>
</body>
</html>