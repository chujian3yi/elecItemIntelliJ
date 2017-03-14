package itest.elec.dao.impl;

import org.springframework.stereotype.Repository;

import itest.elec.dao.IElecExportFieldsDao;
import itest.elec.domain.ElecExportFields;

@Repository(IElecExportFieldsDao.SERVICE_NAME)

public class ElecExportFieldsDaoImpl extends CommonDaoImpl<ElecExportFields> implements IElecExportFieldsDao{

}
