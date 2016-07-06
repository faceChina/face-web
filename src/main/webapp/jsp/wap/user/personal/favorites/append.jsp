<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${type == 1 }">
<c:forEach items="${pagination.datas }" var="data">
	<div class="group-item">
		<div class="group-rowspan">
			<div class="group-colspan group-colspan-first">
			<c:if test="${data.remoteStauts == 1 }">
				<span class="font-null"></span>
			</c:if>
			<c:if test="${data.remoteStauts != 1 }">
				<span class="btn btn-block disabled font-sx">失<br>效</span>
			</c:if>
			</div>
			<div class="group-colspan group-colspan-product">
				<a <c:if test="${data.remoteStauts == 1 }">href="/wap/${data.goodShopNo }/any/item/${data.remoteId}.htm"</c:if>>
					<div class="product">
						<div class="product-item">
							<div class="product-img">
								<img src="${picUrl }${data.remotePicUrl }" alt="产品图片">
							</div>
							<div class="product-info">
								<div data-master>
									<p class="product-title">${data.remoteName }</p>
									<p class="product-other">¥<fmt:formatNumber pattern="0.00" value="${data.remotePrice/100 }"></fmt:formatNumber>
									</p>
								</div>
							</div>
						</div>
					</div>
				</a>	
			</div>
			<div class="group-colspan group-colspan-single">
				<i class="iconfont icon-yuanquanweixuan none" data-select-single data-id="${data.id}"></i>
			</div>
		</div>
	</div>
</c:forEach>
</c:if>
<c:if test="${type == 2 }"> 
	<div class="group-item">
		<div class="group-rowspan">
			<div class="group-colspan group-colspan-first">
				<c:if test="${data.remoteStauts == 1 }">
					<span class="font-null"></span>
				</c:if>
				<c:if test="${data.remoteStauts != 1 }">
					<span class="btn btn-block disabled font-sx">失<br>效</span>
				</c:if>
			</div>
			<div class="group-colspan group-colspan-product">
				<a <c:if test="${data.remoteStauts == 1 }">href="/wap/${sessionShopNo }/buy/personal/favorites/toShop/${data.id}.htm"</c:if>>
					<div class="product">
						<div class="product-item">
							<div class="product-img">
								<img src="${picUrl }${data.remotePicUrl}" alt="产品图片">
							</div>
							<div class="product-info">
								<div data-master>
									<p class="product-title">${data.remoteName}</p>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			<div class="group-colspan group-colspan-single">
				<i class="iconfont icon-yuanquanweixuan none" data-select-single data-id="${data.id}"></i>
			</div>
		</div>
	</div>
</c:if>