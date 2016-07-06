<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>会员卡充值</title>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/main.css">
</head>
<body>

<div id="box">

	<c:choose>
		<c:when test="${not empty marketingActivityDetails }">
			<!-- 发行店铺会员卡的情况下显示   -->
			<div class="member-recharge" id="j-changerecharge">
				<ul>
					<c:forEach items="${marketingActivityDetails }" var="data" varStatus="status">
							<c:choose>
								<c:when test="${status.index == 0 }">
									<li data-value="${data.premiseVal }">
										<i class="iconfont clr-danger icon-roundcheck">
											<!-- <input type="radio" name="premiseVal" value=""/> -->
										</i>
										<span>充
										<fmt:formatNumber value="${data.premiseVal/100 }" pattern="0.00"></fmt:formatNumber>
										元
										<c:if test="${data.resultVal !=null && 
													  data.resultVal !='' &&
													  data.resultVal !=0}">
											送<fmt:formatNumber value="${data.resultVal/100 }" pattern="0.00"></fmt:formatNumber>
										</c:if>
										</span>
									</li>
								</c:when>
								<c:otherwise>
									<li data-value="${data.premiseVal }">
										<input type="hidden" name="premiseVal" value="${data.premiseVal }"/>
										<i class="iconfont clr-danger"></i>
										<span>充
										<fmt:formatNumber value="${data.premiseVal/100 }" pattern="0.00"></fmt:formatNumber>
										元
										<c:if test="${data.resultVal !=null && 
													  data.resultVal !='' &&
													  data.resultVal !=0}">
											送<fmt:formatNumber value="${data.resultVal/100 }" pattern="0.00"></fmt:formatNumber>
										</c:if>
										</span>
									</li>
								</c:otherwise>
							</c:choose>
					</c:forEach>
				</ul>
			</div>
			<form id="rechargeNow" action="${ctx}/wap/${shop.no}/buy/recharge/payWay${ext}">
				<input type="hidden" name="validateToken" value="${validateToken }">
				<input type="hidden" name="price" value="" id="price"/>
				<div class="button">
					<a href="javascript:recharge()" class="btn  btn-danger btn-block">立即充值</a>
				</div>
			</form>
			
			<div class="member-recharge-info">
				<p>注：1.充值完成后直接打到会员卡账户</p>
				<p style="text-indent:32px;">2.会员卡金额只能用于本店铺消费</p>
				<p style="text-indent:32px;">3.不可转赠，不可退款</p>
			</div>
		</c:when>
		<c:otherwise>
			<!-- 没有发行店铺会员卡的情况下显示   -->
			<div class="member-norecharge" style="display:block;">
				<i class="iconfont icon-info clr-danger fnt-24"></i>
				<span>该店铺还未发行会员充值卡哦！</span>
			</div>
		</c:otherwise>
	</c:choose>

	
	
	<%@include file="../../common/foot.jsp" %>
	<%@include file="../../common/nav.jsp" %>
</div>

<script type="text/javascript">
var chageIndex;
	$('#j-changerecharge ul li').click(function(){
		$('#j-changerecharge ul li .iconfont').removeClass('icon-roundcheck');
		$(this).find('.iconfont').addClass('icon-roundcheck');
		chageIndex = $(this).data('value');
	});
	
	function recharge() {
		console.log(chageIndex);
		var price = $('#j-changerecharge ul li').find('.icon-roundcheck').parent().data('value');
		$("#price").val(price);
		console.log($("#price").val());
		$("#rechargeNow").submit();
	}
</script>
</body>
</html>