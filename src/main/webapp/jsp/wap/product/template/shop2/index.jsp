<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop2/css/main.css">
</head>
<style>
.list-product ul li{margin-right:0;margin-left:0;}
</style>
<body navbar="true">

<div id="box">
	
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<c:forEach items="${carouselList}" var="carousel">
				<li>
					<a href="${carousel.resourcePath }">
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
	
	<!-- 模块 图片/标题 -->
	<div class="list-rowspan">
		<div class="list-colspan">
			<ul>
				<c:forEach items="${modularList}" var="modular">
					<li data-background>
						<a href="${modular.resourcePath }" >
							<span>
							<c:choose>
								<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
									<img src="${picUrl }${modular.imgPath }" alt="">
								</c:when>
								<c:otherwise>
									<img src="${modular.imgPath }" alt="${modular.nameZh}">
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
	<div class="list-rowspan" id="item-list">
		<div class="list-product">
			<ul>
				<c:forEach items="${hpGoodList }" var="good">
					<li>
						<a href="${ctx }/wap/${shop.no}/any/item/${good.id}${ext}">
							<span class="pic"><img src="${picUrl }${good.picUrl }" alt="${good.name }"></span>
							<span class="info">${good.name }</span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<%@include file="../../../common/popularize.jsp"%>
	<%@include file="../../../common/foot.jsp" %>
	<%@ include file="../../../common/nav.jsp" %>
</div>

<script type="text/javascript">

//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data=JSON.parse('${json}');

var curPage=1;

var totalPage=0;
//滑动商品
function ajaxLoadFun(){
	var self = this;
	var ul = this.outer.getElementsByTagName('ul')[0];
	if(totalPage==curPage){
		this.removeLoad();
		return;
	}
	
	$.post("${ctx}/wap/${shop.no}/any/append${ext}",{'isSpreadIndex':1,'shopNo':'${shop.no}','pageSize':10,'curPage':++curPage},function(data){
		var list=JSON.parse(data.info).datas;
		totalPage=JSON.parse(data.info).totalPage;
		for(var i = 0; i<list.length; i++){
			var li = document.createElement('li');
			li.innerHTML = "<a href='${ctx}/wap/${shop.no}/any/item/"+list[i].id+"${ext}'><span class='pic'><img src='"+"${picUrl }"+list[i].path+"' /></span><span class='info'>"+list[i].name+"</span></a>";
			ul.appendChild(li);
		}
		self.setDOM();
		if(totalPage==curPage){
			self.removeLoad();
		}
	},'json');
}
//滑动商品
new Slider({
	dom: document.getElementById('item-list'),
	width: 213,
	height:246,
	col:3, //每排数量
	load: true,
	ajaxLoadFun: ajaxLoadFun

});
// $(function(){
// 	var panel=$('#item-list');
// 	var panel_li=panel.find('li');
	
// 	var i=0,widthArr=[],total_width=0,tempWidth=0;
// 	for(i=0;i<panel_li.length;i++){
// 		(function(i){
// 			tempWidth=(panel_li.eq(i).width()+1)+parseInt(panel_li.eq(i).css('margin-left'))+parseInt(panel_li.eq(i).css('margin-right'));
// 			total_width += tempWidth;
		
// 		})(i)
// 	};

	
// 	panel.find('ul').width(total_width);
// });
</script>
</html>
