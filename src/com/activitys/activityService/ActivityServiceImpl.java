package com.activitys.activityService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.activitys.activityDao.ActivityDaoImpl;
import com.bean.Activitys;
import com.bean.Member;
import com.bean.MemberActivity;

@Service
public class ActivityServiceImpl implements ActivityService {
	private ActivityDaoImpl activityDao = new ActivityDaoImpl();
	
	@Override
	public List<Activitys> getActivitysList() {
		// TODO Auto-generated method stub
		return activityDao.getActivitysList();
	}

	@Override
	public Boolean setActivity(Activitys activity) {
		// TODO Auto-generated method stub
		return activityDao.setActivity(activity);
	}

	@Override
	public Activitys getActivityByTheme(String theme) {
		// TODO Auto-generated method stub
		return activityDao.getActivityByTheme(theme);
	}

	@Override
	public int getMemberCountByActivityTheme(String theme) {
		// TODO Auto-generated method stub
		return activityDao.getMemberCountByActivityTheme(theme);
	}

	@Override
	public int getActivityIdByName(String name) {
		// TODO Auto-generated method stub
		return activityDao.getActivityIdByName(name);
	}

	@Override
	public void setMemberAndActivity(MemberActivity memberActivity) {
		// TODO Auto-generated method stub
		activityDao.setMemberAndActivity(memberActivity);
	}

	@Override
	public List<Member> getMemberByActivity(String theme) {
		// TODO Auto-generated method stub
		return activityDao.getMemberByActivity(theme);
	}

	@Override
	public List<MemberActivity> getActivityByxMember(String identity) {
		// TODO Auto-generated method stub
		return activityDao.getActivityByxMember(identity);
	}

	@Override
	public int getActivityCountByMember(String identity) {
		// TODO Auto-generated method stub
		return activityDao.getActivityCountByMember(identity);
	}

	@Override
	public void updateAttendActivity(MemberActivity memberActivity,String identity_old) {
		// TODO Auto-generated method stub
		activityDao.updateAttendActivity(memberActivity, identity_old);
	}

	@Override
	public List<Activitys> getActivitysPartList() {
		// TODO Auto-generated method stub
		return activityDao.getActivitysPartList();
	}

	@Override
	public void updateActivity(Activitys activity, String old_theme) {
		// TODO Auto-generated method stub
		activityDao.updateActivity(activity, old_theme);
	}

	@Override
	public void updateAttendActivityByTheme(MemberActivity memberActivity,
			String old_theme) {
		// TODO Auto-generated method stub
		activityDao.updateAttendActivityByTheme(memberActivity, old_theme);
	}

	@Override
	public void delectAttendActivityByIdentity(String identity,String old_theme) {
		// TODO Auto-generated method stub
		activityDao.delectAttendActivityByIdentity(identity,old_theme);
	}

	@Override
	public String getActivitysByidentity(String identity) {
		// TODO Auto-generated method stub
		return activityDao.getActivitysByidentity(identity);
	}

	@Override
	public void deleteActivity(String theme) {
		// TODO Auto-generated method stub
		activityDao.deleteActivity(theme);
	}

	@Override
	public void deleteAttendActivity(String theme) {
		// TODO Auto-generated method stub
		activityDao.deleteAttendActivity(theme);
	}

}
