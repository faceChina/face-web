<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<%@include file="../../common/base.jsp"%>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
		<title>安全设置</title>
		<%@ include file="../../common/top.jsp"%>
		<link rel="stylesheet" type="text/css" href="${resourcePath }security/css/main.css?t=231">
	</head>
	<body>
		<div id="box">
			<div class="set-group">
				<c:if test="${!isWechat}">
				<div class="group-item set-group-item">
					<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/logincode${ext}?retUrl=/wap/${sessionShopNo}/buy/account/security/index" class="group-rowspan">
						<span class="group-colspan">
							<em class="fnt-16">登录密码修改</em><br />
							<em class="clr-light fnt-12">建议您定期更改密码以保护账号安全</em>
						</span>
						<span class="group-colspan">
							<i class="iconfont icon-right"></i>
						</span>
					</a>
				</div>
				</c:if>
				
				<c:if test="${!isWechat}">
				<div class="group-item set-group-item">
					<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/phonecode${ext}?retUrl=/wap/${sessionShopNo}/buy/account/security/index" class="group-rowspan">
						<span class="group-colspan">
							<em class="fnt-16">手机绑定</em>
							<em class="right clr-danger fnt-12 tel-right">${phone}</em><br />
							<em class="clr-light fnt-12">若您的验证手机已丢失或停用，请立即修改更换</em>
						</span>
						<span class="group-colspan">
							<i class="iconfont icon-right"></i>
						</span>
					</a>
				</div>
				</c:if>
				
				<!-- 微信用户 -->
				<c:if test="${isWechat}">
				<div class="group-item set-group-item">
					<c:if test = "${empty phone}">
					<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/wechatphone${ext}?retUrl=/wap/${sessionShopNo}/buy/account/security/index" class="group-rowspan">
						<span class="group-colspan">
							<em class="fnt-16">手机绑定</em>
							<em class="right clr-danger fnt-12 tel-right">暂未设置</em><br />
							<em class="clr-light fnt-12">请先设置您的手机号码</em>
						</span>
						<span class="group-colspan">
							<i class="iconfont icon-right"></i>
						</span>
					</a>
					</c:if>
					<c:if test = "${not empty phone}">
					<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/phonecode${ext}?retUrl=/wap/${sessionShopNo}/buy/account/security/index" class="group-rowspan">
						<span class="group-colspan">
							<em class="fnt-16">修改绑定手机号码</em>
							<em class="right clr-danger fnt-12 tel-right">${phone}</em><br />
							<em class="clr-light fnt-12">若您的验证手机已丢失或停用，请立即修改更换</em>
						</span>
						<span class="group-colspan">
							<i class="iconfont icon-right"></i>
						</span>
					</a>
					</c:if>
				</div>
				</c:if>
				
				<div class="group-item set-group-item">
					<a href="${ctx }/wap/${sessionShopNo}/buy/account/security/paymentCode${ext}?retUrl=/wap/${sessionShopNo}/buy/account/security/index" class="group-rowspan"> 
						<span class="group-colspan"> 
							<em class="fnt-16">支付密码</em><br />
							<em class="clr-light fnt-12">使用虚拟资产需验证支付密码，保障资产安全</em>
						</span> 
						<span class="group-colspan">
							<i class="iconfont icon-right"></i>
						</span>
					</a>
				</div>
				
				<div class="button mt25" >
					<c:choose>
		               <c:when test="${isDefaultLogin}">
		               		<c:choose>
		               		     <c:when test="${!isBrowser }">
		               		           <button class="btn btn-danger btn-block fnt-16" onclick="toExit()">退出登录</button>
		               		     </c:when>
		               		</c:choose>
		               </c:when>
		               <c:otherwise>
		               		<button class="btn btn-danger btn-block fnt-16" onclick="changeAccount(${isWechat})">更换账号</button>
		               </c:otherwise>
		      		</c:choose>
				</div>
			</div>
			
			<div id="j-account" style="display:none;width:100%">
				<form method="post" action="javascript:void(0);" id="j-form" class="form-login">
					<c:if test="${isWechat}">
			  			<p>使用刷脸账号进行登录？</p>
			  		</c:if>
			  		<c:if test="${!isWechat}">
			  			<p>使用微信账号进行登录？</p>
			  		</c:if>
				</form>
			</div>
			
			<%@ include file="../../common/foot.jsp"%>
			<%@ include file="../../common/nav.jsp"%>
		</div>
		<script type="text/javascript">
			function dealErrMsg() {
				var errMsg = '${errMsg}';
				if (null != errMsg && '' != errMsg) {
					art.dialog.alert(errMsg);
				}
			}
		
			function toExit(){
				sessionStorage.user=0;
				location.href="/wap/${sessionShopNo}/j_spring_cas_security_logout";
			}
			
			function changeAccount(el){
				artDialogComfirm('j-form',function(){
					if (el) {
						location.href="/wap/${sessionShopNo}/login.htm?isWechatSwich=1";
					} else {
						location.href="/wap/${sessionShopNo}/buy/personal/index.htm?isWechatSwich=2";
					}
				});
			};
		</script>
	</body>
</html>

