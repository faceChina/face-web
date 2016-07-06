<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">

</script>
<title>店铺管理-店铺简介</title>	
<!-- top -->
<%@ include file="../../common/base.jsp"%>
<link href="${resourcePath}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<!--top end -->
</head>
<script type="text/javascript">
	var success = ${Success};
	$(function(){
		tab("shop-about");
		if(1==success){
			art.dialog.alert("保存成功！");	
		}else if(0==success){
			art.dialog.alert("保存失败请，稍后再试！");	
		} 
	});
	
	UE.getEditor('editor');
	function getContent() {
	    return UE.getEditor('editor').getContent();
	}
	
	//发布验证
 	$(function(){
		$("#submit").click(function(event) {
			$("#content").val(getContent());			
		});
	}); 
</script>
<body>	
		<!-- header -->
		<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	
	<!-- body -->
	<div class="container">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="about"/>
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">店铺简介</a></li>
								</ul>
							</div>
							<form class="form-horizontal" id="j-form" action="${ctx}/u/shop/add${ext}" method="post">
							<div class="content">
								<div>
									<script id="editor" type="text/plain" style="height:400px;">${shop.shopContent}</script>
									<input type="hidden" id="content" name="shopContent" value=""/>
								</div>
								<div class="text-center about-btn">
									<button type="submit" class="btn btn-default" id="submit">发布</button>
									<!-- <button type="button" class="btn btn-default" id="jpreview">预览</button> -->
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
</html>

