package itest.elec.service;

import itest.elec.domain.ElecExportFields;

public interface IElecExportFieldsService {

		public static final String SERVICE_NAME = "itest.elec.service.impl.ElecExportFieldsServiceImpl";

		ElecExportFields findElecExportFields(String belongTo);

		void saveSetExportExcel(ElecExportFields elecExportFields);

}
