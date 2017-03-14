package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecPopedomDao;
import itest.elec.domain.ElecPopedom;

@Repository(IElecPopedomDao.SERVICE_NAME)

public class ElecPopedomDaoImpl extends CommonDaoImpl<ElecPopedom> implements IElecPopedomDao{

}
