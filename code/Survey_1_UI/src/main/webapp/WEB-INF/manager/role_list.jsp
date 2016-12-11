<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$(":text").change(function(){
			var roleName = $.trim(this.value);
			var roleId = this.id;
			
			if(roleName == "") {
				this.value = this.defaultValue;
				return ;
			}
			
			this.value = roleName;
			
			var url = "${pageContext.request.contextPath}/manager/role/updateRole";
			var paramData = {"roleId":roleId, "roleName":roleName,"time":new Date()};
			var callBack = function(respData){
				var message = respData.message;
				alert(message);
			};
			var type = "json";
			$.post(url, paramData, callBack, type);
			
		});
	});
	
</script>

<title>尚硅谷在线调查系统后台</title>
</head>
<body>
	
	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[查看角色]
		<form action="manager/role/batchDelete">
			<table class="dataTable">
				<c:if test="${empty roleList }">
					
					<tr>
						<td>尚未创建角色</td>
					</tr>
					
				</c:if>
				<c:if test="${!empty roleList }">
					
					<tr>
						<td>ID</td>
						<td>角色名称</td>
						<td>批量删除</td>
						<td>分配权限</td>
					</tr>
					
					<c:forEach items="${roleList }" var="role">
						<tr>
							<td>${role.roleId }</td>
							<td><input type="text" name="roleName" id="${role.roleId }" value="${role.roleName }" class="longInput"/></td>
							<td>
								<input type="checkbox" name="roleIdList" value="${role.roleId }" id="label${role.roleId }"/>
								<label for="label${role.roleId }">点我更轻松</label>
							</td>
							<td>
								<a href="manager/role/toDispatcherUI/${role.roleId }">分配权限</a>
							</td>
						</tr>
					</c:forEach>
					
					<tr>
						<td colspan="4">
							<input type="submit" value="批量删除"/>
						</td>
					</tr>
					
				</c:if>
				
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>