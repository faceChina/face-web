<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@ include file="../../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图标</title>
</head>
<style type="text/css">
.list-icons li{float: left; width: 60px; margin: 5px; background: #ddd}
li.addicon{background: #000;}
</style>
<body>
<ul class="nav nav-tabs" style="background:#f6f6f6;">
  <li class="active"><a href="#icon" data-toggle="tab">图标</a></li></ul>

<!-- Tab panes -->
<div class="tab-content">
<form name="form" id="formPage" action="${ctx}/u/good/shopType/tempImgList${ext}" method="post">
  <div class="tab-pane active" id="icon">
  		<div style="margin:10px 0;">
			请选择类别： 
			<select id="type" name="type" onchange="changeFolder(this.value)">
				<option value="">全部分类</option>
				<option value="FOOD">餐饮</option>
				<option value="HOTEL">酒店</option>
				<option value="CAR">汽车</option>
				<option value="TOURISM">旅游</option>
				<option value="REALTY">房地产</option>
				<option value="EDUCATION">教育培训</option>
				<option value="BODYBUILDING">健身美容</option>
				<option value="MEDICAL">医疗保健</option>
				<option value="PHOTOGRAPHY">婚纱摄影</option>
				<option value="CARTOON">卡通图标</option>
				<option value="COLOR">彩色图标</option>
				<option value="WHITE">白色图标</option>
				<option value="LINE">线条图标</option>
			</select>
			<select id="color" name="color" onchange="changeColor(this.value)">
				<option value="">全部颜色</option>
				<option value="RED">红</option>
				<option value="ORANGE">橙</option>
				<option value="YELLOW">黄</option>
				<option value="GREEN">绿</option>
				<option value="BLUE">蓝</option>
				<option value="GRAY">灰</option>
				<option value="VIOLET">紫</option>
				<option value="BROWN">棕</option>
				<option value="WHITE">白</option>
			</select>
		</div>
		<ul class="list-icons" id="ddddd">
			<c:forEach items="${pagination.datas }" var="data" varStatus="dataStatus">
				<li>
					<a href="javascript:void(0)" >
						<img class="attimg" style="width: 60px; height: 60px;" src="${data.imgPath }">
					</a>
				</li>
			</c:forEach>
		</ul>
	    <%@include file="../../../common/page.jsp"%>
  </div>
  </form>
</div>

<script type="text/javascript">
$('.list-icons li').on('click',function(){
	$(this).addClass('addicon').siblings().removeClass('addicon');
})

$(function() {
	var type = '${type}';
	$('#type').val(type);
	$('#color').val('${color}');
	if (type == "" || type == "CARTOON" || type== "COLOR" || type == "LINE" ||  type == "WHITE") {
		$('#color').attr("disabled","disabled");
	}
});

function changeFolder(v){
	$('#color').val("");
	submitForm();
}
function changeColor(v){
    submitForm();
}

function submitForm() {
	$('#formPage').submit();
}
</script>
</body>
</html>

