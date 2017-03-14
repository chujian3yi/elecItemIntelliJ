package junit;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import itest.elec.domain.ElecSystemDDL;
import itest.elec.domain.ElecText;

public class TestHibernateCache {

	@Test
	/**测试类级别的缓存*/
	public void testClassCache(){
		//默认加载路径下的Hibernate配置文件(hibernate.cfg.xml)和映射文件
		Configuration configration = new Configuration();
		configration.configure();
		//模板代码
		SessionFactory sf = configration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();

		
		ElecSystemDDL elecSystemDDL1= (ElecSystemDDL) s.get(ElecSystemDDL.class, 15);//存在
		
		ElecSystemDDL elecSystemDDL2= (ElecSystemDDL) s.get(ElecSystemDDL.class, 15);//没有，从session的一级缓存中查询
		
		tr.commit();
		s.close();
		
		/*********************************/
		s = sf.openSession();
		tr = s.beginTransaction();
		
		ElecSystemDDL elecSystemDDL3 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 15);//没有，从sessionFactory的二级缓存中查询，存放的散装数据
		
		
		tr.commit();
		s.close();
		
	}
	
	@Test
	/**测试类级别的缓存*/
	public void testQueryCache(){
		//默认加载路径下的Hibernate配置文件(hibernate.cfg.xml)和映射文件
		Configuration configration = new Configuration();
		configration.configure();
		//模板代码
		SessionFactory sf = configration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();

		Query query1 = s.createQuery("from ElecSystemDDL o where o.keyword = '性别'");
		query1.setCacheable(true);
		query1.list();//产生select语句
		
		tr.commit();
		s.close();
		
		/*********************************/
		s = sf.openSession();
		tr = s.beginTransaction();
		
		Query query2 = s.createQuery("from ElecSystemDDL o where o.keyword = '性别'");
		query2.setCacheable(true);
		query2.list();//不产生select语句
		
		tr.commit();
		s.close();
		
	}
}
