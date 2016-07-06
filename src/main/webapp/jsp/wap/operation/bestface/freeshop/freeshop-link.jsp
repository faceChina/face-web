<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="../businesscard/share-card.jsp"%>
<%@include file="top.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />

<title>我要开店-链接店铺</title>
</head>
<body>
<div class="container">
	<div class="m-set">
		<form action="${ctx }/wap/${shopNo}/any/itopic/freeshop-link/${id}${ext}" id="jform" method="post">
			<input type="hidden" name="id" value="${iTopicDto.id }"/>
			<input type="hidden" name="type" value="1" /><!-- 类型-->
			<div class="info">
				<div class="form-group">
				    <label for="" class="col-xs-3 control-label">栏目名称</label>
				    <div class="col-xs-9">
				      <input type="text" class="form-control" placeholder="我的店铺" style="border:0;" id="username" name="username"  value="" disabled>
				    </div>
				</div>
			</div>
			<div class="info">
				<div class="form-group">
				    <label for="">链接地址(只能上传以${url }打头的链接)</label>
				    <textarea class="form-control" rows="3" placeholder="${url }" id="link" name="link">${iTopicDto.link }</textarea>
				</div>
			</div>
			<p style="color:#666;padding:10px;font-size:14px;">将所需要的店铺地址黏贴至此处</p>
			<button type="submit" class="btn btn-danger">保存</button>
		</form>
	</div>
</div>
<script type="text/javascript">
console.log()
	$('#jform button').click(function(){
		
		$('#jform').validate({
			rules:{
				username:{
					required:true
				},
				link:{
					required:true,
					weixinUrl:"${url }"
				}
			},
			messages:{
				username:{
					required:'请输入店铺名称'
				},
				link:{
					required:'请输入链接地址',
					weixinUrl:'请输入有效的链接地址'
				}
			},
			errorPlacement: function(error, element) { 	
				error.insertAfter(element);
			}
		});
});
	


//验证提示
function alertFun(txt){
	var str = '<div class="m-alert Bounce"><span class="m-alert-info">错误提示！</span></div>';
	$('body').append(str);
	var alertEl = $('.m-alert');
	if(alertEl.is(':hidden')){
		alertEl.find('.m-alert-info').html(txt)
		alertEl.show();
		setTimeout(function(){
			alertEl.hide();
		},1000);
	}

	return false;
}
</script>
</body>
</html>