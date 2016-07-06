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
<title>店铺优惠券明细</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }coupon/css/shop-conpon-detail.css">
</head>
<body>
<div class="shopcoupon-detail">
	<c:choose>
		<c:when test="${coupon.valid == 3 }">
			<div class="detail-coupon detail-coupon-sx" align="center">
		</c:when>
		<c:otherwise>
			<div class="detail-coupon" align="center">
		</c:otherwise>
	</c:choose>
		<div class="counpon-name">${shop.name }</div>
		<div class="counpon-price"><span class="price-sign">¥</span>
			<span class="price-num"><fmt:formatNumber value="${coupon.faceValue/100 }" pattern="#.##"></fmt:formatNumber></span>
		</div>
		<div class="counpon-other">满<fmt:formatNumber value="${coupon.orderPrice/100 }" pattern="#.##"></fmt:formatNumber>元可用</div>
		<fmt:formatDate value="${coupon.effectiveTime }" pattern="yyyy.MM.dd" var="effectiveTime"/>
		<fmt:formatDate value="${coupon.endTime }" pattern="yyyy.MM.dd" var="endTime"/>
		<div class="counpon-time">使用期限：${effectiveTime }-${endTime }</div>
		<c:choose>
			<c:when test="${coupon.valid == 3 }"><div class="icon-yinzhang icon-yishixiao"></div></c:when>
		</c:choose>
		
	</div>
	<div class="detail-shop">
		<c:choose>
			<c:when test="${not empty subbranch }">
				<a href="/wap/${shop.no }/any/gwscIndex.htm?subbranch=${subbranch.id}">
					<c:choose>
						<c:when test="${not empty shop.shopLogoUrl }">
							<img src="${picUrl }${shop.shopLogoUrl}">
						</c:when>
						<c:otherwise>
							<img src="${resourcePath }img/defaultShopLogo.jpg">
						</c:otherwise>
					</c:choose>
					<span class="shop-name">${shop.name }-${subbranch.name }</span>
					<i class="iconfont icon-right"></i>
				</a>
			</c:when>
			<c:otherwise>
				<a href="/wap/${shop.no }/any/gwscIndex.htm">
					<c:choose>
						<c:when test="${not empty shop.shopLogoUrl }">
							<img src="${picUrl }${shop.shopLogoUrl}">
						</c:when>
						<c:otherwise>
							<img src="${resourcePath }img/defaultShopLogo.jpg">
						</c:otherwise>
					</c:choose>
					<span class="shop-name">${shop.name }</span>
					<i class="iconfont icon-right"></i>
				</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="detail-other">
	</div>
	<div class="detail-footer">
		<c:choose>
			<c:when test="${coupon.valid == 3}">
				<a class="a-lingqu a-lingqu-disabled">使用优惠券</a>
			</c:when>
			<c:otherwise>
				<button type="button" onclick="useCoupon()" data-id="a-linqu" class="a-lingqu">使用优惠券</button>
			</c:otherwise>
		</c:choose>
	</div>
</div>


<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
<script type="text/javascript">
	function useCoupon() {
		if (${not empty subbranch}) {
			window.location.href = "/wap/${shop.no }/any/gwscIndex.htm?subbranch=${subbranch.id}";
		} else {
			window.location.href = "/wap/${shop.no }/any/gwscIndex.htm";
		}
	}
</script>
</body>
</html>