<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file="../../common/base.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>${shop.name }-相册</title>
<%@ include file="../../common/top.jsp"%>
<script type="text/javascript" src="${resourceBasePath }js/scale.js"></script>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
<link rel="stylesheet" href="${resourcePath}photos/css/main.css">
<script type="text/javascript" src="${resourcePath }js/swiper/js/swiper.min.js"></script>
<link rel="stylesheet" href="${resourcePath }js/swiper/css/swiper.min.css">
</head>
<body>
	<div class="ui-gallery">
 	<div id="swiper-d">
 		<div class="swiper-wrapper">
 			<c:forEach items="${pagination.datas }" var="photo">
			  <div class="swiper-slide"><a href="javascript:;"><img src="${picUrl }${photo.path}" data-type="show-disalogsidler" alt="商品1"></a></div>
 			</c:forEach>
		</div>
		<div class="swiper-pagination swiper-pagination-d"></div>
 	</div>
 	</div>
	<ul class="photos" id="content">
		<c:forEach items="${pagination.datas }" var="photo">
			<li class="photos-item"><img id="pic1" src="${picUrl }${photo.zoomPath}" alt=""></li>
		</c:forEach>
	</ul>
	
<script type="text/javascript">
// var defaultWidth = $(".photos img:first").width();
// var defaultHeight = $(".photos img:first").height();

$(function(){

    //滚动加载 相关配置*/
	var loadObj = {
    			   elemt : ".photos-item",//循环的第一节
    		       url:"${ctx}/wap/${sessionShopNo}/any/album/photolistappend${ext}",
			       loadType:'Json',//使用Json加载方式
			       idd : "#content",//外层ID
			       totalRow :'${pagination.totalRow}',
			       start:'${pagination.end}',
			       param:{
			    	   albumId:'${photoDto.albumId}'
			       }
    };
	rollLoad.init(loadObj);//触发滚动事件
});

function getAppendHtml(dataArray){
	var htm_str = "";
	for(x in dataArray){
   		var str = '<li class="photos-item" onclick="toZoom(this)"><img id="pic1" src="'+ROOT_PICURL+dataArray[x].path+'" alt="" ></li>'
		htm_str+=str;
	}
	return htm_str;
}


var $uigallery=$(".ui-gallery"),
initDSidler=false;
$(".photos-item").off().on("click",function(){
$uigallery.show();
		var swiperD = new Swiper('#swiper-d', {
			 visibilityFullFit : true,
			 initialSlide:$(this).index(),
			 loop:true
			});
		initDSidler=true;
});
$uigallery.off().on("click",function(){
	$uigallery.hide();
});


// //放大
// function toZoom(thiz){
// 	art.dialog({
// 		lock:true,
// 		title:false,
// 		width:"auto",
// 		opacity:0.5,
// 		content:$(thiz).find("img").clone().get(0),
// 		cancel:true
// 	})
	
// 	//触屏
// 	$(function(){
// 		$(".aui_content").find("img").each(function(index,element){
// 			element.addEventListener("gesturechange",gesturechange);
// 			element.addEventListener("gestureend",gestureend);
// 		});
// 	})
	
// 	var x,y;
// 	var width = $(".aui_content").find("img").width();
// 	var height = $(".aui_content").find("img").height();

// 	function touchStart(e){   
// 	   e.preventDefault();
// 	   x = e.touches[0].pageX;
// 	   y = e.touches[0].pageY;  
// 	}
// 	function touchMove(e){} 
// 	function gesturestart(e){}
// 	function gesturechange(e){
// 		e.preventDefault();
// 	    e.target.style.width = (width * e.scale) + "px";
// 	    e.target.style.height = (height * e.scale) + "px";
// 	}
// 	function gestureend(e){
// 		width *= e.scale;  
// 	    height *= e.scale;  
// 	}
// }

</script>
</body>
</html>
