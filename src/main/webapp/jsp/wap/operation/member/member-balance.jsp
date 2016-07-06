<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的余额</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }member/css/main.css">
</head>
<body>

<div id="box">

	<div class="group group-others group-small">
		<div class="group-item">
				<span class="group-colspan">我的余额：<span class="clr-danger">${balance }</span> 元</span>
					<span class="group-colspan">
					<!-- 会员充值功能隐藏 -->
<%-- 						<c:if test="${isRecharge == 1 }"> --%>
<%-- 							<a href="/wap/${sessionShopNo}/buy/recharge/list.htm" class="group-rowspan"> --%>
<!-- 								<button type="button" class="btn btn-warning">充值</button> -->
<!-- 							</a> -->
<%-- 						</c:if> --%>
					</span>
		</div>
	</div>

	<div class="list-tabs">
		<ul>
			<li><a href="javascript:void(0);"><span>1 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>2 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>3 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>4 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>5 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>6 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>7 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>8 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>9 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>10 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>11 月</span><span>2015</span></a></li>
			<li><a href="javascript:void(0);"><span>12 月</span><span>2015</span></a></li>
		</ul>
	</div>
	
	<!-- 没有账单的情况 时显示  -->
	<div class="clr-light member-nobalance" style="display:none;">亲，您还没有产生账单哦！</div>
	<!-- 没有账单的情况 时显示  end -->
	
	<div class="purchase-tit"><b>您的所有账单如下：</b></div>
	
	<div class="purchase-info">
		<c:if test="${empty balanceVoList }">
					亲，您没有产生账单哦
		</c:if>
		<c:if test="${!empty balanceVoList }">
			<table class="table table-bordered">
				<thead>
					<tr class="active">
						<th>日期</th>
						<th>类型</th>
						<th>金额</th>
					</tr>
				</thead>
				<tbody class="table">
					<c:forEach items="${balanceVoList }" var="vo">
						<tr>
							<td>${vo.date }</td>
							<td>${vo.type }</td>
							<td>${vo.money }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	
	<%@ include file="../../common/nav.jsp"%>
	<script type="text/javascript">
	$('.list-tabs li').click(function(){
		$(this).parent().children(".active").removeClass("active");	
		$(this).addClass("active");
		var month=$(this).find('span:first').text();
		var year=$(this).find('span:last').text();
		var monthInt = month.substring(0, month.length-1);
		$('.purchase-tit b').empty().text(monthInt+"月份的消费账单");
		$.post("/wap/${sessionShopNo}/buy/member/balance/listByTime.htm", {'year':year, 'month':monthInt}, function(jsonData) {
			var data = JSON.parse(jsonData);
			if(data == null || data.length==0) {
				$(".purchase-info").empty().text("亲，没有这个月份的消费账单哦");
			} else {
				$(".purchase-info").empty();
					$(".purchase-info").html("<table class='table table-bordered'><thead><tr class='active'><th>日期</th><th>类型</th><th>金额</th></tr></thead><tbody class='table'></tbody></table>")
				for(var i=0; i < data.length; i++) {
					$("tbody[class='table']").append("<tr><td>"+data[i].date+"</td><td>"+data[i].type+"</td><td>"+data[i].money+"</td></tr>");
				}
			}
			
		} );
	});
	</script>
</div>

</body>
</html>