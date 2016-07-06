<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 卖家 -->
<c:set value = "0" var="shopStatus"/>
<c:forEach items="${cartOrderVo.sellerMap}" var="sellerMap">
	<div class="seller" data-userid="${sellerMap.key}">
		<c:forEach items="${sellerMap.value}" var="sellerVo" varStatus="sellerStatus">	
			<!-- 以下是消费送积分规则 -->
			<c:if test="${not empty sellerVo.giftCondition && sellerVo.giftCondition != '0'}">
				<input data-id="gift-condition" value="${sellerVo.giftCondition}" type="hidden"><!-- 消费送积分条件 (单位：分)-->
				<input data-id="gift-result" value="${sellerVo.giftResult}" type="hidden"><!-- 消费送积分结果 (单位：个)-->
			</c:if>
			<!-- 以下是积分抵价规则 -->
			<c:if test="${not empty sellerVo.deductionCondition  && sellerVo.deductionCondition != '0'}">
			<input data-id="user-integral" value="${sellerVo.userIntegral}" data-surplus="${sellerVo.userIntegral}" type="hidden">		<!-- 买家在该店铺当前积分个数(单位：个) -->
			<input data-id="deduction-condition" value="${sellerVo.deductionCondition}" type="hidden"><!-- 积分抵价条件 (单位：个)-->
			<input data-id="deduction-result" value="${sellerVo.deductionResult}" type="hidden"><!-- 积分抵价结果(单位：分)-->
			<input data-id="deduction-maxrete" value="${sellerVo.deductionMaxrete}" type="hidden"><!--  (单位：百分数)-->
			</c:if>
			<c:if test="${not empty sellerVo.matchCoupons }">
				<div data-id="shop-coupons" data-best-value="100000" data-best-coupons="10000" data-best-couponsid="001">
					<c:forEach items="${sellerVo.matchCoupons }" var="coupon">
						<input  value="${coupon.orderPrice }" data-shop-coupons="${coupon.faceValue }" type="hidden" data-couponsid="${coupon.id }">
					</c:forEach>
				</div>
			</c:if>
			<!-- 店铺列表开始 -->
			<c:forEach items="${sellerVo.shopMap}" var="shopMap" >
			<div class="group group-justify" data-shop-id="${shopMap.key.no}">
				<input type="hidden" name="orderBuyList[${sellerStatus.index}${shopStatus }].shopNo" value="${shopMap.key.no}">
				<input type="hidden" name="orderBuyList[${sellerStatus.index}${shopStatus }].buyShopNo" value="${shopMap.key.buyShopNo}">
				<input type="hidden" data-type="shopindex"  name="orderBuyList[${sellerStatus.index}${shopStatus }].deductionIndex" value="">
				<input type="hidden" deliveryway name="orderBuyList[${sellerStatus.index}${shopStatus }].deliveryWay" value="${deliveryMap[shopMap.key.no] }">
				<input type="hidden" pickupid name="orderBuyList[${sellerStatus.index}${shopStatus }].pickUpId" value="">
<%-- 				<input type="hidden" couponButton name="orderBuyList[${sellerStatus.index}${shopStatus }].isCoupon" value=""/> --%>
				<input type="hidden" data-name="couponsid" name="orderBuyList[${sellerStatus.index}${shopStatus }].couponId"  value=""  />
				<input type="hidden" data-freight value="${shopMap.key.postFee}"/>
				<div class="group-item">
					<div class="group-rowspan">
						<div class="group-colspan">店铺名称：<em data-shop-name>${shopMap.key.name}</em></div>
						<div class="group-colspan"></div>
					</div>
				</div>

				<!-- 商品列表 开始-->
				<c:forEach items="${shopMap.value}" var="dto" varStatus="goodStatus">
					<div class="group-item" data-pro-id="${dto.id}" data-good-id="${dto.goodId }">	
						<input type="hidden" name="orderBuyList[${sellerStatus.index}${shopStatus }].buyItemList[${goodStatus.index}].id" value="${dto.id}">
						<input type="hidden" data-stock value="${dto.stock}">	
						<div class="group-rowspan">
							<div class="list-col">
								<div class="list-inline"><img data-src src="${picUrl}${dto.skuPicturePath}" alt="" width="70" height="70"></div>
								<div class="list-top box-flex">
									<ul>
										<li class="txt-rowspan2"  data-title>${dto.goodName}</li>
										<c:if test="${not empty dto.skuPropertiesName}">
											<li class="clr-light txt-rowspan1 fnt-12">${dto.skuPropertiesName}</li>
										</c:if>
									</ul>
								</div>
								<div class="list-top shop-other"  style="text-align:right;">	
									<ul>
										<li>￥<em data-price><fmt:formatNumber pattern="0.00" value="${dto.unitPrice/100}"/></em></li>
										<c:if test="${not empty dto.salesPrice && 0 < dto.salesPrice}">
											<li class="clr-light"><del>￥<fmt:formatNumber pattern="0.00" value="${dto.salesPrice/100}"/></del></li>
										</c:if>
									</ul>
								</div>
							</div>
						</div>
						<div class="group-rowspan">
							<div class="group-colspan">
								购买数量：
							</div>
							<div class="group-colspan">
								<div class="addel">
								    <span class="addel-del"><i class="iconfont icon-jianhao"></i></span>
								    <span class="addel-info"><input type="tel"  name="orderBuyList[${sellerStatus.index}${shopStatus }].buyItemList[${goodStatus.index }].quantity" value="${dto.quantity}" readonly="readonly" data-number></span>
								    <span class="addel-add"><i class="iconfont icon-add"></i></span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				<!-- 商品列表 结束-->
				
				<c:if test="${!isProtocolGood }">
				<c:set var="types" value = "${shopMap.key.logisticsTypes}" />
				<div class="group-item">
				<div class="group-rowspan" <c:if test="${types != 1}"> onclick="toSelectDelivery(this,'${shopMap.key.no}')" </c:if> data-freight="0">
					<div class="group-colspan">
						配送方式：
					</div>
					<div class="group-colspan">
						<span data-delivery-name>
							<c:choose>
								<c:when test="${deliveryMap[shopMap.key.no]==1 }">
									<c:if test="${shopMap.key.postFee != 0}">快递：￥<fmt:formatNumber pattern="0.00" value="${shopMap.key.postFee/100 }"/></c:if>
									<c:if test="${shopMap.key.postFee == 0}">快递：包邮</c:if>
								</c:when>
								<c:when test="${deliveryMap[shopMap.key.no]==2 }">店铺配送</c:when>
								<c:when test="${deliveryMap[shopMap.key.no]==3 }">门店自提</c:when>
							</c:choose>
						</span>
						<%-- <c:if test="${types != 1 && types != 2 && types != 4}"> --%>
						<c:if test="${types != 1}">
						<i class="iconfont icon-right"></i>
						</c:if>
					</div>
				</div>
				<c:if test="${not empty sellerVo.matchCoupons }">
					<div class="group-rowspan">
						<div class="group-colspan" data-coupons>
							
						</div>
						<div class="group-colspan">
							<div class="onoff onoff-off" id="couponButton" data-onoff="off" data-onoff-type="1" data-type="onoff">
								<span class="onoff-handle"></span>
								<span class="onoff-info">●●●</span>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty sellerVo.deductionCondition  && sellerVo.deductionCondition != '0'}">
					<div class="group-rowspan">
						<div class="group-colspan" data-support>积分抵扣：</div>
						<div class="group-colspan">
							<div class="onoff onoff-off" data-onoff="off" data-onoff-type="0"  data-type="onoff">
								<span class="onoff-handle"></span>
								<span class="onoff-info">●●●</span>
							</div>
						</div>
					</div>
				</c:if>
				</div>
				</c:if>
				
				
				<div class="group-item">
					<div class="list-col"  style="border-bottom:1px solid #eee;">
						<div class="box-flex">
							<input type="text" name="orderBuyList[${sellerStatus.index}${shopStatus }].buyerMessage" name="" id="" placeholder="给卖家留言"  data-msg/>
						</div>
					</div>
					<!-- 当多个店铺时显示 -->
					<div class="group-rowspan clr-light">
						<div class="group-colspan">
							共 <em data-shop-number></em> 件
							<!-- 会员订单确认页面  -->
							<c:if test="${not empty sellerVo.giftCondition  && sellerVo.giftCondition != '0'}">
								可获 <em data-shop-score></em> 积分
							</c:if>
							<!-- 会员订单确认页面 end -->
						</div>
						<div class="group-colspan">
							总计：￥<em data-shop-price></em>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>

			<!-- 店铺列表结束 -->
		</c:forEach>
		<c:set var="shopStatus" value="${shopStatus+1}"/>
	</div>
</c:forEach>