package com.activitys.activityService;

import java.util.List;

import com.bean.Activitys;
import com.bean.Member;
import com.bean.MemberActivity;

public interface ActivityService {
	public List<Activitys> getActivitysList();
	
	public List<Activitys> getActivitysPartList();
	
	public Boolean setActivity(Activitys activity);
	
	public Activitys getActivityByTheme(String theme);
	
	public int getMemberCountByActivityTheme(String theme);
	
	public int getActivityIdByName(String name);
	
	public void setMemberAndActivity(MemberActivity memberActivity);
	
	public List<Member> getMemberByActivity(String theme);
	
	public List<MemberActivity> getActivityByxMember(String identity);
	
	public int getActivityCountByMember(String identity);
	
	public void updateAttendActivity(MemberActivity memberActivity,String identity_old);
	
	public void updateActivity(Activitys activity,String old_theme);
	
	public void updateAttendActivityByTheme(MemberActivity memberActivity,String old_theme);
	
	public void delectAttendActivityByIdentity(String identity,String old_theme);
	
	public String getActivitysByidentity(String identity);
	
	public void deleteActivity(String theme);
	
	public void deleteAttendActivity(String theme);
}
