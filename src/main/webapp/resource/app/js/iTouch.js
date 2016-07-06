(function($){
	$.fn.iTouch = function(options){
		var opts = $.extend({},$.fn.iTouch.defaults,options);
		var startPos,endPos,direction
		var height = $(this).outerHeight(true);
		
		return this.each(function(index,element){
			init();
			this.addEventListener("touchstart",touchStart,false);
		});
		
		//初始化
		function init(){
			if(typeof opts.extra == "function") {
		    	opts.extra();
		    }
		}
		
		//滑动开始
		function touchStart(e) {
			//初始化
			startPos = {};
			endPos = {};
			direction = "";
			
			//touchStart还原界面
			$("[data-touch]").each(function(key,dom){
				dom.style.webkitTransform = "translate3d(0px,0px,0px)";
				dom.style.webkitTransitionDuration="200ms";
			})
			
			//touches数组对象获得屏幕上所有的touch，取第一个touch
			var touch=e.touches[0]; 
			//取第一个touch的坐标值
			startPos = {x:touch.pageX,y:touch.pageY}; 
			//触发touchMove、touchEnd 事件
		    this.addEventListener('touchmove',touchMove,false);
		    this.addEventListener('touchend',touchEnd,false);

		}
		 //滑动
		 function touchMove(e) {
			//当屏幕有多个touch或者页面被缩放过，就不执行touchMove操作
		    if(e.touches.length > 1 || e.scale && e.scale !== 1) return;
		    //touches数组对象获得屏幕上所有的touch，取每次move时的touch
			var touch=e.touches[0];
		    //取touchMove之后的位移
			endPos = {x:touch.pageX - startPos.x,y:touch.pageY - startPos.y};
			// 滑动方向
			if(Math.abs(endPos.x) < Math.abs(endPos.y) && endPos.y < 0){
				direction = "up";
			}else if(Math.abs(endPos.x) < Math.abs(endPos.y) && endPos.y > 0){
				direction = "down";
			}else if(Math.abs(endPos.x) > Math.abs(endPos.y) && endPos.x < 0){
				direction = "left";
			}else if(Math.abs(endPos.x) > Math.abs(endPos.y) && endPos.x > 0){
				direction = "right";
			}
			//自定义操作
		    if(direction === "left"){
		    	//阻止触摸事件的默认行为，即阻止滚屏
		        e.preventDefault();
		    	
		        if(endPos.x <= -height){
			    	endPos.x = -height
			    }
		     	this.style.webkitTransform = "translate3d(" + endPos.x + "px,0px,0px)";
		    	this.style.webkitTransitionDuration="200ms";
		     }
		}
		 
		//滑动结束
		function touchEnd(e) {
			if(endPos.x <= -height/3){
		    	endPos.x = -height;
		    }else{
		    	endPos.x = 0
		    }
			this.style.webkitTransform = "translate3d(" + endPos.x + "px,0px,0px)";
			this.style.webkitTransitionDuration="200ms";
			//解绑事件
			this.removeEventListener('touchmove',touchMove,false);
			this.removeEventListener('touchend',touchEnd,false);
		}
		
		$.fn.iTouch.defaults = {
			extra:null
		}
	}
})(jQuery);