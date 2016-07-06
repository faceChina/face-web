<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>银行卡提现</title>	
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<script type="text/javascript">
	$(function(){
		tab("accounts");
	});
</script>
</head>
<body>
<%@include file="../../../common/header.jsp"%>
<div class="container" id="j-content">
	<div class="row">
		<div class="col-md-2 ">
			<%@include file="../../../common/left.jsp"%>
		</div>
		<div class="col-md-10">
			<div class="row">
				<c:set var="crumbs" value="account"/>
						<%@include file="../../../common/crumbs.jsp"%>
			</div>
			<div class="row">
				<div class="box box-auto">
					<div class="title">
						<ul class="nav nav-tabs">
							<li class="active"><a>提取余额到银行卡</a></li>
						</ul>
					</div>
					<div class="content">
						<div class="bank-card-header">
							<b>钱包余额：</b>
							<strong class="color-danger"><fmt:formatNumber value="${withdrawAmount / 100 }" pattern="0.00" /></strong> 元
							<label>（温馨提示：今日您还可以提现${withdrawCount}次）</label>
						</div>
						<div class="alert alert-warning">
							<b class="color-danger">*</b>  提现银行卡支持储蓄卡，信用卡暂不可用，目前支持中国工商银行、农业银行、建设银行、交通银行、邮政储蓄银行、中信银行、光大银行、兴业银行、平安银行、浦发银行、北京银行、上海银行
						</div>
						<div class="form-horizontal bank-card-new">
							
							<div class="form-group add-bank-card">
								<b class="pull-left">选择银行卡：</b>
								<div class="col-md-10">
									<a href="/u/account/bankcard/list.htm">
										<span class="glyphicon glyphicon-plus font-add"></span>
										添加银行卡
									</a>
									<div class="modal-title">
										<span class="glyphicon glyphicon-exclamation-sign fontcor-red"></span>
										此银行卡无需开通网银
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="../../../common/footer.jsp"%>

</body>
</html>

