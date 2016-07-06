<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品列表</title>	
<!-- top -->
<%@ include file="../../../common/base.jsp"%>
<!--top end -->
<script type="text/javascript">
/** 排序 */
$(function(){
	tab("prolist");
	//绑定全选操作
	$('input[checkall="true"],#template tbody input[type="checkbox"]').click(function(){
		checkAll(this);
	})
});
/*删除*/
function del(el,id) {
	art.dialog.confirm('确认删除，删除之后无法还原？', function() {
			$.post('${ctx}/u/good/good/remove/'+id+'${ext}',function(data){
				var result = eval('(' + data + ')');
				if(result.success){
					$(el).parent("td").parent("tr").remove();
					location.reload(true); 
				}else{
					art.dialog.alert(result.info);
				}
			});
	}, function() {
		return true;
	});
}
//批量删除/下架函数
function delSoldoutBatch(){
	$("#template tbody").find("input[type='checkbox']:checked").each(function(){
		$(this).parents("tr").remove();
	})
}

//批量删除
function delBatch(){
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if(checkLen <= 0){
		art.dialog.alert("至少选择一项商品删除！");
		return;
	} 
	var ids = new Array();
	$("#template tbody").find("input[type='checkbox']:checked").each(function(index){
		ids[index] = $(this).val();
	});
	var idsJson=JSON.stringify(ids);
	art.dialog.confirm('确认删除，删除之后无法还原？', function() {
		$("#idsJson").val(idsJson);
		$(".j-loading").show();
	    $('form').attr("action",'${ctx}/u/good/good/removeAll${ext}').submit();
	}, function() {
		return true;
	});	
}

//批量下架
function downShelves(){
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if(checkLen <= 0){
	   art.dialog.alert("至少选择一项商品！");
	   return;
	}
	art.dialog.confirm('确认下架商品？', function() {
		var ids = new Array();
		$("#template tbody").find("input[type='checkbox']:checked").each(function(index){
			ids[index] = $(this).val();
		});
		var idsJson=JSON.stringify(ids);
		$.post('${ctx}/u/good/good/downShelves${ext}',{"idsJson":idsJson},function(data){
			var result = eval('(' + data + ')');
			if(result.success){
				location.reload(true); 
			}else{
				art.dialog.alert(result.info);
			}
		});
	}, function() {
		return true;
	});	
}
//批量上架
function upShelves(){
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if(checkLen <= 0){
		   art.dialog.alert("至少选择一项商品！");
		   return;
	}
	art.dialog.confirm('确认上架商品？', function() {
		var ids = new Array();
		$("#template tbody").find("input[type='checkbox']:checked").each(function(index){
			ids[index] = $(this).val();
		});
		var idsJson=JSON.stringify(ids);
		$.post('${ctx}/u/good/good/upShelves${ext}',{"idsJson":idsJson},function(data){
			var result = eval('(' + data + ')');
			if(result.success){
				location.reload(true); 
			}else{
				art.dialog.alert(result.info);
			}
		});
	}, function() {
		return true;
	});	
}
//全选
function checkAll(self){
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	var totalLen = $("#template tbody").find("input[type='checkbox']").length;
	var flag = $(self).attr('checkall');
	console.log(!flag)
	if(checkLen == totalLen){
		if(!flag){
			$('input[checkall="true"]').attr('checked','checked');
			return ;
		} 
		$("#template tbody").find("input[type='checkbox']").removeAttr('checked');

		
	}else{
		if(!flag){
			$('input[checkall="true"]').removeAttr('checked');
			return ;
		}
		$("#template tbody").find("input[type='checkbox']").attr('checked','checked');
	}
}

/*查询*/
function queryByType(thiz){
	$('#shopTypeId').val($(thiz).val());
	$(".j-loading").show();
	$('form').submit();
}

</script>
</head>
	<body>
		<!-- header -->
		<%@include file="../../../common/header.jsp"%>
		<!-- header end -->
		<!-- body -->
		<div class="container" id="j-content">
			<div class="row">
				<div class="col-md-2 ">
					<!--nav-left -->
					<%@include file="../../../common/left.jsp"%>
					<!--nav-left end-->
				</div>
				<div class="col-md-10">
						<div class="row">
							<c:set var="crumbs" value="goodlist"/>
						<%@include file="../../../common/crumbs.jsp"%>
						</div>
						<div class="row">
						<form id="formPage" name="from" action="${ctx}/u/good/good/list${ext}" method="post">
						<input type="hidden" name="shopTypeId" id="shopTypeId" <c:if test="${not empty goodVo.shopTypeId }">value="${goodVo.shopTypeId}"</c:if>/>
						<input type="hidden" name="idsJson" id="idsJson"/>
						<input type="hidden" name="status" id="status" value="${goodVo.status}"/>
							<div class="box">
								<div class="title">
									<ul class="nav nav-tabs">
										<li <c:if test="${goodVo.status==1}">class="active"</c:if>><a href="${ctx}/u/good/good/list${ext}">出售中的商品</a></li>
										<li <c:if test="${goodVo.status==3}">class="active"</c:if>><a href="${ctx}/u/good/good/list${ext}?status=3">仓库中的商品</a></li>
									</ul>
								</div>
								<div class="content">
									<ul class="pager">
										<li class="previous">
											<div class="col-md-2" style="margin-right:10px;">
												<select class="form-control" onchange="javascript:queryByType(this);">
													<option value="">全部</option>
													<c:forEach items="${shopTypeList}" var="shopType">
														<option value="${shopType.id}" <c:if test="${goodVo.shopTypeId == shopType.id}">selected="selected"</c:if>>${shopType.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-3"> <input class="form-control" name="name" type="text" value="${goodVo.name}" placeholder="请输入商品名称"></div>
											<div class="col-md-1">
												<button type="submit" class="btn btn-default">查询</button>
											</div>
										</li>
									</ul>
									<ul class="pager">
										<li class="previous">
											<div  class="col-md-1"><label style="line-height:32px;"><input type="checkbox" value="" checkall="true"> 全选</label></div>
											<div  class="col-md-1"><button type="button" class="btn btn-default" id="update" onclick="delBatch()">删除</button></div>
											<c:choose>
												<c:when test="${goodVo.status==1}">
													<div  class="col-md-1"><button type="button" class="btn btn-default" id="update" onclick="downShelves()">下架</button></div>
												</c:when>
												<c:when test="${goodVo.status==3}">
													<div  class="col-md-1"><button type="button" class="btn btn-default" id="update" onclick="upShelves()">上架</button></div>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>
											
										</li>
										<c:if test="${goodVo.status==1}">
											<li class="next">
												<a class="btn btn-default" href="${ctx}/u/good/good/listClassification.htm?pid=0&level=0">
													<i class="glyphicon glyphicon-plus-sign"></i> 新增
												</a>
											</li>
										</c:if>
									</ul>
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="6%">选择</th>
											<th width="35%">商品名称</th>
											<th width="20%">单价</th>
											<th width="">商品数量</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${not empty pagination.datas }">
												  <c:forEach items="${pagination.datas }" var="data">
												    <tr>
														<td><input type="checkbox" value="${data.id}"></td>
														<td>
															<dl class="m-products-list">
																<dt class="m-products-img"><a><img src="${picUrl}${data.picUrl}" alt="" style="width:60px;height:60px; background:#c00;'"></a></dt>
																<dd class="m-products-text">${data.name}</dd>
															</dl>
														</td>
														<td>¥<fmt:formatNumber pattern="0.00" value="${data.salesPrice/100}"/></td>
														<td>${data.inventory}</td>
														<td>
															<a class="btn btn-default btn-editor" href="${ctx}/u/good/good/editnew/${data.id}${ext}?goodName=${goodVo.name}&shopTypeId=${goodVo.shopTypeId}&curPage=${pagination.curPage}">编辑</a>
															<button type="button" class="btn btn-default btn-del" onclick="del(this,'${data.id}')">删除</button>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<!-- 没有内容的 样式 -->
													<tr>
														<td colspan="7" class="text-center">暂无内容</td>
													</tr>
												<!-- 没有内容的 样式 end -->	
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								</div>
								<%@include file="../../../common/page.jsp"%>
							</form>
							</div>
					
						</div>
				</div>
			</div>
		</div>
<!-- body end -->
<!-- footer -->
<%@include file="../../../common/footer.jsp"%>
<!-- footer end -->
</body>
</html>

