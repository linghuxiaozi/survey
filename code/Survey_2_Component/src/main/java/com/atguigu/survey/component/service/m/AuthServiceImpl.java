package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AdminDao;
import com.atguigu.survey.component.dao.i.AuthDao;
import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.entities.manager.Auth;

@Service
public class AuthServiceImpl extends BaseServiceImpl<Auth> implements AuthService{
	
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Auth> readGetList() {
		return authDao.getList();
	}

	@Override
	public void writeBatchDelete(List<Integer> authIdList) {
		authDao.batchDelete(authIdList);
	}

	@Override
	public void writeUpdateAuth(Auth auth) {
		authDao.updateAuth(auth);
	}

	@Override
	public void writeDoDispatcher(List<Integer> resIdList, Integer authId) {
		
		authDao.removeOldRelationship(authId);
		
		if(resIdList != null) {
			authDao.saveNewRelationship(authId, resIdList);
		}
		
		adminDao.reCalculateCode();
		
	}

	@Override
	public List<Auth> readGetAllAuthList() {
		return authDao.getList();
	}

}
