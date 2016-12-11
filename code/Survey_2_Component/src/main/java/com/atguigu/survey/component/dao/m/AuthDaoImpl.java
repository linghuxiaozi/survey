package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.AuthDao;
import com.atguigu.survey.entities.manager.Auth;

@Repository
public class AuthDaoImpl extends BaseDaoImpl<Auth> implements AuthDao{

	@Override
	public List<Auth> getList() {
		return getListByHql("From Auth");
	}

	@Override
	public void batchDelete(List<Integer> authIdList) {
		int size = authIdList.size();
		String sql = "delete from survey_auth where auth_id=?";
		Object[][] params = new Object[size][1];
		for(int i = 0; i < size; i++) {
			params[i] = new Object[]{authIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	@Override
	public void updateAuth(Auth auth) {
		String hql = "update Auth a set a.authName=? where a.authId=?";
		updateByHql(hql, auth.getAuthName(), auth.getAuthId());
	}

	@Override
	public void removeOldRelationship(Integer authId) {
		String sql = "delete from inner_auth_res where auth_id=?";
		updateBySql(sql, authId);
	}

	@Override
	public void saveNewRelationship(Integer authId, List<Integer> resIdList) {
		String sql = "insert into inner_auth_res(auth_id,res_id) values(?,?)";
		Object[][] params = new Object[resIdList.size()][2];
		for(int i = 0; i < resIdList.size(); i++) {
			params[i] = new Object[]{authId, resIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

}
