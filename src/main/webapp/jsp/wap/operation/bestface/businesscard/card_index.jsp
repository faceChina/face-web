<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="share-card.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${icard.nickName}的名片</title>
<link rel="stylesheet" type="text/css"	href="${resourcePath }operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }operation/bestface/fonts/iconfont.css">
<link rel="stylesheet" type="text/css"	href="${resourceBasePath }js/artDialog/skins/myself.css">
<script type="text/javascript" src="${resourceBasePath }js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${resourcePath }/js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath }/js/mob-public.js"></script>
<style type="text/css">
html{
	font-size:10px;
	background:#fff;
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
	font-size:45px;
	display:block;
}
.headpic .header li a.link{
	display:block;
	width:6rem;
	overflow:hidden;
	margin:0 auto;
}
.headpic .header li a.link .img{
	display:block;
	width:6rem;
	height:6rem;
	overflow:hidden;
	border:1px solid #fff;
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
	width:90%;
	margin:2.4rem auto 0;
	overflow:hidden;
}
.menu-shortcut li{
	position:relative;
	float:left;
	width:50%;
	padding:0.8rem 0;
	overflow:hidden;
	background:#fff;
  	border-right:1px solid #eee;
 	border-bottom:1px solid #eee; 
}
.menu-shortcut li:nth-child(2),.menu-shortcut li:nth-child(4){
	border-right:none;
}
.menu-shortcut li:nth-child(3),.menu-shortcut li:nth-child(4){
	border-bottom:none;
}
.menu-shortcut li a{
	display:block;
	width:100%;
	background:rgba(255,255,255,0.5);
	border-radius:100%;
	text-align:center;
	overflow:hidden;
}
.menu-shortcut li a{
	color:#E14A48;
}
.menu-shortcut li + li a{
	 color:#60BD64;
}
.menu-shortcut li + li + li  a{
	  color:#C16EBF;
}
.menu-shortcut li + li + li + li  a{
	 color:#FFC26C;
}
.menu-shortcut li a i{
	display:block;
	font-size:3rem;
	line-height:3rem;
	text-align:center;
}
.menu-shortcut li a em{
	display:block;
	line-height:25px;
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
	margin:0rem auto 0;
	overflow:hidden;
	border-top:1px solid #ddd;
}
.fot-menu ul{
	display:table;
	width:100%;
}
.fot-menu ul li{
	display:table-cell;
	width:33.33%;
	height:3rem;
	text-align:center;
	line-height:3rem;
	vertical-align:middle;
}
.fot-menu ul li a{
	display:block;
	font-size:16px;
	veritical-align:middle;
}
.fot-menu ul li a i{
	display:inline-block;
	width:4.5rem;
	height:2.2rem;
	margin-top:15px;
	background:#01CC00;
	border-radius:30px;
	font-size:2rem;
	line-height:2.2rem;
	color:#fff;
	veritical-align:middle;
}

/* 提示 */
.m-art{
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	text-align: center;
	display: none;
	z-index: 99999;
}
.m-art .m-artbox{
	display: -webkit-box;
	height: 100%;
	width: 100%;
	max-width: 80%;
	margin: auto;
	-webkit-box-align:center; 
	-webkit-box-pack:center;

}
.m-art .m-artinfo{
	background: rgba(0,0,0,.9);
	display: inline-block;
	padding: 12px;
	color: #fff;
	border-radius: 4px;
}
.m-art-click{
	background-color: rgba(0,0,0,.5);
}
.m-art-click .m-artinfo{
	background-color: rgba(255,255,255,.9);
	color: #333;
}
.m-art .m-artinfo .title{
	display: block;
	font-size: 16px;
	margin-bottom: 8px;
}


</style>
</head>
<body>
<c:choose>
	<c:when test="${'1'==isMaster}"><c:set var="user" value="我" /></c:when>
	<c:otherwise><c:set var="user" value="ta" /></c:otherwise>
</c:choose>
<div class="container" style="position:relative;">

	<div class="bg"><img src="${resourcePath }/operation/bestface/img/banner3.jpg" alt=""></div>
	
	<div class="headpic">
		<ul class="header">
			<li>
				<a href="javascript:void(0)"  onclick="showerweima()">
					<i class="iconfont icon-erweima1"></i>
					<em>&nbsp;</em>
				</a>
			</li>
			<li>
				<a href="${ctx }/wap/${shopNo}/any/icard/icard-personInfo-show/${icard.id}${ext }" class="link">
					<span class="img"><img src="${picUrl}${icard.headPicture}" alt=""></span>
				</a>
			</li>
			<li>
				<!--本人跳转到点赞页面，他人点赞  -->
				<a href="<c:choose><c:when test="${'1'==isMaster}">${ctx }/wap/${shopNo}/any/icard/icard-praise-record/${icard.id}${ext }</c:when><c:otherwise>javascript:praise();</c:otherwise></c:choose>">
					<i class="iconfont icon-dianzan"></i>
					<em style="color:#F2FD0D;" praise-number="true">${praiseCount }</em>
				</a>
			</li>
		</ul>
		<p class="name">${icard.nickName}</p>
	</div>
	
	<ul class="menu-shortcut">
		<li>
			<c:if test="${'1'==isMaster}">
				<!-- 未生成 -->
				<c:if test="${1 >= icard.status}"><a href="/wap/${shopNo}/any/itopic/freeshop/${icard.id}${ext}"></c:if>
				<!-- 链接：链接为空，不为空 -->
				<c:if test="${2==icard.type}">
					<c:choose>
						<c:when test="${not empty shopITopic.link}"><a href="${shopITopic.link}"></c:when>
						<c:otherwise><a href="/wap/${shopNo}/any/itopic/freeshop/${icard.id}${ext}"></c:otherwise>
					</c:choose>
				</c:if>
				<!-- 店铺：店铺生成，未生成 -->
				<c:if test="${1==icard.type}">
					<c:choose>
						<c:when test="${2 == icard.status}"><a href="${ctx}/free/index${ext}"></c:when>
						<c:otherwise><a href="/wap/${shopNo}/any/itopic/freeshop/${icard.id}${ext}"></c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
			<c:if test="${'0'==isMaster}">
				<!-- 未生成 -->
				<c:if test="${1 >= icard.status}"><a href="javascript:;" onclick="toShop()"></c:if>
				<!-- 链接 ：链接为空，不为空-->
				<c:if test="${2==icard.type}">
					<c:choose>
						<c:when test="${empty shopITopic.link}"><a href="javascript:;" onclick="toShop()"></c:when>
						<c:otherwise><a href="${shopITopic.link}"></c:otherwise>
					</c:choose>
				</c:if>
				<!-- 店铺 ：店铺生成，未生成 -->	
				<c:if test="${1==icard.type}">
					<c:choose>
						<c:when test="${2 == icard.status}"><a href="${ctx}/wap/${iShopNo}/any/list${ext}"></c:when>
						<c:otherwise><a href="javascript:;" onclick="toShop()"></c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
				<span>
					<i class="iconfont icon-tadeshangcheng"></i>
					<em>${user}的商城</em>
				</span>
			</a>
		</li>
		<li>
			<c:choose>
				<c:when test="${'0'==isMaster && empty companyITopicDto}">
					<a href="javascript:;" onclick="toShop()">
				</c:when>
				<c:otherwise>
					<a href="${ctx }/wap/${shopNo}/any/itopic/company/${icard.id}${ext }">
				</c:otherwise>
			</c:choose>
				<span>
					<i class="iconfont icon-tadegongsi"></i>
					<em>${user}的公司</em>
				</span>
			</a>
		</li>
		<li>
			<a href="${ctx }/wap/${shopNo}/any/icard/icard-personInfo-show/${icard.id}${ext }">
				<span>
					<i class="iconfont icon-tademingpian"></i>
					<em>${user}的名片</em>
				</span>
			</a>
		</li>
		<li>
			<!-- 本人可以点击查看访问自己的粉丝 --><!-- 他人只能看，不能点 -->
			<c:choose>
				<c:when test="${'1'==isMaster}"><a href="${ctx }/wap/${shopNo}/any/icard-collect/my-fans/${icard.id}${ext}"></c:when>
				<c:otherwise><a href="javascript:;" onclick="toCollection()"></c:otherwise>
			</c:choose>
				<span>
					<i class="iconfont icon-tadefensi"></i>
					<em>${user}的粉丝(<var id="fanscount">${fansCount}</var>)</em>
				</span>
			</a>
		</li>
	</ul>
	
	<div class="fot-menu">
		<ul>
			<c:if test="${'1'==isMaster}">
				<li>
					<a href="/wap/${shopNo}/any/itopic/freeshop/${icard.id}${ext}" id="j-openstore">
					<c:choose>
						<c:when test="${2 == icard.status}">店铺配置</c:when>
						<c:otherwise>我要开店</c:otherwise>
					</c:choose>
					</a>
				</li>
				<li><a href="<c:choose><c:when test="${empty icard.cell}">#</c:when><c:otherwise>tel:${icard.cell}</c:otherwise></c:choose>">
					<i class="iconfont icon-dianhua"></i></a>
				</li>
				<li><a data-type="share" id="j-fenxiang">分享求收藏</a></li>
			</c:if>
			
			<!--链接要改的  -->
			<c:if test="${'0'==isMaster}">
				<li>
					<c:choose>
						<c:when test="${!isHasIcard }"><a id="j-fenxiang">我也要一个</a></c:when>
						<c:otherwise><a href="${ctx }/wap/${userICard.shopNo}/any/icard/my-card/${userICard.id}${ext}"  id="j-mycard">我的名片</a></c:otherwise>
					</c:choose>
				</li>
				
				<li>
					<a href="<c:choose><c:when test="${empty icard.cell}">#</c:when><c:otherwise>tel:${icard.cell}</c:otherwise></c:choose>">
					<i class="iconfont icon-dianhua"></i></a>
				</li>
				<li>
					<a data-type="share" id="j-collect">
						<c:choose>
							<c:when test="${1 == isCollect}">取消收藏</c:when>
							<c:otherwise>收藏ta</c:otherwise>
						</c:choose>
					</a>
				</li>
			</c:if>
		</ul>
	</div>
	
</div>

<div id="j-ewm" style="display:none;">
	<img src="${picUrl}${icard.twoDimensionalCode}" alt="" style="display:block;width:100%;margin:0 auto;">
	<p>扫一扫上面的二维码图案，访问我的名片</p>
</div>

<!--关注-->
<div class="recommend" style="display:none;" id="j-recommend">
	<div class="recommend-info">
<c:choose>
	<c:when test="${'0'==isMaster}">	
	<!-- 未关注里跳提醒 -->
		<h4>您还没有关注公众号吆！</h4>
		<p>1. 点击右上角的<em>● ● ●</em></p>
		<p>2. 查看公众号<i class="iconfont icon-subscribe"></i></p>
		<p>3. 如已关注，请重新关注！</p>
	</c:when>
	<c:otherwise>
	<!--分享求收藏-->
		<h4>分享给朋友！</h4>
		<p>1. 点击右上角的<em>● ● ●</em></p>
		<p>2. 点击<i class="iconfont icon-fenxiang" style="padding:0 0.5em;"></i><i class="iconfont icon-fenxiangpengyouquan"></i></p>
	</c:otherwise>
</c:choose>
	<button type="button" class="btn btn-block btn-default">知道了</button>
	</div>
</div>

<script type="text/javascript" src="${resourceBasePath }/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/artDialog/jquery.artDialog.js"></script>

<script type="text/javascript">
//判断访问页面的设备类型,字体随屏幕大小的变化而变化
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
})


/**
 * 验证提示 自动消失
 * @param  {[str]} txt  [提示文字]
 * @param  {[boolean]} evt  [关闭方式，true:点击关闭；false:自动消失]
 * @param  {[number]} time [自动消失的时间]
 */
function artTip(txt,evt,time){
	var str = '<div class="m-art"><div class="m-artbox"><span class="m-artinfo">错误提示！</span></div></div>',
	artEl = $('.m-art'),
	time = 1500,
	evtStyle = false;
	for(var i=1;i<arguments.length;i++){
		if(typeof arguments[i] == 'number'){
			time = arguments[i];
		}else if(typeof arguments[i] == 'boolean'){
			evtStyle = evt;
		}
	}
	if(artEl.length == 0){
		$('body').append(str);
		artEl = $('.m-art');
	}
	if(artEl.is(':hidden')){
		artEl.find('.m-artinfo').html(txt);
		artEl.slideDown();
		if(evtStyle){
			artEl.addClass('m-art-click');
			$('.m-art-click').click(function(){
				artEl.slideUp();
			})
		}else{
			setTimeout(function(){
				artEl.hide();
		},1500);
		}	
	}
};

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
})


//显示二维码
/* function showerweima(){
	art.dialog({
		lock:true,
		title:"二维码",
		content:document.getElementById("j-ewm")
	});
}; */
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



function praise(){	
	preloader.showWhite();
	$.post("${ctx}/wap/${shopNo}/any/icard/icard-praise/${icard.id}${ext}",{}, function(data){
		preloader.colse();
		if(data == 1){
			var praiseEl = $('[praise-number="true"]');
			var number = parseInt(praiseEl.text());
			praiseEl.text(++number);
			
			alertFun('点赞成功');
		}else if(data == 2){
			alertFun('不能给自己点赞哦');
		}else if(data == 3){
			alertFun('已经赞过啦');
		}else{
			alertFun('出错了');
		}
	});
}

//关注、分享求收藏
$('#j-fenxiang').click(function(){
	urlOrTankuang();
});

function recommendBind(){
	$('.recommend button').click(function(){
		$('.recommend').hide();
	});
}

function urlOrTankuang(){
	if('0'=="${isMaster}" && ''!="${guideUrl}"){
		location.href="${guideUrl}";
	}else{
		$("#j-recommend").show();
		recommendBind();
	}
}

//TA的商城增加未设置商城提示信息
function toShop(){
	artTip('此栏目内容还没有设置哦，请稍后访问！');
};

function toCollection(){
	artTip('你没有权限看ta的粉丝噢！');
};

var isHasIcard = ${isHasIcard};
//收藏
$('#j-collect').click(function(){
	//未关注公众号的时候
	if (!isHasIcard){
		urlOrTankuang();
	}
	//已关注公众号的时候
	if (isHasIcard){
	 	collect();
	}
});

function collect(){
	preloader.showWhite();
	var fansCount = +$('#fanscount').text();
	//alert(fansCount)
	
	$.post("${ctx}/wap/${shopNo}/any/icard-collect/del-collection/${userICard.id}/${icard.id}${ext}",{}, function(data){
		preloader.colse();
		if(data == 1){
			alertFun('收藏成功！');
			fansCount += 1; 
			$('#fanscount').text(fansCount);
			$('#j-collect').text("取消收藏");
		}else if(data == 0){
			alertFun('取消成功！');
			fansCount -= 1; 
			$('#fanscount').text(fansCount);
			$('#j-collect').text("收藏ta");
		}else{
			alertFun('出错了');
		}
});
}

</script>
</body>
</html>