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
<script type="text/javascript">
$(function(){
	$("#jform").find("button:last").click(function(){
		$("#jform").validate({
			rules:{
				newpwd:{
					required:true,
					minlength: 6,
					maxlength:20
				},
				renewpwd:{
					required:true,
					minlength: 6,
					maxlength:20,
					equalTo:"#newpwd"
				}
			},
			messages:{
				newpwd:{
					required:"请输入密码",
					minlength:$.validator.format("密码不能小于{0}个字 符"),
					maxlength: $.validator.format("密码不能大于{0}个字符")
				},
				renewpwd:{
					required:"请确认密码",
					minlength:$.validator.format("密码不能小于{0}个字 符"),
					maxlength: $.validator.format("密码不能大于{0}个字符"),
					equalTo:"两次输入密码不一致"
				}
			}
		}).form();
		
		if(flag){
			
			$("#jform").submit();
			
			return true;
		}else{
			return false;
		}
		
	});
});
</script>

</head>
<body>
<div class="register">
	<form action="${ctx}/any/web/savePassword${ext}" id="jform"  method="post" class="form">
		<input type="hidden" name="accounts" value="${accounts}">	
		<input type="hidden" name="mobilecode" value="${mobilecode}">	
		<input type="hidden" name="type" value="<c:choose><c:when test="${not empty type }">${type }</c:when><c:otherwise>2</c:otherwise></c:choose>">	
		<input type="hidden" name="codeType" value="2">	
		<input type="hidden" name="validateToken" value="${validateToken }" />
		
		<div class="list-row list-row-width">
			<div class="list-col">
				<div class="list-inline box-flex">
					<input type="password" class="form-control" placeholder="请设置新密码" id="newpwd" name="newpwd" data-form-control/>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex">
					<input type="password" class="form-control" placeholder="请输入密码" id="renewpwd" name="renewpwd" data-form-control/>
				</div>
			</div>
		</div>
		
		<div class="button">
			<button type="submit" class="btn btn-danger btn-block" data-submit>确定</button>
		</div>
		
	</form>
</div>

<%@ include file="../common/foot.jsp"%>
</body>
</html>



