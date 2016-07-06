<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>完善会员卡资料</title>
<%@ include file="../../common/top.jsp"%>
<script type="text/javascript" src="${resourcePath}js/plugin/area.js"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath }member/css/main.css">
<script type="text/javascript">
 window.onload = function() {
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
	if (null != (store = '${memberCard.vAreaCode}') && "" != store) {
		$('#pe').val('${memberCard.province}');
		selectPe();
		$('#city').val('${memberCard.city}');
		selectCity();
		$('#area').val('${memberCard.district}');
		selectArea();
	} else {
		$('#pe').val('0');
		selectPe();
		$('#city').val('0');
		selectCity();
		$('#area').val('0');
		selectArea();
	}
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

</script>

</head>
<body>

<div id="box">

	<form action="save.htm" method="post" data-form class="form" id="jform" >
	
		<div class="list-row list-row-width">
			<div class="list-col">
				<div class="list-inline">姓名</div>
				<div class="list-inline box-flex"><input type="text" class="form-control" name="userName" id="username" placeholder="请输入您的姓名" value="${memberCard.userName }" data-form-control></div>
			</div>
			<div class="list-col">
				<div class="list-inline">手机</div>
				<div class="list-inline box-flex"><input type="text" class="form-control" name="cell" id="moblie" placeholder="请填写您的手机号码" data-form-control value="${memberCard.cell }"></div>
			</div>
			<div class="list-col">
				<div class="list-inline">性别</div>
				<div class="list-inline box-flex">
					<span class="form-select-san">
						<select name="sex" data-form-control id="sex" data-url="true">
							<option value="0" <c:if test="${memberCard.sex == '0' }">selected</c:if>>请选择</option>
							<option value="1" <c:if test="${memberCard.sex == '1' }">selected</c:if>>男</option>
							<option value="2"<c:if test="${memberCard.sex == '2' }">selected</c:if>>女</option>
						</select>
					</span>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">生日</div>
				<div class="list-inline box-flex"><input type="date" name="birthday" id="birthday" value="${memberCard.birthday }" class="form-control" placeholder="请填写您的生日" data-form-control></div>
			</div>
			<div class="list-col">
				<div class="list-inline">省</div>
				<div class="list-inline box-flex">
					<span class="form-select-san">
						<select id="pe" class="form-control" data-form-control name="province" onchange="selectPe();">
						</select>
					</span>
				</div>
			</div>
			
			<div class="list-col">
				<div class="list-inline">市</div>
				<div class="list-inline box-flex">
					<span class="form-select-san">
						<select class="form-control" id="city" name="city" data-form-control onchange="selectCity();">
						</select>
					</span>
				</div>
			</div>
			
			<div class="list-col">
				<div class="list-inline">区/县</div>
				<div class="list-inline box-flex">
					<span class="form-select-san">
						<select class="form-control" id="area" name="district" data-form-control onchange="selectArea();">
						</select>
					</span>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">详细地址</div>
				<div class="list-inline box-flex"><input type="text" id="addressDetail" data-form-control name="addressDetail" class="form-control" placeholder="请填写您的具体地址" value="${memberCard.addressDetail }"></div>
			</div>
		</div>
		
		<div class="button">
			<button type="button" class="btn btn-block btn-danger" onclick="saveInformation()" data-submit="false">提交</button>
		</div>
		
	</form>
	<%@ include file="../../common/nav.jsp"%>
</div>

<script type="text/javascript">
	navbar("personal");
	function saveInformation() {
		$("#jform").validate({
			rules : {
				userName : {
					rangelength:[2,15]
				},
				vAreaCode : {
					notEqual:0
				},
				addressDetail : {
					rangelength:[5,60]
				},
				cell : {
					mobile : true
				}
			},
			messages : {
				userName : {
					rangelength:$.validator.format("姓名长度{0}-{1}位")
				},
				vAreaCode : {
					notEqual:"请输入地区"
				},
				addressDetail : {
					rangelength:$.validator.format("地址长度{0}-{1}位")
				},
				cell : {
					mobile : "请输入正确的手机号码"
				}
			}
		}).form();
		$("#jform").submit();
	}
</script>
</body>
</html>