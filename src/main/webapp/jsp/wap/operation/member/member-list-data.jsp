<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../common/base.jsp" %>
<c:choose>
	<c:when test="${show }">
		<c:forEach items="${pagination.datas }" var="data">
			<div class="memberlist">
				<a href="${ctx}/wap/${data.shopList[0].no}/buy/member/index${ext}">
					<div class="ml-content">
						<div class="ml-c-header">
							<span class="card-name">${data.shopList[0].name }</span>
							<span class="card-type">${data.levalName }</span>
						</div>
						<div class="ml-c-footer">
							<ul>
								<li class="lifont">
									<label>余额：</label>
									<span class="red">
										<c:choose>
											<c:when test="${data.amount == null ||  data.amount == ''}">
												0
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${data.amount / 100 }" pattern="0.00" />
											</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li class="lifont">
									<label>积分：</label>
									<span>
										<c:choose>
											<c:when test="${data.availableIntegral == null ||  data.availableIntegral == ''}">
												0
											</c:when>
											<c:otherwise>
												${data.availableIntegral }
											</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li class="lifont noline">
									<label>折扣：</label>
									<span>
										<c:choose>
											<c:when test="${data.discount == null ||  data.discount == ''}">
												无折扣
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${data.discount == 100 }">
														无折扣
													</c:when>
													<c:otherwise>
														${data.discount/10 }折
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
							</ul>
						</div>
					</div>
					<div class="footer-bg"></div>
				</a>
			</div>
		</c:forEach>
	</c:when>
	<c:when test="${not empty pagination.datas }">
		<c:forEach items="${pagination.datas }" var="data">
			<c:choose>
				<c:when test="${shop.userId == data.sellerId }">
					<div class="memberlist">
						<a href="${ctx}/wap/${data.shopList[0].no}/buy/member/index${ext}">
							<div class="ml-content">
								<div class="ml-c-header">
									<span class="card-name">${data.shopList[0].name }</span>
									<span class="card-type">${data.levalName }</span>
								</div>
								<div class="ml-c-footer">
									<ul>
										<li class="lifont">
											<label>余额：</label>
											<span class="red">
												<c:choose>
													<c:when test="${data.amount == null ||  data.amount == ''}">
														0
													</c:when>
													<c:otherwise>
														<fmt:formatNumber value="${data.amount / 100 }" pattern="0.00" />
													</c:otherwise>
												</c:choose>
											</span>
										</li>
										<li class="lifont">
											<label>积分：</label>
											<span>
												<c:choose>
													<c:when test="${data.availableIntegral == null ||  data.availableIntegral == ''}">
														0
													</c:when>
													<c:otherwise>
														${data.availableIntegral }
													</c:otherwise>
												</c:choose>
											</span>
										</li>
										<li class="lifont noline">
											<label>折扣：</label>
											<span>
												<c:choose>
													<c:when test="${data.discount == null ||  data.discount == ''}">
														无折扣
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${data.discount == 100 }">
																无折扣
															</c:when>
															<c:otherwise>
																${data.discount/10 }折
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</span>
										</li>
									</ul>
								</div>
							</div>
							<div class="footer-bg"></div>
						</a>
					</div>
				</c:when>
			</c:choose>
		</c:forEach>
			
		<c:forEach items="${pagination.datas }" var="data">
			<c:choose>
				<c:when test="${shop.userId != data.sellerId }">
					<div class="memberlist">
						<div class="ml-content">
							<div class="ml-c-header <c:if test="${!show }">ml-c-header1</c:if>">
								<span class="card-name">${data.shopList[0].name }</span>
								<span class="card-type">${data.levalName }</span>
							</div>
							<div class="ml-c-footer">
								<ul>
									<li class="lifont">
										<label>余额：</label>
										<span class="red">
											<c:choose>
												<c:when test="${data.amount == null ||  data.amount == ''}">
													0
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${data.amount / 100 }" pattern="0.00" />
												</c:otherwise>
											</c:choose>
										</span>
									</li>
									<li class="lifont">
										<label>积分：</label>
										<span>
											<c:choose>
												<c:when test="${data.availableIntegral == null ||  data.availableIntegral == ''}">
													0
												</c:when>
												<c:otherwise>
													${data.availableIntegral }
												</c:otherwise>
											</c:choose>
										</span>
									</li>
									<li class="lifont noline">
										<label>折扣：</label>
										<span>
											<c:choose>
												<c:when test="${data.discount == null ||  data.discount == ''}">
													无折扣
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${data.discount == 100 }">
															无折扣
														</c:when>
														<c:otherwise>
															${data.discount/10 }折
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="footer-bg"></div>
					</div>
				</c:when>
			</c:choose>
		</c:forEach>
	</c:when>
</c:choose>