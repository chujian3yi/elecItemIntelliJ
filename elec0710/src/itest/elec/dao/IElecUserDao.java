package itest.elec.dao;

import java.util.List;

import itest.elec.domain.ElecUser;

public interface IElecUserDao extends ICommonDao<ElecUser>{
	
	public static final String SERVICE_NAME = "itest.elec.dao.impl.ElecUserDaoImpl";

	List<Object> findPopedomByLogonName(String name);
}

