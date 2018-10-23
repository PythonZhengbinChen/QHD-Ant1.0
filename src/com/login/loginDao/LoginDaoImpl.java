package com.login.loginDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bean.Administrator;

import utils.BaseDao;
import utils.JDBCBean;

public class LoginDaoImpl extends JDBCBean implements LoginDao {

	@Override
	public Administrator isAdministrator(String name, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from administrator where name = '"+name+"' and password = '"+password +"'";
		Connection conn = this.getConn();
		Administrator admin = new Administrator();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
				admin.setRole(rs.getInt("role"));
				System.out.println(rs.getString("name")+"用户登录！");
				return admin;
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
