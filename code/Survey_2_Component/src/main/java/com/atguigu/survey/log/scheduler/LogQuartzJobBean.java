package com.atguigu.survey.log.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.utils.DataprocessUtils;
import com.atguigu.survey.utils.TokenBinder;

public class LogQuartzJobBean extends QuartzJobBean{
	
	private LogService logService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		System.out.println("===============石英任务=================");
		
		//执行建表操作
		TokenBinder.bindToke(TokenBinder.KEY_LOG);
		String tableName = DataprocessUtils.generateTableName(1);
		logService.writeCreateTable(tableName);
		
		TokenBinder.bindToke(TokenBinder.KEY_LOG);
		tableName = DataprocessUtils.generateTableName(2);
		logService.writeCreateTable(tableName);
		
		TokenBinder.bindToke(TokenBinder.KEY_LOG);
		tableName = DataprocessUtils.generateTableName(3);
		logService.writeCreateTable(tableName);

		System.out.println("======================================");
	}
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
