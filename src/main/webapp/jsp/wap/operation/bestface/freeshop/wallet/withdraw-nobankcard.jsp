<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>没有银行卡时-提现</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
<body>
	<div id="box">
		<div class="withdraw-nobankcard">
			<i class="iconfont icon-tishi clr-blue fnt-18"></i> <span>你还没有可用于提现的银行卡，请先添加一张储蓄卡。</span>
		</div>
		<div class="button">
			<a onclick="toAddCard()"
				class="btn btn-danger btn-block"><i class="iconfont icon-add"></i>
				添加银行卡</a>
		</div>
		<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/freeNav.jsp" %>
	</div>
</body>
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="${ctx}/free/wallet/withdraw/addpage${ext}" id="paymentcode-form" class="form-login">
  		<div class="list-row list-row-clearborder " style="margin:0;">
  			<div class="capacity">请输入支付密码进行验证</div>
    		<div class="list-col">
      			<div class="list-inline"><input type="password" name="paymentCode" id="paymentCode" class="form-border" placeholder="支付密码"></div>
    		</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
	var errorMsg = '${errorMsg}';
	console.log(errorMsg);
	if (null != errorMsg && '' != errorMsg) {
		toAddCard();
		artTip(errorMsg);
	}
})
function toAddCard(){
	$('#paymentCode').val(null);
	//添加银行卡信息
	artDialogComfirm('paymentcode-form',function(){
	 var bool = $("#paymentcode-form").validate({
             rules : {
                 paymentCode : {
                     required : true
                 }
             },
             messages : {
             	paymentCode : {
                     required : "请输入支付密码"
                 }
             }
         }).form();
         if(bool){
              //自定义操作
              $('#paymentcode-form').submit();
         }
	});
}
</script>
</html>