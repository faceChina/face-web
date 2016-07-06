<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${empty propMap && isNotKeyInput==true}">
<input type="hidden" name="skuList[0].id" value="${skuList[0].id }"/>
<tr>
	<th><b class="clr-attention">*</b>商城价：</th>
	<td><input type="text" id="j-price" name="salesPriceYuan" value="<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}" />" 
		class="form-control input-short-5" maxlength="15" 
		validate="{required:true,moneyNotOne:true,lessAndEqualTo:'#marketPriceYuan',messages:{required:'商城价不能为空',lessAndEqualTo:'温馨提示：商城价应小于等于市场价'}}"> 元
	</td>
</tr>
<tr>
	<th>商品属性：</th>
	<td>
		<button type="button" class="btn btn-default j-addshop" >添加</button>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td>
		<table class="j-table">
			<tbody>
			<c:choose >
				<c:when test="${not empty notKeyProperties}">
					<c:forEach items="${notKeyProperties}" var="notKeyProperties" varStatus="status">
						<tr>
							<td><b class="clr-attention">*</b>属性：</td>
							<td><input type="text" name="goodPropertyMap[${status.index}].propName" maxlength=32 value="${notKeyProperties.propName}" class="form-control" validate="{required:true, messages:{required:'属性不能为空'}}"/></td>
							<td><b class="clr-attention">*</b>属性值：</td>
							<td><input type="text" name="goodPropertyMap[${status.index}].propValueName" maxlength="32" value="${notKeyProperties.propValueName}" class="form-control" validate="{required:true, messages:{required:'属性值不能为空'}}"/></td>
							<c:if test="${status.index!=0}">
							<td><button type="button" class="btn btn-default j-delshop">删除</button></td>
							</c:if>
						</tr>
					</c:forEach>
				</c:when>  
				<c:otherwise>
					<tr>
						<td><b class="clr-attention">*</b>属性：</td>
						<td><input type="text" name="goodPropertyMap[0].propName" maxlength="20"  class="form-control" validate="{required:true, messages:{required:'属性不能为空'}}"/></td>
						<td><b class="clr-attention">*</b>属性值：</td>
					 	<td><input type="text" name="goodPropertyMap[0].propValueName" maxlength="20" class="form-control" validate="{required:true, messages:{required:'属性值不能为空'}}"/></td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</td>
</tr>
<tr>
	<th><b class="clr-attention">*</b>商品数量：</th>
	<td>
		<input type="text" value="${good.inventory}" name="inventory"  class="form-control input-short-4" maxlength="8" validate="{required:true,range:[1,99999999],messages:{required:'商品总数不能为空',range:'商品数量必须在1~99999999之间'}}" /> 件
	</td>
</tr>
</c:if>