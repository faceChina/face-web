<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="font-size: 16.875px;">
<head>
<%@ include file="../../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top320.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website4/css/main.css">
<style type="text/css">
.column-line{
    max-width:320px;
    bottom:130px;
}
</style>
</head>
<body>
<div class="outbox">
    <div class="wxhead"><p class="wxhead-title">商城</p></div>
    <div class="inbox">
<div id="box" style="padding-bottom:3.555555555555556rem;">

	<ul class="column-list" id="item-list">
		<c:forEach items="${customModularList}" var="modular">
			<c:if test="${modular.type == 2}">
				<li>
					<a href="javascript:void(0);">
						<span class="column-list-pic">
							<c:choose>
								<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
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
			<c:forEach items="${standardModularList }" var="modular">
				<c:if test="${modular.type == 1}">
					<li>
						<a href="javascript:void(0);">
							<span class="column-line-info">
								<var>${modular.nameZh }</var>
								<var class="font">${modular.nameEn }</var>
							</span>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	
</div>
        </div>
        </div>
</body>
<script type="text/javascript">

//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</html>