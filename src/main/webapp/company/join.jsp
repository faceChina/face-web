<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>刷脸平台-注册</title>
<%@ include file="/company/common/base-company.jsp"%>
<script type="text/javascript" src="${resourceBasePath}js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/artDialog/iframeTools.js"></script>
<%@ include file="/jsp/m/common/validate.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourceBasePath}js/artDialog/skins/myself.css">
<!-- top -->

<!--top end -->
</head>
<script>
$(function(){
	if('${param.flag}'){
		var flag = Number('${param.flag}');
		if(flag == 1){
			art.dialog.alert("注册成功！");
		}else if(flag == 0){
			art.dialog.alert("手机验证码错误");
		}else if(flag == 3){
			art.dialog.alert("验证码超时");
		}else{
			art.dialog.alert("服务器繁忙 ！");
		}
	}
});
		
//获取验证码
function getCode(el){
	var phone = $("#mobile").val();
	var flag= $("#jform-register").validate().element(mobile);
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


$(function(){

	//表单验证
	var formm = $("#jform-register").validate({
		        rules: {
				   mobile:{
					   required: true,
					   mobile:true,
					   remote: {    
				            url: "${ctx}/any/checkLoginAccount${ext}",     //后台处理程序    
				            type: "post",               //数据发送方式    
				            data: {phone: function(){return $("#mobile").val();}
				            }    
				        } 
				   },
				   mobilecode: {
					    required: true,
					    minlength: 4
				   },
				   pwd: {
					    required: true,
					    minlength: 6
				   },
				   repwd: {
					    required: true,
					    minlength: 6,
					    equalTo: "#pwd"
				   }
				  },
				  messages: {
					  mobile: {
						  required: "请输入手机号",
						  mobile:"请输入正确的手机号码",
						  remote:"该手机号已被注册！"
					  },
					  mobilecode: {
					    required: "请输入手机验证码",
					    minlength:$.format("密码不能小于4个字符")
				   },
				   pwd: {
					    required: "请输入密码",
					    minlength: jQuery.format("密码不能小于6个字 符")
				   },
				   repwd: {
					    required: "请输入确认密码",
					    minlength: "确认密码不能小于6个字符",
					    equalTo: "两次输入密码不一致"
				   }
				  }
		    });

	//勾选协议
	$('#check-protocol').on('click',function(){
		//alert(1)
		if($(this).is(':checked')){
			$('#btn-join').addClass('btn-danger').removeAttr('disabled');
		}else{
			$('#btn-join').removeClass('btn-danger').attr('disabled','true');
		}
	
	});

	//提交表单
	$('#btn-join').on('click',function(){
		var flag = formm.form();
		if(flag){
			//提交表单
			$('#jform-register').submit();
		}
	})

})

</script>
<body>
<%@ include file="common/header.jsp"%>
<div class="content boss-page">
	<div class="nav-title"><h2>注册账号</h2><span class="form-input">Register an account</span></div>
	<div class="page-content "> 
		<form id="jform-register" action="${ctx}/any/web/registerfor${ext}" method="post">
			<div class="join-from">
				<div class="form-group">
					<label for="">手机号码：</label>
					<div class="form-controls">
						<span class="form-input"><input id="mobile" name="mobile" type="text" placeholder="请输入手机号码" value="${param.mobile}"></span>
						<p class="form-code"><button type="button" onclick="getCode(this)">免费获取验证码</button></p>
					</div>
				</div>
				<div class="form-group">
					<label for="">手机验证码：</label>
					<div class="form-controls">
						<span class="form-input"><input id="mobilecode" name="mobilecode" type="text" placeholder="请输入验证码"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="">密码：</label>
					<div class="form-controls">
						<span class="form-input"><input type="password" id="pwd" name="pwd" placeholder="请输入密码"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="">确认密码：</label>
					<div class="form-controls">
						<span class="form-input"><input type="password" id="repwd" name="repwd" placeholder="请再次输入密码"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="form-protocol">
						<label for="check-protocol"><input type="checkbox" name="protocol" id="check-protocol">我同意并遵守《<a href="${companyPath}protocol.jsp" target="_blank">浙江脸谱运营平台用户服务协议</a>》</label>
					</div>
				</div>
				<div class="form-group">
					<div class="form-active">
						<button type="button" id="btn-join" class="" disabled>立即注册</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
	<%@ include file="/company/common/footer.jsp"%>
</body>
</html>