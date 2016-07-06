<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>安全设置-设置手机号码</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }security/css/main.css">

<script type="text/javascript">
//错误信息处理
function dealErrMsg() {
	var errMsg = '${errMsg}';
	if (null != errMsg && '' != errMsg) {
		art.dialog.alert(errMsg);
	}
}
</script>
</head>

<body>
	<div id="box">	 
	 <form id="j-form" action="/wap/${sessionShopNo}/buy/account/security/wechatbindphone${ext}" method="post" data-form>
	 	<input type="hidden" name="retUrl" value='${retUrl }' />
	 	<input type="hidden" name="validateToken" value="${validateToken }" />
	 	<div class="list-row list-row-width noborder">
	 		<div class="list-col">
				绑定手机号码
			</div>
	 		<div class="list-col">
				<div class="list-inline box-flex">
					<input type="text" value="" name="phone" id="phone" class="form-control" maxlength="11" placeholder="请输入手机号码" data-form-control>
				</div>
				<div class="verify">
					<button type="button" class="btn btn-warning" onclick="getCode(this,5)">免费获取验证码</button>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex">
					<input type="text" value="" name="mobilecode" id="mobilecode" class="form-control" maxlength="6" placeholder="请输入验证码" data-form-control>
				</div>
			</div>
	 	</div>
	 	
	 	<div class="button">
			<button type="button" class="btn btn-danger btn-block" data-submit>提交</button>
		</div>
	 </form>
</div> 


<script type="text/javascript">
var validForm;
function formSubmit() {
	$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/testMobilecode${ext}", {"type" : 5, "mobilecode" : $('#mobilecode').val(),"phone":$("#phone").val()},
			function(jsonData) {
				var data = JSON.parse(jsonData);
				if (data.success) {
					$('#j-form').submit();
				} else {
					artTip("验证码输入错误");
					return false;
				}
		});
}

//提交的时候验证
$(function (){
	var boolCodeAndIphone=$("[data-form]").validate({
		rules:{
			phone:{
				required:true,
				mobile:true
			},
			mobilecode:{
				required:true
			}
		},
		messages:{
			phone:{
				required:"请输入手机号码",
				mobile:"请输入正确的手机号码"
			},
			mobilecode:{
				required:"请输入验证码"
			}
		}
});
	
	$("[data-submit]").click(function(){
		if(boolCodeAndIphone.form()){
			formSubmit();
			//artTip("手机号码设置成功");
		}else {
			return false;
		}
	});
});

var boolCode=$("[data-form]").validate({
	rules:{
		phone:{
			required:true,
			mobile:true
		}
	},
	messages:{
		phone:{
			required:"请输入手机号码",
			mobile:"请输入正确的手机号码"
		}
	}
});
//获取验证码的时候验证
function getCode(el,type){
	if(boolCode.form()){
		$(el).attr("disabled",true);
		$(el).removeClass('btn-warning');
		var time=60,
		 setIntervalID=setInterval(function(){
			time--;
			$(el).html("验证码已发送 "+ time +"秒");
			
			if(time==0){
				clearInterval(setIntervalID);
				$(el).attr("disabled",false).html("免费获取验证码");
				$(el).addClass('btn-warning');
			}
		},1000);
		
		$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/phoneCode${ext}",
				{"phone" : $("#phone").val(), "type" : type}, function(falg){
			if("true" != falg){
				art.dialog.alert("获取验证码失败");
			}
		});

		return true;
	}
	return false;
}
</script>

</body>
</html>
