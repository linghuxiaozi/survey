package com.atguigu.survey.base.i;

public interface BaseService<T> {
	
	/**
	 * 根据OID查询实体类对象
	 * @param entityId
	 * @return
	 */
	T readGetEntityById(Integer entityId);
	
	/**
	 * 更新实体类对象
	 * @param t
	 */
	void writeUpdateEntity(T t);
	
	/**
	 * 根据OID删除一条记录
	 * @param entityId
	 */
	void writeRemoveEntity(Integer entityId);
	
	/**
	 * 将实体类对象保存到数据库中
	 * @param t
	 */
	void writeSaveEntity(T t);

}
