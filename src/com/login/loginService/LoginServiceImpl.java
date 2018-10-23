package com.login.loginService;

import org.springframework.stereotype.Service;

import com.bean.Administrator;
import com.login.loginDao.LoginDaoImpl;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public Administrator isAdministrator(String name, String password) {
		// TODO Auto-generated method stub
		LoginDaoImpl login = new LoginDaoImpl();
		return login.isAdministrator(name, password);
	}

}
