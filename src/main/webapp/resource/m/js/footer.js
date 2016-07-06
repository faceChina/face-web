$(footer.import.body.innerHTML).appendTo($("body"));

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
	if(bodyHeight < browserHeight){
		$('.footer').offset({ top:spaceHeight})
	}
//	console.log(browserHeight+"***"+bodyHeight+"***"+footerHeight+"**"+spaceHeight);
}