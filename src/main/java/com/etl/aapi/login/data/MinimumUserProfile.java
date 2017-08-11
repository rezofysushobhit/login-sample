package com.etl.aapi.login.data;

public class MinimumUserProfile {
	
	private String userId;
	private String role;
	private String guid;
	public MinimumUserProfile(String userId) {
		super();
		this.userId = userId;
	}
	public MinimumUserProfile(String userId, String role) {
		super();
		this.userId = userId;
		this.role = role;
	}
	public MinimumUserProfile(String userId, String role, String guid) {
		super();
		this.userId = userId;
		this.role = role;
		this.guid = guid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}


}
