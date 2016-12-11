package com.atguigu.survey.model;

import java.util.List;

/**
 * 封装分页数据的专门模型类
 * 并不是所有属性都提供setXxx()方法。原因是对于内部计算或写死的属性不允许外部设置，避免出错。
 * @author Creathin
 *
 * @param <T>
 */
public class Page<T> {
	
	private int pageNo;
	private int totalRecordNo;
	private int totalPageNo;
	private List<T> list;
	private int pageSize = 5;
	
	public Page(String pageNoStr, int totalRecordNo) {
		
		//一、计算总页数
		//1.给totalRecordNo赋值
		this.totalRecordNo = totalRecordNo;
		
		//2.计算总页数
		this.totalPageNo = (totalRecordNo / pageSize) + ((totalRecordNo % pageSize == 0)?0:1);
		
		//二、修正pageNo
		//1.给pageNo设置默认值
		this.pageNo = 1;
		
		//2.执行类型转换
		try {
			this.pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {/*如果转换失败，则什么都不做，保持默认值*/}
		
		//3.在1~totalPageNo之间修正pageNo
		//※考虑到总记录为0时导致总页数也是0，最终执行limit子句时出现SQL语法错误，为了避免这个情况，先做
		//pageNo > totalPageNo判断
		if(pageNo > totalPageNo) {
			pageNo = totalPageNo;
		}
		
		if(pageNo < 1) {
			pageNo = 1;
		}
	}
	
	public boolean isHasPrev() {
		return this.pageNo > 1;
	}
	
	public boolean isHasNext() {
		return this.pageNo < this.totalPageNo;
	}
	
	public int getPrev() {
		return this.pageNo - 1;
	}
	
	public int getNext() {
		return this.pageNo + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

}
