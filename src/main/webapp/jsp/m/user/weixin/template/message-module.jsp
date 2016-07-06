<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>消息管理-自动回复设置</title>	
<!-- top -->
<%@ include file="../../../common/base.jsp" %>
<%@ include file="../../../common/validate.jsp" %>
<!--top end -->
<script type="text/javascript">
	$(function(){
		tab("message-module");
		if('${templateMessageSetting.memberCardSwitch}' == 1){
			$('#memberCardSwitch').css('left','26px');
			$('#memberCardSwitch').prev().addClass("hide");
			$('#memberCardSwitch').prev().prev().removeClass("hide");
		};

	    if('${templateMessageSetting.orderStatusSwitch}' == 1){
	    	$('#orderStatusSwitch').css('left','26px');
	    	$('#orderStatusSwitch').prev().addClass("hide");
	    	$('#orderStatusSwitch').prev().prev().removeClass("hide");
	    };

	    if('${templateMessageSetting.commissionSwitch}' == 1){
	    	$('#commissionSwitch').css('left','26px');
	    	$('#commissionSwitch').prev().addClass("hide");
	    	$('#commissionSwitch').prev().prev().removeClass("hide");
	    };
	});
</script>
</head>
<body>
<!-- header -->
<%@include file="../../../common/header.jsp"%>
<!-- header end -->

<!-- body -->
<div class="container" id="j-content">
	<div class="row">
		<div class="col-md-2 " id="vvv">
			<!--nav-left -->
			<%@ include file="../../../common/left.jsp" %>
			<!--nav-left end-->
		</div>
		<div class="col-md-10">
			<c:set var="crumbs" value="templatemessage"/>
			<%@include file="../../../common/crumbs.jsp"%>
			<div class="row">
				<div class="box">
					<div class="title">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#members-set">模板消息设置</a></li>
						</ul>
					</div>
					<div class="content">
						<!-- 提示信息 -->
						<div class="alert alert-warning clearfix" style="text-align:left">
							<div class="pull-left">
								<p><strong>模板消息：</strong></p>
								<p>1、模板消息用于公众号向用户发送重要服务通知，如佣金提醒，订单状态更新、会员卡领取通知等；</p>
								<p>2、您的店铺必须绑定已认证的微信服务号，才能成功开启模板消息功能。</p>
							</div>
						</div>
						
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>模板消息名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>会员卡领取通知</td>
									<td>
										<div class="OC_box">
											<span>关闭</span>
											<div class="OC_box_bar" id="OC_box_bar">
												<h1 class="ico_btn hide"></h1>
												<h3 class="ico_btn"></h3>
												<h2 class="ico_btn" id="memberCardSwitch" onclick="clickOnOff(this,1);" ></h2>
											</div>
											<span>开启</span>
										</div>
									</td>
								</tr>
								<tr>
									<td>订单状态更新</td>
									<td>
										<div class="OC_box">
											<span>关闭</span>
											<div class="OC_box_bar" id="OC_box_bar">
												<h1 class="ico_btn hide"></h1>
												<h3 class="ico_btn"></h3>
												<h2 class="ico_btn" id="orderStatusSwitch" onclick="clickOnOff(this,2);"></h2>
											</div>
											<span>开启</span>
										</div>
									</td>
								</tr>
								<tr>
									<td>佣金提醒</td>
									<td>
										<div class="OC_box">
											<span>关闭</span>
											<div class="OC_box_bar" id="OC_box_bar">
												<h1 class="ico_btn hide"></h1>
												<h3 class="ico_btn"></h3>
												<h2 class="ico_btn" id="commissionSwitch" onclick="clickOnOff(this,3);"></h2>
											</div>
											<span>开启</span>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						
						
						
					</div><!-- content end -->
				</div><!-- box end -->
			</div><!-- row end -->
		</div>
	</div>
</div>
<!-- body end -->

<!-- footer -->
<%@ include file="../../../common/footer.jsp" %>
<!-- footer end -->

<script type="text/javascript">
	//您当前的店铺没有绑定微信服务号，不能开启模板消息功能
	function noBindWX(){
		art.dialog.alert("您当前的店铺没有绑定微信服务号，不能开启模板消息功能。")
	}
	
	//您当前的店铺绑定的服务号未认证，不能开启模板消息功能
	function noIdentifyServe(){
		art.dialog.alert("您当前的店铺绑定的服务号未认证，不能开启模板消息功能。")
	}
	
	/*开关*/
	function clickOnOff(el,num){
		if($(el).prev().hasClass("hide")){
			//设为关闭
			$.ajax({url:'${ctx}/u/weixin/templateMessage/changeStatus${ext}',
					data:{type:num,status:'0'},
					type:'post',
					dataType:'text',
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success) {
							$(el).animate({
								left: "0"
							}, 200,function(){
								$(el).prev().removeClass("hide");
								$(el).prev().prev().addClass("hide");
							});
						} else {
							art.dialog.tips(result.info);
						}
					},
					error:function(){
						art.dialog.alert('系统繁忙');
					}
				});
			
		}else{
			//设为开启
			$.ajax({url:'${ctx}/u/weixin/templateMessage/changeStatus${ext}',
					data:{type:num,status:'1'},
					type:'post',
					dataType:'text',
					success:function(data){
						var result = eval('(' + data + ')');
						if(result.success) {
							$(el).animate({
								left: "26px"
							}, 200,function(){
								$(el).prev().addClass("hide");
								$(el).prev().prev().removeClass("hide");
							});
						} else {
							art.dialog.tips(result.info);
						}
					},
					error:function(){
						art.dialog.alert('系统繁忙');
					}
				});
			
		}
	}
</script>
</body>
</html>