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
<title>钱包余额支付</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>

<div id="box" style="display: none;">
	<div class="group">
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan">所需支付：</li>
				<li class="group-colspan clr-danger">￥ <fmt:formatNumber pattern="0.00" value="${totalPrice/100 }"/></li>
			</ul>
		</div>
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan">钱包余额：</li>
				<li class="group-colspan">￥ <fmt:formatNumber pattern="0.00" value="${amount/100 }"/><c:if test="${insufficient }"><span class="clr-light"> 余额不足 </span></c:if></li>
			</ul>
		</div>
	</div>
	<c:if test="${!insufficient }">
	<form action="/pay/pursePayConfirm.htm" method="post" data-form id="jform">
		<input type="hidden" name="orderNos" value='${orderNos }'/>
		<div class="group group-left width80">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">支付密码：</li>
					<li class="group-colspan">
						<input type="password" class="form-control" placeholder="支付密码" name="paymentCode" id="password" data-form-control>
					</li>
				</ul>
			</div>
		</div>
		<div class="button">
			<button class="btn btn-danger btn-block disabled" onclick="toPay(this)" data-submit>付款</button>
		</div>
	</form>
	</c:if>
	<c:if test="${insufficient }">
	<div class="group group-left">
		<div class="group-item">
			<a href="javascript:history.go(-1);" class="group-rowspan">
				<span class="group-colspan">使用其他支付方式</span>
				<span class="group-colspan right"><i class="iconfont icon-right"></i></span>
			</a>
		</div>
	</div>
	</c:if>
	<%@ include file="../../common/nav.jsp"%>
	<%@ include file="../../common/foot.jsp"%>
</div>
<script type="text/javascript">
$(function(){
	<c:choose>
		<c:when test="${isPassword}">
			$("#jform").submit();
		</c:when>
		<c:otherwise>
			$('#box').show();
		</c:otherwise>
	</c:choose>

	if('${info}'){
		$.dialog.alert('${info}');
	}
})
var jform = $('#jform').validate({
	rules:{
		password:{
			required:true
		}
	},
	messages:{
		password:{
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

