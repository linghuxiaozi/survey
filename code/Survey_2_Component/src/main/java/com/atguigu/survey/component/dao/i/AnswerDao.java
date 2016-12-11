package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Answer;

public interface AnswerDao extends BaseDao<Answer>{

	void batchSave(List<Answer> answerList);

	List<String> getTextList(Integer questionId);

	int getQuestionEngagedCount(Integer questionId);

	int getOptionEngagedCount(Integer questionId, int index);

	int getSurveyEngagedCount(Integer surveyId);

	List<Answer> getAnswerListBySurveyId(Integer surveyId);

}
