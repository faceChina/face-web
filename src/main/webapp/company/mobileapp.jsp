<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>刷脸平台-手机应用</title>
<%@ include file="/company/common/base-company.jsp"%>
</head>
<body>
<%@ include file="common/header.jsp"%>

<div class="content  mobileapp">
	<div class="left"><img src="${companyPath}img/login/app_img.png"  alt=""/></div>
	<div class="right">
		<div class="app-info">
			<h2>产品简介</h2>
			<p>刷脸是浙江脸谱刷脸平台潜心研发的集聊天、店铺、客户、员工管理于一身的app工具，简单流畅的操作体验让企业老板和员工随时随地管理官网和商城</p>
		</div>
		<div class="app-list">
			<h2>主要功能</h2>
			<ul>
				<li><a href="javascript:;"><i></i><span>订单管理：提供店铺订单数据及详情查询。</span></a></li>
				<li><a href="javascript:;"><i></i><span>发货管理：自由选择快递公司、送货上门及自取方式。</span></a></li>
				<li><a href="javascript:;"><i></i><span>销量统计：及时查看员工及店铺的关键数据。</span></a></li>
				<li><a href="javascript:;"><i></i><span>消息提醒：订单状态变化和商品上下架即时提醒</span></a></li>
				<li><a href="javascript:;"><i></i><span>即时聊天：随时随地与客户交流</span></a></li>
			</ul>
		</div>
		<ul class="app-download" id="j-app-download">
			<li>
				<a href="javascript:;" class="install-btn">安装 Android 版 </a>
				<div class="app-download-info" style="display:none;">
					<div class="app-codedownload">
						<h4>扫码直接安装</h4>
						<img src="${companyPath}img/login/app-code.png" alt="二维码" width="182" height="182"/>
						<a href="http://www.g-jia.net/assistant/bestface.apk">下载安装包</a>
					</div>
				</div>
			</li>
		
			<a href="itms-services://?action=download-manifest&url=https://www.g-jia.cn/assistant/bestface.plist" class="install-btn-ios">安装 IOS 版 </a>
		</ul>
		<div class="app-fot">
			<p>* App仅支持部分功能，方便老板随身简单打理店铺 </p>
			<p>* 商城完整功能，请 <a href="/" class="clr-red">登录</a> 网页端后台使用</p>
		</div>
	</div>
</div>

<%@ include file="/company/common/footer.jsp"%>
<script type="text/javascript">
	$(function(){
		$('#j-app-download li').each(function(){
			$(this).hover(function(){
				$(this).find('div:first').show();
			},function(){
				$(this).find('div:first').hide();
			})
		});
	});
</script>
</body>
</html>