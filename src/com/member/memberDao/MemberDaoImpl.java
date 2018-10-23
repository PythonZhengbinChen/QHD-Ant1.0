package com.member.memberDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.JDBCBean;

import com.bean.Member;

public class MemberDaoImpl extends JDBCBean implements MemberDao {

	@Override
	/**
	 * 获取所有的会员信息
	 * parameter: null
	 * return List<Member>
	 */
	public List<Member> getAllMember() {
		// TODO Auto-generated method stub
		String sql = "select * from member_info";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 通过会员姓名查找会员信息
	 * parameter: name
	 * return: List<Member>
	 */
	public List<Member> getMemberByName(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from member_info where name = '" + name+"'";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 通过会员电话号码获取会员信息
	 * parameter: phone
	 * return: List<Member>
	 */
	public List<Member> getMemberByPhone(String phone) {
		// TODO Auto-generated method stub
		String sql = "select * from member_info where phone = '" + phone +"'";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 通过会员生日获取会员信息
	 * parameter： birthday  生日精确到月
	 * return: List<Member>
	 */
	public List<Member> getMemberListByBirthday(String birthday) {
		// TODO Auto-generated method stub
		String sql = "select * from member_info where month '" + birthday +"'";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 通过会员年龄获取会员信息,这里还要用来做排序的时候使用
	 * parameter: year , sql
	 * return: List<Member>
	 */
	public List<Member> getMemberListByYear(String year,String sql) {
		// TODO Auto-generated method stub
		List<Member> list = new ArrayList<Member>();
		
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 将会员信息列表插入数据库
	 * parameter: List<Member> list
	 * return: null
	 */
	public void setMemberList(List<Member> list) {
		// TODO Auto-generated method stub
		for(int i = 0;i<list.size();i++) {
			this.setMember(list.get(i));
		}
	}

	@Override
	/**
	 * 将一条会员信息记录插入数据库中
	 * parameter: member
	 * return: null
	 */
	public void setMember(Member member) {
		// TODO Auto-generated method stub
		String sql = "insert into member_info(name,phone,identity,site,join_activity,year,month,sex) value('"+
		member.getName() +"','"+member.getPhone()+"','"+member.getIdentity()+"','"+member.getSite()+"','"+
				member.getJoin_activity()+"','"+member.getYear()+"','"+member.getMonth()+"', '"+member.getSex()+"')"; 
		Connection conn = this.getConn();
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
	 * 判断会员是否已经存在系统中
	 * parameter: member
	 * return: boolean
	 */
	public boolean memberIsInDatabase(Member member) {
		// TODO Auto-generated method stub
		String sql = "select * from member_info where identity = '"+ member.getIdentity() +"'";
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				return true;
			} else {
				return false;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	/**
	 * 更新会员信息
	 * parameter: member
	 * return: null
	 */
	public void updateMember(Member member,String identity_old) {
		// TODO Auto-generated method stub
		String sql = "UPDATE member_info SET NAME='"+member.getName()+"', phone='"+member.getPhone()+"', identity='"+
		member.getIdentity()+"', site='"+member.getSite()+"', join_activity='"+member.getJoin_activity()+"', year='"+
				member.getYear()+"', month='"+member.getMonth()+"', sex = '"+member.getSex()+"'  WHERE identity='"+identity_old+"'"; 
		Connection conn = this.getConn();
		System.out.println(sql);
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
	 * 根据条件查询排列的数据
	 * parameter: 次数、年龄、生日的升序和降序
	 */
	public List<Member> getMemberOrderBy(String sql) {
		// TODO Auto-generated method stub
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	 * 更新用户参加活动的次数
	 */
	public void updateMemberActivityCount(String identity, int count) {
		// TODO Auto-generated method stub
		String sql = "UPDATE member_info SET  join_activity='"+count+"'  WHERE identity='"+identity+"'"; 
				Connection conn = this.getConn();
				System.out.println(sql);
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
	public Member getMemberByIdentity(String identity) {
		// TODO Auto-generated method stub
		String sql = "select * from member_info where identity = '"+ identity +"'";
		Connection conn = this.getConn();
		Member member = new Member();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				member.setIdentity(identity);
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setSex(rs.getString("sex"));
				member.setSite(rs.getString("site"));
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public void deleteMemberByIdentity(String identity) {
		// TODO Auto-generated method stub
		String sql = "DELETE from member_info WHERE identity='"+identity+"'";
		String sql2 = "DELETE from attend_activity WHERE identity='"+identity+"'";
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
	public List<Member> getMemberByTheme(String theme) {
		// TODO Auto-generated method stub
		String sql = "SELECT m.* FROM `attend_activity` a ,member_info m WHERE a.theme LIKE '%"+theme+"%' AND a.identity = m.identity" ;
		List<Member> list = new ArrayList<Member>();
		System.out.println(sql);
		Connection conn = this.getConn();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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
	public List<Member> getMemberBySearchMain(String SearchMain) {
		// TODO Auto-generated method stub
		String sql = "SELECT m.* FROM `member_info` m , attend_activity a  WHERE m.identity = '"+SearchMain+"' OR m.phone = '"+SearchMain+"' OR m.`name` LIKE '%"+SearchMain+"%' OR (a.theme LIKE '%"+SearchMain+"%' AND a.identity = m.identity) GROUP BY m.identity ";
		List<Member> list = new ArrayList<Member>();
		Connection conn = this.getConn();
		System.out.println(sql);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getInt("id"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setIdentity(rs.getString("identity"));
				member.setSite(rs.getString("site"));
				member.setJoin_activity(rs.getString("join_activity"));
				member.setYear(rs.getString("year"));
				member.setMonth(rs.getString("month"));
				member.setSex(rs.getString("sex"));
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

}
