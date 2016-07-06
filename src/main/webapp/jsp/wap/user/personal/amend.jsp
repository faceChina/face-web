<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>个人中心-修改资料</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }personal/css/main.css">
</head>
<body>
	<div id="box">
		<form method="post" id="jform" data-form>
		    <input type="hidden" id="headimgurl" name="headimgurl" value=""/>
			<div class="head-amend">
				<div class="head-pic" style="position: relative;">
					<img id="showImg"  height="60" width="60"
					<c:choose>
			        	<c:when test="${not empty user.headimgurl}">src="${picUrl }${user.headimgurl }"</c:when>
			        	<c:otherwise>src="${resourcePath }personal/img/head-def.jpg"</c:otherwise>
					</c:choose> alt=""> <input type="button" id="uploadImage" name="uploadImg"
						style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; margin: 0 auto; display: block; width: 100px; height: 100px; opacity: 0;" />
				</div>
			</div>
			
			<div class="list-row ">
				<c:if test="${!isWechat}">
				<div class="list-col">
					<div class="list-inline">账号</div>
					<div class="list-inline box-flex">
						<input type="text" class="form-control" disabled="disabled"
						    value="${user.loginAccount }"
							placeholder="${user.loginAccount }" data-form-control style="color:#333; ">
					</div>
				</div>
				</c:if>
				
				<div class="list-col">
					<div class="list-inline">姓名</div>
					<div class="list-inline box-flex">
						<input type="text" maxlength="30" value="${user.nickname }" name="nickname" class="form-control"
							placeholder="请输入您的昵称" data-form-control>
					</div>
				</div>
				
				<c:if test="${!isWechat}">
				<div class="list-col">
					<div class="list-inline">邮箱</div>
					<div class="list-inline box-flex">
						<input type="text" maxlength="30" value="${user.email }" name="email" class="form-control"
							placeholder="请输入您的邮箱" >
					</div>
				</div>
				</c:if>
			</div>
			<div class="amend-btn button">
				<button type="submit" class="btn btn-danger btn-block disabled"
					data-submit>提交</button>
				<button type="button" class="btn btn-warning btn-block"
					onclick="history.go(-1)">取消</button>
			</div>
		</form>

		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		$("#jform").validate({
			rules : {
				nickname : {
					required : true
				},
				email : {
					email : true
				}
			},
			messages : {
				nickname : {
					required : "请输入昵称"
				},
				email : {
					email : "请输入正确的邮箱"
				}
			}
		});
		
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
	</script>
</body>
</html>

