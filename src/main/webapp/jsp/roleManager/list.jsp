<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@include file="../m/common/base.jsp" %>
<script type="text/javascript">

function sub(permissionId,index){
	var roleId = $("#role_" + index).val();
	$.post('${ctx}/role/save${ext}',{'permissionId':permissionId,'roleId':roleId},function(data){
		
		
	});
}

</script>

</head>
<body>

<form  action="${ctx}/role/save${ext}"  method="post" >
		<button type="button" onClick="" >新增资源</button>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th width="20%">权限名称</th>
					<th width="20%">资源路径</th>
					<th width="10%">权限标识符</th>
					<th width="25%">角色</th>
					<th width="25%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${permissionList }" var="data" varStatus="status">
					<tr>
						<td>${data.name}<input type="hidden" name="permissionId"  value="${data.id}"/></td>
						<td>${data.url}<input type="hidden" name="permissionUrl"  value="${data.url}"/></td>
						<td>${data.code}<input type="hidden" name="permissionCode"  value="${data.code}"/></td>
						<td>
							<select name="roleId" id="role_${status.index}">
							<c:forEach items="${roleList }" var="role">
								<option value="${role.id}" >${role.name}</option>
							</c:forEach>
							</select>
						</td>
						<td>
						<button type="button" onClick="sub('${data.id}','${status.index}')" >submit</button>
						
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</form>
</body>
</html>