function navBar(){
	var bodyht =  document.documentElement.clientHeight;
	var navht = $("#navbar").outerHeight();
	$("#navbar").css('top', bodyht-navht);
}
$(document).ready(function() {
	navBar();
	window.onresize = function(){
		navBar();
}
});
