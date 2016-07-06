<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>商品详情</title>
<%@include file="../top.jsp" %>
<script type="text/javascript" src="${resourceBasePath}js/slidefocus-jquery.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
/**
 * 自定义分享按钮
 */ 
wx.config({
      debug: false,
      appId: "${appId}",
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
		      title:"${goodDetailVo.good.name}",
		      desc: '商品名称：'+"${goodDetailVo.good.name}",
			  link: '${WgjUrl}/wap/${goodDetailVo.good.shopNo}/any/item/${goodDetailVo.good.id}${ext}', // 分享链接
		      imgUrl: "${picUrl}"+"${goodDetailVo.good.picUrl}",
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
		    title: '商品名称：'+"${goodDetailVo.good.name}", // 分享标题
		    link: '${WgjUrl}/wap/${goodDetailVo.good.shopNo}/any/item/${goodDetailVo.good.id}${ext}', // 分享链接
		    imgUrl: "${picUrl}"+"${goodDetailVo.good.picUrl}", // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
	    
   
   });
</script>
</head>
<body>
<div class="p-detailshop">
	<div id="slider">
		<ul>
			<c:forEach items="${goodDetailVo.goodImgs }" var="data">
				<li><a href="javascript:;"><img src="${picUrl }${data.zoomUrl}" alt=""></a></li>
			</c:forEach>
		</ul>
	</div>

	<div class="detail-shopdetail">
		<ul>
			<li>${goodDetailVo.good.name}</li>
			<li class="hint remind-clr" id="j-price"><fmt:formatNumber value="${goodDetailVo.good.salesPrice/100 }" pattern="0.00" />-<fmt:formatNumber value="${goodDetailVo.good.marketPrice/100 }" pattern="0.00" />元</li>
			<li><span>库存：${goodDetailVo.good.inventory }件</span></li>
		</ul>
	</div>
	<div class="m-shop-info" back-odd="true">
		<c:forEach items="${goodDetailVo.skuList }" var="data">
			<div class="m-shop-item">
				<em class="shop-title">属性：</em>
				<span class="shop-txt">${data.skuPropertiesName }</span>
			</div>
			<div class="m-shop-item">
				<em class="shop-title"></em>
				<span class="shop-txt">价格：<fmt:formatNumber value="${data.salesPrice/100 }" pattern="0.00" />元 </span>
				<span class="shop-txt"> 库存：${data.stock }</span>
			</div>
		</c:forEach>
	</div>

	<div class="m-shop-info">
		<div class="m-shop-item" border-none="true">
			<em class="m-shop-title">商品详情</em>
		</div>
		<div class="m-shop-txt">
			<p class="color-gray">
				${goodDetailVo.good.goodContent }
			</p>
		</div>
	</div>
	
</div>

<div class="m-edit-active">
	<ul class="m-edit-info">
		<li class="m-edit-item"><a href="${ctx}/free/good/edit/${goodDetailVo.good.id}${ext}">编辑</a></li>
		<c:choose>
			<c:when test="${goodDetailVo.good.status==1}">
				<li class="m-edit-item"><a href="javascript:downGood();">下架</a></li>
			</c:when>
			<c:when test="${goodDetailVo.good.status==3}">
				<li class="m-edit-item"><a href="javascript:upGood();">上架</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<li class="m-edit-item"><a href="javascript:removeGood();">删除</a></li>
		<li class="m-edit-item" data-type="share"><span>分享</span></li>
	</ul>
</div>

<!-- 分享 -->
<div class="recommend" data-share="true" style="display:none;">
	<div class="recommend-info">
		<h4>分享给小伙伴，再也不担心小伙伴找不到我了！</h4>
		<p>1. 点击右上角的<em>● ● ●</em></p>
		<p>2. 点击 发送给朋友 或 分享到朋友圈</p>
		<button type="button" class="btn btn-block btn-default" data-share="colse" >知道了</button>
	</div>
</div>
<!--分享 end-->

<script>
		//轮播图
	$.slidefocus({
			dom:"slider",	 //元素id，无“#”字符
			width:"100%",
			height:"100%",
			direction:"left",		 //上(up)右(right)下(down)左(left)
			play:true, 				 //自动播放(true/false),和direction、time组合使用
			time:"3000", 			 //时间间隔(单位毫秒)
			page:true				//是否显示页码
	});

	//分享 收藏
	var shareFun = (function(){
		var latoutEl = $('[data-share="true"]');

		$('[data-type="share"]').on('click',function(){
			latoutEl.show();
		})

		$('[data-share="colse"]').on('click',function(){
			latoutEl.hide();
		})

	})();
	
	function upGood() {
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定上架商品？",
			button : [ {
				name : '确定',
				callback : function() {
					window.location.href = "${ctx}/free/good/upShelvesGood/${goodDetailVo.good.id}${ext}";
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
	
	function downGood() {
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定下架商品？",
			button : [ {
				name : '确定',
				callback : function() {
					window.location.href = "${ctx}/free/good/downShelvesGood/${goodDetailVo.good.id}${ext}";
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
	
	function removeGood() {
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定删除商品？",
			button : [ {
				name : '确定',
				callback : function() {
					window.location.href = "${ctx}/free/good/remove/${goodDetailVo.good.id}${ext}";
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
</script>
</body>
</html>