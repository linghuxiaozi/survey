<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$(".deeplyRemoveAnchor").click(function(){
			
			var surveyName = $(this).parents("tr").children("td:eq(1)").text();
			
			var firstFlag = confirm("您真的要删除【"+surveyName+"】这个调查吗？");
			
			if(firstFlag) {
				
				var secondFlag = confirm("这是一个危险操作，这个调查下所有的包裹和所有的问题也会被一起删除，你真的确定要这么做吗？");
				if(secondFlag) {
					return true;
				}
				
			}
			
			return false;
		});
	});
	
</script>

<title>尚硅谷在线调查系统</title>
</head>
<body>
	
	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[显示我未完成的调查]
		<table class="dataTable">
			
			<c:if test="${empty page.list }">
				<tr>
					<td>您尚未创建任何调查！</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.list }">
				<tr>
					<td>ID</td>
					<td>调查名称</td>
					<td>Logo</td>
					<td colspan="4">操作</td>
				</tr>
				<c:forEach items="${page.list }" var="survey">
					
					<tr>
						<td>${survey.surveyId }</td>
						<td>${survey.surveyName }</td>
						<td><img src="${survey.logoPath }"/></td>
						<td>
							<a href="guest/survey/toDesignUI/${survey.surveyId }">设计</a>
						</td>
						<td>
							<a href="guest/survey/removeSurvey/${survey.surveyId }">删除</a>
						</td>
						<td>
							<a href="guest/survey/toEditUI/${survey.surveyId }/${page.pageNo}">更新</a>
						</td>
						<td class="danger">
							<a class="deeplyRemoveAnchor" style="color: yellow" href="guest/survey/deeplyRemove/${survey.surveyId }">深度删除</a>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="7">
						<c:set var="targetUrl" value="guest/survey/showMyUncompleted" scope="page"/>
						<%@include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>