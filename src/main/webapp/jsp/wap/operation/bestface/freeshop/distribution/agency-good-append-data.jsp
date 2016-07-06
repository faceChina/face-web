<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:forEach items="${pagination.datas }" var="good">
	<div class="group-item">
		<div class="group-rowspan">
			<div class="group-colspan">
				<div class="product">
					<a href="/free/agency/${good.id}/goodDetail.htm" class="product-item">
						<div class="product-img">
							<img src="${picUrl}${good.picUrl }" width="80" alt="产品图片">
						</div>
						<div class="product-info">
							<p class="product-title txt-rowspan2">${good.name }</p>
							<p class="product-other">
								<b class="clr-danger">¥
									<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/>
								</b>
							</p>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
</c:forEach>