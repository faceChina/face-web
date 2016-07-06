<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css?ver=008">
<link rel="stylesheet" type="text/css" href="${resourcePath}fonts/iconfont.css?ver=008">
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="${resourcePath}js/dpreview.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slider.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slideout-jquery.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slidefocus-jquery.js"></script>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop7/css/main.css?ver=008">
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
<script type="text/javascript" src="${resourceBasePath}js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${resourcePath}js/mob-public.js?ver=008"></script>
<c:choose>
	<c:when test="${isPreview == 1 }">
		<c:if test="${not empty signPic && not emptyisDefaultSignPic }">
			<c:if test="${isDefaultSignPic == 1 }">
				<style type="text/css">
					.shopname{
						background-image: url("${signPic}");
					}
				</style>
			</c:if>
			<c:if test="${isDefaultSignPic == 2 }">
				<style type="text/css">
					.shopname{
						background-image: url("${picUrl}${signPic}");
					}
				</style>
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${empty shop.signPic }">
			<style type="text/css">
				.shopname{
					background-image: url("${resourceBasePath}img/dianzhao/1.jpg");
				}
			</style>
		</c:if>
		<c:if test="${not empty shop.signPic && not empty shop.isDefaultSignPic }">
			<c:if test="${shop.isDefaultSignPic == 1 }">
				<style type="text/css">
					.shopname{
						background-image: url("${shop.zoomSignPic}");
					}
				</style>
			</c:if>
			<c:if test="${shop.isDefaultSignPic == 2 }">
				<style type="text/css">
					.shopname{
						background-image: url("${picUrl}${shop.zoomSignPic}");
					}
				</style>
			</c:if>
		</c:if>
	</c:otherwise>
</c:choose>
</head>
<body>

<div id="goodsort" class="goods-all none">
	<div class="group group-justify">
		<div class="group-gs-title">选择分类<a class="goodssorts-cancle" data-id="cancel-goodssort">取消</a></div>
		<div class="group-item">
			<a href="/wap/${sessionShopNo }/any/list.htm" class="group-rowspan">
				<span class="group-colspan"><em class="txt-rowspan1">全部商品</em></span>
				<span class="group-colspan"></span>
			</a>
			<c:forEach items="${shopTypeList }" var="shopType">
				<a href="/wap/${sessionShopNo }/any/list.htm?shopTypeId=${shopType.id}" data-id="${shopType.id }" class="group-rowspan">
					<span class="group-colspan"><em class="txt-rowspan1">${shopType.name }</em></span>
					<span class="group-colspan"><c:if test='${goodVo.shopTypeId==shopType.id }'><i class="iconfont icon-roundcheckfill clr-danger"></i></c:if></span>
				</a>
			</c:forEach>
		</div>
	</div>
</div>

<form action="" method="get">
<div id="box">
	<input type="hidden" name="priceSort" id="priceSort" value="">
	<input type="hidden" name="volumeSort" id="volumeSort" value="">
	<input type="hidden" name="shopTypeId" id="shopTypeId" value="${goodVo.shopTypeId}">
	<input type="hidden" name="layout" id="layout" value="${layout }">
	<div class="search">
		<i class="iconfont icon-search"></i>
		<span><input type="text" name="name" value="${goodVo.name }" placeholder="输入产品名称"></span>
	</div>
	
	<!-- 此处为店铺名称(微名片打开别人的店铺的时候显示) 以及店铺预览的时候也显示 -->
	<ul class="shopname" id="js-shopname">
		<li class="shop-header"><span class="m-pic-icon"><img <c:if test="${empty shop.shopLogoUrl}">src="${resourcePath }img/defaultShopLogo.jpg"</c:if><c:if test="${not empty shop.shopLogoUrl}">src="${picUrl}${shop.shopLogoUrl}"</c:if>></span></li>
		<li class="shop-name">
			<p class="name-home txt-rowspan1">${shopName}</p>
			<c:if test="${not empty mySubbranchName }">
				<p class="txt-rowspan1">—${mySubbranchName }</p>
			</c:if>
		</li>
		<li class="shop-collect">
		<!-- 实心 icon-likefill -->
		<div class="collect-box">
			<c:choose>
				<c:when test="${isFavorites }">
					<i class="iconfont icon-yishoucang" data-id="collectgoodsshop" data-status="1"></i>
				</c:when>
				<c:otherwise>
					<i class="iconfont icon-weishoucang" data-id="collectgoodsshop"data-status="0"></i>
				</c:otherwise>
			</c:choose>
			<a class="collect collectgoodsshop" data-id="collect" data-type="2"  >
			</a>
		</div>
		</li>
	</ul>
	
	
	<!-- shopnotice -->
	<c:if test="${shop.isNotice == 1 }">
		<div class="shopnotice">
			<span><i class="iconfont icon-laba"></i>${notice.noticeContent }</span>
		</div>
	</c:if>
	<!-- tabs -->
	<div id="sortContent" class="nav-goods">
		<ul class="menu-goods">
			<li>
				<a href="/wap/${sessionShopNo }/any/list.htm">全部商品</a>
			</li>
				<li class="line pl10"></li>
			<li >
				<a data-id="goodssorts">商品分类</a>
			</li>
			<li class="line pr5"></li>
			<li><a href="/wap/${sessionShopNo }/any/list.htm?newGoodMark=1">新品上市</a></li>
			<li class="line pr5"></li>
			<li id="j-allPro" likefill class="li-settype"><i class="iconfont icon-iconfontjifenpaixu"></i></li>
		</ul>
		<div id="j-subPro" class="goods-all hide">
			<div class="group group-justify">
				<div class="group-item">
					<a href="/wap/${sessionShopNo }/any/list.htm?volumeSort=DESC" class="group-rowspan">
						<span class="group-colspan"><em class="txt-rowspan1">按销量从高到低排序</em></span>
						<span class="group-colspan"></span>
					</a>
					<a href="/wap/${sessionShopNo }/any/list.htm?priceSort=ASC" class="group-rowspan">
						<span class="group-colspan"><em class="txt-rowspan1">按价格从低到高排序</em></span>
						<span class="group-colspan"><i class="iconfont"></i></span>
					</a>
					<a href="/wap/${sessionShopNo }/any/list.htm?priceSort=DESC" class="group-rowspan">
						<span class="group-colspan"><em class="txt-rowspan1">按价格从高到低排序</em></span>
						<span class="group-colspan"></span>
					</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 商品列表 -->
	<div class="group group-left group-cleartop <c:if test='${layout != 2 }'>goods-sudoku</c:if>" id="content">
		<c:forEach items="${pagination.datas }" var="good">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<div class="product">
							<a href="${ctx }/wap/${shop.no}/any/item/${good.id}${ext}" class="product-item">
								<div class="product-img">
									<img src="${picUrl }${good.picUrl }" width="80" alt="产品图片">
								</div>
								<div class="product-info">
									<p class="product-title txt-rowspan2">${good.name }</p>	
									<p class="product-other">
										<b class="clr-danger">¥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></b>
										<del class="clr-gray clr-light">¥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100 }"/></del>
									</p>	
								</div>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		<div class="opacity-group none"></div>
	</div>
	
	
	<div style="color:#ccc;text-align:center;padding:20px 0;"><c:if test="${empty pagination.datas }">该店铺暂未上传商品哦~</c:if><c:if test="${not empty pagination.datas }"><!-- 下拉加载更多宝贝... /  -->没有更多宝贝啦！</c:if></div>

	
	
	<!-- 店铺预览的时候进来会显示这个 -->
	<div id="j-message" style="display:none;">
		<p>想要设置更高级的首页模板，要去PC端<br/> (http://www.boss-sir.com) 设置</p>
		<p  class="left" style="margin-top:10px;"><input type="checkbox"/> 以后不再提醒</p>
	</div>
	
	<c:if test="${shop.recruitButton == 1 && !isMaxSubbranchLevel}">
		<div class="join-us"><a onclick="recruit()">招募分店</a></div>
	</c:if>
	
	<!-- 回到顶部  -->
	<div class="totop" onclick="toTop()"><i class="iconfont icon-top2"></i></div>
	<%@include file="../../../common/foot.jsp" %>
	<%@ include file="../../../common/nav.jsp" %>
</div>
</form>
</body>
<script type="text/javascript">
function recruit() {
	var isMyOwnShop = ${isMyOwnShop};
	if (isMyOwnShop) {
		artTip("您无法申请成为自己的分公司");
	} else {
		window.location.href = "/wap/${shopNo}/any/subbranch/new.htm?subbranchId=${subbranchId }";
	}
}

$(function(){
	var loadObj = {
    			   elemt : ".group-item",//循环的第一节
    		       url:"${ctx}/wap/${sessionShopNo}/any/append${ext}",
			       loadType:'Json',//使用Json加载方式
			       idd : "#content",//外层ID
			       totalRow :'${pagination.totalRow}',
			       start:'${pagination.end}',
			       param:{
			    	   shopTypeId:'${goodVo.shopTypeId}',
				       priceSort:'${goodVo.priceSort}',
				       volumeSort:'${goodVo.volumeSort}'
			       }
    };
	rollLoad.init(loadObj,function(){
		setproductitemH();
	});
	$("#sort1").click(function(){
		var priceSort = "";
	    if($(".icon-fold").hasClass('active')){
	    	priceSort = 'DESC';
	    }else{
	    	priceSort = 'ASC';
	    }
	    $('#priceSort').val(priceSort);
	    $('form').submit();
	});
	$("#sort2").click(function(){
		$('#volumeSort').val("DESC");
	    $('form').submit();
	});
   var $productimg=$(".product-img");
    wproimg= $productimg.width();
    $productimg.css("height",wproimg+"px");
});
	
var curPage =${pagination.curPage};
var totalPage =${pagination.totalPage};
 $('#j-subPro .group-item').find('a').click(function(index, el) {
       $('#shopTypeId').val($(this).data('id'));
       $('form').submit();
 });

function toDetail(thiz){
	var id =thiz.attr("data-id");
	window.location.href="item/"+id+"${ext}";
}

function getAppendHtml(dataArray){
	var htm_str = "";
	for(x in dataArray){
		var str = ''
			+'<div class="group-item">'
			+'	<div class="group-rowspan">'
			+'		<div class="group-colspan">'
			+'			<div class="product">'
			+'				<a href="${ctx }/wap/${shop.no}/any/item/'+dataArray[x].id+'${ext}" class="product-item">'
			+'					<div class="product-img">'
			+'						<img src="${picUrl}'+dataArray[x].picUrl+'" width="80" alt="产品图片">'
			+'					</div>'
			+'					<div class="product-info">'
			+'						<p class="product-title">'+dataArray[x].name+'</p>'
			+'						<p class="product-other">'
			+'							<b class="clr-danger">¥'+Number(dataArray[x].salesPrice/100).toFixed(2)+'</b>'
			+'							<del class="clr-gray clr-light">¥'+Number(dataArray[x].marketPrice/100).toFixed(2)+'</del>'
			+'						</p>'
			+'					</div>'
			+'				</a>'
			+'			</div>'
			+'		</div>'
			+'	</div>'
			+'</div>';
   		
		htm_str+=str;
	}
	curPage++;
	if(totalPage <= curPage){
		$("#down").html("没有更多宝贝啦！");
	}
   return htm_str;
}
function priceSort() {
    var priceSort = "";
    if($(".icon-fold").hasClass('active')){
    	priceSort = 'DESC';
    }else{
    	priceSort = 'ASC';
    }
    $('#priceSort').val(priceSort);
    $('form').submit();
}
function volumeSort() {
    $('#volumeSort').val("DESC");
    $('form').submit();
}
// 商品排列方式切换
var layout = "${layout}";
var bool = null;
if ("2" != layout) {
	bool = true;
} else {
	bool = false;
}
$(".goods-layout").click(function(){
	bool = !bool;
	if(bool){
		$("#layout").val(1);
		$("#content").addClass("goods-sudoku");
		$(this).attr("src","${resourceBasePath}img/icon-layout-list.png");
		setproductitemH();
	}else{
		$("#layout").val(2);
		$("#content").removeClass("goods-sudoku");
		$(this).attr("src","${resourceBasePath}img/icon-layout-sudoku.png");
		var $productitem=$(".product-item");
		 $productitem.css("height","auto");
		 $productitem.children().css("height","80px");
	}
});
// $(function(){
// 	var layout = "${layout}";
// 	if ("1" == layout) {
// 		$("#content").addClass("goods-sudoku");
// 	} else {
// 		$("#content").removeClass("goods-sudoku");
// 	}
// }); 
//全部商品 	

//商品分类
$("a[data-id='goodssorts']").off().on("click",function(){
	$("#goodsort").toggleClass("none");
	$("#box").hide();
});
//关闭分类box
$("a[data-id='cancel-goodssort']").off().on("click",function(){
	$("#goodsort").toggleClass("none");
	$("#box").show();
});

$("#j-allPro").click(function(e){
	$("#j-subPro").toggleClass("show");
});

$("#j-subPro a").each(function(e){
	$(this).click(function(){
		$("#j-allPro span").html($(this).find("em").html());
	});
});

$("body").click(function(e){
	if($("#j-subPro").hasClass("show") && $(e.target).attr("id") != "j-allPro" && $(e.target).parent().attr("id") != "j-allPro"){
		$("#j-subPro").removeClass("show");
		$(".opacity-group").addClass("none");
	}
})

function setproductitemH(){
	   // 每个商品列表高度保持一致	
	   // 每个商品列表高度保持一致	
    var maxheight=0,
    	$productitem=$(".product-item");
    var $proimg=$(".product-img"),
		proimgw=$proimg.width();
	$proimg.css("height",proimgw+"px");
	 $productitem.each(function(){
	    	var $obj=$(this),
	    		height=$obj.height();
	    		if (height>maxheight) {
	    			maxheight=height;
	    		}
	    });
	    $productitem.css("height",maxheight);
}
window.onload=function(){
	if ("2" != layout) {
		setproductitemH();
	}
	
}
var ajaxCollect=function(callevent){
	var subbranchId = "${subbranchId}";
	var remoteType = null;
	var remoteId = null;
	var isLogin = ${isLogin};
	if (null != subbranchId && "" != subbranchId) {
		remoteType = 3;
		remoteId = subbranchId;
	} else {
		remoteType = 2;
		remoteId = "${sessionShopNo}";
	}
	if (isLogin) {
		$.post("/wap/${sessionShopNo}/buy/favorites/add.htm",{"remoteType":remoteType, "remoteId":remoteId},function(jsonData) {
			if(jsonData.success) {
			 	callevent();
				isPost=false;
			} else {
				artTip("操作失败！");
			}
		},"json");
	} else {
		window.location.href = "/wap/${sessionShopNo}/buy/favorites/addNoLogin/"+remoteType+"/"+remoteId+".htm";
	}
	
}
</script>
</html>


