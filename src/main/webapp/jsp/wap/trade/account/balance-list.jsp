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
<script type="text/javascript" src="${resourcePath}js/plugin/page.js?ver=008"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var start = '${pagination.end}';
		//滚动加载 相关配置*/
		var loadObj = {
			elemt : ".group-item",
			url : "${ctx}/wap/${sessionShopNo}/buy/account/append${ext}",
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
	<c:choose>
		<c:when test="${empty pagination.datas }">
			<div class="purse-banner" style="margin-top:8em;">
				<img src="${resourcePath }accounts/img/bal.jpg"/>
				<p>暂无明细记录</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="help-block">流水明细</div>
			<div id="content" class="group group-justify group-cleartop">
				<c:forEach items="${pagination.datas }" var="data">
					<div class="group-item">
						<a href="${ctx}/wap/${sessionShopNo}/buy/account/detail${ext}?id=${data.id}" class="group-rowspan"> <span
							class="group-colspan"> <em>${data.action }</em><br> <em
								class="clr-light fnt-12">余额：${data.balanceString }</em>
						</span> <span class="group-colspan"> <em class="clr-light fnt-12"><fmt:formatDate value="${data.createTime }" type="date" pattern="yyyy-MM-dd" /></em><br>
								<b 
								<c:choose>
									<c:when test="${data.isFrom }">class="clr-danger"</c:when>
									<c:otherwise>class="clr-blue"</c:otherwise>
								</c:choose> 
								>${data.operationPrice }</b>
						</span>
						</a>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
	<%@ include file="../../common/foot.jsp"%>
	<%@ include file="../../common/nav.jsp"%>
</div>
</body>
</html>