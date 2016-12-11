<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>

<title>尚硅谷在线调查系统</title>
</head>
<body>
	
	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[请选择一个操作的调查]
		
		<table class="dataTable">
			
			<tr>
				<td>ID</td>
				<td>调查名称</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${page.list }" var="survey">
				
				<tr>
					<td>${survey.surveyId }</td>
					<td>${survey.surveyName }</td>
					<td>
						<!-- 判断当前遍历得到的Survey对象是不是“当前调查” -->
						<!-- 拿遍历得到的Survey对象的surveyId的值和请求域中取得的surveyId的值进行比较 -->
						<c:if test="${survey.surveyId == requestScope.surveyId }">
							当前调查
						</c:if>
						<c:if test="${survey.surveyId != requestScope.surveyId }">
							<a href="guest/bag/moveToThisSurvey/${bagId }/${survey.surveyId}">移动到这个调查</a>
							|
							<a href="guest/bag/copyToThisSurvey/${bagId }/${survey.surveyId}">复制到这个调查</a>
						</c:if>
					</td>
				</tr>
				
			</c:forEach>
			
			<tr>
				<td colspan="3">
					<c:set var="targetUrl" value="guest/bag/showTargetSurveys/${bagId }/${surveyId }"></c:set>
					<%@include file="/res_jsp/navigator.jsp" %>
				</td>
			</tr>
			
		</table>
		
		<br/>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>