package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Role;

public interface RoleService extends BaseService<Role>{

	List<Role> readGetList();

	void writeBatchDelete(List<Integer> roleIdList);

	void writeUpdateRole(Role role);

	List<Integer> readGetCurrentAuthIdList(Integer roleId);

	void writeUpdateRelationship(Integer roleId, List<Integer> authIdList);

}
