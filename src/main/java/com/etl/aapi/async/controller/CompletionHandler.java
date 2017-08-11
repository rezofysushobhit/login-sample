package com.etl.aapi.async.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;

public class CompletionHandler extends AsyncCompletionHandler<ReturnObject> {

	private Logger logger = LogManager.getLogger(CompletionHandler.class);

	private String data;

	public CompletionHandler(String data) {
		super();
		this.data = data;
	}

	@Override
	public void onThrowable(Throwable t) {
		logger.debug(data + " Error :" + t.getMessage());
	}

	@Override
	public ReturnObject onCompleted(Response response) throws Exception {
		logger.debug(data + " Response :" + response);
		return new ReturnObject(response.getResponseBody());
	}

}
