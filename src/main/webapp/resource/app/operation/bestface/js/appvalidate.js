(function($){
	$.fn.appvalidate=function(options){
		var opts=$.extend({},$.fn.appvalidate.defaults,options);

		return this.each(function(){
			console.log(opts)
		});
	}

	$.fn.appvalidate.defaults={}
})(jQuery);


/*var appvalidate = function(options){
	console.log()
}*/