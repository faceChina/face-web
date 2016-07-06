<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/app/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<c:set var="resourceMPath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set  var="resourceImPath" value="${pageContext.request.contextPath}/resource/im/" />

<c:set var="sessionShopNo" value="${sessionScope.shop.no}"/>

<c:choose>
	<c:when test="${not empty sessionScope.SPRING_SECURITY_CONTEXT}">
		<c:set var="sessionAuthentication" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication}"/>
	</c:when>
	<c:otherwise>
		<c:set var="sessionAuthentication" value=""/>
	</c:otherwise>
</c:choose>


<c:set var="userType" value="${sessionScope.userType}"/>

<c:set var="memberFile" value="memberFile" />

<c:set var="memberFileSize" value="640_640" />
<!-- 图片上传地址 -->
<c:set var="uploadUrl" value="${ctx}/any/files/upload${ext}"/>

<!-- 店铺名称显示 P-BFn -->
<c:set var="titleShopName" value="${subbranchName }" />

<script type="text/javascript" src="${resourcePath }operation/bestface/js/loading.js"></script>
<script>
var ROOT_PICURL="${picUrl}";
</script>
<script type="text/javascript">
//验证提示
function alertFun(txt){
	var str = '<div class="m-alert Bounce"><span class="m-alert-info">错误提示！</span></div>';
	$('body').append(str);
	var alertEl = $('.m-alert');
	if(alertEl.is(':hidden')){
		alertEl.find('.m-alert-info').html(txt)
		alertEl.show();
		setTimeout(function(){
			alertEl.remove();
		},1000);
	}

	return false;
}
</script>