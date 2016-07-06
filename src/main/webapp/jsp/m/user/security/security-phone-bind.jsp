<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全设置</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
    var validForm;
	$(function() {
		tab("security");
		dealErrMsg();
		validForm = $("#jform").validate({
			ignore: ".ignore",
			rules : {
				cell : {
					required : true,
					mobile : true
				},
				mobilecode : {
					required : true,
					minlength : 6
				},
			},
			messages : {
				cell : {
					required : "请输入手机号码",
					mobile : "请输入正确的手机号码"
				},
				mobilecode : {
					required : "请输入验证码",
					minlength : $.format("验证码不能小于{0}个字符")
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
		cell = $('#cell').val();
		if (type == 6 && (cell == null || cell == '')) {
			art.dialog.alert("请输入手机号码");
		}
		$.post("${ctx}/u/account/mobilecode/phoneCode${ext}",{"type" : type, "phone" : cell},
				  function(falg){
				if("true" != falg){
					art.dialog.alert("获取验证码失败");
				}
		});
		$(el).attr("disabled",true); 
		var time=60;
		var setIntervalID=setInterval(function(){
			time--;
			$(el).html("验证码已发送 "+ time +"秒");
			$('#j-codeprop').show();
			if(time==0){
				clearInterval(setIntervalID);
				$('#j-codeprop').hide();
				$(el).attr("disabled",false).html("免费获取验证码");
			}
		},1000);
	}
	function checkCell(type) {
		var cell = $('#cell').val();
		var mobilecode = $('#mobilecode').val();
		if (type == 6 && (cell == null || cell == '')) {
			art.dialog.alert("请输入手机号码");
		}
		$.post('${ctx}/u/account/mobilecode/testMobilecode${ext}',  {"type" : type, "mobilecode" : mobilecode, "phone" : cell}, function(jsonData){
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
	<div class="container" id="j-content">
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
								<li class="active"><a href="#members-set" data-toggle="tab">手机号码绑定</a></li>
								<li class="text-muted">温馨提示：原手机号码不能接收短信,请联系客服人员为您进行修改。客服电话：<span
									class="fontcor-red">400-000-3777 </span></li>
							</ul>
						</div>
						<div class="content">
							<ul class="m-steps">
								<li <c:if test="${pageIndex == 1 }">class="active"</c:if>>1.身份验证</li>
								<li <c:if test="${pageIndex == 2 }">class="active"</c:if>>2.绑定新手机</li>
								<li>3.修改完成</li>
							</ul>
							<c:if test="${pageIndex == 1 }">
								<form action="${ctx}/u/account/security/checkcell${ext}" class="form-horizontal" method="post" id="jform" style="width: 600px; margin: 0 auto;">
								    <input type="hidden" name="retUrl" value="${retUrl }" />
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-4 control-label">输入已绑定手机号码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value="${phone }"
												id="cell" name="cell" disabled />
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-4 control-label">输入验证码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" value=""
												id="mobilecode" name="mobilecode" />
										</div>
										<div class="col-sm-4">
											<button type="button" class="btn btn-default"
												onclick="getCode(this, 5)">获取验证码</button>
										</div>
									</div>
									<div class="form-group">
									    <label for="inputPassword3" class="col-sm-3 control-label"></label>
									    <div class="col-sm-9" id="j-codeprop" style="display:none;">
									      <small>还没收到验证码？请查看短信是否被拦截</small>	
									    </div>
								 	</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-8">
											<button name="valie-check" type="button" onclick="checkCell(5)" class="btn btn-default active">下一步</button>
										</div>
									</div>
								</form>
							</c:if>
							<c:if test="${pageIndex == 2 }">
								<form action="${ctx}/u/account/security/bind${ext}" class="form-horizontal" method="post" id="jform" style="width: 600px; margin: 0 auto;">
									<input type="hidden" name="retUrl" value="${retUrl }" />
									<input type="hidden" name="validateToken" value="${validateToken }" />
									<div class="form-group">
									    <label for="inputEmail3" class="col-sm-4 control-label">输入新手机号码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" id="cell" name="cell" />
										</div>
									</div>
									  <div class="form-group">
									    <label for="inputPassword3" class="col-sm-4 control-label">输入验证码：</label>
									    <div class="col-sm-4">
									      <input class="form-control" type="text" value="" id="mobilecode" name="mobilecode"  maxlength="20"/>
									    </div>
									    <div class="col-sm-4">
									    	<button type="button" class="btn btn-default" onclick="getCode(this, 6)">获取验证码</button>
									    </div>
									  </div>
										<div class="form-group">
										    <label for="inputPassword3" class="col-sm-3 control-label"></label>
										    <div class="col-sm-9" id="j-codeprop" style="display:none;">
										      <small>还没收到验证码？请查看短信是否被拦截</small>	
										    </div>
								 		</div>
									  <div class="form-group">
									    <div class="col-sm-offset-4 col-sm-8">
									      <button name="valie-check" type="button" onclick="checkCell(6)" class="btn btn-default active" >下一步</button>
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