<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../common/base.jsp"%>
<!-- 滑出层：选择多属性 -->
<form id="buyForm" action="" method="post">
<div id="j-contentSpecial" style="display:none;">
<input type="hidden" id="goodId" name="goodId" value="${goodDetailVo.good.id}">
<input id="price" type="hidden" value="${goodDetailVo.good.salesPrice/100}"/>
	<input type="hidden" id="gid" name="gid" value="${goodDetailVo.good.id}">
	<div class="group group-cleartop">
		<div class="group-item">
			<div class="group-rowspan">
				<div class="group-colspan">
					<div class="product">
						<a href="javascript:void(0);" class="product-item">
							<div class="product-img" id="j-shopimg">
								<img src="${picUrl}${goodDetailVo.good.picUrl}" width="80" height="80" alt="产品图片">
							</div>
							<div class="product-info">
								<p class="product-title"><c:out value="${goodDetailVo.good.name}" escapeXml="false"/></p>
								<p class="product-other"><b class="clr-danger">¥<span data-price1><fmt:formatNumber pattern="0.00" value="${goodDetailVo.good.salesPrice/100}"/></span></b></p>
								<p class="product-other clr-light"><em data-other></em></p>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 显示销售属性或非关键属性 -->

		<c:choose>
			<c:when test="${goodDetailVo.good.classificationId==14}">
				<input type="hidden" id="skuId" name="id" 
				<c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}"> value="${goodDetailVo.skuList[0].id}" </c:if>>
					<div class="group-item"  id="j-color" data-attrs="salesProp"
					<c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}">
							style="display: none;"
					</c:if>>
						<div class="group-rowspan">
							<div class="group-colspan">
								<h3 style="padding-bottom:5px;">属性</h3>
								<p>
									<c:if test="${empty goodDetailVo.skuList[0].skuPropertiesName or goodDetailVo.skuList[0].skuPropertiesName=='' or fn:length(goodDetailVo.skuList)==1}">
										<a href="javascript:void(0)" class="btn btn-default active" data-id="${sku.id}" data-attr="${sku.id}">sku</a>
									</c:if>
									<c:if test="${fn:length(goodDetailVo.skuList) > 1}">
									<c:forEach items="${goodDetailVo.skuList}" var="sku">
										<a href="javascript:void(0)" class="btn btn-default" data-id="${sku.id}" data-attr="${sku.id}">${sku.skuPropertiesName}</a>
									</c:forEach>
									</c:if>
								</p>	
							</div>
						</div>
					</div>
			</c:when>
			<c:when test="${not empty goodDetailVo.salesPropMap}">
				<input type="hidden" id="skuId" name="id"<c:if test="${fn:length(goodDetailVo.skuList)==1}"> value="${goodDetailVo.skuList[0].id}" </c:if>>
				<c:forEach items="${goodDetailVo.salesPropMap}" var="propMap">
					<div class="group-item" id="j-color" data-attrs
					<c:if test="${fn:length(goodDetailVo.skuList)==1}">
					style="display: none;"
					</c:if>>
						<div class="group-rowspan">
							<div class="group-colspan">
								<c:if test="${goodDetailVo.salesPropSize >1}">
									<h3 style="padding-bottom:5px;">${propMap.key}</h3>
								</c:if>
								<p>
									<c:forEach items="${propMap.value}" var="goodProp">
										<a href="javascript:void(0)" class="btn btn-default <c:if test='${fn:length(goodDetailVo.skuList) == 1}'>active</c:if>" data-id="${goodProp.id}" data-attr="${goodProp.id}">${goodProp.propValueName}</a>
									</c:forEach>
								</p>	
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<input type="hidden" id="skuId" name="id" value="${goodDetailVo.skuList[0].id}">
				<c:if test="${not empty goodDetailVo.notKeyPropList}">
				<div class="group-item" id="j-color" data-attrs>
					<div class="group-rowspan">
						<div class="group-colspan">
							<h3>属性</h3>
							<c:forEach items="${goodDetailVo.notKeyPropList}" var="goodProp">
								<p><c:out value="${goodProp.propName}" escapeXml="false"/>：<c:out value="${goodProp.propValueAlias}" escapeXml="false"/></p>
							</c:forEach>
						</div>
					</div>
				</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<div class="group-item" id="j-tota">
			<div class="group-rowspan">
				<div class="group-colspan">
					<h3 style="padding-bottom:5px;">购买数量</h3>
					<div class="addel" data-module="addNumber">
						<input type="hidden" data-id="goodStock" value="${goodDetailVo.good.inventory}">
					    <span class="addel-del" data-type="del" id="DelGoodsNum"><i class="iconfont icon-jianhao"></i></span>
					    <span class="addel-info"><input type="tel" value="1" name="quantity" data-id="number" id="number"></span>
					    <span class="addel-add" data-type="add" id="AddGoodsNum"><i class="iconfont icon-add"></i></span>
					</div>
					<span class="right clr-light" style="line-height:34px;">库存:<em data-id="inventory" id="inventory">${goodDetailVo.good.inventory}</em>件</span>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<script type="text/javascript">
$(document).ready(function() {
	//模拟商品数据
    var  goodData='';//price单位是分，需要转换
    var attrnum= Number(${goodDetailVo.salesPropSize}),//需要告诉确认商品属性的个数
	goodId = $('#goodId').val();//商品ID
	$('[data-attrs] [data-attr]').on('click',function(event) {
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
			var shopArr;
            var i= 0,
            activeId='', //获取属性序列的激活的属性
            activeText='';//获取属性序列的激活的文本
            for(var i=0;i<attrnum; i++){
                (function(i){
                    var activeObj = $('[data-attrs]').eq(i).find('.active'); //获取属性序列激活的dom
                    activeId+=";"+activeObj.data('attr');//获取属性序列的激活的属性
                    activeText+="、"+activeObj.text(); //获取属性序列的激活的文本
                })(i);
            };
            if('${goodDetailVo.good.classificationId}'=='14'){
            	 var activeObj = $('[data-attrs]').find('.active'); //获取属性序列激活的dom
                 activeId=activeObj.data('attr');//获取属性序列的激活的属性
                 activeText=activeObj.text(); //获取属性序列的激活的文本
            }else{
            	activeId=activeId.substring(1);//去除第一个分号
            	activeText=activeText.substring(1);//去除第一个分号
            }
            var activeArr = activeId.toString().split(';');
            //属性都选择好了之后开始做数据请求
            // --- 检查是否属性都选着好了---
            var activeBool=false;
            for(var activeKey in activeArr){
                if(activeArr[activeKey]=='undefined'){
                    activeBool=false;
                    break;
                }else{
                    activeBool=true
                }
            };
            //---如果activeBool(属性都选择好了) 为true 发送请求这里开始异步请求---
            var callbackData;
            if(activeBool){
                var flag=false;
                if(this.tempStorage.length){
                    for(var key in this.tempStorage){
                        if(activeArr.join(';')==this.tempStorage[key].skuProperties){
                            flag=true;
                            this.conAttr(this.tempStorage[key],activeText);//初始化返回数据
                            break;
                        }else{
                            flag=false;
                        }
                    };
                } ;
                if(!flag){
                    $.post("${ctx}/wap/${sessionShopNo}/any/selectSku${ext}",
                    	{ 
                    	"goodId": goodId, 
                    	"skuProperties":activeArr.sort().join(';')
                    	}, 
                    	function(result){ 
                    		var data = JSON.parse(result);
                			if(data.success){
                		          callbackData = JSON.parse(data.info);
                                  attributeReplace.tempStorage.push(callbackData);
                                  attributeReplace.conAttr(callbackData,activeText);//初始化返回数据
                			}
                    	}
                    );
                };
            };
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
			$('[data-price1]').text(callback.salesPrice/100);
			$("#price").val(callback.salesPrice/100);
			$('[data-other]').text(text); //属性文本填充
			$('[data-id="goodStock"]').val(callback.stock);
            if(callback.stock==0){
                //$('.aui_buttons button').attr('disabled',true);
                $('[data-id="number"]').val(callback.stock);
            }else{
                //$('.aui_buttons button').attr('disabled',false);
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
		function reduceShop(){
			if(numberVal>1){
				--numberVal;
				totalPrice(-goodPrice);
			}else{
				if(numberVal==0){
					return false;
				}
				numberVal = 1;
			}
			numberDom.val(numberVal);
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