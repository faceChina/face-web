<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<meta charset="UTF-8" />
<title>商品管理-新品上市</title>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath}js/addNewShop.js"></script>
<style>
.error{color:red;}
#template input{
	width: 100px;
}
</style>
<script type="text/javascript">

$(function(){
	tab("prolist");
	$("img[name='goodFile']").each(function(){
		imgUpload($(this));
	});
	
	
	$.metadata.setType("attr", "validate");

});

function canel(){
	if(!'${good.id}'){
		location.href='/u/good/good/list.htm';
	}else{
		art.dialog.confirm('确认取消正在编辑的商品？', function() {
			location.href='/u/good/good/list.htm';
		}, function() {
			return true;
		});
	}
}

function saves(){
	var formvali = $("#signupForm").validate({
		ignore: ".ignore",
		errorPlacement: function(error, element) {
			var elName = $(element).attr('name');
			if( elName == 'price' || elName == 'stock' || elName == 'barCode' || $(element).attr('id') == 'checkpop' || $(element).attr('id') == 'picpathvalid'){
				art.dialog.tips(error.text());
			}else{
				error.insertAfter(element);  
			}
			
		} 
	});
	if(!formvali.form()){
		return;
	}
	if($('input[name=outerLink]').val().substring(0,7)!='http://'){
		$.dialog.tips('链接必须以http://开头');
		return;
	}
	$('#submitButton').attr('disabled','disabled');
	$(".j-loading").show();
	$("#signupForm").submit();
}

function getJson(){
	var name = $("[name='name']").val();
	var attachmentPaths=[];
	$("#j-addpic li").not('.addimg').find("img").each(function(index){
		attachmentPaths[index]=this.src;
	});
	var marketPriceYuan = $("#marketPriceYuan").val();
	var salesPriceYuan = $("[name='salesPriceYuan']").val();
	var classificationId = $("#classificationId").val();
 	var inventory = $("[name='inventory']").val();
	var goodDto = {
			classificationId:classificationId,
			name:name,
			marketPrice:marketPriceYuan*100,
			salesPrice:salesPriceYuan*100,
			attachmentPaths:attachmentPaths
	};
	return JSON.stringify(goodDto);
}

</script>
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
	<c:if test="${not empty good.id }"><c:set var="editPage" value="1"/></c:if>
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
					<!--crumbs -->
						<c:set var="crumbs" value="goodlist"/>
						<%@include file="../../../common/crumbs.jsp"%>
					<!--crumbs end-->
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">发布商品</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
							<form id="signupForm" method="post"  enctype="multipart/form-data"  autocomplete="off">
								<input type="hidden" id="goodId" name="goodId" value="${good.id}"/>
								<input type="hidden" id="classificationId" name="classificationId" value="${good.classificationId}" />
								<input type="hidden" id="skuJson" name="skuJson"/>
								<input type="hidden" name="shopTypeId" value="${shopTypeId }"/>
								<input type="hidden" name="goodName" value="${goodName }"/>
								<input type="hidden" name="curPage" value="${curPage }"/>
								<table class="table-thleft  table-lg">
									<tbody>
										<tr>
											<th>商品类目 ：</th>
											<td>门户商品</td>
										</tr>
										<tr>
											<th><b class="clr-attention">*</b> 商品标题 ：</th>
											<td>
												<input type="text" id="goodName" name="name"  maxlength="85" value="${good.name}" class="form-control input-short-10" validate="{required:true, messages:{required:'商品标题不能为空'}}"/>
											</td>
										</tr>
										<tr>
											<th style="vertical-align:top">图片：</th>
											<td>
											
												<div class="col-md-12 tabel-pic-box">
													<ul class="details-addimg j-addpic left" id="j-addpic">
												    	<c:choose>
												    		<c:when test="${not empty goodImgs['0']}">
																<li class="addimg" title="0">
																	<input type="hidden" target="id" name="goodImgs['${goodImgs['0'].position}'].id" value="${goodImgs['0'].id}">
														  			<input type="hidden" target="name" name="goodImgs['${goodImgs['0'].position}'].position" value="${goodImgs['0'].position}">
														  			<input type="hidden" target="path" name="goodImgs['${goodImgs['0'].position}'].url" value="${goodImgs['0'].url}">
														  			<img name="goodFile" src="${picUrl}${goodImgs['0'].zoomUrl}" alt="" class="goodFile" data-src="">
														  			<span class="action" style=""><i data-name="delete">删除</i></span>
														  		</li>
														  </c:when>
														  <c:otherwise>
														  		<li class="addimg" title="0">
														  			<input type="hidden" target="name" name="goodImgs['0'].position" value="0">
														  			<input type="hidden" target="path" name="goodImgs['0'].url">
														  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
														  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
														  		</li>
														  </c:otherwise>
												    	</c:choose>
													</ul>
													
													<div class="left pl10"><p>提示：</p>
														<p>
															1.本地图片大小不能超过<span class="fontcor-red">2M</span>
														</p>
														<p>2.本类目下您最多可以上传1张图片。</p>
														<p>
															3.图片尺寸大小<span class="fontcor-red">640*640px</span>
														</p>
														<c:choose>
												    	<c:when test="${not empty goodImgs['0']}">
															<input type="hidden"  id="picpathvalid1" value="true" validate="{picpathvalid:true}">
														</c:when>
														 <c:otherwise>
														 	<input type="hidden"  id="picpathvalid1"  validate="{picpathvalid:true}">
														 </c:otherwise>
													</c:choose>
													</div>
												</div>
													
											</td>
										</tr>
										<tr>
											<th>分类：</th>
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
														<a href="${ctx}/u/good/shopType/list${ext}" class="btn btn-default btn-lg">您还没有创建分类，现在去添加!</a>
													</c:otherwise>
												</c:choose>	
											</td>
										</tr>
										<tr>
											<th>链接 ：</th>
											<td>
												<input type="text" name="outerLink"  maxlength="85" value="${good.outerLink}" class="form-control input-short-10" validate="{required:true, messages:{required:'外链不能为空'}}"/>
											</td>
										</tr>
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
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../../common/footer.jsp"%>
	<!-- footer end -->
	</body>
</html>