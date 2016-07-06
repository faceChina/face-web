void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<link rel="stylesheet" type="text/css" href="../../../../styles/public.css">
<script type="text/javascript" src="../../../../../base/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../../../../../base/js/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="../../../../js/fixedbottom.js"></script>
<script type="text/javascript" src="../../../../js/slidefocus/slidefocus-jquery.js"></script>
<script type="text/javascript">
$(function(){
	//轮播图
	$.slidefocus({
		dom:"slider",	 //元素id，无“#”字符
		width:"data",		
		height:"data",
		direction:"left",		 //上(up)右(right)下(down)左(left)
		play:true, 				 //自动播放(true/false),和direction、time组合使用
		time:"3000", 			 //时间间隔(单位毫秒)
		page:"data"				//是否显示页码
	});
	
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
    
    try{
    	updateStyle(data);
    }catch(err){
    	console.log(err);
    }
    
});
</script>
*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);