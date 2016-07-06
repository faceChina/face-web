<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>登入</title>	
<!-- top -->
<%@ include file="/jsp/m/common/base.jsp"%>
<!--top end -->
</head>
<style type="text/css">
body{min-width: 661px;}
.header_banner{text-align: center;}
@media (min-width: 1000px){
.container{width: 1190px;}	
}
.page-error{
	position: absolute;
	width: 100%;
	height: 100%;
}
.page-error .centent{
	position: absolute;
	width: 100%;
	height: 300px;
	top: 0;
	left: 0;
	margin: auto;
	right: 0;
	bottom: 0;
	text-align: center;
}
</style>
<body>

<div class="header navbar-fixed-top">
	<div class="col-md-4">微信营销平台领导者！<b class="clr-attention">15505711999@163.com</b></div>
	<div class="col-md-8 text-right">
		<a href="">退出</a> | 
		<!-- <a href="">操作指南</a> |  -->
        <span>全国统一服务热线：<em class="tel">400-0003777</em></span>
    </div>
</div>
<!-- body -->
<div class="page-error">
	<div class="centent">
		<h2>${messagetitle}</h2>
		<br/>
		<p>${message}</p>
		<br/>
		<a class="btn btn-default" href="${ctx}${reurl}">返回</a>
	</div>

</div>
<!-- body end -->

<!-- footer -->
	<%@include file="/jsp/m/common/footer.jsp"%>
<!-- footer end -->
	
</body>
</html>


