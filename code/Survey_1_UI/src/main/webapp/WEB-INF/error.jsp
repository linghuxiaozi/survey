<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$("button").click(function(){
			
			//模拟浏览器的后退按钮
			window.history.back();
			
		});
	});
	
</script>

<title>尚硅谷在线调查系统</title>
</head>
<body>
	
	<div id="mainDiv" class="borderDiv">
		
		[操作错误]
		<br/>
		<c:if test="${exception != null }">
			${exception.message }
		</c:if>
		<br/>
		<button>返回继续操作</button>
		
	</div>
	
	
</body>
</html>