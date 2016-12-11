package com.atguigu.survey.log.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.atguigu.survey.utils.TokenBinder;

public class SurveyRoutingDatasource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		
		//从当前线程上获取事先绑定的键
		String token = TokenBinder.getToken();
		
		//从当前线程上移除
		TokenBinder.removeToken();
		
		return token;
	}

}
