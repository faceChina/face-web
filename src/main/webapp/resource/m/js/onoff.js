/* 开关按钮 */
(function($){
	$.onoff = function(options){
		$('[data-onoff]').each(function(){
			var ison = $.trim($(this).data("onoff")),
				thiz = $(this);
			if(ison == "on"){
				thiz.find("[data-onoff-handle]").css({"left":"26px"});
				thiz.find("[data-onoff-on]").removeClass("hide");
				thiz.find("[data-onoff-off]").addClass("hide");
			}else if(ison == "off"){
				thiz.find("[data-onoff-handle]").css({"left":"0"});
				thiz.find("[data-onoff-on]").addClass("hide");
				thiz.find("[data-onoff-off]").removeClass("hide");
			}
		})
	}
	
	$.fn.onoff = function(options){
		var opts = $.extend({},$.fn.onoff.defaults,options);
		return this.each(function(){
			var init = (function(){
				if(typeof opts.extra == "function"){
					opts.extra();
				}
			})();
			$(this).on("click",function(){
				var thiz = $(this);
				var isoff = $.trim(thiz.data("onoff"));
				var flag = false;
				if(isoff == "off"){
					if(typeof opts.on == "function"){
							flag = opts.on();
					}
					if(flag){
						thiz.find("[data-onoff-handle]").animate({
							"left":"26px"
						},100,function(){
							$(this).parents("[data-onoff]").find("[data-onoff-on]").removeClass("hide");
							$(this).parents("[data-onoff]").find("[data-onoff-off]").addClass("hide");
							$(this).parents("[data-onoff]").data("onoff","on").attr("data-onoff","on");
							
						});
					}
					
				}else if(isoff == "on"){
					if(typeof opts.on == "function"){
							flag = opts.off();
					}
					if(flag){
						$(this).find("[data-onoff-handle]").animate({
							"left":"0"
						},100,function(){
							$(this).parents("[data-onoff]").find("[data-onoff-on]").addClass("hide");
							$(this).parents("[data-onoff]").find("[data-onoff-off]").removeClass("hide");
							$(this).parents("[data-onoff]").data("onoff","off").attr("data-onoff","off");
						});
					}
				}
			})
		});
		$.fn.onoff.defaults = {
			extra:null,
			on:null,
			off:null
		}
	}
})(jQuery);

$(function(){
	$.onoff();//初始化页面时，判断默认开关
});



