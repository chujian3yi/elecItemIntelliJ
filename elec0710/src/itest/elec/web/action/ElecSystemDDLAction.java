package itest.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecSystemDDL;
import itest.elec.service.IElecSystemDDLService;

@SuppressWarnings("serial")
@Controller("elecSystemDDLAction")
@Scope(value="prototype")

public class ElecSystemDDLAction extends BaseAction<ElecSystemDDL> {
	//封装，泛型转换，再封装！！！
	private ElecSystemDDL elecSystemDDL = this.getModel();
	
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	/**  
	* @Name: home（方法的名称）
	* @Description: 数据字典首页显示（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/dictionaryIndex.jsp
	*/
	public String home(){
		
		/*1:
		 * 查询系统中所有的类型列表，返回数据类型，封装到List<ElecSystemDDL>
		 * SELECT DISTINCT o.keyword FROM elec_systemddl o	
         */	
		List<ElecSystemDDL> list = elecSystemDDLService.findDistinctKeyword();
		request.setAttribute("systemList", list);
		return "home";
	}
	/**  
	* @Name: edit（方法的名称）
	* @Description: 指定数据类型作为查询条件，查询对应数据类型的数据列表
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/dictionaryEdit.jsp
	*/
	public String edit(){
		//获取数据类型
		String keyword = elecSystemDDL.getKeyword();
		//以数据类型作为查询条件，查询数据字典的信息，返回List<ElecSystemDDL>
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLByKeyword(keyword);
		request.setAttribute("list", list);
		return  "edit";
	}
	/**  
	* @Name: save（方法的名称）
	* @Description: 保存数据字典设置
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: 重定向到system/dictionaryEdit.jsp
	*/
	public String save(){
		elecSystemDDLService.saveSystemDDL(elecSystemDDL);
		return "save";
	}
}
