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
<title>代理申请成功</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div id="box">	
	<form action="#" method="post" class="agency-form"  id="jform" data-form >
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
		
		<div class="agency-apply-info" style="margin-top:10px;">
		
			<!-- 外部申请成功后显示  -->
			<p class="fnt-16" id="js-extrequirest">恭喜你的合作申请已经成功提交，供应商会尽快处理，请耐心等待哦！</p>
			
			<!-- 内部申请成功后显示  -->
			<p class="fnt-16" id="js-inrequest" style="display:none;">恭喜您申请成功，分销功能管理要去 www.boss-sir.com 哦！</p>
			
		</div>
		
		<div class="button">
			<a href="/free/mine/index.htm" class="btn btn-block btn-danger">我知道啦</a>
		</div>
	</form>
	
</div>
<script type="text/javascript">
	//内部代理申请成功
	var session_agency=sessionStorage.getItem('session_agency');
	if(session_agency=='request'){
		$('#js-extrequirest').hide();
		$('#js-inrequest').show();
	};
</script>
</body>
</html>