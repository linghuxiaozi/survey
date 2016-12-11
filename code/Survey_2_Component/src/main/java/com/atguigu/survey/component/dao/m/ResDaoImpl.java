package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.entities.manager.Res;

@Repository
public class ResDaoImpl extends BaseDaoImpl<Res> implements ResDao{

	@Override
	public boolean checkServletPath(String servletPath) {
		
		String hql = "select count(*) from Res s where s.servletPath=?";
		
		return getCountByHql(hql, servletPath) > 0;
	}

	@Override
	public Integer getMaxPos() {
		
		String hql = "select max(r.resPos) from Res r";
		
		return (Integer) getQuery(hql).uniqueResult();
	}

	@Override
	public Integer getMaxCode(Integer maxPos) {
		
		String hql = "select max(r.resCode) from Res r where r.resPos=?";
		
		return (Integer) getQuery(hql, maxPos).uniqueResult();
	}

	@Override
	public List<Res> getList() {
		
		String hql = "From Res s order by s.servletPath";
		
		return getListByHql(hql);
	}

	@Override
	public void batchDelete(List<Integer> resIdList) {
		String sql = "delete from survey_res where res_id=?";
		Object[][] params = new Object[resIdList.size()][1];
		for (int i = 0; i < resIdList.size(); i++) {
			params[i] = new Object[]{resIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	@Override
	public boolean toggleStatus(Integer resId) {
		
		Res res = getEntityById(resId);
		boolean publicRes = !res.isPublicRes();
		res.setPublicRes(publicRes);
		
		return res.isPublicRes();
	}

	@Override
	public List<Integer> getCurrentResIdList(Integer authId) {
		String sql = "select res_id from inner_auth_res where auth_id=?";
		return getListBySql(sql, authId);
	}

	@Override
	public Res getResByServletPath(String servletPath) {
		String hql = "From Res r where r.servletPath=?";
		return getEntityByHql(hql, servletPath);
	}

}
