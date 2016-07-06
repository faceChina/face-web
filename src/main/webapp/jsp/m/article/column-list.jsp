<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章管理</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
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
									<li class=""><a href="${ctx}/u/stuff/article/listArticle${ext}">文章管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/article/listCategory${ext}">文章专题管理</a></li>
									<li class="active"><a href="${ctx}/u/stuff/article/listColumn${ext}">文章栏目管理</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="alert alert-warning" role="alert">
									<p>将文章专题组合，形成文章栏目达到更好的展示效果</p>
								</div>
								<ul class="pager">
									<li class="previous">
										<a href="${ctx}/u/stuff/article/addColumn${ext}" class="btn btn-default">添加栏目</a>
									</li>
								</ul>
								<form action="${ctx}/u/stuff/article/listColumn${ext}"  method="post" id="formPage">
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="15%">栏目名称</th>
											<th>分类图片</th>
											<th width="25%">专题数量</th>
											<th width="15%">模板选择</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas}" var="data">
											<c:choose>
												<c:when test="${empty pagination.datas}">
													<tr>
														<td colspan="5" class="text-center">暂无内容！</td>
													</tr>
												</c:when>
											</c:choose>	
										
										<tr>
											<td>${data.name}</td>
											<td>
												<div class="table-img">
												<c:choose>
												<c:when test="${data.picPath == null}">
													<img src="${resourcePath}img/gw/article1.jpg" alt="" >
												</c:when>
												<c:otherwise>
													<img src="${picUrl}${data.picPath}" alt="" >
												</c:otherwise>
												</c:choose>
												</div>
											</td>
											<td><p>${data.categoryCount }</td>
											<td>文章模板${data.articleTemplateType}</td>
											<td>
												<a href="javascript:void(0)" class="btn-editor" onclick="editColumn(this,'${data.id }')">修改</a> | 
												<a href="javascript:void(0)" class="btn-del" onclick="del(this,'${data.id}')">删除</a>
<%-- 												<a href="javascript:void(0)" class="btn-del" onclick="preview('${data.id}')"> | 预览</a> --%>
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
	
	<form action="" id="editColumn" method="get">
		<input type="hidden" id="editId" name="editId" value=""/>
	</form>
	
	
	<!-- body end -->

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<!-- footer end -->
	</body>


<script type="text/javascript">
//预览
function preview(id) {
	var url= '${ctx}/any/preview/column${ext}?id='+id;
    var iWidth=410;                          //弹出窗口的宽度;
    var iHeight=810;                         //弹出窗口的高度;
    previewToBigData(url,null,null,iWidth,iHeight);
}
/*删除*/
function del(el,id) {
	art.dialog.confirm('确认删除？', function() {
		$.post("${ctx}/u/stuff/article/delColumn${ext}",{"id":id},function(data){
			if(data=="SUCCESS"){
				$(el).parent("td").parent("tr").remove();
			}else{
				art.dialog.alert("删除失败！");
			}
		});
	}, function() {
		//art.dialog.tips('执行取消操作');
		return true;
	});
}

function editColumn(thiz,id){
	$("#editColumn").attr("action","${ctx}/u/stuff/article/editColumn${ext}");
	$("#editId").val(id);
	$("#editColumn").submit();
}

</script>
</html>