<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>登录</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"	href="${resourcePath }public/css/main.css">
<script type="text/javascript">
$(document).ready(function() {
    var sess = '${sessionScope.errorMsg}';
	if(sess){
 		artTip("用户名不存在或密码输入错误！");
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
<div class="login-pic"><img src="${resourcePath }public/img/login-pic.jpg"/></div>
<form method="post" action="${ctx}/j_spring_security_check" id="jform" class="form-login">
	<input type="hidden" name="shopNo"  value="${sessionShopNo}">
		<div class="list-row list-row-width">
			<div class="list-col">
				<div class="list-inline">账户</div>
				<div class="list-inline box-flex">
					<c:choose>
						<c:when test="${empty userLogin}">
							<input type="text" class="form-control" placeholder="请输入注册时的手机号码" id="username" name="username" value="${param.username}" data-form-control/>
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" placeholder="请输入注册时的手机号码" data-form-control disabled value="${userLogin}" style="color:#333; ">
							<input type="hidden" id="username" name="username" value="${userLogin}">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">密码</div>
				<div class="list-inline box-flex">
					<input type="password" class="form-control" placeholder="请输入您的密码" id="password" name="password" data-form-control/>
					<input type="hidden" name="loginType"  value="free">
				</div>
			</div>
		</div>
		
		<div class="button">
			<button type="submit" class="btn btn-danger btn-block" data-submit onclick="toLogin()">登录</button>
		</div>
		
		<div class="clearfix">
			<!-- <a href="" class="btn btn-link clr-light fnt-16">快速注册</a> -->
			<a href="${ctx}/any/findPassword${ext}?type=3" class="btn btn-link right clr-light fnt-16">找回密码</a>
		</div>
</form>
</div>
<%@ include file="../../../common/foot.jsp"%>
</body>
</html>

