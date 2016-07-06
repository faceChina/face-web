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
<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}fonts/iconfont.css">
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/TouchSlide.1.1.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/fixedbottom.js"></script>
<script type="text/javascript" src="${resourcePath}js/dpreview.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slider.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slideout-jquery.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/slidefocus-jquery.js"></script>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop6/css/main.css">
</head>
<body navbar="true">

<div id="box">
	
	<!-- 模块图标 -->
	
	<div class="list-rowspan">
		<div class="list-colspan m-touch">
			<ul id="j-touch">
				
				<c:forEach items="${modularList}" var="modular" varStatus="status">
					<c:if test="${(status.index+1)%2 == 1 }">
						<li><a href="${modular.resourcePath }">
								<span data-background>
									<c:choose>
										<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
											<img src="${picUrl }${modular.imgPath }" alt="">
										</c:when>
										<c:otherwise>
											<img src="${modular.imgPath }" alt="${modular.nameZh}">
										</c:otherwise>
									</c:choose>
								</span>
								<var data-font>${modular.nameZh}</var>
							</a>
						</li>
					</c:if>
				</c:forEach>
				<!-- 偶数 -->
				<c:forEach items="${modularList}" var="modular" varStatus="status">
					<c:if test="${(status.index+1)%2 == 0 }">
						<li><a href="${modular.resourcePath }">
								<span data-background>
									<c:choose>
										<c:when test="${null != modular.imgPath && '' != modular.imgPath && !fn:contains(modular.imgPath, '/base/img')}">
											<img src="${picUrl }${modular.imgPath }" alt="">
										</c:when>
										<c:otherwise>
											<img src="${modular.imgPath }" alt="${modular.nameZh}">
										</c:otherwise>
									</c:choose>
								</span>
								<var data-font>${modular.nameZh}</var>
							</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<!-- 产品展示  -->
	
	<div class="list-rowspan">
		<c:forEach items="${shopTypeAndGoodList }" var="shopTypeDto">
			<div class="list-product">
				<div class="list-product-tit">
					<a href="${ctx }/wap/${shop.no}/any/list${ext}?shopTypeId=${shopTypeDto.id}&shopTypeName=${shopTypeDto.name }">
						<span>${shopTypeDto.name }</span>
						<i class="iconfont icon-right right"></i>
					</a>
				</div>
				<div class="list-product-cont">
					<ul>
						<c:forEach items="${shopTypeDto.goodList }" var="good">
							<li>
								<a href="${ctx }/wap/${shop.no}/any/item/${good.id}${ext}">
									<span class="pic"><img src="${picUrl }${good.picUrl }" alt=""></span>
									<span class="info">${good.name } </span>
									<span class="price clr-danger">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:forEach>
	</div>
<%@include file="../../../common/popularize.jsp"%>
	<%@include file="../../../common/foot.jsp" %>
	<%@ include file="../../../common/nav.jsp" %>
	
</div>
<script type="text/javascript">
//字体颜色、背景颜色、背景透明度、字号可修改
//后端传来的数据
var data = JSON.parse('${json}');

//改变商品分类文字颜色
function updateStyle(data,type) {
	
	if(data == undefined || data.length ==0) return;
	var colNum = Math.ceil(data.length/2);
	var b = 0;
	$("[data-font]").each(function(index){
		//console.log((2*(index-colNum)+1),99)
		var i = index>=colNum?(2*(index-colNum)+1):(index*2);
		//console.log(i,526,index>colNum);
		$(this).css({
			"color":data[i]["color"],
		});
	});
	if($("[data-font-en]").length){
         $("[data-font-en]").each(function(index){
        	 var i = index>=colNum?(2*(index-colNum)+1):(index*2);
	          $(this).css({
	         "color":data[i]["coloren"],
	         });
         });
	}

    if($("[data-background]").length){
         $("[data-background]").each(function(index){
        	 var i = index>=colNum?(2*(index-colNum)+1):(index*2);
	          $(this).css({
	         "background":"rgba("+data[i]['red']+","+data[i]['green']+","+data[i]['blue']+","+data[i]['opacity']+")"
	         }); 
         })
    }

}

try{
	updateStyle(data);
}catch(err){
	console.log(err);
}

/**
 * 滚动条宽度计算
 * @param  {[str]} id  '#id'
 * @param  {[number]} num 多少行
 */
(function(window,jQuery,undefined){

	function overflowWidth(cfg){
		
		var config = cfg || {};
		this.get = function(n){
			return config[n]
		}
		this.set = function(n,v){
			config[n] = v;
		}
		this.init();

		//return this;
	}
	overflowWidth.prototype = {
		init: function(){
			this.createDom();
			//this.bindEvent();
		},
		createDom: function(){
			var idObj = jQuery(this.get('id')),
				width = idObj.children().width(),
				supWidth = idObj.parent().width(),
				len = this.get('num')?this.get('num'):1,
				totalNum = Math.ceil(idObj.children().length/len);
				
			idObj.css({"width":width/supWidth*totalNum*100+"%"})
		}
	};
	window.overflowWidth = window.overflowWidth || overflowWidth;
})(window,jQuery);

$(function(){
	new overflowWidth({"id":"#j-touch","num":2});
})

</script>
</body>
</html>

