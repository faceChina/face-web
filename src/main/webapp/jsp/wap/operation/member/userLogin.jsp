<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<%@include file="../../common/top.jsp" %>
<title>用户登陆</title>
</head>
<body onload="javascript:toLogin();">
<div class="container">
	<form method="post" action="${ctx}/j_spring_security_check"  class="generate-openshop" id="j-form">
		<div class="form-group">
			<input type="hidden" class="form-control" id="username" name="username" value="${username }" placeholder="请输入您的手机号码" >
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" id="password" name="password"  value="${password }" placeholder="请输入您的登录密码">
		</div>
		<input type="hidden" name="loginType"  value="wap">
		<input type="hidden" name="j_captcha"  value="${sessionScope.yzkeyword}">
	</form>
</div>
</body>
<script type="text/javascript">
function toLogin(){
	document.getElementById("j-form").submit();    
}
</script>
</html>