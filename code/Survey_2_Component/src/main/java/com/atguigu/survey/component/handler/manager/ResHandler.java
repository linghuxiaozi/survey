package com.atguigu.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@Controller
public class ResHandler {
	
	@Autowired
	private ResService resService;
	
	@ResponseBody
	@RequestMapping("/manager/res/toggleStatus")
	public Map<String, String> toggleStatus(@RequestParam("resId") Integer resId) {
		
		boolean status = resService.writeToggleStatus(resId);
		
		//{"message":"操作成功！","status":true/false}
		
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("message", "操作成功！");
		jsonMap.put("status", status+"");
		
		return jsonMap;
	}
	
	@RequestMapping("/manager/res/batchDelete")
	public String batchDelete(@RequestParam(value="resIdList",required=false) List<Integer> resIdList) {
		
		if(resIdList != null && resIdList.size() > 0) {
			resService.writeBatchDelete(resIdList);
		}
		
		return "redirect:/manager/res/showList";
	}
	
	@RequestMapping("/manager/res/showList")
	public String showList(Map<String, Object> map) {
		
		List<Res> resList = resService.readGetList();
		map.put("resList", resList);
		
		return "/manager/res_list";
	}

}
