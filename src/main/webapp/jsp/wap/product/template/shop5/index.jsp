<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>${titleShopName}</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop5/css/main.css">
</head>
<body navbar="true">

<div id="box">
	
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<c:forEach items="${carouselList}" var="carousel">
				<li><a href="${carousel.resourcePath }">
						<c:choose>
							<c:when test="${null != carousel.imgPath && carousel.imgPath != '' && !fn:contains(carousel.imgPath, '/base/img')}">
								<img src="${picUrl }${carousel.imgPath }" />
							</c:when>
							<c:otherwise>
								<img src="${carousel.imgPath }"/>
							</c:otherwise>
						</c:choose>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>

	<!-- 个人中心图标 -->
<!-- 	<div class="personal-icon"> -->
<%-- 		<a href="${ctx }/wap/${shop.no}/buy/personal/index${ext}" class="navbar-personal"><img src="${resourcePath}template/page/common/img/personal.png"/></a> --%>
<!-- 	</div> -->
	
	<!-- 模块图标 -->
	
	<div class="list-rowspan">
		<div class="list-colspan">
			<ul>
				<li><a href="${ctx }/wap/${shop.no}/any/list${ext}"><i class="clr-danger">${goodNumVo.totalGoodNum }</i><var>全部商品</var></a></li>
				<li><a href="${ctx }/wap/${shop.no}/any/list${ext}?newGoodMark=1">
					<i class="clr-danger">
					<c:choose>
						<c:when test="${goodNumVo.totalGoodNum > 999 }">
							999
						</c:when>
						<c:otherwise>
							${goodNumVo.totalGoodNum }
						</c:otherwise>
					</c:choose>
					</i>
				<var>最新商品</var></a></li>
				<li><a href="${ctx }/wap/${shop.no}/any/shopType/list${ext}"><i class="clr-danger iconfont icon-sort"></i><var>淘精品</var></a></li>
			</ul>
		</div>
	</div>
	
	<!-- 产品展示  -->
	
	<div class="list-rowspan">
		<c:forEach items="${shopTypeAndGoodList }" var="shopTypeDto">
			<div class="list-product">
				<div class="list-product-tit">
					<a href="${ctx }/wap/${shop.no}/any/list${ext}?shopTypeId=${shopTypeDto.id}&shopTypeName=${shopTypeDto.name }">
						<span>${shopTypeDto.name }</span>
						<i class="iconfont icon-right right"></i>
					</a>
				</div>
				<div class="list-product-cont">
					<ul>
						<c:forEach items="${shopTypeDto.goodList }" var="good">
							<li>
								<a href="${ctx }/wap/${shop.no}/any/item/${good.id}${ext}">
									<span class="pic"><img src="${picUrl }${good.picUrl }" alt=""></span>
									<span class="info">${good.name } </span>
									<span class="price clr-danger"><c:if test="${good.salesPrice!=0 }">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></c:if></span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:forEach>
		
	</div>
<%@include file="../../../common/popularize.jsp"%>
	<%@include file="../../../common/foot.jsp" %>
	<%@ include file="../../../common/nav.jsp" %>
	
</div>

<script type="text/javascript">
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</body>
</html>

