package com.atguigu.survey.component.service.i;

import java.util.Map;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;

public interface EngageService extends BaseService<Survey>{

	Page<Survey> readGetAllAvailable(String pageNoStr);

	void writeParseAndSave(Map<Integer, Map<String, String[]>> allBagMap,
			Integer surveyId);

	Survey readGetSurveyById(Integer surveyId);

}
