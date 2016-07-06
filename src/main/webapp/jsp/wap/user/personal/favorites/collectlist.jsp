<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>收藏</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }personal/css/sdlist.css">
<%-- <script type="text/javascript" src="${resourcePath}js/plugin/page1.js"></script> --%>
<script type="text/javascript">
$(document).ready(function(){  
    //滚动加载 相关配置*/
	var loadObj = {
    			   elemt : ".group-item",
			       url:"${ctx}/wap/${sessionShopNo}/buy/personal/favorites/append${ext}",
			       totalRow :'${pagination.totalRow}',
			       start:'${pagination.end}',
			       param:{
			    	   status:'${status}',
			    	   type:'${type}'
			       },
			       loadType:'html',
			       idd : "#productlist"
    };
    
	
	rollLoad.init(loadObj);//触发滚动事件
});
	function getAppendHtml(dataArray){
    	var htm_str = "";
    	for(x in dataArray){
    	}
       return htm_str;
    }
</script>
</head>
<body>
<div id="box" data-select>

	<!-- 普通商品无订单状态   -->
	<c:if test="${empty pagination.datas }">
		<div class="no-content"  style="display:none;">
			<i class="iconfont icon-gouwuche none"></i>
			<p>您还没有订单,</p>
			<p>赶紧邀请小伙伴来购买吧！</p>
			<p><a class="btn btn-danger goshopping">去逛逛</a></p>
		</div>
	</c:if>
	<!-- 普通商品无订单状态 end   -->
	<ul class="nav-sort">
		<li><a data-id="selectNavTag" data-class="selectSDType"><c:if test="${type == 1 }">商品</c:if><c:if test="${type == 2 }">店铺</c:if><i class="iconfont icon-unfold"></i></a></li>
		<li class="line"></li>
		<li><a data-id="selectNavTag" data-class="selectSDStatus"><c:if test="${status == 1 }">全部</c:if><c:if test="${status == 0 }">失效</c:if><i class="iconfont icon-unfold"></i></a></li>
	</ul>
	<ul class="selectType selectSDStatus none">
		<li class="btline"><a href="/wap/${sessionShopNo }/buy/personal/favorites/${type }/1.htm">全部</a></li>
		<li><a href="/wap/${sessionShopNo }/buy/personal/favorites/${type }/0.htm">失效</a></li>
	</ul>
	<ul class="selectType selectSDType none">
		<li class="btline"><a href="/wap/${sessionShopNo }/buy/personal/favorites/1/1.htm">商品</a></li>
		<li><a href="/wap/${sessionShopNo }/buy/personal/favorites/2/1.htm">店铺</a></li>
	</ul>
	<div class="tool">
		<c:if test="${status == 1 }">
			<c:if test="${type == 1 }">
				<span>全部收藏<i>${goodCount }</i>个商品</span>
				<a data-id="editSD">编辑</a>
			</c:if>
			<c:if test="${type == 2 }">
				<span>全部收藏<i>${shopCount }</i>个店铺</span>
				<a data-id="editSD">编辑</a>
			</c:if>
		</c:if>
		<c:if test="${status == 0 }">
			<c:if test="${type == 1 }">
				<span>全部失效<i>${goodCount }</i>个商品</span>
				<a data-id="editSD">编辑</a>
			</c:if>
			<c:if test="${type == 2 }">
				<span>全部失效<i>${shopCount }</i>个店铺</span>
				<a data-id="editSD">编辑</a>
			</c:if>
		</c:if>
	</div>
	<!-- 一个店铺组 -->
	<c:choose>
		<c:when test="${type == 1 }">
				<div class="group group-others width10 productlist" id="productlist" >
				<c:forEach items="${pagination.datas }" var="data">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan group-colspan-first">
							<c:if test="${data.remoteStauts == 1 }">
								<span class="font-null"></span>
							</c:if>
							<c:if test="${data.remoteStauts != 1 }">
								<span class="btn btn-block disabled font-sx">失<br>效</span>
							</c:if>
							</div>
							<div class="group-colspan group-colspan-product">
								<a <c:if test="${data.remoteStauts == 1 }">href="/wap/${data.goodShopNo }/any/item/${data.remoteId}.htm"</c:if>>
									<div class="product">
										<div class="product-item">
											<div class="product-img">
												<img src="${picUrl }${data.remotePicUrl }" alt="产品图片">
											</div>
											<div class="product-info">
												<div data-master>
													<p class="product-title">${data.remoteName }</p>
													<p class="product-other">¥<fmt:formatNumber pattern="0.00" value="${data.remotePrice/100 }"></fmt:formatNumber>
													</p>
												</div>
											</div>
										</div>
									</div>
								</a>	
							</div>
							<div class="group-colspan group-colspan-single">
								<i class="iconfont icon-yuanquanweixuan none" data-select-single data-id="${data.id}"></i>
							</div>
						</div>
					</div>
				</c:forEach>
				</div>
		</c:when>
		<c:otherwise>
			<div class="group group-others width10 productlist shoplist " id="productlist"> 
				<c:forEach items="${pagination.datas }" var="data">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan group-colspan-first">
								<c:if test="${data.remoteStauts == 1 }">
									<span class="font-null"></span>
								</c:if>
								<c:if test="${data.remoteStauts != 1 }">
									<span class="btn btn-block disabled font-sx">失<br>效</span>
								</c:if>
							</div>
							<div class="group-colspan group-colspan-product">
								<a <c:if test="${data.remoteStauts == 1 }">href="/wap/${sessionShopNo }/buy/personal/favorites/toShop/${data.id}.htm"</c:if>>
									<div class="product">
										<div class="product-item">
											<div class="product-img">
												<img src="${picUrl }${data.remotePicUrl}" alt="产品图片">
											</div>
											<div class="product-info">
												<div data-master>
													<p class="product-title">${data.remoteName}</p>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
							<div class="group-colspan group-colspan-single">
								<i class="iconfont icon-yuanquanweixuan none" data-select-single data-id="${data.id}"></i>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div class="footfix"><a class="delShopGoods none" href="javascript:void(0)" onclick="deleteBatch()">删除</a></div>
<div class="boxOpacity none"></div>
<script type="text/javascript" src="${resourcePath }js/dpreview.js"></script>	
<script type="text/javascript">
	$(function(){
		var getMaskH=function(){
			var bH=$("body").height(),
				wH=$(window).height();
			return bH>=wH?bH:wH;
		};
		var bH=$("body").height(),
			wH=$(window).height(),
		 	$boxOpacity=$(".boxOpacity"),
			$selectType=$(".selectType"),
			$dataSelectSingle=$("[data-select-single]"),
			$delShopGoods=$(".delShopGoods"),
			$selectNavTag=$("a[data-id='selectNavTag']");

		$selectNavTag.off().on("click",function(){
			var $obj=$(this),
				$obji=$obj.children("i"),
				dClass=$obj.data("class");
				
				if ($obji.hasClass("icon-unfold")) {
					$(".nav-sort li").find("i").removeClass("icon-fold").addClass("icon-unfold");
					$obji.removeClass("icon-unfold");
					$obji.addClass("icon-fold");
					$selectType.hide();
					$("."+dClass).show();
					$boxOpacity.show();
					$boxOpacity.css("height",getMaskH()+"px");
				}
				else{
					$obji.removeClass("icon-fold");
					$obji.addClass("icon-unfold");
					$selectType.hide();
					$boxOpacity.hide();
				}
		});
		$boxOpacity.off().on("click",function(){
			$selectNavTag.children("i").removeClass("icon-fold").addClass("icon-unfold");
			$selectType.hide();
			$(this).hide();
		});
		// 编辑/完成
		$("a[data-id='editSD']").off().on("click",function(){
			var $obj=$(this),
				value=$obj.text(),
				$dataSelectSingleI=$(".group-colspan-single i");
				if (value=="编辑") {
					$dataSelectSingleI.show();
					$obj.text("完成");
				}
				else{
					$obj.text("编辑");
					$dataSelectSingleI.removeClass("icon-roundcheckfill");
					$dataSelectSingleI.addClass("icon-yuanquanweixuan");
					$dataSelectSingleI.hide();
					$delShopGoods.hide();
				}

		});
		$("#productlist").off().on("click","[data-select-single]",function(){
			var $obj=$(this);
				if ($obj.hasClass("icon-yuanquanweixuan")) {
					$obj.removeClass("icon-yuanquanweixuan");
					$obj.addClass("icon-roundcheckfill");
				}
				else{
					$obj.removeClass("icon-roundcheckfill");
					$obj.addClass("icon-yuanquanweixuan");
				}
				var selectedLen=$(".icon-roundcheckfill").length;
				if(selectedLen>0){
					$delShopGoods.show();
				}
				else{
					$delShopGoods.hide();
				}
		});
	});
	function deleteBatch() {
		var listId="";
		$(".group-colspan-single .icon-roundcheckfill").each(function(){
			var $obj=$(this);
			   listId=listId + $obj.data("id") + ",";
		});
		listId=listId.substring(0,listId.length-1);
		$.post("/wap/${sessionShopNo}/buy/favorites/cancel.htm", {ids:listId}, function(data) {
			if (data.success) {
				window.location.href = "/wap/${sessionShopNo}/buy/personal/favorites/${type}/1.htm"
			} else {
				artTip("操作失败");
			}
		}, "json");
	}
	
	var rollLoad = {
		    elemt:0, //获取添加字符串的高度
		    totalRow:1, //最大条数
		    idd:'',//最外层的ID
		    totalheight:0,//容器的高度
		    url:'',//数据访问URL
		    start:0,//起始条
		    curPage:1,
		    param:{},
		    //currentTime:'',
		    loadType:'Json',
		    init:function(obj,callevent){//传入默认值
		    	var winht = $(window).height(),
		        screenNum;
		        this.elemt = $(obj.elemt).outerHeight();
		        this.totalRow = obj.totalRow;
		        this.idd= obj.idd;
		        this.url=obj.url;
		        this.start=Number(obj.start);
		        this.param=obj.param;
		       /* if(obj.currentTime){
		            this.currentTime = obj.currentTime;
		        }*/
		        screenNum = Math.round(winht/this.elemt)>10?(Math.round(winht/this.elemt)-10):0; //根据屏幕 计算第一次加载数量
		        if(obj.loadType=='html'){
		        	 this.loadType = obj.loadType;
		        }
		        this.loading(callevent);
		    },
		    loading:function(callevent){ //触发滚动事件
		        var thiz = this;
		        $(window).scroll(function(){  
		           var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
		            thiz.totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
		            if(($(document).height()) <= thiz.totalheight  && thiz.start < thiz.totalRow) { 
		            	if(thiz.loadType == 'html'){
		            		thiz.addHtmltext(10);
		            	}else{
		            		thiz.addJsontext(10,callevent);
		            	}
		            }  
		        });
		    },
		    addJsontext:function(num,callevent){ //添加字符串
		    	var thiz = this;
		    	thiz.curPage+=1;
		        if(thiz.totalRow==0)return ;
		        if(thiz.totalRow==thiz.start)return ;
		        thiz.param.curPage=thiz.curPage;
		        thiz.param.start=thiz.start;
		        thiz.param.pageSize=num;
		    	$.post(thiz.url,thiz.param,function(data){
		    		var result =JSON.parse(data);
		    		if(result.success){
		    			var paging = JSON.parse(result.info);
		    			var htmlStr = getAppendHtml(paging.datas);
				        thiz.start=thiz.start + num;
				        var main = $(thiz.idd); 
				        console.log(htmlStr)
				        main.append(htmlStr);
				        if(callevent!=undefined){ callevent();}	
				      
		    		}else{
		    			alert(result.info);
		    		}
		    	});
		    },
		    addHtmltext:function(num){ //添加字符串
		    	var thiz = this;
		    	thiz.curPage+=1;
		        if(thiz.totalRow==0)return ;
		        if(thiz.totalRow==thiz.start)return ;
		        thiz.param.curPage=thiz.curPage;
		        thiz.param.start=thiz.start;
		        thiz.param.pageSize=num;
		    	$.post(thiz.url,thiz.param,function(data){
		    		if(data){
				        thiz.start+=num;
				        var main = $(thiz.idd); 
				        main.append(data);
				        console.log(data)
				        if($("a[data-id='editSD']").text()=="完成"){
				        	$(".group-colspan-single i").show();
				        }
		    		}else{
		    			alert("加载失败!");
		    		}
		    	});
		    }
		}


</script>
</body>
</html>