package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.AdminDao;
import com.atguigu.survey.component.dao.i.ResDao;
import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.e.AdminLoginFailedException;
import com.atguigu.survey.e.AdminNameAlreadyExistsException;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataprocessUtils;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResDao resDao;

	@Override
	public Admin readLogin(Admin admin) {
		
		String adminPwd = admin.getAdminPwd();
		String md5 = DataprocessUtils.md5(adminPwd);
		admin.setAdminPwd(md5);
		
		Admin loginAdmin = adminDao.getAdminForLogin(admin);
		
		if(loginAdmin == null) {
			throw new AdminLoginFailedException("账号密码不正确，请重新登录！");
		}
		
		return loginAdmin;
	}

	@Override
	public void writeSaveAdmin(Admin admin) {
		boolean exists = adminDao.checkAdminExists(admin.getAdminName());
		if(exists) {
			throw new AdminNameAlreadyExistsException("账号名称已经存在，请重新注册！");
		}
		admin.setAdminPwd(DataprocessUtils.md5(admin.getAdminPwd()));
		adminDao.saveEntity(admin);
	}

	@Override
	public List<Admin> readGetList() {
		return adminDao.getList();
	}

	@Override
	public List<Integer> readGetCurrentRoleIdList(Integer adminId) {
		return adminDao.getCurrentRoleIdList(adminId);
	}

	@Override
	public void writeUpdateRelationship(Integer adminId,
			List<Integer> roleIdList) {
		
		//1.删除旧的关联关系
		adminDao.removeOldRelationship(adminId);
		
		//2.保存新的关联关系
		if(roleIdList != null) {
			adminDao.saveNewRelationship(adminId, roleIdList);
		}
		
		//3.根据Admin和Role的关系为Admin计算权限码数组
		Integer maxPos = resDao.getMaxPos();
		
		//①查询Admin对象
		Admin admin = adminDao.getEntityById(adminId);
		
		//②从Admin对象中获取Role的集合
		Set<Role> roleSet = admin.getRoleSet();
		
		//③计算权限码数组
		String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
		
		//④将权限码数组字符串设置到Admin对象中
		admin.setCodeArrStr(codeArrStr);
		
	}

}
