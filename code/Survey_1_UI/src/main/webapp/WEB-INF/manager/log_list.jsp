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
		
		[查看日志]
		<table class="dataTable">
			
			<c:if test="${empty page.list }">
				<tr>
					<td>暂时没有日志数据可以显示</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.list }">
				<c:forEach items="${page.list }" var="log">
					
					<tr>
						<td>ID</td>
						<td>详情</td>
					</tr>
					<tr>
						<td>${log.logId }</td>
						<td>
							操作人：${log.operator}<br/>
							操作时间：${log.operateTime}<br/>
							方法名：${log.methodName}<br/>
							类型名：${log.typeName}<br/>
							入参：${log.parameters}<br/>
							返回值：${log.returnValue}<br/>
							异常类型：${log.exceptionType}<br/>
							异常信息：${log.exceptionMessage}<br/>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="2">
						<c:set var="targetUrl" value="manager/log/showList" scope="page"/>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>