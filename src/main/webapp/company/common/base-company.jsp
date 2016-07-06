<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="title" value="刷脸平台"/>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<c:set var="companyPath" value="${pageContext.request.contextPath}/company/" />
<script type="text/javascript" src="${resourceBasePath}js/jquery-1.8.3.min.js"></script>

<link rel="stylesheet" type="text/css" href="${companyPath}css/company.css">
<link rel="shortcut icon" href="${companyPath }img/login/favicon.ico" type="image/x-icon">