<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@include file="../../../../common/base.jsp" %>
<c:forEach items="${pagination.datas }" var="data">
	<c:if test="${data.classificationId!=15 }">
	<div class="m-pro-inline" data-btn="true">
		<div class="m-pro-link" data-id="${data.id }">
			<span  class="m-pro-btn" data-active="true">
				<c:choose>
					<c:when test="${data.inventory==0}">
						<a class="btn btn-default" href="${ctx}/free/good/edit/${data.id}${ext}">编辑</a>
					</c:when>
					<c:when test="${data.status==1 && data.inventory!=0}">
						<button class="btn btn-default" data-type="sellout">下架</button>
					</c:when>
					<c:when test="${data.status==3 && data.inventory!=0}">
						<button class="btn btn-default" data-type="sell">上架</button>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<span class="input-checkbox" data-type="check"></span>
			</span>
			<a class="m-pro-info" href="${ctx}/free/good/detail${ext}?id=${data.id}">
				<span class="m-pro-img"><img src="${picUrl}${data.path}" width="70" height="70" alt="img"></span>
				<span class="m-pro-title" >${data.name}</span>
				<span class="m-pro-price"><i class="price-promote">¥<fmt:formatNumber pattern="0.00" value="${data.salesPrice/100}"/></i></span>
				<span class="m-pro-price"><i class="price-cost">库存数：${data.inventory}</i></span>
			</a>
			
		</div>
	</div>
	</c:if>
</c:forEach>
