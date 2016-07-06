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
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website3/css/main.css">
    <style type="text/css">
        .m-nav-bottom{
            max-width:320px;
        }
    </style>
</head>
<body>
<div class="outbox">
    <div class="wxhead"><p class="wxhead-title">官网</p></div>
    <div class="inbox">
<div id="slider" data-width="100%" data-height="504px">
	<ul>
		<c:forEach items="${carouselDiagramList}" var="data">
			<li><a href="javascript:void(0);">
				<c:choose>
					<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
						<img src="${picUrl }${data.imgPath }" />
					</c:when>
					<c:otherwise>
						<img src="${data.imgPath }"/>
					</c:otherwise>
				</c:choose>
			</a></li>
		</c:forEach>
	</ul>
</div>

<div class="box" style="position: absolute;">
	<div class="column-line">
		<ul>
			<c:forEach items="${standardModularList}" var="modular">
				<li>
					<a href="javascript:void(0);" data-background>
						<var  data-font >${modular.nameZh }</var>
						<var data-font-en class="font" >${modular.nameEn }</var>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<c:choose>
		<c:when test="${not empty templateDetail}">
			<div class="column-address" style="background: ${templateDetail.backgroundColor};">
				<h4 style="color:${templateDetail.fontColor};">${templateDetail.cell }</h4>
				<p style="color:${templateDetail.fontColor};">${templateDetail.address }</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="column-address" style="background: red;">
				<h4 style="color:#ffffff;">XXX-XXX-XXXX</h4>
				<p style="color:#ffffff;">XX省XX市XX区XXXXXXXX</p>
			</div>
		</c:otherwise>
	</c:choose>
	
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