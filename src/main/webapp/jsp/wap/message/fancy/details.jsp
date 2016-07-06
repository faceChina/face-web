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
		<title>${fancyMessageItem.name }</title>
		
		
		<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="${resourceBasePath}js/validate/jquery.validate.js"></script>
		<script type="text/javascript" src="${resourcePath}js/mob-public.js"></script>
		<link rel="stylesheet" type="text/css" href="${resourcePath }public/css/main.css">
		<link rel="stylesheet" type="text/css" href="${resourcePath }styles/public.css">
		
	</head>
	<body>
		<div id="box">
			<div class="details">
				<div class="details-title">
					<h3>${fancyMessageItem.name }</h3>
					<h4>
						<span class="time">
							<fmt:formatDate value="${fancyMessageItem.createTime }" pattern="yyyy-MM-dd"/>
						</span>
						<samp>${fancyMessageItem.author}</samp>
					</h4>
				</div>
				<div class="details-center">
					${fancyMessageItem.content}
					<!--  
					<p>HI，大家好：很长时间没有上传gif分享了，因为楼主这段时间忙于参加康佳的手机主题比赛。</p>
					<p><img src="../img/details.jpg"/></p>
					<p>在这里说明一下，这个是国外的优秀设计分享，不是楼主的作品。至于制作软件吗，看设计者的喜好，有ps或是ai又或... 是3D、A</p>
					-->
					<!--
						<a href="">阅读全文&gt;&gt;</a> 
					-->
				</div>
			</div>
		</div>
		<div class="footer-readpraise">
			<div class="read"><span>阅读</span><span class="rp-num">${fancyMessageItem.pageViews}</span></div>
			<a class="a-praise" data-id="praise"><i class="iconfont icon-weizan101"></i><span class="praise-num rp-num">${fancyMessageItem.likeNum}</span></a>
		</div>
		<script type="text/javascript">
			$(function(){
				var id="${fancyMessageItem.id }",
					getcId=getCookie(id),
					$apraise=$("a[data-id='praise']"),
					$iconfontzan=$apraise.children(".iconfont");
				if (getcId!="") {
					$iconfontzan.removeClass("icon-weizan101");
					$iconfontzan.addClass("icon-yizan101");
				}
				$apraise.off().on("click",function(){
					var $obj=$(this);
					getcId=getCookie(id);
					if (getcId!="") {
						artTip("您已经点赞过了！");
					}
					else{
						setCookie(id,id,1000);
							$.ajax({
			                    type: "POST",
			                    url:'${ctx}/message/fancy/like.htm?id=' + ${fancyMessageItem.id },  
			                    dataType: "json",
			                    success: function(data){
			                    	if(data.success){
				                    	var $praisenum=$obj.children(".praise-num"),
					    					$iconfontzan=$obj.children(".iconfont"),
					    					num=parseInt($praisenum.text())+1;
					    					$praisenum.text(num);
					    					$iconfontzan.removeClass("icon-weizan101");
					    					$iconfontzan.addClass("icon-yizan101");
			                    	}else{
				                    	artTip("点赞失败！");
			                    	}
			                    },error:function(){
			                    	artTip("点赞失败！");
								}
			                });
					}	
				});
			});
		</script>
	</body>
</html>

