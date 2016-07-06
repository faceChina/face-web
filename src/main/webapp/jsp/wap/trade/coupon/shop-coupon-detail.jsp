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
		<c:when test="${conponStatus == 3 }">
			<div class="detail-coupon detail-coupon-sx" align="center">
		</c:when>
		<c:otherwise>
			<div class="detail-coupon" align="center">
		</c:otherwise>
	</c:choose>
		<div class="counpon-name">${shop.name }</div>
		<div class="counpon-price"><span class="price-sign">¥</span>
			<span class="price-num"><fmt:formatNumber value="${couponSet.faceValue/100 }" pattern="#.##"></fmt:formatNumber></span>
		</div>
		<div class="counpon-other">满<fmt:formatNumber value="${couponSet.orderPrice/100 }" pattern="#.##"></fmt:formatNumber>元可用</div>
		<fmt:formatDate value="${couponSet.effectiveTime }" pattern="yyyy.MM.dd" var="effectiveTime"/>
		<fmt:formatDate value="${couponSet.endTime }" pattern="yyyy.MM.dd" var="endTime"/>
		<div class="counpon-time">使用期限：${effectiveTime }-${endTime }</div>
		<c:choose>
			<c:when test="${conponStatus == 2 }"><div class="icon-yinzhang icon-yilinwang"></div></c:when>
			<c:when test="${conponStatus == 3 }"><div class="icon-yinzhang icon-yishixiao"></div></c:when>
			<c:when test="${conponStatus == 4 }"><div class="icon-yinzhang icon-yilingqu"></div></c:when>
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
		<c:choose>
			<c:when test="${conponStatus == 2 }">对不起，优惠券被领光了，去小店逛逛吧！</c:when>
			<c:when test="${conponStatus == 3 }">对不起，店铺优惠券已失效，去小店逛逛吧！</c:when>
			<c:when test="${conponStatus == 4 }">优惠券已经领取，去小店逛逛吧！</c:when>
			<c:when test="${conponStatus == 5 }">已达到每人限领张数，不能再领取，去小店逛逛吧！</c:when>
		</c:choose>
	</div>
	<div class="detail-footer">
		<c:choose>
			<c:when test="${conponStatus == 1 }">
				<button type="button"  data-id="a-linqu" class="a-lingqu">领取店铺优惠券</button>
			</c:when>
			<c:otherwise>
				<a class="a-lingqu a-lingqu-disabled">领取店铺优惠券</a>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="shareboxshow none">
	<div class="coupon-success">
		<div class="c-s-title">恭喜，优惠券已领取！</div>
		<div class="c-s-shopinfo">
			<c:choose>
				<c:when test="${not empty subbranch }">
					<a href="/wap/${shop.no }/any/gwscIndex.htm?subbranch=${subbranch.id}" class="golookshop">去店铺看看</a>
				</c:when>
				<c:otherwise>
					<a href="/wap/${shop.no }/any/gwscIndex.htm" class="golookshop">去店铺看看</a>
				</c:otherwise>
			</c:choose>
			<a class="callfriend">告诉小伙伴</a>
		</div>
		<p class="usermode">*使用方式：结算时达到条件自动减免相应价格</p>
	</div>
	<div class="share-box-opacity none">
		<div class="box-opacity "></div>
		<div class="share-box">
			<div class="weixin-arrow">
				<a></a>
			</div>
			<p class="share-tile">点击右上角，分享给好友吧！</p>
			<div class="know">
				<a class="a-iknow">我知道啦</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
<script type="text/javascript">

	$(function(){
		var $shareboxopacity=$(".share-box-opacity"),
			$shareboxshow=$(".shareboxshow");
		$("[data-id='a-linqu']").off().on("click",function(){
			var subbranchId = "${subbranch.id}";
			if(${not empty sessionAuthentication}) {
				
				var $obj=$(this);
				$obj.attr("disabled",true);
				$.ajax( {    
				    url:'/wap/${sessionShopNo}/buy/coupon/${couponSet.id}.htm',   
				    data:{   'subbranchId': subbranchId },    
				    type:'post',       
				    dataType:'json', 
				    success:function(data) {    
				        if(data.success){
				            $(".shopcoupon-detail").addClass("none");
	           				$shareboxshow.removeClass("none");
				        }else{    
				            artTip(data.info); 
				        }    
				     },    
				     error : function() {    
				        $obj.text("领取店铺优惠券");
						$obj.attr("disabled",false);
				          artTip("异常！");    
				     }
				});
			} else {
					window.location.href = "/wap/${sessionShopNo}/buy/couponNoLogin/${couponSet.id}.htm?subbranchId="+subbranchId;
			}

		});
		
		$(".a-iknow").off().on("click",function(){
			$shareboxopacity.addClass("none");
		});
		$(".callfriend").off().on("click",function(){
			$shareboxopacity.removeClass("none");
		});
	});
	

</script>
</body>
</html>