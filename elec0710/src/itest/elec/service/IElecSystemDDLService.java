package itest.elec.service;

import java.util.List;

import itest.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLService {

		public static final String SERVICE_NAME = "itest.elec.service.impl.ElecSystemDDLServiceImpl";

		List<ElecSystemDDL> findDistinctKeyword();

		List<ElecSystemDDL> findSystemDDLByKeyword(String keyword);

		void saveSystemDDL(ElecSystemDDL elecSystemDDL);

		String findDdlNameByKeyAndDdlCode(String ddlCode, String keyword);

	

	

}
