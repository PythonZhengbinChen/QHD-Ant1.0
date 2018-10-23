package com.login.loginDao;

import com.bean.Administrator;

public interface LoginDao {
	public Administrator isAdministrator(String name,String password); 
}
