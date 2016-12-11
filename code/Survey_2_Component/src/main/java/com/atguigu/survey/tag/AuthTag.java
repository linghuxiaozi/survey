package com.atguigu.survey.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;

public class AuthTag extends SimpleTagSupport{
	
	private String servletPath;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		//①获取PageContext
		PageContext pageContext = (PageContext) getJspContext();
		
		//②获取Session
		HttpSession session = pageContext.getSession();
		
		//③使用Spring提供的工具方法获取IOC容器的引用
		ServletContext servletContext = pageContext.getServletContext();
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ResService resService = ioc.getBean(ResService.class);
		
		//④根据servletPath查询Res对象
		Res res = resService.readGetResByServletPath(servletPath);
		
		//2.检查当前资源是否是公共资源
		if(res.isPublicRes()) {
			getJspBody().invoke(null);
			return ;
		}
		
		//3.如果不是公共资源，则检查用户是否登录
		if(servletPath.startsWith("/guest")) {
			
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			
			//4.如果用户已经登录，则检查是否具备权限
			if(user != null) {
				
				String codeArrStr = user.getCodeArrStr();
				
				boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
				
				//5.如果具备权限，则执行标签体
				if(hasRight) {
					getJspBody().invoke(null);
					return ;
				}
				
			}
			
		}
		
		if(servletPath.startsWith("/manager")) {
			
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin != null) {
				
				String adminName = admin.getAdminName();
				
				if("SuperAdmin".equals(adminName)) {
					getJspBody().invoke(null);
					return ;
				}
				
				String codeArrStr = admin.getCodeArrStr();
				boolean hasRight = DataprocessUtils.checkAuthority(codeArrStr, res);
				
				if(hasRight) {
					getJspBody().invoke(null);
					return ;
				}
				
			}
			
		}
		
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}
}
