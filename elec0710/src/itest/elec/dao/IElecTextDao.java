package itest.elec.dao;
import itest.elec.domain.ElecText;

public interface IElecTextDao extends ICommonDao<ElecText>{
//	public void findElecText();
	public static final String SERVICE_NAME="itest.elec.dao.impl.ElecTextDaoImpl";

}
