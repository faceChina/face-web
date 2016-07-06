<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${shopVo.kdType==1 }">
<div class="group group-justify" data-delivery-way='1'>
	<div class="group-item" onclick="toAddDelivery(this,'1','',${postFee})">
		<div class="group-rowspan">
			<div class="group-colspan"><span style="display:none" data-delivery-name>快递</span><h3>
			<c:if test="${postFee != 0}">快递：￥<fmt:formatNumber pattern="0.00" value="${postFee/100 }"/></c:if>
			<c:if test="${postFee == 0}">快递：包邮</c:if>
			</h3></div>
			<div class="group-colspan"><i class="iconfont <c:if test='${deliveryWay==1 }'>icon-roundcheckfill</c:if> clr-danger" data-iconfont style="font-size:24px;"></i></div>
		</div>
	</div>
</div>
</c:if>
<c:if test="${shopVo.ztType==4 }">
<div class="group group-justify" data-delivery-way='3'>
	<div class="group-item">
		<div class="group-rowspan">
			<div class="group-colspan"><h3><em data-delivery-name>门店自取</em> <span class="clr-light">（请选择方便您提取的地址）</span></h3></div>
		</div>
	</div>
	<c:forEach items="${storeList }" var="pickUpStore">
	<div class="group-item" onclick="toAddDelivery(this,'3','${pickUpStore.id}')">
		<div class="group-rowspan">
			<div class="group-colspan">
				<p>地址:${pickUpStore.fullAddress }</p>
				<p>电话:${pickUpStore.phone }</p>
			</div>
			<div class="group-colspan"><i class="iconfont <c:if test='${pickUpId==pickUpStore.id }'>icon-roundcheckfill</c:if> clr-danger" data-iconfont style="font-size:24px;"></i></div>
		</div>
	</div>
	</c:forEach>
</div>
</c:if>
<c:if test="${shopVo.psType==2 }">
<div class="group group-justify" data-delivery-way='2'>
	<div class="group-item">
		<div class="group-rowspan">
			<div class="group-colspan"><h3 data-delivery-name>店铺配送</h3></div>
		</div>
	</div>
	<div class="group-item" onclick="toAddDelivery(this,'2')">
	<c:forEach items="${shopDistributionList }" var="shopDistribution">
		<div class="group-rowspan">
			<div class="group-colspan">
				<p>配送范围：${shopDistribution.distributionRange }</p>
			</div>
			<div class="group-colspan"><i class="iconfont <c:if test='${deliveryWay==2 }'>icon-roundcheckfill</c:if> clr-danger" data-iconfont style="font-size:24px;"></i></div>
		</div>
	</c:forEach>
	</div>
</div>
</c:if>