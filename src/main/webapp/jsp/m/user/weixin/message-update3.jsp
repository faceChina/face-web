<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>消息管理-自动回复消息</title>	
<!-- top -->
<%@ include file="../../common/base.jsp" %>
<%@ include file="../../common/validate.jsp" %>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("message");
	echoSelect('${messageSetting.eventType}', '${messageSetting.matchingType}');
	
	$("#j-select2").change(function() {
		var eventType = ${messageSetting.eventType};
		var recoveryMode = $(this).val();
		var param = "&eventType="+eventType+"&recoveryMode="+recoveryMode;
		if(3==eventType) {
			var keyWord = $("#keyWord").val();
			var matchingType = $("#matchingType").val();
			var name = $("#messageName").val();
			if(''!=keyWord) {
				param += "&keyWord="+keyWord+"&matchingType="+matchingType;
			}else {
				param += "&matchingType="+matchingType;
			}
			param += "&name="+name;
		}
		window.location.href='${ctx}/u/weixin/chooseReplyMode.htm?'+param;
	});
	
	if('${messageSetting.eventType}'=='3'){
		$("#keyWord").val("${messageSetting.keyWord }");
		$("#matchingType").val("${messageSetting.matchingType }");
	}
	
	$('button').click(function(event) {
		var event = ${messageSetting.eventType};
		if(event=='3') {
			$("#form").validate({
				rules : {
					name : {
						required: true,
						maxlength:16
					},
					messageContent : {
						required: true,
						maxlength:300
					},
					keyWord : {
						required: true,
						maxlength:32
					}
				},
				messages : {
					name : {
						required: "请输入名称！",
						maxlength:$.format("名称不能超过{0}个字符")
					},
					messageContent : {
						required: "请输入描述！",
						maxlength:$.format("描述不能超过{0}个字符")
					},
					keyWord : {
						required: "请输入关键词！",
						maxlength:$.format("关键词不能超过{0}个字符")
					}
				}
			});
		} else {
			$("#form").validate({
				rules : {
					messageContent : {
						required: true,
						maxlength:300
					}
				},
				messages : {
					messageContent : {
						required: "请输入描述！",
						maxlength:$.format("描述不能超过{0}个字符")
					}
				}
			});
		}
		
	});
});
</script>


</head>
<body>
	<!-- header -->
	<%@include file="../../common/header.jsp" %>
	<!-- header end -->
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 navleft-sidebar">
				<!--nav-left -->
				<%@include file="../../common/left.jsp" %>
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
							<form id="form" action="${ctx}/u/weixin/editMessage${ext}" method="post">
								<input type="hidden" name="id" value="${messageSetting.id }">
								<input type="hidden" name="eventType" value="${messageSetting.eventType}">
								<input type="hidden" name="messageBodyId" value="${messageSetting.messageBodyId }">
								<c:if test="${messageSetting.eventType == 3}">
									<input type="hidden" name="recoveryMode" value='${messageSetting.recoveryMode }'>
								</c:if>
								<table class="table table-thleft table-noborder">
									<tbody>
										<c:if test="${messageSetting.eventType == 3 }">
											<tr data-name>
												<th width="10%">名称</th>
												<td><input type="text" id="messageName"  name="name" value="${messageSetting.name }" class="form-control"></td>
											</tr>
										</c:if>
										<tr data-module>
											<th>回复模式</th>
											<td><select <c:if test="${messageSetting.eventType == 3}">disabled="disabled"</c:if> name="recoveryMode" class="form-control" id="j-select2">
													<option value="1" selected="selected">文字模式</option>
													<option value="2">单图文模式</option>
													<option value="3">多图文模式</option>
												</select>
											</td>
										</tr>
										<c:if test="${messageSetting.eventType == 3 }">
											<tr data-key>
												<th width="10%">关键词</th>
												<td><input id="keyWord" name="keyWord" type="text" class="form-control"></td>
											</tr>
											<tr data-type id="j-keyword" title="关键词">
												<th width="10%">匹配类型</th>
												<td><select id="matchingType" name="matchingType" class="form-control">
														<option value="1">模糊匹配</option>
														<option value="2">精确匹配</option>
												</select></td>
											</tr>
										</c:if>
										<tr>
											<th width="10%">描述</th>
											<td><textarea name="messageContent" class="form-control" rows="3">${mContents[0].content }</textarea></td>
										</tr>
										<tr>
											<td colspan="2" style="text-align:center;">
												<button id="submit" type="submit" class="btn btn-default">保存</button>
											</td>
										</tr>
									</tbody>
								</table>

							</form>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
	<%@include file="../../common/footer.jsp" %>
	<script type="text/javascript" charset="utf-8" src="${resourcePath}js/wechat.js"></script>
	<!-- footer end -->
	</body>
</html>