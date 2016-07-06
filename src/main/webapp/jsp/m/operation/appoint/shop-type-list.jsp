<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品分类</title>
<!-- top -->
<%@ include file="../../common/base.jsp"%>	
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("appointment-goods");
});
	</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<form id="formPage">
			<div class="col-md-10">
						<c:set var="crumbs" value="appointGoodType"/>
						<%@include file="../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">商品分类</a></li>
								</ul>
							</div>
							<div class="content">
								<ul class="pager">
									<li class="previous">
										<a class="btn btn-default" href="/u/appoint/good-type-add.htm">添加分类</a>
									</li>
								</ul>
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="25%">分类名称</th>
											<th width="">模块图标</th>
											<th width="15%">排序</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
											<c:when test="${not empty pagination.datas }">
												<c:forEach items="${pagination.datas }" var="shopType" varStatus="status">
													<tr>
														<td><c:if test="${status.last }">
														<input type="hidden" id="maxSort" value="${shopType.sort }"/></c:if>
														<input type="hidden" move-row="moveId" name="shopTypes[${status.index}].id" value="${shopType.id}"/>
														<input type="hidden" move-row="moveSort" name="shopTypes[${status.index}].sort" value="${shopType.sort}"/>
														<input type="hidden" name="shopTypes[${status.index}].imgPath" value="$shopType.imgPath}"/>
														${shopType.name}
														</td>
														<td>
															<div plugin="scale" class="table-img">
																<c:if test="${fn:contains(shopType.imgPath, 'resource')}">
																	<img src="${shopType.imgPath}" alt="" width="35" height="35" style="display: block; margin: 32.5px auto 0px;">
																</c:if>
																<c:if test="${fn:contains(shopType.imgPath, 'resource')==false}">
																	<img src="${picUrl }${shopType.imgPath}" alt="" width="35" height="35" style="display: block; margin: 32.5px auto 0px;">
																</c:if>																
															</div>
														</td>
														<td>
															<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath}img/up.jpg" alt=""></a> 
															<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath}img/down.jpg" alt=""></a>
														<td>
															<a href="javascript:;" onclick="jumpPage('/u/appoint/good-type-edit.htm','${shopType.id }')" class="btn-editor" >编辑 | </a>
															<a href="javascript:void(0)" class="btn-del" onclick="del(this, '${shopType.id}')">删除</a>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="7" class="text-center">暂无内容</td>
												</tr> 
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<%@include file="../../common/page.jsp"%>
							</div>
						</div>
					</div>
					
			</div>
			</form>
		</div>
	</div>
	
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	<script type="text/javascript" src="${resourcePath}plugins/updown.js"></script>
	<script type="text/javascript">
	$(function(){
		$.puburl.setting.url="${ctx}/u/good/shopType/sort${ext}";
	});
	
	/*删除*/
	 function del(el,index) {
		var tr = $(el).parent("td").parent("tr");
		art.dialog.confirm('确认删除？', function() {
			var shopTypeId = index;
			if(shopTypeId){
			    $.ajax({
			          url: "${ctx}/u/good/shopType/delete/"+shopTypeId+"${ext}",   
			          type: "post",
			          data: {"shopTypeId":shopTypeId},
			          dataType: "text",
			          contentType: "application/x-www-form-urlencoded; charset=utf-8",
			          success: function (id) {
							if(null == id || '' == id){
								alertMsg("服务器繁忙！");
							}else{
								location.reload();
							}
			          },
			          error: function () {
			        	  alertMsg("服务器繁忙！");
			          }
			      });
			}else{
				tr.remove();
			}
		}, function() {
			return true;
		});
	} 
</script>
	</body>
</html>