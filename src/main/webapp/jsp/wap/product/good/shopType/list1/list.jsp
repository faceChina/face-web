<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../common/top.jsp"%>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta charset="UTF-8" />

<!-- 模板1 -->
<title>${shop.name }-商品分类</title>
<link rel="stylesheet" type="text/css" href="${resourcePath}/shopType/css/list1/main.css">
</head>
<body>
<div id="box">
	<!-- 轮播图  -->
	<div id="slider" data-width="100%" data-height="59.375%">
		<ul>
			<c:choose>
			<c:when test="${not empty carouselDiagramDto }">
			<c:forEach items="${carouselDiagramDto }" var="carouselDiagram" varStatus="status">
				<li><a href="${carouselDiagram.resourcePath}"><img src="${carouselDiagram.imgPath}" alt=""></a></li>
			</c:forEach>
			</c:when>
			</c:choose>
		</ul>
	</div>
	
	<div class="list-row">
		<div class="list-col" href="javascript:void(0);">
			<div class="list-inline box-flex fnt-18 clr-light" tel="${cell }" style="padding:10px 0 10px 10px;">服务电话 ${cell }</div>
			<div class="list-inline  list-other"><i class="iconfont icon-unie629 other-main fnt-24" style="color:#669934;"></i></div>
		</div>
	</div>
		
	<div class="list-rowspan">
		<div class="list-product">		
			<c:choose>
				<c:when test="${not empty pagination.datas }">
					<c:forEach items="${pagination.datas }" var="shopType" varStatus="status">
						<a href="${ctx}/wap/${sessionShopNo}/any/shopType/goodlist${ext}?id=${shopType.id}" class="list-product-col">				
							<span class="pic">
								<c:if test="${fn:contains(shopType.imgPath, 'resource')}">
									<img src="${shopType.imgPath}" alt="">
								</c:if>
								<c:if test="${fn:contains(shopType.imgPath, 'resource')==false}">
									<img src="${picUrl }${shopType.imgPath}" alt="">
								</c:if>											
							</span>
							<span class="info" data-font style="color: #${shopType.fontColor};"><span class="tit">${shopType.name}</span></span>
						</a>
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
	</div>	
</div>
	<div style="color:#ccc;text-align:center;padding:20px 0;">下拉加载更多分类... / 没有更多分类啦！</div>
<script type="text/javascript">
$(function(){
	//轮播图
	$.slidefocus({
		dom:"slider",	 //元素id，无“#”字符
		width:"data",		
		height:"data",
		direction:"left",		 //上(up)右(right)下(down)左(left)
		play:true, 				 //自动播放(true/false),和direction、time组合使用
		time:"3000", 			 //时间间隔(单位毫秒)
		page:"data"				//是否显示页码
	});   
});
</script>
</body>
</html>