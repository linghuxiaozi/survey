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
		
		[给管理员分配角色]
		<form action="manager/admin/doDispatcher">
			<input type="hidden" name="adminId" value="${adminId }"/>
			<table class="dataTable">
				<c:if test="${empty allRoleList }">
					<tr>
						<td>暂时没有角色可以分配</td>
					</tr>
				</c:if>
				<c:if test="${!empty allRoleList }">
					<c:forEach items="${allRoleList }" var="role">
						<tr>
							<td>
								<!-- 回显：判断role.roleId是否在currentRoleIdList集合中 -->
								<%-- 不支持：${currentRoleIdList.contains(role.roleId)} --%>
								<!-- 解决办法：遍历currentRoleIdList，检查遍历得到的每一个值是否和role.roleId相等 -->
								<input type="checkbox" 
									   name="roleIdList" 
									   value="${role.roleId}" 
									   id="label${role.roleId }"
										<c:forEach items="${currentRoleIdList }" var="currentRoleId">
											<c:if test="${currentRoleId==role.roleId }">checked="checked"</c:if>
										</c:forEach>
									   />
									   
								<label for="label${role.roleId }">${role.roleName }</label>
							</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td>
							<input type="submit" value="OK"/>
						</td>
					</tr>
					
				</c:if>
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>