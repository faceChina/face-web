<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>消息管理-自动回复设置</title>	
<!-- top -->
<%@include file="../../common/base.jsp" %>
<script type="text/javascript" src="${resourcePath }js/onoff.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("message");
	if('${toolSetting.attentionSwitch}' == 1){
		$('#attentionSwitch').css('left','26px');
		$('#attentionSwitch').prev().addClass("hide");
		$('#attentionSwitch').prev().prev().removeClass("hide");
	};

    if('${toolSetting.replySwitch}' == 1){
    	$('#replySwitch').css('left','26px');
    	$('#replySwitch').prev().addClass("hide");
    	$('#replySwitch').prev().prev().removeClass("hide")
    };

    if('${toolSetting.keywordRecoverySwitch}' == 1){
    	$('#keywordRecoverySwitch').css('left','26px');
    	$('#keywordRecoverySwitch').prev().addClass("hide");
    	$('#keywordRecoverySwitch').prev().prev().removeClass("hide")
    };
});
</script>
<script type="text/javascript">
function edit(type) {
	location.href = "${ctx}/u/weixin/edit${ext}?eventType="+type+"&recoveryMode="+3;
}
</script>
</head>
<body>
<!-- header -->
<%@include file="../../common/header.jsp" %>
<!-- header end -->

	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 " id="vvv">
				<!--nav-left -->
				<%@ include file="../../common/left.jsp" %>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
						<c:set var="crumbs" value="message"/>
						<%@include file="../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set">消息管理</a></li>
								</ul>
							</div>
							<div class="content">
								<table class="table">
									<tbody>
										<tr>
											<th width="30%">关注时回复</th>
											<td width="30%"><a href="javascript:edit(1);">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar" >
														<h1 class="ico_btn hide"></h1>
														<h3 class="ico_btn"></h3>
														<h2 class="ico_btn" id="attentionSwitch"  onclick="clickOnOff(this,1);" ></h2>
													</div>
													<span>开启</span>
													</div>

											</td>
										</tr>
										<tr>
											<th>消息时自动回复</th>
											<td><a href="javascript:edit(2);">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar" >
														<h1 class="ico_btn hide"></h1>
														<h3 class="ico_btn"></h3>
														<h2 class="ico_btn" id="replySwitch"  onclick="clickOnOff(this,2);" ></h2>
													</div>
													<span>开启</span>
													</div>
											</td>
										</tr>
										<tr>
											<th>关键词自动回复</th>
											<td><a href="javascript:edit(3);">编辑</a></td>
											<td>
												<div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" id="OC_box_bar" >
														<h1 class="ico_btn hide"></h1>
														<h3 class="ico_btn"></h3>
														<h2 class="ico_btn" id="keywordRecoverySwitch"  onclick="clickOnOff(this,3);" ></h2>
													</div>
													<span>开启</span>
													</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp" %>
	<!-- footer end -->
	
	<script type="text/javascript">
		//开关
// 		$("[data-onoff]").each(function(){
// 			var num = $(this).data('num');
// 			var flag = true;
// 			$(this).onoff({
// 				on:function(){
// // 					$.ajax({url:'${ctx}/u/weixin/changeStatus${ext}',
// // 						data:{type:num,status:1},
// // 						type:'post',
// // 						dataType:'text',
// // 						success:function(data){
// // 							var result = eval('(' + data + ')');
// // 							if(result.success) {
// // 								return true;
// // 							} else {
// // 								art.dialog.tips(result.info);
// // 								return false;
// // 							}
// // 						},
// // 						error:function(){
// // 							art.dialog.tips('开启失败');
// // 							return false;
// // 						}
// // 					});
// 					return flag;
// 				},
// 				off:function(){
// 					$.ajax({url:'${ctx}/u/weixin/changeStatus${ext}',
// 						data:{type:num,status:0},
// 						type:'post',
// 						dataType:'text',
// 						success:function(data){
// 							var result = eval('(' + data + ')');
// 							if(result.success) {
// 								return true;
// 							} else {
// 								art.dialog.tips(result.info);
// 								return false;
// 							}
// 						},
// 						error:function(){
// 							art.dialog.tips('关闭失败');
// 							return false;
// 						}
// 					});
// 					return flag;
// 				}
// 			});
// 		});
function clickOnOff(el,num){
	if($(el).prev().hasClass("hide")){
		//设为关闭
		$.ajax({url:'${ctx}/u/weixin/changeStatus${ext}',
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
		$.ajax({url:'${ctx}/u/weixin/changeStatus${ext}',
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