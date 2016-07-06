<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>地址编辑</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcePath }personal/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/area.js"></script>
</head>
<body>
	<div id="box">
		<div id="j-createAddress" data-address-id="">
			<form id="j-form" method="post" data-form>
				<div class="group group-others width60">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">联系人</li>
							<li class="group-colspan"><input type="text"
								placeholder="请输入您的姓名" name="userName" id="userName" value="${address.name }"
								class="form-control" data-form-control></li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">联系电话</li>
							<li class="group-colspan"><input type="text"
								placeholder="请输入您的手机号码" name="cell" id="cell" value="${address.cell }"
								class="form-control" data-form-control></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">省</li>
							<li class="group-colspan"><select id="pe"
							 class="form-control" onchange="selectPe();"></select></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">市</li>
							<li class="group-colspan"><select class="form-control"
								id="city" onchange="selectCity();"></select></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">区/县</li>
								
							<li class="group-colspan"><select class="form-control"
								name="vAreaCode" id="area" onchange="selectArea();"></select></li>
						</ul>
					</div>
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan">详细地址</li>
							<li class="group-colspan"><input type="text"
								placeholder="请填写您的具体地址" name="addressDetail" id="addressDetail" value='<c:out value="${address.addressDetail }" />'
								class="form-control" data-form-control></li>
						</ul>
					</div>
				</div>
				<div class="button">
					<button type="submit" class="btn btn-danger btn-block disabled"
						onclick="toSaveAddress()" data-submit>保存地址</button>
				</div>
			</form>
		</div>
		<%@ include file="../../../common/foot.jsp"%>
		<%@ include file="../../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#pe").html("");
			var option = "<option value='0'>省份</option>";
			$("#pe").append(option);
			peList = areaP.children;
			for (var i = 0; i < peList.length; i++) {
				var pe = peList[i];
				var option = "<option value='" + pe.id + "'>" + pe.name
						+ "</option>";
				$("#pe").append(option);
			}
			//初始化值
			var store;
			if (null != (store = '${address}') && "" != store) {
				$('#pe').val('${address.provice}');
				selectPe();
				$('#city').val('${address.city}');
				selectCity();
				$('#area').val('${address.county}');
				selectArea();
			} else {
				$('#pe').val('0');
				selectPe();
				$('#city').val('0');
				selectCity();
				$('#area').val('0');
				selectArea();
			}
		});
		/**
		 * 地区
		 */
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
			$("#city").html("");
			var option = "<option value='0'>城市</option>";
			$("#city").append(option);
			if (0 != pe) {
				for (var i = 0; i < peList.length; i++) {
					if (pe == peList[i].id) {
						cityList = peList[i].children;
						for (var i = 0; i < cityList.length; i++) {
							var city = cityList[i];
							var option = "<option value='" + city.id + "'>"
									+ city.name + "</option>";
							$("#city").append(option);
						}
						return;
					}
				}
			}
		};
		var initArea = function(city) {
			$("#area").html("");
			var option = "<option value='0'>区/县</option>";
			$("#area").append(option);
			if (0 != city) {
				for (var i = 0; i < cityList.length; i++) {
					if (city == cityList[i].id) {
						var areaList = cityList[i].children;
						for (var i = 0; i < areaList.length; i++) {
							var area = areaList[i];
							var option = "<option value='" + area.id + "'>"
									+ area.name + "</option>";
							$("#area").append(option);
						}
						return;
					}
				}
			}
		};
		
		//获取地址表单数据
		function getAddressForm() {
			var addressFormData = {};
			var contact = $("[name='contact']").val();
			var area = $("[name='area']").val();
			var address = $("[name='address']").val();
			var phone = $("[name='phone']").val();
			addressFormData.contact = contact;
			addressFormData.area = area;
			addressFormData.address = address;
			addressFormData.phone = phone;
			return addressFormData;
		}

		//创建并保存地址
		function toSaveAddress() {
			//地址表单验证
			var bool = $("#j-form").validate({
				rules : {
					userName : {
						required : true,
						rangelength:[2,15]
					},
					vAreaCode : {
						required : true,
						notEqual:0
					},
					addressDetail : {
						required : true,
						rangelength:[5,60]
					},
					cell : {
						required : true,
						mobile : true
					}
				},
				messages : {
					userName : {
						required : "请输入您的姓名",
						rangelength:$.validator.format("姓名长度{0}-{1}位")
					},
					vAreaCode : {
						required : "请输入地区",
						notEqual:"请输入地区"
					},
					addressDetail : {
						required : "请输入地址",
						rangelength:$.validator.format("地址长度{0}-{1}位")
					},
					cell : {
						required : "请输入您的联系电话",
						mobile : "请输入正确的手机号码"
					}
				}
			}).form();
		}
	</script>
</body>
</html>