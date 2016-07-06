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
<title>订单列表</title>
<%@include file="../../../operation/bestface/freeshop/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }order/css/main.css">
</head>
<body>
	<div id="box">
		<div class="list-tabs">
			<ul>
				<li><a href="${ctx }/free/order/listordinary${ext}"><span
						class="<c:if test='${empty dto.status }'>active</c:if>">全部</span></a></li>
				<li><a href="${ctx }/free/order/listordinary${ext}?status=1"><span
						class="<c:if test='${dto.status==1 }'>active</c:if>">待付款</span></a></li>
				<li><a href="${ctx }/free/order/listordinary${ext}?status=2"><span
						class="<c:if test='${dto.status==2 }'>active</c:if>">待发货</span></a></li>
				<li><a href="${ctx }/free/order/listordinary${ext}?status=3"><span
						class="<c:if test='${dto.status==3 }'>active</c:if>">已发货</span></a></li>
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
				<div class="list-row" data-pro-id="">
					<div class="list-col">
						<div class="list-inline box-flex">订单号：${order.orderNo }</div>
						<div class="list-inline clr-warning">${order.statusName }</div>
					</div>
					<c:forEach items="${order.orderItemList }" var="orderItem">
						<a class="list-col" href="${ctx }/free/order/detail${ext}?orderNo=${order.orderNo }">
							<div class="list-inline">
								<img src="${picUrl}${orderItem.skuPicturePath}" alt=""
									width="70" height="70">
							</div>
							<div class="list-top box-flex">
								<ul>
									<li class="txt-rowspan2">${orderItem.goodName }</li>
									<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertiesName }</li>
								</ul>
							</div>
							<div class="list-top shop-other">
								<span>￥<fmt:formatNumber value="${orderItem.totalPrice/100 }" pattern="0.00" />
								</span> <span class="clr-light fnt-12">X ${orderItem.quantity }</span>
							</div>
						</a>
					</c:forEach>
					<div class="list-col">
						<div class="list-inline shop-info">
							<p>共${order.quantity }件商品</p>
							<p>
								<span class="clr-light">运费：</span>￥${order.postFee }
							</p>
							<p>
								<span class="clr-light">实付：</span><span class="clr-danger">￥<fmt:formatNumber
										value="${order.payPrice/100 }" pattern="0.00" />
								</span>
							</p>
						</div>
					</div>
					<div class="list-col ">
						<div class="list-right">
							<c:choose>
								<c:when test="${order.status == 10 || order.status == 30 }">
									<button class="btn btn-danger" onclick="toDeleteorder(this, '${order.orderNo}')">删除订单</button>
								</c:when>
								<c:when test="${order.status == 2}">
									<button class="btn btn-danger"
										onclick="openDeliverFram(this, '${order.orderNo}', '${order.deliveryWay }')">发货</button>
								</c:when>
								<c:when test="${order.status == 1}">
									<button class="btn btn-danger" onclick="toCloseorder(this,'${order.orderNo}')">关闭订单</button>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
		</div>
	</div>
	<%@ include file="../../../common/freeNav.jsp" %>
	<!-- 关闭订单 -->
	<div id="j-closeorder" style="display: none;">
		<form id="j-form" action="" method="post">
			<h3>温馨提示</h3>
			<p style="padding: 5px 0;">您确定要关闭订单?</p>
			<div class="list-col">
				<div class="list-inline box-flex">
					<textarea class="form-textarea" cols="50" rows="5"
						name="refuseReason" id="refuseReason" placeholder="请填写关闭原因"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div id="jdispatch" style="display: none;"></div>
	<script>
	navbar('order');
		$(document).ready(function(){
			//滚动加载 相关配置*/
			var loadObj = {
				elemt : ".list-row",
				url:"${ctx }/free/order/appendordinary${ext}",
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
		//关闭订单
		function toCloseorder(el, orderNo) {
			art.dialog({
				lock : true,
				width : '100%',
				title : '提示',
				content : document.getElementById("j-closeorder"),
				ok : function() {
					var formValid = $('#j-form').validate({
						rules:{
							refuseReason:{
				     			required:true,maxlength:256
							}
						},
						messages:{
							refuseReason:"请输入拒绝原因"
						}
					});
					if (!formValid.form()) {
						return false;
					}
					$.post("${ctx}/free/order/cancel${ext}", {"orderNo" : orderNo, "refuseReason" : $('#refuseReason').val()},
							function(jsonData){
						var data = JSON.parse(jsonData);
						if (data.success) {
							window.location.href="${ctx }/free/order/listordinary${ext}?status=${dto.status}";
						} else {
							artDialogAlert(data.info);
						}
					});
				},
				cancel : true
			});
		};
		//删除订单
		function toDeleteorder(el, orderNo) {
			art.dialog({
				lock : true,
				width : '100%',
				title : '提示',
				content : '确定要删除该订单?',
				ok : function() {
					$.post("${ctx}/free/order/del${ext}", {"orderNo" : orderNo}, function(jsonData){
						var data = JSON.parse(jsonData);
						if (data.success) {
							$(el).parent().parent().parent().remove();
						} else {
							alert(data.info);
						}
					});
				},
				cancel : true
			});
		};
		//发货
		function openDeliverFram(el, orderNo, deliveryWay) {
			$.post("${ctx}/free/order/openfram${ext}", {
				"orderNo" : orderNo, "deliveryWay" : deliveryWay
			}, function(htmlData) {
				$('#jdispatch').html(htmlData);
				openDialog(el);
			});
		}
		function getDeliverUrl() {
			return "${ctx}/free/order/deliver${ext}"
		}
		function afterDeliver(el) {
			window.location.href="${ctx }/free/order/listordinary${ext}?status=${dto.status}";
		}
	</script>
</body>
</html>