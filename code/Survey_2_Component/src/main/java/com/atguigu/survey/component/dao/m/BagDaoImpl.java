package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.BagDao;
import com.atguigu.survey.entities.guest.Bag;

@Repository
public class BagDaoImpl extends BaseDaoImpl<Bag> implements BagDao{

	@Override
	public List<Bag> getBagListBySurveyId(Integer surveyId) {
		
		String hql = "From Bag b where b.survey.surveyId=?";
		
		return getListByHql(hql, surveyId);
	}

	@Override
	public void batchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		
		int bagSize = bagIdList.size();
		
		String sql = "UPDATE survey_bag SET bag_order=? WHERE bag_id=?";
		
		Object[][] params = new Object[bagSize][2];
		
		for (int i = 0; i < bagSize; i++) {
			params[i] = new Object[]{bagOrderList.get(i), bagIdList.get(i)};
		}
		
		batchUpdate(sql, params);
	}

	@Override
	public void moveToThisSurvey(Integer bagId, Integer surveyId) {
		String hql = "update Bag b set b.survey.surveyId=? where b.bagId=?";
		updateByHql(hql, surveyId, bagId);
	}

}
