<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-恭喜,支付成功</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}public/css/main.css">
</head>
<body>
<div id="box">
	<div class="success" id="success5">
		<i class="iconfont icon-yuangou clr-danger"></i>
		<p class="success-info">
			支付成功
		</p>
	</div>
	<div class="button">
		<a href="/wap/${sessionShopNo }/buy/order/list.htm" type="button" class="btn btn-order btn-block">查看我的订单</a>
	</div>
	<div class="tip-uploadsl">
		<c:choose>
			<c:when test="${!isWechat}">
				提示：为了您方便管理订单，建议您关注脸谱<br/>
				中国公众号（脸谱bestface）或下载刷脸app
			</c:when>
			<c:otherwise>
				提示：为了您方便管理订单，建议您关注脸谱<br/>
				中国公众号（脸谱bestface）
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${!isWechat}">
	<div class="button">
		<a href="http://www.o2osl.com/app/download.htm" type="button" class="btn btn-danger btn-block">前去下载</a>
	</div>
	</c:if>	
	
	<div class="goback">
		<a href="/wap/${shop.no }/any/gwscIndex.htm">返回首页</a>
		<a href="/wap/${shop.no }/buy/personal/index.htm">个人中心</a>
	</div>
	
	<%@ include file="../../common/foot.jsp"%>
</div>

</body>
</html>