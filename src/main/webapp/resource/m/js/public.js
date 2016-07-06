
/*
* data-attr="box" 主要容器(内容添加到此)
* data-attr="item 每条属性值
* data-attr="add" 添加属性
* data-sort="true" 替换属性值
* data-attr="del" 删除
* data-attr="move-up"  data-attr="move-down" 移动
* 
 */
//添加属性
$(function(){

})
var addAttr = (function(){

	var str = '',
		moduleNum = $('[data-attr="item"]').length,
		sortId = moduleNum,
		warp = $('[data-attr="box"]');

	var setSort = function(idnum){
		sortId = idnum == ''? 0 : parseInt(idnum);
	}
	var setHtml = function(strhtml){
		str = strhtml;
	}
	var addHtml = function(strhtml){
		var addStr = strhtml.replace(/\{\{sort\}\}/g,sortId++);
		if(warp.length>0){
			warp.append(addStr);
		}else{
			$('[data-attr="add"]').before(addStr);	
		}
		
		moduleNum++;
	}
	var delHtml = function(obj){
		$(obj).closest('[data-attr="item"]').remove();
		moduleNum--;
	}
	var moveUp = function(obj){
		var moduleEl = $(obj).closest('[data-attr="item"]'),
			nextEl = moduleEl.next('[data-attr="item"]'),
			idEl = moduleEl.find('[data-sort="true"]');
		if(moduleNum>=2){
			if(idEl.length >0){
				var nextIdEl = nextEl.find('[data-sort="true"]'),
				 	nextIdVal = nextIdEl.val(),
				 	idVal = idEl.val();

				 	nextIdEl.val(idVal);
				 	idEl.val(nextIdVal);
			}
			moduleEl.next('[data-attr="item"]').after(moduleEl);

			
		}
	}
	var moveDown = function(obj){
		var moduleEl = $(obj).closest('[data-attr="item"]'),
			prevEl = moduleEl.prev('[data-attr="item"]'),
			idEl = moduleEl.find('[data-sort="true"]');
		if(prevEl.attr('data-fix')==1)return;
		if(moduleNum>=2){
			if(idEl.length >0){
				var prevIdEl = prevEl.find('[data-sort="true"]'),
				 	prevIdVal = prevIdEl.val(),
				 	idVal = idEl.val();

				 	prevIdEl.val(idVal);
				 	idEl.val(prevIdVal);

			}

			moduleEl.prev('[data-attr="item"]').before(moduleEl);

			
		}
	}
	//添加
	$('[data-attr="add"]').on('click',function(){
		addHtml(str);
	});

	//删除
	$('body').on('click','[data-attr="del"]',function(){
		delHtml(this);
	});

	//上移
	$('body').on('click','[data-attr="move-up"]',function(){
		moveUp(this);
	});

	//下移
	$('body').on('click','[data-attr="move-down"]',function(){
		moveDown(this);
	});

	return {
		setHtml:setHtml,
		setSort:setSort
	}

})();

//Select 值改变是 对于操作
var selectActive = (function(){
	var dataObj = {
		'DATETIME':{ 'val':'预约时间','place':'无'},
		'INPUT':{ 'val':'','place':''},
		'TEXTAREA':{ 'val':'备注','place':'请输入备注信息'},
		'SELECT':{ 'val':'选项','place':'每个选项以“|”隔开'},
	}

	var dataFun = function(data){
		dataObj = data;
	}

	//根据type 替换相应的value 跟 placeholder值
	var changeAtter = function(self,type){
		
		var itemEl = $(self).closest('[data-attr="item"]'),
			itemVal = itemEl.find('[data-val]'),
			itemPlace = itemEl.find('[data-place]'),
			selfValue = $(itemVal).val();
			itemVal.attr({'placeholder':dataObj[type].val,'value':''});
			itemPlace.attr({'placeholder': dataObj[type].place, 'value': ''});
			if(type == 'DATETIME'){
				itemPlace.attr('disabled','disabled');
			}else{
				itemPlace.removeAttr('disabled');
			}
	}
		$('[data-attr="box"]').on('change','[data-select]',function(){
			//alert(1)
			changeAtter(this,this.value);
		});

	return {
		data:dataFun
	}

})();

//显示隐藏 传入data-id值
function showhideLayer(id){
    var layerDom = $('[data-id="'+id+'"]');
    var flag = layerDom.is(':hidden');
    if(flag){
        layerDom.slideDown();
    }else{
        layerDom.slideUp();
    }
}




//鼠标经过显示商品
(function (window, jQuery, undefined) {

	function MoveshowShop(cfg) {
		var config = cfg || {};
		this.get = function (n) {
			return config[n]
		}
		this.set = function (n, v) {
			config[n] = v;
		}
		this.init();
	}
	MoveshowShop.prototype = {
		init: function () {
			//this.createDom();
			this.bindEvent();
		},
		createDom: function () {

		},
		format: function (template, json) {
			return template.replace(/\{(.*?)\}/g, function (all, key) {
				return json && (key in json) ? json[key] : "";
			});
		},
		//添加监听
		on: function (key, listener) {
			//this.__events存储所有的处理函数
			if (!this.__events) {
				this.__events = {};
			}
			if (!this.__events[key]) {
				this.__events[key] = [];
			}

			this.__events[key].push(listener);

			return this;
		},
		//触发一个事件
		fire: function (key) {

			if (!this.__events || !this.__events[key]) return;

			var args = Array.prototype.slice.call(arguments, 1) || [];

			var listeners = this.__events[key];

			var i = 0,
				l = listeners.length;
			for (i; i < l; i++) {
				listeners[i].apply(this, args);
			}

			return this;
		},
		bindEvent: function () {
			var _this = this,
				bindDom = {},
				selfId = _this.get('id');

			jQuery(selfId).on('click', function () {
				var num = jQuery(this).data('id');
				if (!bindDom[num]) {
					bindDom[num] = true;
				} else {
					bindDom[num] = false;
				}
				_this.fire('show.shop', num);
			});

			_this.set('bindDom', bindDom);

		}
	};
	window.MoveshowShop = window.MoveshowShop || MoveshowShop;
})(window, jQuery);


//批量删除/下架函数
function delSoldoutBatch() {
	$("#template tbody").find("input[type='checkbox']:checked").each(function () {
		$(this).parents("tr").remove();
	})
}
//批量删除
function delBatch() {
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if (checkLen <= 0) return;
	art.dialog.confirm('确认删除？', function () {
		delSoldoutBatch();
	}, function () {
			//art.dialog.tips('执行取消操作');
			return true;
		});
}
//批量下架
function soldoutBatch() {
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if (checkLen <= 0) return;
	art.dialog.confirm('确认下架商品？', function () {
		delSoldoutBatch();
	}, function () {
			//art.dialog.tips('执行取消操作');
			return true;
		});
}
//批量上架
function putawayBatch() {
	var checkLen = $("#template tbody").find("input[type='checkbox']:checked").length;
	if (checkLen <= 0) return;
	art.dialog.confirm('确认下架商品？', function () {
		delSoldoutBatch();
	}, function () {
			//art.dialog.tips('执行取消操作');
			return true;
		});
}

/*common event*/
//加法 
function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) 
	return (arg1*m+arg2*m)/m;
} 
//减法 
function accSubtr(arg1,arg2){ 
	var r1,r2,m,n; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)); 
	n=(r1>=r2)?r1:r2; 
	return ((arg1*m-arg2*m)/m).toFixed(n); 
} 

//乘法 
function accMul(arg1,arg2){ 
	var m=0,s1=arg1.toString(),s2=arg2.toString(); 
	try{m+=s1.split(".")[1].length}catch(e){} 
	try{m+=s2.split(".")[1].length}catch(e){} 
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 
} 
//除法
function accDiv(arg1,arg2){ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
	r1=Number(arg1.toString().replace(".","")) 
	r2=Number(arg2.toString().replace(".","")) 
	return accMul((r1/r2),pow(10,t2-t1)); 
	} 
}

