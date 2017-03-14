package itest.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecUserDao;
import itest.elec.domain.ElecUser;

@Repository(IElecUserDao.SERVICE_NAME)

public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser> implements IElecUserDao{


	/**  
	* @Name: findPopedomByLogonName（方法的名称）
	* @Description: 使用登录名作为条件，查询当前登录名具有的权限
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters:String 登录名：name
	* * @Return: List<Object> list;权限的集合
	*/
	public List<Object> findPopedomByLogonName(final String name) {
		final String sql = "SELECT a.mid FROM elec_role_popedom a"+
					 " INNER JOIN elec_user_role b ON a.roleID = b.roleID"+
					 " INNER JOIN elec_user c ON c.userID = b.userID AND c.logonName=?"+
					 " WHERE c.isDuty='1'";
		List<Object> list = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) 
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, name);
				return query.list();
			}
			
			
		});
		return list;
	}

}
