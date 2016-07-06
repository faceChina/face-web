<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>免费注册</title>
<%@ include file="../common/top.jsp"%>
<link rel="stylesheet" type="text/css"	href="${resourcePath }public/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/cartCookie.js"></script>
<script type="text/javascript" src="${resourcePath}js/onoff.js"></script>
<script type="text/javascript">
$(function(){
	$("#jform").validate({
		rules:{
			mobile:{
				required:true,
				mobile:true,
				remote: {    
		            url: "${ctx}/any/checkLoginAccount${ext}",     //后台处理程序    
		            type: "post",               //数据发送方式    
		            data: {phone: function(){return $("#mobile").val();}
		            }    
		        }
			},
			mobilecode:{
				required:true
			},
			pwd:{
				required:true,
				minlength: 6,
				maxlength:30
			},
			repwd:{
				required:true,
				minlength: 6,
				maxlength:30,
				equalTo:"#pwd"
			}
		},
		messages:{
			mobile:{
				required:"请输入手机号码",
				mobile:"请输入正确的手机号码",
				remote:"该手机号已被注册！"
			},
			mobilecode:{
				required:"请输入验证码"
			},
			pwd:{
				required:"请输入密码",
				minlength:$.validator.format("密码不能小于{0}个字 符"),
				maxlength: $.validator.format("密码不能大于{0}个字符")
			},
			repwd:{
				required:"靖确认密码",
				equalTo:"两次输入密码不一致",
				minlength:$.validator.format("密码不能小于{0}个字 符"),
				maxlength: $.validator.format("密码不能大于{0}个字符")
			}
		},
	});
	
	$("#submit").click(function(){
		var flag = $("#jform").validate().form();
		if(flag){
			postRegister();
		}else{
			return false;
		}
	});
	
});

function textRrror(el,text){
	var str = '<label for="mobile" generated="true" class="error">'+text+'</label>';
	$(el).addClass('error');
	if(!$(el).next().hasClass('error')) $(el).after(str);
	
}

function postRegister(){
	$("#submit").attr("disabled",true);
	var mobile = $("#mobile").val();
	var mobilecode = $("#mobilecode").val();
	var pwd = $("#pwd").val();
	var repwd = $("#repwd").val();
	$.post("${ctx}/any/appRegister${ext}",{"mobile":mobile,"mobilecode":mobilecode,"pwd":pwd,"repwd":repwd,"type":2},
			  function(falg){
		$("#submit").attr("disabled",false);
		if("success" == falg){
			art.dialog.alert("注册成功！",function() {
				$("#username").val(mobile);
				$("#password").val(pwd);
				$("#jformlogin").submit();
				//self.location = "${ctx}/app/login${ext}";
			});
		}else{
			art.dialog.alert(falg);
		}
		
	});
}


//获取验证码
function getCode(el){
	var phone = $("#mobile").val();
	var flag= $("#jform").validate().element(mobile);
	if(flag){
	$.post("${ctx}/any/checkLoginAccount${ext}",{"phone":phone},
			  function(flag){
			if("true" == flag){
				$.post("${ctx}/any/web/registerCode${ext}",{"phone":phone,"type":1},
						  function(flag){
						if("true" == flag){
						}else{
							art.dialog.alert(flag,function() {
								getCode(el);
							});
						}
				});
				
				$(el).attr("disabled",true);
				var time=60;
				var setIntervalID=setInterval(function(){
					time--;
					$(el).html("验证码已发送 "+ time +"秒");
					$(el).removeClass("btn-warning");
					if(time==0){
						clearInterval(setIntervalID);
						$(el).attr("disabled",false).html("免费获取验证码");
						$(el).addClass("btn-warning");
					}
				},1000);
			}
	});
	}
}


//开关
$(function(){
	$("#onoff1").onoff({
		on:function(){
// 			$('#pwd').show();
// 			$('#repwd').hide();
			change("text");
			return true;
		},
		off:function(){
// 			$('#repwd').show();
// 			$('#pwd').hide();
			change("password");
			return true;
		}
	});
	
// 	 $('#pwd').keyup(function(){
// 		 $('#repwd').val($(this).val());
// 		 });
// 		 $('#repwd').keyup(function(){
// 		 	$('#pwd').val($(this).val());
// 		 });

})

function change(type) {
	$("#pwd").attr("type", type);
}







</script>

</head>
<body>
<div class="register">
	<form action="" id="jform" method="post" class="form">
	
	<div class="list-row list-row-width">
		<div class="list-col">
			<div class="list-inline">手机号码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入手机号码" id="mobile" name="mobile" data-form-control></div>
		</div>
		
		<div class="list-col">
			<div class="list-inline">验证码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入验证码" id="mobilecode" name="mobilecode" data-form-control></div>
			<div class="list-inline">
				<button type="button" class="btn btn-warning btn-block" onclick="getCode(this)">获取验证码</button>
			</div>
		</div>
		
		<div class="list-col">
			<div class="list-inline">密码</div>
			<div class="list-inline box-flex">
				<input type="password" class="form-control" placeholder="请输入密码" id="pwd" name="pwd" data-form-control>
<!-- 				<input type="text" class="form-control" placeholder="请输入密码" id="repwd" name="repwd" style="display:none;" data-form-control> -->
			</div>
			<div class="list-inline">
				<div class="onoff onoff-off" data-onoff="off" id="onoff1">
					<span class="onoff-handle"></span>
					<span class="onoff-info">●●●</span>
				</div>
			</div>
		</div>
		
		
	</div>	
		<div class="button">
			<button type="button" id="submit" class="btn btn-danger btn-block" data-submit>确认</button>
		</div>
		
	</form>
</div>


<div class="container" style="display:none;">
<form method="post" action="/wap/${sessionShopNo}/j_spring_security_check" id="jformlogin">
<input type="hidden" name="username" id="username" value="" >
<input type="hidden" name="password" id="password" value="" >
<input type="hidden" name="loginType"  value="wap">
<input type="hidden" name="j_captcha"  value="${sessionScope.yzkeyword}">
</form>
</div>



<%@ include file="../common/foot.jsp"%>
</body>
</html>

