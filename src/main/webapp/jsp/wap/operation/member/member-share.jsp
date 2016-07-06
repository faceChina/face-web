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
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/member/css/main.css">
<script type="text/javascript">
$(function(){
	var sess = '${sessionScope.errorMsg}';
	if(sess){
		art.dialog.tips(sess);
//  		textRrror('#password','用户名不存在或密码输入错误！');
	} 
})
</script>
</head>
<body>
<style>
.aui_content{width:100%;}
</style>
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
		<div class="info">使用时向服务员出示此卡</div>
	</div>
	
	<div class="group member-card">
		<span class="info fnt-16" style="padding-top:10px;">登录后可享受更多线上折扣优惠</span>
		<div class="button">
			<button class="btn btn-danger btn-block fnt-16" onclick="toLogin()">登录</button>
		</div>
	</div>
	
	<div class="group width20">
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan"><i class="iconfont icon-cloudiconfontwenben clr-light fnt-20"></i></li>
				<li class="group-colspan fnt-16">会员卡说明</li>
			</ul>
		</div>
		<div class="group-item">
			<ul class="group-rowspan">
				<li class="group-colspan" style="color:#666;">会员卡泛指普通身份识别卡，包括商场、 宾馆、健身中心、酒家等消费场所的会 员认证。 </li>
			</ul>
		</div>
	</div>
	<%@include file="../../common/foot.jsp" %>
	<%@include file="../../common/nav.jsp" %>
<%-- 	<script type="text/javascript" src="${resourceBasePath }page/login-plugin.js"></script> --%>
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="${ctx}/wap/${shop.no}/any/member/checkUser${ext}" id="jform" class="form-login">
  		<div class="list-row list-row-clearborder ">
    		<div class="list-col">
      			<div class="list-inline box-flex"><input type="text" name="cell" id="cell" class="form-border" placeholder="请填写您的手机号码"></div>
    		</div>
    		<div class="list-col">
      			<div class="list-inline box-flex"><input type="password" name="passwd" id="passwd" class="form-border" placeholder="请填写您的密码"></div>
    		</div>
		</div>
		<div style="padding:0 10px;">
			<a href="${ctx}/any/registration${ext}" class="left">免费注册</a>
			<a href="${ctx}/any/findPassword${ext}?type=2" class="right clr-light">忘记密码？</a>
		<div>
	</form>
</div>
</div>

<script type="text/javascript">
	function toLogin(){
		 art.dialog({
             lock:true,
             title:"会员登录",
             width:'100%',
             content:document.getElementById("j-login"),
             button:[{
                 name:"登录",
                 focus:true,
                 callback:function(){
                     var bool = $("#jform").validate({
                         rules : {
                        	 cell : {
                                 required : true
                             },
                             passwd : {
                                 required : true,
             					 minlength: 6
                             }
                         },
                         messages : {
                             cell : {
                                 required : "请输入用户名"
                             },
                             passwd : {
                                 required : "请输入密码",
             					 minlength:"密码不能小于6个字 符"
                             }
                         }
                     }).form();

                     if(bool){
                         $("#jform").submit();
                     }
                     return false;
                 }
             }]
         })
	};
</script>

</body>
</html>