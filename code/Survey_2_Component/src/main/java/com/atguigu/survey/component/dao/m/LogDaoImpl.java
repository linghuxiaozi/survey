package com.atguigu.survey.component.dao.m;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.LogDao;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.utils.DataprocessUtils;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDao{

	@Override
	public void createTable(String tableName) {
		String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" LIKE survey160519.survey_log";
		updateBySql(sql);
	}

	@Override
	public void saveLog(Log log) {
		String tableName = DataprocessUtils.generateTableName(0);
		String sql = "INSERT INTO "+tableName+"("
				+ "`OPERATOR`,"
				+ "`OPERATE_TIME`,"
				+ "`METHOD_NAME`,"
				+ "`TYPE_NAME`,"
				+ "`PARAMETERS`,"
				+ "`RETURN_VALUE`,"
				+ "`EXCEPTION_TYPE`,"
				+ "`EXCEPTION_MESSAGE`) VALUES(?,?,?,?,?,?,?,?)";
		
		updateBySql(sql, log.getOperator(),
						log.getOperateTime(),
						log.getMethodName(),
						log.getTypeName(),
						log.getParameters(),
						log.getReturnValue(),
						log.getExceptionType(),
						log.getExceptionMessage());
		
	}

	@Override
	public List<String> getTableNames() {
		
		String sql = "SELECT TABLE_NAME "
				+ "FROM information_schema.TABLES "
				+ "WHERE TABLE_SCHEMA='survey160519_log' "
				+ "AND table_name LIKE 'survey_log_%'";
		
		return getListBySql(sql);
	}

	@Override
	public int getLogCount() {
		
		List<String> tableNames = getTableNames();
		
		String subQuery = DataprocessUtils.createSubQuery(tableNames);
		
		String sql = "select count(union_data.log_id) from ("+subQuery+") union_data";
		
		return getCountBySql(sql);
	}

	@Override
	public List<Log> getLogListLimited(int pageNo, int pageSize) {
		
		List<String> tableNames = getTableNames();
		
		String subQuery = DataprocessUtils.createSubQuery(tableNames);
		
		String sql = "select "
				+ "`LOG_ID`,"
				+ "`OPERATOR`,"
				+ "`OPERATE_TIME`,"
				+ "`METHOD_NAME`,"
				+ "`TYPE_NAME`,"
				+ "`PARAMETERS`,"
				+ "`RETURN_VALUE`,"
				+ "`EXCEPTION_TYPE`,"
				+ "`EXCEPTION_MESSAGE` from ("+subQuery+") union_data";
		
		List<Object[]> limitedList = getLimitedListBySql(sql, pageNo, pageSize);
		List<Log> logList = new ArrayList<>();
		
		for (Object[] objects : limitedList) {
			Integer logId = (Integer) objects[0];
			String operator = (String) objects[1];
			String operateTime = (String) objects[2];
			String methodName = (String) objects[3];
			String typeName = (String) objects[4];
			String parameters = (String) objects[5];
			String returnValue = (String) objects[6];
			String exceptionType = (String) objects[7];
			String exceptionMessage = (String) objects[8];
			
			Log log = new Log(logId, operator, operateTime, methodName, typeName, parameters, returnValue, exceptionType, exceptionMessage);
			logList.add(log);
		}
		
		return logList;
	}
	
}
