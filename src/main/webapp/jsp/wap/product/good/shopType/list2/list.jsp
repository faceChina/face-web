<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../common/top.jsp"%>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta charset="UTF-8" />

<!-- 模板2 -->
<title>${shop.name }-商品分类</title>
<link rel="stylesheet" type="text/css" href="${resourcePath}/shopType/css/list2/main.css">
</head>
<body>
<div id="box">

	<!-- 商品分类   -->	
	<div class="list-rowspan">
		<div class="list-product">
			<c:choose>
				<c:when test="${not empty pagination.datas }">
					<c:forEach items="${pagination.datas }" var="shopType" varStatus="status">
						<a href="${ctx}/wap/${sessionShopNo}/any/shopType/goodlist${ext}?id=${shopType.id}" class="list-product-col">
							<c:if test="${status.index % 2 ==1}">
								<span class="info" data-font style="color: #${shopType.fontColor};"><span class="tit">${shopType.name}</span></span>
							</c:if>
							<span class="pic">
								<c:if test="${fn:contains(shopType.imgPath, 'resource')}">
									<img src="${shopType.imgPath}" alt="">
								</c:if>
								<c:if test="${fn:contains(shopType.imgPath, 'resource')==false}">
									<img src="${picUrl }${shopType.imgPath}" alt="">
								</c:if>	
							</span>
							<c:if test="${status.index % 2 ==0}">
								<span class="info" data-font style="color: #${shopType.fontColor};"><span class="tit">${shopType.name}</span></span>
							</c:if>
						</a>
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>
</div>
</body>
</html>