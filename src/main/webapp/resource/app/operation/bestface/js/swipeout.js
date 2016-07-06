//滑出隐藏操作
var swipeout = (function(){

	var startY,
		startX,
		thiz,
		offsetX = 0,
		typeActive = false,
		selfWidth,
		activeWidth;

	
	//手指按下的处理事件
	function startHandler(e){
		//console.log("开始滑动");
		var _self = this;
		selfWidth = $(_self).width();

		activeWidth = $(_self).closest("[data-swipeout='box']").find('[data-active="true"]').eq(0).width();

		if(thiz){
			translate(0,300,$(thiz).closest("[data-swipeout='box']")[0]);

		}

		startY = e.targetTouches[0].pageY;
		startX = e.targetTouches[0].pageX;


		_self.addEventListener("touchmove", moveHandler ,false);
		_self.addEventListener("touchend", endHandler ,false);

	}

	//手指移动的处理事件
	function moveHandler(e){
		//console.log("滑动");
		//兼容chrome android，阻止浏览器默认行为
		e.preventDefault();
		var _self = this;
				
		var endY = e.targetTouches[0].pageY;
		var endX = e.targetTouches[0].pageX;
		
		offsetX = endX-startX;

		if(offsetX<0){
			if(activeWidth <Math.abs(offsetX)) offsetX = -activeWidth;
			translate(offsetX,0,$(_self).closest("[data-swipeout='box']")[0]);

		}else{

			return;
		}	
	}

	//手指抬起的处理事件
	function endHandler(e){
		//console.log("停止滑动");

		var _self = this,
			transformX = 0,
			href = "";
			thiz = _self;

		if(offsetX<0 && Math.abs(offsetX)>activeWidth/3){
			typeActive = true;
			transformX = -activeWidth;	

		}else{

			if(offsetX == 0 && !typeActive){
				href = $(_self).attr("data-href");
				window.location.href = href;
				return;
			}
				typeActive = false;
				transformX = 0;
		}
	
		translate(transformX,300,$(_self).closest("[data-swipeout='box']")[0]);
		offsetX = 0;
		

		_self.removeEventListener("touchmove", moveHandler ,false);
		_self.removeEventListener("touchend", endHandler ,false);
	}

	//滑动效果
	var translate = function( dist, speed, ele ) {
		
		if( !!ele ){ ele=ele.style; }
		ele.webkitTransitionDuration =  ele.MozTransitionDuration = ele.msTransitionDuration = ele.OTransitionDuration = ele.transitionDuration =  speed + 'ms';
		ele.webkitTransform = 'translate(' + dist + 'px,0)' + 'translateZ(0)';
		ele.msTransform = ele.MozTransform = ele.OTransform = 'translateX(' + dist + 'px)';		
	}


	$(function(){
		var self = document.querySelectorAll("[data-swipeout='true']");

		for(var i = 0;i<self.length;i++){
			self[i].addEventListener("touchstart", startHandler ,false);
		}
	});

})();