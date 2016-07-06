<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的银行卡</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/wallet/css/main.css">
</head>
<body>

<div id="box">
	<div class="tab">
			<form method="get" id="bankCardForm">
				<input type="hidden" name="type" id="type" />
				<ul class="tab-handle">
					<li <c:if test="${type == 2 }">class="active"</c:if>><a
						href="#" onclick="selectCardList(2)" data-toggle="tab">提现卡</a></li>
					<li <c:if test="${type == 1 }">class="active"</c:if>><a
						href="#" onclick="selectCardList(1)" data-toggle="tab">支付卡</a></li>
				</ul>
			</form>
			<div class="tab-content">
				<div class="tab-pane active">
					<!-- 银行卡信息  -->
					<div class="group group-justify">
						<c:forEach items="${cardList }" var="card">
							<div class="group-item">
								<ul class="group-rowspan">
									<li class="group-colspan"><em>${card.name }</em><br /> <em
										class="clr-light fnt-12">${card.abbreviation }</em></li>
									<li class="group-colspan" onclick="toDelete(this, '${card.id}')"
										style="cursor: pointer;"><i
										class="iconfont icon-delete clr-light fnt-24"></i></li>
								</ul>
							</div>
						</c:forEach>
					</div>
					<!-- 添加银行卡按钮  -->
					<c:if test="${type == 1 }">
						<div class="button">
							<a onclick="toAddCard()"
								class="btn btn-default btn-block"><i
								class="iconfont icon-add"></i> 添加银行卡</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	
	<script type="text/javascript" src="../../public/page/foot.js"></script>
	<%@ include file="../../../../common/freeNav.jsp" %>
	
</div>
</body>
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="/free/wallet/bankcard/add${ext}" id="paymentcode-form" class="form-login">
		<input type="hidden" name="retUrl" value="${ctx }/free/wallet/bankcard/list${ext }">
  		<div class="list-row list-row-clearborder " style="margin:0;">
  			<div class="capacity">请输入支付密码进行验证</div>
    		<div class="list-col">
      			<div class="list-inline"><input type="password" name="paymentCode" id="paymentCode" class="form-border" placeholder="支付密码"></div>
    		</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
	var errorMsg = '${errorMsg}';
	console.log(errorMsg);
	if (null != errorMsg && '' != errorMsg) {
		toAddCard();
		artTip(errorMsg);
	}
})
navbar("purse");
function selectCardList(type) {
	$('#type').val(type);
	$('#bankCardForm').submit();
}
//删除银行卡信息
function toDelete(thiz, id){
	art.dialog({
		width:'100%',
		title:'提示',
		content:'确定删除？',
		ok:function(){
			$.post("/free/wallet/bankcard/del.htm", {"id" : id}, function(jsonData){
				var data = JSON.parse(jsonData);
				if (data.success) {
					$(thiz).parents(".group-rowspan").remove();
				} else {
					art.dialog.alert(data.info);
				}
			});
		},
		cancel:true
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
         }
	});
}
</script>
</html>