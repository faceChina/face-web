(function($,exports){

function fiexdFooter(){

	var browserHeight =  document.documentElement.clientHeight;  //浏览器可视高度
	var footerHeight = $('.footer').length>0 ? $('.footer').height():0;	//页脚高度
	var bodyHeight = $("body").outerHeight(true);		//文档高度
	var spaceHeight = browserHeight-footerHeight;
	if(bodyHeight < browserHeight){
		$('.footer').offset({ top:spaceHeight})
	}
//	console.log(browserHeight+"***"+bodyHeight+"***"+footerHeight+"**"+spaceHeight);

}


fiexdFooter();

var username=getCookie("username");
if(username!=""){
	var reglogHtml='<a href="/u/index.htm" target="_blank">'+username+'</a><span> | </span><a href="javascript:logout();">退出</a>';
	$(".login .tar").html(reglogHtml);
}
$(window).resize(function(){
	fiexdFooter();
});

})(jQuery) 

//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires+";path=/";
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie  
function clearCookie(name) {  
    setCookie(name, "", -1);  
}  
 
function logout(){
	clearCookie("username");
	window.location.reload();
}