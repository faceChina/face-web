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
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website4/css/main.css">
</head>
<body>
<div id="box" style="padding-bottom:3.555555555555556rem;">
	<ul class="column-list" id="item-list">
		<c:forEach items="${modularList}" var="modular">
			<c:if test="${modular.type == 2}">
				<li>
					<a href="${modular.resourcePath }">
						<span class="column-list-pic">
						<c:choose>
							<c:when test="${null != modular.imgPath && modular.imgPath != '' && !fn:contains(modular.imgPath, '/base/img')}">
								<img src="${picUrl }${modular.imgPath }" />
							</c:when>
							<c:otherwise>
								<img src="${modular.imgPath }"/>
							</c:otherwise>
						</c:choose>
						</span>
						<span class="column-list-tit" data-background>
							<span class="column-list-info" >
								<var data-font>${modular.nameZh}</var>
								<var data-font-en class="font">${modular.nameEn}</var>
							</span>
						</span>
					</a>
				</li>
			</c:if>
		</c:forEach>
	</ul>
	
	<div class="column-line" id="m-menus">
		<ul class="clearfix">
			<c:forEach items="${modularList }" var="modular">
				<c:if test="${modular.type == 1}">
					<li data-background>
						<a href="${modular.resourcePath }">
							<span class="column-line-info">
								<var data-font>${modular.nameZh }</var>
								<var data-font-en class="font">${modular.nameEn }</var>
							</span>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<%@include file="../../../common/popularize.jsp"%>
</div>
</body>
<script type="text/javascript">
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
$(function(){
	
	 //改变商品分类文字颜色
    function newUpdateStyle(data,panel) {//data ,type
	console.log(panel)
    if(panel.length==0 ||data == undefined || data.length ==0) return;
    var datafont=panel.find('[data-font]');
    var datafonten=panel.find('[data-font-en]');
    var databackground=panel.find('[data-background]');
	    if(datafont.length){
		    datafont.each(function(index){
		    $(this).css({
		    "color":data[index]["color"]
		    });
		    });
	    }

	    if(datafonten.length){
		    datafonten.each(function(index){
		    $(this).css({
		    "color":data[index]["coloren"]
		    });
		    });
	    }

	    if(databackground.length){
		    databackground.each(function(index){
		    $(this).css({
		    "background":"rgba("+data[index]['red']+","+data[index]['green']+","+data[index]['blue']+","+data[index]['opacity']+")"
		    });
		    });
	    }

    }
	
	
	var dataStandart = JSON.parse('${json}');
	var jsonCustom = JSON.parse('${jsonCustom}');
	var panel=$('#m-menus');
	var panel_li=panel.find('li');
	var total_width=(panel_li.width()+1)*panel_li.length;
	panel.find('ul').width(total_width);
	
   newUpdateStyle(jsonCustom,$('.column-list'));
   newUpdateStyle(dataStandart,$('.column-line'));
   
});
</script>
</html>