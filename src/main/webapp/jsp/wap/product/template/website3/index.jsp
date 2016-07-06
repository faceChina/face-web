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
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website3/css/main.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }js/swiper/css/swiper.min.css">  
</head>
<body>

	<div id="swiper-h">
 		<div class="swiper-wrapper">
	 		<c:forEach items="${carouselList}" var="data">
				 <div class="swiper-slide">
				 	<a href="${data.resourcePath }" data-type="show-disalogsidler">
				 		<c:choose>
						<c:when test="${null != data.imgPath && data.imgPath != '' && !fn:contains(data.imgPath, '/base/img')}">
							<img src="${picUrl }${data.imgPath }" />
						</c:when>
						<c:otherwise>
							<img src="${data.imgPath }"/>
						</c:otherwise>
					</c:choose>
				 	</a>
				 </div>
			</c:forEach>
		</div>
 	</div>


<div class="box">
	<div class="column-line" id="m-menus">
		<ul>
			<c:forEach items="${modularList}" var="modular">
				<li>
					<a href="${modular.resourcePath }" data-background>
						<var  data-font>${modular.nameZh }</var>
						<var data-font-en class="font">${modular.nameEn }</var>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	
	<c:choose>
		<c:when test="${not empty templateDetail}">
			<div class="column-address" style="background: ${templateDetail.backgroundColor};">
				<a href="tel:${templateDetail.cell }"><h4 style="color:${templateDetail.fontColor};">${templateDetail.cell }</h4></a>
				<p style="color:${templateDetail.fontColor};">${templateDetail.address }</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="column-address" style="background: red;">
				<h4 style="color:#ffffff;">XXX-XXX-XXXX</h4>
				<p style="color:#ffffff;">XX省XX市XX区XXXXXXXX</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript" src="${resourcePath }js/swiper/js/swiper.min.js"></script>
<script type="text/javascript">
	$(function(){
		var swiperH = new Swiper('#swiper-h', {
			 autoplay:3000,
			 visibilityFullFit : true,
			 loop:true,
	        pagination: '.swiper-pagination-h'
   		});
   	 $(".swiper-slide").css("height",window.innerHeight);
	});
</script>
</body>
<script type="text/javascript">

// new Slider({
// 	dom: document.getElementById('m-menus'),
// 	width: 160,
// 	height:140,
// 	col:4
$(function(){
	var panel=$('#m-menus');
	var panel_li=panel.find('li');
	var total_width=(panel_li.width()+1)*panel_li.length;
	panel.find('ul').width(total_width);
});
// });
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</html>