<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../common/base.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website1/css/main.css">
<script type="text/javascript">
// new Slider({
// 	dom: document.getElementById('j-menulist'),
// 	width: 213,
// 	height:246,
// 	col:3
// });  
</script>
</head>
<style>
.m-column-shop{
	width: 100%;
overflow-x: auto;
overflow-y: hidden;
}

</style>
<body>
<div class="box">
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<c:forEach items="${carouselList}" var="data">
				<li>
					<a href="${data.resourcePath }">
						<c:choose>
							<c:when test="${null != data.imgPath && data.imgPath != '' && !fn:contains(data.imgPath, '/base/img')}">
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

	<div class="m-column">
		<ul>
			<c:forEach items="${modularList}" var="modular">
				<c:if test="${modular.type == 1}">
					<li  data-background>
						<a href="${modular.resourcePath }">
							<span class="pic">
							<c:choose>
								<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
									<img src="${picUrl }${modular.imgPath }" alt="">
								</c:when>
								<c:otherwise>
									<img src="${modular.imgPath }" alt="">
								</c:otherwise>
							</c:choose>
							</span>
							<span class="title" data-font>
								<c:choose>
									<c:when test="${fn:length(modular.nameZh) > 4 }">
										${fn:substring(modular.nameZh, 0, 4)}...
									</c:when>
									<c:otherwise>
										${modular.nameZh}
									</c:otherwise>
								</c:choose>
							</span>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

	<div class="m-dbline"></div>
	
	<div class="m-touch" id="j-menulist">
		<div class="m-column m-column-shop" >
			<ul>
				<c:forEach items="${modularList}" var="modular">
					<c:if test="${modular.type == 2}">
						<li>
							<a href="${modular.resourcePath }">
								<span class="pic">
								<c:choose>
									<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
										<img src="${picUrl }${modular.imgPath }" alt="">
									</c:when>
									<c:otherwise>
										<img src="${modular.imgPath }" alt="">
									</c:otherwise>
								</c:choose>
								</span>
								<span class="title txt-rowspan1">
									<c:choose>
										<c:when test="${fn:length(modular.nameZh) > 6 }">
											${fn:substring(modular.nameZh, 0, 6)}...
										</c:when>
										<c:otherwise>
											${modular.nameZh}
										</c:otherwise>
									</c:choose>
								</span>
							</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<%@include file="../../../common/popularize.jsp"%>
</div>


<script>
// $(function(){
// 	var panel=$('#j-menulist');
// 	var panel_li=panel.find('li');
// 	var total_width=(panel_li.width()+1)*panel_li.length;
// 	panel.find('ul').width(total_width);
// });
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
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</body>
</html>
