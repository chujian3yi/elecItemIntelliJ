package itest.elec.util;

/**
 * Created by Administrator on 2017/3/24.
 */
import org.apache.struts2.ServletActionContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ConditionalTagUtil extends SimpleTagSupport {
    private String pattern;

    /**标签中要处理的内容*/
    @Override
    public void doTag() throws JspException, IOException {
        String popedom = (String) ServletActionContext.getRequest().getSession().getAttribute("globle_popedom");
        // <u:if pattern="aa">
        if(popedom.contains(pattern)){
            this.getJspBody().invoke(null);
        }
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}

