<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		
		$("#go").change(function(){
			
			//1.获取当前文本框中输入的值
			var pageNo = this.value;
			//console.log(pageNo);
			
			//2.对pageNo进行必要的校验
			pageNo = $.trim(pageNo);
			
			//Not a number
			if(isNaN(pageNo) || pageNo == "") {
				//console.log("函数停止执行");
				
				//将原来的默认值写回文本框
				this.value = this.defaultValue;
				return ;
			}
			
			//3.如果输入的是正确的值，则将去掉前后空格的值写回
			this.value = pageNo;
			
			//4.跳转页面
			//在IE中，JavaScript代码中的URL地址不参照base标签
			window.location.href = "${pageContext.request.contextPath}/${targetUrl}?pageNoStr="+pageNo;
			
		});
		
	});
</script>
<c:if test="${page.hasPrev }">
	<a href="${targetUrl}?pageNoStr=1">首页</a>
	<a href="${targetUrl}?pageNoStr=${page.prev }">上一页</a>
</c:if>

当前是第${page.pageNo }页
共${page.totalPageNo }页
共${page.totalRecordNo }条记录
跳转到第<input value="${page.pageNo }" type="text" class="shortInput" id="go"/>页

<c:if test="${page.hasNext }">
	<a href="${targetUrl}?pageNoStr=${page.next }">下一页</a>
	<a href="${targetUrl}?pageNoStr=${page.totalPageNo }">末页</a>
</c:if>