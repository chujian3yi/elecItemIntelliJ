package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecRoleDao;
import itest.elec.domain.ElecRole;

@Repository(IElecRoleDao.SERVICE_NAME)

public class ElecRoleDaoImpl extends CommonDaoImpl<ElecRole> implements IElecRoleDao{

}
