package com.atguigu.survey.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class SurveyKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object target, Method method, Object... params) {
		
		String typeName = target.getClass().getName();
		String methodName = method.getName();
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(typeName).append(".").append(methodName).append(".");
		
		if(params != null && params.length > 0) {
			
			for(int i = 0; i < params.length; i++) {
				Object param = params[i];
				builder.append(param).append(".");
			}
			
		}
		
		int index = builder.lastIndexOf(".");
		
		String key = builder.substring(0, index);
		
		System.out.println(key);
		
		return key;
	}

}
