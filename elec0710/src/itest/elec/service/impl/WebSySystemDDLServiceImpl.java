package itest.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import itest.elec.dao.IElecSystemDDLDao;
import itest.elec.domain.ElecSystemDDL;

/**发布服务的方法
 * 	参数String：传递的数据类型
 * 返回值String：根据传递的数据类型，查询该数据类型对应的结果，如果是多个值，中间使用逗号：入【男，女】
 * */
public class WebSySystemDDLServiceImpl {
	public String findSystemByKeyword(String keyword) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecSystemDDLDao elecSystemDDLDao = (IElecSystemDDLDao) ac.getBean(IElecSystemDDLDao.SERVICE_NAME);
		
		//组织查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(keyword)){
			condition += " and o.keyword = ?";
			paramsList.add(keyword);
		}
		Object [] params = paramsList.toArray();
		//排序语句
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode","asc");//按照数据项的编号升序排列
		//数据字典进行查询的时候，使用二级缓存增强检索的效率
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPage(condition, params, orderby);
		StringBuffer webObject  = new StringBuffer("");//axis2支持String类型和XML的类型
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				webObject.append(list.get(i).getDdlName()+",");//值之间用逗号分隔
			}
			webObject.deleteCharAt(webObject.length()-1);
		}
		return webObject.toString();
	}
}

