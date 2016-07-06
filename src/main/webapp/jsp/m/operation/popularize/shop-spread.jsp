<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>推广管理</title>	
<!-- top -->
<!--top end -->
</head>
<body>
<!-- header -->
<%@ include file="../../common/base.jsp"%>
<%@ include file="../../common/header.jsp" %>
<!-- header end -->
<script type="text/javascript">
$(function(){
	tab("shop-spread");

});

	</script>
	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<%@include file="../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#dispatching-set" data-toggle="tab">推广管理</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="alert alert-warning" role="alert">
									<p>温馨提示：发动客户推广您店铺中的商品，当店铺通过该客户的推广产生成交时，推广客户将会得到佣金。</p>
								 </div>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>类型</th>
											<th width="40%">佣金</th>
											<th width="30%">状态</th>
									
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>店铺推广</td>
											<td id="tuiguang" >
												<c:if test="${empty shopPopularizeSetting}">
													商品实付金额*  1%
												</c:if>
												<c:if test="${!empty shopPopularizeSetting }">
													商品实付金额*  ${shopPopularizeSetting.commissionRate}%
												</c:if>
												<a href="javacript:"  onclick="chargesSets(this)"  class="color-danger" style="margin-left:20px;">修改</a>
											</td>
											<td>
                                                <div class="OC_box">
                                                    <span>关闭</span>
                                                    <c:choose>
                                                    	<c:when test="${shopPopularizeSetting.status==1 }">
		                                                    <div class="OC_box_bar" id="switch_div" data-onoff="on">
		                                                        <h1 class="ico_btn" id="switch_h1" data-onoff-on=""></h1>
		                                                        <h3 class="ico_btn hide" id="switch_h3" data-onoff-off=""></h3>
		                                                        <h2 class="ico_btn" id="switch_h2"  data-onoff-handle="" style="left: 26px;"></h2>
		                                                    </div>
                                                    	</c:when>
	                                                    <c:otherwise>
	                                                    	<div class="OC_box_bar" id="switch_div" data-onoff="off">
			                                                        <h1 class="ico_btn hide" id="switch_h1" data-onoff-on=""></h1>
			                                                        <h3 class="ico_btn" id="switch_h3" data-onoff-off=""></h3>
			                                                        <h2 class="ico_btn" id="switch_h2"  data-onoff-handle="" style="left: 0px;"></h2>
			                                                   </div>
	                                                    </c:otherwise>
                                                    </c:choose>
                                                    <span>开启</span>
                                                </div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
    <!-- 编辑名称 -->
    <div id="chargesSets" style="display:none;">
        <form class="form-horizontal" id="form-horizontal" action="commissionRate/save.htm">
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-4 control-label">商品实付金额*</label>
                <div class="col-sm-6">
                    <input type="email" class="form-control" id="inputEmail3" name="commissionRate" placeholder="">
                </div>
                <div class="col-sm-2 control-label" style="text-align: left;">%</div>
            </div>
        </form>
    </div>
	<!-- body end -->
	<!-- footer -->
	<%@include file="../../common/footer.jsp"%>
	<!-- footer end -->
<script type="text/javascript" src="${resourcePath}js/onoff.js"></script>

<script>
    function chargesSets(){
        art.dialog({
            width:"500px",
            title:"佣金设置",
            content:document.getElementById("chargesSets"),
            ok:function(){
            	var boo = $("#form-horizontal").validate({
            		rules:{
            			commissionRate: {
            				required:true,
            				digits:true,
            				range:[1, 99]
            			}
            		},
            		messages:{
            			commissionRate:{
	            			required:"请输入数据",
	            			digits:"请输入1~99正整数",
	            			range:"请输入1~99正整数"
            			}
            		}
            	}).form();
            	if (boo) {
					$.post("commissionRate/save.htm", {'commissionRate': $("#inputEmail3").val()}, function(jsonResult) {
						var result = JSON.parse(jsonResult);
						if (result.success) {
							var data = JSON.parse(result.info);
							$("#tuiguang").empty().html("商品实付金额*  "+data.commissionRate+"%"+"<a href='javascript:'  onclick='chargesSets(this)'  class='color-danger' style='margin-left:20px;''>修改</a>");
						} else {
							art.dialog(result.info);
						}
					});
				} else {
					return false;
				}
            },
            cancel:true
        })
    }

    $(function(){
        $("[data-onoff]").each(function(){
            var self = this,
                    activeEl = $(self).parents('td').prev().find('[data-onoff]');
            $(this).onoff({
                on:function(){
					$.post("status/save.htm", {'status':1}, function(jsonResult) {
						var result = JSON.parse(jsonResult);
						if (!result.success) {
							art.dialog(result.info);
						}
					});
                    activeEl.show();
                    return true;
                },
                off:function(){
					$.post("status/save.htm", {'status':2}, function(jsonResult) {
						var result = JSON.parse(jsonResult);
						if (!result.success) {
							art.dialog(result.info);
						}
					});
                    activeEl.hide();
                    return true;
                }
            })
        });
    })
</script>
	</body>
</html>