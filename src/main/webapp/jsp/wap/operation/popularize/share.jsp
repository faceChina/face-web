<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>链接设置</title>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/popularize/css/main.css">
</head>
<body>
<div id="box">	
	<form  method="post" id="jform" data-form >
		<input type="hidden" name="shareUri" value='${shareUri}'>
		<div class="share-info">
			<p class="fnt-16">下面是您唯一标识的链接，您可以分享到任何的地方，也可以绑定到自己的微信名片上<span class="clr-danger">（我的名片-我的商城-链接自己的店铺）</span>，有人成功下单后，您就能得到奖励佣金哦！</p>
			<input type="text" placeholder="请输入您的手机号码" class="form-control" name="moblie" id="moblie" data-form-control>
			<div class="button">
				<button type="submit" class="btn btn-block btn-danger disabled" data-submit>生成链接</button>
			</div>
		</div>
		<p class="clr-light" style="padding:10px;">注：手机号码是获得佣金的唯一凭证。已注册用户，请填写注册时手机号码。</p>
	</form>
	
</div>

<script type="text/javascript">
$(function(){
	$('#jform').validate({
		rules:{
			moblie:{
				required:true,
				mobile:true
			}
		},
		messages:{
			moblie:{
				required:'请输入手机号码',
				mobile:'请输入正确的手机号码'
			}
		}
	});
});
</script>
</body>
</html>