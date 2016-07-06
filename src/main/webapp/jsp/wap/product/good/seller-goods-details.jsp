<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<c:set var="good" value="${goodDetailVo.good}"/>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-商品详情</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}goods/css/main.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}js/swiper/css/swiper.min.css">
</head>
<style>
.tab-pane img{width:100%;}
</style>
<body>

<div class="ui-gallery">
 	<div id="swiper-d">
        <ul class="sliderul">
        <c:forEach items="${goodDetailVo.goodImgs}" var="goodImg">
        	<li>
             	<div class="pinch-zoom">
                    <img src="${picUrl}${goodImg.zoomUrl}" data-type="show-disalogsidler" alt="">
                </div>
            </li>
		</c:forEach>
        </ul>
 	</div>
</div>

<div id="box">
	<input type="hidden" name="goodId" id="goodId" value="${good.id}">

		<!-- 轮播图 -->
	<div id="swiper-h">
 		<div class="swiper-wrapper">
			<c:forEach items="${goodDetailVo.goodImgs}" var="goodImg">
				<div class="swiper-slide"><a href="javascript:;"  data-type="show-disalogsidler"><img src="${picUrl}${goodImg.zoomUrl}" data-type="show-disalogsidler" alt=""></a></div>
			</c:forEach>
		</div>
		<c:if test="${fn:length(goodDetailVo.goodImgs) > 1}">
			<div class="swiper-pagination swiper-pagination-h"></div>
		</c:if>
		
 	</div>
	
	<c:if test="${empty memberPrice }">
		<div class="group group-cleartop">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<h3 class="txt-rowspan2"><c:out value="${good.name}" escapeXml="false"/></h3>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left"><b class="clr-danger" id="j-price">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></b></span>
							<c:if test="${not empty good.marketPrice}">
								<span class="right clr-light">市场价 <del>￥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/></del></span>
							</c:if>
						</p>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left">销量：${good.salesVolume}件</span>
							<span class="right">浏览量：${good.browerTime}</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty memberPrice }">
		<div class="group group-cleartop">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<h3 class="txt-rowspan2"><c:out value="${good.name}" escapeXml="false"/></h3>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left clr-light">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></span>
							<c:if test="${not empty good.marketPrice && 0 <good.marketPrice}">
								<span class="right clr-light">市场价 <del>￥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/></del></span>
							</c:if>
						</p>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left">
								<var class="member-price">会员价</var>
								<b class="clr-danger" style="vertical-align:middle;">￥<fmt:formatNumber pattern="0.00" value="${memberPrice/100}"/></b>
							</span>
							<span class="right clr-light"><del></del></span>
						</p>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left">销量：${good.salesVolume}件</span>
							<span class="right">浏览量：${good.browerTime}</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:choose>
		<c:when test="${good.inventory>0}">
			<c:choose>
					<c:when test="${not empty goodDetailVo.salesPropMap }">
						<div class="group group-justify" id="j-details-attrs">
							<div class="group-item">
								<div class="group-rowspan" id="group-rowspan">
									<div class="group-colspan" id="j-details-attrs">
										<span id="j-detail-shopmore">选择 尺寸 颜色分类</span>
									</div>
									<div class="group-colspan">
										<i class="iconfont icon-right"></i>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="group group-justify" id="j-details-attrs">
							<div class="group-item">
								<div class="group-rowspan">
									<div class="group-colspan" >
										<span id="j-detail-shopmore">选择购买数量</span>
									</div>
									<div class="group-colspan">
										<i class="iconfont icon-right"></i>
									</div>
								</div>
							</div>
						</div>
					</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
				<div class="group group-justify">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan">
								<span id="j-detail-shopmore">商品已售罄</span>
							</div>
						</div>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
	<div class="group group-justify">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan txt-rowspan1">
					<a > 
						<i class="iconfont icon-dianpuyulan clr-light"></i><span style="padding-left:10px;">${shop.name }</span>
					</a>
				</div>
				<div class="group-colspan" style="padding:5px 0 5px 5px;text-align:right;">
					<a >
						进入店铺 <i class="iconfont icon-right clr-light"></i> 
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 商品详情  -->
	<div class="group">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<span id="j-detail-shopmore">产品展示</span>
				</div>
			</div>
		</div>
	</div>
	<div class="group group-cleartop">
		<div class="group-rowspan goods-details-info">
			<c:out value="${good.goodContent}" escapeXml="false"/>
		</div>
	</div>
	
	
	<%@ include file="../../common/popularize.jsp"%>
	<div class="totop" onclick="toTop()"><i class="iconfont icon-top2"></i></div>
	
	<div class="goback">
		<a >店铺首页</a>
		<a >个人中心</a>
	</div>
	<%@ include file="../../common/foot.jsp"%>
</div>	
<script type="text/javascript" src="${resourcePath}js/slidefocus/slidefocus-jquery.js"></script>
<script type="text/javascript" src="${resourcePath}js/swiper/js/swiper.min.js"></script>
<script type="text/javascript" src="${resourcePath}js/swiper/js/swiperpinchzoom.js"></script>
<script type="text/javascript">
		//轮播图
	$(function(){
		var boo = ${fn:length(goodDetailVo.goodImgs) > 1};
		var swiperH = new Swiper('#swiper-h', {
			 autoplay:3000,
			 visibilityFullFit : true,
			 loop:boo,
	        pagination: '.swiper-pagination-h'
   		});
		
		$("#swiper-h").find(".swiper-slide").css("height",swiperH.slides.width());
		var $uigallery=$(".ui-gallery");
		var initDSidler=false;
		$("a[data-type='show-disalogsidler']").off().on("click",function(){
			$uigallery.show();
			if (!initDSidler) {
				$('div.pinch-zoom').each(function () {
					
	                new RTP.PinchZoom($(this), {});
	            });

		 		window.mySwipe = new Swipe(document.getElementById('swiper-d'), {
	                speed: 400
	            });
				initDSidler=true;
			}
		});	
		$uigallery.off().on("click",function(){
			$uigallery.hide();

		});
	});
	function toTop(){
		$('body,html').animate({scrollTop:0},500);
		return false;
	};
	//禁止页面滚动
	function banscroll(isBan){
		if (isBan) {
			document.addEventListener('touchmove', preventDefault,function (e) {
			}, false);	
		}else{
			document.removeEventListener('touchmove',preventDefault, function (e) {
			}, false);
		}
	}
	function preventDefault(e) {
	  	 e.preventDefault(); 
	}
	function toIm(){
		var url = window.location.href;
		var href = "${ctx}/wap/${sessionShopNo}/im/login${ext}?goodDetailId=${good.id}";
		window.location.href = href;
	}
	window.onscroll=function(){
		var scrollTop=document.body.scrollTop,
			$totop=$(".totop");
		if (scrollTop>110) {
			$totop.show();
		}
		else{
			$totop.hide();
		}
	}

</script>
</body>
</html>