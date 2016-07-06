<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>404错误页面</title>	
</head>
<script type="text/javascript">
var logout = function(){
	window.location.href = "${ctx}/j_spring_security_logout";
}
</script>
<style type="text/css">
body{
	width:100%;
	margin:0 auto;
}
.header_banner{text-align: center;}
@media (min-width: 1000px){
	.container{
		width: 1190px;
		margin:0 auto;
	}	
}
.page-error{
	position: absolute;
	width: 100%;
	height: 100%;
}
.page-error .centent{
	position: absolute;
	width: 40%;
	height:310px;
	top:0;
	left:0;
	margin: auto;
	right: 0;
	bottom: 0;
	text-align: center;
} 
.page-error .centent img{
	width:100%;
	position:relative;
	left:0;
	top:0;
}
.website{
	width:80%;
	position:absolute;
	left:42.5%;
	top:0;
	margin-top:18%;
	text-align:center;
	cursor:pointer;	
	font-size:100%;
	color:#4e4e4e;
} 
.website a{
	padding-bottom:1px;
	color:#d24040;
	font-weight:bold;
	border-bottom:1px solid #d24040;
}
</style>
<body>

<div class="header navbar-fixed-top">
	<div class="col-md-4">微信营销平台领导者！<b class="clr-attention">15505711999@163.com</b></div>
	<div class="col-md-8 text-right">
<!-- 		<a href="">退出</a> |  -->
<!-- 		<a href="">操作指南</a> |  -->
        <span>全国统一服务热线：<em class="tel">400-0003777</em></span>
    </div>
</div>
<!-- body -->
<div class="page-error">
	<div class="centent">
		<img src="${resourcePath}img/404.png" alt="">
		<p class="website">你可以回到<a href="javascript:logout();">网站首页</a>看看</p>
	</div>
</div>
<!-- body end -->
</body>
</html>


