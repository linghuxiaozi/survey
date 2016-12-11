package com.atguigu.survey.component.dao.m;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.entities.guest.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao{
	
	public void updateEmpName(Integer empId, String empName) {
		String hql = "update Employee e set e.empName=? where e.empId=?";
		Session session = factory.getCurrentSession();
		session.createQuery(hql)
			   .setString(0, empName)
			   .setInteger(1, empId)
			   .executeUpdate();
	}

	@Override
	public List<Employee> getEmpList() {
		
		String hql = "From Employee";
		
		return getListByHql(hql);
	}

}
