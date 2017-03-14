package itest.elec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import itest.elec.dao.IElecExportFieldsDao;
import itest.elec.domain.ElecExportFields;
import itest.elec.service.IElecExportFieldsService;

@Service(IElecExportFieldsService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecExportFieldsServiceImpl implements IElecExportFieldsService{
	
	@Resource(name=IElecExportFieldsDao.SERVICE_NAME)
	private IElecExportFieldsDao elecExportFieldsDao;


	/**  
	* @Name: findElecExportFields（方法的名称）
	* @Description:使用belongTo作为主键id，查询导出设置表，获取ElecExportFields对象（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: String 主键ID（方法的入参，如果没有，填写为“无”）
	* @Return: ElecExportFields ：存放对象
	*/
	public ElecExportFields findElecExportFields(String belongTo) {
		ElecExportFields elecExportFields = elecExportFieldsDao.findObjectByID(belongTo);
		return elecExportFields;
	}
	
	/**  
	* @Name: saveSetExportExcel（方法的名称）
	* @Description:  获取页面5个隐藏的值，执行更新操作,针对页面传递的belongTo，更新对应的导出设置字段（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: ElecExportFields：页面传递对象
	* @Return: 无：存放对象
	*/
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	//更新得用事务管理器
	public void saveSetExportExcel(ElecExportFields elecExportFields) {
		elecExportFieldsDao.update(elecExportFields);
	}
}
