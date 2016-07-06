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
<%@include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">
<style>
.aui_content{width:100%;}
</style>
<script type="text/javascript">
/* 开关按钮 */
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
				$(".group-rowspan").find("input[type=tel]").each(function(){
					if($(this).val()!=0){
						i++;	
					}
				})
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
				$(".group-rowspan").find("input[type=tel]").each(function(){
					if($(this).val()!=0){
						i++;	
					}
				})
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
//	$.addel();//初始化页面时，判断默认开关
});




</script>
</head>
<body>
<div id="box">
<div class="tab">
	<ul class="tab-handle list">
		<li>
			<a href="#tabs1" data-toggle="tab">分类</a>
		</li>
		<li><a href="#tabs2" data-toggle="tab">预约说明</a></li>
		<li><a href="#tabs3" data-toggle="tab">联系我们</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane active" id="tabs1">
			<div class="group group-left group-cleartop goods-sudoku" id="content">
				<div class="list-product">
					<ul>
						<c:forEach items="${shopTypeList }" var="shopType">
						<li>
							<a  onclick="toShopnum(this,'${shopType.id}')">
								<span class="pic">
								<c:if test='${fn:contains(shopType.imgPath, "resource/base/") }'><img src="${shopType.imgPath }" alt=""></c:if>
								<c:if test='${!fn:contains(shopType.imgPath, "resource/base/") }'><img src="${picUrl}${shopType.imgPath }" alt=""></c:if>
								<i class="sup">0</i></span>
								<span class="info">${shopType.name }</span>
							</a>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="footfix">
				<div class="group group-justify">
					<div class="group-item">
						<div class="group-rowspan">
							<div class="group-colspan">
								<a><span class="clr-light">共<em id="j-total">0</em>件，<var class="clr-danger">总计：￥<em id="j-price">0.00</em></var></span></a>
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
						<li class="group-colspan"><c:if test="${appointment.isShowSellerMeg == 0 }">暂无地址</c:if>${appointment.address }</li>
						<li class="group-colspan"></li>
					</ul>
				</div>
				<c:if test="${appointment.isShowSellerMeg == 1 }">
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
				<c:if test="${appointment.isShowSellerMeg == 0 }">
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
	<%@include file="../../common/foot.jsp" %>
</div>

<!-- 商品数量选择   -->
<form id="j-form" method="get">
	<input type="hidden" id="j-json" name="json" value=''/>
	<input type="hidden" name="openId" value="${openId }"/>
</form>

<div class="group group-justify group-cleartop" style="display:none;" id="j-shopnum"></div>

</div>
<script type="text/javascript">
$(function(){
	if('${appointment.status}'!='1'){
		$('button').attr('disabled',true);
		$.dialog.alert('活动已关闭');
	}
})
function tj(){
	$("#j-form").attr('action','/wap/${sessionShopNo }/any/appoint/good/${id}.htm');
	var arr=[];
	var json=$("#j-json").val();
	if(json){
		arr=JSON.parse(json)
	}
	$(".group-rowspan").find("input[type=tel]").each(function(){
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
		}
	})
	json=JSON.stringify(arr);
	if(json.length==2){
		$.dialog.alert('请先选择商品')
		return;
	}
	$("#j-json").val(json);
	$("#j-form").submit();
}

	function shopTotal(type,price){
		var totalId = $('#j-total'),
			priceId = $('#j-price'),
			total = parseFloat(totalId.text()),
			totalPrice = parseFloat(priceId.text());
		var i=0;
		$(".group-rowspan").find("input[type=tel]").each(function(){
			if($(this).val()!=0){
				i++;	
			}
		})
		totalId.text(Number($("#num").val())+i);
		console.log(total+","+totalPrice)
		if(type){
			//totalId.text(++total);
			priceId.text(parseFloat(totalPrice+parseFloat(price)).toFixed(2));
		}else{
			//totalId.text(--total);
			priceId.text(parseFloat(totalPrice-parseFloat(price)).toFixed(2));
		}
	}


	//数量统计
	function totalNum(){
		var arr=[];
		var json=$("#j-json").val();
		var flag = 0;
		if(json){
			arr=JSON.parse(json)
		}
		$(".group-rowspan").find("input[type=tel]").each(function(){
			var obj=null;
			if($(this).val()!="0"){
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
				var id=$(this).data("id");
				for(x in arr){
					if(arr[x].id==$(this).data('id')){
						arr.splice(x,1);
					}
				}
			}
		})
		return JSON.stringify(arr);
	}
	var jiage=0;
	//商品数量的选择
	function toShopnum(thiz,shopTypeId){
		jiage=0;
		var self = $(thiz);
		 var totalId = $('#j-total');
		var priceId = $('#j-price');
		var	total = Number(totalId.text());
		var	totalPrice = parseFloat(priceId.text());
		
		var json=$("#j-json").val();
		$.post('/wap/${sessionShopNo}/any/appoint/findGood/${id}.htm',{'shopTypeId':shopTypeId,'json':json},function(data){
			$("#j-shopnum").html(data);
			//$("#j-total").text($("#num").val());
			$("#j-price").text($("#total").val());
			$("#j-json").val($("#json").val());
			$("#j-json").data('val',$("#json").val())
			$(thiz).find('i.sup').text($("#currentTypeNum").val()||0);
				var okTotal = Number(totalId.text()),
						supEl = self.find('.sup'),
						singleTotal = Number(supEl.text())||0;
					
			art.dialog({
				title:'产品数量',
				width:'100%',
				content:document.getElementById('j-shopnum'),
				ok:function(){
					var i=0;
					$(".group-rowspan").find("input[type=tel]").each(function(){
						if($(this).val()!=0){
							i++;	
						}
					})
					
					var okTotal = Number(totalId.text()),
						supEl = self.find('.sup'),
						/* singleTotal = Number(supEl.text())||0; */
						singleTotal = i;
						supEl.show().text(singleTotal);
						$("#j-total").html(Number($("#num").val())+i);
						$("#j-price").html((Number($("#total").val())+Number(jiage)).toFixed(2));
					$("#j-json").val(totalNum());
				},
				cancelVal:'取消',
				cancel:function(){
					totalId.text(total);
					priceId.text(totalPrice.toFixed(2));

				}
			});
		},'html');
		
	}
</script>
</body>
</html>