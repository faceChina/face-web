<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>代理申请</title>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/subbranch/css/main.css">
</head>
<body>

<div id="box">	
	<form action="" method="post" class="agency-form"  id="jform" data-form onsubmit="return false;">
	<input type="hidden" name="subbranchId" id="subbranchId" value="<c:out value='${applySubbranchId }'/>" >
		<p class="fnt-16">你确认提交以下分销合作申请吗？</p>
		<div class="group width60" onclick="goIndex();">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan"><img src="${picUrl}${applyShop.shopLogoUrl }" alt="" width="60"></li>
					<li class="group-colspan fnt-16">${applyShop.name }</li>
				</ul>
			</div>
		</div>
		<div class="group width60">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">姓名</div>
					<div class="group-colspan">
						<input type="text" placeholder="用户的昵称" class="form-control" name="nickname" id="username" data-form-control>
					</div>
				</div>
			</div>
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">电话</div>
					<div class="group-colspan">
						<input type="tel" placeholder="用户的手机" class="form-control" name="phoneNo" id="moblie" data-form-control>
					</div>
				</div>
			</div>
		</div>
		
		<div class="button">
			<button type="button" id="submit" class="btn btn-block btn-danger disabled" data-submit>下一步</button>
		</div>
		<div class="agency-apply-info">
			<h4 class="fnt-18">注意事项:</h4>
			<p>1、申请自动审核通过，您便不可再成为其他供应商的分店。</p>
			<p>2、申请成功后，你的商品库存，商品属性将和供应商的保持一致，且不能修改。</p>
		</div>
	</form>
	
</div>

<div class="register" id="register_box" hidden="true">
	<form action="" id="regform" method="post" class="form">
	<div class="list-row list-row-width">
		<div class="list-col">
			<div class="list-inline">手机号码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" style="color:#c7c7c7;" placeholder="请输入手机号码" id="mobile_reg" name="mobile_reg" readonly="ture" data-form-control></div>
		</div>
		
		<div class="list-col">
			<div class="list-inline">验证码</div>
			<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="请输入验证码" id="mobilecode" name="mobilecode" data-form-control></div>
			<div class="list-inline">
				<button type="button" class="btn btn-warning btn-block" onclick="getCode(this)">获取验证码</button>
			</div>
		</div>
		
		<div class="list-col">
			<div class="list-inline">密码</div>
			<div class="list-inline box-flex">
				<input type="password" class="form-control" placeholder="请输入密码" id="pwd" name="pwd" data-form-control>
<!-- 				<input type="text" class="form-control" placeholder="请输入密码" id="repwd" name="repwd" style="display:none;" data-form-control> -->
			</div>
			<div class="list-inline">
				<div class="onoff onoff-off" data-onoff="off" id="onoff1">
					<span class="onoff-handle"></span>
					<span class="onoff-info">●●●</span>
				</div>
			</div>
		</div>
		
		
	</div>	
		<div class="button">
			<button type="button" id="register_submit" class="btn btn-danger btn-block" data-submit>确认</button>
		</div>
		
	</form>
</div>

<script type="text/javascript">


$(function(){
	
	var isVSuccess= $('#jform').validate({
		rules:{
			nickname:{
				required:true
			},
			phoneNo:{
				required:true,
				mobile:true
			}
		},
		messages:{
			nickname:{
				required:'请输入用户名称'
			},
			phoneNo:{
				required:'请输入手机号码',
				mobile:'请输入正确的手机号码'
			}
		}
	});
	$('#submit').click(function(){
		if(isVSuccess.valid()){
			toApply();
		}
	});
	
	$("#regform").validate({
		rules:{
			mobile:{
				required:true,
				mobile:true,
				remote: {    
		            url: "${ctx}/any/checkLoginAccount${ext}",     //后台处理程序    
		            type: "post",               //数据发送方式    
		            data: {phone: function(){return $("#mobile_reg").val();}
		            }    
		        }
			},
			mobilecode:{
				required:true
			},
			pwd:{
				required:true,
				minlength: 6,
				maxlength:30
			},
			repwd:{
				required:true,
				minlength: 6,
				maxlength:30,
				equalTo:"#pwd"
			}
		},
		messages:{
			mobile:{
				required:"请输入手机号码",
				mobile:"请输入正确的手机号码",
				remote:"该手机号已被注册！"
			},
			mobilecode:{
				required:"请输入验证码"
			},
			pwd:{
				required:"请输入密码",
				minlength:$.validator.format("密码不能小于{0}个字 符"),
				maxlength: $.validator.format("密码不能大于{0}个字符")
			},
			repwd:{
				required:"靖确认密码",
				equalTo:"两次输入密码不一致",
				minlength:$.validator.format("密码不能小于{0}个字 符"),
				maxlength: $.validator.format("密码不能大于{0}个字符")
			}
		},
	});
	
	$("#register_submit").click(function(){
		var flag = $("#regform").validate().form();
		if(flag){
			postRegister();
		}else{
			return false;
		}
	});
	
});

function textRrror(el,text){
	var str = '<label for="mobile" generated="true" class="error">'+text+'</label>';
	$(el).addClass('error');
	if(!$(el).next().hasClass('error')) $(el).after(str);
	
}

function postRegister(){
	var mobile = $("#mobile_reg").val();
	var mobilecode = $("#mobilecode").val();
	var pwd = $("#pwd").val();
	var repwd = $("#repwd").val();
	var nickname = $("#username").val();
	$.post("${ctx}/any/appRegister${ext}",{"mobile":mobile,"mobilecode":mobilecode,"pwd":pwd,"repwd":repwd,"type":2,"nickname":nickname},
			  function(falg){
		if("success" == falg){
				toApply();
		}else{
			art.dialog.alert(falg);
		}
		
	});
}


//获取验证码
function getCode(el){
	var phone = $("#mobile_reg").val();
	var flag= $("#regform").validate().element(mobile_reg);
	if(flag){
	$.post("${ctx}/any/checkLoginAccount${ext}",{"phone":phone},
			  function(flag){
			if("true" == flag){
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
	});
	}
}


//开关
$(function(){
	$("#onoff1").onoff({
		on:function(){
// 			$('#pwd').show();
// 			$('#repwd').hide();
			change("text");
			return true;
		},
		off:function(){
// 			$('#repwd').show();
// 			$('#pwd').hide();
			change("password");
			return true;
		}
	});
	

})

function change(type) {
	$("#pwd").attr("type", type);
}
/* 判断是否已申请  Ajax判断 */
function toApply(el){
	$.post("/wap/${applyShop.no }/any/subbranch/add.htm",$('#jform').serialize(),function(data){
		if(data>0){
			window.location.href='/wap/${applyShop.no }/any/subbranch/finish.htm';
			return false;
		}else if(data==-1900){
 			$('#register_box').show();
 			$('#mobile_reg').val($('#moblie').val());
 			$('#box').hide();
 			return false;
			//window.location.href='/any/registration.htm?mobile='+$('#moblie').val()+'&nickname='+$('#username');
		}else if(data==-1906){
			artTip("该店铺不存在，无法继续申请",false);
		}else if(data==-1907){
			artTip("您已经是其他供应商的分店，不能再申请第二个分店呦！",false);
		}else if(data==-1909){
			artTip("上级分店不存在，无法继续申请",false);
		}else if(data==-1920){
			artTip("所申请分店已经是最大代理等级，无法继续申请",false);
		}else if(data==-1911){
			artTip("自己不能申请成为自己的分店，无法继续申请",false);
		}else if(data==-1921){
			artTip("此申请店铺曾对申请人解除过代理关系，暂时不能申请成为其分店",false);
		}else{
			artTip("申请发送错误，无法继续申请",false);
		}
		$('#register_box').hide();
		$('#box').show();
	});
	return false;
};
//toApply();
function goIndex(){
	window.location.href="/wap/${applyShop.no }/any/gwscIndex.htm<c:if test='${not empty applySubbrachId}'>?subbranchId=${applySubbranchId }</c:if>";
}
</script>
</body>
</html>