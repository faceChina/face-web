<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-登录</title>

<%@ include file="../common/top.jsp"%>
<script type="text/javascript">

var logout = function(){
	window.location.href = "${ctx}/j_spring_security_logout";
}

function login(){
	window.location.href = "${ctx}/wap/login.htm";
}

</script>

</head>
<body>
<a href="javascript:login();">登录</a> 
		<a href="javascript:logout();">退出</a> 



<%@ include file="../common/foot.jsp"%>
</body>
</html>

