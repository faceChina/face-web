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
		$.metadata.setType("attr", "validate");
	});
	function addOrEdit(id, name, distributionRange) {
		$('#id').val(id);
		$('#name').val(name);
		$('#distributionRange').val(distributionRange);
		art.dialog({
			lock : true,
			width : '600px',
			title : "配送范围",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-shopship"),
			button : [ {
				name : '保存',
				callback : function() {
					var formvali = $("#j-form").validate({});
					if (formvali.form()) {
						$('#j-form').submit();
					} else {
						return false;
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
	//删除配送
	function del(el, id) {
		art.dialog.confirm('确认删除？', function() {
			$.post("${ctx }/u/shop/logistics/delps${ext}", {"id" : id}, function(jsonData) {
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
								<li class="active"><a href="#" data-toggle="tab">店铺配送管理</a></li>
							</ul>
						</div>
						<form action="${ctx }/u/shop/logistics/pslist${ext}" method="post"
							id="formPage">
							<div class="content">
								<div class="alert alert-warning" role="alert">
									<p>设置店铺配送后，买家可以选择附近的店铺进行下单</p>
								</div>
								<ul class="pager">
									<li class="previous">
										<button type="button" onclick="addOrEdit()"
											class="btn btn-default add-shopship">新增配送范围</button>
										<button type="button" onclick="location.href='${ctx }/u/shop/logistics/index${ext}'"
											class="btn btn-default">返回</button>
									</li>
								</ul>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width=20%>名称</th>
											<th>配送范围</th>
											<th width=10%>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas }" var="data">
											<tr>
												<td><c:out value="${data.name }" /></td>
												<td><c:out value="${data.distributionRange }" /></td>
												<td><a href="javascript:addOrEdit('${data.id}', '${data.name }', '${data.distributionRange }')">修改 | </a> <a
													href="#"
													class="btn-del" onclick="del(this, '${data.id}')">删除</a></td>
											</tr>
										</c:forEach>
										<c:if test="${empty pagination.datas }">
											<tr>
												<td colspan="3">还没添加信息哦！</td>
											</tr>
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
	<div id="j-shopship" style="display: none;">
		<form action="${ctx }/u/shop/logistics/editps${ext}" id="j-form" method="post"
			class="form-horizontal">
			<input type="hidden" name="id" id="id" />
			<div class="form-group">
				<label for="inputText" class="col-md-2 control-label">名称：</label>
				<div class="col-md-10">
					<input type="text" maxlength="20"
						validate="{required:true, messages:{required:'请输入名称'}}"
						class="form-control" placeholder="" id="name" name="name">
				</div>
			</div>
			<div class="form-group">
				<label for="inputText" class="col-md-2 control-label">配送范围：</label>
				<div class="col-md-10">
					<textarea maxlength="120"
						validate="{required:true, messages:{required:'请输入配送范围'}}"
						name="distributionRange" id="distributionRange"
						class="form-control" rows="5"></textarea>
				</div>
			</div>
		</form>
	</div>
</body>
</html>