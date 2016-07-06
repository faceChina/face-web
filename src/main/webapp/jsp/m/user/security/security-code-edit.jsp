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
				checkIdentity:{
					required:true,
					idcardno: true
				},
				mobilecode : {
					required : true,
					minlength : 6
				},
				paymentCode : {
					required : true,
					minlength : 6
				},
				confirmCode : {
					required : true,
					minlength : 6,
					equalTo : "#paymentCode"
				}
			},
			messages : {
				checkIdentity:{
					required:"请输入证件号码",
					idcardno:"请正确输入证件号码"
				},
				mobilecode : {
					required : "请输入手机验证码",
					minlength : $.format("验证码不能小于{0}个字符")
				},
				paymentCode : {
					required : "请输入支付密码",
					minlength : $.format("密码不能小于{0}个字符")
				},
				confirmCode : {
					required : "请确认支付密码",
					minlength : $.format("密码不能小于{0}个字符"),
					equalTo : "两次输入密码不一致"
				}
			}
		});
	});
	//错误信息处理
	function dealErrMsg() {
		var errMsg = '${errorMsg}';
		if (null != errMsg && '' != errMsg) {
			art.dialog.alert(errMsg);
		}
	}
	//获取验证码
	function getCode(el, type){
		$.post("${ctx}/u/account/mobilecode/phoneCode${ext}",{"type" : type},
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
								<li <c:if test="${pageIndex == 2 }">class="active"</c:if>>2.重置支付密码</li>
								<li>3.重置成功</li>
							</ul>
							<br />
							<c:if test="${pageIndex == 1 }">
								<form action="${ctx }/u/account/security/editPage${ext}" method="post" class="form-horizontal" id="jform">
								<input type="hidden" name="retUrl" value="${retUrl }" />
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-3 control-label">身份证号：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value="${checkIdentity }"
												id="checkIdentity" name="checkIdentity" />
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-3 control-label">已绑定手机号码：</label>
										<div class="col-sm-9">
											<p class="form-control-static">
												<b>${phone }</b>
											</p>
											<p>若当前手机号码已不用，或无法收到验证码？请联系客服处理。</p>
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-3 control-label">输入验证码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value=""
												id="mobilecode" name="mobilecode" />
										</div>
										<div class="col-sm-4">
											<button type="button" class="btn btn-default"
												onclick="getCode(this, 3)">获取验证码</button>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-8">
											<button onclick="formSubmit()" type="button"
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
										<label for="inputEmail3" class="col-sm-3 control-label">设置支付密码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="password" maxlength="20" value=""
												id="paymentCode" name="paymentCode" />
										</div>
										<div class="col-sm-5">
											<p class="form-control-static help-block">密码6-20个字符
											</p>
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-3 control-label">确认支付密码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="password" value="" maxlength="20"
												id="confirmCode" name="confirmCode" />
										</div>
										<div class="col-sm-5">
											<p class="form-control-static help-block">请再次输入支付密码</p>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-8">
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