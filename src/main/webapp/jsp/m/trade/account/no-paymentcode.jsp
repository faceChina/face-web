<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的钱包-我的账户</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("accounts");
		$('.popover-dismiss').popover({
			trigger : 'focus'
		});
	});
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
						<c:set var="crumbs" value="account"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">我的账户</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="my-accounts">
								<span>钱包余额：<b class="clr-attention">
								    <fmt:formatNumber value="${amount / 100 }" pattern="0.00" />
								</b>元
								</span>
								<button type="button" class="btn btn-default"
									onClick="location.href='${ctx}/u/account/security/paymentCode${ext }?retUrl=/u/account/index'">设置支付密码</button>
							</div>
							<div class="help-block">为了保障您的账户安全，请先开通支付密码！</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>


