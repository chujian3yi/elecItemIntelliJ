package itest.elec.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import itest.elec.domain.ElecText;
import itest.elec.service.IElecTextService;

@SuppressWarnings("serial")
@Controller("elecTextAction")
@Scope(value="prototype")
/**
 * 相当于spring中定义了
 * <bean id= "elecTextAction"class="itest.elec.web.action.ElecTextAction">*/
public class ElecTextAction extends BaseAction<ElecText> {
	//封装，泛型转换，再封装！！！
	private ElecText elecText = this.getModel();
	
	@Resource(name=IElecTextService.SERVICE_NAME)
	private IElecTextService elecTextService;
	
	/**保存 */
	  public String save(){
		  elecTextService.save(elecText);
		System.out.println(request.getParameter("textDate")+"  "+request.getParameter("textName"));  
		  return "save";
	  } 
}
