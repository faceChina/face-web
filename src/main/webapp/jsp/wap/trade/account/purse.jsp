<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的钱包</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>
<div id="box">
	<div class="group group-others width20">
		<div class="group-item">
			<a href="${ctx }/wap/${sessionShopNo}/buy/account/list${ext}" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-icon" style="color:#FECF63;font-size:1.8rem;"></i></span>
				<span class="group-colspan">钱包</span>
				<span class="group-colspan">
					<span class="clr-light"><em class="clr-danger"><fmt:formatNumber value="${amount / 100 }" pattern="0.00"/></em> 元</span>
					<i class="iconfont icon-right"></i>
				</span>
			</a>
		</div>
		<div class="group-item">
			<a href="${ctx }/wap/${sessionShopNo}/buy/bankcard/listV2${ext}" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-wodeyinxingqia" style="color:#5DC995;font-size:1.8rem;"></i></span>
				<span class="group-colspan">我的银行卡</span>
				<span class="group-colspan">
					<span class="clr-light"><em class="clr-danger">${number }</em> 张</span>
					<i class="iconfont icon-right"></i>
				</span>
			</a>
		</div>
	</div>
<%@ include file="../../common/foot.jsp"%>
<%@ include file="../../common/nav.jsp"%>
</div>
</body>
</html>