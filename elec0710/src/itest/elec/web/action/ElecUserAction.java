package itest.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecSystemDDL;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecSystemDDLService;
import itest.elec.service.IElecUserService;
import itest.elec.util.ValueStackUtils;

@SuppressWarnings("serial")
@Controller("elecUserAction")
@Scope(value="prototype")
/**
 * 相当于spring中定义了
 * <bean id= "elecCommonMsgAction"class="itest.elec.web.action.ElecTextAction">*/
public class ElecUserAction extends BaseAction<ElecUser> {
	private static final String ElecUser = null;

	//封装，泛型转换，再封装！！！
	private ElecUser elecUser = this.getModel();
	
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	
	/**  
	* @Name: home（方法的名称）
	* @Description:用户管理首页显示（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/userIndex.jsp
	*/
	public String home(){
		//1.从数据字典表中，查询所属单位的列表，返回List<ElecSystemDDL>
		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		//2.指定查询条件，查询用户集合
		List<ElecUser> userList = elecUserService.findUserListByCondition(elecUser);
		request.setAttribute("userList", userList);
		return "home";
	}
	/**  
	* @Name: add（方法的名称）
	* @Description:跳转到用户新增界面（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: 无
	* @Return: 跳转到system/userAdd.jsp
	*/
	public String add(){
		//1.查询性别、职位、所属单位、是否在职的下拉菜单
		this.initSystemDDL();
		return "add";
	}
	/*.查询性别、职位、所属单位、是否在职的下拉菜单*/	
	private void initSystemDDL() {
		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		List<ElecSystemDDL> sexList = elecSystemDDLService.findSystemDDLByKeyword("性别");
		request.setAttribute("sexList", sexList);
		List<ElecSystemDDL> isDutyList = elecSystemDDLService.findSystemDDLByKeyword("是否在职");
		request.setAttribute("isDutyList", isDutyList);
		List<ElecSystemDDL> postList = elecSystemDDLService.findSystemDDLByKeyword("职位");
		request.setAttribute("postList", postList);
	}
	/**  
	* @Name: findJctUnit（方法的名称）
	* @Description:选择页面中选择所属单位，使用所属单位查询对应的所属单位的名称（Ajax的二级联动）（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:ajax。使用struts2的ajax转发
	*/
	public String findJctUnit(){
		//将数据字典的值转换成json数据
		//1.使用jquery的ajax获取 到所属单位的名称
		String keyword = elecUser.getJctID();
		//2。以选择的数据类型作为条件，查询对应数据类型的集合List<ElecSystemDDL>
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLByKeyword(keyword);
		//3.将List放置到栈顶，将list使用struts2的方式转换成json数据（需要导入struts2支持的json的struts2-json-plugin）
		ValueStackUtils.setValueStack(list);
		return "findJctUnit";
	}
	/**  <param name="includeProperties">\[d+\]\.ddlCode,\[d+\]\.ddlName</param>
	* @Name: checkUser（方法的名称）
	* @Description:使用登陆名作为条件，查询登录名在数据库中是否出现重复（ajax的二级联动）（方法的描述）
	* 	String message ="";
	* 			message = 1:表示登录名不能为空
	* 			message = 2：表示登录名在数据库中已经存在，此时不能保存
	* 			message = 3：表示登录名不存在，此时可以保存
	* 
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:ajax。使用struts2的ajax转发
	*/
	public String checkUser(){
		
		//获取页面传递的登录名
		//1.获取登录名以登录名作为条件查询用户表，返回List<ElecUser>
		String logonName = elecUser.getLogonName();
		String message = elecUserService.checkUser(logonName);
		//将message放置到栈顶
		elecUser.setMessage(message);
		return "checkUser";
	}
	/**  
	* @Name: save（方法的名称）
	* @Description:保存页面数据（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:全局转发"close";
	* 关闭子页面，刷新父页面
	*/
	public String save(){
		//直接获取保存的PO对象，执行保存
		elecUserService.saveUser(elecUser);
		return "close";
	}
	/**  
	* @Name: edit（方法的名称）
	* @Description:跳转到编辑页面（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:跳转到/system/userEdit.jsp
	*/
	public String edit(){
		//1.使用userID，查询对应用户的详细信息，返回ElecUser对象，放置到栈顶，用于Struts2支持的表单回显
		ElecUser user = elecUserService.findElecUserByID(elecUser);
		//新对象重新设置viewflag，再推入栈顶
		user.setViewflag(elecUser.getViewflag());
		ValueStackUtils.setValueStack(user);
		//2.查询性别、职位、所属单位、是否在职的下拉菜单
		this.initSystemDDL();
		//3,从栈顶对象ElecUser，获取数据项额编号（ddlCode="2"和ddlName="所属单位"，获取数据项的值上海）
		String ddlName = elecSystemDDLService.findDdlNameByKeyAndDdlCode(user.getJctID(),"所属单位");
		//4.遍历单位名称的集合（依赖所属单位的名称），返回List<ElecSystemDDL>
		List<ElecSystemDDL> jctUnitList = elecSystemDDLService.findSystemDDLByKeyword(ddlName);
		request.setAttribute("jctUnitList", jctUnitList);
		return "edit";
	}
	/**  
	* @Name: delete（方法的名称）
	* @Description:根据userID删除选中的用户信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:重定向到/system/userIndex.jsp
	*/
	public String delete(){
		elecUserService.deleteUserByIds(elecUser);
		return "delete";
	}
}
