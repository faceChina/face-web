<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理页面</title>
<%@ include file="../../common/base.jsp"%>
<script type="text/javascript"
	src="${resourceBasePath}js/ajaxupload3.9.js"></script>
<script type="text/javascript">
	$(function() {
		tab("member");
	});
</script>
</head>
<style>
.member-title{height:25px; line-height:25px;}
.member-code {
	position: relative;
	left: 10px;
	top: 150px;
	font-size: 18px;
	text-align: center;
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
					<c:set var="crumbs" value="member" />
					<%@include file="../../common/crumbs.jsp"%>
				</div>
				<form id="j-form">
					<input type="hidden" name="id" value="${enact.id }" />
					<div class="row">
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"
										data-toggle="tab">会员卡设置</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="row">
									<div class="col-md-5">
										<div class="member-img" plugin="scale">
											<img id="showImg"
												<c:choose>
<%-- 											        	<c:when test="${empty enact.imgPath}">src="${resourcePath }operation/member/img/pic-01.png"</c:when> --%>
											        	<c:when test="${fn:contains(enact.imgPath, 'resource')}">src="${enact.imgPath }"</c:when>
											        	<c:otherwise>src="${picUrl }${enact.imgPath }"</c:otherwise>
													</c:choose>
												alt="" width="320" height="210" class="pull-left" />
										</div>
										<div class="member-title" style="color: #${enact.cardNameColor }">${enact.cardName }</div>
										<div class="member-code" style="font-family:'微软雅黑';font-size: 15px;color: #${enact.cardNoColor }">卡号：${enact.cardCode }-1</div>
									</div>
									<div class="col-md-7">
										<div class="form-horizontal">
											<div class="form-group">
												<label class="col-md-3 col-md-offset-1 control-label">会员卡名称：</label>
												<div class="col-md-8">
													<input type="text" class="form-control" value="${enact.cardName }" maxlength="22"
														name="cardName" id="cardName" placeholder="">
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 col-md-offset-1 control-label">会员卡名称颜色：</label>
												<div class="col-md-8">
													<input class="color form-control" name="cardNameColor"
														value="${enact.cardNameColor }" id="cardNameColor" />
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3 col-md-offset-1 control-label"
													for="exampleInputFile">会员卡背景：</label>
												<div class="col-md-4">
													<input type="text" value="${enact.imgPath }" name="imgPath"
														class="form-control" id="imgPath" placeholder="">
												</div>
												<div class="col-md-2">
													<div class="btn btn-default btn-upload">
														上传<input type="button" value="" class="form-control"
															id="uploadImage" name="uploadImg">
													</div>
												</div>
												<div class="col-md-2">
													<a href="javascript:;" class="btn btn-default"
														onclick="selectPic()">选择</a>
												</div>
											</div>
											<div class="help-block f-help col-md-11 col-md-offset-1">备注：背景图宽537px高343px，图片类型png。</div>
											<div class="form-group">
												<label class="col-md-3 col-md-offset-1 control-label">卡号文字颜色：</label>
												<div class="col-md-8">
													<input class="color form-control" name="cardNoColor"
														value="${enact.cardNoColor }" id="cardNoColor" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"
										data-toggle="tab">卡号设置</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="form-horizontal">
									<div class="form-group">
										<label class="col-md-2 control-label">卡号英文编号：</label>
										<div class="col-md-4">
											<input type="text" value="${enact.cardCode }" maxlength="5"
												class="form-control" name="cardCode" id="cardCode"
												placeholder="">
										</div>
										<p class="help-block col-md-6">
											例：<span class="fontcor-red">BSD</span>-65535 <span
												class="fontcor-red">BSD</span>就是英文编号，英文编号不超过5位
										</p>
									</div>
									<div class="form-group">
										<label class="col-md-3  control-label">卡号生成范围&nbsp;起始卡号：</label>
										<div class="col-md-3">
											<input type="text" class="form-control" maxlength="9"
												value="${enact.startNo }" name="startNo" id="startNo"
												placeholder="">
										</div>
										<div class="col-md-3 col-md-offset-1 col-md-fixed">
											<span>结束卡号：</span> <input type="text" class="form-control" maxlength="9"
												value="${enact.endNo }" name="endNo" id="endNo"
												placeholder="">
										</div>
									</div>
									<div id="card-no-info" class="col-md-offset-1 help-block f-inpt-help">最小起始卡为：${enact.startNo },最大结束卡号为：${enact.endNo }</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="box box-auto">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set"
										data-toggle="tab">会员卡说明</a></li>
								</ul>
							</div>
							<div class="content">
								<%@include file="member-content.jsp"%>
								<div class="text-center f-btn">
									<button type="button" class="btn btn-default" data-id="save">保存</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 会员卡背景选择 -->
	<div id="jmemberbg" style="display: none;">
		<ul class="memberBg">
			<c:forEach items="${img }" var="data" varStatus="status">
				<li><a href="javascript:;"><img
						src="${data.value.imgPath }" alt="" code="${data.key }" /></a><span>会员卡${status.index }</span></li>
			</c:forEach>
		</ul>
	</div>
	<%@include file="../../common/footer.jsp"%>
</body>
<script type="text/javascript">
	//弹出背景列表层
	function selectPic() {
		art.dialog({
			lock : true,
			title : "会员卡背景选择",
			content : document.getElementById("jmemberbg"),
			width : "600px",
			ok : function() {
				$("#showImg").attr("src",
						$(".memberBg li.current a img").attr("src"));
				$("#imgPath").val($(".memberBg li.current a img").attr("src"));
			},
			cancel : true
		})
	}
	//选择背景
	$(".memberBg li").each(function() {
		$(this).click(function() {
			$(this).siblings().removeClass("current");
			$(this).addClass("current");
		});
	});
	var activity = $("#j-form").validate({
		rules : {
			cardName : {
				required : true
			},
			imgPath : {
				required : true
			},
			cardNameColor : {
				required : true
			},
			cardNoColor : {
				required : true
			},
			cardCode : {
				required : true,
				letters : true
			},
			startNo : {
				required : true,
				positiveInteger : true,
				lessTonew : '#endNo'
			},
			endNo : {
				positiveInteger : true,
				required : true
			}
		},
		messages : {
			cardCode : {
				letters : '请输入全英文字母的卡号前缀'
			},
			startNo : {
				positiveInteger : '卡号应该输入正整数',
				lessTonew : '开始卡号要小于结束卡号'
			},
			endNo : {
				positiveInteger : '卡号应该输入正整数',
			}
		}
	});
	$('[data-id=save]').on('click', function() {
		var flag = activity.form();
		if (flag) {
			//提交
			$('#memberContent').val(getContent());
			$(".j-loading").show();
			$.post("${ctx }/u/member/enact/edit${ext}", $('#j-form').serialize(), function(jsonData){
				var data = JSON.parse(jsonData);
				if (data.success) {
					art.dialog.alert("保存成功",function(){location.reload();});
				} else {
					art.dialog.alert(data.info);
				}
				$(".j-loading").hide();
			});
		} else {
			//验证不通过
			return false;
		}
	})
	//图片上传
	var button = $('#uploadImage');
		new AjaxUpload(button, {
			action : '${uploadUrl}',
			data : {},
			onSubmit : function(file, ext) {
				var imageSuffix = new RegExp('${imageSuffix}');
				if (!(ext && imageSuffix.test(ext.toUpperCase()))) {
					art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
					return false;
				}
			},
			autoSubmit : true,
			responseType : 'json',
			onChange : function(file, ext) {
			},
			onComplete : function(file, response) {
				if (response.flag == "SUCCESS") {
					$('#showImg').show();
					$('#showImg').attr("src", '${picUrl}' + response.source);
					$('#imgPath').val(response.path);
				} else {
					art.dialog.alert(response.info);
					return;
				}
			}
		});
</script>
</html>
