<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#golist {display: none;}
		@media (max-device-width: 780px){#golist{display: block !important;}}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=80faea4f504ca1abe79f7270d1722a22"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=80faea4f504ca1abe79f7270d1722a22&v=1.0"></script>
<!-- 	<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script> -->
<%@ include file="../common/top.jsp"%>
<title>${titleShopName}-微管家导航</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty shopLocation }">
			<div id="allmap"></div>
		</c:when>
		<c:otherwise>
			<h3 style="padding:5em 0 0 0;text-align:center;">商家地理位置尚未设定！</h3>
		</c:otherwise>
	</c:choose>
	<%@ include file="../common/nav.jsp"%>
</body>
</html>
<script type="text/javascript">
	navbar("gps");

	var lat="${shopLocation.lat}";
	var lng="${shopLocation.lng}";
	if(''!=lat && ''!=lng){
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		var shopPoint = new BMap.Point(lng,lat);
		map.centerAndZoom(shopPoint,16);
		map.addControl(new BMap.ZoomControl());
		
		var opts = {
			width : 200,    // 信息窗口宽度
			height: 70,     // 信息窗口高度
			title : "${shopLocation.shopName}"  // 信息窗口标题
		}
		var infoWindow = new BMap.InfoWindow("${shopLocation.area}", opts);  // 创建信息窗口对象
		map.openInfoWindow(infoWindow,new BMap.Point(shopPoint.lng,shopPoint.lat+0.0005)); //开启信息窗口 
		
		var marker = new BMap.Marker(shopPoint);  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				 marker.addEventListener("click", function(){
					var start = {
						 latlng:r.point
					}
					var end = {
						latlng:shopPoint
					}
					var opts = {
						mode:BMAP_MODE_DRIVING,
						region:"${shopLocation.cityName}"
					}
					var ss = new BMap.RouteSearch();
					ss.routeCall(start,end,opts);
				}); 
			} else {
				if(this.getStatus() == 6){
					art.dialog.alert('如果需要获取位置信息，请在设置中打开定位');	
				}else{
					art.dialog.alert('failed:'+this.getStatus());
				}
			}        
		},{enableHighAccuracy: true});
	}
	
</script>


<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-地图</title>
</head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=80faea4f504ca1abe79f7270d1722a22"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<style>
#box{
	width:100%;
	height:100%;
	position:absolute;

}
body,div{
	margin:0;
	padding:0;
}
</style>
<body>
<div id="box">
<div id="map" style="width: 100%;height: 100%"></div>
</div>

</body>
<script type="text/javascript">

	var map = new BMap.Map("map");
	map.centerAndZoom("中国",12);
	new BMap.LocalCity().get(function(result){
		map.setCenter(result.name);
	});
	// 地图平移缩放控件            
	map.addControl(new BMap.NavigationControl());
	// 定位控件
	map.addControl(new BMap.GeolocationControl()); 
	//定位
	 if (navigator.geolocation){
    	navigator.geolocation.getCurrentPosition(function(gpsPoint){
    		var gpsmap = new BMap.Point(gpsPoint.coords.longitude,gpsPoint.coords.latitude);
    		BMap.Convertor.translate(gpsmap,0,function(baiduPoint){
    			map.centerAndZoom(baiduPoint, 18);
    			var p2 = new BMap.Point(120.220094,30.261615);
    			
    			var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
    			driving.search(baiduPoint, p2); 
    		});
    		
    	});
    } else {
    	art.dialog.alert("检测到当前设备不支持定位服务");
    }
	 /*
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		alert(this.getStatus());
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			alert(r.point.lng+","+r.point.lat);
			map.centerAndZoom(r.point, 18);
			var p2 = new BMap.Point(120.220094,30.261615);
			
			var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
			driving.search(r.point, p2); 
		}
		else {
			if(this.getStatus() == 6){
				art.dialog.alert('如果需要获取位置信息，请在设置中打开定位');	
			}else{
				art.dialog.alert('failed:'+this.getStatus());
			}
		}        
	},{enableHighAccuracy: true}); */
	
</script>
</html> -->
