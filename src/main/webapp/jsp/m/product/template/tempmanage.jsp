<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>官网模板管理</title>	
<%@include file="../../common/base.jsp" %>
<script type="text/javascript">
$(function(){
	tab("template");

	var val=$(".templatelist").find(".active").find("input[type='hidden']").val();//选中的模版的传递值
	
	if(!$('.templatelist').find("li").hasClass("active")){
		$("#Jbtns").hide();
	}

	$("#current-template").click(function(){
		var url= '${ctx}/any/preview/${subCode}/template${ext}';
		var name='previewTemplate';   //网页名称，可为空
	    var iWidth=400;               //弹出窗口的宽度;
	    var iHeight=800;              //弹出窗口的高度;
	    previewToBigData(url,getJson('${owTemplateHp.code}'),name,iWidth,iHeight);
	});
	
	$('.templatelist').find("li").each(function(index) {
		$(this).find("div").first().click(function(event) {
			
			$('.templatelist li').removeClass("active");
			$(this).parent("li").addClass("active").find("div:first").append($("#Jbtns").show());
			val=$(".templatelist").find(".active").find("input[type='hidden']").val();
		});
	});
	//预览
// 	$("#Jbtns button:first").click(function(){
// 		var url= '${ctx}/any/preview/${subCode}/template${ext}';
// 		var name='previewTemplate';   //网页名称，可为空
// 	    var iWidth=400;               //弹出窗口的宽度;
// 	    var iHeight=800;              //弹出窗口的高度;
// 	    previewToBigData(url,getJson(val),name,iWidth,iHeight);
// 	});
	//保存
	$("#Jbtns button:last").click(function(){
		art.dialog.confirm('选择新的模板，会导致原来首页操作和内容丢失哦？', function () {
			$.post("${ctx}/u/template/switchOwTemplate${ext}",{"code":val},
					  function(falg){
				if(falg == 1 || falg == "1"){
					art.dialog.tips('保存成功！');
					$('#Jbtns').hide();
					location.reload();
				}else{
					art.dialog.tips('保存失败！');
					$('#Jbtns').hide();
				}
			});
		}, function () {
		    art.dialog.tips('取消保存');
		});
	});
});

/**
 *获取提交数据
 */
function getJson(val) {
	var obj = {'owTemplateCode' : val, 'previewType' : 'template'};
	var jsondata = JSON.stringify(obj);
	return jsondata;
}
</script>
</head>
<body>
<!-- header -->
<%@include file="../../common/header.jsp"%>
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
 				<div class="row"> 
					<c:set var="crumbs" value="gwtemplate"/>
					<%@include file="../../common/crumbs.jsp"%>
				</div> 

				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li  class="active"><a >热门模板</a></li>
								<c:if test="false">
								<li><a >行业模板</a></li>
								<li><a >更多</a></li> 
								</c:if>
<!-- 								<li class="pull-right" style="margin-right:10px;"><button id="current-template" type="button" class="btn btn-default">查看当前模板</button></li> -->
							</ul>
						</div>
						<div class="content tab-content">
						
							<div id="Jbtns" style="margin-top:-80px;display:none;">
<!-- 								<button type="button" class="btn btn-default ">预览</button> -->
								<button type="button" class="btn btn-default btn-danger">保存</button>	
							</div>
								
							<div class="tab-pane fade in active panel-body" id="temp1" >
								<ul class="row templatelist">
									<c:forEach items="${pagination.datas }" var="data">
										<c:if test="${data.isHidden != 1 }">
											<li data-template="1" <c:if test="${data.code == owTemplateHp.code }"> class="active"</c:if>>
												<div>
													<span></span>
													<img src="${data.path }" alt="${data.name }">
												</div>
												<div>
													<input type="hidden" name="temp1" value="${data.code}" data-id="1"> ${data.name}
												</div>
											</li>
										</c:if>
									</c:forEach>
								</ul>
								
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

</body>
</html>