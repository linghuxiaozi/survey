package com.atguigu.survey.component.handler.guest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.BagOrderDuplicateException;
import com.atguigu.survey.e.RemoveBagFailedException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class BagHandler {
	
	@Autowired
	private BagService bagService;
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/bag/copyToThisSurvey/{bagId}/{surveyId}")
	public String copyToThisSurvey(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.writeCopyToThisSurvey(bagId, surveyId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/moveToThisSurvey/{bagId}/{surveyId}")
	public String moveToThisSurvey(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.writeMoveToThisSurvey(bagId, surveyId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/showTargetSurveys/{bagId}/{surveyId}")
	public String showTargetSurveys(
			@RequestParam(value="pageNoStr", required=false) String pageNoStr,
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map,
			HttpSession session) {
		
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		Integer userId = user.getUserId();
		
		Page<Survey> page = surveyService.readGetMyUncompletedPage(pageNoStr, userId);
		map.put(GlobalNames.PAGE, page);
		map.put("bagId", bagId);
		map.put("surveyId", surveyId);
		
		return "/guest/bag_moveOrCopyPage";
	}
	
	@RequestMapping("/guest/bag/adjustOrder")
	public String adjustOrder(
			@RequestParam("bagIdList") List<Integer> bagIdList,
			@RequestParam("bagOrderList") List<Integer> bagOrderList,
			@RequestParam("surveyId") Integer surveyId,
			HttpServletRequest request) {
		
		boolean result = DataprocessUtils.checkListDuplicate(bagOrderList);
		
		if(!result) {
			List<Bag> bagList = bagService.readGetBagListBySurveyId(surveyId);
			request.setAttribute("bagList", bagList);
			throw new BagOrderDuplicateException("包裹的序号不要重复！");
		}
		
		bagService.writeBatchUpdateBagOrder(bagIdList,bagOrderList);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toAdjustUI/{surveyId}")
	public String toAdjustUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		List<Bag> bagList = bagService.readGetBagListBySurveyId(surveyId);
		map.put("bagList", bagList);
		map.put("surveyId", surveyId);
		
		return "/guest/bag_adjustUI";
	}
	
	@RequestMapping("/guest/bag/deeplyRemvoe/{bagId}/{surveyId}")
	public String deeplyRemove(@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		bagService.writeDeeplyRemove(bagId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/updateBag")
	public String updateBag(Bag bag) {
		
		bagService.writeUpdateEntity(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	@RequestMapping("/guest/bag/toEditUI/{bagId}")
	public String toEditUI(
			@PathVariable("bagId") Integer bagId,
			Map<String, Object> map) {
		
		Bag bag = bagService.readGetEntityById(bagId);
		map.put("bag", bag);
		
		return "/guest/bag_editUI";
	}
	
	@RequestMapping("/guest/bag/removeBag/{bagId}/{surveyId}")
	public String removeBag(
			@PathVariable("bagId") Integer bagId,
			@PathVariable("surveyId") Integer surveyId) {
		
		try {
			bagService.writeRemoveEntity(bagId);
		} catch (Exception e) {
			e.printStackTrace();
			
			//1.尝试获取异常的原因
			Throwable cause = e.getCause();
			
			//2.检查原因是否存在
			if(cause != null) {
				//3.检查是否是由于外键约束失败导致的
				if(cause instanceof MySQLIntegrityConstraintViolationException) {
					throw new RemoveBagFailedException("包裹中已经创建了问题，不能直接删除！");
				}
			}
			
		}
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/saveBag")
	public String saveBag(Bag bag) {
		
		bagService.writeSaveBag(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	/*public String saveBag(Bag bag) {		//此时是临时状态
		
		bagService.writeSaveEntity(bag);	//保存之后事务提交之前是持久化状态
		
		Integer bagId = bag.getBagId();		//事务提交之后Session关闭，变成游离状态
		System.out.println("bagId="+bagId);
		
		bag.setBagOrder(bagId);
		
		return "redirect:/index.jsp";
	}*/
	
	@RequestMapping("/guest/bag/toAddUI/{surveyId}")
	public String toAddUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		map.put("surveyId", surveyId);
		
		return "/guest/bag_addUI";
	}

}
