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

@media ( min-width : 1000px) {
	.container {
		width: 1190px;
	}
}
</style>
</head>
<body>
	<%@include file="/jsp/m/common/nav-top.jsp"%>
	<!-- body -->
	<div class="container">

		<div class="row">
			<div class="content content-password">
				<h3>找回密码</h3>
				<ul class="m-steps">
					<li>1. 填写账号，验证绑定手机号码</li>
					<li>2. 设置新密码</li>
					<li class="active">3.完成</li>
				</ul>
				<div class="finish">
					<strong>设置成功，您可以使用新密码登入！</strong> <a class="btn btn-default active" href="${ctx}/login.htm">确认</a>
				</div>
			</div>

		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="/jsp/m/common/footer.jsp"%>
	<!-- footer end -->
</body>
</html>



