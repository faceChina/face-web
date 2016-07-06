<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>预约管理</title>	
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<style>
label.control-left{
	width: 86px;
}
label.width150{
	width: 150px;
}
</style>
<script type="text/javascript">
UE.getEditor('editor');
function getContent() {return UE.getEditor('editor').getContent();}
$(function(){
	$.metadata.setType("attr", "validate");
	tab("appointment");
	$('#j-tempname span').hover(function(){
		$(this).next('img').show();
	},function(){
		$(this).next('img').hide();
	})
	if('${appointment.isShowSellerMeg}'=='1'){
		$('input[name=cell]').removeAttr('disabled');
		$('input[name=address]').removeAttr('disabled');
	}
	//开关
	//商家信息设置
	$("[data-onoff]").each(function(){
		$(this).onoff({
			on:function(){
				showhideLayer('shopsInfo');
				$("input").removeAttr('disabled');
				$("#isShowSellerMeg").val(1)
				return true;
			},
			off:function(){
				showhideLayer('shopsInfo');
				$('input[name=address]').attr('disabled','disabled');
				$('input[name=cell]').attr('disabled','disabled');
				$("#isShowSellerMeg").val(0)
				return true;
				//console.log("off")
			}
		})
	});

	//选择商品分类模板
	var val=$(".templatelist").find(".active").find("input[type='hidden']").val();//选中的模版的传递值
	$('.templatelist').find("li").each(function(index) {
		$(this).find("div").first().click(function(event) {
			$('.templatelist li').removeClass("active");
			$(this).parent("li").addClass("active");
			val=$(".templatelist").find(".active").find("input[type='hidden']").val();
		});
	});	
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
								<li class="active"><a href="#temp1" data-toggle="tab">预约设置</a></li>
							</ul>
						</div>
						<div class="content tab-content">
								<form action="" class="form-horizontal" id="j-form" method="post">
								  <input type="hidden" name="templateId" id="templateId" value="<c:if test='${empty appointment.templateId }'>1</c:if>${appointment.templateId }"/>
								  <input type="hidden" name="isStartSms" id="isStartSms" value="0"/>
								  <input type="hidden" name="isShowSellerMeg" id="isShowSellerMeg" value="${appointment.isShowSellerMeg }"/>
								  <div class="other-set" style="margin-bottom:10px"><b>1.选择预约模板</b></div>
								  <div class="form-group">
								    <div class="col-sm-2">
								    	<button type="button" class="btn btn-default" onclick="popTemplateList()">选择模板</button>
								    </div>
								     <div class="col-sm-6 control-left" id="j-tempname">
										<span class="title">模板<c:if test="${empty appointment.templateId}">1</c:if><c:if test="${not empty appointment.templateId }">${appointment.templateId }</c:if></span>
										<img style="display:none;" src='${resourcePath }img/appointment-temp<c:if test="${empty appointment.templateId}">1</c:if><c:if test="${not empty appointment.templateId }">${appointment.templateId }</c:if>.jpg' alt="">
								      </div>
								  </div>

								  <div class="other-set" style="margin-bottom:10px"><b>2.填写预约信息</b></div>
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约名称：</label>
								    <div class="col-sm-6">
								      <input type="text" value="${appointment.name }" validate="{required:true,messages:{required : '请输入预约名称'}}" name="name" class="form-control"/>
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="" class="col-sm-2">商家信息设置：</label>
								    <div class="col-sm-1">
								    	<div class="OC_box_bar" id="OC_box_bar" data-onoff="<c:if test='${appointment.isShowSellerMeg==1 }'>on</c:if><c:if test='${appointment.isShowSellerMeg!=1 }'>off</c:if>">
											<h1 class="ico_btn hide" data-onoff-on=""></h1>
											<h3 class="ico_btn" data-onoff-off=""></h3>
											<h2 class="ico_btn" id="" data-onoff-handle="" style="left: 0px;"></h2>
										</div>
									</div>
								  </div>
								  <div class="form-group" data-id="shopsInfo" <c:if test='${appointment.isShowSellerMeg!=1 }'>style="display:none;"</c:if>>
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约地址：</label> 
								    <div class="col-sm-6">
								      <input type="text" value="${appointment.address }" validate="{required:true,messages:{required : '请输入预约地址'}}" name="address" disabled="disabled" class="form-control"/>
								    </div>
								    <div class="col-sm-3"></div>
								  </div>
								  <div class="form-group" data-id="shopsInfo" <c:if test='${appointment.isShowSellerMeg!=1 }'>style="display:none;"</c:if>>
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约电话：</label> 
								    <div class="col-sm-6">
								      <input type="text" value="${appointment.cell }" validate="{required:true,mobile:true}" name="cell" disabled="disabled" class="form-control"/>
								    </div>
								    <div class="col-sm-3"></div>
								  </div>
								  
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约简介：</label>
								    <div class="col-sm-9">
										<script id="editor" type="text/plain" style="width:100%;height:500px;">${appointment.content}</script>
								      	<input type="hidden" id="content" name="content" validate="{required:true}"/>
								    </div>
								  </div>
								  <div class="other-set"  style="margin-bottom:10px"><b>3.其他设置</b></div>
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-label">重命名在线预约：</label>
								    <div class="col-sm-2">
								      <input validate="{required:true, rangelength:[1,10]}" type="text" value="<c:if test='${empty appointment.navigationTitle }'>在线预约</c:if>${appointment.navigationTitle }" name="navigationTitle" class="form-control"/>
								    </div>
								    <div class="col-sm-8 control-left">&nbsp; 商户修改用户手机中“在线预约”导航的名词，可修改成“在线报名”等</div>
								  </div>
								   <div class="form-group">
									    <label for="" class="col-sm-2 control-label">重命名立即预约：</label>
									    <div class="col-sm-2">
									      <input validate="{required:true,rangelength:[1,10]}" type="text" value="<c:if test='${empty appointment.buttonTitle }'>立即预约</c:if>${appointment.buttonTitle }" name="buttonTitle" class="form-control"/>
									    </div>
									    <div class="col-sm-8 control-left">&nbsp; 商户修改用户手机中“立即预约”按钮的名词，可修改成“提交订单”等</div>
								  </div>
								  <div class="form-group">
									  <div class="col-sm-6 control-left"><b>客户提交信息设置</b></div>
									  <div class="col-sm-6 control-label"><button type="button" onclick="add()" class="btn btn-default" data-attr="add">添加</button></div>
								  </div>
								  <div class="form-group">
								  	   <%@include file="user-form-data.jsp" %>
								  </div>
								  <div class="form-group">
									  <div class="col-sm-12 control-left"><b>预约限制</b></div>
								  </div>

								  
								  <div class="form-group" id="">
								    <label for="" class="col-sm-3 control-label width150">同一手机号可以预约：</label> 
								    <div class="col-sm-3">
								      <input type="text" value="<c:if test='${appointment.maxVal!=0 }'>${appointment.maxVal }</c:if>" name="maxVal" placeholder="默认不限制" 
								      validate="{range:[1,99999],positiveInteger:true}" class="form-control"/>
								    </div>
								    <div class="col-sm-2 control-left">&nbsp; 次</div>
								  </div>
								  
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left width150">订单短信通知手机号：</label>
								    <div class="col-sm-4">
								    	<input type="text" value="${appointment.smsPhone }" name="smsPhone" validate="{required:function(){return $('#noticeCell').attr('checked')=='checked'},mobile:true}" placeholder="请输入手机号码"  class="form-control"/>
								    </div>
								    <div class="col-sm-2">
								    	<div class="checkbox">
								    		<label ><input type="checkbox" name="noticeCell" id="noticeCell" <c:if test='${appointment.isStartSms==1 }'>checked="checked"</c:if>/>  开启</label>
								    	</div>
								    	
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <div class="col-sm-offset-3 col-sm-9">
								      	<a href="javascript:;" onclick="tj();" class="btn btn-default"><c:if test="${empty appointment.id }">下一步，选择预约商品</c:if><c:if test="${not empty appointment.id }">保存</c:if></a>
										<button type="button"  onclick="chongzhi();" class="btn btn-default" >重置</button>
								    </div>
								  </div>
								</form>
						
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<!-- body end -->

<!-- 选择模版 -->
<div class="content tab-content" id="poptemplate" style="display:none;">
	<div class="tab-pane fade in active panel-body" id="temp1">
		<ul class="row templatelist">
			<li class="<c:if test='${appointment.templateId != 2 }'>active</c:if>">
				<div>
					<span></span>
					<img src="${resourcePath }img/appointment-temp1.jpg" alt="模板1">
				</div>
				<div>
					<input type="hidden" name="temp1" value="模板1" data-id='1'> 模板1
				</div>
			</li>
			<li class="<c:if test='${appointment.templateId == 2 }'>active</c:if>">
				<div>
					<span></span>
					<img src="${resourcePath }img/appointment-temp2.jpg" alt="模板2">
				</div>
				<div>
					<input type="hidden" name="temp2" value="模板2" data-id='2'> 模板2
				</div>
			</li>							
		</ul>						
	</div>
</div>

<%@include file="../../common/footer.jsp"%>
<script type="text/javascript" src="${resourcePath }js/public.js"></script>
<script type="text/javascript" src="${resourcePath }js/onoff.js"></script>
<script type="text/javascript">
function chongzhi(){
	$('form').find('input[type=text]').each(function(){
		$(this).val('')
	});
	$('form').find('input[type=checkbox]').each(function(){
		$(this).removeAttr('checked');
	});
}
//弹出模版选择窗口
function popTemplateList(){
	art.dialog({
		title:"更改模板",
		content:document.getElementById("poptemplate"),
		button:[{
			name:"保存",
			focus:true,
			callback:function(){
			var tempname = $('#poptemplate').find('li.active input').val();
			$("#templateId").val($('#poptemplate').find('li.active input').data('id'))
			var imgsrc = $('#poptemplate li.active').find('img').attr('src');
			$('#j-tempname .title').text(tempname);
			$('#j-tempname img').attr('src',imgsrc);
			}
		}]
	});
}

/*上移*/
function up(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var index = tr.index();
	alert(index)
	if (index != 2) {
		tr.insertBefore(tr.prev());
	} else {
		return false;
	}
}

/*下移*/
function down(el) {
	var tbody = $(el).parent("td").parent("tr").parent("tbody");
	var tr = $(el).parent("td").parent("tr");
	var len = tbody.find("tr").length;
	var index = tr.index();
	alert(index)
	if (index != len - 1) {
		tr.insertAfter(tr.next());
	} else {
		return false;
	}
}
//选择只否支付定金
$("input[name='payEarnest']").click(function(){
	var bool = $("input[name='payEarnest']").first().prop("checked");
	if(bool){
		$("#earnestMoney").show();
	}else{
		$("#earnestMoney").hide();
	}
});

function tj(){
	$("#content").val(getContent());
	var va = $("#j-form").validate({}).form();
	if(va){
		$("tr[data-attr]").each(function(index,el){
			$(this).find("input[data-sort]").val(index);
		})
		if($("#noticeCell").attr('checked')){
			$("#isStartSms").val(1);
		}
		$(".j-loading").show();
		$("#j-form").submit();
	}
}
</script>
</body>
</html>

