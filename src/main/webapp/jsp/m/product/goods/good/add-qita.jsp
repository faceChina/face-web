<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<meta charset="UTF-8" />
<title>商品管理-新品上市</title>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<style>
	.border-error{
		border: 1px solid #d82230;
	}
</style>
<script type="text/javascript" src="${resourcePath}js/addNewShop.js"></script>
<script type="text/javascript">
/** ueditor控件相关 */
// UE.getEditor('editor');
// function getContent() {return UE.getEditor('editor').getContent();}
var shopData;


function getsrt2(vid,arr1,data){
	console.dir(typeof vid);
	vid=Number(vid);
	var idvalue='${propertyImgs['+vid+'].id}';
	console.dir(idvalue);
	var str2 =	[ '<tr class="',vid,'">',
			 		'<td class="',arr1,'">',data,'</td>',
			 		'<td><div class="btn-upload"><input type="hidden" name="itemPath"><input type="hidden" name="propertyImgs['+vid+'].id" value="${propertyImgs['+vid+'].id}"><img name="itemFile"  src="../../../../resource/m/img/add100X100.jpg" style="width:100px;height:100px;" alt="" class="">',
			 		'<input type="hidden" name="propertyImgs['+vid+'].picturePath" value="${propertyImgs['+vid+'].picturePath}"><input type="hidden" name="propertyImgs['+vid+'].propValueId" value="'+vid+'"></div></td>',
			 		'</tr>'
          	].join("");	
	return str2;
}



var valiurl ="${ctx}${userGroup}/good/vaildate${ext}";	
$(function(){
	tab("prolist");
	shopData = addNewShop('#signupForm');
	$("img[name='goodFile']").each(function(){
		imgUpload($(this));
	});
	//设置值
	if(''!='${itemJson}'){
		var color =JSON.parse('${colorJson}');
		var size=JSON.parse('${sizeJson}');
		var item = JSON.parse('${itemJson}');
		shopData.setJson(color,'color');
		shopData.setJson(size,'size');
		shopData.setJson(item);
	}
	generalNewShop('.j-table');
	
	$.metadata.setType("attr", "validate");

});

function canel(){
	art.dialog.confirm('确认取消正在编辑的商品？', function() {
		history.go(-1);
	}, function() {
		return true;
	});
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
	if('${isSalesInput}'=='true'){
		console.dir(getGoodItem());
		$("#freeGoodSku").val(getGoodItem());
	}else{
		$("#skuJson").val(shopData.getJson());
	}
	var maxSalePrice=0;
	$("[data-price]").each(function(){
		var $obj=$(this),
		    value=Number($obj.val());
		 if(value>maxSalePrice){
			 maxSalePrice=value;
		 }
	});
	if(Number(maxSalePrice)>Number($("#marketPriceYuan").val())){
		$.dialog.tips('商品属性中的最大价格不能大于市场价');
		return;
	}
	
	$("#goodContent").val(getContent());
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
	var notkeyProperties = [];
	$(".j-table tr").each(function(index){
		var notkeyProperty = {
				name:$(this).find("input[type='text']").eq(0).val(),
				value:$(this).find("input[type='text']").eq(1).val()
		};
		notkeyProperties[index]=notkeyProperty;
	});
	var marketPriceYuan = $("#marketPriceYuan").val();
	var salesPriceYuan = $("[name='salesPriceYuan']").val();
	var content =getContent();
	var skuJson = shopData.getJson();
	var classificationId = $("#classificationId").val();
 	var inventory = $("[name='inventory']").val();
	var goodDto = {
			classificationId:classificationId,
			name:name,
			marketPrice:marketPriceYuan*100,
			salesPrice:salesPriceYuan*100,
			goodContent:content,
			skuJson:skuJson,
			attachmentPaths:attachmentPaths,
			notkeyProperties:notkeyProperties,
			inventory:inventory
	};
	return JSON.stringify(goodDto);
}

function preview(){
	if(!formvali.form()){
		return;
	}
	var url= '${ctx}${userGroup}/preview/good${ext}';
	var name='previewModular';   //网页名称，可为空
    var iWidth=410;                          //弹出窗口的宽度;
    var iHeight=810;                         //弹出窗口的高度;
    previewToBigData(url,getJson(),name,iWidth,iHeight);
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
									<li class="active"><a href="#members-set" data-toggle="tab">新品上市</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
							<ul class="m-steps">
								<li>1.选择商品类目									</li>
								<li class="active">2.填写商品信息</li>
								<li>3.发布成功</li>
							</ul>
							<form id="signupForm" method="post" 
								<c:if test="${not empty good.id }">action="/u/good/good/editnew/${good.id }.htm"</c:if>
								<c:if test="${empty good.id }">action="/u/good/good/addnew/${classification.id }.htm"</c:if>  
								enctype="multipart/form-data"  autocomplete="off">
								<input type="hidden" id="goodId" name="goodId" value="${good.id}"/>
								<input type="hidden" id="classificationId" name="classificationId" value="${good.classificationId}" />
								<input type="hidden" id="skuJson" name="skuJson"/>
								<input type="hidden" name="shopTypeId" value="${shopTypeId }"/>
								<input type="hidden" name="goodName" value="${goodName }"/>
								<input type="hidden" name="curPage" value="${curPage }"/>
								<table class="table-thleft  table-lg">
									<tbody>
										<tr>
											<th><b class="clr-attention">*</b> 商品标题 ：</th>
											<td>
												<input type="text" id="goodName" name="name"  maxlength="85" value="${good.name}" class="form-control input-short-10" validate="{required:true, messages:{required:'商品标题不能为空'}}"/>
											</td>
										</tr>
										<tr>
											<th>所属分类：</th>
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
											<th><b class="clr-attention">*</b>货号：</th>
											<td><input type="text" name="no" value="${good.no}" maxlength="21"  class="form-control input-short-5" validate="{required:true,messages:{required:'不能为空'}}"></td>
										</tr>

										<tr>
											<th><b class="clr-attention">*</b> 市场价：</th>
											<td><input type="text" id="marketPriceYuan" name="marketPriceYuan" value="<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/>" 
											class="form-control input-short-5" maxlength="15" validate="{required:true,moneyNotOne:true,messages:{required:'市场价不能为空'}}" > 元</td>
										</tr>
										<%@include file="temp/picture.jsp"%>
										<%@include file="temp/salesInputProp.jsp"%>
										<%@include file="temp/notkeyProp.jsp"%>
										<%@include file="temp/detail.jsp"%>
									</tbody>
								</table>
								<%@include file="temp/logistic.jsp"%>
								<div class="other-set"><b>其他设置</b></div>
								<table class="table-thleft  table-lg">
									<tr>
										<td>是否享受会员折扣:</td>
										<td>
										<c:choose>
											<c:when test="${not empty good.preferentialPolicy}">
												<input type="radio" name="isJoinMember" value="true" <c:if test="${good.preferentialPolicy==1}">checked="checked"</c:if>/> 是  
										 		<input type="radio" name="isJoinMember" value="false" <c:if test="${good.preferentialPolicy!=1}">checked="checked"</c:if> style="margin-rigdt:10px;" /> 否  
											</c:when>
											<c:otherwise>
												<input type="radio" name="isJoinMember" value="true"  checked="checked"/> 是  
										 		<input type="radio" name="isJoinMember" value="false" style="margin-rigdt:10px;" /> 否  
											</c:otherwise>
										</c:choose>
										</td>
									</tr>
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