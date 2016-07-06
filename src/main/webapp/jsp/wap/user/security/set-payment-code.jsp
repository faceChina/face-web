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
	<c:when test="${not empty user.identity }">
		安全设置-修改支付密码
	</c:when>
	<c:otherwise>
		安全设置-设置支付密码
	</c:otherwise>
</c:choose>
</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }security/css/main.css">
</head>
<body>

	<div id="box">
		<div class="progress">
			<ul>
				<li class="left"><i>1</i><span>验证身份</span></li>
				<li class="right active"><i>2</i>
				<c:choose>
					<c:when test="${not empty user.identity }">
						<span>修改支付密码</span>
					</c:when>
					<c:otherwise>
						<span>设置支付密码</span>
					</c:otherwise>
				</c:choose>
				</li>
			</ul>
		</div>
		<form action="${ctx }/wap/${sessionShopNo}/buy/account/security/set${ext}" id="j-form" method="post" data-form>
		    <input type="hidden" name="retUrl" value='${retUrl }' />
		    <input type="hidden" name="validateToken" value="${validateToken }" />
			<div class="list-row list-row-width">
				<div class="list-col">
					<div class="list-inline">姓名</div>
					<div class="list-inline box-flex">
						<input type="text" <c:if test="${not empty user.contacts }">disabled="disabled"</c:if>
						    name="userName" id="userName" value="${user.contacts }" maxlength="30"
							class="form-control" placeholder="请填写本人真实姓名，无法修改" data-form-control>
					</div>
				</div>
				<div class="list-col">
					<div class="list-inline">身份证</div>
					<div class="list-inline box-flex">
						<input type="text" <c:if test="${not empty user.identity }">disabled="disabled"</c:if>
						    name="identity" id="identity" value="${user.identity }"
							class="form-control" placeholder="请填写本人身份证号码，无法修改" data-form-control>
					</div>
				</div>
				
				<div class="list-col">
					<div class="list-inline">密码</div>
					<div class="list-inline box-flex">
						<input type="password" value="" name="paymentCode" id="paymentCode" maxlength="20"
							class="form-control" placeholder="输入支付密码" data-form-control>
					</div>
				</div>
				<div class="list-col">
					<div class="list-inline">密码</div>
					<div class="list-inline box-flex">
						<input type="password" value="" name="confirmCode" maxlength="20"
							id="confirmCode" class="form-control"
							placeholder="请再次输入支付密码" data-form-control>
					</div>
				</div>
			</div>
			<div class="button">
				<button type="submit" class="btn btn-danger btn-block" data-submit>完成</button>
			</div>
		</form>
		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		$(function() {
			$("[data-submit]").click(function() {
				var bool = $("[data-form]").validate({
					rules : {
						userName : {
							required : true
						},
						identity : {
							required : true,
							idcardno : true
						},
						paymentCode : {
							required : true,
							minlength : 6
						},
						confirmCode : {
							required : true,
							equalTo : "#paymentCode",
							minlength : 6
						}
					},
					messages : {
						userName : {
							required : "请输入真实姓名"
						},
						identity : {
							required : "请输入您的身份证号",
							idcardno : "请正确输入身份证号码"
						},
						paymentCode : {
							required : "请输入支付密码",
							minlength : "密码不能小于6个字符"
						},
						confirmCode : {
							required : "请确认支付密码",
							equalTo : "两次输入密码不一致",
							minlength : "密码不能小于6个字符"
						}
					}
				}).form();
			});
		});
	</script>
</body>
</html>

