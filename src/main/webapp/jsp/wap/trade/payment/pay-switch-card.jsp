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
<title>我的钱包-我的银行卡</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>

<div id="box">
	
	<!-- 银行卡信息  -->
	
	<div class="group">
	
		<!-- 快捷支付 方式  -->
		<c:forEach items="${bankCardList }" var="bankCardVo">
			<div class="group-item">
			<a href="javascript:switchBankCard(${bankCardVo.id })" class="group-rowspan">
				<span class="group-colspan">
					<em>${bankCardVo.bankName }</em><br/>
					<em class="clr-light">${bankCardVo.abbreviation }</em>
				</span>
				<em class="group-colspan clr-light">快捷支付</em>
			</a>
		</div>
		</c:forEach>
	</div>
	
	<!-- 添加银行卡按钮  -->
	<div class="button">
		<a href="javascript:toAddCard();" class="btn btn-default btn-block"><i class="iconfont icon-add"></i> 添加银行卡</a> 			
	</div>
	
	<%@ include file="../../common/foot.jsp"%>
	<%@ include file="../../common/nav.jsp"%>
</div>

<!-- 验证支付密码 -->
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="${ctx }/wap/${sessionShopNo}/buy/bankcard/add${ext}" id="paymentcode-form" class="form-login">
  		<input type="hidden" name="errorUrl" value='/pay/bankCardPay.htm?orderNos=${orderNos}' />
  		<input type="hidden" name="retUrl" value='/pay/switchBankCard.htm?orderNos=${orderNos}' />
  		<div class="list-row list-row-clearborder " style="margin:0;">
  			<div class="capacity">请输入支付密码进行验证</div>
    		<div class="list-col">
      			<div class="list-inline"><input type="password" name="paymentCode" id="paymentCode" class="form-border" placeholder="支付密码"></div>
    		</div>
		</div>
	</form>
</div>

<form id=j-form action="${ctx }/pay/switchBankCard${ext}" method="get">
	<input type="hidden" name="orderNos" value='${orderNos }'/>
	<input type="hidden" name="bankCardId" id="bankCardId" value=''/>
</form>
</body>
<script type="text/javascript">
$(function(){
	dealErrMsg();
})
//错误信息处理
function dealErrMsg() {
	var errMsg = '${errorMsg}';
	if (null != errMsg && '' != errMsg) {
		if (errMsg == '支付密码错误，请重试') {
			artTip(errMsg);
			toAddCard();
		}
	}
}
function switchBankCard(bankCardId){
	$("#bankCardId").val(bankCardId);
	$("#j-form").submit();
}

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

