<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>商品详情</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>
<div id="box">
	<input type="hidden" name="goodId" id="goodId" value="22">
	<!-- 轮播图 -->
	<div id="slider">
		<ul>
			<c:forEach items="${good.goodImgs}" var="img">
				<li><a href="javascript:;"><img src="${picUrl}${img.zoomUrl}" alt=""></a></li>
			</c:forEach>
		</ul>
	</div>
	
	
	<!-- 产品信息 -->
	<div class="group group-cleartop">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<h3 class="txt-rowspan2">${good.good.name }</h3>
					<p class="clearfix" style="margin-top:10px;">
						<span class="left">
							<b class="clr-danger" id="j-price">
								<fmt:formatNumber value="${good.good.salesPrice/100 }" pattern="0.00"></fmt:formatNumber>
							</b>
						</span>
					</p>
				</div>
			</div>
		</div>
	</div>

	
	<!-- 查看多属性 -->
	<c:choose>
			<c:when test="${not empty goodDetailVo.salesPropMap }">
				<div class="group group-justify">
					<div class="group-item">
						<div class="group-rowspan" id="group-rowspan">
							<div class="group-colspan" id="j-details-attrs">
								<span id="j-detail-shopmore">查看 尺寸 颜色分类</span>
							</div>
							<div class="group-colspan">
								<i class="iconfont icon-right"></i>
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="group group-justify">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan" id="j-details-attrs">
								<span id="j-detail-shopmore">查看购买数量</span>
							</div>
							<div class="group-colspan">
								<i class="iconfont icon-right"></i>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
	</c:choose>
	
	<!-- 商品展示  -->
	<div class="group">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">产品展示</div>
			</div>
		</div>
	</div>
	<div class="good-detials pd8">
		<c:out value="${good.good.goodContent}" escapeXml="false"/>
	</div>

	
	<!-- 滑出层：选择多属性 -->
	<div id="j-contentSpecial" style="display:none;">
		<div class="group group-cleartop">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<div class="product">
							<a href="goods-details.html" class="product-item">
								<div class="product-img" id="j-shopimg">
									<img src="${picUrl}${good.good.picUrl}" width="80" height="80" alt="产品图片">
								</div>
								<div class="product-info">
									<p class="product-title">${good.good.name}</p>
									<p class="product-other"><b class="clr-danger">¥<span data-price><fmt:formatNumber pattern="0.00" value="${good.good.salesPrice/100}"/></span></b></p>
								</div>
							</a>
						</div>
					</div>
				</div>
			</div>
			<c:choose>
			<c:when test="${not empty good.salesPropMap}">
				<input type="hidden" id="skuId" name="id" value="">
				<c:forEach items="${good.salesPropMap}" var="propMap" varStatus="status">
					<div class="group-item"  id="j-color" data-attrs="salesProp">
						<div class="group-rowspan">
							<div class="group-colspan">
								<h3 style="padding-bottom:5px;">${propMap.key}</h3>
								<p>
									<c:forEach items="${propMap.value}" var="goodProp">
										<a href="javascript:void(0)" class="btn btn-default" data-attr="${goodProp.id}">${goodProp.propValueName}</a>
									</c:forEach>
								</p>	
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<input type="hidden" id="skuId" name="id" value="${good.skuList[0].id}">
				<c:if test="${not empty good.notKeyPropList}">
					<div class="group-item" data-attrs>
						<div class="group-rowspan">
							<div class="group-colspan">
								<h3>属性</h3>
								<c:forEach items="${good.notKeyPropList}" var="goodProp">
									<p><c:out value="${goodProp.propName}" escapeXml="false"/>：<c:out value="${goodProp.propValueAlias}" escapeXml="false"/></p>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		</div>
	</div>

</div>	

<script type="text/javascript" src="${resourcePath }js/slidefocus/slidefocus-jquery.js"></script>
<script type="text/javascript" src="${resourcePath }js/addel.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//轮播图
	$.slidefocus({
		dom:"slider",	 //元素id，无“#”字符
		width:"100%",
		height:"100%",
		direction:"left",		 //上(up)右(right)下(down)左(left)
		play:true, 				 //自动播放(true/false),和direction、time组合使用
		time:"3000", 			 //时间间隔(单位毫秒)
		page:true				//是否显示页码
	});

	//查看商品属性
	$("#j-details-attrs").click(function(){
        operateFn("j-contentSpecial");
	});
	
    function operateFn(obj){
        art.dialog({
            lock:true,
            title:"规格查看",
            content:document.getElementById(obj),
            ok:function(){
            	return true;
            },
            cancel:true
        });
    };
});
</script>
</body>
</html>