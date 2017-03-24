package itest.elec.web.action;

import itest.elec.domain.ElecCommonMsg;
import itest.elec.domain.ElecPopedom;
import itest.elec.domain.ElecRole;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecCommonMsgService;
import itest.elec.service.IElecRoleService;
import itest.elec.service.IElecUserService;
import itest.elec.util.LogonUtils;
import itest.elec.util.MD5keyBean;
import itest.elec.util.ValueStackUtils;
import itest.elec.web.action.form.MenuForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Controller("elecMenuAction")
@Scope(value="prototype")

public class ElecMenuAction extends BaseAction<MenuForm> {

	
	//注入运行监控的Service
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	//注入运行监控的Service
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;

	//注入角色的Service
	@Resource(name=IElecRoleService.SERVICE_NAME)
	private IElecRoleService elecRoleService;


	//左侧树形菜单ZTree显示的数据集合
	private List<ElecPopedom> modelList;
	public List<ElecPopedom> getModelList() {
		return modelList;
	}
	public void setModelList(List<ElecPopedom> modelList) {
		this.modelList = modelList;
	}


	private MenuForm menuForm = this.getModel();
	
	/**  
	* @Name: menuHome（方法的名称）
	* @Description:登录
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到menu/home.jsp
	*/
	  public String menuHome(){
		/*添加验证码*/	
		 boolean flag = LogonUtils.checkNumber(request);
		//flag=true,验证码输入正确；flag=false，验证码有误
		 if(!flag){
		//存放错误信息
			 this.addFieldError("error", "验证码有误 ");
			 return "error";
		 }
		 //一：验证登录名和密码
		 //获取登录名和登录密码
		  String name = menuForm.getName();
		  String password = menuForm.getPassword();
		 //1，验证登录名,使用Struts2校验机制addfileerror
		 ElecUser elecUser = elecUserService.findElecUserByLogonName(name);
		 if(elecUser == null){
		 //存放错误信息
			 this.addFieldError("error", "登录名有误 ");
			 return "error";
		 }
		 //2，验证登录密码
		  if(StringUtils.isBlank(password)){
			  this.addFieldError("error", "密码不能为空");
			  return "error";
		  }
		  else{
		 //页面密码与elecUser中的密码对比，不一致，输出错误信息密码有误
		 //将页面获取的密码进行md5加密，再进行对比
			MD5keyBean md5keyBean = new MD5keyBean();
			String md5Password =  md5keyBean.getkeyBeanofStr(password);
			if(!md5Password.equals(elecUser.getLogonPwd())){
				this.addFieldError("error", "密码有误");
				return "error";
			}
		  }
		  
		  //二：当前用户具有角色才能登陆
		  //1.获取当前用户所有的角色
		  Set<ElecRole> elecRoles = elecUser.getElecRoles();
		  //2.组织hashtable对象，（线程安全的）
		  Hashtable<String, String> ht = new Hashtable<String,String>();
		  if(elecRoles!=null && elecRoles.size()>0){
			 for(ElecRole elecRole :elecRoles){
				 ht.put(elecRole.getRoleID(), elecRole.getRoleName());
			 }
		  }else{
		 //存放错误信息
				 this.addFieldError("error", "登录名没有分配角色");
				 return "error";
		  }
		 /**三：当前用户具有权限才能登陆*/
		 //1。查询当前用户具有的权限集合，返回字符串，将权限放置到String popedom中，以@符号间隔
		 String popedom = elecUserService.findPopedomByLogonName(name);
		  if(StringUtils.isBlank(popedom)){
		 //存放错误信息
				 this.addFieldError("error", "登录名具有的角色没有权限");
				 return "error";
		  }
		  
		  /**记住我*/
		  LogonUtils.remeberMe(request,response,name,password);
		  
		  request.getSession().setAttribute("globle_user", elecUser);
		  request.getSession().setAttribute("globle_elecRole", ht);
		  request.getSession().setAttribute("globle_popedom", popedom);
		  return "menuHome";
	  }
	  	  /**系统首页标题*/
	  public String title(){
		 
		  return "title";
	  }
	  /**系统左侧菜单*/
	  public String left(){
		  return "left";
	  }
	  /**系统首页框架改变*/
	  public String change(){
		 
		  return "change";
	  }
	  /**系统首页功能区显示*/
	  public String loading(){
		 
		  return "loading";
	  }
	  /**重新登陆*/
	  public String logout(){
		 //清空session
		  //指定某个session清空
		  //request.getSession().removeAttribute(arg0);
		  //清空所有session
		  request.getSession().invalidate();
		  return "logout";
	  }

		/**  
		* @Name: alermStation（方法的名称）
		* @Description: 显示站点运行情况（方法的描述）
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2011-06-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 跳转到menu/alermStation.jsp
		*/
	  public String alermStation(){
		  //1：查询运行监控表，获取运行监控表中的数据，返回ElecCommonMsg对象
		  ElecCommonMsg commonMsg = elecCommonMsgService.findElecCommonMsg();
		  //2：将ElecCommonMsg对象压入栈顶，用于表单回显，将所有数据显示到文本框中
		  ValueStackUtils.setValueStack(commonMsg);
		  return "alermStation";
	  }
	  
	  /**  
		* @Name: alermDevice（方法的名称）
		* @Description: 显示设备运行情况（方法的描述）
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2011-06-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 跳转到menu/alermDevice.jsp
		*/
	  public String alermDevice(){
		//1：查询运行监控表，获取运行监控表中的数据，返回ElecCommonMsg对象
		  ElecCommonMsg commonMsg = elecCommonMsgService.findElecCommonMsg();
		//2：将ElecCommonMsg对象压入栈顶，用于表单回显，将所有数据显示到文本框中
		  ValueStackUtils.setValueStack(commonMsg);
		 
		  return "alermDevice";
	  }

	/**
	 * @Name: showMenu
	 * @Description: 查询左侧菜单显示（ajax）
	 * @Author: ghq（作者）
	 * @Version: V1.00 （版本号）
	 * @Create Date: 2011-06-25 （创建日期）
	 * @Parameters: 无
	 * @Return: 返回json数据
	 */
	  public String showMenu(){
	      //从session中获取当前登录名具有的权限
          String popedom = (String) request.getSession().getAttribute("globle_popedom");
          //从session中获取当前登录名具有的角色
          Hashtable<String,String> ht = (Hashtable<String,String>)request.getSession().getAttribute("globle_elecRole");
          //从session中获取当前登录名具有的用户
          ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
          modelList = elecRoleService.findShowMenu(popedom);
          /*角色权限控制：
          * 如果是非系统管理员，只能打开用户自己的编辑页面，
          * 保存按钮重定向到编辑页面，并取消修改按钮。
          * **/
          if(!ht.containsKey("1")){
              if (modelList!=null && modelList.size()>0){
                  for (ElecPopedom elecPopedom:modelList){
                      //用户管理
                      if(elecPopedom.getMid().equals("an")){
                            elecPopedom.setUrl("../system/elecUserAction_edit.do?userID="+elecUser.getUserID()+"&roleflag=1");
                      }
                  }
              }
          }
		  //ValueStackUtils.setValueStack(list);
	  	return "showMenu";
	  }
}
