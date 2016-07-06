<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品分类</title>
<!-- top -->
<%@ include file="../../../common/base.jsp"%>	
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("good-types");

	//选择商品分类模板
	var val=$(".templatelist").find(".active").find("input[type='hidden']").val();//选中的模版的传递值
	$('.templatelist').find("li").each(function(index) {
		$(this).find("div").first().click(function(event) {
			$('.templatelist li').removeClass("active");
			$(this).parent("li").addClass("active");
			val=$(".templatelist").find(".active").find("input[type='hidden']").val();
		});
	});
	
	/*添加/修改联系方式*/
	$("#addContact").click(function() {
		art.dialog({
			lock : true,
			width : '400px',
			title : "新增或修改联系方式",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-tel"),
			ok : function(){
				var bool=$("#jform").validate({
				rules:{
					cell:{
						required:true,
						/* maxlength:16,
						mobile:true, */
						telAndphone:true
					}
				},
				messages:{
					cell:{
						required:"必填！",
						telAndphone:"请输入正确的手机号码或电话号码\n\n例如:18123456789或0571-1234567或400-000-3777"
						/* maxlength:$.format("联系方式最大长度{0}位"),
						mobile:"请输入正确的手机号码" */
					}
				}
			}).form();
			if(bool){
				var cell = $("#cell").val();
				$.post("${ctx}/u/good/shopType/editShopPhone${ext}",{ "cell":cell}, 
				function(data){ 
			      var result = eval('(' + data + ')');
				  if(result.success){
					art.dialog.alert("修改成功");
					return true;
				  }else{
					art.dialog.alert("修改失败");
					return false;
				  }
				});
			}else{
				return false;
			}
			
		},
			cancel : true
		});
	});

	/*添加/修改商品分类模板*/
	$("#addTemp").click(function() {
		art.dialog({
			lock : true,
			width : '818px',
			title : "商品分类模板选择",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-choose-temp"),
			ok : function(){
				console.log('改变了模板')
			},
			cancel : true
		});
	});
});
		
	</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<form id="formPage">
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="goodtype"/>
						<%@include file="../../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">商品分类</a></li>
									<!-- <li class=""><a href="goods-carousel.html">商品分类轮播图</a></li> -->
									<li class="text-muted">备注： 设置好类别，系统会自动生成一个商品分类列表模块。</li>
								</ul>
							</div>
							<div class="content">
								<ul class="pager">
									<li class="previous">
										<a class="btn btn-default" href="${ctx}/u/good/shopType/add${ext}">添加分类</a>
										<!-- <button type="button" class="btn btn-default" id="addTemp">选择模板</button> -->
										<button type="button" class="btn btn-default" id="addContact">添加/修改联系方式</button>
									</li>
									<!-- <li class="next">
										<button type="button" class="btn btn-default" id="preview">预览</button>
									</li> -->
								</ul>
`								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th width="30%">分类名称</th>
											<th width="">模块图标</th>
	<!-- 										<th width="15%">分类描述</th> -->
											<th width="15%">字体颜色</th>
											<th width="10%">排序</th>
											<th width="20%">操作</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
											<c:when test="${not empty pagination.datas }">
												<c:forEach items="${pagination.datas }" var="shopType" varStatus="status">
													<tr>
														<td><c:if test="${status.last }">
														<input type="hidden" id="maxSort" value="${shopType.sort }"/></c:if>
														<input type="hidden" move-row="moveId" name="shopTypes[${status.index}].id" value="${shopType.id}"/>
														<input type="hidden" move-row="moveSort" name="shopTypes[${status.index}].sort" value="${shopType.sort}"/>
														<input type="hidden" name="shopTypes[${status.index}].imgPath" value="$shopType.imgPath}"/>
														${shopType.name}
														</td>
														<td>
															<div plugin="scale" class="table-img">
																<c:if test="${fn:contains(shopType.imgPath, 'resource')}">
																	<img src="${shopType.imgPath}" alt="" width="35" height="35" style="display: block; margin: 32.5px auto 0px;">
																</c:if>
																<c:if test="${fn:contains(shopType.imgPath, 'resource')==false}">
																	<img src="${picUrl }${shopType.imgPath}" alt="" width="35" height="35" style="display: block; margin: 32.5px auto 0px;">
																</c:if>																
															</div>
														</td>
														<%-- <td>${shopType.typeDescribe}</td> --%>
														<td><input style="background-color:#${shopType.fontColor}" type="text" value="#${shopType.fontColor}" disabled="disabled" class="form-control"></td>
														<td>
															<a href="javascript:void(0)" onclick="moveUp(this,true)"><img src="${resourcePath}img/up.jpg" alt=""></a> 
															<a href="javascript:void(0)" onclick="moveDown(this,true)"><img src="${resourcePath}img/down.jpg" alt=""></a>
<%-- 															<a href="javascript:void(0)" onclick="up(this)"><img src="${resourcePath}img/up.jpg" alt=""></a> 
															<a href="javascript:void(0)" onclick="down(this)"><img src="${resourcePath}img/down.jpg" alt=""></a> --%>
														<td>
															<a href="${ctx}/u/good/shopType/edit/${shopType.id}${ext}" class="btn-editor" >编辑 | </a>
															<a href="javascript:void(0)" class="btn-del" onclick="del(this, '${shopType.id}')">删除</a>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<!-- 没有内容的 样式 -->
												<tr>
													<td colspan="7" class="text-center">暂无内容</td>
												</tr> 
												<!-- 没有内容的 样式 end -->	
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<%@include file="../../../common/page.jsp"%>
							</div>
						</div>
					</div>
					
			</div>
			</form>
		</div>
	</div>
	<!-- 联系方式 -->
	<div id="j-tel" style="display: none;">
		<form id="jform" >
			<input type="text" id="cell" name="cell" value="${cell }" class="form-control" />
		</form>
	</div>
	
	
	<!-- 添加链接至模块  商品分类 或 添加链接至商品-->
	<div id="j-addToPro" style="display: none;">
		<div class="tab-title">
			<div class="row">
				<div class="col-md-7 text-left">商品列表</div>
				<div class="col-md-4"><input class="form-control input-short-11" type="text"></div>
				<div class="col-md-1"><button type="submit" class="btn btn-default">查询</button></div>
			</div>
		</div>
		<!-- Tab panes -->
		<div class="tab-content">
		<div class="tab-pane active" id="proList">
			<ul class="list" style="margin-top:10px;">
				<li><label><input type="checkbox" name="checkbox" value="9" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="10" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="11" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="12" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="13" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="14" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="15" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="16" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="17" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="18" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="19" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="20" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="21" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="22" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="23" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="24" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="25" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="26" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="27" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="28" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="29" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="30" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="31" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="32" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
				<li><label><input type="checkbox" name="checkbox" value="" /> 棉立方2014夏装新款韩版蕾丝圆领小清新棉T</label></li>
			</ul>
		</div>
		</div>
	</div>

	<!-- 商品分类 -->
	<div id="j-choose-temp" style="display: none;">
		<div class="tab-pane fade in active panel-body" id="temp1">
			<ul class="row templatelist" style="">
				<li class="active">
					<div>
						<span></span> <img src="${resourcePath}img/templist-img1.jpg" alt="模板1">
					</div>
					<div>
						<input type="hidden" name="temp1" value="1">模板1
					</div>
				</li>
				<li>
					<div>
						<span></span> <img src="${resourcePath}img/templist-img2.jpg" alt="模板2">
					</div>
					<div>
						<input type="hidden" name="temp1" value="2">模板2
					</div>
				</li>
				<li>
					<div>
						<span></span> <img src="${resourcePath}img/templist-img3.jpg" alt="模板3">
					</div>
					<div>
						<input type="hidden" name="temp3" value="3">模板3
					</div>
				</li>
				<li>
					<div>
						<span></span> <img src="${resourcePath}img/templist-img4.jpg" alt="模板4">
					</div>
					<div>
						<input type="hidden" name="temp4" value="4">模板4
					</div>
				</li>											
			</ul>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../../common/footer.jsp"%>
	<!-- footer end -->
	<script type="text/javascript" src="${resourcePath}plugins/updown.js"></script>
	<script type="text/javascript">
	$(function(){
		$.puburl.setting.url="${ctx}/u/good/shopType/sort${ext}";
	});
	
	/*删除*/
	 function del(el,index) {
		var tr = $(el).parent("td").parent("tr");
		art.dialog.confirm('确认删除？', function() {
//			var shopTypeId = tr.find("[name='shopTypes["+index+"].id']").val();
			var shopTypeId = index;
			if(shopTypeId){
			    $.ajax({
			          url: "${ctx}/u/good/shopType/delete/"+shopTypeId+"${ext}",   
			          type: "post",
			          data: {"shopTypeId":shopTypeId},
			          dataType: "text",
			          contentType: "application/x-www-form-urlencoded; charset=utf-8",
			          success: function (id) {
							if(null == id || '' == id){
								alertMsg("服务器繁忙！");
							}else{
								tr.remove();
								window.location.reload();
							}
			          },
			          error: function () {
			        	  alertMsg("服务器繁忙！");
			          }
			      });
			}else{
				tr.remove();
				window.location.reload();
			}
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	} 
</script>
	</body>
</html>