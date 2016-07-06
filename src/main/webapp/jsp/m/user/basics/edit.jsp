<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本资料</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js"></script>	
<script type="text/javascript">
	$(function() {
		tab("basics");
		$('button').click(function(event) {
			var flag = $("#jform").validate({
				rules : {
					name : {
						maxlength : 9,
					},
					email : {
						email : true,
					}
				},
				messages : {
					name : {
						maxlength : $.format("用户名最大{0}个字 符")
					},
					email : {
						email : '邮箱格式错误'
					}
				}
			}).form();
		})

		var button = $('#uploadImage');
		new AjaxUpload(button, {
			action : '${uploadUrl}',
			data : {},
			onSubmit : function(file, ext) {
				var imageSuffix = new RegExp('${imageSuffix}');
				if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
					art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
					return false;
				}
			},
			autoSubmit : true,
			responseType : 'json',
			onChange : function(file, ext) {
			},
			onComplete : function(file, response) {
				if (response.flag == "SUCCESS") {
					$('#showImg').show();
					$('#showImg').attr("src", '${picUrl}' + response.source);
					$('#headimgurl').val(response.path);
				} else {
					art.dialog.alert(response.info);
					return;
				}
			}
		});
	});
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
						<c:set var="crumbs" value="information"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">基本资料</a></li>
								<li class="text-muted">在申请密码找回、绑定解绑手机、支付密码等服务时会需要使用基本资料，请务必牢记。</li>
							</ul>
						</div>
						<form id="jform" method="post">
						    <input type="hidden" id="headimgurl" name="headimgurl" value=""/>
							<div class="content">
								<div class="basics">
									<table class="table table-thleft table-noborder">
										<tbody>
											<tr>
												<th>头像：</th>
												<td><img id="showImg"
													<c:choose>
											        	<c:when test="${not empty user.headimgurl}">src="${picUrl }${user.headimgurl }"</c:when>
											        	<c:otherwise>src="${resourcePath }/img/spadm/basics.jpg"</c:otherwise>
													</c:choose>
													alt="" width="108" height="108" class="pull-left" />
													<div class="btn btn-default btn-upload">
														上传图片<input type="button" value="" class="form-control"
															id="uploadImage" name="uploadImg" >
													</div>
													<p class="text-muted">支持JPG、PNG格式，建议尺寸108 x 108</p></td>
											</tr>
											<tr>
												<th>账号：</th>
												<td><b><c:out value="${user.loginAccount }"></c:out></b></td>
											</tr>
											<tr>
												<th>昵称：</th>
												<td><input type="text" maxlength="30"
													value="<c:out value="${user.nickname }"></c:out>"
													class="form-control input-short-7" name="nickname"></td>
											</tr>
											<tr>
												<th>邮箱地址：</th>
												<td>
													<div>
														<input type="text" maxlength="30"
															value="<c:out value="${user.email }"></c:out>"
															class="form-control input-short-7" name="email">
														<small class="text-muted">用于接收系统邮件，请认真填写 </small>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="row">
									<div class="basics-infor">
										<p>
											真实姓名：
											<c:out value="${user.contacts }"></c:out>
										</p>
										<p>
											身份证号码：
											<c:out value="${user.identity }"></c:out>
										</p>
									</div>
								</div>
								<div class="text-center basics-btn">
									<button type="submit" class="btn btn-default">保存</button>
									<button type="button" class="btn btn-default"
										onclick="window.history.back(-1);">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>