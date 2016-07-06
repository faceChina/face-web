<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="UTF-8" />
<title>查看教程</title>
<!-- top -->
<%@ include file="../common/base.jsp"%>
<!--top end -->
<script type="text/javascript">
$(function(){
	tab("accounts");
});
</script>
    <style>
        .table-striped>tbody>tr:nth-child(even)>td, .table-striped>tbody>tr:nth-child(even)>th{
            background-color: #f9f9f9;
        }
        .table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th{
            background-color: #fff;
        }
    </style>
</head>
<body>
<!-- header -->
<%@ include file="../common/header.jsp"%>
<!-- header end -->
<!-- body -->
<div class="container" id="j-content">
	<div class="row">

		<div class="col-md-2">
			<!--nav-left -->
			<%@include file="../common/left.jsp"%>
			<!--nav-left end-->
		</div>

		<div class="col-md-10 ">
				<div class="row">
					<%@include file="../common/crumbs.jsp"%>
				</div>

				<div class="row box">
                    <div style="text-align: center">
                        <img src="${resourcePath }/img/tv-img-1.jpg" alt=""/>
                        <img src="${resourcePath }/img/tv-img-2.jpg" alt=""/>
                        <img src="${resourcePath }/img/tv-img-3.jpg" alt=""/>
                        <img src="${resourcePath }/img/tv-img-4.jpg" alt=""/>
                    </div>
				</div>
		</div>
	</div>
</div>
<!-- body end -->
<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- footer end -->
<script>
var activity = $("#j-form").validate({
	rules:{
		phone:{
			phone:true
		},
		url:{
			url2:true
		}
	},
	messages:{
		phone:{
			required:'客户电话错误'
		},
		url:{
			url2:"地址格式错误！"
		}
	}
});
$('button[name="sumbit"]').click(function(){
    var flag = activity.form();
	if(flag){
        //提交
        
	}else{
		//验证不通过
	}
})
</script>
	</body>
</html>