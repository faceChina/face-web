<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript" src="${resourceBasePath}js/jquery.ba-resize.js"></script>
<div class="footer">
	<div class="container text-center">
		<div class="row">
			<div class="col-md-4 col-xs-4">
				<strong class="join"><a href="javascript:window.open('${ctx}/company/idea.jsp','footerInfo').focus();"><i></i>产品理念</a></strong>
			</div>
			<div class="col-md-4 col-xs-4">
				<strong class="aboutus"><a href="javascript:window.open('${ctx}/company/product.jsp','footerInfo').focus();"><i></i>平台产品</a></strong>
			</div>
			<div class="col-md-4 col-xs-4">
				<strong class="join"><a href="javascript:window.open('${ctx}/company/about.jsp','footerInfo').focus();"><i></i>关于我们</a></strong>
			</div>
		</div>
		<div class="row">
			<p class="version">浙江脸谱 @版权所有 浙ICP备14042086号</p>
		</div>
	</div>
</div>
<div class="j-loading">
<div class="j-img">
	<img src="${resourcePath}img/loading.gif" alt="">
</div>
</div>

<script type="text/javascript" src="${resourceBasePath }js/bootstrap.min.js"></script>
<script type="text/javascript" >


$(function(){
	footerAtBottom();
	$(window).resize(function(){
		footerAtBottom();
	});
});

function footerAtBottom(){
	var browserHeight =  document.documentElement.clientHeight;  //浏览器可视高度
	var footerHeight = $('.footer').length>0 ? $('.footer').height():0;	//页脚高度
	var leftWidth=$('.header').height()+$('.top').height();
	var bodyHeight = $("body").outerHeight(true);		//文档高度
	var spaceHeight = browserHeight-footerHeight;
     $('#j-content').css('min-height',(bodyHeight-footerHeight-leftWidth)+'px')
	if(bodyHeight < browserHeight){
      $('.footer').offset({ top:spaceHeight})
	}
//	console.log(browserHeight+"***"+bodyHeight+"***"+footerHeight+"**"+spaceHeight);
}

</script>
