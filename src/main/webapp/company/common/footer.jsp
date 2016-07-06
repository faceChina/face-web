<%@ page pageEncoding="UTF-8"%>
<div class="footer">
	<div class="foot">
		<div class="left about">
			<a href="${companyPath }about.jsp">关于我们</a> | <a href="${companyPath}protocol.jsp">服务协议</a>
			<p>浙江脸谱企业管理有限公司 @版权所有 浙ICP备14042086号</p>
			<p>地址：浙江省杭州市市民中心D座11楼</p>
		</div>
		<div class="right complain">
			<img src="${companyPath}img/login/code.jpg" width="66" height="66" class="left">
			<div class="left tel">
				<p>投诉建议</p>
				<h3>400-000-3777</h3>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	footerAtBottom();
	$(window).resize(function(){
		footerAtBottom();
	});
});

function footerAtBottom(){
	var browserHeight =  document.documentElement.clientHeight;  //浏览器可视高度
	var footerHeight = $('.footer').length>0 ? $('.footer').height():0;	//页脚高度
	var bodyHeight = $("body").outerHeight(true);		//文档高度
	var spaceHeight = browserHeight-footerHeight;
	if(bodyHeight <= browserHeight){
        $('.footer').css({'position':'fixed',"bottom":"0"})
    }else{
        $('.footer').css({'position':'relative',"bottom":"auto"})
    }
}
</script>