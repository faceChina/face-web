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
<title>成功页面</title>
<%@include file="../common/base.jsp"%>
<%@ include file="../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }public/css/main.css">
</head>
<body>
<div id="box">
	
	<!-- 注册登录时 支付密码设置成功 返回的成功页面 -->
	<c:if test="${success == 'paymentCode' }">
		<div class="success" id="success1">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">恭喜，您的新支付密码设置成功！<br/><em class="small clr-light">一定要牢记哦~</em></p>
		</div>
		
		<div class="button">
			<a href="${redirectUrl }" class="btn btn-danger btn-block">确定</a>
		</div>
	</c:if>
	
	<c:if test="${success == 'password' }">
		<div class="success" id="success1">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">恭喜，您的登陆密码设置成功！<br/><em class="small clr-light">一定要牢记哦~</em></p>
		</div>
		
		<div class="button">
			<a href="${redirectUrl }" class="btn btn-danger btn-block">确定</a>
		</div>
	</c:if>
	
	<!-- 安全设置  中新手机号码绑定成功 返回的成功页面 -->
	<c:if test="${success == 'cell' }">
		<div class="success" id="success2">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">恭喜，您的新手机号码绑定成功！<br/></p>
		</div>
		
		<div class="button">
			<a href="${redirectUrl }" class="btn btn-danger btn-block">确定</a>
		</div>
	</c:if>
	
	
	<!--我的钱包中 余额成功转出后返回的成功页面 -->
	<c:if test="${success == 'balance' }">
		<div class="success" id="success4">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">你已成功申请余额转出，<br/>已提交银行处理。</p>
		</div>
		
		<div class="button">
			<a href="${redirectUrl }" class="btn btn-danger btn-block">确定</a>
		</div>
	</c:if>
	
	<!--支付成功后返回的成功页面 -->
	<c:if test="${success == 'pay' }">
		<div class="success" id="success5">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">
				支付成功
			</p>
		</div>
		<div class="button">
			<a href="${redirectUrl }" type="button" class="btn btn-order btn-block">查看我的订单</a>
		</div>
		<div class="tip-uploadsl">
			提示：为了您方便管理订单，建议您关注浙江<br/>
			脸谱公众号（脸谱bestface）或下载刷脸app
		</div>
		<div class="button">
			<a href="http://www.o2osl.com/app/download.htm" type="button" class="btn btn-danger btn-block">前去下载</a>
		</div>	
	</c:if>
	
	<!-- 会员卡充值 成功页面 -->
	<c:if test="${success == 'recharge' }">
		<div class="success" id="success6">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">
				<span>成功充值130元！</span><br/>
				<span>您可以到<span class="clr-danger">我的会员卡</span>中查看充值情况哦</span>
			</p>
		</div>
		
		<div class="button">
			<a href="${redirectUrl }" type="button" class="btn btn-danger btn-block">我知道啦！</a>
		</div>
	</c:if>
	
	
	<!-- 预约信息提交成功  -->
	<c:if test="${success == 'appointment' }">
		<div class="success" id="success7">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">
				<span>您已提交成功！请等待审核。</span><br/>
				<span>审核结果将以短信提醒</span>
			</p>
		</div>
		<div class="button">
			<a href="${redirectUrl }" type="button" class="btn btn-danger btn-block">查看预约订单</a>
		</div>
	</c:if>
	
		
</div>

</body>
</html>