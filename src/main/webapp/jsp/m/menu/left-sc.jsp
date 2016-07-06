<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="sidebar-go"><a href="${ctx}/u/returnIndex${ext}">返回刷脸平台</a></div>
<ul class="sidebar j-toggleCont nav-shopname">
	<li tabindex="index"><a href="javascript:void(0)" data-nano title="${shopName }" > <span class="arrow"></span>${shopName }</a>
		<ul>
			<c:forEach items="${shopList }" var="shop">
				<li><a href="javascript:intoShop('${shop.no }')">${shop.name }</a></li>
			</c:forEach>
		</ul>
	</li>
</ul>
<ul class="sidebar" id="j-toggleCont">
	<li><a href="javascript:void(0)"> <span class="arrow"></span> 帐户中心</a>
		<ul>
			<li  tabindex="accounts"><a href="${ctx }/u/account/shopAccount${ext}">基本资料</a></li>
			<li  tabindex="purse"><a href="${ctx}/u/account/shopInout${ext}"> 店铺收支</a></li>
		</ul>
	</li>
	
	<li><a href="javascript:void(0)"> <span class="arrow"></span> 商城装修</a>
		<ul>
			<li  tabindex="template"><a href="${ctx}/u/template/WSC/listOwTemplate${ext}">模板管理</a></li>
			<c:if test="${owTemplateHp.isModular == 1 }">
			<li  tabindex="modular"><a href="${ctx}/u/template/WSC/listModular${ext}">首页模块设定</a></li>
			</c:if>
			<c:if test="${owTemplateHp.isCarlOrBg != 0 }">
				<li  tabindex="carousel"><a href="${ctx}/u/template/WSC/carousel${ext}">商城轮播图管理</a></li>
			</c:if>
		</ul>
	</li>
	
	<li><a href="javascript:void(0)"> <span class="arrow"></span> 店铺管理</a>
		<ul>
			<li  tabindex="basic-set"><a href="${ctx}/u/lbs/index${ext}">店铺位置</a></li>
			<li  tabindex="shop-about"><a href="${ctx}/u/shop/about${ext}">店铺简介</a></li>
			<li  tabindex="tempmanage"><a href="${ctx}/u/shop/logistics/index${ext}">配送方式设置</a></li>
		</ul>
	</li>
	
	<li><a href="javascript:void(0)"> <span class="arrow"></span> 商品管理</a>
		<ul>
			<li  tabindex="good-types"><a href="${ctx}/u/good/shopType/list${ext}">商品分类</a></li>
			<li  tabindex="prolist"><a href="${ctx}/u/good/good/list${ext}">商品列表</a></li>
			<li  tabindex="recommend"><a href="${ctx}/u/good/good/listRecommend${ext}">商品推荐管理</a></li>
		</ul>
	</li>
	
	<li><a href="javascript:void(0)"><span class="arrow"></span> 订单管理<i class="color-danger" id="gw-total-count"></i></a>
		<ul>
			<li  tabindex="order"><a href="${ctx}/u/order/list${ext}">店铺订单<i class="color-danger" id="gw-salesorder-count"></i></a></li>
		</ul>
	</li>
	<li><a href="javascript:void(0)"><span class="arrow"></span> 店铺推广管理<i class="color-danger" style="position:absolute;"> NEW</i></a>
         <ul>
         <li tabindex="shop-spread"><a href="${ctx }/u/publicity/shop/setting.htm">店铺推广管理</a></li>
         </ul>
     </li>
	<li><a href="javascript:void(0)"><span class="arrow"></span> 预约管理</a>
		<ul>
			<li  tabindex="appointment"><a href="${ctx}/u/appoint/list.htm">预约设置</a></li>
			<li  tabindex="appointment-goods"><a href="${ctx}/u/appoint/good-type-list.htm">商品分类</a></li>
			<li  tabindex="appointment-list"><a href="${ctx}/u/appoint/good-list.htm">商品列表</a></li>
			<li  tabindex="appointment-order"><a href="${ctx}/u/order/bookOrder/list.htm">预约订单<i class="color-danger"> </i></a></li>
		</ul>
	</li>
	
	<li><a href="javascript:void(0)"> <span class="arrow"></span> 基础设置</a>
		<ul>
			<li  tabindex="message"><a href="${ctx }/u/weixin/manage${ext}">回复管理</a></li>
			<li  tabindex="customnav"><a href="${ctx}/u/weixin/menu/list${ext}">自定义菜单</a></li>
			<li  tabindex="message-module"><a href="${ctx}/u/weixin/templateMessage/manage${ext}">模板消息 </a></li>
		</ul>
	</li>
</ul>
<%@include file="left-common-js.jsp" %>
