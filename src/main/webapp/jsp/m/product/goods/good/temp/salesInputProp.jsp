<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${empty propMap && isSalesInput==true}">
<tr>
	<td>商品属性：</td>
	<td>
		<input type="hidden" name="freeGoodSku" id="freeGoodSku"/>
		<table class="table table-bordered  table-mesa" id="template">
			<thead>
				<tr>
					<th width="30%">属性</th>
					<th width="25%">价格（元）</th>
					<th width="25%">库存（件）</th>
					<th width="20%">操作</th>
				</tr>
			</thead>
			<tbody data-attr="box">
				<c:choose>
					<c:when test="${not empty skuList}">
						<c:forEach items="${skuList}" var="goodSku" varStatus="status">
							<c:set var="count" value="${status.index }"/>
							<tr data-attr="item">
								<td><input type="hidden" name="nskuid"  value="${goodSku.id}"><input data-name name="attributeName${status.index }" type="text" validate="{required:true, messages:{required:'属性不能为空'}}" value="${goodSku.skuPropertiesName}"></td>
								<td><input data-price name="skuPrice${status.index }" type="text" validate="{required:true,moneyNotOne:true, messages:{required:'价格不能为空'}}" value="<fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100}"/>"></td>
								<td><input data-stock name="stock${status.index }" type="text" validate="{required:true,range:[1,99999999],messages:{required:'商品数量不能为空',range:'商品数量必须在1~99999999之间'}}"  value="${goodSku.stock}"></td>
								<td>
									<c:if test="${status.index+1 != 1 }">
									<button type="button" class="btn btn-link" data-attr="del">删除</button>
									</c:if>
								</td>
								
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr data-attr="item">
							<td><input data-name name="attributeName0" validate="{required:true, messages:{required:'属性不能为空'}}" type="text" value=""></td>
							<td><input data-price name="skuPrice0" validate="{required:true,moneyNotOne:true, messages:{required:'价格不能为空'}}" type="text" value=""></td>
							<td><input data-stock name="stock0" validate="{required:true,range:[1,99999999],messages:{required:'商品数量不能为空',range:'商品数量必须在1~99999999之间'}}" type="text" value=""></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</td>
</tr>
<tr>
	<th></th>
	<td>
		<button type="button" class="btn btn-default j-addshop" data-attr="add">添加属性</button>
	</td>
</tr>
	<script type="text/javascript" src="${resourcePath}js/public.js"></script>
	<script>
	$(function(){
		$("[data-attr=add]").click(function(){
			str = [
			   		'<tr data-attr="item">',
			   			'<td><input data-name name="attributeName'+(++index)+'" validate="{required:true, messages:{required:\'属性不能为空\'}}" type="text" value=""></td>',
			   			'<td><input data-price name="skuPrice'+index+'" validate="{required:true,moneyNotOne:true, messages:{required:\'价格不能为空\'}}" type="text" value=""></td>',
			   			'<td><input data-stock name="stock'+index+'" validate="{required:true,range:[1,99999999],messages:{required:\'商品数量不能为空\',range:\'商品数量必须在1~99999999之间\'}}" type="text" value=""></td>',
			   			'<td><button type="button"  data-attr="del" class="btn btn-link">删除</button></td>',
			   		'</tr>'
			   	].join('');
			addAttr.setHtml(str);
		})
	})
	var index='${count}';
	var str = [
		'<tr data-attr="item">',
			'<td><input data-name name="attributeName'+(++index)+'" validate="{required:true, messages:{required:\'属性不能为空\'}}" type="text" value=""></td>',
			'<td><input data-price name="skuPrice'+index+'" validate="{required:true,moneyNotOne:true, messages:{required:\'价格不能为空\'}}" type="text" value=""></td>',
			'<td><input data-stock name="stock'+index+'" validate="{required:true,range:[1,99999999],messages:{required:\'商品数量不能为空\',range:\'商品数量必须在1~99999999之间\'}}" type="text" value=""></td>',
			'<td><button type="button"  data-attr="del" class="btn btn-link">删除</button></td>',
		'</tr>'
	].join('');

	//添加 客户提交信息设置
	addAttr.setHtml(str); 
	function getGoodItem() {
		var getArr = [];
		$("[data-attr='box']").find('tr').each(function(){
			var baseData = {};
			var price = $(this).find("input[data-price]").val();
			baseData["id"] = $(this).find("input[name=nskuid]").val();
			baseData["name"] = $("#goodName").val();
			baseData["attributeName"] = $(this).find("input[data-name]").val();
			baseData["salesPrice"] =accMul(price,100);
			baseData["stock"] = $(this).find("input[data-stock]").val();
			getArr.push(baseData);
		});
		console.log(JSON.stringify(getArr))
		return JSON.stringify(getArr);
	}
	</script>
</c:if>