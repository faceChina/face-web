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
        	newpwd:{
        		required: true,
        		minlength: 6,
        		maxlength:20
        	},
        	renewpwd:{
			   required:true,
			   minlength: 6,
			   maxlength:20,
			   equalTo: "#newpwd"
		   }
		  },
		  messages: {
			  newpwd:{
				  required: "请输入新密码",
				  minlength: $.format("密码不能小于{0}个字 符"),
				  maxlength: $.format("密码不能大于{0}个字符")
			  },
			  renewpwd:{
				  required: "请确认新密码",
				  minlength: $.format("密码不能小于{0}个字 符"),
				  maxlength: $.format("密码不能大于{0}个字符"),
				  equalTo: "两次输入密码不一致"
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
</script>
</head>
<body>

	<div class="header navbar-fixed-top">
		<div class="col-md-4">
			微信营销平台领导者！
		</div>
		<div class="col-md-8 text-right">
			<a href="/">刷脸平台</a> | <a href="">操作指南</a> | <span>全国统一服务热线：<em class="tel">400-0003777</em></span>
		</div>
	</div>
	<div class="container header_logonav">
		<div class="row">

			<div class="header_logo col-md-3">
				<img src="${resourcePath}img/logo.gif" alt="微管家" />
			</div>
			<div class="header_nav col-md-9">
				<ul class="pull-right">
					<li class="header_nav_index"><a href="/company/index.htm">首页</a></li>
					<li class="header_nav_news"><a href="/company/index.htm">新闻中心</a></li>
					<li class="header_nav_join"><a href="/company/topic/to/shareholder.htm">渠道加盟</a></li>
					<li class="header_nav_case"><a href="/company/topic/to/case.htm">经典案例</a></li>
					<li class="header_nav_about"><a href="/company/topic/to/product.htm">品牌介绍</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- body -->
	<div class="container">

		<div class="row">
			<div class="content content-password">
				<h3>找回密码</h3>
				<ul class="m-steps">
					<li>1. 填写账号，验证绑定手机号码</li>
					<li class="active">2. 设置新密码</li>
					<li>3.完成</li>
				</ul>
				<form action="${ctx}/any/web/savePassword${ext}" id="jform"  method="post" class="form-horizontal horizontal">
				<input type="hidden" name="accounts" value="${accounts }">	
				<input type="hidden" name="mobile" value="${mobile}">	
				<input type="hidden" name="mobilecode" value="${mobilecode}">	
				<input type="hidden" name="validateToken" value="${validateToken }" />
				<input type="hidden" name="codeType" value="2">
				<input type="hidden" name="type" value="1">	
					<div class="form-group">
					    <label for="" class="col-sm-4 control-label">输入新密码：</label>
					    <div class="col-sm-8">
					      <input class="form-control input-short-6" type="password" value="" id="newpwd" name="newpwd">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="" class="col-sm-4 control-label">再次输入密码：</label>
					    <div class="col-sm-8">
					      <input class="form-control input-short-6" type="password" value="" id="renewpwd" name="renewpwd">
					    </div>
					  </div>
					  <div class="form-group">
					    <div class="col-sm-offset-4 col-sm-8">
					      <button type="submit" class="btn btn-default active" onclick="next()">下一步</button>
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


