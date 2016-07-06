<%@ page pageEncoding="UTF-8"%>
<form id="intoshop-form" method="post" action=""></form>
<div id="menuInit"></div>

<script type="text/javascript" language="javascript">
	$(function() {
		$.post("${ctx}/u/menu/init${ext}", function(data) {
			var sy='${crumbs}'=='pingtai';
			$("#menuInit").html(data);
			if(sy){
				var $thiz = $("#menuInit").find("[tabindex]").next();
				$thiz.find("span").addClass("arrow-down");
				$thiz.find("ul").show();
			}
			var str = $("#menuIndex").val()
			$("#j-toggleCont").find("[tabindex]").removeClass("active");
			$("[tabindex='"+str+"']").addClass("active").parents('ul').show();
			
		});
	});

	var flag = false;
	$("#j-toggle").on("click", function() {
		$("#j-toggleCont").toggle();
		if (!flag) {
			flag = true;
		} else {
			flag = false;
		}
	});
	$(window).resize(function() {
		var width = $("body").width();
		if (width > 1000) {
			$("#j-toggle").css("display", "none");
			$("#j-toggleCont").css("display", "block");
		} else {
			$("#j-toggle").css("display", "block");
			if (flag) {
				$("#j-toggleCont").css("display", "block");
			} else {
				$("#j-toggleCont").css("display", "none");
			}

		}
	});

	function tab(str) {
		$("#menuIndex").val(str);
	}
	
	function intoShop(no){
		$.post("${ctx}/u/index/checkDate${ext}",{"no":no},function(falg){
			if(falg == 1 || falg == "1"){
				$(".j-loading").show();
				$('#intoshop-form').html("");
				$('#intoshop-form').html("<input type='hidden' name='no' value='"+no+"' />").attr("action",'${ctx}/u/index/check${ext}').submit();
			}else if(falg == 0 || falg == "0"){
				alertMsg("产品已过期！");
			} else {
				location.href = "${ctx}/login${ext}";
			}
		});
	}
</script>

