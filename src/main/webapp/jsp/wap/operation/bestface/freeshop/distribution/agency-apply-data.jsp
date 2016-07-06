<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:forEach items="${pagination.datas }" var="shop">
	<div class="list-col">
		<div class="list-inline"><a href="/free/agency/${shop.no }/detail.htm">
			<c:if test="${empty shop.shopLogoUrl }">
				<img src="${resourcePath }img/defaultShopLogo.jpg" alt="" width="70" height="70">
			</c:if>
			<c:if test="${not empty shop.shopLogoUrl }">
				<img src="${picUrl }${shop.shopLogoUrl}" alt="" width="70" height="70">
			</c:if>
		</a>
		</div>
		<div class="list-col box-flex">
			<div class="list-inline box-flex"><a href="/free/agency/${shop.no }/detail.htm">${shop.name }</a></div>
			<div class="list-inline shop-other"><a onclick='validate("${shop.no}")'  class="btn btn-sm btn-default">快速申请</a></div>
		</div>
	</div>
</c:forEach>