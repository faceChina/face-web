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

<div id="box"  class="newBankCardBox bankcardmanage">
	<%-- <form action="/pay/lakala/producer.htm" method="post" data-form id="jform">
		<input type="hidden" name="orderNos" value='${orderNos }'/>
		<input type="hidden" name="bankCardId" id="bankCardId" value="${bankCardList[0].id }"/> --%>
		<div class="group group-justify">
			<c:forEach items="${bankCardList }" var="bankCard">
			<div class="group-item">
				<a class="group-rowspan">
					<span class="group-colspan">
						<em>${bankCard.bankName }</em><c:if test="${bankCard.isWithdrawCard }"><span class="ktx">可提现</span></c:if><br/>
						<em class="clr-light">尾号${bankCard.lastFourChar } <span class="carttype"><c:if test="${bankCard.bankType==2 }">借记卡</c:if><c:if test="${bankCard.bankType==3 }">信用卡</c:if></span></em>
					</span>
				</a>
			</div>
			</c:forEach>
		</div>
	<!-- </form> -->
	<div class="button">
		<a href='/pay/lakala/addBankCard.htm?type=2&orderNos=${orderNos }' class="btn btn-default btn-block btn-danger">
			<i class="iconfont icon-add"></i>添加银行卡
		</a>
	</div>
	<%@ include file="../../common/nav.jsp"%>
	<%@ include file="../../common/foot.jsp"%>
</div>
<script type="text/javascript">
var flag;
$(function(){
	flag=1;
	if('${info}'){
		$.dialog.alert('${info}');
	}
	if('${retCode}'=='0210'){
		$.dialog.alert('此卡签约信息错误,请重新签约.')
	}
})
/* var jform = $('#jform').validate({
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
}); */
function toPay(bankCardId){
	if(flag==1){
		flag=2;
		$("#bankCardId").val(bankCardId);
		$("#jform").submit();
	}
}
</script>
</body>
</html>

