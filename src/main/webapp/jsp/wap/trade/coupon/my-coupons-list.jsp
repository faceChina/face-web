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
<title>我的卡券</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }coupon/css/my-conpon-list.css"> 
</head>
<body>
<div class="my-coupon-list">
	<ul class="m-c-l-header">
		<li><a class="current">优惠券</a></li>
		<li class="line"></li>
		<li><a href="/wap/${sessionShopNo }/buy/member/cardList.htm?show=${show}">会员卡</a></li>
	</ul>
	<c:if test="${empty pagination.datas }">
		<div class="no-content" >
			<img src="${resourcePath }coupon/img/list-cuopons-no.png">
			<p>您还没有优惠券哦~</p>
		</div>
	</c:if>
	<c:if test="${not empty pagination.datas }">
		<div class="m-c-l-content">
			<div class="c-title">
				<i class="iconfont-yuanquan iconfont icon-yuanquanweixuan none" data-select-all></i>
				<span class="all-coupon">全部优惠券<i>${count }</i>张</span>
				<a class="c-t-a-edit" data-id="c-t-a-edit" data-status="0">编辑</a>
			</div>
			<ul class="c-list" id="couponContent">
			<c:forEach items="${pagination.datas }" var="data">
				<li class="c-list-item <c:if test='${data.timeStatus == 2  }'>c-list-item-sx</c:if>">
					<div class="none item-left"><i data-nameid="${data.id }" class="iconfont-yuanquan iconfont icon-yuanquanweixuan none" data-select-single></i></div>
					<div class="item-right">
					<a href="/wap/${sessionShopNo }/buy/coupon/${data.id}.htm">
						<div class="c-l-header"></div>
						<div class="c-l-content">
							<c:choose>
								<c:when test="${not empty data.shopLogo }">
									<img class="l-c-shopheader" src="${picUrl }${data.shopLogo}">
								</c:when>
								<c:otherwise>
									<img class="l-c-shopheader" src="${resourcePath }img/defaultShopLogo.jpg">
								</c:otherwise>
							</c:choose>
							<div class="l-c-price">
								<span class="price-sign">¥</span>
								<span class="price-num"><fmt:formatNumber value="${data.faceValue/100 }" pattern="#.##"></fmt:formatNumber></span>
							</div>
							<div class="l-c-other">
								<p class="other-shopnname">${data.shopName }</p>
								<p class="other-order">满<b><fmt:formatNumber value="${data.orderPrice/100 }" pattern="#.##"></fmt:formatNumber></b>元使用</p>
								<p class="other-line"></p>
								<p class="other-time"><fmt:formatDate value="${data.effectiveTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${data.endTime }" pattern="yyyy.MM.dd"/></p>
							</div>
							<c:if test='${data.timeStatus == 2 }'>
								<div class="icon-yinzhang icon-yishixiao"></div>
							</c:if>
						</div>
					</a>
					</div>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div class="footfix"><a class="delShopGoods none">删除</a></div>
	</c:if>
</div>
<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
<script type="text/javascript" src="${resourcePath }coupon/js/mycouponlist.js"></script>
<%@include file="../../common/nav.jsp" %>
<script type="text/javascript">
	function ajaxDelListCoupons(listid,isAllSelected) {
		console.log("list"+listid);
		console.log(isAllSelected);
		$.ajax( {    
			    url:'/wap/${sessionShopNo}/buy/coupon/delete.htm',   
			    data:{   'idStr':listid,'isAllSelected':isAllSelected },    
			    type:'post',       
			    dataType:'json', 
			    success:function(data) {    
			        if(data.success){
			        	window.location.reload();
			        }else{ 
			        	artTip(data.info);
			        	return false;
			             
			        }    
			     },    
			     error : function() {
			         artTip("异常！");    
			     }
			});
	}
	
	var rollLoad = {
		    elemt:0, //获取添加字符串的高度
		    totalRow:1, //最大条数
		    idd:'',//最外层的ID
		    totalheight:0,//容器的高度
		    url:'',//数据访问URL
		    start:0,//起始条
		    curPage:1,
		    param:{},
		    //currentTime:'',
		    loadType:'Json',
		    init:function(obj,callevent){//传入默认值
		    	var winht = $(window).height(),
		        screenNum;
		        this.elemt = $(obj.elemt).outerHeight();
		        this.totalRow = obj.totalRow;
		        this.idd= obj.idd;
		        this.url=obj.url;
		        this.start=Number(obj.start);
		        this.param=obj.param;
		       /* if(obj.currentTime){
		            this.currentTime = obj.currentTime;
		        }*/
		        screenNum = Math.round(winht/this.elemt)>10?(Math.round(winht/this.elemt)-10):0; //根据屏幕 计算第一次加载数量
		        if(obj.loadType=='html'){
		        	 this.loadType = obj.loadType;
		        }
		        this.loading(callevent);
		    },
		    loading:function(callevent){ //触发滚动事件
		        var thiz = this;
		        $(window).scroll(function(){  
		           var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
		            thiz.totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
		            if(($(document).height()) <= thiz.totalheight  && thiz.start < thiz.totalRow) { 
		            	if(thiz.loadType == 'html'){
		            		thiz.addHtmltext(10);
		            	}
		            }  
		        });
		    },
		    addHtmltext:function(num){ //添加字符串
		    	var thiz = this;
		    	thiz.curPage+=1;
		        if(thiz.totalRow==0)return ;
		        if(thiz.totalRow==thiz.start)return ;
		        thiz.param.curPage=thiz.curPage;
		        thiz.param.start=thiz.start;
		        thiz.param.pageSize=num;
		    	$.post(thiz.url,thiz.param,function(data){
		    		if(data){
				        thiz.start+=num;
				        var main = $(thiz.idd); 
				        main.append(data);
				        if($(".c-t-a-edit").text()=="完成"){
				        	$(".item-left .iconfont-yuanquan").removeClass("none");
				        	$(".item-right").addClass("l68");
				        	
				        }
				        if($("[data-select-all]").hasClass("icon-roundcheckfill")){
				        	$(".iconfont-yuanquan").removeClass("icon-yuanquanweixuan");
				        	$(".iconfont-yuanquan").addClass("icon-roundcheckfill");
				        }
		    		}else{
		    			alert("加载失败!");
		    		}
		    	});
		    }
		}
	
		var loadObj = {
			 	   elemt : "#couponContent",
				       url:"${ctx}/wap/${sessionShopNo}/buy/coupon/append${ext}",
				       loadType:'html',//使用Html加载方式
				       idd : "#couponContent",
				       totalRow :'${pagination.totalRow}',
				       start:'${pagination.end}',
				       param:{
				    	   
				       }
			};
	 //滚动加载 相关配置*/
	rollLoad.init(loadObj);//触发滚动事件
	
</script>
</body>
</html>