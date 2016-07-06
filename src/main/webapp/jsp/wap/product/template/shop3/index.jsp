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
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop3/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/page2.js?ver=008"></script>
<script type="text/javascript"> 
    $(document).ready(function(){  
        //滚动加载 相关配置*/
		var loadObj = {
        			   elemt : "#content li",
				       url:"${ctx}/wap/${sessionShopNo}/any/append${ext}",
				       totalRow :'${pagination.totalRow}',
				       start:'${pagination.end}',
				       param:{
				    	   currentTime:'${dto.currentTime}',
					       shopTypeId:'${dto.shopTypeId}', 
				       },
				       idd : "#content"
        };
		rollLoad.init(loadObj);//触发滚动事件
		
    });
    function getAppendHtml(dataArray){
    	var htm_str = "";
    	for(x in dataArray){
    		var str ='<li>'
                +"<a href='${ctx }/wap/${shop.no}/any/item/"+dataArray[x].id+"${ext}'>"
                +'<span class="pic left"><img src="${ctx }'+'${picUrl}'+dataArray[x].picUrl+'" alt="'+dataArray[x].name+'"/></span>'
                +'<span class="info left"><span class="tit">'+dataArray[x].name+'</span>'+(dataArray[x].salesPrice!=0?'<span class="clr-danger">￥'+Number(dataArray[x].salesPrice/100).toFixed(2)+'</span>':'')+'</span>'
                +"</a></li>";
    		htm_str+=str;
    	}
       return htm_str;
    }
    
 
</script> 
</head>
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
	
	<!-- 模块 -->
	<div class="list-rowspan" id="j-menulist">
		<div class="list-colspan"  style="overflow-x:auto;">
			<ul>
				<c:forEach items="${modularList}" var="modular">
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
				<li <c:if test='${empty shopTypeId }'>style="background:#c30e0e"</c:if>><a href="${ctx }/wap/${shop.no }/any/${urlPath}${ext }">所有产品</a></li>
				<c:forEach items="${shopTypeList }" var="shopType">
					<li <c:if test='${shopTypeId==shopType.id }'>style="background:#c30e0e"</c:if>><a  href="${ctx }/wap/${shop.no }/any/${urlPath}${ext }?shopTypeId=${shopType.id }&shopTypeName=${shopTypeDto.name }">${shopType.name }</a></li>
				</c:forEach>
			</ul>
			<ul class="list-product-sortinfo right" id="content">
				<c:forEach items="${pagination.datas }" var="good">
					<li>
						<a href="${ctx }/wap/${shop.no}/any/item/${good.id}${ext}">
							<span class="pic left"><img src="${picUrl }${good.picUrl }" alt=""></span>
							<span class="info left">
								<span class="tit">${good.name }</span>
								<c:if test="${good.salesPrice!=0 }"><span class="clr-danger">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></span></c:if>
							</span>
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
// $(function(){
// 	new Slider({
// 		dom: document.getElementById('j-menulist'),
// 		width: 100,
// 		height:144,
// 		autoplay:true,//自动播放
// 		col:4
// 	});   
// })
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

	
	panel.find('ul').width(total_width+5);
});
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</body>
</html>

