package com.member.memberDao;

import java.util.List;

import com.bean.Member;

public interface MemberDao {
	public List<Member> getAllMember();
	
	public List<Member> getMemberByName(String name);
	
	public List<Member> getMemberByPhone(String phone);
	
	public List<Member> getMemberByTheme(String theme);
	
	public List<Member> getMemberListByBirthday(String birthday);
	
	public List<Member> getMemberListByYear(String year, String sql);
	
	public void setMemberList(List<Member> list);
	
	public void setMember(Member member);
	
	public boolean memberIsInDatabase(Member member);
	
	public void updateMember(Member member, String identity_old);
	
	public List<Member> getMemberOrderBy(String sql);
	
	public void updateMemberActivityCount(String identity, int count);
	
	public Member getMemberByIdentity(String identity);
	
	public void deleteMemberByIdentity(String identity);
	
	public List<Member> getMemberBySearchMain(String SearchMain); 
}
