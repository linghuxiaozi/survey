package com.atguigu.survey.component.dao.m;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.UserDao;
import com.atguigu.survey.entities.guest.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public boolean checkUserNameUnique(String userName) {
		
		String hql = "select count(*) from User u where u.userName=?";
		
		return getCountByHql(hql, userName) > 0;
	}

	@Override
	public User checkUserNamePwd(User user) {
		
		String hql = "From User u where u.userName=? and u.userPwd=?";
		
		return getEntityByHql(hql, user.getUserName(), user.getUserPwd());
	}

}
