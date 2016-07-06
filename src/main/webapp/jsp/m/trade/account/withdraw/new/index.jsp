<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>我的钱包-我的账户</title>
<%@ include file="../../../../common/base.jsp"%>
<%@ include file="../../../../common/validate.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("accounts");
		$('.popover-dismiss').popover();
		dealErrMsg();
		//返回时卡bin验证
		var isVisible = $('#data-history').is(':visible');
		if (!isVisible) {
			getCardbin();
		}
	});
	//错误信息处理
	function dealErrMsg() {
		var errMsg = '${errorMsg}';
		if (null != errMsg && '' != errMsg) {
			art.dialog.alert(errMsg);
		}
	}
</script>
</head>
<body>
	<%@include file="../../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="account"/>
					<%@include file="../../../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">提取余额到银行卡</a></li>
							</ul>
						</div>
						<div class="content">
							<form action="${ctx }/u/account/withdraw/withdraw${ext}" id="jform" 
							      class="form-horizontal" method="post">
							    <input type="hidden" name="cardId" id="cardId"/>
							    <input type="hidden" name="validateToken" value="${validateToken }"/>
								<div class="form-group">
									<label class="col-md-12 control-left"><b>请绑定持卡人本人的银行卡</b></label>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><b>持卡人：</b></label>
									<div class="col-md-9">
										<span>${userInfo.contacts }</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><b>身份证号码：</b></label>
									<div class="col-md-9">
										<span>${userInfo.identity }</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><b>选择银行卡：</b></label>
									<div class="col-md-3">
										<select class="form-control" id="bankCode" name="bankCode">
											<option value="-1" >---请选择银行---</option>
										    <c:forEach items="${bankTypes }" var="bank">
										        <option value="${bank.bankCode }"<c:if test="${bankCode==bank.bankCode }"> selected</c:if> >${bank.bankName }</option>
										    </c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><b>银行卡号：</b></label>
									<div class="col-md-5">
										<input class="form-control" type="text" value="${cardNo }" id="cardNo" name="cardNo" >
									</div>
									<div class="col-md-5 control-left">*提现银行支持储蓄卡，信用卡暂不可用</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 control-label"><b>钱包余额：</b></label>
									<div class="col-md-9">
										<span class="form-control-static"><b
											class="clr-attention" id="jsurplus"><fmt:formatNumber value="${withdrawAmount/100}" pattern="0.00" /></b> 元</span> <span
											class="form-control-static">（温馨提示：今日您还可以提现<span
											class="color-danger">${withdrawCount}</span>次）
										</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><b>提现金额：</b></label>
									<div class="col-md-3">
										<input class="form-control input-short-11" id="price" value="${price }"
											name="price" type="text">
									</div>
									<div class="col-md-5 control-left">
										元
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 control-label"><b>手机号码：</b></label>
									<div class="col-md-9">
										<span class="form-control-static">${userInfo.cell}</span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 control-label"><b>短信验证：</b></label>
									<div class="col-md-2">
										<input class="form-control" type="text" value=""  maxlength="20"
												id="mobilecode" name="mobilecode" placeholder="" />
									</div>
									<div class="col-md-3">
										<button type="button" class="btn btn-default" 
												onclick="getCode(this)">免费获取验证码</button>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label"><b>支付密码：</b></label>
									<div class="col-md-9">
										<input class="form-control input-short-3" id="paymentCode" 
											name="paymentCode" type="text" >
									</div>
								</div>
								<hr/>
								<div class="form-group">
									<div class="col-md-offset-3 col-md-9">
										<button id="confirm-button" onclick="formSubmit()" type="button" class="btn btn-danger input-short-2" <c:if test="${withdrawCount <= 0}">disabled="disabled"</c:if> >确定</button>
										<button id="cancel-button" onclick="window.history.back(-1);" type="button" class="input-short-2 btn btn-default">取消</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../../../common/footer.jsp"%>
</body>
<script type="text/javascript">
    var validForm = $("#jform").validate({
		ignore : ".ignore",
		rules : {
			bankCode : {
				min : 0
			},
			price : {
				required : true,
				moneyNotZero : true,
				lessTo : '#jsurplus'
			},
			mobilecode : {
				required : true,
				minlength : 6
			},
			cardNo : {
				required : true,
				rangelength:[16, 19]
			},
			paymentCode : {
				required : true,
				minlength : 6
			}
		},
		messages : {
			bankCode : {
				min : "请选择银行卡"
			},
			price : {
				required : "请输入金额",
				lessTo : '可用余额不足',
				moneyNotZero : $.format("金额必须大于0.00，保留两位小数")
			},
			mobilecode : {
				required : "请输入验证码",
				minlength : $.format("密码不能小于{0}个字符")
			},
			cardNo : {
				required : "请输入卡号",
				rangelength : $.format("银行卡号应该在{0}到{1}个字符间")
			},
			paymentCode : {
				required : "请输入支付密码",
				minlength : $.format("支付密码不能小于{0}个字符")
			}
		}
	});
	var judgeCard = function(){
		var domStr = '#judgecard';
		var cardDom = $(domStr);
		function setDom(el){
			cardDom = $(el);
		}
		//显示信用卡
		function creditCard(){
			cardDom.find('[data-debit="true"]').hide().find('input').addClass('ignore');
			cardDom.find('[data-credit="true"]').show().find('input').removeClass('ignore');
		}
		//显示借记卡
		function debitCard(){
			cardDom.find('[data-credit="true"]').hide().find('input').addClass('ignore');
			cardDom.find('[data-debit="true"]').show().find('input').removeClass('ignore');
		}
		//添加银行卡
		function addCard(){
			getCardbin();
			cardDom.find('[data-history="true"]').hide().find('input').addClass('ignore');
			cardDom.find('[data-new="true"]').show().find('input').removeClass('ignore');
		}
		//选择已有卡号
		function historyCard(){
			initInputCard();
			cardDom.find('[data-new="true"]').hide().find('input').addClass('ignore');
			cardDom.find('[data-history="true"]').show().find('input').removeClass('ignore');
		}
		//输入的初始阶段
		function initInputCard() {
			cardDom.find('[data-credit="true"]').hide().find('input').addClass('ignore');
			cardDom.find('[data-debit="true"]').hide().find('input').addClass('ignore');
		}
		//添加银行卡
		function bindaddCard(el){
			$(el).on('click',function(){
				addCard();
			})
		}
		//选择已有卡号
		function bindhistoryCard(el){
			$(el).on('click',function(){
				historyCard();
			})
		}
		return {
			setdom:setDom,
			creditcard: creditCard,
			debitcard: debitCard,
			addcard: bindaddCard,
			historycard: bindhistoryCard,
			initInputCard:initInputCard
		};
	}();
	//绑定事件
	judgeCard.addcard('#addcard');
	judgeCard.historycard('#historycard');

	//提交事件
	function formSubmit() {
		var bankCode = $("bankCode").val();
		if (!validForm.form()) {
			return;
		}
		//选择已有卡操作
		var isVisible = $('#data-history').is(':visible');
		if (isVisible) {
			$('#cardId').val($('#cacheCardId').val());
		} else {
			$('#cardId').val(null);
			//银行卡输入验证
			if($('#cardNo').hasClass('error')){
				return;
			}
		}
		
		$.post('${ctx}/u/account/mobilecode/testMobilecode${ext}',  {"type" : 8, "mobilecode" : $('#mobilecode').val()}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				$('#confirm-button').attr("disabled", "disabled");
				$('#cancel-button').attr("disabled", "disabled");
				//提交
				$('#jform').submit();
			} else {
				art.dialog.alert(data.info);
			}
		});
	}
	//获取验证码
	function getCode(el){
// 		var cell = $('#cell').val();
// 		if (null == cell || '' == cell) {
// 			art.dialog.alert('请输入手机号码');
// 			return;
// 		}
		$.post("${ctx}/u/account/mobilecode/phoneCode${ext}", {"type":8},
				  function(jsonData){
				if("false" == jsonData){
					art.dialog.alert("发送验证码失败");
				} else {
					$(el).attr("disabled",true); 
					var time=60;
					var setIntervalID=setInterval(function(){
						time--;
						$(el).html("验证码已发送 "+ time +"秒");
						if(time==0){
							clearInterval(setIntervalID);
							$(el).attr("disabled",false).html("免费获取验证码");
						}
					},1000);
				}
		});
	}
	//卡bin验证
	function getCardbin() {
		//初始化
		judgeCard.initInputCard();
		//页面验证输入
		var bankCard = $('#cardNo').val();
		if (null == bankCard || "" == bankCard) {
			return;
		}
		//卡bin验证
		$.post("${ctx}/u/account/withdraw/cardbin${ext}", {"bankCard" : bankCard}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				var info = JSON.parse(data.info);
				$('#bankCode').val(info.bankCode);
				if (2 == info.bankType) {
					judgeCard.debitcard();
				} else if (3 == info.bankType) {
					judgeCard.creditcard();
				}
				//去除错误标记
				$('#bankCard').removeClass("error");
			} else {
				//添加错误标记
				$('#bankCard').addClass("error");
				art.dialog.alert(data.info);
			}
		});
	}
	
	$("#paymentCode").focus(function() {
		if ($(this).attr("type") == "text") $(this)[0].type = "password";  
	});
</script>
</html>
