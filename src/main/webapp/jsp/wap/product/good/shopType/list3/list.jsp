<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../common/top.jsp"%>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta charset="UTF-8" />

<!-- 模板3 -->
<title>${shop.name }-商品分类</title>
<link rel="stylesheet" type="text/css" href="${resourcePath}/shopType/css/list3/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script>
</head>
<body>

	<div id="box">
	<form action="${ctx}/wap/${sessionShopNo}/any/list${ext}" method="get">
	<input type="hidden" id="shopTypeId" name="shopTypeId">
	<input type="hidden" id="shopTypeName" name="shopTypeName">
	<!-- 商品分类   -->	
	<div class="list-rowspan">
		<div class="list-product"  id="content">
			<c:choose>
				<c:when test="${not empty pagination.datas }">
					<c:forEach items="${pagination.datas }" var="shopType" varStatus="status">
						<a href="javascript:submit('${shopType.id}','${shopType.name}')" class="list-product-col">
							<span class="pic">
								<c:if test="${fn:contains(shopType.imgPath, 'image')==false}">
									<img src="${shopType.imgPath}" alt="">
								</c:if>
								<c:if test="${fn:contains(shopType.imgPath, 'image')}">
									<img src="${picUrl }${shopType.imgPath}" alt="">
								</c:if>	
							</span>
							<span class="info" data-font style="color: #${shopType.fontColor};"><span class="tit">${shopType.name}</span></span>
							<span class="icon"><i class="iconfont icon-right"></i></span>
						</a>
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
	</div>
	</form>
	<div id="down" style="color:#ccc;text-align:center;padding:20px 0;">
		<c:if test="${not empty pagination.datas && pagination.curPage<pagination.totalPage}">下拉加载更多分类...</c:if>
		<c:if test="${empty pagination.datas}">还木有分类噢！</c:if>
		<c:if test="${not empty pagination.datas && pagination.curPage==pagination.totalPage}">没有更多分类啦！</c:if>
	</div>
</div>

	<%@include file="../../../../common/nav.jsp" %>
	<script type="text/javascript" src="${resourceBasePath}page/scrollToTop.js"></script>
</body>
 <script type="text/javascript">
	var curPage =${pagination.curPage};
	var totalPage =${pagination.totalPage};

    $(document).ready(function(){ 
        //滚动加载 相关配置*/
			var loadObj = {
		    			   elemt : ".list-product",//
		    		       url:"${ctx}/wap/${sessionShopNo}/any/shopType/append${ext}",
					       loadType:'Json',//使用Html加载方式
					       idd : "#content",
					       totalRow :'${pagination.totalRow}',
					       start:'${pagination.end}',
					       param:{
					    	   /* groupId: '${shopTypeVo.groupId}' */
					       }
		    };
			rollLoad.init(loadObj);//触发滚动事件	
    });
	
    function submit(id,name){
	   	$('#shopTypeId').val(id);
	   	$('#shopTypeName').val(name);
	    $('form').submit();
    }
    
    function getAppendHtml(dataArray){
    	var htm_str = "";
    	for(x in dataArray){
    		var str1 = "image";
    		if(dataArray[x].imgPath.indexOf(str1)==-1){
    			str2 = "<img src='"+dataArray[x].imgPath+"' alt=''>"
    		}else{
    			str2 ="	<img src='${picUrl }"+dataArray[x].imgPath+"' alt=''>"
    		}
    		var str ="<a href='javascript:submit(\""+dataArray[x].id+"\",\""+dataArray[x].name+"\");' class='list-product-col' >"
            +"<span class='pic'>"   
            +str2	
 			+"</span>"
            +"<span class='info' data-font style='color: #"+dataArray[x].fontColor+";'><span class='tit'>"+dataArray[x].name+"</span></span>"
            +"<span  class='icon'><i class='iconfont icon-right'></i></span>"
            +"</a>";
    		htm_str+=str;
    	}
    	curPage++;
		if(!"${pagination}" || totalPage <= curPage){
			$("#down").html("没有更多分类啦！");
		}
       return htm_str;
    }
</script>
</html>