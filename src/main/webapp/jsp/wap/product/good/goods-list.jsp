<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${shop.name }-产品列表</title>
<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath}/goods/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js?ver=008"></script>
</head>
<body>
<form action="" method="get">
<%-- <input type="hidden" name="currentTime" id="currentTime" value="${goodVo.currentTime}"> --%>
<input type="hidden" name="priceSort" id="priceSort" value="">
<input type="hidden" name="volumeSort" id="volumeSort" value="">
<input type="hidden" name="shopTypeId" id="shopTypeId" value="${goodVo.shopTypeId}">
<input type="hidden" name="shopTypeName" id="shopTypeName" value="${goodVo.shopTypeName}">
<input type="hidden" name="layout" id="layout" value="${layout }">
<input type="hidden" name="newGoodMark" id="newGoodMark" value="${newGoodMark }">
<div id="box" style="overflow:inherit;">
	<!-- 搜索  -->
	<div class="search">
		<i class="iconfont icon-search"></i>
		<span><input type="text" name="name" value="${goodVo.name}" placeholder="请输入您要搜索的商品"></span>
	</div>
	<!-- tabs -->
	<div id="sortContent" class="nav-goods">
		<ul class="menu-goods">
			<li class="active" id="j-allPro">
				<span class="all txt-rowspan1">
				<c:choose>
					<c:when test="${not empty goodVo.shopTypeId}">
						${goodVo.shopTypeName}
					</c:when>
					<c:otherwise>
						全部商品
					</c:otherwise>
				</c:choose>
				</span>
				<i class="iconfont icon-sanjiao1"></i>
			</li>
			<li id="price-folds">
				<span>价格</span>
				<span class="folds">
					<c:choose>
						<c:when test="${goodVo.priceSort == 'DESC'}">
							<i class="iconfont icon-unfold active" attr-value="DESC"></i>
						</c:when>
						<c:when test="${goodVo.priceSort == 'ASC'}">
							<i class="iconfont icon-fold active"  attr-value="ASC"></i>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</span>
			</li>
			<li id="volume-folds">
				<span>销量</span>
				<span class="folds">
					<c:choose>
						<c:when test="${goodVo.volumeSort == 'DESC'}">
							<i class="iconfont icon-unfold active"  attr-value="DESC"></i>
						</c:when>
					</c:choose>
				</span>
			</li>
			<li><img src="${resourceBasePath}img/icon-layout-list.png" alt="" width="20" height="20" class="goods-layout" ></li>
		</ul>
		
		<div  id="j-subPro" class="goods-all hide">
			<div class="group group-justify">
				<div class="group-item">
					<a href="#" class="group-rowspan">
						<span class="group-colspan"><em class="txt-rowspan1">全部商品</em></span>
						<span class="group-colspan">
						<c:if test="${empty goodVo.shopTypeId}"><i class="iconfont icon-roundcheckfill clr-danger"></i></c:if>
						</span>
					</a>
					<c:forEach items="${shopTypeList}" var="shopType">
						<a href="#" class="group-rowspan" attr-value="${shopType.id}" >
							<span class="group-colspan"><em class="txt-rowspan1">${shopType.name}</em></span>
							<span class="group-colspan">
							<c:if test="${shopType.id==goodVo.shopTypeId}"><i class="iconfont icon-roundcheckfill clr-danger"></i></c:if>
							</span>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 商品列表 -->
	<div class="group group-left group-cleartop <c:if test='${layout != 2 }'>goods-sudoku</c:if>" id="content">
		<c:forEach items="${pagination.datas}" var="good">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<div class="product">
									<a href="item/${good.id}${ext}"class="product-item">
										<div class="product-img">
											<img src="${picUrl}${good.picUrl }" width="80" alt="产品图片"/>
										</div>
										<div class="product-info">
											<p class="product-title txt-rowspan2">${good.name}</p>
											<c:if test="${good.salesPrice!=0 }">
											<p class="product-other">
												<b class="clr-danger">¥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/></b>
												<del class="clr-gray clr-light">¥<fmt:formatNumber pattern="0.00" value="${good.marketPrice/100}"/></del>
											</p>
											</c:if>
											<c:if test="${good.classificationId!=15 }">
											<p class="product-other clr-light">
											<c:choose>
												<c:when test="${good.salesVolume > 0}">${good.salesVolume}人购买</c:when>
												<c:otherwise>0人购买</c:otherwise>
											</c:choose>
											</p>
											</c:if>
										</div>
									</a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div id="down" style="color:#ccc;text-align:center;padding:20px 0;">
		<c:if test="${not empty pagination.datas && pagination.curPage<pagination.totalPage}">下拉加载更多宝贝...</c:if>
		<c:if test="${empty pagination.datas}">还木有宝贝噢！</c:if>
		<c:if test="${not empty pagination.datas && pagination.curPage==pagination.totalPage}">没有更多宝贝啦！</c:if>
	</div>	
	<!-- 店铺预览的时候进来会显示这个 -->
	<div id="j-message" style="display:none;">
		<p>想要设置更高级的首页模板，要去PC端<br/> (http://www.boss-sir.com) 设置</p>
		<p  class="left" style="margin-top:10px;"><input type="checkbox"/> 以后不再提醒</p>
	</div>
	
	<%@ include file="../../common/nav.jsp"%>
	<script type="text/javascript" src="${resourceBasePath}page/scrollToTop.js"></script>
</div>
</form>
</body>
<script type="text/javascript">
	var curPage =${pagination.curPage};
	var totalPage =${pagination.totalPage};
	$(function(){

	    //滚动加载 相关配置*/
		var loadObj = {
	    			   elemt : ".group-item",//循环的第一节
	    		       url:"${ctx}/wap/${sessionShopNo}/any/append${ext}",
				       loadType:'Json',//使用Json加载方式
				       idd : "#content",//外层ID
				       totalRow :'${pagination.totalRow}',
				       start:'${pagination.end}',
				       param:{
				    	   shopTypeId:'${goodVo.shopTypeId}',
// 				    	   currentTime:'${goodVo.currentTime}',
					       orderString:'${goodVo.priceSort}',
					       sortTarget:'${goodVo.volumeSort}',
					       priceSort:'${goodVo.priceSort}',
					       volumeSort:'${goodVo.volumeSort}',
					       groupId:'${goodVo.groupId}'
				       }
	    };
	    
		rollLoad.init(loadObj,function(){
			setproductitemH();
		});//触发滚动事件
		//商品分类
		 $('#j-subPro .group-item').find('a').click(function(index, el) {
		    var shopTypeId = $(this).attr("attr-value");
		    var shopTypeName = $(this).find('em').text();
	        $('#shopTypeId').val(shopTypeId);
	        $('#shopTypeName').val(shopTypeName);
	        if(shopTypeName == '全部商品') {
	        	$('#newGoodMark').val("");
	        }
	        $('form').submit();
	    });
	    //排序状态切换
	    $('#price-folds').click(function(index, el) {
	    	$(this).addClass('active').siblings().removeClass('active');
	        var priceSort = $(this).find('i').attr("attr-value");
	     	if ('DESC' == priceSort) {
	     		priceSort = 'ASC';
        	} else {
        		priceSort = 'DESC';
        	}
            $('#priceSort').val(priceSort);
            $('form').submit();
	    });
	    $('#volume-folds').click(function(index, el) {
	    	$(this).addClass('active').siblings().removeClass('active');
            var volumeSort = $(this).find('i').attr("attr-value");
         	if ('DESC' == volumeSort) {
         		volumeSort = 'ASC';
        	} else {
        		volumeSort = 'DESC';
        	}
            $('#volumeSort').val(volumeSort);
            $('form').submit();
	    });
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
       			+'				<a href="'+('item/'+dataArray[x].id+'.htm')+'" class="product-item">'
       			+'					<div class="product-img">'
       			+'						<img src="${picUrl}'+dataArray[x].picUrl+'" width="80" alt="产品图片">'
       			+'					</div>'
       			+'					<div class="product-info">'
       			+'						<p class="product-title txt-rowspan2">'+dataArray[x].name+'</p>'
       			+(dataArray[x].salesPrice!=0?'						<p class="product-other">'
       			+'							<b class="clr-danger">¥'+Number(dataArray[x].salesPrice/100).toFixed(2)+'</b>'
       			+'							<del class="clr-gray clr-light">¥'+Number(dataArray[x].marketPrice/100).toFixed(2)+'</del>'
       			+'						</p>':'')
       			+(dataArray[x].classificationId!=15?'						<p class="product-other clr-light">'+Number(dataArray[x].salesVolume)+'人购买</p>':'')
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
	//全部商品
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
			e.preventDefault();
			$("#j-subPro").removeClass("show");
		}
	})
	
	//卖家版提示  当卖家预览自己的店铺时显示 
	function toShowInfo(){
		art.dialog({
			lock:true,
			title:"提示",
			content:document.getElementById('j-message'),
			button:[{
				name:"确认",
				callback:function(){
					
				}
			},{
				name:"关闭",
				focus:true,
				callback:function(){
					
				}
			}]
		});
	};
	function setproductitemH(){
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
	
</script>
</html>