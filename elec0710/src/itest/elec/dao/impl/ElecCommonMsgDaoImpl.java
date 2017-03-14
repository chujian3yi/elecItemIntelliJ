package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecCommonMsgDao;
import itest.elec.domain.ElecCommonMsg;

@Repository(IElecCommonMsgDao.SERVICE_NAME)

public class ElecCommonMsgDaoImpl extends CommonDaoImpl<ElecCommonMsg> implements IElecCommonMsgDao{

}
