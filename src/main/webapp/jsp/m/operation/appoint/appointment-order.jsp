<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<%@ include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<title>订单管理</title>
<style>
label.control-left{
	width: 86px;
}
label.width84{
	width: 84px;
}
.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
    cursor: pointer;
}
</style>
<script type="text/javascript">
$(function(){
	tab("appointment-order");
});

function submitf(){
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
			<c:set var="crumbs" value="appointOrder"/>
			<%@include file="../../common/crumbs.jsp"%>

					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#ordermanage" data-toggle="tab">预约订单</a></li>
								</ul>
							</div>
							<div class="content tab-content" >
								<!-- tab -->
								<div class="tab-pane active" id="ordermanage">
									<form class="form-horizontal" method="post" id="formPage">
										<fieldset>
										<div class="form-group">
											<label for="" class="col-md-1 control-label width84" >预约名称：</label>
											<div class="col-md-3">
												<input class="form-control" type="text" id="appointmentName" name="appointmentName" value="${salesOrderVo.appointmentName}">
											</div>
											<label for="" class="col-md-1 control-label width84" >联系人：</label>
											<div class="col-md-2">
												<input class="form-control" type="text" id="receiverName" name="receiverName" value="${salesOrderVo.receiverName}">
											</div>
											<label for="" class="col-md-1 control-label width84" >联系电话：</label>
											<div class="col-md-3">
												<input class="form-control" type="text" id="receiverPhone" name="receiverPhone" value="${salesOrderVo.receiverPhone}">
											</div>
											
										</div>
										<div class="form-group">
											<label for="" class="col-md-2 control-label width84">提交时间：</label>
											<div class="col-md-3">
												<c:choose>
													<c:when test="${null == salesOrderVo.startTime}">
														<input type="text" id="startTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="startTime" >
													</c:when>
													<c:otherwise>
														<input type="text" id="startTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="startTime" value="<fmt:formatDate value="${salesOrderVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
													</c:otherwise>
												</c:choose>

											</div>
											<label for="" class="col-md-1  control-label" style="width:20px;">至</label>
											<div class="col-md-3">
												<c:choose>
													<c:when test="${null == salesOrderVo.endTime}">
														<input type="text" id="endTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="endTime" >
													</c:when>
													<c:otherwise>
														<input type="text" id="endTime" class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="readonly" name="endTime" value="<fmt:formatDate value="${salesOrderVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
													</c:otherwise>
												</c:choose>


											</div>
											<div class="col-md-2">
												<select class="form-control" id="status" name="status" onchange="submitf();">
													<option value="">全部</option>
													<option  <c:if test="${salesOrderVo.status=='40' }">selected="selected"</c:if> value="40">待处理</option>
													<option  <c:if test="${salesOrderVo.status=='41' }">selected="selected"</c:if> value="41">已确定</option>
													<option  <c:if test="${salesOrderVo.status=='42' }">selected="selected"</c:if> value="42">已拒绝</option>
													<option  <c:if test="${salesOrderVo.status=='43' }">selected="selected"</c:if> value="43">已取消</option>
												</select>
											</div>
											<div class="col-md-2 text-right">
												<button type="submit" class="btn btn-default">查询</button>
											</div>
										</div>
									</fieldset>
									<div class="content tab-content">
												<table class="table table-bordered" style="font-size:12px;">
													<thead>
														<tr>
															<th width="12%">预约名称</th>
															<th width="13%">预约商品</th>
															<th width="9%">联系人</th>
															<th width="14%">联系电话</th>
															<th width="">客户需求</th>
															<th width="17%">预约状态</th>
															<th width="12%">操作</th>
														</tr>
													</thead>
												</table>
										<c:forEach items="${pagination.datas}" var="bookOrder">
												<table class="table table-bordered" style="font-size:12px;">
													<tbody>
														<tr class="bg-gray">
															<td colspan="7">
																<div class="m-horizontal">
																	<div class="m-horizontal-left">
																		<span>订单号：${bookOrder.orderNo}</span> |
																		<span>提交时间：<fmt:formatDate value="${bookOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
																		金额：<span class="color-danger"><fmt:formatNumber pattern="0.00" value="${bookOrder.totalPrice / 100}"/></span>元
																	</div>
																	<div class="m-horizontal-right">
																		<a href="javascript:;" class="btn btn-default btn-xs" onclick="remarks(this,'${bookOrder.orderNo}')" onmouseover="remarksOver(this)" onmouseout="remarksOut(this)">备注<i class='glyphicon glyphicon-flag' style='display:none;margin-left:6px;' onmouseover='remarksOver(this)' onmouseout='remarksOut(this)'></i></a>
																		<!-- 备注后的信息 -->
																		<div class="remarkInfo" id="remarkInfo">${bookOrder.sellerMemo}</div>
																	</div>
																</div>
															</td>
														</tr>

														<tr>
															<td width="12%" rowspan="2">${bookOrder.appointmentName}</td>
                                                            <td width="12%">
																<c:choose>
																	<c:when test="${not empty bookOrder.orderItemList}">
																		<c:forEach items="${bookOrder.orderItemList}" var="bookOrder_oi" varStatus="status">
																			${bookOrder_oi.goodName}<c:if test="${!status.last }">,</c:if> <br/>
																		</c:forEach>
																	</c:when>
																	<c:otherwise>
															--
																	</c:otherwise>
																</c:choose>
                                                            </td>
															<td width="9%" rowspan="2">${bookOrder.receiverName}</td>
															<td width="14%" rowspan="2">${bookOrder.receiverPhone}</td>
															<td  class="text-left"  rowspan="2">

																<c:forEach items="${bookOrder.orderAppointmentDetails}" var="appointmentDetail">
																<p>${appointmentDetail.attrName}：${appointmentDetail.attrValue}</p>
																</c:forEach>
															</td>
															<td  width="17%" rowspan="2">
																<p>${bookOrder.statusName}</p>
																<c:if test="${'待处理' == bookOrder.statusName}">
																<p>
																	<a href="javascript:;" class="btn btn-default" onclick="completeOrder(this,'${bookOrder.orderNo}')">确认</a>
																	<a href="javascript:;" class="btn btn-default" onclick="closeOrder(this,'${bookOrder.orderNo}')">拒绝</a>
																</p>
																</c:if>
															</td>
															<td width="12%" rowspan="2"><a href="${ctx}/u/order/detail.htm?orderNo=${bookOrder.orderNo}" class="color-danger">查看详情</a></td>
														</tr>
														</tbody>
													</table>
										</c:forEach>
										<%@include file="../../common/page.jsp" %>
									</form>
									</div>
				
								</div>
								<!-- tab end -->
							</div>
						</div>
					</div>

			</div>
		</div>
	</div>
	<!-- body end -->
	
	<!-- 备注 -->
	<div id="jremark" style="display:none;">
		<form action="" class="" id="jremarkForm">
			<div class="form-group">
				<textarea class="form-control" id="msg" name="msg" style="width:500px;height:200px;"></textarea>
			</div>
		</form>
	</div>
	
	<!-- 关闭定单 -->
	<div id="jcancelOrder" style="display:none;">
		<h4 style="font-size:14px;line-height:28px;">请填写拒绝原因：</h4>
		<textarea name="" id="refuseReason" class="form-control" rows="5" maxlength="20"></textarea>
		<p style="font-size:12px;text-align:right;">您可以输入<span class="color-danger" id="Jnum">20</b>个字符</p>
	</div>
	
	<!-- 完成定单 -->
	<div id="jcompleteOrder" style="display:none;">
		<h4>您确定受理该订单？</h4>
		<p></p>
	</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	</body>
	
<script type="text/javascript">
//备注
function remarks(str,orderNo){
	$("#msg").val($(str).next().text());
	art.dialog({
		title:"添加备注",
		lock:true,
		content:document.getElementById("jremark"),
		ok:function(){
			var memo = $('#msg').val();
			$.post("/u/order/remark.htm",
					{
						orderNo:orderNo,
						sellerMemo:memo
					});

			var flag = $("#msg").val();
			console.log(flag);
			if(flag != ""){
				$(str).find("i").show();
				$(str).siblings(".remarkInfo").text(flag);
			}else{
				$(str).find("i").hide()
			}
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


//关闭定单
function closeOrder(el,orderNo){
	art.dialog({
		title:"拒绝原因",
		lock:true,
		content:document.getElementById("jcancelOrder"),
		ok:function(){
			var refuseReason = $('#refuseReason').val();
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


/* 文本字符限制 */
function textLimit(el,num,max){
	var currentNum = $(el).val().length;//当前字数
	if(currentNum >= max) currentNum = max;
	var less = max - currentNum; //剩余字数
	$(num).text(less);
}

$("#Jtextarea").on("keydown keyup",function(event){
	textLimit(this,"#Jnum",20);
})

</script>
</html>
	
	
