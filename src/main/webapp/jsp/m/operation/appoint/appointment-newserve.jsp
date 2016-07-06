<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>预约管理</title>	
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js"></script>
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
	if('${appointment.isShowSellerMeg}'=='1'){
		$('input[name=cell]').removeAttr('disabled');
		$('input[name=address]').removeAttr('disabled');
	}
	$("body").find("input[name='uploadImg']").each(function(index) {
		imgUpload($(this));
	});
});
function tj(){
	$("#content").val(getContent());
	var flag=$("#j-form").validate({}).form();
	if(flag){
		var imgPath = $('#imgPath').val();
		if (null == imgPath || imgPath == "") {
			art.dialog.alert("预约封面不能为空");
			return;
		}
		$("tr[data-attr]").each(function(index,el){
			$(this).find("input[data-sort]").val(index);
		})
		if($("#noticeCell").attr('checked')){
			$("#isStartSms").val(1);
		}
		$("#price").val($("#priceYuan").val()*100);
		$(".j-loading").show();
		$("#j-form").submit();
	}
}
function imgUpload(uploadBtn) {
	var url = "/any/files/upload${ext}";
	new AjaxUpload(uploadBtn, {
		action : url,
		data : {},
		autoSubmit : true,
		responseType : 'json',
		onSubmit : function(file, ext) {					
			var imageSuffix = new RegExp('${imageSuffix}');
			if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
				art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
				return false;
			}
		},

		onComplete : function(file, response) {
			if (response.code == -1) {
				art.dialog.alert(response.desc);
				return;
			}
			if (response.flag == "SUCCESS") {
				var src_path = '${picUrl}' + response.source;
				console.log(src_path);
				$('#showImg').attr('src', src_path);
				$('#imgPath').val(response.path);
			} else {
				art.dialog.alert(response.info);
			}
		}

	});
}
</script>
</head>
<body>
<!-- header -->
<%@ include file="../../common/header.jsp"%>
<!-- header end --> 
	
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
							<form class="form-horizontal" role="form" id="j-form" method="post">
								<input type="hidden" name="isStartSms" id="isStartSms" value="0"/>
								<input type="hidden" name="price" id="price" value=""/>
								<input type="hidden" name="isShowSellerMeg" id="isShowSellerMeg" value="${appointment.isShowSellerMeg }"/>
								<input type="hidden" id="imgPath" name="picturePath" value="${appointment.picturePath}" />
								<div class="other-set" style="margin-bottom:10px"><b>1.填写预约信息</b></div>
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约名称：</label>
								    <div class="col-sm-6">
								      <input type="text" value="${appointment.name }" maxlength="20" validate="{required:true}" name="name" class="form-control"/>
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left">预约价：</label> 
								    <div class="col-sm-6">
								      <input type="text" id="priceYuan" validate="{moneyNotOne:true}" <c:if test='${not empty appointment.price && 0!=appointment.price }'>value="<fmt:formatNumber pattern='0.00' value='${appointment.price/100 }'/>"</c:if> name="priceYuan" class="form-control"/>
								    </div>
								    <div class="col-sm-3 control-left">&nbsp; 元 （有则填）</div>
								  </div>
								  
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left">库存：</label> 
								    <div class="col-sm-6">
								      <input type="text" name="inventory" value="${appointment.inventory }" validate="{range:[1,99999],positiveInteger:true}" class="form-control"/>
								    </div>
								    <div class="col-sm-3 control-left">&nbsp; 件（有则填）</div>
								  </div>
								  
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约封面：</label>
								    <div class="col-sm-9">
								      		<div class="message-img">
								      			<c:if test='${empty appointment.picturePath }'><img id="showImg" src="${resourcePath }img/banner1.jpg"  alt="" width="300" height="160" style="display: block; margin: 19px auto 0px;"></c:if>
												<c:if test='${not empty appointment.picturePath&&!fn:contains(appointment.picturePath, "resource/m/") }'><img id="showImg" src="${picUrl}${appointment.picturePath}" alt="" width="300" height="160" style="display: block; margin: 19px auto 0px;"></c:if>
												<c:if test='${not empty appointment.picturePath&&fn:contains(appointment.picturePath, "resource/m/") }'><img id="showImg" src="${appointment.picturePath}" alt="" width="300" height="160" style="display: block; margin: 19px auto 0px;"></c:if>
											</div>
											<div class="btn btn-default btn-upload">
												选择图片<input type="button" name="uploadImg" class="form-control">
											</div>
											<p class="help-block">建议尺寸：宽<span class="fontcor-red">640</span>像素，高<span class="fontcor-red">380</span>像素</p>
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
								      <input type="text" value="${appointment.address }" name="address" maxlength="60" validate="{required:true}" disabled="disabled" class="form-control"/>
								    </div>
								    <div class="col-sm-3"></div>
								  </div>
								  <div class="form-group" data-id="shopsInfo" <c:if test='${appointment.isShowSellerMeg!=1 }'>style="display:none;"</c:if>>
								    <label for="" class="col-sm-2 control-left"><b class="clr-attention">*</b>预约电话：</label> 
								    <div class="col-sm-6">
								      <input type="text" value="${appointment.cell }" name="cell" validate="{required:true,mobile:true}" disabled="disabled" class="form-control"/>
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
								  <div class="other-set"  style="margin-bottom:10px"><b>2.其他设置</b></div>
								  <div class="form-group">
								    <label for="" class="col-sm-2 control-label">重命名在线预约：</label>
								    <div class="col-sm-2">
									      <input type="text" value="<c:if test='${empty appointment.navigationTitle }'>在线预约</c:if>${appointment.navigationTitle }" validate="{required:true, rangelength:[1,10]}" name="navigationTitle" class="form-control"/>
								    </div>
								    <div class="col-sm-8 control-left">&nbsp; 商户修改用户手机中“在线预约”导航的名词，可修改成“在线报名”等</div>
								  </div>
								   <div class="form-group">
									    <label for="" class="col-sm-2 control-label">重命名立即预约：</label>
									    <div class="col-sm-2">
									      <input type="text" value="<c:if test='${empty appointment.buttonTitle }'>立即预约</c:if>${appointment.buttonTitle }" validate="{required:true, rangelength:[1,10] }" name="buttonTitle" class="form-control"/>
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
								      	<button type="button" onclick="tj();" class="btn btn-default">保存</button>
										<button type="button" onclick="chongzhi();"  class="btn btn-default" >重置</button>
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
$(function(){
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
			}
		})
	});



});



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
</script>
</body>
</html>

