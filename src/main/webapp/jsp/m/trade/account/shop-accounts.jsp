<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("basics");
	});
</script>
<script type="text/javascript"
	src="${resourceBasePath}js/ajaxupload3.9.js"></script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2">
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
								<li class="active"><a href="#members- set"
									data-toggle="tab">账户信息</a></li>
								<li class="text-muted"></li>
							</ul>
						</div>
						<div class="content">
							<form id="j-form" >
							<table class="table table-striped table-thleft">
								<tbody>
									<tr>
										<th width="20%">店铺名称：</th>
										<td data-nano><c:out value="${shop.name }"></c:out></td>
									</tr>
									<tr>
										<th width="20%">店铺类型：</th>
										<td>${shop.typeString }</td>
									</tr>
									<tr>
										<th>签约年月：</th>
										<td><fmt:formatDate value="${shop.signingTime }" pattern="yyyy年MM月dd日" /> </td>
									</tr>

									<tr>
										<th>有效期至：</th>
										<td><fmt:formatDate value="${shop.effectiveTime }" pattern="yyyy年MM月dd日" /></td>
									</tr>
									<c:if test="${!empty shop.proxyType }">
										<c:if test="${shop.proxyType == '内部代理商' }">
											<tr>
												<th>店铺授权码：</th>
												<td>${shop.authorizationCode }</td>
											</tr>
										</c:if>
									</c:if>
									<tr>
										<th>店铺LOGO：</th>
										<td class="accounts-upload">
											<div class="j-picpath">
												<img id="showImg" <c:choose>
										        	<c:when test="${not empty shop.shopLogoUrl}">src="${picUrl }${shop.shopLogoUrl }"</c:when>
										        	<c:otherwise>src="${resourcePath }img/defaultShopLogo.jpg"</c:otherwise>
												</c:choose> alt="">
												<input type="hidden" name="shopLogoUrl" id="shopLogoUrl">
											</div>
											<div>
												<p class="prop">尺寸大小：300*300px</p>
												<button type="button" id="uploadImage" name="uploadImage" class="btn btn-default">上传图片</button>
											</div>
											
										</td>
									</tr>
									
								</tbody>
							</table>
							<div class="text-center">
									<button type="button" name="sumbit" class="btn btn-danger btn-lg">保存</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
<script>
var activity = $("#j-form").validate({
	rules:{
		phone:{
			phone:true
		},
		url:{
			url2:true
		}
	},
	messages:{
		phone:{
			required:'客户电话错误'
		},
		url:{
			url2:"地址格式错误！"
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
			$('#showImg')
					.attr("src", '${picUrl}' + response.source);
			$('#shopLogoUrl').val(response.path);
		} else {
			art.dialog.alert(response.info);
			return;
		}
	}
});
$('button[name="sumbit"]').click(function(){
    var flag = activity.form();
	if(flag){
        //提交
        $.post("saveInfo.htm", {'wechat':$("#wechat").val(), 'guideUrl':$("#guideUrl").val(), 'shopLogoUrl':$("#shopLogoUrl").val()}, function(jsonResult) {
        	var data = JSON.parse(jsonResult);
        	if (data.success) {
				art.dialog.alert("保存成功!");
			} else {
				art.dialog.alert("保存失败!");
			}
        });
        
	}else{
		//验证不通过
	}
})
</script>
</html>