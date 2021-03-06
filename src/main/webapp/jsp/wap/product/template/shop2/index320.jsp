<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="font-size: 16.875px;">
<head>
<%@include file="../../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top320.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop2/css/main.css">
    <style type="text/css">
        .m-nav-bottom{
            max-width:320px;
        }
        .list-product ul li{margin-right:0;margin-left:0;}
    </style>
</head>

<body navbar="true">
<div class="outbox">
    <div class="wxhead"><p class="wxhead-title">商城</p></div>
    <div class="inbox">
        <div id="box">
	
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<c:forEach items="${carouselDiagramList}" var="data">
				<li>
					<a href="javascript:void(0);">
						<c:choose>
							<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
								<img src="${picUrl }${data.imgPath }" />
							</c:when>
							<c:otherwise>
								<img src="${data.imgPath }"/>
							</c:otherwise>
						</c:choose>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>

	<!-- 个人中心图标 -->
	<div class="personal-icon">
		<a href="#"><img src="${resourcePath}template/page/common/img/personal.png"/></a>
	</div>
	
	<!-- 模块 图片/标题 -->
	<div class="list-rowspan">
		<div class="list-colspan">
			<ul>
				<c:forEach items="${standardModularList}" var="modular">
					<li data-background>
						<a href="#" >
							<span>
							<c:choose>
								<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
									<img src="${picUrl }${modular.imgPath }" />
								</c:when>
								<c:otherwise>
									<img src="${modular.imgPath }"/>
								</c:otherwise>
							</c:choose>
							</span>
							<var data-font>
								<c:choose>
									<c:when test="${fn:length(modular.nameZh) > 4 }">
										${fn:substring(modular.nameZh, 0, 4)}...
									</c:when>
									<c:otherwise>
										${modular.nameZh}
									</c:otherwise>
								</c:choose>
							</var>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<!-- 产品展示  -->
	<div class="list-rowspan">
		<div class="list-product" id="item-list">
			<ul>
				<c:forEach items="${hpGoodList }" var="good">
					<li>
						<a href="#">
							<span class="pic"><img src="${picUrl }${good.path }" alt="${good.name }"></span>
							<span class="info">${good.name }</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<%@include file="../../../common/foot.jsp" %>
		
</div>
    </div>
</div>
<script type="text/javascript">

//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');

new Slider({
	dom: document.getElementById('item-list'),
	width: 106,
	height:123,
	col:3, //每排数量
	maxWidth:320
})

</script>
</html>
