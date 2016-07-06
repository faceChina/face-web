<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章栏目--添加</title>
<!-- top -->
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<!--top end -->
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";
$(function() {
	tab("article");
});

$(function(){
	var button = $('#uploadImage');
		new AjaxUpload(button, {
	    action: url,
	    data: {
	    },
	    onSubmit: function(file,ext){
	    	var imageSuffix = new RegExp('${imageSuffix}');
	    	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
	        	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
	            return false;               
	        }
	    },
	    autoSubmit: true,
	    responseType: 'json',
	    onChange: function(file, ext){
	    },
	    onComplete: function(file, response){
	    	if(response.flag == "SUCCESS"){
	    		$('#showImg').show();
	    		$('#showImg').attr("src",'${picUrl}'+response.source);
	    		$('#picturePath').val(response.path);
	    	}else{
	    		art.dialog.alert(response.info);
	    		return ;
	    	}
	    }
	});
});
</script>
</head>
<body>
	<!-- header -->
		<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2">
				<!--nav-left -->
				<%@include file="../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="article"/>
						<%@include file="../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"data-toggle="tab">文章栏目管理</a></li>
								</ul>
							</div>
							<div class="content">
								<form action="${ctx}/u/stuff/article/addColumn${ext}" method="post" id="j-activity">
								<input type="hidden" value="1" id="tempType" name="articleTemplateType"/>	
								<input type="hidden" value="" id="categoryItem" name="categoryItem"/>
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-md-2 control-label">栏目名称：</label>
											<div class="col-md-10">
												<input type="text" value="" name="name" class="form-control input-short-6">
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">分类图片：</label>
											
											<div class="col-md-3">
												<div class="j-picpath" style="width:150px; height:150px;border:1px solid #ccc;">
													 <img id="showImg" src="${resourceBasePath}img/base-photo.jpg" width="100" height="100" alt="" style="display: block;">
												</div>
											</div>
											<div class="col-md-6" style="margin-left:20px;">
												<div class="btn btn-default btn-upload j-tooltip">
													上传图片<input type="button"  id="uploadImage" name="uploadImg" class="form-control"  data-toggle="tooltip" data-original-title="请上传尺寸大小为 80*80 像素的图片，以png格式为准。">
												</div>
												<p><small>请上传尺寸大小为 640*380 像素的图片</small></p>
												<input type="hidden" id="picturePath" name="picPath" value=""/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">专题选择：</label>
											<div class="col-md-10">
												<div class="article-change" style="display: block;">
													<button type="button" class="btn btn-default addLinkToTemp" onclick="articleChange()">更改专题</button>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">模板选择：</label>
											<div class="col-md-10">
												<div class="article-change" style="display: block;">
													<button type="button" class="btn btn-default addLinkToTemp" onclick="popTemplateList()">更改模板</button>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-10 col-md-offset-2">
												<button type="submit" name="" id="submitColumn" onclick="saveColumn()" class="btn btn-default">保存</button>
												<a href="javascript:history.go(-1)" class="btn btn-default">返回</a>
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
	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<!-- footer end -->
	
	<!-- 添加链接至模块  文章列表 -->
	<div id="j-articleChange" style="display:none;">
		<ul class="list" id="categoryList">
		</ul>
	</div>
	
	
	<!-- 选择模版 -->
	<div class="content tab-content" id="poptemplate" style="display:none;">
		<div class="tab-pane fade in active panel-body" id="temp1">
			<ul class="row templatelist">
				<li  data-id="1">
					<div>
						<span></span>
						<img src="${resourcePath}img/article-temp1.jpg" alt="模板1">
					</div>
					<div>
						 模板1
					</div>
				</li>
				<li data-id="2">
					<div>
						<span></span>
						<img src="${resourcePath}img/article-temp2.jpg" alt="模板2">
					</div>
					<div>
						模板2
					</div>
				</li>
				<li data-id="3">
					<div>
						<span></span>
						<img src="${resourcePath}img/gw/article-temp3.jpg" alt="模板3">
					</div>
					<div>
						 模板3
					</div>
				</li>	
				
				<li data-id="4">
					<div>
						<span></span>
						<img src="${resourcePath}img/article-temp4.jpg" alt="模板4">
					</div>
					<div>
						 模板4
					</div>
				</li>									
			</ul>						
		</div>
	</div>

	
<script type="text/javascript">


//发布验证
var validateFlag;
$(function(){
	validateFlag = $("#j-activity").validate({
				rules : {
					name : {
						required: true,
						maxlength:30
					},
					picPath : "required"
				},
				messages : {
					name : {
						required: "请输入标题！",
						maxlength:$.format("标题不能超过{0}个字符")
					},
					picPath : "请上传图片"
				}
			});
	});
function saveColumn() {
	var flag = validateFlag.form();
	if(flag) {
		$(".j-loading").show();
		$("#j-activity").submit();
	}
}


//模板选择
var tempType=$('#tempType');
var temp=$('#temp1 .templatelist li')
temp.each(function(){
	if($(this).data('id')==tempType.val()){
		$(this).addClass('active');
	}
})

//弹出模版选择窗口
function popTemplateList(){
	art.dialog({
		title:'更改模板',
		content:document.getElementById("poptemplate"),
		button:[{
			name:"保存",
			focus:true,
			callback:function(){
				tempType.val($("#temp1 .templatelist li.active").data('id'));//获取选中的id
			}
		}]
	});
}
var val=$(".templatelist").find(".active").find("input[type='hidden']").val();//选中的模版的传递值
$('.templatelist').find("li").each(function(index) {
	$(this).find("div").first().click(function(event) {
		$('.templatelist li').removeClass("active");
		$(this).parent("li").addClass("active");
		val=$(".templatelist").find(".active").find("input[type='hidden']").val();
	});
});

//添加链接至文章列表页
function articleChange() {
	$.post("${ctx}/u/stuff/article/selectAllCategory${ext}",function(result){
		var newsList = JSON.parse(result);
		var printHtml = "";
		for(var key in newsList){
			var shtml = '<li><label><input type="checkbox" name="article" value="'+ newsList[key].id +'" />'
			       	+ newsList[key].name + '</label></li>';
			printHtml += shtml;
		}
		
		$("#categoryList").html(printHtml);
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至文章列表页",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-articleChange"),
			button : [ {
				name : '保存',
				callback : function() {
					var categoryStatusArr=[]
					$("#categoryList").find('input:checkbox[name="article"]').each(function(){
						if($(this).attr('checked')){
							categoryStatusArr.push($(this).val());
						}
					})
					$("#categoryItem").val(categoryStatusArr);
				},
				focus : true
			}, {
				name : '取消'
			} ]
		});
	});
}
</script>
</body>
</html>
