package itest.elec.service;

import itest.elec.domain.ElecCommonMsg;

public interface IElecCommonMsgService {

		public static final String SERVICE_NAME = "itest.elec.service.impl.ElecCommonMsgServiceImpl";

		ElecCommonMsg findElecCommonMsg();

		void saveElecCommonMsg(ElecCommonMsg elecCommonMsg);
		

}
