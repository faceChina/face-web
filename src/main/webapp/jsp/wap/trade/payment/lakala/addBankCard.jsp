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
<link rel="stylesheet" type="text/css" href="${resourcePath }accounts/css/main.css?ver=001">
</head>
<body>

<div id="box" class="newBankCardBox add-newbancarbox">
	<form action="/pay/lakala/addBankCard.htm" method="post" id="jform" data-form>
		<input type="hidden" name="orderNos" value='${orderNos }'/>
		<input type="hidden" name="type" value='${type }'/>
		<p class="help-block clr-light" style="margin-top:0;">请绑定持卡人本人的银行卡</p>
		
		<div class="group group-others  group-cleartop boder-t-line">
			<div class="group-item width55">
				<ul class="group-rowspan">
					<li class="group-colspan">持卡人</li>
					<li class="group-colspan">${name }</span></li>
					<li class="group-colspan group-colspan-selfcart" ><i onclick="showLayer('card-people')" class="iconfont icon-tishi clr-blue"></i></li>
				</ul>
			</div>
			<div class="group-item width55">
				<ul class="group-rowspan">
					<li class="group-colspan">身份证</li>
					<li class="group-colspan">${identity }</li>
					<li class="group-colspan"></li>
				</ul>
			</div>

			<div class="group-item width40">
				<ul class="group-rowspan">
					<li class="group-colspan">卡号</li>
					<li class="group-colspan">
						<input type="number" name="bankCard" id="card" value="${bankCard }" class="form-control" placeholder="请输入银行卡号" data-form-control><i onclick="showLayer('card')" class="iconfont icon-tishi clr-blue"></i>
					</li>
				</ul>
			</div>
		</div>
		<div id="card-description"></div>
			
		<div class="button">
			<button type="button" class="btn btn-danger btn-block disabled" data-submit onclick="toPay(this)">下一步</button>
		</div>
		
	</form>
	
	<!-- 弹出层  -->
	<div class="remind-box" id="j-showlayer" data-show="card-people" style="display:none;">
		<div class="remind">
			<h4 class="text-center">持卡人说明 </h4>
			<p>为保证账户资金安全，只能绑定认证 用户本人的银行卡。</p>
		</div>
	</div>
	<!-- 弹出层  -->
	<div class="remind-box" id="j-showlayer" data-show="card" style="display:none;">
		<div class="remind">
			<h4 class="text-center">支付：支付可用储蓄卡和信用卡 </h4>
			<p>提现支持：中国工商银行、农业银行
				、建设银行、交通银行、邮政储蓄银
				行、中信银行、光大银行、兴业银行
				、平安银行、浦发银行、北京银行、
				上海银行。
			</p>
		</div>
	</div>
	<%@ include file="../../../common/nav.jsp"%>
	<%@ include file="../../../common/foot.jsp"%>
</div>
<script type="text/javascript">
$(function(){
	if('${sign}'=='1'){
		$.dialog.alert('此卡已签约,您可以直接付款.',function(){
			location.href='/pay/lakalaPay.htm?orderNos=${orderNos}'
		});
	}
})
function toPay(thiz){
	$(thiz).attr("disabled",true);
	$.post("/pay/lakala/checkBankCard.htm",$("#jform").serialize(),function(data){
		$(thiz).attr("disabled",false);
		var json=JSON.parse(data);
		if(json.success){
			$("#jform").submit();
		}else{
			artTip('不支持的银行卡类型.')
		}
	})
}
</script>
</body>
</html>

