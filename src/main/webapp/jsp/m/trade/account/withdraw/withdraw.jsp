<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>我的银行卡</title>	
<%@ include file="../../../common/base.jsp"%>
<%@ include file="../../../common/validate.jsp"%>
<script type="text/javascript">
$(function(){
	tab("accounts");
	if('${errMsg}'!=''){
		$.dialog.alert('${errMsg}');
	}
});

function formSubmit() {
	if(!$("#jform").validate({
		rules:{
			paymentCode:{
				required:true,
				minlength: 6
			},
			price:{
				required:true,
				moneyNotZero: true
			}
		},
		messages:{
			paymentCode:{
				required:"请输入支付密码",
				minlength:$.format("支付密码不能小于{0}个字符")
			},
			price:{
				required:"请输入验证码"
			}
		}
	}).form())return;
	var paymentCode = $('#paymentCode').val();
	if (null == paymentCode || '' == paymentCode) {
		art.dialog.alert("请输入支付密码");
		return;
	}
	if($("#withdrawPrice").val()<=0){
		$.dialog.alert("提现金额必须大于0.");
		return;
	}
	if($("#withdrawPrice").val()*100>'${withdrawAmount}'){
		$.dialog.alert("提现金额不能大于钱包余额.");
		return;
	}
	
	$(".j-loading").show();
	$('#jform').submit();
		
}
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
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a>提取余额到银行卡</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="form-horizontal bank-card-new">
								<div class="form-group">
									<b>钱包余额：</b>
									<strong class="color-danger"><fmt:formatNumber value="${withdrawAmount / 100 }" pattern="0.00" /></strong> 元
									<label>（温馨提示：今日您还可以提现${withdrawCount}次）</label>
								</div>
								<div class="form-group">
									<p class="alert alert-warning">
										<b class="color-danger">*</b>  提现只支持储蓄卡（中国工商银行、农业银行、建设银行、交通银行、邮政储蓄银行、中信银行、光大银行、兴业银行、平安银行、浦发银行、北京银行、上海银行）
									</p>
								</div>
								<form class="form-horizontal" action="/u/account/withdraw/withdrawV2.htm" role="form" id="jform" method="post" >
									<input type="hidden" name="validateToken" value="${validateToken }"/>
								    
								    <input type="hidden" name="mobilecode" value="${mobilecode }"/>
									<div class="form-group">
										<label for="bank" class="col-sm-3 control-label">选择银行：</label>
										<div class="col-sm-4">
											<select class="form-control" name="cardId">
												<c:forEach items="${cardList}" var="bankCard">
												<option value="${bankCard.id}">${bankCard.bankName} 尾号${bankCard.lastFourChar}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-5">
											<a href="/u/account/bankcard/list.htm" class="btn btn-link">添加银行卡</a>
										</div>
									</div>
									<div class="form-group">
										<label for="bankcard" class="col-sm-3 control-label">提现金额：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" id="withdrawPrice" name="price" value="${price }"/>
										</div>
										<label class="col-sm-3 checkbox-inline">元</label>
										<div class="col-sm-5 form-control-static">
											
										</div>
									</div>
									<div class="form-group">
										<label for="bankcard" class="col-sm-3 control-label">支付密码：</label>
										<div class="col-sm-4">
											<input class="form-control" type="text" placeholder=""  maxlength="20" id="paymentCode" name="paymentCode" onfocus="this.type='password'" autocomplete="off"/>
										</div>
										<div class="col-sm-5">
											<a class="btn btn-link" href="${ctx }/u/account/security/paymentCode${ext}?retUrl=/u/account/withdraw/index">忘记密码？</a>
										</div>
									</div>
									<div class="form-group">
										<div class="text-center">
											<a class="btn btn-default" href="javascript:formSubmit();">提交</a>
											<a class="btn btn-default" href="javascript:window.history.back(-1);">取消</a>
										</div>
									</div>
								</form>
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


