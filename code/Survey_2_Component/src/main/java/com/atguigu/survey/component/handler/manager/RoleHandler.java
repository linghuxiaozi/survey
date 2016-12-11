package com.atguigu.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Role;

@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/manager/role/dispatcher")
	public String doDispatcher(
			@RequestParam("roleId") Integer roleId, 
			@RequestParam(value="authIdList", required=false) List<Integer> authIdList) {
		
		roleService.writeUpdateRelationship(roleId, authIdList);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/toDispatcherUI/{roleId}")
	public String toDispatcherUI(@PathVariable("roleId") Integer roleId,Map<String, Object> map) {
		
		List<Auth> allAuthList = authService.readGetAllAuthList();
		List<Integer> currentIdList = roleService.readGetCurrentAuthIdList(roleId);
		
		map.put("allAuthList", allAuthList);
		map.put("currentIdList", currentIdList);
		map.put("roleId", roleId);
		
		return "/manager/dispatcher_role_auth";
	}
	
	@ResponseBody
	@RequestMapping("/manager/role/updateRole")
	public Map<String, Object> updateRole(Role role) {
		
		roleService.writeUpdateRole(role);
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("message", "操作成功！");
		
		return jsonMap;
	}
	
	@RequestMapping("/manager/role/batchDelete")
	public String batchDelete(@RequestParam("roleIdList") List<Integer> roleIdList) {
		
		roleService.writeBatchDelete(roleIdList);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/showList")
	public String showList(Map<String, Object> map) {
		
		List<Role> roleList = roleService.readGetList();
		map.put("roleList", roleList);
		
		return "/manager/role_list";
	}
	
	@RequestMapping("/manager/role/saveRole")
	public String saveRole(Role role) {
		
		roleService.writeSaveEntity(role);
		
		return "redirect:/manager/role/showList";
	}

}
