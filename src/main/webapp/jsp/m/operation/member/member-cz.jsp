<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员充值管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${resourcePath }js/onoff.js"></script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<script type="text/javascript">
		$(function() {
			tab("recharge-manage");
		});
	</script>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="recharge-manage" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">会员充值管理</a></li>
							</ul>
						</div>
						<div class="content">
							<ul class="pager" style="text-align: left; line-height: 31px;">
								<li class="next"><a class="btn btn-default"
									href="${ctx }/u/member/czmg/edit${ext}">＋ 新建</a>
									<div class="OC_box" style="float: right; padding-top: 4px;">
										<span>关闭</span>
										<div class="OC_box_bar" id="OC_box_bar" data-onoff="off">
											<input type="hidden" value="${cz }">
											<c:choose>
												<c:when test="${cz == 1 }">	
													<h1 class="ico_btn"></h1>
														<h3 class="ico_btn hide"></h3>
														<h2 class="ico_btn act_states" style='left: 26px;'
															onclick="clickOnOff(this, ${czid});"></h2>
												</c:when>
												<c:otherwise>
													<h1 class="ico_btn hide"></h1>
														<h3 class="ico_btn"></h3>
														<h2 class="ico_btn act_states" style='left: 0px;'
															onclick="clickOnOff(this, ${czid});"></h2>
												</c:otherwise>
											</c:choose>
										</div>
										<span>开启</span>
									</div></li>
							</ul>
							<form action="${ctx }/u/member/czmg/list${ext}" method="post"
								class="form-horizontal" id="formPage">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>名称</th>
											<th>有效时间</th>
											<th>赠送规则</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pagination.datas }" var="data">
											<tr>
												<td>${data.name }</td>
												<td><fmt:formatDate value="${data.startTime }" pattern="yyyy-MM-dd" />
												    / 
												    <fmt:formatDate value="${data.endTime }" pattern="yyyy-MM-dd" /></td>
												<td>${data.zsGz }
												</td>
												<td>
													<c:choose>
														<c:when test="${data.isActive == 0 }">
															进行中
														</c:when>
														<c:when test="${data.isActive == -1 }">
															已结束
														</c:when>
														<c:otherwise>
															未开始
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:if test="${data.isActive != 0 }">
														<a class="btn btn-link " href="${ctx }/u/member/czmg/edit${ext}?id=${data.id}">修改</a>|
													</c:if>
													<button type="button" class="btn btn-link btn-del" onclick="del(this, ${data.id})">删除</button>
												</td>
											</tr>
										</c:forEach>
										<c:if test="${empty pagination.datas }">
											<tr><td colspan="5" class="text-center">暂无内容</td>
										</c:if>
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
	<%@include file="../../common/footer.jsp"%>
	<script>
		/*删除*/
		function del(el, id) {
			art.dialog.confirm('删除后不可恢复，确定要删除？', function() {
				$.post("${ctx}/u/member/czmg/del${ext}", {"id" : id}, function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						$(el).parent("td").parent("tr").remove();
						var len = $("#formPage tbody tr").length;
						if (len == 0) {
							$('#formPage tbody').html('<tr><td colspan="5" class="text-center">暂无内容</td>');
						}
					} else {
						art.dialog.alert(data.info);
					}
				});
			}, function() {
				return true;
			});
		}
		//活动滑动按钮	
		function clickOnOff(el, toolId) {
			var thiz = el;
			var isClose = $(thiz).prev().hasClass("hide");
			var historyValue = $(thiz).prev().prev().prev().val();
			var changeValue= isClose ? 0 : 1;
			$(thiz).prev().prev().prev().val(changeValue);
			$.post("${ctx }/u/member/czmg/altstatus${ext}", {"toolId" : toolId, "status" : changeValue}
					, function(jsonData) {
				var data = JSON.parse(jsonData);
				if (!data.success) {
					$(thiz).prev().prev().prev().val(historyValue);
					art.dialog.alert(data.info);
				} else {
					if (isClose) {
						//设为关闭
						$(thiz).animate({
							left : "0"
						}, 200, function() {
							$(thiz).prev().removeClass("hide");
							$(thiz).prev().prev().addClass("hide");
						});
					} else {
						//设为开启
						$(thiz).animate({
							left : "26px"
						}, 200, function() {
							$(thiz).prev().addClass("hide");
							$(thiz).prev().prev().removeClass("hide");
						});
					}
				}
			});
		}
	</script>
</body>
</html>