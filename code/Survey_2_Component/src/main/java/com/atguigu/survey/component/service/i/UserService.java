package com.atguigu.survey.component.service.i;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.User;

public interface UserService extends BaseService<User>{

	void writeRegist(User user);

	User readLogin(User user);

}
