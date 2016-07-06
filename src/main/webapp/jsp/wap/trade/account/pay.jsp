<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<title>我的钱包-支付方式</title>
		
		<%@ include file="../../common/top.jsp"%>
		<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
	</head>
	<body>
		<div id="box">
			<div class="group">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">商品名称：${title }</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">应付金额：￥<fmt:formatNumber pattern="0.00" value="${totalPrice/100 }"></fmt:formatNumber></li>
					</ul>
				</div>
			</div>
			<div class="group group-others widthself">
				<c:if test="${isWechatPay }">
					<div class="group-item" id="wechat-href" onclick="wechatPay()">
						<ul class="group-rowspan">
							<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-03.png" width="50"/></li>
							<li class="group-colspan fnt-16">微信支付</li>
							<li class="group-colspan clr-light">需安装微信客户端</li>
							<li class="group-colspan"><i class="iconfont icon-right"></i></li>
						</ul>
					</div>
				</c:if>
				<c:if test="${!isWechatPay }">
					<div class="group-item" id="alipay-href" onclick="alipay()">
						<ul class="group-rowspan">
							<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-06.png" width="50"/></li>
							<li class="group-colspan fnt-16">支付宝</li>
							<li class="group-colspan clr-light">支付宝钱包支付</li>
							<li class="group-colspan"><i class="iconfont icon-right"></i></li>
						</ul>
					</div>
				</c:if>
				<div class="group-item" onclick="lakalaPay()">
					<ul class="group-rowspan">
						<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-04.png" width="50"/></li>
						<li class="group-colspan fnt-16">银行卡支付</li>
						<li class="group-colspan clr-light">有卡就能付</li>
						<li class="group-colspan"><i class="iconfont icon-right"></i></li>
					</ul>
				</div>
<%-- 				<c:if test="${not empty memberCard }"> --%>
<!-- 					<div class="group-item" id="member-href" onclick="memberCardPay()"> -->
<!-- 						<ul class="group-rowspan"> -->
<%-- 							<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-05.png" width="50"/></li> --%>
<!-- 							<li class="group-colspan fnt-16">会员卡支付</li> -->
<!-- 							<li class="group-colspan clr-light">使用会员卡余额支付</li> -->
<!-- 							<li class="group-colspan"><i class="iconfont icon-right"></i></li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
<%-- 				</c:if> --%>
				<div class="group-item" id="purse-href" onclick="pursePay()">
					<ul class="group-rowspan" onclick="">
						<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-01.png" width="50"/></li>
						<li class="group-colspan fnt-16">余额支付</li>
						<li class="group-colspan clr-light">
							<span>支付方式</span><br/>
							<span>使用钱包余额进行支付</span>
						</li>
						<li class="group-colspan"><i class="iconfont icon-right"></i></li>
					</ul>
				</div>
			</div>
			<%@ include file="../../common/nav.jsp"%>
			<%@ include file="../../common/foot.jsp"%>
		</div>
		<form id="wechatPay" action="${ctx }/pay/wechatPay${ext}">
			<input type="hidden" name="orderNos" value='${orderNos }'/>
		</form>
		<form id="alipay" action="${ctx }/pay/alipay/alipayProducer${ext}">
			<input type="hidden" name="orderNos" value='${orderNos }'/>
		</form>
		<form id="pursePay" action="${ctx }/pay/pursePay${ext}" method="post">
			<input type="hidden" name="orderNos" value='${orderNos }'/>
		</form>
		<form id="memberCardPay" action="${ctx }/pay/memberCardPay${ext}" method="post">
			<input type="hidden" name="orderNos" value='${orderNos }'/>
		</form>
		<form id="lakalaPay" action="${ctx }/pay/lakalaPay${ext}" method="post">
			<input type="hidden" name="orderNos" value='${orderNos }'/>
		</form>
		<script type="text/javascript">
			function pursePay(){
				if('${existPaymentCode}'=='true'){
					$('#pursePay').submit();
					$('#purse-href').attr('onclick', 'javascript:void(0)');
				}else{
					location.href='${ctx}/wap/${sessionShopNo}/buy/account/security/paymentCode.htm?retUrl=${ctx}/pay/pursePay${ext}?orderNos=${orderNos}';
				}
			}
			
			function memberCardPay(){
				if('${existPaymentCode}'=='true'){
					$('#memberCardPay').submit();
					$('#member-href').attr('onclick', 'javascript:void(0)');
				}else{
					location.href='${ctx}/wap/${sessionShopNo}/buy/account/security/paymentCode.htm?retUrl=${ctx}/pay/memberCardPay${ext}?orderNos=${orderNos}';
				}
			}
			
			function wechatPay() {
				$('#wechatPay').submit();
				$('#wechat-href').attr('onclick', 'javascript:void(0)');
			}
			function alipay() {
				$('#alipay').submit();
				$('#alipay-href').attr('onclick', 'javascript:void(0)');
			}
			function lakalaPay(){
				if('${existPaymentCode}'=='true'){
					$('#lakalaPay').submit();
				}else{
					location.href='${ctx}/wap/${sessionShopNo}/buy/account/security/paymentCode.htm?retUrl=${ctx}/pay/lakalaPay${ext}?orderNos=${orderNos}';
				}
			}
		</script>
	</body>
</html>

