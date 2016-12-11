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
			var authName = $.trim(this.value);
			var authId = this.id;
			
			if(authName == "") {
				this.value = this.defaultValue;
				return ;
			}
			
			this.value = authName;
			
			var url = "${pageContext.request.contextPath}/manager/auth/updateAuth";
			var paramData = {"authId":authId, "authName":authName,"time":new Date()};
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
		
		[查看权限]
		<form action="manager/auth/batchDelete">
			<table class="dataTable">
				<c:if test="${empty authList }">
					
					<tr>
						<td>尚未创建权限</td>
					</tr>
					
				</c:if>
				<c:if test="${!empty authList }">
					
					<tr>
						<td>ID</td>
						<td>权限名称</td>
						<td>批量删除</td>
						<td>分配资源</td>
					</tr>
					
					<c:forEach items="${authList }" var="auth">
						<tr>
							<td>${auth.authId }</td>
							<td><input type="text" name="authName" id="${auth.authId }" value="${auth.authName }" class="longInput"/></td>
							<td>
								<input type="checkbox" name="authIdList" value="${auth.authId }" id="label${auth.authId }"/>
								<label for="label${auth.authId }">点我更轻松</label>
							</td>
							<td>
								<a href="manager/auth/toDispatcherUI/${auth.authId }">分配资源</a>
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