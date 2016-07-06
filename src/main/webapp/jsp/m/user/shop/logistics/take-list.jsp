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
	//删除配送
	function del(el, id) {
		art.dialog.confirm('确认删除？', function() {
			$.post("${ctx }/u/shop/logistics/ztdel${ext}", {
				"id" : id
			}, function(jsonData) {
				var data = JSON.parse(jsonData);
				if (data.success) {
					$(el).closest("tr").remove();
				} else {
					art.dialog.alert(data.info);
				}
			});
		}, function() {
			return true;
		});
	}
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
								<li class="active"><a href="#dispatching-take-set"
									data-toggle="tab">门店自取管理</a></li>
							</ul>
						</div>
						<form action="${ctx }/u/shop/logistics/ztlist${ext}" method="post"
							id="formPage">
							<div class="content">
								<div class="alert alert-warning" role="alert">
									<p>设置门店自取后，买家可以就近选择预设的自提点，下单后请尽快将商品配送至指定自提点</p>
								</div>
								<ul class="pager">
									<li class="previous"><a
										href="${ctx }/u/shop/logistics/ztedit${ext}"
										class="btn btn-default">新增自提点</a>
										<button type="button" onclick="location.href='${ctx }/u/shop/logistics/index${ext}'"
											class="btn btn-default">返回</button></li>
								</ul>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>自提点名称</th>
											<th>省份</th>
											<th>城市</th>
											<th>地区</th>
											<th>地址</th>
											<th>联系电话</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas }" var="data">
											<tr>
												<td><c:out value="${data.name }" /> </td>
												<td><c:out value="${data.province }" /> </td>
												<td><c:out value="${data.city }" /></td>
												<td><c:out value="${data.county }" /></td>
												<td><c:out value="${data.address }" /></td>
												<td>${data.phone }</td>
												<td><a href="${ctx }/u/shop/logistics/ztedit${ext}?id=${data.id}"
													class="btn-editor">修改 | </a> <a href="javascript:void(0)"
													onclick="del(this, '${data.id }')" class="btn-del">删除</a></td>
											</tr>
										</c:forEach>
										<c:if test="${empty pagination.datas }">
											<tr><td colspan="7">还没添加信息哦！</td></tr>
										</c:if>
									</tbody>
								</table>
								<%@include file="../../../common/page.jsp"%>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../../common/footer.jsp"%>
</body>
</html>