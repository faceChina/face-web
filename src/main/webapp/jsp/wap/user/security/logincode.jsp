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
<title>安全设置-登录密码修改</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }security/css/main.css">
<script type="text/javascript">
$(function(){
	dealErrMsg();
})
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
		<form id="jform" data-form method="post">
			<div class="list-row list-row-width">
				<div class="list-col">
					<div class="list-inline">原密码</div>
					<div class="list-inline box-flex">
						<input type="password" name="passwd" id="passwd" maxlength="20"
							class="form-control" placeholder="请输入原密码" data-form-control>
					</div>
				</div>
				<div class="list-col">
					<div class="list-inline">新密码</div>
					<div class="list-inline box-flex">
						<input type="password" name="newPasswd" id="newPasswd" maxlength="20"
							class="form-control" placeholder="请输入新密码" data-form-control>
					</div>
				</div>
				<div class="list-col">
					<div class="list-inline">新密码</div>
					<div class="list-inline box-flex">
						<input type="password" name="confirmPasswd" id="confirmPasswd"
							class="form-control" placeholder="请再次输入新密码" maxlength="20"
							data-form-control>
					</div>
				</div>
			</div>
			<div class="button">
				<button type="button" onclick="formSubmit()" class="btn btn-danger btn-block" data-submit>提交</button>
			</div>

		</form>

		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
	    var validForm;
		$(function() {
			validForm = $("#jform").validate({
				rules : {
					passwd : {
						required : true,
						minlength : 6
					},
					newPasswd : {
						required : true,
						minlength : 6
					},
					confirmPasswd : {
						required : true,
						minlength : 6,
						equalTo : "#newPasswd"
					}
				},
				messages : {
					passwd : {
						required : "请输入原密码",
						minlength : "密码不能小于6个字符"
					},
					newPasswd : {
						required : "请输入新密码",
						minlength : "密码不能小于6个字符"
					},
					confirmPasswd : {
						required : "请确认新密码",
						minlength : "密码不能小于6个字符",
						equalTo : "两次输入密码不一致"
					}
				}
			});
		});
		function formSubmit(){
			if (validForm.form()) {
				$("#jform").submit();
			}
		}
	</script>
</body>
</html>

