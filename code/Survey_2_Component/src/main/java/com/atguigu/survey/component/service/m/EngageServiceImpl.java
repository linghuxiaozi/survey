package com.atguigu.survey.component.service.m;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class EngageServiceImpl extends BaseServiceImpl<Survey> implements EngageService{
	
	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private AnswerDao answerDao;

	@Override
	public Page<Survey> readGetAllAvailable(String pageNoStr) {
		
		int totalRecordNo = surveyDao.getAllAvailableCount();
		
		Page<Survey> page = new Page<>(pageNoStr, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		List<Survey> list = surveyDao.getAllAvailableList(pageNo,pageSize);
		
		page.setList(list);
		
		return page;
	}

	@Override
	public void writeParseAndSave(
			Map<Integer, Map<String, String[]>> allBagMap, Integer surveyId) {
		
		String uuid = UUID.randomUUID().toString();
		
		//一、解析
		List<Answer> answerList = new ArrayList<>();
		
		//1.解析答案时不关心某个答案属于哪一个包裹，所以allBagMap中的键就没用了
		Collection<Map<String, String[]>> values = allBagMap.values();
		
		//2.遍历values集合
		for (Map<String, String[]> paramMap : values) {
			
			//3.每一个paramMap都封装了一组请求参数
			Set<Entry<String, String[]>> entrySet = paramMap.entrySet();
			for (Entry<String, String[]> entry : entrySet) {
				String paramName = entry.getKey();
				
				if(!paramName.startsWith("q")) {
					continue;
				}
				
				Integer questionId = DataprocessUtils.parseQuestionId(paramName);
				
				String[] paramValues = entry.getValue();
				
				String content = DataprocessUtils.convertParamValues(paramValues);
				
				Answer answer = new Answer(null, content, uuid, surveyId, questionId);
				answerList.add(answer);
			}
			
		}
		
		for (Answer answer : answerList) {
			System.out.println(answer);
		}
		
		//二、保存
		answerDao.batchSave(answerList);
		
	}

	@Override
	public Survey readGetSurveyById(Integer surveyId) {
		return surveyDao.getEntityById(surveyId);
	}

}
