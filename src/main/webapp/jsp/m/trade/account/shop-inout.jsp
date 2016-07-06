<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的钱包-我的收入</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("purse");

		$('.j-tooltip a').tooltip({
			placement : "right"
		})
		
		//下拉框值初始化
		$('#timesType').val('${aide.timesType}');
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
				<div class="row">
						<c:set var="crumbs" value="shopInout"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box box-auto">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">我的账户</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="form-horizontal">
								<div class="form-group">
									<ul class="list-unstyled list-unstyled-line">
										<li class="j-balance">累计收益：<strong class="color-danger">
												<fmt:formatNumber value="${consumeAmount/100 }"
													pattern="0.00" />
										</strong> 元
										</li>
										<li>昨日收益：<strong class="color-danger"> <fmt:formatNumber
													value="${yesterdayAmount / 100 }" pattern="0.00" />
										</strong> 元
										</li>
										<li class="j-tooltip annotation">冻结金额：<strong
											class="color-danger"> <fmt:formatNumber
													value="${freezeAmount / 100 }" pattern="0.00" />
										</strong> 元 <a href="#" data-toggle="tooltip" title=""
											data-original-title="指处于交易中的订单金额，买家确认后自动转化为收益"></a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">收支详情</a></li>
							</ul>
						</div>
						<form action="${ctx }/u/account/shopInout${ext}" id="formPage" method="post" class="form-horizontal">
							<div class="content tab-content">
								<div class="tab-pane fade in active panel-body" id="temp1">
									<div class="form-group">
										<label for="inputEmail3"
											class="col-md-1 control-label text-right">时间:</label>
										<div class="col-md-3">
											<select id="timesType" class="form-control" name="timesType"
												onchange="form.submit();">
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
												<th class="text-center">流水号</th>
												<th class="text-center">发生时间</th>
												<th class="text-center">来源</th>
												<th class="text-center">金额</th>
												<th class="text-center">动作</th>
												<th class="text-center">对象</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pagination.datas }" var="data">
												<tr>
													<td>${data.serialNumber }</td>
													<td><fmt:formatDate value="${data.createTime }"
															type="date" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
													<td>${data.source }</td> 
													<td>${data.operationPrice }</td>
													<td>${data.action }</td>
													<td>${data.target}</td>
												</tr>
											</c:forEach>
											<c:if test="${empty pagination.datas }">
												<tr>
													<td colspan="6" class="text-center">暂无内容</td>
											</c:if>
										</tbody>
									</table>
									<%@include file="../../common/page.jsp"%>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>