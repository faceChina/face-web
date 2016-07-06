<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约订单</title>
<%@include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">
</head>
<body>
<div id="box">
	<div class="list-tab">
		<ul>
			<li><a href="javascript:;"><span class="active">全部</span></a></li>
			<li><a href="javascript:;"><span>已确认</span></a></li>
			<li><a href="javascript:;"><span>待确认</span></a></li>
			<li><a href="javascript:;"><span>已拒绝</span></a></li>
			<li><a href="javascript:;"><span>已取消</span></a></li>
		</ul>
	</div>
	
	<div class="content">
	
		<!-- 商品类预约订单  -->
		
		<!-- 待确认状态   -->
		<div class="list-row">
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">待确认</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col"  href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='reserve-details.html'" data-id="1" style="display:none;">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<div class="list-col" style="cursor: pointer;">
				<div class="list-center box-flex clr-light" onclick="showhideLayer('1')">显示更多商品</div>
			</div>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState1');location.href='reserve-details.html'">查看详情</a>
					<button class="btn btn-default" data-cancel="1" >取消订单</button>
				</div>
			</div>
		</div>
		
		<!-- 已确认状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已确认</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState2');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState2');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState2');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		<!-- 已拒绝状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已拒绝</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState3');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState3');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState3');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		
		<!-- 已取消状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已取消</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState4');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState4');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
					<span class="clr-light fnt-12">X1</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState4');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		
		<!-- 服务类预约订单  -->
		
		<!-- 待确认状态   -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">待确认</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState5');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState5');location.href='reserve-details.html'">查看详情</a>
					<button class="btn btn-default" data-cancel="1" >取消订单</button>
				</div>
			</div>
		</div>
		
		<!-- 已确认状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已确认</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState6');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState6');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		<!-- 已拒绝状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已拒绝</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState7');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState7');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		
		<!-- 已取消状态  -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">已取消</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState8');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
				<div class="list-top shop-other">	
					<span>￥300000.00</span>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState8');location.href='reserve-details.html'">查看详情</a>
				</div>
			</div>
		</div>
		
		<!-- 无价格状态 -->
		<div class="list-row" data-showhide>
			<div class="list-col">
				<div class="list-inline box-flex">该处为店铺名称</div>
				<div class="list-inline clr-warning">待确认</div>
			</div>
			<div class="list-col">
				<div class="list-inline box-flex clr-light">预约时间：2015-01-12 12:00-13:00</div>
			</div>
			<a class="list-col" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState9');location.href='reserve-details.html'">
				<div class="list-inline"><img src="http://i10.topit.me/l135/1013513773d0274e28.jpg" alt="" width="70" height="70"></div>
				<div class="list-top box-flex">
					<ul>
						<li class="txt-rowspan2">2014秋冬韩版西装领长款双排扣外套2014秋冬2014秋冬韩版西装领长款双排扣外套2014秋冬</li>
					</ul>
				</div>
			</a>
			<div class="list-col ">
				<div class="list-right">
					<a class="btn btn-default" href="javascript:sessionStorage.setItem('session_ReserveState','buyerState9');location.href='reserve-details.html'">查看详情</a>
					<button class="btn btn-default" data-cancel="1" >取消订单</button>
				</div>
			</div>
		</div>
		

	</div>
	
	<%@include file="../../common/foot.jsp" %>
	<%@include file="../../common/nav.jsp" %>
</div>

<!-- 确认收货 -->
<div id="j-take" style="display:none;">
	<form action="" id="jform-enterpwd">
		<div class="list-col">
			<div class="list-inline">
				支付金额：<span class="clr-warning">500.00</span>元
			</div>
		</div>
		<div class="list-col list-clearpadding">
			<div class="list-inline box-flex">
				<input type="password" class="form-border" id="password" name="password" placeholder="请输入支付密码">
			</div>
		</div>
	</form>
	<div class="list-col list-clearpadding">
		如果您还未设置支付密码，请先<a href="../../security/page/security-pay-set.html" style="color:blue">设置支付密码</a>
	</div>
</div>


<script type="text/javascript">

//取消订单
$('[data-cancel]').on('click',function(){
	var shopId = $(this).data('cancel');
	art.dialog({
		lock: true,
		width: '90%',
		title:'温馨提示',
	    content: '确定取消订单？',
	    ok: function () {	
	        console.log("商品ID"+shopId)
	    },
	    cancelVal: '取消',
	    cancel: true //为true等价于function(){}
	});
});

	//以下为前端专用，后台可自行删除
	sessionStorage.setItem('session_Reserve','buyer');
</script>
</body>
</html>
