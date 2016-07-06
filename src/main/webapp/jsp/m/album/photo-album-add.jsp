<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>相册专题--添加</title>
<!-- top -->
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<!--top end -->
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";
$(function() {
	tab("photo");

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
		
	var val = $("#j-activity").find('input[name="articleTemplateType"]').val();
	if(val == null || val == ''){
		$("#j-activity").find('input[name="articleTemplateType"]').val(1);
	}
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
						<c:set var="crumbs" value="albumn"/>
						<%@include file="../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"data-toggle="tab">相册专辑管理</a></li>
								</ul>
							</div>
							<div class="content">
								<form method="post" id="j-activity">
								<input type="hidden" value="${photoAlbum.articleTemplateType }" id="tempType" name="articleTemplateType"/>	
								<input type="hidden" value="${photoAlbum.albumItem }" id="albumItem" name="albumItem"/>
								<input type="hidden" value="${photoAlbum.id }" name=id/>
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-md-2 control-label">专辑名称：</label>
											<div class="col-md-10">
												<input type="text" value="${photoAlbum.name }" name="name" class="form-control input-short-6">
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">分类图片：</label>
											
											<div class="col-md-3">
												<div class="j-picpath" style="width:150px; height:150px;border:1px solid #ccc;">
													<c:choose>
														<c:when test="${not empty photoAlbum }">
															<img id="showImg" src="${picUrl }${photoAlbum.picPath }" width="100" height="100" alt="" style="display: block;">
														</c:when>
														<c:otherwise>
															 <img id="showImg" src="${resourceBasePath}img/base-photo.jpg" width="100" height="100" alt="" style="display: block;">
														</c:otherwise>
													</c:choose>
												</div>
											</div>
											<div class="col-md-6" style="margin-left:20px;">
												<div class="btn btn-default btn-upload j-tooltip">
													上传图片<input type="button"  id="uploadImage" name="uploadImg" class="form-control"  data-toggle="tooltip" data-original-title="请上传尺寸大小为 80*80 像素的图片，以png格式为准。">
												</div>
												<p>请上传尺寸大小为 400*400 像素的图片。</p>
												<input type="hidden" id="picturePath" name="picPath" value="${photoAlbum.picPath }"/>
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-2 control-label">相册选择：</label>
											<div class="col-md-10">
												<div class="article-change" style="display: block;">
													<button type="button" class="btn btn-default addLinkToTemp" onclick="articleChange(this)">更改相册</button>
													<button type="button" class="btn btn-default addLinkToTemp" onclick="articleSort(this)">排序</button>
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
											    <button onclick="mySubmit();" name="" class="btn btn-default" id="submit">保存</button>
												<a href="${ctx }/u/stuff/album/listPhotoAlbum${ext}" class="btn btn-default">返回</a>
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
	
	<!-- 添加链接至模块  相册列表 -->
	<div id="j-articleChange" style="display:none;">
		
		<div class="row" style="margin:10px 0;">
			<div class="col-xs-5" style="line-height:34px;">链接至相册列表页（可多选）</div>
			<div class="col-xs-6"><input class="form-control input-short-11" type="text" name="name" placeholder="请输入标题关键字"></div>
			<div class="col-xs-1"><button type="button" onclick="searchAlbum();" class="btn btn-default">搜索</button></div>
		</div>
		<ul class="list" id="albumList">
		</ul>
		
	</div>
	
	<!-- 选择模版 -->
	<div class="content tab-content" id="poptemplate" style="display:none;">
		<div class="tab-pane fade in active panel-body" id="temp1">
			<ul class="row templatelist">
				<li data-id="1">
					<div>
						<span></span>
						<img src="${resourcePath}img/gw/shop-temp1.jpg" alt="模板1">
					</div>
					<div>
						 模板1
					</div>
				</li>
				<li data-id="2">
					<div>
						<span></span>
						<img src="${resourcePath}img/gw/shop-temp2.jpg" alt="模板2">
					</div>
					<div>
						模板2
					</div>
				</li>
				<li data-id="3">
					<div>
						<span></span>
						<img src="${resourcePath}img/gw/shop-temp3.jpg" alt="模板3">
					</div>
					<div>
						模板3
					</div>
				</li>									
			</ul>	
							
		</div>
	</div>

	<!-- 相册  排序 -->
	<div id="j-articleSort" style="display:none;">
		<table class="table table-bordered">
			<thead>
				<tr>
					<td>相册标题</td>
					<td>排序</td>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>
<script type="text/javascript">
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
	var articleTemplateType = $("#j-activity").find('input[name="articleTemplateType"]').val();
	articleTemplateType == null || articleTemplateType == '' ? 1 : articleTemplateType;
	$('.templatelist').find('li[data-id="'+articleTemplateType+'"]').each(function(index){
		$('.templatelist li').removeClass("active");
		$(this).addClass("active");
	});
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

//添加相册 排序
function articleSort() {
	//判断是否已选相册
	var albumItem = $("#j-activity").find('input[name="albumItem"]').val();
	if(!albumItem){
		art.dialog.alert("请先选择相册");
		return;
	}
	//查询相册信息
	$.post("${ctx}/u/stuff/album/findAlbumByIds${ext}",{"ids":albumItem},function(result){
		var arr = JSON.parse(result);
		var html = "";
		var albumItems = albumItem.split(",")
		for(var j in albumItems){
			for(var i in arr){
				if(albumItems[j] != arr[i].id)continue;
				var album = arr[i];
				html += 
					'<tr data-id="'+album.id+'">' +
						'<td style="text-align: left;">'+album.name+'</td>' +
						'<td width="100">' +
							'<a href="javascript:void(0)" onclick="upFirst(this)"><img src="${resourcePath }img/up-first.jpg" alt=""></a>' +
							'<a href="javascript:void(0)" onclick="up(this)"><img src="${resourcePath }img/up.jpg" alt=""></a>' +
							'<a href="javascript:void(0)" onclick="down(this)"><img src="${resourcePath }img/down.jpg" alt=""></a>' +
							'<a href="javascript:void(0)" onclick="downLast(this)"><img src="${resourcePath }img/down-last.jpg" alt=""></a>' +
						'</td>' +
					'</tr>';
			}
		}
		$("#j-articleSort").find("tbody").html(html);
	});
	art.dialog({
		lock : true,
		width : '600px',
		title : "相册排序",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-articleSort"),
		button : [ {
			name : '保存',
			callback : function() {
				var ids = "";
				$("#j-articleSort").find("tbody tr").each(function(index){
					ids += $(this).attr("data-id")+",";
				});
				$("#j-activity").find('input[name="albumItem"]').val(ids);
			},
			focus : true
		}, {
			name : '取消'
		} ]
	});
}
/*上移*/
function up(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var index = tr.index();
	if (index != 0) {
		tr.insertBefore(tr.prev());
	} else {
		return false;
	}
}

/*下移*/
function down(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var len = tbody.find("tr").length;
	var index = tr.index();
	if (index != len - 1) {
		tr.insertAfter(tr.next());
	} else {
		return false;
	}
}

/*上移置顶*/
function upFirst(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var index = tr.index();
	if (index != 0) {
		tr.prependTo(tbody);
	} else {
		return false;
	}
}

/*下移置底*/
function downLast(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var len = tbody.find("tr").length;
	var index = tr.index();
	if (index != len - 1) {
		tr.appendTo(tbody);
	} else {
		return false;
	}
}
function searchAlbum(){
	var name = $('#j-articleChange').find('input[name="name"]').val();
	$.post("${ctx}/u/stuff/album/queryAlbumList${ext}",{'name':name},function(result){
		var newsList = JSON.parse(result);
		var printHtml = "";
		for(var key in newsList){
			var shtml = '<li><label><input type="checkbox" name="albumId" value="'+ newsList[key].id +'" />'
			       	+ newsList[key].name + '</label></li>';
			printHtml += shtml;
		}
		$("#albumList").html(printHtml);
	});
}

//选择相册
function articleChange() {
	searchAlbum();
	var albumItem = $("#j-activity").find('input[name="albumItem"]').val().split(",");
	$("#albumList").find('input[type="checkbox"]').each(function(index){
		for(var i in albumItem){
			if(!albumItem[i])continue;
			if(albumItem[i] == $(this).val()){
				$(this).attr("checked",true);
			}
		}
	});
	art.dialog({
		lock : true,
		width : '600px',
		title : "选择相册",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-articleChange"),
		button : [ {
			name : '保存',
			callback : function() {
				var categoryStatusArr=[]
				$("#albumList").find('input:checkbox[name="albumId"]').each(function(){
					if($(this).attr('checked')){
						categoryStatusArr.push($(this).val());
					}
				})
				$("#albumItem").val(categoryStatusArr);
			},
			focus : true
		}, {
			name : '取消'
		} ]
	});
}

function mySubmit(){
	var flag = $("#j-activity").validate({
		rules : {
			name : {
				required: true,
				maxlength:10
			},
			picPath : "required"
		},
		messages : {
			name : {
				required: "请输专题名称！",
				maxlength:$.format("标题不能超过{0}个字符")
			},
			picPath : "请上传图片"
		}
	}).form();
	if(flag){
		$(".j-loading").show();
		$("#j-activity").submit();
	}
}
</script>
</body>
</html>
