package com.atguigu.survey.component.handler.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class StatisticsHandler {
	
	@Autowired
	private EngageService engageService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/manager/statistics/exportExcel/{surveyId}")
	public void exportExcel(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletResponse response) throws IOException {
		
		HSSFWorkbook workbook = statisticsService.readGenerateWorkBook(surveyId);
		
		//为了能够正常以文件下载的形式导出Excel文件，设置必要的响应消息头
		response.setContentType("application/vnd.ms-excel");
		String fileName = System.nanoTime() + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		
		ServletOutputStream out = response.getOutputStream();
		
		workbook.write(out);
		
	}
	
	@RequestMapping("/manager/statistics/showChartPicture/{questionId}")
	public void showChartPicture(
			@PathVariable("questionId") Integer questionId,
			HttpServletResponse response) throws IOException {
		
		//1.调用statisticsService方法生成JFreeChart对象
		JFreeChart chart = statisticsService.readGenerateChart(questionId);
		
		//2.通过response对象获取字节输出流对象
		ServletOutputStream out = response.getOutputStream();
		
		//3.将图表数据写入字节输出流
		ChartUtilities.writeChartAsJPEG(out, chart, 600, 300);
		
	}
	
	@RequestMapping("/manager/statistics/showTextAnswerList/{questionId}")
	public String showTextList(@PathVariable("questionId") Integer questionId, Map<String, Object> map) {
		
		List<String> textList = statisticsService.readGetTextList(questionId);
		map.put("textList", textList);
		
		return "/manager/statistics_textList";
	}
	
	@RequestMapping("/manager/statistics/showSummary/{surveyId}")
	public String showSummary(
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Survey survey = engageService.readGetEntityById(surveyId);
		map.put("survey", survey);
		
		return "/manager/statistics_summary";
	}
	
	@RequestMapping("/manager/statistics/showAllAvailableSurveys")
	public String showAllAvailableSurveys(
				@RequestParam(value="pageNoStr",required=false) String pageNoStr,
				Map<String, Object> map
			) {
		
		Page<Survey> page = engageService.readGetAllAvailable(pageNoStr);
		map.put(GlobalNames.PAGE, page);
		
		return "/manager/statistics_list";
	}

}
