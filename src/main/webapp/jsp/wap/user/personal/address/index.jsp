<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<%@include file="../../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>地址管理</title>
<%@ include file="../../../common/top.jsp"%>
<link rel="stylesheet" type="text/css" href="${resourcePath }personal/css/main.css">
</head>
<body>
	<div id="box">
		<div id="j-address">
		<c:if test="${empty list }">
			<div class="no-content">
				<i class="iconfont icon-shouhuodizhi clr-light"></i>
				<p>您还没有收货地址哦~</p>
			</div>
		</c:if>
		<c:if test="${not empty list }">
			<c:forEach items="${list }" var="address">
				<div class="group group-justify group-small <c:if test="${address.isDefault == 1 }">active</c:if>">
				<div class="group-item">
					<div class="group-rowspan">
						<div class="group-colspan">
							<b data-address-name>${address.name }</b>
							<b data-address-phone>${address.cell }</b>
						</div>						
						<div class="group-colspan">
							<i class="iconfont <c:if test='${editAddressId==address.id }'>icon-roundcheckfill</c:if> clr-danger fnt-24" data-address-icon></i>
						</div>
					</div>
					<div class="group-rowspan">
						<div class="group-colspan">
							<span class="clr-light" data-address>${address.addressDetail }</span>
						</div>
						<div class="group-colspan">
							<i class="iconfont clr-danger" data-iconfont style="font-size: 24px;"></i>
						</div>
					</div>
				</div>
				<div class="group-item">
					<div class="group-rowspan">
						<div class="group-colspan" style="cursor: pointer;">
							<c:choose>
								<c:when test="${address.isDefault == 1 }">
								    <span onclick="setDefault(this, '${address.id}')" data-default-address class="clr-danger">
										<i class="iconfont icon-dizhiblock fnt-24"></i> 默认
									</span>
								</c:when>
								<c:otherwise>
									<span onclick="setDefault(this, '${address.id}')" data-default-address>
										<i class="iconfont icon-dizhi fnt-24"></i> 设为默认
									</span>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="group-colspan" style="cursor: pointer;">
							<span onclick="location.href='/wap/${sessionShopNo}/buy/address/editAddress${ext }?id=${address.id }'"><i class="iconfont icon-bianji fnt-20"></i> 编辑</span> 
							<span onclick="del(this, '${address.id}')">
								<i class="iconfont icon-shanchu fnt-20"></i> 删除
							</span>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
		</c:if>
			<div class="button">
				<a href="${ctx }/wap/${sessionShopNo}/buy/address/editAddress${ext }" class="btn btn-default btn-danger btn-block">+添加收货地址</a>
			</div>
		</div>
		<%@ include file="../../../common/foot.jsp"%>
		<%@ include file="../../../common/nav.jsp"%>
	</div>
	<script type="text/javascript">
		//设为默认地址
		function setDefault(thiz, id) {
			var isDefault = $(thiz).parents(".group").hasClass("active");
			if (isDefault) {
				return;
			}
			$.post("${ctx }/wap/${sessionShopNo}/buy/address/default${ext }", {"id" : id}, function(jsonData){
				var data = JSON.parse(jsonData);
				if (data.success) {
					$(thiz).parents(".group")
						.siblings(".group")
						.removeClass("active")
						.find("[data-default-address]")
						.removeClass("clr-danger")
						.html('<i class="iconfont icon-location" style="font-size:24px;"></i> 设为默认')
						.end()
						.end()
						.addClass("active")
						.insertBefore($(thiz).parents(".group").siblings(".group")
										.first())
						.find("[data-default-address]")
						.addClass("clr-danger")
						.html('<i class="iconfont icon-locationfill" style="font-size:24px;"></i> 默认')
				} else {
					art.dialog.alert(data.info);
				}
			});
		}

		//删除地址
		function del(thiz, id) {
			var isDefault = $(thiz).parents(".group").hasClass("active");
			if (isDefault) {
				art.dialog.alert("默认收货地址不可删除");
				return;
			}
			art.dialog.confirm("你确定要删除吗？", function() {
				$.post("${ctx }/wap/${sessionShopNo}/buy/address/del${ext }", {"id" : id}, function(jsonData){
					var data = JSON.parse(jsonData);
					if (data.success) {
						$(thiz).parents(".group")
							.siblings(".group")
							.first()
							.addClass("active")
							.find("[data-default-address]")
							.addClass("clr-danger")
							.html('<i class="iconfont icon-locationfill" style="font-size:24px;"></i> 默认')
							.end()//find("[data-default-address]")
							.end()//siblings(".group").first()
							.end()//parents(".group")
							.remove();
					} else {
						art.dialog.alert(data.info);
					}
				});
				
			}, function() {
				return true;
			})
		}
		$('.group').click(function(){
			$('.group .group-item [data-address-icon]').removeClass('icon-roundcheckfill');
			$(this).find('[data-address-icon]').addClass('icon-roundcheckfill');
		});
	</script>
</body>
</html>