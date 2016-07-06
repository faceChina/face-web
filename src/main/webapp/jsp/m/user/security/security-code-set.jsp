<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的钱包-我的账户</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	var validForm;
	$(function() {
		tab("security");
		dealErrMsg();
		validForm = $("#jform").validate({
			ignore : ".ignore",
			rules : {
				mobilecode : {
					required : true,
					minlength : 6
				},
				userName:{
					required:true
				},
				identity:{
					required:true,
					idcardno: true
				},
				paymentCode:{
					required:true,
					minlength: 6
				},
				confirmCode:{
					required:true,
					minlength: 6,
					equalTo: "#paymentCode"
				}
			},
			messages : {
				mobilecode : {
					required : "请输入手机验证码",
					minlength : $.format("验证码不能小于{0}个字符")
				},
				userName:{
					required:"请输入真实姓名"
				} ,
				identity:{
					required:"请输入证件号码",
					idcardno:"请正确输入证件号码"
				},
				paymentCode:{
					required:"请输入支付密码",
					minlength:$.format("密码不能小于{0}个字 符")
				},
				confirmCode:{
					required:"请确认支付密码",
					minlength:$.format("密码不能小于{0}个字 符"),
					equalTo: "两次输入密码不一致"
				}
			}
		});
	});
	//错误信息处理
	function dealErrMsg() {
		var errMsg = '${errorMsg}';
		if (null != errMsg && '' != errMsg) {
			$('#phone').val('${phone}');
			art.dialog.alert(errMsg);
		}
	}
	//获取验证码
	function getCode(el, type, cell){
		$.post("${ctx}/u/account/mobilecode/phoneCode${ext}",{"type" : type, "phone" : cell},
				  function(falg){
				if("true" != falg){
					art.dialog.alert(falg,function() {
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
	//下一步
	function formSubmit() {
		if (!validForm.form()) {
			return;
		}
		$.post('${ctx}/u/account/mobilecode/testMobilecode${ext}',  {"type" : 3, "mobilecode" : $('#mobilecode').val()}, function(jsonData){
			var data = JSON.parse(jsonData); 
			if (data.success) {
				$('#jform').submit();
			} else {
				art.dialog.alert(data.info);
			}
		});
	}
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="security"/>
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">设置支付密码</a></li>
							</ul>
						</div>
						<div class="content">
							<ul class="m-steps">
								<li <c:if test="${pageIndex == 1 }">class="active"</c:if>>1.身份验证</li>
								<li <c:if test="${pageIndex == 2 }">class="active"</c:if>>2.补全资料</li>
								<li>3.设置成功</li>
							</ul>
							<br />
							<c:if test="${pageIndex == 1 }">
								<form action="${ctx }/u/account/security/editPage${ext}" method="post" class="form-horizontal" id="jform">
							    <input type="hidden" name="retUrl" value="${retUrl }" />
									<div class="form-group">
										<label class="col-md-3 control-label">手机号码：</label>
										<div class="col-md-9">
											<p class="form-control-static">
												<b>${phone } </b> <a
													href="${ctx }/u/account/security/bindPhone${ext}?retUrl=/u/account/security/index"
													class="btn-link">更换手机号码</a>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-3 control-label">输入验证码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value=""
												id="mobilecode" name="mobilecode">
										</div>
										<div class="col-sm-4">
											<button type="button" class="btn btn-default"
												onclick="getCode(this, 3)">获取验证码</button>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-8">
											<button onclick="formSubmit()" name="valie-check" type="button"
												class="btn btn-default active">下一步</button>
										</div>
									</div>
								</form>
							</c:if>
							<c:if test="${pageIndex == 2 }">
								<form action="${ctx }/u/account/security/editCode${ext}" method="post" class="form-horizontal" id="jform">
									<input type="hidden" name="retUrl" value="${retUrl }" />
									<input type="hidden" name="validateToken" value="${validateToken }" />
									<div class="form-group">
										<label class="col-md-3 control-label">真实姓名：</label>
										<div class="col-md-3">
											<input class="form-control ignore" type="text" value=""
												id="userName" name="userName" />
										</div>
										<div class="col-md-6">
											<label class="text-muted form-control-static">请填写真实姓名及证件号码，用于钱包激活、支付密码找回等</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">证件类型：</label>
										<div class="col-md-3">
											<select class="form-control">
												<option>身份证</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">证件号码：</label>
										<div class="col-md-3">
											<input class="form-control ignore" type="text" value="" id="identity"
												name="identity" placeholder="身份证号码" />
										</div>
										<div class="col-md-6">
											<label class="text-muted form-control-static">请填写您的证件号码</label>
										</div>
									</div>
									<hr />
									<div class="form-group">
										<label class="col-md-3 control-label">设置支付密码：</label>
										<div class="col-md-3">
											<input class="form-control ignore" type="password" value="" id="paymentCode" maxlength="20"
												name="paymentCode" placeholder="支付密码" />
										</div>
										<div class="col-md-6">
											<label class="text-muted form-control-static">用于管家钱包的余额支付及提现密码，请勿与登录密码相同</label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">确认支付密码：</label>
										<div class="col-md-3">
											<input class="form-control ignore" type="password" value="" maxlength="20"
												id="confirmCode" name="confirmCode" placeholder="确认密码" />
										</div>
										<div class="col-md-6">
											<label class="text-muted form-control-static">用于管家钱包的余额支付及提现密码，请勿与登录密码相同</label>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-offset-3 col-md-9">
											<button type="submit" class="btn btn-default active">下一步</button>
										</div>
									</div>
								</form>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>