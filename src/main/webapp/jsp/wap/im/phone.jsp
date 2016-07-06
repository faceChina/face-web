<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<%@include file="../common/base.jsp"%>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="author" content="hoojo">
<meta http-equiv="email" content="hoojo_@126.com">
<meta http-equiv="blog" content="http://blog.csdn.net/IBM_hoojo">
<meta http-equiv="blog" content="http://hoojo.cnblogs.com">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<link rel="stylesheet" type="text/css" href="${resourceImPath}css/phone.css">
<script type="text/javascript">
   window.contextPath = "<%=path%>";
   window["serverDomin"] = "${serverDomin}";
   window["clusterdomain"] ="${clusterdomain}";
</script>
<script type="text/javascript" src="${resourceImPath}jslib/jquery-1.7.1.min.js"></script>
<%-- <script type="text/javascript" src="${resourceImPath}jslib/JSJaC1.1.js"></script> --%>
<script type="text/javascript" src="${resourceImPath}jslib/jsjac.js"></script>
<script type="text/javascript" src="${resourceImPath}jslib/send.message.editor-1.0.js"></script>
<script type="text/javascript" src="${resourceImPath}jslib/remote.jsjac.chat-2.0.js"></script>
<script type="text/javascript" src="${resourceImPath}jslib/phone.chat-1.0.js"></script>
<style type="text/css">
#icon-loading{width:100%;padding:8px 0;background:#e9e9e9;border-radius:12px;text-align:center;color:#999;}
#icon-loading span,#icon-loading img{vertical-align:middle;}
</style>
<script type="text/javascript">
  var msgTime = new Date().getTime()-60000;
  var loginTime = 0;
  var managedAccount = '${managedAccount}'.toLowerCase();
  
	if ("onpagehide" in window) {
	    window.addEventListener("pagehide", loginout, false);
	} else {
	    window.addEventListener("unload", loginout, false);
	}
	//页面离开时，该聊天帐号离线
	function loginout(){
		remote.jsjac.chat.logout();
	}
  
  $(function () {
 	  $.WebIM({
         sender: '${imUserVo.userName}'
      });
      remote.jsjac.chat.login(document.userForm);
      $("form").hide();
      $.WebIM.newWebIM({
          receiver: managedAccount
      });
      //加载商品信息
      isShowPro();
 });
 var flag = false;
 function sendLink(el){
	 if(flag){
		 $.WebIM.writeReceiveMessage("", "", "已经发送成功,请不要重复发送", true);
		 return;
	 }
	 var $chatMain= $(".phone-im");
	 /* var content='<div class="commodity"">'
	 					+'<dl>'
	 					+'<dt><img src="${picUrl}${good.picUrl}" alt=""/></dt>'
	 					+'<dd>'
	 					+'<h3>${good.name}</h3>'
	 					+'<code>￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></code>'
	 					+'<a href="${ctx}${sendurl}" target="_blank" >查看宝贝</a>'
	 					+'</dd>'
	 					+'</dl>'
	 					+'</div>'; */
	 var data = getShopAnalytical();
	 					
				
	 $.WebIM.sendLink(data,$chatMain);
	 flag = true;
 }
 
 function getShopAnalytical(){
// 	 console.log(localtion.host)
	 var Agreement = "lpprotocol://webpage/";
	 var data = {};
	 	data.pictureUrl = "${picUrl}${good.picUrl}";
	 	data.linkUrl = "http://"+window.location.host+"${sendurl}";
	 	data.title = "${good.name}";
	 	data.desc = "${good.salesPrice/100}";
	 return Agreement + JSON.stringify(data);
 }

 
 //判断移动端IM中的产品信息是否显示
 function isShowPro(){
	 if("${good}" != null && "${good}" != ""){
		 var content = '<div class="list-row" id="phonePro">'
				+'<div class="list-col">'
				+'<div class="list-inline"><img src="${picUrl}${good.picUrl}" alt="" width="70" height="70"></div>'
				+'<div class="list-top box-flex">'
					+'<ul>'
						+'<li class="txt-rowspan2">${good.name}</li>'
						+'<li class="clr-warning">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></li>'
					+'</ul>'
				+'</div>'
			+'</div>'
			+'<div class="list-col">'
				+'<div class="list-center">'
					+'<button type="button" class="btn btn-default btn-shoplink" onclick="sendLink(this)">发送宝贝链接</button>'
				+'</div>'
			+'</div>'
		+'</div>';				  
							
		 $("#phonePro-box").append(content);
	}
}
//播放声音
function voicePlay(){
	var audio = document.getElementById("audio");
	if(audio!=null){
		audio.play();
	}
}
</script>
</head>
<body>
<!-- 播放声音 -->
<audio src="${resourceImPath}voice/wangwang.mp3" id="audio" hidden="true"></audio>
<input type="hidden" id="imUserId" value="${imUserVo.id }" />
<input type="hidden" id="shopNo" value="${sessionShopNo}" />
<input type="hidden" id="currentTime" value="${currentTime}" />
<form name="userForm" style="background-color: #fcfcfc; width: 100%;">
		<input type="hidden" name="userName" value="${imUserVo.userName}" /> <br />
		<input type="hidden" name="password" value="${imUserVo.password}" /> <br />
		<input type="hidden" name="register" value="${imUserVo.register}"/> <br />
		<input type="button" value="Login" id="login" />
</form>
<!-- 日志信息 -->
<div id="error" style="display:none; background-color: red;"></div>
<div id="info" style="display:none; background-color: #999999;"></div>
<div class="phone-im">
	<div id="chat">
		<div class="receive-message clearfix " id="phonePro-box">
			<div id="icon-loading" onclick="moreHistroy()">
				<span>点击显示历史记录</span>
			</div>
		</div> 
		<div class="send-message" id="footer">
			<div class="send-button">
				<input type="hidden" id="to" name="to" value="" />
				<button type="button" class="btn-send"  id="send">发送</button>
			</div>
			<div class="send-text">
				<textarea id="jtextarea" rows="1" style="resize:none;"></textarea>
			</div>	
		</div>
	</div>
</div>

<script>
var content = $("#phonePro-box"); //消息内容
var footerHeight = $("#footer").outerHeight(true); //底部浮动层高度

var meheadPic = '${meheadpic}'?'${picUrl}${meheadpic}':'/resource/im/img/headPic.jpg';
var youheadPic = '${headpic}'?'${picUrl}${headpic}':'/resource/im/img/headPic.jpg';

content.css("padding-bottom",footerHeight+20);

function moreHistroy(){
	var receiver = managedAccount;
	var chatUser = remote.userAddress(receiver.toLowerCase());
    var userJID = "u" + hex_md5(chatUser);
    var params = {
			"sender":$("#imUserId").val(),
  			"receiver":receiver,
  			"userJID":userJID
	 };
     //获取聊天记录并插入到第一页前面
	$.WebIM.loadingMessage(params);
}
</script>
</body>
</html>

