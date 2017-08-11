package com.etl.aapi.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.etl.aapi.web.security.jwt.JwtService;
import com.etl.aapi.web.security.spring.SecurityConfiguration;

public class JwtTokenFilter implements Filter {

	private List<String> publicUrls;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		publicUrls = new ArrayList<String>();
		publicUrls.add("/api/auth/login");
		publicUrls.add("/send-request");
		publicUrls.add("/receive");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hsRequest = (HttpServletRequest) request;

		// if public URL do nothing
		if (publicUrls.contains(hsRequest.getRequestURI())) {
			chain.doFilter(request, response);
		} else {
			String jwtToken = hsRequest.getHeader(SecurityConfiguration.JWT_TOKEN_HEADER_PARAM);
			if (StringUtils.isNotEmpty(jwtToken)) {
				try {
					if (JwtService.validToken(jwtToken)) {
						chain.doFilter(request, response);
					}
				} catch (SecurityException e) {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
				}
			} else {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Incomplete security header, required security token");
			}
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
