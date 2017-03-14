package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecTextDao;
import itest.elec.domain.ElecText;
/**相当于在Spring中定义了：
 * <bean id = "itest.elec.dao.impl.ElecTextDaoImpl" class = "itest.elec.dao.impl.ElecTextDaoImpl"/>
 * <bean>*/
@Repository(IElecTextDao.SERVICE_NAME)
public class ElecTextDaoImpl extends CommonDaoImpl<ElecText> implements IElecTextDao{
	

}
