<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>供应商详情</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<body>

<div id="box">	

	<div class="supplier-banner"><a href="javascript:;"><img src="${picUrl}${daRecruitInfo.headUrl }"></a></div>
	<!-- 代理申请成功后 不显示此字段   -->
	<c:if test="${boo }">
		<div class="supplier-title" id="js-supplier"><a href="javascript:;">供应商招募代理啦！</a></div>
	</c:if>
	
	<div class="group group-cleartop">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<p class="fnt-16">代理介绍：</p>
					<p class="agency-info">${daRecruitInfo.content }</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 商品列表 -->
	<div class="group group-left goods-sudoku" style="position:relative;" id="content">
	<c:forEach items="${pagination.datas }" var="good">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<div class="product">
						<a href="/free/agency/${good.id}/goodDetail.htm" class="product-item">
							<div class="product-img">
								<img src="${picUrl}${good.picUrl }" width="80" alt="产品图片">
							</div>
							<div class="product-info">
								<p class="product-title txt-rowspan2">${good.name }</p>
								<p class="product-other">
									<b class="clr-danger">¥
										<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/>
									</b>
								</p>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
		
		<!-- 代理申请成功后 不显示此字段   -->
		<c:if test="${boo }">
			<div class="join-us" id="js-join"><a href="/free/agency/${supplierShopNo}/agency-apply.htm">成为代理</a></div>
		</c:if>
		
	</div>
	
	<script type="text/javascript" src="${resourcePath }js/slidefocus/slidefocus-jquery.js"></script>
	
</div>
<script type="text/javascript" src="${resourcePath}js/plugin/page.js"></script>
<script type="text/javascript">
	//我的供应商进来页面显示效果
// 	var session_agency=sessionStorage.getItem('session_agency');
// 	if(session_agency=='supplier'){
// 		$('#js-supplier').hide();
// 		$('#js-join').hide();
// 	};
// 	var curPage =${pagination.curPage};
// 	var totalPage =${pagination.totalPage};
// 	$(document).ready(function() {
// 		//滚动加载 相关配置*/
// 		var loadObj = {
// 			elemt : ".head-list2",
// 			url : "/free/agency/${supplierShopNo}/detailAppend.htm",
// 			totalRow : '${pagination.totalRow}',
// 			start : '${pagination.end}',
// 			idd : "#content",
// 			loadType : "json"
// 		};
// 		rollLoad.init(loadObj);//触发滚动事件
// 	});
	$(document).ready(function() {
		//滚动加载 相关配置*/
		var loadObj = {
			elemt : ".group-item",
			url : "/free/agency/${supplierShopNo}/detailAppend.htm",
			totalRow : '${pagination.totalRow}',
			start : '${pagination.end}',
			idd : "#content",
			loadType : "html"
		};
		rollLoad.init(loadObj);//触发滚动事件
	});
</script>
</body>
</html>
