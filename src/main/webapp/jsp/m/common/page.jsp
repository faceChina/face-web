<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function(){
	var pageSize = $("#pageSize").val();
	$("#selectPageSize").val(pageSize);
});

	function topage(toPageNum){
		$('#curPage').val(toPageNum);
		$('#formPage').submit();
    }
    function  jumppages(){
        var re = /^\d+$/;
        var pagetext =  $("#good_page_nb").val();
        if(!re.test(pagetext)){
        	art.dialog.alert("系统无法为您转到第["+pagetext+"]页,请重新输入！");
        	$("#good_page_nb").val("");
        	return false;
        }
        var totalPage = '${pagination.totalPage}';
        if(Number(pagetext)>=Number(totalPage)) {
        	$('#curPage').val(totalPage);
        } else {
        	$('#curPage').val(pagetext);
        }
        $('#formPage').submit();
    }
    function changPageSize(){
    	var size = $("#selectPageSize").val();
    	var curPage = $("#curPage").val();
    	var totalRow = '${pagination.totalRow}';
    	var pagesize = Number(totalRow/size);
    	if(Number(curPage) > Number(pagesize)) {
    		if(Number(pagesize)<=1) {
        		$("#curPage").val(1);
        	} else {
        		pagesize = Math.ceil(totalRow/size);
        		$("#curPage").val(pagesize);
        	}
    	}
    	$("#pageSize").val(size);
    	$('#formPage').submit();
    }
</script>
<input type="hidden" id="pageSize" name="pageSize" value="${pagination.pageSize}"/>
<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>

	<div class="pager">
	<ul id="pagination-digg" class="pagination pull-right">
		<li><span>
			<select class="form-control" id="selectPageSize" onChange="changPageSize();" style="height:20px;padding:0;">
				<c:forEach items="${pagination.showPageSize}" var="showPageSize">
					<option value="${showPageSize}">${showPageSize}</option>
				</c:forEach>
			</select>
		</span></li>
		<li><span>第${pagination.curPage}/${pagination.totalPage}页</span>
		</li>
		<li>
		  <c:choose>
              <c:when test="${pagination.curPage>1}"><a href="javascript:topage('1')">首页</a></c:when>
              <c:otherwise><span>首页</span></c:otherwise>
          </c:choose>
		</li>
		<li>
		    <c:choose>
               <c:when test="${pagination.curPage>1}"><a href="javascript:topage('${pagination.curPage-1}')">上一页</a></c:when>
                <c:otherwise><span>上一页</span></c:otherwise>
            </c:choose>
		</li>
           <c:forEach begin="${pagination.fromPage}" end="${pagination.toPage}" var="num">
				<c:if test="${pagination.curPage!=num}">
					<li><a href="javascript:topage(${num})">${num }</a></li>
				</c:if>
				<c:if test="${pagination.curPage==num}">
					<li><span>${num}</span></li>
				</c:if>
		  </c:forEach>
		<li>
     		 <c:choose>
                 <c:when test="${pagination.curPage<pagination.totalPage}"><a href="javascript:topage('${pagination.curPage+1}')">下一页</a></c:when>
      			 <c:otherwise><span>下一页</span></c:otherwise>
            </c:choose>
		</li>
		<li>
		  <c:choose>
                 <c:when test="${pagination.curPage<pagination.totalPage}"><a href="javascript:topage('${pagination.totalPage}')">尾页</a></c:when>
                 <c:otherwise><span>尾页</span></c:otherwise>
          </c:choose>
          
		</li>
		<li><span id="totalRow">共${pagination.totalRow}条</span></li>
		<li><span>转到：<input class="" id="good_page_nb" style="width:30px;" value="${pagination.curPage}" type="text" />页</span></li>
		<li><a class="goods_go" href="javascript:jumppages()">GO</a></li>
	</ul>
	</div>