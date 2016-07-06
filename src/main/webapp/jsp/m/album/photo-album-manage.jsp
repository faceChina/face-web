<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>相册专辑管理</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("photo");
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
						<c:set var="crumbs" value="albumn"/>
						<%@include file="../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class=""><a href="${ctx}/u/stuff/album/listAlbum${ext}">相册管理</a></li>
									<li class="active"><a href="${ctx}/u/stuff/album/listPhotoAlbum${ext}">相册专辑管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/album/listPhoto${ext}">所有图片</a></li>
								</ul>
							</div>
							<div class="content">
							<form action="${ctx }/u/stuff/album/listPhotoAlbum${ext}"  method="post" id="formPage" >
							
								<ul class="pager">
									<li class="previous">
										<a href="${ctx}/u/stuff/album/addPhotoAlbum${ext}" class="btn btn-default">添加相册专辑</a>
									</li>
								</ul>
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="15%">专辑名称</th>
											<th>专辑头图</th>
											<th width="25%">相册</th>
											<th width="15%">模板选择</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${empty pagination.datas }">
											<tr>
												<td colspan="5" class="text-center">暂无内容</td>
											</tr>
										</c:if>
										<c:forEach items="${pagination.datas}" var="data">
										<tr>
											<td>${data.name}</td>
											<td>
												<div class="table-img">
												<c:choose>
												<c:when test="${data.picPath == null }">
													<img src="${resourcePath }img/gw/article1.jpg" alt="" >
												</c:when>
												<c:otherwise>
													<img src="${picUrl}${data.picPath}" alt="" >
												</c:otherwise>
												</c:choose>
												</div>
											</td>
											<td>相册个数：${data.countAlbum } 个</td>
											<td>模板${data.articleTemplateType}</td>
											<td>
												<a href="${ctx }/u/stuff/album/editPhotoAlbum${ext}?id=${data.id}" class="btn-editor" onclick="">修改</a> | 
												<a href="javascript:void(0)" class="btn-del" onclick="del(this,'${data.id}')">删除 </a>
<!-- 												<a href="javascript:void(0)" class="btn-del" onclick="preview()">| 预览</a> -->
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
	<!-- body end -->

	<!-- footer -->
		<%@include file="../common/footer.jsp"%>
	<!-- footer end -->
	</body>


<script type="text/javascript">
//预览
function preview(){
// 	var val = 1;
// 	window.open("../../app/nullify/mobTemplate/article"+val+"/index320.html","模版"+val,"width=390,height=810");
}
/*删除*/
function del(el,id) {
	art.dialog.confirm('确认删除？', function() {
		$.post("${ctx}/u/stuff/album/delPhotoAlbum${ext}",{"id":id},function(data){
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

</script>
</html>