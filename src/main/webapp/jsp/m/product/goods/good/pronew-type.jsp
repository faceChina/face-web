<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>商品分类-新品上市</title>	
<!-- top -->
<%@ include file="../../../common/base.jsp"%>
<!--top end -->
<script type="text/javascript" src="${resourcePath}js/addNewShop.js"></script>

<script type="text/javascript">
$(function(){
	tab("prolist");
	//选择分类
	var chooseType = function(){
		var steplen = $('.m-choose-column[data-type]').length;
		var navText = '';
		var stepKey;
		function stepLen(){
			return steplen;
		}
		//导入HTML
		function strHtml(data,step){

			var activeTxt;

			for(key in data){
				var str = '';
				var strLi = '';
				stepKey = (parseInt(step)+1) || (parseInt(key)+1);
			
				for(key2 in data[key]){
					var a = (data[key][key2]["active"])?'active':'';
						if(a) activeTxt = data[key][key2]["title"];
					strLi +='<li class="'+a+'"  data-id="'+data[key][key2]["id"]+'"><i>&gt;</i><span>'+data[key][key2]["title"]+'</span></li>';
				}
				str = [
					'<ul>',strLi,'</ul>'

				].join(' ');
				if(activeTxt) navBreadcrumb(stepKey-1,activeTxt);
				
				showHideDom('show',stepKey,str);
			}
			
		}
		//选择分类面包屑
		function navBreadcrumb(index,txt){
			$('[data-type="nav"] li').eq(index).show().find('span').text(txt).end().nextAll().hide();

		}
		//按钮状态
		function btnDisabled(flag){
			if(flag){
				$('[data-type="step"]').removeAttr('disabled');
			}else{
				$('[data-type="step"]').attr('disabled','true');
			}
		}
		//显示隐藏DIV
		function showHideDom(type,step,str){
			var stepDom = $('.m-choose-column[data-type='+step+']');
			if(type == 'show'){
				stepDom.show();
				if(str){
					stepDom.find('.m-choose-type').html(str);
				}
			}else{
				stepDom.hide();
			}
		}
		return {
			sethtml:strHtml,
			steplen:stepLen,
			btndisabled:btnDisabled,
			showHideDom:showHideDom,
			navBreadcrumb:navBreadcrumb
		}
	}();


//初始化
// var data=JSON.parse('${json}');
// chooseType.sethtml(data);


var steplen = chooseType.steplen();
//下拉框 选择最近分类
	$('[data-type="select"]').on('change',function(){
		var id = $("#m-steps .active").val();
		alert("id="+id);
		if(""==id){
			chooseType.btndisabled(true);
		}else{
			$("#next").attr("href","${ctx}/u/good/good/add/"+id+"${ext}");
		}
	});

	//点击分类
	$('.m-choose-type').on('click','li',function(){
		var self=this;
		var step = $(this).closest('.m-choose-column').attr('data-type');
		var title = $(this).find('span').text();
		var id = $(this).attr('data-id');
		chooseType.navBreadcrumb(step-1,title);
		if(step == steplen){
			chooseType.btndisabled(true);
			$("#next").attr("href","${ctx}/u/good/good/add/"+id+"${ext}");
			step++;
			$(self).addClass('active').siblings().removeClass('active');
			if(step <= steplen){
				chooseType.showHideDom('show',step);
			}
		}else{
			//导入数据
			$.post("${ctx}/u/good/good/queryClassification${ext}",{"pid":id,'type':1},function(data){
				var dataJson = JSON.parse(data)
				chooseType.sethtml(dataJson,step);
				chooseType.btndisabled(false);
				if(step == '1'){
					chooseType.showHideDom('hide',steplen);
				}
				step++;

				$(self).addClass('active').siblings().removeClass('active');
				if(step <= steplen){
					chooseType.showHideDom('show',step);
				}
			});
		}
	});

	//搜索
	$('[data-type="search"]').on('input',function(){
		var el = $(this).parent().next().find('li');
		var thisTxt = this.value;
		el.each(function(){
			var txt = $(this).find('span').text(),
				flag = localSearch(thisTxt,txt)
			if(flag){
				$(this).show();
			}else{
				$(this).hide();
			}
		})

	});
});	
//本地搜索 
function localSearch(val,txt){
	var val = val || '',
		txt = txt || '',
	  	valArr = val.split(""),
	  	regStr="";
	for(var k=0;k<valArr.length;k++){
		regStr += '(?=.*?'+valArr[k]+')';
	}
	var reg=new RegExp(regStr);
	if(txt.match(reg)){
		return true;
	}
	return false;
}
</script>
</head>
<body>

<!-- header -->
	<%@include file="../../../common/header.jsp"%>
<!-- header end -->

	<!-- body -->
	<div class="container" id="j-content">
		<div class="row">
			<div class="col-md-2 ">
				<!--nav-left -->
				<%@include file="../../../common/left.jsp"%>
				<!--nav-left end-->
			</div>
			<div class="col-md-10">
					<div class="row">
						<c:set var="crumbs" value="goodlist"/>
						<%@include file="../../../common/crumbs.jsp"%>
					</div>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">发布商品</a></li>
									<li class="text-muted"></li>
								</ul>
							</div>
							<div class="content">
								<div class="row">
									<ul class="m-steps" id="m-steps">
										<li class="active">1.选择商品类目</li>
										<li>2.填写商品信息</li>
										<li><i class="iconfont icon-gou"></i> 设置完成</li>
									</ul>
								</div>
								<div class="row" style="margin:15px 0;">
									<div class="col-md-2"><b style="line-height:34px;">您最近使用的类目：</b></div>
									<div  class="col-md-6">
										<select name="" id="" data-type="select" class="form-control">
											<c:choose>
												<c:when test="${not empty classificationVo}">
													<option value="${classificationVo.id}">${classificationVo.allName}</option>
												</c:when>
												<c:otherwise>
													<option value="">请选择</option>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="m-choose-column" data-type="1">
											<div class="m-choose-search"><input type="text" class="form-control" data-type="search" placeholder="请输入栏目"></div>
											<div class="m-choose-type" >
												<ul>
												<c:forEach items="${classificationList }" var="classification">
													<li data-id="${classification.id}"><i>&gt;</i><span>${classification.name }</span></li>
												</c:forEach>
											</div>
										</div>
										
									</div>
									<div class="col-md-4">
										<div class="m-choose-column" data-type="2" style="display:none;">
											<div class="m-choose-search"><input type="text" class="form-control" data-type="search" placeholder="请输入栏目"></div>
											<div class="m-choose-type" >
												<!-- <ul>
													<li><i>&gt;</i><span>普通商品4</span></li>
													<li><i>&gt;</i><span>普通商品5</span></li>
													<li><i>&gt;</i><span>普通商品6</span></li>
												</ul> -->
											</div>
										</div>
										
									</div>
									<div class="col-md-4">
										<div class="m-choose-column" data-type="3" style="display:none;">
											<div class="m-choose-search"><input type="text" class="form-control" data-type="search" placeholder="请输入栏目"></div>
											<div class="m-choose-type" >
												<!-- <ul>
													<li><span>普通商品7</span></li>
													<li><span>普通商品8</span></li>
												</ul> -->
											</div>
										</div>
										
									</div>
								</div>
								<div class="row">
									<div class="m-breadcrumb-shop clearfix">
										<div class="arrow"></div>
										<div class="m-breadcrumb-title"><b>你当前选择的类目是：</b></div>
										<c:choose>
											<c:when test="${not empty classificationVo}">
												<ul class="m-breadcrumb-list" data-type="nav">
													<c:forEach items="${classificationVo.nameList}" var="nameList" varStatus="status">
														<li><span>${nameList}</span> <c:if test="${status.index+1<fn:length(classificationVo.nameList)}">&gt;</c:if></li>
													</c:forEach>
												</ul>
											</c:when>
											<c:otherwise>
												<ul class="m-breadcrumb-list" data-type="nav">
													<li style="display:none;"><span></span> &gt;</li>
													<li style="display:none;"><span></span> &gt;</li>
													<li style="display:none;"><span></span></li>
												</ul>
											</c:otherwise>
										</c:choose>
									</div>
									
								</div>
								<div class="row">
									<div class="col-md-12 text-center">
										<c:choose>
											<c:when test="${not empty classificationVo}">
												<a id="next" href="${ctx}/u/good/good/add/${classificationVo.id}${ext}" class="btn btn-default" data-type="step">下一步</a>
											</c:when>
											<c:otherwise>
												<a id="next" href="#" class="btn btn-default" data-type="step" disabled="true">下一步</a>
											</c:otherwise>
										</c:choose>
										<!-- 通用商品 --> 
										<!-- <a href="pronew2.html" class="btn btn-default" data-type="step" disabled="true">下一步</a> -->
										<!-- <a href="pronew-general.html" class="btn btn-default" data-type="step" disabled="true">下一步</a> -->
									</div>
									
								</div>
						
								
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!-- body end -->

	<!-- footer -->
		<%@include file="../../../common/footer.jsp"%>
	<!-- footer end -->
	</body>
</html>

