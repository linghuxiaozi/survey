package com.atguigu.survey.component.service.m;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.base.m.BaseServiceImpl;
import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.guest.Employee;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService{

	@Autowired
	private EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {
		System.out.println("EmployeeServiceImpl对象创建了！");
	}
	
	@Override
	public void writeUpdateEmpNameTwice() throws FileNotFoundException {
		employeeDao.updateEmpName(1, "Good");
		
		//System.out.println(10 / 0);
		new FileInputStream("abc.txt");
		
		employeeDao.updateEmpName(3, "Happy");
	}

	@Override
	public List<Employee> readGetEmpList() {
		return employeeDao.getEmpList();
	}
}
