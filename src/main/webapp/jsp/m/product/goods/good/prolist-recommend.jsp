<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品列表</title>	
<!-- top -->
<%@ include file="../../../common/base.jsp"%>
<!--top end -->
<script type="text/javascript" src="${resourcePath}plugins/updown.js"></script>
<script type="text/javascript">
/* 推荐至首页 */
function recomDraw(self){
	var flag = $(self).hasClass('recom-nodraw');
	if(flag){
		$(self).removeClass('recom-nodraw');
	}else{
		$(self).addClass('recom-nodraw');
	}
}

/* 推荐至首页 */
function recomDraw(self,id){
	$.post('${ctx}/u/good/good/spreadIndex${ext}',{"goodId":id},function(data){
		var result = eval('(' + data + ')');
		if(result.success){
			var flag = $(self).hasClass('recom-nodraw');
			if(flag){
				$(self).removeClass('recom-nodraw');
			}else{
				$(self).addClass('recom-nodraw');
			}
		}else{
			art.dialog.alert(result.info);
		}
	});
}

/*查询*/
function queryByType(thiz){
	$('#shopTypeId').val($(thiz).val());
	$(".j-loading").show();
	$('form').submit();
}
$(function(){
	tab("recommend");
	//绑定推荐操作
	$('.recom-draw').click(function(){
		recomDraw(this,$(this).attr('value'));
	});
	$.puburl.setting.url="${ctx}/u/good/good/sort${ext}";
});	
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
						<c:set var="crumbs" value="goodtj"/>
						<%@include file="../../../common/crumbs.jsp"%>
						</div>
						<form id="formPage" name="from" action="${ctx}/u/good/good/listRecommend${ext}" method="get">
						<div class="row">
							<div class="box">
								<div class="title">
									<ul class="nav nav-tabs">
										<li class="active"><a href="javascript:void(-1)">商品管理推荐</a></li>
									</ul>
								</div>
								<div class="content">

									<ul class="pager">
										<li class="previous">
											<div class="col-md-2" style="margin-right:10px;">
												<input type="hidden" name="shopTypeId" id="shopTypeId" <c:if test="${not empty goodVo.shopTypeId }">value="${goodVo.shopTypeId}"</c:if>/>
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
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="35%">商品名称</th>
											<th width="">排序</th>
											<th width="30%">推荐至首页</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
											<c:when test="${not empty pagination.datas }">
												  <c:forEach items="${pagination.datas }" var="data" varStatus="status">
													<tr>
														<td>
															<dl class="m-products-list">
																<dt class="m-products-img"><a href="#"><img src="${picUrl}${data.picUrl}" alt="" style="width:60px;height:60px; background:#c00;'"></a></dt>
																<dd class="m-products-text"><a href="#">${data.name}</a></dd>
															</dl>
														</td>
														<td>
															<input type="hidden" move-row="moveId" name="goods[${status.index}].id" value="${data.id}"/>
															<input type="hidden" move-row="moveSort" name="goods[${status.index}].sort" value="${data.sort}"/>
															<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath}img/up.jpg" alt=""></a> 
															<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath}img/down.jpg" alt=""></a>
														</td>
														<td>
															<c:choose>
																<c:when test="${not empty data.isSpreadIndex && data.isSpreadIndex!=1}">
																	<i class="recom-draw recom-nodraw" value="${data.id}"></i>
																</c:when>
																<c:otherwise>
																	<i class="recom-draw" value="${data.id}"></i>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<!-- 没有内容的 样式 -->
													<tr>
														<td colspan="3" class="text-center">暂无内容</td>
													</tr>
												<!-- 没有内容的 样式 end -->	
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								</div>
							</div>
							<%@include file="../../../common/page.jsp"%>
						</div>
						</form>
				</div>
			</div>
		</div>
		<!-- body end -->

		<!-- footer -->
		<%@include file="../../../common/footer.jsp"%>
		<!-- footer end -->
		</body>
	</html>