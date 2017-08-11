package com.etl.aapi.async.controller;

public class ReturnObject {

	private String message;

	public ReturnObject(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
