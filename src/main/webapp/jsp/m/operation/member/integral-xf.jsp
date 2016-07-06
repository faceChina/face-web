<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消费送积分</title>
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
									data-toggle="tab">消费送积分</a></li>
							</ul>
						</div>
						<div class="content">
							<form class="form-horizontal" role="form" id="j-form"
								method="post">
								<input type="hidden" name="id" value="${id }" />
								<div class="form-group">
									<b class="col-sm-2 control-label" style="width: 100px;">赠送规则</b>
								</div>
									<div class="form-group">
                                        <div class="col-md-6">
                                            <label class="col-sm-2 control-label" style="width:117px;">消费：</label>
                                            <div class="col-sm-4 col-sm-4-fixed" style="width:257px;">
                                                <input type="text" class="form-control" value="<fmt:formatNumber value='${premiseVal / 100 }' pattern='0.00'/>" max="9999999" name="premiseVal" id="premiseVal" placeholder="100" style="width: 230px">
                                                <span>元</span>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <label class="col-sm-2 control-label" style="text-align: center;">送</label>
                                            <div class="col-sm-4" style="width: 240px;">
                                                <input type="text" class="form-control" maxlength="9" value="${resultVal }" name="resultVal" id="resultVal" placeholder="10" style="width: 230px">
                                            </div>
                                            <span>积分</span>
                                        </div>
                                    </div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 200px;"></label>
									<div class="col-sm-4">
										<button type="button" class="btn btn-danger"
											onclick="formSubmit()">保存</button>
										<a href="${ctx }/u/member/integral/index${ext}"
											style="margin-left: 20px;" class="btn btn-default">取消</a>
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
				premiseVal : {
					moneyNotZero : true,
					required : true
				},
				resultVal : {
					positiveInteger : true,
					required : true
				}
			},
			messages : {
				premiseVal : {
					moreTo : '必须大于0',
					moneyBase : '请输入正确的金额，保留两位小数'
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