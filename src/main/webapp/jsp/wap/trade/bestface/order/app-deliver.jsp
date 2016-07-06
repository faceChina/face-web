<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="javascript:;" method="post" id="jdispatchForm">
    <input type="hidden" name="orderNo" value="${orderNo }">
	<div class="list-col">
		<div class="list-inline">配送方式</div>
		<div class="list-inline box-flex">
			<span class="form-select"> <select class="form-control" name="deliveryWay" id="deliveryWay"
				onchange="toSelect(this)">
					<option value="1">快递配送</option>
					<option value="2">店铺配送</option>
					<option value="3">上门自取</option>
			</select>
			</span>
		</div>
	</div>
	<div class="select-info">
		<div id="select2">
			<div class="list-col">
				<div class="list-inline fnt-14">配送人员</div>
				<div class="list-inline box-flex">
					<input type="text" validate="{required:true,maxlength:16, messages:{required:'送货人姓名不能为空',maxlength:'送货人姓名不能超过16位'}}" name="senderName" class="form-control" />
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">联系方式</div>
				<div class="list-inline box-flex">
					<input type="text" name="senderCell" validate="{required:true,mobile:true, messages:{required:'送货人电话不能为空',mobile:'不是正确的手机号码'}}" class="form-control" />
				</div>
			</div>
		</div>
		<div id="select1" style="display: none;">
			<div class="list-col">
				<div class="list-inline">物流公司</div>
				<div class="list-inline box-flex">
					<span class="form-select"> 
					<select class="form-control" name="deliveryCompany">
						<option value="1">申通E物流</option>
						<option value="2">圆通速递</option>
						<option value="3">中通速递</option>
						<option value="4">汇通快递</option>
						<option value="5">韵达快递</option>
						<option value="6">天天快递</option>
						<option value="7">宅急送</option>
						<option value="8">顺丰速运</option>
						<option value="9">全峰快递</option>
						<option value="10">国通快递</option>
						<option value="11">其它</option>
						<option value="12">自提</option>
					</select>
					</span>
				</div>
			</div>
			<div class="list-col">
				<div class="list-inline">快递单号</div>
				<div class="list-inline box-flex">
					<input type="text" validate="{required:true,maxlength:16, messages:{required:'快递单号不能为空',maxlength:'快递单号不能超过16位'}}" name="deliverySn" class="form-control ignore" />
				</div>
			</div>
		</div>
		<div id="select3" style="display: none;"></div>
	</div>
</form>
<script>
$(function(){
	$('#deliveryWay').val('${deliveryWay}');
	$('#deliveryWay').attr('disabled', 'disabled');
	doHideOrShow('${deliveryWay}');
})
var bool = $("#jdispatchForm").validate({
	ignore : ".ignore",
 	rules:{
 		senderName:{
 			required:true,maxlength:16
		},
		senderCell:{
			required:true,mobile:true
		},
		deliverySn:{
			required:true,maxlength:16
		}
	},
	messages:{
		senderName:{
			required:'送货人姓名不能为空',maxlength:'送货人姓名不能超过16位'
		},
		senderCell:{
			required:'送货人电话不能为空',mobile:'不是正确的手机号码'
		},
		deliverySn:{
			required:'快递单号不能为空',maxlength:'快递单号不能超过16位'
		}
	}
 });
//送货方式选择
function toSelect(el){
	doHideOrShow(el.value);
};
 
function doHideOrShow(value){
    $('#deliveryWay').find('.select-info div').each(function(){
		$(this).hide();
	});
	$('#select'+ value).show().siblings().hide();
	$('input[name="senderName"]').addClass('ignore');
	$('input[name="senderCell"]').addClass('ignore');
	$('input[name="deliverySn"]').addClass('ignore');
	if ('1' == value) {
		$('input[name="deliverySn"]').removeClass('ignore');
	} else if ('2' == value) {
		$('input[name="senderName"]').removeClass('ignore');
		$('input[name="senderCell"]').removeClass('ignore');
	}
 }
 
//发货
function openDialog(el) {
	art.dialog({
		lock: true,
		width: '100%',
		title:'发货',
	    content:document.getElementById("jdispatch"),
		ok : function() {
			if (!bool.form()) {
				return false;
			}
			$.post(getDeliverUrl(), $('#jdispatchForm').serialize(), function(jsonData) {
				var data = JSON.parse(jsonData);
				if (data.success) {
					afterDeliver(el);
				} else {
					art.dialog.alert(data.info)
				}
			});
		},
		cancel : true
	})
}
</script>