<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>我的银行卡--添加银行卡</title>
<%@include file="../../../../common/base.jsp"%>
<%@include file="../../../../common/top.jsp"%>
<%@include file="../top.jsp" %>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/wallet/css/main.css">
</head>
<body>

<div id="box">
	
	<form  action="javascript:;" method="post" id="jform" data-form>
	
		<p class="help-block clr-light" style="margin-top:0;">请绑定持卡人本人的银行卡</p>
		
		<div class="group group-others width60 group-cleartop">
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">持卡人</li>
					<li class="group-colspan">王雪</li>
					<li class="group-colspan" onclick="showLayer('card')"><i class="iconfont icon-tishi clr-blue fnt-24"></i></li>
				</ul>
			</div>
			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">身份证</li>
					<li class="group-colspan">3243********0989</li>
					<li class="group-colspan"></li>
				</ul>
			</div>

			<div class="group-item">
				<ul class="group-rowspan">
					<li class="group-colspan">卡号</li>
					<li class="group-colspan">
						<input type="text" name="card" id="card" class="form-control" placeholder="请输入银行卡号" data-form-control>
					</li>
				</ul>
			</div>
		</div>
		<div id="card-description"></div>
			
		<div class="button">
			<button type="submit" class="btn btn-danger btn-block disabled" data-submit onclick="select(this)">下一步</button>
		</div>
		
	</form>
	
	<!-- 弹出层  -->
	<div class="remind-box" id="j-showlayer" data-show="card" style="display:none;">
		<div class="remind">
			<h4 class="text-center">持卡人说明 </h4>
			<p>为保证账户资金安全，只能绑定认证 用户本人的银行卡。</p>
		</div>
	</div>
	
	<%@ include file="../../../../common/foot.jsp"%>
	<%@ include file="../../../../common/freeNav.jsp" %>
	
</div>

<script type="text/javascript">
$(function(){
	$("jform").validate({
		rules: {
			card: {
				required: true,
				integer: true
			}
		},
		messages: {
			card: {
				required: "请输入银行卡号",
				integer:"请输入正确的卡号"
			}
		}
	});
});

function select(){
	art.dialog({
		title:"选择",
		content:"<a href='purse-adddepositcard.html'>添加的为储蓄卡</a> | <a href='purse-addcreditcard.html'>添加的为信用卡</a>"
	})
}


</script>
</body>
</html>