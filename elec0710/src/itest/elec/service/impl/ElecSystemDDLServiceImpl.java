package itest.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import itest.elec.dao.IElecSystemDDLDao;
import itest.elec.domain.ElecSystemDDL;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecSystemDDLService;

@Service(IElecSystemDDLService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecSystemDDLServiceImpl implements IElecSystemDDLService{
	
	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;
	
	
	/**  
	* @Name: findDistinctKeyword（方法的名称）
	* @Description: 将数据类型去掉重复值后返回（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: List<ElecSystemDDL>：封装结果到集合
	*/
	@Override
	public List<ElecSystemDDL> findDistinctKeyword() {
		/*1:
		 * 查询系统中所有的类型列表，返回数据类型，封装到List<ElecSystemDDL>
		 * SELECT DISTINCT o.keyword FROM elec_systemddl o	
         */	 
		List<ElecSystemDDL> list = elecSystemDDLDao.findDistinctKeyword();
		return list;
	}
	/**  
	* @Name: findSystemDDLByKeyword（方法的名称）
	* @Description: 以数据类型作为查询条件，查询数据字典的信息，返回List<ElecSystemDDL>
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: String keyword
	* @Return: List<ElecSystemDDL>：封装结果到集合
	*/	

	public List<ElecSystemDDL> findSystemDDLByKeyword(String keyword) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(keyword)){
			condition += " and o.keyword=?";
			paramsList.add(keyword);
		}
		Object [] params = paramsList.toArray();
		//排序，以数据项的编号升序排序
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPageCache(condition, params, orderby);
		return list;
	}
	
	/**  
	* @Name: saveSystemDDL（方法的名称）
	* @Description: 保存数据字典的设置
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: ElecSystemDDL:存放保存的参数
	* @Return: 无
	*/	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveSystemDDL(ElecSystemDDL elecSystemDDL) {
		//1：获取传递给服务器的参数{3}个
		//数据类型
		String keyword = elecSystemDDL.getKeywordname();
		//数据项的值【数组】
		String [] ddlNames = elecSystemDDL.getItemname();
		//判断业务的标识
		String typeflag = elecSystemDDL.getTypeflag();
		
		//2：使用typeflag的标识，用来判断操作的业务逻辑
		//新增一种数据类型
		if (typeflag!=null && typeflag.equals("new")) {
			//遍历itemname数组，组织PO对象，执行保存
			this.saveSystem(ddlNames,keyword);
		}
		//在已有的数据类型的基础上进行修改和编辑
		/*1，使用数据类型作为条件，查询对应的数据类型下的数据，返回List<ElecSystemDDL>
		 * 2.使用List,删除之前的数据
		 * 3.遍历itemname的数组，组织PO对象，执行保存
		 * 
		 * 当表中的主键没有主外键关联，可以使用先删除再创建
		 */		
		else{
			List<ElecSystemDDL> list = this.findSystemDDLByKeyword(keyword);
			elecSystemDDLDao.deleteObjectByCollection(list);
			this.saveSystem(ddlNames, keyword);
		}
	}
	
	/*遍历itemname数组，组织PO对象，执行保存*/	
	private void saveSystem(String[] ddlNames, String keyword) {
		if(ddlNames!=null && ddlNames.length>0){
			for(int i=0;i<ddlNames.length;i++){
				ElecSystemDDL systemDDL = new ElecSystemDDL();//PO对象
				systemDDL.setKeyword(keyword);
				systemDDL.setDdlCode(i+1);//从1开始累加
				systemDDL.setDdlName(ddlNames [i]);
				//执行保存
				elecSystemDDLDao.save(systemDDL);
			}
			
		}
	}
	
	
	/**  
	* @Name: findDdlNameByKeyAndDdlCode（方法的名称）
	* @Description: 从栈顶对象ElecUser，获取数据项额编号(ddlCode="2"和ddlName="所属单位")获取数据项的值上海
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: String ddlCode:数据想的编号
	* 				String keyword:数据类型
	* @Return: String：数据想的值
	*/	
	public String findDdlNameByKeyAndDdlCode(String ddlCode, String keyword) {
		String ddlName = elecSystemDDLDao.findDdlNameByKeywordAndDdlCode(keyword, ddlCode);
		return ddlName;
	}

}
