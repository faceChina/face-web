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
<link rel="stylesheet" type="text/css" href="${resourcePath }template/page/visitingcard/css/main.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourcePath }js/swiper/css/swiper.min.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }js/animate/animate.min.css">
<link rel="stylesheet" type="text/css" href="${resourceBasePath }js/artDialog/skins/myself.css">
<c:if test="${card.picType == 2 }">
	<style type="text/css">
		section.swiper-slide{
			background:url(${picUrl}${card.zoomBackgroundPic}) no-repeat left top;
			background-size: 100% 100%;
		}
	</style>
</c:if>
<c:if test="${card.picType == 1 }">
	<style type="text/css">
		section.swiper-slide{
			background:url(${card.defaultPicForWap}) no-repeat left top;
			background-size: 100% 100%;
		}
	</style>
</c:if>
<c:if test="${ empty card.backgroundPic }">
	<style type="text/css">
		section.swiper-slide{
			background:url(${resourcePath }template/page/visitingcard/img/defaultImg/bg1_640.jpg) no-repeat left top;
			background-size: 100% 100%;
		}
	</style>
</c:if>
</head>
<body>
<div class="swiper-container">
    <div class="swiper-wrapper">
       <section class="swiper-slide">
       	  <div class="header ani resize" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s" >
       	   <div class="userinfo">
	       	  	<div class="ui-header ani resize" swiper-animate-effect="fadeInDown" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">
	       	  		<c:choose>
	       	  			<c:when test="${empty user.headimgurl }">
	       	  				<img src="${resourcePath }template/page/visitingcard/img/userheader.png">
	       	  			</c:when>
	       	  			<c:otherwise>
			       	  		<img src="${picUrl}${user.headimgurl }">
	       	  			</c:otherwise>
	       	  		</c:choose>
	       	  		<c:if test="${user.sex == 1 }">
		       	  		<span class="sex-icon"><i class="iconfont icon-nan"></i></span>
	       	  		</c:if>
	       	  		<c:if test="${user.sex == 2 }">
		       	  		<span class="sex-icon"><i class="iconfont icon-nv"></i></span>
	       	  		</c:if>
	       	  	</div>
				<div class="ui-name">${user.nickname }</div>
				<div class="ui-position">
					<c:if test="${not empty card.position }">
						<span class="ui-p-name">
							${card.position }
						</span>
					</c:if>
					<c:if test="${not empty card.industryName }">
						<span class="ui-p-dl">
							${card.industryName }
						</span>
					</c:if>
				</div>
				<a class="a-qrcode" onclick="showerweima();"><i class="iconfont icon-erweima"></i></a>
			</div>
			<div class="companyinfo">
				<div class="ci-left ani resize" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s"  >
					<div class="ci-l-name">
						<c:choose>
							<c:when test="${empty card.companyName }">他还没有填写公司名称</c:when>
							<c:otherwise>${card.companyName }</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${not empty user.myInvitationCode }">
						<div class="ci-l-slaccount">刷脸号：
								${user.myInvitationCode }
						</div>
					</c:if>
					<div class="ci-l-iphone">电话：<c:choose>
							<c:when test="${showPhone }">
								<c:if test="${empty card.phone }">未设置</c:if>
								<c:if test="${not empty card.phone }"><a href="tel:${card.phone }">${card.phone }</c:if></a>
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
					</div>
				</div>
				<a href="${shopUrl }"  class="ci-right ani resize" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">
					<i class="iconfont icon-dianpu1"></i>
					<p class="goshopname">
						我的公司
					</p>
				</a>
			</div>
       	  </div>
       	  <div class="content ani resize" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.8s" swiper-animate-delay="0.3s">
       	  		<div class="content-g">
       	  			<div class="content-g-left ani resize" swiper-animate-effect="swing" swiper-animate-duration="0.8s" swiper-animate-delay="0.3s">
       	  				<a>供</a>
       	  			</div>
       	  			<div class="content-g-right ani resize" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="0.3s">
       	  				<span  class="txt-rowspan3 ">
							<c:choose>
								<c:when test="${empty card.industryProvide }">他还没有填写可供信息。</c:when>
								<c:otherwise>
									${card.industryProvide }
								</c:otherwise>
							</c:choose>
						</span>
					</div>
       	  		</div>
       	  		<div class="content-line content-line-top"></div>
       	  		<div class="content-line content-line-bottom"></div>
				<div class="content-q">
					<div class="content-q-left ani resize" swiper-animate-effect="swing" swiper-animate-duration="0.8s" swiper-animate-delay="0.3s">
       	  				<a>求</a>
       	  			</div>
       	  			<div class="content-q-right ani resize" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="0.3s">
	       	  			<span  class="txt-rowspan3 ">
		       	  			<c:choose>
								<c:when test="${empty card.industryRequirement }">他还没有填写需求信息。</c:when>
								<c:otherwise>${card.industryRequirement }</c:otherwise>
		       	  			</c:choose>
		       	  		</span>
					</div>
				</div>
       	  </div>
       	  <div class="swiper-footer"> <img class="nextpage" swiper-animate-delay="0.3s"  src="${resourcePath }template/page/visitingcard/img/nextPage.png" /></div>    	 
       </section>
        <section class="swiper-slide">
       	  <div class="header header1 ani resize" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s" >
       	   <div class="userinfo">
	       	  	<div class="ui-header ani resize" swiper-animate-effect="fadeInDown" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">
	       	  		<c:choose>
	       	  			<c:when test="${empty user.headimgurl }">
	       	  				<img src="${resourcePath }template/page/visitingcard/img/userheader.png">
	       	  			</c:when>
	       	  			<c:otherwise>
			       	  		<img src="${picUrl}${user.headimgurl }">
	       	  			</c:otherwise>
	       	  		</c:choose>
	       	  		<c:if test="${user.sex == 1 }">
		       	  		<span class="sex-icon"><i class="iconfont icon-nan"></i></span>
	       	  		</c:if>
	       	  		<c:if test="${user.sex == 2 }">
		       	  		<span class="sex-icon"><i class="iconfont icon-nv"></i></span>
	       	  		</c:if>
	       	  	</div>
	       	  	<c:if test="${not empty user.myInvitationCode }">
					<div class="ui-account">
					 刷脸号: ${user.myInvitationCode }
					</div>
	       	  	</c:if>
			</div>
			<div class="cooperate ani resize" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">
				<p>为了以后的更多合作</p>
				<p>希望您能添加我的联系方式</p>
			</div>
       	  </div>
       	  <div class="content1">
       	  	  <a class="my-cooperate ani resize" href="/wap/${toChatShopNo}/im/cardChat/${user.id}.htm" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">与我联系</a>
       	  	  <a class="my-cooperate ani resize" href="${shareUrl }" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">与我合作</a>
       	  	  <a class="add-slcard ani resize" href="/app/download.htm" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.8s" swiper-animate-delay="0.5s">创建我的刷脸名片</a>
       	  </div>
       	  <div class="swiper-footer swiper-footer-logo">
       	  	<img src="${resourcePath }template/page/visitingcard/img/logo.png" alt=""> 
       	  </div>
       	 
       </section>
    </div>
</div>
<div id="QRCodeBox" class="none">
	<span><img src="${picUrl }${qrCode }" alt=""></span>
	<p>扫一扫上面的二维码，加我刷脸</p>
</div>
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourcePath }js/dpreview.js"></script>
<script type="text/javascript" src="${resourcePath }js/swiper/js/swiper.min.js"></script>
<script type="text/javascript" src="${resourcePath }js/animate/swiper.animate.min.js"></script>
<script type="text/javascript" src="${resourcePath }template/page/visitingcard/js/visitingcard.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="${resourcePath}js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath }js/mob-public.js?ver=008"></script>

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
</script>
</body>
</html>