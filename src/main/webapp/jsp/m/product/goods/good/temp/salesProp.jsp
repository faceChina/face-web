<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${not empty propMap}">
	<input type="hidden"  id="checkpop" validate="{checkpop:true}">
	<c:forEach items="${propMap}" var="propMap">
		<c:choose>
			<c:when test="${propMap.key.isColorProp}">
				<tr>
					<th><b class="clr-attention">*</b>${propMap.key.name}：</th>
					<td>
						<ul class="pro-color" id="j-proColor">
						<c:forEach items="${propMap.value}" var="propValue">
							<c:choose>
								<c:when test="${empty propValue.hex}">
									<li>
										<img src="${resourcePath}/img/${propValue.code}.jpg" alt="">
										<input type="hidden" value="" name='goodPropertyMap["${propValue.code}"].id' disabled="disabled">
										<input type="checkbox" value="${propValue.id}" name='goodPropertyMap["${propValue.code}"].propValueId'>
										<input type="text" value="${propValue.name}" class="form-control" name='goodPropertyMap["${propValue.code}"].propValueAlias' disabled="disabled">
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<i style="background: ${propValue.hex};"></i>
										<input type="hidden" value="" name='goodPropertyMap["${propValue.code}"].id' disabled="disabled">
										<input type="checkbox" value="${propValue.id}" name='goodPropertyMap["${propValue.code}"].propValueId'>
										<input type="text" value="${propValue.name}" class="form-control" name='goodPropertyMap["${propValue.code}"].propValueAlias' disabled="disabled">
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						</ul>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<table class="table table-bordered  table-mesa" id="template2" >
							<thead>
								<tr>
									<th>颜色</th>
									<th>图片（无图可不填）</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${propertyImgs}" var="propertyImgs">
									<tr class="${propertyImgs.key}">
										<td class="colorName">${propertyImgs.value.propValueAlias}</td>
										<td>
											<div class="btn-upload">
											<input type="hidden" name="itemPath" value="${propertyImgs.value.picturePath}">
											<input type="hidden" name="propertyImgs[${propertyImgs.key}].id" value="${propertyImgs.value.id}">
											<c:choose>
												<c:when test="${not empty propertyImgs.value.picturePath}">
													<img name="itemFile" src="${picUrl}${propertyImgs.value.picturePath}" style="width:100px;height:100px;" alt="" class="">
												</c:when>
												<c:otherwise>
													<img name="itemFile" src="${resourcePath}img/add100X100.jpg" style="width:100px;height:100px;" alt="" class="">
												</c:otherwise>
											</c:choose>
											<input type="hidden" name="propertyImgs[${propertyImgs.key}].picturePath" value="${propertyImgs.value.picturePath}">
											<input type="hidden" name="propertyImgs[${propertyImgs.key}].propValueId" value="${propertyImgs.key}">
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</c:when>
			<c:when test="${propMap.key.isEnumProp}">
				<tr>
					<th><b class="clr-attention">*</b>${propMap.key.name}：</th>
					<td>
						<ul class="pro-color pro-size" id="j-proSize">
							<c:forEach items="${propMap.value}" var="propValue">
								<li>
									<input type="hidden" value="" name='goodPropertyMap["${propValue.code}"].id' disabled="disabled">
									<input type="checkbox" value="${propValue.id}" name='goodPropertyMap["${propValue.code}"].propValueId'>
									<input type="text" value="${propValue.name}" class="form-control" name='goodPropertyMap["${propValue.code}"].propValueAlias' disabled="disabled">
								</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				加载失败了
			</c:otherwise>
		</c:choose>
	</c:forEach>
		<td>&nbsp;</td>
		<td>
			<table class="table table-bordered  table-mesa" id="template">
				<thead>
					<tr>
						<th>颜色</th>
						<th><b class="clr-attention">*</b>商品价格</th>
						<th><b class="clr-attention">*</b>商品数量</th>
						<th>商品型号</th>
						<th>商品条码</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</td>
	</tr>
	<tr>
		<th>商城价：</th>
		<td>
			<input type="text" name="salesPriceYuan"  value="<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100}"/>" class="form-control input-short-4" disabled="disabled" id="j-price" > 元
		</td>
	</tr>
	<tr>
		<th>商品总数：</th>
		<td>
			<input type="text"  name="inventory" value="<fmt:formatNumber pattern="0" value="${good.inventory}"/>" class="form-control input-short-4" disabled="disabled" id="j-total"/> 件
		</td>
	</tr>	
</c:if> 