package com.liaoyb.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6334417877152363915L;
	private String userId;
	private String username;
	private String nickname;
	private String email;
	private String sex;
	private Date birthday;
	//存放photo的url链接
	private String photo;
	private String info;
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public OnlineDisk getDisk() {
		return disk;
	}

	public void setDisk(OnlineDisk disk) {
		this.disk = disk;
	}

	public List<ShareInfo> getShareInfos() {
		return shareInfos;
	}

	public void setShareInfos(List<ShareInfo> shareInfos) {
		this.shareInfos = shareInfos;
	}

	//我的网盘
	private OnlineDisk disk;
	
	//我的所有分享
	private List<ShareInfo> shareInfos=new ArrayList<ShareInfo>();
}
