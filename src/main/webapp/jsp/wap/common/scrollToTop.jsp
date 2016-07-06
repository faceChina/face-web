<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="j-scrollToTop" style="position:fixed;bottom:60px;right:10px;width:40px;height:40px;background:#ccc;border-radius:100%;text-align:center;color:#fff;opacity:0.8;cursor:pointer;">▲<br>TOP</div>

<script type="text/javascript">
$("#j-scrollToTop").click(function(){
	$(document.body).scrollTop(0);
});
$("#j-scrollToTop").hide();
$(window).scroll(function(){
	var bodyScrollTop = $(document.body).scrollTop();
	if(bodyScrollTop > 300){
		$("#j-scrollToTop").show();
	}else{
		$("#j-scrollToTop").hide();
	}
});
</script>