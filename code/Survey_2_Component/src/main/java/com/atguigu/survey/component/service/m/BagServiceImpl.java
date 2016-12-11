package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.BagDao;
import com.atguigu.survey.component.dao.i.QuestionDao;
import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class BagServiceImpl extends BaseServiceImpl<Bag> implements BagService{
	
	@Autowired
	private BagDao bagDao;
	
	@Autowired
	private QuestionDao questionDao;

	@Override
	public void writeSaveBag(Bag bag) {
		//开启Session
		//开启事务
		
		bagDao.saveEntity(bag);
		
		Integer bagId = bag.getBagId();
		
		bag.setBagOrder(bagId);
		
		//提交事务
		//关闭Session
	}

	@Override
	public void writeDeeplyRemove(Integer bagId) {
		
		Bag bag = bagDao.getEntityById(bagId);
		
		bagDao.removeEntity(bag);
		
	}

	@Override
	public List<Bag> readGetBagListBySurveyId(Integer surveyId) {
		return bagDao.getBagListBySurveyId(surveyId);
	}

	@Override
	public void writeBatchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		bagDao.batchUpdateBagOrder(bagIdList, bagOrderList);
	}

	@Override
	public void writeMoveToThisSurvey(Integer bagId, Integer surveyId) {
		bagDao.moveToThisSurvey(bagId, surveyId);
	}

	@Override
	public void writeCopyToThisSurvey(Integer bagId, Integer surveyId) {
		
		//1.根据bagId查询Bag对象
		Bag bag = bagDao.getEntityById(bagId);
		
		//2.针对Bag对象进行深度复制
		Bag newBag = (Bag) DataprocessUtils.deeplyCopy(bag);
		
		//3.给Bag对象设置surveyId
		
		//空指针异常
		//newBag.getSurvey().setSurveyId(surveyId);
		
		/*Survey survey = new Survey();
		survey.setSurveyId(surveyId);
		newBag.setSurvey(survey);*/
		
		newBag.setSurvey(new Survey(surveyId));
		
		//4.将复制得到的Bag对象保存到数据库中
		bagDao.saveEntity(newBag);
		
		//5.将复制得到的Question集合保存到数据库中
		questionDao.batchSaveQuestions(newBag.getQuestionSet());
		
	}

}
