<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$(":text").change(function(){
			
			var bagOrder = $.trim(this.value);
			
			if(bagOrder == "" || isNaN(bagOrder)) {
				
				this.value = this.defaultValue;
				
				return ;
				
			}
			
			this.value = bagOrder;
			
		});
	});
	
</script>

<title>尚硅谷在线调查系统</title>
</head>
<body>
	
	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[调整包裹顺序]
		<form action="guest/bag/adjustOrder" method="post">
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<table class="dataTable">
			
				<c:if test="${exception != null }">
					<tr>
						<td colspan="3">${exception.message }</td>
					</tr>
				</c:if>
				
				<c:if test="${empty bagList }">
					<tr>
						<td>您尚未创建任何包裹！</td>
					</tr>
				</c:if>
				<c:if test="${!empty bagList }">
					
					<tr>
						<td>ID</td>
						<td>包裹名称</td>
						<td>序号</td>
					</tr>
					<c:forEach items="${bagList }" var="bag">
						
						<tr>
							<td>${bag.bagId }</td>
							<td>${bag.bagName }</td>
							<td>
								<input type="hidden" name="bagIdList" value="${bag.bagId }"/>
								<input type="text" name="bagOrderList" value="${bag.bagOrder }" class="longInput"/>
							</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td colspan="3">
							<input type="submit" value="OK"/>
						</td>
					</tr>
					
				</c:if>
				
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>