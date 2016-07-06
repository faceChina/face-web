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
<title>我的申请</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div id="box">	
	<c:if test="${not empty authVo }">
		<c:forEach items="${authVo }" var="vo">
			<div class="group width80">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">店铺名称</li>
						<li class="group-colspan clr-light">${vo.supplierShopName }</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">申请时间</li>
						<li class="group-colspan clr-light">
							<fmt:formatDate value="${vo.applyTime }" pattern="yyyy年MM月dd日 HH:mm"/>
						</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">申请状态</li>
						<li class="group-colspan clr-warning">${vo.status }</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<c:if test="${vo.status == '申请通过' }">
							<li class="group-colspan">恭喜您申请成功，分销功能管理要去${wgjurl } 哦！</li>
						</c:if>
						<c:if test="${vo.status == '审核中' }">
							<li class="group-colspan">还在审核中，请耐心等待哦！</li>
						</c:if>
						<c:if test="${vo.status == '被拒绝' }">
							<li class="group-colspan">拒绝原因</li>
							<c:if test="${vo.refuseType == 1 }">
								<li class="group-colspan clr-light">${vo.refuseReason }</li>
							</c:if>
							<c:if test="${vo.refuseType == 2 }">
								<li class="group-colspan clr-light">您只能代理一家供应商</li>
							</c:if>
						</c:if>
					</ul>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty authVo }">
		您还没申请过任何供货商的代理哦，快去申请吧！
	</c:if>
</div>
</body>
</html>