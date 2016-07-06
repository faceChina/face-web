<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="../common/base.jsp" %>
</head>
<body>
    <form id="form-test" method="post">
        <input name="key" id="key" type="hidden" value='${key }' />
    	<p>hello ${name }</p>
		<p>
		<div>
			<input type="text" name="value">
			<button type="button" onclick="normalCase()">正常更新</button>
		</div>
		<p>
		<div>
			<input type="text" name="value1">
			<button type="button" onclick="errSynchCase()">异常同步更新</button>
		</div>
		<p>
		<div>
			<input type="text" name="value2">
			<button type="button" onclick="errAsynchCase()">异常异步更新</button>
		</div>
    </form>
</body>
<script type="text/javascript">
function normalCase(){
	$('#form-test').attr('action', '${ctx}/m/test/redis/set${ext}').submit();
}

function errSynchCase(){
	$('#form-test').attr('action', '${ctx}/m/test/redis/setErrSynch${ext}').submit();
}

function errAsynchCase(){
	$.post('${ctx}/m/test/redis/setErrAsych${ext}', {"key" : $('#key').val()}, function(jsonData){
	});
}
</script>
</html>