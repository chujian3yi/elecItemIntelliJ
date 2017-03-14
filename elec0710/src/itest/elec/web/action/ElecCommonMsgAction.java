package itest.elec.web.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecCommonMsg;
import itest.elec.service.IElecCommonMsgService;
import itest.elec.util.ValueStackUtils;

@SuppressWarnings("serial")
@Controller("elecCommonMsgAction")
@Scope(value="prototype")
/**
 * 相当于spring中定义了
 * <bean id= "elecCommonMsgAction"class="itest.elec.web.action.ElecTextAction">*/
public class ElecCommonMsgAction extends BaseAction<ElecCommonMsg> {
	//封装，泛型转换，再封装！！！
	private ElecCommonMsg elecCommonMsg = this.getModel();
	
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	
	
	/**  
	* @Name: home（方法的名称）
	* @Description: 运营监控 的首页显示（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2011-06-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到system/actingIndex.jsp
	*/
	  public String home(){
		  //1：查询运行监控表，获取运行监控表中的数据，返回ElecCommonMsg对象
		  ElecCommonMsg commonMsg = elecCommonMsgService.findElecCommonMsg();
		  //2：将ElecCommonMsg对象压入栈顶，用于表单回显，将所有数据显示到文本框中
		  ValueStackUtils.setValueStack(commonMsg);
		  return "home";
	  } 
	  /**  
		* @Name: save（方法的名称）
		* @Description: 保存运营监控 （方法的描述）
		* @Author: ghq（作者）
		* @Version: V1.00 （版本号）
		* @Create Date: 2011-06-25 （创建日期）
		* @Parameters: 无（方法的入参，如果没有，填写为“无”）
		* @Return: 重定向到system/actingIndex.jsp
		*/
		  public String save(){
			  //elecCommonMsgService.saveElecCommonMsg(elecCommonMsg);
			  //模拟多条数据的保存
			 
				//模拟：循环遍历150条数据，观察百分比的变化情况ServletActionContext.getRequest(),多线程ajax操作
				for(int i=1;i<=1000;i++){
					elecCommonMsgService.saveElecCommonMsg(elecCommonMsg);
					request.getSession().setAttribute("percent", (double)i/1000*100);//存放计算的百分比
				}
				//线程结束时，清空当前session
				request.getSession().removeAttribute("percent");
				return "save";
		  } 
		  
		  /**  
			* @Name: progressBar（方法的名称）
			* @Description: ajax调用，实现页面动态显示百分比的进度条
			* @Author: ghq（作者）
			* @Version: V1.00 （版本号）
			* @Create Date: 2011-06-25 （创建日期）
			* @Parameters: 无（方法的入参，如果没有，填写为“无”）
			* @Return: null或者是NONE
			*/
		  public String progressBar() throws Exception{
				//从session中获取操作方法中计算的百分比
				Double percent = (Double) ServletActionContext.getRequest().getSession().getAttribute("percent");
				String res = "";
				//此时说明操作的业务方法仍然继续在执行
				if(percent!=null){
					//计算的小数，四舍五入取整
					int percentInt = (int) Math.rint(percent); 
					res = "<percent>" + percentInt + "</percent>";
				}
				//此时说明操作的业务方法已经执行完毕，session中的值已经被清空
				else{
					//存放百分比
					res = "<percent>" + 100 + "</percent>";
				}
				//定义ajax的返回结果是XML的形式
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				//存放结果数据，例如：<response><percent>88</percent></response>
				out.println("<response>");
				out.println(res);
				out.println("</response>");
				out.close();
				return null;
			}


}
