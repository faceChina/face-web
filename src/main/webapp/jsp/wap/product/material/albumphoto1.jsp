<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../../common/base.jsp"%>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${shop.name }-相册专辑</title>
<%@ include file="../../common/top.jsp"%>

<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
<link rel="stylesheet" href="${resourcePath}template/page/photo1/css/main.css">
</head>
<style>
#slider{
	width:100%; height:200px;
	position:relative;
	margin-top:-5px;
	box-shadow: 0px 2px 3px rgba(0,0,0,0.1);
	-webkit-box-shadow: 0px 2px 3px rgba(0,0,0,0.1);
}
#slider img{ max-width:100%; max-height:100%; position:absolute; left:0; right:0; bottom:0; top:0; margin:auto auto;}
</style>
<body>
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="50%">
		<ul>
			<li><a href="javascript:;"><img src="${picUrl }${photoAlbum.picPath}" alt=""></a></li>
		</ul>
	</div>
	
	<!-- 图片 -->
	<div class="pic" id="content">
		<c:forEach items="${pagination.datas }" var="album">
			<div class="pic-item">
				<span class="pic-img">
					<a href="${ctx }/wap/${shopNo}/any/album/photolist${ext}?albumId=${album.id}">
						<c:choose>
							<c:when test="${album.path == '' || album.path == null}">
								<img src="${resourceBasePath}img/base-photo.jpg" alt="">
							</c:when>
							<c:otherwise>
								<img src="${picUrl }${album.path }" alt="">
							</c:otherwise>
						</c:choose>
					</a>
				</span>
				<span class="pic-title">${album.name }</span>
			</div>
		</c:forEach>
	</div>
	
<script type="text/javascript">
$(function(){
    //滚动加载 相关配置*/
	var loadObj = {
    			   elemt : ".pic-item",//循环的第一节
    		       url:"${ctx}/wap/${sessionShopNo}/any/album/photolistappend${ext}",
			       loadType:'Json',//使用Json加载方式
			       idd : "#content",//外层ID
			       totalRow :'${pagination.totalRow}',
			       start:'${pagination.end}',
			       param:{
			    	   albumId:'${albumDto.photoAlbumId}'
			       }
    };
	rollLoad.init(loadObj);//触发滚动事件
});

function getAppendHtml(dataArray){
	var htm_str = "";
	for(x in dataArray){
   		var str = 
   			'<div class="pic-item">'+
				'<span class="pic-img"><a href="${ctx }/wap/{shopNo}/any/album/photolist${ext}?albumId='+dataArray[x].id+'"><img src="'+ROOT_PICURL+dataArray[x].path+'" alt=""></a></span>'+
				'<span class="pic-title">'+dataArray[x].name+'</span>'+
			'</div>';
		htm_str+=str;
	}
	return htm_str;
}

</script>
</body>
</html>