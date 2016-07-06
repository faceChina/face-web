<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看会员信息</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("member-manage");
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
						<div class="content">
							<div class="row">
								<p class="basics-v2-title">
									<b>基本资料</b>
								</p>
								<table class="table table-bordered">
									<tbody>
										<tr>
											<th scope="row">会员卡号</th>
											<td colspan="3">${card.memberCard }</td>
										</tr>
										<tr>
											<th scope="row">姓名</th>
											<td style="width: 34%">${card.userName }</td>
											<th>性别</th>
											<td>
											<c:choose>
												<c:when test="${card.sex == 1 }">
													男
												</c:when>
												<c:when test="${card.sex == 2 }">
													女
												</c:when>
												<c:otherwise>
													未填写
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
										<tr>
											<th scope="row">联系方式</th>
											<td>${card.cell }</td>
											<th>生日</th>
											<td><c:choose>
												<c:when test="${empty card.birthday }">
													未填写
												</c:when>
												<c:otherwise>
													${card.birthday }
												</c:otherwise>
											</c:choose></td>
										</tr>
										<tr>
											<th scope="row">地址</th>
											<td colspan="3">
												<c:choose>
													<c:when test="${empty card.addressDetail }">
														未填写
													</c:when>
													<c:otherwise>
														${card.addressDetail }
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="row">
								<p class="basics-v2-title">
									<b>会员权限</b>
								</p>
								<table class="table table-bordered">
									<tbody>
										<tr>
											<th scope="row">会员等级</th>
											<td>${memberName }</td>
											<th>余额</th>
											<td><fmt:formatNumber value="${card.amount / 100 }" pattern="0.00" /></td>
										</tr>
										<tr>
											<th scope="row">消费总额</th>
											<td><fmt:formatNumber value="${card.consumptionAmout / 100 }" pattern="0.00" /></td>
											<th>总积分</th>
											<td>${card.availableIntegral }</td>
										</tr>
										<tr>
											<th scope="row">状态</th>
											<td colspan="3"><c:choose><c:when test="${card.status == 1 }">正常</c:when><c:otherwise>冻结中</c:otherwise></c:choose> </td>
										</tr>
										<tr>
											<th scope="row">备注</th>
											<td colspan="3">
												<c:choose>
													<c:when test="${empty card.remark }">
														未填写
													</c:when>
													<c:otherwise>
														${card.remark }
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="text-center">
								<button type="button" class="btn btn-danger"
									style="margin-right: 10px;"
									onclick="location.href='${ctx}/u/member/hymg/editpage${ext}?id=${card.id }'">修改</button>
								<button type="button" class="btn btn-default"
									onclick="location.href='${ctx}/u/member/hymg/list${ext}'">返回</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
</html>


