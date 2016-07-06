<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
/* @media (max-height: 500px) { 
  #navbar,
  .invoic-default {
    display: none;
  }
} */
</style>


<div class="share-arrow hide" id="j-share" onclick="toggleShare()"></div>
<div class="m-nav-bottom" id="navbar">
  <ul>
    <li data-navicon="index" class="active"><a href="/free/index.htm"><span></span><p>首页</p></a></li>
    <li data-navicon="order"><a href="/free/order/index.htm"><span></span><p>订单</p></a></li>
    <li data-navicon="goods"><a href="/free/good/list.htm"><span></span><p>商品</p></a></li>
    <li data-navicon="purse"><a href="/free/wallet/index.htm"><span></span><p>钱包</p></a></li>
    <li data-navicon="set"><a href="/free/mine/index.htm"><span></span><p>我的</p></a></li>
  </ul>
</div>


<script type="text/javascript">
	//切换分享显示层
	function toggleShare(){
		$("#j-share").toggleClass("show");
	}

	//导航背景定位
	function navbar(str){
		$("#navbar").find("[data-navicon]").removeClass("active");
		$("[data-navicon='"+str+"']").addClass("active");
	};
	
	//跳转聊天窗口，获取当前URL,处理商品详细页
	function toIm(){
		var url = window.location.href;
		var href = "${ctx}/app/${sessionShopNo}/im/login${ext}";
		if(url.indexOf("/good/detail/") != -1){
			var goodId = url.substring(url.indexOf("detail/")+7, url.indexOf(".htm"));
			href += "?goodDetailId="+goodId;
		}
		window.location.href = href;
	}
</script>