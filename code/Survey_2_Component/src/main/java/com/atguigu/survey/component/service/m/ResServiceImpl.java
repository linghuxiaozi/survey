package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@Service
public class ResServiceImpl extends BaseServiceImpl<Res> implements ResService{
	
	@Autowired
	private ResDao resDao;

	@Override
	public boolean readCheckServletPath(String servletPath) {
		return resDao.checkServletPath(servletPath);
	}

	@Override
	public Integer readGetMaxPos() {
		return resDao.getMaxPos();
	}

	@Override
	public Integer readGetMaxCode(Integer maxPos) {
		return resDao.getMaxCode(maxPos);
	}

	@Override
	public List<Res> readGetList() {
		return resDao.getList();
	}

	@Override
	public void writeBatchDelete(List<Integer> resIdList) {
		resDao.batchDelete(resIdList);
	}

	@Override
	public boolean writeToggleStatus(Integer resId) {
		return resDao.toggleStatus(resId);
	}

	@Override
	public List<Integer> readGetCurrentResIdList(Integer authId) {
		return resDao.getCurrentResIdList(authId);
	}

	@Override
	public Res readGetResByServletPath(String servletPath) {
		return resDao.getResByServletPath(servletPath);
	}

}
