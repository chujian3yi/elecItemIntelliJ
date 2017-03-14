package junit;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import itest.elec.domain.ElecText;

public class TestHibernate {

	@Test
	/**
	 *保存 
	 */
	public void save(){
		//默认加载路径下的Hibernate配置文件(hibernate.cfg.xml)和映射文件
		Configuration configration = new Configuration();
		configration.configure();
		//模板代码
		SessionFactory sf = configration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		ElecText elecText = new ElecText();
		elecText.setTextName("测试名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Hibernate备注");
		s.save(elecText);
		
		tr.commit();
		s.close();
	}
}
