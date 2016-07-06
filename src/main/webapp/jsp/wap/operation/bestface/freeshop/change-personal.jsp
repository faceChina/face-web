<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>修改个人资料</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="top.jsp" %>
<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js"></script>
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="/free/mine/info/saveEdit.htm" class="m-form" method="post" id="jform" enctype="multipart/form-data">
			<div class="info">
				<div class="form-group head-portrait" id="j-upload">
				    <label for="" class="col-xs-3 control-label">店铺头像</label>
				    <div class="col-xs-3" >
				    	<div class="pic" data-upload="true">
				    		<span class="m-pic-icon">
				    		<c:if test="${empty shop.shopLogoUrl }">
								<img src="${resourcePath }img/defaultShopLogo.jpg" alt="" width="60"/>
							</c:if>
							<c:if test="${not empty shop.shopLogoUrl }">
				    			<img src="${picUrl }${shop.shopLogoUrl}" alt="" width="60" />
							</c:if>
				    		</span>
							<input class="m-pic-file" type="file" data-upload="up" name="logo" accept="image/*" capture="camera">
				    	</div>
				    </div>
				    <div class="col-xs-1 pull-right">
				    	<i class="iconfont icon-right clr-light"></i>
				    </div>
				</div>
				<div class="form-group">
				    <label for="" class="col-xs-3 control-label">昵称</label>
				    <div class="col-xs-9">
				    	<input type="text" class="form-control" value="${user.nickname }" placeholder="默认读取微信昵称" id="username" name="nickname"></input>
				    </div>
				</div>
				<div class="form-group">
				    <label for="" class="col-xs-3 control-label">店铺名称</label>
				    <div class="col-xs-9">
				    	<input type="text" class="form-control" value="${shop.name }" placeholder="请填写店铺名称" id="shopname" name="shopname"></input>
				    </div>
				</div>
			</div>
			<button type="submit" class="btn btn-danger">确认修改</button>
		</form>
	</div>
</div>
<script type="text/javascript">
	$('#jform button').click(function(){
		$('#jform').validate({
			rules:{
				nickname:{
					required:true
				},
				shopname:{
					required:true
				}
			},
			messages:{
				nickname:{
					required:'请输入原昵称'
				},
				shopname:{
					required:'请输入店铺名称'
				}
			},
			errorPlacement: function(error, element) { 			
				error.insertAfter(element);
			}
		});
	});

//验证提示
function alertFun(txt){
	var str = '<div class="m-alert Bounce"><span class="m-alert-info">错误提示！</span></div>';
	$('body').append(str);
	var alertEl = $('.m-alert');
	if(alertEl.is(':hidden')){
		alertEl.find('.m-alert-info').html(txt)
		alertEl.show();
		setTimeout(function(){
			alertEl.hide();
		},1000);
	}

	return false;
}

//图片上传
var picUpload = (function(){
	window.URL = window.URL || window.webkitURL;

	$('#j-upload').on('change','[data-upload="up"]',function(){
	 		handleFiles(this);
	 });

	 $('#j-upload').on('click','[data-upload="del"]',function(){
	 	//alert(1)
	 		handleDel(this);
	 });

	 //删除图片
	 function handleDel(obj){
	 	var parentEl = $(obj).closest('[data-upload="true"]'),
	 		imgEl = parentEl.find('.m-pic-icon'),
	 		fileEl = parentEl.find('[data-upload="up"]');

	 		$(obj).hide();
	 		imgEl.html('');
	 		fileEl.val('');


	 }

	 //上传图片
	function handleFiles(obj) {
		var files = obj.files,
			img = new Image(),
			parentEl = $(obj).closest('[data-upload="true"]'),
	 		imgEl = parentEl.find('.m-pic-icon'),
	 		delEl = parentEl.find('[data-upload="del"]');
		if(window.URL){
			//File API
			// alert(files[0].name + "," + files[0].size + " bytes");
			img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
			img.width = 200;
			img.onload = function(e) {
			 window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
			}

			imgEl.html(img);
			delEl.show();
		}
	}
})();
</script>
</body>
</html>