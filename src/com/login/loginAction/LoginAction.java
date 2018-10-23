package com.login.loginAction;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.Administrator;
import com.login.loginService.LoginService;

@Controller
public class LoginAction {
	@Resource
	LoginService loginService;
	
	@RequestMapping(value = "/login.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean login(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "name") String name,
			@RequestParam(value = "password") String password) {
		Administrator admin = loginService.isAdministrator(name, password);
		if(admin != null && admin.getName().equals(name)){
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
//			response.sendRedirect("pages/index.html");
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping("/loginOut.do")
	@ResponseBody
	public void loginOut(HttpServletRequest requerst, HttpServletResponse response) {
		HttpSession session = requerst.getSession();
		session.removeAttribute("admin");
		System.out.println("====用户成功退出！====");
		try {
			response.sendRedirect("/QHD-Ant/pages/login.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getAdmin.do")
	@ResponseBody
	public Administrator getAdmin(HttpServletRequest requerst, HttpServletResponse response) {
		HttpSession session = requerst.getSession();
		return (Administrator)session.getAttribute("admin");
	}
	
}
