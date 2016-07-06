<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />

<title>预约管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("appointment");
	});
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>

	<!-- body  -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<c:set var="crumbs" value="ordershop"/>
							<%@include file="../../common/crumbs.jsp"%>

				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">新增预约</a></li>
							</ul>
						</div>
						<div class="content tab-content">
							<div class="appointment-type">
								<a class="appointment-type-itme" href="/u/appoint/add.htm?type=1">
									<b>商品类预约</b>
									<span>适用于餐饮、干洗等行业</span>
								</a>
								<a class="appointment-type-itme" href="/u/appoint/add.htm?type=2">
									<b>服务类预约</b>
									<span>适用于汽车试驾、活动报名等行业</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- body end -->

	<%@include file="../../common/footer.jsp"%>
</body>

<!-- 备注 -->
<div id="jremark" style="display: none;">
	<form action="" class="" id="jremarkForm">
		<div class="form-group">
			<textarea class="form-control" id="msg" name="msg" style="width: 500px; height: 200px;"></textarea>
		</div>
	</form>
</div>

<!-- 关闭定单 -->
<div id="jcancelOrder" style="display:none;">
	<h4>您确定要取消以下订单？</h4>
	<p>订单号：2312321321321</p>
</div>

<!-- 完成定单 -->
<div id="jcompleteOrder" style="display:none;">
	<h4>您确定要完成以下订单？</h4>
	<p>订单号：2312321321321</p>
</div>

<!-- 发货 -->
<div id="jdispatch" style="display: none;">
	<form action="" class="form-horizontal" id="jdispatchForm">
		<div class="form-group">
			<label class="col-md-4 control-label">送货方式选择：</label>
			<div class="col-md-8">
				<select class="form-control" onchange="hideAndShow(this)">
					<option>送货上门</option>
					<option>快递</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-4 control-label">送货人姓名：</label>
			<div class="col-md-8">
				<input type="text" value="" class="form-control" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-4 control-label">联系方式：</label>
			<div class="col-md-8">
				<input type="text" value="" class="form-control" />
			</div>
		</div>

		<div class="form-group hide">
			<label class="col-md-4 control-label">物流公司：</label>
			<div class="col-md-8">
				<select class="form-control">
					<option>申通</option>
					<option>圆通</option>
					<option>中通</option>
				</select>
			</div>
		</div>

		<div class="form-group hide">
			<label class="col-md-4 control-label">快递单号：</label>
			<div class="col-md-8">
				<input type="text" value="" class="form-control" />
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	//活动滑动按钮	
	function clickOnOff(thiz) {
		var thizObj = $(thiz);
		var state = thizObj.attr('state');
		if (state != undefined && state != '') {
			if (thizObj.prev().hasClass("hide")) {
				//设为关闭
				thizObj.animate({
					left : "0"
				}, 200, function() {
					thizObj.prev().removeClass("hide");
					thizObj.prev().prev().addClass("hide");
				});
			} else {
				//设为开启
				thizObj.animate({
					left : "26px"
				}, 200, function() {
					thizObj.prev().addClass("hide");
					thizObj.prev().prev().removeClass("hide");
				});

			}
			textStateRep(thizObj, state);
		} else {
			art.dialog.tips("活动时间已结束");
		}
		
		activityOnOff(thiz);
	}
	//删除
	function del(el) {
		var thiz = el;
		art.dialog.confirm('确认删除？', function() {
			$(thiz).parents("tr").remove();
		}, function() {
			return true;
		});
	}

	//活动开启关闭
	function activityOnOff(thiz) {
		var thizObj = $(thiz);
		var str = '<a href="appointment-shop.html" class="color-danger">商品管理</a> | <a href="appointment-create.html">修改</a> | <a href="javascript:;"  onclick="del(this)">删除</a>';
		
		//设置当前对象state属性on或者off 移除state表示活动结束
		var state = thizObj.attr('state');
		if(state == "on"){
			thizObj.parents("td").prev().html(str);
		}
		if(state == "off"){
			thizObj.parents("td").prev().html("");
		}

		//state不存在或者为空的时候
		if (state != undefined && state != '') {

		}
	}

	//状态文字替换
	function textStateRep(obj, str) {
		//$(thiz).text(str);
		var timeState = obj.parents('tr').find('.j-timestate');
		var stateText = obj.parents('tr').find('.j-state');
		var operation = obj.parents('tr').find('.j-operation');
		if (str == 'off') {

			if (timeState.attr('timestate') == '0') {
				stateText.text('进行中');
			} else if (timeState.attr('timestate') == '1') {
				stateText.text('进行中');
			} else if (timeState.attr('timestate') == '2') {
				stateText.text('未开始');
				operation.show();
			}
			obj.attr('state', 'on');
		} else {
			operation.hide();
			stateText.text('已关闭');
			obj.attr('state', 'off');

		}

	}
	//备注
	function remarks(str) {
		art.dialog({
			title : "添加备注",
			lock : true,
			content : document.getElementById("jremark"),
			ok : function() {
				var flag = $("#msg").val();
				if (flag != "") {
					$(str).find("i").show()
				} else {
					$(str).find("i").hide()
				}
			},
			cancel : true
		})
	}

	function remarksOver(str) {
		if ($(str).next().text() != "") {
			$(str).next().show()
		}
	}
	function remarksOut(str) {
		$(str).next().hide()
	}
	
	//关闭定单
	function closeOrder(el){
		art.dialog({
			title:"取消定单",
			lock:true,
			content:document.getElementById("jcancelOrder"),
			ok:function(){
				$(el).parents("td").html("交易关闭");
			},
			cancel:true
		})
	}

	//交易完成
	function completeOrder(el){
		art.dialog({
			title:"完成定单",
			lock:true,
			content:document.getElementById("jcompleteOrder"),
			ok:function(){
				$(el).parents("td").html("交易完成");
			},
			cancel:true
		})
	}
</script>

</html>

