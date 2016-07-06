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
<title>${titleShopName}-购物车</title>

<%@ include file="../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }cart/css/main.css">
<style type="text/css">
.product-title,
.product-other{
	font-size:12px;
	display: -webkit-box;
 	-webkit-line-clamp: 2; 
 	-webkit-box-orient: vertical;
 	overflow: hidden;
}
.product-info-more{}
.product-info-more .product-other-more{
 	width:116px;
 	border:1px solid #ccc;
}
.product-info-more .product-other-more em{
 	float:left;
 	width:98px; 
 	display: -webkit-box; 
 	-webkit-line-clamp: 2; 
 	-webkit-box-orient: vertical; 
 	overflow: hidden; 
 	font-size:12px; 
}
.product-info-more .product-other-more i{
	float:right; 
 	width:16px; 
 	font-size:12px; 
 	line-height:2em; 
}

.btndel{
	position:absolute;
	z-index:1;
	top:0px;
	right:0px;
	background:#c00;
	color:#fff;
	text-align:center;
}
</style>
</head>
<body>
<div id="box" data-select>
<c:choose>
				<c:when test="${fn:length(map)<1}">
					<!-- 普通商品无订单状态   -->
					<div class="no-content" >
						<i class="iconfont icon-gouwuche clr-light"></i>
						<p>您还没有商品,</p>
						<p>赶紧邀请小伙伴来购买吧！</p>
						<c:if test="${show == null }">
							<p><a class="btn btn-danger goshopping" href="/wap/${shop.no }/any/list.htm">去逛逛</a></p>
						</c:if>
					</div>
					<!-- 普通商品无订单状态 end   -->
				</c:when>
				<c:otherwise>


	<c:forEach items="${cartWithCouponMap }" var="cartMap">
	<c:set var="cartList" value="${cartMap.value.cardDtoList }"/>
	<c:set var="couponSetList" value="${cartMap.value.couponSetList }"/>
	<c:if test="${not empty cartList }">
	<div class="group group-others width20 productlist" data-shop-id="${cartList[0].shopNo }" data-select-group>
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan"><i class="iconfont icon-round clr-light" data-select-all></i></div>
				<div class="group-colspan"><a href="">${cartList[0].buyShopName }</a></div>
				<div class="group-colspan">
				<c:if test="${not empty couponSetList}">
					<a class="get-coupons" data-id="getcoupons">领券</a>	
					<span class="cartline"></span>
				</c:if>
				<span onclick="toEdit(this)">编辑</span><span class="hide" onclick="toSave(this)">保存</span></div>
			</div>
		</div>
		
		<c:forEach items="${cartList }" var="cart">
		<div class="group-item" data-pro-id="${cart.id }" id="${cart.id }" data-sku-id="${cart.goodSkuId }" data-stock="${cart.stock }" data-shop-no=${cart.shopNo }>
			<div>
			<div class="group-rowspan">
				<div class="group-colspan"><i class="iconfont icon-round clr-light" data-select-single></i></div>
				<div class="group-colspan">
					<div class="product">
						<div class="product-item">
							<div class="product-img">
								<img src="${picUrl}${cart.skuPicturePath }" width="70" height="70 alt="产品图片">
							</div>
							<div class="product-info">
								<div data-master class="">
									<p class="product-title">${cart.goodName }</p>
									<p class="product-other clr-light">${cart.skuPropertiesName }</p>
								</div>
								<div data-slave class="product-info-more hide">
									<div class="addel" id="addel1">
									    <span class="addel-del"><i class="iconfont icon-jianhao"></i></span>
									    <span class="addel-info"><input type="tel" value="${cart.quantity }" data-number></span>
									    <span class="addel-add"><i class="iconfont icon-add"></i></span>
									</div>
									<p class="product-other-more clr-light clearfix" onclick="popup(this,'${cart.goodId}')"
									<c:if test="${empty cart.skuPropertiesName or cart.skuPropertiesName==''}">
										style="display: none;"
									</c:if>>
										<em>${cart.skuPropertiesName }</em>
										<i class="iconfont icon-unfold clr-light"></i>
									</p>
								</div>
								
							</div>
						</div>
					</div>
				</div>
				<div class="group-colspan" style="vertical-align:top;">
					<div data-master class="">
						<p class="fnt-12">￥<em data-price><fmt:formatNumber pattern="0.00" value="${cart.salesPrice/100 }"/></em></p>
						<p class="fnt-12">X<em data-pro-number>${cart.quantity }</em></p>
					</div>
					<div data-slave class="product-info-more hide">
						<button class="btn btn-danger" onclick="delPro(this,'${cart.id}')">删除</button>
					</div>
					
				</div>
			</div>
			<c:if test="${ cart.unitPrice>cart.salesPrice }">
			<div class="group-rowspan">
				<div class="group-colspan clr-danger" style="text-align:right;">
					商品价格已调整，降<fmt:formatNumber pattern="0.00" value="${(cart.unitPrice-cart.salesPrice)/100 }"/>元。
				</div>
			</div>
			</c:if>
			<c:if test="${ cart.unitPrice<cart.salesPrice }">
			<div class="group-rowspan">
				<div class="group-colspan clr-danger" style="text-align:right;">
					商品价格已调整，涨<fmt:formatNumber pattern="0.00" value="${(cart.salesPrice-cart.unitPrice)/100 }"/>元。
				</div>
			</div>
			</c:if>
			</div>
		</div>
		</c:forEach>
		
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan" style="text-align:right;">
<!-- 					 <em class="clr-danger" data-shop-selected-number></em>  -->
					 小计：￥<em data-shop-selected-price></em>
				</div>
			</div>
		</div>
		<!-- 优惠券 -->
		<div class="coupons-box none">
			<div class="c-b-title"><span class="c-b-t-font">领取优惠券</span><a class="close-coupons">×</a></div>
			 <ul class="c-b-content">
			 <c:forEach items="${couponSetList }" var="couponSet">
			 	<li>
			 		<div class="c-b-c-left">
			 			<p class="c-l-price"><span class="price-sign">¥</span><span class="price-num"><fmt:formatNumber value="${couponSet.faceValue/100 }" pattern="#.##"/></span></p>
			 			<p class="c-l-order">订单满<fmt:formatNumber value="${couponSet.orderPrice/100 }" pattern="#.##"/>元使用（不含邮费）</p>
			 			<p class="c-l-time">使用期限<fmt:formatDate value="${couponSet.effectiveTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${couponSet.endTime }" pattern="yyyy.MM.dd"/></p>
			 		</div>
			 		<div class="c-b-c-right">
			 		<c:choose>
			 			<c:when test="${couponSet.isAllReceive }">
				 			<bottom class="c-r-get" data-id="c-r-get" disable >已领完</bottom>
			 			</c:when>
			 			<c:when test="${couponSet.hasReceive }">
				 			<bottom class="c-r-get" data-id="c-r-get" disable >已领取</bottom>
			 			</c:when>
			 			<c:otherwise>
				 			<bottom class="c-r-get" data-id="c-r-get" disable data-nameid="${couponSet.id }" data-namesubbranchid=${cartMap.key }>领取</bottom>
			 			</c:otherwise>
			 		</c:choose>
			 		</div>
			 	</li>
			 </c:forEach>
			 </ul>
		</div>
	</div>
	</c:if>
	</c:forEach>
	
	<!-- 失效商品 -->
	<div class="group group-left width20 productlist" data-nullity>
	<c:forEach items="${expireList }" var="cart">
		<div class="group-item" data-pro-id="${cart.id }">
			<div class="group-rowspan" data-touch>
				<div class="group-colspan">
					<span class="btn btn-block disabled" style="padding:0;">失<br>效</span>
				</div>
				<div class="group-colspan">
					<div class="product">
						<div class="product-item">
							<div class="product-img">
								<img src="${picUrl }${cart.skuPicturePath }" width="70" height="70" alt="产品图片">
							</div>
							<div class="product-info">
								<p class="product-title">${cart.goodName }</p>
								<p class="product-other clr-light">${cart.skuPropertiesName }</p>
							</div>
						</div>
					</div>
				</div>
				<div class="group-colspan" style="vertical-align:top;">
					<p class="fnt-12">X<em>1</em></p>
				</div>
			</div>
			<div class="btndel" onclick="toDel(this,'${cart.id}')" data-oprate>删除</div>
		</div>
	</c:forEach>
	</div>
	
	
	
	
	<!-- 清除失效商品 -->
	<c:if test="${not empty expireList }">
	<div class="button" onclick="delNullity(this);">
		<a href="javascript:void(0)" class="btn btn-block"><i class="iconfont icon-delete clr-light"></i> 清除失效商品</a>
	</div>
	</c:if>
	
	<div class="footfix" style="position:fixed;bottom:0;left:0;z-index:999;">
		<div class="group group-justify">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<p><i class="iconfont icon-round clr-light" style="font-size:24px;" data-select-total></i> 
						全选<span class="price">总计：￥<em data-total-price></em></span> 
						</p>
					</div>
					<div class="group-colspan">
						<button class="btn btn-danger" type="button" onclick="settlement()" id="j-goaccounts">提交订单(<em class="clr-danger" data-total-number></em>) </button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="goodPage" ></div>
	<%@ include file="../../common/foot.jsp"%>
		</c:otherwise>
	</c:choose>
</div>

<form id="jform" method="post">
	<input type="hidden" name="json"/>
</form>
<div class="box-opacity none"></div>
<script type="text/javascript" src="${resourcePath }js/addel.js"></script>	
<script type="text/javascript" src="${resourcePath }js/allSelect.js"></script>	
<script type="text/javascript" src="${resourcePath }js/iTouch.js"></script>
<script type="text/javascript" src="${resourcePath }js/fsize.js"></script>
<script type="text/javascript">
(function($){
	$.fn.addel = function(options){
		var opts = $.extend({},$.fn.addel.defaults,options);
		
		return this.each(function(){
			var thiz = $(this),
			objAdd=$(this).find(".addel-add")[0],
			objDel=$(this).find(".addel-del")[0];

			if(objAdd){
				objAdd.addEventListener('touchstart', function(event) {
					var number = Number(thiz.find("input").val());
					//opts.add();
					++number;
//					number = number >= opts.number ? opts.number : number;
					if(number >= opts.number){
						number = opts.number;
						artTip("超过最大购买数量！");
					}else{
						number = number;
					}
					thiz.find("input").val(number);
					opts["add"]();
				
				});
			}
			if(objDel){
				objDel.addEventListener('touchstart', function(event) {
					var number = Number(thiz.find("input").val());
					//opts.del();
					--number;
//					number = number <= 1 ? 1 : number;
					if(number < 1){
						number = 1;
						artTip("购买数量不能低于1！");
					}else{
						number = number;
					}
					
					thiz.find("input").val(number);
					opts["del"]();
				
				});
			}
		});
		$.fn.addel.defaults = {
			number:1,
			add:null,
			del:null
		}
	}
})(jQuery);
function settlement(){
	var arr=[];
	$("[data-shop-id]").each(function(){
		$(this).find("[data-pro-id] .icon-roundcheckfill").each(function(){
			var sel= $(this).parents("[data-pro-id]");
			arr.push(sel.attr('data-pro-id'));
		})
	});
	var json=JSON.stringify(arr);
	if(json.length>2){
		$('#jform').attr('action','/wap/${sessionShopNo}/buy/cart/buy.htm')
		$('#jform [name=json]').val(json);
		$('body').find('button').attr('disabled',true);
		$('#jform').submit();
	}else{
		$.dialog.alert('请先选择商品')
	}
}
$(function(){
	if('${info1}'){
		$.dialog.alert('${info1}')
	}
	//未选状态
// 	getShopTotalNumber()
// 	getShopTotalPrice()
// 	getTotalNumber()
// 	getTotalPrice()
	
	//选择状态
	getSelectedShopTotalNumber();
	getSelectedShopTotalPrice();
	getSelectedTotalPrice()
	
	//选择数量
	$("[data-pro-id]").each(function(index,element){
		var thiz = $(this);
		thiz.each(function(key,dom){
			//初始加载页面时，获取各商品库存
			var stock = thiz.data('stock');
			$(this).find("input[data-number]").on('input',function(){
                var numberVal=$(this).val();
                if(numberVal<1){
                    $(this).val("1");
                    artTip("购买数量不能低于1！");
                    return ;
                }else if(numberVal>stock){
                    $(this).val(stock);
                    artTip("超过最大购买数量"+stock+"！");
                    return ;
                }
                getSelectedShopTotalNumber();
                getSelectedShopTotalPrice();
                getSelectedTotalPrice();
            });
			$(this).find("input[data-number]").on('blur',function(){
                var numberVal=$(this).val();
                if(numberVal<=0){
                    $(this).val(1);
                    getSelectedShopTotalNumber();
	                getSelectedShopTotalPrice();
	                getSelectedTotalPrice();
                }else{
                	return;
                }
            });
			$(this).addel({
				number:stock,
				add:function(){
					getSelectedShopTotalNumber();
					getSelectedShopTotalPrice();
					getSelectedTotalPrice();
					
					//改变页面上的显示
					var num = thiz.find("input[data-number]").val();
					thiz.find("[data-pro-number]").html(num);
				},
				del:function(){
					getSelectedShopTotalNumber();
					getSelectedShopTotalPrice();
					getSelectedTotalPrice();
					
					//改变页面上的显示
					var num = thiz.find("input[data-number]").val();
					thiz.find("[data-pro-number]").html(num);
				}
			});
		});
	});
	
	//全选
	$("[data-select]").each(function(index, element) {
		$(this).allSelect({
			add:function(){
				getSelectedShopTotalNumber();
				getSelectedShopTotalPrice();
				getSelectedTotalPrice()
			},
			del:function(){
				getSelectedShopTotalNumber();
				getSelectedShopTotalPrice();
				getSelectedTotalPrice()
			}
		});
    });
	
	//滑动删除
	$("[data-touch]").each(function(){
		var thiz = $(this);
		thiz.iTouch({
			extra:function(){
				var h = thiz.outerHeight(true);
				$("[data-touch]").parent().css({
					"position":"relative"
				});
				$("[data-touch]").css({
					"position":"relative",
					"z-index":2,
					"background":"#fff"
				});
	 			$("[data-oprate]").css({
	 				width:h+"px",
	 				height:h+"px",
	 				lineHeight:h+"px"
	 			});
			}
		});
	});
	$("[data-select-group]").each(function(){
		if($(this).data('shop-id')=='${sessionShopNo}'){
			$(this).find('[data-select-all]').click();
		}
	})
});

//删除滑动
function toDel(thiz,id){
	//alert('删除滑动')
	$.post("/wap/${sessionShopNo}/buy/cart/delete.htm",{'id':id},function(data){
		$(thiz).parent()
		   .animate({opacity:0},500)
		   .delay(500,function(){
			   $(this).remove()
		    });
		var proLen = $("[data-pro-id]").length;
		if (proLen <= 1) {
			if($('#box').find("[data-shop-id]")){
				$('#box').html(
					'<div class="no-content" >'+
						'<i class="iconfont icon-gouwuche clr-light"></i>'+
						'<p>您还没有商品</p>'+
						'<p>赶紧邀请小伙伴来购买吧！</p>'+
						'<p><a class="btn btn-danger goshopping" href="/wap/${shop.no}/any/list.htm">去逛逛</a></p>'+
					'</div>'	
				);
			}
		}
	});
}

//各店铺选中的商品总数
function getSelectedShopTotalNumber(){
	var $jgoaccounts= $("#j-goaccounts"),
	total = 0,
	$selectedInput=$(".group-item").find(".icon-roundcheckfill");
	$("[data-shop-id]").each(function(){
		$(this).find("[data-pro-id]").each(function(){
			if($(this).find('.icon-roundcheckfill').length >0){
				total += Number($(this).find("[data-number]").val());
			}
		});
	});
	if(total>0){
		$jgoaccounts.text("提交订单("+total+")");
	}
	else{
		$jgoaccounts.text("提交订单");
	}
	
}
//各店铺选中的商品总额
function getSelectedShopTotalPrice(){
	$("[data-shop-id]").each(function(){
		var thiz = $(this);
		var total = 0;
		thiz.find("[data-pro-id] .icon-roundcheckfill").each(function(){
			var price = Number($(this).parents("[data-pro-id]").find("[data-price]").html());
			var number = Number($(this).parents("[data-pro-id]").find("[data-number]").val());
			var all = Math.round(price*100) * number/100;
			total += all;
			total=Math.round(total*100)/100;
		});
		thiz.find("[data-shop-selected-price]").html(total);
	});
}

//选中的商品总额
function getSelectedTotalPrice(){
	var total = 0;
	$("[data-shop-id]").each(function(){
		var price = Number($(this).find("[data-shop-selected-price]").html());
		total += price;
	})
	$("[data-total-price]").html(total);
	return total;
}


//删除商品
	function delPro(thiz,id){
			var len = $(thiz).parents("[data-shop-id]").find("[data-pro-id]").length;
			artDialogComfirm(null,function(){
				$.post("/wap/${sessionShopNo}/buy/cart/delete.htm",{'id':id},function(data){
					if(len <= 1){
						$(thiz).parents("[data-shop-id]").remove();
						var proLen = $("[data-pro-id]").length;
						if (proLen < 1) {
							if($('#box').find("[data-shop-id]")){
								$('#box').html(
									'<div class="no-content" >'+
										'<i class="iconfont icon-gouwuche clr-light"></i>'+
										'<p>您还没有商品</p>'+
										'<p>赶紧邀请小伙伴来购买吧！</p>'+
										'<p><a class="btn btn-danger goshopping" href="/wap/${shop.no}/any/list.htm">去逛逛</a></p>'+
									'</div>'	
								);
							}
						}
					}else{
						$(thiz).parents("[data-pro-id]").remove();
					}
					getSelectedShopTotalNumber();
					getSelectedShopTotalPrice();
					getSelectedTotalPrice()
				});
				return true;
			},'<div style="padding:10px 60px 10px;">确认删除？</div>');
		}



//清除失效商品
function delNullity(obj){

	artDialogComfirm(null,function(){
		$.post("/wap/${sessionShopNo}/buy/cart/delNullity.htm",{},function(data){
			$("[data-nullity]").remove();
			var len = $("[data-pro-id]").length;
			if (len < 1) {
				$('#box').html(
					'<div class="no-content" >'+
						'<i class="iconfont icon-gouwuche clr-light"></i>'+
						'<p>您还没有商品</p>'+
						'<p>赶紧邀请小伙伴来购买吧！</p>'+
						'<p><a class="btn btn-danger goshopping" href="/wap/${shop.no}/any/list.htm">去逛逛</a></p>'+
					'</div>'	
				);
			}
		});
		$(obj).remove();
		return true;
	}, '<div style="padding:10px 40px 10px;">确认删除失效商品？</div>');
}

//编辑
function toEdit(thiz){
	$(thiz).hide().siblings().show();
	$(thiz).parents("[data-shop-id]").find(".group-item").each(function(){
		$(this).find("[data-master]").addClass("hide").removeClass("show");
		$(this).find("[data-slave]").addClass("show").removeClass("hide");
	});
	
}

//保存
function toSave(thiz){
	var flag=true;
	$(thiz).parents("[data-shop-id]").find("[data-pro-id]").each(function(){
		var stock=$(this).find('input[type=tel]').val();
		if(stock<=0&&flag){
			$.dialog.alert('购买数量不能低于1！')
			flag=false;
		}
	})
	if(!flag){return;}
	$(thiz).hide().siblings().show();
	var arr=[];
	$(thiz).parents("[data-shop-id]").find("[data-pro-id]").each(function(){
		var obj=new Object();
		obj.id=$(this).data('pro-id')
		obj.goodSkuId=$(this).data('sku-id')
		var stock=$(this).data('stock');
		obj.quantity=$(this).find('input[type=tel]').val();
		obj.shopNo=$(this).data('shop-no');
		var flag=true;
		$(this).find("[data-master]").addClass("show").removeClass("hide");
		$(this).find("[data-slave]").addClass("hide").removeClass("show");
		$(this).find('input[type=tel]').val(obj.quantity);
		$(this).find('[data-pro-number]').html(obj.quantity);
		for(var x in arr){
			if(arr[x].goodSkuId==obj.goodSkuId){
				arr[x].quantity=(Number(arr[x].quantity)+Number(obj.quantity))>stock?stock:(Number(arr[x].quantity)+Number(obj.quantity));
				$(thiz).parents('[data-shop-id]').find('[data-pro-id='+arr[x].id+'] input[type=tel]').val(arr[x].quantity);
				$(thiz).parents('[data-shop-id]').find('[data-pro-id='+arr[x].id+'] [data-pro-number]').html(arr[x].quantity);
				$(this).remove();
				flag=false;
			}
		}
		if(flag){
			arr.push(obj);
		}
	});
	$.post("/wap/${sessionShopNo}/buy/cart/save.htm",{'json':JSON.stringify(arr)},function(data){
		
	})
}

var shopData = '{"TIANLAN-M":{"colorValueCode":"TIANLAN","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"12010","sizeValueCode":"M","stock":11,"version":"v_0.010"},"TIANLAN-L":{"colorValueCode":"TIANLAN","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"12030.00","sizeValueCode":"L","stock":110,"version":"v_0.010"},"TIANLAN-S":{"colorValueCode":"TIANLAN","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"12040.00","sizeValueCode":"S","stock":10,"version":"v_0.010"},"HE-M":{"colorValueCode":"HE","id":15,"picturePath":"http://y2.ifengimg.com/fashion_spider/dci_2013/01/aa1f0af1397546ddc5e6a2a4df606395.jpg","salesPrice":"12100.00","sizeValueCode":"M","stock":130,"version":"v_0.010"},"HE-L":{"colorValueCode":"HE","id":15,"picturePath":"http://y2.ifengimg.com/fashion_spider/dci_2013/01/aa1f0af1397546ddc5e6a2a4df606395.jpg","salesPrice":"12020","sizeValueCode":"L","stock":140,"version":"v_0.010"},"HE-S":{"colorValueCode":"HE","id":15,"picturePath":"http://y2.ifengimg.com/fashion_spider/dci_2013/01/aa1f0af1397546ddc5e6a2a4df606395.jpg","salesPrice":"12300.00","sizeValueCode":"S","stock":0,"version":"v_0.010"},"HEI-M":{"colorValueCode":"HEI","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"23.00","sizeValueCode":"M","stock":3,"version":"v_0.010"},"HEI-L":{"colorValueCode":"HEI","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"1200.00","sizeValueCode":"L","stock":0,"version":"v_0.010"},"HEI-S":{"colorValueCode":"HEI","id":15,"picturePath":"http://i10.topit.me/l135/1013513773d0274e28.jpg","salesPrice":"33.00","sizeValueCode":"S","stock":10,"version":"v_0.010"}}';

$('[data-attrs] [data-attr]').on('click',function(event) {
	
	if($(this).is('.active')){
		return false;
	}
	$('[data-id="number"]').val(1);
	$(this).addClass('active').siblings().removeClass('active');//点击切换选中状态	
	attributeReplace.getGoodArr(this);//执行以获取属性信息
	attributeReplace.changeShop($(this).data('attr')); 

	
});

//获取商品JSON 不同颜色商品切换
var attributeReplace = {
	goodArr: null,
	srcc: '',
	singleData:null,//单条数据
	goodId:'',
	inventory: 0,
	sizeAtr:'',
	colorAtr:'',
	type:'',
	//price:'0',
	getGoodArr:function(thiz){
		dataId = $(thiz).closest('[data-attrs]').attr('id');
		if(dataId == 'j-color'){
			this.colorAtr = $(thiz).data('attr');
			this.type = '1';
		}else if(dataId == 'j-size'){
			this.sizeAtr = $(thiz).attr('data-attr');
			this.type = '2';
		}/*else{
			this.sizeAtr = $('#j-size').find('.active').data('attr');
			this.colorAtr = $('#j-color').find('.active').data('attr');
		}*/
	},
	setdata: function(shopData){ //切换商品属性
		this.goodArr = JSON.parse(shopData);
	},
	changeShop:function(){
		var arr = [];
		var shopId = '';
		for(var i=0;i<arguments.length;i++){
			if(arguments[i] == ''){
				continue;
			}
			if(shopId !== '') shopId += "-";
			shopId += arguments[i];
			//arr =
		}
		$('[data-attr]').removeAttr('disabled');
		var tempData = this.getTempData(shopId);
			for(key in tempData){
				if(this.type == "1"){
					if(tempData[key].stock == 0){
						$('[data-attr="'+tempData[key].sizeValueCode+'"]').removeClass("active").attr('disabled','true');
					}
					this.srcc = this.singleData.picturePath;
					
				}else if(this.type == "2"){
					if(tempData[key].stock == 0){
							$('[data-attr="'+tempData[key].colorValueCode+'"]').removeClass("active").attr('disabled','true');	
					}
					
				}
					$('#j-size [data-attr]').removeAttr('disabled');
						
	
				
			}

			
		this.inventory = this.singleData.stock;
		this.conAttr();
	},
	getTempData:function(shopId){ //获取商品 ID 购买数量 number 商品价格 salesPrice
		var shopArr = [];
		var	activeId = $('#j-color').find('.active').data('attr') +'-'+$('#j-size').find('.active').data('attr');

		for(key in this.goodArr){
			var idArr = key.split('-');
			for(var i=0;i<idArr.length;i++){

				if(idArr[i] == shopId){
					if(i == 0){
						this.singleData = this.goodArr[key];
					}
					shopArr.push(this.goodArr[key]);
					break;
				}
			}
			if(key == activeId){
				this.singleData = this.goodArr[key];
			}
		}
	
		/*shopArr['goodItemId'] = this.goodArr.id;
		shopArr['quantity'] = $("#number").val();
		shopArr['version'] = this.goodArr.version;*/
		return shopArr;
	},
	getdata:function(){ //获取商品 ID 购买数量 number 商品价格 salesPrice
		return this.singleData;
	},
	conAttr: function(){
		if(this.srcc.replace(/(^s*)|(s*$)/g, "").length !==0) $('#j-shopimg').find('img').attr('src',this.singleData.picturePath);  //商品图片地址需要修改
		var mycolor = $("#j-color").find(".active").text();
    	var mysize = $("#j-size").find(".active").text();
		$('#inventory').text(this.singleData.stock);
		$('[data-price]').text(this.singleData.salesPrice);
		$('[data-other]').text(mycolor+'、'+mysize);
		$('[data-id="goodStock"]').val(this.singleData.stock);
	}
}


function popup(thiz,id){
	$.post("/wap/${sessionShopNo}/buy/cart/goodPage.htm",{'id':id},function(data){
		$("#goodPage").html(data);
		art.dialog({
	        lock:true,
	        title:"商品属性",
	        content:document.getElementById("j-contentSpecial"),
	        button:[{
	            name:"加入购物车",
	            focus:true,
	            callback:function(){
	            	if($("#inventory").text()=="0"){
	            		artTip("商品库存为0不能购买");
	            		return false;
	            		
	            	}
	            	var str=$(thiz).parents('div[data-sku-id]').find('.product-other').text()
	            	var ar=[];
	            	if(str.indexOf(";")!=-1){
	            		ar=str.split(";");
	            	}else{
	            		ar=[str];
	            	}
	            	var str1=$("#j-contentSpecial").find("em[data-other]").text()
	            	if(str1){
		            	for(x in ar){
		            		var arr=ar[x].split(":")
		            		arr[1]=str1.split("、")[x]
		            		ar[x]=arr[0]+':'+arr[1]
		            	}
		            }
	            	$(thiz).find('em').text(ar.join(";"));
	            	$(thiz).parents('div[data-sku-id]').find('.product-other').text(ar.join(";"));
	            	if(str.indexOf(':')==-1){
	            		$(thiz).find('em').text(str1);
	            		$(thiz).parents('div[data-sku-id]').find('.product-other').text(str1);
	            	}
	            	$(thiz).parent().find('input[type=tel]').val($("#number").val());
	            	$(thiz).parents('div[data-sku-id]').data('sku-id',$("#skuId").val());
	            	$(thiz).parents('div[data-sku-id]').data('stock',$("#inventory").text());
	            	$(thiz).parents('div[data-sku-id]').find('[data-price]').text(Number($("#price").val()).toFixed(2));
	            	getSelectedShopTotalNumber();
					getSelectedShopTotalPrice();
					getSelectedTotalPrice()
	            }
	        }]
	    })
	},'html')
}
//领取优惠券
$(function(){

	// 领券
	$("a[data-id='getcoupons']").off().on("click",function(){
		$(this).parents(".productlist").find(".coupons-box").slideDown();
		$(".box-opacity").removeClass("none");
		$("#box").css({"height":$(window).height(),"overflow-y":"hidden"});
	});
	
	var $boxopacity=$(".box-opacity");
		$boxopacity.off().on("click",function(){
		closeCouponsBox();
	});
	$(".close-coupons").off().on("click",function(){
		closeCouponsBox();
	});
	// 关闭
	function closeCouponsBox(){
		$boxopacity.addClass("none");
		$(".coupons-box").slideUp();
		$("#box").css({"height":"auto","overflow-y":"auto"});
	}
	$("[data-id='c-r-get']").off().on("click",function(){
		var $obj=$(this);
		if($obj.attr("disabled")){
			return false;
		}
		var couponSetId = $obj.data("nameid");
		var subbranchId = $obj.data("namesubbranchid").split("@")[1];
		if (subbranchId == '0') {
			subbranchId = "";
		}
		$obj.attr("disabled",true);
		$obj.text("领取中...");
		$.ajax( {    
			    url:'/wap/${sessionShopNo}/buy/coupon/'+couponSetId+".htm",   
			    data:{   'subbranchId':subbranchId  },    
			    type:'post',       
			    dataType:'json', 
			    success:function(data) {    
			        if(data.success){
			            $obj.text("已领取");
			            $obj.attr("disabled",true);
			        }else{
			        	$obj.text("领取");
						$obj.attr("disabled",false);
			            artTip(data.info); 
			        }    
			     },    
			     error : function() {    
			        $obj.text("领取");
					$obj.attr("disabled",false);
			          artTip("异常！");    
			     }
		});

	});

})

</script>

</body>
</html>

