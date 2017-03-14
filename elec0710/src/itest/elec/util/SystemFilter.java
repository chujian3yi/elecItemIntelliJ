package itest.elec.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.zip.CheckedInputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemFilter implements Filter {
	
	/**初始化，在服务开启时执行*/
	public void init(FilterConfig config) throws ServletException {

	}
	/**每次访问URL时都要访问此方法*/
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
			
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
			//记住我的代码放置到这里fowardIndexPage（）
			String path = request.getServletPath();
			this.fowardIndexPage(request,path);
			chain.doFilter(request, response);
	}
	
	
	/**销毁**/
	public void destroy() {
	}
	//记住我的代码放置到这里
	private void fowardIndexPage(HttpServletRequest request, String path) {
		if(path.equals("/index.jsp")){
			String name = "";
			String password = "";
			String checked = "";
			Cookie [] cookies = request.getCookies();
			if(cookies !=null && cookies.length>0){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("name")){
						name = cookie.getValue();
						//解决中文二进制转换（解）
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						checked ="checked";
					}
					if(cookie.getName().equals("password")){
						password = cookie.getValue();
					}
				}
			}
			request.setAttribute("name", name);
			request.setAttribute("password", password);
			request.setAttribute("checked", checked);
			
		}
		
	}
}
