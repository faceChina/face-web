<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>我的钱包-我的账户</title>	
<%@ include file="../../../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("accounts");
});
</script>
</head>
<body>	
	<%@include file="../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="account"/>
						<%@include file="../../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">等待提现信息</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="widthdraw">
									<strong>已递交银行处理，将在9:00到15：00到账（节假日除外）；</strong>
									<hr/>
									<p>如果银行信息填写错误，导致提现失败，资金将自动退还到您的账户中！  </p>
									<ul>
										<li><a href="${ctx }/u/account/withdraw/index${ext}"  class="btn btn-default">继续提现</a></li>
										<li><a href="${ctx }/u/account/witdrawList${ext}"  class="btn btn-default">查看提现记录</a></li>
										<li><a href="${ctx }/u/account/inout${ext}"  class="btn btn-default">返回我的账户</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../../common/footer.jsp"%>
	</body>
</html>