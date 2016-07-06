<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送方式设置</title>
<%@ include file="../../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("tempmanage");
	});
	//活动滑动按钮	
	function clickOnOff(el, value) {
		var thiz = el;
		var isClose = $(thiz).prev().hasClass("hide");
		var historyValue = $(thiz).prev().prev().prev().val();
		if (isClose) {
			$(thiz).prev().prev().prev().val(0);
		} else {
			$(thiz).prev().prev().prev().val(value);
		}
		$.post("${ctx }/u/shop/logistics/altlogisticsTp${ext}", $("#j-form")
				.serialize(), function(jsonData) {
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
</head>
<body>
	<%@ include file="../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="logistics"/>
					<%@include file="../../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">配送方式设置</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="alert alert-warning" role="alert">
								<p>配送方式设置：对配送方式进行设置，包括快递配送、店家配送和门店自取三种配送方式</p>
							</div>
							<form id="j-form">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>配送方式名字</th>
											<th width="30%"></th>
											<th width="30%">操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>快递配送</td>
											<td><a
												href="${ctx }/u/shop/logistics/dispatchIndex${ext}">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar">
														<input type="hidden" name="logisticsTypeArr"
															value="${shop.kdType}" />
														<c:choose>
															<c:when test="${shop.kdType == 1 }">
																<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, 1);"></h2>
															</c:when>
															<c:otherwise>
																<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, 1);"></h2>
															</c:otherwise>
														</c:choose>
													</div>
													<span>开启</span>
												</div>
											</td>
										</tr>
										<tr>
											<c:choose>
												<c:when test=""></c:when>
											</c:choose>
											<td>店家配送</td>
											<td><a href="${ctx }/u/shop/logistics/pslist${ext}">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar">
														<input type="hidden" name="logisticsTypeArr"
															value="${shop.psType}" />
														<c:choose>
															<c:when test="${shop.psType == 2 }">
																<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, 2);"></h2>
															</c:when>
															<c:otherwise>
																<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, 2);"></h2>
															</c:otherwise>
														</c:choose>
													</div>
													<span>开启</span>
												</div>
											</td>
										</tr>
										<tr>
											<td>门店自取</td>
											<td><a href="${ctx }/u/shop/logistics/ztlist${ext}">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar">
														<input type="hidden" name="logisticsTypeArr"
															value="${shop.ztType}" />
														<c:choose>
															<c:when test="${shop.ztType == 4 }">
																<h1 class="ico_btn"></h1>
																<h3 class="ico_btn hide"></h3>
																<h2 class="ico_btn act_states" style='left: 26px;'
																	onclick="clickOnOff(this, 4);"></h2>
															</c:when>
															<c:otherwise>
																<h1 class="ico_btn hide"></h1>
																<h3 class="ico_btn"></h3>
																<h2 class="ico_btn act_states" style='left: 0px;'
																	onclick="clickOnOff(this, 4);"></h2>
															</c:otherwise>
														</c:choose>
													</div>
													<span>开启</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../../common/footer.jsp"%>
</body>
</html>