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
<title>订单列表</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }order/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<script type="text/javascript">
$(document).ready(function(){  
    //滚动加载 相关配置*/
	var loadObj = {
    			   elemt : ".list-row",
			       url:"${ctx}/wap/${sessionShopNo}/buy/order/appendList${ext}",
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
	function getAppendHtml(dataArray){
    	var htm_str = "";
    	for(x in dataArray){
    	}
       return htm_str;
    }
</script>
</head>
<body>
<div id="box">
	<div class="list-tab">
		<ul>
			<li ><a href="/wap/${sessionShopNo }/buy/order/list.htm"><span class="<c:if test='${empty status }'>active</c:if>">全部</span></a></li>
			<li ><a href="/wap/${sessionShopNo }/buy/order/list.htm?status=1"><span class="<c:if test='${status==1 }'>active</c:if>">待付款<c:if test="${not empty newOrderNumber && newOrderNumber != 0}">(${newOrderNumber})</c:if></span></a></li>
			<li ><a href="/wap/${sessionShopNo }/buy/order/list.htm?status=2"><span class="<c:if test='${status==2 }'>active</c:if>">待发货<c:if test="${not empty payOrderNumber && payOrderNumber != 0}">(${payOrderNumber})</c:if> </span></a></li>
			<li ><a href="/wap/${sessionShopNo }/buy/order/list.htm?status=3"><span id="waitForRecive" class="<c:if test='${status==3 }'>active</c:if>">待收货<c:if test="${not empty deliverOrderNumber && deliverOrderNumber !=0}">(${deliverOrderNumber})</c:if> </span></a></li>
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
			<div class="list-row" data-showhide >
				<div class="list-col">
					<div class="list-inline box-flex">${order.shopName }</div>
					<div class="list-inline clr-warning statusName">
						<c:choose>
							<c:when test="${order.status==1 && order.payStatus == 2 }">
								支付中
							</c:when>
							<c:otherwise>
								${order.statusName }
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:forEach items="${order.orderItemList }" var="orderItem" varStatus="status">
				<a class="list-col" href="/wap/${sessionShopNo}/buy/order/detail.htm?orderNo=${order.orderNo }" data-id="<c:if test='${status.index>1 }'>${order.orderNo }</c:if>" style="<c:if test='${status.index>1 }'>display:none;</c:if>">
					<div class="list-inline"><img src="${picUrl }${orderItem.skuPicturePath }" alt="" width="70" height="70"></div>
					<div class="list-top box-flex">
						<ul>
							<li class="txt-rowspan2">${orderItem.goodName }</li>
							<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertiesName }</li>
						</ul>
					</div>
					<div class="list-top shop-other">	
						<span>￥<fmt:formatNumber pattern="0.00" value="${orderItem.discountPrice/100 }"/></span>
						<span class="clr-light fnt-12">X${orderItem.quantity }</span>
					</div>
				</a>
				</c:forEach>
	
				<div class="list-col">
					<div class="list-inline shop-info">
						<p>共${order.quantity }件商品</p>
						<c:if test="${order.hasPostFee==1 }"><p><span class="clr-light">运费：</span>￥<fmt:formatNumber pattern="0.00" value="${order.postFee/100 }"/></p></c:if>
						<p><span class="clr-light">实付：</span><span class="clr-danger">￥<span class="totalPrice"><fmt:formatNumber pattern="0.00" value="${order.totalPrice/100 }"/></span></span></p>
					</div>
				</div>
				<c:if test="${order.quantity>2 }">
				<div class="list-col" style="cursor: pointer;">
					<div class="list-center box-flex clr-light" onclick="showhideLayer('${order.orderNo}')">显示更多商品</div>
				</div>
				</c:if>
				<div class="list-col ">
					<div class="list-right">
						<c:choose>
							<c:when test="${order.status==1 && order.payStatus != 2  }">
								<button class="btn btn-default" data-cancel="${order.orderNo }" >取消订单</button>
								<a class="btn btn-danger" href="javascript:;" onclick="orderToPay('${order.orderNo}')">立即支付</a>
							</c:when>
							<c:when test="${order.status==3 }">
								<button class="btn btn-danger" data-take="${order.orderNo }">确认收货</button>
							</c:when>
							<c:when test="${order.status==10 || order.status==30 }">
								<button class="btn btn-default" onclick="deleteOrder(this,'${order.orderNo}')">删除订单</button>
							</c:when>
						</c:choose>
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
				支付金额：<span class="clr-warning" id="totalPrice">500.00</span>元
			</div>
		</div>
		<div class="list-col list-clearpadding">
			<div class="list-inline box-flex">
				<input type="password" class="form-border" id="password" name="password" placeholder="请输入支付密码">
			</div>
		</div>
	</form>
	<div class="list-col list-clearpadding">
		如果您忘记支付密码，请点击<a href="/wap/${sessionShopNo }/buy/account/security/paymentCode${ext }?retUrl=/wap/${sessionShopNo }/buy/order/list${ext }" style="color:blue">设置支付密码</a>
	</div>
</div>
<form id="orderToPay" action="/wap/${sessionShopNo}/buy/order/orderToPay.htm" method="post">
	<input type="hidden" name="orderNo">
</form>

<script>
	function orderToPay(orderNo){
		$("#orderToPay").find("input[name=orderNo]").val(orderNo);
		$("#orderToPay").submit();
	}
	//取消订单
	$('[data-cancel]').on('click',function(){
		var orderNo = $(this).data('cancel');
		var el=this;
		art.dialog({
			lock: true,
			width: '90%',
			title:'温馨提示',
		    content: '确定取消订单？',
		    ok: function () {	
		        console.log("orderNo:"+orderNo)
		        $.post("/wap/${sessionShopNo}/buy/order/cancelOrder.htm",{'orderNo':orderNo},function(data){
		        	if(!data.success){
		        		$.dialog.alert(data.info)
		        	}else{
				        $(el).parents("div[data-showhide]").find("div.statusName").text('已取消');
				        $(el).parent().html('<button class="btn btn-default" onclick="deleteOrder(this,\''+orderNo+'\')" >删除订单</button>')
		        	}
		        },'json')
		    },
		    cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
		});
		
	});
	//删除订单
	function deleteOrder(el,orderNo){
		art.dialog({
			lock: true,
			width: '90%',
			title:'温馨提示',
		    content: '确定删除订单？',
		    ok: function () {	
		        console.log(orderNo)
		        $.post("/wap/${sessionShopNo}/buy/order/deleteOrder.htm",{'orderNo':orderNo},function(data){
		        	if(!data.success){
		        		$.dialog.alert(data.info);
		        	}else{
				    	$(el).parents("div[data-showhide]").remove();
		        	}
		        },'json')
		    },
		    cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
		});
		
	}
	//确认收货
	//data-take="1"
	$('[data-take]').on('click',function(){
		var orderNo = $(this).data('take');
		var el=this;
		$("#totalPrice").html($(this).parents("div[data-showhide]").find('span.totalPrice').html());
		$("#jform-enterpwd")[0].reset();//清空表单
		var validator = 	$("#jform-enterpwd").validate({
				         	rules:{
								password:{
									required:true
								}
							},
							messages:{
								password:{
									required:"支付密码不能为空"
								}
							}
				         });
		
		art.dialog({
			lock: true,
			width: '100%',
			title:'确认收货',
		    content:document.getElementById("j-take"),
		    ok: function () {
		    	if(validator.form()){
			    	$.post("/wap/${sessionShopNo}/buy/order/confirmOrder.htm",{'orderNo':orderNo,'paymentCode':$("#password").val()},function(data){
			    		if(!data.success){
			    			art.dialog.alert(data.info);
			    		}else{
			    			$(el).parents("div[data-showhide]").find("div.statusName").text('已收货');
			    			$(el).remove();
			    		}
			    	},'json');
			    	var waitForRecive = $("#waitForRecive").text().substring(4, 5);
			    	if (waitForRecive > 0) {
						$("#waitForRecive").text("待收货("+(waitForRecive-1)+")");
					}
		    	}else{
		    		return false;
		    	}
		    },
		    cancelVal: '关闭',
		    cancel: true
		});
		
	});
	
	
	
</script>
<%@ include file="../../common/nav.jsp"%>
<%@ include file="../../common/foot.jsp"%>
</body>
</html>

