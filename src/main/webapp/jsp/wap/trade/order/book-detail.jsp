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
<title>预约订单-订单状态详情</title>
	<%@ include file="../../common/top.jsp"%>
	<link rel="stylesheet" type="text/css" href="${resourcePath }operation/appoint/css/main.css">
</head>
<body>
<div id="box">

	<!--------------- 买家版订单状态信息  -------------->
	
	<!-- 待确认状态  -->
	<div class="reserve-tit" style="display:none;" id="js-Noconfirmed">
		<p class="clr-danger">${salesOrder.statusName}</p>
		<c:if test="${null != salesOrder.refuseReason}">
			<p class="clr-light">${salesOrder.refuseReason}</p>
		</c:if>
	</div>
	
	<!-- 已确认状态  -->
	<div class="reserve-tit"  style="display:none;" id="js-confirmed"><p class="clr-danger">${salesOrder.statusName}</p></div>
	
	<!-- 已拒绝状态  -->
	<div class="reserve-tit"  style="display:none;" id="js-refuse">
		<p class="clr-danger">${salesOrder.statusName}</p><p class="clr-light">${salesOrder.refuseReason}</p>
	</div>
	
	<!-- 已取消状态  -->
	<div class="reserve-tit"  style="display:none;" id="js-cancel"><p class="clr-danger">${salesOrder.statusName}</p></div>
	
	<!--------------- 卖家版订单状态信息  -------------->
	<!-- 待处理状态  -->
	<div class="reserve-tit" style="display:none;" id="js-Pending"><p class="clr-danger">待处理</p></div>
	<!-- 卖家信店铺信息   -->
	<div class="list-row" style="display:none;" id="js-sellerInfo">
		<div class="list-col">
			<div class="list-inline box-flex">${salesOrder.orderNo}</div>
			<div class="list-inline"><i class="iconfont icon-right"></i></div>
		</div>
		<a class="list-col" href="javascript:void(0);">
			<c:choose>
				<c:when test="${null == salesOrder.appointmentAddress}">
					<div class="list-inline txt-rowspan1">地址：--</div>
				</c:when>
				<c:otherwise>
					<div class="list-inline txt-rowspan1">地址：${salesOrder.appointmentAddress}</div>
				</c:otherwise>
			</c:choose>

		</a>
		<a class="list-col" href="tel:${salesOrder.sellerPhone}">
			<c:choose>
				<c:when test="${null == salesOrder.sellerPhone}">
					<div class="list-inline box-flex1" tel="0571-8990789">预约电话：--</div>
				</c:when>
				<c:otherwise>
					<div class="list-inline box-flex" tel="${salesOrder.sellerPhone}">预约电话：${salesOrder.sellerPhone}</div>
					<div class="list-inline  list-other"><i class="iconfont icon-unie629 other-main fnt-24" style="color:#669934;"></i></div>
				</c:otherwise>
			</c:choose>
		</a>
	</div>
	
	
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">预约清单</div>
		</div>
		<!-- 预约时间  -->
		<div class="list-col" style="display:none;" id="js-appointmentTime">
			<div class="list-inline box-flex clr-light">预约时间：${salesOrder.orderAppointmentDetails[0].attrValue}</div>
		</div>
		<c:choose>
			<c:when test="${0 == fn:length(salesOrder.orderItemList)}">
				<a class="list-col" href="javascript:toPost(buyerNormalTopayEms)">
					<div class="list-inline"><img src="${picUrl}${salesOrder.picturePath}" alt="" width="70" height="70"></div>
					<div class="list-top box-flex">
						<ul>
							<li class="txt-rowspan2">${salesOrder.appointmentName}</li>
						</ul>
					</div>

					<!-- 产品价格与数量  -->
					<div class="list-top shop-other">
						<span  id="js-price">￥<fmt:formatNumber value="${salesOrder.price/100}" pattern="0.00"/></span>
						<span class="clr-light fnt-12" id="js-Num">X1</span>
					</div>

				</a>
			</c:when>
			<c:otherwise>
				<c:forEach items="${salesOrder.orderItemList}" var="orderItem">
					<a class="list-col" href="javascript:toPost(buyerNormalTopayEms)">
						<div class="list-inline"><img src="${picUrl}${orderItem.skuPicturePath}" alt="" width="70" height="70"></div>
						<div class="list-top box-flex">
							<ul>
								<li class="txt-rowspan2">${orderItem.goodName}</li>
							</ul>
						</div>

						<!-- 产品价格与数量  -->
						<div class="list-top shop-other">
							<span  id="js-price">￥<fmt:formatNumber value="${orderItem.price/100}" pattern="0.00"/></span>
							<span class="clr-light fnt-12" id="js-Num">X${orderItem.quantity}</span>
						</div>

					</a>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		
		<!-- 总计  -->
		<c:if test="${not empty salesOrder.orderItemList }">
			<div class="list-col" style="display:none;" id="js-total">
				<div class="list-inline box-flex">共${salesOrder.quantity}件商品</div>
				<div class="list-inline"><span class="clr-light">合计：</span><span class="clr-danger">￥<fmt:formatNumber value=" ${salesOrder.totalPrice / 100}" pattern="0.00" /></span></div>
			</div>
		</c:if>
		
		<div class="list-col">
			<div class="list-inline clr-light">
				<ul>
					<c:choose>
						<c:when test="${null == salesOrder.receiverName}">
							<li>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：--</li>
						</c:when>
						<c:otherwise>
							<li>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：${salesOrder.receiverName}</li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${null == salesOrder.receiverPhone}">
							<li>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：--</li>
						</c:when>
						<c:otherwise>
							<li>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：${salesOrder.receiverPhone}</li>
						</c:otherwise>
					</c:choose>

					<%--<li>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：${salesOrder.pickUpAddress}</li>--%>

					<li>预约时间：${salesOrder.orderAppointmentDetails[0].attrValue.replace(',', ' ')}</li>
					<li>创建时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.createTime}"/></li>
					<c:choose>
						<c:when test="${null != salesOrder.buyerMessage}">
							<li>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：${salesOrder.buyerMessage}</li>
						</c:when>
						<c:otherwise>
							<li>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：无</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
		
	</div>
	
	<!--  买家版的订单操作方式    -->
	<c:if test="${'待处理' == salesOrder.statusName}">
	<div class="footfix" style="display:none;" id="js-cancelOrder">
		<div class="list-col ">
			<div class="list-right">
				<button class="btn btn-default" data-cancel="1" onclick="bookOrderCancel(this,'${salesOrder.orderNo}')">取消订单</button>
			</div>
		</div>
	</div>
	</c:if>
	
	<!-- 卖家版的操作方式  -->
<c:if test="${'待处理' == salesOrder.statusName}">
	<div class="footfix" style="display:none;" id="js-confirmrefused">
		<div class="list-col ">
			<div class="list-right">
				<button class="btn btn-default" data-confirm="1" >确认</button>
				<button class="btn btn-default" data-refuse="1" >拒绝</button>
			</div>
		</div>
	</div>
	</c:if>
	
	<!-- 拒绝订单以及拒绝原因填写  -->
	<div style="display:none;" id="j-refuse">
		<p class="clr-light" style="text-align:left;">请填写拒绝原因：</p>
		<textarea class="form-textarea" name="" id="memo" cols="35" rows="10"></textarea>
	</div>
	
</div>

<script>
//取消订单
function bookOrderCancel(el,orderNo){
	var shopId = $(this).data('cancel');
	art.dialog({
		lock: true,
		width: '90%',
		title:'温馨提示',
	    content: '确定取消订单？',
	    ok: function () {
			$("#js-cancelOrder").remove();
			$("#js-Noconfirmed p").text("已取消");
	        console.log("商品ID"+shopId);
			$.post("/wap/${sessionShopNo}/buy/"+ orderNo + "/43.htm");
	    },
	    cancelVal: '取消',
	    cancel: true //为true等价于function(){}
	});
};

//确认订单
$('[data-confirm]').on('click',function(){
	var shopId = $(this).data('confirm');
	art.dialog({
		lock: true,
		width: '90%',
		title:'温馨提示',
	    content: '您确定受理该订单？',
	    ok: function () {	
	        console.log("商品ID"+shopId);
			$.post("/u/order/"+ orderNo + "/41.htm");
	    },
	    cancelVal: '取消',
	    cancel: true //为true等价于function(){}
	});
});

//拒绝订单
$('[data-refuse]').on('click',function(){
	var shopId = $(this).data('refuse');
	art.dialog({
		lock: true,
		width: '90%',
		title:'提示',
	    content:document.getElementById('j-refuse'),
	    ok: function () {	
	        console.log("商品ID"+shopId)
			var refuseReason = $('#memo').val();
			$.post("/u/order/"+ orderNo + "/42.htm",
					{
						memo:refuseReason
					});
	    },
	    cancelVal: '取消',
	    cancel: true //为true等价于function(){}
	});
});




	//以下为前端专用，后台可自行删除 ,判断是买家还是卖家
	var session_Reserve = sessionStorage.getItem('session_Reserve');
	if(session_Reserve == 'buyer'){	//买家版状态信息
		$('#js-sellerInfo').show();	//卖家店铺信息
		$('#js-price').show();	//商品价格
	}else if(session_Reserve == 'seller'){	//卖家版状态信息
		$('#js-price').show();	//商品价格
	};
	
	//买家版订单状态 
	var session_ReserveState = sessionStorage.getItem('session_ReserveState');
	if(session_ReserveState == 'buyerState1'){	//商品类待确认状态
		$('#js-Noconfirmed').show();	//订单状态信息
		$('#js-cancelOrder').show();	//取消订单操作
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'buyerState2'){	//商品类已确认状态	
		$('#js-confirmed').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'buyerState3'){	//商品类已拒绝状态
		$('#js-refuse').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'buyerState4'){	//商品类已取消状态
		$('#js-cancel').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'buyerState5'){	//服务类待确认状态
		$('#js-Noconfirmed').show();	//订单状态信息
		$('#js-cancelOrder').show();	//取消订单操作
	}else if(session_ReserveState == 'buyerState6'){	//服务类已确认状态
		$('#js-confirmed').show();	//订单状态信息
	}else if(session_ReserveState == 'buyerState7'){	//服务类已拒绝状态
		$('#js-refuse').show();	//订单状态信息
	}else if(session_ReserveState == 'buyerState8'){	//服务类已取消状态
		$('#js-cancel').show();	//订单状态信息
	}else if(session_ReserveState == 'buyerState9'){	//服务类待确认状态（无价格）
		$('#js-Noconfirmed').show();	//订单状态信息
		$('#js-price').hide();	//商品价格
		$('#js-cancelOrder').show();	//取消订单操作
	};
	
	
	//卖家版订单状态 
	var session_ReserveState = sessionStorage.getItem('session_ReserveState');
	if(session_ReserveState == 'sellerState1'){	//商品类待处理状态
		$('#js-Pending').show();	//订单状态信息
		$('#js-confirmrefused').show();	//订单操作
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'sellerState2'){	//商品类已确认状态	
		$('#js-confirmed').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'sellerState3'){	//商品类已拒绝状态
		$('#js-refuse').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'sellerState4'){	//商品类已取消状态
		$('#js-cancel').show();	//订单状态信息
		$('#js-Num').show();	//商品数量
		$('#js-total').show();	//商品价格和数量总计
	}else if(session_ReserveState == 'sellerState5'){	//服务类待确认状态
		$('#js-Pending').show();	//订单状态信息
		$('#js-confirmrefused').show();	//订单操作
	}else if(session_ReserveState == 'sellerState6'){	//服务类已确认状态
		$('#js-confirmed').show();	//订单状态信息
	}else if(session_ReserveState == 'sellerState7'){	//服务类已拒绝状态
		$('#js-refuse').show();	//订单状态信息
	}else if(session_ReserveState == 'sellerState8'){	//服务类已取消状态
		$('#js-cancel').show();	//订单状态信息
	}else if(session_ReserveState == 'sellerState9'){	//服务类待确认状态（无价格）
		$('#js-Pending').show();	//订单状态信息
		$('#js-price').hide();	//商品价格
		$('#js-confirmrefused').show();	//订单操作
	};

</script>


</body>
</html>
