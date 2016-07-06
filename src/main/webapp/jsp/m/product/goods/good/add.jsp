<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../../common/base1.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<meta charset="UTF-8" />
<title>商品分类-新品上市</title>	
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath }js/uploadpic.js"></script>
<script type="text/javascript">
//验证错误信息弹出显示
$.validator.setDefaults({
	onkeyup:false,//避免提交后实时验证
	focusInvalid:false,//提交后不获取焦点
	focusCleanup:true,
	showErrors: function(map, list) {
		$.each(list, function(index, error) {
			$(error.element).addClass('error');
			art.dialog.tips(error.message);
		});
	
	}
});
//商品明细图片
function itemFile(file, response,uploadBtn){
	if(response.source&&response.path){
		console.log(uploadBtn)
		uploadBtn.attr('src',ROOT_PICURL+response.source);
		uploadBtn.data('src',ROOT_PICURL+response.source);
		//uploadBtn.parent().find('input[name="itemPath"]').val(response.path);
		//uploadBtn.next().val(response.path);
	}
}
//商品主图图片
function goodFile(file, response,uploadBtn){
	if(response.source&&response.path){
		console.log(222)
		uploadPic.addgoodsImg(uploadBtn,response);
	}
}
function selectCallBack(file, response,uploadBtn){
	if(uploadBtn.attr('name')=='itemFile'){
		itemFile(file, response,uploadBtn);
	}else if(uploadBtn.attr('name')=='goodFile'){
		goodFile(file, response,uploadBtn);
		$('#picpathvalid').val(true);
	}else{
		art.dialog.alert(response.desc);
	}
}

function imgUpload(uploadBtn){
	var url = "/any/files/upload.htm";
	var read=new FileReader();
	
	var ajax=new AjaxUpload(uploadBtn, {
        action: url,
        autoSubmit: false,
        responseType: 'json',
        onChange:function(file,ext){
        	var imageSuffix = new RegExp('jpg|png|jpeg|JPG|PNG|JPEG');
        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
           	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
               return false;               
           }
        	read.readAsDataURL(this._input.files[0]);
        },
        onComplete: function(file, response){
        	if(response.flag != 'SUCCESS'){
        		art.dialog.alert(response.info);
        		return ;
        	}
        	selectCallBack(file,response,uploadBtn);
        }
	}); 
	read.onload=function(){
		var img=new Image();
		img.src=read.result;
		if(img.width<640){
			art.dialog.alert("图片宽度必须大于640px ");
			ajax._input.value="";
			return false;
		}
		ajax.submit();
	};
}

$(function(){
	tab("prolist");
	$.metadata.setType("attr", "validate");
	$("img[name='goodFile']").each(function(){
		imgUpload($(this));
	});
    //图片上传 方法
   	$('.j-addpic').on('click','i',function(){
		var num = $(this).data('name');
		var _self = this;
		
		if(num == 'delete'){

			art.dialog.confirm('确认删除？', function() {
				uploadPic.delLi(_self);
			}, function() {
				//art.dialog.tips('执行取消操作');
				return true;
			});
				
			}
	});


//代理提示
$('.j-tooltip a').popover({
	placement:"right",
	trigger:"hover"
});

//代理价格显示/隐藏
$('.j-tooltip input').on('click',function(){
	var index = $(this).index();
	if(index == 0){
		$('.agentprice').hide();
	}else{
		$('.agentprice').show();
	}
});

 //运费模板 信息隐藏
$(".j-tempaddress").on('click',function(){
	var index = $(this).data("id");
	if(index == 1){
		$('#postFeeYuan').removeClass('ignore');
		$("#tempaddress").hide();
	}else{
		$('#postFeeYuan').addClass('ignore').removeClass('error');
		$("#tempaddress").show();
	}
	
});

})

function saves(){
	
	var flag=$('#signupForm').validate({
		ignore:'.ignore',
		rules:{
			name:{
				required:true
			},
			no:{
				required:true,
			},
			marketPriceYuan:{
				required:true,
				moneyNotOne:true
			},
			picpathvalid1:{
				picpathvalid:true
			},
			scPrice:{
				required:true,
				moneyNotOne:true,
				maxlength:10
			},
			stock:{
				required:true,
				positiveInteger:true,
				maxlength:10
			},
			barCode:{
				maxlength:10
			},
			postFeeYuan:{
				required:true,
				moneyBase:true,
				maxlength:10
			}
		},
		messages:{
			name:{
				required:'商品标题不能为空'
			},
			no:{
				required:'货号不能为空'
			},
			marketPriceYuan:{
				required:'市场价不能为空',
				moneyNotOne:'市场价格式错误'
			},
			picpathvalid1:{
				picpathvalid:'请上传商品图片'
			},
			scPrice:{
				required:"请输入商品价格",
				moneyNotZero:"金额格式错误"
			},
			stock:{
				required:"请输入商品数量",
				positiveInteger:"请输入正确的商品数量"
			},
			postFeeYuan:{
				required:'运费不能为空',
				moneyBase:'必须为正整数且保留两位小数'
			}
		}
	}).form();

	if(flag){
		var maxLen = 0,
			maxSalePrice=0,//商品最大价格
	 prorLen = $(".J_Prorow").length;
		$(".J_Prorow").each(function(){
			var len = $(this).find('.J_Checkbox:checked').length;
			if(len>0) maxLen++;
		});
		
		$(".J_mapsalesPrice").each(function(){
			var $obj=$(this),
			    value=Number($obj.val());
			 if(value>maxSalePrice){
				 maxSalePrice=value;
			 }
		});
		if(Number(maxSalePrice)>Number($("#marketPriceYuan").val())){
			$.dialog.tips('商品价格中的最大价格不能大于市场价');
			return;
		}
		if(maxLen != prorLen){
			$.dialog.tips('请勾选商品属性');
			return;
		}
		$("#skuJson").val(app.db.getJson());
		console.log(app.db.getJson());
		$("#goodContent").val(getContent());
		$('#submitButton').attr('disabled','disabled');
		$(".j-loading").show();
		$("#signupForm").submit();
	}
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

	<%@include file="../../../common/header.jsp"%>
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
					<c:set var="crumbs" value="goodlist"/>
						<%@include file="../../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">发布商品</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
							<form id="signupForm" enctype="multipart/form-data"  autocomplete="off"  method="post"
								<c:if test="${not empty good.id }">action="/u/good/good/editnew/${good.id}.htm"</c:if>
								<c:if test="${empty good.id }">action="/u/good/good/addnew/${classification.id}.htm"</c:if>>
								<input type="hidden" id="goodId" name="goodId" value="${good.id}"/>
								<input type="hidden" id="classificationId" name="classificationId" value="${good.classificationId}" />
								<input type="hidden" id="skuJson" name="skuJson"/>
								<input type="hidden" name="shopTypeId" value="${shopTypeId }"/>
								<input type="hidden" name="goodName" value="${goodName }"/>
								<input type="hidden" name="curPage" value="${curPage }"/>
								<table class="table-thleft  table-lg">
									<tbody>
                                        <tr>
                                            <th><b class="clr-attention"></b> 商品类目：</th>
                                            <td>${classFullName }</td>
                                        </tr>
										<tr>
											<th><b class="clr-attention">*</b> 商品标题：</th>
											<td><input type="text" name="name" maxlength="85" value="${good.name}" class="form-control input-short-10"></td>
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
														<a href="${ctx}/u/good/shopType/list${ext}" class="btn btn-default btn-lg">您还没有创建分类，现在去添加!</a>
													</c:otherwise>
												</c:choose>	
											</td>
										</tr>

										<tr>
											<th>货号：</th>
											<td><input type="text" name="no" value="${good.no}" maxlength="21" class="form-control input-short-5"></td>
										</tr>
	
										<tr>
											<th><b class="clr-attention">*</b> 市场价(元)：</th>
											<td><input id="marketPriceYuan" type="text" maxlength="12" name="marketPriceYuan" value="<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/>" class="form-control input-short-5"></td>
										</tr>
										<%@include file="temp/picture.jsp"%>
										<c:if test="${not empty propMap}">
											<!-- <input type="hidden"  id="checkpop" validate="{checkpop:true,messages:{checkpop:'请勾选商品属性'}}"> -->
											<c:forEach items="${propMap}" var="propMap" varStatus="status">
												<c:choose>
													<c:when test="${propMap.key.isColorProp&&propMap.key.isSalesProp}">
														<tr>
															<th><b class="clr-attention">*</b>${propMap.key.name}：</th>
															<td>
																<ul class="pro-color J_Prorow" data-check id="j-proColor"  data-index="${status.index }">
																<c:forEach items="${propMap.value}" var="propValue" >
																	<c:choose>
																		<c:when test="${empty propValue.hex}">
																			<li>
																				<img src="${resourcePath}/img/${propValue.code}.jpg" alt="">
																				<input type="checkbox" class="J_Checkbox" data-id="${propValue.id }" value="sx${status.index+1 }:${propValue.id}" name=''>
																				<input type="text" value="${propValue.name}" maxlength="64" class="form-control" disabled="disabled">
																			</li>
																		</c:when>
																		<c:otherwise>
																			<li>
																				<i style="background: ${propValue.hex};"></i>
																				<input type="checkbox" class="J_Checkbox" data-id="${propValue.id }" value="sx${status.index+1 }:${propValue.id}" name=''>
																				<input type="text" value="${propValue.name}" maxlength="64" class="form-control" disabled="disabled">
																			</li>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
																</ul>
															</td>
														</tr>
														<tr>
															<td>&nbsp;</td>
															<td>
																<table class="table table-bordered  table-mesa" id="template2" >
																	<thead>
																		<tr>
																			<th>颜色</th>
																			<th>图片（无图可不填）</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${propertyImgs}" var="propertyImgs">
																			<tr class="${propertyImgs.key}">
																				<td class="colorName">${propertyImgs.value.propValueAlias}</td>
																				<td>
																					<div class="btn-upload">
																					<input type="hidden" name="itemPath" value="${propertyImgs.value.picturePath}">
																					<input type="hidden" name="propertyImgs[${propertyImgs.key}].id" value="${propertyImgs.value.id}">
																					<c:choose>
																						<c:when test="${not empty propertyImgs.value.picturePath}">
																							<img name="itemFile" src="${picUrl}${propertyImgs.value.picturePath}" style="width:100px;height:100px;" alt="" class="">
																						</c:when>
																						<c:otherwise>
																							<img name="itemFile" src="${resourcePath}img/add100X100.jpg" style="width:100px;height:100px;" alt="" class="">
																						</c:otherwise>
																					</c:choose>
																					<input type="hidden" value="${propertyImgs.value.picturePath}">
																					<input type="hidden" value="${propertyImgs.key}">
																					</div>
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</td>
														</tr>
													</c:when>
													<c:when test="${propMap.key.isEnumProp&&propMap.key.isSalesProp}">
														<tr>
															<th><b class="clr-attention">*</b>${propMap.key.name}：</th>
															<td>
																<ul class="pro-color pro-size J_Prorow" data-check 
																	<c:if test="${propMap.key.isColorProp }">
																		id="j-proSize"
																	</c:if> 
																  data-index="${status.index }">
																	<c:forEach items="${propMap.value}" var="propValue">
																		<li>
																			<input type="checkbox" class="J_Checkbox" data-id="${propValue.id }" value="sx${status.index+1 }:${propValue.id}" name='fgf'>
																			<input type="text" value="${propValue.name}" class="form-control" disabled="disabled">
																		</li>
																	</c:forEach>
																</ul>
															</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
											</tr>
										</c:if> 
										<tr>
											<td>&nbsp;</td>
							
											<td>
												<div class="shop-amend">
													<div class="info">
														<img src="${resourcePath }img/amend-icon.png" alt="">批量设置：
														<button type="button" data-id="price" class="btn btn-default btn-sm" >价格</button>
														<button type="button" data-id="total" class="btn btn-default btn-sm">数量</button>
													</div>
													
													<div data-id="salesPrice" class="amend-item">
														<input type="text" class="form-control form-control input-short-5" placeholder="价格">
														<button type="button" data-type="ok" class="btn btn-default btn-sm">确定</button>
														<button type="button" data-type="no" class="btn btn-default btn-sm">取消</button>
													</div>
													<div data-id="stock" class="amend-item">
														<input type="text" class="form-control form-control input-short-5"  placeholder="数量">
														<button type="button" data-type="ok" class="btn btn-default btn-sm">确定</button>
														<button type="button" data-type="no" class="btn btn-default btn-sm">取消</button>
													</div>


													</div>
												<table class="table table-bordered  table-mesa" id="template">
													<thead>
														<tr>
															<c:if test="${not empty propMap}">
																<c:forEach items="${propMap}" var="propMap" varStatus="status">
																	<th>${propMap.key.name }</th>
																</c:forEach>
															</c:if>
															<th>商品价格</th>
															<th>商品数量</th>
															<th>商品条码</th>
														</tr>
													</thead>
													<tbody></tbody>
													<!-- <tbody><tr class="J_MapRow" data-id="35" data-type="sx1-3|sx2-3|sx3-3"><td rowspan="2"><span class="J_mapsx1-3">巧克力色</span></td><td rowspan="2"><span class="J_mapsx2-3">L</span></td><td><span class="J_mapsx3-3">非主流</span></td><td class="salesPrice"><input name="salesPrice" class="J_mapsalesPrice" data-type="salesPrice"></td><td class="stock"><input name="stock" class="J_mapstock" data-type="stock"></td><td class="barcode"><input name="barcode" class="J_mapbarcode" data-type="barcode"></td></tr><tr class="J_MapRow" data-id="36" data-type="sx1-3|sx2-3|sx3-004"><td><span class="J_mapsx3-004">白富美</span></td><td class="salesPrice"><input name="salesPrice" class="J_mapsalesPrice" data-type="salesPrice"></td><td class="stock"><input name="stock" class="J_mapstock" data-type="stock"></td><td class="barcode"><input name="barcode" class="J_mapbarcode" data-type="barcode"></td></tr></tbody> -->
												</table>
											</td>
										</tr>
										<tr>
											<th><b class="clr-attention">*</b> 商城价：</th>
											<td>
												<input type="text" name="qwe" value="<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/>" class="form-control input-short-4" disabled="disabled" id="j-price"/> 元
											</td>
										</tr>
										<tr>
											<th>商品总数：</th>
											<td>
												<input type="text" name="dfd" value="<fmt:formatNumber pattern="0" value="${good.inventory}"/>" class="form-control input-short-4" disabled="disabled" id="j-total"/> 件
											</td>
										</tr>
										<%@include file="temp/detail.jsp"%>
									</tbody>
								</table>
								<%@include file="temp/logistic.jsp"%>
								<div class="other-set"><b>其他设置</b></div>
								<table class="table-thleft  table-lg">
									<tr>
										<td>会员折扣:</td>
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
									<button type="button" class="btn btn-default btn-lg" onclick="saves()">保存</button>
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

	<%@include file="../../../common/footer.jsp"%>
	</body>
<script type="text/javascript" src="${resourcePath }js/dbShop.js"></script>
<script>
    $(function(){
        //提交控制
        function  picCtrl(){
            var firstImg=$('#j-addpic li:eq(0)').find('img').attr('img');//第一个主图图片
            if(firstImg){
                return false;
            }
        }
        var data =	'${goodSkus}';
        if(!!data){
        	var arr=JSON.parse('${propValueIdList}');
            for(var x in arr){
            	$("input[type=checkbox][data-id="+arr[x]+"]").attr("checked","checked");
            }
        }
        
        app.db.init();
        app.db.setJson(data);
    })
    function canel(){
		art.dialog.confirm('确认取消正在编辑的商品？', function() {
			history.go(-1);
		}, function() {
			return true;
		});
	}
</script>
</html>

