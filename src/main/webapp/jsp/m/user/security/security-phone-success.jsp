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
									<li class="active"><a href="#members-set" data-toggle="tab">手机号码绑定</a></li>
									<li class="text-muted">温馨提示：原手机号码不能接收短信,请联系客服人员为您进行修改。客服电话：<span class="fontcor-red">400-000-3777 </span></li>
								</ul>
							</div>
							<div class="content">
								<ul class="m-steps">
									<li>1. 解绑旧手机</li>
									<li>2. 绑定新手机</li>
									<li  class="active">3.修改完成</li>
								</ul>
								<div class="finish">
									<strong>恭喜，您的手机绑定修改成功！  </strong>
									<button type="button" class="btn btn-default active" onClick="location.href='${ctx}${retUrl }${ext }'">确认</button>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	</body>
</html>