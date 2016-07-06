<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章管理</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
<script type="text/javascript" src="${resourcePath}js/allSelect.js"></script>


<script type="text/javascript">
$(function(){
	tab("article");
});
</script>
<!--top end -->
</head>
<body>
	<!-- header -->
		<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2">
				<!--nav-left -->
					<%@include file="../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="article"/>
						<%@include file="../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="${ctx}/u/stuff/article/listArticle${ext}">文章管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/article/listCategory${ext}">文章专题管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/article/listColumn${ext}">文章栏目管理</a></li>
								</ul>
							</div>
							<div class="content">
							<form  action="${ctx}/u/stuff/article/listArticle${ext}"  method="post" id="formPage">
								<ul class="pager">
									<li class="previous">
										<button type="button" class="btn btn-default" onclick="location.href='${ctx}/u/stuff/article/addArticle${ext}'">添加文章</button>
										<button type="button" class="btn btn-default" id="update" onclick="delBatch()">批量删除</button>
									</li>
									<li class="next navbar-form pull-right" >
										
											<div class="form-group">
												<input type="text" class="form-control" value="${ soso}" name="soso" placeholder="请输入文章标题">
											</div>
											<button type="submit"  class="btn btn-default">搜索</button>
										
									</li>
								</ul>
								<table class="table table-bordered" id="template"  plugin="allSelect">
									<thead>
										<tr>
											<th width="10%">
												<label><input type="checkbox" value="" pluginHandles="handles"> 全选</label>
											</th>
											<th>标题</th>
											<th width="20%">添加日期</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody pluginContent="content">
									
										<c:forEach items="${pagination.datas}" var="data">
											<c:choose>
												<c:when test="${empty pagination.datas}">
													<tr>
														<td colspan="5" class="text-center">暂无内容！</td>
													</tr>
												</c:when>
											</c:choose>	
										<tr>
											<td><input type="checkbox" value="${data.id}"></td>
											<c:choose>
												<c:when test="${fn:length(data.title)<=20}">
													<td>${data.title }</td>
												</c:when>
												<c:otherwise>
													<td>${fn:substring(data.title,0,20)}...</td>
												</c:otherwise>
											</c:choose>
											<td><fmt:formatDate value="${data.createTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>
												<a href="javascript:void(0)" class="btn-editor" onclick="editArticle(this,'${data.id}')">修改 | </a>
												<a href="javascript:void(0)" class="btn-del" onclick="delSingle(this,'${data.id}')">删除</a>
											</td>
										</tr>
										</c:forEach>
										
									</tbody>
								</table>
									<%@include file="../common/page.jsp"%>
									
							</form>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	
	<form action="" id="editArticle" method="get">
		<input type="hidden" id="editId" name="editId" value=""/>
	</form>
	<input type="hidden" id="delId" name="delId" value=""/>
	<!-- footer -->
		<%@include file="../common/footer.jsp"%>
	<!-- footer end -->
	</body>
	
<script type="text/javascript">


function editArticle(thiz,id){
	
	$("#editArticle").attr("action","${ctx}/u/stuff/article/editArticle${ext}");
	$("#editId").val(id);
	$("#editArticle").submit();
	
}

//批量删除
function delBatch(){
	var len = $("#template tbody").find("input[type='checkbox']:checked").length;
	if(len >= 1){
		art.dialog.confirm("确定删除？",function(){
			var categoryStatusArr=[]
			$("#template tbody").find("input[type='checkbox']:checked").each(function(){
				categoryStatusArr.push($(this).val());
				$(this).parents("tr").remove();
			})
			$("#delId").val(categoryStatusArr);
			var delId = $("#delId").val();
			$.post("${ctx}/u/stuff/article/delAllArticle${ext}",{"delId":delId},function(data){
				if(data != "SUCCESS"){
					art.dialog.alert("删除失败！");
				}
			});
		},function(){
			return true;
		});
	}
}

//单个删除
function delSingle(thiz,id){
	art.dialog.confirm("确定删除？",function(){
		
		$.post("${ctx}/u/stuff/article/delArticle${ext}",{"id":id},function(data){
			if(data=="SUCCESS"){
				$(thiz).closest("tr").remove();
			}else{
				art.dialog.alert("删除失败！");
			}
		});
		
	},function(){
		return true;
	});
}
</script>
</html>