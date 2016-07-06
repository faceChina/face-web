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
<title>银行卡支付</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>

<div id="box">
	
	<form action="${ctx }/pay/bindPayForRecharge${ext}" id="jform" data-form method="post">
		<input type="hidden" name="rechargeNo" value='${rechargeNo }'/>
		<input type="hidden" name="bankCardId" value='${bankCardId }'/>
		<div class="group group-left width80">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">应付金额：</li>
					<li class="group-colspan"><span class="clr-danger">￥ <fmt:formatNumber pattern="0.00" value="${totalPrice/100 }"/></span> 元</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">
						<input type="password" name="paymentCode" id="paymentCode" class="form-border" placeholder="支付密码" data-form-control>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="button">
			<button class="btn btn-danger btn-block disabled" onclick="toPay(this)" data-submit>确认支付</button>
		</div>	
	</form>
	
	<%@ include file="../../common/nav.jsp"%>
	<%@ include file="../../common/foot.jsp"%>
</div>
<script type="text/javascript">
$(function(){
	if('${message}'){
		artTip('${message}');
	}
})
var jform = $('#jform').validate({
	rules:{
		paymentCode:{
			required:true
		}
	},
	messages:{
		paymentCode:{
			required:"请输入您的支付密码"
		}
	}
});
function toPay(thiz){
	if(jform.form()){
		$(thiz).attr("disabled",true);
		$("#jform").submit();
	}
}
</script>
</body>
</html>

