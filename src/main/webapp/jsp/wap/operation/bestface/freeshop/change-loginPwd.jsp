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
<title>修改登录密码</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<%@include file="top.jsp" %>
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="/free/mine/loginPwd/saveEdit.htm" class="form" id="jform">
			<div class="form-group form-info">
				<input class="form-control" type="password" placeholder="请输入旧密码" id="pwd" name="pwd"/>
				<!--  当选择显示密码的时候使用-->
<!-- 				<input class="form-control" type="text" placeholder="请输入旧密码" id="pwd" name="pwd"/> -->
				<i class="iconfont icon-roundclose" style="width:100px;text-align:right;"></i>
			</div>
			<div class="form-group form-info">
				<input class="form-control" type="password" placeholder="请输入新密码" id="newpwd" name="newpwd"/>
				<!--  当选择显示密码的时候使用-->
<!-- 				<input class="form-control" type="text" placeholder="请输入新密码" id="newpwd" name="newpwd"/> -->
				<i class="iconfont icon-roundclose"></i>
			</div>
			<div class="form-group form-info">
				<input class="form-control" type="password" placeholder="确定新密码"  id="renewpwd" name="renewpwd"/>
				<!--  当选择显示密码的时候使用-->
<!-- 				<input class="form-control" type="text" placeholder="确定新密码"  id="renewpwd" name="renewpwd"/> -->
				<i class="iconfont icon-roundclose"></i>
			</div>
			<button type="submit" class="btn btn-default btn-danger" disabled="disabled">确认修改</button>
		</form>
		
		
	</div>
</div>
<script type="text/javascript">
	$('.icon-roundclose').click(function(){
		$(this).prev().val('');
	});
	
	$('input').each(function(){
		$(this).keyup(function(){
			var bool1 = ($('#pwd').val() != "");
			var bool2 = ($('#newpwd').val() != "");
			var bool3 = ($('#renewpwd').val() != "");
			if(bool1 && bool2 && bool3){
				$('button').attr('disabled',false);
			}else{
				$('button').attr('disabled',true);
			}
		});
	});
	
	$("#jform button").click(function(){
		$("#jform").validate({
				rules:{
					pwd:{
						required:true
					},
					newpwd:{
						required:true
					},
					renewpwd:{
						required:true,
						equalTo:"#newpwd"
					}
				},
				messages:{
					pwd:{
						required:"请输入原密码"
					},
					newpwd:{
						required:"请输入新密码"
					},
					renewpwd:{
						required:"请确认新密码",
						equalTo: "两次输入密码不一致"
					}
				},
				errorPlacement: function(error, element) { 	
					error.insertAfter(element);
				}
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
$(function(){
	dealErrMsg();
})
//错误信息处理
function dealErrMsg() {
	var errMsg = '${errMsg}';
	if (null != errMsg && '' != errMsg) {
		artDialogAlert(errMsg);
	}
}
</script>
</body>
</html>