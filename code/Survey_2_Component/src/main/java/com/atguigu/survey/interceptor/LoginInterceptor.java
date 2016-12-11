package com.atguigu.survey.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.e.AdminOperationForbiddenException;
import com.atguigu.survey.e.UserOperationForbiddenException;
import com.atguigu.survey.utils.GlobalNames;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		Set<String> publicResSet = new HashSet<>();
		publicResSet.add("/guest/user/toLoginUI");
		publicResSet.add("/guest/user/toRegistUI");
		publicResSet.add("/guest/user/login");
		publicResSet.add("/guest/user/regist");
		publicResSet.add("/guest/user/logout");
		publicResSet.add("/manager/admin/toMainUI");
		publicResSet.add("/manager/admin/toLoginUI");
		publicResSet.add("/manager/admin/login");
		publicResSet.add("/manager/admin/logout");
		
		String servletPath = request.getServletPath();
		
		if(publicResSet.contains(servletPath)) {
			System.out.println("公共资源，直接放行"+servletPath);
			return true;
		}
		
		HttpSession session = request.getSession();
		
		//1.检查当前请求所属的名称空间
		if(servletPath.startsWith("/guest")) {
			
			//2.检查User是否登录
			Object user = session.getAttribute(GlobalNames.LOGIN_USER);
			
			if(user == null) {
				throw new UserOperationForbiddenException("请登录后再执行这个操作！");
			}
			
		}
		
		if(servletPath.startsWith("/manager")) {
			
			//3.检查Admin是否登录
			Object admin = session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin == null) {
				throw new AdminOperationForbiddenException("请登录后再执行这个操作！");
			}
			
		}
		
		return true;
	}

}
