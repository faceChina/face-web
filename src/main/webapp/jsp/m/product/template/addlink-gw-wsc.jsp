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
						var codeVal = '${owTemplateScHp.code}'+'_'+$(radio).attr("data-type")+'_'+$(radio).attr("data-code");
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
							$(code).val('${owTemplateScHp.code}'+'_'+$(this).attr("data-type"));
						});
						if(str.length>0){
							bindIds.val(str.substr(0,str.length-1));
						}
					}else{
						var str='';
						$("#shopList").find("input[name='goodCheckbox']:checked").each(function(index){
							str+=this.value+",";
							$(code).val('${owTemplateScHp.code}'+'_'+$(this).attr("data-type"));
							console.log(str)
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
	}else if(val=="addAppointment"){
		var bindIds = $(form).find("input[name='bindIds']").val();
		$("#j-addAppointment").find('input[name="appointmentRadio"]').each(function(index){
			if($(this).val() == bindIds){
				$(this).attr("checked",true);
			}
		})
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

