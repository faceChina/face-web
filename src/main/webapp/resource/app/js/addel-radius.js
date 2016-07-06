/* 开关按钮 */
(function($){
	$.fn.addelRadius = function(options){
		var opts = $.extend({},$.fn.addelRadius.defaults,options);
		
		return this.each(function(){
			var thiz = $(this);
			var number = parseInt($(this).find("input").val());
			
			//初始状态
			var init = (function(){
				if(number == 0){
					thiz.find(".addel-del,.addel-info").hide();
				}
			})();
			
			$(this).find(".addel-add").on("click",function(){
				++number;
				if(number > opts.number){
					number = opts.number;
					//artTip('库存不足');
				}else{
					opts["add"]();
					if(number == opts.number) $(this).attr('disabled','disabled');
				}

				thiz.find("input").val(number);
				if(number > 0 ){
					thiz.find(".addel-del,.addel-info").show();
				}
			});
			$(this).find(".addel-del").on("click",function(){
				--number;
				//number = number <= 0 ? 0 : number;
				if(number < 0){
					number = 0;
				}else{
					opts["del"]();
				}
				thiz.find("input").val(number);				
				if(thiz.find(".addel-add").attr('disabled') == 'disabled'){
					thiz.find(".addel-add").removeAttr('disabled');
				}
				
				if(number == 0){
					thiz.find(".addel-del,.addel-info").hide();
				}
				
			});
		});
		
		$.fn.addelRadius.defaults = {
			number:1,
			add:null,
			del:null
		}
	}
})(jQuery);

$(function(){
//	$.addel();//初始化页面时，判断默认开关
});



