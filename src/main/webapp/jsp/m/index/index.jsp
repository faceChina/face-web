<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的管家</title>
<%@ include file="../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("steward");
});

// function intoShop(no){
// 	$("#shopNo").val(no);
// 	$.post("${ctx}/u/index/checkDate${ext}",{"no":no},function(falg){
// 		console.log(falg);
// 		if(falg == 1 || falg == "1"){
// 			$(".j-loading").show();
// 			$('#checkFrom').attr("action",'${ctx}/u/index/check${ext}').submit();
// 		}else if(-1==falg || '-1'==falg){
// 			alertMsg("产品已过期！");
// 		}else{
// 			location.href = "${ctx}/login${ext}";
// 		}
// 	});
// }
	
</script>
</head>
<body>

<!-- header -->
	<%@include file="../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
<!-- body -->
<div class="container" id="j-content">
	<div class="row">
		<div class="col-md-2 ">
			<!--nav-left -->
			<c:set var="crumbs" value="pingtai"/>
			<%@include file="../common/left.jsp"%>
			<!--nav-left end-->
		</div>
		<div class="col-md-10">
			<div class="row">
					<%@include file="../common/crumbs.jsp"%>
			</div>
			<div class="row">	
				<div class="box box-auto">
					<div class="title">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#members-set" data-toggle="tab">我的钱包</a></li>
						</ul>
					</div>
					<div class="content">
						<ul class="media-info" style="width:25%;line-height:30px;">
							<li>可用余额：<b class="color-danger"><fmt:formatNumber value="${amount / 100 }" pattern="0.00" /></b> 元</li>
						</ul>
						<ul class="media-info media-columns">
							<li class="btn-li">
								<a href="${ctx }/u/account/index${ext}" class="btn btn-default">收支明细</a>
								<a href="${ctx }/u/account/withdraw/index${ext}" class="btn btn-default btn-danger">提现</a>
							</li>
						</ul>
					</div>
				</div>

				<div class="box box-auto">
					<div class="title">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#members-set" data-toggle="tab">我的店铺</a></li>
						</ul>
					</div>
					<div class="content">
					<form name="form" id="checkFrom" action="" method="post">
					<input type="hidden" id="shopNo" name="no" value="">	
						<table class="table table-bordered">
							<thead>
								<tr>
									<th width="20%" class="text-center">店铺名称</th>
									<th width="10%" class="text-center">店铺类型</th>
									<th width="25%" class="text-center">创建/到期时间</th>
									<th	width="20%" class="text-center">公众号</th>
									<th class="text-center">设置</th>
								</tr>
							</thead>
							<tbody>
							
							<c:forEach items="${shopList}" var="data">
								<tr>
									<td>
										<span class="j-shopname" data-id="${data.no }">${data.name}</span>
									</td>
									<td>
									<c:choose>
										<c:when test="${data.type == 1}">
										官网
										</c:when>
										<c:when test="${data.type == 2&&data.proxyType==1}">
										商城(内)
										</c:when>
										<c:when test="${data.type == 2&&data.proxyType==2}">
										商城(外)
										</c:when>
										<c:when test="${data.type == 2&&data.proxyType==0}">
										免费店铺
										</c:when>
									</c:choose>
									</td>
									<td>
									<fmt:formatDate value="${data.activationTime }" type="both" pattern="yyyy-MM-dd"/>
									 / 
									 <fmt:formatDate value="${data.effectiveTime}" type="both" pattern="yyyy-MM-dd"/>
									</td>
									<td>${data.wechat}</td>
									<td>
										<a href="javascript:;" class="btn" onclick="toAmend(this,'${data.no}')">修改</a> | 
										<a href="javascript:void(0)" onclick="intoShop('${data.no}');" class="btn">进入管理</a>
									</td>
								</tr>
							</c:forEach>
					
							</tbody>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!-- body end -->

	<!-- 添加店铺 -->
	<div id="j-addproduct" style="display:none;">
		<p style="margin-bottom:10px;">请输入店铺授权码：</p>
		<form class="form-horizontal" role="form" id="j-form-addshop">
		  <p><input type="text" class="form-control" id="shopcode" name="shopcode" placeholder=""></p>
		</form>
		<p style="margin-top:10px;">还没有？ 手机微信，关注“景正通信管家”公众号，在线购买</p>
		
	</div>
	<!-- 选中店铺 -->
	<div id="j-checkShop" style="display: none;">
		<div class="tab-pane active" id="proList">
			<div class="row" style="margin:10px 0;">
				<label  class="col-xs-2 control-label">店铺名称：</label >
				<div class="col-xs-5"><input class="form-control" type="text"> </div>
				<div class="col-xs-2"><button style="margin-left:20px;" type="submit" class="btn btn-default">搜索</button></div>
			</div>
			<ul class="add-list">
				
			</ul>
		</div>
	</div>
	
	<!-- 店铺修改 -->
	<div id="j-toAmend" style="display:none;">
		<form class="form-horizontal" role="form" id="j-form-toAmend" method="post">
		   <div class="form-group">
		   		<p class="col-sm-3 text-right" style="font-weight:bold;">公众号绑定</p>
		   </div>
		  <div class="form-group">
		    <label for="" class="col-sm-3 control-label">微信公众号</label>
		    <div class="col-sm-6">
		      <input type="text" class="form-control" id="wechat" name="wechat" placeholder="" value=""/>
		    </div>
		    <a href="javascript:;" onclick="unBundling(this);" class="btn btn-link aui_content" style="display:none;" id="unbind">公众号解绑</a>
		  </div>
		  <div class="form-group">
		    <label for="" class="col-sm-3 control-label">公众号密码</label>
		    <div class="col-sm-6">
		      <input type="password" class="form-control" id="passwd" name="passwd" placeholder=""/>
		    </div>
		  </div>
		  <div class="form-group">
		   		<p class="col-sm-5 text-right color-danger">请登录公众平台开发者中心获取</p>
		   </div>
		  <div class="form-group">
		    <label for="" class="col-sm-3 control-label">AppID(应用ID)</label>
		    <div class="col-sm-6">
		      <input type="text" class="form-control" placeholder="" id="appId" name="appId"/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="" class="col-sm-3 text-right">AppSecret<small class="help-block">(应用密钥)</small></label>
		    <div class="col-sm-6">
		      <input type="password" class="form-control" placeholder="" id="appSecret" name="appSecret"/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="" class="col-sm-3 text-right">是否认证<small class="help-block">(公众号是否在微信认证)</small></label>
		    <div class="col-sm-6">
		      <input type="checkbox" class="form-control" placeholder="" id="authenticate" name="authenticate" style="width: 35px;"/>
		    </div>
		  </div>
		</form>
	</div>
	<!-- 公众号解绑 -->
	<div id="j-unbundling" style="display:none;">
		<div class="aui_content" style="padding: 10px;">
			您是否真要解除绑定。
		</div>
	</div>
	<div id="j-shopname" style="display:none;">
		<form class="form-horizontal" role="form" id="j-form-shopname">
		  <div class="form-group">
		    <label for="username" class="col-sm-3 control-label">店铺名称：</label>
		    <div class="col-sm-6">
		      <input type="text" class="form-control" id="shopname" maxlength="30" name="name" placeholder="请输入店铺名称">
		    </div>
		  </div>
		</form>
	</div>

	<!-- footer -->
		<%@include file="../common/footer.jsp"%>

</body>

<script type="text/javascript">
//一键提取钱包余额提取
var balances = (function(){
	$('#j-quickextract').on('click',function(){
			art.dialog({
				lock : true,
				width : '500px',
				title : '子账户钱包余额提取',
				content:'将子账户钱包余额一键提取到主账户钱包，方便快捷！',
				background : '#000', // 背景色
				opacity : 0.1, // 透明度
				button : [
				     {
						name : '确定',
						callback : function(){
							//
						},
						focus: true
				     },
					 {
						 name:'取消'
					 }]
			});
		
	});
})();

//添加店铺
var addProduct = (function(){
	$('#addproduct').on('click',function(){
			art.dialog({
				id: 'addProduct',
				lock : true,
				width : '500px',
				title : '添加新店铺',
				content: document.getElementById("j-addproduct"),
				background : '#000', // 背景色
				opacity : 0.1, // 透明度
				button : [
				     {
						name : '确定',
						callback : function(){
							var bool = $("#j-form-addshop").validate({
								rules:{
									shopcode:{
										required:true
									}
								},
								messages:{
									shopcode:{
										required:"请输入店铺授权码"
									}
								}
							}).form();
							
							if(bool){
								$(".j-loading").show();
								$.post('/u/activate.htm',{"code":$("#shopcode").val()},function(data){
									$(".j-loading").hide();
									if(data.success){
										if('0'==data.info){
											location.reload()
										}else if('1'==data.info){
											$.dialog.alert('授权码已经使用过了！');
										}else if('-1'==data.info){
											$.dialog.alert('授权码已失效！');
										}
						    		}
								},'json')
							}else{
								return false;
							}
						},
						focus: true
				     },
					 {
						 name:'取消'
					 }]
			});
		
	});
})();

//店铺名称修改
var amendShopname = (function(){
	$('.j-shopname').on('click',function(){
		var thiz = this;
		var shopname = $(this).text();
		
		$('#shopname').val(shopname);
		var no=$(this).data('id');
		art.dialog({
			title:"修改店铺名称",
			content: document.getElementById("j-shopname"),
			width:"600px",
			lock: true,
					button : [
				     {
						name : '确定',
						callback : function(){
							var bool = $("#j-form-shopname").validate({
								rules:{
									shopname:{
										required:true,
										maxlength:10 
									}
								},
								messages:{
									shopname:{
										required:"店铺名称不能为空",
										maxlength:"店铺名称最多10个中文，20个英文"
									}
								}
							}).form();
							
							if(bool){
								
								$.post("/u/shop/editName.htm",{"no":no,"name":$("#shopname").val()},function(){
									location.reload();
								})

								return true;
							}else{
								return false;
							}
						},
						focus: true
				     },
					 {
						 name:'取消'
					 }]
		})
	})
})();
var dpEditDialog,
    objGzhName;
//修改店铺
function toAmend(el,no){
	var wechat = $(el).parent('td').prev('td').text();
	$("#wechat").val(wechat);
	$("#passwd").val('');
	$("#appId").val('');
	$("#appSecret").val('');
	$("#authenticate").attr("checked",false);
	
	if(wechat!=''&&wechat!=null&&wechat!='undefined'){
		$('#unbind').show();
		$('#shopNo').val(no);
	}else{
		$('#unbind').hide();
		$('#shopNo').val('');
	}
	objGzhName=el;
	 dpEditDialog=art.dialog({
		lock : true,
		width : '700px',
		title : '店铺修改',
		content: document.getElementById("j-toAmend"),
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		button : [
		     {
				name : '确定',
				callback : function(){
					var bool=$('#j-form-toAmend').validate({
						rules:{
							wechat:{
								required:true,
								maxlength:128
							},
							passwd:{
								required:true,
								maxlength:64
							},
							appId:{
								required:true,
								maxlength:64
							},
							appSecret:{
								required:true,
								maxlength:64
							}
							
						},
						messages:{
							wechat:{
								required:'微信公众号不能为空',
								maxlength:$.format("微信公众号不能超过{0}个字符")
							},
							passwd:{
								required:'公众号密码不能为空',
								maxlength:$.format("公众号密码不能超过{0}个字符")
							},
							appId:{
								required:'AppID不能为空',
								maxlength:$.format("AppID不能超过{0}个字符")
							},
							appSecret:{
								required:'AppSecret不能为空',
								maxlength:$.format("AppSecret不能超过{0}个字符")
							}
						}
					}).form();
					
					if(bool){
						$(".j-loading").show();
						var authenticate = $("#authenticate").attr("checked") ? 1 : 0;
						$.ajax({
							url:"${ctx }/u/bindshop${ext }",
							type:"post",
							data:{
								wechat : $("#wechat").val(),
								passwd : $("#passwd").val(),
								appId : $("#appId").val(),
								appSecret : $("#appSecret").val(),
								authenticate : authenticate,
								no:no
							},
							dataType: "text",
				          	contentType: "application/x-www-form-urlencoded; charset=utf-8",
				          	success: function (data) {
					        	  	var result = eval('('+ data +')');
									if(result.success){
										if(''!=$("#wechat").val()) {
											$(el).parent('td').prev('td').text($("#wechat").val());
										}
										$(".j-loading").hide();
										art.dialog.tips("保存成功！");
									}else{
										$(".j-loading").hide();
										alertMsg(result.info);
									}
					          },
					          error: function () {
					        	  alertMsg("服务器繁忙！");
					          }
						});
					}else{
						return false;
					}
				},
				focus: true
		     },
			 {
				 name:'取消'
			 }]
	});
}

//公众号解绑
function unBundling(el){
	art.dialog({
		lock : true,
		width : '400px',
		title : '公众号解绑',
		content: document.getElementById("j-unbundling"),
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		button : [
		     {
				name : '确定',
				callback : function(){
					$.ajax({
						url:"${ctx }/u/unbindshop${ext }",
						type:"post",
						data:{
							wechat : $("#wechat").val(),
							passwd : $("#passwd").val(),
							no:$('#shopNo').val()
						},
						dataType: "text",
			          	contentType: "application/x-www-form-urlencoded; charset=utf-8",
			          	success: function (data) {
				        	  	var result = eval('('+ data +')');
								if(result.success){
									$(".j-loading").hide();
									$('#unbind').hide();
									$('#shopNo').val('');
									art.dialog.tips("解绑成功！");
									dpEditDialog.hide();
									$(objGzhName).parent('td').prev('td').text('');
								}else{
									$(".j-loading").hide();
									alertMsg(result.info);
								}
				          },
				          error: function () {
				        	  alertMsg("服务器繁忙！");
				          }
					});
				},
				focus: true
		     },
			 {
				 name:'取消'
			 }]
	});
}


</script>
</html>