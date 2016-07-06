var peList;
var cityList;
var selectPe = function() {
	var pe = $("#pe").val();
	$("#city").empty();
	cityList = null;
	$("#area").empty();
	initCity(pe);
	initArea(0);
};

var selectCity = function() {
	var city = $("#city").val();
	$("#area").empty();
	initArea(city);
};
var selectArea = function() {
	var area = $("#area").val();
	$('#areaCode').val(area);
};
var initCity = function(pe) {
	var option = "<option value='0'>请选择市...</option>";
	$("#city").append(option);
	if(0!=pe){
		for ( var i = 0; i < peList.length; i++) {
			if (pe == peList[i].id) {
				cityList = peList[i].children;
				for ( var i = 0; i < cityList.length; i++) {
					var city = cityList[i];
					var option = "<option value='" + city.id + "'>" + city.name
							+ "</option>";
					$("#city").append(option);
				}
				return;
			}
		}
	}
};
var initArea = function(city) {
	var option = "<option value='0'>请选择区...</option>";
	$("#area").append(option);
	if(0!=city){
		for ( var i = 0; i < cityList.length; i++) {
			if (city == cityList[i].id) {
				var areaList = cityList[i].children;
				for ( var i = 0; i < areaList.length; i++) {
					var area = areaList[i];
					var option = "<option value='" + area.id + "'>" + area.name
							+ "</option>";
					$("#area").append(option);
				}
				return;
			}
		}
	}
};
$(document).ready(function() {
	var option = "<option value='0'>请选择省...</option>";
	$("#pe").append(option);
	peList = areaP.children;
	for ( var i = 0; i < peList.length; i++) {
		var pe = peList[i];
		var option = "<option value='" + pe.id + "'>" + pe.name
				+ "</option>";
		$("#pe").append(option);
	}
	$("#pe").val("0");
  	var len = $(".top").length;
	if(len != 0){
		$("#line2").hide(); 
	}
});