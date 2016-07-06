<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分设置</title>
<%@ include file="../../common/base.jsp"%>
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
									data-toggle="tab">积分抵价</a></li>
							</ul>
						</div>
						<div class="content">
							<form class="form-horizontal" role="form" id="j-form" method="post">
								<input type="hidden" name="id" value="${id }"/>
								<div class="form-group">
									<b class="col-sm-2 control-label" style="width: 100px;">抵价规则</b>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 200px;"></label>
										<span class="word-desc"> <i>100</i> <em>积分</em>抵价 <i>1.00</i> 元
									</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 200px;">全店最高可抵扣比例</label>
									<div class="col-sm-4">
										<span class="icon"></span> 
										<input type="text" class="form-control" value="${maxVal }" name=maxVal id="maxVal" placeholder="1">
									</div>
									<span class="word-desc word-desc-gray"> <em>%</em>（1%-90%之间）
									</span>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 200px;"></label>
									<div class="col-sm-4">
										<button type="button" onclick="formSubmit()" class="btn btn-danger" id="btn-amend">保存</button>
										<a href="${ctx }/u/member/integral/index${ext}" style="margin-left: 20px;" class="btn btn-default">取消</a>
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
				maxVal : {
					positiveInteger : true,
					required : true,
					range:[1,90]
				}
			},
			messages : {
				maxVal : {
					positiveInteger : '请输入1-90之间的整数',
					range:'请输入1-90之间的整数'
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