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
<title>提现</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }accounts/css/main.css">
<script type="text/javascript">
$(function(){
	dealErrMsg();
	var count = '${withdrawCount}';
	if (count <= 0) {
		$('#jsubmit').removeAttr('data-submit');
	}
	
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
</script>
</head>
<body>
<div id="box">
	<div id="step1">
		<form action="${ctx }/wap/${sessionShopNo}/buy/account/withdraw/toWithdraw${ext}" method="post" id="jform" data-form>
			<input type="hidden" name="validateToken" value="${validateToken }"/>
			<input type="hidden" id="cardId" name="cardId" value="${defaultCard.id }"/>
			<input type="hidden" name="paymentCode" id="paymentCode" />
			<input type="hidden" name="from" value="${from }"/>
			<div class="group group-others" <c:if test="${fn:length(cardList) >= 1}">onclick="selectCard()"</c:if> >
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan" id="j-cardinfo">
							<b>${defaultCard.bankName }</b><br/>
							<em class="clr-light">${defaultCard.abbreviation}</em>
						</li>
						<li class="group-colspan" style="cursor:pointer;"><i class="iconfont icon-right fnt-24"></i></li>
					</ul>
				</div>
			</div>
			<div class="group group-cleartop width60">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan"><b>钱包余额</b></li>
						<li class="group-colspan clr-light">
						<b class="clr-danger" id="max-price"><fmt:formatNumber value="${amount / 100 }" pattern="0.00" /></b> 元</li>
					</ul>
				</div>
			</div>
			<div class="group group-cleartop width60">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan"><b>提现金额</b></li>
						<li class="group-colspan">
							<input type="text" name="price" id="price" value="${price }"
							class="form-control" placeholder="请输入提现金额" data-form-control>
						</li>
					</ul>
				</div>
				<div class="small clr-light">温馨提示：今日您还可以提现 <b class="clr-danger">${withdrawCount}</b> 次</div>
			</div>
			<div class="list-row list-row-clearborder">
				<div class="list-col">
					<div class="list-inline">手机号码</div>
					<div class="list-inline box-flex"><c:out value="${phone }" /> </div>
				</div>
				<div class="list-col list-clearpadding">
					<div class="list-inline box-flex">
					<input type="text" name="mobilecode"  id="mobilecode" maxlength="20"
					     class="form-border" placeholder="请输入验证码" data-form-control></div>
					<div class="list-inline">
						<button type="button" class="btn btn-warning btn-block" onclick="getCode(this)">免费获取验证码</button>
					</div>
				</div>
			</div>
			<div class="button">
				<button type="button" class="btn btn-danger btn-block disabled" data-submit id="jsubmit">下一步</button>
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
	</div>

	<div id="step2">
		<div class="group group-others" id="jchangecard">
			<c:forEach items="${cardList }" var="card">
				<div class="group-item">
				    <ul class="group-rowspan">
						<li class="group-colspan">
							<input type="hidden" value="${card.id }" />
							<b>${card.bankName}</b><br/>
							<em class="clr-light">${card.abbreviation}</em>
						</li>
						<li class="group-colspan">
							<c:choose>
								<c:when test="${card.isDefault == 1 }">
									<i class="iconfont clr-danger fnt-24 icon-roundcheckfill" ></i>
								</c:when>
								<c:otherwise>
									<i class="iconfont clr-danger fnt-24" ></i>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>
			</c:forEach>
		</div>
		<div class="button">
			<a onclick="toAddCard()"
			   class="btn btn-danger btn-block" data-submit>添加银行卡</a>
		</div>
	</div>
	<%@ include file="../../../common/foot.jsp"%>
	<%@ include file="../../../common/nav.jsp"%>
</div>
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="${ctx}/wap/${sessionShopNo}/buy/account/withdraw/add${ext}" id="paymentcode-form" class="form-login">
		<input type="hidden" name="retUrl" value="/wap/${sessionShopNo}/buy/account/withdraw/withdraw">
		<input type="hidden" name="id" value="${defaultCard.id }"/>
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
$('#jsubmit').click(function(){
	var bool = $('#jform').validate({
		rules:{
			price:{
				required:true,
				moneyNotZero: true,
				lessTo: '#max-price'
			},
			mobilecode:{
				required:true,
				minlength : 6
			}
		},
		messages:{
			price:{
				required:'请输入提现金额',
				moneyNotZero:"金额必须大于0.00，保留两位小数",
				lessTo:'可用余额不足'
			},
			mobilecode:{
				required:'请输入验证码',
				minlength : "密码不能小于6个字符"
			}
		}
	}).form();
		
	if(bool){
		$.post("${ctx }/wap/${sessionShopNo}/buy/mobilecode/testMobilecode${ext}", 
				{"type" : 8, "mobilecode" : $('#mobilecode').val()}, function(jsonData){
			var data = JSON.parse(jsonData);
			if (data.success) {
				$('#show-price').html($('#price').val()+"元");
				art.dialog({
					lock: true,
					width: '100%',
					title:'确定支付',
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
});	
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
	
//选择银行卡
$('#jchangecard .group-item').click(function(){
	$('#jchangecard .group-item .iconfont').removeClass('icon-roundcheckfill');
	$(this).find('.iconfont').addClass('icon-roundcheckfill');
	$('#step1').animate({
		'left':'0%'
	},300,function(){
		$(this).css('position','relative');
	});
	$('#step2').animate({
		'left':'-100%'
	},300,function(){
		$(this).css('position','absolute');
	});
	var cardname=$(this).find('.group-colspan b').text();
	var cardcode=$(this).find('.group-colspan em').text();
	var cardId=$(this).find('.group-colspan input').val();
	$('#j-cardinfo b').text(cardname);
	$('#j-cardinfo em').text(cardcode);
	$('#cardId').val(cardId);
})

//选择银行卡的页面的切换
function selectCard(){
	$('#step1').animate({
		'left':'-100%'
	},300,function(){
		$(this).css('position','absolute');
	});
	$('#step2').show().animate({
		'left':'0%'
	},300,function(){
		$(this).css('position','relative');
	});
}
function toAddCard(){
	$('#paymentCode').val(null);
	//添加银行卡信息
	artDialogComfirm('paymentcode-form',function(){
	 var bool = $("#paymentcode-form").validate({
                   rules : {
                       paymentCode : {
                           required : true
                       }
                   },
                   messages : {
                   	paymentCode : {
                           required : "请输入支付密码"
                       }
                   }
               }).form();
               if(bool){
                   //自定义操作
                   $('#paymentcode-form').submit();
                  return true;
               }
               return false;
	});
}
</script>
</body>
</html>