package com.zjlp.face.web.security.cached;

import java.util.concurrent.ConcurrentHashMap;

public class RedirectLoginCached extends ConcurrentHashMap<String,String> {
	
	private static final long serialVersionUID = 1L;
	
	public static final String LOGIN_KEY = "LOGIN_KEY";
	public static RedirectLoginCached cached = new RedirectLoginCached(1);
	
	private RedirectLoginCached(int initialCapacity) {
		super(initialCapacity);
	}
	
	public static RedirectLoginCached getInstance(){
		return cached;
	}

	@Override
	public String get(Object key) {
		return super.get(key);
	}

	@Override
	public String put(String key, String value) {
		return super.put(key, value);
	}

}
