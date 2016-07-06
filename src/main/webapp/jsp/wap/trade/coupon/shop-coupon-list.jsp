<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>店铺优惠券列表</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }coupon/css/shop-conpon-list.css">
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
<script type="text/javascript"> 
    $(document).ready(function(){  
    	var loadObj = {
    		 	   elemt : "#couponContent",
    			       url:"${ctx}/wap/${sessionShopNo}/buy/member/append${ext}",
    			       loadType:'html',//使用Html加载方式
    			       idd : "#couponContent",
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
<div class="shopcoupon-list">
	<ul class="sp-list-ul" id="couponContent">
	<c:forEach items="${pagination.datas }" var="data">
		<li class="sp-list-item">
			<div class="l-i-left">
				<i class="iconfont icon-youhuiquan"></i>
			</div>
			<div class="l-i-center">
				<p class="center-shop">店铺优惠券全店使用</p>
				<p class="center-order">订单满<fmt:formatNumber value="${data.orderPrice/100 }" pattern="#.##"/>元使用（不含邮费）</p>
				<p class="center-time">使用期限<fmt:formatDate value="${data.effectiveTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${data.endTime }" pattern="yyyy.MM.dd"/></p>
				<c:if test="${data.isAllReceive }">
					<div class="geted-coupon"></div>
				</c:if>
			</div>
			<div class="l-i-right" align="center">
				<div class="price">
					<span class="price-sign">¥</span>
					<span class="price-num"><fmt:formatNumber value="${data.faceValue/100 }" pattern="#.##"/></span>
				</div>
				<div class="getcoupon">
					<c:choose>
						<c:when test="${data.hasReceive }">
							<a class="a-getcoupon" >已领取</a>
						</c:when>
						<c:when test="${data.isAllReceive }">
						</c:when>
						<c:otherwise>
							<button class="a-getcoupon" data-id="a-getcoupon" data-nameid="${data.id }">领取</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</li>
	</c:forEach>
	</ul>
	<div class="footfixbt0"><a onclick="history.go(-1);">返回</a></div>
</div>
<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
<script type="text/javascript">
	$(function(){
		$("[data-id='a-getcoupon']").off().on("click",function(){
			if (${not empty sessionAuthentication}) {
				var $obj=$(this);
				var couponSetId = $obj.attr("data-nameid");
				var subbranchId1 = "${subbranchId}";
				var receiveUrl = "/wap/${sessionShopNo}/buy/coupon/"+couponSetId+".htm";
				$obj.text("领取中...");
				$obj.attr("disabled",true);
				$.ajax( {    
				    url:receiveUrl,   
				    data:{'subbranchId':subbranchId1},    
				    type:'post',       
				    dataType:'json', 
				    success:function(data) {    
				        if(data.success){
				            artTip("领取成功",function(){
				            	$obj.parent(".getcoupon").html("<a class='a-getcoupon' >已领取</a>");
				            }); 
				        }else{   
				        	$obj.text("领取");
							$obj.attr("disabled",false);
				            artTip(data.info); 
				        }    
				     },    
				     error : function() {    
				        $obj.text("领取");
						$obj.attr("disabled",false);
				          artTip("异常！");    
				     }
				});
			} else {
				window.location.href="/wap/${sessionShopNo}/buy/couponNoLoginInGoodDetail.htm"
			}
			
		});
	});
</script>
</body>
</html>