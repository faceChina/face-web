<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>首页-脸谱中国</title>
		<script type="text/javascript" src="${resourceBasePath}js/jquery-1.8.3.min.js"></script>
		<link href="${resourcePath}/css/zjlp_index/common.css" rel="stylesheet" type="text/css" />
		<link href="${resourcePath}/css/zjlp_index/index.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${ctx }/company/img/login/favicon.ico" type="image/x-icon">
	</head>
	<body>
		<div class="layout login">
			<div class="tar main-body">
				<a href="/login.htm" target="_blank">登录</a><span>|</span>
				<a href="/company/join.jsp" target="_blank">注册</a>
			</div>
		</div>
		<div class="layout nav">
			<div class="mod-nav main-body">
				<div class="logo l">
					<a href="/"><img src="${resourcePath}/img/zjlp_index/logo.png" alt=""></a>
				</div>
				<div class="nav-list">
		            <a href="/" class="active">首页</a>
		            <a href="idea.html">刷脸理念</a>
		            <a href="product.html">刷脸产品</a>
		            <a href="case.html">成功案例</a>
		            <a href="/information/index.htm">资讯中心</a>
		        </div>
		        <div class="phone r">
		        	<b class="phone-icon">400-000-3777</b>
		        </div>
			</div>
		</div>
		
		<div class="layout banner-new">
			<div class="mod-banner main-body">
				<div class="inner banner-bd-new tac">	
					<div class="d-qrcode">
						<img src="${resourcePath}/img/zjlp_index/code.jpg" />
					</div>
					<div class="app-download">
						<div class="download-btn d-iphone-new">
							<img src="${resourcePath }/img/zjlp_index/icon-iphone.png" />
							<a href="https://itunes.apple.com/cn/app/shua-lian/id1009103031?l=en&mt=8" target="_blank">iPhone版</a>
						</div>
						<div class="download-btn d-android-new">
							<img src="${resourcePath }/img/zjlp_index/icon-android.png" />
							<a href="http://m.o2osl.com/app/bestface.apk" target="_blank">Android版</a>
						</div>
						<p class="txt">[扫描下载刷脸APP]</p>
					</div>
				</div>
			</div>
			<img class="banner-lt" src="${resourcePath }/img/zjlp_index/banner_b3.png">
			<img class="banner-ft" src="${resourcePath }/img/zjlp_index/banner_b2.jpg" width="100%" />
		</div>
		
		<div class="main">
			<div class="layout bg-c">
				<div class="notice">
					<span class="l">系统公告</span>
					<div class="l"><!--  id="scrollup" -->
						<ul>
							<li  data-list="0">●<a style="text-decoration:none;">刷脸最新版本上线<i class="notice-new">NEW</i></a></li>
							
						</ul>
					</div>
				<!-- 	<div class="r"><a href="notice.html">更多>></a></div> -->
				</div>
			</div>
		
			<div class="platform-box">
				<h2 class="tac title">平台理念</h2>
				<div class="platform">
					<a>
						<!-- <img src="img/platform-1.png" alt="互联网4.0时代"/> -->
						<div class="m-img"></div>
						<h3>互联网4.0时代</h3>
						<p>员工强则老板强，老板</br>强则企业强</p>
					</a>
					<a>
						<!-- <img src="img/platform-2.png" alt="去中心化"/> -->
						<div class="m-img"></div>
						<h3>去中心化</h3>
						<p>把原来的大中心化打散，让每个</br>企业都拥有自己的中心</p>
					</a>
					<a>
						<!-- <img src="img/platform-3.png" alt="泛平台化"/> -->
						<div class="m-img"></div>
						<h3>泛平台化</h3>
						<p>每个老板员工都拥有自己平台</br>一个手机就是一个平台</p>
					</a>
					<a>
						<!-- <img src="img/platform-4.png" alt="圈子传播"/> -->
						<div class="m-img"></div>
						<h3>圈子传播</h3>
						<p>以人为中心的平台，以信任为基</br>础向自身的圈子进行有效传播</p>
					</a>
				</div>
			</div>
			
			<div class="video bg-c">
				<h2 class="tac title">脸谱视频</h2>
				<div class="mod-video main-body">
					<div class="item tac" data-src="17a85169f8&vu=acedf71a99"><img src="${resourcePath}/img/zjlp_index/video-4.jpg" alt="打造老板阶层的交往与生意">
					<p>打造老板阶层的交往与生意</p>
					</div>
					<div class="item tac" data-src="17a85169f8&vu=e2f54899ad"><img src="${resourcePath}/img/zjlp_index/video-5.jpg" alt="8月8日千人路演活动全程现场"><p>8月8日千人路演活动全程现场</p></div>
					<div class="item tac" data-src="17a85169f8&vu=0f0bb42bc0"><img src="${resourcePath}/img/zjlp_index/video-6.jpg" alt="众品言明并-脸谱中国"><p>众品言明并-脸谱中国</p></div>
				</div>
			</div>
		
			</div>
			<div class="index-news">
				<div class="mod-news">
					<h2 class="tac title">资讯中心</h2>
					<c:if test="${informationFirst != null }">
						<div class="news-list main-body clearfix">
							<div class="news-l news-big l">
								<a href="${cxt }/information/details/${informationFirst.id}${ext}" target="_blank">
									<img src="
									<c:choose>
										<c:when test="${informationFirst.picPath==null || informationFirst.picPath==''}">
											${resourcePath}/img/zjlp_index/img-default.jpg
										</c:when>
										<c:otherwise>
											${picUrl}${informationFirst.picPath}
										</c:otherwise>
									</c:choose>
									" alt="" style="width: 406px;height: 200px">
								</a>
								<a class="h" href="${cxt }/information/details/${informationFirst.id}${ext}" target="_blank">${informationFirst.title }</a>
								<p class="txt-rowspan3">${informationFirst.summary }</p>
							</div>
							<div class="news-r news-item l">
								<ul>
									<c:forEach items="${informationList}" var="information" varStatus="status">
										<c:choose>
											<c:when test="${status.index == 0 }"></c:when>
											<c:otherwise>
												<li>
													<p class="time tac l">
														<b>
															<fmt:formatDate value="${information.publishTime }" pattern="yyyy-MM"/>
														</b>
														<fmt:formatDate value="${information.publishTime }" pattern="dd"/>
													</p>
													<p class="info">
														<a class="h" href="${cxt }/information/details/${information.id}${ext}" target="_blank">
															<b>${information.title }</b>
														</a>
														<span class="digest txt-rowspan3">${information.summary }</span>
													</p>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			
		</div>
		
		<div class="footer">
			<div class="foot">
				<div class="l mt10">
					<img src="${resourcePath}/img/zjlp_index/logo2.png" alt="">
				</div>
				<div class="l about ml15">
					<a href="about.html">关于我们</a> | <a href="protocol.html">服务协议</a>
					<p>浙江脸谱科技有限公司 @版权所有 浙ICP备14042086号</p>
					<p>地址：浙江省杭州市市民中心D座11楼</p>
					<p>友情链接：<a href="http://m.ickd.cn" target="_blank">快递查询</a></p>
				</div>
				<div class="r complain">
					<img src="${resourcePath}/img/zjlp_index/code1.jpg"  class="l">
					<div class="l tel">
						<p>投诉建议</p>
						<h3>400-000-3777</h3>
					</div>
				</div>
			</div>
		</div>
		
		<section class="playvideo" >
			<div class="video-cancel close r">×</div>
			<div class="video-box">
			</div>
		</section>
		<script type="text/javascript">
			var HtmlVideo = (function(){
				function HtmlVideo(opt){
					var element = this.element = opt.id || '';
			
					function init(){
						bindEvent();
					}
			
					function bindEvent(){
			
						element.addEventListener("click",playPause);
					}
			
					function playPause(){
						if (element.paused){
						  element.play(); 
						}else {
						  element.pause(); 
						}
					}
			
					function makeBig (){
						this.element.width = '100%'
					}
			
					init();
			
					return {
						play:playPause
					}
				}
			
				return HtmlVideo
			
			})();
			
			function createVideo(src){
				var videoDom =  document.createElement('video');
				var source = document.createElement('source');
			
				videoDom.appendChild(source);
				videoDom.id = 'video1';
				videoDom.controls = 'controls';
				source.src = src;
			
				$('.video-box').append(videoDom);
			}
			
			
			$('.mod-video .item').on('click',function(){
				var $videobox=$(".video-box"),
				    htmlFlash='<embed src="http://yuntv.letv.com/bcloud.swf" allowFullScreen="true" quality="high"  width="960" height="500" align="middle" allowScriptAccess="always" flashvars="uu='+$(this).data("src")+'&auto_play=1&gpcflag=1&width=960&height=500" type="application/x-shockwave-flash"></embed>';
				 $videobox.html('').html(htmlFlash);
				 console.log(htmlFlash)
				$('.playvideo').show();
			
			})
			
			$('.video-cancel').on('click',function(){
				$('.playvideo').hide();
			})
			
			
				
			/*新闻滚动*/
			function scrollUp(){
				var $this = $("#scrollup");
				var scrollTimer;
				$this.hover(function(){
				clearInterval(scrollTimer);
				},function(){
				scrollTimer = setInterval(function(){
						 scrollNews( $this );
					}, 5000 );
				}).trigger("mouseleave");
			}
			function scrollNews(obj){
				var $self = obj.find("ul:first"); 
				var lineHeight = $self.find("li:first").height(); //获取行高
				$self.animate({ "marginTop" : -lineHeight +"px" }, 1000 , function(){
					$self.css({marginTop:0}).find("li:first").appendTo($self); //appendTo能直接移动元素
				})
			}
			
			$(function(){
			
				//新闻滚动
				scrollUp();
				//刷脸APP下载
				bannerLeft();
				
				$("#j-case ul li").each(function(index){
					$(this).click(function(){
						$("#j-case-bd ul").removeClass("arrow");
						$("#j-case-bd ul").eq(index).addClass("arrow");
					})
				})
			})
			
			var i=0;
			$("#j-case ul li").each(function(index){
				$(this).click(function(){
					$("#j-case-bd ul").removeClass("arrow");
					$("#j-case ul li").removeClass("current");
					$("#j-case-bd ul").eq(index).addClass("arrow");
					$("#j-case ul li").eq(index).addClass("current");
					i=index;
				})
			})
			
			function autoplay(){
				var len=$("#j-case ul li").length;
				$("#j-case ul li").removeClass("current");
				$("#j-case ul li").eq(i).addClass("current");
				$("#j-case-bd ul").removeClass("arrow");
				$("#j-case-bd ul").eq(i).addClass("arrow");
				if(i++ > len-2){
					i=0;
				}
			}	
			setInterval(autoplay,3000);
			
			/*刷脸APP下载*/
			function bannerLeft(){
				var leftWidth = ($('.mod-banner').offset().left)*0.45;
				if(document.body.offsetWidth > 1060){
					$('.banner-bd-new').css('left', leftWidth + 'px');
				}else{
					$('.banner-bd-new').removeAttr('style');
				}
			}
			
			window.onresize = function () {
				bannerLeft();
			}
		</script>
		<script src="${resourcePath}/js/common.js"></script>
		<script type="text/javascript" src="${resourceBasePath}js/bdcnzztj.js"></script>
	</body>
</html>