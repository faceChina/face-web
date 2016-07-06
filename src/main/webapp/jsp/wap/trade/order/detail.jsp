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
<title>订单详情</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }order/css/main.css">
</head>
<body>
<div id="box">
	<div class="list-address">
		<ul>
			<li>
				<span class="user">收货人:${salesOrder.receiverName }</span>
				<span class="mobile">${salesOrder.receiverPhone }</span>
			</li>
			<li class="clr-light address">${salesOrder.receiverState}${salesOrder.receiverDistrict}${salesOrder.receiverCity}${salesOrder.receiverAddress } </li>
		</ul>
	</div>
	
	<!-- 卖家版 订单详情 -->
	<div class="list-row list-row-clearborder" style="display:none;" id="js-buyersNote">
		<div class="list-col">买家备注</div>
		<div class="list-col list-clearpadding">金陵科技学院学士学位论文摘要 大学生网上订餐系统</div>
	</div>
	
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">${salesOrder.shopName }</div>
			<div class="list-inline"><i class="iconfont icon-right"></i></div>
		</div>
		<c:forEach items="${salesOrder.orderItemList }" var="orderItem">
		<a class="list-col" href="javascript:;" onclick="togood('${salesOrder.shopNo }','${orderItem.goodId }');" >
			<div class="list-inline"><img src="${picUrl }${orderItem.skuPicturePath }" alt="" width="70" height="70"></div>
			<div class="list-top box-flex">
				<ul>
					<li class="txt-rowspan2">${orderItem.goodName }</li>
					<li class="clr-light txt-rowspan1 fnt-12">${orderItem.skuPropertiesName }</li>
				</ul>
			</div>
			<c:choose>
				<c:when test="${salesOrder.orderCategory==1}">
					<div class="list-top shop-other">	
						<span>￥<fmt:formatNumber pattern="0.00" value="${orderItem.discountPrice/100 }"/></span>
						<span class="clr-light fnt-12">X${orderItem.quantity }</span>
					</div>
				</c:when>
				<c:when test="${salesOrder.orderCategory==2}">
					<div class="list-inline shop-other">
						<div class="button">
							<c:if test="${orderItem.authorizationCode.status==0 }">
							<button type="button" class="btn btn-danger" onclick="toActive(this,'${orderItem.authorizationCode.code}')">激活</button>
							</c:if>
							<c:if test="${orderItem.authorizationCode.status==1 }">
							<button type="button" class="btn btn-danger" disabled='true'>已激活</button>
							</c:if>
						</div>
					</div>
				</c:when>
			</c:choose>
		</a>
		<c:if test="${salesOrder.orderCategory==2}">
		<div class="list-col">
			<b class="list-inline">授权码：${orderItem.authorizationCode.code}</b>
		</div>
		</c:if>
		</c:forEach>
		<c:if test="${salesOrder.orderCategory!=2 }">
		<div class="list-col">
			<div class="list-inline box-flex">
				<ul>
					<!-- 卖家版详情是有运费信息显示的 -->
					<li style="display:none;" id="js-freight">运费：<span class="flot-right">￥5.00</span></li>
					<li style="display:none;" id="js-payment">实付款：<span class="flot-right clr-danger">￥600000</span></li>
				</ul>
			</div>
		</div>
		<div class="list-col" style="padding:0;">
			<div class="list-inline box-flex">
				<ul data-figure="">
					<li>商品总价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.price / 100}"/> </span></li>
					<li>运费：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.postFee / 100}"/></span></li>
					<c:choose>
						<c:when test="${lowPrice == 'coupon' }">
							<li>优惠券抵价<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.couponPrice / 100}"/></span></li>
						</c:when>
						<c:when test="${lowPrice == 'integral' }">
							<li>积分抵价<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.integral / 100}"/></span></li>
						</c:when>
					</c:choose>
					<li>卖家改价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.adjustPrice / 100}"/></span></li>
					<li>实付款：<span class="flot-right clr-danger">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice / 100}"/></span></li>
				</ul>
			</div>
		</div>
		<div class="list-col">
			<div class="list-inline">
				<ul class="block-dbtext">
					<!-- 配货方式的选择  -->
					<c:choose>
						<c:when test="${salesOrder.deliveryWay==1 }">
							<li style="" id="js-way2">
							<p>配送方式：</p>
							<p>快递配送</p>
							</li>
						</c:when>
						<c:when test="${salesOrder.deliveryWay==2 }">
							<li style="" id="js-way1">
							<p>配送方式：</p>
							<p>店铺配送</p>
							</li>
							<li style="" id="js-delivery">
								<p>配送范围：</p>
								<p>${salesOrder.deliveryRange }</p>
							</li>
						</c:when>
						<c:when test="${salesOrder.deliveryWay==3 }">
							<li style="" id="js-way3">
							<p>配送方式：</p>
							<p>上门自取</p>
							</li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
		<div class="list-col">
			<div class="list-inline clr-light">
				<ul>
					<li>订单编号：${salesOrder.orderNo }</li>
					<li>下单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.createTime }"/></li>
					<li>订单状态：<span class="statusName">
						<c:choose>
							<c:when test="${salesOrder.status==1 && salesOrder.payStatus == 2 }">
								支付中
							</c:when>
							<c:otherwise>
								${salesOrder.statusName }
							</c:otherwise>
						</c:choose>
					</span></li>
					<c:if test="${salesOrder.status == 1 || salesOrder.status == 2}">
						<!-- 代付款 -->
						<li>付款方式：${salesOrder.payWayName }</li>
					</c:if>
					<c:if test="${salesOrder.status == 3 ||salesOrder.status == 4 || salesOrder.status == 5  }">
						<!-- 待收货 已收货 交易成功 -->
						<c:choose>
							<c:when test="${salesOrder.deliveryWay==1}">
								<li>物流公司：${salesOrder.deliveryCompanyName }</li>
								<li>快递单号：${salesOrder.deliverySn }</li>
								<li>付款方式：${salesOrder.payWayName }</li>
							</c:when>
							<c:when test="${salesOrder.deliveryWay==2 }">
								<li>配送人员：${salesOrder.senderName }</li>
								<li>联系电话：${salesOrder.senderCell }</li>
								<li>付款方式：${salesOrder.payWayName}</li>
							</c:when>
							<c:when test="${salesOrder.deliveryWay==3 }">
								<li>自提地址：${salesOrder.pickUpAddress }</li>
								<li>联系电话：${salesOrder.pickUpPhone }</li>
								<li>付款方式：${salesOrder.payWayName }</li>
							</c:when>
						</c:choose>
					</c:if>
					<li style="display:none;" id="js-closecause">关闭原因：操作超时</li>
				</ul>
			</div>
		</div>
		</c:if>
		<c:if test="${salesOrder.orderCategory==2 }">
		<div class="list-col">
			<div class="list-inline box-flex">
				<ul>
					<!-- 卖家版详情是有运费信息显示的 -->
					<li style="display:none;" id="js-payment">实付款：<span class="flot-right clr-danger">￥600000</span></li>
				</ul>
			</div>
		</div>
		<div class="list-col" style="padding:0;">
			<div class="list-inline box-flex">
				<ul data-figure="">
					<li>商品总价：<span class="flot-right">￥<fmt:formatNumber pattern="0.00" value="${(salesOrder.price+salesOrder.postFee) / 100}"/> </span></li>
					<li>实付款：<span class="flot-right clr-danger">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice / 100}"/></span></li>
				</ul>
			</div>
		</div>
		
		<div class="list-col">
			<div class="list-inline clr-light">
				<ul>
					<li>订单编号：${salesOrder.orderNo }</li>
					<li>下单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.createTime }"/></li>
					<li>订单状态：<span class="statusName">
						<c:choose>
							<c:when test="${salesOrder.status==1 && salesOrder.payStatus == 2 }">
								支付中
							</c:when>
							<c:otherwise>
								${salesOrder.statusName }
							</c:otherwise>
						</c:choose>
					</span></li>
					<c:if test="${salesOrder.status == 1 || salesOrder.status == 2}">
						<!-- 代付款 -->
						<li>付款方式：${salesOrder.payWayName }</li>
					</c:if>
					<c:if test="${salesOrder.status == 3 ||salesOrder.status == 4 || salesOrder.status == 5  }">
						<!-- 待收货 已收货 交易成功 -->
						<c:choose>
							<c:when test="${salesOrder.deliveryWay==1}">
								<li>付款方式：${salesOrder.payWayName }</li>
							</c:when>
							<c:when test="${salesOrder.deliveryWay==2 }">
								<li>付款方式：${salesOrder.payWayName}</li>
							</c:when>
							<c:when test="${salesOrder.deliveryWay==3 }">
								<li>付款方式：${salesOrder.payWayName }</li>
							</c:when>
						</c:choose>
					</c:if>
					<li style="display:none;" id="js-closecause">关闭原因：操作超时</li>
				</ul>
			</div>
		</div>
		</c:if>
	</div>
	<c:if test="${salesOrder.status!=5 && salesOrder.status!=2}">
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">
				<c:if test="${salesOrder.status==1||salesOrder.status==3 }">共计：<span class="clr-warning"><fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice/100 }"/></span>元</c:if>
			</div>
			<div class="list-inline">
				<c:choose>
					<c:when test="${salesOrder.status==1 && salesOrder.payStatus != 2}">
						<button class="btn btn-default" data-cancel="${salesOrder.orderNo }" >取消订单</button>
						<a class="btn btn-danger" href="javascript:orderToPay('${salesOrder.orderNo}')">立即支付</a>
					</c:when>
					<c:when test="${salesOrder.status==3 }">
						<button class="btn btn-danger" data-take="${salesOrder.orderNo }">确认收货</button>
					</c:when>
					<c:when test="${salesOrder.status==10 || salesOrder.status==30 }">
						<button class="btn btn-default" onclick="deleteOrder(this,'${salesOrder.orderNo}')">删除订单</button>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
	</c:if>
	<%@ include file="../../common/nav.jsp"%>
	<%@ include file="../../common/foot.jsp"%>
</div>
<div id="j-take" style="display:none;">
	<form action="" id="jform-enterpwd">
		<div class="list-col list-clearpadding">
			<div class="list-inline box-flex">
				<input type="password" class="form-border" id="password" name="password" placeholder="请输入支付密码">
			</div>
		</div>
	</form>
	<div class="list-col list-clearpadding">
		如果您还未设置支付密码，请先<a href="/wap/${sessionShopNo }/buy/account/security/paymentCode${ext }?retUrl=/wap/${sessionShopNo }/buy/order/detail${ext }?orderNo=${salesOrder.orderNo }" style="color:blue">设置支付密码</a>
	</div>
</div>
<form id="orderToPay" action="/wap/${sessionShopNo}/buy/order/orderToPay.htm" method="post">
	<input type="hidden" name="orderNo">
</form>
<div id="j-active" style="display:none;" style="text-align:center;">
	<h4 class="fnt-16" style="padding-bottom:10px;">激活成功！</h4>
	<p>您可以通过账号、密码登录浙江脸谱官方后台：http://www.boss-sir.com就可以管理官网喽！</p>
</div>
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
			        $('body').find("span.statusName").text('已取消');
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
			    	location.href='/wap/${sessionShopNo}/buy/order/list.htm';
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
		    			$('body').find("span.statusName").text('已收货');
		    			$(el).parent().parent().parent().remove();
		    		}
		    	},'json')
	    	}else{
	    		return false;
	    	}
	    },
	    cancelVal: '关闭',
	    cancel: true
	});
	
});



	//删除订单
	$('[data-del]').on('click',function(){
		var shopId = $(this).data('del');//获取ID
		art.dialog({
			lock: true,
			width: '90%',
			title:'温馨提示',
		    content: '确定删除订单？',
		    ok: function () {	
		        console.log("商品ID"+shopId)
		    },
		    cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
		});
		
	});
	
	//状态传值，前端专用，后端自行删除
	
	//买家和卖家的区别的显示
	var session_order=sessionStorage.getItem('session_order');
	if(session_order=='buyer'){
		
	}else if(session_order=='seller'){
		$('#js-buyersNote').show();
		$('#js-closecause').show();
	}
	
	//买家版送货方式的选择
	var session_buyerway=sessionStorage.getItem('session_buyerway');
	if(session_buyerway=='buyerway1'){
		$('#js-way1').show();
		$('#js-delivery').show();
		$('#js-payment').show();
	}else if(session_buyerway=='buyerway2'){
		$('#js-way2').show();
		$('#js-freight').show();
		$('#js-payment').show();
	}else if(session_buyerway=='buyerway3'){
		$('#js-way3').show();
		$('#js-selfTwilling').show();
		$('#js-telephone').show();
		$('#js-payment').show();
	}
	
	//卖家版送货方式的选择
	var session_sellerway=sessionStorage.getItem('session_sellerway');
	if(session_sellerway=='sellerway1'){
		$('#js-way1').show();
		$('#js-payment').show();
	}else if(session_sellerway=='sellerway2'){
		$('#js-way2').show();
		$('#js-freight').show();
		$('#js-payment').show();
	}else if(session_sellerway=='sellerway3'){
		$('#js-way3').show();
		$('#js-selfTwilling').show();
		$('#js-telephone').show();
	}
	function toActive(el,code){
		art.dialog({
			lock: true,
			width: '100%',
			title:'提示',
		    content:'您确定为本账号激活店铺？',
		    ok: function (){
		    	$.post('/wap/${sessionShopNo}/buy/shop/activate.htm',{'code':code},function(data){
		    		if(data.success){
						if('0'==data.info){
							art.dialog.alert("激活成功!<br> 您可以通过账号、密码登录浙江脸谱官方后台：http://"+location.host+"，就可以管理店铺喽!")
					    	$(el).attr('disabled',true).html('已激活');
						}else if('1'==data.info){
							$(el).text("已激活").attr("disabled",true);
							$.dialog.alert('授权码已经使用过了！');
						}else if('-1'==data.info){
							$(el).text("已失效").attr("disabled",true);
							$.dialog.alert('授权码已失效！');
						}
		    		}
		    	},'json') 
				return true;
		    },
		    cancel: true
		});
		event.stopPropagation();
	};
	function togood(shopNo,goodId){
		/*
		$.post('/wap/${sessionShopNo}/any/validate.htm',{'goodId':goodId},function(data){
			if(data.success){
				location.href='/wap/'+shopNo+'/any/item/'+goodId+'.htm';
			}else{
				$.dialog.alert('商品已下架!')
			}
		},'json')
		*/
		location.href='/wap/'+shopNo+'/any/item/'+goodId+'.htm';
	}

</script>
</body>
</html>

