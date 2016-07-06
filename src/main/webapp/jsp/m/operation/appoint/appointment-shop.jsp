<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>预约管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
	$(function() {
		tab("appointment");
		$('select').val('${goodVo.shopTypeId}')
	});
function query(){
	var ids=[];
	for(var x in arr){
		ids.push((arr[x].id).toString())
	}
	$("#goodIds").val(JSON.stringify(ids))
	return true;
}
function cx(){
	$("#formPage").submit();
}
function save(){
	var ids=[];
	for(var x in arr){
		ids.push((arr[x].id).toString())
	}
	$.post('/u/appoint/save.htm',{'goodIds':JSON.stringify(ids),'id':$("#id").val()},function(){
		location.href="/u/appoint/list.htm";
	})
}
</script>
</head>
<body>
	<!-- header -->
	<%@ include file="../../common/header.jsp"%>
	<!-- header end -->

	<!-- body  -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
				<c:set var="crumbs" value="ordershop"/>
							<%@include file="../../common/crumbs.jsp"%>

				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs" id="templatelist">
								<li class="active"><a href="#temp1" data-toggle="tab">选择商品</a></li>
							</ul>
						</div>
						<div class="content">
							<form class="form-horizontal" role="form" id="formPage" method="post" onsubmit="return query();">
								<input type="hidden" name="goodIds" id="goodIds" value=''/>
								<input type="hidden" name="id" id="id" value="${id }"/>
								<div class="row">
									<div class="col-md-9">
									<div class="promotion" style="margin-right: 10px;">
										<h3 class="title">商品列表</h3>
										<div class="form-inline" style="margin:10px;">
											<div class="form-group">
												<select class="form-control" name="shopTypeId" onchange="cx();">
													<option value="0">选择分类</option>
													<c:forEach items="${shopTypeList }" var="shopType">
														<option value="${shopType.id }">${shopType.name }</option>
													</c:forEach>
												</select>
											</div>
											<div class="form-group">
												<input type="text" class="form-control" name="name" value="${goodVo.name }" placeholder="请输入关键词">
											</div>
											<button type="button" onclick="cx();" class="btn btn-default">搜索</button>
											<button type="button" class="btn btn-default" onclick="selectAll()">本页全选</button>
										</div>
										<ul class="productlist" id="j-source">
										<c:forEach items="${pagination.datas }" var="good">
 											<li class="product" data-id="${good.id }">
												<span class="pro-img"><img src="${good.picUrl }" alt="60" width="60" height=""/></span>
												<span class="pro-title">${good.name }</span>
												<span class="pro-price"><fmt:formatNumber pattern="0.00" value="${good.salesPrice }"/></span>
												<span class="pro-status"></span>
											</li>
										</c:forEach>
										</ul>
										<%@include file="../../common/page.jsp"%>
										
									</div>
									</div>
									
									<div  class="col-md-3">
										<div class="promotion" style="overflow:hidden;overflow-y:auto;">
											<h3 class="title">已选择商品</h3>
											<div class="form-inline" style="margin: 10px;">
												<div class="form-group">
													<input type="text" class="form-control" id="inputPassword2" placeholder="请输入关键词" onkeyup="localSearch(this)">
												</div>
											</div>
											<ul class="productlist productlist-selected" id="j-selected"></ul>
										</div>
									</div>
								</div>
								<div class="row text-center" style="margin-top:10px;">
									<!-- 修改 -->
									<button type="button" onclick="save()" class="btn btn-default">保存</button>
									<!-- 发布 -->
									<!-- <button type="submit" class="btn btn-default">完成</button> -->
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
</body>
<script type="text/javascript">
	//模拟后台数据
	var data = JSON.parse('${goodList}')
	
	//模拟分页数据
	var data2 = []


    var loadDataAdd=function(obj,data){
        var html='',index=0;
        for( index in data){
            html +=' <li class="product '+(data[index].status?'active':'')+'" data-id="'+data[index].id+'" onclick="addPro(this)">'
                    +'<span class="pro-img"><img src="${picUrl}'+data[index].src+'" alt="" width="60" height="60"/></span>'
                    +'<span class="pro-title">'+data[index].title+'</span>'
                    +'<span class="pro-price">￥<em>'+(Number(data[index].price)/100).toFixed(2)+'</em></span>'
                    +' <span class="pro-status"></span>'
                    +'</li>'
        };
        obj.html(html);
    }

    var loadDataDel=function(obj,data){
        var html='',index=0;
        for( index in data){
            html +=' <li class="product" data-id="'+data[index].id+'" onclick="delPro(this)">'
            +'<span class="pro-img"><img src="'+((data[index].src).indexOf('${picUrl}')!=-1?'':'${picUrl}')+data[index].src+'" alt="" width="60" height="60"/></span>'
            +'<span class="pro-title">'+data[index].title+'</span>'
            +'<span class="pro-price">￥<em>'+(Number(data[index].price)).toFixed(2)+'</em></span>'
            +' <span class="pro-status"></span>'
            +'</li>'
        };
        obj.html(html);
    }

	var arr = JSON.parse('${selectedList}');//选择后存放的数据
	//var arr=[];
	var newArr = []; // 搜索后的存放的数据

    loadDataAdd($("#j-source"),data);//将后台数据展示到页面
    loadDataDel($("#j-selected"),arr);

	//添加产品
	function addPro(el){
		var bool = $(el).hasClass("active");
		var id = $(el).attr("data-id");
		var src=$(el).find(".pro-img img").attr("src");
		var title = $(el).find(".pro-title").html();
		var price = $(el).find(".pro-price em").html();
		if(!bool){
			var obj = {
					id:id,
	            	src:src,
	            	title:title,
	            	price:price,
	            	status:true
					};
			arr.push(obj);
		}
		$(el).addClass("active");

        loadDataDel($("#j-selected"),arr);
		console.log(arr);
	}
	
	//删除产品
	function delPro(el){
		var selectedId = $(el).attr("data-id");
		for(var key in arr){
			var id = arr[key]["id"];
			if(id == selectedId){
				arr[key]["status"] = false;
				arr.splice(key,1);
				$("#j-source .product[data-id='"+selectedId+"']").removeClass("active");
				$(el).remove();
			}
		}
		console.log(arr);
	}
	
	//分页对比
	function otherPage(){

        loadDataAdd($("#j-source"),data2);

		for(var k in arr){
			for(var m in data2){
				if(arr[k]["id"] == data2[m]["id"]){
					console.log(arr[k]["id"]);
					$("#j-source .product[data-id='"+arr[k]["id"]+"']").addClass("active");
				}
			}
		}
		console.log(arr);
	}

	//本地搜索
	function localSearch(el){
		var val = $(el).val(),
			  valArr = val.split(""),
			  len = arr.length,
			  regStr="";
		 newArr = [];
		for(var k=0;k<valArr.length;k++){
			regStr+=valArr[k]+"\w*";
		}
		var reg=new RegExp(regStr);
		
		for(var i=0; i< len; i++){
			if(arr[i]["title"].match(reg)){
				newArr.push(arr[i]);
			};
		};

        loadDataDel($("#j-selected"),newArr);

		console.log(newArr);
	}
	
	//本页全选
	function selectAll(){
		$("#j-source .product").each(function(){
			var bool = $(this).hasClass("active");
			if(!bool){
				$(this).addClass("active");
				var id = $(this).attr("data-id");
				var src=$(this).find(".pro-img img").attr("src");
				var title = $(this).find(".pro-title").html();
				var price = $(this).find(".pro-price em").html();
				var obj = {
						id:id,
		            	src:src,
		            	title:title,
		            	price:price,
		            	status:true
						};
				arr.push(obj);
			}
		})

        loadDataDel($("#j-selected"),arr);

		console.log(arr);
	}

	$("#j-form").validate({
		rules:{
			username:{
				required:true,
				minlength:2
			}
		},
		messages:{
			username:{
				required:"请输入推广名称",
				minlength:$.format("推广名称不少于{0}个字符")
			}
		}
	})
</script>

</html>

