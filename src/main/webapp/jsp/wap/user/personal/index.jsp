<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<%@include file="../../common/base.jsp"%>
		<meta charset="UTF-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<title>个人中心</title>
		<%@ include file="../../common/top.jsp"%>
		<link rel="stylesheet" type="text/css" href="${resourcePath }personal/css/main.css">
	</head>
	<body>
		<div id="box">
			<div class="personal-row noborder">
				<div class="personal-center">
					<div class="list-col noborder">
						<a class="list-inline head-pics" href="${ctx }/wap/${sessionShopNo}/buy/personal/edit${ext}">
						    <img 
						        <c:choose>
						        	<c:when test="${not empty user.headimgurl}">src="${picUrl }${user.headimgurl }"</c:when>
						        	<c:otherwise>src="${resourcePath }personal/img/head-pic.jpg"</c:otherwise>
								</c:choose>
								 alt="" height="70" width="70">
						</a>
						<div class="list-inline box-flex noborder">
							<ul class="personal-info">
								<li class="fnt-16"><a href="${ctx }/wap/${sessionShopNo}/buy/personal/edit${ext}">${user.nickname }</a></li>
								<c:if test="${!isWechat}">
								<li class="fnt-14 clr-light"><a href="${ctx }/wap/${sessionShopNo}/buy/personal/edit${ext}">${user.loginAccount}</a></li>
								</c:if>
							</ul>
						</div>
						
						<div class="personal-top-r span-icon">
							<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/index${ext}">
								<i class="iconfont icon-iconfontshezhi fnt-24 personal-set"></i>
							</a>
							<c:if test="${not empty memberCardDto && hasRegistratRule}">
								<button type="button" class="btn personal-qiandao" onclick="toSignin(this,${memberCardDto.id},'integral')">
									<c:choose>
								    	<c:when test="${isRegist }">
											<i class="iconfont icon-emoji fnt-14"></i> 已签到
								    	</c:when>
								    	<c:otherwise>
											<i class="iconfont icon-shenqing fnt-14"></i> 签到 
								    	</c:otherwise>
								    </c:choose> 
								</button>
							</c:if>
						</div>
					</div>
					<div class="j-collect-item j-collect-goodsshop">
						<a href="favorites/1/1.htm">
							<div class="j-collect-item-list goodsshop-goods flot-left">
								<span>${goodCount }</span>
								<p>收藏的商品</p>
							</div>
						</a>
						<a href="favorites/2/1.htm">
							<div class="j-collect-item-list goodsshop-shop flot-right">
								<span>${shopCount }</span>
								<p>收藏的店铺</p>
							</div>
						</a>
					</div>
				</div>
			</div>
			
			<div class="personal-row">
				<a class="list-col" href="${ctx }/wap/${sessionShopNo}/buy/order/list${ext}">
					<div class="list-inline">
						<i class="iconfont icon-dingdan fnt-24" style="color:#eb4f38;"></i>
					</div>
					<div class="list-inline box-flex noborder clr-333">我的订单</div>
					<div class="list-inline noborder">
						<span style="color:#989898">查看全部订单</span>
						<i class="iconfont icon-right"></i>
					</div>
				</a>
				<div class="j-collect-item j-order-status">
					<div class="j-collect-item-list">
						<a href="/wap/${sessionShopNo }/buy/order/list.htm?status=1">
							<span class="span-icon">
								<i class="iconfont icon-daifukuan01 fnt-24"></i>
								<c:if test="${newOrderNumber > 0}">
									<sup class="status-num">${newOrderNumber}</sup>
								</c:if>
							</span>
							<p class="clr-333">待付款</p>
						</a>
					</div>
					<div class="j-collect-item-list">
						<a href="/wap/${sessionShopNo }/buy/order/list.htm?status=2">
							<span class="span-icon">
								<i class="iconfont icon-daifahuo01 fnt-24"></i>
								<c:if test="${payOrderNumber > 0}">
									<sup class="status-num">${payOrderNumber }</sup>
								</c:if>
							</span>
							<p class="clr-333">待发货</p>
						</a>
					</div>
					<div class="j-collect-item-list">
						<a href="/wap/${sessionShopNo }/buy/order/list.htm?status=3">
							<span class="span-icon">
								<i class="iconfont icon-daishoukuan01 fnt-24"></i>
								<c:if test="${deliverOrderNumber > 0}">
									<sup class="status-num">${deliverOrderNumber}</sup>
								</c:if>
							</span>
							<p class="clr-333">待收货</p>
						</a>
					</div>
					<div class="j-collect-item-list">
						<a href="/wap/${sessionShopNo }/buy/order/list.htm?status=5">
							<span class="span-icon">
								<i class="iconfont icon-iconfontdaishoukuan01 fnt-24"></i>
								<!-- <sup class="status-num">8</sup> -->
							</span>
							<p class="clr-333">交易成功</p>
						</a>
					</div>
				</div>
			</div>
			
			<div class="list-row personal-row">
				<a class="list-col" href="${ctx }/wap/${sessionShopNo}/buy/order/bookOrder${ext}">
					<div class="list-inline"><i class="iconfont icon-iconfontdianhua fnt-24" style="color:#47c144;"></i></div>
					<div class="list-inline box-flex clr-333">我的预约</div>
					<div class="list-inline"><i class="iconfont icon-right"></i></div>
				</a>
				<a class="list-col" href="${ctx }/wap/${sessionShopNo}/buy/account/index${ext}">
					<div class="list-inline"><i class="iconfont icon-pay fnt-24" style="color:#ff6600;"></i></div>
					<div class="list-inline box-flex clr-333">我的钱包</div>
					<div class="list-inline"><i class="iconfont icon-right"></i></div>
				</a>
				<a class="list-col" href="${ctx }/wap/${sessionShopNo}/buy/coupon/list${ext}">
					<div class="list-inline">
						<i class="iconfont icon-vipcard fnt-24" style="color:#47c144;"></i>
					</div>
					<div class="list-inline box-flex clr-333">我的卡券</div>
					<div class="list-inline">
<%-- 						<c:if test="${not empty memberCardDto }"> --%>
<!-- 							<span class="member-integral" id="integral"> -->
<!-- 								<i class="iconfont icon-dian"></i> -->
<%-- 								${memberCardDto.availableIntegral }积分 --%>
<!-- 							</span> -->
<%-- 						</c:if> --%>
						<i class="iconfont icon-right"></i>
					</div>
				</a>
				<a class="list-col" href="${ctx }/wap/${sessionShopNo}/buy/address/index${ext}">
					<div class="list-inline list-weight"><i class="iconfont icon-dizhi fnt-24" style="color:#5ca6ff;"></i></div>
					<div class="list-inline box-flex noborder clr-333">收货地址</div>
					<div class="list-inline noborder"><i class="iconfont icon-right"></i></div>
				</a>
			</div>
			
			<!-- app下载下载属性 -->
			<c:if test="${!shopApp }">
				<div class="group group-justify">
					<div class="download-app-box">
						<img class="d-appbox-logo" src="${resourcePath }img/download-app-logo.png">
						<div class="d-appbox-font">
							<h2>刷脸APP</h2>
							<c:choose>
								<c:when test="${!isWechat}">
									<p>订单信息随时查看<p>
								</c:when>
								<c:otherwise>
									<p>开店做生意必备神器<p>
								</c:otherwise>
							</c:choose>
						</div>
						<a class="d-appbox-down" href="/app/download.htm">点击下载</a>
					</div>
				</div>
			</c:if>
		
			<%@ include file="../../common/foot.jsp"%>
			<%@ include file="../../common/nav.jsp" %>
		</div>
		
		<!-- 个人中心安全设置提示 -->
		<c:if test="${!isPayment && !isFirstCookie}">
			<div class="personal-share-arrow">
				<div class="personal-share-set">
					<img src="${resourcePath }personal/img/personal-set.jpg" width="30">
				</div>
			
				<img class="flot-left" src="${resourcePath }personal/img/share_layer.png" width="100%">
				
				<div class="personal-share-prompt">
					<p>为保障账户安全，请点击设置按钮</p>
					<p class="personal-clr-yellow">设置支付密码</p>
					<div class="personal-share-btn">
						<button class="btn fnt-16">我知道啦</button>
					</div>
				</div>
			</div>
		</c:if>
	</body>
	<%@include file="../../common/sign.jsp"%>
	<script type="text/javascript">
		navbar("personal");
		
		/* 安全设置提示层隐藏 */
		$(function(){
			$(".personal-share-arrow").on('click',function(){
				$(this).hide();
			})
		})
	</script>
</html>
