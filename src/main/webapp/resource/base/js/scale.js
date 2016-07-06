(function($){
	$.fn.scale=function(options){
		var opts=$.extend({},$.fn.scale.defaults,options);
		
		return this.each(function(){
			var thiz=$(this);
			var img=$(this).find("img");
			
			var w=thiz.width();
			var h=thiz.height();
			var imgw=img.width();
			var imgh=img.height();
			
			var halfw=(w-imgw)/2;
			var halfh=(h-imgh)/2;
			
			var ratio=w/h;
			var imgratio=imgw/imgh;
			
			//console.log(w+"--"+h+"--"+imgw+"--"+imgh);
			//图片宽高小于外框
			if(imgw < w && imgh < h){
				//console.log(imgw+"==="+imgh)
				img.css({
					display:"block",
					margin:"0 auto",
					marginTop:halfh
				});
			}
			
			//图片宽高比大小外框宽高比
			if(imgratio > ratio && imgw > w){
				imgw=w;
				imgh=w/imgratio;
				halfh=(h-imgh)/2;
				img.css({
					display:"block",
					width:imgw,
					height:imgh,
					marginTop:halfh
				});
			}
			
			//图片宽高比小于外框宽高比
			if(imgratio < ratio && imgh > h){
				imgh=h;
				imgw=h*imgratio;
				halfw=(w-imgw)/2;
				img.css({
					display:"block",
					width:imgw,
					height:imgh,
					margin:"0 auto"
				});
			}
			
		});// end return this.each()
		
	}// end $.fn.fk_scale
	
	$.fn.scale.defaults={}
})(jQuery);


$(function(){
	$("[plugin='scale']").each(function(){
		$(this).scale();
	});
});