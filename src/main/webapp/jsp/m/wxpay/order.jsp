<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/base.jsp" %>
<title>Insert title here</title>
</head>
<body>
<form action="${ctx}/wxpay/payPage${ext}" method="post">
订单号：<input type="text" name="orderNo"/><br/>
商品名称：<input type="text" name="goodname"/><br/>
<label>商品价格默认为1分钱</label><br/>
<button type="submit">提交</button>
</form>
</body>
</html>