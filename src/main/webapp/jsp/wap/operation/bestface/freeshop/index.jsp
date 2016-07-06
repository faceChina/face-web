<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>后台管理</title>
<%@include file="top.jsp" %>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${resourcePath }/js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath }/js/mob-public.js"></script>
<script type="text/javascript">
/**
 * 自定义分享按钮
 */ 
wx.config({
      debug: false,
      appId: '${appId}',
      timestamp: 1422009542,
      nonceStr: 'rfOEfBdBznhLFkZW',
      signature:"${signature}",
      jsApiList: [
        'onMenuShareTimeline',
        'onMenuShareAppMessage'
      ]
  });
  
 wx.ready(function(){

   // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
  wx.onMenuShareAppMessage({
      title:"${shop.name}",
	  link: '${WgjUrl}/wap/${shop.no}/any/list${ext}', // 分享链接
      imgUrl: "${picUrl}"+"${iCard.headPicture}",
      trigger: function (res) {
        //alert('用户点击发送给朋友');
      },
      success: function (res) {
        //alert('已分享');
      },
      cancel: function (res) {
        //alert('已取消');
      },
      fail: function (res) {
        //alert(JSON.stringify(res));
      }
    });
   
  wx.onMenuShareTimeline({
	  title:"${shop.name}",
	  link: '${WgjUrl}/wap/${shop.no}/any/list${ext}', // 分享链接
      imgUrl: "${picUrl}"+"${iCard.headPicture}",
    success: function () { 
        // 用户确认分享后执行的回调函数
    },
    cancel: function () { 
        // 用户取消分享后执行的回调函数
    }
});
   
 
 });
</script>
<style type="text/css">
html{
	font-size:10px;
	background:#f5f5f5;
}
.bg{
	position:absolute;
	top:0;
	left:0;
	z-index:-1;
}
.bg img{
	display:block;
	width:100%;
}

/*=========================*/
.headpic{
	padding-top:1.5rem;
}
.headpic .header{
	width:100%;
	display:table;
}
.headpic .header li{
	display:table-cell;
	width:33%;
	vertical-align:middle;
	text-align:center;
}
.headpic .header li a{}
.headpic .header li a i{
	padding:0.5rem;
	color:#fff;
	font-size:40px;
	
}
.headpic .header li a.link{
	display:block;
	width:5rem;
	overflow:hidden;
	margin:0 auto;
}
.headpic .header li a.link .img{
	display:block;
	width:5rem;
	height:5rem;
	overflow:hidden;
	border:5px solid #85d6ff;
	border-radius:100%;
}
.headpic .header li a.link .img img{
	display:block;
	width:100%;
	height:100%;
	overflow:hidden;
	border-radius:100%;
}
.headpic .name{
	width:100%;
	margin-top:0.25rem;
	font-size:14px;
	line-height:24px;
	color:#ffffff;
	text-align:center;
}
/*=========================*/

/*=========================*/
.menu-shortcut{
	width:100%;
	margin:2.5rem auto 0;
	overflow:hidden;
}
.menu-shortcut li{
	float:left;
	width:5.1rem;
	height:5.1rem;
	margin:1rem 0.6rem 0;
	overflow:hidden;
	background:#fff;
	border-radius:3px;
}
.menu-shortcut li:nth-child(3n){
	margin-right:0;
}
.menu-shortcut li a{
	display:block;
	width:100%;
	height:100%;
	background:rgba(255,255,255,0.5);
	border-radius:100%;
	text-align:center;
	
	display: -webkit-box;-webkit-box-orient: horizontal;-webkit-box-pack: center;-webkit-box-align: center;
	display: -moz-box;-moz-box-orient: horizontal;-moz-box-pack: center;-moz-box-align: center;
	display: -o-box;-o-box-orient: horizontal;-o-box-pack: center;-o-box-align: center;
	display: -ms-box;-ms-box-orient: horizontal;-ms-box-pack: center;-ms-box-align: center;
	display: box;box-orient: horizontal;box-pack: center;box-align: center;	
}
.menu-shortcut li a i{
	display:block;
	font-size:35px;
	line-height:30px;
	text-align:center;
}
.menu-shortcut li a em{
	display:block;
	padding-top:5px;
}
/* 分享 */
.recommend{
	position:fixed;
	top:0;
	left:0;
	width:100%;
	height:100%;
	background:rgba(0,0,0,.7);
	z-index:9;
}
.recommend-info{
	width:90%;
	margin:3em auto 0;
	padding:1em 1em 2em 1em;
	border:1px dashed #f5f5f5;
	border-radius:6px;
	color:#fff;
}
.recommend-info h3{
	margin-bottom:1em;
	font-size:16px;
	font-weight:normal;
}
.recommend-info h4{
	margin-bottom:1em;
	font-size:14px;
}
.recommend-info p{
	margin-bottom:20px;
	font-size:12px;
	line-height:25px;
}
.recommend-info p em{
	display:inline-block;
	margin-left:10px;
	padding:0.4em 2em;
	text-align:center;
	background:#22292c;
	border-radius:6px;
}
.recommend-info p a{
	display:inline-block;
	margin-left:10px;
}
/* 分享 end*/

.fot-menu{
	position:fixed;
	bottom:0;
	max-width:640px;
	min-width:320px;
	width:100%;
	margin:0 auto;
	overflow:hidden;
}
.fot-menu ul{
	display:table;
	width:100%;
}
.fot-menu ul li{
	display:table-cell;
	box-flex:1.0;
	width:50%;
	height:2.5rem;
	background:#148AE4;
	border-right:1px solid #8AC5F1;
	text-align:center;
	line-height:2.5rem;
	vertical-align:middle;
}
.fot-menu ul li:last-child{
	border-right:none;
}
.fot-menu ul li a{
	display:block;
	color:#fff;
}
.fot-menu ul li a i{
	font-size:24px;
	vertical-align:middle;
}
.fot-menu ul li a em{
	display:inline-block;
	font-size:14px;
	vertical-align:middle;
}

/* 二维码样式  */
#j-ewm{}
#j-ewm span{
	width:60%;
	display:block;
	margin:40px auto;
}
#j-ewm span img{
	width:100%;
	display:block;
}
.p-font{
	color: #fff;
	padding-top: 5px;
	font-size: 14px;
}
/* 二维码样式  end*/
</style>
</head>
<body>
<div class="container" style="position:relative;">

	<div class="bg"><img src="${resourcePath }operation/bestface/img/banner4.jpg" alt=""></div>
	
	<div class="headpic">
		<ul class="header">
			<li>
				<a href="javascript:void(0)"  onclick="showerweima()">
					<i class="iconfont icon-erweima1"></i>
					<p class="p-font">二维码</p>
				</a>
			</li>
			<li>
				<a href="${ctx }/wap/${iCard.shopNo}/any/icard/my-card/${iCard.id}${ext }" class="link">
					<span class="img"><img src="${picUrl}${iCard.headPicture}" alt=""></span>
				</a>
			</li>
			<li>
				<a href="${ctx }/wap/${iCard.shopNo}/any/icard/my-card/${iCard.id}${ext }">
					<i class="iconfont icon-gerenmingpian"></i>
					<p class="p-font">我的名片</p>
				</a>
			</li>
		</ul>
		<p class="name">${iCard.nickName}</p>
	</div>
	
	<ul class="menu-shortcut">
		<li>
			<a href="${ctx}/wap/${shop.no}/any/index${ext}">
				<span>
					<i class="iconfont icon-dianpuyulan" style="color:#CB0101;"></i>
					<em>店铺预览</em>
				</span>
			</a>
		</li>
		<li>
			<a href="${ctx }/free/order/index${ext}">
				<span>
					<i class="iconfont icon-dingdan" style="color:#FFA317;"></i>
					<em>我的订单</em>
				</span>
			</a>
		</li>
		<li>
			<a href="${ctx }/free/wallet/index${ext}">
				<span>
					<i class="iconfont icon-qianbao" style="color:#339900;"></i>
					<em>我的钱包</em>
				</span>
			</a>
		</li>
		<li>
			<a href="${ctx }/free/good/list${ext}">
				<span>
					<i class="iconfont icon-quanbushangpin" style="color:#9A06CE;"></i>
					<em>商品管理</em>
				</span>
			</a>
		</li>
		<c:if test="${!isDistribution }">
			<li>
				<a href="${ctx }/free/agency/apply/list${ext}">
					<span>
						<i class="iconfont icon-friendadd" style="color:#CD0305;"></i>
						<em>成为代理</em>
					</span>
				</a>
			</li>
		</c:if>
		<c:if test="${isDistribution }">
			<!-- 已经成为代理之后这里是我的代理，上面的链接也会发生改变  -->
			<li>
				<a  onclick="myAgency()" >
					<span>
						<i class="iconfont icon-friendadd" style="color:#CD0305;"></i>
						<em>我的代理</em>
					</span>
				</a>
			</li>
		</c:if>
	</ul>
	
	<div class="fot-menu">
		<ul>
			<li>
				<a href="/free/mine/index.htm">
					<i class="iconfont icon-settings"></i>
					<em>店铺设置</em>
				</a>
			</li>
			<li>
				<a onclick="share()">
					<i class="iconfont icon-shoucang"></i>
					<em>分享求收藏</em>
				</a>
			</li>
		</ul>
	</div>
	
</div>

<div id="j-ewm" style="display:none;">
	<span><img src="${picUrl}${twoDimensionalCode}" alt=""></span>
	<c:choose>
		<c:when test="${isEmployee == 1 }">
			<!-- 员工店铺-总店二维码  -->
			<p>扫一扫上面的二维码图案，关注总店公众号</p>
		</c:when>
		<c:otherwise>
			<!-- 外部店铺-店铺二维码  -->
			<p>扫一扫上面的二维码图案，访问我的店铺</p>
		</c:otherwise>
	</c:choose>
</div>

<!--分享求收藏-->
<div class="recommend" style="display:none;">
	<div class="recommend-info">
		<h3>把商城分享给朋友！</h3>
		<p>1. 点击右上角的<em>● ● ●</em></p>
		<p>2. 点击<i class="iconfont icon-fenxiang" style="padding:0 0.5em;"></i><i class="iconfont icon-fenxiangpengyouquan"></i></p>
		<button type="button" class="btn btn-block btn-default" onClick="iKnow()">知道了</button>
	</div>
</div>

<script type="text/javascript">

//判断访问页面的设备类型
function dpreview() {
    var sUserAgent = navigator.userAgent.toLowerCase();  
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad",
  		  bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os",
		  bIsMidp = sUserAgent.match(/midp/i) == "midp",
		  bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4",
		  bIsUc = sUserAgent.match(/ucweb/i) == "ucweb",
		  bIsAndroid = sUserAgent.match(/android/i) == "android",
		  bIsCE = sUserAgent.match(/windows ce/i) == "windows ce",
		  bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";  

    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {  
			return 2; //非PC
    } else {  
			return 1; //PC
    }
}
//根据宽度变化字号
function fontsize(fontsize){
	var width = window.innerWidth;
	fontsize = (fontsize == undefined) ? ( (width < 640) ? ((33.75*width)/640) : 33.75) : 10; //PC端算法
	//手机端算法：(width < 640) ? ((60*width)/480) : 67.5
	$("html").attr("data-dpr",this.dpreview()); //设备类型
	$("html").css("font-size",fontsize); //基本字号
}
$(function(){
	fontsize();
	$(window).resize(function(){
		fontsize();
	})
	
	if('${IsSetPayMentCode}' == 'false') {
		//弹出支付密码
		art.dialog({
			lock:true,
			title:"支付密码设置",
			width:'100%',
			content:"<span style='font-size:14px;'>为了您的钱包资金安全，<br/>请先设置支付密码哦！</span>",
			button:[{
				name:"残忍拒绝",
				callback:function(){
					
				}
			},{
				name:"前去设置",
				focus:true,
				callback:function(){
					location.href='${ctx}/free/mine/paymentCode/edit.htm';
				}
			}]
		})
	}
})

//显示二维码
/* function showerweima(){
	art.dialog({
		lock:true,
		title:"扫一扫",
		content:document.getElementById("j-ewm")
	})
} */
var jewmDialog=0,
isShow=false;
//显示二维码
function showerweima(){
jewmDialog=artDialogNoBtComfirm("j-ewm");
isShow=true;
};
$("body").click(function(){
if (!isShow) {
	jewmDialog.close();
}
isShow=false;
});

//分享求收藏
function share(){
	$('.recommend').show();
}
function iKnow(){
	$('.recommend').hide();
}

//自适应背景
function resetBg(){
	var winHeight = window.innerHeight;
	var bodyHeight = document.body.scrollHeight;
	if(winHeight >= bodyHeight){
		$(".bg").height(winHeight);
	}else{
		$(".bg").height(bodyHeight);
	}
}
$(function(){
	resetBg();
	$(window).resize(function(){
		resetBg();
	})
});

//获取手机分辨率的高
function getScreenH(){
	var Wheight = window.screen.height;
	if(Wheight == 480){
		$('body').css('margin-top','-15px');
		$('.headpic').css('padding-top','2rem');
		$('.menu-shortcut').css('margin-top','2rem');
	}
};
getScreenH();

//状态传值，前端专用，后端自行删除
sessionStorage.setItem('session_goods','sellerPreview');

sessionStorage.setItem('session_storeconfiger','admin');

sessionStorage.setItem('session_agency','supplier');

 function myAgency() {
	 var daModel = "${daModel}";
	 if (daModel == "1") {
		location.href = "${ctx}/free/agency/${supplierShopNo}/innerDetail.htm"
	} else {
		location.href= "${ctx}/free/agency/${supplierShopNo}/detail.htm";
	}
 }
</script>
</body>
</html>