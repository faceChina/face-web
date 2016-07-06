<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../common/base.jsp"%>
<c:set var="address" value="${cartOrderVo.address}"/>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-购物车-订单确认</title>

<link rel="stylesheet" type="text/css" href="${resourcePath}styles/public.css?ver=008">
<script type="text/javascript" src="${resourceBasePath}js/jquery-2.0.3.min.js"></script>

<script type="text/javascript" src="${resourceBasePath}js/artDialog/jquery.artDialog.js?ver=008"></script>
<script type="text/javascript" src="${resourceBasePath}js/artDialog/iframeTools.js?ver=008"></script>
<link rel="stylesheet" type="text/css" href="${resourceBasePath}js/artDialog/skins/myself.css?ver=008">

<script type="text/javascript" src="${resourceBasePath}js/slideout-jquery.js?ver=008"></script>
<script type="text/javascript" src="${resourceBasePath}js/tab-jquery.js?ver=008"></script>

<script type="text/javascript" src="${resourcePath }js/addel-radius.js?ver=008"></script>
<script type="text/javascript" src="${resourcePath}js/form-submit.js?ver=008"></script>
<script type="text/javascript" src="${resourcePath}js/onoff.js?ver=008"></script>
<script type="text/javascript" src="${resourcePath}js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="${resourcePath}js/validata/additional-methods.js?ver=008"></script>
<script type="text/javascript" src="${resourcePath}js/mob-public.js?ver=008"></script>

<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js?ver=008"></script>

<link rel="stylesheet" type="text/css" href="${resourcePath }cart/css/main.css">
<script type="text/javascript" src="${resourcePath}js/plugin/area.js"></script>
<script type="text/javascript" src="${resourcePath}cart/js/addel.order.js"></script>
<script type="text/javascript">
var peList;
var cityList;
var selectPe = function() {
	var pe = $("#pe").val();
	$("#city").empty();
	cityList = null;
	$("#area").empty();
	initCity(pe);
	initArea(0);
};

var selectCity = function() {
	var city = $("#city").val();
	$("#area").empty();
	initArea(city);
};
var selectArea = function() {
	var area = $("#area").val();
	$('#areaCode').val(area);
};
var initCity = function(pe) {
	var option = "<option value='0'>请选择市...</option>";
	$("#city").append(option);
	if(0!=pe){
		for ( var i = 0; i < peList.length; i++) {
			if (pe == peList[i].id) {
				cityList = peList[i].children;
				for ( var i = 0; i < cityList.length; i++) {
					var city = cityList[i];
					var option = "<option value='" + city.id + "'>" + city.name
							+ "</option>";
					$("#city").append(option);
				}
				return;
			}
		}
	}
};
var initArea = function(city) {
	var option = "<option value='0'>请选择区...</option>";
	$("#area").append(option);
	if(0!=city){
		for ( var i = 0; i < cityList.length; i++) {
			if (city == cityList[i].id) {
				var areaList = cityList[i].children;
				for ( var i = 0; i < areaList.length; i++) {
					var area = areaList[i];
					var option = "<option value='" + area.id + "'>" + area.name
							+ "</option>";
					$("#area").append(option);
				}
				return;
			}
		}
	}
};
$(document).ready(function() {
	var option = "<option value='0'>请选择省...</option>";
	$("#pe").append(option);
	peList = areaP.children;
	for ( var i = 0; i < peList.length; i++) {
		var pe = peList[i];
		var option = "<option value='" + pe.id + "'>" + pe.name
				+ "</option>";
		$("#pe").append(option);
	}
	$("#pe").val("0");
  	var len = $(".top").length;
	if(len != 0){
		$("#line2").hide(); 
	}
	$("#pe").val('0');
	selectPe();
	$("#city").val('0');
	selectCity();
	$("#area").val('0');
});
</script>
</head>
<body>
<div id="box">
	<!-- 商品信息及操作 -->
	<form id="jform" action="/wap/${sessionShopNo }/buy/order/orderBuy.htm" method="post">
	<input type="hidden" id="addressId" name="addressId" value="${address.id }">
		<input type="hidden" name="buyType" value="${cartOrderVo.buyType }"/>
	<input type="hidden" id="validateToken" name="validateToken" value="${validateToken }">
	<div id="j-cart">
		<!-- 无送货地址时 -->
		<div class="group group-justify group-noborder group-small" id="j-noAddress" onclick="toCreateAddress(this)">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<span data-address>请新建收货地址，确保商品顺利达到</span>
					</div>
					<div class="group-colspan">
						<i class="iconfont icon-right"></i>
					</div>
				</div>
			</div>
		</div>
		<div class="group group-justify group-noborder group-small list-address" id="j-haveAddress" onclick="toSelectAddress(this)">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan">
						<b>收货人：<em data-address-name>${address.name }</em></b>
					</div>
					<div class="group-colspan">
						<b class="right" data-address-phone>${address.cell }</b>
					</div>
				</div>
				<div class="group-rowspan">
					<div class="group-colspan">
						<span data-address>${address.addressDetail }</span>
					</div>
					<div class="group-colspan">
						<i class="iconfont icon-right"></i>
					</div>
				</div>
			</div>
		</div>
		<!-- 有送货地址时 -->
		<!-- 开始遍历卖家 -->
		<%@ include file="temp/cart-order-all.jsp"%>
		
		<!-- 提交订单-->
		<div class="footfix">
			<div class="group">
				<div class="group-item">
					<div class="group-rowspan">
						<div class="group-colspan">
							<p>共 <em class="clr-danger" data-total-number></em> 件</p>
						</div>
						<div class="group-colspan">
							<p class="clr-danger">总计：￥<strong data-total-price></strong></p>
						</div>
						<div class="group-colspan" style="text-align:right;">
							<button class="btn btn-danger btn-twords" type="button" onclick="toSubmit()">确认</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
	
	<!-- 新建送货地址 -->
	<div id="j-createAddress" data-address-id="" style="display:none;">
		<form id="j-form" action="#">
			<input id="areaCode" type="hidden" name="vAreaCode" value="${address.vAreaCode }" />
			<div class="group group-others width60">
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">联系人</li>
						<li class="group-colspan"><input type="text" placeholder="请输入您的姓名" name="contact" id="contact" class="form-control" ></li>
						<li class="group-colspan"></li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">联系电话</li>
						<li class="group-colspan"><input type="text" placeholder="请输入您的手机号码" name="phone" id="phone" class="form-control" ></li>
					</ul>
				</div>
				<!-- <div class="form-group">
			  		<select class="form-control input-short-3" title="省"  onchange="selectPe();" style="width:33%;"></select><select class="form-control input-short-3" title="市" id="city" onchange="selectCity();" style="width:33%;margin-left:0.33%;"></select><select  class="form-control input-short-3" title="区" id="area" onchange="selectArea();" style="width:33%;margin-left:0.33%;"></select> 
			    </div> -->
				<div class="group-item">
				  <ul class="group-rowspan">
						<li class="group-colspan">省</li>
						<li class="group-colspan" >
							<select id="pe" class="form-control" onchange="selectPe();">
							</select>
						</li>
						
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">市</li>
						<li class="group-colspan">
							<select class="form-control" id="city" onchange="selectCity();">
							</select>
						</li>
					</ul>
				</div>
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">区/县</li>
						<li class="group-colspan"  >
							<select id="area" class="form-control" onchange="selectArea();" >
							</select>
						</li>
						
					</ul>
				</div>
					
				<div class="group-item">
					<ul class="group-rowspan">
						<li class="group-colspan">详细地址</li>
						<li class="group-colspan"><input type="text" placeholder="请填写您的具体地址" name="address" id="address" class="form-control" ></li>
					</ul>
				</div>
			</div>
			
			<!-- 保存地址 按钮 -->
			<div class="button">
				<button type="button" class="btn btn-danger btn-block" onclick="toSaveAddress()">保存地址</button>
			</div>
		</form>
	</div>
	
	
	<!-- 送货地址选择及管理 -->
	<div id="j-address" style="display:none;">
		<c:forEach items="${cartOrderVo.addressList }" var="addr">
		<div class="group group-justify group-small">
			<div class="group-item" onclick="toAddAddress(this)" val="${addr.id }">
				<div class="group-rowspan">
					<div class="group-colspan">
						<b data-address-name>${addr.name }</b>
					</div>
					<div class="group-colspan">
						<b data-address-phone>${addr.cell }</b>
					</div>
				</div>
				<div class="group-rowspan">
					<div class="group-colspan">
						<span class="clr-light" data-address>${addr.addressDetail }</span>
					</div>
					<div class="group-colspan">
					<c:if test='${addr.isDefault==1 }'>
						<i class="iconfont icon-roundcheckfill clr-danger" data-iconfont style="font-size:24px;"></i>
					</c:if>
					<c:if test='${addr.isDefault!=1 }'>
						<i class="iconfont clr-danger" data-iconfont style="font-size:24px;"></i>
					</c:if>
					</div>
				</div>
			</div>
			<div class="addressEdit group-item" data-id="${addr.id }" data-area-code="${addr.vAreaCode }">
				<div class="group-rowspan">
					<c:if test="${addr.isDefault==1 }">
					<div class="group-colspan">
						<span onclick="setDefault(this,${addr.id})" data-default-address class="clr-danger"><i class="iconfont icon-locationfill" style="font-size:24px;"></i> 默认</span>
					</div>
					</c:if>
					<c:if test="${addr.isDefault!=1 }">
					<div class="group-colspan">
						<span onclick="setDefault(this,${addr.id})" data-default-address><i class="iconfont icon-location" style="font-size:24px;"></i> 设为默认</span>
					</div>
					</c:if>
					<div class="group-colspan">
						<span onclick="editAddress(this,${addr.id})"><i class="iconfont icon-edit" style="font-size:24px;"></i> 编辑</span>
						<span onclick="delAddress(this,${addr.id})"><i class="iconfont icon-shanchu" style="font-size:24px;"></i> 删除</span>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
		<!-- 管理收货地址 按钮 -->
		<div class="button">
			<a href="javascript:void(0)" class="btn btn-default btn-block" onclick="toCreateAddress2(this)">+ 添加收货地址</a>
		</div>
	</div>
	<!-- 送货地址选择及管理 end -->
	
	
	<div id="j-delivery" data-delivery-id="" style="display:none;">
		
	</div>
<script type="text/javascript">
var addressObj = '${not empty address}'=='true';
$(function(){
	//根据是否有默认地址，显示相应的地址界面
	var showAddress = (function (){
		if(addressObj){
			$("#j-noAddress").hide();
			$("#j-haveAddress").show();
		}else{
			$("#j-noAddress").show();
			$("#j-haveAddress").hide();
		}
	})();
	
	//选择数量
	$("[data-shop-id]").each(function(index,element){
		var thiz = $(this);
		thiz.find("[data-pro-id]").each(function(key,dom){
			//初始加载页面时，获取各商品库存
			var stock = $(this).find("input[data-stock]").val(),
			   $obj=$(this),
			   $seller=$obj.parents(".seller");
			$obj.find("input[data-number]").on('input',function(){
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
                getShopTotalPrice($seller);
            });
			
			$obj.addel({
				number:stock,
				add:function(){
				},
				del:function(){
				}
			});
		});
	});
	
	//各店铺总数及总额
	calShopGoodsQuantity();
	getShopTotalPrice($("#box"));
	
	//总数及总额
	calGoodsQuantity();
});

//店铺ID、地址ID
var shopId = null, addressId =null;

//去选择地址
function toSelectAddress(thiz){
	//隐藏主体页面
	$("#j-cart").hide();
	//载入地址选择页面
	$("#j-address").show();
}

//无地址时，新建一个
function toCreateAddress(thiz){
	//设置地址ID
	addressId = $("#j-createAddress").data("addressId",1).attr("data-address-id",1);
	//隐藏主体页面、地址页面
	$("#j-cart").hide();
	$("#j-address").hide();
	//载入创建地址页面
	$("#j-createAddress").show();
}

//在地址管理界面中，新建一个地址
function toCreateAddress2(thiz){
	//设置地址ID
	addressId = $("#j-createAddress").data("addressId",3).attr("data-address-id",3);
	//隐藏地址页面
	$("#j-address").hide();
	//载入创建地址页面
	$("#j-createAddress").show();
	//清除form表单数据
	$("#j-createAddress").find("input[name='contact']").val("");
	$("#j-createAddress").find("input[name='area']").val("");
	$("#j-createAddress").find("input[name='address']").val("");
	$("#j-createAddress").find("input[name='phone']").val("");
}

//创建并保存地址
function toSaveAddress(){
	//地址表单验证
	var bool = $("#j-form").validate({
		rules:{
			contact:{
				required:true,
				rangelength:[2,15]
			},
			area:{
				required:true
			},			
			address:{
				required:true,
				rangelength:[5,60]
			},
			phone:{
				required:true,
				mobile:true
			}
		},
		messages:{
			contact:{
				required:"请输入您的姓名",
				rangelength:$.validator.format("姓名长度{0}-{1}位")
			},
			area:{
				required:"请输入地区"
			},
			address:{
				required:"请输入地址",
				rangelength:$.validator.format("地址长度{0}-{1}位")
			},
			phone:{
				required:"请输入您的联系电话",
				mobile:"请输入正确的手机号码"
			}
		}
	}).form();
	var vAreaCode=$("#areaCode").val()
	if(bool){
		if($("#pe").val()==null||$("#pe").val()==0){
			artTip("收货地址省份不能为空");
			return;
		}
		if($("#city").val()==null||$("#city").val()==0){
			artTip("收货地址城市不能为空");
			return;
		}
		if($("#area").val()==null||$("#area").val()==0){
			artTip("收货地址地区不能为空");
			return;
		}
		//获取地址ID
		var currentAddressId = $("#j-createAddress").data("addressId");
		if(currentAddressId == 1){
			//获取数据
			var addressFormData = getAddressForm();
			$.post("/wap/${sessionShopNo}/buy/address/add.htm",{'name':addressFormData.contact,'cell':addressFormData.phone,'addressDetail':addressFormData.address,'vAreaCode':vAreaCode},function(data){
				//添加页面
				setAndInSertDefaultAddress(addressFormData.contact,addressFormData.phone,addressFormData.address,data.id,vAreaCode);
				//将数据显示到页面
				$("#j-cart").find("[data-address-name]").text(addressFormData.contact);
				$("#j-cart").find("[data-address-phone]").text(addressFormData.phone);
				$("#j-cart").find("[data-address]").text(addressFormData.address);
				//显示主体页面、地址页面
				$("#j-cart").show();
				$("#j-haveAddress").show();
				$("#addressId").val(data.id);
				//隐藏创建地址页面、初始无地址页面
				$("#j-createAddress").hide();
				$("#j-noAddress").hide();
				getShopTotalPrice($("#box"));
			},'json')
		}else if(currentAddressId == 2){
			//获取数据
			var addressFormData = getAddressForm();
			$.post("/wap/${sessionShopNo}/buy/address/edit.htm",{'id':$("#j-createAddress").attr('data-address-id'),'name':addressFormData.contact,'cell':addressFormData.phone,'addressDetail':addressFormData.address,'vAreaCode':vAreaCode},function(data){
				//将数据显示到页面
				$("#j-address").find(".group.selected").find("[data-address-name]").text(addressFormData.contact);
				$("#j-address").find(".group.selected").find("[data-address-phone]").text(addressFormData.phone);
				$("#j-address").find(".group.selected").find("[data-address]").text(addressFormData.address);
				//去除标记
				$("#j-address").find(".group.selected").removeClass("selected");
				$("#j-address").find(".addressEdit[data-id="+$("#j-createAddress").attr('data-address-id')+"]").data('area-code',vAreaCode);
				//显示地址页面
				$("#j-address").show();
				//隐藏创建地址页面
				$("#j-createAddress").hide();
			})
		}else if(currentAddressId == 3){
			var addressFormData = getAddressForm();
			$.post("/wap/${sessionShopNo}/buy/address/add.htm",{'name':addressFormData.contact,'cell':addressFormData.phone,'addressDetail':addressFormData.address,'vAreaCode':vAreaCode},function(data){
				setAndInSertAddress(addressFormData.contact,addressFormData.phone,addressFormData.address,data.id,vAreaCode);
				//显示地址页面
				$("#j-address").show();
				//隐藏创建地址页面
				$("#j-createAddress").hide();
			},'json')
		}
	}
}

//选中一个地址
function toAddAddress(thiz){
	//获取地址数据
	var contact = $(thiz).find("[data-address-name]").text();
	var phone = $(thiz).find("[data-address-phone]").text();
	var address = $(thiz).find("[data-address]").text();
	//将数据显示到页面
	$("#j-cart").find("[data-address-name]").text(contact);
	$("#j-cart").find("[data-address-phone]").text(phone);
	$("#j-cart").find("[data-address]").text(address);
	//更新选中状态
	$("#j-address").find(".icon-roundcheckfill").removeClass("icon-roundcheckfill");
	$(thiz).find("[data-iconfont]").addClass("icon-roundcheckfill");
	//显示主体页面
	$("#j-cart").show();
	$("#addressId").val($(thiz).attr('val'));
	//隐藏地址选择页面
	$("#j-address").hide();
	getTotalPrice();
	getShopTotalPrice($("#box"));
}

//获取地址
function getAddress(){
	var addressData = null;
	var name = $("#j-haveAddress").find("[data-address-name]").html();
	var phone = $("#j-haveAddress").find("[data-address-phone]").html();
	var address = $("#j-haveAddress").find("[data-address]").html();
	addressData = [name,phone,address];
	return addressData;
}

//获取地址表单数据
function getAddressForm(){
	var addressFormData = {};
	var contact = $("[name='contact']").val();
	var area = $("[name='area']").val();
	var address = $("[name='address']").val();
	var phone = $("[name='phone']").val();
	addressFormData.contact = contact;
	addressFormData.area = area;
	addressFormData.address = address;
	addressFormData.phone = phone;
	return addressFormData;
}

//创建地址节点
function setAndInSertAddress(contact,phone,address,id,code){
	var str ='' 
		+'<div class="group group-justify group-small">'
		+'	<div class="group-item" onclick="toAddAddress(this)" val="'+id+'" data-area-code='+code+'>'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<b data-address-name="">'+contact+'</b>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<b data-address-phone="">'+phone+'</b>'
		+'			</div>'
		+'		</div>'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<span class="clr-light" data-address="">'+address+'</span>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<i class="iconfont clr-danger" data-iconfont style="font-size:24px;"></i>'
		+'			</div>'
		+'		</div>'
		+'	</div>'
		+'	<div class="addressEdit group-item" data-id='+id+' data-area-code='+code+'>'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<span onclick="setDefault(this)" data-default-address=""><i class="iconfont icon-location" style="font-size:24px;"></i> 设为默认</span>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<span onclick="editAddress(this,'+id+')"><i class="iconfont icon-edit" style="font-size:24px;"></i> 编辑</span>'
		+'				<span onclick="delAddress(this,'+id+')"><i class="iconfont icon-shanchu" style="font-size:24px;"></i> 删除</span>'
		+'			</div>'
		+'		</div>'
		+'	</div>'
		+'</div>';
	$(str).insertBefore($("#j-address .button"));
}
//创建默认地址节点
function setAndInSertDefaultAddress(contact,phone,address,id,code){
	var str ='' 
		+'<div class="group group-justify group-small">'
		+'	<div class="group-item" onclick="toAddAddress(this)" val="'+id+'" data-area-code="code">'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<b data-address-name="">'+contact+'</b>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<b data-address-phone="">'+phone+'</b>'
		+'			</div>'
		+'		</div>'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<span class="clr-light" data-address="">'+address+'</span>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<i class="iconfont icon-roundcheckfill clr-danger" data-iconfont style="font-size:24px;"></i>'
		+'			</div>'
		+'		</div>'
		+'	</div>'
		+'	<div class="addressEdit group-item" data-area-code='+code+'>'
		+'		<div class="group-rowspan">'
		+'			<div class="group-colspan">'
		+'				<span onclick="setDefault(this)" data-default-address="" class="clr-danger"><i class="iconfont icon-locationfill" style="font-size:24px;"></i> 默认</span>'
		+'			</div>'
		+'			<div class="group-colspan">'
		+'				<span onclick="editAddress(this,'+id+')"><i class="iconfont icon-edit" style="font-size:24px;"></i> 编辑</span>'
		+'				<span onclick="delAddress(this,'+id+')"><i class="iconfont icon-shanchu" style="font-size:24px;"></i> 删除</span>'
		+'			</div>'
		+'		</div>'
		+'	</div>'
		+'</div>';
	$(str).insertBefore($("#j-address .button"));
}
//设为默认地址
function setDefault(thiz,id){
	$.post("/wap/${sessionShopNo}/buy/address/setDefault.htm",{"id":id},function(){
		$("#j-address").find("[data-default-address]").removeClass("clr-danger").html('<i class="iconfont icon-location" style="font-size:24px;"></i> 设为默认');
		$(thiz).addClass("clr-danger").html('<i class="iconfont icon-locationfill" style="font-size:24px;"></i> 默认');
	})
}

//删除地址
function delAddress(thiz,id){
	if($(thiz).parents("div.addressEdit").find("i.icon-locationfill").length>0){
		$.dialog.alert("默认收货地址不能删除")
	}else{
		art.dialog.confirm("你确定要删除吗？",function(){
			$.post("/wap/${sessionShopNo}/buy/address/delete.htm",{"id":id},function(){
				$(thiz).parents(".group").remove();
			})
		},function(){
			return true;
		})
	}
}

//编辑地址
function editAddress(thiz,id){
	//设置地址ID
	addressId = $("#j-createAddress").data("addressId",2).attr("data-address-id",id);
	var code=$(thiz).parents(".addressEdit").data('area-code')
	$("#areaCode").val(code);
	$.post('/wap/${sessionShopNo}/buy/address/get.htm',{'id':id},function(data){
		var json=JSON.parse(data);
		$("#pe").val(json.provice);
		selectPe();
		$("#city").val(json.city);
		selectCity();
		$("#area").val(json.county);
	})
	//标记当前编辑项
	$(thiz).parents(".group").addClass("selected");
	//获取数据
	var contact = $(thiz).parents(".group").find("[data-address-name]").html();
	var area = "";
	var address = $(thiz).parents(".group").find("[data-address]").html();
	var phone = $(thiz).parents(".group").find("[data-address-phone]").html();
	//显示数据
	$("#j-createAddress").find("input[name='contact']").val(contact);
//	$("#j-createAddress").find("input[name='area']").val(area);
	$("#j-createAddress").find("input[name='address']").val(address);
	$("#j-createAddress").find("input[name='phone']").val(phone);
	//隐藏地址页面
	$("#j-address").hide();
	//显示编辑地址页面
	$("#j-createAddress").show();
}

//去选择配送方式
function toSelectDelivery(thiz,shopNo){
	//获取当前点击的店铺ID
	shopId = $(thiz).parents(".group").data("shopId");
	//设置配送方式页面对应的店铺ID
	$("#j-delivery").data("deliveryId",shopId);
	var addressId=$("#addressId").val();
	if(!addressId){
		artTip('请先选择收货地址');
		return;
	}
	var arr=[];
	$(thiz).parents('[data-shop-id]').find('[data-good-id]').each(function(){
		var emp=true;
		for(var x in arr){
			if(arr[x].id==$(this).data('good-id')){
				arr[x].inventory=Number(arr[x].inventory)+Number($(this).find('input[type=tel]').val());
				emp=false;
			}
		}
		if(emp){
			var obj=new Object();
			obj.id=$(this).data('good-id')
			obj.inventory=$(this).find('input[type=tel]').val()
			arr.push(obj);
		}
	})
	var deliveryWay=$(thiz).parents('[data-shop-id]').find('[deliveryway]').val()
	var pickUpId=$(thiz).parents('[data-shop-id]').find('[pickupid]').val()
	$.post('/wap/${sessionShopNo}/buy/cart/delivery.htm',{'shopNo':shopNo,'json':JSON.stringify(arr),'addressId':addressId,'deliveryWay':deliveryWay,'pickUpId':pickUpId},function(data){
		$("#j-delivery").html(data);
		//隐藏主体页面
		$("#j-cart").hide();
		//载入配送方式选择页面
		$("#j-delivery").show();
		
	},'html')
}

//选中一个配送方式
function toAddDelivery(thiz,deliveryWay,id,postFee){
	//获取当前店铺ID
	var shopIdOfDelivery = $("#j-delivery").data("deliveryId");
	if(shopIdOfDelivery == shopId){
		//获取数据
		//var deliveryname = $(thiz).parents(".group").find("[data-delivery-name]").text();
		var deliveryname;
		if (postFee == undefined) {
			deliveryname = $(thiz).parents(".group").find("[data-delivery-name]").text();
		} else {
			deliveryname = $(thiz).parents(".group").find("h3").text();
		}
		//将数据显示到页面
		//$("#j-cart").find("[data-shop-id='"+shopIdOfDelivery+"']").find("[data-delivery-name]").html(deliveryname);
		$("#j-cart").find("[data-shop-id='"+shopIdOfDelivery+"']").find("[data-delivery-name]").html(deliveryname);
		$("#j-cart").find("[data-shop-id='"+shopIdOfDelivery+"']").find('input[deliveryway]').val(deliveryWay);
		$("#j-cart").find("[data-shop-id='"+shopIdOfDelivery+"']").find('input[pickupid]').val(id);
		$("#j-cart").find("[data-shop-id='"+shopIdOfDelivery+"']").find('input[data-freight]').val(postFee);
		//更新选中状态
		$("#j-delivery").find(".icon-roundcheckfill").removeClass("icon-roundcheckfill");
		$(thiz).find("[data-iconfont]").addClass("icon-roundcheckfill");
		//店铺总额
		getShopTotalPrice($("#box"));
		//显示主体页面
		$("#j-cart").show();
		//隐藏地址选择页面
		$("#j-delivery").hide();
	}
}

//计算店铺内商品总件数
function calShopGoodsQuantity(){
	$("[data-shop-id]").each(function(index,element){
		var thiz = $(this);
		var count = 0;
		thiz.find("[data-pro-id]").each(function(key,dom){
			var number = $(this).find("[data-number]").val();
			count+= parseInt(number);
		})
		$(this).find("[data-shop-number]").html(count);
	});
}

//计算订单商品总件数
function calGoodsQuantity(){
	var total = 0;
	$("[data-shop-id]").each(function(){
		var number = Number($(this).find("[data-shop-number]").html());
		total += number;
	})
	$("[data-total-number]").html(total);
	return total;
}

//后台重新匹配最佳优惠券
function rematchCoupon(thiz, orderPrice) {
	shopNo = $(thiz).data("shopId");
	$.post("${ctx}/wap/${sessionShopNo}/buy/coupon/rematch.htm", {"shopNo":shopNo, "orderPrice":orderPrice}, function(data) {
		
	},"json");
}

/* 
//取整或者保留两位
function getMathCeil (amt) {
	return Math.ceil(amt*100)/100;
} */
//后台获取邮费
function getShopPostFee(thiz,backfun){
	//获取当前点击的店铺ID
	shopId = $(thiz).data("shopId");
	//设置配送方式页面对应的店铺ID
	$("#j-delivery").data("deliveryId",shopId);
	var addressId=$("#addressId").val();
	/* if(!addressId){
		$.dialog.alert('请先选择收货地址');
		return;
	} */
	var arr=[];
	$(thiz).find('[data-good-id]').each(function(){
		var emp=true;
		for(var x in arr){
			if(arr[x].id==$(this).data('good-id')){
				arr[x].inventory=Number(arr[x].inventory)+Number($(this).find('input[type=tel]').val());
				emp=false;
			}
		}
		if(emp){
			var obj=new Object();
			obj.id=$(this).data('good-id');
			obj.inventory=$(this).find('input[type=tel]').val();
			arr.push(obj);
		}
	});
	var deliveryWay=$(thiz).find('[deliveryway]').val();
	var pickUpId=$(thiz).find('[pickupid]').val();
	console.dir(deliveryWay)
		console.dir(addressId)
			console.dir(arr)
				console.dir(pickUpId)
	$.post('${ctx}/wap/${sessionShopNo}/buy/cart/getShopPostFee${ext}',
			{'shopNo':shopId,'json':JSON.stringify(arr),
		'addressId':addressId,'deliveryWay':deliveryWay,"pickUpId":pickUpId},function(data){
		var result=JSON.parse(data); 
		if(result.success){
			$(thiz).find('[data-freight]').val(result.info);
			alterDelivery(thiz,result.info,deliveryWay);
			backfun();
		}else{
			alert(result.info);
		}		
	});
}

//改变（显示）计件运费
function alterDelivery ($thiz,postFee,deliveryWay) {
	if (deliveryWay == "1") {
		if (postFee == 0) {
			$thiz.find('[data-delivery-name]').text("快递：包邮");
		} else {
			$thiz.find('[data-delivery-name]').text("快递：￥"+(postFee/100).toFixed(2));
		}
	}
}

//各店铺总额
function getShopTotalPrice($objP){
	var userintegral;
	$objP.find("[data-shop-id]").each(function(index,element){
		
		var thiz = $(this);
		var total = 0;
		var $userintegral=thiz.parents(".seller").find("input[data-id='user-integral']");//用户总积分
		if(index==0){
			userintegral = 0;
			if ($userintegral.length!=0) {
				userintegral=$userintegral.val();//用户总积分
				//$userintegral.attr("data-surplus",userintegral);
			}
		}
		getShopPostFee(thiz,function(){
			thiz.find("[data-pro-id]").each(function(key,dom){
				var price = $(this).find("[data-price]").text(),
				 number =$(this).find("[data-number]").val(),
				 all = price*100*number;
				 total += all;
			});
			var freight = thiz.find("[data-freight]").val(); //运费
			var realTotal = total; //积分不能抵扣运费
			total = total+freight*1; //积分可以抵扣运费
			var $seller=thiz.parents(".seller"),
						isCarryI=false,
						isSupportI=false,
						isCoupons=false,//是否优惠券
						fullCouponsAmt=0,//满多少
						couponsAmt=0,//优惠券
						giftcondition=0,
						giftresult=0,
						supportScale=1,//积分抵价，单位：分
						maxSupportScale=0,// 最大抵的比例
						supportAmt=0,//真实抵扣值，单位：元
					    $giftcondition=$seller.find("input[data-id='gift-condition']"),//积分赠送临界值，订单消费额，单位：元
					    $giftresult=$seller.find("input[data-id='gift-result']"),//每次满足临界值赠送积分数，单位：个
					    $deductionmaxrete=$seller.find("input[data-id='deduction-maxrete']"),
						$shopcoupons=$seller.find("[data-id='shop-coupons']"),//优惠券
			  			curShopAmt=getCurShopGoodAmt(thiz),
			  			isMax=0;
					    // 送积分
					    if ($giftcondition.length!=0) {
					    	isCarryI=true;
					    	giftresult=$giftresult.val(),//每次满足临界值赠送积分数，单位：个
					    	giftcondition=$giftcondition.val();//积分赠送临界值，订单消费额，单位：元
					    }
					     // 抵积分
					    if ($userintegral.length!=0) {
					    	isSupportI=true;
					    	//var surplusintegral=$userintegral.attr("data-surplus"),//用户剩余总积分
					    	var	maxSupportScale = $deductionmaxrete.val();// 最大抵的比例
					    }
					    // 优惠券
					    if ($shopcoupons.length!=0) {
					    	isCoupons=true;
					    	isMax=getBestCoupon($seller.find("[data-id='shop-coupons']"),curShopAmt*100);
					    	fullCouponsAmt=getMathCeil($shopcoupons.attr("data-best-value"))/100;
					    	couponsAmt=getMathCeil($shopcoupons.attr("data-best-coupons"))/100;
					    }
			var $datasupport=thiz.find("[data-support]"),//店铺内积分抵扣显示区间
				$shopprice=thiz.find("[data-shop-price]"),//店铺内积分抵扣额度
				 $datacoupons=thiz.find("[data-coupons]"),//优惠券	 
				 $onoffSupport=thiz.find("[data-onoff-type='0']"),//积分低价
			     $onoffCoupons=thiz.find("[data-onoff-type='1']");//优惠券
			if (isSupportI&&$onoffSupport.attr("data-onoff")=="on") {
				supportAmt=userintegral*supportScale; //所有可以抵扣积分
				//maxsupportAmt=parseInt($deductionmaxrete.val()*total/100);//积分最大抵扣额度计算，单位：分(可以抵扣运费)
				maxsupportAmt=parseInt($deductionmaxrete.val()*realTotal/100);//积分最大抵扣额度计算，单位：分(不可以抵扣运费)
				supportAmt=supportAmt<=maxsupportAmt?supportAmt:maxsupportAmt;
				var makeSupportIntegral = supportAmt;
				if ($onoffSupport.data("isSupportI")) {
					$userintegral.attr("data-surplus",userintegral - makeSupportIntegral);
				}
				$datasupport.html('积分抵扣 ：用了<span data-type="makeintergral">'+makeSupportIntegral+'</span>积分抵'+supportAmt/100+'元');
				total=total-supportAmt;
				$shopprice.html(total/100);
				userintegral = $userintegral.attr("data-surplus");
			}
			//初始化且有最优的优惠券 
			
			else if (isCoupons&&isMax>0&&!$onoffCoupons.data("isinit")) {
				$datasupport.html("积分抵扣");
				onAndoff($onoffCoupons,true);	
				$shopprice.html(getMathCeil(total));
				var couponsid = $shopcoupons.attr("data-best-couponsid");
				$seller.find("[data-name='couponsid']").val(couponsid);
				$datacoupons.html('店铺优惠券：满<span data-type="makeintergral">'+parseInt(fullCouponsAmt)+'</span>元减'+couponsAmt+'元');
				total=total-couponsAmt*100;
				$shopprice.html(total/100);
				$onoffCoupons.data("isinit",true);
			}
			else if (isCoupons&&$onoffCoupons.attr("data-onoff")=="on") {
				console.log("0001")
				if (fullCouponsAmt>curShopAmt) {
					 //遍历最优优惠券
					$datasupport.html("积分抵扣");
					$datacoupons.html("店铺优惠券");
					onAndoff($onoffCoupons,false);
				}
				else{
					onAndoff($onoffCoupons,true);	
					$shopprice.html(getMathCeil(total));
					var couponsid = $shopcoupons.attr("data-best-couponsid");
					$seller.find("[data-name='couponsid']").val(couponsid);
					$datacoupons.html('店铺优惠券：满<span data-type="makeintergral">'+parseInt(fullCouponsAmt)+'</span>减'+couponsAmt+'元');
					total=total-couponsAmt*100;
				}
				$shopprice.html(total/100);
				
			}
			else{
				$datasupport.html("积分抵扣");
				$datacoupons.html("店铺优惠券");
				$shopprice.html(total/100);
			}
			if (isCarryI) {
				thiz.find("[data-shop-score]").html(parseInt(((total-freight)/giftcondition)*giftresult));
			}
			getTotalPrice();
		});
	});
}


//订单总额
function getTotalPrice(){
	var total = 0;
	$("[data-shop-id]").each(function(){
		var price = Number($(this).find("[data-shop-price]").html());
		total += price;
	})
	$("[data-total-price]").html((Number(total)).toFixed(2));
	return total;
}

//提交订单
function toSubmit(){
	//提交操作
	if(!$("#addressId").val()){
		artTip('收货地址不能为空');
		return;
	}
	var flag=true;
	$("input[type=tel]").each(function(){
		if($(this).val()<=0){
			artTip("购买数量不能低于1！");
			flag=false;
		}
	})
	$("input[deliveryway]").each(function(){
		if(!$(this).val()){
			artTip("配送方式不能为空");
			flag=false;
		}
	})
	if(!flag){
		return;
	}
	var price=Number($("em[data-total-price]").text());
	$("#jform").find('button').attr("disabled",true);
	$("#jform").submit();
}
//取整或者保留两位
function getMathCeil (amt) {
	return Math.ceil(amt*100)/100;
}
//单个店铺总额
function getCurShopGoodAmt(obj){
	var total = 0;
	obj.find("[data-pro-id]").each(function(){
		var price = Number($(this).find("[data-price]").html())*Number($(this).find("[data-number]").val());
		total += price;
	})
	return total;
}
function onAndoff($obj,status){
	if (status) {
		$obj.attr("data-onoff","on");
	  	$obj.removeClass("onoff-off");
	  	$obj.children(".onoff-handle").css({"right": "0px","left": "auto"})
	  	$obj.children(".onoff-info").css({"text-align":"left"});
	  	$obj.children(".onoff-info").text("开启");
	}
	else{
		$obj.attr("data-onoff","off");
	  	$obj.addClass("onoff-off");
	  	$obj.children(".onoff-handle").css({"right": "auto","left": "0px"})
	  	$obj.children(".onoff-info").css({"text-align": "right"})
	  	$obj.children(".onoff-info").text("●●●");
	}
}
// 遍历优惠券最优一张
function getBestCoupon(obj,curShopAmt){
	var couponsAmtArray=[],
		max=0;

	obj.find("input").each(function(){
		var $obj=$(this),
			fullCouponsAmt=getMathCeil($obj.val()),
			couponsAmt=getMathCeil($obj.data("shop-coupons")),
			id=$obj.data("couponsid");
		if (curShopAmt>=fullCouponsAmt) {
			couponsAmtArray.push({'id':id,'value':fullCouponsAmt,'couponsAmt':couponsAmt});
		}
	});
	for (var i = 0; i < couponsAmtArray.length; i++) {
		var value=getMathCeil(couponsAmtArray[i].couponsAmt);
		if (value>max) {
			max=value;
			obj.attr("data-best-value",couponsAmtArray[i].value);
			obj.attr("data-best-coupons",max);
			obj.attr("data-best-couponsid",couponsAmtArray[i].id);
			
		}
	}
	return max;
}

//开关按钮
$("[data-type='onoff']").off().on("click",function(){
	  var $obj=$(this),
	  	  onoff=$obj.attr("data-onoff"),
	  	 $shop=$(this).parents("[data-shop-id]"),
		  $seller=$(this).parents(".seller"),
		  offtype=$obj.data("onoff-type");
			if (offtype=="1") {
				if (getMathCeil($seller.find("[data-id='shop-coupons']").attr("data-best-value"))/100>getCurShopGoodAmt($shop)) {
					artTip("没有找到相匹配的优惠券！");
					return false;
				}

			}
		  var $userintegral=$seller.find("input[data-id='user-integral']"),
		  surplus=$userintegral.attr("data-surplus"),
		  $groupjustify=$obj.parents(".group-justify"),
		  $shopindexs=$seller.find("input[data-type='shopindex']"),
		  $shopindex= $groupjustify.children("input[data-type='shopindex']"),
		  $seller=$obj.parents(".seller"),
		  maxindex=0,
		  $datanamecouponsid=$seller.find("input[data-name='couponsid']"),
		  $datasupport=$shop.find("[data-support]"),//积分抵扣
			 $datacoupons=$shop.find("[data-coupons]");//优惠券
			 onAndoff($shop.find("[data-type='onoff']"),false);//关闭
			  $datasupport&&$datasupport.html("积分抵扣");
			  $datacoupons&&$datacoupons.html("店铺优惠券");
			  $datanamecouponsid&&$datanamecouponsid.val("");	
			  $shopindex&&$shopindex.val("");
	    if (onoff=="off") {
	    	
	    	if(offtype=="0"){
	    		if (surplus=="0") {
					artTip("该店铺积分已经抵消完！");
					return false;
				}
				onAndoff($obj,true);//开启
			  	getShopTotalPrice($seller);

				$shopindexs.each(function(){
					var indexvalue=$(this).val();
					if (indexvalue!="") {
						indexvalue=parseInt(indexvalue);
						if (indexvalue>maxindex) {
							maxindex=indexvalue;
						}
					}
				});
				$shopindex.val(maxindex+1);
	    	}
	    	else{
	    		onAndoff($obj,true);//开启
			  	getShopTotalPrice($seller);
	    	}
	  	}
		else{
			onAndoff($obj,false);//开启
		  	var makeintergral=$obj.parent().prev().find("span[data-type='makeintergral']").text();
		  	$userintegral.attr("data-surplus",parseInt(surplus)+parseInt(makeintergral));
		  	getShopTotalPrice($seller);
		}
});

</script>

<%@ include file="../../common/foot.jsp"%>
</div>
</body>
</html>