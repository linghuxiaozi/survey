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
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Res;

@Controller
public class AuthHandler {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ResService resService;
	
	@RequestMapping("/manager/auth/dispatcher")
	public String doDispatcher(
			@RequestParam(value="resIdList", required=false) List<Integer> resIdList,
			@RequestParam("authId") Integer authId) {
		
		authService.writeDoDispatcher(resIdList, authId);
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/toDispatcherUI/{authId}")
	public String toDispatcherUI(
			@PathVariable("authId") Integer authId,
			Map<String, Object> map) {
		
		List<Res> resAllList = resService.readGetList();
		List<Integer> currentResIdList = resService.readGetCurrentResIdList(authId);
		
		map.put("resAllList", resAllList);
		map.put("currentResIdList", currentResIdList);
		map.put("authId", authId);
		
		return "/manager/dispatcher_auth_res";
	}
	
	@ResponseBody
	@RequestMapping("/manager/auth/updateAuth")
	public Map<String, String> updateAuth(Auth auth) {
		
		authService.writeUpdateAuth(auth);
		
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("message", "操作成功！");
		
		return jsonMap;
	}
	
	@RequestMapping("/manager/auth/batchDelete")
	public String batchDelete(@RequestParam("authIdList") List<Integer> authIdList) {
		
		authService.writeBatchDelete(authIdList);
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/showList")
	public String showList(Map<String, Object> map) {
		
		List<Auth> authList = authService.readGetList();
		map.put("authList", authList);
		
		return "/manager/auth_list";
	}
	
	@RequestMapping("/manager/auth/saveAuth")
	public String saveAuth(Auth auth) {
		
		authService.writeSaveEntity(auth);
		
		return "redirect:/manager/auth/showList";
	}

}
