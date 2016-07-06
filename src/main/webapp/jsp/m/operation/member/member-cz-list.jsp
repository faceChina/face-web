<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>充值记录</title>
<%@ include file="../../common/base.jsp"%>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<script type="text/javascript">
		$(function() {
			tab("member-manage");

		});
	</script>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="member-manage" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">会员管理</a></li>
							</ul>
						</div>
						<form id="formPage" method="post">
							<div class="content">
								<ul class="pager" style="text-align: left; line-height: 31px;">
									<li class="desc"><em>${cardNo }</em> 充值记录</li>
								</ul>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>充值时间</th>
											<th width="30%">充值金额</th>
											<th width="30%">充值类型</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas }" var="data">
											<tr>
												<td><fmt:formatDate value="${data.transTime }" pattern="yyyy-MM-dd"/> </td>
												<td><fmt:formatNumber value="${data.transPrice / 100 }" pattern="0.00" /></td>
												<td>${data.goodInfo }</td>
											</tr>
										</c:forEach>
										<c:if test="${empty pagination.datas }">
											<tr><td colspan="3" class="text-center">暂无内容</td>
										</c:if>
									</tbody>
								</table>
								<%@include file="../../common/page.jsp"%>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>