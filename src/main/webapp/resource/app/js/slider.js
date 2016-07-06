/*  
* @description:Slider mobile touch
* @version: 1.0
* @author: wmm 
* @update: 2014-10-31
* @mail: 421543076@qq.com
*/

function Slider(opts){
	//构造函数需要的参数

	this.wrap = typeof opts.dom == 'string' ? document.getElementById(opts.dom) : opts.dom;
	this.outer = this.wrap.children[0];
	this.dot = opts.dot?opts.dot:false;//圆点
	this.sliderdir = opts.sliderdir?opts.sliderdir:false; //true 左右滚动 false 上下滚动
	this.autoplay = opts.autoplay?opts.autoplay:false; //自动播放
	this.titOnClassName = opts.titOnClassName?opts.titOnClassName:'active'; //圆点Class
	this.load = opts.load?opts.load:false;//异步加载
	this.refresh = opts.refresh?opts.refresh:false;//每次滑动刷新内容
	this.clickLoad = opts.clickLoad?opts.clickLoad:false;//每次滑动刷新内容
	if(opts.ajaxLoadFun) this.ajaxLoadFun = opts.ajaxLoadFun;//加载函数
	if(opts.refreshFun) this.refreshFun = opts.refreshFun;//每次滑动刷新内容
	if(opts.clickFun) this.clickFun = opts.clickFun;//点击刷新内容
	if(opts.translate) this.translate = opts.translate;//初始化translate值
	this.children= this.outer.getElementsByTagName('li');//INFO LI集合
	this.scrollerStyle = this.outer.style;//INFO样式
	this.warpStyle = this.wrap.style;//INFO样式

	//opts.maxWidth 为百分比时 opts.width opts.height 也必须为百分比
	this.maxWidth = !!opts.maxWidth ? (typeof opts.maxWidth == "number" ? opts.maxWidth : parseInt(opts.maxWidth)/100*window.innerWidth) : 640;//默认屏幕尺寸最大为640px
	this.row = opts.row?opts.row:1;//行数
	this.col = opts.col?opts.col:1;//列数
	this.width = opts.width;//LI宽
	this.height = opts.height;//LI高
	
	console.log(this.row,this.col,this.width,this.height)
	//设置wrap info 可见状态
	this.wrap.style.position = "relative";
	this.wrap.style.overflow = "hidden";
	this.scrollerStyle.overflow = "hidden";
	//构造
	this.init();
	this.bindDOM();
}

Slider.prototype = {
	init : function(){
		var self = this;
		this.direction = true; //自动播放的方向变化
		this.active = 0; //圆点的位置
		this._startTranslate = 0; //记录INFO translate的值
		this.state = 0;//滑动数量
		var i = 0;
		var loadDom = document.getElementById(this.wrap.id+'-load');
		var loadW = 0;
		if(loadDom){
			loadW = loadDom.clientWidth;
		}
		

		var maxWidth = self.maxWidth//默认屏幕尺寸最大为640px
		var maxWindow = window.innerWidth>maxWidth || window.innerWidth == 0 ?maxWidth:window.innerWidth; //手机屏幕的宽度;
		var whRatio = window.innerHeight/maxWindow;//屏幕宽高比例
		
		console.log(maxWindow+"maxWindow")
		var domWidth = typeof self.width == "number"?self.width:parseInt(self.width)/100*maxWidth;//LI宽
		var domHeight = typeof self.height == "number"?self.height:parseInt(self.height)/100*maxWidth*whRatio;//LI高

		var picRatio = maxWindow/maxWidth;
		var liWidth =  picRatio*domWidth;//640为窗口最大宽度 高度
		var liHeight = picRatio*domHeight;//640为窗口最大宽度
		var maxRowWidth = self.col*liWidth;
		var maxColHeight = self.row*liHeight;

		if(!!self.translate){
			self.translate = self.indexDom();
			
		}

		//设置宽高
		this.setDOM = function(){
			
//			console.log(maxRowWidth)

			if(self.sliderdir){//行排 还是列排
				//self.scrollerStyle.height =(self.children.length/self.col)*liHeight+'px';
				if(self.translate>(self.children.length-self.row)){
					self.translate = self.children.length-self.row;
				}
				self.state = (Math.ceil(self.children.length/self.col) - self.row);
				self.infoWH =  self.state*liHeight;
				self.warpStyle.height = maxColHeight +"px";
				self.wrapWH = liHeight;
				if(!!self.translate){
					self.scrollerStyle.webkitTransform = 'translate(0,'+(-self.translate*liHeight)+'px)' + 'translateZ(0)';
					self._startTranslate = -self.translate*liHeight;
				}

			}else{
				
				if(self.translate>(self.children.length-self.col)){
					self.translate = self.children.length-self.col
				}
				self.warpStyle.width = maxRowWidth+"px";
				self.scrollerStyle.width = (self.children.length/self.row)*liWidth+loadW+'px';
				self.state = (Math.ceil(self.children.length/self.row) - self.col);
				self.infoWH = self.state*liWidth;
				self.wrapWH = liWidth;
				if(!!self.translate){
					self.scrollerStyle.webkitTransform = 'translate('+(-self.translate*liWidth)+'px,0)' + 'translateZ(0)';
					self._startTranslate = -self.translate*liWidth;
				}
			}
		
			//设置每个LI的宽度
			for(i; i < self.children.length; i++) {
				self.children[i].style.width = liWidth+'px';
				self.children[i].style.height = liHeight+'px';
			}
		}

		this.setDOM();
		if(this.dot){
			//设置Info的宽度
			this.dotList = this.wrap.getElementsByClassName('dot-list')[0];
			if(this.dotList.getElementsByTagName('ul').length == 0){
				this.dotListUl = document.createElement('ul'); 
				for(var i = 0; i < this.children.length; i++) {
					var li = document.createElement('li');
					this.dotListUl.appendChild(li)
				}
				this.dotList.appendChild(this.dotListUl);
				this.dotListLI = this.dotList.getElementsByTagName('li');
				this.dotListLI[0].className = 'active';
			}
		}

	window.addEventListener("resize", this.setDOM, false); 
	return this;
	},
	removeLoad : function(){
		var loadDOM = document.getElementById(this.wrap.id+'-load');
		if(loadDOM){
			this.outer.removeChild(loadDOM)
		}
	},
	ajaxLoadFun : function(){	
		return false;
	},
	//计算移动位移
	gettranslateX : function(x,state,flag){

		//flag true为自动播放
		var _endTranslate = 0;//结束后距离
		var a = this.autoplay?0:Math.round(this.offsetYX/this.wrapWH);

		if(this.refresh){
			state = 0;
			a = this.offsetYX>0?1:-1;
		} 
		if(state != 0 || this.autoplay){
			var state = state?state:1;
		}
		if(state==1) this.offsetYX = 0;
		if(x <= 0){		
			if(flag){
				if(Math.abs(this._startTranslate) == this.infoWH){
					this.direction = false;
				}else if(Math.abs(this._startTranslate) == 0){
					this.direction = true;
				}
			}else{
				this.direction = true;
			}
		}else{
			this.direction = false;
		}
		if(this.direction){//右滚 或 下滚

			_endTranslate = this._startTranslate+(this.wrapWH*(a-state));
				if(Math.abs(_endTranslate)>this.infoWH){	
					_endTranslate = -this.infoWH;
				}else{
					this.active++
				}	

		}else{//左滚 或 上滚
			_endTranslate  = this._startTranslate+(this.wrapWH*(a+state));
	
			if(_endTranslate>0){
				_endTranslate = 0;
			}else{
				this.active--;
			}
		}
		//移动圆点的位置
		if(this.dot){
			//设置className
			for ( var i=0; i<this.dotListLI.length; i++ )
			{	
				this.dotListLI[i].className = '';
				if( i == this.active ){ this.dotListLI[i].className = this.titOnClassName}
			}
		}
		this.offsetYX = 0;
		this._startTranslate = _endTranslate;
		return _endTranslate;
	},
	bindDOM : function(){
		//手指按下的处理事件
		var self = this;
			self.startYX = 0;
			self.targetYX = 0;

		var isTouchPad = (/hp-tablet/gi).test(navigator.appVersion);
		var hasTouch = 'ontouchstart' in window && !isTouchPad;
    	var touchEvents = {
            start: hasTouch ? 'touchstart' : 'mousedown',
            move: hasTouch ? 'touchmove' : 'mousemove',
            end: hasTouch ? 'touchend' : 'mouseup'
        };
	
		//自动播放
		var autoSlider = function(){
			//evt.preventDefault();
			var defaultauto = self.gettranslateX(-1,1,true);

			self.sliderdir?self._translate(0,defaultauto,500):self._translate(defaultauto,0,500);
	
		};
		if(this.autoplay){
			this.intervalID = setInterval(autoSlider, 5000);
		}

		//手指按下的处理事件
		var startHandler = function(evt){
			if (!self.outer || self.children.length<=self.row*self.col) return;

			//记录刚刚开始按下的时间
			self.startTime = new Date() * 1;
			self.offsetYX = 0;//清空移动距离
			var point = hasTouch ? evt.touches[0] : evt;
			self.startY = point.pageY;
			self.startX = point.pageX;
			
			
			self.startYX = self.sliderdir?self.startY:self.startX;

			self.outer.addEventListener(touchEvents.move, moveHandler,false);
			self.outer.addEventListener(touchEvents.end, endHandler,false);		
		};

		//手指移动的处理事件
		var moveHandler = function(evt){
			var point = hasTouch ? evt.touches[0] : evt;
			var endY = point.pageY;
			var endX = point.pageX;

			//默认为左右滚动 上下滚动的时候触发滚动条
			if(!self.sliderdir){
				if(Math.abs(endY-self.startY)>Math.abs(endX-self.startX)){
					return;		
				}
			}else{
				if(Math.abs(endX-self.startX)>Math.abs(endY-self.startY)){
					return;		
				}
			}
			//兼容chrome android，阻止浏览器默认行为
			evt.preventDefault();

			//清除轮播动画
			if(self.intervalID){
				clearInterval(self.intervalID);
			}

			//获取滑动的坐标
			self.targetYX = self.sliderdir?endY:endX;

			self.offsetYX =  self.targetYX - self.startYX;
			self.sliderdir?self._translate(0,self._startTranslate+self.offsetYX):self._translate(self._startTranslate+self.offsetYX,0);

		};

		//手指抬起的处理事件
		var endHandler = function(evt){
			
			if(self.offsetYX==0) {
				if(self.clickLoad){
					self.clickFun(evt);
				}
				return;
			}
			evt.preventDefault();
			if(self.refresh){
				self.refreshFun(evt);
			}
			var touchNum = self.state/3<1?1:Math.ceil(self.state/3);

			//手指抬起的时间值
			var endTime = new Date() * 1;
			//滑动到最下面
			if(self.load && Math.abs(self.infoWH+50)<Math.abs(self._startTranslate+self.offsetYX) && self.offsetYX<0){
				self.ajaxLoadFun();
			}

			var _endTranslate = 0;

			//当手指移动时间超过300ms 的时候，按位移算
				if(endTime - self.startTime > 200){
					 _endTranslate = self.gettranslateX(self.offsetYX,0);
				}else{
					 _endTranslate = self.gettranslateX(self.offsetYX,touchNum);
				
				}

			if(self.sliderdir){
				self._translate(0,_endTranslate,500);
			}else{
				self._translate(_endTranslate,0,500);	
			}

			if(self.intervalID){
				self.intervalID = setInterval(autoSlider, 3000);
			}
			self.outer.removeEventListener(touchEvents.move, moveHandler,false);
			self.outer.removeEventListener(touchEvents.end, endHandler,false);
			
		};

		//绑定事件
		this.outer.addEventListener(touchEvents.start, startHandler,false);
		
	},
	indexDom: function () {
        for(var i =0;i<this.children.length;i++){
			if(this.translate == this.children[i].getAttribute('data-id')){
				return i;
			}
		}
    },
	//偏移动画时间
	_transform: function (time) {
		//alert(1)		
		
	},
	//偏移值设置
	//滑动效果
	_translate: function( x,y,speed) {
		var ele = this.scrollerStyle;
		speed = speed?speed:0;
		ele.webkitTransitionDuration = speed+'ms';
		ele.webkitTransform = 'translate(' + x + 'px,'+y+'px)' + 'translateZ(0)';	
		ele.webkitTransitionTimingFunction ='ease-out';
	},
	//缓动效果 未完成	
	_animate: function (destX, destY, duration, easingFn) {
		
	}

}

