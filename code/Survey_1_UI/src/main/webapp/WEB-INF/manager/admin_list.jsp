<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>

<title>尚硅谷在线调查系统后台</title>
</head>
<body>
	
	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[查看账号]
		<table class="dataTable">
			<c:if test="${empty adminList }">
				
				<tr>
					<td>尚未创建账号</td>
				</tr>
				
			</c:if>
			<c:if test="${!empty adminList }">
				
				<tr>
					<td>ID</td>
					<td>账号名称</td>
					<td>分配角色</td>
				</tr>
				
				<c:forEach items="${adminList }" var="admin">
					<tr>
						<td>${admin.adminId }</td>
						<td>${admin.adminName }</td>
						<td>
							<a href="manager/admin/toDispatcherUI/${admin.adminId }">分配角色</a>
						</td>
					</tr>
				</c:forEach>
				
			</c:if>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>