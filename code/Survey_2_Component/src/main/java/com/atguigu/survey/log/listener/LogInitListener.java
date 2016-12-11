package com.atguigu.survey.log.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.TokenBinder;

public class LogInitListener implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private LogService logService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//获取触发事件的IOC容器对象
		ApplicationContext ioc = event.getApplicationContext();
		
		//System.out.println("IOC容器启动了……，启动的容器是："+ioc);
		
		//获取当前容器的父容器
		ApplicationContext parent = ioc.getParent();
		//System.out.println("IOC容器启动，当前容器的父容器是："+parent);
		
		//如果父容器为null，则说明当前IOC容器是Spring的IOC容器
		if(parent == null) {
			
			//执行建表操作
			//System.out.println("IOC容器启动了……");
			
			//1.创建当前月的表
			TokenBinder.bindToke(TokenBinder.KEY_LOG);
			String tableName = DataprocessUtils.generateTableName(0);
			logService.writeCreateTable(tableName);
			
			//2.创建后三个月的表
			TokenBinder.bindToke(TokenBinder.KEY_LOG);
			tableName = DataprocessUtils.generateTableName(1);
			logService.writeCreateTable(tableName);
			
			TokenBinder.bindToke(TokenBinder.KEY_LOG);
			tableName = DataprocessUtils.generateTableName(2);
			logService.writeCreateTable(tableName);
			
			TokenBinder.bindToke(TokenBinder.KEY_LOG);
			tableName = DataprocessUtils.generateTableName(3);
			logService.writeCreateTable(tableName);
		}
		
	}

}
