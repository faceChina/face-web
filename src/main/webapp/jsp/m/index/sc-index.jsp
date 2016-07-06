<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户管理</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("index");
	$('.j-tooltip a').tooltip({
		placement:"right"
	})
});
</script>
</head>
<body>
<!-- header -->
	<%@include file="../common/header.jsp"%>
<!-- header end -->
<!-- body -->
<div class="container" id="j-content">
	<div class="row">

		<div class="col-md-2">
			<!--nav-left -->
					<%@include file="../common/left.jsp"%>
			<!--nav-left end-->
		</div>

		<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="scindex"/>
					<%@include file="../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#">店铺信息</a></li>
							</ul>
						</div>
						<div class="content">
								<ul class="media-info" style="width:60%;">
									<li data-nano>店铺名称：${shopInfo.name}</li>
									<li>店铺类型：商城
										
									</li>
									<fmt:formatDate value="${shopInfo.effectiveTime }" pattern="yyyy年MM月dd日" var="formatDate" />
									<li><c:if test="${shopInfo.proxyType == 1 }">
										<span class="morecol j-tooltip" style="margin-left:0;">
											招募授权码：${shopInfo.authorizationCode }
											<a href="#" data-toggle="tooltip" title="" data-original-title="店铺授权码：发给员工/代理商，绑定后可以对其进行管理"></a>
										</span>
										</c:if></li>
									<li>到期时间：${formatDate }</li>
									<!-- <li>会员到期时间：2014-12-08    <a href="javascript:;" class="btn btn-default" onclick="mediaRenewal()">续费</a></li> -->
								</ul>
								<div class="u-verticalline" style="height:66px;"></div>
								<ul class="media-info">
									<li>昨日收益：<span class="clr-attention"><fmt:formatNumber pattern="0.00" value="${yesterdayIncome/100 }"/></span> 元</li>
									<li ></li>
									<li>
										<a href="/u/account/shopInout.htm" class="btn btn-default">收支明细</a> 
									</li>
								</ul>
						</div>
					</div>
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#">店铺经营</a></li>
							</ul>
						</div>
						<div class="content">
							<ul class="media-info" style="width:50%;">
								<li><b>订单情况</b></li>
								<li>
									<span class="display-inline">
										今日所有订单：<b><a href="/u/order/list.htm?timeStates=1" class="clr-attention">（${countTodayAll }）</a></b>
									</span>
									<span class="display-inline">
										付款订单：<b><a href="/u/order/list.htm?status=2" class="clr-attention">（${countPay }）</a></b>
									</span>
								</li>
							</ul>
							<div class="u-verticalline" style="height:54px;"></div>
							<ul class="media-info"  >
								<li><b>商品管理</b></li>
								<li>
									<span class="display-inline">
										在售商品：<b><a href="/u/good/good/list.htm?status=1" class="clr-attention">（${onsell }）</a></b>
									</span>
									<span class="display-inline">
										下架商品：<b><a href="/u/good/good/list.htm?status=3" class="clr-attention">（${unshelve }）</a></b>
									</span>
								</li>
							</ul>
						</div>
					</div>
					<div class="box">
                    <div class="title">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#">代理推广管理</a></li>
                        </ul>
                    </div>
                    <div class="content">
                        <ul class="media-info" style="width:27.333%;">
                            <li><b>订单情况</b></li>
                            <li>
                            	<c:if test="${shopInfo.isFree == 1 }">
                            		<!-- 供货商 -->
									<span class="display-inline">
										待付款订单：<b><a href="/u/agency/order/orderlist.htm" class="clr-attention">（${waitForPayAgencyOrderCount }）</a></b>
									</span>
									<span class="display-inline">
										待发货订单：<b><a href="/u/agency/order/orderlist.htm" class="clr-attention">（${wairForSendAgencyOrderCount }）</a></b>
									</span>
								</c:if>
								<c:if test="${shopInfo.isFree == 2 }">
									<!-- 代理商 -->
									<span class="display-inline">
										待付款订单：<b><a href="/u/proxy/order/listorder.htm" class="clr-attention">（${waitForPayAgencyOrderCount }）</a></b>
									</span>
									<span class="display-inline">
										待发货订单：<b><a href="/u/proxy/order/listorder.htm" class="clr-attention">（${wairForSendAgencyOrderCount }）</a></b>
									</span>
								</c:if>
                            </li>
                        </ul>
                        <div class="u-verticalline" style="height:54px;"></div>
                        <ul class="media-info" style="width:30.333%;" >
                            <li><b>商品管理</b></li>
                            <li>
                            	<c:if test="${shopInfo.isFree == 1 }">
                            		<!-- 供货商 -->
									<span class="display-inline">
										在售商品：<b><a href="/u/agency/good/list.htm?status=1" class="clr-attention">（${salesAgencyGoodCount }）</a></b>
									</span>
									<span class="display-inline">
										仓库商品：<b><a href="/u/agency/good/list.htm?status=3" class="clr-attention">（${warehouseAgencyGoodCount }）</a></b>
									</span>
								</c:if>
								<c:if test="${shopInfo.isFree == 2 }">
									<!-- 代理商 -->
									<span class="display-inline">
										在售商品：<b><a href="/u/proxy/good-list.htm" class="clr-attention">（${salesAgencyGoodCount }）</a></b>
									</span>
									<span class="display-inline">
										仓库商品：<b><a href="/u/proxy/good-list-storage.htm" class="clr-attention">（${warehouseAgencyGoodCount }）</a></b>
									</span>
								</c:if>
                            </li>
                        </ul>
                        <div class="u-verticalline" style="height:54px;"></div>
                        <ul class="media-info"  style="width:30.33%;">
                        <c:if test="${shopInfo.isFree == 1 }">
                        <!-- 供货商 -->
	                        <c:if test="${shopInfo.proxyType == 1 }">
	                        	<!-- 内部供货商 -->
	                            <li><b>代理商管理</b></li>
	                            <li>
										<span class="display-inline">
											<a href="/u/agency/grade/list.htm" class="clr">店铺等级管理</a>
										</span>
	                            </li>
	                        </c:if>
	                        <c:if test="${shopInfo.proxyType == 2 }">
	                        	<!-- 外部供货商 -->
	                        	<li><b>代理商管理</b></li>
	                                <li>
										<span class="display-inline">
											待审核代理商：<b><a href="/u/agency/recruit/applylist.htm" class="clr-attention">（${waitForCheckShopCount }）</a></b>
										</span>
										<span class="display-inline">
											代理商管理：<b><a href="/u/agency/recruit/authlist.htm" class="clr-attention">（${passedAgencyShopCount }）</a></b>
										</span>
	                                </li>
	                        </c:if>
                        </c:if>
                        <c:if test="${shopInfo.isFree == 2 }">
                        	<!-- 分销商 -->
                        	<c:if test="${subordinateCount > 0 }">
                        		 <li><b>下级代理订单情况</b></li>
                                <li>
									<span class="display-inline">
										待付款订单：<b><a href="/u/proxy/order/ntorders.htm" class="clr-attention">（${subordinateWaitForPayOrderCount }）</a></b>
									</span>
									<span class="display-inline">
										待发货订单：<b><a href="/u/proxy/order/ntorders.htm" class="clr-attention">（${subordinateWaitForSendOrderCount }）</a></b>
									</span>
                                </li>
                        	</c:if>
                        </c:if>
                        </ul>
                    </div>
                </div>	
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members- set" data-toggle="tab">快捷导航</a></li>
								<li class="text-muted"></li>
							</ul>
						</div>
						<div class="content">
							<div class="quick-nav">
<!-- 								<div class="faster-reminder"><img src="../img/faster-reminder.png" alt=""></div> -->
<!-- 								<a href="tempmanage.html" class="btn btn-default">官网装修</a> -->
<!-- 								<span class="arrows"></span> -->
								<a href="${ctx}/u/template/WSC/listOwTemplate${ext}" class="btn btn-default">商城装修</a>
								<span class="arrows"></span>
								<a href="${ctx}/u/lbs/index${ext}" class="btn btn-default">店铺管理</a>
								<span class="arrows"></span>
								<a href="${ctx}/u/good/shopType/list${ext}" class="btn btn-default">商品管理</a>
								<span class="arrows"></span>
								<a href="${ctx}/u/order/list${ext}" class="btn btn-default">订单管理</a>
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<!-- body end -->

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<!-- footer end -->


	</body>
</html>