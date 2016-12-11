package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.AnswerDao;
import com.atguigu.survey.entities.guest.Answer;

@Repository
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao{

	@Override
	public void batchSave(List<Answer> answerList) {
		
		int size = answerList.size();
		
		String sql = "INSERT INTO `survey_answer`("
				+ "`CONTENT`,"
				+ "`UUID`,"
				+ "`SURVEY_ID`,"
				+ "`QUESTION_ID`"
				+ ") VALUES(?,?,?,?)";
		
		Object[][] params = new Object[size][4];
		
		for (int i = 0; i < size; i++) {
			Answer answer = answerList.get(i);
			params[i] = new Object[]{answer.getContent(), answer.getUuid(), answer.getSurveyId(), answer.getQuestionId()};
		}
		
		batchUpdate(sql, params);
	}

	@Override
	public List<String> getTextList(Integer questionId) {
		
		String sql = "select content from survey_answer where question_id=? and content != '' and content is not null";
		
		return getListBySql(sql, questionId);
	}

	@Override
	public int getQuestionEngagedCount(Integer questionId) {
		
		String sql = "SELECT COUNT(*) FROM survey_answer WHERE question_id=?";
		
		return getCountBySql(sql, questionId);
	}

	@Override
	public int getOptionEngagedCount(Integer questionId, int index) {
		
		//这里拼好的字符串两边不需要加引号
		String contentParam = "%,"+index+",%";
		
		String sql = "SELECT COUNT(*) FROM survey_answer WHERE question_id=? AND CONCAT(\",\",content,\",\") LIKE ?";
		
		return getCountBySql(sql, questionId, contentParam);
	}

	@Override
	public int getSurveyEngagedCount(Integer surveyId) {
		String sql = "SELECT COUNT(DISTINCT `uuid`) FROM survey_answer WHERE survey_id=?";
		return getCountBySql(sql, surveyId);
	}

	@Override
	public List<Answer> getAnswerListBySurveyId(Integer surveyId) {
		
		String hql = "From Answer a where a.surveyId=?";
		
		return getListByHql(hql, surveyId);
	}

}
