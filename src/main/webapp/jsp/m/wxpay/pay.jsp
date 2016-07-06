<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../common/base.jsp" %>
</head>
<script type="text/javascript">
$(function(){
	var flag = checkWeixinVersion();
	if(!flag) {
		alert("微信支付适用于微信5.0以后的版本，请更新微信版本进行支付");
	}
});
function checkWeixinVersion() {
	var s = navigator.userAgent;

	re = /MicroMessenger\/([\d]{1})/ig;
	r = re.exec(s);

	if(r != null && r[1] <5) {
		return false;
	} else {
		return true;
	}
}

var jsParam=${jsParam};
function wxpay(){
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest'
		,jsParam
		,callback
	);
}

function callback(res){ 
	//  返回 res.err_msg,取值 
	// get_brand_wcpay_request:cancel   用户取消 
    // get_brand_wcpay_request:fail  发送失败 
    // get_brand_wcpay_request:ok 发送成功 
     WeixinJSBridge.log(res.err_msg); 	     	     				 
     if(res.err_msg=='get_brand_wcpay_request:ok')
     {
     	alert("支付成功");
     }
	else
	{
		var msg='支付失败,请重新支付.';
		alert(msg);
		 alert(res.err_code+" err_desc="+res.err_desc+" err_msg="+res.err_msg); 	
	}
};
</script>
<body>
	<div style="width: 80%;margin-left:50px;">
		<h3>微信调起支付，此页面请在微信客户端打开</h3>
		<ul>
			<li>微信支付测试</li>
			<li>支付1分钱测试</li>
			<li>预支付订单号：${prepayid }</li>
			<li>支付JS调起参数：${jsParam }</li>
		</ul>
		<button onclick="wxpay()" class="btn btn-default">点击支付</button>
	</div>
</body>
</html>