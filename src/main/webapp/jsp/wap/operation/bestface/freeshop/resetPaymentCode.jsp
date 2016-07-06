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
<title>重置支付密码</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/security/css/main.css">
<script type="text/javascript">
$(function(){
	dealErrMsg();
})
//错误信息处理
function dealErrMsg() {
	var errMsg = '${errorMsg}';
	if (null != errMsg && '' != errMsg) {
		art.dialog.alert(errMsg);
	}
}
</script>
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="change-password-two.html" method="post" class="form" id="jform">
			<div class="form-group">
				<label for="">身份证号码：</label>
				<input class="form-control" type="text" id="idcard" name="idcard" placeholder="请输入您绑定的身份证号码"/>
			</div>
			<div class="form-group">
				<label for="">已绑定的手机号码：${cell }</label>
			</div>
			<div class="form-group">
				<div class="col-xs-6">
					<input type="text" class="form-control" id="mobilecode" name="mobilecode" placeholder="请输入六位验证码">
				</div>
				<div class="col-xs-offset-1 col-xs-5">
					<button type="button" class="btn btn-default" onclick="getCode(this)" style="margin-top:0;">获取手机验证码</button>
				</div>
			</div>
			
			<button type="button" class="btn btn-default btn-danger">下一步</button>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#jform button").click(function(){
		 $("#jform").validate({
			rules:{
				idcard:{
					required:true,
					idcardno:true
				},
				mobile:{
					required:true,
					mobile:true
				},
				mobilecode:{
					required:true
				}
			},
			messages:{
				idcard:{
					required:"请输入您绑定的身份证号码",
					idcardno:"请正确输入身份证号码"
				},
				mobile:{
					required:"请输入手机号码",
					mobile:"请输入正确的手机号码"
				},
				mobilecode:{
					required:"请输入验证码"
				}
			},
			errorPlacement:function(error,element) {
				error.insertAfter(element);
			}
		});
	});
	//获取验证码
	function getCode(el){
		$.post("/wap/${sessionShopNo}/buy/mobilecode/phoneCode.htm",
				{"phone" : phone, "type" : 4}, function(falg){
			if("true" != falg){
				art.dialog.alert("获取验证码失败");
			}
		});
		$(el).attr("disabled",true); 
		$(el).removeClass('btn-warning');
		var time=60;
		var setIntervalID=setInterval(function(){
			time--;
			$(el).html("验证码已发送 "+ time +"秒");
			if(time==0){
				clearInterval(setIntervalID);
				$(el).attr("disabled",false).html("免费获取验证码");
			}
		},1000);
	}
});
</script>
</body>
</html>