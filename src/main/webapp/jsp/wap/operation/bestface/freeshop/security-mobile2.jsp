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
<title>安全设置-设置手机号码2</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/security/css/main.css">
</head>
<body>

<div id="box">
	 <div class="progress">
	 	<ul>
	 		<li class="left">
 				<i>1</i>
 				<span>验证身份</span>
	 		</li>
	 		<li class="right active">
 				<i>2</i>
 				<span>绑定手机</span>
	 		</li>
	 	</ul>
	 </div>
	 
	 
	 <form action="/free/mine/phone/bind.htm" id="j-form" method="post" data-form>
	 	<div class="list-row list-row-width">
	 		<div class="list-col">
				<div class="list-inline">手机号</div>
				<div class="list-inline box-flex">
					<input type="text" value="" name="mobile" id="mobile" class="form-control" placeholder="请输入手机号" data-form-control>
					<input type="hidden" name="validateToken" value="${validateToken }" />
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">验证码</div>
				<div class="list-inline box-flex"><input type="text" value="" name="code" id="code" class="form-control" placeholder="请输入验证码" data-form-control></div>
				<div class="verify">
					<button type="button" class="btn btn-warning" onclick="getCode(this)">免费获取验证码</button>
				</div>
			</div>
	 	</div>
	 	
	 	<div class="button">
			<button type="button" onclick="formSubmit()" class="btn btn-danger btn-block" data-submit>下一步</button>
		</div>
	 	
	 </form>
	
	<%@ include file="../../../common/foot.jsp"%>
</div> 



<script type="text/javascript">
var validForm;
$(function (){
		validForm=$("[data-form]").validate({
			rules:{
				mobile:{
					required:true,
					mobile:true
				},
				code:{
					required:true,
					minlength : 6
				}
			},
			messages:{
				mobile:{
					required:"请输入要绑定的手机",
					mobile:"请输入正确的手机号码"
				},
				code:{
					required:"请输入验证码",
					minlength : "验证码不能小于6个字符"
				}
			}
		});
});

//获取验证码
function getCode(el){
	var phone = $('#mobile').val();
	if (null == phone || '' == phone) {
		art.dialog.alert("请输入手机号码");
	}
	$.post("/wap/${freeShopNo}/buy/mobilecode/phoneCode.htm",
			{"phone" : phone, "type" : 6}, function(falg){
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
function formSubmit() {
	if (validForm.form()) {
		$.post("/wap/${freeShopNo}/buy/mobilecode/testMobilecode.htm", {"type" : 6, "phone" : $('#mobile').val(), "mobilecode" : $('#code').val()},
				function(jsonData) {
			var data = JSON.parse(jsonData);
			if (data.success) {
				$('#j-form').submit();
			} else {
				art.dialog.alert("验证码输入错误");
			}
		});
	}
}
</script>

</body>
</html>

