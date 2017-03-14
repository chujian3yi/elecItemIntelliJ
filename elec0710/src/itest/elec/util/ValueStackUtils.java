package itest.elec.util;

import org.apache.struts2.ServletActionContext;

public class ValueStackUtils {
	//将传递的值，压入到栈顶
	public static void setValueStack(Object object) {
		
		ServletActionContext.getContext().getValueStack().pop();
		ServletActionContext.getContext().getValueStack().push(object);
	}
	
}
