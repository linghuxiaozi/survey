package com.atguigu.survey.base.i;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 根据一个实体类对象进行删除，主要用于深度删除
	 * @param t
	 */
	void removeEntity(T t);
	
	/**
	 * 查询分页的List
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List getLimitedListBySql(String sql, int pageNo, int pageSize, Object... params);
	
	/**
	 * 查询分页的List
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List<T> getLimitedListByHql(String hql, int pageNo, int pageSize, Object... params);
	
	/**
	 * 执行count()统计函数
	 * @param hql
	 * @param params
	 * @return
	 */
	int getCountBySql(String sql, Object... params);
	
	/**
	 * 执行count()统计函数
	 * @param hql
	 * @param params
	 * @return
	 */
	int getCountByHql(String hql, Object... params);
	
	/**
	 * 根据SQL查询实体类的List集合
	 * @param sql
	 * @param params
	 * @return
	 * select emp_name,age from emp→Object[]
	 * select age from emp→Integer
	 */
	List getListBySql(String sql, Object... params);
	
	/**
	 * 根据HQL查询实体类的List集合
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> getListByHql(String hql, Object... params);
	
	/**
	 * 根据HQL查询单个对象
	 * @param hql
	 * @param params
	 * @return
	 */
	T getEntityByHql(String hql, Object... params);
	
	/**
	 * 根据OID查询实体类对象
	 * @param entityId
	 * @return
	 */
	T getEntityById(Integer entityId);
	
	/**
	 * 批量更新
	 * @param sql
	 * @param params
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp01,10]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp02,20]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp03,30]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp04,40]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp05,50]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp06,60]
	 * INSERT INTO survey_employee(EMP_NAME,age) VALUES(?,?) [emp07,70]
	 * 
	 * 一维数组的长度：看SQL语句中有多少个问号
	 * 二维数组的长度：看SQL语句要执行多少次
	 * 
	 * ※注意：Object[][]... params表示传入一个三维数组
	 */
	void batchUpdate(String sql, Object[][] params);
	
	/**
	 * 根据SQL语句更新
	 * @param sql
	 * @param params
	 */
	void updateBySql(String sql, Object... params);
	
	/**
	 * 根据HQL语句更新
	 * @param hql
	 * @param params
	 */
	void updateByHql(String hql, Object... params);
	
	/**
	 * 更新实体类对象
	 * @param t
	 */
	void updateEntity(T t);
	
	/**
	 * 根据OID删除一条记录
	 * @param entityId
	 */
	void removeEntity(Integer entityId);
	
	/**
	 * 将实体类对象保存到数据库中
	 * @param t
	 */
	void saveEntity(T t);

}
