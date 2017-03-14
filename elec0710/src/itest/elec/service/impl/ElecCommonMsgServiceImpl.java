package itest.elec.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import itest.elec.dao.IElecCommonMsgDao;
import itest.elec.domain.ElecCommonMsg;
import itest.elec.service.IElecCommonMsgService;

@Service(IElecCommonMsgService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecCommonMsgServiceImpl implements IElecCommonMsgService{
	
	@Resource(name=IElecCommonMsgDao.SERVICE_NAME)
	private IElecCommonMsgDao elecCommonMsgDao;


	/**  
	* @Name: findElecCommonMsg（方法的名称）
	* @Description: 回获取运行监控中的数据（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: ElecCommonMsg:封装对象
	*/
	
	
	public ElecCommonMsg findElecCommonMsg() {
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		ElecCommonMsg commonMsg = null;
		if(list!=null && list.size()>0){
			commonMsg = list.get(0);
		}
		return commonMsg;
	}
	
	/**  
	* @Name: saveElecCommonMsg（方法的名称）
	* @Description: 回获取运行监控中的数据（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: ElecCommonMsg：封装保存的参数
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecCommonMsg(ElecCommonMsg elecCommonMsg) {
		//1：查询运行监控表，获取 运行监控表的数据，返回List<ElecCommonMsg> List,使用List作为判断数据库中是否存在数据
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		//如果list！=null：数据表中存在数据，获取页面传递的两个参数，组织PO对象，执行更新（update）
		if(list!=null && list.size()>0){
			//方案一；先删除在创建
			//方案二；组织PO对象，执行update
		ElecCommonMsg commonMsg = list.get(0);
		commonMsg.setStationRun(elecCommonMsg.getStationRun());
		commonMsg.setDevRun(elecCommonMsg.getDevRun());
		commonMsg.setCreateDate(new Date());
		
		/*******/
		//行不行？不行的。a diferent object with the same identifier value was already associated with the session:
		//一个session中不能存放2个形同的oid对象。
		/*elecCommonMsg.setComID(commonMsg.getComID());
		elecCommonMsg.setCreateDate(new Date());
		elecCommonMsgDao.update(commonMsg);*/
		/*******/
		
		}
		//如果list==null:数据表中不存在数据，获取页面传递的两个参数，组织PO对象，执行保存（save）
		else{
			elecCommonMsg.setCreateDate(new Date());
			elecCommonMsgDao.save(elecCommonMsg);
		}
	}
}
