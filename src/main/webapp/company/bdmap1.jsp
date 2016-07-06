<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<style type="text/css">
			body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
			#golist {display: none;}
			@media (max-device-width: 780px){#golist{display: block !important;}}
		</style>
		<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=D4ebab88193b0339c2a43fd0b6b2ea72&v=1.0"></script>
		<title>${shop.name }</title>
	</head>
	<body>
		<div id="allmap"></div>
	</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(${myshopLocation.lng},${myshopLocation.lat});
	map.centerAndZoom(point, 15);
	map.addControl(new BMap.ZoomControl());       

	var marker = new BMap.Marker(new BMap.Point(${myshopLocation.lng},${myshopLocation.lat}));  //创建标注
	map.addOverlay(marker);    // 将标注添加到地图中
	var opts = {
		width : 220,    // 信息窗口宽度
		title : "${shop.name }", // 信息窗口标题
		enableAutoPan : true //自动平移
	}
	var infoWindow = new BMap.InfoWindow("地址：${myshopLocation.address }", opts);  // 创建信息窗口对象
	map.openInfoWindow(infoWindow,point); //开启信息窗口
	marker.addEventListener("click", function(){          
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	});
</script>
