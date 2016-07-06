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
<link rel="stylesheet" type="text/css"
	href="${resourcePath }security/css/main.css">
<script type="text/javascript">
var validForm;
$(function(){
	dealErrMsg();
	validForm = $("#j-form").validate({
		rules : {
			mobilecode : {
				required : true,
				minlength : 6
			}
		},
		messages : {
			mobilecode : {
				required : "请输入验证码",
				minlength : "验证码不能小于6个字符"
			}
		}
	});
})

//错误信息处理
function dealErrMsg() {
	var errMsg = '${errMsg}';
	if (null != errMsg && '' != errMsg) {
		art.dialog.alert(errMsg);
	}
}

function formSubmit() {
	if (validForm.form()) {
		$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/testMobilecode${ext}", {"type" : 5, "mobilecode" : $('#mobilecode').val()},
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
</head>
<body>
	<div id="box">
		<div class="progress">
			<ul>
				<li class="left active"><i>1</i><span>验证身份</span></li>
				<li class="right"><i>2</i><span>绑定手机</span></li>
			</ul>
		</div>
		<form id="j-form" action="/wap/${sessionShopNo}/buy/account/security/phonecode${ext}" method="post" data-form >
			<input type="hidden" name="retUrl" value="${retUrl }" />
			<div class="list-row list-row-width">
				<div class="list-col">
					<div class="list-inline" style="width: 120px;">已绑定的手机号码</div>
					<div class="list-inline box-flex clr-danger">${phone }</div>
				</div>
				<div class="list-col">
					<div class="list-inline">验证码</div>
					<div class="list-inline box-flex">
						<input type="text" name="mobilecode" id="mobilecode" maxlength="20"
							class="form-control" placeholder="请输入验证码" data-form-control>
					</div>
					<div class="verify">
						<button type="button" class="btn btn-warning"
							onclick="getCode(this, 5)">免费获取验证码</button>
					</div>
				</div>
			</div>
			<div class="button">
				<button type="button" onclick="formSubmit()" class="btn btn-danger btn-block" data-submit>下一步</button>
			</div>
		</form>
		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		//获取验证码
		function getCode(el, type, phone){
			$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/phoneCode${ext}",
					{"phone" : phone, "type" : type}, function(falg){
				if("true" != falg){
					art.dialog.alert("获取验证码失败");
				}
			});
			$(el).attr("disabled",true); 
			var time=60;
			var setIntervalID=setInterval(function(){
				time--;
				$(el).html("验证码已发送 "+ time +"秒");
				$(el).removeClass("btn-warning");
				if(time==0){
					clearInterval(setIntervalID);
					$(el).attr("disabled",false).html("免费获取验证码");
					$(el).addClass("btn-warning");
				}
			},1000);
		}
	</script>
</body>
</html>

