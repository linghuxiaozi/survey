<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.atguigu.com/survey" prefix="atguigu" %>
<div id="logoDiv" class="borderDiv">尚硅谷在线调查系统后台</div>
	
	<div id="topDiv" class="borderDiv">
		
		<c:if test="${sessionScope.loginAdmin == null }">
			[<a href="manager/admin/toLoginUI">登录</a>]
		</c:if>
		<c:if test="${sessionScope.loginAdmin != null }">
			[欢迎您：${sessionScope.loginAdmin.adminName }]
			[<a href="manager/admin/logout">退出登录</a>]
			
			<atguigu:auth servletPath="/manager/statistics/showAllAvailableSurveys">
				[<a href="manager/statistics/showAllAvailableSurveys">统计调查</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/res/showList">
				[<a href="manager/res/showList">资源列表</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/auth/toAddUI">
				[<a href="manager/auth/toAddUI">创建权限</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/auth/showList">
				[<a href="manager/auth/showList">查看权限</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/role/toAddUI">
				[<a href="manager/role/toAddUI">创建角色</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/role/showList">
				[<a href="manager/role/showList">查看角色</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/admin/toAddUI">
				[<a href="manager/admin/toAddUI">创建账号</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/admin/showList">
				[<a href="manager/admin/showList">查看账号</a>]
			</atguigu:auth>
			
			<atguigu:auth servletPath="/manager/log/showList">
				[<a href="manager/log/showList">查看日志</a>]
			</atguigu:auth>
		</c:if>
		
		[<a href="manager/admin/toMainUI">首页</a>]
		
	</div>