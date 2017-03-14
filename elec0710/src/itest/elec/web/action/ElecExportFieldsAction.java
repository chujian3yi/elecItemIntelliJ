package itest.elec.web.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecExportFields;
import itest.elec.service.IElecExportFieldsService;
import itest.elec.util.StringToListUtils;

@SuppressWarnings("serial")
@Controller("elecExportFieldsAction")
@Scope(value="prototype")

public class ElecExportFieldsAction extends BaseAction<ElecExportFields> {
	//封装，泛型转换，再封装！！！
	private ElecExportFields elecExportFields = this.getModel();
	
	@Resource(name=IElecExportFieldsService.SERVICE_NAME)
	private IElecExportFieldsService elecExportFieldsService;
	
	
	
	/**  
	* @Name: setExportExcel（方法的名称）
	* @Description: 跳转到导出设置的页面（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到system/exportExcel.jsp
	*/
	  public String setExportExcel(){
	//获取所属模块
		  String belongTo = elecExportFields.getBelongTo();
	//1：使用belongTo作为主键，查处导出设置表，获取ElecExportFields对象
		  ElecExportFields elecExportFields = elecExportFieldsService.findElecExportFields(belongTo);
	 /* *
		  * 2：使用ElecExportFields对象获取4个字段的值
		  * 未导出的中文字段		未导出的英文字段		导出的中文字段	到处的英文字段
		  * 
		  * 同时将四个字段的值，使用#分割，转换成4个List<String> list
	 */
		 List<String> noZList = StringToListUtils.stringToList(elecExportFields.getNoExpNameList(),"#"); 
		 List<String> noEList = StringToListUtils.stringToList(elecExportFields.getNoExpFieldName(),"#"); 
		 List<String> zList = StringToListUtils.stringToList(elecExportFields.getExpNameList(),"#"); 
		 List<String> eList = StringToListUtils.stringToList(elecExportFields.getExpFieldName(),"#"); 
		 
	/**3:由于特点
	 * 【未导出的中文字段和味道出的英文字段，肠毒药一一对应
	 * 导出的中文字段和导出的英文字段，长度要一一对应】
	 * 
	 * 4:使用2个Map集合，存放未导出字段和导出字段
	 * 	Map<String,String> map
	 * map集合的key存放英文信息，value存放中文信息
	 * */
		 //未导出的字段
		 Map<String, String> noMap = new LinkedHashMap<String, String>();
		 //导出的字段
		 Map<String, String> map = new LinkedHashMap<String, String>();
		 //未导出的中文字段和味道出的英文字段，肠毒药一一对应
		 if(noZList!=null && noZList.size()>0){
			 for (int i = 0; i < noZList.size(); i++) {
				 map.put(noEList.get(i), noZList.get(i));
			}
		 }
		 //导出的中文字段和导出的英文字段，长度要一一对应
		 if(zList!=null && zList.size()>0){
			 for (int i = 0; i < zList.size(); i++) {
				 map.put(eList.get(i), zList.get(i));
			}
		 }
		 request.setAttribute("noMap", noMap);
		 request.setAttribute("map", map);
		 
		  return "setExportExcel";
	  } 
	  
	  /**  
		* @Name: saveSetExportExcel（方法的名称）
		* @Description: 保存导出设置（方法的描述）
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2011-06-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 跳转到close.jsp页面
		* 
		* 
		*/
	  public String saveSetExportExcel(){
		  //1:获取页面5个隐藏的值，执行更新操作
		  elecExportFieldsService.saveSetExportExcel(elecExportFields);
		  return "close";
	  }

}
