<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-receiverPhone-web-app-capable" />
<meta content="black" name="apple-receiverPhone-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>预约信息填写</title>
<%@include file="../../common/base.jsp"%>
<%@include file="../../common/top.jsp"%>
<script src="http://code.jquery.com/jquery-migrate-1.1.1.js"></script> 
<link rel="stylesheet" href="${resourcePath }operation/appoint/css/main.css">
<script type="text/javascript">
$(function(){
	$.metadata.setType('attr','validate');
})
</script>
</head>
<body>
<div id="box">	
	<form action="/wap/${sessionShopNo }/any/appoint/good/${id}.htm" method="post" id="jform" >
		<input type="hidden" name="json" value='${json }'/>
		<input type="hidden" name="validateToken" value="${validateToken }"/>
		<input type="hidden" name="openId" value="${openId }"/>
		<div class="list-row list-row-width list-address">
			<div class="list-col">
				<div class="list-inline">${dynamicFormList[0].lable }</div>
				<div class="list-inline box-flex"><input type="text" value="${user.contacts }" class="form-control" placeholder="${dynamicFormList[0].placeHolder }" maxlength="20"
				id="receiverName" name="receiverName" data-rule-required="true" data-msg-required="联系人不能为空"  data-form-control></div>
			</div>
			<div class="list-col">
				<div class="list-inline">${dynamicFormList[1].lable }</div>
				<div class="list-inline box-flex"><input type="text" value="${user.cell }" class="form-control" placeholder="${dynamicFormList[1].placeHolder }" 
				id="receiverPhone" name="receiverPhone" data-rule-required="true" data-msg-required="手机号码不能为空" data-rule-mobile="true" data-msg-mobile="手机号码错误" data-form-control></div>
			</div>
		</div>
		<div class="list-row list-row-width">
		<c:forEach items="${dynamicFormList }" var="dynamicForm" varStatus="status">
		<c:if test="${dynamicForm.innerSort==3 }">
			<div class="list-col">
				<div class="list-inline">${dynamicForm.lable }</div>
				<input type="hidden" name="appointmentDetailList[0].attrName" value="${dynamicForm.lable }"/>
				<div class="list-inline box-flex"><span class="form-control-select"><input type="date"  class="form-control" data-form-control value="${dateString }" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空" placeholder="${dynamicForm.placeHolder }" name="appointmentDetailList[0].attrValue"></span></div>
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
							<div class="list-inline box-flex"><textarea class="form-textarea" maxlength="256" placeholder="${dynamicForm.placeHolder }" data-rule-required="${dynamicForm.isRequired }" data-msg-required="${dynamicForm.lable }不能为空" cols="30" rows="5" name="appointmentDetailList[${status.index }].attrValue"></textarea></div>
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
		</div>

		<div class="group group-justify">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">预约清单</li>
					<li class="group-colspan"><i class="iconfont icon-unfold"></i></li>
				</ul>
			</div>
			<c:forEach items="${selectedList }" var="goodSku">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan txt-rowspan1">${goodSku.name }</li>
					<li class="group-colspan"><span><var class="clr-light">${goodSku.quantity }*</var><fmt:formatNumber pattern="0.00" value="${goodSku.salesPrice/100 }"/>元</span></li>
				</ul>
			</div>
			</c:forEach>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan clr-light">数量：<span class="clr-danger">${num }</span>个</li>
					<li class="group-colspan clr-light">总计：<span class="clr-danger">￥<fmt:formatNumber pattern="0.00" value="${total/100 }"/></span></li>
				</ul>
			</div>
		</div>
		
		<div class="group group-justify">
			<div class="group-item">
				<div class="group-rowspan">
					<div class="group-colspan"></div>
					<div class="group-colspan">
						<button type="button" onclick="tj()" class="btn btn-danger">确认提交</button>
					</div>
				</div>
			</div>
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
	<%@include file="../../common/nav.jsp" %>
</div>

<script type="text/javascript">
	function tj(){
		var val=$("#jform").validate({
		});
		if(val.form()){
			$('button').attr('disabled',true);
			console.log($("#jform").serialize())
			$("#jform").submit();
		}
	}
</script>
</body>
</html>
