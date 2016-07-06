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
		<title>会员中心</title>
		<%@include file="../../common/top.jsp"%>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/main.css">
	</head>
	<body>
		<c:choose>
			<c:when test="${not empty memberEnactment}">
				<div id="box">
<!-- 					<div class="member-card"> -->
<!-- 						<div class="card"> -->
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${memberEnactment.imgPath!=null && memberEnactment.imgPath!='' && !fn:contains(memberEnactment.imgPath,'resource/') }"> --%>
<%-- 									<img src="${picUrl }${memberEnactment.imgPath }" alt=""/> --%>
<%-- 								</c:when> --%>
<%-- 								<c:when test="${memberEnactment.imgPath!=null && memberEnactment.imgPath!='' && fn:contains(memberEnactment.imgPath,'resource/') }"> --%>
<%-- 									<img src="${ctx }${memberEnactment.imgPath}" alt=""/> --%>
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<%-- 									<img src="${resourcePath }operation/member/img/pic-01.png" alt=""/> --%>
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
<%-- 							<div class="cardname" style="color:${memberEnactment.cardNameColor};"> --%>
<%-- 								${memberEnactment.cardName } --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
<!-- 					<div class="member-info"> -->
<!-- 						<div class="group width20"> -->
<!-- 							<div class="button"> -->
<!-- 								<button class="btn btn-danger btn-block fnt-16" onclick="getcard()">登陆并领取</button> -->
<!-- 							</div> -->
<!-- 							<div class="group-item"> -->
<!-- 								<ul class="group-rowspan"> -->
<!-- 									<li class="group-colspan"> -->
<!-- 										<i class="iconfont icon-cloudiconfontwenben clr-light fnt-20"></i> -->
<!-- 									</li> -->
<!-- 									<li class="group-colspan fnt-16">会员卡说明</li> -->
<!-- 								</ul> -->
<!-- 							</div> -->
<!-- 							<div class="group-item"> -->
<!-- 								<ul class="group-rowspan"> -->
<!-- 									<li class="group-colspan" style="color:#666;">会员卡泛指普通身份识别卡，包括商场、 宾馆、健身中心、酒家等消费场所的会 员认证。  </li> -->
<!-- 								</ul> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
					
					
					<div class="group group-cleartop member-card">
						<div class="card">
							<c:choose>
								<c:when test="${memberEnactment.imgPath!=null && memberEnactment.imgPath!='' && !fn:contains(memberEnactment.imgPath,'resource/') }">
									<img src="${picUrl }${memberEnactment.imgPath }" alt=""/>
								</c:when>
								<c:when test="${memberEnactment.imgPath!=null && memberEnactment.imgPath!='' && fn:contains(memberEnactment.imgPath,'resource/') }">
									<img src="${ctx }${memberEnactment.imgPath}" alt=""/>
								</c:when>
								<c:otherwise>
									<img src="${resourcePath }operation/member/img/pic-01.png" alt=""/>
								</c:otherwise>
							</c:choose>
							<div class="cardname" style="color:${memberEnactment.cardNameColor};">${memberEnactment.cardName }</div>
							<div class="cardcode" style="color:${memberEnactment.cardNameColor};">卡号：${memberEnactment.cardCode } - 1</div>
						</div>
						<div class="info">使用时向服务员出示此卡</div>
					</div>
					
					<div class="group member-card">
						<span class="info fnt-16" style="padding-top:10px;">登录后可享受更多线上折扣优惠</span>
						<div class="button">
							<button class="btn btn-danger btn-block fnt-16" onclick="getcard()">登录</button>
						</div>
					</div>
					
					<div class="group width20">
						<div class="group-item">
							<ul class="group-rowspan">
								<li class="group-colspan"><i class="iconfont icon-cloudiconfontwenben clr-light fnt-20"></i></li>
								<li class="group-colspan fnt-16">会员卡说明</li>
							</ul>
						</div>
						<div class="group-item">
							<ul class="group-rowspan">
								<li class="group-colspan" style="color:#666;">会员卡泛指普通身份识别卡，包括商场、 宾馆、健身中心、酒家等消费场所的会 员认证。 </li>
							</ul>
						</div>
					</div>
					<%@include file="../../common/foot.jsp" %>
					<%@include file="../../common/nav.jsp" %>
				</div>
			</c:when>
		</c:choose>
				
		<script type="text/javascript">
			//分享求收藏
			function getcard(){
				location.href='${WgjUrl }/wap/${shop.no}/buy/member/getcard${ext}';
			}
		</script>
	</body>
</html>