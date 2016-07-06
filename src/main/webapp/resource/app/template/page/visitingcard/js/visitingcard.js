/*	
 * 功能：基类：个人名片公共js
 * */
function changeClientHeight(){
	  	var height=document.documentElement.clientHeight,
	  	  sUserAgent = navigator.userAgent.toLowerCase(),
  		  bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    if (height<=486) {
		       $(".header").css("height","45%");
		       $(".header1").css("height","42%");
		       $(".companyinfo").css({"companyinfo":"65%","margin-top":"3%","line-height":"1rem"});
		       $(".content").css({"height":"35%","margin-top":"0.8rem"});
		       $(".userinfo .ui-header img").css({"height":"3rem","width":"3rem"});
		       $(".ui-name").css({"padding":"0.3rem 0","font-size":"0.85rem"})
		       $(".userinfo .sex-icon").css({"width":"0.8rem","height":"0.8rem","right":"1.4rem"});
		       $(".sex-icon i").css({"top":"-0.5rem","right":"-0.2rem"});
		       $(".content1 a").css({"margin-top":"0.55rem"});
		       
		    }
		    if (!bIsIphoneOs) {
		    	$(".userinfo .sex-icon i").css("top","-0.1rem");
		    }
	}
$(function(){
	changeClientHeight();
	var mySwiper = new Swiper ('.swiper-container', {
	   direction : 'vertical',
	   mousewheelControl : true,
	   onInit: function(swiper){
		   swiperAnimateCache(swiper);
		   swiperAnimate(swiper);
		  },
		   onSlideChangeEnd: function(swiper){
			swiperAnimate(swiper);
			changeClientHeight();
	    }	  
 	}); 
 	$(".nextpage").off().on("click",function(){
 		   mySwiper.slideNext(2); 
 	});
});
window.addFriend=function(){
}
window.business=function(){
}
window.share=function(){
}
