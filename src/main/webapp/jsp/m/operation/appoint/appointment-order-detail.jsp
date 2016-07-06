<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<%@ include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<title>订单管理-详情</title>	
<!-- top -->
<script type="text/javascript" src="top.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("appointment-order");
});
</script>
</head>
<body>
<%@ include file="../../common/header.jsp"%>
<div class="container" id="j-content">
	<div class="row">
		<div class="col-md-2">
			<%@include file="../../common/left.jsp"%>
		</div>
		<div class="col-md-10">
			<c:set var="crumbs" value="appointSet"/>
			<%@include file="../../common/crumbs.jsp"%>

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
										<!-- <tr>
											<th width="10%">订单编号：</th>
											<td>2312312123121313</td>
										</tr> -->
										<tr>
											<th width="10%">提交时间：</th>
											<td><fmt:formatDate value="${salesOrder.createTime}" pattern="yyyy-MM-dd HH:ss:mm"/> </td>
										</tr>

										<tr>
											<th>预约状态：</th>
											<td>${salesOrder.statusName}
												<c:if test="${'待处理' == salesOrder.statusName}">
												<a href="javascript:;" class="btn btn-default" onclick="completeOrder(this,'${salesOrder.orderNo}')">确定</a>
												<a href="javascript:;" class="btn btn-default" onclick="closeOrder(this,'${salesOrder.orderNo}')">拒绝</a>
											</td>

										</c:if>
										</tr>
											<c:if test="${'已确认' == salesOrder.statusName}">
										<tr>
											<th width="10%">确定时间：</th>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:ss:mm" value="${salesOrder.confirmTime}"/></td>
										</tr>
											</c:if>
										<c:if test="${'已拒绝' == salesOrder.statusName}">
											<tr>
												<th>拒绝时间：</th>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:ss:mm" value="${salesOrder.refuseTime}"/> </td>
											</tr>
											<tr>
												<th>拒绝原因：</th>
												<td>${salesOrder.refuseReason}</td>
											</tr>
										</c:if>
										<c:if test="${'已取消' == salesOrder.statusName}">
											<tr>
												<th width="10%">取消时间：</th>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:ss:mm" value="${salesOrder.cancelTime}"/> </td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>

						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">联系人及相关信息</a></li>
								</ul>
							</div>
							<div class="content">
								<table class="table table-noborder table-thleft">
									<tbody>
										<tr>
											<th width="10%">联系人：</th>
											<td>${salesOrder.receiverName}</td>
										</tr>
										<tr>
											<th>联系电话：</th>
											<td>${salesOrder.receiverPhone}</td>
										</tr>

										<%--<tr>
											<th>联系地址：</th>
											<td>${salesOrder.pickUpAddress}</td>
										</tr>--%>
										<c:forEach items="${bookOrder.orderAppointmentDetails}" var="appointmentDetail">
										<tr>
											<th>${appointmentDetail.attrName}：</th>
											<c:if test="${null == appointmentDetail.attrValue}">
												<td>无</td>
											</c:if>
											<td>${appointmentDetail.attrValue}</td>
										</tr>
										</c:forEach>
										<c:choose>
											<c:when test="${fn:length(salesOrder.buyerMessage) <= 0}">
												<tr>
													<th>买家备注：</th>
													<td>无</td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<th>买家备注：</th>
													<td>${salesOrder.buyerMessage}</td>
												</tr>
											</c:otherwise>
										</c:choose>

									</tbody>
								</table>
							</div>
						</div>
						
					<c:if test="${not empty salesOrder.orderItemList }">
						<div class="box box-auto" request-type="1">
							<div class="title">
								<ul class="nav nav-tabs">

								</ul>
							</div>
							<div class="content">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>预约商品</th>
											<th width="20%">售价（元）</th>
											<th width="20%">预约数量</th>
											<th width="20%">所需金额（元）</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test="${0 == fn:length(salesOrder.orderItemList)}">
											<tr>
												<td>${salesOrder.appointmentName}</td>
												<td><fmt:formatNumber value="${salesOrder.price / 100}" pattern="0.00"/></td>
												<td>${salesOrder.quantity}</td>
												<td><fmt:formatNumber value="${salesOrder.totalPrice/100}" pattern="0.00"/> </td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${salesOrder.orderItemList}" var="orderItem">
												<tr>
													<td>${orderItem.goodName}</td>
													<td><fmt:formatNumber value="${orderItem.price / 100}" pattern="0.00"/></td>
													<td>${orderItem.quantity}</td>
													<td><fmt:formatNumber value="${orderItem.totalPrice/100}" pattern="0.00"/> </td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>

									</tbody>
								</table>
								<div class="text-right" style="margin-right:6px;">
									<b>金额：<fmt:formatNumber value="${salesOrder.totalPrice / 100}" pattern="0.00"/>元</b>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<!-- body end -->
	
	<!-- 关闭定单 -->
	<div id="jcancelOrder" style="display:none;">
		<h4 style="font-size:14px;line-height:28px;">请填写拒绝原因：</h4>
		<textarea name="" id="memo" class="form-control" rows="5" maxlength="20"></textarea>
		<p style="font-size:12px;text-align:right;">您可以输入<span class="color-danger" id="Jnum">20</b>个字符</p>
	</div>
	
	<!-- 完成定单 -->
	<div id="jcompleteOrder" style="display:none;">
		<h4>您确定要完成以下订单？</h4>
		<p>订单号：2312321321321</p>
	</div>
	
	<script type="text/javascript">
	//关闭定单
	function closeOrder(el,orderNo){
		$("#jcompleteOrder p").text("订单号：" + orderNo);
		art.dialog({
			title:"拒绝原因",
			lock:true,
			content:document.getElementById("jcancelOrder"),
			ok:function(){
				var refuseReason = $('#memo').val();
				$.post("/u/order/"+ orderNo + "/42.htm",
						{
							memo:refuseReason
						});
				$(el).parents("td").html("已拒绝");
			},
			cancel:true
		})
	}

	//交易完成
	function completeOrder(el,orderNo){
		$("#jcompleteOrder p").text("订单号：" + orderNo);
		art.dialog({
			title:"完成订单",
			lock:true,
			content:document.getElementById("jcompleteOrder"),
			ok:function(){
				$.post("/u/order/"+ orderNo + "/41.htm");
				$(el).parents("td").html("已确认");
			},
			cancel:true
		})
	}
	</script>
	<!-- footer -->
	<script type="text/javascript" src="footer.js"></script>
	<!-- footer end -->
	</body>
</html>

