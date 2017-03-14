package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import itest.elec.dao.IElecTextDao;
import itest.elec.domain.ElecText;

public class TestDao {

	@Test
	/**
	 *保存 
	 *
	 *先要加载Spring容器，容器来加载Hibernate
	 */
	public void save(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
//		3.实例化一个javabean对象，加载数据，并调用save();
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Dao名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Dao备注");
		elecTextDao.save(elecText);
		
	}
	@Test
	/**
	 *更新
	 *
	 *先要加载Spring容器，容器来加载Hibernate
	 */
	public void update(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
//		3.实例化一个javabean对象，加载数据，并调用save();
		ElecText elecText = new ElecText();
		
		elecText.setTextID("402881e8561809af01561809b2d50001");
		elecText.setTextName("更新名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("更新备注");
		elecTextDao.update(elecText);
		
	}
	@Test
	/**
	 *使用主键id查询
	 *
	 *先要加载Spring容器，容器来加载Hibernate
	 */
	public void findObjectById(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);     
		Serializable id = "402881ea5655f3f9015655f4d1360001";
		ElecText  elecText = elecTextDao.findObjectByID(id);
		
		System.out.println(elecText.getTextName()+"  "+elecText.getTextDate()+"  "+elecText.getTextRemark());
	}
	
	/**删除（使用一个主键id和多个主键id的数组）*/
	@Test
	public void deleteObjectByIDs(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME); 
		//Serializable [] ids = {"402881ea5655f3f9015655f4d1360001","402881ec56456616015645661dda0001","402881ec5622ad21015622ad615f0001"};
		Serializable ids = "402881ee5659f1b0015659f1b5d10002";
		elecTextDao.deleteObjectByIDS(ids);

	}
	/**删除（将对象封装成集合，使用集合删除集合中存放的所有对象，得有id）
	 * 用法：将数据查询获取封装到list中，删除全部的list，即先查询后删除
	 * */
	@Test
	public void deleteObjectByCollection(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME); 
		
		List<ElecText> list = new ArrayList<ElecText>();
		
		ElecText elecText1 = new ElecText();
		elecText1.setTextID("402881e8561809af01561809b2d50001");
		ElecText elecText2 = new ElecText();
		elecText2.setTextID("402881ec56405f0d0156405f11120001");
		list.add(elecText1);
		list.add(elecText2);
		
		elecTextDao.deleteObjectByCollection(list);
	}	
	
	/**指定条件的删除
	 * 指定页面传递的查询条件，查询对应的结果集信息，返回List<ElecText>，不分页
	 * */
	/**
	SELECT * FROM elec_text o WHERE 1=1      #Dao层
	AND o.textName LIKE '%张%'           #Service层
	AND o.textRemark LIKE '%张%'         #Service层
	ORDER BY o.textDate ASC,o.textRemark DESC  #Service层
	 */
	@Test
	public List deleteObjectByConditionNoPage(){
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
