package com.example.urlshortener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class Base62Service {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final AtomicLong id = new AtomicLong(0);
	private final Map<Long,String> idToLongUrl = new ConcurrentHashMap<>();
	private final Map<String,Long> longUrlToId = new ConcurrentHashMap<>();
	private final int BASE = 62;
	
	public String encode(String longUrl) {
		long currId = 0;
		if(longUrlToId.containsKey(longUrl)) currId = longUrlToId.get(longUrl);
		else currId = id.getAndIncrement();
		idToLongUrl.put(currId,longUrl);
		longUrlToId.put(longUrl, currId);
		String code = encodeHelper(currId);
		return code;
	}

	private String encodeHelper(long currId) {
		// TODO Auto-generated method stub
		if(currId==0) return String.valueOf(ALPHABET.charAt((int)currId));
		StringBuilder code = new StringBuilder();
		while(currId>0) {
			long rem = currId%62;
			code.append(ALPHABET.charAt((int)rem));
			currId/=BASE;
		}
		return code.reverse().toString();
	}
	
	public String decode(String code) {
		long longUrlId = decodeHelper(code);
		if(idToLongUrl.containsKey(longUrlId)) return idToLongUrl.get(longUrlId);
		return null;
	}

	public long decodeHelper(String code) {
		long longUrlId = 0;
		for(char c:code.toCharArray()) {
			if (ALPHABET.indexOf(c) < 0) throw new IllegalArgumentException("Invalid Base62 char: " + c);
			longUrlId=longUrlId*62+ALPHABET.indexOf(c);
		}
		return longUrlId;
	}

}
