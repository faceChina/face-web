<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="../businesscard/share-card.jsp"%>
<%@include file="top.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>公司介绍</title>
</head>
<body>
<div class="container" style="padding-bottom:60px;">
	<div class="banner">
		<c:forEach items="${dto.details }" var="detail">
			<c:if test="${not empty detail.picturePath }">
				<img src="${picUrl }${detail.picturePath }"/>
			</c:if>
			<p style="text-indent:25px;">${detail.content }</p>
		</c:forEach>
	</div>
	<c:if test="${'1'==isMaster}">
	<div class="company-edit" style="">
		<a href="${ctx }/wap/${shopNo}/any/itopic/company/select/${id}${ext }?type=2">编辑</a>
	</div>
	</c:if>
</div>
</body>
</html>