package utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Administrator;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        String path = servletRequest.getRequestURI();

        Administrator admin = (Administrator)session.getAttribute("admin");
        if(admin != null){
     	   chain.doFilter(servletRequest, servletResponse);
	             return;
        }
        if(path.contains(".css")||path.contains(".jpg")||path.contains(".png")||path.contains("login.html")) {
     	   chain.doFilter(servletRequest, servletResponse);
     	  
     	   return;
         }
        if(path.contains(".js")&&!path.contains(".html")){
	        	chain.doFilter(servletRequest, servletResponse);
	             return;
        }
        if(path.contains("login.do")){
     	 chain.doFilter(servletRequest, servletResponse);
     	 return;
        }
        if(admin == null){
     	servletResponse.sendRedirect("/QHD-Ant/pages/login.html");
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
