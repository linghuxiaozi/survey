package junt.test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.guest.Employee;

public class IOCTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	private EmployeeService empService = ioc.getBean(EmployeeService.class);
	private EmployeeDao empDao = ioc.getBean(EmployeeDao.class);
	
	@Test
	public void createTable() {}
	
	@Test
	public void testBaseServiceUpdateEntity() {
		
		Employee t = new Employee(5, "KKK", 666);
		
		empService.writeUpdateEntity(t);
	}
	
	@Test
	public void testBaseDao14GetEntityById() {
		Employee employee = empDao.getEntityById(10);
		System.out.println(employee);
	}
	
	@Test
	public void testBaseDao13GetLimitedListBySql() {
		String sql = "select emp_name,emp_id,age from survey_employee";
		
		int pageNo = 2;
		int pageSize = 5;
		
		List<Object[]> list = empDao.getLimitedListBySql(sql, pageNo, pageSize);
		for (Object[] objects : list) {
			List<Object> asList = Arrays.asList(objects);
			System.out.println(asList);
		}
		
		String s = "From User u where　u.userName=?";
	}
	
	@Test
	public void testBaseDao12GetLimitedListByHql() {
		
		String hql = "From Employee";
		
		int pageNo = 2;
		int pageSize = 5;
		
		List<Employee> list = empDao.getLimitedListByHql(hql, pageNo, pageSize);
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	@Test
	public void testBaseDao11GetCountBySql() {
		String sql = "select count(*) from survey_employee";
		int count = empDao.getCountBySql(sql);
		System.out.println(count);
	}
	
	@Test
	public void testBaseDao10GetCountByHql() {
		String hql = "select count(*) from Employee";
		int count = empDao.getCountByHql(hql);
		System.out.println(count);
	}
	
	@Test
	public void testBaseDao09GetListBySql() {
		
		String sql = "select emp_id,emp_name,age from survey_employee where age>?";
		
		List<Object[]> list = empDao.getListBySql(sql, 940);
		for (Object[] objects : list) {
			List<Object> asList = Arrays.asList(objects);
			System.out.println(asList);
		}
	}
	
	@Test
	public void testBaseDao08GetListByHql() {
		String hql = "From Employee e where e.empName like ?";
		List<Employee> list = empDao.getListByHql(hql, "%emp0%");
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	@Test
	public void testBaseDao07GetEntityByHql() {
		
		String hql = "From Employee e where e.empName like ?";
		
		Employee employee = empDao.getEntityByHql(hql, "%emp%");
		System.out.println(employee);
	}
	
	@Test
	public void testBaseDao06BatchUpdate() {
		
		String sql = "INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?)";
		Object[][] params = new Object[100][2];
		
		for (int i = 0; i < params.length; i++) {
			Object [] param = new Object[2];
			param[0] = "empName"+i;
			param[1] = i*10;
			
			params[i] = param;
			
			//params[i] = new Object[]{"empName"+i,i*10};
		}
		
		empDao.batchUpdate(sql, params);
	}
	
	@Test
	public void testBaseDao05UpdateBySql() {
		String sql = "update survey_employee set emp_name=? where emp_id=?";
		empDao.updateBySql(sql, "AAA", 4);
	}
	
	@Test
	public void testBaseDao04UpdateByHql() {
		String hql = "update Employee e set e.empName=? where e.empId=?";
		empDao.updateByHql(hql, "Happy", 4);
	}
	
	@Test
	public void testBaseDao03UpdateEntity() {
		
		Employee t = new Employee(4, "GoodName", 333);
		
		empDao.updateEntity(t);
		
	}
	
	@Test
	public void testBaseDao02RemoveEntity() {
		empDao.removeEntity(3);
	}
	
	@Test
	public void testBaseDao01SaveEntity() {
		
		Employee t = new Employee(null, "emp05", 50);
		
		empDao.saveEntity(t);
		
		Integer empId = t.getEmpId();
		System.out.println(empId);
	}
	
	@Test
	public void testTransaction() throws FileNotFoundException {
		empService.writeUpdateEmpNameTwice();
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

}
