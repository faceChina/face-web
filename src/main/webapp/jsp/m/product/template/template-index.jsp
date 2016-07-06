<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>首页模块--设定</title>	
<%@include file="../../common/base.jsp" %>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath }js/ZeroClipboard/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${resourcePath}plugins/updown.js"></script>
<!--top end -->
<script type="text/javascript">
	$(function() {
		tab("modular");
		
		//预览
		$("#preview").on('click',function(){
			var url= '${ctx}/any/preview/${subCode}/template${ext}';
			var name='previewTemplate';   //网页名称，可为空
		    var iWidth=400;               //弹出窗口的宽度;
		    var iHeight=800;              //弹出窗口的高度;
		    previewToBigData(url,getJson('${owTemplateHp.code}'),name,iWidth,iHeight);
		});
		
		$('.add-contact').on('click',function(){
			art.dialog({
				lock : true,
				width : '600px',
				title : "联系方式",
				background : '#000', // 背景色
				opacity : 0.1, // 透明度
				content : document.getElementById("j-contact"),
				button : [ {
					name : '保存',
					callback : function() {
						var activity = $("#j-activity").validate({
					        rules:{
					            cell:{
					                required:true,
					                telAndphone:true
					               
					            },
					            address:{
					            	maxlength:128,
					                required:true
					            }
					        },
					        messages:{
					        	cell:{
					                required:'联系方式不可为空！',
					                telAndphone:'请输入正确的联系方式！'
					            },
					            address:{
					            	maxlength :$.format("地址不能超过{0}个字符"),
					                required:'地址不能为空'
					            }
					        }
						
					    });
						
                        var flag = activity.form();
						if(flag) {
							$.ajax({
								  url: "${ctx}/u/template/saveTemplateDetail${ext}",   
						          type: "post",
						          data: {id:'${templateDetail.id }',
						        	  	 cell:$("#cell").val(),
						        	  	 address:$("#address").val(),
						        	  	 fontColor:$("#fontColor").val(),
						        	  	 backgroundColor:$("#backgroundColor").val()
						        	  	 },
						          dataType: "text",
						          contentType: "application/x-www-form-urlencoded; charset=utf-8",
						          success: function (data) {
						        	  	var result = eval('('+ data +')');
										if(result.success){
											art.dialog.tips("保存成功！");
										}else{
											alertMsg(result.info);
										}
						          },
						          error: function () {
						        	  alertMsg("服务器繁忙！");
						          }
							});
						} else{
                            //验证不通过
                            return false;
                        }
					},
					focus : true
				}, {
					name : '关闭'
				} ]
			});
		});

	//复制链接
	var host = window.location.host;
	var url = host + "/wap/${sessionScope.shopInfo.no}/any/index${ext}";
	$("#see-address").attr('data-clipboard-text',url);
	var client = new ZeroClipboard( document.getElementById("see-address") );
	client.on( "ready", function( readyEvent ) {
		client.on( "aftercopy", function( event ) {
			art.dialog.tips("复制成功！")
		});
	});
	
	$.puburl.setting.url="${ctx}/u/template/modular/${subCode}/sortModular${ext}";
});


function seeSignage(){
	
	$('#j-signage').show();
	$('#j-signage button').each(function(index){
		if(index == 0){
			uploadimg($("#uploadButton"));
		}
	})
	$('#j-signage button').eq(1).click(function(){
		$('#j-signage').hide();
	})
}
/**
 *获取提交数据
 */
function getJson(val) {
	var obj = {'owTemplateCode' : val, 'previewType' : 'template'};
	var jsondata = JSON.stringify(obj);
	return jsondata;
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
		<div class="col-md-2 ">
			<!--nav-left -->
			<%@include file="../../common/left.jsp"%>
			<!--nav-left end-->
		</div>
		<div class="col-md-10">
				<div class="row">
						<c:set var="crumbs" value="homepagemodular"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">标准模块配置</a></li>
								<li class="text-muted">备注： 设置好类别，系统会自动生成一个首页分类列表模块。</li>
							</ul>
						</div>
						<div class="content">
							<ul class="pager">
								<li class="next">
									<button type="button" data-clipboard-text="www.baidu.com" class="btn btn-default" id="see-address">复制链接地址</button>
<!-- 									<button type="button" class="btn btn-default" id="preview">预览</button> -->
									<button type="button" class="btn btn-default" id="resest" onclick="reset()">重置</button>
									<c:if test="${owTemplateHp.isChange == 1 }">
										<a  href="${ctx }/u/template/${subCode}/1/addModular${ext}" class="btn btn-default" data-format="2,3,4,5" >添加模块</a>
									</c:if>
									<c:if test="${owTemplateHp.isShopStrokes == 1 }">
										<button type="button" class="btn btn-default btn-danger" data-format="2"  onclick="seeSignage()" >设置店招</button>
									</c:if>
									<c:if test="${owTemplateHp.isContactWay == 1 }">
										<button type="button" class="btn btn-default btn-danger add-contact" data-format="3" >添加/修改联系方式</button>
									</c:if>
								</li>
							</ul>
							<table class="table table-bordered" id="template">
								<thead>
									<tr>
										<th width="20%">模块名称</th>
										<c:if test="${owTemplateHp.isImage == 1 }">
											<th width="">模块图标</th>
										</c:if>
										<th width="20%">字体颜色</th>
										<c:if test="${owTemplateHp.isChange == 1 }">
											<th width="12%">排序</th>
										</c:if>
										<th width="15%">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${standardModularList }" var="data">
									<tr>
										<td>${data.nameZh }</td>
										<c:if test="${owTemplateHp.isImage == 1 }">
											<td>
												<div style="width:80px; height:80px;border:0px solid #ccc;margin: auto;" class="table-img">
													<c:choose>
														<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
															<img src="${picUrl }${data.imgPath }" alt="" style="max-width: 80px;max-height: 80px;">
														</c:when>
														<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 1 }">
															<img src="${data.imgPath }" alt="" style="max-width: 80px;max-height: 80px;">
														</c:when>
														<c:otherwise>
															<img src="${resourceBasePath }img/goods.jpg" alt="" style="max-width: 80px;max-height: 80px;">
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</c:if>
										<td><input style="background-color:#FFFFFF" type="text" value="${data.nameZhColor }" disabled="disabled" class="form-control"></td>
										<c:if test="${owTemplateHp.isChange == 1 }">
											<td>
												<input type="hidden" name="modularId" value="${data.id }"/>
												<input type="hidden" move-row="moveId" value="${data.id }"/>
												<input type="hidden" move-row="moveSort" name="sort" value="${data.sort }"/>
												<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath }img/up.jpg" alt=""></a>
												<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath }img/down.jpg" alt=""></a>
											</td>
										</c:if>
										<td>
											<a href="${ctx }/u/template/${subCode}/1/editModular/${data.id}${ext}" class="btn-editor" >编辑</a>
											<c:if test="${owTemplateHp.isChange == 1 }">
												<a href="javascript:void(0)" class="btn-del" onclick="del(this)">| 删除</a>
											</c:if>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<c:if test="${owTemplateHp.isUserDefined == 1 }">
						<div class="box" data-format="1,2,4">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">自定义模块设置</a></li>
								</ul>
							</div>
							<div class="content">
								<ul class="pager">
									<li class="next">
										<a class="btn btn-default" href="${ctx }/u/template/${subCode}/2/addModular${ext}">添加模块</a>
									</li>
								</ul>
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="20%">模块名称</th>
											<c:if test="${owTemplateHp.isImageCustom == 1 }">
												<th width="">模块图标</th>
											</c:if>
											<th width="12%">排序</th>
											<th width="15%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${customModularList }" var="data">
											<tr>
												<td>${data.nameZh }</td>
												<c:if test="${owTemplateHp.isImageCustom == 1 }">
													<td>
														<div style="width:80px; height:80px;border:0px solid #ccc;margin: auto;" class="table-img">
															<c:choose>
																<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
																	<img src="${picUrl }${data.imgPath }" alt="" style="max-width: 80px;max-height: 80px;">
																</c:when>
																<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 1 }">
																	<img src="${data.imgPath }" alt="" style="max-width: 80px;max-height: 80px;">
																</c:when>
																<c:otherwise>
																	<img src="${resourceBasePath }img/goods.jpg" alt="" style="max-width: 80px;max-height: 80px;">
																</c:otherwise>
															</c:choose>
														</div>
													</td>
												</c:if>
												<td>
													<input type="hidden" name="modularId" value="${data.id }"/>
													<input type="hidden" move-row="moveId" value="${data.id }"/>
													<input type="hidden" move-row="moveSort" name="sort" value="${data.sort }"/>
													<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath }img/up.jpg" alt=""></a>
													<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath }img/down.jpg" alt=""></a>
												</td>
												<td>
													<a href="${ctx }/u/template/${subCode}/2/editModular/${data.id}${ext}" class="btn-editor" >编辑 | </a>
													<a href="javascript:void(0)" class="btn-del" onclick="del(this)">删除</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
				</div>
		</div>
	</div>
</div>

<div id="j-contact" style="display:none;">
	<form action="" mothed="post" id="j-activity">
		<input type="hidden" name="id" value="${templateDetail.id }"/>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-md-2 control-label">电话：</label>
				<div class="col-md-10">
					<input type="text" value="${templateDetail.cell }" id="cell" name="cell" class="form-control input-short-6">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">地址：</label>
				<div class="col-md-10">
					<input type="text" value="${templateDetail.address }" id="address" name="address" class="form-control input-short-6">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">文字颜色：</label>
				<div class="col-md-10">
					<input class="color form-control input-short-3" name="fontColor" value="${templateDetail.fontColor }" id="fontColor" autocomplete="off" style="color: rgb(255, 255, 255); background-color: rgb(51, 51, 51);">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label">背景颜色：</label>
				<div class="col-md-10">
					 <p><input type="text" class="color  form-control input-short-3" name="backgroundColor" value="${templateDetail.backgroundColor }" id="backgroundColor" onchange="
							document.getElementById('red').value = parseInt(this.color.rgb[0]*255);
							document.getElementById('grn').value = parseInt(this.color.rgb[1]*255);
							document.getElementById('blu').value = parseInt(this.color.rgb[2]*255);
							document.getElementById('myOpacityBgColor').style.backgroundColor = 'rgb('+parseInt(this.color.rgb[0]*255)+','+parseInt(this.color.rgb[1]*255)+','+parseInt(this.color.rgb[2]*255)+')';" autocomplete="off" style="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255);">
					</p>
					<br>
					<p>
						R：<input type="text" value="255" id="red" size="5" class="form-control input-short-2" disabled="disabled">  
						G：<input type="text" value="255" id="grn" size="5" class="form-control input-short-2" disabled="disabled">  
						B：<input type="text" value="255" id="blu" size="5" class="form-control input-short-2" disabled="disabled">
					</p>
			</div>
		</div>
		</div>
	</form>
</div>

<!-- 设置店招 -->
<div id="j-signage" style="display: none;">
	<div class="j-signage" >
		<div class="info">
			<p class="title">请上传图片</p>
			<p class="pic" style="width:320px; height:125px;border:0px solid #ccc;margin: auto;">
				<c:choose>
					<c:when test="${fn:contains(templateDetail.shopStrokesPath, 'base/img') }">
						<img src="${templateDetail.shopStrokesPath }" id="shopStrokes" alt="" style="max-width: 320px;max-height: 125px;">
					</c:when>
					<c:otherwise>
						<img src="${picUrl }${templateDetail.shopStrokesPath }" id="shopStrokes" alt="" style="max-width: 320px;max-height: 125px;">
					</c:otherwise>
				</c:choose>
				<input type="hidden" id="shopStrokesPath" />
			</p>
			<p class="hint">提示尺寸：640*250px</p>
			<div class="btn-upload text-center">
				<button type="button" id="uploadButton" class="btn btn-default btn-danger">上传</button>
				<button type="button" class="btn btn-default">取消</button>
			</div>
		</div>
		
	</div>
	
</div>
<!-- body end -->
<!-- footer -->
<%@include file="../../common/footer.jsp"%>
<!-- footer end -->
<script>

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
	
	/*重置*/
	function reset(){
		art.dialog.confirm('确认重置？', function() {
			location.href="${ctx}/u/template/modular/${subCode}/resetModular${ext}";
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}

	/*删除*/
	function del(el) {
		art.dialog.confirm('确认删除？', function() {
			var len=$("#template tbody tr").length;
			if(len<2){
				art.dialog.tips("对不起，至少保留一条记录！");
				return;
			}
			
			var tr = $(el).parent("td").parent("tr");
			var id = tr.find("input[name='modularId']").val();
			if(!id){
				tr.remove();
				return;
			}
			$.post("${ctx}/u/template/modular/${subCode}/deleteModular${ext}",
	  			  {id:id}, 
	  			  function(data){
	  				if(data == 1){
	  					$(tr).remove();
	  				}else{
	  					art.dialog.tips('删除失败');
	  				}
	  		});
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
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
	        		var src_path = '${picUrl}'+response.source;
	    			$.post("${ctx}/u/template/saveTemplateDetail${ext}",
	        				{id:"${templateDetail.id }",
	        				shopStrokesPath: response.path},
	        				function(data){
	        					var result = eval('('+ data +')');
	        					if(result.success){
	        						$("#shopStrokes").attr('src', src_path);
	        						art.dialog.tips('上传店招成功！');
	        	  				}else{
	        	  					art.dialog.tips('上传店招失败！');
	        	  				}
	        				} 
	        		);
	    		} else {
					art.dialog.alert(response.info);
				}
	        }
		});
	}
</script>
</body>
</html>

