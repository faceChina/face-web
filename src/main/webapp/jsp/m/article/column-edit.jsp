<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章栏目--编辑</title>
<!-- top -->
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath}plugins/updown.js"></script>
<!--top end -->
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";
$(function() {
	tab("article");
	$.puburl.setting.url="${ctx}/u/stuff/article/sortCategory${ext}";
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
								<form action="${ctx}/u/stuff/article/editColumn${ext}" method="post" id="j-activity">
								<input type="hidden" value="${data.articleTemplateType }" id="tempType" name="articleTemplateType"/>	
								
								<input type="hidden" value="${data.id }"  name="id"/>
								<input type="hidden" value="${data.shopNo }"  name="shopNo"/>
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-md-2 control-label">栏目名称：</label>
											<div class="col-md-10">
												<input type="text" value="${data.name }" name="name" class="form-control input-short-6">
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">分类图片：</label>
											
											<div class="col-md-3">
												<div class="j-picpath" style="width:150px; height:150px;border:1px solid #ccc;">
												<c:choose>
												<c:when test="${data.picPath == null}">
													<img id="showImg" src="${resourceBasePath}img/base-photo.jpg" width="100" height="100" alt="" style="display: block;">
												</c:when>
												<c:otherwise>
													<img id="showImg" src="${picUrl}${data.picPath}" alt=""  width="100"  height="100"  style="display: block;">
												</c:otherwise>
												</c:choose>
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
													<button type="button" class="btn btn-default addLinkToTemp" onclick="articleChange(this,'${data.id}')">更改专题</button>
													<button type="button" class="btn btn-default addLinkToTemp" onclick="articleSort(this,'${data.id}')">排序</button>
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
											    <button type="submit" name="" class="btn btn-default" id="submitColumn" onclick="saveColumn()">保存</button>
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
		<input type="hidden" value="" id="categoryItem" name="categoryItem"/>
		<div class="row" style="margin:10px 0;">
			<div class="col-xs-5" style="line-height:34px;">链接至文章列表页（可多选）</div>
			<div class="col-xs-6"><input class="form-control input-short-11" id="sosoTitle" type="text" placeholder="请输入标题关键字"></div>
			<input type="hidden" value="" id="sosoId" name="sosoId"/>
			<div class="col-xs-1"><button type="button" onclick="sosoColumn()"  class="btn btn-default">搜索</button></div>
		</div>
		<ul class="list" id="articleList">
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
	
	
		<!-- 添加链接至模块  文章列表  排序 -->
	<div id="j-articleSort" style="display:none;">
		<table class="table table-bordered">
			<thead>
				<tr>
					<td>专题标题</td>
					<td>排序</td>
				</tr>
			</thead>
			<tbody id="categorySort">
			</tbody>
		</table>
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
					}
				},
				messages : {
					name : {
						required: "请输入标题！",
						maxlength:$.format("标题不能超过{0}个字符")
					}
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
		title:"更改模板",
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

//添加链接至文章列表页 排序
function articleSort(thiz,id) {
	$.post("${ctx}/u/stuff/article/getSortCategorys${ext}",{"columnId":id},function(result){
 		var data = JSON.parse(result);
		var htmlString = "";
		for(var key in data){
			var shtml = '<tr><td style="text-align: left;">'+data[key].name+'</td>'
			+'<td width="100">'
			+'<input type="hidden" move-row="moveId" name="moveId" value="'+data[key].relationId+'"/>'
			+'<input type="hidden" move-row="moveSort" name="moveSort" value="'+data[key].sort+'"/>'
			+'<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath }img/up.jpg" alt=""></a>'
			+'<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath }img/down.jpg" alt=""></a>'
			+'</td></tr>'; 	
			htmlString += shtml;

		}
	$("#categorySort").html(htmlString); 
	art.dialog({
		lock : true,
		width : '600px',
		title : "添加链接至文章列表页",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-articleSort"),
		button : [ {
			name : '保存',
			callback : function() {

			},
			focus : true
		}, {
			name : '取消'
		} ]
	});
	
	});
}


//添加链接至文章列表页
function articleChange(thiz,id) {
	var sosotitle = $("#sosoTitle").val();
	$.post("${ctx}/u/stuff/article/selectCategory${ext}",{"columnId":id,"title":sosotitle},function(result){
		$("#sosoId").val(id);
		var newsList = JSON.parse(result);
		var printHtml = "";
		for(var key in newsList){
			if(newsList[key].flag == "1"){
				var shtml = '<li><label><input type="checkbox" checked name="article" value="'+ newsList[key].id +'" />'
		       	+ newsList[key].name + '</label></li>';
			}else{
				var shtml = '<li><label><input type="checkbox" name="article" value="'+ newsList[key].id +'" />'
		       	+ newsList[key].name + '</label></li>';
			}
			printHtml += shtml;
		}
		
		$("#articleList").html(printHtml);
		alertCategory(id);

	});
}


function alertCategory(id){
	art.dialog({
		lock : true,
		width : '600px',
		title : "添加链接至专题列表页",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-articleChange"),
		button : [ {
			name : '保存',
			callback : function() {
				var categoryStatusArr=[]
				$("#articleList").find('input:checkbox[name="article"]').each(function(){
					if($(this).attr('checked')){
						categoryStatusArr.push($(this).val());
					}
				})
				$("#categoryItem").val(categoryStatusArr);
				changeCategory(id);
				
			},
			focus : true
		}, {
			name : '取消'
		} ]
	});
}


function changeCategory(id){
	var categoryItem = $("#categoryItem").val();
	$.post("${ctx}/u/stuff/article/changeCategory${ext}",{"id":id,"categoryItem":categoryItem},function(result){
		if(result != "SUCCESS"){
			art.dialog.alert(result);
		}
	});
}


function sosoColumn(){
	var id = $("#sosoId").val();
	var sosotitle = $("#sosoTitle").val();
	$.post("${ctx}/u/stuff/article/selectCategory${ext}",{"columnId":id,"title":sosotitle},function(result){
		$("#sosoId").val(id);
		var newsList = JSON.parse(result);
		var printHtml = "";
		for(var key in newsList){
			if(newsList[key].flag == "1"){
				var shtml = '<li><label><input type="checkbox" checked name="article" value="'+ newsList[key].id +'" />'
		       	+ newsList[key].name + '</label></li>';
			}else{
				var shtml = '<li><label><input type="checkbox" name="article" value="'+ newsList[key].id +'" />'
		       	+ newsList[key].name + '</label></li>';
			}
			printHtml += shtml;
		}
		$("#articleList").html(printHtml);
	});
}

</script>
</body>
</html>
