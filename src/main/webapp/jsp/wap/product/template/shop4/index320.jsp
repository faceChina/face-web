<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="font-size: 16.875px;">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}</title>
<%@include file="../common/top320.jsp" %>
<link rel="stylesheet" type="text/css"	href="${resourcePath }template/page/shop4/css/main.css">
    <style type="text/css">
        .m-nav-bottom{
            max-width:320px;
        }
    </style>
</head>
<body navbar="true">
<div class="outbox">
    <div class="wxhead"><p class="wxhead-title">商城</p></div>
    <div class="inbox">
        <div id="box">

            <!-- 模块图标 -->

            <div class="list-rowspan">
                <div class="list-colspan">
                    <ul>
                        <c:forEach items="${standardModularList}" var="modular">
							<li><a href="#">
									<span data-background>
										<c:choose>
											<c:when test="${null != modular.imgPath && modular.imgPath != '' && modular.isBasePic == 0}">
												<img src="${picUrl }${modular.imgPath }" />
											</c:when>
											<c:otherwise>
												<img src="${modular.imgPath }"/>
											</c:otherwise>
										</c:choose>
									</span>
									<var data-font>${modular.nameZh}</var>
								</a>
							</li>
						</c:forEach>
                    </ul>
                </div>
            </div>

            <!-- 产品展示  -->

            <div class="list-rowspan">
                <c:forEach items="${shopTypeAndGoodList }" var="shopTypeDto">
					<div class="list-product">
						<div class="list-product-tit">
							<a href="#">
								<span>${shopTypeDto.name }</span>
								<i class="iconfont icon-right right"></i>
							</a>
						</div>
						<div class="list-product-cont">
							<ul>
								<c:forEach items="${shopTypeDto.goodList }" var="good">
									<li>
										<a href="#">
											<span class="pic"><img src="${picUrl }${good.path }" alt=""></span>
											<span class="info">${good.name } </span>
											<c:if test="${good.salesPrice!=0 }"><span class="price clr-danger">￥<fmt:formatNumber pattern="0.00" value="${good.salesPrice/100 }"/></span></c:if>
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:forEach>

            </div>

            <%@include file="../../../common/foot.jsp" %>

        </div>
    </div>
</div>
<script type="text/javascript">
var data = ${json};
</script>
</body>
</html>

