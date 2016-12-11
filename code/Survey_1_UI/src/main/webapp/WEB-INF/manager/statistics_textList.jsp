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
		
		[显示简答题答案]
		<table class="dataTable">
			
			<c:if test="${empty textList }">
				<tr>
					<td>这个问题还没有人参与</td>
				</tr>
			</c:if>
			<c:if test="${!empty textList }">
				<c:forEach items="${textList }" var="content">
					<tr>
						<td>${content }</td>
					</tr>
				</c:forEach>
			</c:if>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>