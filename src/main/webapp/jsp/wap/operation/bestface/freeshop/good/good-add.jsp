<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp" %>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>发布商品</title>
<%@include file="../top.jsp" %>
<script>
$(function(){
	var str = [
	'<div class="m-add-attr" data-attr="true">',
		'<input type="hidden" name="goodItemId"/>',
		'<div class="m-add-del" data-attr="del"></div>',
		'<div class="m-shop-item">',
			'<em class="shop-title">属性名</em>',
			'<span class="shop-txt"><input id="attrname-1" type="text" name="attributeName" placeholder="请输入属性" maxlength="64"/></span>',
		'</div>',
		'<div class="m-shop-item">',
			'<em class="shop-title">价格</em>',
			'<span class="shop-txt"><input id="price-1" type="text" name="price" placeholder="请输入价格" /></span>',
		'</div>',
		'<div class="m-shop-item">',
			'<em class="shop-title">库存</em>',
			'<span class="shop-txt"><input id="inventory-1" type="text" name="stock" placeholder="请输入库存" /></span>',
		'</div>',
	'</div>'
].join('');
addAttr.setId('.m-shop-info');
addAttr.setHtml(str);	
});

</script>
</head>
<body>
<form action="${ctx}/free/good/saveGood${ext}" id="j-form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="freeGoodSku" id="freeGoodSku"/>
	<input type="hidden" name="marketPrice" id="marketPrice"/>
	<input type="hidden" name="validateToken" value="${validateToken }">
	<div class="m-pic-upload" id="j-upload">   
		<div class="m-pic-item" data-upload="true">
			<span class="m-pic-icon"></span>
  			<input type="hidden" target="name" name="goodImgs['0'].position" value="0">
  			<input type="hidden" target="path" name="goodImgs['0'].url">
			<input class="m-pic-file" type="file" name="goodFile[0]" data-upload="up" accept="image/*" capture="camera">
			<span class="m-pic-del" data-upload="del">删除</span>	
		</div>

		<div class="m-pic-item" data-upload="true">
			<span class="m-pic-icon"></span>
			<input type="hidden" target="name" name="goodImgs['1'].position" value="1">
  			<input type="hidden" target="path" name="goodImgs['1'].url">
			<input class="m-pic-file" type="file" name="goodFile[1]" data-upload="up" accept="image/*" capture="camera">
			<span class="m-pic-del" data-upload="del">删除</span>
		</div>

		<div class="m-pic-item" data-upload="true">
			<span class="m-pic-icon"></span>
			<input type="hidden" target="name" name="goodImgs['2'].position" value="2">
  			<input type="hidden" target="path" name="goodImgs['2'].url">
			<input class="m-pic-file" type="file" name="goodFile[2]" data-upload="up" accept="image/*" capture="camera">
			<span class="m-pic-del" data-upload="del">删除</span>
		</div>

		<div class="m-pic-item" data-upload="true">
			<span class="m-pic-icon"></span>
			<input type="hidden" target="name" name="goodImgs['3'].position" value="3">
  			<input type="hidden" target="path" name="goodImgs['3'].url">
			<input class="m-pic-file" type="file" name="goodFile[3]" data-upload="up" accept="image/*" capture="camera">
			<span class="m-pic-del" data-upload="del">删除</span>
		</div>

		<div class="m-pic-item" data-upload="true">
			<span class="m-pic-icon"></span>
			<input type="hidden" target="name" name="goodImgs['4'].position" value="4">
  			<input type="hidden" target="path" name="goodImgs['4'].url">
			<input class="m-pic-file" type="file" name="goodFile[4]" data-upload="up" accept="image/*" capture="camera">
			<span class="m-pic-del" data-upload="del">删除</span>
		</div>
	</div>
	
	<div class="m-shop-info">
		<div class="m-shop-item">
			<em class="shop-title">标题</em>
			<span class="shop-txt"><input type="text" placeholder="请输入标题" maxlength="85" name="name" id="name"/></span>
		</div>
		<div class="m-shop-item">
			<em class="shop-title">市场价</em>
			<span class="shop-txt">
				<input type="text" placeholder="请输入市场价 " name="market" id="market" />
			</span>
		</div>
		<div class="m-add-attr">
			<div class="m-shop-item">
				<em class="shop-title">属性名</em>
				<span class="shop-txt">
					<input type="text" name="attributeName" placeholder="请输入属性" maxlength="64" />
				</span>
			</div>
			<div class="m-shop-item">
				<em class="shop-title">价格</em>
				<span class="shop-txt">
					<input type="text" name="price" placeholder="请输入价格" />
				</span>
			</div>
			<div class="m-shop-item">
				<em class="shop-title">库存</em>
				<span class="shop-txt">
					<input type="text" name="stock" placeholder="请输入库存" />
				</span>
			</div>
		</div>

		<div class="m-add-btn" data-attr="add">
			<em class="add-btn-icon"></em>
			<span class="add-btn-txt">添加属性</span>
		</div>

<!-- 	</div> -->
		<c:if test="${not empty shopTypeList }">
			<div class="m-shop-item" border-none="true">
				<em class="shop-title">所属分类</em>
				<span class="shop-txt" position="true">
					<i class="shop-icon triangle-icon"></i>
					<select name="shopTypeId" id="shopTypeId">
						<c:forEach items="${shopTypeList }" var="data">
							<option value="${data.id }">${data.name}</option>
						</c:forEach>
					</select>
				</span>
			</div>
		</c:if>
		
		<div class="m-shop-item" border-none="true">
			<em class="shop-title">统一运费</em>
			<span class="shop-txt">
				<input type="text" name="postFeeYuan" placeholder="请填写运费" />
			</span>
		</div>
		
		<div class="m-shop-info">
			<div class="m-shop-item" border-none="true">
				<em class="m-shop-title">商品详情</em>
			</div>
			<div class="m-shop-txt">
				<textarea name="goodContent" rows="8"></textarea>	
			</div>
		</div>
	</div>

	<div class="m-shop-sub">
		<button class="btn btn-danger" type="button" onclick="save()" id="j-submit">完成</button>
	</div>


</form>

<script type="text/javascript" src="${resourcePath }operation/bestface/js/newshop.js"></script>
<script>
// $('#j-submit').click(function(){
	var mValidate = $('#j-form').validate({
				rules:{
					name:{
						required:true,
						maxlength:85
					},
					market:{
						required:true,
						moneyBase: true
					},
					attributeName:{
						required:true,
						maxlength:64
					},
					price:{
						required:true,
						moneyBase: true
					},
					postFeeYuan:{
						required:true,
						moneyBase: true
					},
					stock:{
						required:true,
						positiveInteger:true
					}

				},
				messages:{
					name:{
						required:'请输入标题',
						maxlength:'标题不能超过{0}个字符'
					},
					market:{
						required:'请输入市场价',
						moneyBase: '金额必须大于等于0.00，保留两位小数'
					},
					attributeName: {
						required: '请输入属性',
						maxlength:'属性不能超过{0}个字符'
					},
					price:{
						required:'请输入价格',
						moneyBase: '金额必须大于等于0.00，保留两位小数'
					},
					postFeeYuan:{
						required:'请输入运费',
						moneyBase: '金额必须大于等于0.00，保留两位小数'
					},
					stock:{
						required:'请输入库存',
						positiveInteger:'请输入正确的库存'
					},
					
				},
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
			})
// })
	
function save(){
		var flag = $('#j-form').validate().form();
		var index = 0;
		$("#j-upload").find("img").each(function(){
			index++;
		});
		if(index == 0) {
			alertFun("请上传图片");
			return;
		}
		if(flag) {
			$("#freeGoodSku").val(getGoodItem());
			$("#marketPrice").val(parseInt($("#market").val()*100));
			$("#j-form").submit();
		}
}

function getGoodItem() {
	var getArr = [];
	$(".m-shop-info").find('.m-add-attr').each(function(){
		var baseData = {};
		var price = $(this).find("input[name=price]").val();
		baseData["id"] = $(this).find("input[name=id]").val();
		baseData["name"] = $("#name").val();
		baseData["attributeName"] = $(this).find("input[name=attributeName]").val();
		baseData["salesPrice"] = parseInt(price*100);
		baseData["stock"] = $(this).find("input[name=stock]").val();
		getArr.push(baseData);
	});
	return JSON.stringify(getArr);
}
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