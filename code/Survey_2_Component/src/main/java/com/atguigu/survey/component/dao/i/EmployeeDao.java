package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.base.i.BaseDao;
import com.atguigu.survey.entities.guest.Employee;

public interface EmployeeDao extends BaseDao<Employee>{
	
	void updateEmpName(Integer empId, String empName);

	List<Employee> getEmpList();

}
