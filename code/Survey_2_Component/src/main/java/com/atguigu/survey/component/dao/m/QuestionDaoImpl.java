package com.atguigu.survey.component.dao.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.entities.guest.Question;

@Repository
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements QuestionDao{

	@Override
	public void updateQuestion(Question question) {
		String hql = "update Question q set q.questionName=?,q.questionType=?,q.options=? where q.questionId=?";
		updateByHql(hql, question.getQuestionName(), question.getQuestionType(), question.getOptions(), question.getQuestionId());
	}

	@Override
	public void batchSaveQuestions(Set<Question> questionSet) {
		
		List<Question> questionList = new ArrayList<>(questionSet);
		
		int size = questionList.size();
		
		String sql = "INSERT INTO `survey_question`(`QUESTION_NAME`,`QUESTION_TYPE`,`OPTIONS`,`BAG_ID`) VALUES(?,?,?,?)";
		
		Object[][] params = new Object[size][4];
		
		for (int i = 0; i < size; i++) {
			Question question = questionList.get(i);
			params[i] = new Object[]{
					question.getQuestionName(),
					question.getQuestionType(),
					question.getOptions(),
					question.getBag().getBagId()};
		}
		
		batchUpdate(sql, params);
		
	}

}
