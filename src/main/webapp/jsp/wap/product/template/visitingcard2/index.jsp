<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<%@include file="../../../common/base.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${user.nickname }的个人名片</title>
<link rel="stylesheet" type="text/css" href="${resourcePath }styles/public.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourcePath }template/page/visitingcard2/css/main.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourcePath }template/page/visitingcard2/css/animate.min.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourceBasePath }js/artDialog/skins/myself.css">
<c:if test="${card.picType == 2 }">
	<style type="text/css">
		#visttincard .vc-header{
			background-image:url(${picUrl}${card.zoomBackgroundPic});
			background-size:100%;
		}
	</style>
</c:if>
<c:if test="${card.picType == 1 }">
	<style type="text/css">
		#visttincard .vc-header{
			background-image:url(${card.defaultPicForWap});
			background-size:100%;
		}
	</style>
</c:if>
<c:if test="${ empty card.backgroundPic }">
	<style type="text/css">
		#visttincard .vc-header{
			background-image:url(${resourcePath }template/page/visitingcard/img/defaultImg/bg10_640.jpg);
			background-size:100%;
		}
	</style>
</c:if>
</head>
<body>

<div id="visttincard">
	
	<div class="vc-header">
		<div class="vc-header-opacity"></div>
		 <div class="userheader">
			 <a  href="${shopUrl }" class="uh-shopdefault animated fadeInLeftBig" ><i class="iconfont icon-jinrudianpu"></i></a>
			 <a class="uh-a-img animated rotateIn">
				<c:choose>
       	  			<c:when test="${empty user.headimgurl }">
       	  				<img src="${resourcePath }template/page/visitingcard/img/userheader.png">
       	  			</c:when>
       	  			<c:otherwise>
		       	  		<img src="${picUrl}${user.headimgurl }">
       	  			</c:otherwise>
       	  		</c:choose>
			 </a>
			 <a class="uh-qrcode animated fadeInRightBig" onclick="showerweima();"><i class="iconfont icon-erweima3"></i></a>
		 </div>
		 <div class="userinfo animated fadeInDownBig">
			 <a class="ui-name">${user.nickname }<c:if test="${user.sex == 1 }"><i class="iconfont icon-nan"></i></c:if><c:if test="${user.sex == 2 }"><i class="iconfont icon-nv"></i></c:if></a>
			 <c:if test="${not empty card.industryName }">
			 	<span class="ui-dl">${card.industryName }</span>
			 </c:if>
		 </div>
		 <div class="userother animated fadeInDownBig none">
		 	<p>为了以后的更多合作希望您能</p>
		 	<p>添加我的联系方式</p>
		 </div>
		 
	</div>
	<div class="vc-content">
	   <div class="vc-c-shiguanline">
	   	 <a class="sg-round round1 animated fadeInDownBig"></a>
	   	 <a class="sg-round round2 animated fadeInLeftBig"></a>
	   	 <a class="sg-round round3 animated fadeInUpBig"></a>
	   	 <div class="sg-line"></div>
	   </div>
	   <div class="vc-c-companyinfo">

			<ul class="companyinfo">
				<div class="lifont animated fadeInLeft"><span>公司名称</span>
					<samp>
						<c:choose>
							<c:when test="${empty card.companyName }">他还没有填写公司名称</c:when>
							<c:otherwise>${card.companyName }</c:otherwise>
						</c:choose>
					</samp>
				</div>
				<div class="line"></div>
				<div class="lifont animated fadeInRight"><span>公司职务</span>
					<samp>
						<c:choose>
							<c:when test="${empty card.position }">他还没有填写职位</c:when>
							<c:otherwise>${card.position }</c:otherwise>
						</c:choose>
					</samp>
				</div>
				<div class="line"></div>
				<div class="lifont animated fadeInLeft"><span>刷脸号</span>
					<samp>
						<c:choose>
							<c:when test="${empty user.myInvitationCode }">他还没有设置脸谱号</c:when>
							<c:otherwise>${user.myInvitationCode }</c:otherwise>
						</c:choose>
					</samp>
				</div>
				<div class="line"></div>
				<div class="lifont animated fadeInRight"><span>联系电话</span>
				
					<c:if test="${not empty card.phone }">
						<a  href="tel:${card.phone }" class="shopiphone animated flash">
							<i class="iconfont icon-daohangdianhua"></i>
						</a>
					</c:if>
					
					<samp class="lable-iphone">
						<c:choose>
							<c:when test="${showPhone }">
								<c:if test="${empty card.phone }">未设置</c:if>
								<c:if test="${not empty card.phone }">
									${card.phone }
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${card.phoneVisibility == 2 }">
									成为好友后可见
								</c:if>
								<c:if test="${card.phoneVisibility == 3 }">
									未公开
								</c:if>
							</c:otherwise>
						</c:choose>
					</samp>
				</div>
				<div class="companyinfobg"></div>
			</ul>
			<ul class="supplydemandinfo">
				<li class="lifont supply animated fadeInUp">
					<a class="supply-iconfont"><i class="iconfont icon-gong"></i></a>
					<span class="supply-.companyinfo li.lifont label">
						<samp class="txt-rowspan3">
							<c:choose>
								<c:when test="${empty card.industryProvide }">他还没有填写可供信息。</c:when>
								<c:otherwise>
									${card.industryProvide }
								</c:otherwise>
							</c:choose>
						</samp>
					</span>
				</li>
				<li class="line"></li>
				<li class="lifont demand  animated fadeInUp"><a  class="demand-iconfont"><i class="iconfont icon-qiu"></i></a>
					<span class="demand-info">
							<samp class="txt-rowspan3">
								<c:choose>
									<c:when test="${empty card.industryRequirement }">他还没有填写需求信息。</c:when>
									<c:otherwise>${card.industryRequirement }</c:otherwise>
			       	  			</c:choose>
			       	  		</samp>
					</span></li>
				
				<div class="companyinfobg"></div>
			</ul>
			<div class="contactinfo">
				<a href="/wap/${toChatShopNo}/im/cardChat/${user.id}.htm" class="animated slideInLeft">与我联系</a>
				<a href="${shareUrl }" class="animated slideInRight">申请成为代理</a>
				<a href="/app/download.htm" class="animated slideInLeft">创建我的刷脸名片</a>
			</div>
			<div class="logobox">
			    	<img src="${resourcePath }template/page/visitingcard2/img/logo.png" alt=""> 
			</div>
		</div>
	</div>
	<c:if test="${card.picType == 2 }">
		<img  src="${picUrl}${card.zoomBackgroundPic}" class="companyopacity" id="companyopacity" />
	</c:if>
	<c:if test="${card.picType == 1 }">
		<img  src="${card.defaultPicForWap}" class="companyopacity" id="companyopacity" />
	</c:if>
	<c:if test="${ empty card.backgroundPic }">
		<img  src="${resourcePath }template/page/visitingcard/img/defaultImg/bg10_640.jpg" class="companyopacity" id="companyopacity" />
	</c:if>
	<canvas  id="canvas"></canvas></div>
</div>
<div id="QRCodeBox" class="none">
	<span><img src="${picUrl }${qrCode }" alt=""></span>
	<p>扫一扫上面的二维码，加我刷脸</p>
</div>
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourcePath}js/fsize.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="${resourcePath}js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath }js/mob-public.js?ver=008"></script>
<script type="text/javascript" src="${resourcePath }template/page/visitingcard2/js/StackBlur.js?ver=008"></script>
<script type="text/javascript">

	//显示二维码
	var jewmDialog=0,
		isShow=false,
	 showerweima=function(){
		jewmDialog=artDialogNoBtComfirm("QRCodeBox");
		isShow=true;
	};
	$("body").click(function(){
		if (!isShow) {
			jewmDialog.close();
		}
		isShow=false;
	});
	// ios
	if (isiOSAndAndroid()) {
		$(".vc-c-companyinfo .supplydemandinfo li a").css("margin-top","0.3rem");
		$(".vc-c-companyinfo .supplydemandinfo li a.demand-iconfont").css("margin-top","0.2rem");
	}
	

	$(function(){
		var vH=$("#visttincard").height();
		$("#companyopacity").css("height",vH);
		stackBlurImage( 'companyopacity', 'canvas', 160, false );
	});
</script>
</body>
</html>