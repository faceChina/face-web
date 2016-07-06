<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品分类--新增/编辑</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript"
	src="${resourceBasePath}js/ajaxupload3.9.js"></script>
<script type="text/javascript">
    var activity;
	$(function() {
		tab("appointment-goods");
		//上传图片 提示文字
		$('.j-tooltip input').tooltip({
			placement : "right"
		})
		activity = $("#j-activity").validate({
			rules : {
				name : {	
					required : true
				}
			},
			messages : {
				name : {
					required : '商品分类不能为空'
				}
			}
		});
		//图片上传
		$("#j-activity").find("input[name='uploadImg']").each(function(index) {
			imgUpload($(this));
		});
	});
	function submitForm() {
		var flag = activity.form();
		if (flag) {
			var imgPath = $('#imgPath').val();
			if (null == imgPath || imgPath == "") {
				art.dialog.alert("分类图片不能为空");
				return;
			}
			$('#submitButton').attr('disabled','disabled');
			$(".j-loading").show();
			$('#j-activity').submit();
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
						<c:set var="crumbs" value="ordershop"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">商品分类管理</a></li>
							</ul>
						</div>
						<div class="content">
						<c:if test="${empty shopType.id}">
							<form action="${ctx}/u/good/shopType/add${ext}" method="post" id="j-activity">
						</c:if>
						<c:if test="${not empty shopType.id}">
							<form action="${ctx}/u/good/shopType/edit/${shopType.id}${ext}" method="post" id="j-activity">
						</c:if>
								<input type="hidden" name="serviceId" value="2"/>
								<div class="form-horizontal">
									<div class="form-group">
										<label class="col-md-2 control-label"><b
											class="clr-attention">*</b> 商品分类：</label>
										<div class="col-md-10">
											<input type="text" value="${shopType.name}" name="name"
												class="form-control input-short-6"  maxlength="10"> <span style="color: #666666;">最多10个字</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label"><b
											class="clr-attention">*</b> 分类图片：</label>
										<div class="col-md-3">
											<div class="j-picpath"
												style="width: 150px; height: 150px; border: 1px solid #ccc;">
												<c:if
													test="${fn:contains(shopType.imgPath, 'image')==false}">
													<img id="showImg" src="${shopType.imgPath}" alt="">
												</c:if>
												<c:if test="${fn:contains(shopType.imgPath, 'image')}">
													<img id="showImg" src="${picUrl }${shopType.imgPath}"
														alt="">
												</c:if>
											</div>
										</div>
										<div class="col-md-6" style="margin-left: 20px;">
											<button type="button" class="btn btn-default"
												style="display: block; margin-bottom: 10px;"
												onclick="showIcons()">选择图标</button>
											<div class="btn btn-default btn-upload j-tooltip">
												上传图片<input type="button" name="uploadImg"
													class="form-control" data-toggle="tooltip"
													data-original-title="请上传尺寸大小为 ${owTemplateGt.goodTypeImageWidth}*${owTemplateGt.goodTypeImageHeight} 像素的图片，以png格式为准。" />
												<a href="#" title=""></a>
											</div>
										</div>

									</div>
											
									<div class="form-group">
										<div class="col-md-10 col-md-offset-2">
											<button id="submitButton" type="button" onclick="submitForm()" class="btn btn-default">确认保存</button>
											<a href="javascript:void(0);" onclick="cancel()" class="btn btn-default">取消返回</a>
										</div>
									</div>
								</div>
								<input type="hidden" id="imgPath" name="imgPath"
									value="${shopType.imgPath}" /> <input type="hidden" id="isPic"
									name="isPic" value="false" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- body end -->
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->

	<script type="text/javascript">
		function cancel() {
			location.href = '${ctx}/u/appoint/good-type-list${ext}';
		}
		/*--------------------------------------图标选择-------------------------------------------------*/
		//图标选择
		function showIcons() {
			art.dialog.open('${ctx}/u/good/shopType/tempImgList${ext}', {
				title : '选择素材',
				width : '800px',
				height : '300px',
				// 在open()方法中，init会等待iframe加载完毕后执行
				ok : function() {
					var picpath = $('.aui_content iframe').contents().find(
							'.addicon img').attr('src');
					$('.j-picpath img').attr('src', picpath);
					$('#imgPath').val(picpath);
					$('#isPic').val(false);
				},
				cancel : true
			});
		}

		//图片上传
		function imgUpload(uploadBtn) {
			var url = "/any/files/upload${ext}";
			new AjaxUpload(uploadBtn, {
				action : url,
				data : {},
				autoSubmit : true,
				responseType : 'json',
				onSubmit : function(file, ext) {					
					var imageSuffix = new RegExp('${imageSuffix}');
					if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
						art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
						return false;
					}
				},

				onComplete : function(file, response) {
					if (response.code == -1) {
						art.dialog.alert(response.desc);
						return;
					}
					if (response.flag == "SUCCESS") {
						var src_path = '${picUrl}' + response.source;
						console.log(src_path);
						$('#showImg').attr('src', src_path);
						$('#imgPath').val(response.path);
						$('#isPic').val(true);
					} else {
						art.dialog.alert(response.info);
					}
				}

			});
		}
	</script>
</body>
</html>
