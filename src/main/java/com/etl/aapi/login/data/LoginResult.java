package com.etl.aapi.login.data;

public class LoginResult {

	private String status;

	public LoginResult() {
		super();
	}

	public LoginResult(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
