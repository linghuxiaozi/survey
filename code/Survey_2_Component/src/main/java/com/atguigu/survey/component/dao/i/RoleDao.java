package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Role;

public interface RoleDao extends BaseDao<Role>{

	List<Role> getList();

	void batchDelete(List<Integer> roleIdList);

	void updateRole(Role role);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void saveNewRelationship(List<Integer> authIdList, Integer roleId);

	void removeOldRelationship(Integer roleId);

	Role getRoleByName(String roleName);

}
