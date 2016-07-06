<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${appointment.navigationTitle }</title>
<%@ include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<script type="text/javascript" src="${resourcePath }js/addel-radius.js"></script>
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">
<script>
(function($){
	$.fn.addelRadius = function(options){
		var opts = $.extend({},$.fn.addelRadius.defaults,options);
		
		return this.each(function(){
			var thiz = $(this);
			var number = parseInt($(this).find("input").val());
			
			//初始状态
			var init = (function(){
				if(number == 0){
					thiz.find(".addel-del,.addel-info").hide();
				}
			})();
			
			$(this).find(".addel-add").on("click",function(){
				++number;
				if(number > opts.number){
					number = opts.number;
				}else{
					opts["add"]();
					if(number == opts.number) $(this).attr('disabled','disabled');
				}

				thiz.find("input").val(number);
				if(number > 0 ){
					thiz.find(".addel-del,.addel-info").show();
				}
				var i=0;
				$(".list-col").find("input[type=tel]").each(function(){
					if($(this).val()!="0"){
						i++;
					}
				})
				$('#j-total').text(Number('${otherNum}')+i);
			});
			$(this).find(".addel-del").on("click",function(){
				--number;
				//number = number <= 0 ? 0 : number;
				if(number < 0){
					number = 0;
				}else{
					opts["del"]();
				}
				thiz.find("input").val(number);				
				if(thiz.find(".addel-add").attr('disabled') == 'disabled'){
					thiz.find(".addel-add").removeAttr('disabled');
				}
				
				if(number == 0){
					thiz.find(".addel-del,.addel-info").hide();
				}
				var i=0;
				$(".list-col").find("input[type=tel]").each(function(){
					if($(this).val()!="0"){
						i++;
					}
				})
				$('#j-total').text(Number('${otherNum}')+i);
			});
		});
		
		$.fn.addelRadius.defaults = {
			number:1,
			add:null,
			del:null
		}
	}
})(jQuery);

$(function(){
	var i=0;
	$(".list-col").find("input[type=tel]").each(function(){
		if($(this).val()!="0"){
			i++;
		}
	})
	$('#j-total').text(Number('${otherNum}')+i);
	var totalprice=0;
	$(".addel-radius").each(function(){
		totalprice+=(Number($(this).data('price'))*Number($(this).find('input[type=tel]').val()));
	});
	$("#j-price").text((totalprice+Number("<fmt:formatNumber pattern='0.00' value='${otherTotal/100 }'/>")).toFixed(2))
});
</script>
</head>
<body>
<div id="box">
	
	<div class="tab">
		<ul class="tab-handle list">
			<li class="width78">
				<a href="#tabs1" data-toggle="tab" id="j-sort">分类 <i class="iconfont icon-sort"></i></a>
				<span class="j-close nav-close">关闭<i class="iconfont icon-close"></i></span>
				<ul class="nav hide" id="j-nav">
					<c:forEach items="${shopTypeList }" var="shopType">
						<li class="<c:if test='${shopType.id==shopTypeId }'>active</c:if>"><a href="javascript:;" onclick="ref('${shopType.id}');">${shopType.name }</a></li>
					</c:forEach>
				</ul>
			</li>
			<li><a href="#tabs2" data-toggle="tab">预约说明</a></li>
			<li><a href="#tabs3" data-toggle="tab">联系我们</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabs1">
				<div class="list-row" data-showhide id="scroll-box">
					<c:forEach items="${goodSkuList }" var="goodSku">
					<div class="list-col">
						<div class="list-inline"><a href="/wap/${sessionShopNo }/any/appoint/infodetails/${goodSku.id }.htm"><img src="${picUrl }${goodSku.picturePath}" alt="" width="70" height="70"></a></div>
						<div class="list-top box-flex list-position">
							<ul>
								<li class="txt-rowspan2">${goodSku.name }</li>
								<li class="clr-danger txt-rowspan1 fnt-14 left" style="padding-top:8px;">价格：￥<fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100 }"/></li>
								<li class="right add-position">
									<div class="addel-radius" data-total="${goodSku.stock }" data-price="<fmt:formatNumber pattern='0.00' value='${goodSku.salesPrice/100 }'/>">
									    <span class="addel-del"><i class="iconfont icon-jianhao"></i></span>
									    <span class="addel-info"><input type="tel" readOnly="true" data-id="${goodSku.id }" value="<c:if test='${empty goodSku.quantity }'>0</c:if>${goodSku.quantity}"></span>
									    <span class="addel-add" <c:if test="${goodSku.quantity==goodSku.stock||goodSku.stock==0 }">disabled="disabled"</c:if>><i class="iconfont icon-add"></i></span>
									</div>
								</li>
							</ul>
						</div>
					</div>
					</c:forEach>
				</div>
				
				<div class="footfix" id="j-foot">
					<div class="group group-justify">
						<div class="group-item">
							<div class="group-rowspan">
								<div class="group-colspan">
									<a href="javascript:;"><span class="clr-light">共<em id="j-total">${num }</em>件，<var class="clr-danger">总计：￥<em id="j-price"><fmt:formatNumber pattern="0.00" value="${total/100 }"/></em></var></span></a>
								</div>
								<div class="group-colspan">
									<button href="javascript:;" onclick="tj();" class="btn btn-danger">${appointment.buttonTitle }</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div class="tab-pane" id="tabs2">
				<div class="group">
					<div class="reserve-info">
						${appointment.content }
					</div>
				</div>
			</div>
			<div class="tab-pane" id="tabs3">
				
				<div class="group group-others width20">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan"><i class="iconfont icon-dizhi clr-light fnt-24"></i></li>
							<li class="group-colspan"><c:if test="${appointment.isShowSellerMeg==0}">暂无地址</c:if>${appointment.address }</li>
							<li class="group-colspan"></li>
						</ul>
					</div>
					<c:if test="${appointment.isShowSellerMeg==1}">
					<a href="tel:${appointment.cell }">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan"><i class="iconfont icon-dianhua clr-light fnt-24"></i></li>
							<li class="group-colspan">${appointment.cell }</li>
							<li class="group-colspan"><i class="iconfont icon-right"></i></li>
						</ul>
					</div>
					</a>
					</c:if>
					<c:if test="${appointment.isShowSellerMeg==0}">
					<div class="group-item">
						<ul class="group-rowspan">
							<li class="group-colspan"><i class="iconfont icon-dianhua clr-light fnt-24"></i></li>
							<li class="group-colspan">暂无联系电话</li>
							<li class="group-colspan"><i class="iconfont icon-right"></i></li>
						</ul>
					</div>
					</c:if>
				</div>
				
			</div>
		</div>
	</div>
	<style>
	.goback{
	  	display: -moz-box;
	  	display: -webkit-box;
		max-width:640px;
		min-width:320px;
		width:100%;
		margin:0 auto;
		padding:0.5em;
	}
	.goback a{
		display: block;
	  	-moz-box-flex: 1.0;
	    -webkit-box-flex: 1.0;
	    border-right:1px solid #c4c4c4;
	    color:#c4c4c4;
	    text-align: center;
	}
	.goback a:last-child{
		border-right:none;
	}
	</style>
	<div class="goback">
		<a href="/wap/${shop.no }/any/index.htm">店铺首页</a>
		<a href="/wap/${shop.no }/buy/personal/index.htm">个人中心</a>
	</div>
</div>
<input type="hidden" value="${num}" id="num"/>
<form id="j-form" method="get">
	<input type="hidden" id="json" name="json"/>
	<input type="hidden" id="shopTypeId" name="shopTypeId"/>
	<input type="hidden" name="openId" value="${openId }"/>
</form>
<script type="text/javascript">
function tj(){
	$("#j-form").attr('action','/wap/${sessionShopNo }/any/appoint/good/${id}.htm');
	var arr=[];
	if('${json}'){
		arr=JSON.parse('${json}')
	}
	$(".list-col").find("input[type=tel]").each(function(){
		if($(this).val()!="0"){
			var obj=null;
			for(x in arr){
				if(arr[x].id==$(this).data('id')){
					obj=arr[x];
				}
			}
			if(!obj){
				obj=new Object();
				arr.push(obj);
			}
			obj.id=$(this).data("id");
			obj.quantity=$(this).val();
		}else{
			for(x in arr){
				if(arr[x].id==$(this).data('id')){
					arr.splice(x,1);
				}
			}
		}
	})
	var json=JSON.stringify(arr);
	if(json.length==2){
		$.dialog.alert('请先选择商品');
		return;
	}
	$("#json").val(json);
	$("#j-form").submit();
}
function ref(shopTypeId){
	$("#j-form").attr('action','/wap/${sessionShopNo }/any/appoint/${id}.htm');
	$("#shopTypeId").val(shopTypeId);
	var arr=[];
	if('${json}'){
		arr=JSON.parse('${json}')
	}
	$(".list-col").find("input[type=tel]").each(function(){
		if($(this).val()!="0"){
			var obj=null;
			for(x in arr){
				if(arr[x].id==$(this).data('id')){
					obj=arr[x];
				}
			}
			if(!obj){
				obj=new Object();
				arr.push(obj);
			}
			obj.id=$(this).data("id");
			obj.quantity=$(this).val();
		}else{
			for(x in arr){
				if(arr[x].id==$(this).data('id')){
					arr.splice(x,1);
				}
			}
		}
	})
	$("#json").val(JSON.stringify(arr));
	$("#j-form").submit();
}
	

	$(function(){
		if('${appointment.status}'!='1'){
			$('button').attr('disabled',true);
			$.dialog.alert('活动已关闭');
		}
		
		//阻止分类按钮冒泡、显示隐藏
		$("#j-sort").click(function(event){
			var wHeight = window.innerHeight;
			var Fheight = $('#j-foot').height();
			var parenht = $(this).closest('li').height();
			var Nheight = wHeight-Fheight-parenht;
			$('.j-close').show();
			$("#j-nav").show().addClass('show');
			$('#j-nav').height(Nheight);
		
		});
		$('.j-close').on('click',function(event){
			$(this).hide();
			$("#j-nav").hide().removeClass('show');
		});

	});

	function shopTotal(type,price){
		var totalId = $('#j-total'),
			priceId = $('#j-price'),
			total = parseFloat(totalId.text()),
			totalPrice = parseFloat(priceId.text());
		if(type){
			totalId.text(++total);
			priceId.text(parseFloat(totalPrice+parseFloat(price)).toFixed(2));
		}else{
			totalId.text(--total);
			priceId.text(parseFloat(totalPrice-parseFloat(price)).toFixed(2));
		}
	}

 	//增减数量
	$(".addel-radius").each(function(){
		var self = $(this);
		$(this).addelRadius({
			number:self.data('total'),//后台传数据
			add:function(){
				shopTotal(true,self.data('price'));
			},
			del:function(){
				shopTotal(false,self.data('price'))
			}
		})
	});
	
</script>
</body>
</html>
