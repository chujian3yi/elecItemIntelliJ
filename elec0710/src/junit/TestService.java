package junit;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import itest.elec.domain.ElecText;
import itest.elec.service.IElecTextService;

public class TestService {

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
		IElecTextService elecTextService = (IElecTextService)ac.getBean(IElecTextService.SERVICE_NAME);
//		3.实例化一个javabean对象，加载数据，并调用save();
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Service名称");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Service备注");
		elecTextService.save(elecText);
		
	}
	
	@Test
	/**
	 *模拟Action层，调用Service层
	 */
	public void findCollectionByConditionNoPage(){
//		 1. 加载容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
//		2.调用Dao，这里使用多态，应认真思考以加重对多态的印象
		IElecTextService elecTextService = (IElecTextService)ac.getBean(IElecTextService.SERVICE_NAME);
//		3.实例化一个javabean对象，加载数据，并调用save();
		ElecText elecText = new ElecText();
		
		//elecText.setTextName("张");
		//elecText.setTextRemark("张");
		List<ElecText> list = elecTextService.findCollectionByConditionNoPage(elecText);
		if(list!=null && list.size()>0){
			for (ElecText text : list) {
				System.out.println(text.getTextName()+"  "+text.getTextDate()+"  "+text.getTextRemark());
			}
		}
	
	}
}
