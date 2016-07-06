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
		<title>会员中心</title>
		<%@include file="../../common/top.jsp"%>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/main.css">
	</head>
	<body>
		<c:choose>
			<c:when test="${not empty memberCardDto}">
				<div id="box">
					<div class="group group-cleartop member-card">
						<div class="card">
							<c:choose>
								<c:when test="${memberCardDto.cardImgPath != null && memberCardDto.cardImgPath != '' && !fn:contains(memberCardDto.cardImgPath, 'resource/') }">
									<img src="${picUrl }${memberCardDto.cardImgPath }" alt="">
								</c:when>
								<c:when test="${memberCardDto.cardImgPath != null && memberCardDto.cardImgPath != '' && fn:contains(memberCardDto.cardImgPath, 'resource/') }">
									<img src="${ctx }${memberCardDto.cardImgPath}" alt="">
								</c:when>
								<c:otherwise>
									<img src="${resourcePath }operation/member/img/pic-01.png" alt="">
								</c:otherwise>
							</c:choose>
							<div class="cardname" style="color:${memberCardDto.cardNameColor};">${memberCardDto.levalName }</div>
							<div class="cardcode" style="color:${memberCardDto.cardNoColor}">卡号：${memberCardDto.memberCard }</div>
						</div>
						<div class="info">微时代会员卡，方便携带收藏，永不挂失</div>
					</div>
					
					<div class="group group-center group-verticalline group-equally" style="padding:10px 0;">
						<div class="group-item">
							<ul class="group-rowspan">
								<li class="group-colspan">
									<a href="/wap/${sessionShopNo }/buy/member/consume/list.htm">
										<span>累积消费金额</span><br/>
										<span style="padding-top:5px;">
											<var class="clr-danger">
												<c:choose>
													<c:when test="${memberCardDto.realConsumptionAccount == null ||  memberCardDto.realConsumptionAccount == ''}">
														0
													</c:when>
													<c:otherwise>
														<fmt:formatNumber value="${memberCardDto.realConsumptionAccount / 100 }" pattern="0.00" />
													</c:otherwise>
												</c:choose>
											</var>元
										</span>
									</a>
								</li>
								<li class="group-colspan">
									<a href="/wap/${sessionShopNo }/buy/member/integral/list.htm">
										<span>我的积分</span><br/>
										<span class="clr-light" style="padding-top:5px;">
											<span id="integral">
												<c:choose>
													<c:when test="${memberCardDto.availableIntegral == null ||  memberCardDto.availableIntegral == ''}">
														0
													</c:when>
													<c:otherwise>
														${memberCardDto.availableIntegral }
													</c:otherwise>
												</c:choose>
											</span>
											分
										</span>
									</a>
								</li>
								<li class="group-colspan">
									<a href="/wap/${sessionShopNo }/buy/member/balance/list.htm">
										<span>卡内余额</span><br/>
										<span class="clr-light"  style="padding-top:5px;">
											<c:choose>
												<c:when test="${memberCardDto.amount == null ||  memberCardDto.amount == ''}">
													0
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${memberCardDto.amount / 100 }" pattern="0.00" />
												</c:otherwise>
											</c:choose>
										元</span>
									</a>
								</li>
							</ul>
						</div>
					</div>
					
					<div class="group width20 group-others group-small">
					<!-- 会员充值功能隐藏 -->
						<%-- <c:if test="${isRecharge == 1 }">
							<div class="group-item">
								<div class="group-rowspan">
									<span class="group-colspan"><i class="iconfont icon-lock clr-light fnt-20"></i></span>
									<span class="group-colspan fnt-16">我要充值</span>
									<span class="group-colspan"><a href="${ctx}/wap/${shop.no}/buy/recharge/list${ext}"  class="btn btn-warning btn-sm">点击充值</a></span>
								</div>
							</div>
						</c:if> --%>
						<div class="group-item">
							<a href="/wap/${sessionShopNo }/buy/member/imformation/add.htm" class="group-rowspan">
								<span class="group-colspan"><i class="iconfont icon-friend clr-light fnt-20"></i></span>
								<span class="group-colspan fnt-16">完善会员资料</span>
								<span class="group-colspan"><i class="iconfont icon-right"></i></span>
							</a>
						</div>
					</div>
					
					<div class="group group-others">
						<div class="group-item">
							<ul class="group-rowspan  width20">
								<li class="group-colspan"><i class="iconfont icon-cloudiconfontwenben clr-light fnt-20"></i></li>
								<li class="group-colspan fnt-16">会员卡说明</li>
								<li class="group-colspan">
								<c:if test="${hasRegistratRule }">
									<button type="button" class="btn btn-health btn-sm"
											<c:if test="${isRegist }">disabled="disabled"</c:if>
										    onclick="toRegistration(this, ${memberCardDto.id}, str,'integral')">
										    <c:choose>
										    	<c:when test="${isRegist }">
										    		签到成功
										    	</c:when>
										    	<c:otherwise>
										    		签到
										    	</c:otherwise>
										    </c:choose>
								    </button>
							     </c:if>
							     </li>
							</ul>
						</div>
						<div class="group-item">
							<ul class="group-rowspan">
								<li class="group-colspan"></li>
								<li class="group-colspan content-details-info" style="color:#666;">${memberEnactment.memberContent }</li>
								<li class="group-colspan"></li>
							</ul>
						</div>
					</div>
					
					<%@include file="../../common/foot.jsp" %>
					<%@include file="../../common/nav.jsp" %>
				</div>
			</c:when>
			<c:otherwise>
				<div id="box">
					您暂时还没有获取会员卡！
				</div>
			</c:otherwise>
		</c:choose>
	
		<script type="text/javascript">
			var str;
			$(function(){
				str = '签到成功';
				//如果初次领卡，并且有赠送积分，则显示提示
				var sendIntegral = '${sendIntegral}';
				if (null != sendIntegral && '' != sendIntegral) {
					artTip("恭喜，店家赠送您"+sendIntegral+"积分，赶紧看看吧！", true);
				}
			})
			/**
			 * 自定义分享按钮
			 */ 
			function imgPath() {
				path = "${memberCardDto.cardImgPath}";
				if(path != null && path != '' && path.indexOf('resource/m/') == -1) {
					path = "${picUrl }"+path;
				} else if(path != null && path != '' && path.indexOf('resource/m/') != -1) {
					path = "${ctx }"+path;
				} else {
					path = "${resourcePath }operation/member/img/pic-01.png";
				}
				return path;
			}
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
			   /*
			   	config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
			   	则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			   	*/
		  		wx.onMenuShareAppMessage({
		      		title:'${shop.name}',
			  		link: '${WgjUrl }/wap/${shop.no}/any/member/subscribe${ext}', // 分享链接
		      		imgUrl: imgPath(),
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
				  	link: '${WgjUrl }/wap/${shop.no}/any/member/subscribe${ext}', // 分享链接
			      	imgUrl: imgPath(),
			    	success: function () { 
			        	//用户确认分享后执行的回调函数
				    },
				    cancel: function () { 
				        //用户取消分享后执行的回调函数
				    }
				});
			});
		</script>
		<%@include file="../../common/sign.jsp"%>
	</body>
</html>