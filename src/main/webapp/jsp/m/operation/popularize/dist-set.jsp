<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>代理商品推广管理</title>

</head>
<body>
	<!-- header -->
	<%@ include file="../../common/base.jsp"%>
	<%@ include file="../../common/header.jsp" %>
	<%@ include file="../../common/validate.jsp"%>
	<!-- header end -->

	<!-- body  -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
				<c:set var="crumbs" value="proxyGeneralize"></c:set>
				<%@include file="../../common/crumbs.jsp"%>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">代理商品推广管理</a></li>
							</ul>
						</div>
						<div class="content tab-content">

							<div class="alert alert-warning" role="alert">
								<b>分销商品推广管理：</b>
								<p>针对代理商品，您可以设置推广佣金，当有用户分享并且成交，第一级分享的人就可以获得佣金</p>
								<p>商品利润 = 商品零售价 - 供货价</p>
							 </div>
							<div class="tab-pane fade in active">
								<table class="table table-bordered">
									<thead>
										<tr>
											<td>类型</td>
											<td width="40%">佣金</td>
											<td width="24%">状态</td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>代理商品推广</td>
											<td id="tuiguang">
												<c:if test="${empty distributionPopularize}">
													商品利润 *  1%
												</c:if>
												<c:if test="${!empty distributionPopularize}">
													商品利润 * ${distributionPopularize.commissionRate }% 
												</c:if>
												<a href="javacript:"  onclick="chargesSets(this)"  class="color-danger" style="margin-left:20px;">修改</a>
											</td>
											<td class="j-timestate" timestate='0'>
												<div class="OC_box">
													<span>关闭</span>
													<c:choose>
														<c:when test="${distributionPopularize.status == 1}">
															<div class="OC_box_bar" data-onoff="on">
																<h1 class="ico_btn hide" data-onoff-on=""></h1>
																<h3 class="ico_btn" data-onoff-off=""></h3>
																<h2 class="ico_btn" id="" data-onoff-handle="" style="left: 26px;"></h2>
															</div>
														</c:when>
														<c:otherwise>
															<div class="OC_box_bar" data-onoff="off">
																<h1 class="ico_btn" data-onoff-on=""></h1>
																<h3 class="ico_btn hide" data-onoff-off=""></h3>
																<h2 class="ico_btn" id="" data-onoff-handle="" style="left: 0px;"></h2>
															</div>
														</c:otherwise>
													</c:choose>
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
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->

	<div id="j-distamend" style="display:none;">
	<form action="" class="form-horizontal" id="form-horizontal">
		<div class="form-group">
			<label class="col-md-3 control-label width110">商品利润 *</label>
			<div class="col-md-5">
				<input type="text" value="" name="commissionRate" id="inputEmail3" class="form-control">
			</div>
			<div class="col-md-1 control-left">%</div>
		</div>
		
	</form>
</div>


<script type="text/javascript" src="${resourcePath}js/onoff.js"></script>
<script type="text/javascript">

tab("dist-set");

$('[data-amend]').on('click',function(){

});

/* $(function(){
/*添加产品线*/
/* $('[data-amend]').on('click',function(){

	var form = $('#j-distamend form').validate({
					rules:{
						dist:{
							required:true
						}
					},
					messages:{
						dist:{
							required:"输入错误"
						}
					}
				});
	art.dialog({
		lock : true,
		width : '400px',
		title : "佣金设置",
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-distamend"),
		ok : function(){
			
			return form.form();
		},
		cancel : function(){
			form.resetForm();
			return true;
		}
	});
});

});  */

function chargesSets(){
    art.dialog({
        width:"500px",
        title:"佣金设置",
        content:document.getElementById("j-distamend"),
        ok:function(){
        	var boo = $("#form-horizontal").validate({
        		rules:{
        			commissionRate: {
        				required:true,
        				digits:true,
        				range:[1, 99]
        			}
        		},
        		messages:{
        			commissionRate:{
            			required:"请输入数据",
            			digits:"请输入1~99正整数",
            			range:"请输入1~99正整数"
        			}
        		}
        	}).form();
        	if (boo) {
				$.post("commissionRate/save.htm", {'commissionRate': $("#inputEmail3").val()}, function(jsonResult) {
					var result = JSON.parse(jsonResult);
					if (result.success) {
						var data = JSON.parse(result.info);
						$("#tuiguang").empty().html("商品利润*  "+data.commissionRate+"%"+"<a href='javacript:'  onclick='chargesSets(this)'  class='color-danger' style='margin-left:20px;''>修改</a>");
					} else {
						art.dialog(result.info);
					}
				});
			} else {
				return false;
			}
        },
        cancel:true
    })
}

	//开关
	$("[data-onoff]").each(function(){
		var self = this,
			activeEl = $(self).parents('td').prev().find('[data-onoff]');
		$(this).onoff({
			on:function(){
				$.post("status/save.htm", {'status':1}, function(jsonResult) {
					var result = JSON.parse(jsonResult);
					if (!result.success) {
						art.dialog(result.info);
					}
				});
				activeEl.show();
				return true;
			},
			off:function(){
				$.post("status/save.htm", {'status':2}, function(jsonResult) {
					var result = JSON.parse(jsonResult);
					if (!result.success) {
						art.dialog(result.info);
					}
				});
				activeEl.hide();
				return true;
			}
		})
	});
</script>

</body>
</html>