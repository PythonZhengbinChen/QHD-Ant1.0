package com.member.memberService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bean.Member;
import com.member.memberDao.MemberDaoImpl;

@Service
public class MemberServiceImpl implements MemberService {
	private MemberDaoImpl memberDao = new MemberDaoImpl();
	
	@Override
	public List<Member> getAllMember() {
		// TODO Auto-generated method stub
		return memberDao.getAllMember();
	}

	@Override
	public List<Member> getMemberByName(String name) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByName(name);
	}

	@Override
	public List<Member> getMemberByPhone(String phone) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByPhone(phone);
	}

	@Override
	public List<Member> getMemberListByBirthday(String birthday) {
		// TODO Auto-generated method stub
		return memberDao.getMemberListByBirthday(birthday);
	}

	@Override
	public List<Member> getMemberListByYear(String year, String sql) {
		// TODO Auto-generated method stub
		return memberDao.getMemberListByYear(year, sql);
	}

	@Override
	public void setMemberList(List<Member> list) {
		// TODO Auto-generated method stub
		memberDao.setMemberList(list);

	}

	@Override
	public void setMember(Member member) {
		// TODO Auto-generated method stub
		memberDao.setMember(member);
	}

	@Override
	public boolean memberIsInDatabase(Member member) {
		// TODO Auto-generated method stub
		return memberDao.memberIsInDatabase(member);
	}

	@Override
	public void updateMember(Member member,String identity_old) {
		// TODO Auto-generated method stub
		memberDao.updateMember(member, identity_old);
	}

	@Override
	public List<Member> getMemberOrderBy(String sql) {
		// TODO Auto-generated method stub
		return memberDao.getMemberOrderBy(sql);
	}

	@Override
	public void updateMemberActivityCount(String identity, int count) {
		// TODO Auto-generated method stub
		memberDao.updateMemberActivityCount(identity, count);
	}

	@Override
	public Member getMemberByIdentity(String identity) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByIdentity(identity);
	}

	@Override
	public void deleteMemberByIdentity(String identity) {
		// TODO Auto-generated method stub
		memberDao.deleteMemberByIdentity(identity);
	}

	@Override
	public List<Member> getMemberByTheme(String theme) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByTheme(theme);
	}

	@Override
	public List<Member> getMemberBySearchMain(String SearchMain) {
		// TODO Auto-generated method stub
		return memberDao.getMemberBySearchMain(SearchMain);
	}

}
