package com.atguigu.survey.component.handler.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class AdminHandler {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/manager/admin/doDispatcher")
	public String doDispatcher(
			@RequestParam("adminId") Integer adminId,
			@RequestParam(value="roleIdList" , required=false) List<Integer> roleIdList) {
		
		adminService.writeUpdateRelationship(adminId, roleIdList);
		
		return "redirect:/manager/admin/showList";
	}

	
	@RequestMapping("/manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(
			@PathVariable("adminId") Integer adminId,
			Map<String, Object> map) {
		
		//1.全部角色：List<Role>
		List<Role> allRoleList = roleService.readGetList();
		
		//2.当前账号已经有的角色：List<Integer> roleIdList
		List<Integer> currentRoleIdList = adminService.readGetCurrentRoleIdList(adminId);
		
		//3.adminId
		map.put("adminId", adminId);
		map.put("allRoleList", allRoleList);
		map.put("currentRoleIdList", currentRoleIdList);
		
		return "/manager/dispatcher_admin_role";
	}
	
	@RequestMapping("/manager/admin/showList")
	public String showList(Map<String, Object> map) {
		
		List<Admin> adminList = adminService.readGetList();
		map.put("adminList", adminList);
		
		return "/manager/admin_list";
	}
	
	@RequestMapping("/manager/admin/saveAdmin")
	public String saveAdmin(Admin admin) {
		
		adminService.writeSaveAdmin(admin);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/manager/admin/toMainUI";
	}
	
	@RequestMapping("/manager/admin/login")
	public String login(
			Admin admin,
			HttpSession session) {
		
		Admin loginAdmin = adminService.readLogin(admin);
		
		session.setAttribute(GlobalNames.LOGIN_ADMIN, loginAdmin);
		
		return "redirect:/manager/admin/toMainUI";
	}

}
