/*	基类：base
 * 描述：基础类，可扩展此类
 * 功能：整合与封装具体的操作
 * */
var base = function(){
	var mine = {}
	var _private = mine._private;
	
	//扩展方法
	mine.extend = function(target,source){
		for(var key in source){
			target[key] = source[key];
		}
		return target;
	};
	
	return mine;
}();

//扩展
base.extend(this.base,{
	//判断访问页面的设备类型
	dpreview:function () {
	    var sUserAgent = navigator.userAgent.toLowerCase();  
	    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad",
	  		  bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os",
			  bIsMidp = sUserAgent.match(/midp/i) == "midp",
			  bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4",
			  bIsUc = sUserAgent.match(/ucweb/i) == "ucweb",
			  bIsAndroid = sUserAgent.match(/android/i) == "android",
			  bIsCE = sUserAgent.match(/windows ce/i) == "windows ce",
			  bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";  

	    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {  
				return 2; //非PC
	    } else {  
				return 1; //PC
	    }
	},
	//根据宽度变化字号
	fontsize : function (fontsize){
		var width = window.innerWidth;
		fontsize = (fontsize == undefined) ? ( (width < 640) ? ((33.75*width)/640) : 33.75) : 10; //PC端算法
		//手机端算法：(width < 640) ? ((60*width)/480) : 67.5
		$("html").attr("data-dpr",this.dpreview()); //设备类型
		$("html").css("font-size",fontsize); //基本字号
	}
})