<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../../common/base.jsp" %>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		
		<link rel="stylesheet" type="text/css" href="${resourceBasePath }css/main.css">
		
		<title>${titleShopName}-支付等待</title>
		
		<style>
			.aui_content{
				min-height: 60px!important;
			}
			.aui_content .payfail-dialog{
				padding: 0 30px;
			}
		</style>
		
		<%@ include file="../../common/top.jsp"%>
		
		<script type="text/javascript">
			var jsParam = ${jsParam};
			function onBridgeReady(){
		   		WeixinJSBridge.invoke(
		       		'getBrandWCPayRequest', 
			       	jsParam,
			       	function(res){
			           	if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	   	location.href="${ctx}/pay/wechat-recharge-success${ext}?tsn=${tsn}";
			           	} else if(res.err_msg == "get_brand_wcpay_request:cancel") {
			        	   	//location.href="${ctx}/wap/${sessionShopNo}/buy/member/index${ext}";
		           			wechatNative();
			           	} else if(res.err_msg == "get_brand_wcpay_request:fail") {
			        	  	 //payFailDialog("支付失败，请重新支付");
			           		wechatNative();
			           	} else {
			        	   payFailDialog("支付失败，请重新支付");
			           }
			       }
			   ); 
			}
			if (typeof WeixinJSBridge == "undefined"){
			   	if( document.addEventListener ){
			       	document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			   	}else if (document.attachEvent){
			       	document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
			       	document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			   	}
			}else{
			   	onBridgeReady();
			}
			
			function payFailDialog(mgs){
		 	   	art.dialog({
			    	lock: true,
				    init:function(){
			    		that=this;
				    	$(".aui_titleBar").remove();
				    },
				    content: '<div class="payfail-dialog">'+mgs+'</div>',
				    ok: function () {
			    		location.href="${ctx}/wap/${sessionShopNo}/buy/member/index${ext}";
				    }
				});
			}
			//二维码支付
			function wechatNative(){
				$.ajax({
					url:'${ctx }/pay/wechatNativeRecharge${ext}',
					type:'post',
					data:{"rechargeNo":'${rechargeNo}'},
					dataType:'text',
		          	contentType: "application/x-www-form-urlencoded; charset=utf-8",
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success){
							if(!$.contains(result.info,'resource/')){
								$('#nativeUrl').attr('src','${picUrl }' + result.info);
							}else{
								$('#nativeUrl').attr('src','${ctx }' + result.info);
							}
							$('#box').show();
						}else{
							art.dialog.alert('系统异常,请稍后再试');
						}
					},
					error:function(){
						art.dialog.alert('系统繁忙');
					}
				});
			}
		</script>
		
		<style>
			html{background:#fff;}
		</style>
	</head>
	<body style="background:#fff;">
		<div id="box" style="display: none;">
			<div class="pay-wechat">
				<h2 class="clr-danger">微信扫码支付</h2>
				<h4 class="clr-light">遇到不允许跨号支付？</h4>
				<span class="pic">
					<img id="nativeUrl"/>
				</span>	
				<p>
					<i class="iconfont icon-woyaokaidian clr-light"></i>
					<span class="clr-light">
						长按图片
						<var class="clr-danger">[识别二维码]</var>
						付款
					</span>
				</p>
			
				<div class="button">
					<a href="javaScript:history.go(-1);" class="btn btn-danger btn-block">使用其他支付方式</a>
				</div>
			</div>
		</div>
	</body>
</html>
