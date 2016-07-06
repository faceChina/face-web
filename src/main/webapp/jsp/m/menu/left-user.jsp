<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ext" value=".htm" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<ul class="sidebar" id="j-toggleCont">
		<li tabindex="steward"><a href="${ctx }/u/returnIndex${ext}"><i></i>刷脸平台<em></em></a></li>
		<li><a href="javascript:void(0)"> <span class="arrow"></span> <i></i>账户中心</a>
			<ul>
				<li  tabindex="accounts"><a href="${ctx }/u/account/index${ext}"> 我的钱包<em></em></a></li>
				<li  tabindex="bankcard"><a href="${ctx }/u/account/bankcard/list${ext}">我的银行卡<em></em></a></li>
				<li  tabindex="basics"><a href="${ctx }/u/account/user/index${ext}">基本资料 <em></em></a></li>
				<li  tabindex="security"><a href="${ctx }/u/account/security/index${ext}">安全设置<em></em></a></li>
			</ul>
		</li>
		<c:if test="${isSeller }">
		<li><a href="javascript:void(0)"> <span class="arrow"></span> <i></i>会员管理</a>
            <ul>
                <li  tabindex="member-manage"><a href="${ctx }/u/member/hymg/list${ext}"> 会员管理<em></em></a></li>
<%--                 <li  tabindex="recharge-manage"><a href="${ctx }/u/member/czmg/list${ext}">会员充值管理<em></em></a></li> --%>
                <li  tabindex="integral-setting"><a href="${ctx }/u/member/integral/index${ext}">积分设置 <em></em></a></li>
                <li  tabindex="member-set"><a href="${ctx }/u/member/rule/list${ext}">会员等级设置 <em></em></a></li>
                <li  tabindex="member"><a href="${ctx }/u/member/enact/edit${ext}">会员卡设置<em></em></a></li>
            </ul>
        </li>
        </c:if>
	</ul>
<%@include file="left-common-js.jsp" %>