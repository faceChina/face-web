<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--删除了一些冲突的css样式,官网使用 --%>
<c:set var="title" value="微管家"/>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="companyPath" value="${pageContext.request.contextPath}/company/" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<!-- 图片上传地址 -->
<c:set var="uploadUrl" value="${ctx}/any/files/upload${ext}"/>

<!-- 可上传图片格式 -->
<c:set var="imageSuffix" value="jpg|png|jpeg|JPG|PNG|JPEG" />

<!-- 样式表 -->
<link rel="shortcut icon" href="${companyPath }img/login/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="${resourceBasePath}css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${resourceBasePath}css/validate.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}css/main.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}css/main-gw.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}css/core/form.css">
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html75shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<!-- 公共JS -->
<script type="text/javascript" src="${resourceBasePath}js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/artDialog/iframeTools.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/jscolor/jscolor.js"></script>


<script type="text/javascript" src="${resourceBasePath}js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/validate/additional-methods.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/validate/additional-methods-new.js"></script>
<script type="text/javascript" src="${resourceBasePath}js/validate/messages_zh.js"></script>

<script type="text/javascript" src="${resourcePath}plugins/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${resourcePath}plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${resourcePath}plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${resourcePath}plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" src="${resourceBasePath}js/scale.js"></script>
<script type="text/javascript" src="${resourcePath}js/getTableJSON.js"></script>
<script type="text/javascript" src="${resourcePath}js/table.js"></script>
<script>
var ROOT_PICURL="${picUrl}";
$(function(){
	$('form').submit(function(){
		$('input[type!=hidden]').each(function(){
			var val=$(this).val();
			val=val.replace(/[']/g,'&#39;');
			val=val.replace(/["]/g,'&quot;');
			val=val.replace(/</gi,'&lt;');
			$(this).val(val);
		})
		return true;
	});
	 $('.j-tooltip').css({"z-index":500,"poition":"relative"});
	 $('.j-tooltip').on('click',function(){
		 $("[name=userfile]").trigger('click');
	 });

})
</script>
