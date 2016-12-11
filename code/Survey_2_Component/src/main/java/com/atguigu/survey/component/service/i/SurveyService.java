package com.atguigu.survey.component.service.i;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;

public interface SurveyService extends BaseService<Survey>{

	Page<Survey> readGetMyUncompletedPage(String pageNoStr, Integer userId);

	void writeCompleteSurvey(Integer surveyId);

	void writeDeeplyRemove(Integer surveyId);

}
