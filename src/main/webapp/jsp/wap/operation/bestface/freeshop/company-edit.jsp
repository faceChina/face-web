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
	
var selectLink = (function(){

	$('[data-url="true"]').on('change',function(){
		window.location= this.value;
	});
})();

var index = '${count}' == '' ? 0 :parseInt('${count}');
var str = [
			'<div class="m-shoppoto-upload" data-attr="true">',
			'<input type="hidden" data-sort="true" name="details[{{sort}}].sort" value="{{sort}}">',
			'<input type="hidden" name="details[{{sort}}].id">',
				'<div class="m-shop-poto" data-upload="true">',
					'<span class="m-pic-icon light-clr">',
						'<i class="iconfont icon-roundadd"></i>',
						'<span>点击上传图片</span>',
					'</span>',
					'<input class="m-pic-file" type="file" name="iTopicFile[{{sort}}]" data-upload="up" accept="image/*" capture="camera">',
					'<span class="shoppoto-btn">',
						'<span class="shoppoto-btn-item btn-blue" data-attr="move-up">上移</span>',
						'<span class="shoppoto-btn-item btn-blue" data-attr="del">删除</span>',
					'</span>',
				'</div>',
				'<div class="m-shop-txt">',
					'<textarea validate="{maxlength:256}" placeholder="请输入段落文字" name="details[{{sort}}].content" rows="6"></textarea>',
				'</div>',
			'</div>'
		].join('');
//添加段落
addAttr.setHtml(str);
addAttr.setSort(index);

});
	
</script>
</head>
<body>
<form action="${ctx }/wap/${shopNo}/any/itopic/company/saveOrEdit/${id}${ext }" id="j-form" method="post"  enctype="multipart/form-data">
	<input type="hidden" name="id" value="${dto.id }"/>
	<input type="hidden" name="type" value="2" />
	<input type="hidden" name="validateToken" value="${validateToken }" /><!-- 类型-->
	<div class="m-shop-info m-shop-info-new">
		<div class="m-shop-item">
			<em class="shop-title">类型</em>
			<span class="shop-txt">
				<span class="shop-txt" position="true">
					<select name="" id="" data-url="true" style="padding-right:5em;">
						<option selected="true" value="${ctx }/wap/${shopNo}/any/itopic/company/select/${id}${ext }?type=2">图文</option>
						<option value="${ctx }/wap/${shopNo}/any/itopic/company/select/${id}${ext }?type=1">链接</option>
					</select>
				</span>
				<i class="iconfont icon-right company-icon-right"></i>
			</span>
		</div>
<%-- 		<div class="m-shop-item" border-none="true">
			<em class="shop-title">栏目</em>
			<span class="shop-txt">
				<input type="text" placeholder="店铺名称" name="shopname" value="<c:if test="${empty dto.name }">我的公司 </c:if>${dto.name }" disabled="disabled"></input>
			</span>
		</div> --%>
	</div>

	<div class="m-shop-info" id="j-upload">
		<div class="m-shop-item" border-none="true">
			<em class="m-shop-title">详情内容</em>
		</div>
		<c:forEach items="${dto.details }" var="detail" varStatus="status">
			<div class="m-shoppoto-upload" data-attr="true">
				<input type="hidden" data-sort="true" name="details[${status.index }].sort" value="${status.index }">
				<input type="hidden" name="details[${status.index }].id" value="${detail.id }">
				<div class="m-shop-poto" data-upload="true">
					<span class="m-pic-icon" data-img="true">
						<c:if test="${not empty detail.picturePath }">
							<img src="${picUrl }${detail.picturePath }"/>
						</c:if>
					</span>
					<span class="m-pic-icon light-clr">
						<i class="iconfont icon-roundadd"><span>点击上传图片</span></i></span>
					<input class="m-pic-file" type="file" name="iTopicFile[${status.index }]" data-upload="up" accept="image/*" capture="camera">
					<span class="shoppoto-btn">
						<span class="shoppoto-btn-item btn-blue" data-attr="move-up">上移</span>
						<span class="shoppoto-btn-item btn-blue" data-attr="del">删除</span>
						
						<!-- <span class="shoppoto-btn-item">上传图片</span> -->
					</span>
				</div>
				<div class="m-shop-txt">
					<textarea validate="{maxlength:256}" placeholder="请输入段落文字" name="details[${status.index }].content" rows="6">${detail.content }</textarea>	
				</div>
			</div>
		</c:forEach>
		<div class="m-shoppoto-add" data-attr="add">
			<span class="btn-blue">添加新段落</span>
		</div>
	</div>
	<div class="m-shop-sub">
		<button class="btn btn-danger" type="button" id="j-submit">完成</button>
	</div>
</form>

<script type="text/javascript" src="${resourcePath }/operation/bestface/js/company.js"></script>
<script>
$.metadata.setType("attr", "validate"); 
$('#j-submit').click(function(){
	var flag = $('#j-form').validate({
		errorPlacement: function(error, element) { 			
		},
		invalidHandler: function(event, validator) {
		    // 'this' refers to the form
		    var errors = validator.numberOfInvalids();

		    if (errors) {
		       alertFun(validator.errorList[0].message);
		       $(validator.errorList[0].element).focus();
		    }
	  }
	}).form();
	if(!flag){
		return;
	}
	/* var name = $("input[name='name']").val();
	if(!name){
		alertFun("请填写栏目信息");
		return;
	} */
	var contentFlag = false;
	$("body").find("textarea").each(function(index){
		if(!$(this).val()){
			contentFlag = true;
		}
	});
	var imgFlag = false;
	$("body").find("[data-img='true']").each(function(index){
		if($(this).find('img').length == 0){
			imgFlag = true;
		}
	});
	if(contentFlag && imgFlag){
		alertFun("请上传图片或者填写内容");
		return;
	}
	
	$('#j-form').submit(); 
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