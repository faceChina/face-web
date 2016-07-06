<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website2/css/main.css">
</head>
<body>
<div class="box">
	<div class="signage">
		<c:choose>
			<c:when test="${not empty templateDetail}">
				<c:choose>
					<c:when test="${templateDetail.isBasePic == 0}">
						<img src="${picUrl }${templateDetail.shopStrokesPath }" />
					</c:when>
					<c:otherwise>
						<img src="${templateDetail.shopStrokesPath }"/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<img src="${resourceBasePath }img/signage.jpg" alt="店招">
			</c:otherwise>
		</c:choose>
	</div>
	<div class="content clearfix">
		<div id="slider" data-width="67.96875%" data-height="140.1654%" style="float:left;">
			<ul>
				<c:forEach items="${carouselList}" var="carousel">
					<c:choose>
						<c:when test="${null != carousel.imgPath && carousel.imgPath != '' && !fn:contains(carousel.imgPath, '/base/img')}">
							<li><a href="${carousel.resourcePath }"><img src="${picUrl }${carousel.imgPath }" alt=""></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${carousel.resourcePath }"><img src="${carousel.imgPath }" alt=""></a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="m-touch" id="item-list">
			<div class="shop-item">
				<ul>
					<c:forEach items="${modularList}" var="modular">
						<c:if test="${modular.type == 2}">
							<li>
								<a href="${modular.resourcePath }">
									<span class="picture">
									<c:choose>
										<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
											<img src="${picUrl }${modular.imgPath }" alt="">
										</c:when>
										<c:otherwise>
											<img src="${modular.imgPath }" alt="">
										</c:otherwise>
									</c:choose>
									</span>
									<span class="title txt-rowspan1">${modular.nameZh}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

	<div style="position: relative;">
        <span class="iconfont  icon-back"></span>
		<div class="m-column-line" id="m-menus">
			<ul class="clearfix">
				<c:forEach items="${modularList }" var="modular">
					<c:if test="${modular.type == 1}">
						<li data-background>
							<a href="${modular.resourcePath }" >
								<span class="picture">
								<c:choose>
									<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
										<img src="${picUrl }${modular.imgPath }" alt="">
									</c:when>
									<c:otherwise>
										<img src="${modular.imgPath }" alt="">
									</c:otherwise>
								</c:choose>
								</span>
								<span class="title" data-font>${modular.nameZh }</span>
							</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		 <span class="iconfont  icon-right"></span>
    </div>
	<%@include file="../../../common/popularize.jsp"%>
</div>

<script>

// new Slider({
// 	dom: document.getElementById('item-list'),
// 	width: 200,
// 	height:200,
// 	sliderdir:true,//true 左右滚动 false 上下滚动
// 	row:3,
// 	load: true,
// 	ajaxLoadFun: ajaxLoadFun

// });
// new Slider({
// 	dom: document.getElementById('m-menus'),
// 	width: 160,
// 	height:140,
// 	col:4

// });
$(function(){
	var panel=$('#m-menus');
	var panel_li=panel.find('li');
	var total_width=(panel_li.width()+2)*panel_li.length;
	panel.find('ul').width(total_width);
	
});
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</body>
</html>
