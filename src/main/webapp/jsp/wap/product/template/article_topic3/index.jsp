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
<title>${category.name}</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/article_topic3/css/main.css">
</head>
<body>
<div id="box">
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<li><a href="#"><img src="${picUrl }${category.picPath}" alt=""></a></li>
		</ul>
	</div>
	<div class="article-title"><a href="#">${category.name }</a></div>
	
	<div class="list-rowspan">
		<div class="list-product">
			<ul>
			<c:forEach items="${newsDtoList}" var="data">
				<li>
					<c:choose>
						<c:when test="${data.type == '1'}">
							<a href="${ctx}/wap/${sessionShopNo}/any/article/news${ext}?id=${data.id}" class="list-product-col">
							<span class="pic">
							<c:if test="${empty data.picPath}">
								<img src="${resourceBasePath}img/base-photo.jpg" alt="">
							</c:if>
							<c:if test="${not empty data.picPath}">
								<img src="${picUrl}${data.picPath}" alt="">
							</c:if>
							</span>
							<span class="info">${data.title}</span>
							</a>
						</c:when>
						<c:when test="${data.type == '2'}">
							<a href="${data.hyperlink}" class="list-product-col">
							<span class="pic">
							<c:if test="${empty data.picPath}">
								<img src="${resourceBasePath}img/base-photo.jpg" alt="">
							</c:if>
							<c:if test="${not empty data.picPath}">
								<img src="${picUrl}${data.picPath}" alt="">
							</c:if>
							</span>
							<span class="info">${data.title}</span>
							</a>
						</c:when>
					</c:choose>
				</li>
			</c:forEach>	
			</ul>
		</div>
	</div>
	
</div>
</body>
</html>