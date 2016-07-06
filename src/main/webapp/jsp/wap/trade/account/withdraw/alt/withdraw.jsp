<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>提现</title>
<%@ include file="../../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css">
</head>
<body>
<div id="box">
	<form action="${ctx }/wap/${sessionShopNo }/buy/account/withdraw/toWithdraw${ext}" method="post" id="jform" data-form>
	    <input type="hidden" name="validateToken" value="${validateToken }"/>
	    <input type="hidden" id="max-price" value='<fmt:formatNumber value="${amount / 100 }" pattern="0.00" />'>
	    <input type="hidden" name="paymentCode" id="paymentCode" />
<!-- 	    <input type="hidden" id="bankCode" name="bankCode"> -->
		<p class="help-block clr-light" style="margin-top:0;">请绑定持卡人本人的银行卡</p>
		<div class="group group-others width60 group-cleartop">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">持卡人</li>
					<li class="group-colspan">${user.contacts }</li>
					<li class="group-colspan" onclick="showLayer('card')"><i class="iconfont icon-tishi clr-blue"  style="font-size:22px;"></i></li>
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
					<li class="group-colspan">银行</li>
					<li class="group-colspan">
						<select class="form-control" name="bankCode">
							<option value=''>请选择提现银行卡开户行名称</option>
							<c:forEach items="${bankList }" var="bank">
								<option <c:if test="${bankCode == bank.bankCode }">selected="selected"</c:if> value="${bank.bankCode }">${bank.bankName }</option>
							</c:forEach>
						</select>
					</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">卡号</li>
					<li class="group-colspan"><input type="text" value="${bankCard }" name="bankCard" id="bankCard" class="form-control" placeholder="提现只能填写储蓄卡卡号" data-form-control></li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">提现金额</li>
					<li class="group-colspan"><input type="text" name="price" id="price" class="form-control" placeholder='当前钱包余额为  <fmt:formatNumber value="${amount / 100 }" pattern="0.00" /> 元' value="${price }"  data-form-control></li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">手机号码</li>
					<li class="group-colspan">${user.cell }</li>
					<li class="group-colspan"></li>
				</ul>
			</div>
			<div class="list-row list-row-clearborder">
				<div class="list-col list-clearpadding">
					<div class="list-inline box-flex"><input type="text" id="mobilecode" name="mobilecode" class="form-border" placeholder="请输入验证码" data-form-control></div>
					<div class="list-inline">
						<button class="btn btn-warning btn-block" onclick="getCode(this)">免费获取验证码</button>
					</div>
				</div>
			</div>
		</div>
		<div class="button">
			<button type="button" class="btn btn-danger btn-block disabled" id="jsubmit" data-submit >下一步</button>
		</div>
	</form>
	<!-- 确定转出 -->
		<div id="j-take" style="display:none;">
			<form action="javascript:;" id="j-paypassword">
				<h4 id="show-price" class="clr-danger" style="text-align:center;"></h4>
				<div class="list-col list-clearpadding">
					<div class="list-inline box-flex">
						<input type="password" class="form-border"
						 onchange="$('#paymentCode').val($(this).val())"
						 id="password" name="password" placeholder="支付密码">
					</div>
				</div>
			</form>
		</div>
	
	<!-- 弹出层  -->
	<div class="remind-box" data-show="card" style="display:none;">
		<div class="remind">
			<h4>持卡人说明</h4>
			<p>为保证账户资金安全，只能绑定认证 用户本人的银行卡。</p>
		</div>
	</div>
	<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/nav.jsp"%>
</div>
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="${ctx}/wap/${sessionShopNo}/buy/account/withdraw/add${ext}" id="paymentcode-form" class="form-login">
		<input type="hidden" name="retUrl" value="/wap/${sessionShopNo}/buy/account/withdraw/withdraw">
		<input type="hidden" name="price" value="${price }">
  		<div class="list-row list-row-clearborder " style="margin:0;">
  			<div class="capacity">请输入支付密码进行验证</div>
    		<div class="list-col">
      			<div class="list-inline"><input type="password" name="paymentCode" id="paymentCode" class="form-border" placeholder="支付密码"></div>
    		</div>
		</div>
	</form>
</div>
<script type="text/javascript">
var validateForm;
$(function(){
	dealErrMsg();
	var count = '${withdrawCount}';
	if (count <= 0) {
		$('#jsubmit').removeAttr('data-submit');
	}
	
	validateForm = $("#jform").validate({
		rules:{
			bankCode:{
				required:true
			},
			bankCard:{
				required:true,
				maxlength:19,
				stringMinLength:16
			},
			mobilecode:{
				required:true
			},
			price:{
				required:true,
				moneyNotZero: true,
				lessTo: '#max-price'
			}
		},
		messages:{
			bankCode:{
				required:"请选择开户银行"
			},
			bankCard:{
				required:"请输入您的银行卡号",
				maxlength:"银行卡号长度不能超过19位",
				stringMinLength:"银行卡号长度不能小于16位"
			},
			mobilecode:{
				required:'请输入手机验证码'
			},
			price:{
				required:'请输入提现金额',
				moneyNotZero:"金额必须大于0.00，保留两位小数",
				lessTo:'可用余额不足'
			}
		}
	});
})
 
 //错误信息处理
	function dealErrMsg() {
		var errMsg = '${errorMsg}';
		if (null != errMsg && '' != errMsg) {
			if (errMsg == '支付密码错误，请重试') {
				selectCard();
				toAddCard();
			}
			artTip(errMsg);
		}
	}
$('#jsubmit').click(function(){
	if(validateForm.form()){
		$.post("${ctx }/wap/${sessionShopNo}/buy/mobilecode/testMobilecode${ext}", 
				{"type" : 8, "mobilecode" : $('#mobilecode').val()}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {  //TODO
				$('#show-price').html($('#price').val()+"元");
				art.dialog({
					lock: true,
					width: '100%',
					title:'确定提现',
				    content:document.getElementById("j-take"),
				    ok: function () {	
				         var bool=$('#j-paypassword').validate({
							rules:{
								password:{
									required:true
								}
							},
							messages:{
								password:{
									required:'请输入支付密码'
								}
							}
						}).form();
						if(bool){
							$('#jsubmit').addClass("disabled");
							$(".j-loading").show();
							$('#jform').submit();
						}else{
							return false;
						}
				    },
				    cancel: true
				});
			} else {
				art.dialog.alert("验证码输入错误");
			}
		});
	}
})
//获取验证码
function getCode(el){
	$.post("${ctx }/wap/${sessionShopNo}/buy/mobilecode/phoneCode${ext}",
			{"type" : 8}, function(falg){
		if("true" != falg){
			art.dialog.alert("获取验证码失败");
		}
	});
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
</script>
</body>
</html>