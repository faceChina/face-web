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
<title>账户余额</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
</head>
<body>

	<div id="box">
		<div class="group group-justify">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">流水号：</li>
					<li class="group-colspan clr-light">${detail.serialNumber }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">动作：</li>
					<li class="group-colspan clr-light">${detail.action }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">
						<c:choose>
							<c:when test="${detail.isFrom }">
								支出：
							</c:when>
							<c:otherwise>
								收入：
							</c:otherwise>
						</c:choose>
					</li>
					<li <c:choose>
							<c:when test="${detail.isFrom }">class="clr-danger group-colspan"</c:when>
							<c:otherwise>class="clr-blue group-colspan"</c:otherwise>
						</c:choose>
				    ><b>${detail.operationPrice } 元</b></li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">时间：</li>
					<li class="group-colspan clr-light"><fmt:formatDate value="${detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">余额：</li>
					<li class="group-colspan clr-light">${detail.balanceString }元</li>
				</ul>
			</div>
<!-- 			<div class="group-item"> -->
<!-- 				<ul class="group-rowspan"> -->
<!-- 					<li class="group-colspan">收支对象：</li> -->
<%-- 					<li class="group-colspan clr-light">${detail.target }</li> --%>
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
</body>
</html>