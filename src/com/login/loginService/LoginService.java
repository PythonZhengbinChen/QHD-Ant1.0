package com.login.loginService;

import com.bean.Administrator;

public interface LoginService {
	public Administrator isAdministrator(String name,String password);
}
