<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>账户余额</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<script type="text/javascript" src="${resourcePath}js/plugin/page.js"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/wallet/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
<script type="text/javascript">
	$(document).ready(function() {
		var start = '${pagination.end}';
		//滚动加载 相关配置*/
		var loadObj = {
			elemt : ".group-item",
			url : "/free/wallet/append.htm",
			totalRow : '${pagination.totalRow}',
			start : start,
			idd : "#content",
			loadType : "html"
		};
		rollLoad.init(loadObj);//触发滚动事件
	});
</script>
</head>
<body>

<div id="box">
	<div class="purse-banner">
		<img src="${resourcePath }accounts/img/purse.png"/>
		<p class="clr-light">可用金额</p>
		<p>￥<fmt:formatNumber value="${withdrawAmount / 100 }" pattern="0.00" /></p>
		<div class="button">
			<button class="btn btn-danger btn-block" onclick="location.href='${ctx}/free/wallet/withdraw/index${ext }'">提现</button>						
		</div>
	</div>
	<div class="group group-justify">
		<div class="group-item">
			<div class="group-rowspan">
				<span class="group-colspan">冻结金额</span>
				<span class="group-colspan">￥<fmt:formatNumber value="${freezeAmount / 100 }" pattern="0.00" /></span>
			</div>
		</div>
		<div class="group-item">
			<a href="${ctx}/free/wallet/balancelist${ext}" class="group-rowspan">
				<span class="group-colspan">流水明细</span>
				<span class="group-colspan"><i class="iconfont icon-right"></i></span>
			</a>
		</div>
	</div>
	<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/freeNav.jsp" %>
	
</div>
<script type="text/javascript">
	navbar("purse");
</script>
</body>
</html>