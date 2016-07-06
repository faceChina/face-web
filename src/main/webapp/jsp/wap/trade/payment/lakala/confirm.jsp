<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection"/> 
<title>银行卡支付</title>
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/top.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#getCode").attr("disabled",true);
		$("#getCode").removeClass('btn-warning');
		var time=60;
		var setIntervalID=setInterval(function(){
			time--;
			$("#getCode").html("验证码已发送 "+ time +"秒");
			if(time==0){
				clearInterval(setIntervalID);
				$("#getCode").attr("disabled",false).html("免费获取验证码");
				$("#getCode").addClass('btn-warning');
			}
		},1000)
	})
</script>
</head>

<body>
<div id="box"  class="newBankCardBox add-newbancarbox">
	
	<form action="/pay/lakala/consumer.htm" id="jform" data-form method="post">
		<input type="hidden" name="orderNos" value='${orderNos }'/>
		<input type="hidden" name="bankCardId" value='${bankCardId }'/>
		<input type="hidden" name="payeeAmount" value="${resultMap.cnyAmount }">
		<input type="hidden" name="orderAmount" value="${resultMap.cnyAmount }">
		<input type="hidden" name="transactionId" value="${resultMap.transactionId }">
		<input type="hidden" name="merOrderId" value="${resultMap.merOrderId }">
		<div class="group group-left">
			<div class="group-item width70">
				<ul class="group-rowspan">
					<li class="group-colspan">应付金额</li>
					<li class="group-colspan"><span class="clr-danger">￥${resultMap.cnyAmount }</span> 元</li>
				</ul>
			</div>
			<div class="group-item width55">
				<ul class="group-rowspan">
					<li class="group-colspan">手机号</li>
					<li class="group-colspan">${cell }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">
						<input type="text" name="msgCode" class="form-border input-code" placeholder="请输入手机验证码" data-form-control>
					</li>
					<li class="group-colspan">
						<button type="button" class="btn btn-warning btn-block bt-code" id="getCode" onclick="getcode(this);">获取验证码</button>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="button">
			<button type="button" class="btn btn-danger btn-block disabled" onclick="pay(this);" data-submit>确认支付</button>
		</div>	
	</form>
	
	<%@ include file="../../../common/nav.jsp"%>
	<%@ include file="../../../common/foot.jsp"%>
</div>
<script type="text/javascript">
	$("#jform").validate({
		rules:{
			msgCode:{
				required:true
			}
		},
		messages:{
			msgCode:{
				required:'请输入短信验证码',
			}
		}
	});
	function getcode(el){
		$(el).attr("disabled",true);
		$(el).removeClass('btn-warning');
		var time=60;
		var setIntervalID=setInterval(function(){
			time--;
			$(el).html("验证码已发送 "+ time +"秒");
			if(time==0){
				clearInterval(setIntervalID);
				$(el).attr("disabled",false).html("免费获取验证码");
				$(el).addClass('btn-warning');
			}
		},1000);
		$.post("/pay/lakala/getPayCode.htm",$("#jform").serialize(),function(data){
			console.log(data)
			var json=JSON.parse(data);
			if(json.retCode=='0000'){
				artTip("短信验证码发送成功!");
			}else{
				artTip(json.retMsg);
			}
		});
	}
	function pay(el){
		$(el).attr("disabled",true);
		$(el).removeClass('btn-warning');
		$.post("/pay/lakala/consumer.htm",$("#jform").serialize(),function(data){
			$(el).attr("disabled",false);
			$(el).addClass('btn-warning');
			var json=JSON.parse(data);
			if(json.retCode=='0000' || json.retCode=='0008'){
				artTip('付款成功',function(){
					location.href='/pay/pay-success.htm';
				});
			}else {
				$(el).attr("disabled",false);
				$(el).addClass('btn-warning');
				artTip(json.retMsg)
				/*if(json.retCode=='0312'){
					artTip('付款失败,短信验证码错误.')
				}else{
					artTip('短信验证码验证失败.')
				} */
			}
		})
	}
</script>
</body>
</html>