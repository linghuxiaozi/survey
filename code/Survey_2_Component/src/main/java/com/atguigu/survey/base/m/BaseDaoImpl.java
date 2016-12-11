package com.atguigu.survey.base.m;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.base.i.BaseDao;

@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	protected SessionFactory factory;
	
	private Class<T> entityType;
	
	public BaseDaoImpl() {
		
		//1.父类构造器中的this关键字，在创建子类对象时指向子类对象
		//System.out.println(this);
		
		//2.可以通过this关键字获取子类的Class类对象，进而获取父类的Class类对象，但是不带有泛型参数
		Class<?> superclass = this.getClass().getSuperclass();
		//System.out.println(superclass);
		
		//3.可以通过子类Class对象的专门方法获取“带有泛型参数”的父类类型
		//java.lang.reflect.Type代表“类型”这样一个概念
		//凡是可以用来修饰“引用类型”变量的就是类型
		//String s = null;
		//List<String> l = null;
		//同样是类型，也互相有区别。有的类型不带有泛型参数，有的类型带有泛型参数
		//java.lang.reflect.ParameterizedType就是专门描述“带泛型参数类型”的接口
		Type type = this.getClass().getGenericSuperclass();
		//System.out.println(type);
		
		//4.将type对象的类型转换为ParameterizedType
		ParameterizedType pt = (ParameterizedType) type;
		
		//5.调用ParameterizedType类型中的getActualTypeArguments()方法获取实际泛型参数的数组
		//之所以是数组类型是因为泛型参数可以有多个：Map<String,Object>
		Type[] types = pt.getActualTypeArguments();
		//System.out.println(Arrays.asList(types));
		
		//6.从上面数组中获取第一个元素即可
		entityType = (Class<T>) types[0];
		
	}
	
	public Session getSession() {
		
		//作用：开启一个新的会话
		//场合：单独测试Dao方法时。注意：项目中最终要使用的还是getCurrentSession()，
		//openSession()只是临时解决方案
		//经典错误：通过服务器运行项目时如果发现“点几下点不动了”，那么基本上就是因为使用了openSession()
		//原因：只开不关，耗尽了数据库连接池中的数据库连接
		//return factory.openSession();
		
		//作用：从当前线程上获取Session对象
		//场合：测试或调用事务方法时
		return factory.getCurrentSession();
		
	}
	
	public Query getQuery(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query;
	}
	
	public SQLQuery getSQLQuery(String sql, Object... params) {
		SQLQuery query = getSession().createSQLQuery(sql);

		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query;
	}
	
	public T getEntityById(Integer entityId) {
		return (T) getSession().get(entityType, entityId);
	}

	@Override
	public List getLimitedListBySql(String sql, int pageNo, int pageSize,
			Object... params) {
		return getSQLQuery(sql, params).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
	}

	@Override
	public List<T> getLimitedListByHql(String hql, int pageNo, int pageSize,
			Object... params) {
		return getQuery(hql, params).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
	}

	@Override
	public int getCountBySql(String sql, Object... params) {
		
		BigInteger count = (BigInteger) getSQLQuery(sql, params).uniqueResult();
		
		return count.intValue();
	}

	@Override
	public int getCountByHql(String hql, Object... params) {
		
		long count = (Long) getQuery(hql, params).uniqueResult();
		
		return (int) count;
		
		//return (int) getQuery(hql, params).uniqueResult();
	}

	@Override
	public List getListBySql(String sql, Object... params) {
		return getSQLQuery(sql, params).list();
	}

	@Override
	public List<T> getListByHql(String hql, Object... params) {
		return getQuery(hql, params).list();
	}

	@Override
	public T getEntityByHql(String hql, Object... params) {
		return (T) getQuery(hql, params).uniqueResult();
	}

	@Override
	public void batchUpdate(final String sql, final Object[][] params) {
		
		getSession().doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				
				PreparedStatement ps = connection.prepareStatement(sql);
				
				if(params != null) {
					for (int i = 0; i < params.length; i++) {
						Object[] param = params[i];
						
						//给每一条SQL语句的?占位符设置具体的参数值
						for (int j = 0; j < param.length; j++) {
							ps.setObject(j+1, param[j]);
						}
						
						//关键：积攒SQL语句
						ps.addBatch();
						
					}
					
					//执行批量操作
					ps.executeBatch();
				}
				
				//释放资源
				if(ps != null) {
					ps.close();
				}
				
				//释放资源的原则：谁开的谁关。
				
			}
		});
		
	}

	@Override
	public void updateBySql(String sql, Object... params) {
		getSQLQuery(sql, params).executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object... params) {
		getQuery(hql, params).executeUpdate();
	}

	@Override
	public void updateEntity(T t) {
		getSession().update(t);
		
		/*Session session = getSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.update(t);
		
		transaction.commit();
		
		session.close();*/
		
	}

	@Override
	public void removeEntity(Integer entityId) {
		
		//Object object = getSession().get(entityType, entityId);
		
		//getSession().delete(object);
		
		//1.通过factory对象获取实体类对象的元数据
		ClassMetadata classMetadata = factory.getClassMetadata(entityType);
		
		//2.通过元数据获取OID属性的属性名
		String idName = classMetadata.getIdentifierPropertyName();
		
		//3.拼HQL语句
		//delete from Employee e where e.empId=?
		String hql = "delete from "+entityType.getSimpleName()+" alias where alias."+idName+"=?";
		
		//4.执行HQL语句
		updateByHql(hql, entityId);
	}

	@Override
	public void saveEntity(T t) {
		getSession().save(t);
	}

	@Override
	public void removeEntity(T t) {
		getSession().delete(t);
	}
	
}
