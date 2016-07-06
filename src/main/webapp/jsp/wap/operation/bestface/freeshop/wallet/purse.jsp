<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的钱包</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/wallet/css/main.css">
</head>
<body>

<div id="box">
	<div class="group group-others width20">
		<div class="group-item">
			<a href="list.htm" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-qian" style="color:#FFA620;font-size:1.8rem;"></i></span>
				<span class="group-colspan">钱包</span>
				<span class="group-colspan">
					<span class="clr-light"><em class="clr-danger">
						<fmt:formatNumber value="${amount/100 }" pattern="0.00"></fmt:formatNumber>
					</em> 元</span>
					<i class="iconfont icon-right"></i>
				</span>
			</a>
		</div>
		<div class="group-item">
			<a href="bankcard/list.htm?type=1" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-bbgyinxingqia" style="color:#0499CF;font-size:1.8rem;"></i></span>
				<span class="group-colspan">我的银行卡</span>
				<span class="group-colspan">
					<span class="clr-light"><em class="clr-danger">${number }</em> 张</span>
					<i class="iconfont icon-right"></i>
				</span>
			</a>
		</div>
	</div>
	
	<!-- 卖家版 用户没在商城首页设置支付密码时进入钱包显示  -->
		<div id="j-message" style="display:none;">
			<p>为了您的钱包资金安全，请先设置支付密码哦！</p>
			<p style="text-align:right;padding:5px 5px 0 0;">
				<a href="/free/mine/paymentCode/edit.htm" class="clr-danger">前去设置</a>
			</p>
		</div>
	
	<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/freeNav.jsp" %>
	<!-- <script type="text/javascript" src="../../public/page/nav.js"></script> -->
	
</div>

<script type="text/javascript">
navbar('purse');
//如果用户未在商城首页设置支付密码，则用户进入我的钱包后，出现设置支付密码浮层。
//设置完成后才可以查看钱包明细和管理银行卡
$(function() {
	var paymentCode = ${isSetPaymentCode};
	if (!paymentCode) {
		toSet();
	}
});
function toSet(){
	artTip('<p style="line-height:25px;">为了您的钱包资金安全，请先设置支付密码哦！<a href="/free/mine/paymentCode/edit.htm" class="clr-danger">前去设置</a></p>',true);
};

</script>

</body>
</html>