package itest.elec.dao.impl;

import itest.elec.dao.ICommonDao;
import itest.elec.util.GenericTypeUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T> {
	 Class entityClass = GenericTypeUtils.getGenericSuperClass(this.getClass());
		
	/*HibernateDaoSupport的作用：需要使用模板来写顶层方法save()等。。。
	 * 要使用模板，就要有容器，此时Spring就出现了
	 * bean.xml去写容器。使用注解的方式。
	 *
	 */
	
	/*	spring容器中定义
	 * <bean id="commondDao" class="itest.elec.dao.impl.CommondImpl">
 		<property name="sessionFactory" ref="sessionFactory"></property>
 		</bean>
	 * 
	 * */
	@Resource(name="sessionFactory")
	public final void setSessionFactoryDi(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
		
	}
		
	/**保存*/
	public void save(T entry) {
		this.getHibernateTemplate().save(entry);
	}
	/**更新*/
	public void update(T entry) {
		this.getHibernateTemplate().update(entry);
	}
	/**使用主键id查询对象*/
	
	public T findObjectByID(Serializable id) {
       	return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	/**删除（使用一个主键id和多个主键id的数组）*/

	public void deleteObjectByIDS(Serializable... ids) {
		if(ids!=null && ids.length>0){
			for (Serializable id : ids) {
				Object entity = this.findObjectByID(id);
				this.getHibernateTemplate().delete(entity);
			}
		}
	}

	/**删除（将对象封装成集合，使用集合删除集合中存放的所有对象）*/
	public void deleteObjectByCollection(List<T> list) {
		this.getHibernateTemplate().deleteAll(list);
		
	}
	
	
	
	/*指定页面传递的条件，查询对应的结果集的信息，返回一个List<ElecText>  不分页*/
	/**SELECT * FROM elec_text o WHERE 1=1		#Dao层
	 * AND o.textName LIKE '%张%'				#Servicec层
	 * AND o.textRemark LIKE '%张%'				#Servicec层
	 * ORDER BY o.teextDate ASC ,o.textRemark DESC	#Servicec层
	 * */
	
	public List<T> findCollectionByConditionNoPage(String condition, Object[] params, Map<String, String> orderby) {

		String hql = " select o from " + entityClass.getSimpleName()+" o where 1=1 ";
		//解析Map集合，获取排序的语句
		String orderbyhql = this.orderby(orderby);
		final String finalHql = hql + condition + orderbyhql;
		//方式一
		//List<T> list = this.getHibernateTemplate().find(finalHql, params);
		//使用hibernate提供的回调函数，回调session.实质上，这段代码实现this.getHibernateTemplate().find(finalHql, params);
		List<T> list =  (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i = 0;i < params.length; i ++){
						query.setParameter(i, params[i]);
						
					}
					
				}
				return query.list();
			}

		});
		return list;
	}
	//解析Map集合，获取排序的语句  ORDER BY o.teextDate ASC ,o.textRemark DESC	#Servicec层
	private String orderby(Map<String, String> orderby) {
		StringBuffer buffer = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			buffer.append(" order by ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()+" "+map.getValue()+",");
			}
			//删除最后一个逗号
			buffer.deleteCharAt(buffer.length()-1);
			}
		
		return buffer.toString();
	}
	/**添加二级缓存，针对数据自带呢*/
	public List<T> findCollectionByConditionNoPageCache(String condition, Object[] params, Map<String, String> orderby) {

		String hql = " select o from " + entityClass.getSimpleName()+" o where 1=1 ";
		//解析Map集合，获取排序的语句
		String orderbyhql = this.orderby(orderby);
		final String finalHql = hql + condition + orderbyhql;
		//方式一
		//List<T> list = this.getHibernateTemplate().find(finalHql, params);
		//使用hibernate提供的回调函数，回调session.实质上，这段代码实现this.getHibernateTemplate().find(finalHql, params);
		List<T> list =  (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i = 0;i < params.length; i ++){
						query.setParameter(i, params[i]);
					}
				}
				query.setCacheable(true);
				return query.list();
			}

		});
		return list;
	}

}
