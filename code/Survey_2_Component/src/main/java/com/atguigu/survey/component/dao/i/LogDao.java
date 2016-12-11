package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Log;

public interface LogDao extends BaseDao<Log>{

	void createTable(String tableName);

	void saveLog(Log log);
	
	List<String> getTableNames();
	
	int getLogCount();
	
	List<Log> getLogListLimited(int pageNo, int pageSize);

}
