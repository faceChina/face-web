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
<link rel="stylesheet" type="text/css"	href="${resourcePath }/operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }/operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css"	href="${resourcePath }/operation/bestface/fonts/iconfont.css">
<link rel="stylesheet" type="text/css"	href="${resourceBasePath }/js/artDialog/skins/myself.css">

<script type="text/javascript" src="${resourcePath }/js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath }/js/mob-public.js"></script>
<style type="text/css">
/*=========================*/
html{
	font-size:16px;
}
.main{
	position:relative;
	height:180px;
	overflow:hidden;
	background:url(${resourcePath }/operation/bestface/img/banner.jpg) no-repeat;
}
.main a.btn-editor{
	position:absolute;
	right:0.5rem;
	top:0.5rem;
	display: inline-block;
    border:1px solid #FFFFFF;
    padding:0.3rem 1rem;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    color:#fff;
    text-align: center;
  	line-height:1rem;
}
.headpic{
	padding-top:2rem;
	height:140px;
	overflow:hidden;
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
.headpic .header li a{

}
.headpic .header li a i{
	color:#fff;
	font-size:48px;
}
.headpic .header li a em{
	display:block;
	line-height:30px;
	color:#fff;
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
	font-size:14px;
	line-height:24px;
	color:#666;
	text-align:center;
}
/*=========================*/
.menu{
	width:100%;
	height:40px;
	display:table;
	overflow:hidden;
	background:rgba(0,0,0,0.2);
	padding:0.3rem 0;
}
.menu li{
	display:table-cell;
	width:33%;
	border-right:1px solid rgba(0,0,0,0.1);
}
.menu li:last-child{
	border:none;
}
.menu li a{
	display:block;
	line-height:1rem;
	text-align:center;
	
	display: -webkit-box;-webkit-box-orient: horizontal;-webkit-box-pack: center;-webkit-box-align: center;
	display: -moz-box;-moz-box-orient: horizontal;-moz-box-pack: center;-moz-box-align: center;
	display: -o-box;-o-box-orient: horizontal;-o-box-pack: center;-o-box-align: center;
	display: -ms-box;-ms-box-orient: horizontal;-ms-box-pack: center;-ms-box-align: center;
	display: box;box-orient: horizontal;box-pack: center;box-align: center;	
}
.menu li a i{
	display:block;
	font-size:24px;
	line-height:24px;
}
.menu li a em{
	display:block;
	color:#fff;
}
/*=========================*/
.item-group{
	width:100%;
	background:#fff;
	border-top:1px solid #ccc;
	border-bottom:1px solid #ccc;
	margin-top:10px;
	padding-left:10px;
	font-size:14px;
}
.item-group .item-title{
	padding:10px;
	padding-bottom:0;
	color:#999;
	font-weight:normal;
}
.item-group .item-row{
	width:100%;
	display:table;
	padding:10px;
	padding-left:0;
/* 	margin-left:10px; */
	border-bottom:1px solid #ccc;
}
.item-group .item-row:last-child{
	border-bottom:none;
}
.item-group .item-row .item-cell{
	display:table-cell;
	vertical-align:middle;
	text-align:left;
}
/* 类型 */
.item-group .item-row.item-default{}
.item-group .item-row.item-colspan{}
.item-group .item-row.item-colspan .item-cell:first-child{
	width:15%;
}

/* 头像 */
.friendpic{
	display:inline-block;
	width:3rem;
	overflow:hidden;
	margin:0 auto;
}
.friendpic .img{
	display:block;
	width:3rem;
	height:3rem;
	overflow:hidden;
	border-radius:100%;
}
.friendpic .img img{
	display:block;
	width:100%;
	height:100%;
	overflow:hidden;
	border-radius:100%;
}
</style>
</head>
<body>
<div class="container main">
	<!-- 在别人看到我的名片时是不能编辑的 但是自己进去是可以编辑的  -->
<%-- 	<a href="/icard/icard-edit/{masterId}${ext }" class="btn-editor" style="display:none;" id="js-editor">编辑</a> --%>
<c:if test="${1==isMaster }">
		<a href="${ctx }/wap/${shopNo}/any/icard/icard-edit/${icard.id}${ext}" class="btn-editor"  id="js-editor">编辑</a>
</c:if>
	<div class="headpic">
		<ul class="header">
			<li>
				<a href="javascript:void(0)"  onclick="showerweima()">
					<i class="iconfont icon-erweima"></i>
				</a>
			</li>
			<li>
				<a href="<c:choose><c:when test="${empty icard.cell}">#</c:when><c:otherwise>tel:${icard.cell}</c:otherwise></c:choose>" class="link">
					<span class="img"><img src="${picUrl}${icard.headPicture}" alt=""></span>
				</a>
			</li>
			<li>
				<a href="<c:choose><c:when test="${empty icard.cell}">#</c:when><c:otherwise>tel:${icard.cell}</c:otherwise></c:choose>">
					<i class="iconfont icon-phone"></i>
				</a>
			</li>
		</ul>
		<p class="name">${icard.nickName}</p>
	</div>
	
	<ul class="menu">
		<li>
			<a href="javascript:void(0);">
				<span>
					<em>点赞</em>
					<em>${praiseCount }</em>
				</span>
			</a>
		</li>
		<li>
			<a href="javascript:void(0);">
				<span>
					<em>粉丝</em>
					<em>${fansCount}</em>
				</span>
			</a>
		</li>
		<li>
			<a href="javascript:void(0);">
				<span>
					<em>被查看</em>
					<em><c:if test="${empty icard.sumContacts}">0</c:if>${icard.sumContacts}</em>
				</span>
			</a>
		</li>
	</ul>
</div>
<div class="item-group">
	<div class="item-row item-colspan">
		<span class="item-cell">姓名</span>
		<span class="item-cell">${icard.nickName}</span>
	</div>
	<div class="item-row item-colspan">
		<span class="item-cell">手机</span>
		<span class="item-cell">${icard.cell}</span>
	</div>
	<div class="item-row item-colspan">
		<span class="item-cell">公司</span>
		<span class="item-cell">${icard.company}</span>
	</div>
	<div class="item-row item-colspan">
		<span class="item-cell">职位</span>
		<span class="item-cell">${icard.position}</span>
	</div>
</div>

<!-- 二维码 -->
<div id="j-ewm" style="display:none;">
	<img src="${picUrl}${icard.twoDimensionalCode}" alt="" style="display:block;width:100%;margin:0 auto;">
	<p>扫一扫上面的二维码图案，访问我的名片</p>
</div>

<script type="text/javascript" src="${resourceBasePath }js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/slidefocus-jquery.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript">
//显示二维码
/* function showerweima(){
	art.dialog({
		lock:true,
		title:"二维码",
		content:document.getElementById("j-ewm")
	})
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

//状态传值，前端专用，后端自行删除
var session_goods=sessionStorage.getItem('session_goods');
if(session_goods=='self'){
	$('#js-editor').show();
}else if(session_goods=='seller'){
	
};
</script>
</body>
</html>


