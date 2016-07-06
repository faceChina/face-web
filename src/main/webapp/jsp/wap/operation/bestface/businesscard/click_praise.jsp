<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="share-card.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>点赞列表</title>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/businesscard.css">
</head>
<body>
<div class="page-padding">
	<div class="m-slogan-praise">
		<span class="slogan-praise-info">被点赞了${count }次 <em class="slogan-praise-gary">继续加油哦~</em></span>
	</div>
	<div class="m-contacts-list">
		<c:forEach items="${list }" var="dto">
		<c:choose>
			<c:when test="${dto.status >= 0}"><a class="m-contacts-item" href="${ctx }/wap/${dto.shopNo}/any/icard/my-card/${dto.icardId}${ext}"></c:when>
			<c:otherwise><a class="m-contacts-item" href="#"></c:otherwise>
		</c:choose>
			
				<span class="m-contacts-time">
					<i class="m-contacts-icon"></i>
					<span class="m-contacts-val"><fmt:formatDate value='${dto.createTime }' type='both' pattern='yy/MM/dd hh:mm a'/></span>
				</span>
				<span class="m-contacts-poto">
				
				<c:choose>
					<c:when test="${dto.status >= 0}"><img src="${picUrl }${dto.headPicture}" alt=""></c:when>
					<c:otherwise><img src="${resourcePath }/img/human-icon.png" alt=""></c:otherwise>
				</c:choose>
				</span>
				<span class="m-contacts-txt">
					<span class="contacts-txt-item">
						<em class="contacts-txt-name">
						<c:choose>
							<c:when test="${dto.status >= 0}">${dto.nickName }</c:when>
							<c:otherwise>游客</c:otherwise>
						</c:choose>
						</em>
					</span>
					<span class="contacts-txt-item">
						<em class="contacts-txt-comp">给您点了赞！</em>
					</span>
				</span>
			</a>
		</c:forEach>
	</div>
	
</div>
</body>
</html>