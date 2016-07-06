<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
/* @media (max-height: 500px) { 
  #navbar,
  .invoic-default {
    display: none;
  }
} */
</style>

<%if(null==request.getParameter("delBottomNav")||!"1".equals(request.getParameter("delBottomNav"))||"true".equals(request.getAttribute("appShowBottom"))){ %>
<footer>
<div class="share-arrow hide" id="j-share" onclick="toggleShare()"></div>
<div class="m-nav-bottom m-nav-bottom-fixed" id="navbar">
  <ul>
		<li data-navicon="index" class="active"><a href="${ctx}/wap/${sessionShopNo}/any/gwscIndex${ext}"><span></span><p>首页</p></a></li>
	    <li data-navicon="cart"><a href="${ctx}/wap/${sessionShopNo}/buy/cart/find${ext}"><span></span><p>购物车</p></a></li>
    	<li data-navicon="im"><a href="javascript:toIm();"><span></span><p>聊天</p></a></li>
	    <li data-navicon="personal"><a href="${ctx}/wap/${sessionShopNo }/buy/personal/index${ext}"><span></span><p>我的</p></a></li>
  </ul>
</div>
</footer>
<%} %>

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
	//跳转聊天窗口，获取当前URL,处理商品详细页
	function toIm(){
		var url = window.location.href;
		var href = "${ctx}/wap/${sessionShopNo}/im/login${ext}";
		if(url.indexOf("/good/detail/") != -1){
			var goodId = url.substring(url.indexOf("detail/")+7, url.indexOf(".htm"));
			href += "?goodDetailId="+goodId;
		}
		window.location.href = href;
	}
</script>