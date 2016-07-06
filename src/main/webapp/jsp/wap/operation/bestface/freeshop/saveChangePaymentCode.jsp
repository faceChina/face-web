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
<title>修改支付密码</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="set.html" class="form" id="jform">
			<div class="form-group form-info">
				<input class="form-control" type="password" placeholder="请输入新密码" id="newpwd" name="newpwd"/>
			</div>
			<div class="form-group form-info">
				<input class="form-control" type="password" placeholder="请重新输入新密码" id="renewpwd" name="renewpwd"/>
			</div>
			<button type="submit" class="btn btn-default btn-danger">确认保存</button>
		</form>
	</div>
</div>
<script type="text/javascript">
$("#jform button").click(function(){
	$("#jform").validate({
			rules:{
				newpwd:{
					required:true
				},
				renewpwd:{
					required:true,
					equalTo:"#newpwd"
				}
			},
			messages:{
				newpwd:{
					required:"请输入新密码"
				},
				renewpwd:{
					required:"请重新输入新密码",
					equalTo: "两次输入密码不一致"
				}
			},
				errorPlacement: function(error, element) { 	
					error.insertAfter(element);
				},
			});
});
	


//验证提示
function alertFun(txt){
	var str = '<div class="m-alert Bounce"><span class="m-alert-info">错误提示！</span></div>';
	$('body').append(str);
	var alertEl = $('.m-alert');
	if(alertEl.is(':hidden')){
		alertEl.find('.m-alert-info').html(txt)
		alertEl.show();
		setTimeout(function(){
			alertEl.hide();
		},1000);
	}

	return false;
}
</script>
</body>
</html>