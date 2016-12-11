package com.atguigu.survey.component.dao.m;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.AdminDao;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataprocessUtils;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{

	@Override
	public Admin getAdminForLogin(Admin admin) {
		
		String hql = "From Admin a where a.adminName=? and a.adminPwd=?";
		
		return getEntityByHql(hql, admin.getAdminName(), admin.getAdminPwd());
	}

	@Override
	public boolean checkAdminExists(String adminName) {
		String hql = "select count(*) from Admin a where a.adminName=?";
		return getCountByHql(hql, adminName) > 0;
	}

	@Override
	public List<Admin> getList() {
		return getListByHql("From Admin");
	}

	@Override
	public List<Integer> getCurrentRoleIdList(Integer adminId) {
		
		String sql = "select role_id from inner_admin_role where admin_id=?";
		
		return getListBySql(sql, adminId);
	}

	@Override
	public void removeOldRelationship(Integer adminId) {
		String sql = "delete from inner_admin_role where admin_id=?";
		updateBySql(sql, adminId);
	}

	@Override
	public void saveNewRelationship(Integer adminId, List<Integer> roleIdList) {
		String sql = "insert into inner_admin_role(admin_id, role_id) values(?,?)";
		Object[][] params = new Object[roleIdList.size()][2];
		for (int i = 0; i < roleIdList.size(); i++) {
			params[i] = new Object[]{adminId, roleIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	@Override
	public void reCalculateCode() {
		
		//查询maxPos
		String hql = "select max(r.resPos) from Res r";
		Integer maxPos = (Integer) getQuery(hql).uniqueResult();
		
		//一、重新计算所有Admin的权限码数组
		//1.查询系统中所有的Admin
		List<Admin> adminList = getList();
		
		//2.遍历Admin集合
		for (Admin admin : adminList) {
			//3.针对每一个Admin分别计算权限码数组
			Set<Role> roleSet = admin.getRoleSet();
			String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
			admin.setCodeArrStr(codeArrStr);
		}
		
		//二、重新计算所有User的权限码数组
		//1.查询系统中所有的User
		List<User> userList = getUserList();
		
		//2.遍历User集合
		for (User user : userList) {
			//3.针对每一个User分别计算权限码数组
			Set<Role> roleSet = user.getRoleSet();
			String codeArrStr = DataprocessUtils.calculateCodeArr(roleSet, maxPos);
			user.setCodeArrStr(codeArrStr);
		}
		
	}

	public List<User> getUserList() {
		
		String hql = "From User";
		
		return getQuery(hql).list();
	}

}
