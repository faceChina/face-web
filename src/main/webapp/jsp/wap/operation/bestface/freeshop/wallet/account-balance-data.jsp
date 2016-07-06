<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sessionShopNo" value="${sessionScope.shop.no}"/>
<c:forEach items="${pagination.datas }" var="data">
	<div class="group-item">
		<a href="/free/wallet/detail.htm?id=${data.id}" class="group-rowspan"> <span
			class="group-colspan"> <em>${data.action }</em><br> <em
				class="clr-light fnt-12">余额：${data.balanceString }</em>
		</span> <span class="group-colspan"> <em class="clr-light fnt-12"><fmt:formatDate value="${data.createTime }" type="date" pattern="yyyy-MM-dd" /></em><br>
				<b 
				<c:choose>
					<c:when test="${data.isFrom }">class="clr-danger"</c:when>
					<c:otherwise>class="clr-blue"</c:otherwise>
				</c:choose> 
				>${data.operationPrice }</b>
		</span>
		</a>
	</div>
</c:forEach>