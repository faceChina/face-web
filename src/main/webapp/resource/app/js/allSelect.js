/* 版本：allSelect.V.1.0.0
 * 功能：全选
 * 作者：凡开
 * 时间：2014-04-1
 * 使用方法：
*/

(function($){
	$.fn.allSelect=function(options){
		var opts=$.extend({},$.fn.allSelect.defaults,options);
		
		var thiz=$(this);
		var group = thiz.find('[data-select-group]');
		var total = thiz.find('[data-select-total]');
		var all = group.find("[data-select-all]");
		var single = group.find("[data-select-single]");
		
		return this.each(function(){
			//single click
			single.on("click",function(){
				thix = $(this);
				$(this).toggleClass("icon-roundcheckfill clr-danger")
					   .toggleClass("icon-round clr-light");
				var bool = $(this).hasClass("icon-roundcheckfill");
				if(bool){
					opts.add();
				}else{
					opts.del();
				}
				var len = $(this).parents("[data-select-group]").find("[data-select-single]").length;
				var len2 = $(this).parents("[data-select-group]").find("[data-select-single].icon-roundcheckfill").length;
				if(len == len2){
					$(this).parents("[data-select-group]")
						   .find("[data-select-all]")
						   .addClass("icon-roundcheckfill clr-danger")
						   .removeClass("icon-round clr-light")
					a(thix);
				}else{
					$(this).parents("[data-select-group]")
						   .find("[data-select-all]")
						   .addClass("icon-round clr-light")
						   .removeClass("icon-roundcheckfill clr-danger")
					a(thix);
				}
			})
			
			//all click
			all.on("click",function(){
				thix = $(this);
				$(this).toggleClass("icon-roundcheckfill clr-danger")
					   .toggleClass("icon-round clr-light");
				var bool = $(this).hasClass("icon-roundcheckfill");
				if(bool){
					$(this).parents("[data-select-group]")
						   .find("[data-select-single]")
						   .addClass("icon-roundcheckfill clr-danger")
						   .removeClass("icon-round clr-light")
					a(thix);
					opts.add();
				}else{
					$(this).parents("[data-select-group]")
						   .find("[data-select-single]")
						   .addClass("icon-round clr-light")
						   .removeClass("icon-roundcheckfill clr-danger")
					a(thix);
					opts.del();
				}
			})
			
			//total click
			total.on("click",function(){
				$(this).toggleClass("icon-roundcheckfill clr-danger")
					   .toggleClass("icon-round clr-light");
				var bool = $(this).hasClass("icon-roundcheckfill");
				if(bool){
					$(this).parents("[data-select]")
						   .find("[data-select-all],[data-select-single]")
						   .addClass("icon-roundcheckfill clr-danger")
						   .removeClass("icon-round clr-light")
					opts.add();
				}else{
					$(this).parents("[data-select]")
						   .find("[data-select-all],[data-select-single]")
						   .addClass("icon-round clr-light")
						   .removeClass("icon-roundcheckfill clr-danger")
					opts.del();
				}
			});
			
			//联动
			function a(thix){
				var len = $(thix).parents("[data-select]").find("[data-select-all]").length;
				var len2 = $(thix).parents("[data-select]").find("[data-select-all].icon-roundcheckfill").length;
				if(len == len2){
					$(thix).parents("[data-select]")
					   .find("[data-select-total]")
					   .addClass("icon-roundcheckfill clr-danger")
					   .removeClass("icon-round clr-light")
				}else{
					$(thix).parents("[data-select]")
					   .find("[data-select-total]")
					   .addClass("icon-round clr-light")
					   .removeClass("icon-roundcheckfill clr-danger")
				}
			}
		});// end return this.each()
	};// end $.fn.allSelect
	
	$.fn.allSelect.defaults={
		add:null,
		del:null
	};
})(jQuery);

$(function(){
//	$("[data-select]").each(function(index, element) {
//		$(this).allSelect({
//			add:function(){
//				console.log(1)
//			},
//			del:function(){
//				console.log(2)
//			}
//		});
//    });
});