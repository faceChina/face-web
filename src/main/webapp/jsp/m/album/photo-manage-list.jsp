<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>相册管理</title>	
<!-- top -->
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath}js/allSelect.js"></script>
<script type="text/javascript" src="http://apps.bdimg.com/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";
$(function(){
	tab("photo");
	upladImg($('#uploadImage'));
	upladImg($('#uploadAlbumImage'));
});

function upladImg(button){
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
	    		if($(button).attr("data-id") == "photo"){
		    		$('#uploadPhoto').find('input[name="path"]').val(response.path);
		    		$('#uploadPhoto').find('input[name="type"]').val(response.type);
		    		$("#uploadPhoto").submit();
	    		} else {
	    			$('#showImg').show();
		    		$('#showImg').attr("src",'${picUrl}'+response.source);
		    		$('#picturePath').val(response.path);
	    		}
	    	}else{
	    		art.dialog.alert(response.info);
	    		return ;
	    	}
	    }
	});
}
</script>
<!--top end -->
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
									<li class="active"><a href="${ctx}/u/stuff/album/listAlbum${ext}">相册管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/album/listPhotoAlbum${ext}">相册专辑管理</a></li>
									<li class=""><a href="${ctx}/u/stuff/album/listPhoto${ext}">所有图片</a></li>
								</ul>
							</div>
							<div class="content" plugin="allSelect">
								<ul class="pager">
									<li class="previous">
										<a href="${ctx}/u/stuff/album/listAlbum${ext}" style="border:none;">${album.name }</a>
										<a href="javascript:void(0)" style="border:none;" onclick="toEditPhoneAlbum(${album.id})">编辑相册</a>
										<a href="javascript:void(0)" style="border:none;" onclick="toDelPhoneAlbum(${album.id})">删除相册</a>
									</li>
									<li class="next">
										<div class="uploadImg pull-right" style="display:inline-block;">
											<div class="btn btn-default btn-upload">
												上传图片<input type="button" id="uploadImage" data-id="photo" value="" class="form-control" data-tips="请上传尺寸大小为 **** 像素的图片，以png格式为准。"/>
											</div>
										</div>
										<div class="pull-right" style="margin-right:10px;">大小：不超过2M，格式：png，jpeg，jpg</div>
									</li>
								</ul>
								
								<table class="table">
									<thead>
										<tr>
											<th style="text-align:left;">
												<span style="border:none;"><label><input type="checkbox" name="inlineRadioOptions" id="" value="" pluginHandles="handles"> 全选</label></span>
												<button type="button" class="btn btn-default " onclick="toMovePhotos()">批量移动到相册</button>
												<button type="button" class="btn btn-default " onclick="toDelPhotos()">批量删除</button>
											</th>
										</tr>
									</thead>
								</table>
								
								<ul class="album clearfix" id="j-album" pluginContent="content">
								
								<c:forEach items="${pagination.datas}" var="data">
									<li class="album-item" >
										<span class="album-img"><a href=""><img src="${picUrl}${data.path }" alt="${data.name }"></a></span>
										<span class="album-title"><label><input type="checkbox" name="inlineRadioOptions" id="" value="${data.id }">${data.name }</label></span>
										<span class="album-operate">
											<a href="javascript:void(0)" class="pull-left" data-name="${data.name }" onclick="toEditPhotoName(this)">编辑</a>
											<a href="javascript:void(0)" data-id="${data.albumId }" onclick="toMoveSinglePhoto(this)">移动到</a>
											<a href="javascript:void(0)" class="pull-right" onclick="toDelSinglePhoto(this)">删除</a>
										</span>
									</li>
								</c:forEach>
								
								</ul>
								<form id="formPage" action="${ctx}/u/stuff/album/listPhoto${ext}">
									<input type="hidden" name="albumId" value="${album.id }"/>
									<input type="hidden" name="urlType" value="1"/>
									<%@include file="../common/page.jsp"%>
								</form>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	
	<!-- 编辑相册 -->
	<div id="jcreatePhoneAlbum" style="display:none;">
		<form class="form-horizontal" id="addAlbum" action="" method="post">
		  <input type="hidden" name="id" value=""/>	
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">相册名称</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="inputEmail3" name="name" placeholder="请输入相册名称">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">相册封面</label>
		    <div class="col-sm-10">
				<div  class="table-img" style="display:inline-block;margin:0 0 0 0; border:1px solid #ccc;">
					<img id="showImg" src="" >
				</div>
				<div class="uploadImg" style="display:inline-block;vertical-align:top;">
					<div class="btn btn-default btn-upload">
						上传图片<input type="button"  id="uploadAlbumImage" name="album" class="form-control" data-tips="请上传尺寸大小为 **** 像素的图片，以png格式为准。">
					</div>
					<input type="hidden" id="picturePath" name="path" value=""/>
				</div>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">相册描述</label>
		    <div class="col-sm-10">
		    	<textarea placeholder="请简单描述相册主题或内容（选填）" name="memo" class="form-control"></textarea>
		    </div>
		  </div>
		  <!-- 记录分页情况 -->
		  <input type="hidden" name="urlType" value="1"/>
		  <input type="hidden" name="albumId" value="${album.id }"/>
		  <input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
		  <input type="hidden" id="pageSize" name="pageSize" value="${pagination.pageSize}"/>
		</form>
	</div>
	
	<form action="${ctx}/u/stuff/album/uploadPhoto${ext}" method="post" id="uploadPhoto">
		<input type="hidden" name="path" value=""/>
		<input type="hidden" name="type" value=""/>
		<input type="hidden" name="albumId" value="${album.id }"/>
		<input type="hidden" name="urlType" value="1"/>
	</form>
	
	<!-- 编辑名称 -->
	<div id="jeditPhotoName" style="display:none;">
		<form class="form-horizontal" action="${ctx }/u/stuff/album/updatePhoto${ext}" method="post" id="j-editNameForm">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-3 control-label">图片名称</label>
		    <div class="col-sm-9">
		      <input type="text" name="name" class="form-control"  placeholder="请输入相册名称，最多10个字符">
			  <input type="hidden" name="id" value=""/>
			  <input type="hidden" name="urlType" value="1"/>
			  <input type="hidden" name="nowAlbumId" value="${album.id }"/>
			  <!-- 记录分页情况 -->
			  <input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
			  <input type="hidden" id="pageSize" name="pageSize" value="${pagination.pageSize}"/>
		    </div>
		  </div>
		</form>
	</div>
	
	<!-- 移动图片 -->
	<div id="jselectAlbum" style="display:none;">
		<div class="form-group">
		    <label for="inputEmail3" class="col-sm-3 control-label">选择相册：</label>
		    <div class="col-sm-9">
		    	<c:forEach items="${albumList }" var="data">
		    		<c:if test="${data.id != album.id}">
		    			<label class=""> <input type="radio" name="inlineRadioOptions" value="${data.id }"> ${data.name }</label>
		    		</c:if>
		    	</c:forEach>
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../common/footer.jsp"%>
	<!-- footer end -->
	</body>


<script type="text/javascript">
//拖动排序
$("#j-album").sortable({
	 update:function(event,ui){
		 var sorted = $(this).sortable("serialize", {key:"sort"});
	 }
});

//移动多张图片到相册
function toMovePhotos(){
	clearAlbumCheck();
	var ids = "";
	$("#j-album").find("input[type='checkbox']:checked").each(function(index){
		ids += $(this).val()+",";
	});
	if(!ids){
		art.dialog.alert("请选择要移动的相片");
		return;
	}
	art.dialog({
		width:"400px",
		title:"移动相册",
		content:document.getElementById("jselectAlbum"),
		ok:function(){
			var albumId = $("#jselectAlbum").find('input[name="inlineRadioOptions"]:checked').val();
			if(!albumId){
				art.dialog.alert("请选择相册");
				return;
			}
			var moveObj = $("#j-album").find("input[type='checkbox']:checked").parents("li.album-item");
			movePhoto(ids,albumId,moveObj);
		},
		cancel:true
	})
}

//移动单张图片到相册
function toMoveSinglePhoto(thiz){
	clearAlbumCheck();
	var oldAlbumId = $(thiz).attr("data-id");
	$("#jselectAlbum").find('input[name="inlineRadioOptions"]').each(function(index){
		if($(this).val() == oldAlbumId){
			$(this).attr("checked",true);
		}
	});
	art.dialog({
		width:"400px",
		title:"移动相册",
		content:document.getElementById("jselectAlbum"),
		ok:function(){
			var albumId = $("#jselectAlbum").find('input[name="inlineRadioOptions"]:checked').val();
			if(!albumId){
				art.dialog.alert("请选择相册");
				return;
			}
			var ids = $(thiz).parent().prev().find('input[type="checkbox"]').val();
			var moveObj = $(thiz).parents("li.album-item");
			movePhoto(ids,albumId,moveObj);
		},
		cancel:true
	})
}
function clearAlbumCheck(){
	$("#jselectAlbum").find('input[name="inlineRadioOptions"]').each(function(index){
		$(this).attr("checked",false);
	});
}
//移动相册
function movePhoto(ids,albumId,moveObj){
	$.post("${ctx}/u/stuff/album/movePhoto${ext}",{"ids":ids,"albumId":albumId},
			  function(falg){
		if(falg == 1 || falg == "1"){
			art.dialog.tips('移动成功！');
			$(moveObj).remove();
		}else{
			art.dialog.tips('移动失败！');
		}
	});
}

//删除多张图片
function toDelPhotos(){
	var ids = "";
	$("#j-album").find("input[type='checkbox']:checked").each(function(index){
		ids += $(this).val()+",";
	});
	if(ids.length == 0){
		art.dialog.alert("请选择要删除的图片");
		return;
	}
	art.dialog.confirm("确定删除？",function(){
		var removeObj = $("#j-album").find("input[type='checkbox']:checked").parents("li.album-item");
		
		del(ids,removeObj);
	},function(){
		
	})
}

//删除单张图片
function toDelSinglePhoto(thiz){
	var ids = $(thiz).parent().prev().find('input[type="checkbox"]').val();
	art.dialog.confirm("确定删除？",function(){
		var removeObj = $(thiz).parents("li.album-item");
		del(ids,removeObj);
	},function(){
		
	})
}

//编辑单张图片名称
function toEditPhotoName(thiz){
	var id = $(thiz).parent().prev().find('input[type="checkbox"]').val();
	var name = $(thiz).attr("data-name");
	$("#j-editNameForm").find('input[name="id"]').val(id);
	$("#j-editNameForm").find('input[name="name"]').val(name);
	art.dialog({
		width:"400px",
		title:"编辑名称",
		content:document.getElementById("jeditPhotoName"),
		ok:function(){
			var flag = checkName();
			if(!flag){
				return false;
			}
			var name = $("#j-editNameForm").find('input[name="name"]').val();
			$("#j-editNameForm").submit();
		},
		cancel:true
	})
}

function checkName(){
	return $("#j-editNameForm").validate({
		rules : {
			name : {
				required: true,
				maxlength:10
			}
		},
		messages : {
			name : {
				required: "请输入名称！",
				maxlength:$.format("名称不能超过{0}个字符")
			}
		}
	}).form();
}

function del(ids,removeObj){
	$.post("${ctx}/u/stuff/album/delPhoto${ext}",{"ids":ids},
			  function(falg){
		if(falg == 1 || falg == "1"){
			art.dialog.tips('删除成功！');
			$(removeObj).remove();
		}else{
			art.dialog.tips('删除失败！');
		}
	});
}

function checkAlbumForm(){
	return $("#addAlbum").validate({
		rules : {
			name : {
				required: true,
				maxlength:10
			},
			path : "required"
		},
		messages : {
			name : {
				required: "请输入相册名称！",
				maxlength:$.format("标题不能超过{0}个字符")
			},
			path : "请上传图片"
		}
	}).form();
}

//编辑相册
function toEditPhoneAlbum(id){
	$.post("${ctx}/u/stuff/album/getAlbum${ext}",{"id":id},function(data){
		var obj = eval('(' + data + ')');
		$("#addAlbum").find('input[name="id"]').val(obj.id);
		$("#addAlbum").find('input[name="name"]').val(obj.name);
		$("#addAlbum").find('input[name="path"]').val(obj.path);
		var showPath = obj.path == null || obj.path == '' ? "${resourceBasePath}img/base-photo.jpg" : '${picUrl}'+obj.path
		$("#addAlbum").find('img').attr('src',showPath);
		$("#addAlbum").find('[name="memo"]').attr('value',obj.memo);
		art.dialog({
			width:"500px",
			title:"编辑相册",
			content:document.getElementById("jcreatePhoneAlbum"),
			ok:function(){
				var flag = checkAlbumForm();
				if(!flag){
					return false;
				}
				$("#addAlbum").attr('action',"${ctx}/u/stuff/album/editAlbum${ext}");
				$("#addAlbum").submit();
			},
			cancel:function(){
				$('#inputEmail3').removeClass('error').siblings('label').remove().end().val('');
				$('#picturePath').removeClass('error').siblings('label').remove().end().val('');
				return true
			}
		})
	});
}

//删除相册
function toDelPhoneAlbum(id){
	art.dialog.confirm("仅删除相册不删除图片,相册图片将自动归入默认相册。<br>是否继续删除？",function(){
		$.post("${ctx}/u/stuff/album/delAlbum${ext}",{"id":id},function(data){
			if(data=="SUCCESS"){
				location.href="${ctx}/u/stuff/album/listAlbum${ext}";
			}else{
				art.dialog.alert("删除失败！");
			}
		});
	},function(){
		
	})
}

</script>
</html>