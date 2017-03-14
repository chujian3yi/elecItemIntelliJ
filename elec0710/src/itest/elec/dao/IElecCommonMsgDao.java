package itest.elec.dao;

import itest.elec.domain.ElecCommonMsg;

public interface IElecCommonMsgDao extends ICommonDao<ElecCommonMsg>{
	
	public static final String SERVICE_NAME = "itest.elec.dao.impl.ElecCommonMsgDaoImpl";
}

