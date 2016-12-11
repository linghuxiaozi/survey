<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.atguigu.com/survey" prefix="atguigu" %>
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
		
		[参与调查]
		<table class="dataTable">
			<tr>
				<td colspan="2">调查信息</td>
			</tr>
			<tr>
				<td>Logo</td>
				<td>名称</td>
			</tr>
			<tr>
				<td><img src="${sessionScope.currentSurvey.logoPath }"/></td>
				<td>${sessionScope.currentSurvey.surveyName }</td>
			</tr>
		</table>
		
		<br/><br/>
		
		<form action="guest/engage/engage" method="post">
			
			<!-- 服务器端需要根据currentIndex计算“上一个”或“下一个”包裹的索引 -->
			<input type="hidden" name="currentIndex" value="${requestScope.currentIndex }"/>
			
			<!-- 提交当前包裹的id，因为服务器端需要根据bagId将当前包裹提交的请求参数保存到allBagMap中 -->
			<input type="hidden" name="bagId" value="${requestScope.currentBag.bagId }"/>
			
			<table class="dataTable">
				<tr>
					<td>当前包裹：${requestScope.currentBag.bagName }</td>
				</tr>
				<tr>
					<td>
						<c:forEach items="${requestScope.currentBag.questionSet }" var="question">
							${question.questionName }
							<c:if test="${question.questionType==0 }">
								<c:forEach items="${question.optionArr }" var="option" varStatus="optionStatus">
									<!-- <input type="radio" name="q43" value="0" id="label430"> -->
									<!-- <label for="label430">李小龙</label> -->
									<input type="radio" 
										   name="q${question.questionId }" 
										   value="${optionStatus.index }" 
										   id="label${question.questionId }${optionStatus.index }"
											<atguigu:doRedisplayTag 
													questionType="${question.questionType }" 
													currentValue="${optionStatus.index }" 
													bagId="${requestScope.currentBag.bagId }" 
													paramName="q${question.questionId }"/>
										   >
									<label for="label${question.questionId }${optionStatus.index }">${option }</label>
								</c:forEach>
							</c:if>
							<c:if test="${question.questionType==1 }">
								<c:forEach items="${question.optionArr }" var="option" varStatus="optionStatus">
									<input type="checkbox" 
										   name="q${question.questionId }" 
										   value="${optionStatus.index }" 
										   id="label${question.questionId }${optionStatus.index }"
										   	<atguigu:doRedisplayTag 
													questionType="${question.questionType }" 
													currentValue="${optionStatus.index }" 
													bagId="${requestScope.currentBag.bagId }" 
													paramName="q${question.questionId }"/>
										   >
									<label for="label${question.questionId }${optionStatus.index }">${option }</label>
								</c:forEach>
							</c:if>
							<c:if test="${question.questionType==2 }">
								<input type="text" 
									   class="longInput" 
									   name="q${question.questionId }"
									   <atguigu:doRedisplayTag 
											questionType="${question.questionType }" 
											bagId="${requestScope.currentBag.bagId }" 
											paramName="q${question.questionId }"/>
								/>
							</c:if>
							<br/>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						<c:if test="${requestScope.currentIndex>0 }">
							<input type="submit" name="submit_prev" value="返回上一个包裹"/>
						</c:if>
						
						<c:if test="${requestScope.currentIndex<lastIndex }">
	   					 	<input type="submit" name="submit_next" value="进入下一个包裹"/>
						</c:if>
						
					    <input type="submit" name="submit_quit" value="放弃"/>
					    
					    <c:if test="${requestScope.currentIndex==lastIndex }">
						    <input type="submit" name="submit_done" value="完成"/>
					    </c:if>
					</td>
				</tr>
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>