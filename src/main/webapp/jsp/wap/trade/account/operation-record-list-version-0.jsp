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
		<div class="group group-left group-verticalline">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan"><em class="clr-light">可用金额(元)</em><br>
						<em class="clr-danger"><fmt:formatNumber
								value="${withdrawAmount / 100 }" pattern="0.00" /></em></li>
					<li class="group-colspan"><em class="clr-light">冻结金额(元)</em><br>
						<em><fmt:formatNumber value="${freezeAmount / 100 }" pattern="0.00" /></em></li>
				</ul>
			</div>
		</div>
		<div class="button">
			<button class="btn btn-danger btn-block" onclick="location.href='${ctx}/wap/${sessionShopNo }/buy/account/withdraw/index${ext }'">提现</button>
		</div>
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
		<%@ include file="../../common/foot.jsp"%>
		<%@ include file="../../common/nav.jsp"%>
	</div>
</body>
</html>