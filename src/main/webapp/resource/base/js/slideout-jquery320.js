/*
 * 层从四个方向滑出
 * 使用方法：详见demo
 * */
;var touchSlide = (function(){
	//私有变量
	var winWidth = "100%",
		  winHeight = "100%",
		  bodyHeight = "100%",
		  scrollTop = 0,
		  btnStr = "";
	
	//未touchStart前，初始化页面元素
	function init(opts){
		$("#"+opts.slideId).addClass("slidebox");
	}
	
	//设置背景大小并添加到body中
	function addBackground(opts){
		winWidth = window.innerWidth;
	 	winHeight = window.innerHeight;
	 	bodyHeight = (bodyHeight = document.body.clientHeight) <= winHeight ? winHeight : document.body.clientHeight;
	 	scrollTop = $("body").scrollTop();
	 	var box = $('body .inbox').length >0?$('.inbox'):$("body");
	 	$("<div id='slideout-bg'></div>").appendTo(box).addClass("slidebg").css("height",bodyHeight);
	}
	
	//设置按钮并添加到body中
	function addButton(opts){
		btnStr = "<div id='btns' class='slidebtns'>" +
					 "<div id='confirm'>确认</div>" +
					 "<div id='cancel'>取消</div>" +
					 "</div>";
		var box = $('body .inbox').length >0?$('.inbox'):$("body");
		$(btnStr).appendTo(box);
		$("#confirm,#cancel").css({
			"float":"left",
			"width":(parseInt(opts.width)/100*winWidth/2),
			"border-right":"1px solid #ddd"
		});
	}
	
	//下滑出
	function touchStartDown(opts){
		// 弹出滑出层的额外操作
		if(typeof opts.extra == "function") opts.extra(); 
		
		addBackground(opts);
		addButton(opts);
		
		//背景效果
		$("#slideout-bg").animate({"opacity":1},300,"linear"); 
		
		//内容效果
		$("#"+opts.slideId).css({
			"display":"block",
 			"width":(parseInt(opts.width)/100*winWidth),
 			"height":(parseInt(opts.height)/100*winHeight),
 			"bottom":-winHeight-scrollTop
 		}).animate({
 			"bottom":"40px"
 		},300,"linear");
		
		//按钮效果
		$("#btns").animate({
			"bottom":"0"
		},300,"linear",function(){
			document.getElementById("cancel").addEventListener(touchEvents.start, function(){close(opts)}, false);
 			document.getElementById("confirm").addEventListener(touchEvents.start, function(){
 				close(opts);
 				//点击确定后的回调函数
 				if(typeof opts.ok == "function") opts.ok();
 			}, false);
		});
		
	}

	var support = (function () {
        var support = {
            touch: !!(('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch)
        };
    
        // Export object
        return support;
    })();
	
	//关闭弹出层
	function close(opts){
		$("#"+opts.slideId).animate({
			"bottom":-winHeight-scrollTop
		},300,function(){
			$(this).css("display","none");
			$("#slideout-bg,#btns").remove();
			// 关闭滑出层的回调函数
			if(typeof opts.cancel == "function") opts.cancel();
		});
	}
	var touchEvents = {
            start: support.touch ? 'touchstart' : 'click'
        }
	
	return {
		down:function(opts){
			if(!opts || opts == null) return;
			if(!opts.id || !opts.slideId)  return;
			init(opts);
			document.getElementById(opts.id).addEventListener(touchEvents.start, function(){touchStartDown(opts);}, false);
		}
	}
}());

	

/*;var touchSlide = {
		//未touchStart前，初始化页面元素
		init:function(opts){
			$("#"+opts.slideId).addClass("slidebox");
		},
		//touchStart时，初始化页面元素
		touchStart:function(event,opts){
			winWidth = window.innerWidth;
		 	winHeight = window.innerHeight;
		 	bodyHeight = (bodyHeight = document.body.clientHeight) <= winHeight ? winHeight : document.body.clientHeight;
		 	scrollTop = $("body").scrollTop();
		 	$("<div id='slideout-bg'></div>").appendTo($("body")).addClass("slidebg").css({
		 		"height":bodyHeight,
		 	});
		},
		//右滑出
		touchStartRight:function(opts){
			this.touchStart(event,opts);
			$("<div id='closed'>取<br>消</div>").appendTo($("body")).css({
		 		"width":"40px",
				"height":(parseInt(opts.height)/100*winHeight)-20,
				"padding-top":((parseInt(opts.height)/100*winHeight)-20-60)/2,
				"background":"rgba(255,255,255,0.9)",
				"border-radius":"6px",
				"color":"#666",
				"text-align":"center",
				"line-height":"40px",
				"position":"fixed",
			 	"right":"10px",
		 		"top":"10px",
	 			"z-index":11,
	 			"opacity":"0"
		 	}).animate({
		 		"opacity":"1"
		 	},300);
			
			$("#"+opts.slideId).css({
	 			"display":"block",
	 			"width":(parseInt(opts.width)/100*winWidth),
	 			"height":(parseInt(opts.height)/100*winHeight)-20,
	 			"border-radius":"6px",
	 			"top":"10px",
	 			"right":-winWidth
	 		}).animate({
	 			"right":"60px"
	 		},0,function(){
// 	 			document.getElementById("slideout-bg").addEventListener("touchend", close, false);
	 			document.getElementById("closed").addEventListener("touchend", close, false);
	 			function close(){
	 				$("#"+opts.slideId).animate({
	 						"right":-winWidth,
	 						"display":"none"
	 				},0);
	 				$("#slideout-bg,#closed").animate({
	 					opacity:"0"
	 				},300,function(){
	 					$("#slideout-bg,#closed").remove();
	 				});
	 			}
	 		});
		},
		//左滑出
		touchStartLeft:function(opts){
			this.touchStart(event,opts);
			$("<div id='closed'>取<br>消</div>").appendTo($("body")).css({
		 		"width":"40px",
				"height":(parseInt(opts.height)/100*winHeight)-20,
				"padding-top":((parseInt(opts.height)/100*winHeight)-20-60)/2,
				"background":"rgba(255,255,255,0.9)",
				"border-radius":"6px",
				"color":"#666",
				"text-align":"center",
				"line-height":"40px",
				"position":"fixed",
			 	"left":"10px",
		 		"top":"10px",
	 			"z-index":11,
	 			"opacity":"0"
		 	}).animate({
		 		"opacity":"1"
		 	},300);
			$("#"+opts.slideId).css({
				"display":"block",
				"width":(parseInt(opts.width)/100*winWidth),
	 			"height":(parseInt(opts.height)/100*winHeight)-20,
	 			"border-radius":"6px",
	 			"top":"10px",
	 			"left":-winWidth
	 		}).animate({
	 			"left":"60px"
	 		},0,function(){
	 			document.getElementById("closed").addEventListener("touchend", close, false);
	 			function close(){
	 				$("#"+opts.slideId).animate({
		 					"left":-winWidth,
							"display":"none"
	 				},0);
	 				$("#slideout-bg,#closed").animate({
	 					opacity:"0"
	 				},300,function(){
	 					$("#slideout-bg,#closed").remove();
	 				});
	 			}
	 		});
		},
		//上滑出
		touchStartUp:function(opts){
			this.touchStart(event,opts);
			$("<div id='closed'>取消</div>").appendTo($("body")).css({
		 		"width":(parseInt(opts.width)/100*winWidth)-20,
				"height":"40px",
				"background":"rgba(255,255,255,0.9)",
				"border-radius":"6px",
				"color":"#666",
				"text-align":"center",
				"line-height":"40px",
				"position":"fixed",
			 	"left":"10px",
		 		"top":"10px",
	 			"z-index":11,
	 			"opacity":"0"
		 	}).animate({
		 		"opacity":"1"
		 	},300);
			
			$("#"+opts.slideId).css({
				"display":"block",
				"width":(parseInt(opts.width)/100*winWidth)-20,
	 			"height":(parseInt(opts.height)/100*winHeight),
	 			"border-radius":"6px",
	 			"top":-winHeight-scrollTop,
	 			"left":"10px"
	 		}).animate({
	 			"top":"60px"
	 		},0,function(){
	 			document.getElementById("closed").addEventListener("touchend", close, false);
	 			function close(){
	 				$("#"+opts.slideId).animate({
		 					"top":-winHeight-scrollTop,
							"display":"none"
	 				},0);
	 				$("#slideout-bg,#closed").animate({
	 					opacity:"0"
	 				},300,function(){
	 					$("#slideout-bg,#closed").remove();
	 				});
	 			}
	 		});
		},
		//下滑出
		touchStartDown:function(opts){
			this.touchStart(event,opts);
			//按钮组
			var btnStr = "<div id='btns' class='slidebtns'>" +
					"<div id='confirm'>确认</div>" +
					"<div id='cancel'>取消</div>" +
					"</div>";
			$(btnStr).appendTo($("body"));
			$("#confirm,#cancel").css({
				"float":"left",
				"width":(parseInt(opts.width)/100*winWidth/2),
				"border-right":"1px solid #eee"
			});
			
			$("#slideout-bg").animate({"opacity":1},300);
			$("#"+opts.slideId).css({
				"display":"block",
	 			"width":(parseInt(opts.width)/100*winWidth),
	 			"height":(parseInt(opts.height)/100*winHeight),
	 			"bottom":-winHeight-scrollTop
	 		}).animate({
	 			"bottom":"0"
	 		},0,function(){
	 			document.getElementById("cancel").addEventListener("touchend", close, false);
	 			document.getElementById("confirm").addEventListener("touchend", function(){close();opts.callback();}, false);
	 			function close(){
	 				$("#"+opts.slideId).animate({
		 					"bottom":-winHeight-scrollTop,
							"display":"none"
	 				},0,function(){
	 					$("#slideout-bg,#btns").remove();
	 				});
	 			}
	 		});
		},
		//触发右滑出
		right:function(opts){
			this.init(opts);
			document.getElementById(opts.id).addEventListener("touchstart", function(){touchSlide.touchStartRight(opts);}, false);
		},
		//触发左滑出
		left:function(opts){
			this.init(opts);
			document.getElementById(opts.id).addEventListener("touchstart", function(){touchSlide.touchStartLeft(opts);}, false);
		},
		//触发上滑出
		up:function(opts){
			this.init(opts);
			document.getElementById(opts.id).addEventListener("touchstart", function(){touchSlide.touchStartUp(opts);}, false);
		},
		//触发下滑出
		down:function(opts){
			this.init(opts);
			document.getElementById(opts.id).addEventListener("touchstart", function(){touchSlide.touchStartDown(opts);}, false);
		}
};*/