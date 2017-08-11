package com.etl.aapi.web.security.jwt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etl.aapi.login.data.MinimumUserProfile;
import com.etl.aapi.web.security.SecretKeyProvider;
import com.etl.aapi.web.security.SecurityException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	private static final String ISSUER = "com.etl";
	private static final int HOURS = 500;
	private SecretKeyProvider secretKeyProvider;

	public JwtService() {
		this(null);
	}

	@Autowired
	public JwtService(SecretKeyProvider secretKeyProvider) {
		this.secretKeyProvider = secretKeyProvider;
	}

	public String generateTokenFor(MinimumUserProfile minimalProfile) throws IOException, URISyntaxException {
		byte[] secretKey = secretKeyProvider.getKey();
		Date expiration = new DateTime().withZone(DateTimeZone.forTimeZone(TimeZone.getDefault())).plusHours(HOURS)
				.toDate();
		Claims claims = Jwts.claims().setSubject(minimalProfile.getUserId());
		claims.put("userProfile", new MinimumUserProfile(minimalProfile.getUserId(), "AFFILIATE",
				RandomStringUtils.randomAlphanumeric(20)));
		StringBuilder tokenBuilder = new StringBuilder(
				Jwts.builder().setSubject(minimalProfile.getUserId()).setClaims(claims).setExpiration(expiration)
						.setIssuer(ISSUER).signWith(SignatureAlgorithm.HS512, secretKey).compact());
		return tokenBuilder.toString();
	}

	@SuppressWarnings("unchecked")
	public static boolean validToken(String jwtToken) throws SecurityException {
		boolean validToken = false;
		Map<String, String> stringClaims = null;
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(new SecretKeyProvider().getKey()).parseClaimsJws(jwtToken);
			stringClaims = (Map<String, String>) claims.getBody().get("userProfile");
			if (StringUtils.isEmpty(stringClaims.get("guid"))) {
				throw new SecurityException("Invalid Security token");
			} else {
				validToken = true;
			}
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(e);
		}
		return validToken;
	}

	public Jws<Claims> getClaim(MinimumUserProfile minimalProfile, String token) {
		Jwts.parser().setSigningKey(new SecretKeyProvider().getKey()).parseClaimsJws(token);
		Jws<Claims> claims = Jwts.parser().setSigningKey(new SecretKeyProvider().getKey()).parseClaimsJws(token);// Jwts.parser().setSigningKey(secretKeyProvider.getKey()).parseClaimsJws(token);
		System.out.println(claims.getBody().get("userProfile"));
		return claims;
	}

	public static void main(String[] args) throws Exception {
		JwtService jwtService = new JwtService(new SecretKeyProvider());
		MinimumUserProfile userProfile = new MinimumUserProfile("harishworks@gmail.com");
		String token = jwtService.generateTokenFor(userProfile);
		System.out.println(token);
		// "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYXJpc2h3b3JrcyIsImV4cCI6MTQ4ODk5NjI1OSwiaXNzIjoiY29tLmV0bCJ9.GeAmKqyxAikp1YcImMUEJDfw7ZtLhyn7mr7vdh9CjEWlg6y_w4wtliTocqoQFJil2FlHAw_zVatPE572sWGnZw";
		jwtService.getClaim(new MinimumUserProfile("harishworks@gmail.com"), token);

	}
}