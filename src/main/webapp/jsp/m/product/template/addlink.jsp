<%@ page pageEncoding="UTF-8"%>

<script type="text/javascript">

/* -----------------------------------下拉选择 弹出页面 ----------------------------------*/
var isSelect = true;
function manageLink(){
	isSelect=!isSelect;
	if(!isSelect)return;
	var val=$("#j-link").val();
	var form=$("#j-activity");
	var code = $(form).find("input[name='code']");
	
	if(val=="addTemp"){
		cancelTemp();
		var modularId=$(form).find("input[name='modularId']").val();
		$("#modularId").val(modularId);
		//回显模块链接
		if(null != $(code).val() && $(code).val().indexOf("modular") != -1){
			$("#j-addToTemp").find("input:radio").each(function(index){
				if($(code).val().indexOf($(this).attr('data-code')) != -1){
					$(this).attr("checked",true);
				}
			});
		}
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至模块",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToTemp"),
			button : [ {
				name : '保存',
				callback : function() {
					var radio = $("#j-addToTemp").find("input[name='modularRadio']:checked");
					if($(radio).attr("data-code")){
						var codeVal = '${owTemplateHp.code}'+'_'+$(radio).attr("data-type")+'_'+$(radio).attr("data-code");
						code.val(codeVal);
						form.find('input[name="resourcePath"]').val($(radio).val());
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}else if(val=="addPro"){
		cancelPro();
		var bindIds = $(form).find("input[name='bindIds']");
		if($(code).val().match("shopType$") != -1){
			$("#shopClass").find("input[name='shopTypeCheckbox']").each(function(index){
				if($(this).val() == bindIds.val()){
					$(this).attr("checked",true);
				}
			});
		}
		if($(code).val().indexOf("shopTypes") != -1){
			var arr=bindIds.val().split(",");
			$("#shopClass").find("input[name='shopTypeCheckbox']").each(function(index){
				for(var i=0;i<arr.length;i++){
					if($(this).val() == arr[i]){
						$(this).attr("checked",true);
					}
				}
			});
		}
		if($(code).val().indexOf("good") != -1){
			var arr=bindIds.val().split(",");
			$("#shopList").find("input[name='goodCheckbox']").each(function(index){
				for(var i=0;i<arr.length;i++){
					if($(this).val() == arr[i]){
						$(this).attr("checked",true);
					}
				}
				$("#j-addToPro .nav-tabs li").removeClass("active");
				$("#j-addToPro .nav-tabs li:last").addClass("active");
				$("#j-addToPro .tab-content .tab-pane").removeClass("active");
				$("#j-addToPro .tab-content .tab-pane:last").addClass("active");
			});
		}
		art.dialog({
			lock : true,
			width : '1000px',
			title : "添加商品",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToPro"),
			button : [ {
				name : '保存',
				callback : function() {
					if($("#shopClass").hasClass("active")){
						var str='';
						$("#shopClass").find("input[name='shopTypeCheckbox']:checked").each(function(index){
							str+=this.value+",";
							$(code).val('${owTemplateHp.code}'+'_'+$(this).attr("data-type"));
						});
						if(str.length>0){
							bindIds.val(str.substr(0,str.length-1));
						}
					}else{
						var str='';
						$("#shopList").find("input[name='goodCheckbox']:checked").each(function(index){
							str+=this.value+",";
							$(code).val('${owTemplateHp.code}'+'_'+$(this).attr("data-type"));
						});
						if(str.length>0){
							bindIds.val(str.substr(0,str.length-1));
						}
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}else if(val=='addTempArtical'){
		searchNews();
		cancelTempArtical();
		var val = $(form).find("input[name='bindIds']").val();
		if(val){
			var bindIds=val.split(",");
			if(code.val().indexOf("articleCategory")!=-1){
				$("#proClass").find("input:radio").each(function(index){
					for(var i=0;i<bindIds.length;i++){
						if($(this).val() == bindIds[i]){
							$(this).attr("checked",true);
						}
					}
				})
			}
			if(code.val().indexOf("aricleColumn")!=-1){
				$("#j-addToTemp-artical .nav-tabs li").removeClass("active");
				$("#j-addToTemp-artical .nav-tabs li:first").next().addClass("active");
				$("#j-addToTemp-artical .tab-content .tab-pane").removeClass("active");
				$("#j-addToTemp-artical .tab-content .tab-pane:first").next().addClass("active");
				$("#proList").find("input:radio").each(function(index){
					for(var i=0;i<bindIds.length;i++){
						if($(this).val() == bindIds[i]){
							$(this).attr("checked",true);
						}
					}
				})
			}
			if(code.val().indexOf("newsList") != -1){
				$("#j-addToTemp-artical .nav-tabs li").removeClass("active");
				$("#j-addToTemp-artical .nav-tabs li:last").addClass("active");
				$("#j-addToTemp-artical .tab-content .tab-pane").removeClass("active");
				$("#j-addToTemp-artical .tab-content .tab-pane:last").addClass("active");
				$("#pronews").find('input[name="newsListRadio"]').each(function(index){
					for(var i=0;i<bindIds.length;i++){
						if($(this).val() == bindIds[i]){
							$(this).attr("checked",true);
						}
					}
				});
			}
		}
		art.dialog({
			lock : true,
			width : '1000px',
			title : "文章列表",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToTemp-artical"),
			button : [ {
				name : '保存',
				callback : function() {
					var radio;
					if($("#proClass").hasClass("active")){
						radio = $("#proClass").find('input[name="articleCategoryRadio"]:checked');
					}else if($("#proList").hasClass("active")){
						radio = $("#proList").find('input[name="aricleColumnRadio"]:checked');
					}else if($("#pronews").hasClass("active")){
						radio = $("#pronews").find('input[name="newsListRadio"]:checked');
					}
					if(radio.val()){
						$(form).find("input[name='bindIds']").val(radio.val());
						var codeVal = '${owTemplateHp.code}'+'_'+radio.attr("data-type");
						$(form).find("input[name='code']").val(codeVal);
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}else if(val=='addPoto'){
		cancelPhoto();
		var bindIds = $(form).find("input[name='bindIds']").val();
		if(code.val().indexOf("album")!=-1){
			$("#potoLink").find('input[name="albumRadio"]').each(function(index){
				if($(this).val() == bindIds){
					$(this).attr("checked",true);
				}
			})
		}
		if(code.val().indexOf("photoAlbum")!=-1){
			$("#j-addPoto .nav-tabs li").removeClass("active");
			$("#j-addPoto .nav-tabs li:first").next().addClass("active");
			$("#j-addPoto .tab-content .tab-pane").removeClass("active");
			$("#j-addPoto .tab-content .tab-pane:first").next().addClass("active");
			$("#potoList").find('input[name="photoAlbumRadio"]').each(function(index){
				if($(this).val() == bindIds){
					$(this).attr("checked",true);
				}
			})
		}
		art.dialog({
			lock : true,
			width : '1000px',
			title : "相册",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addPoto"),
			button : [ {
				name : '保存',
				callback : function() {
					var radio;
					if($("#potoLink").hasClass("active")){
						radio = $("#potoLink").find('input[name="albumRadio"]:checked');
					}else if($("#potoList").hasClass("active")){
						radio = $("#potoList").find('input[name="photoAlbumRadio"]:checked');
					}
					if(radio.val()){
						$(form).find("input[name='bindIds']").val(radio.val());
						var codeVal = '${owTemplateHp.code}'+'_'+radio.attr("data-type");
						$(form).find("input[name='code']").val(codeVal);
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}else if(val=='addLink'){
		if(code.val().indexOf("linkUrl")!=-1){
			var resourcePath = $(form).find("input[name='resourcePath']").val();
			$("#j-addToLinks").find("input").val(resourcePath);
		}
		art.dialog({
			lock : true,
			width : '400px',
			title : "添加外部链接",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToLinks"),
			button : [ {
				name : '保存',
				callback : function() {
					var linkUrl = $("#j-addToLinks").find("input");
					var length = $(linkUrl).val().length;
					if(length==0 || length>256) {
						art.dialog.tips("链接地址不能为空且长度不能超过256字");
						return false;
			    	}else{
			    		 $(form).find("input[name='resourcePath']").val($(linkUrl).val());
	                     var codeVal = '${owTemplateHp.code}'+'_linkUrl';
	                     $(form).find("input[name='code']").val(codeVal);
			    	}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}else if(val=="addAppointment"){
		var bindIds = $(form).find("input[name='bindIds']").val();
		$("#j-addAppointment").find('input[name="appointmentRadio"]').each(function(index){
			if($(this).val() == bindIds){
				$(this).attr("checked",true);
			}
		});
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至预约",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addAppointment"),
			button : [ {
				name : '保存',
				callback : function() {
					var radio = $("#j-addAppointment").find('input[name="appointmentRadio"]:checked');
					if(radio.val()){
						$(form).find("input[name='bindIds']").val(radio.val());
						var codeVal = '${owTemplateHp.code}'+'_'+radio.attr("data-type");
						$(form).find("input[name='code']").val(codeVal);
					}
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
}

function cancelTemp(){
	$("#j-addToTemp").find("input[name='modularRadio']").each(function(index){
		$(this).attr("checked",false);
	});
}

function cancelPro(){
	$("#j-addToPro").find("input[name='shopTypeCheckbox']").each(function(index){
		$(this).attr("checked",false);
	});
	$("#j-addToPro").find("input[name='goodCheckbox']").each(function(index){
		$(this).attr("checked",false);
	});
}

function cancelTempArtical(){
	$("#proClass").find("input:radio").each(function(){
		$(this).attr("checked",false);
	});
	$("#proList").find("input:radio").each(function(){
		$(this).attr("checked",false);
	});
	$("#pronews").find("input:radio").each(function(){
		$(this).attr("checked",false);
	});
	$("#j-addToTemp-artical .nav-tabs li").removeClass("active");
	$("#j-addToTemp-artical .nav-tabs li:first").addClass("active");
	$("#j-addToTemp-artical .tab-content .tab-pane").removeClass("active");
	$("#j-addToTemp-artical .tab-content .tab-pane:first").addClass("active");
}

function cancelPhoto(){
	$("#j-addPoto").find('input[name="albumRadio"]').each(function(index){
		$(this).attr("checked",false);
	});
	$("#j-addPoto").find('input[name="photoAlbumRadio"]').each(function(index){
		$(this).attr("checked",false);
	});
}

function searchNews(){
	var title = $("#pronews").find('input[name="title"]').val();
	/*
	$.post("${ctx}/u/template/queryNewsList${ext}",{'title':title},function(result){
		var newsList = JSON.parse(result);
		var printHtml = "";
		for(var key in newsList){
			var shtml = '<li><label><input type="radio" name="newsListRadio" data-type="newsList" value="'+newsList[key].id+'" /> '+newsList[key].title+'</label></li>';
			printHtml += shtml;
		}
		alert(1);
		$("#pronews ul").html(printHtml);
	});
	*/
	$.ajax({
	 	type : "post",  
       	url : "${ctx}/u/template/queryNewsList${ext}",  
       	async : false,
       	data: {'title':title},
       	success : function(result){  
       		var newsList = JSON.parse(result);
    		var printHtml = "";
    		for(var key in newsList){
    			var shtml = '<li><label><input type="radio" name="newsListRadio" data-type="newsList" value="'+newsList[key].id+'" /> '+newsList[key].title+'</label></li>';
    			printHtml += shtml;
    		}
    		$("#pronews ul").html(printHtml);
       	}
	});
}
$(function() {
	$("#j-addToTemp").find("li").click(function() {
		$(this).find(":radio").attr("checked", "checked");
	});
});
</script>

<!-- 添加链接至模块 -->
<div id="j-addToTemp" style="display:none;">
	<ul class="add-list">
		<c:forEach items="${baseModularList }" var="data" >
			<li><label><input type="radio" name="modularRadio" data-code="${data.code }" data-type="modular" value="${data.resourcePath }" /><span> ${data.nameZh }</span></label></li>
		</c:forEach>
	</ul>
</div>
<!-- 添加链接至预约 -->
<div id="j-addAppointment" style="display:none;">
	<ul class="add-list">
		<c:forEach items="${appointmentList }" var="data" >
			<li><label><input type="radio" name="appointmentRadio" data-type="appointment" value="${data.id }" /><span>${data.name }</span></label></li>
		</c:forEach>
	</ul>
</div>



<!-- 添加链接至模块  文章列表 -->
<div id="j-addToTemp-artical" style="display: none;">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#proClass" data-toggle="tab">链接至文章专题</a></li>
		<li><a href="#proList" data-toggle="tab">链接至文章栏目</a></li>
		<li><a href="#pronews" data-toggle="tab">链接至文章</a></li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane active" id="proClass">
			<ul class="list" style="margin-top:10px;">
				<c:if test="${empty articleCategoryList }">
					<li><label>暂无内容</label></li>
				</c:if>
				<c:if test="${not empty articleCategoryList }">
					<c:forEach items="${articleCategoryList }" var="data" >
						<li><label><input type="radio" name="articleCategoryRadio" data-type="articleCategory" value="${data.id }" /> ${data.name }</label></li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		<div class="tab-pane" id="proList">
			<ul class="list" style="margin-top:10px;">
				<c:if test="${empty aricleColumnList }">
					<li><label>暂无内容</label></li>
				</c:if>
				<c:if test="${not empty aricleColumnList }">
					<c:forEach items="${aricleColumnList }" var="data">
						<li><label><input type="radio" name="aricleColumnRadio" data-type="aricleColumn" value="${data.id }" /> ${data.name }</label></li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		<div class="tab-pane" id="pronews">
			<div class="row" style="margin:10px 0;">
				<div class="col-xs-10"><input class="form-control" name="title" type="text"></div>
				<div class="col-xs-offset-1 col-xs-1"><button type="button" onclick="searchNews()" class="btn btn-default">搜索</button></div>
			</div>
			<ul class="list">
				<%--< c:forEach items="${newsList }" var="data">
					<li><label><input type="radio" name="newsListRadio" data-type="newsList" value="${data.id }" /> ${data.title }</label></li>
				</c:forEach> --%>
			</ul>
		</div>
	</div>
</div>


<!-- 添加链接至模块  商品分类 或 添加链接至商品-->
<div id="j-addToPro" style="display: none;">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#shopClass" data-toggle="tab">链接至商品分类</a></li>
		<li><a href="#shopList" data-toggle="tab">链接至商品列表</a></li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane active" id="shopClass">
			<div class="tab-pane" id="checkbox">
				<ul class="list" style="margin-top:10px;">
					<c:forEach items="${shopTypeList }" var="data">
						<li><label><input type="checkbox" name="shopTypeCheckbox" value="${data.id }" data-type="shopTypes" /> ${data.name }</label></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="tab-pane" id="shopList" plugin="allSelect">
			<c:if test="${! empty goodList }">
				<div class="row" style="margin:10px 0;">
					<button class="btn btn-default" pluginhandles="handles" type="button">全选</button>
				</div>
			</c:if>
				<ul class="list" id="productlist" style="margin-top:10px;" plugincontent="content">
					<c:forEach items="${goodList }" var="data">
						<li><label><input type="checkbox" name="goodCheckbox" value="${data.id }" data-type="good" /> ${data.name }</label></li>
					</c:forEach>
				</ul>

		</div>
	</div>
</div>

<!-- 添加链接相册 -->
<div id="j-addPoto" style="display: none;">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#potoLink" data-toggle="tab">链接至相册</a></li>
		<li><a href="#potoList" data-toggle="tab">链接至相册专辑</a></li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane active" id="potoLink">
			<div class="tab-pane">
				<ul class="poto-list" style="margin-top:10px;">
					<c:forEach items="${albumList }" var="data">
						<li>
							<span class="poto">
								<c:choose>
									<c:when test="${data.path == '' || data.path == null}">
										<img src="${resourceBasePath}img/base-photo.jpg" alt="" width="80" height="80">
									</c:when>
									<c:otherwise>
										<img src="${picUrl }${data.path }" width="80" height="80" alt="">
									</c:otherwise>
								</c:choose>
							</span>
							<span class="title"><label><input type="radio" name="albumRadio" data-type="album" value="${data.id }" /> ${data.name }</label></span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="tab-pane" id="potoList">
			<ul class="poto-list" style="margin-top:10px;">
				<c:forEach items="${photoAlbumList }" var="data">
					<li>
						<span class="poto"><img src="${picUrl }${data.picPath }" width="80" height="80" alt=""></span>
						<span class="title"><label><input type="radio" name="photoAlbumRadio" data-type="photoAlbum" value="${data.id }" /> ${data.name }</label></span>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>

<!-- 添加链接至模块  外部链接-->
<div id="j-addToLinks" style="display: none;">
	<input type="text" name="link" placeholder="请输入外部链接地址" class="form-control" />
</div>