<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约订单</title>
	<%@ include file="../../common/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath }order/css/main.css">
</head>
<body>
<div id="box">
	<div class="list-tab">
		<ul>
			<li><a href="/wap/${sessionShopNo }/buy/order/bookOrder.htm"><span class="<c:if test='${empty status }'>active</c:if>">全部</span></a></li>
			<li><a href="/wap/${sessionShopNo }/buy/order/bookOrder.htm?status=40"><span class="<c:if test='${status==40 }'>active</c:if>">待确认</span></a></li>
			<li><a href="/wap/${sessionShopNo }/buy/order/bookOrder.htm?status=41"><span class="<c:if test='${status==41 }'>active</c:if>">已确认</span></a></li>
			<li><a href="/wap/${sessionShopNo }/buy/order/bookOrder.htm?status=42"><span class="<c:if test='${status==42 }'>active</c:if>">已拒绝</span></a></li>
			<li><a href="/wap/${sessionShopNo }/buy/order/bookOrder.htm?status=43"><span class="<c:if test='${status==43 }'>active</c:if>">已取消</span></a></li>
		</ul>
	</div>
	
	<div class="content" id="content">
	
		<!-- 商品类预约订单  -->
		
		<!-- 待确认状态   -->
		<c:if test="${empty pagination.datas }">
			<div class="no-content">
			<i class="iconfont icon-gouwuche clr-light"></i>
			<p>您还没有订单哦~</p>
		</div>
		</c:if>
		<c:if test="${not empty pagination.datas }">
		
			<c:forEach items="${pagination.datas}" var="bookOrder">
			<div class="list-row" data-list-row>
				<div class="list-col">
					<div class="list-inline box-flex">${bookOrder.shopName}</div>
					<div class="statusName">${bookOrder.statusName}</div>
				</div>
				<div class="list-col">
					<div class="list-inline box-flex clr-light">预约时间：${bookOrder.orderAppointmentDetails[0].attrValue.replace(',', ' ')}</div>
				</div>
				<c:choose>
					<c:when test="${0 == fn:length(bookOrder.orderItemList)}">
						<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'">
							<c:if test='${not empty bookOrder.picturePath&&!fn:contains(bookOrder.picturePath, "resource/m/") }'>
								<div class="list-inline"><img src="${picUrl}${bookOrder.picturePath}" alt="" width="70" height="70"></div>
							</c:if>
							<c:if test='${not empty bookOrder.picturePath&&fn:contains(bookOrder.picturePath, "resource/m/") }'>
								<div class="list-inline"><img src="${bookOrder.picturePath}" alt="" width="70" height="70"></div>
							</c:if>
	
							<div class="list-top box-flex">
								<ul>
									<li class="txt-rowspan2">${bookOrder.appointmentName}</li>
								</ul>
							</div>
							<div class="list-top shop-other">
								<span>￥<fmt:formatNumber value="${bookOrder.price/100}" pattern="0.00"/></span>
	
							</div>
						</a>
					</c:when>
					<c:otherwise>
						<c:forEach items="${bookOrder.orderItemList}" var="orderItem" begin="0" end="1">
							<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'">
								<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
								<div class="list-top box-flex">
									<ul>
										<li class="txt-rowspan2">${orderItem.goodName}</li>
									</ul>
								</div>
								<div class="list-top shop-other">
									<span>￥<fmt:formatNumber value="${orderItem.price/100}" pattern="0.00"/></span>
									<span class="clr-light fnt-12">X${orderItem.quantity}</span>
								</div>
							</a>
						</c:forEach>
						<c:forEach items="${bookOrder.orderItemList}" var="orderItem" begin="2" >
							<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'" data-id="${bookOrder.orderNo}" style="display:none;">
								<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
								<div class="list-top box-flex">
									<ul>
										<li class="txt-rowspan2">${orderItem.goodName}</li>
									</ul>
								</div>
								<div class="list-top shop-other">
									<span>￥<fmt:formatNumber value="${orderItem.price/100}" pattern="0.00"/></span>
									<span class="clr-light fnt-12">X${orderItem.quantity}</span>
								</div>
							</a>
						</c:forEach>
					</c:otherwise>
				</c:choose>
	
					<c:if test="${bookOrder.orderItemSize > 2}" >
				<div class="list-col" style="cursor: pointer;">
					<div class="list-center box-flex clr-light" onclick="showhideLayer('${bookOrder.orderNo}')">显示更多商品</div>
				</div>
					</c:if>
				<div class="list-col ">
					<div class="list-right">
						<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='detail.htm?orderNo=${bookOrder.orderNo}'">查看详情</a>
						<c:if test="${40 == bookOrder.status}">
						<button class="btn btn-default" data-cancel="1" onclick="bookOrderCancel(this,'${bookOrder.orderNo}')">取消订单</button>
						</c:if>
					</div>
				</div>
			</div>
			</c:forEach>
		</c:if>
		
	</div>
	

</div>


<!-- 确认收货 -->
<div id="j-take" style="display:none;">
	<form action="" id="jform-enterpwd">
		<div class="list-col">
			<div class="list-inline">
				支付金额：<span class="clr-warning">500.00</span>元
			</div>
		</div>
		<div class="list-col list-clearpadding">
			<div class="list-inline box-flex">
				<input type="password" class="form-border" id="password" name="password" placeholder="请输入支付密码">
			</div>
		</div>
	</form>
	<div class="list-col list-clearpadding">
		如果您还未设置支付密码，请先<a href="../../security/page/security-pay-set.html" style="color:blue">设置支付密码</a>
	</div>
</div>


<script type="text/javascript">

	$(document).ready(function(){
		//滚动加载 相关配置*/
		var loadObj = {
			elemt : ".list-row",
			url:"${ctx}/wap/${sessionShopNo}/buy/order/appendBookOrder${ext}",
			totalRow :'${pagination.totalRow}',
			start:'${pagination.end}',
			param:{
		    	status:'${status}'
		    },
			loadType:'html',
			idd : "#content"
		};
		rollLoad.init(loadObj);//触发滚动事件
	});

//取消订单

function bookOrderCancel(el,orderNo){
	var shopId = $(this).data('cancel');
	art.dialog({
		lock: true,
		width: '90%',
		title:'温馨提示',
		content: '确定取消订单？',
		ok: function () {
			console.log("商品ID"+shopId);
			$.post("/wap/${sessionShopNo}/buy/"+ orderNo + "/43.htm",function(data){
				if(!data.success){
					$.dialog.alert(data.info)
				}else{
					$(el).parents("div[data-list-row]").find("div.statusName").text('已取消');
					$(el).remove();
				}
			},'json')
		},
		cancelVal: '取消',
		cancel: true //为true等价于function(){}
	});
};

	//以下为前端专用，后台可自行删除
	sessionStorage.setItem('session_Reserve','buyer');
</script>
<%@ include file="../../common/nav.jsp"%>
<%@ include file="../../common/foot.jsp"%>
</body>
</html>
