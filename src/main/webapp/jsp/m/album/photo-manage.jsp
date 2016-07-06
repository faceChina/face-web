<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>相册管理</title>	
<%@ include file="../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript">
var url = "${ctx}/any/files/upload${ext}";

$(function(){
	tab("photo");
	//上传图片 提示文字
	$('.j-tooltip input').tooltip({
	     placement:"right"
	 });
});
 
$(function(){
	//$('input[type=file]').parent().css('z-index','10000');
	var button = $('#uploadImage');
// 		button.on('click',function(){
// 		if($("[name=userfile]").length){
// 				$("[name=userfile]").trigger('click');
// 			}
// 		});
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
							<div class="content">
								<ul class="pager">
									<li class="previous">
										<button type="button" class="btn btn-default " onclick="toCreatePhoneAlbum()">创建相册</button>
									</li>
								</ul>
								
								<ul class="album clearfix">
								
								<c:forEach items="${pagination.datas}" var="data">
									<c:choose>
										<c:when test="${empty pagination.datas }">
											<div style="text-align:center;padding:20px 0;">暂无相册！</div>
										</c:when>
									</c:choose>
								
									<li class="album-item">
										<span class="album-img">
											<a href="${ctx }/u/stuff/album/listPhoto${ext}?albumId=${data.id}&urlType=1">
												<c:choose>
													<c:when test="${data.path == '' || data.path == null}">
														<img src="${resourceBasePath}img/base-photo.jpg" alt="">
													</c:when>
													<c:otherwise>
														<img src="${picUrl}${data.path}" alt="">
													</c:otherwise>
												</c:choose>
											</a>
										</span>
										<span class="album-title"><a href="${ctx }/u/stuff/album/listPhoto${ext}?albumId=${data.id}&urlType=1">${data.name}</a></span>
										<span class="album-operate">
											<a href="javascript:void(0)" class="pull-left" onclick="toEditPhoneAlbum(this,'${data.id}')">编辑</a>
											<a href="${ctx }/u/stuff/album/listPhoto${ext}?albumId=${data.id}&urlType=1">图片管理</a>
											<c:if test="${!data.isDefault }">
												<a href="javascript:void(0)" class="pull-right" onclick="toDelPhoneAlbum(this,'${data.id}')">删除</a>
											</c:if>
										</span>
									</li>
								</c:forEach>
								</ul>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	
	<!-- 创建相册 -->
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
				<div  class="table-img" style="width:150px;height:150px;display:inline-block;margin:0 0 0 0; border:1px solid #ccc;">
					<img id="showImg" src="${resourcePath }img/goods.jpg"  >
				</div>
				<div class="uploadImg j-tooltip" style="display:inline-block;vertical-align:top;">
					<div class="btn btn-default btn-upload">
						上传图片<input type="button"  id="uploadImage" name="uploadImg" class="form-control" data-toggle="tooltip" data-original-title="请上传大小为 200*200 像素的图片">
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
		</form>
	</div>
	<!-- body end -->

	<!-- footer -->
		<%@include file="../common/footer.jsp"%>
</body>

<script type="text/javascript">

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

//创建相册
function toCreatePhoneAlbum(){
	art.dialog({
		width:"500px",
		title:"创建相册",
		content:document.getElementById("jcreatePhoneAlbum"),
		ok:function(){
			var flag = checkAlbumForm();
			if(!flag){
				return false;
			}
			$("#addAlbum").attr('action',"${ctx}/u/stuff/album/addAlbum${ext}");
			$("#addAlbum").submit();
		},
		cancel:function(){
			$('#inputEmail3').removeClass('error').siblings('label').remove().end().val('');
			$('#picturePath').removeClass('error').siblings('label').remove().end().val('');
			return true
		}
	})
}

//编辑相册
function toEditPhoneAlbum(thiz,id){
	$.post("${ctx}/u/stuff/album/getAlbum${ext}",{"id":id},function(data){
		console.log(data+"--");
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
function toDelPhoneAlbum(thiz,id){
	art.dialog.confirm("仅删除相册不删除图片,相册图片将自动归入默认相册。<br>是否继续删除？",function(){
		$.post("${ctx}/u/stuff/album/delAlbum${ext}",{"id":id},function(data){
			if(data=="SUCCESS"){
				$(thiz).parents("li.album-item").remove();
			}else{
				art.dialog.alert("删除失败！");
			}
		});
	},function(){
		
	})
}
</script>
</html>