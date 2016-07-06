<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function(){
		var pageSize = $("#pageSize").val();
	});

	function topage(toPageNum){
		$('#curPage').val(toPageNum);
		$('#formPage').submit();
    }
    function  jumppages(){
    	 var re = /^\d+$/;
        var pagetext =  $("#good_page_nb").val();
        if(!re.test(pagetext)){
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
</script>
<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pagination.pageSize}"/>
<div class="page">
	<ul class="pagination">
		<c:choose>
               <c:when test="${pagination.curPage>1}">
               		<li><a href="javascript:topage('${pagination.curPage-1}')"">上一页</a></li>
               </c:when>
               <c:otherwise>
                	<li class="disabled"><a href="#">上一页</a></li>
               </c:otherwise>
       </c:choose>
        <c:forEach begin="1" end="${pagination.toPage}" var="num">
				<c:if test="${pagination.curPage!=num}">
					<li><a href="javascript:topage(${num})">${num }</a></li>
				</c:if>
				<c:if test="${pagination.curPage==num}">
					<li class="active"><span>${num}</span></li>
				</c:if>
		</c:forEach>
		<c:choose>
            <c:when test="${pagination.curPage<pagination.totalPage}">
            	<li><a href="javascript:topage('${pagination.curPage+1}')">下一页</a></li>
            </c:when>
     		<c:otherwise>
     			<li><a href="#">下一页</a></li>
     		</c:otherwise>
        </c:choose>
		<li><a href="javascript:void(0)">跳转到：<input id="good_page_nb" type="text" value="${pagination.curPage}" style="size: 10px;"></a></li>
		<li><a href="javascript:jumppages()">确认</a></li>
    </ul>
</div>