<!DOCTYPE html>

<%@ page pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<form class="login-form" id="jlogin" action="${ctx}/j_spring_security_check" method="post">
		  <div class="login-input">
			<i class="icon-login un"> </i>
			<input type="text" placeholder="刷脸账号" id="username" name="username" >
		  </div>
		  <div class="login-input">
          	<i class="icon-login pwd"> </i>
          	<input type="password" placeholder="密码" id="password" name="password">
          </div>
          <button type="submit" class="login-btn">登录</button>
</form>
</html>