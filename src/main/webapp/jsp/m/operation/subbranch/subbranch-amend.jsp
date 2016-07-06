<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>分公司管理</title>	
<!-- top -->
<!--top end -->
<%@ include file="../../common/base.jsp"%>
<%@ include file="../../common/header.jsp" %>
<script type="text/javascript">
$(function(){
	tab("subbranch");
    $('.j-tooltip a').tooltip({
	     placement:"right"
	 })
});
</script>
<!-- header -->
<!-- header end -->
</head>
<body>

<!-- body -->
<div class="container" id="j-content">
	<div class="row">

		<div class="col-md-2">
			<!--nav-left -->
			<%@include file="../../common/left.jsp"%>
			<!--nav-left end-->
		</div>

		<div class="col-md-10">
				<div class="row">
					<%@include file="../../common/crumbs.jsp"%>
				</div>

				<div class="row">
					<div class="box box-auto">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#members-set" data-toggle="tab">基本信息</a></li>
							</ul>
						</div>
						<div class="content">
							<div class="form-horizontal">
								<div class="form-group">
										<ul class="list-unstyled list-unstyled-line">
	 										<li class="j-balance"><b>姓名：</b>${subbranch.userName }</li>
	 										<li><b>联系方式：</b>${subbranch.userBindingCell }</li>
	 										<li><b>店铺名称：</b>${subbranchName }
	 											
	 										</li>
	 									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">权限级别设置</a></li>
							</ul>
						</div>
						<form action="${ctx}/u/subbranch/set${ext}" method="post" id="jform" class="form-horizontal">
						<div class="content tab-content">
							<div class="tab-pane fade in active panel-body" id="temp1">
								<div class="form-group">
							        <b class="col-md-1 control-label width70">佣金设置:</b>
							        <label class="col-md-1 control-label width70">商城价*</label>
							        <div class="col-md-3">
							          <input type="text" name="profit" value="${subbranch.profit }" class="form-control">
							        </div>
							         <div class="col-md-1 control-label width14">%</div>
							        <div class="col-md-6 control-left j-tooltip">
							        	<a href="#"  data-toggle="tooltip" title="" data-original-title="1、佣金指别人成为您的分店，卖出商品后所能获得的利润。2、目前设置针对所有商品"></a>
							        </div>
							      </div>
							      <input type="hidden" name="id" value="${id }">
							      <div class="form-group">
							        <b class="col-md-2 control-label width70">发货权限:</b>
							        <label class="col-md-1 control-label">
							        	<input type="radio" name="deliver" value="0" <c:if test="${subbranch.deliver == 0 }">checked</c:if>>
							        	无
							        </label>
							        <label class="col-md-1 control-label">
							        	<input type="radio" name="deliver" value="1" <c:if test="${subbranch.deliver == 1 }">checked</c:if>>
							        	有
							        </label>
							         <div class="col-md-6 control-left j-tooltip">
							        	<a href="#"  data-toggle="tooltip" title="" data-original-title="您可以将自己的发货权限（发货，关闭订单，删除订单）授权给下级分店，与此同时，自己不再具有发货权限。"></a>
							        </div>
							      </div>
							<div class="text-center">
									<button type="button" id="cancelButton" class="btn btn-default btn-lg">取消</button>
									<button type="button" id="save" class="btn btn-default btn-lg">保存</button>
								</div>
							</div>
						</div>
					</form>
					</div>
				</div>
		</div>
	</div>
</div>
<!-- body end -->
<!-- footer -->
<%@include file="../../common/footer.jsp"%>
<!-- footer end -->

</body>
<script type="text/javascript">
$($("#save").click(function() {
	var boo = $("#jform").validate({
		rules:{
			profit:{
				required:true,
				range:[0,100] 
			}
		}, 
		messages:{
			profit:{
				required:"请输入佣金比例",
				range:"佣金比例应在0%到100%之间"
			}
		} 
	}).form();
	
	if (boo) {
		$("#jform").submit();
	}
}));

$("#cancelButton").click(function() {
	window.location.href = "${ctx}/u/subbranch/list${ext}";
});

</script>
</html>