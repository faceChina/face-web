/* 版本：form-submit
 * 功能：表单监听
 * 作者：凡开
 * 时间：2014-04-1
 * 使用方法：给判断元素添加 data-form-control属性
 * 			给提交元素添加 data-submit 属性
 *			如果是非必填 给 data-submit="false"属性
*/

(function($){
	$.fn.formSubmit=function(options){
		var opts=$.extend({},$.fn.formSubmit.defaults,options);
		
		var thiz=$(this);
		var input = thiz.find("[data-form-control]");
		var submit = thiz.find("[data-submit]");
		var required = submit.data('submit');
		var reflag = (required === false) ? false : true;
		
		return this.each(function(){
			var flag = true, dataArr = [];
			//init
			function init(){
				var len = thiz.find("[data-form-control]").length;
				var len2 = 0;
				var nulllen = 0;
				thiz.find("[data-form-control]").each(function(index,element){
					var domName = this.tagName,
						keyName = this.name;
			
					if(flag){
	
						if(domName == 'INPUT' || domName == 'TEXTAREA'){
							dataArr.push(this.value);
						}else if(domName == 'SELECT'){
							var num = this.selectedIndex;
							dataArr.push(this.options[num].text);
						}
						if(index == (len-1)){
							flag = false;
						}

					}
					
					if(domName == 'INPUT' || domName == 'TEXTAREA'){
						
						if(this.value != dataArr[index]){
							len2++;
						}
						if(reflag && this.value != ''){
							nulllen++;
						}
					}
					if(domName == 'SELECT'){
						var num = this.selectedIndex;
						if(this.options[num].text != dataArr[index]){
							len2++;
						}
						if(reflag && this.options[num].text != '请选择'){
							nulllen++;
						}
					}
				});

				var iflen = (required === false) ? len2 > 0 : len == len2;

				//console.log(iflen,nulllen,len)

				if(iflen || nulllen == len){
					thiz.find("[data-submit]").removeClass("disabled");
				}else{
					thiz.find("[data-submit]").addClass("disabled");
				}
			}
			init();
			
			//input click
			input.on("keyup change",function(){
				init();
			});
			
		});// end return this.each()
	};// end $.fn.allSelect
	
	$.fn.formSubmit.defaults={};
})(jQuery);

$(function(){
	$("[data-form]").each(function(index, element) {
		$(this).formSubmit();
    });
});