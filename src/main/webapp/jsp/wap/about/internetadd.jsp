<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />

<title>互联网+</title>
<%@ include file="../common/top.jsp" %>
<link rel="stylesheet" href="${resourcePath }/about/css/internetadd.css">
</head>
<body>

<div id="box">
	<div class="d-content">
		<dl data-href="http://www.o2osl.com/">
			<dt><a class="a-ordermanage"><img src="${resourcePath }about/img/item-logo1.jpg" /></a></dt>
			<dd class="d-title">黄金宝</dd>
			<dd>投资理财O2O平台
			<br/></dd>
			<dd><a class="goshoup">进入官网 ></a></dd>
		</dl>
		<dl data-href="http://www.o2osl.com/">
			<dt><a class="a-ordermanage"><img src="${resourcePath }about/img/item-logo2.jpg" /></a></dt>
			<dd class="d-title">亿人壹品</dd>
			<dd>礼品O2O平台
			<br/></dd>
			<dd><a class="goshoup">进入官网 ></a></dd>
		</dl>
		<dl data-href="http://www.o2osl.com/">
			<dt><a class="a-ordermanage"><img src="${resourcePath }about/img/item-logo3.jpg" /></a></dt>
			<dd class="d-title">淘女神</dd>
			<dd>女神O2O平台
			<br/></dd>
			<dd><a class="goshoup">进入官网 ></a></dd>
		</dl>
		<!-- <dl>
			<dt><a class="a-ordermanage"><img src="../img/item-logo2.jpg" /></a></dt>
			<dd class="d-title">消息提醒</dd>
			<dd>店铺订单状态变化提醒和商品上下架即
			<br/>时提醒</dd>
			<dd><a class="goshoup">进入官网 ></a></dd>
		</dl> -->
	</div>
</div>
<script type="text/javascript" src="${resourcePath }js/dpreview.js"></script>
<script type="text/javascript">
 $(function(){
 	$(".d-content dl").off().on("click",function(){
 	    window.location.href=$(this).data("href");
 	});
 });
</script>
</body>
</html>
