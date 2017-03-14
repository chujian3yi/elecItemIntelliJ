package itest.elec.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import itest.elec.util.GenericTypeUtils;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,ServletRequestAware,ServletResponseAware {

	protected  HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	private T entity; 
	public BaseAction(){
		//泛型转换
		Class entityClass = GenericTypeUtils.getGenericSuperClass(this.getClass());
		
		try {
			entity = (T)entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public T getModel() {
		return entity;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}
}
