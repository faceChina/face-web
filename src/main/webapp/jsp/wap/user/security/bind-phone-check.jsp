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
<title>
<c:choose>
	<c:when test="${isSetPaymentCode}">
		安全设置-修改支付密码
	</c:when>
	<c:otherwise>
		安全设置-设置支付密码
	</c:otherwise>
</c:choose>
</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }security/css/main.css">
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
	<div id="box">
		<div class="progress">
			<ul>
				<li class="left active"><i>1</i><span>验证身份</span></li>
				<li class="right">
					<i>2</i>
					<c:choose>
						<c:when test="${isSetPaymentCode}">
							<span>修改支付密码</span>
						</c:when>
						<c:otherwise>
							<span>设置支付密码</span>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
		<form method="post" id="j-form" data-form>
			<input type="hidden" name="validateToken" value="${validateToken }" />
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
							onclick="getCode(this, 3)">免费获取验证码</button>
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
		var isSetPaymentCode = ${isSetPaymentCode};
		if (!isSetPaymentCode) {
			artTip("为了您的钱包资金安全，请先设置支付密码哦！");
		}
		var validForm;
		$(function() {
			validForm = $("[data-form]").validate({
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
		});

		//获取验证码
		function getCode(el, type){
			$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/phoneCode${ext}",
					{"type" : type}, function(falg){
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
		function formSubmit() {
			if (validForm.form()) {
				$.post("${ctx}/wap/${sessionShopNo}/buy/mobilecode/testMobilecode${ext}", {"type" : 3, "mobilecode" : $('#mobilecode').val()},
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
