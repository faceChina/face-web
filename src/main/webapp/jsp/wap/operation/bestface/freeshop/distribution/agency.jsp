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
<title>成为代理</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/bestface/distribution/css/main.css">
</head>
<style type="text/css">
	html{background:#fff;}
	body{background:#fff;}
</style>
<body>

<div id="box">	
	
	<div class="tab">
		<ul class="tab-handle">
			<li class="active"><a href="#tabs1" data-toggle="tab">品牌供应商</a></li>
			<li><a href="#tabs2" data-toggle="tab">成为内部代理</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabs1">
				<!-- 搜索  -->
				<form action="/free/agency/apply/list/search.htm" method="post" id="searchForm">
					<div class="search">
						<i class="iconfont icon-search"></i>
						<span><input type="text" value="${keyword }" id="search" name="keyword" placeholder="搜索"></span>
					</div>
				</form>
				<!-- 推荐品牌供应商  -->
				<div class="list-row" id="content">
					<div class="list-col">
						<div class="list-inline box-flex fnt-16">推荐品牌供应商</div>
					</div>
					<c:forEach items="${pagination.datas }" var="shop">
						<div class="list-col">
							<div class="list-inline">
								<a href="/free/agency/${shop.no }/detail.htm">
									<c:if test="${empty shop.shopLogoUrl }">
										<img src="${resourcePath }img/defaultShopLogo.jpg" alt="" width="70" height="70">
									</c:if>
									<c:if test="${not empty shop.shopLogoUrl }">
										<img src="${picUrl }${shop.shopLogoUrl}" alt="" width="70" height="70">
									</c:if>
								</a>
							</div>
							<div class="list-col box-flex">
								<div class="list-inline box-flex"><a href="/free/agency/${shop.no }/detail.htm">${shop.name }</a></div>
								<div class="list-inline shop-other"><a onclick='validate("${shop.no}")'  class="btn btn-sm btn-default">快速申请</a></div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane" id="tabs2">
				<form method="post" data-form>
					<input type="text" placeholder="已知供应商，输入授权码" id="distributionAuthNo" name="distributionAuthNo" class="form-control" data-form-control>
					<div class="button">
						<button type="button" onclick="formSubmit()" class="btn btn-block btn-danger disabled" data-submit>确认提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
</div>


<script type="text/javascript" src="${resourcePath}js/plugin/page.js"></script>
<script type="text/javascript">
// 	var curPage =${pagination.curPage};
// 	var totalPage =${pagination.totalPage};
// 	$(document).ready(function() {
// 		//滚动加载 相关配置*/
// 		var loadObj = {
// 			elemt : ".head-list2",
// 			url : "/free/agency/apply/listAppend.htm",
// 			totalRow : '${pagination.totalRow}',
// 			start : '${pagination.end}',
// 			idd : "#content",
// 			loadType : "json"
// 		};
// 		rollLoad.init(loadObj);//触发滚动事件
// 	});
		//滚动加载 相关配置*/
		var loadObj = {
			elemt : ".list-col",
			url : "/free/agency/apply/listAppend.htm",
			totalRow : '${pagination.totalRow}',
			start : '${pagination.end}',
			idd : "#content",
			loadType : "html"
		};
		rollLoad.init(loadObj);//触发滚动事件
    function validate(applyShopNo) {
    	$.post("/free/agency/validate.htm", {'supplierShopNo':applyShopNo}, function(jsonResult) {
    		var data = JSON.parse(jsonResult);
    		if (data.success) {
    			location.href = "/free/agency/"+applyShopNo+"/agency-apply.htm";
			} else {
				artTip(data.info);
			}
    	});
    }
    $("#search").keydown(function(e) {
    	if (e.keyCode == 13) {
			$("#searchForm").submit();
		}
    });
    function formSubmit() {
    	var code = $("#distributionAuthNo").val();
	    $.post("/free/agency/saveInApply.htm", {'distributionAuthNo':$("#distributionAuthNo").val()}, function(jsonResult) {
	    	var result = JSON.parse(jsonResult);
	    	if (!result.success) {
	    		artTip(result.info);
			} else {
				location.href = "/free/agency/"+code+"/innerApplysuccess.htm";
			}
	    });
    }
</script>
</body>
</html>
