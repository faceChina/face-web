<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送方式设置</title>
<%@ include file="../../../common/base.jsp"%>
<script type="text/javascript" src="${resourcePath}plugins/area.js"></script>
<script type="text/javascript">
	$(function() {
		tab("tempmanage");
		$.metadata.setType("attr", "validate");
	});
	function formSubmit() {
		var formvali = $("#j-activity").validate({});
		if (formvali.form()) {
			$('#submitButton').attr('disabled','disabled');
			$(".j-loading").show();
			$('#j-activity').submit();
		}
	}
</script>
</head>
<body>
	<%@ include file="../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="logistics"/>
					<%@include file="../../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-take-set"
									data-toggle="tab">门店自取管理</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="alert alert-warning" role="alert">
								<p>设置门店自取后，买家可以就近选择预设的自提点，下单后请尽快将商品配送至指定自提点</p>
							</div>
							<form method="post" id="j-activity">
								<div class="form-horizontal">
									<div class="form-group">
										<label class="col-md-2 control-label">自提点名称：</label>
										<div class="col-md-10">
											<input type="text"
												validate="{required:true, messages:{required:'请输入自提点名称'}}"
												value="<c:out value='${store.name }'/>" placeholder="请填写自提点名称便于买家理解和管理"
												name="name" maxlength="32"
												class="form-control input-short-6">
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">自提点地址：</label>
										<div class="col-sm-3">
											<select class="form-control"
											    name="province" id="pe" onchange="selectPe();">
												<option value="">请选择省份</option>
											</select>
										</div>
										<div class="col-sm-3" style="margin: 0 15px;">
											<select class="form-control" id="city" name="city"
												onchange="selectCity();">
												<option value="">请选择城市</option>
											</select>
										</div>
										<div class="col-sm-3">
											<select class="form-control" id="area"
												onchange="selectArea();">
												<option value="0">请选择区/县</option>
											</select>
											<input type="hidden"  validate="{required:true,notEqual:0, messages:{required:'请选择门店自提点地区', notEqual:'请选择门店自提点地区'}}" name="county" id="areaCode">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">具体地址：</label>
										<div class="col-md-10">
											<input type="text"
												validate="{required:true, messages:{required:'请输入具体地址'}}"
												value="<c:out value='${store.address }'/>" placeholder="详细街道地址，不需要重复填写省/市/区"
												name="address" class="form-control input-short-6">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">联系电话：</label>
										<div class="col-md-10">
											<input type="text"
												validate="{required:true, mobile:true, messages:{required:'请输入手机号码', mobile:'请输入正确的手机号码'}}"
												value="<c:out value='${store.phone }'/>" placeholder="填写准确的联系电话，便于买家联系"
												name="phone" class="form-control input-short-6">
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-10 col-md-offset-2">
											<button id="submitButton" type="button" onclick="formSubmit()"
												class="btn btn-default">确认保存</button>
											<button type="button"
												onclick="location.href='${ctx }/u/shop/logistics/ztlist${ext}'"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../../common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		$("#pe").html("");
		var option = "<option>请选择省份</option>";
		$("#pe").append(option);
		peList = areaP.children;
		for (var i = 0; i < peList.length; i++) {
			var pe = peList[i];
			var option = "<option value='" + pe.id + "'>" + pe.name
					+ "</option>";
			$("#pe").append(option);
		}
		$("#pe").val("0");
		
		//初始化值
		var store;
		if (null != (store = '${store}')) {
			$('#pe').val('${store.province}');
			selectPe();
			$('#city').val('${store.city}');
			selectCity();
			$('#area').val('${store.county}');
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
		selectArea();
	};

	var selectCity = function() {
		var city = $("#city").val();
		$("#area").empty();
		initArea(city);
		selectArea();
	};
	var selectArea = function() {
		var area = $("#area").val();
		$('#areaCode').val(area);
	};
	var initCity = function(pe) {
		$("#city").html("");
		var option = "<option>请选择城市</option>";
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
		var option = "<option value='0'>请选择区/县</option>";
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
</html>