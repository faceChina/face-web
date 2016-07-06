<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>分公司管理</title>	
<!-- top -->
<!--top end -->
</head>

<body>

<!-- header -->
<%@ include file="../../common/base.jsp"%>
<%@ include file="../../common/header.jsp" %>
<!-- header end -->

<!-- body -->
<div class="container" id="j-content">
	<div class="row">
		<div class="col-md-2 ">
			<!--nav-left -->
			<%@include file="../../common/left.jsp"%>
			<!--nav-left end-->
		</div>
		<div class="col-md-10">
				<div class="row">
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#agency-shop">分店列表</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="alert alert-warning" role="alert">
								<p>对分店进行管理，包括状态、发货权限等</p>
							 </div>
							<form action="${ctx}/u/subbranch/list${ext}" id="formPage" method="post" class="form-horizontal">
							<fieldset>
								<div class="form-group">
									<label for="" class="col-md-1 control-label">姓名：</label>
									<div class="col-md-2">
										<input class="form-control" value="${userName }" name="userName" type="text">
									</div>
									<label for="" class="col-md-1 control-label ">电话：</label>
									<div class="col-md-3">
										<input class="form-control" value="${userBindingCell }" name="userBindingCell" type="text">
									</div>
									<label for="" class="col-md-2 control-label width110">发货权限：</label>
									<div class="col-md-2">
										<select name="delivery" class="form-control">
											<option value="-1" <c:if test="${delivery == -1 }">selected</c:if> >所有</option>
											<option value="1" <c:if test="${delivery == 1 }">selected</c:if> >有</option>
											<option value="0" <c:if test="${delivery == 0 }">selected</c:if> >无</option>
										</select>
									</div>
									<div class="col-md-1">
										<button type="submit" class="btn btn-default">查询</button>
									</div>
								</div>								
							</fieldset>
                           <table class="table table-bordered" id="template">
							<thead>
								<tr>
									<th width="15%">账号</th>
									<th width="15%">姓名</th>
									<th width="13%">电话</th>
									<th width="">分店名称</th>
									<th width="12%">发货权限</th>
									<th width="12%">操作</th>
								</tr>
							</thead>
							<tbody>
							<!-- 没有内容的 样式 -->
							<c:choose>
								<c:when test="${ empty pagination.datas  }">
									<tr>
										<td colspan="7" class="text-center">暂无内容</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${pagination.datas }" var="data">
										<tr>
											<td>${data.subAccount }</td>
											<td>${data.userName }</td>
											<td>${data.userBindingCell }</td>
											<td>${data.subbranchName }</td>
											<td>${data.delieveryStr }</td>
											<td>
											<a href="${ctx}/u/subbranch/detail${ext}?id=${data.id}" class="btn-editor">修改</a> |
											<a href="javascript:void(0)" onclick="unbinding(${data.id})" class="btn-editor">解绑</a>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<!-- 没有内容的 样式 end -->	
							</tbody>
						</table>
						<%@include file="../../common/page.jsp"%>
						</form>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<!-- body end -->

<!-- footer -->
<%@include file="../../common/footer.jsp"%>
<!-- footer end -->

<div id="codeJpg" style="display:none;">
	<p>每个员工店铺拥有唯一的店铺推广码，当客户从该员工二维码进入进行购买，则该员工享受利益分成</p>
	<br/>
	<p class="text-center"><img src="../img/login/code1.jpg" alt="" width="200"></p>
	<br/>
	<p class="text-center"><button class="btn btn-link">点击下载</button></p>
</div>

<script type="text/javascript">
tab("subbranch");
/*删除*/
function del(el) {
	art.dialog.confirm('确认删除？', function() {
		$(el).parent("td").parent("tr").remove();
	}, function() {
		//art.dialog.tips('执行取消操作');
		return true;
	});
}

function codeJpg(){
	art.dialog({
		title:"员工推广二维码",
		width:"400px",
		lock:true,
		content:document.getElementById("codeJpg")
	})
}

function unbinding(id) {
	art.dialog.confirm('确认解绑？', function() {
		window.location.href = "/u/subbranch/unbinding.htm?id="+id;
	}, function() {
		return true;
	});
}
</script>
</body>
</html>

