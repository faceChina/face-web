<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<style>
.addimg img{ width:100%; height:100%;}
</style>
<meta charset="UTF-8" />
<title>官网首页轮播图管理</title>	
<!-- top -->
<%@include file="../../common/base.jsp" %>
<!--top end -->
<script type="text/javascript" src="${resourceBasePath}js/ajaxupload3.9.js"></script>
<script type="text/javascript" src="${resourcePath }js/allSelect.js"></script>
<script type="text/javascript">
		$(function(){
			tab("carousel-sc");
			$('body').find('img[name="uploadImg"]').each(function(index){
				var _this=$(this);
				uploadimg($(this));
				$(this).siblings('span').css('z-index',10000);
				$(this).siblings('span').find('i').click(function(){
					if($(this).data('name')=='delete'){
						del(this);
					}
					if($(this).data('name')=='link'){
						addPro(this);
					}
					$('.action').css('z-index',100);
				})
			});
			
			$('#change-select').on('change',function(){
				var attr = this.value;
				$('[data-name="'+attr+'"]').show().siblings('[data-name]').hide();
			})
		});	
		
	function del(el){
		var Img=$(el).parent().siblings('img');
		var id = $(el).parent().siblings('input[type=hidden]').eq(0);
		if(!id.val()){
			Img.attr('src','/resource/m/img/add-pic.jpg');
			Img.prev().val('');
			$(el).parent().hide();
			return;
		}
		art.dialog.confirm('确认删除？', function() {
			$.post("${ctx}/u/template/${subCode}/delCarousel${ext}",
	  			  {id:$(id).val()}, 
	  			  function(data){
	  				if(data == 1){
	  					Img.attr('src','/resource/m/img/add-pic.jpg');
	  					Img.prev().val('');
	  					$(id).val('');
	  					$(el).parent().hide();
	  				}else{
	  					art.dialog.tips('删除失败');
	  				}
	  		});
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}

	/*添加链接至模块--商品分类 或 添加链接至商品*/
	function addPro(el) {
		cancel();
		var input = $(el).parent().siblings('input[type=hidden]');
		var code = $(input).eq(4);
		var bindIds = $(input).eq(5);
		var pro = $("#j-addToPro");
		
		//回显模块链接
		if(null != $(code).val() && $(code).val().indexOf("modular") != -1){
			pro.find("input[name='modularRadio']").each(function(index){
				if($(code).val().indexOf($(this).attr('data-code')) != -1){
					$(this).attr("checked",true);
				}
			});
			$('[data-name="module"]').show().siblings('[data-name]').hide();
			$('#change-select').val('module');
		}
		//回显预约
		if(null != $(code).val() && $(code).val().indexOf("appointment") != -1){
			pro.find("input[name='appointmentRadio']").each(function(index){
				if(bindIds.val() == $(this).val()){
					$(this).attr("checked",true);
				}
			});
			$('[data-name="appointment"]').show().siblings('[data-name]').hide();
			$('#change-select').val('appointment');
		}
		//回显商品分类
		if(null != $(code).val() && $(code).val().indexOf("shopType") != -1 && $(code).val().indexOf("modular") == -1){
			var arr=bindIds.val().split(",");
			pro.find("input[name='shopTypeCheckbox']").each(function(index){
				for(var i=0;i<arr.length;i++){
					if($(this).val() == arr[i]){
						$(this).attr("checked",true);
					}
				}
			});
			$('[data-name="shop"]').show().siblings('[data-name]').hide();
		}
		//回显商品
		if(null != $(code).val() && $(code).val().indexOf("good") != -1){
			var arr=bindIds.val().split(",");
			pro.find("input[name='goodCheckbox']").each(function(index){
				for(var i=0;i<arr.length;i++){
					if(arr[i] == $(this).val()){
						$("#j-addToPro .nav-tabs li").removeClass("active");
						$("#j-addToPro .nav-tabs li:last").addClass("active");
						$("#j-addToPro .tab-content .tab-pane").removeClass("active");
						$("#j-addToPro .tab-content .tab-pane:last").addClass("active");
						$(this).attr("checked",true);
					}
				}
			});
			$('[data-name="shop"]').show().siblings('[data-name]').hide();
		}
		
		art.dialog({
			lock : true,
			width : '1000px',
			title : "链接",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToPro"),
			button : [ {
				name : '保存',
				callback : function() {
					$('.action').css('z-index',10000);
					if($('#change-select').val() == 'module'){
						var radio = pro.find("input[name='modularRadio']:checked");
						if($(radio).attr("data-code")){
							var codeVal = '${owTemplateScHp.code}'+'_'+$(radio).attr("data-type")+'_'+$(radio).attr("data-code");
							code.val(codeVal);
							code.prev().val($(radio).val());
						}
					}else if($('#change-select').val() == 'appointment'){
						var radio = pro.find("input[name='appointmentRadio']:checked");
						if($(radio).val()){
							var codeVal = '${owTemplateHp.code}'+'_'+$(radio).attr("data-type");
							code.val(codeVal);
							bindIds.val($(radio).val());
						}
					}else if($("#proClass").hasClass("active")){
						var str='';
						pro.find("input[name='shopTypeCheckbox']:checked").each(function(index){
							str+=this.value+",";
							code.val('${owTemplateScHp.code}'+'_'+$(this).attr("data-type"));
						});
						if(str.length>0){
							bindIds.val(str.substr(0,str.length-1));
						}
					}else{
						var str='';
						pro.find("input[name='goodCheckbox']:checked").each(function(index){
							str+=this.value+",";
							code.val('${owTemplateHp.code}'+'_'+$(this).attr("data-type"));
						});
						if(str.length>0){
							bindIds.val(str.substr(0,str.length-1));
						}
					}
				},
				focus : true
			}, {
				name : '关闭',
				callback:function(){
					$('.action').css('z-index',10000);
				}
			} ]
		});
	}
	
	function cancel(){
		$("#j-addToPro").find("input[name='modularRadio']").each(function(index){
			$(this).attr("checked",false);
		});
		$("#j-addToPro").find("input[name='shopTypeCheckbox']").each(function(index){
			$(this).attr("checked",false);
		});
		$("#j-addToPro").find("input[name='goodCheckbox']").each(function(index){
			$(this).attr("checked",false);
		});
	}

	function uploadimg(uploadBtn){
	    var url = "${ctx}/any/files/upload${ext}";
	    new AjaxUpload(uploadBtn, {
	        action: url,
	        data: {
	        },
	        autoSubmit: true,
	        responseType: 'json',
	        onSubmit: function(file,ext){
	        	var imageSuffix = new RegExp('${imageSuffix}');
	        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
	            	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
	                return false;               
	            }
	        },
	        onComplete: function(file, response){
	        	if(response.code == -1){
	        		art.dialog.alert(response.desc);
	        		return ;
	        	}
	        	if(response.flag == "SUCCESS"){
	        		var src_path = '${picUrl}'+response.source;
	        		$(uploadBtn).attr('src',src_path);
	        		$(uploadBtn).prev().val(response.path);
	        		$(uploadBtn).parent().removeClass('addimg');
	        		$(uploadBtn).parent().find('.action').show().css('z-index',10000);
	    		} else {
					art.dialog.alert(response.info);
				}
	        }
		});
	}
	function mySubmit(){
		$(".j-loading").show();
		$("#j-form").submit();
	}
	
	//预览
	function preview() {
		var url= '${ctx}/any/preview/${subCode}/template${ext}';
		var name='previewTemplate';   //网页名称，可为空
	    var iWidth=410;                          //弹出窗口的宽度;
	    var iHeight=810;                         //弹出窗口的高度;
	    previewToBigData(url,getJson(),name,iWidth,iHeight);
	}
	
	/**
	 *获取提交数据
	 */
	function getJson() {
		var owTemplateCode = '${owTemplateScHp.code}';
		var arr = new Array();
		var i =0;
		console.log($(".add-uploadpic li").val());
		$(".add-uploadpic li").find("img").each(function(index) {
			 var img = $(this).attr('src');
			 if (null != img && '' != img && img.indexOf("/resource/m/img")<0) {
				 arr[i++] = img;
			 }
		});
		var imgArrJson = JSON.stringify(arr);
		var obj = {owTemplateCode : owTemplateCode, 'imgArrJson' : imgArrJson, 'previewType' : 'carousel'};
		var jsondata = JSON.stringify(obj);
		return jsondata;
	}
	</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="scmodular"/>
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="" data-toggle="tab">轮播图管理</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<form id="j-form" method="post">
							<input type="hidden" name="validateToken" value="${validateToken }"/>
							<div class="content">
								<div class="alert alert-warning" role="alert" >
									<p>提示：</p>
									<p>1.轮播图图片规格大小为${owTemplateScHp.carouselImageWidth }*${owTemplateScHp.carouselImageHeight }px。</p>
									<p>2.本类目下您最多可以上传 5 张图片。</p>
									<p>3.本地上传图片大小不能超过2MB。</p>
								</div>
								<c:if test="${owTemplateScHp.isCarlOrBg == 2 }">
									<ul class="add-uploadpic j-addpic" data-height="232">
										<c:forEach items="${carouselDiagramList }" var="data" varStatus="status">
									  		<li class="" >
									  			<input type="hidden" name="list[${status.index }].id" value="${data.id }"/>
									  			<input type="hidden" name="list[${status.index }].owTemplateId" value="${owTemplateScHp.id }"/>
									  			<input type="hidden" name="list[${status.index }].sort" value="${status.index }"/>
									  			<input type="hidden" name="list[${status.index }].resourcePath" value="${data.resourcePath }"/>
									  			<input type="hidden" name="list[${status.index }].code" value="${data.code }"/>
									  			<input type="hidden" name="list[${status.index }].bindIds" value="${data.bindIds }"/>
									  			<input type="hidden" name="list[${status.index }].imgPath" value="${data.imgPath }"/>
									  			<c:choose>
													<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
														<img name="uploadImg" src="${picUrl }${data.imgPath }" alt=""  data-src="img/lp/goods-1.jpg">
													</c:when>
													<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 1 }">
														<img name="uploadImg" src="${data.imgPath }"  alt=""  data-src="img/lp/goods-1.jpg">
													</c:when>
													<c:otherwise>
														<img name="uploadImg" src="" alt=""  data-src="img/lp/goods-1.jpg">
													</c:otherwise>
												</c:choose>
									  			<span class="action" style=""><i data-name="delete">删除</i></span>
									  		</li>
								  		</c:forEach>
								  		<c:forEach step="1" begin="${size }" end="4" varStatus="status">
								  			<li class="addimg"  >
								  				<input type="hidden" name="list[${status.index }].id" value=""/>
								  				<input type="hidden" name="list[${status.index }].owTemplateId" value="${owTemplateScHp.id }"/>
									  			<input type="hidden" name="list[${status.index }].sort" value="${status.index }"/>
									  			<input type="hidden" name="list[${status.index }].resourcePath" value=""/>
									  			<input type="hidden" name="list[${status.index }].code" value="${data.code }"/>
									  			<input type="hidden" name="list[${status.index }].bindIds" value=""/>
									  			<input type="hidden" name="list[${status.index }].imgPath" value=""/>
									  			<img name="uploadImg" src="${resourcePath }img/add-backpic.jpg" alt="" class="">
									  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
									  		</li>
								  		</c:forEach>
				 					</ul>
			 					</c:if>
			 					<c:if test="${owTemplateScHp.isCarlOrBg == 1 }">
				 					<ul class="add-uploadpic j-addpic" data-height="86">
				 						<c:forEach items="${carouselDiagramList }" var="data" varStatus="status">
									  		<li class="" >
									  			<input type="hidden" name="list[${status.index }].id" value="${data.id }"/>
									  			<input type="hidden" name="list[${status.index }].owTemplateId" value="${owTemplateScHp.id }"/>
									  			<input type="hidden" name="list[${status.index }].sort" value="${status.index }"/>
									  			<input type="hidden" name="list[${status.index }].resourcePath" value="${data.resourcePath }"/>
									  			<input type="hidden" name="list[${status.index }].code" value="${data.code }"/>
									  			<input type="hidden" name="list[${status.index }].bindIds" value="${data.bindIds }"/>
									  			<input type="hidden" name="list[${status.index }].imgPath" value="${data.imgPath }"/>
									  			<c:choose>
													<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 0}">
														<img name="uploadImg" src="${picUrl }${data.imgPath }" alt=""  data-src="img/lp/goods-1.jpg">
													</c:when>
													<c:when test="${null != data.imgPath && data.imgPath != '' && data.isBasePic == 1 }">
														<img name="uploadImg" src="${data.imgPath }" alt=""  data-src="img/lp/goods-1.jpg">
													</c:when>
													<c:otherwise>
														<img name="uploadImg" src="" alt=""  data-src="img/lp/goods-1.jpg">
													</c:otherwise>
												</c:choose>
									  			<span class="action"><i data-name="delete">删除</i><i data-name="link">链接</i></span>
									  		</li>
								  		</c:forEach>
								  		<c:forEach step="1" begin="${size }" end="4" varStatus="status">
								  			<li class="addimg"  >
								  				<input type="hidden" name="list[${status.index }].id" value=""/>
								  				<input type="hidden" name="list[${status.index }].owTemplateId" value="${owTemplateScHp.id }"/>
									  			<input type="hidden" name="list[${status.index }].sort" value="${status.index }"/>
									  			<input type="hidden" name="list[${status.index }].resourcePath" value=""/>
									  			<input type="hidden" name="list[${status.index }].code" value="${data.code }"/>
									  			<input type="hidden" name="list[${status.index }].bindIds" value=""/>
									  			<input type="hidden" name="list[${status.index }].imgPath" value=""/>
									  			<img name="uploadImg" src="${resourcePath }img/add-backpic.jpg" alt="" class="">
									  			<span class="action" style="display:none;"><i data-name="delete">删除</i><i data-name="link">链接</i></span>
									  		</li>
								  		</c:forEach>
				 					</ul>
			 					</c:if>
							</div>
							<div class="content" >
								<div class="text-center">
<!-- 									<button type="button" class="btn btn-default" onclick="preview()">预览</button> -->
									<button type="button" onclick="mySubmit()" class="btn btn-default" >保存</button>
								</div>
							</div>
		 					</form>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
	<!-- 添加链接至模块  商品分类 或 添加链接至商品-->
	<div id="j-addToPro" style="display: none;">
		<div class="form-horizontal">
			<div class="form-group">
				<label for="input" class="col-md-2 control-label" style="width:138px;">链接模块或地址：</label>
				<div class="col-md-3">
					<select class="form-control" id="change-select">
						<option value="shop">链接至商品</option>
						<option value="module">链接至模块</option>
						<option value="appointment">链接至预约</option>
					</select>
				</div>
			</div>
		</div>
		<div data-name="module" style="display:none;">
			<ul class="list">
				<c:forEach items="${baseModularList }" var="data" >
					<li><label><input type="radio" name="modularRadio" data-code="${data.code }" data-type="modular" value="${data.resourcePath }" /><span> ${data.nameZh }</span></label></li>
				</c:forEach>
			</ul>
		</div>
		<div data-name="appointment" style="display:none;">
			<ul class="list">
				<c:forEach items="${appointmentList }" var="data" >
					<li><label><input type="radio" name="appointmentRadio" data-type="appointment" value="${data.id }" /><span> ${data.name }</span></label></li>
				</c:forEach>
			</ul>
		</div>
		<div data-name="shop">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#proClass" data-toggle="tab">链接至商品分类</a></li>
				<li><a href="#shopList" data-toggle="tab">链接至商品</a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="proClass">
				<ul class="list" style="margin-top:10px;">
					<c:forEach items="${shopTypeList }" var="data">
						<li><label><input type="checkbox" name="shopTypeCheckbox" value="${data.id }" data-type="shopTypes" /> ${data.name }</label></li>
					</c:forEach>
				</ul>
			</div>
			<div class="tab-pane" id="shopList" plugin="allSelect">

				<div class="row" style="margin:10px 0;">
					<button class="btn btn-default" pluginhandles="handles" type="button">全选</button>
				</div>

				<ul class="list" style="margin-top:10px;" plugincontent="content">
					<c:forEach items="${goodList }" var="data">
						<li><label><input type="checkbox" name="goodCheckbox" value="${data.id }" data-type="good" /> ${data.name }</label></li>
					</c:forEach>
				</ul>
			</div>
			</div>
		</div>
		
	</div>
</body>
</html>

