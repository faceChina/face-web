
//验证错误信息弹出显示
$.validator.setDefaults({
	onkeyup:false,//避免提交后实时验证
	focusInvalid:false,//提交后不获取焦点
	focusCleanup:true,
	showErrors: function(map, list) {
		$.each(list, function(index, error) {
			$(error.element).addClass('error');
			artTip(error.message);
		});
	
	}
});

/**
 * 验证提示 自动消失
 * @param  {[str]} txt  [提示文字]
 * @param  {[boolean]} evt  [关闭方式，true:点击关闭；false:自动消失]
 * @param  {[number]} time [自动消失的时间]
 */
function artTip(txt,callback,time,evt){
	var str = '<div class="m-art"><div class="m-artbox"><span class="m-artinfo">错误提示！</span></div></div>',
	artEl = $('.m-art'),
	time =time? time:2000,
	evtStyle = false;
	for(var i=1;i<arguments.length;i++){
		if(typeof arguments[i] == 'number'){
			time = arguments[i];
		}else if(typeof arguments[i] == 'boolean'){
			evtStyle = evt;
		}
	}
	if(artEl.length == 0){
		$('body').append(str);
		artEl = $('.m-art');
	}
	if(artEl.is(':hidden')){
		artEl.find('.m-artinfo').html(txt);
		artEl.slideDown();
		if(evtStyle){
			artEl.addClass('m-art-click');
			$('.m-art-click').click(function(){
				artEl.slideUp();
			})
		}else{
			setTimeout(function(){
				artEl.hide();
				if (callback) {
					callback();
				}
		},time);
		}
	}
	
}

//获取验证码
function getCode(el){
	$(el).attr("disabled",true);
	var time=60;
	var setIntervalID=setInterval(function(){
		time--;
		$(el).html("验证码已发送 "+ time +"秒");
		if(time==0){
			clearInterval(setIntervalID);
			$(el).attr("disabled",false).html("免费获取验证码");
		}
	},1000)
}
//获取验证码
function getCode1(el){
	$(el).attr("disabled",true);
	$(el).removeClass('bg-dc3132');
	 var time=59;
	 $(el).html("已发送("+ time +")");
	 var setIntervalID=setInterval(function(){
		time--;
		$(el).html("已发送("+ time +")");
		if(time==0){
			clearInterval(setIntervalID);
			$(el).attr("disabled",false).html("获取");
			$(el).addClass('bg-dc3132');
		}
	},1000)
};
//showLayer显示相对应的层
//给显示层添加 data-show="type" 
//type为传入的值
function showLayer(type){
	var layerDom = $('[data-show="'+type+'"]');
		layerDom.show();
		layerDom.on('click',function(){
			layerDom.hide();
		})
}


//显示隐藏 传入data-id值
function showhideLayer(id){
	var layerDom = $('[data-id="'+id+'"]');
	var flag = layerDom.is(':hidden');
	if(flag){
		layerDom.slideDown();
		$(event.target).text("隐藏更多商品");
	}else{
		layerDom.slideUp();
		$(event.target).text("显示更多商品");
	}
}
// 将内容复制到剪切板
function copyToClipboard(text){
  if (window.clipboardData){
	    window.clipboardData.setData("Text", text);
	    }else if (window.netscape){
	      try{
	        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	    }catch(e){
	        alert("该浏览器不支持一键复制！n请手工复制文本框链接地址～");
	    }
	    var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
	    if (!clip) return;
	    var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
	    if (!trans) return;
	    trans.addDataFlavor('text/unicode');
	    var str = new Object();
	    var len = new Object();
	    var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
	    var copytext=text;
	    str.data=copytext;
	    trans.setTransferData("text/unicode",str,copytext.length*2);
	    var clipid=Components.interfaces.nsIClipboard;
	    if (!clip) return false;
	    clip.setData(trans,null,clipid.kGlobalClipboard);
  }
  
}
//滚动时固定在顶部
/*(function(window,jQuery,undefined){

	jQuery(function(){
		var oldDom,
		topArr = [],
		stickyDom = jQuery('[data-sticky]'),
		stickyLen = stickyDom.length;

		//console.log(scrollTop)
		function stickyNav(self,index){
	      	var scrollTop = jQuery(window).scrollTop();
	      	console.log(jQuery('#2').offset().top)
				if (scrollTop > (topArr[index]-50)) { 
					$('.sticky').removeClass('sticky');
				    jQuery(self).addClass('sticky');
				}else{
					jQuery(self).removeClass('sticky');
				}
		
		}
		
		function stickyBind(){
			
				stickyDom.each(function(index){
				if(topArr.length < stickyLen){
					console.log(1)
					topArr.push(jQuery(this).offset().top);
				}
	      		stickyNav(this,index);
			});
		}


		stickyBind();
	
		jQuery(window).scroll(function() {
		   stickyBind();
		});
	})	
	
})(window,jQuery);*/

//滚动加载
(function(window,jQuery,undefined){
	function moveScroll(cfg){
		var config = cfg || {};
		this.get = function(n){
			return config[n]
		}
		this.set = function(n,v){
			config[n] = v;
		}
		this.init();
	}
	moveScroll.prototype = {
		init: function(){
			this.createDom();
			this.bindEvent();
		},
		createDom: function(){
			var scrollBox = document.getElementById("scroll-box"),
				maxTran = scrollBox.offsetHeight - scrollBox.parentNode.offsetHeight;

				//console.log(scrollBox.childNodes)

				//jquery 后期有时间修改
				var sticky = jQuery(scrollBox).find('[data-sticky]');
				var padd = jQuery(scrollBox).parent().offset().top;
				//console.log(padd)
				if(sticky.length>0){
					var stickyArr = [];
					sticky.each(function(){
						stickyArr.push($(this).offset().top-padd);
					});
					this.set('stickyArr',stickyArr);
				}
				//end jquery 后期有时间修改

				this.set("box",scrollBox);//存入Box节点
				this.set("maxTran",maxTran);//最大tran值
				this.set("parentHt",scrollBox.parentNode.offsetHeight);//Box父类高

				
		},
		//滑动效果
		translate:function( dist, speed, ele ) {
			//if( !!ele ){  }else{ ele=conBox.style; }
			//console.log(dist)
			ele=ele.style;
			ele.webkitTransitionDuration =  ele.MozTransitionDuration = ele.msTransitionDuration = ele.OTransitionDuration = ele.transitionDuration =  speed + 'ms';
			ele.webkitTransform = 'translate3d(0,' + dist + 'px,0)';
			ele.msTransform = ele.MozTransform = ele.OTransform = 'translate3d(0,' + dist + 'px,0)';	
		},
		bindEvent: function(){
			var _this = this,
				box = _this.get("box"),//Box
				isTouchPad = (/hp-tablet/gi).test(navigator.appVersion),
				hasTouch = 'ontouchstart' in window && !isTouchPad,
				touchEvents = {
		            start: hasTouch ? 'touchstart' : 'mousedown',
		            move: hasTouch ? 'touchmove' : 'mousemove',
		            end: hasTouch ? 'touchend' : 'mouseup'
		        },
		        startPoint = {},//开始触摸坐标
		        startTime, //开始时间
		        startTran, //滑动开始translate 值
		        endTran,
		        moveDist = 0;//移动距离
			//手指按下的处理事件
			var startMove = function(evt){
				//记录刚刚开始按下的时间
				console.log("start");
				moveDist = 0;
				startTime = new Date() * 1;
				var point = hasTouch ? evt.touches[0] : evt;
				//console.log(_this.get('endTran'))
				startTran = _this.get('endTran') || 0;

				startPoint['Y'] = point.pageY;

				box.addEventListener(touchEvents.move, moveMove,false);
				box.addEventListener(touchEvents.end, endMove,false);		
			};

			//手指移动的处理事件
			var moveMove = function(evt){

				//兼容chrome android，阻止浏览器默认行为
				evt.preventDefault();

				var point = hasTouch ? evt.touches[0] : evt,
					parentHt = _this.get('parentHt'),
					maxTran = _this.get('maxTran'),
					endPoint = {};

				
				endPoint['Y'] = point.pageY;

				moveDist = (endPoint['Y']-startPoint['Y']);
				
				if(moveDist>0){
					_this.set('sign',1);
				}else{
					_this.set('sign',-1);
				}

				endTran = startTran+moveDist;

				if(endTran>parentHt/2){
					endTran = parentHt/2;
				}else if(Math.abs(maxTran)<Math.abs(endTran+parentHt/2)){
					endTran = -(maxTran+parentHt/2);
				}

				_this.set('endTran',endTran);//结束translate值
				_this.translate(endTran,0,box);

				/*//jquery 后期有时间修改 可以把对象跟值对象存入
				_this.get('stickyArr').forEach(function(e,i){
				
					if(e<Math.abs(endTran)){

						jQuery("#scroll-box").find('[data-sticky]').eq(i).css('-webkit-transform','translateY('+(Math.abs(endTran)-e-8)+'px)').siblings().removeAttr('style');
						_this.get('anchor').eq(i).addClass('active').siblings().removeClass('active');
						if(endTran>0||Math.abs(endTran)>maxTran){
							if(endTran>0){
								_this.get('anchor').eq(0).addClass('active').siblings().removeClass('active');
							}else{
								_this.get('anchor').last().addClass('active').siblings().removeClass('active');
							}
							jQuery("#scroll-box").find('[data-sticky]').removeAttr('style');
						}

						return;
					}
				})				
				//end jquery 后期有时间修改*/
				
			};

			//手指抬起的处理事件
			var endMove = function(evt){

				//console.log(moveDist)
				if(moveDist == 0){
					return;
				}
				evt.preventDefault();

				var endTime = new Date() * 1,
					maxTran = _this.get('maxTran') || 0,
					parentHt = _this.get('parentHt'),
					movetime = endTime-startTime;
				
				console.log("end");
				//jQuery('#j-price').text(moveDist)
				if(movetime<300){

					endTran =  maxTran/100*(moveDist*10/parentHt)*movetime/25 + _this.get('endTran');

					_this.set('endTran',endTran);//结束translate值
					_this.translate(endTran,movetime*5,box);//滑动弹回	
				}
				//console.log(Math.abs(maxTran),Math.abs(endTran));
				

				//滑出边界
				if(endTran>0){
					_this.set('endTran',0);//设置translate值
					_this.translate(0,500,box);//滑动弹回				
				}else if(Math.abs(maxTran)<Math.abs(endTran) ){
					//console.log(2)
					_this.set('endTran',-maxTran);//设置translate值
					_this.translate(-maxTran,500,box);//滑动弹回	
				}

				//jquery 后期有时间修改 可以把对象跟值对象存入
				/*_this.get('stickyArr').forEach(function(e,i){
				
					if(e<Math.abs(endTran)){

						jQuery("#scroll-box").find('[data-sticky]').eq(i).css('-webkit-transform','translateY('+(Math.abs(endTran)-e-8)+'px)').siblings().removeAttr('style');
						_this.get('anchor').eq(i).addClass('active').siblings().removeClass('active');
						if(endTran>0||Math.abs(endTran)>maxTran){
							if(endTran>0){
								_this.get('anchor').eq(0).addClass('active').siblings().removeClass('active');
							}else{
								_this.get('anchor').last().addClass('active').siblings().removeClass('active');
							}
							jQuery("#scroll-box").find('[data-sticky]').removeAttr('style');
						}

						return;
					}
				})	*/			
				//end jquery 后期有时间修改

				box.removeEventListener(touchEvents.move, moveMove,false);
				box.removeEventListener(touchEvents.end, endMove,false);
				
			};

			//绑定事件
			box.addEventListener(touchEvents.start, startMove,false);
		}
	};
	window.moveScroll = window.moveScroll || moveScroll;

})(window,jQuery);


//点击描点
(function(window,jQuery,undefined){

	function clickAnchor(cfg){
		var config = cfg || {};
		this.get = function(n){
			return config[n]
		}
		this.set = function(n,v){
			config[n] = v;
		}
		this.init();
	}
	clickAnchor.prototype = {
		init: function(){
			this.createDom();
			this.bindEvent();
		},
		createDom: function(){
			this.set('anchor',jQuery('#j-nav li'));
		},
		//滑动效果
		translate:function( dist, speed, ele ) {
			//if( !!ele ){  }else{ ele=conBox.style; }
			//console.log(dist)
			ele=ele.style;
			ele.webkitTransitionDuration =  ele.MozTransitionDuration = ele.msTransitionDuration = ele.OTransitionDuration = ele.transitionDuration =  speed + 'ms';
			ele.webkitTransform = 'translate3d(0,' + dist + 'px,0)';
			ele.msTransform = ele.MozTransform = ele.OTransform = 'translate3d(0,' + dist + 'px,0)';	
		},
		bindEvent: function(){
			var _this = this;
				box = this.get('box'),
				stickyArr = this.get('stickyArr');

			jQuery('#j-nav li').on('click',function(){
				var index = $(this).index();

					$(this).addClass('active').siblings().removeClass('active');

				_this.translate(-stickyArr[index],1000,box);
			});
		}
	};
	window.clickAnchor = window.clickAnchor || clickAnchor;
})(window,jQuery);


//弹窗artdialog扁平化
//Alert
function artDialogAlert(mgs,callevent){
	var that=art.dialog({
		lock: true,
		skin:"artDialogAlert",
		init:function(){
			$(".artDialogAlert .aui_titleBar").remove();
		},
		content: '<div class="artDialogAlert-box">'+mgs+'</div>',
		 ok: function () {
		    that.close();
		    if (callevent!=undefined) {callevent();}
		 }
	});
}
//comfirm
function artDialogComfirm(id,sureCallback,msg){
	var contents=msg?msg:document.getElementById(id);
	art.dialog({
		skin:"artDialogAlert",
		init:function(){
			$(".artDialogAlert .aui_titleBar").remove();
		},
		lock:true,
		content:contents,
		button:[{
            name:"确认",
            focus:true,
            callback:function(){
            	var issuccess=sureCallback();
            	if (issuccess) {
            		return true;
            	}
            	else{
            		return false;
            	}
            }
        }],
		cancel:true
		 
	}); 
}
//noButtonComfirm
//comfirm
function artDialogNoBtComfirm(id){
	return art.dialog({
		skin:"artDialogAlert",
		init:function(){
			$(".artDialogAlert .aui_titleBar").remove();
		},
		lock:true,
		content:document.getElementById(id) 
	}); 
}
function getUrlParame(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 

// 删除 加入购物车导航条（app页面调用）
function delShoppingCartNav(){
	$(".footfix").remove();
}

// 删除 底部购物车导航条（app页面调用）
function delBottomNav(){
	$("#navbar").remove();
}

// $(function(){
// // 隐藏购物车导航条（app页面调用）
//  var parameDelCartNav=getUrlParame("delShoppingCartNav"),
//  	 parameDelBottomNav=getUrlParame("delBottomNav");
//  if (parameDelCartNav==1) {
//  	delShoppingCartNav();
//  }
//  if(parameDelBottomNav==1){
//  	delBottomNav();
//  }
   

// });

$(function(){
	$("i[data-id='collectgoodsshop']").off().on("click",function(){
		var $obj=$(this),
			status=$obj.data("status");
			if (status=="0") {
				ajaxCollect(function(){
					$obj.removeClass("icon-weishoucang");
					$obj.addClass("icon-yishoucang");
					artTip("收藏成功");
					$obj.data("status","1");
				});
				
			}
			else{
				ajaxCollect(function(){
					$obj.addClass("icon-weishoucang");
					$obj.removeClass("icon-yishoucang");
					artTip("已取消收藏");
					$obj.data("status","0")
				});

			}
	});
	
});
//正则验证公共
var isIphone=function(value){
	var patrn=/^(13|15|18|17|14)\d{9}$/;
	return  patrn.test(value);
}
// 头部置顶
 function toTop(){
    $('body,html').animate({scrollTop:0},300);
    return false;
  };
  
window.onscroll=function(){
	var scrollTop=document.body.scrollTop,
	  $totop=$(".totop");
	if (scrollTop>110) {
	  $totop.show();
	}
	else{
	  $totop.hide();
	}
}
//判断ios或者Android
function isiOSAndAndroid(){
	var sUserAgent = navigator.userAgent.toLowerCase(),
  		 bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		if (bIsIphoneOs) {
			return true;
		}
		else{
			return false;
		}
}
//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires+";path=/";
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie  
function clearCookie(name) {  
    setCookie(name, "", -1);  
}  