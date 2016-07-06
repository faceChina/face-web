<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-详情页</title>
<%@ include file="../../common/top.jsp"%>
</head>
<body>
<div class="container">
	<div class="details">
		<c:choose>
			<c:when test="${empty messageContent}">
				该内容已被发布者删除！
			</c:when>
			<c:otherwise>
				<div class="details-title">
					<h3>${messageContent.title }</h3>
					<h4><fmt:formatDate value="${messageContent.createTime }" pattern="yyyy-MM-dd"/></h4>
				</div>
				<div class="details-center">
					<p>${messageContent.description }</p>
					<img src="${picUrl }${messageContent.picPath}" class="img-responsive" alt="Responsive image"/>
					<p>${messageContent.content }</p>
					<c:if test="${messageContent.linkPath !=null && messageContent.linkPath !=''}">
						<a href="${messageContent.linkPath }">阅读原文&gt;&gt;</a>
					</c:if>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<%@ include file="../../common/foot.jsp" %>
</body>
</html>