<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 滑出层：选择多属性 -->
<div id="j-contentSpecial" class="new-buygood-diablog" style="display:none;">
<!-- 控件加载整个j-contentSpecial层，表单必须放置在层之中 -->
<form id="buyForm" action="" method="post" enctype="application/x-www-form-urlencoded">
	<input type="hidden" id="iscallback" name="iscallback" value="false">
	<input type="hidden" id="username" name="username"  value="" >
	<input type="hidden" id="password" name="password"  value="" >
	<input type="hidden" id="callbackurl" name="callbackurl"  value="" >
	<input type="hidden" id="skipurl" name="skipurl"  value="" >
	
	<input type="hidden" id="url2" name="url2" value="/wap/${sessionShopNo}/j_spring_security_check">
	<input type="hidden" id="shareId" name="shareId" value="${param.shareId}">
	<input type="hidden" id="gid" name="gid" value="${goodDetailVo.good.id}">
	<div class="group group-cleartop">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan proimg-header">
					<div class="product-item product-item-img">
						<span class="product-img" id="j-shopimg">
							<img src="${picUrl}${goodDetailVo.good.picUrl}" width="80" height="80" alt="产品图片">
						</span>
						<div class="product-info">
							<p class="product-other product-price">¥<span class="price-font" data-price><fmt:formatNumber pattern="0.00" value="${goodDetailVo.good.salesPrice/100}"/></span></p>
							<p class="product-other">库存<em data-id="inventory" id="inventory">${goodDetailVo.good.inventory}</em>件</p>
							<p class="product-other">
								<span>
									<em data-other>
										<c:if test="${fn:length(goodDetailVo.skuList)==1}">
											<c:out value="${goodDetailVo.skuList[0].skuPropertiesName}" escapeXml="false"/>
										</c:if>
									</em>
								</span>
							</p>
						
							<!-- 
							<p class="product-title"><c:out value="${goodDetailVo.good.name}" escapeXml="false"/></p>
							<p class="product-other"><b class="clr-danger">¥<span data-price><fmt:formatNumber pattern="0.00" value="${goodDetailVo.good.salesPrice/100}"/></span></b></p>
							<p class="product-other clr-light"><em data-other>
							<c:if test="${fn:length(goodDetailVo.skuList)==1}">
							<c:out value="${goodDetailVo.skuList[0].skuPropertiesName}" escapeXml="false"/>
							</c:if>
							</em></p>
							 -->
						</div>
						<a class="a-close-dialog" data-id="closedialog"><i class="iconfont icon-iconfontdianhua1"></i></a>
					</div>
				</div>
			</div>
		</div>
		<!-- 显示销售属性或非关键属性 -->
		<c:choose>
			<c:when test="${goodDetailVo.good.classificationId==14}">
				<input type="hidden" id="skuId" name="id"
				<c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}"> value="${goodDetailVo.skuList[0].id}" </c:if>>
				<div class="groupskulist">
					<div class="group-item" data-attrs='salesProp'
						 <c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}">
							style="display: none;"
						 </c:if>>
						<div class="group-rowspan">
							<div class="group-colspan pd30">
								<h3 style="padding-bottom:5px;">属性</h3>
								<p>
									<c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}">
										<a href="javascript:void(0)" class="btn-a active" data-id="${sku.id}" data-attr="${sku.id}">${good.name}</a>
									</c:if>
									<c:if test="${fn:length(goodDetailVo.skuList) > 1}">
									<c:forEach items="${goodDetailVo.skuList}" var="sku">
										<a href="javascript:void(0)" class="btn-a" data-id="${sku.id}" data-attr="${sku.id}">${sku.skuPropertiesName}</a>
									</c:forEach>
									</c:if>
								</p>	
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:when test="${not empty goodDetailVo.salesPropMap}">
				<input type="hidden" id="skuId" name="id" <c:if test="${fn:length(goodDetailVo.skuList)==1}"> value="${goodDetailVo.skuList[0].id}" </c:if> >
				<c:forEach items="${goodDetailVo.salesPropMap}" var="propMap" varStatus="status">
					<div class="groupskulist">
						<div class="group-item" data-attrs='salesProp'
							<c:if test="${fn:length(goodDetailVo.skuList)==1}">
								style="display: none;"
							</c:if>>
							<div class="group-rowspan">
								<div class="group-colspan pd30">
									<c:if test="${goodDetailVo.salesPropSize >1}">
										<h3 style="padding-bottom:5px;">${propMap.key}</h3>
									</c:if>
									<p>
										<c:forEach items="${propMap.value}" var="goodProp">
											<a href="javascript:void(0)" class="btn-a <c:if test='${fn:length(goodDetailVo.skuList) == 1}'>active</c:if>" data-id="${goodProp.id}" data-attr="${goodProp.id}">${goodProp.propValueName}</a>
										</c:forEach>
									</p>	
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<input type="hidden" id="skuId" name="id" value="${goodDetailVo.skuList[0].id}">
				<c:if test="${not empty goodDetailVo.notKeyPropList}">
					<div class="groupskulist">
						<div class="group-item" data-attrs>
							<div class="group-rowspan">
								<div class="group-colspan pd30">
									<h3 style="padding-bottom:5px;">属性</h3>
									<c:forEach items="${goodDetailVo.notKeyPropList}" var="goodProp">
										<p><c:out value="${goodProp.propName}" escapeXml="false"/>：<c:out value="${goodProp.propValueAlias}" escapeXml="false"/></p>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<div class="group-item group-buy-num" id="j-tota">
			<div class="group-rowspan">
				<div class="group-colspan">
					<h3 class="goods-num">购买数量</h3>
					<div class="addel" data-module="addNumber">
						<input type="hidden" data-id="goodStock" value="${goodDetailVo.good.inventory}">
					    <span class="addel-del" id="DelGoodsNum" data-type="del"><i class="iconfont icon-jianhao1 disabled"></i></span>
					    <span class="addel-info"><input type="tel" value="1" name="quantity" data-id="number" id="number"></span>
					    <span class="addel-add" id="AddGoodsNum" data-type="add"><i class="iconfont icon-jiahao <c:if test="${goodDetailVo.good.inventory <= 0 }">disabled</c:if>"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
		</form>
</div>
<script type="text/javascript">
var saleGoodsSku=JSON.parse('${goodDetailVoJson}');

$(document).ready(function() {
	//模拟商品数据
    var  goodData='';//price单位是分，需要转换
    var attrnum= Number(${goodDetailVo.salesPropSize}),//需要告诉确认商品属性的个数
	goodId = $('#goodId').val();//商品ID
	var $attrItem=$('[data-attrs] [data-attr]');
	$attrItem.on('click',function(event) {
		if($(this).is('.active')){
			return false;
		}
		$('[data-id="number"]').val(1);
		$(this).addClass('active').siblings().removeClass('active');//点击切换选中状态
		attributeReplace.getTempData();
	});
	//获取商品JSON 不同颜色商品切换
	var attributeReplace = {
		srcc: '',
		inventory: 0,
        tempStorage:[],//请求过来的数据临时存储容器
		type:'',
		getTempData:function(){ //获取商品 ID 购买数量 number 商品价格 salesPrice
		     var skulist=saleGoodsSku.data.skulist,
             skulistitem="",
             skuactiveitem="",
             activeText="",
             activeLen=0;
	         $attrItem.each(function(){
		        		var $obj=$(this);
		        		if ($obj.is('.active')) {
		        			skuactiveitem+=$obj.data("id")+",";
		        			activeText+=$obj.text()+"、";
		        			activeLen+=1;
		        		}
		
		     }); 
	         if (activeLen==saleGoodsSku.data.salesPropMapSize) {
		         skuactiveitem=skuactiveitem.substring(0,skuactiveitem.length-1);
				 skuactiveitem=skuactiveitem.split(',');
				 activeText=activeText.substring(0,activeText.length-1);
			     for (var item in skulist) {
			    	 var itemSub=item;
			    	 if(/^;/.test(item)){
			       	 	itemSub=item.substring(1);
			    	 }
			    	 if(/;$/.test(item)){
				     	itemSub=itemSub.substring(0,itemSub.length-1);
			    	 }
				       	  skulistitem=itemSub.split(';');
				       	 if (skuactiveitem.sort().toString() ==skulistitem.sort().toString()) {
				       		 this.conAttr(skulist[item],activeText);//初始化返回数据
				       		 break;
				       	}
				       	 
		          }
	         }
		},
		conAttr: function(callback,text){
            var goodsImg=$('#j-shopimg').find('img'), //商品图片dom
            oldSrc=goodsImg.attr('src');    //商品图片的路劲
            if(null != callback.picturePath && ''!=callback.picturePath){
            	  this.srcc=ROOT_PICURL+callback.picturePath;  //获取返回来的商品图片路径
                  goodsImg.attr('src',this.srcc);//商品图片地址需要修改
            }
            $('#skuId').val(callback.id);
         	$('[data-id="inventory"]').text(callback.stock);
			$('[data-price]').text(callback.salesPrice/100);
			$('[data-other]').text(text); //属性文本填充
			$('[data-id="goodStock"]').val(callback.stock);
			var $button=$('.aui_buttons button');
            if(callback.stock==0){
                $('[data-id="number"]').val(callback.stock);
                
            }else{
            	$button.attr('disabled',false);
            	
            }
		}
	}


//添加商品数量
	var addNumber = function(){
		var numberVal,numberDom,inventory,moduleDom,goodPrice,total = parseFloat($("#talpice").text()),ostock = 0;

		var totalPrice = function(price){
			total += parseFloat(price);
			$("#talpice").text(parseFloat(total).toFixed(2));
			if(total == 0){
				$('[data-id="sub"]').attr('disabled','disabled');
			}else{
				$('[data-id="sub"]').removeAttr('disabled');
			}
		}

		function setNumber(self,invent){
			moduleDom = self.parents('[data-module="addNumber"]');
			var priceDom = moduleDom.find('[data-id="goodPrice"]'),
				stockDom = moduleDom.find('[data-id="goodStock"]');

			goodPrice = priceDom.val();

			inventory = stockDom.length >0 ? parseInt(stockDom.val()) : parseInt(moduleDom.find('[data-id="inventory"]').text());
			numberVal = self.val().replace(/\D/g,'');
			if(numberVal>inventory){
				numberVal = inventory;
			}
			self.val(numberVal);
			if(numberVal == '') numberVal = 1;
			totalPrice((numberVal - ostock)*goodPrice);
			ostock = numberVal;
		}
		//绑定标签
		function setDom(self,invent){

			moduleDom = self.parents('[data-module="addNumber"]');
			numberDom = moduleDom.find('[data-id="number"]');
			var priceDom = moduleDom.find('[data-id="goodPrice"]'),
				stockDom = moduleDom.find('[data-id="goodStock"]');

			goodPrice = priceDom.val();


			inventory = stockDom.length >0 ? parseInt(stockDom.val()) : parseInt(moduleDom.find('[data-id="inventory"]').text());

			numberVal = parseInt(numberDom.val());
		}

		//减少商品
		var $delGoodsNumi=$("#DelGoodsNum i"),
		 	$addGoodsNumi=$("#AddGoodsNum i");
		function reduceShop(){
			if(numberVal>1){
				--numberVal;
				totalPrice(-goodPrice);
			}else{
				if(numberVal=="0"){
					return false;
				}
				numberVal = 1;
			}
			numberDom.val(numberVal);
			 if(numberVal==1){
					$delGoodsNumi.addClass("disabled");
			  }
			 else{
				$addGoodsNumi.removeClass("disabled");
			  }
		}
		//添加商品
		function addShop(){

			if(numberVal>=Number(inventory)){
				numberVal = inventory
			}else{
				++numberVal;
				totalPrice(goodPrice);
			}
			numberDom.val(numberVal);
			  if(numberVal==Number(inventory)){
					$addGoodsNumi.addClass("disabled");
				}
				else{
					$delGoodsNumi.removeClass("disabled");
				}
		}

		//self 减少添加Dom，invent 库存
		function inputNumber (self,invent){
			var type = self.attr('data-type');
			setDom(self,invent);
			if(type == "add"){
				addShop();
			}else if(type == "del"){
				reduceShop();
			}
		}
		//添加商品数量绑定事件
		/* $('[data-module="addNumber"]').on('click','[data-type]',function(){
			inputNumber($(this));
		}); */
		var objAdd = document.getElementById('AddGoodsNum');
		objAdd.addEventListener('touchstart', function(event,obj) {
			inputNumber($(event.currentTarget));
		});
		var objDel = document.getElementById('DelGoodsNum');
		objDel.addEventListener('touchstart', function(event,obj) {
				
			inputNumber($(event.currentTarget));
		});
		//添加商品数量绑定事件
		$('[data-module="addNumber"]').on('input','[data-id="number"]',function(){
			setNumber($(this));
		});

		//添加商品数量绑定事件
		$('[data-module="addNumber"]').on('blur','[data-id="number"]',function(){
			if(this.value == '' || this.value == 0 ){
				this.value = 1;
			}
		});

		$('[data-module="addNumber"]').on('focus','[data-id="number"]',function(){
			ostock = this.value;
		});

		return {
			inputNumber:inputNumber,
			setNumber:setNumber
		}
	}();
	});
</script>