<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送方式设置</title>
<%@ include file="../../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("tempmanage");
	});
</script>
</head>
<body>
	<%@ include file="../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="logistics"/>
					<%@include file="../../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#" data-toggle="tab">快递配送设置</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="alert alert-warning" role="alert">
								<p>运送方式：除指定地区外，其余地区的运费采用"全国默认地区运费"</p>
							</div>
							<ul class="pager">
								<li class="previous">
									<c:if test="${dto == null }">
										<a href="${ctx }/u/shop/logistics/dispatchAdd${ext}"class="btn btn-default">新增运费模板</a>
									</c:if>
									<button type="button" onclick="location.href='${ctx }/u/shop/logistics/index${ext}'" class="btn btn-default">返回</button>
								</li>
							</ul>
							<ul class="pager">
								<c:if test="${dto != null }">
									<li class="text-left">
										<div class="col-md-5">名称：${dto.name }</div>
									</li>
									<li class="next">
										<a href="${ctx }/u/shop/logistics/dispatchEdit${ext}" class="btn btn-default">修改</a>
<%-- 										<button onclick="location.href='${ctx}/u/shop/logistics/dispatchDel${ext }?id=${dto.id }'" type="button" class="btn btn-default">删除</button> --%>
									</li>
								</c:if>
							</ul>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>配送方式</th>
										<th>运送到</th>
										<th>首件</th>
										<th>运费（元）</th>
										<th>续件</th>
										<th>运费</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${dto.itemVoList }" var="item">
										<tr>
											<td>快递</td>
											<td>${item.destination }</td>
											<td>${item.startStandard }</td>
											<td><fmt:formatNumber value="${item.startPostage / 100 }" pattern="0.00" /></td>
											<td>${item.addStandard }</td>
											<td><fmt:formatNumber value="${item.addPostage / 100 }" pattern="0.00" /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty dto.itemVoList }">
										<tr><td colspan="6" class="text-center">暂无内容</td>
									</c:if>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../../common/footer.jsp"%>
</body>
</html>


