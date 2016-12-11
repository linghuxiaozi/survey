<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
		
		[编辑调查]
		<form:form 
			action="guest/survey/updateSurvey" 
			method="post" 
			modelAttribute="survey"
			enctype="multipart/form-data">
			
			<form:hidden path="surveyId"/>
			
			<!-- 这里讲旧的logoPath放在表单隐藏域中，可以同时适配“用户上传文件”和“用户没有上传文件”两种情况 -->
			<form:hidden path="logoPath"/>
			
			<!-- SpringMVC处理表单步骤：
					1.调用Survey的无参构造器创建SurveyId对象。
					2.将表单请求参数封装到SurveyId对象中。
					3.如果不通过表单隐藏域提供userId，那么更新时数据库中的USER_ID会被设置为null
			 -->
			<form:hidden path="user.userId"/>
			
			<!-- 关于表单隐藏域：name属性和value属性要分别对待 -->
			<!-- name属性：控制将隐藏域的值注入到目标实体类对象的什么属性中 -->
			<!-- value属性：控制隐藏域真正要提交到服务器的值 -->
			<%-- <input type="hidden" name="user.userId" value="${survey.user.userId }"/> --%>
			
			<!-- 对于不在Survey实体类中的属性就不能使用Spring的表单标签 -->
			<%-- <form:hidden path="pageNo"/> --%>
			<input type="hidden" name="pageNo" value="${pageNo }"/>
			
			<table class="formTable">
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">
							${exception.message }
						</td>
					</tr>
				</c:if>
			
				<tr>
					<td>调查名称</td>
					<td>
						<form:input path="surveyName" cssClass="longInput"/>
					</td>
				</tr>
				<tr>
					<td>当前图片</td>
					<td><img src="${survey.logoPath }"/></td>
				</tr>
				<tr>
					<td>选择图片</td>
					<td>
						<input type="file" name="logoFile"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="更新"/>
					</td>
				</tr>
			
			</table>
			
		</form:form>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>