package com.activitys.activityDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.JDBCBean;

import com.bean.Activitys;
import com.bean.Member;
import com.bean.MemberActivity;

public class ActivityDaoImpl extends JDBCBean implements ActivityDao {

	@Override
	/***
	 * 获取所有的活动列表
	 * parameter: null
	 * return: List<Activitys>
	 */
	public List<Activitys> getActivitysList() {
		// TODO Auto-generated method stub
		List<Activitys> list = new ArrayList<Activitys>();
		
		String sql = "select * from activity_info";
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Activitys activity = new Activitys();
				activity.setId(rs.getInt("id"));
				activity.setTheme(rs.getString("theme"));
				activity.setTime(rs.getString("time"));
				activity.setSite(rs.getString("site"));
				activity.setRemark(rs.getString("remark"));
				list.add(activity);
				activity = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.close();
		}
		
		return list;
	}
	
	@Override
	/***
	 * 获取所有的活动列表
	 * parameter: null
	 * return: List<Activitys>
	 */
	public List<Activitys> getActivitysPartList() {
		// TODO Auto-generated method stub
		List<Activitys> list = new ArrayList<Activitys>();
		
		String sql = "select * from activity_info   ORDER BY id DESC LIMIT 0,7 ";
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Activitys activity = new Activitys();
				activity.setId(rs.getInt("id"));
				activity.setTheme(rs.getString("theme"));
				activity.setTime(rs.getString("time"));
				activity.setSite(rs.getString("site"));
				activity.setRemark(rs.getString("remark"));
				list.add(activity);
				activity = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.close();
		}
		
		return list;
	}

	
	@Override
	/**
	 * 将一条活动插入数据库中
	 * parameter: Activitys
	 * return: boolean
	 */
	public Boolean setActivity(Activitys activity) {
		// TODO Auto-generated method stub
		String sql = "insert into activity_info(theme,time,site,remark) value('"
		+activity.getTheme()+"','"+activity.getTime()+"','"+activity.getSite()+"','"+activity.getRemark()+"')";
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.close();
		}
		return true;
	}

	@Override
	/**
	 * 根据活动对应的id查询活动记录
	 * parameter: id
	 * return Activitys
	 */
	public Activitys getActivityByTheme(String theme) {
		// TODO Auto-generated method stub
		Activitys activity = new Activitys();
		String sql = "select * from activity_info where theme = '"+theme +"'";
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				activity.setId(rs.getInt("id"));
				activity.setTheme(rs.getString("theme"));
				activity.setTime(rs.getString("time"));
				activity.setSite(rs.getString("site"));
				activity.setRemark(rs.getString("remark"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		} 
		return activity;
	}

	@Override
	/**
	 * 根据活动的id查找参加的人数
	 * parameter: id
	 * return: count
	 */
	public int getMemberCountByActivityTheme(String theme) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from attend_activity where theme = '" + theme + "'";
		int count = 0;
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				count = Integer.valueOf(rs.getString("count(*)"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}


	@Override
	/**
	 * 根据活动的name获取对应的id
	 * parameter: name
	 * return: id
	 */
	public int getActivityIdByName(String name) {
		// TODO Auto-generated method stub
		String sql = "select id from activity_info where theme = '"+ name+"'";
		int id = 0;
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
		return id;
	}


	@Override
	/**
	 * 将参加活动的信息插入attend_activity表中
	 */
	public void setMemberAndActivity(MemberActivity memberActivity) {
		// TODO Auto-generated method stub
		String sql = "insert into attend_activity(theme,identity) value('"
				+memberActivity.getTheme()+"','"+memberActivity.getIdentity()+"')";
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				this.close();
		}
	}


	@Override
	/**
	 * 通过活动主题查找对应学生的信息
	 */
	public List<Member> getMemberByActivity(String theme) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM attend_activity AS a,member_info AS m WHERE a.identity = m.identity AND a.theme = '"+theme +"'";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setSex(rs.getString("sex"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				list.add(member);
				member = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return list;
	}


	@Override
	/**
	 * 根据身份证号获取活动主题
	 */
	public List<MemberActivity> getActivityByxMember(String identity) {
		// TODO Auto-generated method stub
		String sql = "select * from attend_activity where identity = '"+identity +"'";
		List<MemberActivity> list = new ArrayList<MemberActivity>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				MemberActivity memberActivity = new MemberActivity();
				memberActivity.setTheme(rs.getString("theme"));
				memberActivity.setIdentity(identity);
				list.add(memberActivity);
				memberActivity = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
		
		return list;
	}


	@Override
	/**
	 * 根据身份证号获取活动总数
	 */
	public int getActivityCountByMember(String identity) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from attend_activity where identity = '"+identity +"'";
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				return Integer.valueOf(rs.getString("count(*)"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
		return 0;
	}


	@Override
	/**
	 * 更新参加活动的身份信息表
	 */
	public void updateAttendActivity(MemberActivity memberActivity,String identity_old) {
		// TODO Auto-generated method stub
		String sql = "UPDATE attend_activity SET theme='"+memberActivity.getTheme()+"', identity='"+memberActivity.getIdentity()+"' WHERE identity='"+identity_old+"'";  
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	@Override
	/**
	 * 更新活动信息
	 */
	public void updateActivity(Activitys activity,String old_theme) {
		// TODO Auto-generated method stub
		String sql = "UPDATE activity_info SET theme = '" + activity.getTheme() + "', time = '" + activity.getTime() + "', site = '" + activity.getSite() + "', remark = '"+activity.getRemark()+"' where theme = '" + old_theme +"'";  
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	@Override
	/**
	 * 更新活动会员信息
	 */
	public void updateAttendActivityByTheme(MemberActivity memberActivity,String old_theme) {
		// TODO Auto-generated method stub
		String sql = "UPDATE attend_activity SET theme='"+memberActivity.getTheme()+"', identity='"+memberActivity.getIdentity()+"' WHERE theme='"+old_theme+"' and identity = '"+memberActivity.getIdentity() +"'";  
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	@Override
	/**
	 * 根据用户身份证删除活动会员信息
	 */
	public void delectAttendActivityByIdentity(String identity,String old_theme) {
		// TODO Auto-generated method stub
		String sql = "DELETE from attend_activity WHERE identity='"+identity+"' and theme = '"+old_theme+"'";  
		Connection conn = this.getConn();
		//System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	@Override
	public String getActivitysByidentity(String identity) {
		// TODO Auto-generated method stub
		String sql = "select * from attend_activity WHERE identity='"+identity+"'";
		Connection conn = this.getConn();
		//System.out.println(sql);
		String str = "";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				str += rs.getString("theme") +",";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
		return str;
	}

	@Override
	public void deleteActivity(String theme) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM activity_info WHERE theme = '"+theme+"' ";  
		String sql2 = "DELETE FROM attend_activity WHERE theme = '"+theme+"'";  
		Connection conn = this.getConn();
		System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
			st.execute(sql2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

	@Override
	public void deleteAttendActivity(String theme) {
		String sql = "DELETE FROM attend_activity WHERE theme = '"+theme+"'";  
		Connection conn = this.getConn();
		System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	

}
