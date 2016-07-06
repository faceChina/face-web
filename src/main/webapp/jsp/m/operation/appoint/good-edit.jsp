<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品编辑</title>	
<!-- top -->
<%@ include file="../../common/base.jsp"%>
<%@ include file="../../common/validate.jsp"%>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	$.metadata.setType('attr','validate');
	tab("appointment-list");
	$("img[name='goodFile']").each(function(){
		imgUpload($(this));
	});
})
function canel(){
	art.dialog.confirm('确认取消正在编辑的商品？', function() {
		history.go(-1);
	}, function() {
		return true;
	});
}
function saves(){
	var formvali = $("#signupForm").validate({
		ignore: ".ignore"
	});
	console.log("sdfgssdfg");
	if(!formvali.form()){
		return;
	}
	$(".j-loading").show();
	$('#submitButton').attr('disabled','disabled');
	$("#goodContent").val(getContent());
	$("#signupForm").submit();
}
</script>
<script type="text/javascript" src="${resourcePath}js/addNewShop.js"></script>
<style>
.error{color:red;}
#template input{
	width: 100px;
}
</style>

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
			<div class="col-md-10">
					<c:set var="crumbs" value="ordershop"/>
							<%@include file="../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">发布商品</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
							<form id="signupForm" <c:if test="${empty good.id }">action="/u/appoint/good-add.htm"</c:if><c:if test="${not empty good.id }">action="/u/appoint/good-edit.htm"</c:if> method="post">
								<input type="hidden" name="serviceId" value="2">
								<input type="hidden" name="id" value="${good.id}"/>
								<input type="hidden" name="shopTypeId" value="${shopTypeId }"/>
								<input type="hidden" name="goodName" value="${goodName }"/>
								<input type="hidden" name="curPage" value="${curPage }"/>
								<c:forEach items="${skuList }" var="goodSku" varStatus="status">
									<input type="hidden" name="skuList[${status.index }].id" value="${goodSku.id }"/>
								</c:forEach>
								<table class="table-thleft  table-lg">
									<tbody>
										<tr>
											<th><b class="clr-attention">*</b> 商品标题：</th>
											<td>
												<input type="text" name="name"  maxlength="85" value="${good.name}" class="form-control input-short-10" validate="{required:true, messages:{required:'商品标题不能为空'}}"/>
											</td>
										</tr>
										<tr>
											<th>商品分类：</th>
											<td>
												<c:choose>
													<c:when test="${not empty shopTypeList}">
														<table>
															<c:forEach items="${shopTypeList}" var="shopType" varStatus="status">
																<c:if test="${0==status.index%4}"> 
																	<tr>
																</c:if>
																<td><input type="checkbox" name="shoptype[${status.index}].shopTypeId"<c:if test="${not empty good.id&&shopType.goodId == good.id}">checked="checked"</c:if> value="${shopType.id}"> ${shopType.name} &nbsp;</td>
																<c:if test="${3==status.index%4}">	
																	</tr>
															 	</c:if>    
															</c:forEach>
														</table>
													</c:when>
													<c:otherwise>
														<a href="${ctx}/u/appoint/good-type-add${ext}" class="btn btn-default btn-lg">您还没有创建分类，现在去添加!</a>
													</c:otherwise>
												</c:choose>	
											</td>
										</tr>
										<tr>
											<th><b class="clr-attention">*</b> 价格(元)：</th>
											<td><input type="text" id="j-price" name="salesPriceYuan" value="<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}" />" 
													class="form-control input-short-5" maxlength="15" 
													validate="{required:true,moneyNotOne:true,messages:{required:'价格不能为空'}}"> 元
											</td>
										</tr>
										<tr>
											<th><b class="clr-attention">*</b> 库存(件)：</th>
											<td>
											<input type="text" value="${good.inventory}" name="inventory"  class="form-control input-short-4" maxlength="8" validate="{required:true,range:[1,99999],positiveInteger:true}" /> 件
											</td>
										</tr>
										<%@include file="../../product/goods/good/temp/picture.jsp"%>
										<%@include file="../../product/goods/good/temp/detail.jsp"%>
									</tbody>
								</table>
								<div class="text-center">
									<button id="submitButton" type="button" class="btn btn-default btn-lg" onclick="saves()">保存</button>
									<button type="button" class="btn btn-default btn-lg" onclick="canel()">取消</button>
								</div>
								</form>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	</body>
</html>

