<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全设置</title>	
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("security");
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
						<c:set var="crumbs" value="security"/>
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">支付密码设置</a></li>
								</ul>
							</div>
							<div class="content">
								<c:choose>
							        <c:when test="${hasPaymentCode }">
							        	<ul class="m-steps">
											<li>1. 身份验证</li>
											<li>2. 重置支付密码</li>
											<li  class="active">3.重置成功</li>
										</ul>
										<div class="finish">
											<strong>恭喜!你的支付密码重置成功! </strong>
											<button type="button" class="btn btn-default active" onClick="location.href='${ctx}${retUrl }${ext }'">确认</button>
										</div>
							        </c:when>
							        <c:otherwise>
							        	<ul class="m-steps">
											<li>1. 验证已绑定手机</li>
											<li>2. 补全资料</li>
											<li  class="active">3.设置成功</li>
										</ul>
										<div class="finish">
											<strong>恭喜，您的钱包支付密码设置成功！  </strong>
											<button type="button" class="btn btn-default active" onClick="location.href='${ctx}${retUrl }${ext }'">确认</button>
										</div>
							        </c:otherwise>
							    </c:choose>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	</body>
</html>