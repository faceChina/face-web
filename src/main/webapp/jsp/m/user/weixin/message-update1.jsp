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
<script type="text/javascript" src="${resourcePath}plugins/ajaxupload3.9.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${resourcePath}js/wechat.js"></script>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("message");
	
	echoSelect('${messageSetting.eventType}', '${messageSetting.matchingType}');
	
	$('.content').find("button[name='uploadImg']").each(function(index){
		uploadimg($(this));
	});
	
	$("#j-select2").change(function() {
		var eventType = ${messageSetting.eventType};
		var recoveryMode = $(this).val();
		var param = "&eventType="+eventType+"&recoveryMode="+recoveryMode;
		var name = $("#messageName").val();
		if(3==eventType) {
			var keyWord = $("#keyWord").val();
			var matchingType = $("#matchingType").val();
			if(''!=keyWord) {
				param += "&keyWord="+keyWord+"&matchingType="+matchingType;
			}else {
				param += "&matchingType="+matchingType;
			}
			param += "&name="+name;
		}
		console.log(param);
		window.location.href='${ctx}/u/weixin/chooseReplyMode.htm?'+param;
	});
	
	//回显赋值
	if('${mContents[0].linkType }'=="1") {
		$(".appmsg_editor:visible .js_url_area").hide();
		$("#linkType").val("${mContents[0].linkType }");
	} else{
		$(".appmsg_editor:visible .js_url_area").show();
		$("#linkType").val("${mContents[0].linkType }");
		$("#linkPath").val("${mContents[0].linkPath }");
	}
	
	if('${messageSetting.eventType}'=='3'){
		$("#keyWord").val("${messageSetting.keyWord }");
		$("#matchingType").val("${messageSetting.matchingType }");
	}
	wechat.delft(${weixinItem });
});

/* 单图文 多图文编辑*/

var elNum = 1; //全局变量
var storeNum = 1; //全局变量

function uploadimg(btn){
    var url = "${ctx}/any/files/upload${ext}";
    new AjaxUpload(btn, {
        action: url,
        autoSubmit: true,
        responseType: 'json',
        onSubmit: function(file,ext){
        	var imageSuffix = new RegExp('${imageSuffix}');
        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
            	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
                return false;               
            }
        },
        onComplete: function(file, response){
        	if(response.flag == "SUCCESS") {
        		$('#showImg').show();
        		$('#showImg').attr("src",'${picUrl}'+response.source);
        		$('#editorImg').show();
        		$('#editorImg').attr("src",'${picUrl}'+response.source);
        		$('#picPath').val(response.path);
        		$('.appmsg_pathimg').find('img').show().prev().hide();
        	} else {
        		art.dialog.alert(response.info);
        	}
        }
    });
}

function saveMessage() {
	var flg = wechat.submitFrom();
	var keyword = $("#keyWord").val();
	var messageName = $("#messageName").val();
	if($("${messageSetting.eventType}").val()=='3') {
		if(keyword=='' || keyword.length>32) {
			flg = false;
			wechat.pageTips('关键词不能为空并且不能超过32个字符');
		};
		if(messageName =='' || messageName.length > 16) {
			flg = false;
			wechat.pageTips('消息名称不能为空并且不能超过16个字符');
		};
	}
	if(flg) {
		$(".j-loading").show();
		$("#form").submit();
	}
}
</script>
<script type="text/javascript">
/**
* ueditor控件相关
*/
UE.getEditor('editor');
function getContent() {
    return UE.getEditor('editor').getContent();
}
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
								<input type="hidden" name="messageBodyId" value="${messageSetting.messageBodyId }">
								<input type="hidden" name="eventType" value="${messageSetting.eventType}">
								<input id="weixinItem" type="hidden" name="weixinItem" value='${weixinItem }'>
								<c:if test="${messageSetting.eventType == 3}">
									<input type="hidden" name="recoveryMode" value='${messageSetting.recoveryMode }'>
								</c:if>
								<table class="table table-thleft table-noborder">
									<tbody>
										<c:if test="${messageSetting.eventType == 3 }">
											<tr data-name>
												<th width="10%">名称</th>
												<td><input type="text" id="messageName" name="name" value="${messageSetting.name}" class="form-control"></td>
											</tr>
										</c:if>
										<tr data-module>
											<th width="10%">回复模式</th>
											<td><select <c:if test="${messageSetting.eventType == 3}">disabled="disabled"</c:if> name="recoveryMode" class="form-control" id="j-select2">
														<option value="1">文字模式</option>
														<option value="2" selected="selected">单图文模式</option>
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
									
									</tbody>
								</table>


								<div class="main_bd" id="j-txtimg">
									<div class="media_preview_area">
										<div class="appmsg  editing">
											<div id="js_appmsg_preview" class="appmsg_content">
											    <div id="appmsgItem1" data-fileid="" data-id="1" class="js_appmsg_item" style="padding:0 14px;">
												<h4 class="appmsg_title"><a onclick="return false;" href="javascript:void(0);" target="_blank">${mContents[0].title }</a></h4>
												<div class="appmsg_info">
													<em class="appmsg_date"></em>
												</div>
												<div class="appmsg_thumb_wrp">
													<img id="showImg"  class="js_appmsg_thumb appmsg_thumb default" src="${picUrl}${mContents[0].picPath }">
													<i class="appmsg_thumb">封面图片</i>
												</div>
												<p class="appmsg_description">${mContents[0].description }</p>
											</div>
											</div>
										</div>

									</div>

									<div class="media_edit_area">
										<div id="js_appmsg_editor">
										<div class="appmsg_editor" style="margin-top: 0px;">
											<div class="inner">
												<div class="appmsg_edit_item">
													<label for="" class="frm_label">标题<span class="fontcor-red">*</span></label>
													<span class="frm_input_box"><input type="text" class="frm_input j-datawx" onkeyup="wechat.blurText(this)" name="title" value="${mContents[0].title }"></span>
												</div>
												<div class="appmsg_edit_item">
													<label for="" class="frm_label">
														<strong class="title">封面<span class="fontcor-red">*</span></strong>
														<p class="js_cover_tip tips">（建议大图360*200PX,小图200*200PX）</p>
														<input class="j-datawx" type="hidden" id="picPath" name="picPath" value="${mContents[0].picPath }">
														<input class="j-datawx" type="hidden" name="messageContentId" value="${mContents[0].id }">
													</label>
													<div class="upload_wrap">
														<button type="button" name='uploadImg' class="btn btn-default">上传</button>
													</div>
													<p class="appmsg_pathimg"><span style="display: none;">图文</span><img id="editorImg" class="j-datawx" name="pic" src="${picUrl}${mContents[0].picPath }" alt="" style="height:78px;"></p>
												</div>
												<div class="js_desc_area appmsg_edit_item">
													<label for="" class="frm_label">摘要</label>
														<span class="frm_textarea_box"><textarea id="description" class="frm_textarea j-datawx" onkeyup="wechat.blurText(this)" name="description">${mContents[0].description }</textarea></span>
												</div>
												<div class="appmsg_edit_item">
													<label for="" class="frm_label">正文<span class="fontcor-red">*</span>（正文和链接类型选择填写一样）</label>
													<div class="js-ueditor" style="width:393px;">
														<script id="editor" type="text/plain" style="height:120px;">${mContents[0].content}</script>
													</div>
												</div>
	
												<p style="margin-bottom:5px;">
													<select id="linkType" class="form-control input-short-5 j-linkaddr j-datawx" name="linkType">
														<option value="2">链接地址</option>
														<option value="1">首页</option>
													</select>
												</p>
												<p style="margin-bottom:5px;">（注：选择链接至首页时，正文内容不显示）</p>
												<div class="js_url_area appmsg_edit_item">
													<span class="frm_input_box"><input id="linkPath" type="text" class="js_url frm_input j-datawx" name="linkPath"></span>
												</div>
											</div>
											<i class="arrow arrow_out" style="margin-top: 0px;"></i>
											<i class="arrow arrow_in" style="margin-top: 0px;"></i>
										</div>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="text-center" style="border-top:1px solid #ccc;margin-top:20px;padding-top:20px;">
										<button type="button" onclick="saveMessage();" class="btn btn-default">发布</button>
									</div>
								</div>
							</form>
							</div>
						</div>
					</div>
		</div>
	</div>
	<!-- body end -->
</div>
	
	<!-- footer -->
	<%@include file="../../common/footer.jsp" %>
	<!-- footer end -->
	
	</body>
</html>