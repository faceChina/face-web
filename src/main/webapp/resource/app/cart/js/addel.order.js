/* 增减 */
(function($){
	$.fn.addel = function(options){

		var opts = $.extend({},$.fn.addel.defaults,options);
		
		return this.each(function(){

			var thiz = $(this),
				objAdd=$(this).find(".addel-add")[0],
				objDel=$(this).find(".addel-del")[0];
			// $(this).find(".addel-add")[0].add("click",function(){
				
				
			// });
			if (objAdd) { 
				objAdd.addEventListener('touchstart', function(event,obj) {
					var number = Number(thiz.find("input[data-number]").val());
					opts.add();
					++number;
					//number = number >= opts.number ? opts.number : number;
					if(number > opts.number){
						number = opts.number;
						artTip("超过最大购买数量！");
					}else{
						number = number;
					}
					thiz.find("input[data-number]").val(number);
					

					 //（店铺和总店数）数量更改
					calShopGoodsQuantity();
					 calGoodsQuantity();
				    //（店铺和总店数）价格更改
					 getShopTotalPrice($("#box"));
					getTotalPrice();
				});
			}
			// $(this).find(".addel-del")[0].on("click",function(){
				
			// });
			if (objDel) {
					objDel.addEventListener('touchstart', function(event,obj) {
					var number = Number(thiz.find("input[data-number]").val());
					opts.del();
					--number;
					//number = number <= 1 ? 1 : number;
					if(number < 1){
						number = 1;
						artTip("购买数量不能低于1！");
					}else{
						number = number;
					}
					thiz.find("input[data-number]").val(number);
					 //（店铺和总店数）数量更改
					calShopGoodsQuantity();
				    calGoodsQuantity();
				    //（店铺和总店数）价格更改
					 getShopTotalPrice($("#box"));
					getTotalPrice();
				});
			}
			
		});
		$.fn.addel.defaults = {
			number:1,
			add:null,
			del:null
		}
	}
})(jQuery);

$(function(){
//	$.addel();//初始化页面时
});



