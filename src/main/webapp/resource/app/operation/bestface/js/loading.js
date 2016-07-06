/*  
* @description: loading 弹框 
* @author: WMM
* @update: 421543076@qq.com
*/


//调用方法
//preloader.show(); 无背景 黑色图标
//preloader.showWhite(); 黑色背景白色图标
//preloader.colse(); 关闭图层


var preloader = (function(){

	var str = '<div class="preloader-layout"><div class="preloader-backnone"><span class="preloader"></span></div></div>';

	var whiteStr = '<div class="preloader-layout"><div class="preloader-back"><span class="preloader preloader-white"></span></div></div>';

	var showWhite = function(){
		appendBody(whiteStr);
	}

	var show = function(){	
		appendBody(str);
	}

	var appendBody = function(html){
		$('body').append(html);
	}

	var closeLoad = function(){
		$('.preloader-layout').remove();
	}

	return{
		show:show,
		showWhite:showWhite,
		colse:closeLoad
	}
})();

