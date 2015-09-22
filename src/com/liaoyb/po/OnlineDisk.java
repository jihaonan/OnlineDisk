package com.liaoyb.po;

import java.util.ArrayList;
import java.util.List;

/**
 * ÍøÅÌ
 * @author Liao-Pc
 *
 */
public class OnlineDisk {

	//×Ö½Ú
	private float capacity;
	private float userspace;
	
	private String userId;

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getUserspace() {
		return userspace;
	}

	public void setUserspace(float userspace) {
		this.userspace = userspace;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public OnlineDisk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnlineDisk(float capacity, float userspace, String userId) {
		super();
		this.capacity = capacity;
		this.userspace = userspace;
		this.userId = userId;
	}

	
	
	
	
}
