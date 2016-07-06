<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员充值管理</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript" src="${ctx}/resource/m/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	    var index;
		$(function() {
			tab("recharge-manage");
			index = initIndex();
			$.metadata.setType("attr", "validate");
		});
		function initIndex() {
			var index = '${fn:length(active.detailList)}';
			if (null == index || '' == index) {
				index = 0;
			}
			parseInt(index)
			index++;
			return index;
		}
	</script>
</head>
<style>
.col-sm-4 {
	position: relative;
}
.col-sm-time input[type=text] {
	text-indent: 35px;
}
.time-icon {
	position: absolute;
	/*background: url("../img/base-ico.png");*/
	/*background-position: -3px -133px;*/
	background: url("../img/time-icon.png") center center no-repeat;
	display: block;
	width: 34px;
	height: 32px;
	border-right: 1px solid #ccc;
	top: 1px;
}
</style>
<body>
	<%@ include file="../../common/header.jsp"%>
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<%@include file="../../common/left.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="row">
					<c:set var="crumbs" value="recharge-manage" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<div class="row">
					<div class="box">
						<div class="title">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#dispatching-set"
									data-toggle="tab">会员充值管理</a></li>
							</ul>
						</div>
						<div class="content">
							<form method="post" class="form-horizontal" role="form" id="j-form">
								<input type="hidden" name="id" value="${active.id }">
								<div class="form-group" style="margin-top: 30px;">
									<label class="col-sm-2 control-label" style="width: 100px;">名称：</label>
									<div class="col-sm-4">
										<input type="text" validate="{required:true}"  maxlength="22"
										 class="form-control" value="${active.detailList[0].name }" name="name" placeholder="会员卡充值">
									</div>
								</div>
								<div class="form-group">
									<b class="col-sm-2 control-label" style="width: 100px;">赠送规则</b>
								</div>
								<c:if test="${empty active.detailList }">
									<div class="form-group" data-name="recharge-name">
									    <input type="hidden">
										<div class="col-md-4">
											<label class="col-sm-2 control-label" style="width: 100px;">充：</label>
											<div class="col-sm-4" style="width: 130px;">
												<input type="text" maxlength="11"
												 validate="{required:true,moneyNotZero:true,messages:{moneyNotZero:'请输入正确的金额，保留两位小数'}}" 
												 class="form-control" name="detailList[0].premiseValAmount" placeholder="100">
											</div>
											<span style="line-height: 34px;">元</span>
										</div>
										<div class="col-md-4">
											<label class="col-sm-2 control-label" style="width: 80px;">送：</label>
											<div class="col-sm-4" style="width: 130px;">
												<input type="text" maxlength="11"
												 validate="{required:true,max:21474836.47,moneyBase:true,messages:{moneyBase:'请输入正确的金额，保留两位小数',max:'最大值：21474836.47'}}" 
												 class="form-control" name="detailList[0].resultValAmount" placeholder="10">
											</div>
											<span style="line-height: 34px;">元</span>
										</div>
										<div class="col-md-4">
											<button type="button" class="btn btn-default" onclick="add()">＋ 新加</button>
										</div>
									</div>
								</c:if>
								<c:forEach items="${active.detailList }" var="gz" varStatus="i">
									<div class="form-group" data-name="recharge-name">
										<input type="hidden" name="detailList[${i.index }].id" value="${gz.id }"/>
										<div class="col-md-4">
											<label class="col-sm-2 control-label" style="width: 100px;">充：</label>
											<div class="col-sm-4" style="width: 130px;">
												<input type="text" maxlength="11"
												 validate="{required:true,moneyNotZero:true,messages:{moneyNotZero:'请输入正确的金额，保留两位小数'}}"
												 value='<fmt:formatNumber value='${gz.premiseVal / 100 }' pattern="0.00" />'
												 class="form-control" name="detailList[${i.index }].premiseValAmount"  placeholder="100">
											</div>
											<span style="line-height: 34px;">元</span>
										</div>
										<div class="col-md-4">
											<label class="col-sm-2 control-label" style="width: 80px;">送：</label>
											<div class="col-sm-4" style="width: 130px;">
												<input type="text" maxlength="11"
												 validate="{required:true,moneyBase:true,max:21474836.47,messages:{moneyBase:'请输入正确的金额，保留两位小数',max:'最大值：21474836.47'}}" 
												 class="form-control" value='<fmt:formatNumber value='${gz.resultVal / 100 }' pattern="0.00" />' name="detailList[${i.index }].resultValAmount" placeholder="10">
											</div>
											<span style="line-height: 34px;">元</span>
										</div>
										<div class="col-md-4">
											<c:choose>
												<c:when test="${i.index != 0 }">
													<button type="button" class="btn btn-default" id="" onclick="del(this, ${gz.id})">-&nbsp;&nbsp;&nbsp;删除</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-default" id="" onclick="add()">＋ 新加</button>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:forEach>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 100px;">活动时间：</label>
									<div class="col-md-4">
										<input type="text" id="startTime" validate="{required:true}" 
										class="form-control dateFirst" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d-%H-%m-%s'});" readonly="readonly" name="startTime" value="<fmt:formatDate value='${active.startTime }' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>"
													placeholder="">
									</div>
									<div class="col-md-1 text-center" style="line-height:30px;">
										至
									</div>
									<div class="col-md-4">
										<input type="text" id="endTime" validate="{required:true}" 
										 class="form-control dateLast" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d-%H-%m-%s'});" readonly="readonly" name="endTime" value="<fmt:formatDate value='${active.endTime }' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>"
													placeholder="">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" style="width: 90px;"></label>
									<div class="col-sm-4" style="margin-left: 10px;">
										<button type="button" onclick="formSubmit()" class="btn btn-danger" id="btn-amend">保存</button>
										<a href="${ctx}/u/member/czmg/list${ext}" style="margin-left: 20px;"
											class="btn btn-default">取消</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/footer.jsp"%>
	<script>
		//添加
		function add() {
			var str = '<div class="form-group" data-name="recharge-name">'
					+ '<input type="hidden" name="detailList['+index+'].id" value=""/>'
					+ '<div class="col-md-4">'
					+ ' <label class="col-sm-2 control-label" style="width:100px;">充：</label>'
					+ '<div class="col-sm-4" style="width:130px;">'
					+ '<input type="text" class="form-control" name="detailList['+index+'].premiseValAmount"'
					+ 'maxlength="11" validate="{required:true,moneyNotZero:true,messages:{moneyNotZero:\'请输入正确的金额，保留两位小数\'}}"placeholder="100">'
					+ '</div><span style="line-height: 34px;">元</span>'
					+ '</div>'
					+ '<div class="col-md-4">'
					+ ' <label class="col-sm-2 control-label" style="width:80px;">送：</label>'
					+ ' <div class="col-sm-4" style="width:130px;">'
					+ '<input type="text" maxlength="11" validate="{required:true,moneyBase:true,max:21474836.47,messages:{moneyBase:\'请输入正确的金额，保留两位小数\',max:\'最大值：21474836.47\'}}" class="form-control" name="detailList['+index+'].resultValAmount" placeholder="10">'
					+ '</div> <span style="line-height: 34px;">元</span>'
					+ ' </div>'
					+ ' <div class="col-md-4">'
					+ '<button type="button" class="btn btn-default" id=""  onclick="del(this)">-&nbsp;&nbsp;&nbsp;删除</button>'
					+ '</div>' + ' </div>'

			var recharge = $("[data-name=recharge-name]");
			recharge.eq(recharge.length - 1).after(str);
			index = index+1;
		}
		//删除
		function del(el, id) {
			var len = $("[data-name=recharge-name]").length;
			if (len > 1) {
				art.dialog.confirm('确认删除？', function() {
					if (id != null) {
						$.post("${ctx}/u/member/czmg/deldetail${ext}", {"id" : id}, function(jsonData){
							var data = JSON.parse(jsonData);
							if (data.success) {
								$(el).parents('[data-name=recharge-name]').remove();
							} else {
								art.dialog.alert(data.info);
							}
						});
					} else {
						$(el).parents('[data-name=recharge-name]').remove();
					}
				}, function() {
					return true;
				});
			} else {
			}
		}
		var activity = $("#j-form").validate({
		});
		function formSubmit() {
			if (activity.form()) {
				var flag = isValid();
				if (!flag) {
					return;
				}
				$(".j-loading").show();
				$.post("${ctx}/u/member/czmg/edit${ext}", $("#j-form").serialize(), function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						window.location.href="${ctx}/u/member/czmg/list${ext}";
					} else {
						$(".j-loading").hide();
						art.dialog.alert(data.info);
					}
				});
			}
		}
		function isValid() {
			var oldRechargenum=0,
            oldSendnum=0,isSuccess=true;
	        // 判断抵付金额上下级
	        $("div[data-name='recharge-name']").each(function(index,elem){
	            var $obj=$(this),
// 	                isSuccess=true,
	                $newRechargenum=$obj.find("input:eq(1)"),
	                $newSendnum=$obj.find("input:eq(2)"),
	                newRechargenum=parseFloat($.trim($newRechargenum.val())),
	                newSendnum=parseFloat($.trim($newSendnum.val()));
	
	           if (oldRechargenum!=0&&newRechargenum<=oldRechargenum) {
	                var $label=$newRechargenum.next("label");
	               if ($label.length!=0) {
	                    $label.text("大于以上充值金额");
	                    $label.show();
	               }
	               else{
	                    $newRechargenum.after('<label class="error">大于以上充值金额</label>');
	               }
	                isSuccess=false;
	           }
	           if (oldSendnum!=0&&newSendnum<=oldSendnum) {
	                var $label=$newSendnum.next("label");
	               if ($label.length!=0) {
	                    $label.text("大于以上充值金额");
	                    $label.show();
	               }
	               else{
	                 $newSendnum.after('<label class="error">大于以上赠送金额</label>');
	               }
	               isSuccess=false;
	           }
	           if (!isSuccess) {
	                return false;
	           }
	           else{
	                oldRechargenum=newRechargenum;
	                oldSendnum=newSendnum;
	           }
	       });
	        return isSuccess;
		}
	    function adddelblur(){
	      var $rechargename=$("div[data-name='recharge-name']");
	        $rechargename.find("input").off().on("blur",function(){
	            var $obj=$(this),
	                index=$obj.index("input"),
	                value=$.trim($obj.val());
	                if (index>2) {
	                   var  preValue=$rechargename.find("input").eq(index-3).val();
	                       if (value>preValue) {
	                            $obj.next().hide();
	                       }
	                }
	       });
	    }
	   adddelblur();
	</script>
</body>
</html>