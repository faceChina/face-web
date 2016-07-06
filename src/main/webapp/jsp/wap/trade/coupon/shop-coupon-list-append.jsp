<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${pagination.datas }" var="data">
		<li class="sp-list-item">
			<div class="l-i-left">
				<i class="iconfont icon-youhuiquan"></i>
			</div>
			<div class="l-i-center">
				<p class="center-shop">店铺优惠券全店使用</p>
				<p class="center-order">订单满<fmt:formatNumber value="${data.orderPrice/100 }" pattern="#.##"/>元使用（不含邮费）</p>
				<p class="center-time">使用期限<fmt:formatDate value="${data.effectiveTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${data.endTime }" pattern="yyyy.MM.dd"/></p>
				<c:if test="${data.isAllReceive }">
					<div class="geted-coupon"></div>
				</c:if>
			</div>
			<div class="l-i-right" align="center">
				<div class="price">
					<span class="price-sign">¥</span>
					<span class="price-num"><fmt:formatNumber value="${data.faceValue/100 }" pattern="#.##"/></span>
				</div>
				<div class="getcoupon">
					<c:choose>
						<c:when test="${data.hasReceive }">
							<a class="a-getcoupon" >已领取</a>
						</c:when>
						<c:when test="${data.isAllReceive }">
						</c:when>
						<c:otherwise>
							<button class="a-getcoupon" data-id="a-getcoupon" data-nameid="${data.id }">领取</button>
						</c:otherwise>
					</c:choose>				
				</div>
			</div>
		</li>
	</c:forEach>