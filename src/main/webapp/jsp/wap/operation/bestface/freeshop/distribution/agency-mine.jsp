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
<title>我的代理</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<style type="text/css">
	html{background:#fff;}
	body{background:#fff;}
</style>
<body>

<div id="box">	

 	<div class="table-box">
		<table class="table table-bordered">
			<thead>
				<tr class="danger">
					<th>供应商名称</th>
					<th>商品数量</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${shopName }</td>
					<td>${count }</td>
				</tr>
			</tbody>
		</table>
	</div>
	
</div>

<script type="text/javascript">		
//状态传值，前端专用，后端自行删除
sessionStorage.setItem('session_agency','request');
</script>
</body>
</html>
