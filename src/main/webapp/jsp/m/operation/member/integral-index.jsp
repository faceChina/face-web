<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理-会员等级设置</title>
<title>积分设置</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${resourcePath }js/onoff.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			tab("integral-setting");
		});
	</script>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="integral-setting" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">积分设置</a></li>
							</ul>
						</div>
						<div class="content">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>类型</th>
										<th width="30%">操作</th>
										<th width="30%">状态</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>积分抵价</td>
										<td><a href="${ctx }/u/member/integral/djedit${ext}">修改</a></td>
										<td>
											<div class="OC_box">
												<span>关闭</span>
												<div class="OC_box_bar" id="" data-onoff="off">
													<input type="hidden" value="${dj }">
													<c:choose>
														<c:when test="${dj == 1 }">	
															<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, ${djid});"></h2>
														</c:when>
														<c:otherwise>
															<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, ${djid});"></h2>
														</c:otherwise>
													</c:choose>
												</div>
												<span>开启</span>
											</div>
										</td>
									</tr>
									<tr>
										<td>签到送积分</td>
										<td><a href="${ctx }/u/member/integral/qdedit${ext}">修改</a></td>
										<td>
											<div class="OC_box">
												<span>关闭</span>
												<div class="OC_box_bar" id="" data-onoff="off">
												    <input type="hidden" value="${qd }" />
													<c:choose>
														<c:when test="${qd == 1 }">	
															<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, ${qdid});"></h2>
														</c:when>
														<c:otherwise>
															<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, ${qdid});"></h2>
														</c:otherwise>
													</c:choose>
												</div>
												<span>开启</span>
											</div>
										</td>
									</tr>
									<tr>
										<td>消费送积分</td>
										<td><a href="${ctx }/u/member/integral/xfedit${ext}">修改</a></td>
										<td>
											<div class="OC_box">
												<span>关闭</span>
												<div class="OC_box_bar" id="" data-onoff="off">
													<input type="hidden" value="${xf }" />
													<c:choose>
														<c:when test="${xf == 1 }">	
															<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, ${xfid});"></h2>
														</c:when>
														<c:otherwise>
															<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, ${xfid});"></h2>
														</c:otherwise>
													</c:choose>
												</div>
												<span>开启</span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
<script type="text/javascript">
//活动滑动按钮	
function clickOnOff(el, toolId) {
	var thiz = el;
	var isClose = $(thiz).prev().hasClass("hide");
	var historyValue = $(thiz).prev().prev().prev().val();
	var changeValue= isClose ? 0 : 1;
	$(thiz).prev().prev().prev().val(changeValue);
	$.post("${ctx }/u/member/integral/altstatus${ext}", {"toolId" : toolId, "status" : changeValue}
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
</html>