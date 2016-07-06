<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>登录</title>
<%@ include file="../../common/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/cartCookie.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    shopCart.init({
		'decimal' : 4,
		'cartName':'CART_WJGJ'
       });
    var cookieJson = JSON.stringify(shopCart.findItem());
    $("#shopCart").val(cookieJson); 
    
    
    var sess = '${sessionScope.errorMsg}';
	if(sess){
 		textRrror('#password','用户名不存在或密码输入错误！');
	} 
 	
});

function textRrror(el,text){
	var str = '<label for="mobile" generated="true" class="error">'+text+'</label>';
	$(el).addClass('error');
	if(!$(el).parent().next().hasClass('error')) $(el).parent().after(str);
	
}


$(function(){
	$("#jform").find("button").click(function(){
		$("#jform").validate({
			rules:{
				username:{
					required:true
				},
				password:{
					required:true,
					minlength: 6
				}
			},
			messages:{
				username:{
					required:"请输入用户名"
				},
				password:{
					required:"请输入密码",
					minlength:$.validator.format("密码不能小于{0}个字 符")
				}
			}
		});
	});
});
</script>

</head>
<body>
<div class="login">
<form method="post" action="${ctx}/j_spring_free_check" id="jform" class="form-login">
<input type="hidden" name="shopNo"  value="${sessionShopNo}">
	<h3>用户登录</h3>
		<div class="form-login-group">
			<i class="icon-user"></i>
			<input type="text" class="form-control" name="username" id="username" placeholder="请输入您注册时的手机号码">
		</div>
		<div class="form-login-group">
			<i class="icon-password"></i>
			<input type="password" class="form-control" name="password" id="password" placeholder="请输入您的密码">
		</div>
		<div class="form-login-group">
			<button type="submit" class="btn btn-danger btn-block">登录</button>
		</div>
		<div class="form-login-group">
		</div>
		<div class="form-login-group">
		</div>
</form>
</div>
<%-- <%@ include file="../common/foot.jsp"%> --%>
</body>
</html>

