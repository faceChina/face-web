<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<%@include file="../../common/base.jsp"%>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<title>会员中心</title>
		<%@include file="../../common/top.jsp"%>
		<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/main.css">
		<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/mycardlist.css">
		<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
	
		<script type="text/javascript"> 
		    $(document).ready(function(){  
		    	var loadObj = {
		    		 	   elemt : ".#content",
		    			       url:"${ctx}/wap/${sessionShopNo}/buy/member/append${ext}",
		    			       loadType:'html',//使用Html加载方式
		    			       idd : "#content",
		    			       totalRow :'${pagination.totalRow}',
		    			       start:'${pagination.end}',
		    			       param:{
		    			    	   
		    			       }
		    		};
		        //滚动加载 相关配置*/
				rollLoad.init(loadObj);//触发滚动事件
				
		    });
		</script> 
	</head>
	<body>
		<div id="box" class="memberlistbox">
			<ul class="m-c-l-header">
				<li><a href="/wap/${sessionShopNo }/buy/coupon/list.htm?show=${show}">优惠券</a></li>
				<li class="line"></li>
				<li><a class="current">会员卡</a></li>
			</ul>
			<c:if test="${empty pagination.datas }">
				<div class="memberlist">
					<div class="no-content">
						<i class="iconfont icon-huiyuanqia1 clr-light"></i>
						<p>您还没有领取会员卡哦~</p>
					</div>
				</div>
			</c:if>
			
			<%@include file="member-list-data.jsp" %>
			
			<%@include file="../../common/foot.jsp" %>
			<%@include file="../../common/nav.jsp" %>
		</div>
	
		<script type="text/javascript">
			//充值会员卡的两种情况 
			function select(){
				art.dialog({
					title:'提示',
					content:'<a href="member-norecharge.html">未发行充值卡</a> | <a href="member-recharge.html">已发行充值卡</a>'
				});
			};
			
			function select2(){
				art.dialog({
					title:'提示',
					content:'<a href="member-nobalance.html">无账单</a> | <a href="member-balance.html">有账单</a>'
				});
			}
			
			//签到信息
			function toRegistration(){
				artTip("签到成功! +5个积分,明天可领10个积分，接着领哦！");
			};
		</script>
		
		<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
	</body>
</html>