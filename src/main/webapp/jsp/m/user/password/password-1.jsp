<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>登入</title>
<!-- top -->
<%@ include file="/jsp/m/common/base.jsp"%>
<%@ include file="/jsp/m/common/validate.jsp"%>
<!--top end -->
<style type="text/css">
body {
	min-width: 661px;
}

.header_banner {
	text-align: center;
}
label.error{
	display:block;
}

.horizontal{
	width:600px;
	margin: 0 auto;
}
@media ( min-width : 1000px) {
	.container {
		width: 1190px;
	}
}
</style>

<script type="text/javascript">

function next(){
	var flag=$("#jform").validate({
        rules: {
        	accounts:{
        		required: true
        	},
		   mobile:{
			   required:true,
			   mobile:true
			   
		   },
		   mobilecode: {
			    required: true,
			    minlength: 4
		   }
		  },
		  messages: {
			  accounts:{
				  required: "请输入账号"
			  },
			  mobile:{
				  required: "请输入手机号",
				  mobile:"请输入正确的手机号码"
			  },
			  mobilecode:{
			    required: "请输入验证码",
			    minlength:$.format("验证码不能小于{0}个字符")
		   }
		  }
    }).form();
	
	if(flag){
		$(".j-loading").show();
		$("#jform").submit();
		
		return true;
	}else{
		return false;
	}
	
}


//获取验证码
function getCode(el){
	
	var phone = $("#mobile").val();
	var loginAccount = $("#accounts").val();
	$.post("${ctx}/any/checkUserPhone${ext}",{"loginAccount":loginAccount,"phone":phone},
			  function(flag){
					if("SUCCESS" == flag){
						if("" != phone){
							$.post("${ctx}/any/web/registerCode${ext}",{"phone":phone,"type":2},
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
								if(time==0){
									clearInterval(setIntervalID);
									$(el).attr("disabled",false).html("免费获取验证码");
								}
							},1000);
						 }
					}else{
						art.dialog.alert(flag);
					}
	});
}
</script>
</head>
<body>
	<%@include file="/jsp/m/common/nav-top.jsp"%>
	<!-- body -->
	<div class="container">

		<div class="row">
			<div class="content content-password">
				<h3>找回密码</h3>
				<ul class="m-steps">
					<li class="active">1. 填写账号，验证绑定手机号码</li>
					<li>2. 设置新密码</li>
					<li>3.完成</li>
				</ul>
				
				<form action="${ctx}/any/web/checkUser${ext}" id="jform"  method="post" class="form-horizontal horizontal">
				<input type="hidden" name="codeType" value="2">	
				<input type="hidden" name="type" value="1">	
				<div class="form-group">
				    <label for="" class="col-sm-4 control-label">账号：</label>
				    <div class="col-sm-8">
				      <input class="form-control input-short-6" type="text" value="" id="accounts" name="accounts">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="" class="col-sm-4 control-label">已绑定手机号：</label>
				    <div class="col-sm-8">
				      <input class="form-control input-short-6" type="text" value="" id="mobile" name="mobile">
					  <button type="button" class="btn btn-default" style="display:block;margin-top:10px;" onclick="getCode(this)">免费获取验证码</button>
					  <p class="help-block">默认为注册手机号</p>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="" class="col-sm-4 control-label">手机验证码：</label>
				    <div class="col-sm-8">
				      <input class="form-control input-short-6" type="text" value="" id="mobilecode" name="mobilecode">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="col-sm-offset-4 col-sm-8">
				      <button type="button" class="btn btn-default active" onclick="next()">下一步</button>
				    </div>
				  </div>
				</form>
			</div>

		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="/jsp/m/common/footer.jsp"%>
	<!-- footer end -->
</body>
</html>


