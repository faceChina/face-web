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
		var handles=thiz.find("[pluginHandles='handles']");
		var content=thiz.find("[pluginContent='content']");
		var contentChild;
		var len;
//		var callback = opts.callback;
//		var removeCallback = opts.removeCallback;
		
		return this.each(function(){
			handles.on("click",function(){
				allSelect();
			});
			content.find("input[type='checkbox']").each(function(){
				$(this).on("click",function(){
					contentChild=content.find("input[type='checkbox']:not(:disabled)");
					len=contentChild.length;
					
					var isChecked = $(this).prop("checked");
					if(isChecked){
						$(this).attr("checked",true);
					}else{
						$(this).attr('checked',false);
					}
					
					var len2=content.find("input[type='checkbox']:checked").length;
					if(len == len2){
						handles.attr("checked",true);
					}else{
						handles.attr('checked',false);
					}
				});
			});
		});// end return this.each()
		
		function allSelect(){
			contentChild=content.find("input[type='checkbox']:not(:disabled)");
			len=contentChild.length;
			var len2=content.find("input[type='checkbox']:checked").length;
			if(len == len2){
				contentChild.attr('checked',false);
			}else{
				contentChild.attr("checked",true);
			}
		}
	};// end $.fn.allSelect
	
	$.fn.allSelect.defaults={};
})(jQuery);

$(function(){
	$("[plugin='allSelect']").each(function(index, element) {
		$(this).allSelect();
    });
});