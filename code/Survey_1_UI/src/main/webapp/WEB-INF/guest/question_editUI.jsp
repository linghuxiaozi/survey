<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		//页面初始化时要根据实际的题型决定#optionRow是否显示
		//1.查找当前页面上被选中的radio——查找当前页面上所有的radio，然后在这个范围中查找被选中的那个
		var choosedType = $(":radio:checked").val();
		console.log(choosedType);
		
		//2.检查type值
		if(choosedType == 2) {
			$("#optionRow").hide();
		}
		
		//点击单选按钮时切换
		$(":radio").click(function(){
			//2.获取当前被点击的单选按钮的value属性值
			var type = this.value;
			
			//3.检查value值是0,1,2中的哪一个，如果是0或1，则将#optionRow显示
			if(type == 0 || type == 1) {
				$("#optionRow").show();
			}
			
			//4.如果是2，则将#optionRow隐藏
			if(type == 2) {
				$("#optionRow").hide();
			}
			
		});
		
	});

</script>

<title>尚硅谷在线调查系统</title>
</head>
<body>
	
	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[编辑问题]
		<form:form action="guest/question/updateQuestion" method="post" modelAttribute="question">
			
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<form:hidden path="questionId"/>
			
			<table class="formTable">
				
				<tr>
					<td>问题名称</td>
					<td>
						<form:input path="questionName" cssClass="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>选择题型</td>
					<td>
						<form:radiobuttons path="questionType" items="${requestScope.radioMap }"/>
					</td>
				</tr>
				
				<tr id="optionRow">
					<td>选项</td>
					<td>
						<!-- 由于回显数据和注入请求参数需要的属性名不一致，所以使用HTML标签 -->
						<%-- <form:textarea path="editOption"/> --%>
						<textarea class="multiInput" name="options">${question.editOption }</textarea>
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