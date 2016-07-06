/*
 * 层从下往上滑出
 * 使用方法：详见demo
 * */

var touchSlide = {
	settings:{
		winWidth : window.innerWidth,
		winHeight : window.innerHeight,
		bodyHeight : "100%",
		scrollTop : 0,
		template : ''
			+'<div class="slidebox">'
			+'	<table class="slidebox-outer">'
			+'		<thead>'
			+'			<tr>'
			+'				<td class="slidebox-header">'
			+'					<h3 class="slidebox-title"></h3>'
			+'					<span class="slidebox-close">✕</span>'
			+'				</td>'
			+'			</tr>'
			+'		</thead>'
			+'		<tbody>'
			+'			<tr>'
			+'				<td class="slidebox-main"></td>'
			+'			</tr>'
			+'		</tbody>'
			+'		<tfoot>'
			+'			<tr>'
			+'				<td class="slidebox-footer"></td>'
			+'			</tr>'
			+'		</tfoot>'
			+'	</table>'
			+'</div>'
	},
	defaults:{
		id:"",	//事件触发对象
		slideId:'',	//滑出内容对象
		width:"50%",	//滑出层宽度
		height:"50%",	//滑出层高度
		extra:null,	//当滑出同时的额外回调
		ok:null,	//确定按钮
		cancel:null,	//取消按钮
		button:[]	//自定义按钮组
	},
	//未touchStart前，初始化页面元素
	init:function(opts){
		if(!opts || !opts.id || !opts.slideId) return;
		$("#"+opts.slideId).hide();
	},
	//touch时创建层
	addSlide:function(opts){
		$(touchSlide.settings.template).appendTo($("body"));
		$(".slidebox-title").html(opts.title);
		$("#"+opts.slideId).show().appendTo($(".slidebox-main"));
		//右上角关闭按钮
		$(".slidebox-close").click(function(){
			touchSlide.close(opts);
		})
	},
	//设置背景大小并添加到body中
	addBackground:function(){
		touchSlide.settings.bodyHeight = (touchSlide.settings.bodyHeight = document.body.clientHeight) <= touchSlide.settings.winHeight ? touchSlide.settings.winHeight : document.body.clientHeight;
		touchSlide.settings.scrollTop = $("body").scrollTop();
	 	$("<div class='slidebox-mask'></div>").css("height",touchSlide.settings.bodyHeight).appendTo($("body")).animate({"opacity":1},300,"linear"); 
	},
	//添加按钮
	addButton:function(opts){
		var btnstr = "";	//按钮字符串
//		if((opts.button != null) && (opts.ok !=null || opts.cancel != null)) return; //属性 button 和 ok、cancel 不能同时使用
		if(opts.button == null && opts.ok == null && opts.cancel == null) return;
		console.log(opts)
		if(opts.ok !=null && opts.cancel !=null){
			opts.button = [{
				name:"确定"
			},{
				name:"取消"
			}];
		}else if(opts.ok !=null && opts.cancel ==null){
			opts.button = [{
				name:"确定"
			}];
		}else if(opts.ok ==null && opts.cancel !=null){
			opts.button = [{
				name:"取消"
			}];
		}
		
		for(var i = 0, len =  opts.button.length; i < len; i++){
			btnstr += '<a href="javascript:void(0)">'+opts.button[i].name+'</a>';
		}
		
		$(".slidebox-footer").html(btnstr);
		
		$(".slidebox-footer").find("a").each(function(){
			$(this).css({
				color:opts.button[$(this).index()].color,
				border:opts.button[$(this).index()].border,
				background:opts.button[$(this).index()].background
			}).click(function(){
				if(typeof opts.ok === "function" && $(this).html() === "确定"){
					var bool = opts.ok();
					if(bool) touchSlide.close(opts);
				}
				if(typeof opts.cancel === "function" && $(this).html() === "取消"){
					var bool = opts.cancel();
					if(bool) touchSlide.close(opts);
				}
				if(typeof opts.button[$(this).index()].callback === "function" && $(this).html() !== "确定" && $(this).html() !== "取消"){
					var bool = opts.button[$(this).index()].callback();
					if(bool) touchSlide.close(opts);
				}
			});
		});
	},
	//下滑出
	touchStartDown:function(opts){
		// 弹出滑出层的额外操作
		if(opts.extra != null) {
			opts.extra();
		} 
		touchSlide.addBackground();
		touchSlide.addSlide(opts);
		touchSlide.addButton(opts);

		//内容效果
		$(".slidebox").css({
			"display":"block",
 			"width":(parseInt(opts.width)/100 * touchSlide.settings.winWidth) || "auto",
 			"height":(parseInt(opts.height)/100 * touchSlide.settings.winHeight) || "auto",
 			"bottom":-touchSlide.settings.winHeight-touchSlide.settings.scrollTop
 		}).animate({
 			"bottom":"0"
 		},300,"linear");
		
		var headerHeight = $(".slidebox-header").outerHeight(true);
		var footerHeight = $(".slidebox-footer").outerHeight(true);
		$("#"+opts.slideId).css({
			maxHeight:touchSlide.settings.winHeight-headerHeight-footerHeight,
			overflow:"auto"
		});
	},
	//关闭滑出层
	close:function(opts){
		$(".slidebox").animate({
			"bottom":-touchSlide.settings.winHeight-touchSlide.settings.scrollTop
		},300,"linear",function(){
			$("#"+opts.slideId).hide().appendTo($("body"));
			$(this).remove();
		});
		
		$(".slidebox-mask").animate({
			"opacity":0
		},300,"linear",function(){
			$(this).remove();
		}); 
	},
	//滑出
	down:function(opts){
		var config = $.extend({},touchSlide.defaults,opts);
		touchSlide.init(config);
		
		//兼容触屏与鼠标操作
		var support = (function () {
	        var support = {
	            touch: !!(('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch)
	        };
	        // Export object
	        return support;
	    })();
	
		var touchEvents = {
	        start: support.touch ? 'touchstart' : 'click'
	    }
		
		document.getElementById(config.id).addEventListener(touchEvents.start, function(){
			touchSlide.touchStartDown(config);
		}, false);
	},
	//关闭
	closed:function(id){
		$(".slidebox").animate({
			"bottom":-touchSlide.settings.winHeight-touchSlide.settings.scrollTop
		},300,"linear",function(){
			$("#"+id).hide().appendTo($("body"));
			$(this).remove();
		});
		
		$(".slidebox-mask").animate({
			"opacity":0
		},300,"linear",function(){
			$(this).remove();
		});
	},
	//关闭所有
	closedAll:function(){
		
	},
	//自动滑出
	autoPlay:function(opts){
		var config = $.extend({},touchSlide.defaults,opts);
		touchSlide.init(config);
		
		//兼容触屏与鼠标操作
		var support = (function () {
	        var support = {
	            touch: !!(('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch)
	        };
	        // Export object
	        return support;
	    })();
	
		var touchEvents = {
	        start: support.touch ? 'touchstart' : 'click'
	    }
		
		touchSlide.touchStartDown(config);
	}
	
}