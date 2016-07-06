<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理-会员管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("member-manage");
		$('#level-select').val('${index}');
		$('#orderbyCode').val('${cardDto.orderbyCode}');
	});
	/*删除*/
	function del(el) {
		art.dialog.confirm('确认删除？', function() {
			$(el).parent("td").parent("tr").remove();
		}, function() {
			return true;
		});
	}

	//保存
	function save() {
		art.dialog.confirm('是否保存？', function() {
			art.dialog.tips('保存成功!');
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}
</script>
</head>

<style>
.table>thead>tr>.active, .table>tbody>tr>.active, .table>tfoot>tr>.active,
	.table>thead>.active>td, .table>tbody>.active>td, .table>tfoot>.active>td,
	.table>thead>.active>th, .table>tbody>.active>th, .table>tfoot>.active>th
	{
	background-color: rgb(252, 252, 252);
}
</style>

<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="member-manage" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">会员管理</a></li>
							</ul>
						</div>
						<form action="${ctx }/u/member/hymg/list${ext}" method="post"
								class="form-horizontal" id="formPage">
						    <input type="hidden" id="maxAmount" name="maxAmount" value='${cardDto.maxAmount }'>
						    <input type="hidden" id="minAmount" name="minAmount" value='${cardDto.minAmount }' >
						    <input type="hidden" id="index" name="index"/>
							<div class="content">
								<div class="form-group">
									<label for="" class="col-md-1 control-label">筛选</label>
									<div class="col-md-2" style="width: 155px;">
										<input class="form-control" type="text" name="condition" value="${cardDto.condition }"
											placeholder="请输入姓名或联系电话" style="padding: 6px 2px;">
									</div>
									<label for="" class="col-md-2 control-label"
										style="max-width: 90px;">会员类型：</label>
									<div class="col-md-2" style="width: 137px; max-width: 137px;">
										<select class="form-control" id="level-select" onchange="changeLevel(this)">
											<option id="rule-0" value="0">所有</option>
											<c:forEach items="${ruleList }" var="rule" varStatus="i">
												<option id="rule-${i.index + 1 }" value="${i.index + 1 }" amount="${rule.consumptionAmout }">${rule.name }</option>
											</c:forEach>
										</select>
									</div>
									<label for="" class="col-md-2 control-label"
										style="max-width: 90px;">排序方式：</label>
									<div class="col-md-2" style="width: 159px; max-width: 159px;">
										<select class="form-control" id="orderbyCode" name="orderbyCode">
											<option value="0">默认</option>
											<option value="1">消费总额从高到低</option>
											<option value="2">消费总额从低到高</option>
											<option value="3">余额从高到低</option>
											<option value="4">余额从低到高</option>
											<option value="5">总积分从高到低</option>
											<option value="6">总积分从低到低</option>
										</select>
									</div>
									<div class="col-md-1">
										<button type="button" onclick="form.submit()" class="btn btn-default">查询</button>
									</div>
								</div>
<!-- 								<div class="">注意:在每行的输入框里可以通过输入消费金额（可以填写负数）来增减会员积分</div> -->
								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="11%">卡号</th>
											<th width="10%">姓名</th>
											<th width="13%">联系电话</th>
											<th width="10%">领卡时间</th>
											<th width="10%">会员等级</th>
											<th>消费总额</th>
											<th>余额</th>
											<th width="10%">总积分</th>
											<th width="10%">状态</th>
											<th width="10%">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas }" var="data">
											<tr>
												<td>${data.memberCard }</td>
												<td>${data.userName }</td>
												<td>${data.cell }</td>
												<td><fmt:formatDate value="${data.claimTime }" pattern="yyyy-MM-dd" /></td>
												<td>${data.levalName }</td>
												<td><fmt:formatNumber value="${data.consumptionAmout / 100 }" pattern="0.00" /> </td>
												<td><fmt:formatNumber value="${data.amount / 100 }" pattern="0.00" /></td>
												<td>${data.availableIntegral }</td>
												<td><c:choose><c:when test="${data.status == 1 }">正常</c:when><c:when test="${data.status == 0 }">冻结中</c:when></c:choose></td>
												<td><a class="btn btn-link btn-xs"
													href="${ctx }/u/member/hymg/view${ext}?id=${data.id}">查看</a> 
													<a class="btn btn-link btn-xs" href="${ctx }/u/member/hymg/editpage${ext}?id=${data.id}">编辑</a> 
													<!-- 会员充值功能隐藏 -->
<%-- 													<a class="btn btn-link btn-xs" href="${ctx }/u/member/hymg/czlist${ext}?cardId=${data.id}">充值记录</a> --%>
<%-- 													<a class="btn btn-link btn-xs" href="${ctx }/u/member/hymg/xflist${ext}?cardId=${data.id}">消费记录</a> --%>
												</td>
											</tr>
										</c:forEach>
										<c:if test="${empty pagination.datas }">
											<tr><td colspan="10" class="text-center">暂无内容</td>
										</c:if>
									</tbody>
								</table>
								<%@include file="../../common/page.jsp"%>
							</div>
						</form>
					</div>

				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
<script type="text/javascript">
//改变会员级别
function changeLevel(el) {
	var index = $(el).val();
	$('#index').val(index);
	if (0 == index) {
		$('#maxAmount').val(null);
		$('#minAmount').val(null);
		return;
	}
	var level = $('#rule-'+index);
	$('#minAmount').val(level.attr("amount"));
	index = parseInt(index) + 1;
	level = $('#rule-'+index);
	if (null != level) {
		$('#maxAmount').val(level.attr("amount"));
	} else {
		$('#maxAmount').val(null);
	}
}
</script>
</html>