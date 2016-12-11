package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Survey;

public interface SurveyDao extends BaseDao<Survey>{

	int getMyUncompletedCount(Integer userId);

	List<Survey> getMyUncompletedList(Integer userId, int pageNo, int pageSize);

	int getAllAvailableCount();

	List<Survey> getAllAvailableList(int pageNo, int pageSize);

}
