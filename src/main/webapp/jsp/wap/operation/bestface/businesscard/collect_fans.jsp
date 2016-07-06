<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<%@include file="share-card.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<c:choose>
	<c:when test="${'1'==isMaster}"><c:set var="user" value="我" /></c:when>
	<c:otherwise><c:set var="user" value="ta" /></c:otherwise>
</c:choose>
<title>${user }的<c:choose><c:when test="${isCollect == 1 }">收藏</c:when><c:otherwise>粉丝</c:otherwise></c:choose></title>
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/reset.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/public.css">
<link rel="stylesheet" type="text/css" href="${resourcePath }operation/bestface/css/businesscard.css">
<script type="text/javascript" src="${resourceBasePath }js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${resourcePath }operation/bestface/js/search-human.js"></script>
<script>
$(function(){
	//编辑我的收藏
	var collectEidt = (function(){

		var btnEl = $('[data-edit="btn"]');
		var showBtn = function(obj){
			var self = $(obj);
			self.hide();
			self.next().show();
			btnEl.css('display','block');
		}
		var hideBtn = function(obj){
			var self = $(obj);
			self.hide();
			self.prev().show();
			btnEl.css('display','none');
		}

		//删除
		btnEl.on('click',function(){
			preloader.showWhite();
			var itemEl = $(this).closest('[data-edit="item"]');
			var id = itemEl.attr("data-id");
			$.post("${ctx}/wap/${shopNo}/any/icard-collect/del-collection/${icard.id}/"+id+"${ext}",{}, function(data){
				preloader.colse();
  				if(data == 0){
  					if(itemEl.siblings().length == 1) itemEl.prev().remove();
  					itemEl.remove();
  					var collectEl = $('[collect-number="true"]');
  					var number = parseInt(collectEl.text());
  					collectEl.text(--number);
  					alertFun('删除成功');
  				}else{
  					alertFun('删除失败');
  				}
        	});
		});

		//编辑
		$('[data-edit="true"]').on('click',function(){
			showBtn(this);
		});

		$('[data-edit="ok"]').on('click',function(){
			hideBtn(this);
		});

	})();

});


</script>
</head>
<body>
<div class="page-collect">
<c:if test="${1==isMaster}">
	<div class="m-btn-round" >
		<ul class="m-btn-row">
			<li class="m-btn-col"><a href="${ctx }/wap/${shopNo}/any/icard-collect/my-fans/${icard.id}${ext}" class="m-btn-item <c:if test="${isCollect ==0 }">active</c:if>">我的粉丝(<i class="color-prop">${fansCount}</i>)</a></li>
			<li class="m-btn-col"><a href="${ctx }/wap/${shopNo}/any/icard-collect/my-collection/${icard.id}${ext }" class="m-btn-item <c:if test="${isCollect == 1 }">active</c:if>">我的收藏(<i class="color-prop"  collect-number="true">${collectCount}</i>)</a></li>
		</ul>
	</div>
	<div class="page-collect-info">
		<div class="m-search-hum" <c:if test="${1==isCollect && 1==isMaster && not empty iCardDtos}">search-btn="true"</c:if>>
			<c:if test="${1==isCollect && 1==isMaster && not empty iCardDtos}">
				<span class="m-search-btn">
					<button type="button" class="" data-edit="true">编辑</button>
					<button type="button" class="" data-edit="ok" style="display:none;">完成</button>
				</span>
			</c:if>
			<div class="m-search-in">
				<span class="m-search-icon"></span>
				<span class="m-search-input"><input data-search="true" type="text" placeholder="请输入联系人" style="font-size:14px;"></span>
			</div>
		
		</div>		
		<div class="page-collect-list">
			<c:forEach items="${iCardDtos }" var="iCardDto">
					<div class="m-contacts-list">
						<h3 class="m-contacts-key" id="contacts-<c:choose><c:when test="${iCardDto.firstLetter == '#'}">27</c:when><c:otherwise>${iCardDto.firstLetter}</c:otherwise></c:choose>">${iCardDto.firstLetter}</h3>
						<c:forEach items="${iCardDto.icards }" var="iCardDtoIcard">
								<div class="m-contacts-item" data-edit="item" data-search="item" data-id="${iCardDtoIcard.id }">
									<span class="m-contacts-btn" data-edit="btn">
										<span class="m-contacts-del"></span>
									</span>	
									<a href="${ctx }/wap/${iCardDtoIcard.shopNo}/any/icard/my-card/${iCardDtoIcard.id}${ext}">								
									<span class="m-contacts-poto">
										<img src="${picUrl}${iCardDtoIcard.headPicture}" alt=""> 
									</span>
									<span class="m-contacts-txt">
										<span class="contacts-txt-item">
											<em class="contacts-txt-name" data-search="name">${iCardDtoIcard.nickName}</em>
											<var class="contacts-txt-job">${iCardDtoIcard.position}</var>
										</span>
										<span class="contacts-txt-item">
											<em class="contacts-txt-comp">${iCardDtoIcard.company}</em>
										</span>
									</span>
									</a>
								</div>
						</c:forEach>
					</div>
				</c:forEach>
		</div>
		<div class="m-wordcrumbs">
			<ul>
				<li><a href="#contacts-A">A</a></li>
				<li><a href="#contacts-B">B</a></li>
				<li><a href="#contacts-C">C</a></li>
				<li><a href="#contacts-D">D</a></li>
				<li><a href="#contacts-E">E</a></li>
				<li><a href="#contacts-F">F</a></li>
				<li><a href="#contacts-G">G</a></li>
				<li><a href="#contacts-H">H</a></li>
				<li><a href="#contacts-I">I</a></li>
				<li><a href="#contacts-J">J</a></li>
				<li><a href="#contacts-K">K</a></li>
				<li><a href="#contacts-L">L</a></li>
				<li><a href="#contacts-M">M</a></li>
				<li><a href="#contacts-N">N</a></li>
				<li><a href="#contacts-O">O</a></li>
				<li><a href="#contacts-P">P</a></li>
				<li><a href="#contacts-Q">Q</a></li>
				<li><a href="#contacts-R">R</a></li>
				<li><a href="#contacts-S">S</a></li>
				<li><a href="#contacts-T">T</a></li>
				<li><a href="#contacts-U">U</a></li>
				<li><a href="#contacts-V">V</a></li>
				<li><a href="#contacts-W">W</a></li>
				<li><a href="#contacts-X">X</a></li>
				<li><a href="#contacts-Y">Y</a></li>
				<li><a href="#contacts-Z">Z</a></li>
				<li><a href="#contacts-27">#</a></li>

			</ul>
		</div>	
	</div>	
</c:if>
<c:if test="${0==isMaster}">
	<style> 
		.divcss5{text-align:center} 
	</style>
	<div class="page-collect-info">
		<div class="page-collect-list">
			<div class="m-contacts-list">
				<div class="m-contacts-item divcss5">
					<em class="contacts-txt-name" data-search="name">		
						你没有权限查看他的<c:choose><c:when test="${isCollect == 1 }">收藏</c:when><c:otherwise>粉丝</c:otherwise></c:choose>,</br>
						请用正常途径访问！
					</em>
				</div>
			</div>
		</div>
	</div>
</c:if>
</div>
</body>
</html>