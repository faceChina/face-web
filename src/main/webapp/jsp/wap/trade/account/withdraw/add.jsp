<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>提现-添加银行卡</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
</head>
<body>
	<div id="box">
		<form method="post" id="jform" data-form>
			<input type="hidden" name="validateToken" value="${validateToken }"/>
		    <input type="hidden" id="bankType" />
			<p class="help-block clr-light" style="margin-top: 0;">请绑定持卡人本人的银行卡</p>
			<div class="group group-others width60 group-cleartop">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">持卡人</li>
						<li class="group-colspan"><c:out value="${user.contacts }" /></li>
						<li class="group-colspan" onclick="showLayer('card')"><i
							class="iconfont icon-tishi clr-blue" style="font-size: 22px;"></i></li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">身份证</li>
						<li class="group-colspan"><c:out value="${user.identity }" /></li>
						<li class="group-colspan"></li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">银行</li>
						<li class="group-colspan">
							<select class="form-control" id="bankCode" name="bankCode">
							    <c:forEach items="${bankTypes }" var="bank">
							        <option value="${bank.bankCode }">${bank.bankName }</option>
							    </c:forEach>
							</select>
						</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">卡号</li>
						<li class="group-colspan"><input type="text" name="cardNo" id="cardNo"
						    onblur="cardbin($(this).val())" class="form-control" placeholder="请输入银行卡号" data-form-control></li>
					</ul>
				</div>
				<div id="ignoreGroup" style="display: none">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">年限</li>
							<li class="group-colspan"><input type="text" name="endTime" id="endTime"
							    onblur="check()"
								class="form-control ignore" placeholder="请输入年限" data-form-control></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">卡号三位数</li>
							<li class="group-colspan"><input type="text" name="cvv" id="cvv"
							    onblur="check()"
								class="form-control ignore" placeholder="请输入卡后三位数" data-form-control></li>
						</ul>
					</div>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">手机号码</li>
						<li class="group-colspan"><input type="text" name="cell" id="cell"
								class="form-control ignore" placeholder="请输入银行预留手机号码" data-form-control></li>
					</ul>
					<div class="list-col list-clearpadding">
						<div class="list-inline box-flex">
						<input type="text" name="mobilecode"  id="mobilecode" maxlength="20"
						     class="form-border" placeholder="请输入验证码" data-form-control></div>
						<div class="list-inline">
							<button type="button" class="btn btn-warning btn-block" onclick="getCode(this)">免费获取验证码</button>
						</div>
					</div>
				</div>
			</div>
			<div class="button">
				<button type="button" onclick="formSubmit()" id="button-submit" class="btn btn-danger btn-block disabled"
					data-submit>下一步</button>
			</div>
		</form>
		<div class="remind-box" data-show="card" style="display: none;">
			<div class="remind">
				<h4>持卡人说明</h4>
				<p>为保证账户资金安全，只能绑定认证 用户本人的银行卡。</p>
			</div>
		</div>
		<%@ include file="../../../common/foot.jsp"%>
		<%@ include file="../../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		var validForm = $("#jform").validate({
			ignore: ".ignore",
			rules : {
				bank : "required",
				cardNo : {
					required : true
				},
				endTime:{
					required : true
				},
				cvv : {
					required : true
				}
			},
			messages : {
				bank : "请输入开户银行名称",
				cardNo : {
					required : "请输入您的银行卡号"
				},
				endTime:{
					required : "请输入信用卡年限"
				},
				cvv : {
					required : "请输入卡后三位数"
				}
			}
		});
		//卡bin验证
		function cardbin(cardNo) {
			if (null == cardNo || '' == cardNo) return;
			$.post("${ctx}/wap/${sessionShopNo}/buy/account/withdraw/cardbin${ext}", 
					{"cardNo" : cardNo}, function(jsonData){
				var data = JSON.parse(jsonData);
				if (!data.success) {
					$('#bankType').val(null);
					$('#ignoreGroup').hide();
					art.dialog.alert(data.info);
				} else {
					var card = JSON.parse(data.info);
					var bankType = card.bankType;
					if (2 == bankType) {
						$('#ignoreGroup').hide();
						$('#endTime').addClass('ignore').removeAttr('data-form-control');
						$('#cvv').addClass('ignore').removeAttr('data-form-control');
						$('#cardNo').trigger("keyup");
					} else {
						$('#ignoreGroup').show();
						$('#endTime').removeClass('ignore');
						$('#cvv').removeClass('ignore');
					}
					$('#bankCode').val(card.bankCode);  //动态加载银行名称
					$('#bankType').val(bankType);
				}
			});
		}
		
		function check() {
			var endTime = $('#endTime').val();
			var cvv = $('#cvv').val();
			if (endTime == null || endTime == '' || cvv == null || cvv == '') {
				return ;
			}
			$('#button-submit').removeClass('disabled');
		}
		
		function getCode(el, type) {
			var phone = $('#cell').val();
			if (null == phone || '' == phone) {
				art.dialog.alert("请输入手机号码");
				return;
			}
			$.post("${ctx }/wap/${sessionShopNo}/buy/bankcard/mobilecode${ext}",
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
		
		function formSubmit() {
			if (validForm.form()) {
				$.post("${ctx}/wap/${sessionShopNo}/buy/account/withdraw/toadd${ext}", 
						$('#jform').serialize(), function(jsonData){
					console.log(jsonData);
					var data = JSON.parse(jsonData);
					if (data.success) {
						var id = data.info;
						window.location.href="${ctx}/wap/${sessionShopNo}/buy/account/withdraw/withdraw${ext}?from=add&id="+id;
					} else {
						art.dialog.alert(data.info);
					}
				}); 
			}
		}
	</script>
</body>
</html>