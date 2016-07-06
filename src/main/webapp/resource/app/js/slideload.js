/*  
* @description:SlideLoad
* @version: 1.0
* @author: wmm 
* @update: 2014-10-27
* @mail: 421543076@qq.com
*/

//构造函数
function SlideLoad(opts){
	//构造函数需要的参数
	this.wrap = typeof opts.dom == 'string' ? document.querySelector(opts.dom) : opts.dom;
	this.list = opts.list;

	this.scrollX = opts.scrollX ? opts.scrollX : false;
	this.scrollY = opts.scrollX ? false : true;
	this.loadUp = opts.loadUp ? opts.loadUp : false;
	this.loadDown = opts.loadDown ? opts.loadDown : false;

	//构造
	this.init();
	this.renderDOM();
	this.bindDOM();
}

SlideLoad.prototype = {
	init : function(){
		this.scrollbar = window.pageYOffset;	//滚动条高度
		this.outer = this.wrap.children[0]; 	//inner
		this.scrollerStyle = this.outer.style;	// 设置样式
		this.outerX = 0;				//滑动X距离
		this.outerY = 0;				//滑动Y距离
		this.outerYX = 0;
		this.scrollTop = 0;		//距离顶部距离
		this.scrollBottom = 0; 	//距离底部距离
		//this.aaaa = 0;
		//this.loadUp = true;		//开启上滑加载
		//this.loadDown = true;	//开启下滑加载

		if(this.scrollX){
			this.innerHW = this.outer.offsetWidth; //inner高度
			this.slideHW = this.wrap.offsetWidth; //容器高度
		}else{
			this.innerHW = this.outer.offsetHeight; //inner高度
			this.slideHW = this.wrap.offsetHeight; //容器高度
		}
		this.maxoffsetYX = Math.abs(this.innerHW-this.slideHW);	

		this.loadType = 'up'; //
		this.loading = document.createElement('div'); //创建加载提示文字
		this.loading.id = 'loading'; //设置ID
		this.loadfun = null;
		//this.loading.innerHTML = '加载中...'

		this._translate()

	},
	renderDOM : function(loadList){
		var self = this;
		var wrap = this.wrap;
		var data = loadList ? loadList : this.list;
		var inner = self.renderDomFun(data);
		
		//上拉 加载内容容器下面
		if(this.loadType == 'down'){
			this.outer.appendChild(inner);
		}else if(this.loadType == 'up'){ //下啦 加载内容容器上面
			this.outer.insertBefore(inner,this.outer.children[1]);
		}
		
	},
	renderDOMAjax : function(loadList){
		
		
	},
	renderDomFun : function (data){
		var len = data.length;
		ul = document.createElement('ul');
		//根据元素的
		for(var i = 0; i < len; i++){
			var li = document.createElement('li');
			var item = data[i];
			if(item){
				li.innerHTML = item['txt']+i;
			}
			ul.appendChild(li);
				
		}
		return ul;
		
	},
	bindDOM : function(){

		//手指按下的处理事件
		var self = this;
		var outer = self.outer;


		//手指按下的处理事件
		var startHandler = function(evt){
			//evt.preventDefault();
			//clearTimeout(self.loadfun);//删除加载函数
			if(self.scrollX){
				self.innerHW = self.outer.offsetWidth; //inner高度
				self.slideHW = self.wrap.offsetWidth; //容器高度
			}else{
				self.innerHW = self.outer.offsetHeight; //inner高度
				self.slideHW = self.wrap.offsetHeight; //容器高度
			}
			self.maxoffsetYX = Math.abs(self.innerHW-self.slideHW);	

			//记录刚刚开始按下的时间
			self.startTime = new Date() * 1;

			//记录手指按下的坐标
			self.startY = evt.touches[0].pageY;
			self.startX = evt.touches[0].pageX;

			//清除偏移量
			self.offsetYX = 0;
			self.offsetX = 0;
			//console.log(3)
		};

		//手指移动的处理事件
		var moveHandler = function(evt){
			//兼容chrome android，阻止浏览器默认行为
			evt.preventDefault();
			self.loadType = self.offsetYX>0?'up':'down';
			if(self.scrollX ){
				self.offsetYX = evt.targetTouches[0].pageX - self.startX;
				//self.translate = parseInt(self.translate)+self.offsetY;
				self.outerYX = parseInt(self.scrollTop)+self.offsetYX;
			}else{
				self.offsetYX = evt.targetTouches[0].pageY - self.startY;
				//self.translate = parseInt(self.translate)+self.offsetY;
				self.outerYX = parseInt(self.scrollTop)+self.offsetYX;
				
			}
			//计算手指的偏移量
			self._translate(self.outerYX)	
			

			if(self.maxoffsetYX < Math.abs(self.outerY)-50){
				
			}

			if(self.loadType == 'up' && self.loadUp){
				
				self.outer.insertBefore(self.loading,self.outer.children[0]);
			}
			else if(self.loadType == 'down' && self.loadDown){
			
				self.outer.appendChild(self.loading);
			}
				
		};

		//手指抬起的处理事件
		var endHandler = function(evt){
			//evt.preventDefault();
			var endTime = new Date() * 1;

			if(self.loadUp || self.loadDown){

				//添加加载文字提示
				this.loadfun = setTimeout(function(){
					var loadDOM = document.getElementById('loading');
					//loadDOM是否已被删除
					if(loadDOM){
						self.outer.removeChild(loadDOM);
					}
				},800);		
			}
			

			//拉动 加载内容容器下面
			if((self.loadUp && self.outerYX >= 0 && self.offsetYX >0) || (self.loadDown && self.maxoffsetYX <= Math.abs(self.outerYX))){
				self.renderDOM(list2)
			}

			//快速滑动 未完成
			if(endTime - self.startTime < 300){

				var ddd = self.offsetY>0?400:-400

			}
			console.log( self.maxoffsetYX)

			//滑动到最上面或最下面
			if(self.outerYX >0 || self.maxoffsetYX < Math.abs(self.outerYX)){
				
				if(self.offsetYX>0 || self.slideHW>self.innerHW){
					self.outerYX = 0;
				}else{
					self.outerYX = self.slideHW-self.innerHW;
				}
				
				self._translate(self.outerYX);
				self._transform();
			}

			self.scrollTop = self.outerYX;	
		};

		//绑定事件
		outer.addEventListener('touchstart', startHandler);
		outer.addEventListener('touchmove', moveHandler);
		outer.addEventListener('touchend', endHandler);		
	},
	//偏移动画时间
	_transform: function (time) {
		//alert(1)	
		var transTime = time?time:0.2;
		
		this.scrollerStyle.webkitTransition = '-webkit-transform '+transTime+'s ease-out';
	},
	//偏移值设置
	_translate: function (yx) {
		var x = 0;
		var y = 0;
		if(this.scrollX){
			x = yx;
		}else{
			y = yx;
		}

		this.scrollerStyle.webkitTransform = 'translate3d('+x+'px ,'+y+'px , 0)';
	},
	//缓动效果 未完成
	_animate: function (destX, destY, duration, easingFn) {
		
	}

}

