package itest.elec.dao;

import java.util.List;
import java.util.Map;

import itest.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLDao extends ICommonDao<ElecSystemDDL>{
	
	public static final String SERVICE_NAME = "itest.elec.dao.impl.ElecSystemDDLDaoImpl";

	List<ElecSystemDDL> findDistinctKeyword();

	String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode);

	
}

