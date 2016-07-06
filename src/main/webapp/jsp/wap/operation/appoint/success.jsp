<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约成功</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }public/css/main.css">
</head>
<body>
	<div id="box">
		<div class="success" id="success4">
			<i class="iconfont icon-yuangou clr-danger"></i>
			<p class="success-info">
				你已成功提交预约！请等待审核，审核结果将以短信提醒
			</p>
			<c:if test="${user.realType!=0 }">
				<div class="button">
					<a href="${ctx }/wap/${sessionShopNo}/buy/order/bookOrder.htm" class="btn btn-default btn-danger btn-block">查看预约订单</a>
				</div>
			</c:if>
		</div>
	</div>
	<%@ include file="../../common/nav.jsp"%>
</body>
</html>