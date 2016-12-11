package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Bag;

public interface BagDao extends BaseDao<Bag>{

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagIdList, List<Integer> bagOrderList);

	void moveToThisSurvey(Integer bagId, Integer surveyId);

}
