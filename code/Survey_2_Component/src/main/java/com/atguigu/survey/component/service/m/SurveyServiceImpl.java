package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.SurveyDao;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.EmptyBagException;
import com.atguigu.survey.e.EmptySurveyException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;

@Service
public class SurveyServiceImpl extends BaseServiceImpl<Survey> implements SurveyService{
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Page<Survey> readGetMyUncompletedPage(String pageNoStr,
			Integer userId) {
		
		//1.查询数据库取得总记录数
		int totalRecordNo = surveyDao.getMyUncompletedCount(userId);
		
		//2.根据总记录数和pageNoStr创建Page对象
		Page<Survey> page = new Page<>(pageNoStr, totalRecordNo);
		
		//3.从Page对象中获取经过修正后正确的pageNo和pageSize
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		//4.根据pageNo和pageSize查询list
		List<Survey> list = surveyDao.getMyUncompletedList(userId, pageNo, pageSize);
		
		//5.将list给Page对象设置进去
		page.setList(list);
		
		return page;
	}

	@Override
	public void writeCompleteSurvey(Integer surveyId) {
		
		//1.检查当前调查是否是完整的
		Survey survey = surveyDao.getEntityById(surveyId);
		
		Set<Bag> bagSet = survey.getBagSet();
		if(bagSet == null || bagSet.size() == 0) {
			throw new EmptySurveyException("当前调查是空的，不能完成！");
		}
		
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			if(questionSet == null || questionSet.size() == 0) {
				throw new EmptyBagException("当前调查中存在空的包裹，不能完成！");
			}
		}
		
		//2.如果是完整的，则切换completed状态
		survey.setCompleted(true);
	}

	@Override
	public void writeDeeplyRemove(Integer surveyId) {
		//1.从数据库中查询得到Survey对象
		Survey survey = surveyDao.getEntityById(surveyId);
		/*Survey survey = new Survey();
		survey.setSurveyId(surveyId);*/
		
		//2.删除持久化对象
		surveyDao.removeEntity(survey);
	}

}
