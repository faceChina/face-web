<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
/**
 * 自定义分享按钮
 */ 
//  alert("{${icard.appId}}");
wx.config({
      debug: false,
      appId: "${icard.appId}",
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
		      title:"${icard.nickName}"+"的名片",
		      desc: '公司：'+"${icard.company}"+'\n职位：'+"${icard.position}"+'\n点击查看更多信息',
			  link: '${WgjUrl}/wap/${shopNo}/any/icard/share/${icard.id}${ext}', // 分享链接
		      imgUrl: "${picUrl}"+"${icard.headPicture}",
		      trigger: function (res) {
		        //alert('用户点击发送给朋友');
		      },
		      success: function (res) {
		        //alert('已分享');
		      },
		      cancel: function (res) {
		        //alert('已取消');
		      },
		      fail: function (res) {
		        //alert(JSON.stringify(res));
		      }
		    }); 
	   wx.onMenuShareTimeline({
		    title: "${icard.nickName}"+"的名片", // 分享标题
		    link: '${WgjUrl}/wap/${shopNo}/any/icard/share/${icard.id}${ext}', // 分享链接
		    imgUrl: "${picUrl}"+"${icard.headPicture}", // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});  
   });
</script>