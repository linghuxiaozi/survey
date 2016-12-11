package com.atguigu.survey.component.dao.i;

import java.util.Set;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Question;

public interface QuestionDao extends BaseDao<Question>{

	void updateQuestion(Question question);

	void batchSaveQuestions(Set<Question> questionSet);

}
