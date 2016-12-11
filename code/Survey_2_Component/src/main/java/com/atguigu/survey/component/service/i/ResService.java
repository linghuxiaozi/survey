package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Res;

public interface ResService extends BaseService<Res>{

	boolean readCheckServletPath(String servletPath);

	Integer readGetMaxPos();

	Integer readGetMaxCode(Integer maxPos);

	List<Res> readGetList();

	void writeBatchDelete(List<Integer> resIdList);

	boolean writeToggleStatus(Integer resId);

	List<Integer> readGetCurrentResIdList(Integer authId);

	Res readGetResByServletPath(String servletPath);

}
