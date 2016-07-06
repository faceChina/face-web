<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../common/base.jsp"%>
<script>$(function(){
	$(".addel-radius").each(function(){
		var self = $(this);
		$(this).addelRadius({
			number:self.data('total'),//后台传数据
			price:self.data('price'),
			add:function(){
				jiage+=Number(this.price);
			},
			del:function(){
				jiage-=Number(this.price);
			}
		})
	});
})</script>
	<div class="group-item">
	<input type="hidden" id="num" value="${num }"/>
	<input type="hidden" id="total" value="<fmt:formatNumber pattern='0.00' value='${total/100 }'/>" />
	<input type="hidden" id="json" value='${json }' data-val='${json }'/>
	<input type="hidden" id="currentTypeNum" value="${currentTypeNum}"/>
	<c:forEach items="${goodSkuList }" var="goodSku">
		<ul class="group-rowspan list-position">
			<li class="group-colspan">
				<span class="txt-rowspan1">${goodSku.name }</span>
				<span class="clr-danger">￥<fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100 }"/></span>
			</li>
			<li class="group-colspan add-position" style="bottom:0;">
				<div class="addel-radius" id="addel5" data-total="${goodSku.stock }" data-price='<fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100 }"/>'>
				    <span class="addel-del"><i class="iconfont icon-jianhao"></i></span>
				    <span class="addel-info"><input type="tel" data-id="${goodSku.id }" readonly="true" value="<c:if test='${empty goodSku.quantity }'>0</c:if>${goodSku.quantity}"></span>
				    <span class="addel-add" <c:if test="${goodSku.quantity==goodSku.stock||goodSku.stock==0 }">disabled="disabled"</c:if>><i class="iconfont icon-add"></i></span>
				</div>
			</li>
		</ul>
	</c:forEach>
	</div>
