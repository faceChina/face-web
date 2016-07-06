<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>安全设置-设置支付密码2</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/security/css/main.css">
</head>
<body>

<div id="box">
	 <div class="progress">
	 	<ul>
	 		<li class="left">
 				<i>1</i>
 				<span>验证身份</span>
	 		</li>
	 		<li class="right active">
 				<i>2</i>
 				<span>设置支付密码</span>
	 		</li>
	 	</ul>
	 </div>
	 
	 
	 <form action="/free/mine/paymentCode/save.htm"  method="post" data-form>
	 	<div class="list-row list-row-width">
			<c:if test="${empty user.contacts }">
		 		<div class="list-col">
					<div class="list-inline">姓名</div>
						<div class="list-inline box-flex">
							<input type="text" name="username" id="username" class="form-control" placeholder="请填写真实的姓名" data-form-control>
						</div>
				</div>
			</c:if>
			<c:if test="${empty user.identity }">
				<div class="list-col">
					<div class="list-inline">身份证</div>
					<div class="list-inline box-flex"><input type="text"  name="idcard" id="idcard" class="form-control" placeholder="请填写身份证号码" data-form-control></div>
				</div>
			</c:if>
			<div class="list-col">
				<div class="list-inline">密码</div>
				<div class="list-inline box-flex">
					<input type="password" value="" name="password" id="password" class="form-control" placeholder="输入支付密码" data-form-control>
					<input type="hidden" name="validateToken" value="${validateToken }" />
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">密码</div>
				<div class="list-inline box-flex"><input type="password" value="" name="confirm_password" id="confirm_password" class="form-control" placeholder="请再次输入支付密码" data-form-control></div>
			</div>
	 	</div>
	 	
	 	<div class="button">
			<button type="submit" class="btn btn-danger btn-block" data-submit>完成</button>
		</div>
	 	
	 </form>
	<%@ include file="../../../common/foot.jsp"%>
</div>

<script type="text/javascript">
$(function (){
	$("[data-submit]").click(function(){
		var bool=$("[data-form]").validate({
			rules:{
				username:{
					required:true
				},
				idcard:{
					required:true,
					idcardno:true
				},
				password:{
					required:true
				},
				confirm_password:{
					required:true,
					equalTo:"#password"
				}
			},
			messages:{
				username:{
					required:"请输入真实姓名"
				},
				idcard:{
					required:"请输入您的身份证号",
					idcardno:"请正确输入身份证号码"
				},
				password:{
					required:"请输入支付密码"
				},
				confirm_password:{
					required:"请确认支付密码",
					equalTo: "两次输入密码不一致"
				}
			}
		}).form();
	});
});

</script>

</body>
</html>

