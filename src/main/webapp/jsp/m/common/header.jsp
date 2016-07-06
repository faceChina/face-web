<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
$(document).ready(function() {
	$(".j-loading").hide();
});

function confirmMsg(msg){
	art.dialog.confirm(msg, function() {
		
	}, function() {
		//取消执行操作
		return true;
	});
}

function alertMsg(msg){
	art.dialog.alert(msg);
}

var logout = function(){
	clearCookie("username");
	window.location.href = "${ctx}/j_spring_cas_security_logout";
}

/**
 * get跳转至预览页面
 */
function previewTo(url,name,iWidth,iHeight){
	if(null==url||''==url)return;
	if(null==name||''==name)name='preview';    //网页名称，可为空;
	if(null==iWidth||0>=iWidth)iWidth=673;	   //弹出窗口的宽度;
	if(null==iHeight||0>=iHeight)iHeight=900;  //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight-30-iHeight)/2;        
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
	var win=window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=yes,titlebar=no');
	win.focus();
}

/**
 * post跳转至预览页面
 */
 var ow;
function previewToBigData(url,jsonstr,name,iWidth,iHeight){
	if(null==url||''==url)return;
	if(null==name||''==name)name='preview';    //网页名称，可为空;
	if(null==iWidth||0>=iWidth)iWidth=673;	   //弹出窗口的宽度;
	if(null==iHeight||0>=iHeight)iHeight=900;  //弹出窗口的高度;
	//获得窗口的垂直位置
    var iTop = (window.screen.availHeight-30-iHeight)/2;        
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth-10-iWidth)/2;
	if(ow){
		ow.close();	
	}
	ow=window.open('about:blank',name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=no,titlebar=no');
	 $.ajax({
         type: "POST", url: url,
         async:false,
         data:{"jsonstr":jsonstr,"jsonType":name},
         success:function(data){
	       	ow.document.write(data);
	        ow.focus();
	        ow.document.close();	
         }
     });   
}

function previewToSerialize(url,seridata,name,iWidth,iHeight){
	if(null==url||''==url)return;
	if(null==name||''==name)name='preview';    //网页名称，可为空;
	if(null==iWidth||0>=iWidth)iWidth=673;	   //弹出窗口的宽度;
	if(null==iHeight||0>=iHeight)iHeight=900;  //弹出窗口的高度;
	//获得窗口的垂直位置
    var iTop = (window.screen.availHeight-30-iHeight)/2;        
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth-10-iWidth)/2;
	if(ow){
		ow.close();	
	}
	ow=window.open('about:blank',name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=no,titlebar=no');
	 $.ajax({
         type: "POST", url: url,
         async:false,
         data:seridata,
         success:function(data){
	       	ow.document.write(data);
	        ow.focus();
	        ow.document.close();	
         }
     });   
}

//滚动公告需要的js
$(document).ready(function() {
	scrollUp();

	/* 新闻弹出*/
	$('#rollnews').click(function(event) {
	/* Act on the event */
	$(this).hide();
	});
	$('#scrollup li').on('click', function(event) {
		event.preventDefault();
		/* Act on the event */
		$('#rollnews').show();
		$('#rollnews .news-list').eq($(this).attr('data-list')).show().siblings().hide();
	});

});

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
/*新闻滚动*/
function scrollNews(obj){
var $self = obj.find("ul:first"); 
var lineHeight = $self.find("li:first").height(); //获取行高
$self.animate({ "marginTop" : -lineHeight +"px" }, 1000 , function(){
 $self.css({marginTop:0}).find("li:first").appendTo($self); //appendTo能直接移动元素
})
}
function jumpPage(url,id){
	$("#tjform").attr("action",url);
	$("#formid").val(id);
	$("#tjform").submit();
}
//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires+ ";path=/";
}
//清除cookie  
function clearCookie(name) {  
    setCookie(name, "", -1);  
} 

</script>
<%-- <% request.setAttribute("noticeList",((UserBusiness)SpringUtil.getBean("userBusiness")).queryNotice());//获取动态滚动公告,放入request中 %> --%>

<div class="top">
	<div class="container">
		<div class="row">
			<div class="col-md-4">互联网4.0时代，强大自己！</div>
			<div class="col-md-8 text-right">
				<span><sec:authentication property="name" /></span>
				<a href="javascript:logout();">退出</a>
				<!-- |  <a href="">操作指南</a> |  -->
		    </div>
    	</div>
	</div>
</div>
<div class="header">
	<div class="container">
		<div class="row">
			<a href="" class="pull-left"><img src="${ctx }/company/img/login/logo.png" alt="" /></a>
			<span class="pull-right"><img src="${resourcePath }img/telephone.png" alt="" /></span>
			<div></div>
		</div>
	</div>
</div>

<div class="news">
  <div class="row">
  	<div class="notice">
  	<div class="pull-left">
  		<span  style="float:left"><strong>公告：</strong> </span>
        <div style="float:left" id="scrollup">
           <ul >
       	   </ul>
        </div>
    </div>
    </div>
  </div>
</div>

<div id="rollnews">
  <div class="rollnews">
  </div>
</div>
<form id="tjform" method="post">
	<input type="hidden" id="formid" name="id"/>
</form>

<input type="hidden" value="accounts"  name="" id="menuIndex"/>