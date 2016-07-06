$(function(){
	$("#header").load("../public/header.html");
	$("#footer").load("../public/footer.html");
	
	minHeight();
	$(window).resize(function(){
		minHeight();
	})
})

//footer最下面处理
function minHeight(){
	var bodyht =  document.documentElement.clientHeight;
	var footerht = $('.footer').length>0 ? $('.footer').outerHeight(true):0;
	// BUG原因：banner图片未加载完全就执行如下代码，解决方法有2种,建议使用第1种
	// 第1种方法：直接给定值170，弊端是头部文件有变化时，则此方法有bug
	if($("ul").hasClass("sidebar")){
		var contHt = bodyht - (136 +footerht) ;
		$('#j-content').css('min-height',contHt);
	}else{
		if($('.footer').length >0){
			var footht = $('.footer').offset().top+$('.footer')[0].offsetHeight;
			if (bodyht>footht) {
				$('.footer').addClass('navbar-fixed-bottom');
			}else{
				$('.footer').removeClass('navbar-fixed-bottom');
			}
		}
	}
	
	//第2种方法：当banner图片加载完全之后，再执行下面的语句，弊端是如果删除此banner，则此方法有bug;加载慢时有bug;有其他相同banner样式时有bug
	//	function topHeight(){
	//		if($("ul").hasClass("sidebar")){
	//			var offsetTop = $('.sidebar').offset().top;
	//			var contHt = bodyht - (offsetTop +footerht) ;
	//			$('#j-content').css('min-height',contHt);
	//		}else{
	//			if($('.footer').length >0){
	//				var footht = $('.footer').offset().top+$('.footer')[0].offsetHeight;
	//				if (bodyht>footht) {
	//					$('.footer').addClass('navbar-fixed-bottom');
	//				}else{
	//					$('.footer').removeClass('navbar-fixed-bottom');
	//				}
	//			}
	//		}
	//	}
	//	$("div").hasClass("banner") ? $(".banner img")[0].onload=function(){topHeight()} : topHeight();
}