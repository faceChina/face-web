<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的银行卡</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("bankcard");
		$('.popover-dismiss').popover({
			trigger : 'focus'
		});
		//验证码错误
		//添加银行卡切换
		judgeCard = function(){
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
				cardDom.find('[data-history="true"]').hide().find('input').addClass('ignore');
				cardDom.find('[data-new="true"]').show().find('input').removeClass('ignore');
			}
			//选择已有卡号
			function historyCard(){
				cardDom.find('[data-new="true"]').hide().find('input').addClass('ignore');
				cardDom.find('[data-history="true"]').show().find('input').removeClass('ignore');
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
				historycard: bindhistoryCard
			};
		}();
		//绑定事件
		judgeCard.setdom('#addbankcredit');
		judgeCard.addcard('#addcard');
		judgeCard.historycard('#historycard');
	});
	
	//删除银行卡
	function del(el, cardId){
		$('#paymentCode').val(null);
		//是否设置支付身份信息设置
		$.post("${ctx}/u/account/check${ext}", {}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				art.dialog({
					lock: true,
					width: '400px',
					title: '验证提示',
					background : '#000', // 背景色
					opacity : 0.1, // 透明度
					content : document.getElementById("j-payPassword"),
					button : [ {
						name : '确定',
						callback : function() {
							var flag = $('#payPassword').validate({
								rules:{
									paymentCode:{
										required: true,
									}
								},
								messages:{
									paymentCode:{
										required: '请输入支付密码'
									}
								}
							}).form();
							if(flag){
								//支付密码验证
								$.post("${ctx }/u/account/bankcard/pcck${ext}", {"paymentCode":$('#paymentCode').val()}, function(jsonData){
									var data = JSON.parse(jsonData);
									if (data.success) {
										$.post("${ctx}/u/account/bankcard/del${ext}", {"id" : cardId}, function(jsonData){
											var data = JSON.parse(jsonData);
											if (data.success) {
												$(el).parents("li").remove();
											} else {
												art.dialog.alert(data.info);
											}
										});
										return true;
									} else {
										art.dialog.alert(data.info);
										return false;
									}
								});
							}else{
								return false;
							}
						},
						focus : true
					},
					{
						name:'取消'
					}
					]

				});
			} else {
				//未设置支付密码
				art.dialog({
					title:"提示",
					content:"为了您的账户安全，请先设置支付密码。<br><a href='${ctx}/u/account/security/paymentCode${ext }?retUrl=/u/account/bankcard/list' style='color:blue'>前去设置支付密码 &gt;&gt;</a>",
					ok:true
				})
			}
		});
	}
	
	//卡bin验证
	function getCardbin() {
		var bankCard = $('#bankCard').val();
		if (null == bankCard || "" == bankCard) {
			return;
		}
		$.post("${ctx}/u/account/bankcard/cardbin${ext}", {"bankCard" : bankCard}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				var info = JSON.parse(data.info);
				$('#bankName').html(info.bankName);
				$('#bankCode').val(info.bankCode);
				$('#bankType').val(info.bankType);
				if (2 == info.bankType) {
					judgeCard.debitcard();
				} else if (3 == info.bankType) {
					judgeCard.creditcard();
				}
			} else {
				art.dialog.alert(data.info);
			}
		});
	}
	
	//设置默认银行卡
	function setDefault(el, cardId, type){
		//当前默认卡
		if($(el).parent().parent().parent().attr("class")=="defaultcard"){
			return;
		};
		art.dialog.confirm('确定设为默认？', function () {
			$.post("${ctx}/u/account/bankcard/setDefault${ext}", 
					{"cardId" : cardId, "type" : type},
					function(jsonData){
						var data = JSON.parse(jsonData);
						if (data.success) {
							$(".consumption").find("li").each(function(){
								if($(this).attr("class")=="defaultcard"){
									$(this).removeClass("defaultcard");
									$(this).find("a:last").html("设为默认");
								}
							});
							$(el).parents("li").addClass("defaultcard");
							$(el).html("默认卡号");
						} else {
							art.dialog.alert(data.info);
						}
					}
			);
		}, function () {
		   return true;
		});
	}
	//添加支付卡
	function addbankcredit(){
		$('#paymentCode').val(null);
		//是否设置支付身份信息设置
		$.post("${ctx}/u/account/check${ext}", {}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				art.dialog({
					lock: true,
					width: '400px',
					title: '验证提示',
					background : '#000', // 背景色
					opacity : 0.1, // 透明度
					content : document.getElementById("j-payPassword"),
					button : [ {
						name : '确定',
						callback : function() {
							var flag = $('#payPassword').validate({
								rules:{
									paymentCode:{
										required: true,
									}
								},
								messages:{
									paymentCode:{
										required: '请输入支付密码'
									}
								}
							}).form();
							if(flag){
								//支付密码验证
								$.post("${ctx }/u/account/bankcard/pcck${ext}", {"paymentCode":$('#paymentCode').val()}, function(jsonData){
									var data = JSON.parse(jsonData);
									if (data.success) {
										$('#validateToken').val(data.info);
										addCard();
										return true;
									} else {
										art.dialog.alert(data.info);
										return false;
									}
								});
							}else{
								return false;
							}
						},
						focus : true
					},
					{
						name:'取消'
					}
					]

				});
			} else {
				//未设置支付密码
				art.dialog({
					title:"提示",
					content:"为了您的账户安全，请先设置支付密码。<br><a href='${ctx}/u/account/security/paymentCode${ext }?retUrl=/u/account/bankcard/list' style='color:blue'>前去设置支付密码 &gt;&gt;</a>",
					ok:true
				})
			}
		});
	}
	var tj=1;
	function addCard(){
		//添加银行卡
		art.dialog({
			lock : true,
			width : '570px',
			title : "添加银行卡",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addbankcredit"),
			button : [ {
				name : '确定',
				callback : function() {
					var flag = $('#addbankcredit').validate({
						ignore: ".ignore",
						rules:{
							bankCard:{
								required:true,
							},
							endTime:{
								required:true
							},
							cvv:{
								required:true
							},                        
							cell:{
	                            required:true,
	                            mobile:true
	                        },
	                        msgCode:{
	                        	 required:true,
	                        	 minlength : 6
	                        }
						},
						messages:{
							bankCard:{
								required:'银行卡号不能为空',
							},
							endTime:{
								required:"请输入最终期限"
							},
							cvv:{
								required:"请输入卡后三位数"
							},
							cell:{
	                            required:'电话号码不能为空',
	                            mobile:'电话号码错误'
	                        },
	                        msgCode:{
	                        	 required:'验证码不能为空',
	                        	 minlength : $.format("验证码不能小于{0}个字符")
	                        }
						}
					}).form();
					if(flag&&tj==1){
						tj=0;
						$.post("/pay/lakala/sign.htm",$("#addbankcredit").serialize(),function(data){
							tj=1;
							var json=JSON.parse(data);
							if(json.retCode=='0000'){
								location.reload();
							}else{
								$.dialog.tips(json.retMsg);
							}
						});
					}
					return false;
				},
				focus : true
			},
			{
				name:'取消'
			}
			]
		});
	}
	//获取验证码
	function getCode(el){
		var phone = $('#cell').val();
		if (phone == null || phone == '') {
			art.dialog.alert("请输入手机号码");
			return;
		}
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
		$.post("/pay/lakala/getSignCode.htm",$("#addbankcredit").serialize(),function(data){
			var json=JSON.parse(data);
			if(json.retCode!='0000'){
				if(json.retCode=='1002'){
					$.dialog.tips("此卡已签约.");
					setTimeout(function(){window.location.reload()},1000);
				}else{
					$.dialog.tips(json.retMsg);
				}
				clearInterval(setIntervalID);
				$(el).attr("disabled",false).html("免费换取验证码");
			}
		});
	}
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
						<c:set var="crumbs" value="bankcard"/>
						<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li ><a data-toggle="tab">我的银行卡</a></li>
							</ul>
						</div>
						<div class="content tab-content">
							<div class="tab-pane consumption active" id="consumption">
								<ul>
								    <c:forEach items="${cardList }" var="card">
								    	<li <c:if test="${card.isDefault == 1 }">class="defaultcard"</c:if>>
											<div class="pull-left bank-card-left">
												<div class="bank-card-left-head">
													<strong>${card.bankName }</strong> 
													<small class="pull-right">${card.bankTypeString }</small>
													<c:if test="${card.isWithdrawCard }"><samll class="pull-right bank-card-sign">可提现</samll></c:if>
												</div>
												<div class="bank-card-left-center">
													<p>**** **** **** ${card.lastFourChar }</p>
													<a href="javascript:void(0)" onclick="del(this, '${card.id}')">删除</a> 
													<c:choose>
													    <c:when test="${card.isDefault == 1 }">
													    	<a href="javascript:void(0)" onclick="setDefault(this, '${card.id}')">默认卡号</a>
													    </c:when>
													    <c:otherwise>
													        <a href="javascript:void(0)" onclick="setDefault(this, '${card.id}')">设为默认</a>
													    </c:otherwise>
													</c:choose>
													<img class="pull-right" src="${resourcePath}/img/spadm/unionpay.png"
														alt="" />
												</div>
											</div>
										</li>
								    </c:forEach>
							    	<li>
										<div class="pull-left bank-card-add" onclick="addbankcredit()">
											<b>+</b> <span>添加银行卡</span>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="j-addbankcredit" style="display: none;">
		<form action="/pay/lakala/sign.htm" method="post" class="form-horizontal" role="form" id="addbankcredit">
			<input type="hidden" name="validateToken" id="validateToken"/>
			<input type="hidden" id="bankCode" name="bankCode">
		    <input type="hidden" id="bankType" name="bankType">
			<div class="form-group">
				<label class="col-sm-3 control-label">持卡人：</label>
				<div class="col-sm-4">
					<span>${user.contacts }</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">身份证号：</label>
				<div class="col-sm-4">
					<span>${user.identity }</span>
				</div>
			</div>
			<div class="form-group">
				<label for="bankcard" class="col-sm-3 control-label">银行卡卡号：</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" value="${bankCard }" id="bankCard" onblur="getCardbin()"
						name="bankCard" placeholder="">
				</div>
			</div>
			<div class="form-group" data-credit="true" data-debit="true"
				style="display: none;">
				<label for="username" class="col-sm-3 control-label">银行：</label>
			    <div class="col-sm-4">
			      <span id="bankName"></span>
			    </div>
			</div>
			<div class="form-group">
				<label for="mobile" class="col-sm-3 control-label">手机号码：</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" value="${cell }" name="cell" id="cell"
						placeholder="银行预留手机号码">
				</div>
			</div>
			<div class="form-group" data-credit="true" style="display: none;">
				<label for="years" class="col-sm-3 control-label">卡正面有效期：</label>
				<div class="col-sm-4">
					<input type="text" class="form-control ignore"  value="${endTime }" name="endTime" maxlength="4"
						id="endTime" placeholder="(MMYY)">
				</div>
				<div class="col-sm-5" style="padding-left: 10px;">
					<small class="text-muted showimg"><img
						src="${resourcePath}/img/spadm/creditcard1.png" alt=""></small>
				</div>
			</div>
			<div class="form-group" data-credit="true" style="display: none;">
				<label for="digits" class="col-sm-3 control-label ignore">卡背后三位数：</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" value="${cvv }" name="cvv" id="cvv" maxlength="3"
						placeholder="">
				</div>
				<div class="col-sm-5" style="padding-left: 10px;">
					<small class="text-muted showimg"><img
						src="${resourcePath}/img/spadm/creditcard2.png" alt=""></small>
				</div>
			</div>
			<div class="form-group">
				<label for="bankcard" class="col-sm-3 control-label">验证码：</label>
				<div class="col-sm-3">
					<input class="form-control input-short-10" type="text" value="" maxlength="6"
						id="mobilecode" name="msgCode" placeholder="">
				</div>
				<div class="col-sm-5">
					<button type="button" class="btn btn-default"
						onclick="getCode(this)">免费获取验证码</button>
				</div>
			</div>
		</form>
	</div>
	<%@include file="../../common/footer.jsp"%>
		<!-- 支付密码确认 -->
	<div id="j-payPassword" style="display:none;">
		<form action="" class="form-horizontal" id="payPassword">
			<div class="form-group">
				<p>请先输入支付密码进行验证</p>
			</div>
			 <div class="form-group">
			    <div class="col-sm-12">
			      <input type="password" class="form-control" name="paymentCode" id="paymentCode" placeholder="请输入支付密码">
			    </div>
			  </div>
		</form>
	</div>
	<script>
		$("#endTime").add('#cvv').on({
			focus: function(){
				$(event.target).closest('.form-group').find('.text-muted').show();
			},
			blur: function(){
				$(event.target).closest('.form-group').find('.text-muted').hide();
			}
		});
	</script>
</body>
</html>