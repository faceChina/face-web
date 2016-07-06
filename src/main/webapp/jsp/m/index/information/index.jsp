<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${pageContext.request.contextPath}/resource/m/" />
<c:set var="resourceBasePath" value="${pageContext.request.contextPath}/resource/base/" />
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>企业网站-资讯中心</title>
		<script type="text/javascript" src="${resourceBasePath}js/jquery-1.8.3.min.js"></script>
		<link href="${resourcePath}/css/zjlp_index/common.css" rel="stylesheet" type="text/css" />
		<link href="${resourcePath}/css/zjlp_index/index.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="${ctx }/company/img/login/favicon.ico" type="image/x-icon">
		
		<script type="text/javascript">
			function topage(toPageNum){
				$('#curPage').val(toPageNum);
				$('#formPage').submit();
	    	}
		</script>
	</head>
	<body>
		<div class="layout login">
			<div class="tar main-body">
				<a href="/login.htm" target="_blank">登录</a><span>|</span>
				<a href="/company/join.jsp" target="_blank">注册</a>
			</div>
		</div>
		<div class="layout nav nav-border">
			<div class="mod-nav main-body">
				<div class="logo l">
					<a href="/"><img src="${resourcePath}/img/zjlp_index/logo.png" alt=""></a>
				</div>
				<div class="nav-list">
		            <a href="/">首页</a>
		            <a href="/idea.html">刷脸理念</a>
		            <a href="/product.html">刷脸产品</a>
		            <a href="/case.html">成功案例</a>
		            <a href="/information/index.htm" class="active">资讯中心</a>
		        </div>
		        <div class="phone r">
		        	<b class="phone-icon">400-000-3777</b>
		        </div>
			</div>
		</div>
		
		<c:if test="${informationList!=null && fn:length(informationList)!=0 }">
			<div class="news">
				<ul class="news-list">
					<c:forEach items="${informationList}" var="information" varStatus="status">
						<li>
							<a href="${cxt }/information/details/${information.id}${ext}" target="_blank">
								${information.title }
								<span><fmt:formatDate value="${information.publishTime }" pattern="yyyy-MM-dd"/></span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="page">
				<ul class="pagination">
					<c:forEach begin="1" end="${totalPage }" var="i">
						<c:if test="${i == 1 }">
							<c:choose>
								<c:when test="${curPage == 1 }">
									<li class="disabled">
										<a href="javaScript:void(0)">上一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a href="javaScript:topage(${curPage }-1)">上一页</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:if>
					
						<li <c:if test="${curPage == i }">class="active"</c:if>>
							<a href="javaScript:topage(${i })">${i }</a>
						</li>
						
						<c:if test="${i == totalPage }">
							<c:choose>
								<c:when test="${curPage == totalPage }">
									<li class="disabled">
										<a href="javaScript:void(0)">下一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a href="javaScript:topage(${curPage }+1)">下一页</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
			    </ul>
			    <form action="/information/index.htm" id="formPage">
			    	<input type="hidden" id="curPage" name="curPage" value="${curPage }"/>
			    </form>
			</div>
		</c:if>
		<div class="footer">
			<div class="foot">
				<div class="l mt05">
					<img src="${resourcePath}/img/zjlp_index/logo2.png" alt="">
				</div>
				<div class="l about ml15">
					<a href="/about.html">关于我们</a> | <a href="/protocol.html">服务协议</a>
					<p>浙江脸谱科技有限公司 @版权所有 浙ICP备14042086号</p>
					<p>地址：浙江省杭州市市民中心D座11楼</p>
				</div>
				<div class="r complain">
					<img src="${resourcePath}/img/zjlp_index/code1.jpg" class="l">
					<div class="l tel">
						<p>投诉建议</p>
						<h3>400-000-3777</h3>
					</div>
				</div>
			</div>
		</div>
		<script src="${resourcePath}/js/common.js"></script>
		<script type="text/javascript" src="${resourceBasePath}js/bdcnzztj.js"></script>
	</body>
</html>
