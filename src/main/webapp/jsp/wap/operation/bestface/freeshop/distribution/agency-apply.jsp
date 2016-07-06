<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>代理申请</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div id="box">	
	<form class="agency-form"  id="jform" data-form >
		<p class="fnt-16">你确认提交以下分销合作申请吗？</p>
		<div class="group width60">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">
					<c:if test="${empty supplierShop.shopLogoUrl }">
						<img src="${resourcePath }img/defaultShopLogo.jpg" alt="" width="70" height="70">
					</c:if>
					<c:if test="${not empty supplierShop.shopLogoUrl }">
						<img src="${picUrl }${supplierShop.shopLogoUrl}" alt="" width="70" height="70">
					</c:if>
					</li>
					<li class="group-colspan fnt-16">${supplierShop.name }</li>
				</ul>
			</div>
		</div>
		<div class="group width60">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">姓名</div>
					<div class="group-colspan">
						<input type="text" class="form-control" name="nickname" id="username" value="${user.nickname }" data-form-control>
					</div>
				</div>
			</div>
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">电话</div>
					<div class="group-colspan">
						<input type="text" class="form-control" name="cell" id="moblie" value="${user.cell }" data-form-control>
					</div>
				</div>
			</div>
		</div>
		
		<div class="button">
			<button type="button" onclick="formSubmit()" class="btn btn-block btn-danger disabled" data-submit>提交申请</button>
		</div>
		<div class="agency-apply-info">
			<h4 class="fnt-18">注意事项:</h4>
			<p>1、申请成功后，你需要通过PC进行管理代理的商品及零售价格。</p>
			<p>2、申请成功后，不可再申请其他的代理，你可以在PC端主动终止分销合作。</p>
			<p>3、申请成功后，你的商品库存，商品属性将和供应商的保持一致。</p>
		</div>
	</form>
	
</div>

<script type="text/javascript">
$(function(){
	$('#jform').validate({
		rules:{
			username:{
				required:true
			},
			moblie:{
				required:true,
				mobile:true
			}
		},
		messages:{
			username:{
				required:'请输入用户名称'
			},
			moblie:{
				required:'请输入手机号码',
				mobile:'请输入正确的手机号码'
			}
		}
	});
});

function formSubmit() {
	$.post("/free/agency/${supplierShop.no }/saveApply.htm", {'nickname':$("#username").val(), 'cell':$("#moblie").val()}, function(jsonResult) {
		var result = JSON.parse(jsonResult);
		if (result.success) {
			location.href = "/free/agency/${supplierShop.no }/agency-success.htm";
		} else {
			artTip(result.info);
		}
	});
}
</script>
</body>
</html>