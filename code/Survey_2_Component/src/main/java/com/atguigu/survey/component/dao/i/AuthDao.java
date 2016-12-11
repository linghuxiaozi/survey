package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Auth;

public interface AuthDao extends BaseDao<Auth>{

	List<Auth> getList();

	void batchDelete(List<Integer> authIdList);

	void updateAuth(Auth auth);

	void removeOldRelationship(Integer authId);

	void saveNewRelationship(Integer authId, List<Integer> resIdList);

}
