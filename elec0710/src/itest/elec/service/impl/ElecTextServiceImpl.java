package itest.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import antlr.StringUtils;
import freemarker.template.utility.StringUtil;
import itest.elec.dao.IElecTextDao;
import itest.elec.domain.ElecText;
import itest.elec.service.IElecTextService;

@Service(IElecTextService.SERVICE_NAME)

/**@Transactional的作用是Spring管理事务要加上的注解，
 * 全局有效
 * 默认是可写，开发时候类级别的事务可以定义为只读
 * 在save()里面就会报错，此时可以在save()加上注解
 * @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
 * 即方法里面改变类的属性？
 * */

@Transactional(readOnly=true)					
public class ElecTextServiceImpl implements IElecTextService {

	@Resource(name=IElecTextDao.SERVICE_NAME)
	private IElecTextDao elecTextDao;
	
	/**保存*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(ElecText elecText) {
		elecTextDao.save(elecText);
		
	}
	
	
	/*指定页面传递的条件，查询对应的结果集的信息，返回一个List<ElecText>*/
	/**SELECT * FROM elec_text o WHERE 1=1		#Dao层
	 * AND o.textName LIKE '%张%'				#Servicec层
	 * AND o.textRemark LIKE '%张%'				#Servicec层
	 * ORDER BY o.textDate ASC ,o.textRemark DESC	#Servicec层
	 * */
	
	public List<ElecText> findCollectionByConditionNoPage(ElecText elecText) {
		//组织查询条件
		String condition = "";
		//存放可变参数
		List<Object> paramsList = new ArrayList<Object>();
		if(org.apache.commons.lang.StringUtils.isNotBlank(elecText.getTextName())){
			condition += " and o.textName like ?";
			paramsList.add("%"+elecText.getTextName()+"%");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(elecText.getTextRemark())){ 
			condition += " and o.textRemark like ?";
			paramsList.add("%"+elecText.getTextRemark()+"%");
		}
		//将集合存放的可变参数变成数组
		Object [] params = paramsList.toArray();
		//使用集合存放排序的条件
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.textDate", "asc");
		orderby.put("o.textRemark", "desc");
		
		List<ElecText> list = elecTextDao.findCollectionByConditionNoPage(condition,params,orderby);
		return list;
	}

}
