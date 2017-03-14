package itest.elec.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecSystemDDLDao;
import itest.elec.domain.ElecSystemDDL;

@Repository(IElecSystemDDLDao.SERVICE_NAME)

public class ElecSystemDDLDaoImpl extends CommonDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao{

	/**  
	* @Name: findDistinctKeyword（方法的名称）
	* @Description: 将数据类型去掉重复值后返回（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: List<ElecSystemDDL>：封装结果到集合
	*/
	
	 /**
	  * 查询系统中所有的类型列表，返回数据类型，封装到List<ElecSystemDDL>
	  * SELECT DISTINCT o.keyword FROM elec_systemddl o	
	  * */
	
	public List<ElecSystemDDL> findDistinctKeyword() {
		//结果集
		List<ElecSystemDDL> systemList = new ArrayList<ElecSystemDDL>();
		
		
		/**1:
		 * 查询系统中所有的类型列表，返回数据类型，封装到List<ElecSystemDDL>
		 * SELECT DISTINCT o.keyword FROM elec_systemddl o	
		 * List<Object> list = this.getHibernateTemplate().find(hql);
		 * 为什么是Object，由于hql和sql的特点，查询的是对象，如果查询跟随的是字段和属性，为投影查询：按字段查询。
		 * 无论是hql还是sql，投影的是一个字段，此时，返回的是List<Object>
		 * 					投影的是多个值，（SELECT DISTINCT o.keyword,o.ddlName FROM elec_systemddl o）
		 * 					此时返回的是List<Object []>
         */	
		
		/**
		 * 方案一
		String hql = "SELECT DISTINCT o.keyword FROM ElecSystemDDL o";
		 List<Object> list = this.getHibernateTemplate().find(hql);
		
		 * //转换
		 if(list!=null && list.size()>0){
			 for(Object o : list){
				 //数据类型
				String keyword = o.toString();
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				elecSystemDDL.setKeyword(keyword);
				systemList.add(elecSystemDDL);
			 }
		 }
		 */
		 //方案二,利用hql是面向对象的
			String hql = "SELECT DISTINCT new itest.elec.domain.ElecSystemDDL (o.keyword) FROM ElecSystemDDL o";
			systemList = this.getHibernateTemplate().find(hql);
			
		return systemList;
	}
	
	/**  
	* @Name: findDdlNameByKeywordAndDdlCode（方法的名称）
	* @Description: 使用数据类型和数据想的编号获取数据项的值
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-28 （创建日期）
	* @Parameters: String keyword, String ddlCod
	* @Return: String 数据项的值
	*/
	public String findDdlNameByKeywordAndDdlCode(final String keyword, final String ddlCode) {
		String hql = "select o.ddlName from ElecSystemDDL o where o.keyword = ? and o.ddlCode = ?";
		
	List<Object> list = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback(){

		@Override
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			Query query = session.createQuery(hql);
			query.setParameter(0, keyword);
			query.setParameter(1, Integer.parseInt(ddlCode));
			query.setCacheable(true);//开启二级缓存
			return query.list();
		}
		 
	 });
		//数据项的值
		String ddlName = "";
		if(list!=null && list.size()>0){
			Object o = list.get(0);
			ddlName = o.toString();
		}
		return ddlName;
	}
}
