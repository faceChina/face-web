<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送方式设置</title>
<%@ include file="../../../common/base.jsp"%>
<script type="text/javascript">
	var index;
	$(function() {
		tab("tempmanage");
		$('.m-address-info').popover({
			html : true,
			trigger : "hover"
		});
		//验证初始化
		$.metadata.setType("attr", "validate");
		//初始化索引
		index = initIndex();
	});

	function initIndex() {
		var index = '${fn:length(dto.itemList)}';
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
	<%@ include file="../../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
						<c:set var="crumbs" value="logistics"/>
						<%@include file="../../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#" data-toggle="tab">快递配送设置</a></li>
							</ul>
						</div>
						<form class="form-horizontal" id="j-form" method="post">
							<input type="hidden" name="id" value="${dto.id}" />
							<div class="content">
								<div class="alert alert-warning" role="alert">
									<p>运送方式：除指定地区外，其余地区的运费采用"全国默认地区运费"</p>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label" style="width: 80px;">模板名称：</label>
									<div class="col-md-10">
										<input type="text" maxlength="32"
											validate="{required:true, messages:{required:'请输入模板名称'}}"
											value="${dto.name }" placeholder="模板名字" name="name"
											class="form-control input-short-6">
									</div>
								</div>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>配送区域</th>
											<th>首件（个）</th>
											<th>运费（元）</th>
											<th>续件（个）</th>
											<th>运费（元）</th>
										</tr>
									</thead>
									<tbody id="table-body">
										<c:forEach items="${dto.itemList }" var="item" varStatus="i">
											<tr>
												<c:choose>
													<c:when test="${i.index == 0 }">
														<td>全国默认地区<input type="hidden" 
															name="itemList[${i.index }].destination"
															value="${item.destination }"
															validate="{required:true, messages:{required:'请输入省份地区'}}" /></td>
													</c:when>
													<c:otherwise>
														<td><input type="hidden"
															name="itemList[${i.index }].destination"
															value="${item.destination }"
															validate="{required:true, messages:{required:'请输入省份地区'}}" />
															<div class="m-address">
																<span>指定地区</span> <span class="add-address"
																	onclick="openProvice(this, '${i.index }')">编辑</span> <span
																	class="" onclick="del(this, '${item.id}')">删除</span>
															</div>
															<div id="title-${i.index }" class="m-address-info txt-rowspan1"
																data-toggle="popover" data-placement="right" title="指定地区"
																data-content="${item.destinationString }" data-original-title="指定地区">${item.destinationString }</div></td>
													</c:otherwise>
												</c:choose>
												<td><input type="hidden" name="itemList[${i.index}].id"
													value="${item.id}" /> <input type="text" maxlength="10"
													validate="{required:true,positiveInteger:true, messages:{required:'请输入件数'}}"
													name="itemList[${i.index}].startStandard"
													value="${item.startStandard }"></td>
												<td><input type="text" maxlength="11"
													validate="{required:true,money:true, messages:{required:'请输入运费'}}"
													name="itemList[${i.index}].startPostageYuan"
													value="<fmt:formatNumber value='${item.startPostage / 100 }' pattern='0.00'/>"></td>
												<td><input type="text" maxlength="10"
													validate="{required:true,positiveInteger:true, messages:{required:'请输入件数'}}"
													name="itemList[${i.index}].addStandard"
													value="${item.addStandard }"></td>
												<td><input type="text"  maxlength="11"
													validate="{required:true,money:true, messages:{required:'请输入运费'}}"
													name="itemList[${i.index}].addPostageYuan"
													value="<fmt:formatNumber value='${item.addPostage / 100 }' pattern='0.00'/>"></td>
											</tr>
										</c:forEach>
										<c:if test="${empty dto.itemList }">
											<td>全国默认地区<input type="hidden"
												name="itemList[0].destination" value="ALL"
												validate="{required:true, messages:{required:'请输入省份地区'}}" /></td>
											<td><input type="text"  maxlength="10"
												validate="{required:true,positiveInteger:true, messages:{required:'请输入件数'}}"
												name="itemList[0].startStandard"
												value=""></td>
											<td><input type="text"  maxlength="11"
												validate="{required:true,money:true, messages:{required:'请输入运费'}}"
												name="itemList[0].startPostageYuan"
												value=""></td> 
											<td><input type="text" maxlength="10"
												validate="{required:true,positiveInteger:true, messages:{required:'请输入件数'}}"
												name="itemList[0].addStandard" value=""></td>
											<td><input type="text"  maxlength="11"
												validate="{required:true,money:true, messages:{required:'请输入运费'}}"
												name="itemList[0].addPostageYuan" value=""></td>
										</c:if>
										<tr onclick="addTable(this)" id="show-add" <c:if test="${fn:length(dto.itemList) > 1 }">style="display: none"</c:if>>
											<td colspan="5" class="text-left"><i></i><b
												style="cursor: pointer;">增加指定地区</b></td>
										</tr>
									</tbody>
								</table>
								<div class="form-group">
									<div class="col-md-12 text-center">
										<button type="button" onclick="javascript:formSubmit()"
											class="btn btn-default">确认保存</button>
										<button type="button" onclick="location.href='${ctx }/u/shop/logistics/dispatchIndex${ext}'"
											class="btn btn-default">返回</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="j-address" style="display: none;">
		<form id="form-address">
			<ul class="list-address">
				<li><span class="address-area"><label for=""></label></span>
					<ul>
						<c:forEach items="${provice.children }" var="pe">
							<li><label for=""> <input name="check-box-name"
									id="check-box-id-${pe.code }" value="${pe.code }" pe-string="${pe.area }"
									type="checkbox">${pe.area }
							</label></li>
						</c:forEach>
					</ul></li>
			</ul>
		</form>
	</div>
	<%@include file="../../../common/footer.jsp"%>
</body>
<script type="text/javascript">
	function addTable(thiz) {
		var str = '<tr>'
				+ '<td>'
				+ '<input type="hidden" name="itemList['+index+'].destination" validate="{required:true, messages:{required:\'请输入省份地区\'}}" />'
				+ '<div class="m-address">'
				+ '<span>指定地区</span>'
				+ '<span class="add-address" onclick="openProvice(this, '
				+ index
				+ ')">编辑</span>'
				+ '<span class="" onclick="del(this)">删除</span>'
				+ '</div>'
				+ '<div id="title-'+index+'" class="m-address-info txt-rowspan1" data-toggle="popover" data-placement="right" title="指定地区" data-content="" data-original-title="指定地区"></div>'
				+ '</td>'
				+ '<td><input type="text" validate="{required:true,positiveInteger:true, messages:{required:\'请输入件数\'}}" name="itemList['+index+'].startStandard" placeholder="" value=""></td>'
				+ '<td><input type="text" validate="{required:true,money:true, messages:{required:\'请输入运费\'}}" name="itemList['+index+'].startPostageYuan" value=""></td>'
				+ '<td><input type="text" validate="{required:true,positiveInteger:true, messages:{required:\'请输入件数\'}}" name="itemList['+index+'].addStandard" value=""></td>'
				+ '<td><input type="text" validate="{required:true,money:true, messages:{required:\'请输入运费\'}}" name="itemList['+index+'].addPostageYuan" value=""></td>'
				+ '</tr>';
		$(thiz).before(str);
		index++;
		canAdd();
		$('.m-address-info').popover({
			html : true,
			trigger : "hover"
		});
	}
	/*删除*/
	function del(el, id) {
		if (null == id || id == "") {
			$(el).closest("tr").remove();
			canAdd();
			return;
		}
		art.dialog.confirm('确认删除？', function() {
			$.post("${ctx}/u/shop/logistics/itemDel${ext}", {
				"id" : id
			}, function(jsonData) {
				var data = JSON.parse(jsonData);
				if (data.success) {
					$(el).closest("tr").remove();
					canAdd();
				} else {
					art.dialog.alert(data.info);
				}
			});
		}, function() {
			return true;
		});
	}
	function formSubmit() {
		var formvali = $("#j-form").validate({});
		if (formvali.form()) {
			$('#j-form').submit();
		}
	}
	function openProvice(el, index) {
		//清空值
		cleanProvice();
		//设值
		setProvice(el, index);
		art.dialog({
			lock : true,
			width : '600px',
			title : "配送范围",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-address"),
			button : [
					{
						name : '保存',
						callback : function() {
							var data = getNewProvice();
							var dataString = getNewProviceString();
							// $(el).parent().prev().val(data);
							$("input[name='itemList[" + index + "].destination']").val(data);
							$('#title-'+index).attr("data-content", dataString);
							$('#title-'+index).html(dataString);
						},
						focus : true
					}, {
						name : '关闭'
					} ]
		})
	}
	function setProvice(el, index) {
		var provice = $("input[name='itemList[" + index + "].destination']")
				.val();
		if (null == provice || "" == provice) {
			return;
		}
		var arr = provice.split(",");
		for ( var i in arr) {
			$('#check-box-id-' + arr[i]).attr('checked', 'checked');
		}
	}
	function cleanProvice() {
		$('#form-address').find("input[name='check-box-name']").each(
				function(index) {
					$(this).attr("checked", null);
				});
	}
	function getNewProvice() {
		var data = "";
		$('#form-address').find("input[name='check-box-name']").each(
				function(index) {
					var checkFlag = $(this).attr("checked");
					if ('checked' == checkFlag) {
						data += $(this).val() + ",";
					}
				});
		if (null != data && data.length > 0) {
			data = data.substring(0, data.length - 1);
		}
		return data;
	}
	
	function getNewProviceString() {
		var data = "";
		$('#form-address').find("input[name='check-box-name']").each(
				function(index) {
					var checkFlag = $(this).attr("checked");
					if ('checked' == checkFlag) {
						data += $(this).attr('pe-string') + ",";
					}
				});
		if (null != data && data.length > 0) {
			data = data.substring(0, data.length - 1);
		}
		return data;
	}
	function canAdd() {
		var len = $('#table-body').find("tr").length;
		if (len >= 3) {
			$('#show-add').hide();
		} else {
			$('#show-add').show();
		}
	}
</script>
</html>


