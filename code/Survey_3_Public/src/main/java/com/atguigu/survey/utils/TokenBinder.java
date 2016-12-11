package com.atguigu.survey.utils;

public class TokenBinder {
	
	private static ThreadLocal<String> local = new ThreadLocal<>();
	
	public static final String KEY_MAIN = "DATASOURCE_MAIN";
	public static final String KEY_LOG = "DATASOURCE_LOG";
	
	public static void bindToke(String token) {
		local.set(token);
	}
	
	public static void removeToken() {
		local.remove();
	}
	
	public static String getToken() {
		return local.get();
	}

}
