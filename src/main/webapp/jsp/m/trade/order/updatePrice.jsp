<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 修改定单 -->
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>商品</th>
					<th width="160">单价</th>
					<th width="100">数量</th>
					<th width="160">小计</th>
					<th width="160">运费</th>
					<th width="160">积分抵价</th>
					<th width="120">+涨价/-减价</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${salesOrder.orderItemList}" var="orderItem" varStatus="status">
					<c:choose>
						<c:when test="${status.index == 0 }">
							<tr>
								<td>
									<div class="text-left">
										<p>${orderItem.goodName }</p>
										<p>${orderItem.skuPropertiesName }</p> 
									</div>
								</td>
								<td>￥<fmt:formatNumber pattern="0.00" value="${orderItem.discountPrice/100 }"/></td>
								<td>${orderItem.quantity }</td>
								<td>￥<fmt:formatNumber pattern="0.00" value="${orderItem.totalPrice/100 }"/></td>
			                    <td ROWSPAN="${fn:length(salesOrder.orderItemList)}"><fmt:formatNumber pattern="0.00" value="${salesOrder.postFee/100}"/></td>
			                    <td ROWSPAN="${fn:length(salesOrder.orderItemList)}"><fmt:formatNumber pattern="0.00" value="${salesOrder.discountPrice/100 }"/></td>
			                    <td ROWSPAN="${fn:length(salesOrder.orderItemList)}">
			                         <div>共计：<span>￥<fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice/100}"/></span></div>
			                         <c:choose>
										<c:when test="${not empty salesOrder.adjustPrice}">
											<input type="text" id="adjustPriceYuan" value="<fmt:formatNumber pattern="0.00" value="${salesOrder.adjustPrice/100}"/>" class="form-control" onkeyup="updateAllTimes(this)"/>
										</c:when>
										<c:otherwise>
											<input type="text" id="adjustPriceYuan" value="0" class="form-control" onkeyup="updateAllTimes(this)"/>
										</c:otherwise>
									</c:choose>
			                    </td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>
									<div class="text-left">
										<p>${orderItem.goodName }</p>
										<p>${orderItem.skuPropertiesName }</p> 
									</div>
								</td>
								<td>￥<fmt:formatNumber pattern="0.00" value="${orderItem.discountPrice/100 }"/></td>
								<td>${orderItem.quantity }</td>
								<td>￥<fmt:formatNumber pattern="0.00" value="${orderItem.totalPrice/100 }"/></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7">
						<div class="text-left">
							<p id="priceCount">买家实付： <span ><fmt:formatNumber pattern="0.00" value="${salesOrder.price/100}"/></span>
							+ <span><fmt:formatNumber pattern="0.00" value="${salesOrder.postFee/100}"/></span>
							- <span><fmt:formatNumber pattern="0.00" value="${salesOrder.discountPrice/100}"/></span>
							<c:choose>
								<c:when test="${not empty salesOrder.adjustPrice}">
									<span id="updateNumber" ><c:if test="${salesOrder.adjustPrice > 0}">+</c:if><fmt:formatNumber pattern="0.00" value="${salesOrder.adjustPrice/100}"/></span> 
								</c:when>
								<c:otherwise>
									<span id="updateNumber">+0.00</span> 
								</c:otherwise>
							</c:choose>
								= <span><fmt:formatNumber pattern="0.00" value="${salesOrder.totalPrice/100}"/></span>
							</p>
							<p>买家实付 = 原价 + 运费 - 优惠金额    + 涨价或减价</p>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>