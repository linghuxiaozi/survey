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
		
		[给权限分配资源]
		<form action="manager/auth/dispatcher">
			<input type="hidden" name="authId" value="${authId }"/>
			<table class="dataTable">
				
				<c:if test="${empty resAllList }">
					<tr>
						<td>暂时没有资源可以分配</td>
					</tr>
				</c:if>
				<c:if test="${!empty resAllList }">
					
					<tr>
						<td>资源列表</td>
					</tr>
					<c:forEach items="${resAllList }" var="res">
						
						<tr>
							<td>
								<input type="checkbox" 
									   name="resIdList" 
									   value="${res.resId }" 
									   id="label${res.resId }"
										   <c:forEach items="${currentResIdList }" var="currentResId">
										   		<c:if test="${currentResId==res.resId }">
										   			checked="checked"
										   		</c:if>
										   </c:forEach>
									   />
								<label for="label${res.resId }">${res.servletPath }</label>
							</td>
						</tr>
						
					</c:forEach>
				
				</c:if>
				
				<tr>
					<td>
						<input type="submit" value="OK"/>
					</td>
				</tr>
				
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>