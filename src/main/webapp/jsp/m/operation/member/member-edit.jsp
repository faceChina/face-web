<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息修改</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript">
$(function(){
	tab("member-manage");
});
</script>
</head>
<body>	
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="member-manage" />
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">会员管理</a></li>
								</ul>
							</div>
                            <form class="form-horizontal" role="form" id="j-form" method="post">
                            	<input type="hidden" name="id" value="${card.id }" />
                                <div class="content">
                                    <div class="row">
                                        <p class="basics-v2-title"><b>基本资料</b></p>
                                        <table class="table table-bordered">
                                            <tbody>
                                            <tr>
                                                <th scope="row">会员卡号</th>
                                                <td colspan="3">${card.memberCard }</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">姓名</th>
                                                <td style="width: 34%"><input type="text" maxlength="20" name="userName" id="userName" value="${card.userName }" class="form-control text-center" placeholder=""/> </td>
                                                <th>性别</th>
                                                <td>
                                                	<c:choose>
														<c:when test="${car.sex == 1 }">
															男
														</c:when>
														<c:when test="${card.sex == 2 }">
															女
														</c:when>
														<c:otherwise>
															未填写
														</c:otherwise>
													</c:choose>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th scope="row">联系方式</th>
                                                <td>${card.cell }</td>
                                                <th>生日</th>
                                                <td><c:choose>
												<c:when test="${empty card.birthday }">
													未填写
												</c:when>
												<c:otherwise>
													${card.birthday }
												</c:otherwise>
											</c:choose></td>
                                            </tr>
                                            <tr>
                                                <th scope="row">地址</th>
                                                <td colspan="3"><c:choose>
													<c:when test="${empty card.addressDetail }">
														未填写
													</c:when>
													<c:otherwise>
														${card.addressDetail }
													</c:otherwise>
												</c:choose></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="row">
                                        <p class="basics-v2-title"><b>会员权限</b> <em>* 通过消费金额调整（可为负数）可以调整会员等级和会员积分</em></p>
                                        <table class="table table-bordered">
                                            <tbody>
                                            <tr>
                                                <th scope="row">会员等级</th>
                                                <td>${memberName }</td>
                                                <th>余额</th>
                                                <td><fmt:formatNumber value="${card.amount / 100 }" pattern="0.00" /></td>
                                            </tr>
                                            <tr>
                                                <th scope="row">消费总额</th>
                                                <td><fmt:formatNumber value="${card.consumptionAmout / 100 }" pattern="0.00" /></td>
                                                <th>总积分</th>
                                                <td>${card.availableIntegral }</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">状态</th>
                                                <td colspan="3" class="radio-control">
                                                    <small>
                                                        <label for="status1"><input type="radio" name="status" value="1" <c:if test="${card.status == 1 }">checked</c:if> /> 正常</label>
                                                    </small>
                                                    <small>
                                                        <label for="status2"><input type="radio" name="status" value="0" <c:if test="${card.status == 0 }">checked</c:if> /> 冻结</label>
                                                    </small>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th scope="row">备注</th>
                                                <td colspan="3"><input type="text" value="${card.remark }" name="remark" id="remark" placeholder="备注" class="form-control text-center" maxlength="100"/></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="text-center">
                                        <button type="button" class="btn btn-danger" onclick="formSubmit()" style="margin-right:10px;">保存</button>
                                        <button type="button" class="btn btn-default" onclick="location.href='${ctx}/u/member/hymg/list${ext }'">取消</button>
                                    </div>
                                </div>
                            </form>
						</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
    <script>
        var activity = $("#j-form").validate({
            rules:{
            	/* userName:{
                    required:true
                }, */
//                 consumptionAmoutString:{
//                 	required:true,
//                 	moneyBase:true
//                 },
                status:{
                    required:true
                }
            },
//             messages : {
//             	consumptionAmoutString:{
//             		moneyBase:'请输入正确的金额，保留两位小数'
//             	}
//             }
        });
        function formSubmit() {
        	if (activity.form()) {
        		$(".j-loading").show();
        		$.post("${ctx}/u/member/hymg/edit${ext}", $("#j-form").serialize(), function(jsonData){
        			var data = JSON.parse(jsonData);
        			if (data.success) {
        				window.location.href='${ctx}/u/member/hymg/list${ext}';
        			} else {
        				art.dialog.alert(data.info);
        				$(".j-loading").hide();
        			}
        		});
        	}
        }
    </script>
	</body>
</html>