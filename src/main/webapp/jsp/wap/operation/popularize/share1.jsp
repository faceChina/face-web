<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>链接设置</title>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/popularize/css/main.css">
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
<div id="box">	
	<form action="javascript:;" method="post" id="jform" >
	
		<div class="share-info">
		
			<i class="iconfont icon-close fnt-24 clr-light" onclick="location.href=''"></i>
			<div class="button">
				长按以下内容链接来复制，建议将链接分享到：微信、朋友圈等。
			</div>
			<p class="fnt-16 txt-rowspan share-info-url">${shareLink}</p>
			
			<div class="button">
				<button type="button" class="btn btn-block btn-danger" onclick="share()">分享</button>
			</div>
<!-- 			<div class="button"> -->
<!-- 				<button type="button" data-type="copyurl" class="btn btn-block btn-danger">复制链接</button> -->
<!-- 			</div> -->
		</div>
		
		<div class="" style="margin-top:10px;padding:15px;">
			<h4>佣金领取方式</h4>
			<p class="clr-light" style="padding-top:10px;">当客户通过您分享的商品购买且完成交易后，平台会短信或微信模板消息方式进行提醒，使用生成推广链接的手机号码登录就可以领取佣金。</p>
		</div>
		
	</form>
	
	<!--分享求收藏-->
	<div class="recommend" style="display:none;">
		<div class="recommend-info">
			<p>1. 点击右上角的<em>● ● ●</em></p>
			<p>2. 点击<i class="iconfont icon-fenxiang" style="padding:0 0.5em;"></i><i class="iconfont icon-fenxiangpengyouquan"></i></p>
			<button type="button" class="btn btn-block btn-default" onClick="iKnow()">知道了</button>
		</div>
	</div>
	
	<%@include file="../../common/foot.jsp" %>
	<%@include file="../../common/nav.jsp" %>
	<script type="text/javascript" src="${resourcePath }js/mob-public.js"></script>
</div>

<script type="text/javascript">
$(function(){
// 	$('#jform').validate({
// 		rules:{
// 			moblie:{
// 				required:true,
// 				mobile:true
// 			}
// 		},
// 		messages:{
// 			moblie:{
// 				required:'请输入手机号码',
// 				mobile:'请输入正确的手机号码'
// 			}
// 		}
// 	});
	$("button[data-type='copyurl']").off().on("click",function(){
		copyToClipboard($(".share-info-url").text())
	   	artTip('复制成功！');
	});
});

//分享求收藏
function share(){
	$('.recommend').show();
}
function iKnow(){
	$('.recommend').hide();
}

/**
 * 自定义分享按钮
 */ 
wx.config({
      debug: false,
      appId: '${appId}',
      timestamp: 1422009542,
      nonceStr: 'rfOEfBdBznhLFkZW',
      signature:"${signature}",
      jsApiList: [
        'onMenuShareTimeline',
        'onMenuShareAppMessage'
      ]
  });
  
 wx.ready(function(){

   // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
  wx.onMenuShareAppMessage({
      title:"${shop.name}",
	  link: "${shareLink}", // 分享链接
      imgUrl: "",
      trigger: function (res) {
        //alert('用户点击发送给朋友');
      },
      success: function (res) {
        alert('已分享');
      },
      cancel: function (res) {
        //alert('已取消');
      },
      fail: function (res) {
        //alert(JSON.stringify(res));
      }
    });
   
  wx.onMenuShareTimeline({
	  title:"${shop.name}",
	  link: "${shareLink}", // 分享链接
      imgUrl: "",
    success: function () { 
        // 用户确认分享后执行的回调函数
    },
    cancel: function () { 
        // 用户取消分享后执行的回调函数
    }
	});
 });
</script>
</body>
</html>