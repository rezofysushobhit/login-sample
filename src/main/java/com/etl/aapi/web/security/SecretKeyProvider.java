package com.etl.aapi.web.security;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {
	 public byte[] getKey() {
		 	byte[] keyBytes=null;
		 	try {
				keyBytes=Files.readAllBytes(Paths.get(this.getClass().getResource("/jwt/jwt-rs-256.key").toURI()));
			} catch (Exception e) {
				System.err.println(e);
				//FIXME: Log.fatal
			}
		 	
	        return keyBytes;
	    }
	 public String getKeyString() {
		 	String keyString =null;
		 	try {
				byte[] keyBytes=Files.readAllBytes(Paths.get(this.getClass().getResource("../../../../../jwt/jwt-rs-256.key").toURI()));
				keyString = new String(keyBytes);
			} catch (Exception e) {
				System.err.println(e);
				//FIXME: Log.fatal
			}
		 	
	        return keyString;
	    }
}
