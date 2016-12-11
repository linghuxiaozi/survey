package com.atguigu.survey.base.m;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.base.i.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{
	
	@Autowired
	private BaseDao<T> baseDao;

	@Override
	public T readGetEntityById(Integer entityId) {
		return (T) baseDao.getEntityById(entityId);
	}

	@Override
	public void writeUpdateEntity(T t) {
		baseDao.updateEntity(t);
	}

	@Override
	public void writeRemoveEntity(Integer entityId) {
		baseDao.removeEntity(entityId);
	}

	@Override
	public void writeSaveEntity(T t) {
		baseDao.saveEntity(t);
	}

	

}
