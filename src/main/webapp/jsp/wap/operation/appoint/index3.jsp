<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-receiverPhone-web-app-capable" />
<meta content="black" name="apple-receiverPhone-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${appointment.navigationTitle }</title>
<%@include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<%-- <%@include file="../../common/validate.jsp"%> --%>
<script src="http://code.jquery.com/jquery-migrate-1.1.1.js"></script> 
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">

</head>
<style>
body{
	position:relative;
}
</style>
<body>
<div id="box">
	<div class="reserve-banner"><a href="#">
	<img <c:if test='${not empty appointment.picturePath&&!fn:contains(appointment.picturePath, "resource/m/") }'>src="${picUrl }${appointment.picturePath }"</c:if> alt="">
	<img <c:if test='${not empty appointment.picturePath&&fn:contains(appointment.picturePath, "resource/m/") }'>src="${appointment.picturePath }"</c:if> alt=""></a></div>
	<div class="reserve-title"><a href="#">${appointment.name }</a></div>
	
	<!-- 无价格的时候这里不显示  -->
	<c:if test="${appointment.price>0 }">
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline box-flex">预约价<span class="clr-danger" style="padding-left:10px;">￥<fmt:formatNumber pattern="0.00" value="${appointment.price/100 }"/>元</span></div>
			<div class="list-inline list-other box-flex">
				<span class="other-main color-red">数量<var style="padding-left:10px;">${appointment.inventory }</var></span>
			</div>
		</div>
	</div>
	</c:if>
	<!-- 无价格的时候这里不显示  end-->
	
	<div class="list-row">
		<div class="list-col">
			<div class="list-inline">介绍</div>
		</div>
		<div class="information">
			<p>${appointment.content }</p>
		</div>
	</div>
	<c:if test="${appointment.isShowSellerMeg == 1 }">
		<div class="group group-others width20">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan"><i class="iconfont icon-dizhi clr-light fnt-24"></i></li>
					<li class="group-colspan">${appointment.address }</li>
					<li class="group-colspan"></li>
				</ul>
			</div>
			<a href="tel:${appointment.cell }"><div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan"><i class="iconfont icon-dianhua clr-light fnt-24"></i></li>
					<li class="group-colspan">${appointment.cell }</li>
					<li class="group-colspan"><i class="iconfont icon-right"></i></li>
				</ul>
			</div></a>
		</div>
	</c:if>
	<form action="/wap/${sessionShopNo }/any/appoint/serve/${id}.htm" method="post" id="jform">
		<input type="hidden" name="openId" value="${openId }"/>
		<div class="list-row list-row-width">
			<div class="list-col">
				请认真填写表单信息
			</div>
			<div class="list-col">
				<div class="list-inline">${dynamicFormList[0].lable }</div>
				<div class="list-inline box-flex"><input type="text" value="${user.contacts }" class="form-control" placeholder="${dynamicFormList[0].placeHolder }" maxlength="20"
				id="receiverName" name="receiverName" data-rule-required="true" data-msg-required="${dynamicFormList[0].lable }不能为空" data-form-control></div>
			</div>
			<div class="list-col">
				<div class="list-inline">${dynamicFormList[1].lable }</div>
				<div class="list-inline box-flex"><input type="text" value="${user.cell }" class="form-control" placeholder="${dynamicFormList[1].placeHolder }" 
				id="receiverPhone" name="receiverPhone" data-rule-required="true" data-msg-required="${dynamicFormList[1].lable }不能为空" data-rule-mobile="true" data-msg-mobile="手机号码错误" data-form-control></div>
			</div>
			<c:forEach items="${dynamicFormList }" var="dynamicForm" varStatus="status">
			<c:if test="${dynamicForm.innerSort==3 }">
				<div class="list-col">
					<div class="list-inline">${dynamicForm.lable }</div>
					<input type="hidden" name="appointmentDetailList[0].attrName" value="${dynamicForm.lable }"/>
					<div class="list-inline box-flex"><span class="form-control-select"><input type="date" class="form-control" data-form-control value="${dateString}" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空" placeholder="${dynamicForm.placeHolder }" name="appointmentDetailList[0].attrValue"></span></div>
					<div class="list-inline">
						<span class="form-select-san" style="min-width:100px;">
							<select name="appointmentDetailList[0].attrValue">
								<option value="00:00-01:00">00:00-01:00</option>
								<option value="01:00-02:00">01:00-02:00</option>
								<option value="02:00-03:00">02:00-03:00</option>
								<option value="03:00-04:00">03:00-04:00</option>
								<option value="04:00-05:00">04:00-05:00</option>
								<option value="05:00-06:00">05:00-06:00</option>
								<option value="06:00-07:00">06:00-07:00</option>
								<option value="07:00-08:00">07:00-08:00</option>
								<option value="08:00-09:00">08:00-09:00</option>
								<option value="09:00-10:00">09:00-10:00</option>
								<option value="10:00-11:00">10:00-11:00</option>
								<option value="11:00-12:00">11:00-12:00</option>
								<option value="12:00-13:00">12:00-13:00</option>
								<option value="13:00-14:00">13:00-14:00</option>
								<option value="14:00-15:00">14:00-15:00</option>
								<option value="15:00-16:00">15:00-16:00</option>
								<option value="16:00-17:00">16:00-17:00</option>
								<option value="17:00-18:00">17:00-18:00</option>
								<option value="18:00-19:00">18:00-19:00</option>
								<option value="19:00-20:00">19:00-20:00</option>
								<option value="20:00-21:00">20:00-21:00</option>
								<option value="21:00-22:00">21:00-22:00</option>
								<option value="22:00-23:00">22:00-23:00</option>
								<option value="23:00-24:00">23:00-24:00</option>
							</select>
						</span>
					</div>
				</div>	
			</c:if>
			<c:if test="${dynamicForm.innerSort==4 }">
				<div class="list-col">
					<div class="list-inline" style="-webkit-box-align: none;">${dynamicForm.lable }</div>
					<div class="list-inline box-flex"><textarea class="form-textarea" name="buyerMessage" maxlength="256" id="" cols="30" rows="5" placeholder="${dynamicForm.placeHolder }" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空"></textarea></div>
				</div>
			</c:if>
			<c:if test="${status.index>1&&dynamicForm.innerSort==0 }">
				<c:choose>
					<c:when test="${dynamicForm.type=='INPUT' }">
						<div class="list-col">
							<div class="list-inline">${dynamicForm.lable }</div>
							<input type="hidden" name="appointmentDetailList[${status.index }].attrName" value="${dynamicForm.lable }"/>
							<div class="list-inline box-flex"><input type="text" class="form-control" placeholder="${dynamicForm.placeHolder }" maxlength="64" 
							name="appointmentDetailList[${status.index }].attrValue" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空"></div>
						</div>
					</c:when>
					<c:when test="${dynamicForm.type=='TEXTAREA' }">
						<div class="list-col">
							<div class="list-inline" style="-webkit-box-align: none;">${dynamicForm.lable }</div>
							<input type="hidden" name="appointmentDetailList[${status.index }].attrName" value="${dynamicForm.lable }"/>
							<div class="list-inline box-flex"><textarea class="form-control" maxlength="256" placeholder="${dynamicForm.placeHolder }" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空" cols="30" rows="5" name="appointmentDetailList[${status.index }].attrValue"></textarea></div>
						</div>
					</c:when>
					<c:when test="${dynamicForm.type=='DATETIME' }">
						<div class="list-col">
							<div class="list-inline">${dynamicForm.lable }</div>
							<input type="hidden" name="appointmentDetailList[${status.index }].attrName" value="${dynamicForm.lable }"/>
							<div class="list-inline box-flex"><span class="form-control-select"><input type="date" class="form-control" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空" placeholder="${dynamicForm.placeHolder }" name="appointmentDetailList[${status.index }].attrValue"></span></div>
							<div class="list-inline">
								<span class="form-select-san" style="min-width:100px;">
									<select name="appointmentDetailList[${status.index }].attrValue">
										<option value="00:00-01:00">00:00-01:00</option>
										<option value="01:00-02:00">01:00-02:00</option>
										<option value="02:00-03:00">02:00-03:00</option>
										<option value="03:00-04:00">03:00-04:00</option>
										<option value="04:00-05:00">04:00-05:00</option>
										<option value="05:00-06:00">05:00-06:00</option>
										<option value="06:00-07:00">06:00-07:00</option>
										<option value="07:00-08:00">07:00-08:00</option>
										<option value="08:00-09:00">08:00-09:00</option>
										<option value="09:00-10:00">09:00-10:00</option>
										<option value="10:00-11:00">10:00-11:00</option>
										<option value="11:00-12:00">11:00-12:00</option>
										<option value="12:00-13:00">12:00-13:00</option>
										<option value="13:00-14:00">13:00-14:00</option>
										<option value="14:00-15:00">14:00-15:00</option>
										<option value="15:00-16:00">15:00-16:00</option>
										<option value="16:00-17:00">16:00-17:00</option>
										<option value="17:00-18:00">17:00-18:00</option>
										<option value="18:00-19:00">18:00-19:00</option>
										<option value="19:00-20:00">19:00-20:00</option>
										<option value="20:00-21:00">20:00-21:00</option>
										<option value="21:00-22:00">21:00-22:00</option>
										<option value="22:00-23:00">22:00-23:00</option>
										<option value="23:00-24:00">23:00-24:00</option>
									</select>
								</span>
							</div>
						</div>
					</c:when>
					<c:when test="${dynamicForm.type=='SELECT' }">
						<div class="list-col">
							<div class="list-inline">${dynamicForm.lable }</div>
							<input type="hidden" name="appointmentDetailList[${status.index }].attrName" value="${dynamicForm.lable }"/>
							<div class="list-inline box-flex">
								<span class="form-select-san">
									<select name="appointmentDetailList[${status.index }].attrValue" >
										<c:forEach items="${dynamicForm.selectOption }" var="option">
										<option value="${option }">${option }</option>
										</c:forEach>
									</select>
								</span>
							</div>
						</div>
					</c:when>
				</c:choose>
			</c:if>
			</c:forEach>
		</div>
		<div class="button">
			<button type="button" onclick="tj()" class="btn btn-danger btn-block">${appointment.buttonTitle }</button>
		</div>
	</form>
	<style>
	.goback{
	  	display: -moz-box;
	  	display: -webkit-box;
		max-width:640px;
		min-width:320px;
		width:100%;
		margin:0 auto;
		padding:0.5em;
	}
	.goback a{
		display: block;
	  	-moz-box-flex: 1.0;
	    -webkit-box-flex: 1.0;
	    border-right:1px solid #c4c4c4;
	    color:#c4c4c4;
	    text-align: center;
	}
	.goback a:last-child{
		border-right:none;
	}
	</style>
	<div class="goback">
		<a href="/wap/${shop.no }/any/index.htm">店铺首页</a>
		<a href="/wap/${shop.no }/buy/personal/index.htm">个人中心</a>
	</div>
	<%@include file="../../common/foot.jsp" %>
</div>

<script type="text/javascript">
$(function(){
	if('${appointment.status}'!='1'){
		$('button').attr('disabled',true);
		$.dialog.alert('活动已关闭');
	}
});
function tj(){
	if($("#jform").validate({}).form()){
		$('button').attr('disabled',true);
		$("#jform").submit();
	}
}
</script>
</body>
</html>
