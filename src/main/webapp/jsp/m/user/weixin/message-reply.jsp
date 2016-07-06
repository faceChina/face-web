<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>消息管理-自动回复消息库</title>	
<!-- top -->
<%@ include file="../../common/base.jsp" %>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("message");
	
	$("#keyWord").val("${keyWord}");
});
	/*删除*/
	function del(el,id) {
		art.dialog.confirm('确认删除？', function() {
			window.location.href = "${ctx}/u/weixin/remove${ext}?id="+id;
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}
	
	/*编辑*/
	function update(id) {
		window.location.href = "${ctx}/u/weixin/editMessage${ext}?id="+id;
	}
	
	/*新增*/
	function addMessage() {
		location.href='${ctx}/u/weixin/messagePage${ext}?eventType=3&recoveryMode=3';
	}
</script>
</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp"%>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 navleft-sidebar">
				<!--nav-left -->
				<%@ include file="../../common/left.jsp" %>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
				
						<c:set var="crumbs" value="message"/>
						<%@include file="../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">消息管理</a></li>
								</ul>
							</div>
							<div class="content">
								<form id="formPage" action="${ctx}/u/weixin/messageReply${ext}" method="post">
								<ul class="pager">
									<li class="previous">
										<button type="button" class="btn btn-default" onclick="addMessage();">添加</button>
									</li>
									<li class="next">
										<div   class="navbar-form pull-right">
											<label class="control-label " for="inputSuccess">关键词：</label>
											<div class="form-group">
												<input id="keyWord" name="keyWord" value="${keyWord }" type="text" class="form-control" placeholder="Search">
											</div>
											<button type="submit" class="btn btn-default">搜索</button>
										</div>
									</li>
								</ul>

								<table class="table table-bordered" id="template">
									<thead>
										<tr>
											<th>名称</th>
											<th>自动回复事件</th>
											<th>回复模式</th>
											<th>关键词</th>
											<th>匹配类型</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${not empty pagination.datas }">
												<c:forEach items="${pagination.datas }" var="data">
													<tr>
														<td>${data.name}</td>
														<td><c:choose>
																<c:when test="${data.eventType==1}">
											                                                    关注时回复
										                        </c:when>
																<c:when test="${data.eventType==2}">
											                                                    消息时回复
										                        </c:when>
										                        <c:when test="${data.eventType==3}">
											                                                    关键字回复
										                        </c:when>
															</c:choose></td>
														<td><c:choose>
																<c:when test="${data.recoveryMode==1}">
											                                                    文字
										                        </c:when>
																<c:when test="${data.recoveryMode==2}">
											                                                    单图文
										                        </c:when>
																<c:otherwise>
											                                                     多图文
										                        </c:otherwise>
															</c:choose></td>
														<td>${data.keyWord }</td>
														<td><c:choose>
																<c:when test="${data.matchingType==1}">
											                                                    模糊匹配
										                        </c:when>
																<c:when test="${data.matchingType==2}">
											                                                    精确匹配
										                        </c:when>
															</c:choose></td>
														<td>
															<button type="button" class="btn btn-default" onclick="update('${data.id}')">修改</button>
															<button type="button" class="btn btn-default btn-del" onclick="del(this,'${data.id}')">删除</button>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="6" class="text-center">暂未设置回复消息</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<%@include file="../../common/page.jsp"%>
								</form>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@ include file="../../common/footer.jsp" %>
	<!-- footer end -->
	</body>
</html>