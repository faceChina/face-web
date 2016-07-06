<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>代理申请成功</title>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/subbranch/css/main.css">
</head>
<body>
<div id="box">	
	<form action="#" method="post" class="agency-form"  id="jform" data-form >
		<div class="group width60" onclick="goIndex();">
			<div class="group-item"  style="border-bottom:none;">
				<ul class="group-rowspan">
					<li class="group-colspan" style="text-align:center;padding:10px 0 5px 0;"><img src="${picUrl}${shop.shopLogoUrl }" alt="" width="120"></li>
				</ul>
			</div>
			<div class="group-item" style="border-bottom:none;">
				<ul class="group-rowspan">
					<li class="group-colspan fnt-16" style="text-align:center;padding:5px 0 5px 0;">${shop.name }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan fnt-16 clr-light" style="text-align:center;padding:0px 0 10px 0;">欢迎您的加入</li>
				</ul>
			</div>
		</div>
		
		<div class="group width60">
			<div class="group-item">
				<ul class="group-rowspan">
					<c:choose>
						<c:when test="${isAppBrowser }">
							<li class="group-colspan fnt-16">恭喜您成为${shop.name }的分店，前去管理店铺吧！</li>
							</c:when>
						<c:otherwise>
							<li class="group-colspan fnt-16">请下载刷脸app,通过申请的手机号码登录即可进入您的店铺。</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
		
		<div class="button">
		<c:choose>
			<c:when test="${isAppBrowser }">
				<a href="/appBrowser/subbranch/apply/success.htm" class="btn btn-block btn-danger">前去管理</a>
			</c:when>
			<c:otherwise>
				<a href="/app/download.htm" class="btn btn-block btn-danger">前去下载</a>
			</c:otherwise>
		</c:choose>
		</div>
	</form>
	
</div>
<script type="text/javascript">
	//åé¨ä»£çç³è¯·æå
	var session_agency=sessionStorage.getItem('session_agency');
	if(session_agency=='request'){
		$('#js-extrequirest').hide();
		$('#js-inrequest').show();
	};
	function goIndex(){
		window.location.href="/wap/${shop.no }/any/gwscIndex.htm<c:if test='${not empty subbrachid}'>?subbranchId=${subbrachid }</c:if>";
	}
</script>
</body>
</html>