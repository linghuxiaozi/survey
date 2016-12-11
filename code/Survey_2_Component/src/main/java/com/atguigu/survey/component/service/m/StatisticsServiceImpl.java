package com.atguigu.survey.component.service.m;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;

@Service
public class StatisticsServiceImpl extends BaseServiceImpl<Survey> implements StatisticsService{

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private SurveyDao surveyDao;
	
	@Override
	public List<String> readGetTextList(Integer questionId) {
		
		return answerDao.getTextList(questionId);
	}

	@Override
	public JFreeChart readGenerateChart(Integer questionId) {
		
		//1.查询Question对象
		Question question = questionDao.getEntityById(questionId);
		
		//2.查询问题被参与的次数
		int questionEngagedCount = answerDao.getQuestionEngagedCount(questionId);
		
		//3.获取选项数组
		String[] optionArr = question.getOptionArr();
		
		//4.遍历选项数组
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for (int i = 0; i < optionArr.length; i++) {
			
			int index = i;
			String option = optionArr[i];
			
			//5.在遍历过程中，根据index查询当前选项被选中的次数
			int optionEngagedCount = answerDao.getOptionEngagedCount(questionId, index);
			
			//6.将遍历和查询得到的数据保存到dataset对象中
			dataset.setValue(option, optionEngagedCount);
			
		}
		//7.根据dataset对象创建JFreeChart对象
		String title = question.getQuestionName() + "" + questionEngagedCount + "次参与";
		
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset);

		//8.对JFreeChart对象进行必要的修饰
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 30));
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 20));
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0},{1}/{3},{2}"));
		plot.setForegroundAlpha(0.6f);
		
		return chart;
	}

	@Override
	public HSSFWorkbook readGenerateWorkBook(Integer surveyId) {
		
		//1.查询所需要的数据
		//①Survey对象
		Survey survey = surveyDao.getEntityById(surveyId);
		
		//②调查被参与的次数
		int surveyEngagedCount = answerDao.getSurveyEngagedCount(surveyId);
		
		//③创建List<Question>
		List<Question> questionList = new ArrayList<>();
		Set<Bag> bagSet = survey.getBagSet();
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			questionList.addAll(questionSet);
		}
		
		//④查询List<Answer>
		List<Answer> answerList = answerDao.getAnswerListBySurveyId(surveyId);
		
		//2.转换数据结构
		//<UUID, Map<questionId, content>>
		Map<String, Map<Integer, String>> bigMap = new HashMap<>();
		
		//遍历answerList生成bigMap
		for (Answer answer : answerList) {
			String uuid = answer.getUuid();
			Integer questionId = answer.getQuestionId();
			String content = answer.getContent();
			
			//尝试从bigMap中获取smallMap
			Map<Integer, String> smallMap = bigMap.get(uuid);
			
			if(smallMap == null) {
				smallMap = new HashMap<>();
				bigMap.put(uuid, smallMap);
			}
			
			smallMap.put(questionId, content);
			
		}
		
		//3.生成workbook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		String title = survey.getSurveyName() + "【"+surveyEngagedCount+"次参与】";
		
		HSSFSheet sheet = workbook.createSheet(title);
		
		//生成第一行
		HSSFRow rowFirst = sheet.createRow(0);
		
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			String questionName = question.getQuestionName();
			
			HSSFCell cell = rowFirst.createCell(i);
			
			cell.setCellValue(questionName);
		}
		
		//生成后面的每一行
		//生成workbook的时候不需要uuid的值，所以取Map的values
		Collection<Map<Integer, String>> smallMapCollection = bigMap.values();
		
		//遍历小Map的集合时需要用到循环变量作为单元格的索引值
		List<Map<Integer, String>> smallMapList = new ArrayList<>(smallMapCollection);
		
		for(int i = 0; i < smallMapList.size(); i++) {
			Map<Integer, String> smallMap = smallMapList.get(i);
			
			//此时索引是从1开始的，因为0已经被占用了
			HSSFRow rowEvery = sheet.createRow(i+1);
			
			for(int j = 0; j < questionList.size(); j++) {
				
				//j既是循环变量，又用来从questionList中获取对应Question对象
				HSSFCell cell = rowEvery.createCell(j);
				
				Question question = questionList.get(j);
				
				Integer questionId = question.getQuestionId();
				
				//根据questionId从小Map中获取当前问题对应的答案内容
				String content = smallMap.get(questionId);
				
				cell.setCellValue(content);
			}
			
		}
		
		return workbook;
	}
	
	

}
