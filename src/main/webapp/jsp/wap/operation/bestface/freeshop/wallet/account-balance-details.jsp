<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>账户余额</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/wallet/css/main.css">
</head>
<body>

<div id="box">
	<div class="group group-justify">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">流水号：</li>
					<li class="group-colspan clr-light">${detail.serialNumber }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">动作：</li>
					<li class="group-colspan clr-light">${detail.action }</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">支出：</li>
					<li <c:choose>
							<c:when test="${data.isFrom }">class="clr-danger group-colspan"</c:when>
							<c:otherwise>class="clr-blue group-colspan"</c:otherwise>
						</c:choose>
				    ><b>${detail.operationPrice } 元</b></li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">时间：</li>
					<li class="group-colspan clr-light"><fmt:formatDate value="${detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">余额：</li>
					<li class="group-colspan clr-light">${detail.balanceString }元</li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">收支对象：</li>
					<li class="group-colspan clr-light">${detail.target }</li>
				</ul>
			</div>
		</div>
	
	
	<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/freeNav.jsp" %>
	
	<script type="text/javascript">
		//卖家版订单状态 
		navbar("purse");
		var session_Account = sessionStorage.getItem('session_Account');
		if(session_Account == 'account1'){	
			$('#js-withdrawal').show();	//提现
		}else if(session_Account == 'account2'){	
			$('#js-onlinePay').show();	//在线支付
		}else if(session_Account == 'account3'){	
			$('#js-commissionincome').show();		//佣金收入
		}else if(session_Account == 'account4'){	
			$('#js-storeinto').show();	//店铺转入
		};
	</script>
</div>
</body>
</html>