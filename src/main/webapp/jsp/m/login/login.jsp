<%@ page pageEncoding="UTF-8" %>

<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>刷脸平台</title>
<%@ include file="/company/common/base-company.jsp"%>
<c:set var="nowDate" value="<%=new Date()%>"></c:set> 
<script  type="text/javascript" src="${resourcePath}plugins/jquery.cookie.js"></script>
<%@ include file="/jsp/m/common/validate.jsp"%>
<style type="text/css">label.error{margin-right:10px;}
body{background-color: #062b60;}
.nav-border{ border:0px;}
</style>
</head>
<body>
	<div class="head-box">
		<%@ include file="/company/common/header.jsp"%>
		<div class="banner">
			<div class="inner">
				<div class="right login">
					<h3 id="login-title">登录</h3>
					<form class="login-form" id="jlogin" action="/j_spring_security_check" method="post">
						<input type="hidden" name="loginType"  value="rms"/>
						<div class="login-input-cont">
							<div class="login-input">
								<i class="icon-login un"> </i>
								<input type="text" placeholder="刷脸账号" id="username" name="username"  value="${param.username}" >
							</div>
							<div class="login-input">
                            	<i class="icon-login pwd"> </i>
                            	<input type="password" placeholder="密码" id="password" name="password">
                            </div>
                            <div class="clr-red" id="jerror"></div>
                            <div class="login-ver">
                            	<input type="text" placeholder="验证码" value="" name="j_captcha" maxlength="4" class="left">
                            	<img src="${companyPath}img/login/code-img.png" id="flashImage" alt=""/>
                            </div>
						</div>
						<div class="login-help">
                            <label><input type="checkbox" value="" id="check" name=""> 记住密码</label>
                            <a href="${ctx}/any/retrievePassword${ext}" class="clr-red right">忘记密码？</a>
						</div>
						<button type="button" class="login-btn">登录</button>
					</form>
				</div>
				<%-- <div class="code">
					<img src="${companyPath}img/login/code.jpg" alt="二维码" width="110" height="110"/>
					<p>扫描并关注<br/>刷脸微信公众号</p>
				</div>
<!-- 				<div class="code" style="top:210px"> -->
					<img src="${companyPath}img/login/code-android.png" alt="二维码" width="110" height="110"/>
<!-- 					<p>手机版Best Face <br>随时随地做生意<br>打理店铺更轻松</p> -->
<!-- 				</div> -->
				<div class="code  code-down" >
						<a href="${ctx}/company/mobileapp.jsp" >手机应用下载 >></a>
				</div> --%>
			</div>
		</div>
	</div>
	
	<%@ include file="/company/common/footer.jsp"%>
<script type="text/javascript">
	$(function(){
		//判断密码是否正确
		var sess = '${sessionScope.errorMsg}';
		var codeError = '${sessionScope.captchaFlag}';
	 	if(sess || codeError){
	 		if(sess){
	 			textRrror('#password',sess);
	 		}
	 		if(codeError == "false"){
	 			textRrror('#flashImage','验证码错误！');
		 	}
	  		var time=3;
	  		var setIntervalID=setInterval(function(){
	  			time--;
	  			if(time==0){
	  				$('.login-input-cont').find("label").remove();
	  			}
	  		},1000);
		} 
	 
		
		//记住密码/记住用户名
		var cookie_pwd = 'password'; //userName
		if ($.cookie(cookie_pwd)) {
			$("#check").attr("checked","checked");
			$("#password").val($.cookie(cookie_pwd));
		}
		//记住用户名
		var cookie_name = 'username';
		if ($.cookie(cookie_name)) {
			$("#username").val($.cookie(cookie_name));
		}
		$('#flashImage').hide().attr('src','<c:url value="${pageContext.request.contextPath}/jsp/m/login/image.jsp"/>'+ '?'+ Math.floor(Math.random() * 100)).fadeIn(); 	
		$("#flashImage").click(function(){  
	       $('#flashImage').hide().attr('src','<c:url value="${pageContext.request.contextPath}/jsp/m/login/image.jsp"/>'+ '?'+ Math.floor(Math.random() * 100)).fadeIn();  
	   });  
	var bool=$("#jlogin").validate({
			rules:{
				username:{
					required:true,
					minlength: 6
				},
				password:{
					required:true,
					minlength: 6
				},
				j_captcha:{
					required:true,
					minlength: 4
				},
			},
			messages:{
				username:{
					required:"请输入用户名",
					minlength:$.format("用户名不能小于{0}个字 符")
				},
				password:{
					required:"请输入密码",
					minlength:$.format("密码不能小于{0}个字 符")
				},
				j_captcha:{
					required:"请输入验证码"
				},
			},
			errorLabelContainer:"#jerror"
		});
		$('.login-btn').click(function(event) {
			var flag = bool.form();
			if(flag){
				//登录
				login();
			}
	});
});
	
	
function textRrror(el,text){
	var str = '<label for="mobile" generated="true" class="error">'+text+'</label>';
	$(el).addClass('error');
	if(!$(el).parent().next().hasClass('error')) $(el).parent().after(str);
	
}

/** 登录 */
function login(){
	//记住密码/记住用户名
	var cookie_pwd = 'password'; //userName
	//记住用户名
	var cookie_name = 'username';
	if ($("#check").prop("checked")) {
		$.cookie(cookie_pwd, $("#password").val(), {
			path : '/',
			expires : 10
		});
	} else {
		$.cookie(cookie_pwd, null, {
			path : '/'
		});
	}
	$.cookie(cookie_name, $("#username").val(), {
		path : '/',
		expires : 10
	});
	$("#jlogin").submit();
}

/** 登录响应回车 */
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
    login();
  }
};



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
</script>
</body>
</html>