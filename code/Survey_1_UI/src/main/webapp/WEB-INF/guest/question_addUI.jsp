<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/res_jsp/base.jsp" %>
<script type="text/javascript">

	$(function(){
		
		//页面刚刚加载出来的时候让包含选项的表格行隐藏
		$("#optionRow").hide();
		
		//点击各个单项按钮时切换#optionRow的显示状态
		//1.通过选择器选中题型对应的单选按钮并绑定单击响应函数
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
		
		[创建问题]
		<form action="guest/question/saveQuestion" method="post">
		
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<input type="hidden" name="bag.bagId" value="${bagId }"/>
			
			<table class="formTable">
				
				<tr>
					<td>问题名称</td>
					<td>
						<input type="text" name="questionName" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>选择题型</td>
					<td>
						<input type="radio" name="questionType" value="0" id="radio01"/>
						<label for="radio01">单选题</label>
						
						<input type="radio" name="questionType" value="1" id="radio02"/>
						<label for="radio02">多选题</label>
						
						<input type="radio" checked="checked" name="questionType" value="2" id="radio03"/>
						<label for="radio03">简答题</label>
					</td>
				</tr>
				
				<tr id="optionRow">
					<td>选项</td>
					<td>
						<textarea class="multiInput" name="options"></textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>