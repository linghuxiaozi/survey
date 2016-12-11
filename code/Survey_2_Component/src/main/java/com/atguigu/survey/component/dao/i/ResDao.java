package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.manager.Res;

public interface ResDao extends BaseDao<Res>{

	boolean checkServletPath(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> getList();

	void batchDelete(List<Integer> resIdList);

	boolean toggleStatus(Integer resId);

	List<Integer> getCurrentResIdList(Integer authId);

	Res getResByServletPath(String servletPath);

}
