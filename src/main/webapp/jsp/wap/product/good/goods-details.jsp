<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<c:set var="good" value="${goodDetailVo.good}"/>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-商品详情</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}goods/css/main.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourcePath}js/swiper/css/swiper.min.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}public/css/loginregdialog.css">
</head>
<style>
.tab-pane img{width:100%;}
</style>
<body>

<div class="ui-gallery">
<!--  	<div id="swiper-d"> -->
<!--  		<div class="swiper-wrapper"> -->
<%--  			<c:forEach items="${goodDetailVo.goodImgs}" var="goodImg"> --%>
<%-- 				<div class="swiper-slide"><a href="javascript:;"><img src="${picUrl}${goodImg.zoomUrl}" data-type="show-disalogsidler" alt=""></a></div> --%>
<%-- 			</c:forEach> --%>
<!-- 		</div> -->
<%-- 		<c:if test="${fn:length(goodDetailVo.goodImgs) > 1}"> --%>
<!-- 			<div class="swiper-pagination swiper-pagination-d"></div> -->
<%-- 		</c:if> --%>
		
<!--  	</div> -->
 	<div id="swiper-d">
        <ul class="sliderul">
        <c:forEach items="${goodDetailVo.goodImgs}" var="goodImg">
        	<li>
             	<div class="pinch-zoom">
                    <img src="${picUrl}${goodImg.zoomUrl}" data-type="show-disalogsidler" alt="">
                </div>
            </li>
		</c:forEach>
        </ul>
 	</div>
</div>

<div id="box">
	<input type="hidden" name="goodId" id="goodId" value="${good.id}">

		<!-- 轮播图 -->
	<div id="swiper-h">
 		<div class="swiper-wrapper">
			<c:forEach items="${goodDetailVo.goodImgs}" var="goodImg">
				<div class="swiper-slide"><a href="javascript:;"  data-type="show-disalogsidler"><img src="${picUrl}${goodImg.zoomUrl}" data-type="show-disalogsidler" alt=""></a></div>
			</c:forEach>
		</div>
		<c:if test="${fn:length(goodDetailVo.goodImgs) > 1}">
			<div class="swiper-pagination swiper-pagination-h"></div>
		</c:if>
		
 	</div>
	
	<c:if test="${empty memberPrice }">
		<div class="group group-cleartop">
			<div class="group-item group-item-noborder">
				<div class="group-rowspan">
					<div class="group-colspan">
						<div class="name-collect">
							<h3 class="txt-rowspan2 goodsname"><c:out value="${good.name}" escapeXml="false"/></h3>
							<a class="goodsname-collect" data-id="collect" >
								<c:choose>
									<c:when test="${isFavorite }">
										<i class="iconfont icon-yishoucang" data-status="1" data-id="collectgoodsshop"></i>
									</c:when>
									<c:otherwise>
										<i class="iconfont icon-weishoucang" data-status="0" data-id="collectgoodsshop"></i>
									</c:otherwise>
								</c:choose>
								<span class="font-collect">收藏</span>
							</a>
						</div>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left"><b class="clr-danger font16" id="j-price">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></b></span>
							<c:if test="${not empty good.marketPrice}">
								<span class="right clr-light"><del>￥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/></del></span>
							</c:if>
						</p>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left">销量：${good.salesVolume}件</span>
							<span class="right">浏览量：${good.browerTime}</span>
						</p>
						
					</div>
				</div>
			</div>
			<c:if test="${couponSetCount > 0 }">
				<div class="shopgoods-coupon">
					 <a href="/wap/${sessionShopNo }/any/couponSet/list.htm?goodId=${good.id}" class="coupon-a"><sup>券</sup><span>点击领取优惠券</span></a>
				</div>
			</c:if>
		</div>
	</c:if>
	<c:if test="${not empty memberPrice }">
		<div class="group group-cleartop">
			<div class="group-item group-item-noborder">
				<div class="group-rowspan">
					<div class="group-colspan">
						<div class="name-collect">
							<h3 class="txt-rowspan2 goodsname"><c:out value="${good.name}" escapeXml="false"/></h3>
							<a class="goodsname-collect" data-id="collect" >
								<c:choose>
									<c:when test="${isFavorite }">
										<i class="iconfont icon-yishoucang" data-status="1" data-id="collectgoodsshop"></i>
									</c:when>
									<c:otherwise>
										<i class="iconfont icon-weishoucang" data-status="0" data-id="collectgoodsshop"></i>
									</c:otherwise>
								</c:choose>
								<span class="font-collect">收藏</span>
							</a>
						</div>
						<p class="clearfix" style="margin-top:10px;">
							
							<span class="left">
								<var class="member-price">会员价</var>
								<b class="clr-danger font16" style="vertical-align:middle;">￥<fmt:formatNumber pattern="0.00" value="${memberPrice/100}"/></b>
							</span>
							<span class="right clr-light"><del></del></span>
						</p>
						<p class="clearfix" style="margin-top:10px;">
							<span class="left clr-light">商城价  ￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></span>
								<c:if test="${not empty good.marketPrice && 0 <good.marketPrice}">
									<span class="right clr-light"><del>￥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/></del></span>
								</c:if>
							</p>
							<p class="clearfix" style="margin-top:10px;">
							<span class="left">销量：${good.salesVolume}件</span>
							<span class="right">浏览量：${good.browerTime}</span>
						</p>
					</div>
				</div>
			</div>
			<c:if test="${couponSetCount > 0 }">
				<div class="shopgoods-coupon">
					 <a href="/wap/${sessionShopNo }/any/couponSet/list.htm?goodId=${good.id}" class="coupon-a"><sup>券</sup><span>点击领取优惠券</span></a>
				</div>
			</c:if>
		</div>		
	</c:if>
	
	<c:choose>
		<c:when test="${good.inventory>0}">
			<c:choose>
					<c:when test="${not empty goodDetailVo.salesPropMap }">
						<div class="group group-justify" id="j-details-attrs">
							<div class="group-item">
								<div class="group-rowspan" id="group-rowspan">
									<div class="group-colspan" id="j-details-attrs">
										<span id="j-detail-shopmore">选择 ${propName }</span>
									</div>
									<div class="group-colspan">
										<i class="iconfont icon-right"></i>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="group group-justify" id="j-details-attrs">
							<div class="group-item">
								<div class="group-rowspan">
									<div class="group-colspan" >
										<span id="j-detail-shopmore">选择购买数量</span>
									</div>
									<div class="group-colspan">
										<i class="iconfont icon-right"></i>
									</div>
								</div>
							</div>
						</div>
					</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
				<div class="group group-justify">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan">
								<span id="j-detail-shopmore">商品已售罄</span>
							</div>
						</div>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
	
	<div class="group group-justify">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan txt-rowspan1">
					<a href="/wap/${sessionShopNo }/any/gwscIndex.htm"> 
						<i class="iconfont icon-dianpuyulan clr-light"></i><span style="padding-left:10px;">${shop.name }</span>
					</a>
				</div>
				<div class="group-colspan" style="padding:5px 0 5px 5px;text-align:right;">
					<a href="/wap/${sessionShopNo }/any/gwscIndex.htm">
						进入店铺 <i class="iconfont icon-right clr-light"></i> 
					</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 进入店铺首页导航  -->
	<c:if test="${not empty myshopLocation }">
		<div class="group group-justify group-mapnav">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan mapnav">
						<a class="a-left a-map txt-rowspan1" href="/company/bdmap1.jsp"> 
							<i class="iconfont icon-dizhiblock clr-light"></i><span style="padding-left:10px;">${myshopLocation.address }</span>
						</a>
						<a class="a-iphone-right" href="tel:${myshopLocation.cell }">
							<i class="iconfont icon-iconfontdianhua"></i> 
						</a>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	
	<!-- 商品详情  -->
	<!-- 
	<div class="group none">
	-->
	<div class="group">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<span id="j-detail-shopmore">产品展示</span>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 
	<div class="probox">
		<a href="javascript:;" class="current"  data-class="pro-one">产品展示</a>
		<a href="javascript:;" data-class="pro-two">产品参数</a>
	</div>
	 -->
	
	<div class="group group-cleartop pro-one pro-item">
		<div class="group-rowspan goods-details-info">
			<c:out value="${good.goodContent}" escapeXml="false"/>
		</div>
	</div>
	
	<!-- 购物车/操作-->
	<%if(null==request.getParameter("delShoppingCartNav")||!"1".equals(request.getParameter("delShoppingCartNav"))){ %>
	<div class="footfix">
		<div class="group group-justify group-verticalline">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan group-colspan-w41 center">
						<a href="javascript:toIm();" class="kefu-cart"><i class="iconfont icon-liaotian1"></i><p class="font20">客服</p></a>
					</div>
					<div class="group-colspan group-colspan-w51 center">
						<a href="${ctx}/wap/${sessionShopNo }/buy/cart/find${ext}" class="shoppingcart kefu-cart" id="j-shoppingcart"><i class="iconfont icon-gouwuche1"></i><em id="j-cartCount" class="kefu-cart-num">${cartCount}</em><p class="font20">购物车</p></a>
					</div>
					<div class="group-colspan fnt-16" style="text-align: right;">
					<c:choose>
						<c:when test="${good.inventory>0}">
							<button class="btn btn-warning clr" id="j-addCart" type="button">加入购物车</button>
							<button class="btn btn-danger pdlr17" type="button" id="j-buy">立即购买</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-danger"  type="button">商品已售罄</button>
						</c:otherwise>
					</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%} %>
	<!-- 商品选择弹出窗口 -->
	<%@ include file="temp/itemPropSelect.jsp"%>
	
	<%@ include file="../../common/popularize.jsp"%>
	
	<c:if test="${!shopApp }">
		<div class="group group-justify">
			<div class="download-app-box">
				<img class="d-appbox-logo" src="${resourcePath }img/download-app-logo.png">
				<div class="d-appbox-font">
					<h2>刷脸APP</h2>
					<c:choose>
						<c:when test="${!isWechat}">
							<p>订单信息随时查看<p>
						</c:when>
						<c:otherwise>
							<p>开店做生意必备神器<p>
						</c:otherwise>
					</c:choose>
				</div>
				<a class="d-appbox-down" href="/app/download.htm">点击下载</a>
			</div>
		</div>
	</c:if>
	
	<c:if test="${shop.recruitButton == 1 && !isMaxSubbranchLevel}">
		<div class="join-us"><a onclick="recurit()">招募分店</a></div>
	</c:if>
	<div class="totop" onclick="toTop()"><i class="iconfont icon-top2"></i></div>
	
	<div class="goback">
		<a href="/wap/${shop.no }/any/gwscIndex.htm">店铺首页</a>
		<a href="/wap/${shop.no }/buy/personal/index.htm">个人中心</a>
	</div>
	<%@ include file="../../common/foot.jsp"%>
</div>	


	<!--regloginbox start-->

	<!--手机号码-->
	<div class="c-dialog-loginregbox none" id="dialog-iphonebox">
		<div class="lrbox-title">请填写您的手机号码</div>
		<ul class="lrbox-content">
			<li class="li-iphone"><span class="l-font whf3">+86</span><input id="input-iphone" class="input-pwd" type="tel" placeholder="请输入您的手机号码"></li>
		</ul>
		<div class="lrbox-content lrboox-bt">
			<a class="bg-dc3132 bt-reg" data-id="bt-iphone">确 定</a>
		</div>
	</div>

	<!--登录-->
	
	<div class="c-dialog-loginregbox none" id="dialog-loginbox">
		<div class="lrbox-title">该号码已经注册，请您直接登录</div>
		<ul class="lrbox-content">
			<li class="disabled"><span class="l-font whf4">手机号码</span><label class="label-iphone"></label></samp></li>
			<li><span class="l-font whf4">密码</span><input class="input-pwd" type="password" placeholder="请输入登录密码" id="inputPassword"></li>
		</ul>
		<div class="lrbox-content lrboox-bt mt50">
			<a class="bg-dc3132 bt-reg" data-id="bt-login" onclick="login()">登 录</a>
		</div>
		<div class="lrbox-content tright pd36 pb0"><a class="fleft forgetpwd" href="/any/findPassword${ext }?type=4">忘记密码？</a><a data-id="change-iphone">更换号码</a></div>
	</div>

	<!--注册-->
	<div class="c-dialog-loginregbox none" id="dialog-regbox">
		<div class="lrbox-title">注册账号</div>
		<ul class="lrbox-content">
			<li class="disabled"><span class="l-font whf4">手机号码</span><label class="label-iphone"></label></li>
			<li class="noline"><div class="div-code fleft"><span class="l-font whf3">验证码</span><input  class="input-code" type="text" placeholder="请输入验证码 " id="mobilecode"></div><a class="bt-code disabled bg-dc3132" id="a-code">获取</a></li>
			<li><span class="l-font whf3">密码</span><input class="set-pwd" type="password" placeholder="设置登录密码,下次登录使用" id="registerPassword"></li>
		</ul>
		<div class="lrbox-content tright pd36"><a data-id="change-iphone">更换号码</a></div>
		<div class="lrbox-content lrboox-bt">
			<a class="bg-dc3132 bt-reg" data-id="bt-login" onclick="register()">注 册</a>
		</div>
	</div>
	<!--regloginbox end-->


<%-- <script type="text/javascript" src="${resourcePath}js/slidefocus/slidefocus-jquery.js"></script> --%>
<script type="text/javascript" src="${resourcePath}js/swiper/js/swiper.min.js"></script>
<script type="text/javascript" src="${resourcePath}js/addel.js"></script>
<script type="text/javascript" src="${resourcePath}js/swiper/js/swiperpinchzoom.js"></script>
<script type="text/javascript">
function recurit() {
	var isMyOwnShop = ${isMyOwnShop};
	if (isMyOwnShop) {
		artTip("您无法申请成为自己的分店");
	} else {
		window.location.href = "/wap/${urlShopNo}/any/subbranch/new.htm?subbranchId=${subbranchId }";
	}
}

$(document).ready(function() {
	
	 //商品属性选择
	$("#j-details-attrs").click(function(){
        operateFn("j-contentSpecial");
	});
    function operateFn(obj){
    	dialogGoodsDetail=art.dialog({
            lock:true,
            title:"商品属性",
            skin:"goods-details-dislog",
        	 width:''+$("#box").width()+'px',
            content:document.getElementById(obj),
            init:function(){
				initGoodSDetailog();
			},
			close:function(){
				banscroll(false);
			},
            button:[{
                name:"加入购物车",
                focus:true,
                callback:function(){
                	if(!isStockNull()){
						return false;
					}
                	if(check()){
						addCat();
	    				return true;
	        		}else{
	        			return false;
	        		}
                }
            },{
                name:"立即购买",
                callback:function(){
                	if(!isStockNull()){
						return false;
					}
                	if(check()){
	    				settlement();
	    				return true;
	        		}else{
	        			return false;
	        		}
                }
            }]
        })
    }

	//加入购物车
	$("#j-addCart").click(function(){
		goodsDetailDialog(true);
	});


	//立即购买
	$("#j-buy").click(function(){
		goodsDetailDialog(false);
	});
});
var dialogGoodsDetail;
function goodsDetailDialog(isShoppingCart){
	dialogGoodsDetail=art.dialog({
			lock:true,
			title:"",
			skin:"goods-details-dislog goods-details-dislog-singlebt ",
			width:''+$("#box").width()+'px',
			content:document.getElementById("j-contentSpecial"),
			init:function(){
				initGoodSDetailog();
			},
			close:function(){
				banscroll(false);
			},
			button:[{
				name:"确定",
				focus:true,
				callback:function(){
					if(!isStockNull()){
						return false;
					}
	    			var mycolor = $("#j-color").find(".active").text();
	    			var mysize = $("#j-size").find(".active").text();
	    			var mynum = $("#number").val();
	    			var str = '已选："'+mycolor+'"、"'+mysize+'"、"'+mynum+'"';
	    			$("#j-detail-shopmore").text(str);
	    			if (isShoppingCart) { 
    					if(check()){
    						isAddCat = true;
    						addCat();
    	    				return true;
    	        		}else{
    	        			return false;
    	        		}
    				}else{
    					if(check()){
    						isAddCat = false;
    	    				settlement();
    	    				return true;
    	        		}else{
    	        			return false;
    	        		}
    				}
				}//callback end
			}]//button end
		});
}
function initGoodSDetailog(){
	banscroll(true);
	var $auistatelock=$(".aui_state_lock"),
		$auistatefocus=$(".aui_state_focus");
	$auistatelock.css({position:"fixed",top:"auto",bottom:"0"});
	$auistatefocus.hide();
	$auistatefocus.slideDown();
	$("body").off().on("click",function(){
		$auistatefocus.css({position:"fixed",top:"auto",bottom:"0"});
	});

}
var isStockNull=function(){
	if($("#inventory").text()=="0"){
		artTip("商品库存为0不能购买")
		return false;
	}
	else{
		return true;
	}
	
}
var hideSubBt=function(){
	var $button=$('.aui_buttons button');
	$button.attr('disabled',true);
    $button.text('商品已售罄');
    $button.removeClass("aui_state_highlight");
}
function check(){
	var goodSkuId = $('#skuId').val();
	var flag = true;
	var str = '已选：';
	//获取选择的商品属性，并显示在原页面中
	$('.group-cleartop').find("div[data-attrs='salesProp']").each(function(index){
		var prop = $(this).find(".active").text();
		if(prop==''){
			artTip('请选择'+$(this).find("h3").text()+"!");
			flag =false;
			return false;
		}
		str+=prop+'、';
	});
	if(flag){
		var mynum = $("#number").val();
		str+=mynum+'件'
		$("#j-detail-shopmore").text(str);
	}
	if(!goodSkuId){
		return false;
	}
	return flag;
}

var isAddCat = true;

//加入购物车
function addCat(){
	if(${not empty sessionAuthentication}){
		var goodSkuId = $('#skuId').val();//获取选择商品属性
		var quantity = $('#number').val();//获取选择商品数量
		$.post("${ctx}/wap/${sessionShopNo}/buy/cart/add${ext}", 
				{"goodSkuId":goodSkuId,"quantity":quantity,"subbranchId":'${subbranchId}',"shareId":"${param.shareId}"}, function(data){
			var result = eval('(' + data + ')');
			if(result.success){
				$('#j-cartCount').html(result.info);
				artTip("成功加入购物车！");
			}else{
				artTip("添加购物车失败！");
			}
		});
		return true;
	}else{
		queryTgtLogin();
		//$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/cart/addForLogin${ext}").submit();
	}
	
}
//结算
function settlement(){
	if(${not empty sessionAuthentication}){
		$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/good/buy${ext}").submit();
	}else{
		queryTgtLogin();
	}
}
</script>

<script type="text/javascript">
	var wheight,bheight;

	//轮播图
	$(function(){
		
		wheight=document.documentElement.clientHeight,
		bheight=$("body").height();
		
		var boo = ${fn:length(goodDetailVo.goodImgs) > 1};
		var swiperH = new Swiper('#swiper-h', {
			 autoplay:3000,
			 visibilityFullFit : true,
			 loop:boo,
	        pagination: '.swiper-pagination-h'
   		});
		
		$("#swiper-h").find(".swiper-slide").css("height",swiperH.slides.width());
		var $uigallery=$(".ui-gallery");
		var initDSidler=false;
		$("a[data-type='show-disalogsidler']").off().on("click",function(){
			$uigallery.show();
			if (!initDSidler) {
				$('div.pinch-zoom').each(function () {
					
	                new RTP.PinchZoom($(this), {});
	            });

		 		window.mySwipe = new Swipe(document.getElementById('swiper-d'), {
	                speed: 400
	            });
				initDSidler=true;
			}
		});	
		$uigallery.off().on("click",function(){
			$uigallery.hide();

		});
	});
	function toTop(){
		$('body,html').animate({scrollTop:0},500);
		return false;
	};
	//禁止页面滚动
	function banscroll(isBan){
		if (isBan) {
			document.addEventListener('touchmove', preventDefault,function (e) {
			}, false);	
		}else{
			document.removeEventListener('touchmove',preventDefault, function (e) {
			}, false);
		}
	}
	function preventDefault(e) {
	  	 e.preventDefault(); 
	}
	function toIm(){
		var url = window.location.href;
		var href = "${ctx}/wap/${sessionShopNo}/im/login${ext}?goodDetailId=${good.id}";
		window.location.href = href;
	}
	window.onscroll=function(){
		var scrollTop=document.body.scrollTop,
			$totop=$(".totop");
		if (scrollTop>110) {
			$totop.show();
		}
		else{
			$totop.hide();
		}
	}
	
	var ajaxCollect=function(callevent){
		var remoteType = 1;
		var remoteId = "${goodDetailVo.good.id}";
		var isLogin = ${isLogin};
		if (isLogin) {
			$.post("/wap/${sessionShopNo}/buy/favorites/add.htm",{"remoteType":remoteType, "remoteId":remoteId},function(jsonData) {
				if(jsonData.success) {
				 	callevent();
					isPost=false;
				} else {
					artTip("操作失败！");
				}
			},"json");
		} else {
			window.location.href = "/wap/${sessionShopNo}/buy/favorites/addNoLogin/"+remoteType+"/"+remoteId+".htm";
		}
	}
	
	
	var dialogIphoneBox,
		dialogRegBox,
		dialogLoginBox;
		// dialog-iphone
	var showDialogIphoneBox=function(){
		dialogIphoneBox=art.dialog({
			lock:true,
			skin:"goods-details-regloginbox",
			width:'95%',
			init:function(){
				var that=this;
			  	setTimeout(function(){
			  		initRegLoginlog(that);
			  	},10);
				
				$(".goods-details-regloginbox .aui_titleBar").remove();
				that.content(document.getElementById("dialog-iphonebox"));
				$("#input-iphone").focus();
				// 
				$("a[data-id='bt-iphone']").off().on("click",function(){
					var $inputiphone=$("#input-iphone"),
				 		iphoneValue=$.trim($inputiphone.val());
					 	console.log(iphoneValue);
					 	
				    //ajax
				    if(isIphone(iphoneValue)){
				    	that.close();
				    	
					    $.post("${ctx}/any/checkLoginAccount${ext}",{'phone':iphoneValue},function(result){
					    	$('.label-iphone').html(iphoneValue);
					    	$('#username').val(iphoneValue);
					    	 if(result=="true"){
				        	  	//未注册
				        		showDialogRegBox();
					    	 }else{
				        	  	//已注册
				        		showDialogLoginBox();
					    	 }
					    	 //$(".goods-details-regloginbox").css("top",(wheight- $(".goods-details-regloginbox").height())/2+"px");
						});
				    }else{
			    		artTip("请输入正确的手机号码")
				    }
				});
				/* $("#input-iphone").off().on("blur",function(){
					 $(".goods-details-regloginbox").css("top",(wheight- $(".goods-details-regloginbox").height())/2+"px");
				}); */
			},
			close:function(){
				banscroll(false);
			}
		});
	}

	//dialog-LoginBox
	var showDialogLoginBox=function(){
		dialogLoginBox=art.dialog({
			lock:true,
			skin:"goods-details-regloginbox",
			width:'95%',
			init:function(){
				var that=this;
			  	setTimeout(function(){
			  		initRegLoginlog(that);
			  	},10);
				
				$(".goods-details-regloginbox .aui_titleBar").remove();
	        	that.content(document.getElementById("dialog-loginbox"));
	        	//登录
	        	$("a[data-id='bt-lgin']").off().on("click",function(){
	               that.close();
		    	});
		    	$(".forgetpwd").off().on("click",function(){
		    		
		    		$('#iscallback').val('password');
		    		$('#callbackurl').val($(this).attr("href"));
		    		$('#skipurl').val("/wap/${sessionShopNo}/j_spring_security_check");
		    		
		    		if(isAddCat){
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/cart/addForLogin${ext}").submit();
	       			}else{
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/good/buy${ext}").submit();
	       			}
		    		
		    		//window.location.href=$(this).attr("href") + "&iscallback=true&callbackurl=" + callbackurl + "&skipurl=" + skipurl;
		    	});
		    	changeIphoneNum(that);
			},
			close:function(){
				banscroll(false);
			}
		});
	}
	
	//dialog-RegBox
	var showDialogRegBox=function(){
		dialogRegBox=art.dialog({
			lock:true,
			skin:"goods-details-regloginbox",
			width:'95%',
			init:function(){
				var that=this;
		  		setTimeout(function(){
		  			initRegLoginlog(that);
		  		},10);
			
				$(".goods-details-regloginbox .aui_titleBar").remove();
				that.content(document.getElementById("dialog-regbox"));
				// 获取验证码
		    	$("#a-code").off().on("click",function(){
		    		getCode("#a-code");
		    	});
		    	// 注册
		    	$("a[data-id='bt-reg']").off().on("click",function(){
	               that.close();
		    	});
		    	changeIphoneNum(that);
			},
			close:function(){
				banscroll(false);
			}
		});
	}

	var changeIphoneNum=function(obj){
		var objIphone=obj;
		$("a[data-id='change-iphone']").off().on("click",function(){
		 	showDialogIphoneBox();
	    	objIphone.close();
	    	cleanPhoneData();
		});
	}
	
	function cleanPhoneData(){
		$('#inputPassword').val('');
		$('#label-iphone').html('');
		$('#registerPassword').val('');
		$('#mobilecode').val('');
	}
	
	//初始化购买商品弹窗
	function initRegLoginlog(obj){
		banscroll(true);
		var $auistatelock=$(".aui_state_lock"),
			$auistatefocus=$(".aui_state_focus");
		$auistatelock.css({position:"fixed"});
		$(document).bind("click",function(event){
			obj.close();
			$(document).unbind("click");
			return false;
		});
		$(".goods-details-regloginbox").off().on("click",function(){
			obj.show();
			return false;
		});
		$(".m-art").off().on("click",function(){
			obj.show();
			return false;
		});

		var $dialogIphoneLoginRegBox=$(".goods-details-regloginbox");
	 	$dialogIphoneLoginRegBox.css("top",(wheight- $dialogIphoneLoginRegBox.height())/2+"px");
	}
	 // 关闭弹窗
    $("a[data-id='closedialog']").off().on("click",function(){
    	dialogGoodsDetail.close();
    });
	
	function login(type){
		var password = '';
		
		if(type=='regBox'){
			password = $('#registerPassword').val();
		}else{
			password = $('#inputPassword').val();
		}
		
		if(password=='' || password=='undefined'){
			artTip("请输入密码");
			return;
		}
		
		
		$.ajax({
		 	type : "post",  
	       	url : "${ctx}/any/wap/goodsDetail/login${ext}",  
	       	async : false,
	       	data: {'phone':$('#username').val(),'password':password},
	       	success : function(result){  
	       		if(result=='true'){
	       			$('#password').val(password);
	       			$('#iscallback').val("true");
	       			$('#callbackurl').val("/wap/${sessionShopNo}/j_spring_security_check");
	       			
	       			if(isAddCat){
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/cart/addForLogin${ext}").submit();
	       			}else{
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/good/buy${ext}").submit();
	       			}
	       		}else{
	       			artTip(result);
	       			return;
	       		}
	       	}
		});
	}
	//获取验证码
	function getCode(el){
		var phone = $("#username").val();
		$.post("${ctx}/any/web/registerCode${ext}",{"phone":phone,"type":1},function(flag){
			if("true" == flag){
			}else{
				art.dialog.alert(flag,function() {
					getCode(el);
				});
			}
		});
		getCode1(el)

	}
	//注册
	function register(){
		var mobilecode = $('#mobilecode').val();
		var phone = $("#username").val();
		var registerPassword = $('#registerPassword').val();
		
		if(mobilecode=='' || mobilecode=='undefined'){
			artTip("请输入验证码");
			return;
		}
		
		if(mobilecode.length < 4){
			artTip("验证码不能小于4个字 符");
			return;
		}
		
		if(registerPassword=='' || registerPassword=='undefined'){
			artTip("请输入密码");
			return;
		}
		
		if(registerPassword.length < 6){
			artTip("密码不能小于6个字 符");
			return;
		}
		
		$.post("${ctx}/any/appRegister${ext}",{"mobile":phone,"mobilecode":mobilecode,"pwd":registerPassword,"repwd":registerPassword,"nickname":phone},function(flag){
			if("success" == flag){
				login('regBox');
			}else{
				artTip(flag);
				return;
			}
		});
	}
	
	function queryTgtLogin(){
		$.ajax({
		 	type : "post",  
	       	url : "${ctx}/any/wap/goodsDetail/tgtLogin${ext}",  
	       	async : false,
	       	success : function(result){  
	       		if(result=='true'){
	       			if(isAddCat){
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/cart/addForLogin${ext}").submit();
	       			}else{
	       				$('#buyForm').attr("action","${ctx}/wap/${sessionShopNo}/buy/good/buy${ext}").submit();
	       			}
	       		}else{
	       			showDialogIphoneBox();
	       		}
	       	}
		});
	}
</script>
<script type="text/javascript" src="${resourcePath}js/fsize.js"></script>
</body>
</html>