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

<script>
	$(function(){
		$.metadata.setType("attr", "validate");
		tab("order");
	});
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
					<%-- 				<c:set var="crumbs" value="guanjia"/> --%>
<%-- 				<%@include file="../../common/crumbs.jsp"%> --%>
					<div class="row">
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">订单详情</a></li>
								</ul>
							</div>
							<div class="content">
								<table class="table table-noborder table-thleft">
									<tbody>
										<tr>
											<th width="10%">订单编号：</th>
											<td>${salesOrder.orderNo }</td>
											<c:if test="${salesOrder.sellerRemoteType == 2 }">
												<th width="10%">订单来源：</th>
                                            	<td>${salesOrder.sellerName }</td>
											</c:if>
										</tr>
										<tr>
											<th>下单时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.createTime }"/></td>
										</tr>
										<c:if test="${salesOrder.status==3 }">
										<tr>
											<th>付款时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.paymentTime }"/></td>
										</tr>
										<tr>
											<th>发货时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.deliveryTime }"/></td>
										</tr>
										</c:if>
										<c:if test="${salesOrder.status==5 }">
										<tr>
											<th>付款时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.paymentTime }"/></td>
										</tr>
										<tr>
											<th>发货时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.deliveryTime }"/></td>
										</tr>
										<tr>
											<th>完成时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.confirmTime }"/></td>
										</tr>
										</c:if>
										<tr>
											<th>支付方式：</th>
											<td><c:if test="${empty salesOrder.payChannelName }">在线支付</c:if>
											<c:if test="${not empty salesOrder.payChannelName }">${salesOrder.payChannelName }</c:if>
											</td>
										</tr>
										<tr>
											<th>订单状态：</th>
											<td><span id="statusName">
											<c:choose>
												<c:when test="${salesOrder.status==1 && salesOrder.payStatus == 2 }">
													支付中
												</c:when>
												<c:otherwise>
													${salesOrder.statusName }
												</c:otherwise>
											</c:choose>
											</span>
											<c:if test="${empty salesOrder.payStatus && salesOrder.status==1 }"><a href="javascript:;" class="btn btn-default" onclick="cancelOrder('${salesOrder.orderNo}')">取消订单</a></c:if>
											<c:if test="${salesOrder.status==2 && salesOrder.deliveryRemoteId == shopNo  }"><a href="javascript:;" class="btn btn-default" onclick="delivery('${salesOrder.deliveryWay }','${salesOrder.orderNo}')">发货</a></c:if>
											<c:if test="${salesOrder.status==30 }"><button class="btn btn-default" onclick="del('${salesOrder.orderNo}')">删除</button></c:if>
											</td>
										</tr>
										<c:if test="${(salesOrder.status==5 || salesOrder.status==3) && salesOrder.deliveryWay==1 }">
										<tr>
											<th>快递公司：</th>
											<td>${salesOrder.deliveryCompanyName }</td>
										</tr>
										<tr>
											<th>快递单号：</th>
											<td>${salesOrder.deliverySn }</td>
										</tr>
										</c:if>
										<!-- <tr>
											<th>送货人：</th>
											<td>XXXXXX</td>
										</tr>
										<tr>
											<th>联系方式：</th>
											<td>12345678912</td>
										</tr> -->
									</tbody>
								</table>
							</div>
						</div>

						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">物流及配送信息</a></li>
								</ul>
							</div>
							<c:if test="${salesOrder.deliveryWay==1 || salesOrder.deliveryWay==2 }">
							<div class="content">
								<table class="table table-noborder table-thleft">
									<tbody>
										<tr>
											<th width="10%">运送方式：</th>
											<td>${salesOrder.deliveryWayName }</td>
										</tr>
										<tr>
											<th width="10%">收货人：</th>
											<td>${salesOrder.receiverName }</td>
										</tr>
										<tr>
											<th>收货电话：</th>
											<td>${salesOrder.receiverPhone }</td>
										</tr>
										<tr>
											<th>收货地址：</th>
											<td>${salesOrder.receiverState}${salesOrder.receiverCity}${salesOrder.receiverDistrict}${salesOrder.receiverAddress }</td>
										</tr>
										<tr>
											<th>买家留言：</th>
											<td>${salesOrder.buyerMessage }</td>
										</tr>
									</tbody>
								</table>
							</div>
							</c:if>
							<c:if test="${salesOrder.deliveryWay==3 }">
							<div class="content">
								<table class="table table-noborder table-thleft">
									<tbody>
										<tr>
											<th width="10%">运送方式：</th>
											<td>${salesOrder.deliveryWayName }</td>
										</tr>
										<tr>
											<th>收货人：</th>
											<td>${salesOrder.receiverName }</td>
										</tr>
										<tr>
											<th>收货电话：</th>
											<td>${salesOrder.receiverPhone }</td>
										</tr>
										<tr>
											<th width="10%">自提点：</th>
											<td>${salesOrder.pickUpAddress }</td>
										</tr>
										<tr>
											<th>联系电话：</th>
											<td>${salesOrder.pickUpPhone }</td>
										</tr>
									</tbody>
								</table>
							</div>
							</c:if>
						</div>
						
						
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">商品详情</a></li>
								</ul>
							</div>
							<div class="content">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>商品名称</th>
											<th width="15%">单价</th>
											<th width="10%">数量</th>
											<th width="20%">订单状态</th>
											<th width="20%">商品总价</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${salesOrder.orderItemList }" var="orderItem" varStatus="status">
										<tr>
											<td>
												<dl class="m-products-list">
													<dt class="m-products-img"><a ><img src="${picUrl }${orderItem.skuPicturePath }" alt="" style="width:50px;height:50px; background:#c00;'"/></a></dt>
													<dd class="m-products-text"><a >${orderItem.goodName }</a></dd>
													<dd class="m-products-text">${orderItem.skuPropertiesName }</dd>
												</dl>
											</td>
											<td>
												<p class="color-danger">￥<fmt:formatNumber pattern="0.00" value="${(orderItem.discountPrice+orderItem.adjustPrice)/100 }"/></p>
												<p><del>￥<fmt:formatNumber pattern="0.00" value="${orderItem.price/100 }"/></del></p>
											</td>
											<td>${orderItem.quantity }</td>
											<c:if test="${status.first }">
											<td  rowspan="${salesOrder.orderItemSize }">
												<p>${salesOrder.statusName }</p>
											</td>
											<td  rowspan="${salesOrder.orderItemSize }">
												<p class="color-danger">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.price/100 }"/></p>
												<%-- <p>(原价：<fmt:formatNumber pattern="0.00" value="${(salesOrder.price+salesOrder.postFee)/100 }"/>)</p> --%>
												<%-- <p>(快递：<fmt:formatNumber pattern="0.00" value="${salesOrder.postFee/100 }"/>)</p> --%>
											</td>
											</c:if>
										</tr>
										</c:forEach>
										
									</tbody>
								</table>
								<div class="text-right" style="margin-right:6px;">
                                    <%-- <p>积分抵价：<fmt:formatNumber pattern="0.00" value="${salesOrder.integral/100 }"/> 元</p> --%>
									<c:if test="${salesOrder.deliveryWay==1 }">
										<p><b>快递：<fmt:formatNumber pattern="0.00" value="${salesOrder.postFee/100 }"/></b></p>
									</c:if>
                                    <c:if test="${not empty salesOrder.adjustPrice && salesOrder.adjustPrice != 0 }">
										<p>
											<b>卖家改价：<fmt:formatNumber value="${salesOrder.adjustPrice / 100 }" pattern="0.00" /> 元</b>
										</p>
									</c:if>
                                    <p><b>实付金额：<fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice/100 }"/>元</b></p>
                                    <p><b><c:if test="${null != payCommission && payCommission != 0 }">佣金支出：-<fmt:formatNumber pattern="0.00" value="${payCommission/100 }"/>元</c:if></b></p>
                                    <p><span style="padding-right: 30px;">
                                    	</span><b class="color-danger">合计金额：<fmt:formatNumber pattern="0.00" value="${(salesOrder.totalPrice - payCommission)/100 }"/>元</b>
                                    </p>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!-- body end -->
	
	<!-- 取消订单 -->
	<div id="jcancelOrder" style="display:none;">
		<h4>您确定要取消以下订单？</h4>
		<p>订单号：${salesOrder.orderNo }</p>
	</div>
	<div id="jdeleteOrder" style="display:none;">
		<h4>您确定要删除以下订单？</h4>
		<p>订单号：${salesOrder.orderNo }</p>
	</div>
	<div id="jdispatch1" style="display:none;">
		<form action="" class="form-horizontal">
			<div class="form-group">
				<label class="col-md-6 control-label">送货方式：快递配送</label>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">物流公司：</label>
				<div class="col-md-8">
					<select class="form-control" name="deliveryCompany">
						<option value="1">申通E物流</option>
				      	<option value="2">圆通速递</option>
				      	<option value="3">中通速递</option>
				      	<option value="4">汇通快递</option>
				      	<option value="5">韵达快递</option>
				      	<option value="6">天天快递</option>
				      	<option value="7">宅急送</option>
				      	<option value="8">顺丰速运</option>
				      	<option value="9">全峰快递</option>
				      	<option value="10">国通快递</option>
				      	<option value="11">其它</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">快递单号：</label>
				<div class="col-md-8">
					<input type="text" value="" class="form-control" name="deliverySn" validate="{required:true,maxlength:16, messages:{required:'快递单号不能为空',maxlength:'快递单号不能超过16位'}}"/>
				</div>
			</div>
		</form>
	</div>
	<div id="jdispatch2" style="display:none;">
		<form action="" class="form-horizontal">
			<div class="form-group">
				<label class="col-md-6 control-label">送货方式：店铺配送</label>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">送货人姓名：</label>
				<div class="col-md-8">
					<input type="text" value="" name="senderName" class="form-control" validate="{required:true,maxlength:16, messages:{required:'送货人姓名不能为空',maxlength:'送货人姓名不能超过16位'}}"/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">送货人电话：</label>
				<div class="col-md-8">
					<input type="text" value="" name="senderCell" class="form-control" validate="{required:true,mobile:true, messages:{required:'送货人电话不能为空',mobile:'不是正确的手机号码'}}"/>
				</div>
			</div>
			
		</form>
	</div>
	<div id="jdispatch3" style="display:none;">
		<form action="" class="form-horizontal" >
			<div class="form-group">
				<label class="col-md-6 control-label">送货方式：门店自取</label>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	function cancelOrder(orderNo){
		art.dialog({
			title:"取消订单",
			lock:true,
			content:document.getElementById("jcancelOrder"),
			ok:function(){
				$.post('/u/order/cancel.htm',{'orderNo':orderNo},function(data){
					location.reload();
				})
			},
			cancel:true
		})
	}
	function del(orderNo){
		art.dialog({
			title:"删除订单",
			lock:true,
			content:document.getElementById("jdeleteOrder"),
			ok:function(){
				$.post('/u/order/delete.htm',{'orderNo':orderNo},function(data){
					location.href='/u/order/list.htm';
				})
			},
			cancel:true
		})
	}
	function delivery(deliveryWay,orderNo){
		console.log(deliveryWay+','+orderNo)
		art.dialog({
			title:"发货",
			lock:true,
			width:"450px",
			content:document.getElementById("jdispatch"+deliveryWay),
			ok:function(){
				var form=$("#jdispatch"+deliveryWay).find('form');
				var validate=form.validate().form();
				if(validate){
					$.post('/u/order/delivery.htm',{'orderNo':orderNo,'senderName':form.find('input[name=senderName]').val(),'senderCell':form.find('input[name=senderCell]').val(),'deliveryCompany':form.find('select[name=deliveryCompany]').val(),'deliverySn':form.find('input[name=deliverySn]').val()},function(data){
						location.reload();
					})
				}
				return validate;
			},
			cancel:true
		})
	}
	</script>
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	</body>
</html>
