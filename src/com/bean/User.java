package com.bean;

public class User {

	private int id;
//	private String taskid;
	private String topic;
	private String uid;
	private String uname;
	private String gender;
	private String verify;
	private String level;
	private String location;
	private int fansnum;
	private String Sfansnum;
	private int friendsnum;
	private int blogsnum;
	private byte[] headpic;
	private String headpicsrc;
	private double socialeffect;
	private double topiceffect;
	
	private int forwardnum;
	private int aspect;
	private String forwardText;
	public String getForwardText() {
		return forwardText;
	}
	public void setForwardText(String forwardText) {
		this.forwardText = forwardText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getTaskid() {
//		return taskid;
//	}
//	public void setTaskid(String taskid) {
//		this.taskid = taskid;
//	}
	public String getSfansnum() {
		return Sfansnum;
	}
	public void setSfansnum(String sfansnum) {
		Sfansnum = sfansnum;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getFansnum() {
		return fansnum;
	}
	public void setFansnum(int fansnum) {
		this.fansnum = fansnum;
	}
	public int getFriendsnum() {
		return friendsnum;
	}
	public void setFriendsnum(int friendsnum) {
		this.friendsnum = friendsnum;
	}
	public int getBlogsnum() {
		return blogsnum;
	}
	public void setBlogsnum(int blogsnum) {
		this.blogsnum = blogsnum;
	}
	public String getHeadpicsrc() {
		return headpicsrc;
	}
	public void setHeadpicsrc(String headpicsrc) {
		this.headpicsrc = headpicsrc;
	}
	public byte[] getHeadpic() {
		return headpic;
	}
	public void setHeadpic(byte[] headpic) {
		this.headpic = headpic;
	}
	public double getSocialeffect() {
		return socialeffect;
	}
	public void setSocialeffect(double socialeffect) {
		this.socialeffect = socialeffect;
	}
	public double getTopiceffect() {
		return topiceffect;
	}
	public void setTopiceffect(double topiceffect) {
		this.topiceffect = topiceffect;
	}
	public int getForwardnum() {
		return forwardnum;
	}
	public void setForwardnum(int forwardnum) {
		this.forwardnum = forwardnum;
	}
	public int getAspect() {
		return aspect;
	}
	public void setAspect(int aspect) {
		this.aspect = aspect;
	}
	
}
