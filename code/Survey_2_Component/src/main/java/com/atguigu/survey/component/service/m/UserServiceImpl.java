package com.atguigu.survey.component.service.m;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.dao.i.RoleDao;
import com.atguigu.survey.component.dao.i.UserDao;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.e.UserLoginFailedException;
import com.atguigu.survey.e.UserNameAlreadyExistsException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ResDao resDao;
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public void writeRegist(User user) {
		
		//1.检查用户名是否被占用
		boolean exists = userDao.checkUserNameUnique(user.getUserName());
		
		//2.如果用户名被占用，注册失败
		if(exists) {
			throw new UserNameAlreadyExistsException("用户名已存在，请重新注册！");
		}
		
		//※加密
		String userPwd = user.getUserPwd();
		userPwd = DataprocessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		//※补充功能：计算用户的权限码数组值
		//[1]查询当前系统中最大的权限位数值
		Integer maxPos = resDao.getMaxPos();
		
		//[2]查询当前用户对应的Role
		boolean company = user.isCompany();
		Set<Role> roleSet = new HashSet<>();
		
		//[3]将User的Role保存到Set集合中
		if(company) {
			Role role = roleDao.getRoleByName("企业用户");
			roleSet.add(role);

		}else{
			Role role = roleDao.getRoleByName("个人用户");
			roleSet.add(role);
			
		}
		
		//[4]将Set<Role>设置到User对象中，目的：为了在保存User对象的时候能够同时在中间表保存User和Role的关联关系
		user.setRoleSet(roleSet);
		
		//[5]根据Set<Role>和maxPos计算权限码数组
		String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
		
		//[6]将权限码数组设置到User对象中
		user.setCodeArrStr(codeArrStr);
		
		//[7]保存User对象
		
		//3.如果用户名可用，则将User对象保存到数据库中
		userDao.saveEntity(user);
		
	}

	@Override
	public User readLogin(User user) {
		
		//1.加密：由于MD5加密算法不可逆，所以这里比较密文是否一致
		String userPwd = user.getUserPwd();
		String md5 = DataprocessUtils.md5(userPwd);
		user.setUserPwd(md5);
		
		//2.查询数据库具体执行验证
		User loginUser = userDao.checkUserNamePwd(user);
		
		//3.如果loginUser是null，则说明验证失败
		if(loginUser == null) {
			throw new UserLoginFailedException("用户名密码不匹配，请重新登录！");
		}
		
		return loginUser;
	}

}
