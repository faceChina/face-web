<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${ctx}/resource/m/plugins/My97DatePicker/WdatePicker.js"></script>
<table class="table table-bordered" id="template">
	<thead>
		<tr>
			<th width="15%">类型</th>
			<th width="20%">名称</th>
			<th width="">初始内容</th>
			<th width="10%">是否必填</th>
			<th width="10%">排序</th>
			<th width="10%">操作</th>
		</tr>
	</thead>
	<tbody data-attr="box">
		
		<tr data-attr="item">
			<td>
			<input type="hidden" name="dynamicFormList[0].sort" value="0" data-sort>
			<input type="hidden"  name="dynamicFormList[0].type" value="INPUT"/>
			<input type="hidden" name="dynamicFormList[0].innerSort" value="1">
			单文本
			</td>
			<td><input type="text" placeholder="姓名" readonly="readonly" class="form-control"  name="dynamicFormList[0].lable" validate="{required:true}"
			    maxlength="10" value="姓名" data-val></td>
			<td><input type="text" placeholder="请输入姓名" readonly="readonly" name="dynamicFormList[0].placeHolder" id="placeHolder-0"
				value="请输入姓名" class="form-control" data-place></td>
			<td><label for="">
			    <input type="hidden" name="dynamicFormList[0].isRequired" value="1">
			    是</label></td>
			<td></td>
			<td></td>
		</tr>
		<tr data-attr="item" data-fix="1">
			<td>
			<input type="hidden" name="dynamicFormList[1].sort" value="1" data-sort>
			<input type="hidden" name="dynamicFormList[1].type" value="INPUT"/>
			<input type="hidden" name="dynamicFormList[1].innerSort" value="2">
			单文本
			</td>
			<td><input type="text" placeholder="联系方式" readonly="readonly" class="form-control"  name="dynamicFormList[1].lable" validate="{required:true}"
			    maxlength="10" value="联系方式" data-val></td>
			<td><input type="text" placeholder="请输入联系方式" readonly="readonly" name="dynamicFormList[1].placeHolder" id="placeHolder-1"
				value="请输入联系方式" class="form-control" data-place></td>
			<td><label for="">
			    <input type="hidden" name="dynamicFormList[1].isRequired" value="1">
			    是</label></td>
			<td></td>
			<td></td>
		</tr>
		<c:if test="${empty appointment.id }">
			<tr data-attr="item">
				<td>
				<input type="hidden" name="dynamicFormList[2].sort" value="2" data-sort>
				<input type="hidden" name="dynamicFormList[2].type" value="DATETIME">
				<input type="hidden" name="dynamicFormList[2].innerSort" value="3">
				日期
				</td>
				<td><input type="text" readonly="readonly" placeholder="备注" class="form-control"  name="dynamicFormList[2].lable" validate="{required:true}"
				    maxlength="10" value="预约时间" data-val></td>
				<td><input type="text" readonly="readonly" placeholder="无" name="dynamicFormList[2].placeHolder" id="placeHolder-2"
					value="无" class="form-control" data-place></td>
				<td><label for="">
				    <input type="hidden" name="dynamicFormList[2].isRequired" value="1">
				    是</label></td>
				<td><a href="javascript:void(0)" data-attr="move-down"><img
						src="${resourcePath }img/up.jpg" alt=""></a> <a
					href="javascript:void(0)" data-attr="move-up"><img
						src="${resourcePath }img/down.jpg" alt=""></a></td>
				<td></td>
			</tr>
			<tr data-attr="item">
				<td>
				<input type="hidden" name="dynamicFormList[3].sort" value="3" data-sort>
				<input type="hidden" name="dynamicFormList[3].type" value="TEXTAREA">
				<input type="hidden" name="dynamicFormList[3].innerSort" value="4">
				多文本
				</td>
				<td><input type="text" placeholder="备注" readonly="readonly" class="form-control"  name="dynamicFormList[3].lable" validate="{required:true}"
				    maxlength="10" value="备注" data-val></td>
				<td><input type="text" readonly="readonly" placeholder="请输入备注信息" name="dynamicFormList[3].placeHolder" id="placeHolder-3"
					value="请输入备注信息" class="form-control" data-place></td>
				<td><label for="">
				    <input type="hidden" name="dynamicFormList[3].isRequired" value="1">
				    是</label></td>
				<td><a href="javascript:void(0)" data-attr="move-down"><img
						src="${resourcePath }img/up.jpg" alt=""></a> <a
					href="javascript:void(0)" data-attr="move-up"><img
						src="${resourcePath }img/down.jpg" alt=""></a></td>
				<td></td>
			</tr>
		</c:if>
		<c:forEach items="${dynamicFormList }" var="data" varStatus="i">
		<c:if test="${i.index>1&&data.innerSort==0 }">
			<tr data-attr="item">
				<td>
				<input type="hidden" name="dynamicFormList[${i.index }].sort" value="${data.sort }" data-sort>
				<select class="form-control" name="dynamicFormList[${i.index }].type" data-select>
						<option
							<c:if test="${data.type == 'DATETIME' }">selected="selected"</c:if>
							value="DATETIME">日期</option>
						<option
							<c:if test="${data.type == 'INPUT' }">selected="selected"</c:if>
							value="INPUT">单文本</option>
						<option
							<c:if test="${data.type == 'TEXTAREA' }">selected="selected"</c:if>
							value="TEXTAREA">多文本</option>
						<option
							<c:if test="${data.type == 'SELECT' }">selected="selected"</c:if>
							value="SELECT">下拉框</option>
				</select></td>
				<td><input type="text" placeholder="备注" class="form-control"  name="dynamicFormList[${i.index }].lable" validate="{required:true}"
				    maxlength="10" value="${data.lable }" data-val></td>
				<td><input type="text" <c:if test="${data.type == 'DATETIME' }">disabled="disabled"</c:if> placeholder="无" name="dynamicFormList[${i.index }].placeHolder" id="placeHolder-${i.index }"
					value="${data.placeHolder }" class="form-control" data-place></td>
				<td><label for="">
				    <input type="hidden" name="dynamicFormList[${i.index }].isRequired" value="${data.isRequired }">
				    <input
						<c:if test="${data.isRequired }">checked="checked"</c:if>
						name="isRequired" type="checkbox" onclick="changeRequired(this, ${i.index})">是</label></td>
				<td><a href="javascript:void(0)" data-attr="move-down"><img
						src="${resourcePath }img/up.jpg" alt=""></a> <a
					href="javascript:void(0)" data-attr="move-up"><img
						src="${resourcePath }img/down.jpg" alt=""></a></td>
				<td><a href="javascript:void(0)" class="btn-del"
					data-attr="del">删除</a></td>
			</tr>
		</c:if>
		<c:if test="${data.innerSort==3 }">
			<tr data-attr="item">
				<td>
				<input type="hidden" name="dynamicFormList[${i.index }].sort" value="${i.index }" data-sort>
				<input type="hidden" name="dynamicFormList[${i.index }].type" value="DATETIME">
				日期
				</td>
				<td><input type="text" readonly="readonly" placeholder="备注" class="form-control"  name="dynamicFormList[${i.index }].lable" validate="{required:true}"
				    maxlength="10" value="预约时间" data-val></td>
				<td><input type="text" readonly="readonly" placeholder="无" name="dynamicFormList[${i.index }].placeHolder" id="placeHolder-${i.index }"
					value="无" class="form-control" data-place></td>
				<td><label for="">
				    <input type="hidden" name="dynamicFormList[${i.index }].isRequired" value="1">
				    是</label></td>
				<td><a href="javascript:void(0)" data-attr="move-down"><img
						src="${resourcePath }img/up.jpg" alt=""></a> <a
					href="javascript:void(0)" data-attr="move-up"><img
						src="${resourcePath }img/down.jpg" alt=""></a></td>
				<td></td>
			</tr>
		</c:if>
		<c:if test="${data.innerSort==4 }">
			<tr data-attr="item">
				<td>
				<input type="hidden" name="dynamicFormList[${i.index }].sort" value="${i.index }" data-sort>
				<input type="hidden" name="dynamicFormList[${i.index }].type" value="TEXTAREA">
				多文本
				</td>
				<td><input type="text" readonly="readonly" placeholder="备注" class="form-control"  name="dynamicFormList[${i.index }].lable" validate="{required:true}"
				    maxlength="10" value="备注" data-val></td>
				<td><input type="text" readonly="readonly" placeholder="请输入备注信息" name="dynamicFormList[${i.index }].placeHolder" id="placeHolder-${i.index }"
					value="请输入备注信息" class="form-control" data-place></td>
				<td><label for="">
				    <input type="hidden" name="dynamicFormList[${i.index }].isRequired" value="1">
				    是</label></td>
				<td><a href="javascript:void(0)" data-attr="move-down"><img
						src="${resourcePath }img/up.jpg" alt=""></a> <a
					href="javascript:void(0)" data-attr="move-up"><img
						src="${resourcePath }img/down.jpg" alt=""></a></td>
				<td></td>
			</tr>
		</c:if>
		</c:forEach>
	</tbody>
</table>
<script>
var index;

$(function(){
	$.metadata.setType("attr", "validate");
	index = initIndex();
	if(index==1)index=5;
})
function add(){
	var str = [
	       	'<tr data-attr="item">',
	       		'<td>',
	       			'<input type="hidden" name="dynamicFormList['+index+'].sort" value="'+index+'" data-sort>',
	       			'<select class="form-control" name="dynamicFormList['+index+'].type" id="" data-select>',
	       				'<option value="DATETIME">日期</option>',
	       				'<option  value="INPUT">单文本</option>',
	       				'<option value="TEXTAREA">多文本</option>',
	       				'<option selected value="SELECT">下拉框</option>',
	       			'</select>',
	       		'</td>',
	       		'<td><input type="text" placeholder="下拉框名" name="dynamicFormList['+index+'].lable" validate="{required:true}" class="form-control" data-val></td>',
	       		'<td><input type="text" placeholder="每个选项以“|”隔开" name="dynamicFormList['+index+'].placeHolder" class="form-control" data-place></td>',
	       		'<td><label for="">',
	       		'<input type="hidden" name="dynamicFormList['+index+'].isRequired" value="false">',
	       		'<input name="isRequired" type="checkbox" onclick="changeRequired(this, '+index+')">是</label></td>',
	       		'<td>',
	       			'<a href="javascript:void(0)" data-attr="move-down"><img src="${resourcePath }img/up.jpg" alt=""></a>',
	       			'<a href="javascript:void(0)" data-attr="move-up"><img src="${resourcePath }img/down.jpg" alt=""></a>',
	       		'</td>',
	       		'<td>',
	       			'<a href="javascript:void(0)" class="btn-del" data-attr="del">删除</a>',
	       		'</td>',
	       	'</tr>'
	       ].join('');
	var trlen = $('#template tbody tr').length;
	var tdlen = $('#template tbody tr td').length;
	if (trlen == 1 && tdlen == 1) {
		 $('#template tbody').html(null);
	}
	addAttr.setHtml(str);
	index = index + 1;
	
}
function changeRequired(el, i){
	var isRequired = $(el).attr('checked') == 'checked';
	if (isRequired) {
		$(el).val(true);
		$(el).prev().val(true);
	} else {
		$(el).val(false);
		$(el).prev().val(false);
	}
}
function initIndex() {
	var index = '${fn:length(dynamicFormList)}';
	if (null == index || '' == index) {
		index = 0;
	}
	parseInt(index)
	index++;
	return index;
}
</script>