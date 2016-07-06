<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约管理</title>
<script type="text/javascript" src="${resourcePath }js/onoff.js"></script>
<script type="text/javascript">
$(function(){
	tab("appointment");
});
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
						<c:set var="crumbs" value="appointSet"/>
						<%@include file="../../common/crumbs.jsp"%>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">预约设置</a></li>
							</ul>
						</div>
						
						<div class="content tab-content">
							<div class="tab-pane fade in active" id="temp1">
								<ul class="pager">
									<li class="next">
										<a  class="btn btn-default " href="/u/appoint/appointment-type.htm">新增预约</a>
										<!-- <a  class="btn btn-default " href="appointment-newshop.html">新增商品类预约</a>
										<a  class="btn btn-default " href="appointment-newserve.html">新增服务类预约</a> -->
									</li>
								</ul>
								<form id="formPage">
								<table class="table table-bordered">
									<thead>
										<tr>
											<td>预约名称</td>
											<td width="21%">类型</td>
											<td width="24%">操作</td>
											<td width="10%">活动操作</td>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${pagination.datas }" var="appointment">
										<tr>
											<td>${appointment.name }</td>
											<td><c:if test="${appointment.type==1 }">商品类预约</c:if><c:if test="${appointment.type==2 }">服务类预约</c:if></td>
											<td>
												<c:if test="${appointment.type==1 }"><a href="/u/appoint/good-manage/${appointment.id }.htm" class="color-danger">商品管理</a> |</c:if>
												<a href="/u/appoint/edit.htm?id=${appointment.id }">修改</a> | 
												<a href="javascript:;" onclick="del(this,'${appointment.id}')">删除</a>
											</td>
											<td class="j-timestate" timestate='0'><div class="OC_box">
													<span>关闭</span>
													<div class="OC_box_bar" data-onoff="<c:if test='${appointment.status==0 }'>off</c:if><c:if test='${appointment.status==1 }'>on</c:if>">
														<h1 class="ico_btn hide" data-onoff-on=""></h1>
														<h3 class="ico_btn" data-onoff-off=""></h3>
														<h2 class="ico_btn" id="" data-onoff-handle="" style="left: 0px;" onclick="clickOnOff(this,'${appointment.id}');"></h2>
													</div>
													<span>开启</span>
												</div></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>

								<%@include file="../../common/page.jsp"%>
							</form>
							</div>
						</div>
						
						
						
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
<script type="text/javascript">
//删除
function del(el,id) {
	var thiz = el;
	art.dialog.confirm('确认删除？', function() {
		$.post("/u/appoint/delete.htm",{'id':id},function(){
			location.reload();
		})
	}, function() {
		return true;
	});
}	
function clickOnOff(el,id){
	if($(el).prev().hasClass("hide")){
		//设为关闭
		$.ajax({url:'${ctx}/u/appoint/switch${ext}',
				data:{'id':id,status:'0'},
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
		$.ajax({url:'${ctx}/u/appoint/switch${ext}',
				data:{'id':id,status:'1'},
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
//开关
	$("[data-onoff]").each(function(){
		var self = this,
			activeEl = $(self).parents('td').prev().find('[data-onoff]');
		/* $(this).onoff({
			on:function(){

				activeEl.show();
				return true;
			},
			off:function(){

				activeEl.hide();
				return true;
			}
		}) */
	});

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
</script>
</html>