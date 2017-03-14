package itest.elec.service;

import java.util.List;

import itest.elec.domain.ElecUser;

public interface IElecUserService {
	public static final String SERVICE_NAME = "itest.elec.service.impl.ElecUserServiceImpl";

	List<ElecUser> findUserListByCondition(ElecUser elecUser);

	String checkUser(String logonName);

	void saveUser(itest.elec.domain.ElecUser elecUser);

	itest.elec.domain.ElecUser findElecUserByID(itest.elec.domain.ElecUser elecUser);

	void deleteUserByIds(itest.elec.domain.ElecUser elecUser);

	ElecUser findElecUserByLogonName(String name);

	String findPopedomByLogonName(String name);
}
