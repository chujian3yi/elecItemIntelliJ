package itest.elec.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.URLUtil;

public class LogonUtils {

		/**验证码*/
	public static boolean checkNumber(HttpServletRequest request) {
		
		//从页面获取验证码
		String checkNumber = request.getParameter("checkNumber");
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		
		//从session中获取验证码
		String CHECK_NUMBER_KEY = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		return checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY);
	}

		/**记住我*/
	public static void remeberMe(HttpServletRequest request, HttpServletResponse response, String name,
			String password) {
		
		//创建2个cookie
		Cookie nameCookie = new Cookie("name", name);
		Cookie passwordCookie = new Cookie("password", password);
		
		/*解决中文名不能放置在记住我功能的登录框
		 * （换）*/
		try {
			name = URLEncoder.encode("name", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//设置cookie存在的有效路径（绝对路径）
		nameCookie.setPath(request.getContextPath()+"/");
		passwordCookie.setPath(request.getContextPath()+"/");
		
		//设置coolie存在的有效时间(一周)
		String remberMe = request.getParameter("remberMe");
		if(remberMe !=null && remberMe=="yes"){
			nameCookie.setMaxAge(60*60*12*7);
			passwordCookie.setMaxAge(60*60*12*7);
		}else {
			
			//取消有效时间，设置有效时间为0
			nameCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}
		
			//存放cookies
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);
		
	}
	
}
