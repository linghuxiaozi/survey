package com.atguigu.survey.component.service.i;

import java.io.FileNotFoundException;
import java.util.List;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Employee;

public interface EmployeeService extends BaseService<Employee> {
	
	void writeUpdateEmpNameTwice() throws FileNotFoundException;

	List<Employee> readGetEmpList();

}
