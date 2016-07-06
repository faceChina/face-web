<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>签到送积分</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("integral-setting");

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
					<c:set var="crumbs" value="integral-setting" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">签到送积分</a></li>
							</ul>
						</div>
						<div class="content">
							<form class="form-horizontal" role="form" id="j-form" method="post">
								<input type="hidden" name="id" value="${id }" />
								<div class="form-group">
									<b class="col-sm-2 control-label" style="width: 100px;">赠送规则</b>
									<span class="rule-desc"> （设置首日送 <em> 5 </em>个，连续签到多送<em>
											5 </em> 个，即：首日签到领 <em> 5 </em> 个，连续第二天可领 <em> 10 </em> 个，第三天 <em>
											15 </em> 个)
									</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 120px;">首日签到送</label>
									<div class="col-sm-4">
										<span class="icon"></span> <input type="text"
											class="form-control form-control-fix" name="first" value="${first }" max="9999999"
											id="first" placeholder="5">
									</div>
									<span>积分</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 120px;">连续签到多送</label>
									<div class="col-sm-4">
										<span class="icon"></span> <input type="text"
											class="form-control form-control-fix" name="stepAccumulate" value="${stepAccumulate }" max="9999999"
											id="stepAccumulate" placeholder="5">
									</div>
									<span>积分</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 120px;">每天最多送</label>
									<div class="col-sm-4">
										<span class="icon"></span> <input type="text"
											class="form-control form-control-fix" name="maxVal" value="${maxVal }" max="9999999"
											id="maxVal" placeholder="10">
									</div>
									<span>积分</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 120px;"></label>
									<div class="col-sm-4">
										<button type="button" class="btn btn-danger" onclick="formSubmit()">保存</button>
										<a href="${ctx }/u/member/integral/index${ext}" style="margin-left: 20px;"
											class="btn btn-default">取消</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	<script>
		var activity = $("#j-form").validate({
			rules : {
				first : {
					required : true,
					positiveInteger : true,
					lessTo:$('#maxVal')
				},
				stepAccumulate : {
					lessTo:$('#maxVal'),
					positiveInteger : true,
					required : true
				},
				maxVal : {
					positiveInteger : true,
					required : true
				}
			},
			messages : {
				stepAccumulate : {
					lessTo:"必须小于最多赠送值"
				},
				first : {
					lessTo:"必须小于最多赠送值"
				}
			}
			
		});
		function formSubmit() {
			if (activity.form()) {
				$(".j-loading").show();
				$('#j-form').submit();
			}
		}
	</script>
</body>
</html>