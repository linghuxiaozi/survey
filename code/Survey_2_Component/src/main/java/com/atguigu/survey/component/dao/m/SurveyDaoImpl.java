package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.entities.guest.Survey;

@Repository
public class SurveyDaoImpl extends BaseDaoImpl<Survey> implements SurveyDao{

	@Override
	public int getMyUncompletedCount(Integer userId) {
		
		String hql = "select count(*) from Survey s where s.completed=false and s.user.userId=?";
		
		return getCountByHql(hql, userId);
	}

	@Override
	public List<Survey> getMyUncompletedList(Integer userId, int pageNo,
			int pageSize) {
		
		String hql = "From Survey s where s.completed=false and s.user.userId=? order by s.surveyId desc";
		
		return getLimitedListByHql(hql, pageNo, pageSize, userId);
	}

	@Override
	public int getAllAvailableCount() {
		
		String hql = "select count(*) from Survey s where s.completed=true";
		
		return getCountByHql(hql);
	}

	@Override
	public List<Survey> getAllAvailableList(int pageNo, int pageSize) {
		
		String hql = "From Survey s where s.completed=true";
		
		return getLimitedListByHql(hql, pageNo, pageSize);
	}

}
