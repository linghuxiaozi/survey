package junt.test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.atguigu.survey.entities.guest.Employee;
import com.atguigu.survey.utils.DataprocessUtils;

public class NormalTest {
	
	@Test
	public void testSubQuery() {
		List<String> tableNameList = Arrays.asList("a","b","c");
		String subQuery = DataprocessUtils.createSubQuery(tableNameList);
		
		System.out.println(subQuery);
	}
	
	@Test
	public void testGenerateTableName() {
		for(int i = -50; i < 50; i++) {
			String tableName = DataprocessUtils.generateTableName(i);
			System.out.println("i="+i+"\t"+tableName);
		}
	}
	
	@Test
	public void testMove222() {
		int i = 1;
		System.out.println(i<<30);
		System.out.println(i<<31);
		System.out.println(i<<32);
	}
	
	@Test
	public void testCut() {
		String s = "/guest/survey/toEditUI/param/2/3/abc/5/6";
		String string = DataprocessUtils.cutServletPath(s);
		System.out.println(string);
	}
	
	@Test
	public void testRemoveComma() {
		String options = ",1,2,3";
		
		int lastIndexOf = options.lastIndexOf(",");
		System.out.println("lastIndexOf="+lastIndexOf);
		
		options = options.substring(1, options.lastIndexOf(","));
		System.out.println(options);
	}
	
	@Test
	public void testDeeplyCopy() {
		Employee employee = new Employee(234, "tom1993", 50);
		Serializable target = DataprocessUtils.deeplyCopy(employee);
		
		System.out.println("源对象内的数据："+employee);
		System.out.println("源对象的hashCode值："+employee.hashCode());
		
		System.out.println("目标对象内的数据："+target);
		System.out.println("目标对象的hashCode值："+target.hashCode());
	}
	
	@Test
	public void testDuplicate() {
		List<Integer> list = Arrays.asList(1,2,2);
		boolean result = DataprocessUtils.checkListDuplicate(list);
		System.out.println(result);
	}
	
	@Test
	public void testMD5() {
		
		String source = "atguigu";
		String md5 = DataprocessUtils.md5(source);
		System.out.println(md5);
		
	}
	
	@Test
	public void testMove() {
		byte i = -20;
		byte j = (byte) (i >>> 4);
		System.out.println(j);
	}

}
