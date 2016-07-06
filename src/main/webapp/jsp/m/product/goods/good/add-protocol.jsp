<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品分类-新品上市</title>	
<!-- top -->
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("prolist");
	$("img[name='goodFile']").each(function(){
		imgUpload($(this));
	});
	$.metadata.setType('attr','validate');
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
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">新品上市</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
							<form id="signupForm" <c:if test="${empty good.id }">action="/u/good/good/add/${classification.id }.htm"</c:if><c:if test="${not empty good.id }">action="/u/good/good/edit/${good.id }.htm"</c:if>method="post">
								<input type="hidden" id="classificationId" name="classificationId" value="${good.classificationId}" />
								<input type="hidden" name="id" name="${good.id }">
								<input type="hidden" name="shopTypeId" value="${shopTypeId }"/>
								<input type="hidden" name="goodName" value="${goodName }"/>
								<input type="hidden" name="curPage" value="${curPage }"/>
								<ul class="m-steps">
										<li>1.选择商品分类</li>
										<li class="active">2.填写商品信息</li>
										<li>3.发布成功</li>
									</ul>
								<table class="table-thleft  table-lg">
									<tbody>
										<tr>
											<th>商品类目：</th>
											<td>协议商品>${classification.name}</td>
										</tr>
										<tr>
											<th><b class="clr-attention">*</b> 商品标题：</th>
											<td>
												<input type="text" name="name"  maxlength="85" value="${good.name}" class="form-control input-short-10" validate="{required:true, messages:{required:'商品标题不能为空'}}"/>
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
											<input type="text" value="${good.inventory}" name="inventory"  class="form-control input-short-4" maxlength="8" validate="{required:true,range:[0,99999999],messages:{required:'商品库存不能为空',range:'商品库存必须在零至一千万件之间'}}" /> 件
											</td>
										</tr>
										<%@include file="temp/picture.jsp"%>
										<%@include file="temp/detail.jsp"%>
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
	<%@include file="../../../common/footer.jsp"%>
	</body>
</html>

