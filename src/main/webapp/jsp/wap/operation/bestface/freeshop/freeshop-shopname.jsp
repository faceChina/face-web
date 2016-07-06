<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我要开店-链接店铺</title>
<%@include file="top.jsp" %>
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="${ctx}/free/openShop${ext}" id="jform" method="post">
			<div class="info">
				<div class="form-group">
				    <label for="" class="col-xs-3 control-label">店铺名称</label>
				    <div class="col-xs-9">
				      <input type="text" class="form-control" placeholder="请填写店铺名称" style="border:0;" id="name" name="name"  value="">
				    </div>
				</div>
			</div>
			<button type="submit" class="btn btn-danger">开启店铺</button>
		</form>
	</div>
</div>
<script type="text/javascript">
	$('#jform button').click(function(){
		$('#jform').validate({
			rules:{
				name:{
					required:true
				}
			},
			messages:{
				name:{
					required:'请输入店铺名称'
				}
			},
			errorPlacement: function(error, element) { 	
				error.insertAfter(element);
			}
		});
});
</script>
</body>
</html>