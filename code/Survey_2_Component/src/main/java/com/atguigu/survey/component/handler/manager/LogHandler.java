package com.atguigu.survey.component.handler.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.TokenBinder;

@Controller
public class LogHandler {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/manager/log/showList")
	public String showList(
			@RequestParam(value="pageNoStr", required=false) String pageNoStr, 
			Map<String, Object> map) {
		
		TokenBinder.bindToke(TokenBinder.KEY_LOG);
		
		Page<Log> page = logService.readGetPage(pageNoStr);
		map.put(GlobalNames.PAGE, page);
		
		return "/manager/log_list";
	}

}
