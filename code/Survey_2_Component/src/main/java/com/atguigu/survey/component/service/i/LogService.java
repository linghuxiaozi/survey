package com.atguigu.survey.component.service.i;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.model.Page;

public interface LogService extends BaseService<Log>{

	void writeCreateTable(String tableName);

	void writeSaveLog(Log log);
	
	Page<Log> readGetPage(String pageNoStr);

}
