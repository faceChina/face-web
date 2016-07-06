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
<title>设置</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="top.jsp" %>
<style>
	body{
		padding-bottom:60px;
	}
</style>
</head>
<body>
<div class="container">
	<div class="m-set">
		<div class="info">
			<ul class="m-list">
				<li>
					<a href="/free/mine/info/edit.htm">
						<span class="pic">
						<c:if test="${empty shop.shopLogoUrl }">
							<img src="${resourcePath }img/defaultShopLogo.jpg" alt="" width="60"/>
						</c:if>
						<c:if test="${not empty shop.shopLogoUrl }">
							<img src="${picUrl }${shop.shopLogoUrl}" alt="" width="60"/>
						</c:if>
						</span>
						<span class="tit">
							<span>昵称：${user.nickname }</span>
							<span>店铺：${shop.name }</span>
						</span>
						<i class="iconfont icon-right icon pull-right clr-light"></i>
					</a>
				</li>
			</ul>
		</div>
		<div class="info">
			<h4>安全信息</h4>
			<ul class="m-list">
				<li>
					<a href="/free/mine/loginPwd/edit.htm" id="j-login">
						<span>登录密码</span>
						<i class="iconfont icon-right pull-right clr-light"></i>
					</a>
				</li>
				<li>
					<a href="/free/mine/paymentCode/edit.htm">
						<span>支付密码</span>
						<i class="iconfont icon-right pull-right clr-light"></i>
					</a>
				</li>
				<li>
					<a href="/free/mine/phone/check.htm">
						<span>绑定手机：<var>${user.cell }</var></span>
						<i class="iconfont icon-right pull-right clr-light"></i>
					</a>
				</li>
			</ul>
		</div>
		<div class="info">
			<h4>身份信息</h4>
			<ul class="m-list">
				<li>
					<a href="javascript:;">
						<span class="tit pull-left">
						<span>真实姓名：${user.contacts }</span>
						<span>身份证号 : <var class="light-clr">${user.identity }</var></span>
						</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	/* navbar("set"); */
	/* function select(){
		art.dialog({
			content:'<a href="../../app/pages/security/page/security-pay-set.html">未设置支付密码</a> | <a href="reset-password1.html">已设置支付密码</a>'
		});
	} */
</script>
</body>
</html>