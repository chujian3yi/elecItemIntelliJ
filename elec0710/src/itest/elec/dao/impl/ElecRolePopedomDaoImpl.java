package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecRolePopedomDao;
import itest.elec.domain.ElecRolePopedom;

@Repository(IElecRolePopedomDao.SERVICE_NAME)

public class ElecRolePopedomDaoImpl extends CommonDaoImpl<ElecRolePopedom> implements IElecRolePopedomDao{

}
