<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="../businesscard/share-card.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>公司介绍-编辑</title>
<link rel="stylesheet" type="text/css" href="${resourcePath }/operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }/operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }/operation/bestface/fonts/iconfont.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }/operation/bestface/css/freeshop.css">
<script type="text/javascript" src="${resourceBasePath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/validate/additional-methods.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/validate/additional-methods-new.js"></script>
<script type="text/javascript" src="${resourceBasePath }/js/validate/messages_zh.js"></script>

<script>
$(function(){	
var str = [
			'<div class="m-shoppoto-upload" data-attr="true">',
				'<div class="m-shop-poto" data-upload="true">',
					'<span class="m-pic-icon"></span>',
					'<input class="m-pic-file" type="file" data-upload="up" accept="image/*" capture="camera">',
					'<span class="shoppoto-btn">',
						'<span class="shoppoto-btn-item btn-blue" data-attr="move-up">上移</span>',
						'<span class="shoppoto-btn-item btn-blue" data-attr="del">删除</span>',
						'<span class="shoppoto-btn-item">上传图片</span>',
					'</span>',
				'</div>',
				'<div class="m-shop-txt">',
					'<textarea placeholder="请输入段落文字" name="" rows="6"></textarea>',
				'</div>',
			'</div>'
		].join('');
//添加段落
addAttr.setHtml(str);

var selectLink = (function(){

	$('[data-url="true"]').on('change',function(){
		window.location= this.value;
	});
})();

});
	
</script>
</head>
<body>
<form action="${ctx }/wap/${shopNo}/any/itopic/company/saveOrEdit/${id}${ext }" id="j-form" method="post">
	<input type="hidden" name="type" value="1" /><!-- 类型-->
	<input type="hidden" name="validateToken" value="${validateToken }" /><!-- 类型-->
	<input type="hidden" name="id" value="${dto.id }"/>
	<div class="m-shop-info m-shop-info-new">
		<div class="m-shop-item">
			<em class="shop-title">类型</em>
			<span class="shop-txt">
				<span class="shop-txt" position="true">
					<select name="" id="" data-url="true" style="padding-right:5em;">
						<option value="${ctx }/wap/${shopNo}/any/itopic/company/select/${id}${ext }?type=2">图文</option>
						<option selected="true" value="${ctx }/wap/${shopNo}/any/itopic/company/select/${id}${ext }?type=1" >链接</option>
					</select>
				</span>
				<i class="iconfont icon-right company-icon-right"></i>
			</span>
		</div>
<%-- 		<div class="m-shop-item" border-none="true">
			<em class="shop-title">栏目</em>
			<span class="shop-txt">
				<input type="text" placeholder="我的公司 " name="shopname" value="<c:if test="${empty dto.name }">我的公司 </c:if>${dto.name }" disabled/>
			</span>
		</div> --%>
	</div>

	<div class="m-shop-info">
		<div class="m-shop-item" border-none="true">
			<em class="m-shop-title">链接地址</em>
		</div>
		<div class="m-shop-txt">
			<textarea name="link" rows="2" >${dto.link }</textarea>	
		</div>
		
	</div>

	<div class="m-shop-sub">
		<button class="btn btn-danger" type="sumbit" id="j-submit">完成</button>
	</div>


</form>

<script type="text/javascript" src="${resourcePath }/operation/bestface/js/newshop.js"></script>
<script>
$('#j-submit').click(function(){
	var flag = $('#j-form').validate({
					rules:{
						shopname:{
							required:true
						},
						link:{
							required:true,
							url:true
						}
					},
					messages:{
						shopname:{
							required:'请输入栏目'
						},
						link:{
							required:'请输入链接',
								url:'请输入正确的网址'
						}
						
					},
					errorPlacement: function(error, element) { 
						//console.log(error)
						error.insertAfter(element);
					}
				}).form();
})
	


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
}


</script>

</body>
</html>