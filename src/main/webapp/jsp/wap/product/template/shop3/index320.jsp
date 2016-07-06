<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="font-size: 16.875px;">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top320.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop3/css/main.css">
    <style type="text/css">
        .m-nav-bottom{
            max-width:320px;
        }
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
	
	<!-- 模块 -->
	<div class="list-rowspan" id="j-menulist">
		<div class="list-colspan">
			<ul>
				<c:forEach items="${standardModularList}" var="modular">
					<li data-background>
						<a href="${modular.resourcePath }" data-font>
							${modular.nameZh}
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<!-- 产品展示 -->
	<div class="list-rowspan">
		<div class="list-product">
			<ul class="list-product-sort left">
				<li><a href="#">所有产品</a></li>
				<c:forEach items="${shopTypeList }" var="shopType">
					<li><a href="#">${shopType.name }</a></li>
				</c:forEach>
			</ul>
			<ul class="list-product-sortinfo right" id="content">
				<c:forEach items="${pagination.datas }" var="good">
					<li>
						<a href="#">
							<span class="pic left"><img src="${picUrl }${good.path }" alt=""></span>
							<span class="info left">
								<span class="tit">${good.name }</span>
								<span class="clr-danger">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></span>
							</span>
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
$(function(){
	var panel=$('#j-menulist');
	var panel_li=panel.find('li');
	
	var i=0,widthArr=[],total_width=0,tempWidth=0;
	for(i=0;i<panel_li.length;i++){
		(function(i){
			tempWidth=(panel_li.eq(i).width()+1)+parseInt(panel_li.eq(i).css('margin-left'))+parseInt(panel_li.eq(i).css('margin-right'));
			total_width += tempWidth;
		
		})(i)
	};

	
	panel.find('ul').width(total_width);
});
var data = JSON.parse('${json}');
</script>
</body>
</html>

