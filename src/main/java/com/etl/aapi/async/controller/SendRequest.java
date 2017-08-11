package com.etl.aapi.async.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ning.http.client.AsyncHttpClient;

@RestController
public class SendRequest {

	private Logger logger = LogManager.getLogger(SendRequest.class);

	@RequestMapping(value = "/send-request", method = RequestMethod.POST)
	public ResponseEntity<String> send() {
		logger.debug("/send-request -start");
		List<String> sendData = new ArrayList<String>();
		sendData.add("first");
		sendData.add("second");
		sendData.forEach(data -> {
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			asyncHttpClient.preparePost("http://localhost:9080/receive")
					.setBody(data).execute(new CompletionHandler(data));
			logger.debug(asyncHttpClient);
			asyncHttpClient.closeAsynchronously();
		});
		logger.debug("/send-request -ends");
		return new ResponseEntity<>("complete", HttpStatus.OK);
	}

	@RequestMapping(value = "/receive", method = RequestMethod.POST)
	public ReturnObject receive() throws InterruptedException {
		Thread.sleep(10000);
		return new ReturnObject("Received");
	}

}
