package com.atguigu.survey.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 由于线程本地化技术是以local对象作为key，以要绑定的数据作为value保存到Thread对象中的Map里面。
 * 所以在绑定和移除的过程中需要让local是同一个对象，所以设置为静态属性。
 * @author Creathin
 *
 */
public class RequestBinder {
	
	private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();
	
	public static void bindRequest(HttpServletRequest request) {
		local.set(request);
	}
	
	public static void removeRequest() {
		local.remove();
	}
	
	public static HttpServletRequest getRequest() {
		return local.get();
	}

}
