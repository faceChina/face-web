/***
 * 购物车操作模块(使用中)
 */
var shopCart = function(window){
	"use strict";
	//全局变量
	// note new new Date("2020-12-23") 在ie下面报错，不支持这样的语法
	var items = [],
	cartName='GJWJ_CART',
	expires = new Date( new Date().getTime()+86400000*30 ),
	debug = false,
	decimal = 2;
	var options = {
		'cartName' : cartName, //cookie的名字
		'expires' : expires, //cookie失效的时间
		'debug' : debug,  //是否打印调试信息
		'decimal' : decimal, //钱的精确到小数点后的位数
		'callback' : undefined
	};
	//商品类
	/***
	 * @name item
	 * @example 
	   item(goodItemId, name, unitPrice, quantity)
	 * @params {string} goodItemId 商品细项ID
	 * @params {string} name 商品的名字
	 * @param {number} unitPrice 商品的价格
	 * @param {number} quantity 商品的数量
	 * @param {string} version 商品的版本
	 * @param {string} colorValue 商品的颜色
	 * @param {string} sizeValue 商品的尺寸
	 * @param {string} sizeValue 商品的店铺号
	 * @param {string} sizeValue 商品的店铺名称
	 * @param {string} sizeValue 购物车主键（shopNo+goodItemId）
	 */
	function item(goodItemId, name, unitPrice, quantity,version,colorValue,sizeValue,picturePath,shopNo,shopName,sid){
		this.goodItemId = goodItemId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.version=version;
		this.colorValue=colorValue;
		this.sizeValue=sizeValue;
		this.picturePath=picturePath
		this.shopNo = shopNo;
		this.shopName = shopName;
		this.sid = sid
	}
	//暴露给外部的接口方法
	return {
		inited : false,
		init: function(option){
			//判断用户是否禁用cookie
			if(!window.navigator.cookieEnabled ){
				alert('您的浏览器不支持cookie无法使用购物车！,请设置允许设置cookie。');
				return false;
			}
			//从cookie中获取购物车中的数据
			this.inited = true;
			if(option){
				extend(options,option);
			}
			var cookie = getCookie(options.cartName);
			if(typeof cookie === 'undefined'){
				setCookie(options.cartName,'',options.expires); 
			}else{
				//每个item之间用&分开，item的属性之间用|分割
				var cookie = getCookie(options.cartName);
				if(cookie){
					var cItems = cookie.split('&');
					for(var i=0,l=cItems.length;i<l;i++){
						var cItem = cItems[i].split('|');
							var item = {};
							item.goodItemId = cItem[0] || '';
							item.name = cItem[1] || '';
							item.unitPrice = cItem[2] || '';
							item.quantity = cItem[3] || '';
							item.version = cItem[4] || '';
							item.colorValue = cItem[5] || '';
							item.sizeValue = cItem[6] || '';
							item.picturePath = cItem[7] || '';
							item.shopNo = cItem[8] || '';
							item.shopName = cItem[9] || '';
							item.sid=cItem[10] || '';
							items.push(item);
					};
				};
			};
		},
		findItem: function(sid){//根据sId标示查找商品
			//如果木有提供sId,则返回所有的item
			if(sid){
				for(var i=0,l=items.length;i<l;i++){
					var item = items[i];
					if(item.sid == sid){
						return item;
					}
				}
				return undefined;
			}else{
				return items;
			}
			
		},
		getItemIndex : function(sid){ //获取item在items的数组下标
			for(var i=0,l=items.length;i<l;i++){
				var item = items[i];
				if(item.sid == sid){
					return i;
				}
			}
			//木有找到返回-1
			return -1;
		},
		addItem: function(item){ //增加一个新商品到购物车
			//添加一个商品
			var hasItem = this.findItem(item.sid);
			if(hasItem){
				//商品已存在，版本相同，累加数量
				if(hasItem.version==item.version){
					if(options.debug){
						_log('商品已存在,版本相同，累计商品数量');
					}
					this.cumulativeQuantity(item);
					return true;
				}
				if(options.debug){
					_log('商品已存在,版本不同，删除后继续添加商品');
				}
				delItem(sid);
			}
			items.push(item);
			_saveCookie();
			return true;
		},
		delItem: function(sid){ //从购物车中删除一个商品
			//删除一个商品
			var index = this.getItemIndex(sid);
			if(index > -1){
				items.splice(index,1);
				_saveCookie();
			}else{
				if(options.debug){
					_log('商品不存在');
					return false;
				}
			}
		},
		cumulativeQuantity: function(item){ //累加商品的数量
			var index = this.getItemIndex(item.sid);
			if(index > -1){
				var quantity = Number(items[index].quantity)+Number(item.quantity);
				items[index].quantity = quantity; 
				_saveCookie();
			}else{
				if(options.debug){
					_log('商品不存在');
					return false;
				}
			}
		},
		addQuantity: function(sid,quantity){ //减少商品的数量
			var index = this.getItemIndex(sid);
			if(index > -1){
				var result = Number(items[index].quantity)+Number(quantity);
				items[index].quantity = result; 
				_saveCookie();
			}else{
				if(options.debug){
					_log('商品不存在');
					return false;
				}
			}
		},
		reduceQuantity: function(sid,quantity){ //减少商品的数量
			var index = this.getItemIndex(sid);
			if(index > -1){
				var result = Number(items[index].quantity)-Number(quantity);
				items[index].quantity = result; 
				_saveCookie();
			}else{
				if(options.debug){
					_log('商品不存在');
					return false;
				}
			}
		},
	    updateQuantity: function(sid,quantity){ //更新商品的数量  
            //更新一个商品  
            var index = this.getItemIndex(sid);  
            if(index > -1){  
                items[index].quantity = quantity;   
                _saveCookie();  
            }else{  
                if(options.debug){  
                    _log('商品不存在');  
                    return false;  
                }  
            }  
	    },  
		emptyCart: function(){
			//清空数组
			items.length = 0;
			_saveCookie();
		},
		checkout: function(){
			//点击结算后的回调函数
			if(options.callback){
				options.callback();
			}
		},
		getTotalCount: function(sid){
			//获取购物车商品的数量，如果传某个商品的id，那么就返回该商品的数量
			var totalCount = 0;
			if(sid){
				totalCount = (typeof this.findItem(sid) === 'undefined' ? 0 : this.findItem(sid).quantity );
			}else{
				for(var i=0,l=items.length;i<l;i++){
					totalCount += (parseInt(items[i].quantity) === 'NaN' ? 0 : parseInt(items[i].quantity )) ;
				}
			}
			return totalCount;
		},
		getTotalPrice : function(sid){
			//获取购物车商品的总价格 ,如果传某个商品的id，那么就返回该商品的总价格
			var totalPrice = 0.0;
			if(sid){
				var num = parseInt((typeof this.findItem(sid) === 'undefined' ? 0 : this.findItem(sid).quantity )),
				unitPrice = parseFloat((typeof this.findItem(sid) === 'undefined' ? 0 : this.findItem(sid).unitPrice ));
				num = num=== 'NaN' ? 0 : num;
				unitPrice = unitPrice === 'NaN' ? 0 : unitPrice;
				totalPrice = unitPrice * num;
			}else{
				for(var i=0,l=items.length;i<l;i++){
					totalPrice += (parseFloat(items[i].unitPrice ) * parseInt(items[i].quantity)); 
				}
			}
			return totalPrice.toFixed(options.decimal);
		},
		getCookie : getCookie,
		setCookie : setCookie
	};
	
	
	/**
	 * 设置cookie
	 * @name setCookie
	 * @example 
	   setCookie(name, value[, options])
	 * @params {string} name 需要设置Cookie的键名
	 * @params {string} value 需要设置Cookie的值
	 * @params {string} [path] cookie路径
	 * @params {Date} [expires] cookie过期时间
	 */
	function setCookie(name, value, options) {
		options = options || {};
		var expires = options.expires || null;
		var path = options.path || "/";
		var domain = options.domain || document.domain;
		var secure = options.secure || null;
		/**
		document.cookie = name + "=" + escape(value) 
		+ ((expires) ? "; expires=" + expires.toGMTString() : "") 
		+ "; path=" + path
		+ "; domain=" + domain ;
		+ ((secure) ? "; secure" : "");
		*/
		var str = name + "=" + encodeURIComponent(value)
		+ ((expires) ? "; expires=" + expires.toGMTString() : "") 
		+ "; path=/";
		document.cookie = str;
	};
	
	/**
	 * 获取cookie的值
	 * @name getCookie
	 * @example 
	   getCookie(name)
	 * @param {string} name 需要获取Cookie的键名
	 * @return {string|null} 获取的Cookie值，获取不到时返回null
	 */
	function getCookie(name) {
		var arr = document.cookie.match(new RegExp("(^| )" + name
				+ "=([^;]*)(;|$)"));
		if (arr != null) {
			return decodeURIComponent(arr[2]);
		}
		return undefined;
	};
	
	//***********************私有方法********************/
	function _saveCookie(){
		var i=0,l=items.length;
		if(l>0){
			var tItems = [];
			for(;i<l;i++){
				var item = items[i];
				tItems[i] = item.goodItemId + '|' +item.name + '|' 
				+ item.unitPrice + '|' + item.quantity+ '|' + item.version
				+ '|' + item.colorValue+ '|' + item.sizeValue + '|' + item.picturePath
				+ '|' + item.shopNo+ '|' + item.shopName+'|' + item.sid;
			};
			var str = tItems.join('&');
			setCookie(options.cartName, str, {expires:options.expires});
		}else{
			setCookie(options.cartName, '', {expires:options.expires});
		}
		
	};
	
	//***********************工具方法********************/
	//显示调试信息
	function _log(info){
		if(typeof console != 'undefined'){
			console.log(info);
		}
	};
	//继承属性
	function extend(destination, source) {
		for ( var property in source) {
			destination[property] = source[property];
		}
	};
}(typeof window === 'undifined' ? this: window);



