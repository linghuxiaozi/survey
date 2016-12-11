<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	$(function(){
		$(":button").click(function(){
			
			//在事件响应函数中this关键字引用的才是最初点击的那个按钮
			var thisReference = this;
			
			//1.为了执行Ajax切换资源状态的操作准备数据
			var resId = this.id;
			
			//2.为了发送Ajax请求为$.post()方法准备参数
			var url = "${pageContext.request.contextPath}/manager/res/toggleStatus";
			var paramData = {"resId":resId,time:new Date()};
			var callBack = function(respData){
				//alert(respData);
				
				//{"message":"操作成功！","status":"true"/"false"}
				var message = respData.message;
				var status = respData.status;
				
				alert(message);
				
				//0,"",null……在进行条件判断时会被当做false
				//非零,非空字符串,非空对象……在进行条件判断时会被当做true
				
				//this关键字在函数中，引用的是调用当前函数的那个对象
				
				if(status == "true") {
					//alert("服务器端返回了【真】");
					thisReference.value = "公共资源";
				}

				if(status == "false"){
					//alert("服务器端返回了【假】");
					thisReference.value = "受保护资源";
				}
				
			};
			
			//告诉jQuery服务器端返回的数据是什么格式的
			var type = "json";
			
			$.post(url, paramData, callBack, type);
			
		});
	});
</script>

<title>尚硅谷在线调查系统后台</title>
</head>
<body>
	
	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[资源列表]
		<form action="manager/res/batchDelete" method="post">
			<table class="dataTable">
				
				<c:if test="${empty resList }">
					<tr>
						<td>暂时没有录入资源</td>
					</tr>
				</c:if>
				<c:if test="${!empty resList }">
				
					<tr>
						<td>ID</td>
						<td>ServletPath</td>
						<td>资源状态</td>
						<td>删除</td>
					</tr>
					
					<c:forEach items="${resList }" var="res">
						
						<tr>
							<td>${res.resId }</td>
							<td>${res.servletPath }</td>
							<td>
								<c:if test="${res.publicRes }">
									<input type="button" id="${res.resId }" value="公共资源"/>
								</c:if>
								<c:if test="${!res.publicRes }">
									<input type="button" id="${res.resId }" value="受保护资源"/>
								</c:if>
							</td>
							<td>
								<input type="checkbox" name="resIdList" value="${res.resId }" id="label${res.resId }"/>
								<label for="label${res.resId }">点我更轻松</label>
							</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td colspan="4">
							<input type="submit" value="批量删除"/>
						</td>
					</tr>
				
				</c:if>
				
			</table>
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>