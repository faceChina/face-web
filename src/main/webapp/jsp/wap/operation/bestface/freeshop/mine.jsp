<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的</title>
<%@include file="../../../common/base.jsp"%>
<%@include file="../../../common/top.jsp"%>
<%@include file="top.jsp" %>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>

<div id="box">	
	
	<div class="group group-others width20">
		<div class="group-item">
			<a href="/free/mine/info.htm" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-zhanghaoguanli fnt-24" style="color:#006699;"></i></span>
				<span class="group-colspan">账号管理</span>
				<span class="group-colspan"><i class="iconfont icon-right"></i></span>
			</a>
		</div>
		<c:if test="${!isDistributor }">
			<div class="group-item">
				<a href="/free/agency/apply/list.htm" class="group-rowspan">
					<span class="group-colspan"><i class="iconfont icon-shenqing fnt-24" style="color:#4DA723;"></i></span>
					<span class="group-colspan">申请成为代理</span>
					<span class="group-colspan"><i class="iconfont icon-right"></i></span>
				</a>
			</div>
		</c:if>
		<c:if test="${isDistributor }">
			<!-- 代理申请成功后显示   -->
			<div class="group-item">
				<a class="group-rowspan" onclick="myAgency()">
					<span class="group-colspan"><i class="iconfont icon-shenqing fnt-24" style="color:#4DA723;"></i></span>
					<span class="group-colspan">我的供应商</span>
					<span class="group-colspan"><i class="iconfont icon-right"></i></span>
				</a>
			</div>
		</c:if>
		
		
		<div class="group-item">
			<a href="/free/agency/apply/record.htm" class="group-rowspan">
				<span class="group-colspan"><i class="iconfont icon-jilu fnt-24" style="color:#EA5038;"></i></span>
				<span class="group-colspan">我的代理申请</span>
				<span class="group-colspan"><i class="iconfont icon-right"></i></span>
			</a>
		</div>
	</div>
	
	<!-- 卖家版底部导航  -->
	<%@ include file="../../../common/foot.jsp"%>
	<%@ include file="../../../common/freeNav.jsp" %>
	<%-- <script type="text/javascript" src="${resourcePath }operation/bestface/foot.js"></script>
	<script type="text/javascript" src="${resourcePath }operation/bestface/nav1.js"></script> --%>
</div>

<script type="text/javascript">
	navbar('set');
	function myAgency() {
		var daModel = "${daModel}";
		if (daModel == "1") {
			//内部代理
			location.href = '/free/agency/${supplierShopNo}/innerDetail.htm';
		} else {
			location.href = '/free/agency/${supplierShopNo }/detail.htm';
		}
	}
	
	function select(){
		art.dialog({
			title:'选择',
			content:'<a href="../../accounts/page/purse.html">已设置支付密码 </a> | <a href="../../security/page/security-pay-set.html">未设置支付密码</a>'
		});
	}
	
	function toExit(){
		sessionStorage.user=0;
		location.href="../../public/page/login.html"
	}
	
	/* 签到  */
	function toSignin(el){
		artTip("签到成功！ +5个积分，明天可领10个积分，继续领哦！",true)
		$(el).attr('disabled',true).html('<i class="iconfont icon-qiandaotubiao fnt-20"></i> 已签到');
	};
	
	//状态传值，前端专用，后端自行删除
	sessionStorage.setItem('session_agency','supplier');
	
</script>
</body>
</html>
