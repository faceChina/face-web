/*
 * 层从四个方向滑出
 * 使用方法：详见demo
 * */
+function($){
	'use strict';
	$(document).on("click","[data-toggle='tab']",function(){
		var src = $(this).attr("href");
		$(this).parent().addClass("active").siblings().removeClass("active");
		$(src).addClass("active").siblings().removeClass("active");
		return false;
	});
}(jQuery)