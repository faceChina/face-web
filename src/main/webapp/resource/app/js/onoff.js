/* 开关按钮 */
(function($){
	$.onoff = function(options){
		$('[data-onoff]').each(function(){
			var ison = $(this).data("onoff");
			var info = $(this).data("info");
			if(ison == "on"){
				$(this).find(".onoff-handle").css({"right":"0","left":"auto"});
				if(info == "" || info == null || info == undefined){
					$(this).find('.onoff-info').html('ABC').css("text-align","left");
				}else{
					var text = info.split(",");
					$(this).find('.onoff-info').html(text[0]).css("text-align","left");
				}
			}else if(ison == "off"){
				$(this).find(".onoff-handle").css({"right":"auto","left":"0"});
				if(info == "" || info == null || info == undefined){
					$(this).find('.onoff-info').html('•••').css("text-align","right");
				}else{
					var text = info.split(",");
					$(this).find('.onoff-info').html(text[1]).css("text-align","right");
				}
				
			}
		})
	}
	
	$.fn.onoff = function(options){
		var opts = $.extend({},$.fn.onoff.defaults,options);
		var info = $(this).data("info");
		var flag = false;
		return this.each(function(){
			var init = (function(){
				if(typeof opts.extra == "function"){
					opts.extra();
				}
			})();
			$(this).on("click",function(){
				var isoff = $(this).data("onoff");
				
				if(isoff == "off"){
					if(typeof opts.on == "function"){
						flag = opts.on();
					}
					if(flag){
						$(this).removeClass('onoff-off').find(".onoff-handle").css({"right":"0","left":"auto"});
						if(info == "" || info == null || info == undefined){
							console.log(111)
							$(this).find('.onoff-info').html('ABC').css("text-align","left");
						}else{
							var text = $(this).data("info").split(",");
							$(this).find('.onoff-info').html(text[0]).css("text-align","left");
						}
						$(this).data("onoff","on");
					}
				}else if(isoff == "on"){
					if(typeof opts.on == "function"){
						flag = opts.off();
					}
					if(flag){
						$(this).addClass('onoff-off').find(".onoff-handle").css({"right":"auto","left":"0"});
						if(info == "" || info == null || info == undefined){
							$(this).find('.onoff-info').html('•••').css("text-align","right");
						}else{
							var text = $(this).data("info").split(",");
							$(this).find('.onoff-info').html(text[1]).css("text-align","right");
						}
						$(this).data("onoff","off");
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



