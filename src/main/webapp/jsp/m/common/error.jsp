<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>错误页面</title>	
<script type="text/javascript">
var logout = function(){
	window.location.href = "${ctx}/j_spring_security_logout";
}
</script>
</head>
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
/* .page-error{
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
} */
.page-error{
	padding-top:12%;
	margin:0 auto;
}
.page-error .centent{
	width:40%;
	margin:0 auto;
	overflow:hidden;
}
.page-error .centent p{
	margin-left:32px;
	margin-top:10px;
	color:#4e4e4e;
	font-weight:bold;
	font-size:22px;
}
.page-error .centent span{
	display:block;
	margin-left:32px;
	margin-top:40px;
	color:#4e4e4e;
	font-size:16px;
}
.page-error-pic{
	margin-top:60px;
	margin-left:32px;
}
.page-error-pic img{
	width:70%;
}
.page-error .centent ul{
	margin-left:27px;
	margin-top:10px;
}
.page-error .centent ul li{
	float:left;
	display:inline-block;
	margin-left:6px;
	margin-right:6px;	
}
.page-error .centent ul li a{
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
		<a href="">退出</a> | 
		<!-- <a href="">操作指南</a> |  -->
        <span>全国统一服务热线：<em class="tel">400-0003777</em></span>
    </div>
</div>
<!-- body -->
<div class="page-error">
	<div class="centent">
		<div class="pull-left">
			<img src="${resourcePath}img/error.jpg" alt="">
		</div>
		<div class="pull-left">
			<div class="page-error-pic">
				<img src="${resourcePath}img/sorry.png" alt=""/>
			</div>
			<p><!-- 您访问的页面弄丢了 --> ${message}</p>
			<span>您可以通过以下方式进行操作</span>
			<ul>
				<li><a href="javascript:history.go(-1);">返回</a></li>
			</ul>
		</div>
	</div>
	

</div>
</body>
</html>

