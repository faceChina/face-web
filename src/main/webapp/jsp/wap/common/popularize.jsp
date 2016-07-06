<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 分享赚佣金 -->
<c:set var="isShopPopularizeOpenSession" value="${sessionScope.shop.isShopPopularizeOpenSession}"/>
<c:set var="isAgencyPopularizeOpenSession" value="${sessionScope.shop.isAgencyPopularizeOpenSession}"/>

<c:if test="${isShopPopularizeOpenSession||isAgencyPopularizeOpenSession}">
	<c:if test="${empty param.shareId && empty cookie['POPULARIZE_SHARE_COOKIE'].value}">
		<div class="join-us"><a href="${ctx}/wap/${sessionShopNo}/any/popularize/share${ext}">点我赚钱</a></div>
	</c:if>
</c:if>