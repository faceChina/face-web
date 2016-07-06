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
<title>商品列表-出售中</title>
<%@include file="../top.jsp" %>
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
<script type="text/javascript">
$(function(){
	navbar("goods");
})
</script>
<style>
body{
	padding-bottom:60px;
}
</style>
<script type="text/javascript"> 
 $(document).ready(function(){  
    //滚动加载 相关配置*/
	var loadObj = {
		    	   elemt : ".item-shop",
       		       url:"${ctx}/free/good/append${ext}",
			       loadType:'html',//使用Html加载方式
			       idd : "#content",
			       totalRow :'${pagination.totalRow}',
			       start:'${pagination.end}',
			       param:{
			    	   status:'${goodVo.status}',
			    	   inventory:'${goodVo.inventory}'
			       }
    };
	rollLoad.init(loadObj);//触发滚动事件
}); 
</script>  
</head>
<body>
<div class="page-list">
	
	<div data-show="show">
		<div class="m-btn-inline" >
			<ul class="m-btn-row">
				<li class="m-btn-col"><a href="${ctx}/free/good/addGood${ext}" class="m-btn-border">发布新品</a></li>
				<li class="m-btn-col"><span type="button" class="m-btn-border" data-batch="true">批量操作</span></li>
			</ul>
		</div>

		<div class="m-btn-inline">
			<ul class="m-btn-row">
				<c:choose>
					<c:when test="${goodVo.inventory==0}">
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=1" class="m-btn-item">出售中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=3" class="m-btn-item">仓库中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?inventory=0" class="m-btn-item active">已售完</a></li>
					</c:when>
					<c:when test="${goodVo.status==1}">
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=1" class="m-btn-item active">出售中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=3" class="m-btn-item">仓库中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?inventory=0" class="m-btn-item ">已售完</a></li>
					</c:when>
					<c:when test="${goodVo.status==3}">
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=1" class="m-btn-item">出售中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?status=3" class="m-btn-item active">仓库中</a></li>
						<li class="m-btn-col"><a href="${ctx}/free/good/list${ext}?inventory=0" class="m-btn-item ">已售完</a></li>
					</c:when>
				</c:choose>
			</ul>
		</div>	
	</div>
	

	<div class="item-shop" id="content">
		<%@include file="good-list-data.jsp" %>
	</div>
</div>

<div class="m-edit-active" data-edit="true" data-type="hide">
	<ul class="m-edit-info">
		<li class="m-edit-item" data-type="checkAll"><span>全选</span></li>
		<li class="m-edit-item"  data-type="colse"><span>退出操作</span></li>
		<c:choose>
			<c:when test="${goodVo.status == 1 && data.inventory!=0}">
				<li class="m-edit-item"  data-type="sellout"><span>下架</span></li>
			</c:when>
			<c:when test="${goodVo.status == 3 && data.inventory!=0}">
				<li class="m-edit-item"  data-type="sell"><span>上架</span></li>
			</c:when>
		</c:choose>
		<li class="m-edit-item"><a href="javascript:removeGood();">删除</a></li>	
	</ul>
</div>
<%@ include file="../../../../common/freeNav.jsp" %>
</body>
<script type="text/javascript" src="${resourcePath }operation/bestface/js/shopedit.js"></script>
<script type="text/javascript">
$(function(){
	var typeEl = $('[data-active="true"]'),
	editEl = $('[data-edit="true"]'),
	checkEl = typeEl.find('[data-type="check"]');
	//下架
	typeEl.find('[data-type="sellout"]').on('click',function(){
		var self = this;
		console.log(1)
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定下架商品？",
			button : [ {
				name : '确定',
				callback : function() {
					batchShop.delHtml(self);
					//获取ID
					var id = batchShop.getId(self);
					preloader.show();
					window.location.href = "${ctx}/free/good/downShelvesGood/"+id+"${ext}";
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
		
	});

	//上架
	typeEl.find('[data-type="sell"]').on('click',function(){
		var self = this;
		
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定上架商品？",
			button : [ {
				name : '确定',
				callback : function() {
					batchShop.delHtml(self);
					//获取ID
					var id = batchShop.getId(self);
					preloader.show();
					window.location.href = "${ctx}/free/good/upShelvesGood/"+id+"${ext}";
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
		
	});

	//批量下架
	editEl.find('[data-type="sellout"]').on('click',function(){
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定下架选中的商品？",
			button : [ {
				name : '确定',
				callback : function() {
					preloader.show();
					var idsJson = batchShop.getCheck();
					window.location.href = "${ctx}/free/good/downShelves${ext}?idsJson="+idsJson;
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});	

	});

	//批量上架
	editEl.find('[data-type="sell"]').on('click',function(){
		
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定上架选中的商品？",
			button : [ {
				name : '确定',
				callback : function() {
					preloader.show();
					var idsJson = batchShop.getCheck();
					window.location.href = "${ctx}/free/good/upShelves${ext}?idsJson="+idsJson;
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});	

	});

	//批量删除
	editEl.find('[data-type="del"]').on('click',function(){
		art.dialog({
			lock : true,
			width : '80%',
			title : "提示",
			content : "确定删除选中商品？",
			button : [ {
				name : '确定',
				callback : function() {
					preloader.show();
					getCheck();
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});	

	});	
});
function removeGood() {
	art.dialog({
		lock : true,
		width : '80%',
		title : "提示",
		content : "确定上架选中的商品？",
		button : [ {
			name : '确定',
			callback : function() {
				preloader.show();
				var idsJson = batchShop.getCheck();
				window.location.href = "${ctx}/free/good/removeGood${ext}?idsJson="+idsJson;
			},
			focus : true
		}, {
			name : '关闭'
		} ]
	});	
}
</script>
</html>