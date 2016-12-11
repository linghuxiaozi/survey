package com.atguigu.survey.component.handler.guest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/guest/user/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
	
	//既金针刺绣，又巨笔摩天
	@RequestMapping("/guest/user/login")
	public String login(User user, HttpSession session) {
		
		//1.检测封装表单数据的user对象是否包含了正确的用户名、密码，如果验证通过，则返回从数据库查询得到的User对象
		User loginUser = userService.readLogin(user);
		
		//2.将从数据库查询得到的User对象保存到Session域中
		session.setAttribute(GlobalNames.LOGIN_USER, loginUser);
		
		/*HttpServletRequest request = RequestBinder.getRequest();
		System.out.println(request);*/
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/regist")
	public String regist(User user) {
		
		userService.writeRegist(user);
		
		return "/guest/user_login";
	}
	
}
