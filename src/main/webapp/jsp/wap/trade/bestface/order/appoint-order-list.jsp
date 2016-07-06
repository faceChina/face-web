<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约订单-卖家版</title>
<%@include file="../../../operation/bestface/freeshop/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/appoint/css/main.css">
</head>
<body>
<div id="box">
	<div class="list-tab">
		<ul>
			<li><a href="${ctx }/free/order/appointorders${ext}"><span <c:if test="${empty dto.status }">class="active"</c:if>>全部</span></a></li>
			<li><a href="${ctx }/free/order/appointorders${ext}?status=40"><span <c:if test="${dto.status ==40}">class="active"</c:if>>待处理</span></a></li>
			<li><a href="${ctx }/free/order/appointorders${ext}?status=41"><span <c:if test="${dto.status ==41}">class="active"</c:if>>已确认</span></a></li>
			<li><a href="${ctx }/free/order/appointorders${ext}?status=42"><span <c:if test="${dto.status ==42}">class="active"</c:if>>已拒绝</span></a></li>
			<li><a href="${ctx }/free/order/appointorders${ext}?status=43"><span <c:if test="${dto.status ==43}">class="active"</c:if>>已取消</span></a></li>
		</ul>
	</div>
	<div class="content" id="content">
	<c:if test="${empty pagination.datas }">
		<div class="no-content" >
			<i class="iconfont icon-gouwuche clr-light"></i>
			<p>您还没有订单哦~</p>
		</div>
	</c:if>
	<c:if test="${not empty pagination.datas }">
		<c:forEach items="${pagination.datas }" var="order">
		     <div class="list-row">
				<div class="list-col">
					<div class="list-inline box-flex">预约时间：<fmt:formatDate value="${order.appointmentTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </div>
				</div>
				<c:forEach items="${order.orderItemList }" var="orderItem">
				     <a class="list-col"  href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo }">
						<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
						<div class="list-top box-flex">
							<ul>
								<li class="txt-rowspan2">${orderItem.goodName }</li>
							</ul>
						</div>
						<div class="list-top shop-other">	
							<span>￥<fmt:formatNumber value="${orderItem.totalPrice/100 }" pattern="0.00" /></span>
							<span class="clr-light fnt-12">X ${orderItem.quantity }</span>
						</div>
					</a>
				</c:forEach>
				<c:if test="${empty order.orderItemList }">
					<a class="list-col"  href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo }">
						<div class="list-inline"><img src="${picUrl}${order.picturePath}" alt="" width="70" height="70"></div>
						<div class="list-top box-flex">
							<ul>
								<li class="txt-rowspan2">--</li>
							</ul>
						</div>
						<div class="list-top shop-other">	
							<span>￥<fmt:formatNumber value="${order.totalPrice/100 }" pattern="0.00" /></span>
						</div>
					</a>
				</c:if>
				<div class="list-col ">
					<div class="list-inline  box-flex clr-warning">${order.statusName}</div>
					<div class="list-inline">
					    <c:if test="${order.status == 40}">
					    	<button class="btn btn-default" data-refuse="1" onclick="confimOrder(this, '${order.orderNo}')">确认订单</button>
					    	<button class="btn btn-default" data-refuse="1" onclick="refuse(this, '${order.orderNo}')">拒绝订单</button>
					    </c:if>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
	</div>
</div>
<%@ include file="../../../common/freeNav.jsp" %>
<!-- 拒绝订单以及拒绝原因填写  -->
<div style="display:none;" id="j-refuse">
	<form action="" method="post" id="refuse-form">
		<p class="clr-light" style="text-align:left;">请填写拒绝原因：</p>
		<textarea class="form-textarea" name="refuseReason" id="refuseReason" cols="35" rows="10"></textarea>
	</form>
</div>
<script type="text/javascript">
navbar('order');
$(document).ready(function(){
	//滚动加载 相关配置*/
	var loadObj = {
		elemt : ".list-row",
		url:"${ctx }/free/order/appendappointorders${ext}",
		totalRow :'${pagination.totalRow}',
		start:'${pagination.end}',
		param:{
	    	status:'${dto.status}'
	    },
		loadType:'html',
		idd : "#content"
	};
	rollLoad.init(loadObj);//触发滚动事件
});
//确认订单
function confimOrder(el, orderNo){
	art.dialog({
		lock: true,
		width: '90%',
		title:'温馨提示',
	    content: '您确定受理该订单？',
	    ok: function () {	
	    	$.post("${ctx }/free/order/"+ orderNo + "/41${ext}", {}, function(jsonData){
	    		var data = JSON.parse(jsonData);
	    		if (data.success) {
	    			$(el).parent().parent().html("已确认");
	    		} else {
	    			art.dialog.alert("确认订单失败");
	    		}
	    	});
	    },
	    cancelVal: '取消',
	    cancel: true 
	});
}
//拒绝订单
function refuse(el, orderNo) {
	art.dialog({
		lock: true,
		width: '90%',
		title:'提示',
	    content:document.getElementById('j-refuse'),
	    ok: function () {
	    	var formVali = $('#refuse-form').validate({
	    		rules : {
	    			refuseReason : {
	    				required:true
	    			},
	    		},
	    		messages : {
	    			refuseReason : {
	    				required:"请输入拒绝理由"
	    			}
	    		}
	    	})
	    	if (!formVali.form()) {
	    		return false;
	    	}
	    	var refuseReason = $('#refuseReason').val();
			$.post("${ctx }/free/order/"+ orderNo + "/42${ext}", {memo:refuseReason}, function(jsonData){
				var data = JSON.parse(jsonData);
				if (data.success) {
					$(el).parent().parent().html("已拒绝");
				} else {
					art.dialog.alert("拒绝订单失败");
				}
			});
	    },
	    cancelVal: '取消',
	    cancel: true
	});
}
</script>
</body>
</html>