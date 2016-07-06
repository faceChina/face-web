<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${titleShopName}-登录等待</title>
<script type="text/javascript">
	function toSubmit(){
		 document.getElementById("myform").submit();  
	}
</script>
</head>
<body onload="javascript:toSubmit();">
	<form id="myform" method=post action="${ctx}/j_spring_security_phone_check" enctype="application/x-www-form-urlencoded">
		<input type="hidden" name="shopCart"  value="${shopCart}">
		<input type="hidden" name="shopNo"    value="${shopNo}">
		<input type="hidden" name="username"  value="${username}" >
		<input type="hidden" name="password"  value="${password}" >
	</form>
</body>
</html>
