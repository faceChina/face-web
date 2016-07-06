<script type="text/javascript">
$(function() {
	console.log($(".j-toggleCont").length)
	$(".sidebar").find("li").has("ul").find("ul").css("display","none"); //没有子菜单的选项样式
	$(".sidebar").find("li").has("ul").find("ul").has(".active").css("display","block");  //刷新后，依然展开选中的菜单
	$(".sidebar").find("li").has("ul").find("ul").has(".active").prev("a").find("span").removeClass("arrow").addClass("arrow-down");  //刷新后，展开的菜单右侧箭头向下
	//有子菜单的选项样式及点击操作
	$(".sidebar").find("li:has(ul)").children("a").click(function(){
		//如果当前菜单的下一个紧邻同辈节点被隐藏
		if($(this).next("ul").is(":hidden")){
			//那么，显示它
			$(this).next("ul").slideDown("fast");
			$(this).find("span").removeClass("arrow").addClass("arrow-down");
			//如果当前菜单的同级其他菜单显示了子菜单
			if($(this).parent("li").siblings("li").children("ul").is(":visible")){
				//那么，隐藏它
				$(this).parent("li").siblings("li").find("ul").slideUp("fast");
				$(this).parent("li").siblings("li").find("a span").removeClass("arrow-down").addClass("arrow");
				return false;
			}
		}else{
		//否则，点击当前菜单，隐藏当前菜单
			$(this).next("ul").slideUp("fast");
			$(this).find("span").removeClass("arrow-down").addClass("arrow");
			//当前菜单的之菜单的子菜单淡出隐藏
			$(this).next("ul").children("li").find("ul").fadeOut("fast");
			return false;
		}
	});
});
</script>