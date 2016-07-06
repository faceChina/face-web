/**/
(function($){
	$.slidefocus=function(options){
		var opts=$.extend({},$.slidefocus.defaults,options);
		
		var domNode = document.getElementById(opts.dom),
			  dom = $("#"+opts.dom),
			  parentDom = dom.parent();
			  ul = dom.find("ul:first"),
			  len = dom.find("ul li").length,
			  width = 0,
			  height = 0;
			  parentDomWidth = 0;
		var startPos,			//取touchStart的坐标值
			  endPos,			//取touchMove之后的坐标位移
			  direction,			//触屏滑动的方向
			  setIntervalId,	//返回的定时调用函数的值
			  total = 0,			//translate动画移动的距离
		      i = 0,				//页码及循环计算li的位置
			  str = "";			//页码字符串
		
		if(dom.length <= 0) return console.log("找不到轮播图对象");
			
		//设置相关样式
		var style;
		var styleSheet ="#"+opts.dom+"{overflow:hidden;position:relative;}"
		+"#"+opts.dom+" ul,"
		+"#"+opts.dom+" ul li,"
		+"#"+opts.dom+" ul li a,"
		+"#"+opts.dom+" ul li a img{display:block;width:100%;height:100%;}"
		+"#"+opts.dom+" ul{position:relative;}"
		+"#"+opts.dom+" ul li{position:absolute;}"
		+"#"+opts.dom+" .slidepage{width:100%;position:absolute;bottom:0.5em;text-align:center;}"
		+"#"+opts.dom+" .slidepage span{display:inline-block;width:0.5em;height:0.5em;border-radius:100%;background-color:rgba(255,255,255,0.5);margin:0 0.25em;}"
		+"#"+opts.dom+" .slidepage span.active{background-color:rgba(255,0,0,1)}";
		if(document.getElementsByTagName("style").length > 0){
			var styleSheetLen = document.getElementsByTagName("style").length;
			style = document.getElementsByTagName("style")[styleSheetLen-1];
		}else{
			style = document.createElement("style");
			document.getElementsByTagName("head")[0].appendChild(style);
		}
		text = document.createTextNode(styleSheet);
		style.appendChild(text);
		
		//只有两张图时
		if(len ==2){
			ul.children().clone().appendTo(ul);
			newLen = dom.find("ul li").length;
		}else{
			newLen = len;
		}

		//根据宽高值的不同，计算尺寸
		function size(){
			parentDomWidth = parentDom.width();
			
			if(opts.width == "" || opts.width == null) {
				width = parentDomWidth;
			}else if(opts.width.indexOf("%") > -1){
				width = parseFloat(opts.width)/100 * parentDomWidth;
			}else if(opts.width == "auto"){
				width = parseFloat(window.innerWidth);
			}else if(opts.width == "data"){
				var dataWidth = String(dom.data("width"));
				if(dataWidth == "" || dataWidth == null) {
					width = parentDomWidth;
				}else if(dataWidth.indexOf("%") > -1){
					width = parseFloat(dataWidth)/100 * parentDomWidth;
				}else if(dataWidth == "auto"){
					width = parseFloat(window.innerWidth);
				}else{
					width = parseFloat(dataWidth);
				}
			}else{
				width = parseFloat(opts.width);
			}
			
			if(opts.height == "" || opts.height == null) {
				height = parseFloat(width)/100 * parentDomWidth;
			}else if(opts.height.indexOf("%") > -1){
				height = parseFloat(opts.height)/100 * width;
			}else if(opts.height == "auto"){
				height = parseFloat(window.innerHeight);
			}else if(opts.height == "data"){
				var dataHeight = String(dom.data("height"));
				if(dataHeight == "" || dataHeight == null) {
					height = parseFloat(dataHeight)/100 * parentDomWidth;
				}else if(dataHeight.indexOf("%") > -1){
					height = parseFloat(dataHeight)/100 * width;
				}else if(dataHeight == "auto"){
					height = parseFloat(window.innerHeight);
				}else{
					height = parseFloat(dataHeight);
				}
			}else{
				height = parseFloat(opts.height);
			}
			
			dom.css({
				"width":width,
				"height":height
			});
		};
		
		size();
		
		//改变屏幕尺寸时
		$(window).resize(function(){
			size();
		});
		
		//初始化状态下，根据参数设定是水平排列还是垂直排列
		(function init(){
			//水平
			if(opts.direction === "left" || opts.direction === "right"){
				dom.find("ul li").each(function(index){
					$(this).css({"left":width*index})
				})
				if(len > 1) dom.find("ul li:last-child").css({"left":-width});		//最后一个排到最前左边
			}
			//垂直
			if(opts.direction === "up" || opts.direction === "down"){
				dom.find("ul li").each(function(index){
					$(this).css({"top":height*index})
				})
				if(len > 1) dom.find("ul li:last-child").css({"top":-height});		//最后一个排到最前上边
			}
			//添加页码
			addPage();
			//自动滑动
			if(len > 1) autoPlay();
		})();
		
		
		//滑动开始
		function touchStart(e) {
			clearInterval(setIntervalId);
			var touch=e.touches[0]; //touches数组对象获得屏幕上所有的touch，取第一个touch
			startPos = {x:touch.pageX,y:touch.pageY}; //取第一个touch的坐标值
			if(window.attachEvent){//IE
				domNode.attachEvent('ontouchmove',touchMove);//触发touchMove、touchEnd 事件
				domNode.attachEvent('ontouchend',touchEnd);
			}else{
				domNode.addEventListener('touchmove',touchMove,false);//触发touchMove、touchEnd 事件
				domNode.addEventListener('touchend',touchEnd,false);
			}
		}
		
		//滑动
		function touchMove(e) {
			clearInterval(setIntervalId);
//			e.preventDefault();
			//当屏幕有多个touch或者页面被缩放过，就不执行touchMove操作
		    if(e.touches.length > 1 || e.scale && e.scale !== 1) return; 
		    //touches数组对象获得屏幕上所有的touch，取每次move时的touch
			var touch=e.touches[0]; 
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
			
			//左右 或 上下 滑动时的效果
			if((opts.direction == "left" || opts.direction == "right") && (direction == "left" || direction == "right")){
				transform(total+endPos.x,0);
				e.preventDefault();
			}else if((opts.direction == "up" || opts.direction == "down") && (direction == "up" || direction == "down")){
				transform(0,total+endPos.y);
			}
		}

		//滑动结束
		function touchEnd(e) {
			//左右 或 上下 滑动结束后的效果
			if(opts.direction == "left" || opts.direction == "right"){
				if(direction == "left" && Math.abs(endPos.x) >60){
					left();
				}else if(direction == "right" && Math.abs(endPos.x) >60){
					right();
				}else{
					transform(total,0);
				}
			}else if(opts.direction == "up" || opts.direction == "down"){
				if(direction == "up" && Math.abs(endPos.y) >60){
					up();
				}else if(direction == "down" && Math.abs(endPos.y) >60){
					down();
				}else{
					transform(0,total);
				}
			}
			
			//滑动结束后，恢复自动滑动
			autoPlay();
			
			if(window.detachEvent){//IE
				domNode.detachEvent('ontouchmove',touchMove,false); //解绑事件
			    domNode.detachEvent('ontouchend',touchEnd,false); //解绑事件
			}else{
				domNode.removeEventListener('touchmove',touchMove,false); //解绑事件
			    domNode.removeEventListener('touchend',touchEnd,false); //解绑事件
			}
		}
		
		if(len == 1) return;
		if(window.attachEvent){//IE
			domNode.attachEvent('ontouchstart',touchStart); //绑定事件
		}else{
			domNode.addEventListener('touchstart',touchStart,false); //绑定事件
		}
		
		//添加页码
		function addPage(){
			var isPage = true;
			
			//只有一张图时，无页码
			if(len <= 1) {
				isPage = false;
			}else{
				if(opts.page == "data"){
					var dataPage = dom.data("page");
					if(dataPage == false || dataPage == ""){
						isPage = false;
					}else{
						isPage = true;
					}
				}else if(opts.page == true){
					isPage = true;
				}else{
					isPage = false;
				}
			}

			if(isPage){
				for(var j=0;j<len;j++){
					str += "<span></span>"
				}
				$(str).appendTo($("<div class='slidepage'></div>").appendTo(dom));
				$(".slidepage span:first-child").addClass("active");
			}
		}
		
		//自动滑动
		function autoPlay(){
			if(opts.play){
				if(opts.direction == "left"){
					setIntervalId = setInterval(left,opts.time);
				}else if(opts.direction == "right"){
					setIntervalId = setInterval(right,opts.time);
				}else if(opts.direction == "up"){
					setIntervalId = setInterval(up,opts.time);
				}else if(opts.direction == "down"){
					setIntervalId = setInterval(down,opts.time);
				}
			}
		};
		
		//动画
		function transform(x,y){
			ul.css({
	    		'-webkit-transform': 'translate('+x+'px,'+y+'px)',
	    		'-webkit-transition-duration': '300ms',
	    		'-webkit-transition-timing-function': 'ease-out',
	    		'-moz-transform': 'translate('+x+'px,'+y+'px)',
	    		'-moz-transition-duration': '300ms',
	    		'-moz-transition-timing-function': 'ease-out',
	    		'-ms-transform': 'translate('+x+'px,'+y+'px)',
	    		'-ms-transition-duration': '300ms',
	    		'-ms-transition-timing-function': 'ease-out',
	    		'-o-transform': 'translate('+x+'px,'+y+'px)',
	    		'-o-transition-duration': '300ms',
	    		'-o-transition-timing-function': 'ease-out',
	    		'transform': 'translate('+x+'px,'+y+'px)',
	    		'transition-duration': '300ms',
	    		'transition-timing-function': 'ease-out'
	    	});
		}
		
		//左
		function left(){
	    	total = total - width;
	    	transform(total,0);
	    	i++;
	    	ul.find("li").eq((i+1)%newLen).css({"left":width - total});
			if(i>=newLen){
				i=0;
			}
			if(len != 2){
				if(opts.page) $(".slidepage span").removeClass("active").eq(i).addClass("active");
			}else{
				if(opts.page) $(".slidepage span").removeClass("active").eq(i%2).addClass("active");
			}
		}
		
		//右
		function right(){
			total = total + width;
			transform(total,0);
			i--;
			ul.find("li").eq((i-1)%newLen).css({"left":-width - total});
			if(i<=-newLen){
				i=0;
			}
			if(len != 2){
				if(opts.page) $(".slidepage span").removeClass("active").eq(i).addClass("active");
			}else{
				if(opts.page) $(".slidepage span").removeClass("active").eq(i%2).addClass("active");
			}
		}
		
		//上
		function up(){
	    	total = total - height;
	    	transform(0,total);
	    	i++;
	    	ul.find("li").eq((i+1)%newLen).css({"top":height - total});
			if(i>=newLen){
				i=0;
			}
			if(len != 2){
				if(opts.page) $(".slidepage span").removeClass("active").eq(i).addClass("active");
			}else{
				if(opts.page) $(".slidepage span").removeClass("active").eq(i%2).addClass("active");
			}
		}
		
		//下
		function down(){
			total = total + height;
			transform(0,total);
			i--;
			ul.find("li").eq((i-1)%newLen).css({"top":-height - total});
			if(i<=-newLen){
				i=0;
			}
			if(len != 2){
				if(opts.page) $(".slidepage span").removeClass("active").eq(i).addClass("active")
			}else{
				if(opts.page) $(".slidepage span").removeClass("active").eq(i%2).addClass("active");
			}
		}
		
		return this.each(function(){});//return this.each
	}//$.slidefocus
	
	$.slidefocus.defaults={
			dom:null,				 //元素id，无“#”字符
			width:"100%",		//百分比：值为比值*父元素宽，定值：取定值（无单位或px单位），auto:取屏幕的宽度，data:取页面中data-width的值(包括百分比、定值、auto)
			height:"100%",		//百分比：值为比值*width，定值：取定值（无单位或px单位），auto:取屏幕的高度，data:取页面中data-height的值(包括百分比、定值、auto)
			play:true, 				 //自动播放(true/false),和direction、time组合使用
			direction:"left", 		//上(up)右(right)下(down)左(left)
			time:"3000", 			 //时间间隔(单位毫秒)
			page:true,			//是否显示页码
	}//$.slidefocus.defaults
})(jQuery);