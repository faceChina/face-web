<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理-会员等级设置</title>
<style type="text/css">
.error:focus{
	border:1px solid #d82230;
	-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(216,34,48,0.6);
	box-shadow:inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(216,34,48,0.6);
	transition:border-color ease-in-out 0.15s,box-shadow ease-in-out 0.15s;
	-webkit-transition:border-color ease-in-out 0.15s,box-shadow ease-in-out 0.15s;
}

.Jerror{display:none;}
.Jerror h3{padding-bottom:10px;color:red; text-align:center;}
.Jerror p{line-height:1.5;}
.clr-red{font-size:14px;font-weight:bold;font-style:normal;color:#c00;}
.table-bordered textarea, .table-bordered input[type=text]{  background-color:rgb(247,247,247);}
</style>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
    var index;
	$(function(){
		tab("member-set");
		$.metadata.setType("attr", "validate");
		index = initIndex();
	});
	
	function initIndex() {
		var index = '${fn:length(list)}';
		if (null == index || '' == index) {
			index = 0;
		}
		parseInt(index)
		index++;
		return index;
	}
</script>
</head>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="member-set" />
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">会员规则设定</a></li>
								</ul>
							</div>
							<div class="content">
								<ul class="pager" style="text-align:left;line-height:31px;"> 
									<li class="next">
										<button type="button" onclick="add()" class="btn btn-default">＋ 新增</button>
										<button type="button" onclick="editorSaveAll(this)" class="btn btn-default" id="jeditorSaveAll">全部修改</button>
									</li>
								</ul>
								<form id="j-form" action="${ctx }/u/member/rule/edit${ext}" method="post">
								<table class="table table-bordered tabe-inputbone" id="template">
									<thead>
										<tr>
											<th>会员级别</th>
											<th>消费金额设定(单位：元)</th>
											<th>折扣(%)</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list }" var="data" varStatus="i">
											<tr>
												<td> <input type="hidden" name="ruleList[${i.index }].id" value="${data.id }"/>
												     <input type="text" value='${data.name }' name="ruleList[${i.index }].name" onchange="changeValue(this)"
												     validate="{required:true}" maxlength="10" disabled="disabled" class="form-control">     </td>
												<td><input type="text" onchange="changeValue(this)" value="${data.amountString }" name="ruleList[${i.index }].amountString" disabled="disabled" class="form-control"
												    validate="{required:true,moneyBase:true,messages:{moneyBase:'请输入正确的金额，保留两位小数'}}" maxlength="11"
												    <c:if test="${i.index == 0 }">id="jlevelOnePrice"</c:if>></td>
												<td><input type="text" onchange="changeValue(this)" value="${data.discount }" name="ruleList[${i.index }].discount" disabled="disabled" class="form-control" 
												     validate="{required:true,range:[1,100],messages:{range:'折扣必须在1~100之间'}}" maxlength="3"
												    <c:if test="${i.index == 0 }">id="jlevelOneDiscount"</c:if>></td>
												<td>
													<c:if test="${i.index != 0 }">
														<button type="button" class="btn btn-default btn-del" onclick="del(this, ${data.id})">删除</button>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</form>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	</body>
	<script type="text/javascript">
	var formvali = $("#j-form").validate({
		errorPlacement: function(error, element) {
			error.insertAfter(element);  
		} 
	});
	//全部修改或全部保存
	function editorSaveAll(el){
		if (!formvali.form()) {
			return;
		}
		var str = $(el).html(),
		 	  trNodes = $("#template tbody tr"),
	    	  len = trNodes.length;
		if(str == "全部修改"){
			$("#template").find("input[type='text']").attr("disabled", false);
			$("#jlevelOneDiscount,#jlevelOnePrice").attr("disabled",true);
			$(el).html("全部保存");
			$("#template").find(".btn-editorSave").html("保存");
		}else if(str == "全部保存"){
			//判断是否能够全部保存
			if(len >1){
				for(var i=0;i<len-1;i++){
					trNodes.find("input[type='text']").removeClass("error"); //还原样式
					var a1 = trNodes.eq(i).find("input[type='text']").eq(0).val();  //第1行第1列
					var a2 = parseFloat(trNodes.eq(i).find("input[type='text']").eq(1).val());  //第1行第2列
					var a3 = parseFloat(trNodes.eq(i).find("input[type='text']").eq(2).val());  //第1行第3列
					for(var j=i+1;j<len;j++){
						var b1 = trNodes.eq(j).find("input[type='text']").eq(0).val(); //第N行第1列
						var b2 = parseFloat(trNodes.eq(j).find("input[type='text']").eq(1).val()); //第N行第2列
						var b3 = parseFloat(trNodes.eq(j).find("input[type='text']").eq(2).val()); //第N行第3列
						//匹配非空字符串
						if(a1.match(/\S/g) == null || b1.match(/\S/g) == null){
							art.dialog.alert("不能为空。");
							trNodes.find("input[type='text']").each(function(){
								if(!$(this).val().match(/\S/g)){
									$(this).addClass("error");
								}
							});
							return;
						}
						//匹配非零数字
						if(isNaN(a2) || isNaN(a3) || isNaN(b2) || isNaN(b3) ){
							art.dialog.alert("不能为非数字或0");
							trNodes.find("td:first").siblings().find("input[type='text']").each(function(){
								var num = parseFloat($(this).val());
								if(isNaN(num)){
									$(this).addClass("error");
								}
							});
							return;
						}
						if(a1 != b1){
							if((a2 <= b2 && a3 < b3) || (a2 >= b2 && a3 > b3)){
								art.dialog.alert("输入有误！消费金额高的会员级别，折扣更低。");
								trNodes.eq(i).find("input[type='text']").eq(1).addClass("error");
								trNodes.eq(j).find("input[type='text']").eq(1).addClass("error");
								trNodes.eq(i).find("input[type='text']").eq(2).addClass("error");
								trNodes.eq(j).find("input[type='text']").eq(2).addClass("error");
								return;
							}
						}else{
							art.dialog.alert("输入有误！不能输入相同的名称。");
							trNodes.eq(i).find("input[type='text']").eq(0).addClass("error");
							trNodes.eq(j).find("input[type='text']").eq(0).addClass("error");
							return;
						}
					}
				}
			}
			//响应
			art.dialog.confirm('你确定要保存？', function () {
				$("#template").find("input[type='text']").attr("disabled", false);
				$(el).html("全部修改");
				$("#template").find(".btn-editorSave").html("修改");
				$(".j-loading").show();
				$('#j-form').submit();
			}, function () {
			});
		}
	}
	//显示弹出窗口中的商品列表
	function showProList(el){
		document.getElementById('jpros').style.display='block';
		$(el).css("cursor","default")
		console.log(el)
	}
	//添加
	function add(){
		var str = '<tr>'
				  +'<td><input type="text" onchange="changeValue(this)" value="" name="ruleList['+index+'].name"'
				  +'validate="{required:true}" maxlength="10" class="form-control"></td>'
				  +'<td><input type="text" value="" onchange="changeValue(this)" name="ruleList['+index+'].amountString" class="form-control"'
				  +'validate="{required:true,moneyBase:true,messages:{moneyBase:\'请输入正确的金额，保留两位小数\'}}" maxlength="11"></td>'
				  +'<td><input type="text" value="" onchange="changeValue(this)" name="ruleList['+index+'].discount" class="form-control"' 
				  +'validate="{required:true,range:[1,100],messages:{range:\'折扣必须在1~100之间\'}}" maxlength="3"></td>'
				  +'<td><button type="button" class="btn btn-default btn-del" onclick="del(this)">删除</button></td></tr>';
		var len = $("#template tbody tr").length;
		if(len < 4) {
			$(str).appendTo($("#template tbody"));
			$("#jeditorSaveAll").html("全部保存");
			index++;
		}else{
			art.dialog.alert("亲，您的会员级别数量已爆棚，无法新增！");
		}
	}
	//删除
	function del(el, id) {
		var len = $("#template tbody tr").length;
		if(len > 1){
			art.dialog.confirm('确认删除？', function() {
				if (id != null) {
					$.post("${ctx}/u/member/rule/del${ext}", {"id" : id}, function(jsonData){
						var data = JSON.parse(jsonData);
						if (data.success) {
							$(el).parent("td").parent("tr").remove();
						} else {
							art.dialog.alert(data.info);
						}
					});
				} else {
					$(el).parent("td").parent("tr").remove();
				}
			}, function() {
				return true;
			});
		}else{
			art.dialog.alert("亲，休息一下别删了，至少要保留一个哦！");
		}
	}
	//修改值
	function changeValue(el) {
		$(el).attr('value', $(el).val());
		/* var afterVal = $(el).parent().parent().prev("tr").children().next().find("input[type='text']").val();
		if(Number(afterVal) >= $(el).val()){
			art.dialog.alert("消费金额必须大于上一级的会员消费金额！");
		} */
	}
	</script>
</html>