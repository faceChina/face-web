<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的钱包-我的账户</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("accounts");
		//下拉框值初始化
		$('#timesType').val('${aide.timesType}');
		if ("${type}" == 2) {
			$('#status').val('${status}');
		}
		$('.j-tooltip a').tooltip({
	        placement:"right"
	    })
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
						<c:set var="crumbs" value="account"/>
						<%@include file="../../common/crumbs.jsp"%>
				<div class="row">
					<div class="box box-auto">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">我的账户</a></li>
							</ul>
						</div>
						<div class="content">
							<ul class="media-info" style="width: 50%;">
								<li>钱包余额：<b class="color-danger"><fmt:formatNumber
											value="${amount / 100 }" pattern="0.00" /></b> 元 <a
									href="${ctx }/u/account/withdraw/index${ext}" class="btn btn-default">申请提现</a></li>
								<li>冻结金额：
									<b class="color-danger"><fmt:formatNumber value="${freezeAmount/100 }" pattern="0.00"></fmt:formatNumber></b> 元  
									<span class="morecol j-tooltip">
										<a href="#" data-toggle="tooltip" title="" data-original-title="指处于交易中的订单金额，订单交易成功后自动转为收益。"></a>
									</span>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li <c:if test="${type == 1 }">class="active"</c:if>><a
									href="${ctx }/u/account/inout${ext}"
									<c:if test="${type == 1 }">data-toggle="tab"</c:if>>收支详情</a></li>
								<li <c:if test="${type == 2 }">class="active"</c:if>><a
									href="${ctx }/u/account/witdrawList${ext}"
									<c:if test="${type == 2 }">data-toggle="tab"</c:if>>提现记录</a></li>
							</ul>
						</div>
						<c:choose>
							<c:when test="${type == 1 }">
								<form action="${ctx }/u/account/inout${ext}"
									class="form-horizontal" id="formPage">
									<div class="content tab-content">
										<div class="tab-pane fade in active panel-body" id="temp1">
											<div class="form-group">
												<label for="inputEmail3"
													class="col-md-1 control-label text-right">时间：</label>
												<div class="col-md-3">
													<select id="timesType" class="form-control" name="timesType" onchange="form.submit();">
														<option value="0">全部记录</option>
														<option value="7">近一周</option>
														<option value="30">近一个月</option>
														<option value="90">近三个月</option>
														<option value="365">近一年</option>
													</select>
												</div>
											</div>
											<table class="table table-bordered">
												<thead>
													<tr>
														<th width="20%" class="text-center">流水号</th>
														<th width="20%" class="text-center">时间</th>
														<th width="20%" class="text-center">名称</th>
														<th class="text-center">收入</th>
														<th width="10%" class="text-center">支出</th>
														<th width="10%" class="text-center">账户余额</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${pagination.datas }" var="data">
														<tr>
															<td>${data.serialNumber }</td>
															<td><fmt:formatDate value="${data.createTime }" type="date" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
															<td>${data.userAction }</td>
															<td>${data.recieve }</td>
															<td>${data.pay }</td>
															<td>${data.userBalanceString }</td>
														</tr>
													</c:forEach>
													<c:if test="${empty pagination.datas }">
														<tr><td colspan="6" class="text-center">暂无内容</td>
													</c:if>
												</tbody>
											</table>
											<%@include file="../../common/page.jsp" %>
										</div>
									</div>
								</form>
							</c:when>
							<c:otherwise>
								<form action="${ctx }/u/account/witdrawList${ext}"
									class="form-horizontal" id="formPage">
									<div class="tab-pane fade in panel-body" id="temp2">
										<div class="form-group">
											<label for="inputEmail3"
												class="col-md-1 control-label text-right">时间：</label>
											<div class="col-md-3">
												<select id="timesType" class="form-control" id="timesType" name="aide.timesType" onchange="form.submit();">
													<option value="0">全部记录</option>
													<option value="7">近一周</option>
													<option value="30">近一个月</option>
													<option value="90">近三个月</option>
													<option value="365">近一年</option>
												</select>
											</div>
											<label for="inputEmail3"
												class="col-md-1 control-label text-right">状态：</label>
											<div class="col-md-3">
												<select class="form-control" id="status" name="status" onchange="form.submit();">
										         	<option value="">全部记录</option>
													<option value="5">提现中</option>
													<option value="0">提现成功</option>
													<option value="-1">提现失败</option>
												</select>
											</div>
										</div>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th class="text-center">流水号</th>
													<th class="text-center">时间</th>
													<th class="text-center">金额</th>
													<th class="text-center">收支对象</th>
													<th class="text-center">状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${pagination.datas}" var="data">
													<tr>
														<td>${data.seriNumber}</td>
														<td><fmt:formatDate value="${data.createTime }" type="date" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
														<td><fmt:formatNumber value="${data.withdrawPrice / 100 }" pattern="0.00" /></td>
														<td>${data.cardForView}</td>
														<td>${data.withdrawalStates}</td>
													</tr>
												</c:forEach>
												<c:if test="${empty pagination.datas }">
													<tr><td colspan="5" class="text-center">暂无内容</td>
												</c:if>
											</tbody>
										</table>
										<%@include file="../../common/page.jsp" %>
									</div>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>