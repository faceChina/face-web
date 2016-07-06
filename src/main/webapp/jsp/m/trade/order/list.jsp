<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理</title>
<script type="text/javascript">
$(function(){
	$.metadata.setType("attr", "validate");
	$('[name=timeStates]').val('${salesOrderVo.timeStates}');
	$('[name=payChannel]').val('${salesOrderVo.payChannel}')
	$('[name=deliveryWay]').val('${salesOrderVo.deliveryWay}')
	$('[name=orderCategory]').val('${salesOrderVo.orderCategory}')
	tab("order");
});

/*删除*/
function delOrder(el,orderNo) {
	art.dialog.confirm('确认删除？', function() {
		$.post('/u/order/delete.htm',{'orderNo':orderNo},function(data){
			location.reload();
		})
	}, function() {
		return true;
	});
}
function changeStatus(status){
	$("#status").val(status);
	$('#formPage').submit();
}
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
						<c:set var="crumbs" value="ordershop"/>
						<%@include file="../../common/crumbs.jsp"%>
				<div class="row">
					<ul class="table-nav">
						<li>今日订单数：<span>${todayCompile }</span></li>
						<li>待付款：<span>${wait }</span></li>
						<li>待发货：<span>${pay }</span></li>
					</ul>
				</div>
						<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="javascript:void(-1)">店铺订单</a></li>
								</ul>
							</div>
							<div class="content">
								<form class="form-horizontal" method="get" id="formPage">
									<fieldset>
										<div class="form-group">
											<div class="col-md-2">
												<select class="form-control" name="timeStates" onchange="form.submit()">
													<option value="0">时间查询</option>
													<option value="1">今日</option>
													<option value="2">昨日</option>
													<option value="7">最近一周</option>
													<option value="30">最近一月</option>
												</select>
											</div>
											<label for="" class="col-md-2 control-label" style="width:70px;">订单号</label>
											<div class="col-md-2">
												<input  class="form-control"  type="text" name="orderNo" value="${salesOrderVo.orderNo }"/>
											</div>
											<label for="" class="col-md-2 control-label" style="width:70px;">买家账号</label>
											<div class="col-md-2">
												<input  class="form-control"  type="text" name="buyerAccount" value="${salesOrderVo.buyerAccount }"/>
											</div>
											<label for="" class="col-md-1 control-label" style="width:90px;">分店主姓名</label>
		                                    <div class="col-md-2">
		                                        <input  class="form-control" id="sellerNameStr" value="${salesOrderVo.sellerName }"  type="text" onblur="sellerNameEncode()" />
		                                        <input  class="form-control" name="sellerName" id="sellerName" type="hidden" />
		                                    </div>
											
										</div>
										<div class="form-group">
											<label for="" class="col-md-2 control-label" style="width:84px;">支付方式：</label>
											<div class="col-md-2">
												<select class="form-control" name="payChannel" onchange="form.submit()">
													<option value="0">全部</option>
													<option value="2">余额支付</option>
													<option value="3">微信支付</option>
													<option value="5">支付宝支付</option>
												</select>
											</div>
											<label for="" class="col-md-2 control-label" style="width:84px;">配送方式：</label>
											<div class="col-md-2">
												<select class="form-control" name="deliveryWay" onchange="form.submit()">
													<option value="0">全部</option>
													<option value="1">快递配送</option>
													<option value="2">店铺配送</option>
													<option value="3">门店自取</option>
												</select>
											</div>
											<label for="" class="col-md-2 control-label" style="width:84px;">订单类型：</label>
		                                    <div class="col-md-2">
		                                        <select name="orderCategory" class="form-control" onchange="form.submit()">
		                                            <option value="0">全部</option>
		                                            <option value="1">自营</option>
		                                            <option value="4">分店</option>
		                                        </select>
		                                    </div>
											<div class="col-md-2">
												<button type="submit" class="btn btn-default">查询</button>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<input type="hidden" id="status" name="status" value="${salesOrderVo.status }"/>
												<button type="button" onclick="changeStatus('0')" class="btn btn-default <c:if test='${empty salesOrderVo.status }'>btn-danger</c:if>">所有订单</button>
											
												<button type="button" onclick="changeStatus('1')" class="btn btn-default <c:if test='${salesOrderVo.status==1 }'>btn-danger</c:if>">待付款</button>
											
												<button type="button" onclick="changeStatus('2')" class="btn btn-default <c:if test='${salesOrderVo.status==2 }'>btn-danger</c:if>">已付款</button>
											
												<button type="button" onclick="changeStatus('3')" class="btn btn-default <c:if test='${salesOrderVo.status==3 }'>btn-danger</c:if>">已发货</button>
												
												<button type="button" onclick="changeStatus('4')" class="btn btn-default <c:if test='${salesOrderVo.status==4 }'>btn-danger</c:if>">已收货</button>
											
												<button type="button" onclick="changeStatus('5')" class="btn btn-default <c:if test='${salesOrderVo.status==5 }'>btn-danger</c:if>">交易成功</button>
											
												<button type="button" onclick="changeStatus('30')" class="btn btn-default <c:if test='${salesOrderVo.status==30 }'>btn-danger</c:if>">交易关闭</button>
											</div>
										</div>
									</fieldset>
								<table class="table table-bordered">
										<thead>
											<tr>
												<th>商品信息</th>
												<th width="10%">单价</th>
												<th width="8%">数量</th>
												<th width="10%">买家</th>
												<th width="10%">付款方式/配送方式</th>
												<th width="15%">订单状态</th>
												<th width="15%">实付金额</th>
											</tr>
										</thead>
										<tbody class="font-small" style="margin-top:10px;">
										<c:forEach items="${pagination.datas }" var="salesOrder">
											<tr class="bg-gray">
												<td colspan="7">
													<div class="m-horizontal">
														<div class="m-horizontal-left font-middle">
															订单号：${salesOrder.orderNo } | 下单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${salesOrder.createTime }"/>
														</div>
														<div class="m-horizontal-right">
															<a href="javascript:;" class="btn btn-default btn-xs" onclick="remarks(this,'${salesOrder.orderNo}')" onmouseover="remarksOver(this)" onmouseout="remarksOut(this)">备注<i class='glyphicon glyphicon-heart' style='margin-left:6px;<c:if test="${not empty salesOrder.sellerMemo}">color:#c00</c:if>' onmouseover='remarksOver(this)'></i></a>
															<div class="remarkInfo">${salesOrder.sellerMemo}</div>
														</div>
													</div>
												</td>
											</tr>
											<c:forEach items="${salesOrder.orderItemList }" var="orderItem" varStatus="status">
												<tr>
													<td>
														<dl class="m-products-list">
															<dt class="m-products-img">
															     <a><img src="${picUrl }${orderItem.skuPicturePath}" alt="" style="width:50px;height:50px; background:#c00;'"/></a>
															     <c:if test="${orderItem.popularize }"><span>推广</span></c:if>
															     <c:if test="${salesOrder.isSubbranchOrder }"><span>分店</span></c:if>
													        </dt>
															<dd class="m-products-text"><a >${orderItem.goodName }</a></dd>
															<dd class="m-products-text">${orderItem.skuPropertiesName }</dd>
														</dl>
													</td>
													<td>
														<p  class="color-danger">￥<fmt:formatNumber pattern="0.00" value="${orderItem.discountPrice/100 }"/></p>
														<p><del>￥<fmt:formatNumber pattern="0.00" value="${orderItem.price/100 }"/></del></p>
													</td>
													<td>${orderItem.quantity }</td>
													<c:if test="${status.first }">
													<td rowspan="${salesOrder.orderItemSize }">
														<p>${salesOrder.buyerAccount } </p>
														<p>在线联系</p>
													</td>
													<td rowspan="${salesOrder.orderItemSize }"><p>${salesOrder.payChannelName }</p><p>${salesOrder.deliveryWayName }</p></td>
													<td  rowspan="${salesOrder.orderItemSize }">
														<c:choose><c:when test="${not empty salesOrder.payStatus && salesOrder.status == 1}"><p>支付中</p></c:when><c:otherwise><p>${salesOrder.statusName }</p></c:otherwise> </c:choose>
														<p  class="color-danger"><a href="/u/order/detail.htm?orderNo=${salesOrder.orderNo }">查看详情</a></p>
														<c:if test="${empty salesOrder.payStatus && salesOrder.status==1 }"><p><a href="javascript:;" class="btn btn-default" onclick="cancelOrder(this,'${salesOrder.orderNo}')">取消订单</a></p></c:if>
														<c:if test="${salesOrder.status==2 && salesOrder.deliveryRemoteId == shopNo }"><p><a href="javascript:;" class="btn btn-default" onclick="dispatch('${salesOrder.deliveryWay }','${salesOrder.orderNo}')">发货</a></p></c:if>
														<c:if test="${salesOrder.status==30 }"><p><button type="button" class="btn btn-default" onclick="delOrder(this,'${salesOrder.orderNo}')">删除</button></p></c:if>
													</td>
													<td  rowspan="${salesOrder.orderItemSize }">
														<p  class="color-danger">￥<fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice/100 }"/></p>
														<%-- <p>(原价：<fmt:formatNumber pattern="0.00" value="${(salesOrder.price+salesOrder.postFee)/100 }"/>)</p> --%>
														<p>(含快递：<fmt:formatNumber pattern="0.00" value="${salesOrder.postFee/100 }"/>)</p>
														<c:if test="${(empty salesOrder.payStatus) && salesOrder.status == 1 }"><p><a href="javascript:;" class="btn btn-default" onclick="updateOrder(this,'${salesOrder.orderNo}')">修改价格</a></p></c:if>
													</td>
													</c:if>
												</tr>
											</c:forEach>
										</c:forEach>
										</tbody>
									</table>
									<%@include file="../../common/page.jsp"%>
								</form>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>

	<!-- 备注 -->
	<div id="jremark" style="display:none;">
		<form action="" class="" id="jremarkForm">
			<div class="form-group">
				<textarea class="form-control" id="msg" name="msg" style="width:500px;height:200px;"></textarea>
			</div>
		</form>
	</div>
	
	<!-- 取消订单 -->
	<div id="jcancelOrder" style="display:none;">
		<h4>您确定要取消以下订单？</h4>
		<p>订单号：<span id="cancelOrder"></span></p>
	</div>
	
	<!-- 修改订单 -->
	<div id="jupdateOrder" style="display:none;">
		
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

	<%@include file="../../common/footer.jsp"%>
	</body>
	
<script type="text/javascript">

$(function(){
	tab("order");
});
function checkNum(price,val){
	if(!isNaN(val)){
		var reg=/(.[0-9]{1,2})?$/;
		if(reg.test(val)){
			calculate();
		}
	}
}

function sellerNameEncode() {
	var str = $("#sellerNameStr").val();
	var sellerName = encodeURIComponent(str);
	$("#sellerName").val(sellerName);
}
function calculate(){
	var price=0;
	$("#orderItems tr").each(function(){
		price+=Number($(this).find('input').val());
	})
	var val=((Number($("#yuanjia").text())*100+price*100)/100).toFixed(2);
	if(val<0.1){
		$.dialog.alert('价格不能低于0.1')
		return ;
	}
	$("#shifu").text(val)
}
//备注
function remarks(str,orderNo){
	$('#msg').val($(str).next().text());
	art.dialog({
		title:"添加备注",
		lock:true,
		content:document.getElementById("jremark"),
		ok:function(){
			var flag = $("#msg").val();
			if(flag.length>256){
				$.dialog.tips('备注信息不能超过256个字!')
				return false;
			}
			$.post('/u/order/remark.htm',{'orderNo':orderNo,'sellerMemo':flag},function(data){
				$(str).find("i").css('color','#c00');
				$(str).next().text(flag);
				console.log($(str).next().text())
				if(flag == ""){
					$(str).find("i").css('color','')
					$(str).next().text('');
				}
			})
		},
		cancel:true
	})
}

function remarksOver(str){
	if($(str).next().text() != ""){
		$(str).next().show()
	}
}
function remarksOut(str){
		$(str).next().hide()
}


//取消订单
function cancelOrder(el,orderNo){
	$("#cancelOrder").text(orderNo);
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
function checkNum(price,val){
	if(!isNaN(val)){
		var reg=/(.[0-9]{1,2})?$/;
		if(reg.test(val)){
			calculate();
		}
	}
}
function calculate(){
	var price=0;
	$("#orderItems tr").each(function(){
		price+=Number($(this).find('input').val());
	})
	var val=((Number($("#yuanjia").text())*100+price*100)/100).toFixed(2);
	if(val<0.1){
		$.dialog.alert('价格不能低于0.1')
		return ;
	}
	$("#shifu").text(val)
}
function updateOrder(el,orderNo){
	$.post('/u/order/orderDetail.htm',{'orderNo':orderNo},function(data){
		$("#jupdateOrder").html(data);
		art.dialog({
			title:"修改价格",
			lock:true,
			content:document.getElementById("jupdateOrder"),
			ok:function(){
				var adjustPriceYuan = $('#adjustPriceYuan').val();
				$.post('/u/order/updatePrice.htm',{'orderNo':orderNo,'adjustPriceYuan':adjustPriceYuan},function(data){
						location.reload();
				});
			}
		})
	},'html');
}

function updateAllTimes(el){
    var total = 0;
    var priceObj=$('#priceCount');
    var goodsPrice=priceObj.find('span:eq(0)').text();
    var transportPrice=priceObj.find('span:eq(1)').text();
    var integralPrice=priceObj.find('span:eq(2)').text();

    var selfVal = parseFloat($(el).val());
    if(selfVal == "" || isNaN(selfVal)) selfVal = 0;
    total =selfVal.toFixed(2);
    if(total >= 0) {
        $("#updateNumber").text("+"+total);
    }else{
        $("#updateNumber").text(total);
    };
    lastPrice=Number(goodsPrice)+Number(transportPrice)-Number(integralPrice)+Number(total);
    lastPrice=Math.round(lastPrice*100)/100;
    if(lastPrice<0){
        priceObj.find('span:eq(4)').text(0);
        art.dialog.tips("不能再减了，极限了！");
    }else{
        priceObj.find('span:eq(4)').text(lastPrice);
    }

}


//发货
function dispatch(deliveryWay,orderNo){
	art.dialog({
		title:"发货",
		lock:true,
		width:"450px",
		content:document.getElementById("jdispatch"+deliveryWay),
		ok:function(){
			var form=$("#jdispatch"+deliveryWay).find('form');
			var validate=form.validate({}).form();
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
</html>