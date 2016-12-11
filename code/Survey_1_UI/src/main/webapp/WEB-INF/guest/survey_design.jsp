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
			
			var bagName = $(this).parents("tr").children("td:eq(0)").text();
			
			var flag = confirm("您真的要对【"+bagName+"】这个包裹执行深度删除操作吗？");
			
			if(flag) {
				
				var againFlag = confirm("这是一个危险操作，这个包裹下的所有问题也会被一起删除，你真的确定要这么做吗？");
				
				if(againFlag) {
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
		
		[设计调查]
		<table class="dataTable">
			<tr>
				<td colspan="4">调查的基本信息</td>
			</tr>
			<tr>
				<td><img src="${survey.logoPath }"/></td>
				<td>${survey.surveyName }</td>
				<td>
					<a href="guest/bag/toAddUI/${survey.surveyId }">创建包裹</a>
				</td>
				<td>
					<a href="guest/bag/toAdjustUI/${survey.surveyId }">调整包裹顺序</a>
				</td>
			</tr>
		</table>
		
		<br/><br/>
		
		<table class="dataTable">
			<tr>
				<td colspan="2">包裹信息</td>
			</tr>
			<tr>
				<td>包裹名称</td>
				<td>基本操作</td>
			</tr>
			<c:if test="${empty survey.bagSet }">
				<tr>
					<td colspan="2">尚未创建包裹</td>
				</tr>
			</c:if>
			<c:if test="${!empty survey.bagSet }">
				
				<c:forEach items="${survey.bagSet }" var="bag">
					<tr>
						<td>${bag.bagName }</td>
						<td>
							<a href="guest/bag/removeBag/${bag.bagId }/${requestScope.survey.surveyId}">删除包裹</a>
							<a href="guest/bag/toEditUI/${bag.bagId }">更新包裹</a>
							<a href="guest/question/toAddUI/${bag.bagId }/${requestScope.survey.surveyId}">创建问题</a>
							<a class="deeplyRemoveAnchor" href="guest/bag/deeplyRemvoe/${bag.bagId }/${requestScope.survey.surveyId}" style="background-color: black;color: yellow;">深度删除</a>
							<a href="guest/bag/showTargetSurveys/${bag.bagId }/${requestScope.survey.surveyId}">移动/复制调查</a>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table class="dataTable">
								<c:if test="${empty bag.questionSet }">
									<tr><td>尚未创建问题</td></tr>
								</c:if>
								<c:if test="${!empty bag.questionSet }">
									
									<tr>
										<td>问题详情</td>
										<td>问题操作</td>
									</tr>
									<c:forEach items="${bag.questionSet }" var="question">
										
										<tr>
											<td>
												${question.questionName }
												
												<!-- 判断具体题型 -->
												<c:if test="${question.questionType == 0 }">
													<c:forEach items="${question.optionArr }" var="option">
														<input type="radio"/>${option }
													</c:forEach>
												</c:if>
												
												<c:if test="${question.questionType == 1 }">
													<c:forEach items="${question.optionArr }" var="option">
														<input type="checkbox"/>${option }
													</c:forEach>
												</c:if>
												
												<c:if test="${question.questionType == 2 }">
													<input type="text" class="longInput"/>
												</c:if>
												
											</td>
											<td>
												<a href="guest/question/removeQuestion/${question.questionId }/${survey.surveyId}">删除问题</a>
												<a href="guest/question/toEditUi/${question.questionId }/${survey.surveyId}">更新问题</a>
											</td>
										</tr>
										
									</c:forEach>
								
								</c:if>
							</table>
						</td>
					</tr>
				</c:forEach>
				
			</c:if>
			
			<tr>
				<td colspan="2">
					<a href="guest/survey/complete/${survey.surveyId }">完成调查</a>
				</td>
			</tr>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>