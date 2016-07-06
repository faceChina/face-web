<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章管理-发布内容</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<!--top end -->
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";

	$(function(){
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
	<!-- body  -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
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
									<li class="active"><a href="#members-set" data-toggle="tab">编辑文章</a></li>
								</ul>
							</div>
							<div class="content">
								<form class="form-horizontal" id="j-form" action="${ctx}/u/stuff/article/editArticle${ext}" method="post">
									<input type="hidden" id="categoryItem" name="categoryItem" value=""/>
									<input type="hidden"  name="id" value="${news.id }"/>
									<input type="hidden"  name="shopNo" value="${news.shopNo }"/>
									<div class="form-group">
										<label for="inputText" class="col-md-2 control-label">文章标题</label>
										<div class="col-md-7">
											<input type="text" class="form-control" placeholder="" value="${news.title }" name="title"  id="title" onkeyup="toLimitString(this,30)">
										</div>
										<div class="col-md-3 help-block">
											<span class="fontcor-red">*</span>
											最多只能输入<span class="fontcor-red">30</span>个字符。
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputText" class="col-md-2 control-label">摘要</label>
										<div class="col-md-7">
											<textarea class="form-control" rows="3" name="introduction" id="textarea" onkeyup="toLimitString(this,60)">${news.introduction}</textarea>
										</div>
										<div class="col-md-3 help-block">
											  限制<span class="fontcor-red">60</span>字内，可不填
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputText" class="col-md-2 control-label">文章封面</label>
										<div class="col-md-2">
											   <c:choose>
												<c:when test="${news.picPath == null}">
													<img id="showImg" src="${resourceBasePath}img/base-photo.jpg" width="100" height="100" alt="" style="display: block;">
												</c:when>
												<c:otherwise>
													<img id="showImg" src="${picUrl}${news.picPath}" alt=""  width="100"  height="100"  style="display: block;">
												</c:otherwise>
												</c:choose>
										</div>
										<div class="col-md-3">
											<div class="uploadImg" style="display:block;">
												<div class="btn btn-default btn-upload">
													选择封面<input type="button"  id="uploadImage" name="uploadImg" class="form-control">
												</div>
											</div>
											<input type="hidden" id="picturePath" name="picPath" value=""/>
											<p class="help-block">推荐大小为400×400</p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="inputText" class="col-md-2 control-label">文章专题</label>
										<div class="col-md-7">
											<button class="btn btn-default" style="display:none;">您还没有创建分类，现在去添加</button>
											<div class="sm-table">
												<c:forEach items="${articleCategory}" var="data">
													<div class="col-md-6">
													<c:choose>
													<c:when test="${data.flag == '1' }">
														<label><input type="checkbox" checked value="${data.id}" name="categoryId" >${data.name}</label>
													</c:when>
													<c:otherwise>
														<label><input type="checkbox"  value="${data.id}" name="categoryId" >${data.name}</label>
													</c:otherwise>
													</c:choose>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
									
									 <div class="form-group">
										<div class="col-md-offset-2 col-md-2">
											<div class="radio">
											
												<label onclick="toShow()"> <input id="radio1" type="radio" name="type"  checked="checked" value="1"> 发布内容</label>
											</div>
										</div>
										<div class="col-md-offset-1 col-md-4">
											<div class="radio">
												<label onclick="toHide()"> <input id="radio2"  type="radio" name="type" value="2"> 发布链接</label>
											</div>
										</div>
									</div>


									<div class="form-group" id="artical-cont1">
										<label for="inputText" class="col-md-2 control-label">文章内容</label>
										<div class=" col-md-10" style="height:550px;">
											<script id="editor" type="text/plain" style="height:400px;">${news.articleContent}</script>
											<input type="hidden" id="content" name="articleContent" value=""/>
										</div>
									</div>

									<div class="form-group" id="artical-cont2" style="display: none;">
										<label for="inputText" class="col-md-2 control-label">文章链接：</label>
										<div class="col-md-10">
											<input type="text" value="${news.hyperlink}" class="form-control" name="hyperlink" id="link" placeholder="">
										</div>
									</div>
									<div class="text-center">
										<button type="submit" class="btn btn-default" id="submit">发布</button>
										<button type="button" id="" class="btn btn-default" onclick="canel()">取消</button>
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
	
	<script type="text/javascript">
	UE.getEditor('editor');
	function getContent() {
	    return UE.getEditor('editor').getContent();
	}

	
	$(document).ready(function(){ 
		var type = '${news.type}';
		if(type == 1){
			toShow();
			$("#radio1").attr("checked","checked");
		}
		if(type == 2){
			toHide();
			$("#radio2").attr("checked","checked");
		}
	});
	
	function canel(){
		art.dialog.confirm('确认取消正在编辑的文章？', function() {
			location.href="${ctx}/u/stuff/article/listArticle${ext}";
		}, function() {
			return true;
		});
	}
	
	//发布验证
		$(function(){
			$("#submit").click(function(event) {
				$("#content").val(getContent());
				
				var categoryStatusArr=[]
				$("#j-form").find('input:checkbox[name="categoryId"]').each(function(){
					if($(this).attr('checked')){
						categoryStatusArr.push($(this).val());
					}
				})
				$("#categoryItem").val(categoryStatusArr);
				var type=$("#j-form").find('input:radio[name="type"]:checked').val();
				var valiateFlag;
				if(1==type) {
					valiateFlag = $("#j-form").validate({
						rules : {
							title : {
								required: true,
								maxlength:30
							},
							articleContent : "required"
						},
						messages : {
							title : {
								required: "请输入标题！",
								maxlength:$.format("标题不能超过{0}个字符")
							},
							articleContent : "请输入文章内容！"
						}
					});
				} else {
					valiateFlag = $("#j-form").validate({
						rules : {
							title : {
								required: true,
								maxlength:30
							},
							hyperlink : {
								required: true,
								maxlength : 256
							}
						},
						messages : {
							title : {
								required: "请输入标题！",
								maxlength:$.format("标题不能超过{0}个字符")
							},
							hyperlink : {
								required: "请输入链接！",
								maxlength:$.format("链接不能超过{0}个字符")
								
							}
						}
					});
				}
				var flag = valiateFlag.form();
				if(flag) {
					$(".j-loading").show();
					$("#j-form").submit();
				};
			});
		});
	
	
	
	//取消
	function toReset(){
		$("#title").val("");
		$("#textarea").val("");
		$("#img").attr("src","");
		$("input[type='checkbox']:checked").each(function(){
			$(this).prop("checked",false);
		});
		$("#link").val("");
	}
	
	//显示隐藏
	function toShow(){
		$("#artical-cont1").css("display", "block");
		$("#artical-cont2").css("display", "none");
	}
	function toHide(){
		$("#artical-cont1").css("display", "none");
		$("#artical-cont2").css("display", "block");
	}
	
	//限字
	function toLimitString(thiz,num){
		var str = $(thiz).val();
		var len = str.length;
		if(len >= num){
			$(thiz).val(str.substring(0,num));
		}
	}
	</script>
	</body>
</html>

