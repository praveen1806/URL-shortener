package com.example.urlshortener;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
@Service
public class BasicService {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int CODE_LEN = 6;
	private static final SecureRandom random = new SecureRandom();
	private final Map<String, String> store = new ConcurrentHashMap<>();
	
	public String shortenUrl(String longUrl) {
		String code = generateCode();
		while(store.containsKey(code)) code=generateCode();
		store.put(code,longUrl);
		return code;
	}
	
	public String generateCode() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(CODE_LEN);
		for(int i=0;i<CODE_LEN;i++) {
			int idx = random.nextInt(ALPHABET.length());
			sb.append(ALPHABET.charAt(idx));
		}
		return sb.toString();
	}

	public String expandUrl(String code) {
        return store.getOrDefault(code, null);
    }
	
	

}
