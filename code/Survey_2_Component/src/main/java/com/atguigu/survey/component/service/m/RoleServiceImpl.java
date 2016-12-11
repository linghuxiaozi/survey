package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AdminDao;
import com.atguigu.survey.component.dao.i.RoleDao;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.manager.Role;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Role> readGetList() {
		return roleDao.getList();
	}

	@Override
	public void writeBatchDelete(List<Integer> roleIdList) {
		roleDao.batchDelete(roleIdList);
	}

	@Override
	public void writeUpdateRole(Role role) {
		roleDao.updateRole(role);
	}

	@Override
	public List<Integer> readGetCurrentAuthIdList(Integer roleId) {
		return roleDao.getCurrentAuthIdList(roleId);
	}

	@Override
	public void writeUpdateRelationship(Integer roleId, List<Integer> authIdList) {
		roleDao.removeOldRelationship(roleId);
		
		if(authIdList != null) {
			roleDao.saveNewRelationship(authIdList,roleId);
		}
		
		adminDao.reCalculateCode();
		
	}

}
