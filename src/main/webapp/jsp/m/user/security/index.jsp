<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全设置</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("security");
	});
</script>
</head>
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
							<li class="active"><a href="#members-set" data-toggle="tab">安全设置</a></li>
						</ul>
					</div>
					<div class="content">
						<table class="table table-thleft table-border">
							<tbody>
								<tr>
									<th>登录密码</th>
									<td class="text-left">互联网账号存在被盗风险，建议您定期更改密码以保护账户安全</td>
									<td><button type="button" class="btn btn-default"
											onclick="altLogincode()">修改</button></td>
								</tr>
								<tr>
									<th>手机号码绑定</th>
									<td class="text-left">您绑定的手机号码：${phone }</td>
									<td><button type="button" class="btn btn-default"
											onclick="location.href='${ctx }/u/account/security/bindPhone${ext}?retUrl=/u/account/security/index'">修改</button></td>
								</tr>
								<tr>
									<th>支付密码</th>
									<td class="text-left">用于开启钱包余额消费，在钱包账户发生资金变动（如购买、提现）时验证支付密码，保障账户安全</td>
									<td><a href="javascript:void(0)" class="btn btn-default"
										onclick="location.href='${ctx }/u/account/security/paymentCode${ext}?retUrl=/u/account/security/index'">修改</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 修改密码 -->
<div id="f-amend" style="display: none;">
	<form id="j-chagepassword"
	      class="form-horizontal  text-left">
		<div class="form-group">
			<label for="inputEmail3" class="col-md-4 control-label">当前密码：</label>
			<div class="col-md-8">
				<input type="password" class="form-control" id="password"  maxlength="20"
					placeholder="输入当前密码" name="password">
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-md-4 control-label">新密码：</label>
			<div class="col-md-8">
				<input type="password" class="form-control" id="newPasswd" maxlength="20"
					placeholder="输入新密码" name="newPasswd">
			</div>
		</div>
		<div class="form-group">
			<label for="inputEmail3" class="col-md-4 control-label">确认新密码：</label>
			<div class="col-md-8">
				<input type="password" class="form-control" id="confirmPasswd" maxlength="20"
					placeholder="再次输入新密码" name="confirmPasswd">
			</div>
		</div>
	</form>
</div>
<%@include file="../../common/footer.jsp"%>
<script type="text/javascript">
function altLogincode(){
	$('#password').val(null);
	$('#newPasswd').val(null);
	$('#confirmPasswd').val(null);
	art.dialog({
		lock : true,
		width : '400px',
		title : "密码修改",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-chagepassword"),
		button : [ {
			name : '确定',
			callback : function() {
				var flag = $('#j-chagepassword').validate({
					rules:{
						password:{
							required:true,
							minlength: 6,
							maxlength:20
						},
						newPasswd:{
							required:true,
							minlength: 6,
							maxlength:20
						},
						confirmPasswd:{
							required:true,
							minlength: 6,
							equalTo : "#newPasswd",
							maxlength:20
						}
						
					},
					messages:{
						password:{
							required : "请输入当前登陆密码",
							minlength : $.format("密码不能小于{0}个字符"),
							  maxlength: $.format("密码不能大于{0}个字符")
						},
						newPasswd:{
							required : "请输入当前登陆密码",
							minlength : $.format("密码不能小于{0}个字符"),
							  maxlength: $.format("密码不能大于{0}个字符")
						},
						confirmPasswd:{
							required : "请输入当前登陆密码",
							minlength : $.format("密码不能小于{0}个字符"),
							equalTo : "两次输入密码不一致",
							  maxlength: $.format("密码不能大于{0}个字符")
						}
					}
				}).form();
				if(flag){
					$.post("${ctx }/u/account/security/logincode${ext}", $('#j-chagepassword').serialize(), 
							function(jsonData){
						  var data = JSON.parse(jsonData);
						  if (data.success) {
							  var time=3;
							  art.dialog.alert("密码修改成功，"+ time +"秒后系统将自动退出..."); 
							  var setIntervalID=setInterval(function(){
									time--;
									if(time==0){
										clearInterval(setIntervalID);
										window.location.href = "${ctx}/j_spring_security_logout";
									}
								},1000);
						  } else {
							  art.dialog.alert(data.info);
						  }
					});
					return true;
				}else{
					return false;
				}
			},
			focus : true
		},
		{
			name:'取消'
		}
		]
	});
}
</script>
</body>
</html>