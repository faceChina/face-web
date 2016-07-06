<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>添加银行卡</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }js/mobiscroll/css/mobiscroll.scroller.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }js/mobiscroll/css/mobiscroll.widget.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }js/mobiscroll/css/mobiscroll.scroller.month.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css?ver=001">
<script type="text/javascript" src="${resourcePath }js/mobiscroll/mobiscroll.js"></script>
</head>
<body>

<div id="box" class="newBankCardBox">
	<form action="/pay/lakala/sign.htm" method="post" id="jform" data-form>
		<input type="hidden" name="orderNos" value='${orderNos }'/>
		<input type="hidden" name="bankCard" value="${bankCard }"/>
		<input type="hidden" name="type" value="${type }"/>
		<p class="help-block clr-light" style="margin-top:0;">请选择银行卡类型</p>
		<div class="group group-others width55 group-cleartop">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">卡类型</li>
					<li class="group-colspan"><em class="clr-blue"> ${bankCardVo.bankName } <c:if test="${bankCardVo.bankType==2 }">借记卡</c:if><c:if test="${bankCardVo.bankType==3 }">信用卡</c:if></em></li>
					<li class="group-colspan"></li>
				</ul>
			</div>
			<c:if test="${bankCardVo.bankType==3 }">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">有效期</li>
					<li class="group-colspan">
						<input type="text" name="endTime" readonly="readonly" class="form-control date-month" placeholder="月份/年份" data-form-control>
					</li>
					<li class="group-colspan"></li>
				</ul>
			</div>
			<div class="group-item width110">
				<ul class="group-rowspan">
					<li class="group-colspan">卡背面三位数字</li>
					<li class="group-colspan">
						<input type="text" name="cvv" class="form-control" placeholder="三位数字" maxlength="3" data-form-control>
					</li>
					<li class="group-colspan"></li>
				</ul>
			</div>
			</c:if>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">手机号</li>
					<li class="group-colspan">
						<input type="text" name="cell" id="cell" class="form-control" maxlength="11" placeholder="请输入手机号码" data-form-control>
					</li>
					<li class="group-colspan" onclick="showLayer('card')"><i class="iconfont icon-tishi clr-blue"></i></li>
				</ul>
			</div>
		</div>
		
		<div class="list-row list-row-clearborder">
			<div class="list-col list-clearpadding">
				<div class="list-inline box-flex"><input type="text" name="msgCode" maxlength="6" class="form-border input-code" placeholder="请输入手机验证码" data-form-control></div>
				<div class="list-inline">
					<button type="button" class="btn btn-warning btn-block bt-code" onclick="getSignCode(this)">获取验证码</button>
				</div>
			</div>
		</div>
		<div class="button">
			<button type="button" class="btn btn-danger btn-block" onclick="toPay(this)" data-submit>完成</button>
		</div>
		
	</form>
	
	<!-- 弹出层  -->
	<div class="remind-box" id="j-showlayer"  data-show="card" style="display:none;">
		<div class="remind">
			<h4>手机号说明</h4>
			<p>银行预留的手机号码是办理该银行卡
			时所填写的手机号码。没有预留、手
			机号码忘记或停用，请联系银行客服
			更新处理。</p>
		</div>
	</div>
	<%@ include file="../../../common/nav.jsp"%>
	<%@ include file="../../../common/foot.jsp"%>
</div>
<script type="text/javascript">
$(function(){
	if('${info}'){
		artTip('${info}');
	}
	if('${bankCardVo.bankType}'=='3'){
		bool = $("#jform").validate({
			rules:{
				endTime:{
					required:true,
					minlength:4
				},
				cvv:{
					required:true,
					minlength:3
				},
				cell:{
					required:true,
					mobile:true
				},
				msgCode:{
					required:true,
					minlength:4
				}
			},
			messages:{
				endTime:{
					required:"请输入有效期",
				},
				cvv:{
					required:"请输入卡背面的后三位数字"
				},
				cell:{
					required:"请输入银行预留手机号码",
					mobile:"请输入正确的手机号码"
				},
				msgCode:{
					required:"请输入验证码"
				}
			},
		});
	}else{
		bool = $("#jform").validate({
			rules:{
				cell:{
					required:true,
					mobile:true
				},
				msgCode:{
					required:true
				}
			},
			messages:{
				cell:{
					required:"请输入银行预留手机号码",
					mobile:"请输入正确的手机号码"
				},
				msgCode:{
					required:"请输入验证码"
				}
			},
		});
	}
})
var type='${type}';
function getSignCode(el){
	var reg=/^(13|14|15|16|17|18)\d{9}$/;
	if($("#cell").val().match(reg)==null){
		return;
	}
	$(el).attr("disabled",true);
	$(el).removeClass('btn-warning');
	var time=60;
	var setIntervalID=setInterval(function(){
		time--;
		$(el).html("验证码已发送 "+ time +"秒");
		if(time==0){
			clearInterval(setIntervalID);
			$(el).attr("disabled",false).html("免费获取验证码");
			$(el).addClass('btn-warning');
		}
	},1000);
	$.post("/pay/lakala/getSignCode.htm",$("#jform").serialize(),function(data){
		var json=JSON.parse(data);
		if(json.retCode=='1002'){
			if(type=='2'){
				artTip('此卡已签约.',function(){
					location.href='/wap/${sessionShopNo}/buy/bankcard/listV2.htm';
				})
			}else{
				artTip('此卡已签约,您可以直接付款.',function(){
					location.href='/pay/lakalaPay.htm?orderNos=${orderNos}'
				})
			}
		}else if(json.retCode=='0000'){
			artTip("验证码发送成功!");
		}else{
			artTip(json.retMsg);
		}
	});
}
function toPay(thiz){
	if(bool.form()){
		$(thiz).attr("disabled",true);
		$.post("/pay/lakala/sign.htm",$("#jform").serialize(),function(data){
			$(thiz).attr("disabled",false);
			var json=JSON.parse(data);
			if(json.retCode=='0000'){
				if(type=='2'){
					artTip('签约成功.',function(){
						location.href='/wap/${sessionShopNo}/buy/bankcard/listV2.htm';
					})
				}else{
					artTip('签约成功,去付款...');
					setTimeout(function(){location.href='/pay/lakalaPay.htm?orderNos=${orderNos}'},600);
				}
			}else{
				artTip(json.retMsg);
			}
		});
	}
}
var now = new Date();
$('.date-month').scroller($.extend({
	    preset: 'date',
	    minDate:now,
	    maxDate: new Date(now.getFullYear()+50, now.getMonth(), now.getDate()),
	    dateOrder: 'mmyy',
	    dateFormat: 'mm/y'
	},{
	    lang: 'zh'
}));
</script>
</body>
</html>

