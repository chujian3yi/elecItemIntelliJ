package itest.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecPopedom;
import itest.elec.domain.ElecRole;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecRoleService;

@SuppressWarnings("serial")
@Controller("elecRoleAction")
@Scope(value="prototype")
/**
 * 相当于spring中定义了
 * <bean id= "elecCommonMsgAction"class="itest.elec.web.action.ElecTextAction">*/
public class ElecRoleAction extends BaseAction<ElecPopedom> {
	private static final String ElecUser = null;

	//封装，泛型转换，再封装！！！
	private ElecPopedom elecPopedom = this.getModel();
	
	@Resource(name=IElecRoleService.SERVICE_NAME)
	private IElecRoleService elecRoleService;

	/**  
	* @Name: home（方法的名称）
	* @Description: 角色管理的首页显示（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到system/roleIndex.jsp
	*/
	  public String home(){
		  //1.从角色表中查询所有角色，列表返回到集合List<ElecRole>,遍历页面的下拉菜单
		  List<ElecRole> roleList = elecRoleService.findRoleList();
		  request.setAttribute("roleList", roleList);
		  //2.从权限列表中产讯所有权限，返回集合List<ElecPopedom>,遍历到页面的权限分配
		  List<ElecPopedom> popedomList = elecRoleService.findPopedomList();
		  request.setAttribute("popedomList", popedomList);
		  return "home";
	  } 
		/**  
		* @Name: edit（方法的名称）
		* @Description:跳转到角色编辑的（ajax）pub。jsp封装
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2016-10-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 跳转到system/roleEdit.jsp
		*/
	  
	  public String edit(){
		  //1:使用角色ID，获取角色关联权限集合
		  String roleID = elecPopedom.getRoleID();
		  List<ElecPopedom> popedomList = elecRoleService.findPopedomListByRoleID(roleID);
		  request.setAttribute("popedomList", popedomList);
		  //2：使用角色ID，获取用户角色关联表
		  List<ElecUser> userList =  elecRoleService.findUserListByUserID(roleID);
		  request.setAttribute("userList", userList);
		  return "edit";
	  }
	  
		/**  
		* @Name: save（方法的名称）
		* @Description:保存角色和权限（没使用hibernate），保存用户和角色（使用hibernate）
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2016-10-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 重定向到roleIndex.jsp
		*/
	  public String save(){
		  elecRoleService.saveRole(elecPopedom);
		  return "save";
	  }
}
