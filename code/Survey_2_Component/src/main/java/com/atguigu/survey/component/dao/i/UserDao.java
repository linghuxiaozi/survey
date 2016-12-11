package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.User;

public interface UserDao extends BaseDao<User>{

	boolean checkUserNameUnique(String userName);

	User checkUserNamePwd(User user);

}
