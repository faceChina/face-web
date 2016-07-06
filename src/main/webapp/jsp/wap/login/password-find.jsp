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
<title>密码找回</title>

<%@ include file="../common/top.jsp"%>
<link rel="stylesheet" type="text/css"	href="${resourcePath }public/css/main.css">
<script type="text/javascript">
$(function(){
	$("#jform").find("button:last").click(function(){
		 $("#jform").validate({
			rules:{
				accounts:{
					required:true,
					mobile:true
				},
				phone:{
					   required:true,
					   mobile:true
					   
				   },
				mobilecode:{
					required:true,
					minlength: 6
				}
			},
			messages:{
				accounts:{
					required:"请输入账号",
					mobile:"请输入正确的账号"
				},
				phone:{
					required: "请输入手机号",
					  mobile:"请输入正确的手机号码"
					   
				   },
				mobilecode:{
					required:"请输入验证码",
					minlength: "验证码不能小于6个字符"
				}
			},
		}).form();
		if(flag){
			$("#jform").submit();
			return true;
		}else{
			return false;
		}
	});
});

//获取验证码
function getCode(el){
	
	var loginAccount = $("#accounts").val();
	$.post("${ctx}/any/checkUserPhone${ext}",{"loginAccount":loginAccount, "phone" : $('#phone').val()},
			  function(flag){
	if("SUCCESS" == flag){
			$.post("${ctx}/any/web/registerCode${ext}",{"phone":loginAccount,"type":2},
					  function(flag){
					if("true" == flag){
					}else{
						artTip(flag,function() {
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
	}else{
		artTip(flag);
	}
	});
	
}

</script>
</head>
<body>
<div class="register">
	
	<form action="${ctx}/any/web/checkUser${ext}" id="jform"  method="post" class="form">
		<input type="hidden" name="type" value='<c:choose><c:when test="${not empty type }">${type }</c:when><c:otherwise>2</c:otherwise></c:choose>'>	
		<input type="hidden" name="codeType" value="2">	
		
		<div class="list-row list-row-width">
		<div class="list-col">
			<div class="list-inline">账号</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入账号" id="accounts" name="accounts" data-form-control></div>
		</div>
		<div class="list-col">
			<div class="list-inline">手机号码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入手机号码" id="phone" name="phone" data-form-control></div>
		</div>
		<div class="list-col">
			<div class="list-inline">验证码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入验证码" id="mobilecode" name="mobilecode" data-form-control></div>
			<div class="list-inline" style="margin-right:0;">
				<button type="button" class="btn btn-warning btn-block" onclick="getCode(this)">获取验证码</button>
			</div>
		</div>
		</div>
		
		<div class="button">
			<button type="submit" class="btn btn-danger btn-block" data-submit>下一步</button>
		</div>
	</form>
</div>
			

<%@ include file="../common/foot.jsp"%>
</body>
</html>



