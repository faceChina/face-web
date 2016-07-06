<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(function() {
	$.post("${ctx}/u/crumbs/init${ext}", function(data) {
		
		if("user"==data){
			$("#user-crumbs").show();
		}
		if("sc"==data){
			$("#sc-crumbs").show();	
		}
		if("gw"==data){
			$("#gw-crumbs").show();
		}
		
	});
});

</script>
<style>
.m-breadcrumb>li+li:before {
padding: 0 5px;
color: #ccc;
content: ">\00a0";
}
</style>
<div class="row">
	<div class="m-crumbs">
		<h4 ></h4>
	<ul id="user-crumbs" class="m-breadcrumb" style="display:none;">
	<c:choose>
		<c:when test="${crumbs=='pingtai' }"><li>刷脸平台</li><li>首页</li></c:when>
		<c:when test="${crumbs=='account' }"><li>刷脸平台</li><li>账户中心</li><li>我的钱包</li></c:when>
		<c:when test="${crumbs=='bankcard' }"><li>刷脸平台</li><li>账户中心</li><li>我的银行卡</li></c:when>
		<c:when test="${crumbs=='information' }"><li>刷脸平台</li><li>账户中心</li><li>基本资料</li></c:when>
		<c:when test="${crumbs=='security' }"><li>刷脸平台</li><li>账户中心</li><li>安全设置</li></c:when>
		
		<c:when test="${crumbs=='member-manage' }"><li>刷脸平台</li><li>会员管理</li><li>会员管理</li></c:when>
		<c:when test="${crumbs=='recharge-manage' }"><li>刷脸平台</li><li>会员管理</li><li>会员充值管理</li></c:when>
		<c:when test="${crumbs=='integral-setting' }"><li>刷脸平台</li><li>会员管理</li><li>积分设置</li></c:when>
		<c:when test="${crumbs=='member-set' }"><li>刷脸平台</li><li>会员管理</li><li>会员等级设置</li></c:when>
		<c:when test="${crumbs=='member' }"><li>刷脸平台</li><li>会员管理</li><li>会员卡设置</li></c:when>
	</c:choose>
	</ul>
	
	
	<ul id="gw-crumbs" class="m-breadcrumb" style="display:none;">
	<c:choose>
		<c:when test="${crumbs=='gwindex' }"><li>刷脸平台</li><li>官网</li></c:when>
		<c:when test="${crumbs=='information' }"><li>刷脸平台</li><li>账户中心</li><li>基本资料</li></c:when>
		<c:when test="${crumbs=='shopInout' }"><li>刷脸平台</li><li>账户管理</li><li>店铺收支</li></c:when>
		<c:when test="${crumbs=='gwtemplate' }"><li>刷脸平台</li><li>官网管理</li><li>官网模板管理</li></c:when>
		<c:when test="${crumbs=='homepagemodular'}"><li>刷脸平台</li><li>官网管理</li><li>首页模块设定</li></c:when>		
		<c:when test="${crumbs=='gwcarousel' }"><li>刷脸平台</li><li>官网管理</li><li>官网轮播图管理</li></c:when>
		<c:when test="${crumbs=='article' }"><li>刷脸平台</li><li>素材管理</li><li>文章管理</li></c:when>
		<c:when test="${crumbs=='albumn' }"><li>刷脸平台</li><li>素材管理</li><li>相册管理</li></c:when>
		<c:when test="${crumbs=='sctemplate' }"><li>刷脸平台</li><li>商城管理</li><li>商城模板管理</li></c:when>
		<c:when test="${crumbs=='scmodular' }"><li>刷脸平台</li><li>商城管理</li><li>首页模块设定</li></c:when>
		<c:when test="${crumbs=='sccarousel' }"><li>刷脸平台</li><li>商城管理</li><li>商城轮播图管理</li></c:when>
		<c:when test="${crumbs=='lbs' }"><li>刷脸平台</li><li>店铺管理</li><li>店铺位置</li></c:when>
		<c:when test="${crumbs=='logistics' }"><li>刷脸平台</li><li>店铺管理</li><li>配送方式设置</li></c:when>
		<c:when test="${crumbs=='goodtype'}"><li>刷脸平台</li><li>商品管理</li><li>商品分类</li></c:when>
		<c:when test="${crumbs=='goodlist' }"><li>刷脸平台</li><li>商品管理</li><li>商品列表</li></c:when>
		<c:when test="${crumbs=='goodtj' }"><li>刷脸平台</li><li>商品管理</li><li>商品推荐管理</li></c:when>
		<c:when test="${crumbs=='ordershop' }"><li>刷脸平台</li><li>店铺管理</li><li>店铺订单</li></c:when>
		<c:when test="${crumbs=='appointSet' }"><li>刷脸平台</li><li>预约管理</li><li>预约设置</li></c:when>
		<c:when test="${crumbs=='appointGoodType' }"><li>刷脸平台</li><li>预约管理</li><li>商品分类</li></c:when>
		<c:when test="${crumbs=='appointGoodList' }"><li>刷脸平台</li><li>预约管理</li><li>商品列表</li></c:when>
		<c:when test="${crumbs=='appointOrder' }"><li>刷脸平台</li><li>预约管理</li><li>预约订单</li></c:when>
		<c:when test="${crumbs=='message' }"><li>刷脸平台</li><li>基础设置</li><li>回复管理</li></c:when>
		<c:when test="${crumbs=='custommenu' }"><li>刷脸平台</li><li>基础设置</li><li>自定义菜单</li></c:when>
		<c:when test="${crumbs=='templatemessage' }"><li>刷脸平台</li><li>基础设置</li><li>模板消息设置</li></c:when>
	</c:choose>
	</ul>
	
	
	<ul id="sc-crumbs" class="m-breadcrumb" style="display:none;">
	<c:choose>
		<c:when test="${crumbs=='scindex' }"><li>刷脸平台</li><li>商网</li></c:when>
		<c:when test="${crumbs=='information' }"><li>刷脸平台</li><li>账户中心</li><li>基本资料</li></c:when>
		<c:when test="${crumbs=='shopInout' }"><li>刷脸平台</li><li>账户管理</li><li>店铺收支</li></c:when>
		<c:when test="${crumbs=='sctemplate' }"><li>刷脸平台</li><li>商城管理</li><li>商城模板管理</li></c:when>
		<c:when test="${crumbs=='scmodular' }"><li>刷脸平台</li><li>商城管理</li><li>首页模块设定</li></c:when>
		<c:when test="${crumbs=='gwcarousel' }"><li>刷脸平台</li><li>商城管理</li><li>商城轮播图管理</li></c:when>
		<c:when test="${crumbs=='lbs' }"><li>刷脸平台</li><li>店铺管理</li><li>店铺位置</li></c:when>
		<c:when test="${crumbs=='logistics' }"><li>刷脸平台</li><li>店铺管理</li><li>配送方式设置</li></c:when>
		<c:when test="${crumbs=='about' }"><li>刷脸平台</li><li>店铺管理</li><li>店铺简介</li></c:when>
		<c:when test="${crumbs=='goodtype'}"><li>刷脸平台</li><li>商品管理</li><li>商品分类</li></c:when>
		<c:when test="${crumbs=='goodlist' }"><li>刷脸平台</li><li>商品管理</li><li>商品列表</li></c:when>
		<c:when test="${crumbs=='goodtj' }"><li>刷脸平台</li><li>商品管理</li><li>商品推荐管理</li></c:when>
		<c:when test="${crumbs=='ordershop' }"><li>刷脸平台</li><li>店铺管理</li><li>店铺订单</li></c:when>
		<c:when test="${crumbs=='appointSet' }"><li>刷脸平台</li><li>预约管理</li><li>预约设置</li></c:when>
		<c:when test="${crumbs=='appointGoodType' }"><li>刷脸平台</li><li>预约管理</li><li>商品分类</li></c:when>
		<c:when test="${crumbs=='appointGoodList' }"><li>刷脸平台</li><li>预约管理</li><li>商品列表</li></c:when>
		<c:when test="${crumbs=='appointOrder' }"><li>刷脸平台</li><li>预约管理</li><li>预约订单</li></c:when>
		<c:when test="${crumbs=='proxyOrder' }"><li>刷脸平台</li><li>代理分销管理</li><li>代理订单</li></c:when>
		<c:when test="${crumbs=='proxyGoodSource' }"><li>刷脸平台</li><li>代理分销管理</li><li>货源管理</li></c:when>
		<c:when test="${crumbs=='proxyGoodList' }"><li>刷脸平台</li><li>代理分销管理</li><li>代理商品列表</li></c:when>
		<c:when test="${crumbs=='proxyGeneralize' }"><li>刷脸平台</li><li>代理分销管理</li><li>代理商品推广管理</li></c:when>
		<c:when test="${crumbs=='proxySupplierManage' }"><li>刷脸平台</li><li>代理分销管理</li><li>供货商管理</li></c:when>
		<c:when test="${crumbs=='message' }"><li>刷脸平台</li><li>基础设置</li><li>回复管理</li></c:when>
		<c:when test="${crumbs=='custommenu' }"><li>刷脸平台</li><li>基础设置</li><li>自定义菜单</li></c:when>
		<c:when test="${crumbs=='templatemessage' }"><li>刷脸平台</li><li>基础设置</li><li>模板消息设置</li></c:when>
	</c:choose>
	</ul>
	</div>
</div>


