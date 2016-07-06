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
<title>我的钱包-支付方式</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>

<div id="box">
	<div class="group">
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan">商品名称：会员卡充值</li>
			</ul>
		</div>
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan">应付金额：￥<fmt:formatNumber pattern="0.00" value="${recharge.price/100 }"></fmt:formatNumber></li>
			</ul>
		</div>
	</div>
	<div class="group group-others widthself">
		<c:if test="${isWechatPay}">
			<div class="group-item" id="wechat-href" onclick="wechatPay()">
				<ul class="group-rowspan">
					<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-03.png" width="50"/></li>
					<li class="group-colspan fnt-16">微信支付</li>
					<li class="group-colspan clr-light">需安装微信客户端</li>
					<li class="group-colspan"><i class="iconfont icon-right"></i></li>
				</ul>
			</div>
		</c:if>
<!-- 		<div class="group-item" onclick="bankCardPay()"> -->
<!-- 			<ul class="group-rowspan"> -->
<%-- 				<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-04.png" width="50"/></li> --%>
<!-- 				<li class="group-colspan fnt-16">银行卡支付</li> -->
<!-- 				<li class="group-colspan clr-light">有卡就能付</li> -->
<!-- 				<li class="group-colspan"><i class="iconfont icon-right"></i></li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
		<div class="group-item" id="purse-href" onclick="pursePay()">
			<ul class="group-rowspan">
				<li class="group-colspan"><img src="${resourcePath }accounts/img/icon-01.png" width="50"/></li>
				<li class="group-colspan fnt-16">余额支付</li>
				<li class="group-colspan clr-light">使用钱包余额进行支付</li>
				<li class="group-colspan"><i class="iconfont icon-right"></i></li>
			</ul>
		</div>
	</div>
	
	<%@ include file="../../common/nav.jsp"%>
	<%@ include file="../../common/foot.jsp"%>
</div>
<form id="wechatPay" action="${ctx }/pay/wechatRecharge${ext}">
	<input type="hidden" name="rechargeNo" value='${recharge.rechargeNo }'/>
</form>
<form id="pursePay" action="${ctx }/pay/purseRecharge${ext}" method="post">
	<input type="hidden" name="rechargeNo" value='${recharge.rechargeNo }'/>
</form>
<form id="bankCardPay" action="${ctx }/pay/bankCardRecharge${ext}" method="post">
	<input type="hidden" name="rechargeNo" value='${recharge.rechargeNo }'/>
</form>
<script type="text/javascript">
// function select(){
// 	art.dialog({
// 		title:"选择",
// 		content:"<a href='../../security/page/security-pay-set.html'>未设置支付密码</a> | <a href='pay-purse.html'>余额充足</a> | <a href='pay-nopurse.html'>余额不足</a>"
// 	})
// }
function pursePay(){
	if('${existPaymentCode}'=='true'){
		$('#pursePay').submit();
		$('#purse-href').attr('onclick', 'javascript:void(0)');
	}else{
		location.href='/wap/${sessionShopNo}/buy/account/security/paymentCode.htm?retUrl=/pay/purseRecharge.htm?rechargeNo=${recharge.rechargeNo}';
	}
}
function bankCardPay(){
	if('${existPaymentCode}'=='true'){
		$('#bankCardPay').submit();
	}else{
		location.href='${ctx}/wap/${sessionShopNo}/buy/account/security/paymentCode.htm?retUrl=${ctx}/pay/bankCardPay${ext}?orderNos=${orderNos}';
	}
}
function wechatPay() {
	$('#wechatPay').submit();
	$('#wechat-href').attr('onclick', 'javascript:void(0)');
}
</script>
</body>
</html>