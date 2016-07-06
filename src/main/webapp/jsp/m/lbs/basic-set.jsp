<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../common/base.jsp" %>
<%@include file="../common/validate.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>基础设置-LBS位置设置</title>
<script type="text/javascript">
$(function(){
	tab("basic-set");

});

</script>	
<!-- top -->
<!-- <script type="text/javascript" src="top.js"></script> -->
<!--top end -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=80faea4f504ca1abe79f7270d1722a22"></script>
<script type="text/javascript" src="${resourcePath}plugins/area.js"></script>
</head>
<body>
	<!-- header -->
	<%@ include file="../common/header.jsp" %>
	<!-- header end -->
	<!-- body -->
	<div class="container"  id="j-content">
		<div class="row">
			<div class="col-md-2 navleft-sidebar">
				<!--nav-left -->
				<%@ include file="../common/left.jsp" %>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<c:set var="crumbs" value="lbs"/>
					<%@include file="../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#basic-set" data-toggle="tab">LBS位置设置</a></li>
									<li class="text-muted">说明：开启LBS功能后用户可在微信获取商家的地理位置信息。</li>
								</ul>
							</div>
							<div class="content">
								<form id="jfrom" class="form-horizontal" role="form" action="${ctx}/u/lbs/save${ext}" method="post">
									<input type="hidden" name="id" value="${shopLocation.id }" >
									<input type="hidden" id="lat" name="lat" value="${shopLocation.lat }">
									<input type="hidden" id="lng" name="lng" value="${shopLocation.lng }">
									
									<div class="form-group">
									    <label for="inputEmail3" class="col-sm-1 control-label" style="min-width:90px;text-align:left;">联系电话:</label>
									    <div class="col-sm-6">
									      <input type="text" name="cell" class="form-control" id="inputEmail3" placeholder="" value="${shop.cell }">
									    </div>
									   <!--  <div class="col-sm-4 help-block">
									    	<span class="fontcor-red">*</span>
									    	<span>最多只能输入<span class="fontcor-red">30</span>个字符</span>
									    </div> -->
									</div>
									<div class="form-group">
									    <label for="inputEmail3" class="col-sm-1 control-label" style="min-width:90px;text-align:left;">商家所在地:</label>
									    <div class="col-sm-3">
									    	<select class="form-control" id="pe" onchange="selectPe();">
												<option value="0">请选择省份</option>
											</select>
									    </div>
									    <input type="hidden" name="peCode" id="peCode" value="0">
									    <div class="col-sm-3" style="margin:0 15px;">
									    	<select class="form-control" id="city" onchange="selectCity();">
												<option value="0">请选择城市</option>
											</select>
											<input type="hidden" name="cityCode" id="cityCode" value="0">
									    </div>
									    <div class="col-sm-3">
									    	<select class="form-control" id="area" onchange="selectArea();">
												<option value="0">请选择区/县</option>
											</select>
											<input type="hidden" name="areaCode" id="areaCode" value="0">
									    </div>
									</div>
									<div class="form-group">
									    <label for="inputEmail3" class="col-sm-1 control-label" style="min-width:90px;text-align:left;">地址:</label>
									    <div class="col-sm-7">
									      <input type="text" class="form-control" name="address" id="detail" placeholder="如：市民中心D座11楼" value="${shopLocation.address }">
									    </div>
									    <div class="col-sm-1" style="margin-left:15px;">
									      <button type="button" onclick="locationArea()" class="btn btn-default ">定位</button>
									    </div>
									    <!-- <div class="col-sm-1">
									    	<span class="fontcor-red">*</span>
									    </div> -->
									</div>
									<div class="form-group">
									    <div class="col-sm-offset-2 col-sm-10 help-block">
									      	输入地址后，点击“定位”按钮可以在地图上定位。（如果输入地址后无法定位，请在地图上直接点击选择地点）
									    </div>
									</div>
									<div class="form-group">
										<div id="container" style="width: 790px;height: 320px;"></div>
									</div>
									<div class="text-center"><button type="button" onclick="save();" class="btn btn-default ">保存并开启</button></div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- body end --> 
	
	<!-- footer -->
	<%@ include file="../common/footer.jsp" %>
	<!-- footer end -->
	</body>
</html>
<script type="text/javascript">
	$(function() {
		tab("basic-set");
		$("#pe").html("");
		var option = "<option value='0'>请选择省份</option>";
		$("#pe").append(option);
		peList = areaP.children;
		for ( var i = 0; i < peList.length; i++) {
			var pe = peList[i];
			var option = "<option value='" + pe.id + "'>" + pe.name
					+ "</option>";
			$("#pe").append(option);
		}
		$("#pe").val("0");
		
		var area = '${shopLocation.areaCode}'; 
		var city = '${shopLocation.cityCode}'; 
		if (''!=city && 0!=city) {
			initShopCity(city);
		}
		if(''!=area && 0!=area) {
			$("#area").val(area);
			$("#areaCode").val(area);
		}
		initAreaMarker();
	}); 
	// 百度地图API功能
	var map = new BMap.Map("container");    // 创建Map实例
	var lat="${shopLocation.lat}";
	var lng="${shopLocation.lng}";
	// 初始化地图,设置中心点坐标和地图级别   省8市10
	if(''!=lat && ''!=lng){
		map.centerAndZoom(new BMap.Point(lng, lat), 16);
	} else {
		map.centerAndZoom("中国", 4);
	}
	map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
	// 地图平移缩放控件            
	map.addControl(new BMap.NavigationControl());
	// 定位控件
	map.addControl(new BMap.GeolocationControl()); 
	//全景控件
	var stCtrl = new BMap.PanoramaControl(); //构造全景控件
	stCtrl.setOffset(new BMap.Size(20, 20));
	map.addControl(stCtrl);//添加全景控件
	
	//点击添加标注点
	var marker;
	map.addEventListener("click",function(e){
		map.clearOverlays();
		marker = new BMap.Marker(e.point);
		marker.enableDragging();
		map.addOverlay(marker);
	});
	
	function locationArea(){
		var pe = $("#pe option:selected").text();
		var city = $("#city option:selected").text();
		var area = $("#area option:selected").text();
		var detail = $("#detail").val();
		 //逆地址解析
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(pe+city+area+detail, function(point){
			if (point) {
				map.clearOverlays();
				map.centerAndZoom(point, 16);
				marker = new BMap.Marker(point);
				marker.enableDragging();
				map.addOverlay(marker);
			}else{
				art.dialog.alert("无法定位，请尝试填写完整详细地址或者手动点击地图标注位置");
			}
		}, city); 
	}
	
	function initAreaMarker() {
		map.clearOverlays();
		var new_point = new BMap.Point(lng, lat);
		map.centerAndZoom(new_point, 16);
		var marker = new BMap.Marker(new_point);  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		map.panTo(new_point);
	}
	
	function initShopArea(area) {
		var pe = area.toString().substring(0, 2);
		var city = area.toString().substring(0, 4);
		$("#pe").val(Number(pe) * 10000);
		var pe = $("#pe").val();
	 	$("#city").empty();
	 	cityList = null;
	 	$("#area").empty();
	 	initCity(pe);
	 	initArea(0);	
		$("#city").val(Number(city) * 100);
		var city = $("#city").val();
	 	$("#area").empty();
	 	initArea(city);
		$("#area").val(area);
		$("#areaCode").val(area);
	}
	function initShopCity(code) {
		var pe = code.toString().substring(0, 2);
		var city = code.toString().substring(0, 4);
		$("#pe").val(Number(pe) * 10000);
		var pe = $("#pe").val();
	 	$("#city").empty();
	 	cityList = null;
	 	$("#area").empty();
	 	initCity(pe);
	 	initArea(0);	
		$("#city").val(Number(city) * 100);
		var city = $("#city").val();
	 	$("#area").empty();
	 	initArea(city);
	 	$('#cityCode').val(code);
	}
	
	/**
	 * 地区
	 */
	 var peList;
	 var cityList;
	 var selectPe = function() {
	 	var pe = $("#pe").val();
	 	$("#city").empty();
	 	cityList = null;
	 	$("#peCode").val(pe);
	 	$("#area").empty();
	 	initCity(pe);
	 	initArea(0);
	 	map.centerAndZoom($("#pe option:selected").text(), 8);
	 };

	 var selectCity = function() {
	 	var city = $("#city").val();
	 	$("#area").empty();
	 	initArea(city);
	 	$('#cityCode').val(city);
	 	map.centerAndZoom($("#city option:selected").text(), 10);
	 };
	 var selectArea = function() {
	 	var area = $("#area").val();
	 	$('#areaCode').val(area);
	 	map.centerAndZoom($("#area option:selected").text(), 13);
	 };
	 var initCity = function(pe) {
		 $("#city").html("");
	 	var option = "<option value='0'>请选择城市</option>";
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
		 $("#area").html("");
		 $("#areaCode").val(0);
	 	var option = "<option value='0'>请选择区/县</option>";
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
	 var jfrom = $("#jfrom").validate({
			rules: {
				shopName:{
					required:true,
					maxlength:30
				},
				cityCode:{
					vareaCode:true
				},
				address:{
					required:true,
					maxlength:256
				}
			},
			messages:{
				shopName:{
					required:"请输入商家名称！",
					maxlength:$.format("商家名称不能超过{0}个字符")
				},
				cityCode:{
					vareaCode:"请选择商家所在地！"
				},
				address:{
					required:"请输入地址！",
					maxlength:$.format("地址不能超过{0}个字符")
				}
			}
		});
	function save(){
		var flag = jfrom.form();
		if(flag) {
			var lat = $("#lat").val();
			var lng = $("#lng").val();
			if(null == marker && '' == lng && ''==lat){
				art.dialog.alert("请先定位商铺位置");
			} else {
				if(null == marker) {
					var new_point = new BMap.Point(lng, lat);
					marker = new BMap.Marker(new_point);
				}
				var point = marker.getPosition();
				$("#lat").val(point.lat);
				$("#lng").val(point.lng);
				
				//手动定位获取地址
				/*
				var gc = new BMap.Geocoder();
				gc.getLocation(point, function(rs){
					var addComp = rs.addressComponents;
					$('#detail').val(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);
				});
				*/
				
				art.dialog({
					width : '100px',
					title : "保存LBS位置设置",
					lock : true,
					background : '#000', // 背景色
					content : "确定要保存并开启商铺位置吗？",
					button : [ {
						name : '确定',
						callback : function() {
							$("#jfrom").submit();
						},
						focus : true
					}, {
						name : '取消'
					} ]
				}); 
			}
		}
	}
</script>
