<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的银行卡-添加银行卡</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
<script type="text/javascript">
	$(function() {
		var errorMsg = '${errorMsg}';
		if (null != errorMsg && '' != errorMsg) {
			cardbin($('#bankCard'));
			next();
			art.dialog.alert(errorMsg);
		}
	})
</script>
</head>
<body>
	<div id="box">
		<form action="${ctx }/free/wallet/bankcard/toadd${ext}"
			method="post" id="jform" data-form>
			<input type="hidden" name="validateToken" id="validateToken" value='${validateToken }' />
			<input type="hidden" name="bankCode" id="bankCode" /> <input
				type="hidden" name="retUrl" value="${retUrl }" /> <input
				type="hidden" name="bankType" id="bankType">
			<p class="help-block clr-light" style="margin-top: 0;">请绑定持卡人本人的银行卡</p>
			<div id="first-step">
				<div class="group group-others width60 group-cleartop">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">持卡人</li>
							<li class="group-colspan">${user.contacts }</li>
							<li class="group-colspan" onclick="showLayer('card-1')"><i
								class="iconfont icon-tishi clr-blue fnt-24"></i></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">身份证</li>
							<li class="group-colspan">${user.identity }</li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">卡号</li>
							<li class="group-colspan"><input type="text" name="bankCard"
								value="${bankCard }" onblur="cardbin(this)" id="bankCard"
								class="form-control" placeholder="请输入银行卡号" data-form-control>
							</li>
						</ul>
					</div>
				</div>
				<button type="button" id="firstButton"
					class="btn btn-danger btn-block disabled" onclick="next()">下一步</button>
			</div>
			<div id="second-step" style="display: none">
				<div class="group group-others width60 group-cleartop">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">卡类型</li>
							<li class="group-colspan"><em class="clr-blue"
								id="name-from-cardbin"></em></li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<div class="group-item" id="endTime-div" style="display: none">
						<ul class="group-rowspan">
							<li class="group-colspan">有效期</li>
							<li class="group-colspan"><input type="text" name="endTime"
								value="${endTime }" id="endTime" class="form-control ignore"
								placeholder="月份/年份" data-form-control></li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<div class="group-item width100" id="cvv-div" style="display: none">
						<ul class="group-rowspan">
							<li class="group-colspan">卡背面三位数字</li>
							<li class="group-colspan"><input type="text" name="cvv"
								value="${cvv }" id="cvv" class="form-control ignore"
								placeholder="三位数字" data-form-control></li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">手机号码</li>
							<li class="group-colspan"><input type="text" name="cell"
								value="${cell }" id="cell" onblur="check()" class="form-control"
								placeholder="请输入手机号码" data-form-control></li>
							<li class="group-colspan" onclick="showLayer('card-2')"><i
								class="iconfont icon-tishi clr-blue fnt-24"></i></li>
						</ul>
					</div>
				</div>
				<div class="list-row list-row-clearborder">
					<div class="list-col list-clearpadding">
						<div class="list-inline box-flex">
							<input type="text" id="mobilecode" name="mobilecode"
								class="form-border" onblur="check()" placeholder="请输入验证码"
								data-form-control>
						</div>
						<div class="list-inline">
							<button type="button" class="btn btn-warning btn-block" onclick="getCode(this, 7)">免费获取验证码</button>
						</div>
					</div>
				</div>
				<div class="button">
					<button id="second-button" type="button" onclick="addBankCard()"
						class="btn btn-danger btn-block disabled" data-submit>完成</button>
				</div>
			</div>
		</form>
		<!-- 弹出层  -->
		<div class="remind-box" data-show="card-1" style="display: none;">
			<div class="remind">
				<h4 class="text-center">持卡人说明</h4>
				<p>为保证账户资金安全，只能绑定认证 用户本人的银行卡。</p>
			</div>
		</div>

		<div class="remind-box" data-show="card-2" style="display: none;">
			<div class="remind">
				<h4>持卡人说明</h4>
				<p>温馨提示：银行预留的手机号码是办理该银行卡 时所填写的手机号码。没有预留、手 机号忘记或者已停用，请联系银行客
					服更新处理。</p>
			</div>
		</div>
		<%@ include file="../../../../common/foot.jsp"%>
		<%@ include file="../../../../common/freeNav.jsp" %>
	</div>
	<script type="text/javascript">
		var formvalid;
		$(function() {
			formvalid = $("#jform").validate({
				ignore : ".ignore",
				rules : {
					bankCard : {
						required : true
					},
					endTime : {
						required : true,
					},
					cvv : {
						required : true
					},
					cell : {
						required : true,
						mobile : true
					},
					mobilecode : {
						required : true,
						minlength : 6
					}
				},
				messages : {
					bankCard : {
						required : "请输入您的银行卡号"
					},
					endTime : {
						required : "请输入有效期",
					},
					cvv : {
						required : "请输入卡背面的后三位数字"
					},
					cell : {
						required : "请输入银行预留手机号码",
						mobile : "请输入正确的手机号码"
					},
					mobilecode : {
						required : "请输入验证码",
						minlength : "验证码不能小于6个字符"
					}
				}
			});
		});
		function cardbin(thiz) {
			var bankCard = $(thiz).val();
			if (null == bankCard || "" == bankCard)
				return;
			$.post("${ctx}/free/wallet/bankcard/cardbin${ext}", {
				"bankCard" : bankCard
			}, function(jsonData) {
				var data = JSON.parse(jsonData);
				if (!data.success) {
					art.dialog.alert(data.info);
				} else {
					var card = JSON.parse(data.info);
					//卡bin验证成功
					$('#bankType').val(card.bankType);
					$('#bankCode').val(card.bankCode);
					$('#name-from-cardbin').html(
							card.bankName + " " + card.bankTypeString);
					$('#firstButton').removeClass('disabled');
				}
			});
		}

		function next() {
			$('#first-step').hide();
			$('#second-step').show();
			var bankType = $('#bankType').val();
			if (3 == bankType) {
				$('#cvv').removeClass('ignore');
				$('#endTime').removeClass('ignore');
				$('#cvv-div').show();
				$('#endTime-div').show();
			}
		}

		//获取验证码
		function getCode(el, type) {
			var phone = $('#cell').val();
			if (null == phone || '' == phone) {
				art.dialog.alert("请输入手机号码");
				return;
			}
			$.post("${ctx }/free/wallet/bankcard/mobilecode${ext}",
				{
					"cell" : phone
				}, function(jsonData) {
					var data = JSON.parse(jsonData);
					if (!data.success) {
						art.dialog.alert("验证码发送失败");
					} else {
						$(el).attr("disabled", true);
						var time = 60;
						var setIntervalID = setInterval(function() {
							time--;
							$(el).html("验证码已发送 " + time + "秒");
							if (time == 0) {
								clearInterval(setIntervalID);
								$(el).attr("disabled", false).html("免费获取验证码");
							}
						}, 1000);
					}
				});
		}

		function check() {
			var mobilecode = $('#mobilecode').val();
			var cell = $('#cell').val();
			if (null != mobilecode && '' != mobilecode && null != cell
					&& '' != cell) {
				$('#second-button').removeClass('disabled');
			} else {
				$('#second-button').addClass('disabled');
			}
		}
		
		function addBankCard() {
			if (formvalid.form()) {
				$.post("${ctx }/free/wallet/bankcard/toadd${ext}", $('#jform').serialize(), function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						window.location.href=data.info;
					} else {
						art.dialog.alert(data.info);
					}
				});
			}
		}
	</script>
</body>
</html>