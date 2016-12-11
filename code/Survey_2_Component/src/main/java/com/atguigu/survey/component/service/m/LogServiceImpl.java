package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.LogDao;
import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.model.Page;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService{
	
	@Autowired
	private LogDao logDao;

	@Override
	public void writeCreateTable(String tableName) {
		logDao.createTable(tableName);
	}

	@Override
	public void writeSaveLog(Log log) {
		logDao.saveLog(log);
	}

	@Override
	public Page<Log> readGetPage(String pageNoStr) {
		
		int totalRecordNo = logDao.getLogCount();
		
		Page<Log> page = new Page<>(pageNoStr, totalRecordNo);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		
		List<Log> list = logDao.getLogListLimited(pageNo, pageSize);
		
		page.setList(list);
		
		return page;
	}

}
