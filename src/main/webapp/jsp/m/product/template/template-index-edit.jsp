<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>首页模块--设置</title>
<!-- top -->
<%@include file="../../common/base.jsp" %>
<!--top end -->

<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath }js/allSelect.js"></script>
<script type="text/javascript">
var valiateFlag;
$(function() {
	
	valiateFlag = $("#j-activity").validate({
		rules : {
			nameZh : {
				required: true,
				maxlength:14,
				minlength:1
			},
			nameEn : {
				maxlength:28,
				minlength:1
			}
		},
		messages : {
			nameZh : {
				required: "模块中文名称不能为空！",
				maxlength:$.format("模块中文名称不能超过{0}个字符"),
				minlength:$.format("模块中文名称不能超过{0}个字符")
			},
			nameEn : {
				maxlength:$.format("模块英文名称不能超过{0}个字符"),
				minlength:$.format("模块英文名称不能超过{0}个字符")
			}
		}
	});
	
tab("modular");

//上传图片 提示文字
$('.j-tooltip input').tooltip({
     placement:"right"
 });

//自定义图片上传绑定
$("body").find("input[name='standardUploadImg']").each(function(index){
	uploadimg($(this));
});
var transparency = '${modular.transparency}' == '' ? 100 : parseInt('${modular.transparency}');
$("#Jopacitybar span").css("left",transparency);
$('#myOpacity').val(transparency);
$("#myOpacityBgColor").css("opacity",transparency/100);

if('${modular.code}'.indexOf('good') != -1 || ('${modular.code}'.indexOf('shopType') != -1 && '${modular.code}'.indexOf('modular') == -1)){
	$("#j-link").val("addPro");
} else if('${modular.code}'.indexOf('newsList') != -1 || '${modular.code}'.indexOf('articleCategory') != -1 || '${modular.code}'.indexOf('aricleColumn') != -1) {
	$("#j-link").val("addTempArtical");
} else if('${modular.code}'.indexOf('album') != -1 || '${modular.code}'.indexOf('photoAlbum') != -1){
	$("#j-link").val("addPoto");
} else if('${modular.code}'.indexOf('modular') != -1){
	$("#j-link").val("addTemp");
} else if('${modular.code}'.indexOf('linkUrl') != -1){
	$("#j-link").val("addLink");
} else if('${modular.code}'.indexOf('appointment') != -1) {
	$("#j-link").val("addAppointment");
}

});

function saveModular(){
	var flag = valiateFlag.form();
	if(flag) {
		$(".j-loading").show();
		$("#j-activity").submit();
	}
}
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
<%-- 						<%@include file="../../common/crumbs.jsp"%> --%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"data-toggle="tab">首页模块管理</a></li>
								</ul>
							</div>
							<div class="content">
								<form method="post" id="j-activity">
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-md-2 control-label">模块名称：</label>
											<div class="col-md-10">
												<input type="text" value="${modular.nameZh }" name="nameZh" class="form-control input-short-6">
											</div>
										</div>
										<c:if test="${(owTemplateHp.isNameEn == 1 && modularType == 1) || (owTemplateHp.isNameEnCustom == 1 && modularType == 2)}">
											<div class="form-group" data-format="4,3" >
												<label class="col-md-2 control-label">英文名称：</label>
												<div class="col-md-10">
													<input type="text" value="${modular.nameEn }" name="nameEn" class="form-control input-short-6">（选填）
												</div>
											</div>
										</c:if>
										<c:if test="${(owTemplateHp.isImage == 1 && modularType == 1) || (owTemplateHp.isImageCustom == 1 && modularType == 2)}"> 
											<div class="form-group">
												<label class="col-md-2 control-label">分类图片：</label>
												<div class="col-md-3">
													<div class="j-picpath" style="width:150px; height:150px;border:1px solid #ccc;">
														<c:choose>
															<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
																<img src="${picUrl }${modular.imgPath }" alt="">
																<input type="hidden" id="imgPath"  name="imgPath" value="${modular.imgPath }"/>
															</c:when>
															<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 1 }">
																<img src="${modular.imgPath }" alt="" >
																<input type="hidden" id="imgPath"  name="imgPath" value="${modular.imgPath }"/>
															</c:when>
															<c:otherwise>
																<img src="${resourceBasePath }img/goods.jpg" alt="" >
																<input type="hidden" id="imgPath"  name="imgPath" value="${resourceBasePath }img/goods.jpg"/>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
												<div class="col-md-6" style="margin-left:20px;">
													<button type="button" class="btn btn-default" style="display:block;margin-bottom:10px;" onclick="showIcons()">选择图标</button>
													<div class="btn btn-default btn-upload j-tooltip">
														上传图片
														<c:choose>
															<c:when test="${modularType == 1 }">
																<input type="button" name="standardUploadImg" value="" class="form-control"  data-toggle="tooltip" 
																	data-original-title="请上传尺寸大小为 ${owTemplateHp.modularImageWidth }*${owTemplateHp.modularImageHeight } 像素的图片，以png格式为准。"/>
															</c:when>
															<c:otherwise>
																<input type="button" name="standardUploadImg" value="" class="form-control"  data-toggle="tooltip" 
																	data-original-title="请上传尺寸大小为 ${owTemplateHp.customModularImageWidth }*${owTemplateHp.customModularImageHeight } 像素的图片，以png格式为准。"/>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${modularType == 1 || (owTemplateHp.isNameZhColorCustom == 1 && modularType == 2) }">
											<div class="form-group">
												<label class="col-md-2 control-label">模块文字颜色：</label>
												<div class="col-md-10">
													<c:if test="${empty modular.nameZhColor  }">
														<input class="color form-control input-short-3" value="333333" name="nameZhColor" id="myColor" />
													</c:if>
													<c:if test="${not empty modular.nameZhColor }">
														<input class="color form-control input-short-3" value="${modular.nameZhColor }" name="nameZhColor" id="myColor" />
													</c:if>
												</div>
											</div>
										</c:if>
										<c:if test="${(owTemplateHp.isNameEn == 1 && modularType == 1) || (owTemplateHp.isNameEnCustom == 1 && modularType == 2)}">
											<div class="form-group" data-format="4,3" >
												<label class="col-md-2 control-label">英文文字颜色：</label>
												<div class="col-md-10">
													<c:if test="${empty modular.nameEnColor }">
														<input class="color form-control input-short-3" value="666666" name="nameEnColor" id="myColor2" />
													</c:if>
													<c:if test="${not empty modular.nameEnColor }">
														<input class="color form-control input-short-3" value="${modular.nameEnColor }" name="nameEnColor" id="myColor2" />
													</c:if>
												</div>
											</div>
										</c:if>
										<c:if test="${(owTemplateHp.isBackgroundColor == 1 && modularType == 1) || (owTemplateHp.isBackgroundColorCustom == 1 && modularType == 2)}">
											<div class="form-group">
												<label class="col-md-2 control-label">背景颜色：</label>
												<div class="col-md-10">
													 <p>
													 <c:if test="${empty modular.backgroundColor }">
														 <input type="text" name="backgroundColor" value="EEEEEE" class="color  form-control input-short-3" id="myBgColor" onchange="
																document.getElementById('red').value = parseInt(this.color.rgb[0]*255);
																document.getElementById('grn').value = parseInt(this.color.rgb[1]*255);
																document.getElementById('blu').value = parseInt(this.color.rgb[2]*255);
																document.getElementById('myOpacityBgColor').style.backgroundColor = 'rgb('+parseInt(this.color.rgb[0]*255)+','+parseInt(this.color.rgb[1]*255)+','+parseInt(this.color.rgb[2]*255)+')';"
																autocomplete="off" style="color: rgb(0, 0, 0); background-color: rgb(255, 107, 245);">
													 </c:if>
													 <c:if test="${not empty modular.backgroundColor }">
														 <input type="text" name="backgroundColor" value="${modular.backgroundColor }" class="color  form-control input-short-3" id="myBgColor" onchange="
																document.getElementById('red').value = parseInt(this.color.rgb[0]*255);
																document.getElementById('grn').value = parseInt(this.color.rgb[1]*255);
																document.getElementById('blu').value = parseInt(this.color.rgb[2]*255);
																document.getElementById('myOpacityBgColor').style.backgroundColor = 'rgb('+parseInt(this.color.rgb[0]*255)+','+parseInt(this.color.rgb[1]*255)+','+parseInt(this.color.rgb[2]*255)+')';"
																autocomplete="off" style="color: rgb(0, 0, 0); background-color: rgb(255, 107, 245);">
													 </c:if>
													</p>
													<br>
													<p>
														R：<input type="text" value="255" id="red" size="5" class="form-control input-short-2" disabled="disabled">  
														G：<input type="text" value="255" id="grn" size="5" class="form-control input-short-2" disabled="disabled">  
														B：<input type="text" value="255" id="blu" size="5" class="form-control input-short-2" disabled="disabled">
													</p>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">背景透明度：</label>
												<div class="col-md-10">
													<div class="opacitybar" id="Jopacitybar">
														<span class="opacitybar-handler"></span>
													</div>
													<input type="text" name="transparency" value="${modular.transparency }" class="form-control input-short-3" id="myOpacity" readonly="readonly"/>
													<input type="text" value="" id="myOpacityBgColor" size="5" class="form-control input-short-2" style="background:#fff;" disabled = "disabled">
												</div>
											</div>
										</c:if>
										<div class="form-group">
											<label class="col-md-2 control-label">外链或模块：</label>
											<div class="col-md-10">
												<select class="form-control input-short-3" id="j-link" onclick="manageLink()">
													<option value="">管理链接</option>
													<option value="addPro">链接至商品</option>
													<c:if test="${subCode == 'WGW' }">
													<option value="addTempArtical">链接至文章</option>
													<option value="addPoto">链接至相册</option>
													</c:if>
													<option value="addTemp">链接至模块</option>
													<option value="addAppointment">链接至预约</option>
													<c:if test="${subCode == 'WGW' }">
													<option value="addLink">链接至外部链接</option>
													</c:if>	
												</select>
											</div>
										</div>
										<input type="hidden" name="id" value="${modular.id }"/>
										<input type="hidden" name="modularId" value="${modular.id }"/>
										<input type="hidden" name="code" value="${modular.code }"/>
										<input type="hidden" name="bindIds" value="${modular.bindIds }"/>
										<input type="hidden" name="resourcePath" value="${modular.resourcePath }"/>
										<input type="hidden" name="type" value="${modularType }"/>
										<div class="form-group">
											<div class="col-md-10 col-md-offset-2">
												<button type="button" name="sumbit" class="btn btn-default" onclick="saveModular();">确认</button>
												<a href="javascript:history.go(-1);" class="btn btn-default">取消</a>
											</div>
										</div>
									</div>
								</form>					
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!-- body end -->
	<%@include file="addlink.jsp" %>
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	
<script type="text/javascript">
/*--------------------------------------透明度拖动条-------------------------------------------------*/
	var data = 20;//获取转递的数据
	var startX,spaceX,endX,barX; //x轴位置相应事件的值
	
	//确定三角的位置和输入框的值
	$("#myOpacity").val(data);
	$("#Jopacitybar span").css("left",data);
	$("#myOpacityBgColor").css("opacity",data/100);
	
	//鼠标事件
	$("#Jopacitybar span").mousedown(function(e){
		barX = parseInt($(this).css("left"));
		startX = e.pageX;
		$(document).mousemove(function(e){
			//拖动时文字不可选
			$("body").css({
				"-webkit-user-select":"none",
				"-moz-user-select":"none",
				"-ms-user-select":"none",
				"-o-user-select":"none",
				"user-select":"none"
			});
			document.getElementsByTagName("body")[0].setAttribute("onselectstart","javascript:return false")
			
			spaceX = e.pageX - startX;
			endX = barX + spaceX;
			if(endX <= 0){
				endX = 0;
			}else if(endX >= 100){
				endX = 100;
			}
			$("#Jopacitybar span").css("left",endX);
			$("#myOpacity").val(endX);
			$("#myOpacityBgColor").css("opacity",endX/100);
		});
	});
	$(document).mouseup(function(){
		$(this).unbind("mousemove");
		
		$("body").css({
			"-webkit-user-select":"none",
			"-moz-user-select":"none",
			"-ms-user-select":"none",
			"-o-user-select":"none",
			"user-select":"none"
		})
		document.getElementsByTagName("body")[0].setAttribute("onselectstart","javascript:return true")
	});
	
/*--------------------------------------图标选择-------------------------------------------------*/	
function showIcons(){
	art.dialog.open('${ctx}/u/good/shopType/tempImgList${ext}', {
	    title: '选择素材',
	    width: '800px',
	    height: '300px',
	    ok: function () {
	    	var picpath = $('.aui_content iframe').contents().find('.addicon img').attr('src');
	    	$('.j-picpath img').attr('src',picpath);
	    	$('#imgPath').val(picpath);
	    },
	    cancel: true
	});
}

 function uploadimg(uploadBtn){
	    var url = "${ctx}/any/files/upload${ext}";
	    new AjaxUpload(uploadBtn, {
	        action: url,
	        data: {
	        },
	        autoSubmit: true,
	        responseType: 'json',
	        onSubmit: function(file,ext){
	        	var imageSuffix = new RegExp('${imageSuffix}');
	        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
	            	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
	                return false;               
	            }
	        },
	        onComplete: function(file, response){
	        	if(response.code == -1){
	        		art.dialog.alert(response.desc);
	        		return ;
	        	}
	        	if(response.flag == "SUCCESS"){
	        		var div=uploadBtn.parent().parent().parent();
	        		var src_path = '${picUrl}'+response.source;
	  	 			if('${owTemplateHp.shape}' == '1'){
	  	 				div.find("img").attr("src",src_path).css({
	  	 					"border-radius":"100%",
	  	 				});
	  	 			}else{
	  	 				div.find("img").attr("src",src_path);
	  	 			}; 
	    			div.find("img").attr('src',src_path).css("display","inline-block");
	    			div.find("input[name='imgPath']").val(response.path);
	    		} else {
					art.dialog.alert(response.info);
				}
	        }
			});
	}
 
 
</script>
</body>
</html>
