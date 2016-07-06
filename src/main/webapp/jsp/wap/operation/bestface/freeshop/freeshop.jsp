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
<title>我要开店</title>
</head>
<body>
<div class="container">
	<div class="m-set">
		<div class="info">
			<ul class="m-list" id="j-openshop">
				<li data-type="1">
					<!-- <a href="javascript:;"> -->
					<a>
						<i class="iconfont icon-roundcheckfill" <c:if test="${empty icard.type || 1==icard.type}">data-check="true"</c:if> ></i>
						<span class="frees-info" data-type="shopname">免费版店铺</span>
						<c:if test="${2>icard.status}"><button type="button" class="btn btn-danger pull-right btn-open" onclick="location.href='${ctx}/free/addFreeShopPage.htm?id=${id}'">去开通</button></c:if>
					</a>
				</li>
				<li data-type="2">
					<!-- <a href="javascript:;"> -->
					<a>
						<i class="iconfont icon-roundcheckfill" <c:if test="${2==icard.type}">data-check="true"</c:if>></i>
						<span  data-type="shopname">店铺链接</span>
						<i class="iconfont icon-bianji pull-right" onclick="location.href='${ctx }/wap/${shopNo}/any/itopic/freeshop-link/${id}${ext}'"></i>
					</a>
				</li>
			</ul>
		</div>
		<div class="info">
			<ul class="m-list" style="padding-bottom:0;">
				<li>
				<!--OfficialGoodListUrl:官网商品列表页，  OfficialUrl：官网首页url"-->
					<a href="${OfficialGoodListUrl}">
						<span>去看看我们其他高级版店铺</span>
						<i class="iconfont icon-right pull-right clr-light"></i>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>

<script type="text/javascript">
	/*店铺链接切换  */
	var reType=${icard.type};
	$('#j-openshop li .icon-roundcheckfill').click(function(){
		checkedShop($(this));
	});
	$('span[data-type="shopname"]').click(function(){
		checkedShop($(this).prev());
	});
	function checkedShop($obj){
		$('#j-openshop li .icon-roundcheckfill').removeAttr('data-check',true);
		$obj.attr('data-check',true);
	 	var type = $obj.closest('li').data('type');
		if(type!=reType)
			collect(type);
	}

	 function collect(type){
		 preloader.showWhite(); 
		  $.post("${ctx}/wap/${shopNo}/any/itopic/freeshop-link-shop/"+type+"/${icard.id}${ext}",{}, function(data){
			preloader.colse();
			if(data == 1){
				alertFun('选择免费版店铺成功！');
				reType=1;
			}else if(data == 2){
				alertFun('选择店铺链接成功！');
				reType=2;
			}else if(data == 0){
				alertFun('无须更改！');
			}else{
				alertFun('出错了');
			}
		}); 
	}

	
</script>

</body>
</html>