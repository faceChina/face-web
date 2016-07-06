<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本资料</title>	
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("basics");
	
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
						<c:set var="crumbs" value="information"/>
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">基本资料</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="alert alert-warning">
									温馨提示：原手机号码不能接收短信,请联系客服人员为您进行修改。客服电话：<span class="fontcor-red">400-000-3777 </span>
								</div>
							
								<div class="row">
									<div class="basics">
										<img <c:choose>
									        	 <c:when test="${not empty user.headimgurl}">src="${picUrl }${user.headimgurl }"</c:when>
									        	 <c:otherwise>src="${resourcePath }/img/spadm/basics.jpg"</c:otherwise>
											 </c:choose>
                                            alt="" width="108" height="108" class="pull-left"/>
										<p><b>账号：</b><small>${user.loginAccount }</small></p>
										<p><b> 昵称：</b><small><c:out value="${user.nickname }"></c:out> </small></p>
										<p><b>邮箱地址：</b><small><c:out value="${user.email }"></c:out> </small> </p>
									</div>
								</div>
								<div class="row">
									<div class="basics-infor">
										<p>真实姓名：<c:out value="${user.contacts }"></c:out> </p>
										<p>身份证号码：<c:out value="${user.identity }"></c:out> </p>
									</div>
								</div>
								<div class="text-center">
									<button type="button" class="btn btn-default" onclick="location.href='${ctx}/u/account/user/edit${ext }'">修改资料</button>
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