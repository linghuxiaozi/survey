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
		
		[创建调查]
		<form action="guest/survey/saveSurvey" method="post" enctype="multipart/form-data">
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
						<input type="text" name="surveyName" class="longInput" value="${param.surveyName }"/>
					</td>
				</tr>
				<tr>
					<td>选择图片</td>
					<td>
						<!-- 文件上传框的name属性值不能使用Survey对象的logoPath属性 -->
						<!-- logoPath属性是String类型的 -->
						<!-- 文件上传框提交的数据是二进制文件数据 -->
						<!-- 所以如果强行将文件数据设置到logoPath属性中会产生“类型转换”失败错误，在页面上显示400 -->
						<input type="file" name="logoFile"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="上传"/>
					</td>
				</tr>
			</table>
		</form>
		
		<!-- <img src="surveyLogos/happy.gif"/> -->
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>