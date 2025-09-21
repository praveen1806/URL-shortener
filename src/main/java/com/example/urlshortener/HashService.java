package com.example.urlshortener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class HashService {
	private final Map<String,String> map = new ConcurrentHashMap<>();
	public String shortenUrl(String longUrl) {
		String hash = getHash(longUrl);
		String code = hash.substring(0,8);
		map.put(code,longUrl);
		return code;
	}
	public String getHash(String longUrl) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(longUrl.getBytes());
			StringBuilder code = new StringBuilder();
			for(byte b:hashBytes) {
				code.append(String.format("%02x",b));
			}
			return code.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 not available", e);
		}
	}
	public String expandUrl(String code) {
		return map.containsKey(code)?map.get(code):null;
	}
}
