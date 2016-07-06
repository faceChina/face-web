<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品列表</title>	
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">

/*删除*/
function del(el,id) {
	art.dialog.confirm('确认删除？', function() {
		$.post('/u/appoint/good-delete.htm',{'id':id},function(){
			location.reload();
		})
	}, function() {
		//art.dialog.tips('执行取消操作');
		return true;
	});
}

/*编辑*/
function editor(el) {
	var tr = $(el).parent("td").parent("tr");

	tr.find("input").css({
		border : "1px solid #ccc"
	}).attr("disabled", false);

	tr.find("img").css({
		display : "none"
	});

	tr.find(".uploadImg").css({
		display : "block"
	});

	$(el).css({
		display : "none"
	})
	tr.find(".btn-save").css({
		display : "inline-block"
	});
}

/*保存*/
function save(el) {
	var tr = $(el).parent("td").parent("tr");

	tr.find("input").css({
		border : "none"
	}).attr("disabled", true);

	tr.find("img").css({
		display : "inline-block"
	});

	tr.find(".uploadImg").css({
		display : "none"
	});

	$(el).css({
		display : "none"
	})
	tr.find(".btn-editor").css({
		display : "inline-block"
	});
}

/* 推荐至首页 */
function recomDraw(self){

	var flag = $(self).hasClass('recom-nodraw');
	if(flag){
		$(self).removeClass('recom-nodraw');
	}else{
		$(self).addClass('recom-nodraw');
	}
}
/*上移*/
function up(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var index = tr.index();
	if (index != 0) {
		tr.insertBefore(tr.prev());
	} else {
		return false;
	}
}

/*下移*/
function down(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var len = tbody.find("tr").length;
	var index = tr.index();
	if (index != len - 1) {
		tr.insertAfter(tr.next());
	} else {
		return false;
	}
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
	if(checkLen <= 0) return;
	art.dialog.confirm('确认删除？', function() {
		var arr=[];
		$("#template tbody").find("input[type='checkbox']:checked").each(function(){
			arr.push($(this).data('id').toString())
		});
		if(arr.length>0){
			$.post('/u/appoint/good-batch-delete.htm',{'ids':JSON.stringify(arr)},function(){
				location.reload();
			})
		}
	}, function() {
		//art.dialog.tips('执行取消操作');
		return true;
	});	
}
//批量下架
function soldoutBatch(){
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if(checkLen <= 0) return;
	art.dialog.confirm('确认下架商品？', function() {
		delSoldoutBatch();
	}, function() {
		//art.dialog.tips('执行取消操作');
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
$(function(){
	tab("appointment-list");
	$("#shopTypeId").val('${goodVo.shopTypeId}');
	//绑定推荐操作
	$('.recom-draw').click(function(){
		recomDraw(this);
	});
	//绑定全选操作
	$('input[checkall="true"],#template tbody input[type="checkbox"]').click(function(){
		checkAll(this);
	})
});	
	</script>
	</head>
	<body>

		<%@ include file="../../common/header.jsp"%>

		<!-- body -->
		<div class="container" id="j-content">
			<div class="row">
				<div class="col-md-2 ">
					<%@include file="../../common/left.jsp"%>
				</div>
				<div class="col-md-10">
						<div class="row">
							<c:set var="crumbs" value="appointGoodList"/>
							<%@include file="../../common/crumbs.jsp"%>
						</div>
						<div class="row">
							<div class="box">
								<div class="title">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#list">商品列表</a></li>
									</ul>
								</div>
								<div class="content">
									<form id="formPage">
									<ul class="pager">
										<li class="previous">
											<div class="col-md-2" style="margin-right:10px;">
												<select class="form-control" onchange="form.submit()" name="shopTypeId" id="shopTypeId">
													<option value="0">全部</option>
													<c:forEach items="${shopTypeList }" var="shopType">
													<option value="${shopType.id }">${shopType.name }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-3"> <input class="form-control" type="text" name="name" value="${goodVo.name }" placeholder=""></div>
											<div class="col-md-1">
												<button type="submit" class="btn btn-default">查询</button>
											</div>
										</li>
									</ul>
									<ul class="pager">
										<li class="previous">
											<div  class="col-md-1"><label style="line-height:32px;"><input type="checkbox" value="" checkall="true"> 全选</label></div>
											<div  class="col-md-1"><button type="button" class="btn btn-default" id="update" onclick="delBatch()">删除</button></div>
										</li>
										<li class="next">
											<a class="btn btn-default" href="/u/appoint/good-add.htm">
												<i class="glyphicon glyphicon-plus-sign"></i> 新增
											</a>
										</li>
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
									<c:if test="${empty pagination.datas }">
										<tr>
											<td colspan="7" class="text-center">暂无内容</td>
										</tr>
									</c:if>
									<c:forEach items="${pagination.datas }" var="good">
										<tr>
											<td><input type="checkbox" data-id="${good.id }"></td>
											<td>
												<dl class="m-products-list">
													<dt class="m-products-img"><a><img src="${picUrl }${good.picUrl }" alt=""></a></dt>
													<dd class="m-products-text"><a>${good.name }</a></dd>
												</dl>
											</td>
											<td><fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></td>
											<td>${good.inventory }</td>
											<td>
												<a class="btn btn-default btn-editor" href="/u/appoint/good-edit.htm?id=${good.id }&shopTypeId=${goodVo.shopTypeId}&goodName=${goodVo.name}&curPage=${pagination.curPage}">编辑</a>
												<button type="button" class="btn btn-default btn-del" onclick="del(this,'${good.id}')">删除</button>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<%@include file="../../common/page.jsp" %>
								</form>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
		<!-- body end -->
		<%@include file="../../common/footer.jsp"%>
		</body>
	</html>

