package com.etl.aapi.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etl.aapi.login.data.LoginResult;
import com.etl.aapi.login.data.MinimumUserProfile;
import com.etl.aapi.login.data.User;
import com.etl.aapi.web.security.SecurityException;
import com.etl.aapi.web.security.jwt.JwtService;
import com.etl.aapi.web.security.spring.SecurityConfiguration;

@RestController
public class LoginController {
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	private JwtService jwtService;

	@RequestMapping(path = "/api/auth/login", method = RequestMethod.POST)
	public LoginResult login(@ModelAttribute User user, HttpServletResponse response) {
		logger.debug("OK");
		try {
			
			response.setHeader(SecurityConfiguration.JWT_TOKEN_HEADER_PARAM,
					jwtService.generateTokenFor(new MinimumUserProfile(user.getUserId())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new LoginResult("Success");
	}

	@RequestMapping(value = "/api/echo", method = RequestMethod.GET)
	public LoginResult index() {
		logger.debug("echo");
		return new LoginResult("Success");

	}

	@Autowired
	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<?> handleUserNotFoundException(HttpServletRequest req, Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}