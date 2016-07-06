<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${pagination.datas }" var="data">
	<li class="c-list-item <c:if test='${data.timeStatus == 2  }'>c-list-item-sx</c:if>">
		<div class="none item-left"><i data-nameid="${data.id }" class="iconfont-yuanquan iconfont icon-yuanquanweixuan none" data-select-single></i></div>
		<div class="item-right">
			<div class="c-l-header"></div>
			<div class="c-l-content">
				<c:choose>
					<c:when test="${not empty data.shopLogo }">
						<img class="l-c-shopheader" src="${picUrl }${data.shopLogo}">
					</c:when>
					<c:otherwise>
						<img class="l-c-shopheader" src="${resourcePath }img/defaultShopLogo.jpg">
					</c:otherwise>
				</c:choose>
				<div class="l-c-price">
					<span class="price-sign">¥</span>
					<span class="price-num"><fmt:formatNumber value="${data.faceValue/100 }" pattern="#.##"></fmt:formatNumber></span>
				</div>
				<div class="l-c-other">
					<p class="other-shopnname">${data.shopName }</p>
					<p class="other-order">满<fmt:formatNumber value="${data.orderPrice/100 }" pattern="#.##"></fmt:formatNumber>元使用</p>
					<p class="other-line"></p>
					<p class="other-time"><fmt:formatDate value="${data.effectiveTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${data.endTime }" pattern="yyyy.MM.dd"/></p>
				</div>
				<c:if test='${data.timeStatus == 2 }'>
					<div class="icon-yinzhang icon-yishixiao"></div>
				</c:if>
			</div>
		</div>
	</li>
</c:forEach>