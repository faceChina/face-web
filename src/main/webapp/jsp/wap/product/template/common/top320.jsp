<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/fixedbottom.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slidefocus-jquery.js"></script>
<script type="text/javascript" src="${resourcePath}js/slider.js"></script>
<link rel="stylesheet" type="text/css"	href="${resourcePath}template/page/common/css/preview-noscroll.css">
<script type="text/javascript">
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
		});
	});
}
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
	
	updateStyle(data);
});
</script>