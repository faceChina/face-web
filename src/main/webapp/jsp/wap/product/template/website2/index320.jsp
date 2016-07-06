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
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/website2/css/main.css">
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
        <div class="box">
	<div class="signage">
		<c:choose>
			<c:when test="${not empty templateDetail}">
				<c:choose>
					<c:when test="${templateDetail.isBasePic == 0}">
						<img src="${picUrl }${templateDetail.shopStrokesPath }" />
					</c:when>
					<c:otherwise>
						<img src="${templateDetail.shopStrokesPath }"/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<img src="${resourceBasePath }img/signage.jpg" alt="店招">
			</c:otherwise>
		</c:choose>
	</div>
	<div class="content clearfix">
		<div id="slider" data-width="67.96875%" data-height="140.1654%" style="float:left;">
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
		<div class="m-touch">
			<div class="shop-item">
				<ul>
					<c:forEach items="${customModularList}" var="modular">
						<c:if test="${modular.type == 2}">
							<li>
								<a href="javascript:void(0);">
									<span class="picture">
									<c:choose>
										<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
											<img src="${picUrl }${modular.imgPath }" />
										</c:when>
										<c:otherwise>
											<img src="${modular.imgPath }"/>
										</c:otherwise>
									</c:choose>
									</span>
									<span class="title txt-rowspan1">${modular.nameZh}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

	<div class="m-column-line">
		<ul class="clearfix">
			<c:forEach items="${standardModularList}" var="modular">
				<c:if test="${modular.type == 1}">
					<li data-background>
						<a href="javascript:void(0);" >
							<span class="picture">
								<c:choose>
									<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
										<img src="${picUrl }${modular.imgPath }" />
									</c:when>
									<c:otherwise>
										<img src="${modular.imgPath }"/>
									</c:otherwise>
								</c:choose>
							</span>
							<span class="title" data-font>${modular.nameZh }</span>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	
</div>
    </div>
</div>


<script>
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');
</script>
</body>
</html>
