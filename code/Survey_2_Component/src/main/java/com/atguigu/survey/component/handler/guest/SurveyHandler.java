package com.atguigu.survey.component.handler.guest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.FileTooLargeException;
import com.atguigu.survey.e.FileTooLargeExceptionEdit;
import com.atguigu.survey.e.FileTypeInvalidException;
import com.atguigu.survey.e.FileTypeInvalidExceptionEdit;
import com.atguigu.survey.e.RemoveSurveyFailedException;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.TokenBinder;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class SurveyHandler {
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/survey/deeplyRemove/{surveyId}")
	public void deeplyRemove(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		surveyService.writeDeeplyRemove(surveyId);
		
		//1.从请求消息头中获取Refer值：点击超链接时所在页面的URL地址
		String referer = request.getHeader("Referer");
		
		//2.重定向到Refer值对应的URL地址
		response.sendRedirect(referer);
	}
	
	@RequestMapping("/guest/survey/complete/{surveyId}")
	public String complete(@PathVariable("surveyId") Integer surveyId) {
		
		surveyService.writeCompleteSurvey(surveyId);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/survey/toDesignUI/{surveyId}")
	public String toDesignUI(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Survey survey = surveyService.readGetEntityById(surveyId);
		map.put("survey", survey);
		
		return "/guest/survey_design";
	}
	
	@RequestMapping("/guest/survey/updateSurvey")
	public String updateSurvey(
			Survey survey, 
			@RequestParam("logoFile") MultipartFile logoFile,
			@RequestParam("pageNo") Integer pageNo,
			HttpSession session,
			HttpServletRequest request) throws IOException {
		
		if(!logoFile.isEmpty()) {
			
			//※文件验证
			//i.检查文件大小
			long size = logoFile.getSize();
			if(size > 100*1024) {
				//map.put("survey", survey);
				request.setAttribute("survey", survey);
				throw new FileTooLargeExceptionEdit("您上传的文件太大了！请不要超过100K");
			}
			
			//ii.检查文件类型
			String contentType = logoFile.getContentType();
			
			if(!GlobalNames.ALLOWED_TYPES.contains(contentType)) {
				//map.put("survey", survey);
				request.setAttribute("survey", survey);
				throw new FileTypeInvalidExceptionEdit("请上传图片！");
			}
			
			InputStream inputStream = logoFile.getInputStream();
			
			String virtualPath = "/surveyLogos";
			
			ServletContext servletContext = session.getServletContext();
			
			String realPath = servletContext.getRealPath(virtualPath);
			
			String logoPath = DataprocessUtils.resizeImages(inputStream, realPath);
			
			//这个设置的操作是在if块中，保证了用户上传文件时使用真实文件路径覆盖默认值，
			//如果没有上传文件则保持默认值
			survey.setLogoPath(logoPath);
			
		}
		
		surveyService.writeUpdateEntity(survey);
		
		return "redirect:/guest/survey/showMyUncompleted?pageNoStr="+pageNo;
	}
	
	@RequestMapping("/guest/survey/toEditUI/{surveyId}/{pageNo}")
	public String toEditUI(
			@PathVariable("pageNo") Integer pageNo,
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		//1.根据surveyId查询Survey对象
		Survey survey = surveyService.readGetEntityById(surveyId);
		
		//2.将Survey对象保存到请求域中
		map.put("survey", survey);
		map.put("pageNo", pageNo);
		
		return "/guest/survey_editUI";
	}
	
	@RequestMapping("/guest/survey/removeSurvey/{surveyId}")
	public void removeSurvey(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			//将具体数据源对应的键绑定到当前线程上
			TokenBinder.bindToke(TokenBinder.KEY_MAIN);
			
			surveyService.writeRemoveEntity(surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			
			//1.尝试获取异常的原因
			Throwable cause = e.getCause();
			
			//2.检查异常的原因是否存在
			if(cause != null) {
				
				//3.检查原因是否是com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
				if(cause instanceof MySQLIntegrityConstraintViolationException) {
					throw new RemoveSurveyFailedException("调查中有包裹，不能直接删除！");
				}
				
			}
			
		}
		
		//1.从请求消息头中获取Refer值：点击超链接时所在页面的URL地址
		String referer = request.getHeader("Referer");
		
		//2.重定向到Refer值对应的URL地址
		response.sendRedirect(referer);
		
	}
	
	/*@RequestMapping("/guest/survey/removeSurvey/{surveyId}/{pageNo}")
	public String removeSurvey(@PathVariable("surveyId") Integer surveyId, @PathVariable("pageNo") Integer pageNo) {
		
		surveyService.writeRemoveEntity(surveyId);
		
		return "redirect:/guest/survey/showMyUncompleted?pageNoStr="+pageNo;
	}*/
	
	@RequestMapping("/guest/survey/showMyUncompleted")
	public String showMyUncompleted(
			Map<String, Object> map,
			@RequestParam(value="pageNoStr", required=false) String pageNoStr,
			HttpSession session) {
		
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		Integer userId = user.getUserId();
		
		Page<Survey> page = surveyService.readGetMyUncompletedPage(pageNoStr, userId);
		map.put(GlobalNames.PAGE, page);
		
		return "/guest/survey_showMyUncompleted";
	}
	
	@RequestMapping("/guest/survey/saveSurvey")
	public String saveSurvey(
			Survey survey, 
			@RequestParam("logoFile") MultipartFile logoFile,
			HttpSession session) throws IOException {
		
		//1.如果上传了文件，则压缩图片文件，并为Survey对象设置logoPath
		//①判断上传的文件是否为空
		if(!logoFile.isEmpty()) {
			
			//※文件验证
			//i.检查文件大小
			long size = logoFile.getSize();
			if(size > 100*1024) {
				throw new FileTooLargeException("您上传的文件太大了！请不要超过100K");
			}
			
			//ii.检查文件类型
			String contentType = logoFile.getContentType();
			
			if(!GlobalNames.ALLOWED_TYPES.contains(contentType)) {
				throw new FileTypeInvalidException("请上传图片！");
			}
			
			//②获取上传文件对应的输入流对象
			InputStream inputStream = logoFile.getInputStream();
			
			//③声明surveyLogos目录的虚拟路径
			String virtualPath = "/surveyLogos";
			
			//④将虚拟路径转换为服务器端部署目录下真实的物理路径
			//[1]获取ServletContext对象
			ServletContext servletContext = session.getServletContext();
			//[2]执行转换
			String realPath = servletContext.getRealPath(virtualPath);
			
			//⑤执行压缩图片操作，以返回值作为logoPath的值
			String logoPath = DataprocessUtils.resizeImages(inputStream, realPath);
			
			//⑥为Survey对象的logoPath属性赋值
			//这个设置的操作是在if块中，保证了用户上传文件时使用真实文件路径覆盖默认值，
			//如果没有上传文件则保持默认值
			survey.setLogoPath(logoPath);
			
		}
		
		//2.不管有没有上传文件都需要设置User
		//①获取当前登录的User对象
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		
		//②给Survey对象设置User
		survey.setUser(user);
		
		//3.保存Survey对象到数据库中
		surveyService.writeSaveEntity(survey);
		
		return "redirect:/guest/survey/showMyUncompleted";
	}

}
