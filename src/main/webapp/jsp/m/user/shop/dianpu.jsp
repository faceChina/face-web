<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>店铺管理-店铺简介</title>	
<!-- top -->
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("signPic-sc");
});
</script>
</head>
<body>	
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	
	<!-- body -->
	<div class="container">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
				<div class="row">
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">设置店铺招牌</a></li>
							</ul>
						</div>
						<div class="content">
							<form action="activity-bigwheel.html" mothed="" id="j-activity">
								<div class="form-horizontal">
									<div class="form-group">
										<label class="col-md-1 control-label"></label>
										<div id="ontemplate" class="col-md-4">
											<c:if test="${shop.isDefaultSignPic == 2 }">
												<img id="img" src="${picUrl }${shop.zoomSignPic}" width="95%" data-val="1" />
											</c:if>
											<c:if test="${shop.isDefaultSignPic == 1}">
												<img id="img" src="${shop.zoomSignPic}" width="95%" data-val="1" />
											</c:if>
											<c:if test="${shop.signPic == null }">
												<img id="img" src="${resourceBasePath }img/dianzhao/1.jpg" width="95%" data-val="1" />
											</c:if>
										</div>
										<div class="col-md-6">
											<div class="btn btn-default btn-upload">
												上传图片<input type="button" value="" id="uploadImage" name="uploadImage" class="form-control">
											</div>
											<span>建议尺寸：宽<span class="fontcor-red">640</span>像素，高<span class="fontcor-red">280</span>像素
											</span>
											<input type="hidden" id="signPic" value="${shop.zoomSignPic }">
											<input type="hidden" id="isDefaultSignPic" value="${shop.isDefaultSignPic }"> 
											<div class="media">
												<button type="button" class="btn btn-default" onclick="shopTemplateList()">选择模板</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-10 col-md-offset-2">
										<button type="button" class="btn btn-default" onclick="preview();">预览</button>
										<button type="button" class="btn btn-default" onclick="save(this)">保存</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 店招模板 -->
	<div id="shoptemplate" style="display: none;">
		<div class="shop-template-list">
			<ul>
				<c:forEach items="${srcList }" var="src" varStatus="status">
					<li <c:if test="${num == status.count }">class="active"</c:if> >
						<div class="template-pic">
							<span></span>
							<img src="${src }" alt="模板${status.count }">
						</div>
						<div class="template-text">
							<input type="hidden" name="temp${status.count }" value="${status.count }">模板${status.count }
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	<script type="text/javascript">
		//店铺招牌模版选择窗口
		function shopTemplateList(){
			art.dialog({
				lock : true,
				width : '836px',
				title:"店招模板选择",
				background : '#000', // 背景色
				opacity : 0.1, // 透明度
				content:document.getElementById("shoptemplate"),
				ok : function(){
					//art.dialog.alert('保存成功');
					//console.log('改变了模板')
					var tempname = $('.shop-template-list').find('.active input').val();
					var imgsrc = $('.shop-template-list .active').find('img').attr('src');
					$("img#img").attr({
						src: imgsrc,
						'data-val': tempname
					});
					$('#signPic').val(imgsrc);
					$("#isDefaultSignPic").val(1);
					console.log(imgsrc + ":" + tempname);
				},
				cancel : true
			});
		}
		
		var button = $('#uploadImage');
		new AjaxUpload(button, {
			action : '${uploadUrl}',
			data : {},
			onSubmit : function(file, ext) {
				var imageSuffix = new RegExp('${imageSuffix}');
				if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
					art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
					return false;
				}
			},
			autoSubmit : true,
			responseType : 'json',
			onChange : function(file, ext) {
			},
			onComplete : function(file, response) {
				if (response.flag == "SUCCESS") {
					$('#img').show();
					$('#img')
							.attr("src", '${picUrl}' + response.source);
					$('#signPic').val(response.path);
					$("#isDefaultSignPic").val(2);
				} else {
					art.dialog.alert(response.info);
					return;
				}
			}
		});

		$(function(){
			var val=$(".shop-template-list").find(".active").find("input[type='hidden']").val();//选中的模版的传递值
			$(".shop-template-list").find("li").each(function(index) {
				$(this).find("div").first().click(function(event) {
					$(".shop-template-list li").removeClass("active");
					$(this).parent("li").addClass("active");
					val=$(".shop-template-list").find(".active").find("input[type='hidden']").val();
				});
			});
		})
		function preview(){
			var val = $('#ontemplate').find('img').attr('data-val');
			var signPic = $("#signPic").val();
			var isDefaultSignPic = $("#isDefaultSignPic").val();
			var isPreview = 1;
			window.open("/wap/${shop.no}/any/gwscIndex.htm?signPic="+signPic+"&isDefaultSignPic="+isDefaultSignPic+"&isPreview="+isPreview,"_blank", "width=480,height=680");
// 			window.open("dianpuTemplate"+val+".html","模版"+val,"width=600,height=300");
		}

		function save() {
			var signPic = $("#signPic").val();
			var isDefaultSignPic = $("#isDefaultSignPic").val();
			$.post("signPic.htm", {"signPic":signPic, "isDefaultSignPic":isDefaultSignPic}, function(data) {
				if (data.success) {
					art.dialog.tips('保存成功');
				} else {
					art.dialog.tips('保存失败');
				}				
			},"json");
		}
	</script>
	</body>
</html>


