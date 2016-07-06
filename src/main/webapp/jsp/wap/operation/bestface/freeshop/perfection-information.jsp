<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>完善信息</title>
<%@include file="top.jsp" %>
</head>
<body>
<div class="container">
	<form action="${ctx}/free/addFreeShop${ext}" method="post" class="m-form" id="jform" enctype="multipart/form-data">
		<input type="hidden" name="cardId" value="${iCard.id }">
		<div class="info">
			<div class="form-group head-portrait" id="j-upload">
			    <label for="" class="col-xs-3 control-label">头像</label>
			    <div class="col-xs-3">
			    	<div class="pic" data-upload="true">
			    		<span class="m-pic-icon">
			    			<img name="headimgurl" src="${picUrl }${iCard.headPicture}" alt="">
			    		</span>
						<input class="m-pic-file" type="file" name="headImageFile" data-upload="up" accept="image/*" capture="camera">
			    	</div>
			    </div>
			    <div class="col-xs-1 pull-right">
			    	<i class="iconfont icon-right"></i>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">账号</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请输入您的手机号码" value="${cell }" id="cell" name="cell" onblur="phoneOnBlur(this)"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">昵称</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="默认读取微信昵称" id="nickname" name="nickname" value="${iCard.nickName }"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">店铺名称</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写店铺名称" value="${name }" id="name" name="name"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">验证码</label>
			    <div class="col-xs-4">
			    	<input type="text" class="form-control" placeholder="验证码"  name="code" id="mobilecode"></input>
			    </div>
			    <div class="col-xs-5">
			    	<a href="javascript:;" type="button" style="float:right;" class="btn btn-default" onclick="getCode(this)">获取验证码</a>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">登录密码</label>
			    <div class="col-xs-5">
			    	<input type="password" class="form-control" placeholder="请输入密码" id="passwd" name="passwd"></input>
				    <input type="text" class="form-control" placeholder="请输入密码" style="display:none;" id="password2" name="password2"></input>
			    </div>
			    <div class="col-xs-4">
			    	<div class="switch">
			    		<i>***</i>
			    		<span id="j-switch" class="open"></span>
			    	</div>
			    </div>
			</div>
			<!-- 我要成为代理  -->
		  	<div class="form-group" id="j-code">
			    <label for="" class="col-xs-3 control-label">输入授权码 (选填)</label>
			    <div class="col-xs-8">
			    	<input type="text" class="form-control" value="${proxyAuthorizationCode }" placeholder="请输入授权码" id="code" name="proxyAuthorizationCode"></input>
			    </div>
			</div>
		  	
		</div>
		<button type="button" class="btn btn-danger btn-block" onclick="mySubmit()">保存</button>
	</form>

	<!-- 会员登录  -->
	<div class="login" id="j-login" style="display:none;width:100%">
		<form method="post" action="javscript:;" id="j-form">
			<div class="form-login" style="border:none;">
				<div class="form-group" style="border:none;">
					<input type="text" class="form-control" name="username" id="loginUsername" placeholder="请输入手机号码" style="border:1px solid #ccc;border-radius:6px;">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="password" id="loginPassword" placeholder="请输入密码" style="border:1px solid #ccc;border-radius:6px;">
				</div>
			</div>
			<div class="form">
				<a href="${ctx}/free/findPassword${ext}" class="pull-right">找回密码？</a>
			</div>
		</form>
	</div>
	
</div>
<script type="text/javascript">
$(function() {
	var errorMsg = "${errorMsg}";
	if (""!=errorMsg) {
		artDialogAlert(errorMsg);
	}
});
	//显示或隐藏密码
	$('#j-switch').toggle(function(){
		$(this).removeClass('open').addClass('close');
		$('.switch').css('background','#2EA8E7');
		$('.switch i').html('ABC').css('padding-right','12px');
		$('#passwd').hide();
		$('#password2').show();
	},function(){
		$(this).removeClass('close').addClass('open');
		$('.switch').css('background','#BFBFBF');
		$('.switch i').html('***').css('padding-right','0px');
		$('#password2').hide();
		$('#passwd').show();
	});
	
	$('#passwd').keyup(function(){
		$('#password2').val($(this).val());
	});
	$('#password2').keyup(function(){
		$("#passwd").val($(this).val());
	});
	
	var my = $('#jform').validate({
		rules:{
			nickname:{
				required:true,
				maxlength:32
			},
			name:{
				required:true,
				maxlength:64
			},
			cell:{
				required:true,
				mobile:true
			},
			passwd:{
				required:true,
				minlength:6,
				maxlength:64
			},
			code:{
				required:true
			}
		},
		messages:{
			nickname:{
				required:'请输入昵称',
				maxlength:$.validator.format("昵称最大长度不能超过{0}个字符")
			},
			name:{
				required:'请输入店铺名称',
				maxlength: $.validator.format("店铺名称最大长度不能超过{0}个字符")
			},
			cell:{
				required:'请输入您的手机号码',
				mobile:"请输入正确的手机号码"
			},
			passwd:{
				required:'请输入登录密码',
				minlength:$.validator.format("密码长度最少不能低于{0}个字符"),
				maxlength:$.validator.format("密码长度最大不能超过{0}个字符")
			},
			code:{
				required:'请输入验证码'
			}
		},
		errorPlacement:function(error,element) {
			error.appendTo(element.parent());
		},
	});
	
	/**检查手机是否存在*/
	var isHasFreeShop = false;
	var isHas = false;
	function phoneOnBlur(thiz){
		
		$.post("${ctx}/any/icard/checkPhone${ext}",{"phone":$(thiz).val()},
			function(flag){
				if("1" == flag){
					isHas = true;
					isHasFreeShop= false;
					toRegistered($(thiz).val());
				}else if("0" == flag){
					isHas = true;
					isHasFreeShop= true;
					alertFun("该手机号码已注册过免费店铺且与其它名片绑定，请更改！");
				}else{
					isHas = false;
					isHasFreeShop= false;
				}
		});
	}
	
	
	//判断手机号码是已注册的时候弹出
	function toRegistered(phone){
		console.dir(phone);
		art.dialog({
			lock:true,
			title:"提示",
			width:'100%',
			content:"您已经是我们的用户，登录开启免费店 !",
			button:[{
				name:"确认取消",
				callback:function(){
					
				}
			},{
				name:"直接登录",
				focus:true,
				callback:function(){
				    location.href="${ctx}/free/loginExist/"+phone+"${ext}";
				}
			}]
		});
	};
	
	//验证
	
	function mySubmit(){
		var flag1 = my.form();
		var phone = $('#cell').val();
		if(flag1){
			$.post("${ctx}/any/icard/checkPhone${ext}",{"phone":phone},
					function(flag){
				if("1" == flag){
					toRegistered(phone);
				}else if("0" == flag){
					alertFun("该手机号码已注册过免费店铺且与其它名片绑定，请更改！");
				}
				$('#jform').submit();
			});
			
		}else{
			return false;
		}
	}
		
	
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
	
	function getCode(el){
	 	var phone = $("#cell").val();
		var flag= $("#jform").validate().element("#cell");
		if(flag){
			//有免费店铺
			if(isHasFreeShop){
				alertFun("该手机号码已注册过免费店铺且与其它名片绑定，请更改！");
			}else if(isHas){
				toRegistered(phone);
			}else{
				$.post("${ctx}/any/web/registerCode${ext}",{"phone":phone,"type":1},
						  function(flag){
						if("true" == flag){
						}else{
							art.dialog.alert(flag,function() {
								getCode(el);
							});
						}
				});
				
				$(el).attr("disabled",true);
				var time=60;
				var setIntervalID=setInterval(function(){
					time--;
					$(el).html("验证码已发送 "+ time +"秒");
					if(time==0){
						clearInterval(setIntervalID);
						$(el).attr("disabled",false).html("免费获取验证码");
					}
				},1000);
			}
		}  
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

//我要成为代理
$('input[type=checkbox]').click(function(){
	$('input[type=checkbox]').attr('checked');
	$('#j-code').toggle();
});

/**
 * 验证提示 自动消失
 * @param  {[str]} txt  [提示文字]
 * @param  {[boolean]} evt  [关闭方式，true:点击关闭；false:自动消失]
 * @param  {[number]} time [自动消失的时间]
 */
function artTip(txt,evt,time){
	var str = '<div class="m-art"><div class="m-artbox"><span class="m-artinfo">错误提示！</span></div></div>',
	artEl = $('.m-art'),
	time = 1500,
	evtStyle = false;
	for(var i=1;i<arguments.length;i++){
		if(typeof arguments[i] == 'number'){
			time = arguments[i];
		}else if(typeof arguments[i] == 'boolean'){
			evtStyle = evt;
		}
	}
	if(artEl.length == 0){
		$('body').append(str);
		artEl = $('.m-art');
	}
	if(artEl.is(':hidden')){
		artEl.find('.m-artinfo').html(txt);
		artEl.slideDown();
		if(evtStyle){
			artEl.addClass('m-art-click');
			$('.m-art-click').click(function(){
				artEl.slideUp();
			})
		}else{
			setTimeout(function(){
				artEl.hide();
		},1500);
		}	
	}
};

</script>
</body>
</html>