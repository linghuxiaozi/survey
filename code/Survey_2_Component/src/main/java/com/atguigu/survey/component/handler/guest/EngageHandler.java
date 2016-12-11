package com.atguigu.survey.component.handler.guest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class EngageHandler {
	
	@Autowired
	private EngageService engageService;
	
	@RequestMapping("/guest/engage/engage")
	public String engage(
			@RequestParam("currentIndex") Integer currentIndex, 
			@RequestParam("bagId") Integer bagId,
			HttpServletRequest request,
			HttpSession session) {
		
		//一、合并答案
		//1.从Session域中获取allBagMap
		Map<Integer, Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//2.从request对象中获取parameterMap：其中包含的是当前请求中封装的实际请求参数
		Map<String,String[]> parameterMap = request.getParameterMap();
		
		//※注意：Tomcat提供的parameterMap，在处理不同请求时使用的是同一个对象，不能满足合并答案的需求
		//需要为每一个请求创建新的Map对象用来保存请求参数
		Map<String,String[]> realMap = new HashMap<>(parameterMap);
		
		//3.将parameterMap以bagId为键保存到allBagMap中，覆盖以前的值
		allBagMap.put(bagId, realMap);
		
		//二、包裹的导航
		//1.判断是否要执行包裹导航操作
		if(parameterMap.containsKey("submit_prev") || parameterMap.containsKey("submit_next")) {
			
			//2.为包裹导航做一些准备工作：从Session域中查找bagList
			List<Bag> bagList = (List<Bag>) session.getAttribute(GlobalNames.BAG_LIST);
			
			//3.根据旧的索引值计算新的索引值
			Integer newIndex = null;
			
			if(parameterMap.containsKey("submit_prev")) {
				newIndex = currentIndex - 1;
			}
			
			if(parameterMap.containsKey("submit_next")) {
				newIndex = currentIndex + 1;
			}
			
			//4.从bagList中根据newIndex获取Bag对象
			Bag bag = bagList.get(newIndex);
			
			//5.将bag对象保存到request域中
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//5.将计算得到的newIndex保存到request域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, newIndex);
			
			//6.前往engage_engage.jsp
			return "/guest/engage_engage";
		}
		
		//三、如果当前点击的是“完成”按钮，则解析并保存答案
		if(parameterMap.containsKey("submit_done")) {
			
			Survey survey = (Survey) session.getAttribute(GlobalNames.CURRENT_SURVEY);
			Integer surveyId = survey.getSurveyId();
			
			engageService.writeParseAndSave(allBagMap, surveyId);
			
		}
		
		//四、清理Session并返回首页
		session.removeAttribute(GlobalNames.ALL_BAG_MAP);
		session.removeAttribute(GlobalNames.BAG_LIST);
		session.removeAttribute(GlobalNames.CURRENT_SURVEY);
		session.removeAttribute(GlobalNames.LAST_INDEX);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/engage/entry/{surveyId}")
	public String entry(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request,
			HttpSession session) {
		
		//①为整个参与调查操作服务的：在切换各个包裹时不变
		//		[1]根据surveyId查询Survey对象
		//Survey survey = engageService.readGetEntityById(surveyId);
		Survey survey = engageService.readGetSurveyById(surveyId);
		
		//		[2]将Survey对象保存到Session域中
		session.setAttribute(GlobalNames.CURRENT_SURVEY, survey);
		
		//		[3]从Survey对象中获取Set<Bag>
		Set<Bag> bagSet = survey.getBagSet();
		
		//		[4]将Set<Bag>转换为List<Bag>
		List<Bag> bagList = new ArrayList<>(bagSet);
		
		//		[5]将List<Bag>保存到Session域中
		session.setAttribute(GlobalNames.BAG_LIST, bagList);
		
		//		[6]创建allBagMap对象
		Map<Integer, Map<String, String[]>> allBagMap = new HashMap<>();
		
		//		[7]将allBagMap对象保存到Session域中
		session.setAttribute(GlobalNames.ALL_BAG_MAP, allBagMap);
		
		//		[8]计算List<Bag>集合最后一个元素的索引值
		int lastIndex = bagList.size()-1;
		
		//		[9]将最后一个元素的索引值保存到Session域中
		session.setAttribute(GlobalNames.LAST_INDEX, lastIndex);
		
		//②为显示第一个包裹服务的：第一个包裹专属的
		//		[1]获取第一个包裹对应的Bag对象
		Bag bag = bagList.get(0);
		
		//		[2]将第一个包裹的Bag对象保存到request域中
		request.setAttribute(GlobalNames.CURRENT_BAG, bag);
		
		//		[3]将第一个包裹的索引值0保存到request域中
		request.setAttribute(GlobalNames.CURRENT_INDEX, 0);
		
		return "/guest/engage_engage";
	}
	
	@RequestMapping("/guest/engage/showAllAvailableSurveys")
	public String showAllAvailable(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			Map<String, Object> map
			) {
		
		Page<Survey> page = engageService.readGetAllAvailable(pageNoStr);
		
		map.put(GlobalNames.PAGE, page);
		
		return "/guest/engage_list";
	}

}
