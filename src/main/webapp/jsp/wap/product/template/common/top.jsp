<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}fonts/iconfont.css">
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/fixedbottom.js"></script>
<script type="text/javascript" src="${resourcePath}js/dpreview.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slider.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slideout-jquery.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slidefocus-jquery.js"></script>
<script type="text/javascript">
$(function(){
	//轮播图
	if($('#slider').length){
		$.slidefocus({
			dom:"slider",	 //元素id，无“#”字符
			width:"data",		
			height:"data",
			direction:"left",		 //上(up)右(right)下(down)左(left)
			play:true, 				 //自动播放(true/false),和direction、time组合使用
			time:"3000", 			 //时间间隔(单位毫秒)
			page:"data"				//是否显示页码
		});
	}
// 	if($("#slider").length > 0){
// 		$.slidefocus({
// 			dom:"slider",	 //元素id，无“#”字符
// 			width:"data",		
// 			height:"data",
// 			direction:"left",		 //上(up)右(right)下(down)左(left)
// 			play:true, 				 //自动播放(true/false),和direction、time组合使用
// 			time:"3000", 			 //时间间隔(单位毫秒)
// 			page:true				//是否显示页码
// 		});
// 	}
	
	//改变商品分类文字颜色
    function updateStyle(data,type) {
    	if(data == undefined || data.length ==0) return;
		$("[data-font]").each(function(index){
			$(this).css({
				"color":data[index]["color"],
			});
		});
		$("[data-font-en]").each(function(index){
			$(this).css({
				"color":data[index]["coloren"],
			});
		});
	
		$("[data-background]").each(function(index){
			$(this).css({
				"background":"rgba("+data[index]['red']+","+data[index]['green']+","+data[index]['blue']+","+data[index]['opacity']+")"
			})
		})
    }
    
  //改变商品分类文字颜色
    function newUpdateStyle(data,panel) {//data ,type
    if(panel.length==0 ||data == undefined || data.length ==0) return;
    var datafont=panel.find('[data-font]');
    var datafonten=panel.find('[data-font-en]');
    var databackground=panel.find('[data-background]');
	    if(datafont.length){
		    datafont.each(function(index){
		    $(this).css({
		    "color":data[index]["color"]
		    });
		    });
	    }

	    if(datafonten.length){
		    datafonten.each(function(index){
		    $(this).css({
		    "color":data[index]["coloren"]
		    });
		    });
	    }

	    if(databackground.length){
		    databackground.each(function(index){
		    $(this).css({
		    "background":"rgba("+data[index]['red']+","+data[index]['green']+","+data[index]['blue']+","+data[index]['opacity']+")"
		    })
		    })
	    }

    }
    try{
    	updateStyle(data);
    }catch(err){
    	console.log(err);
    }
    
});
</script>