package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.manager.Auth;

public interface AuthService extends BaseService<Auth>{

	List<Auth> readGetList();

	void writeBatchDelete(List<Integer> authIdList);

	void writeUpdateAuth(Auth auth);

	void writeDoDispatcher(List<Integer> resIdList, Integer authId);

	List<Auth> readGetAllAuthList();

}
