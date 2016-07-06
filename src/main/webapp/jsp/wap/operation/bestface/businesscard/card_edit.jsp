<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="share-card.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/fonts/iconfont.css">
<script type="text/javascript" src="${resourceBasePath }js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/artDialog/iframeTools.js"></script>
<link rel="stylesheet" type="text/css" href="${resourceBasePath }js/artDialog/skins/myself.css">

<script type="text/javascript" src="${resourceBasePath }js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/validate/additional-methods.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/validate/additional-methods-new.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/validate/messages_zh.js"></script>
<script type="text/javascript" src="${resourceBasePath }js/slider.js"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/businesscard.css">

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>修改我的名片</title>
<script type="text/javascript" src="${resourcePath}js/plugin/area.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js"></script>
<script type="text/javascript">
var peList;
var cityList;
var selectPe = function() {
	var pe = $("#pe").val();
	$("#city").empty();
	cityList = null;
	initCity(pe);
	$("input[name='province']").val(pe);
	$("input[name='city']").val('');
	
};

var selectCity = function(obj) {
	var city = $("#city").val();
	$("input[name='city']").val(city);
};

var selectIndustry = function() {
	var ind = $("#ind").val();
	$("input[name='industry']").val(ind);
}
var initCity = function(pe) {
	var option = "<option value='0'>请选择市...</option>";
	$("#city").append(option);
	if(0!=pe){
		for ( var i = 0; i < peList.length; i++) {
			if (pe == peList[i].id) {
				cityList = peList[i].children;
				for ( var i = 0; i < cityList.length; i++) {
					var city = cityList[i];
					var option = "<option value='" + city.id + "' >" + city.name
							+ "</option>";
					$("#city").append(option);
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
	var industry = '${icard.industry}';
	if('' != industry){
		$("#ind").val(industry);
	}
	var province = '${icard.province}';
	if('' != province){
		$("#pe").val(province);
		var city = '${icard.city}';
		if('' != city){
			initCity(province);
			$('#city').val(city);
		}
	} 
});
</script>
</head>
<body>
<div class="container">
	<form action="${ctx}/wap/${shopNo}/any/icard/icard-edit/${icard.id}${ext}" class="m-form" id="jform" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="id" value="${icard.id}"/>
		<input type="hidden" name="industry" value="${icard.industry}" /><!-- 行业 -->
		<input type="hidden" name="province" value="${icard.province }"><!-- 省 -->
		<input type="hidden" name="city" value="${icard.city }"><!-- 市 -->
		<div class="info">
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label" style="font-size:16px;">必填项目</label>
			</div>
			<div class="form-group head-portrait" id="j-upload">
			    <label for="" class="col-xs-3 control-label">头像</label>
			    <div class="col-xs-3">
			    	<div class="pic j-tooltip" data-upload="true" >
			    		<span class="m-pic-icon"><img id="showImg" src="${picUrl}${icard.headPicture}" alt=""></span>
						<input class="m-pic-file" type="file" name="headImageFile" data-upload="up" accept="image/*" capture="camera">
			    	</div>
			    </div>
			    <div class="col-xs-1 pull-right">
			    	<i class="iconfont icon-right clr-light"></i>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">姓名</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="您的真实姓名" id="nickName" maxlength="16"  name="nickName" value="${icard.nickName}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">手机</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="您的手机号码" id="cell" name="cell" value="${icard.cell}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">公司</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写公司名称" id="company" maxlength="32" name="company" value="${icard.company}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">职位</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="您的岗位" id="position" maxlength="32" name="position" value="${icard.position}"></input>
			    </div>
			</div>
		</div>
		<div class="info">
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label" style="font-size:16px;">选填项目</label>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">行业</label>
			    <div class="col-xs-9 m-form-select">
			    	<select class="form-control" id="ind" onchange="selectIndustry();">
					  <option value="请选择">请选择</option>
					  <option valye="IT·互联网·游戏">IT·互联网·游戏</option>
					  <option valye="金融业(投资·保险·证券·基金)">金融业(投资·保险·证券·基金)</option>
					  <option valye="医疗·保健·美容">医疗·保健·美容</option>
					  <option valye="教育·培训·科研·院校">教育·培训·科研·院校</option>
					  <option valye="通信行业">通信行业</option>
					  <option valye="房地产开发·建筑与工程">房地产开发·建筑与工程</option>
					  <option valye="广告·会展·公关">广告·会展·公关</option>
					  <option valye="电子·微电子">电子·微电子</option>
					  <option valye="物业管理·商业中心">物业管理·商业中心</option>
					  <option valye="家具·室内设计·装潢">家具·室内设计·装潢</option>
					  <option valye="中介服务">中介服务</option>
					  <option valye="专业服务(咨询·财会·法律等)">专业服务(咨询·财会·法律等)</option>
					  <option valye="检验·检测·认证">检验·检测·认证</option>
					  <option valye="贸易·进出口">贸易·进出口</option>
					  <option valye="媒体·出版·文化传播">媒体·出版·文化传播</option>
					  <option valye="印刷·包装·造纸">印刷·包装·造纸</option>
					  <option valye="快速消费品">快速消费品</option>
					  <option valye="耐用消费品">耐用消费品</option>
					  <option valye="玩具·工艺品·收藏品·奢侈品">玩具·工艺品·收藏品·奢侈品</option>
					  <option valye="家电业">家电业</option>
					  <option valye="办公设备·用品">办公设备·用品</option>
					  <option valye="批发·零售">批发·零售</option>
					  <option valye="交通·运输·物流">交通·运输·物流</option>
					  <option valye="娱乐·运动·休闲">娱乐·运动·休闲</option>
					  <option valye="制药·生物工程">制药·生物工程</option>
					  <option valye="医疗设备·器械">医疗设备·器械</option>
					  <option valye="环保行业">环保行业</option>
					  <option valye="石油·化工·矿产·采掘·冶炼·原材料">石油·化工·矿产·采掘·冶炼·原材料</option>
					  <option valye="能源·水利">能源·水利</option>
					  <option valye="仪器·仪表·工业自动化·电气">仪器·仪表·工业自动化·电气</option>
					  <option valye="汽车·摩托车">汽车·摩托车</option>
					  <option valye="机械制造·机电·重工">机械制造·机电·重工</option>
					  <option valye="原材料及加工">原材料及加工</option>
					  <option valye="农·林·牧·渔">农·林·牧·渔</option>
					  <option valye="航空·航天研究与制造">航空·航天研究与制造</option>
					  <option valye="船舶制造">船舶制造</option>
					  <option valye="政府·非营利机构">政府·非营利机构</option>
					  <option valye="酒店·旅游">酒店·旅游</option>
					  <option valye="餐饮·娱乐">餐饮·娱乐</option>
					  <option valye="直销行业">直销行业</option>
					  <option valye="美容美发">美容美发</option>
					  <option valye="微商">微商</option>
					</select>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">部门</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写部门" maxlength="32" id="department" name="department" value="${icard.department}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">微信号</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写您的微信号" maxlength="128" id="wechatNo" name="wechatNo" value="${icard.wechatNo}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">邮箱地址</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写您的邮箱地址" id="email" name="email" value="${icard.email}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">所在地区</label>
			    <div class="col-xs-9" style="font-size:0;">
				<div class="col-xs-4 m-form-select">
					<select class="form-control"  title="省" id="pe" onchange="selectPe();">
					</select>
			    </div>
				<div class="col-xs-5 m-form-select">
			  	  	<select class="form-control" title="市" id="city" onchange="selectCity();" >
			  		  	<option>请选择市</option>
			    	</select>
				</div>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">详细地址</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写您的详细地址" maxlength="128" id="addressInfo" name="addressInfo" value="${icard.addressInfo}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">我专注</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写专注，如互联网营销" maxlength="16" id="focus" name="focus" value="${icard.focus}"></input>
			    </div>
			</div>
			<div class="form-group">
			    <label for="" class="col-xs-3 control-label">我在找</label>
			    <div class="col-xs-9">
			    	<input type="text" class="form-control" placeholder="请填写产品，服务" id="find" maxlength="16" name="find" value="${icard.find}"></input>
			    </div>
			</div>
		</div>
		<button type="submit" class="btn btn-danger btn-block">保存</button>
	</form>
</div>
<script type="text/javascript">
	//验证
	$('#jform button').click(function(){
		$('#jform').validate({
			rules:{
				nickName:{
					required:true,
				},
				cell:{
					required:true,
					mobile:true
				},
				company:{
					required:true,
				},
				position:{
					required:true,
				},
				email:{
					email:true,
				}
			},
			messages:{
				username:{
					required:'请输入您的真实姓名',
				},
				cell:{
					required:'请输入您的手机号码',
					mobile:"请输入正确的手机号码"
				},
				company:{
					required:'请输入您的公司名称',
				},
				job:{
					required:'请输入您的岗位',
				},
				email:{
					email:'请输入正确的邮箱地址',
				}
			},
			errorPlacement:function(error,element) {
				error.insertAfter(element);
			}
		});
	});

	//图片上传
	var picUpload = (function(){
		window.URL = window.URL || window.webkitURL;

		$('#j-upload').on('change','[data-upload="up"]',function(){
		 		handleFiles(this);
		 });

		 $('#j-upload').on('click','[data-upload="del"]',function(){
		 	//alert(1)
		 		handleDel(this);
		 });

		 //删除图片
		 function handleDel(obj){
		 	var parentEl = $(obj).closest('[data-upload="true"]'),
		 		imgEl = parentEl.find('.m-pic-icon'),
		 		fileEl = parentEl.find('[data-upload="up"]');

		 		$(obj).hide();
		 		imgEl.html('');
		 		fileEl.val('');


		 }

		 //上传图片
		function handleFiles(obj) {
			var files = obj.files,
				img = new Image(),
				parentEl = $(obj).closest('[data-upload="true"]'),
		 		imgEl = parentEl.find('.m-pic-icon'),
		 		delEl = parentEl.find('[data-upload="del"]');
			if(window.URL){
				//File API
				// alert(files[0].name + "," + files[0].size + " bytes");
				img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
				img.width = 200;
				img.onload = function(e) {
				 window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
				}

				imgEl.html(img);
				delEl.show();
			}
		}
	})();
</script>
</body>
</html>