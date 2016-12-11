package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Bag;

public interface BagService extends BaseService<Bag>{

	void writeSaveBag(Bag bag);

	void writeDeeplyRemove(Integer bagId);

	List<Bag> readGetBagListBySurveyId(Integer surveyId);

	void writeBatchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList);

	void writeMoveToThisSurvey(Integer bagId, Integer surveyId);

	void writeCopyToThisSurvey(Integer bagId, Integer surveyId);

}
