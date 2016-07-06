<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">

$(function(){
	//运费模板 信息隐藏
	$(".j-tempaddress").on('click',function(){
		var index = $(this).data("id");
		if(index == 1){
			$("#tempaddress").hide();
			$('#postFeeYuan').removeAttr('disabled');
		}else{
			$("#tempaddress").show();
			$('#postFeeYuan').val("");
			if($('#postFeeYuan').siblings('label').length){
				$('#postFeeYuan').siblings('label').remove().end().removeClass('error');
			}
			$('#postFeeYuan').attr('disabled',true);
		}
	});
});
</script>
<div class="other-set"><b>物流信息</b></div>
<table class="table-thleft  table-lg">
	<c:choose>
		<c:when test="${not empty good.logisticsMode}">
			<tr>
				<td width="94">
					<input data-id="1" class="j-tempaddress" name="logisticsMode" type="radio" value="2" id="express1"
					<c:if test="${good.logisticsMode!=1}">checked</c:if>
					><label for="express1">统一运费:</label>
				</td>
				<td width="200">
					<input type="text" id="postFeeYuan" name="postFeeYuan" class="form-control" 
					validate="{required:true,moneyBase:true,messages:{required:'运费不能为空',moneyBase:'必须为正整数且保留两位小数'}}"
					<c:if test="${good.logisticsMode==2}">value="<fmt:formatNumber pattern="0.00" value="${good.postFee/100}" />"</c:if>
					<c:if test="${good.logisticsMode==1}">disabled="disabled"</c:if>>
				</td>
				<td>元  运费模板（"0"元为卖家包邮）</td>
			</tr>
			<tr>
				<td>
					<input class="j-tempaddress" data-id="2" type="radio" name="logisticsMode" value="1" id="express2" 
					<c:if test="${good.logisticsMode==1}">checked</c:if>><label for="express2">运费模板:</label>
				</td>
				<td colspan="2">
					<a class="btn btn-default" target="_black" href="${ctx}/u/shop/logistics/dispatchIndex${ext}">设置模板</a>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td width="94"><input data-id="1" class="j-tempaddress" name="logisticsMode" type="radio" value="2" id="express1"  checked><label for="express1">统一运费:</label></td>
				<td width="200">
					<input type="text" id="postFeeYuan" name="postFeeYuan" class="form-control" 
					validate="{required:true,moneyBase:true,messages:{required:'运费不能为空',moneyBase:'必须为正整数且保留两位小数'}}">
				</td>
				<td>元  运费模板（"0"元为卖家包邮）</td>
			</tr>
			<tr>
				<td><input class="j-tempaddress" data-id="2" type="radio" name="logisticsMode" value="1" id="express2"><label for="express2">运费模板:</label>
				</td>
				<td colspan="2">
					<a class="btn btn-default" target="_black" href="${ctx}/u/shop/logistics/dispatchIndex${ext}">设置模板</a>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	<tr>
		<td colspan="3">
			<div id="tempaddress" class="pronew-tempaddress">
				<c:choose>
					<c:when test="${not empty deliveryTemplateVo.itemVoList}">
							<p>默认运费：${deliveryTemplateVo.itemVoList[0].startStandard}件内
							<fmt:formatNumber pattern="0.00" value="${deliveryTemplateVo.itemVoList[0].startPostage/100}" />元，
							每增加${deliveryTemplateVo.itemVoList[0].addStandard}件，
							加<fmt:formatNumber pattern="0.00" value="${deliveryTemplateVo.itemVoList[0].addPostage/100}" />元</p>
							<c:if test="${not empty deliveryTemplateVo.itemVoList[1]}">
								<p>指定区域运费</p>
								<p>${deliveryTemplateVo.itemVoList[1].destination}
									${deliveryTemplateVo.itemVoList[1].startStandard}件内
									<fmt:formatNumber pattern="0.00" value="${deliveryTemplateVo.itemVoList[1].startPostage/100}" />元，
									每增加${deliveryTemplateVo.itemVoList[1].addStandard}件，
									加<fmt:formatNumber pattern="0.00" value="${deliveryTemplateVo.itemVoList[1].addPostage/100}" />元
								</p>
							</c:if>
					</c:when>
					<c:otherwise>
						您还没有配置运费模版..模版设置后，运费生效。
					</c:otherwise>
				</c:choose>
			</div>
		</td>
	</tr>
	<tr>
		<td>门店自取：</td>
		<td colspan="2">
			<a class="btn btn-default" target="_black" href="${ctx}/u/shop/logistics/ztlist${ext}">新增自提点</a>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="color:#999;">${pickUpStoreStr}</td>
	</tr>
	<tr>
		<td>店铺配送：</td>
		<td colspan="2">
			<a class="btn btn-default" target="_black" href="${ctx}/u/shop/logistics/pslist${ext}">新增配送范围</a>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="color:#999;">
			${shopDistributionStr}
		</td>
	</tr>
</table>