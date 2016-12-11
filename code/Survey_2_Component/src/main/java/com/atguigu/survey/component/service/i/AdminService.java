package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Admin;

public interface AdminService extends BaseService<Admin>{

	Admin readLogin(Admin admin);

	void writeSaveAdmin(Admin admin);

	List<Admin> readGetList();

	List<Integer> readGetCurrentRoleIdList(Integer adminId);

	void writeUpdateRelationship(Integer adminId, List<Integer> roleIdList);

}
