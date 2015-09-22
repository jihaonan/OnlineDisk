package com.liaoyb.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/** 
	 * 获取访问用户的客户端IP（适用于公网与局域网）. 
	 */  
	public static final String getIpAddr(final HttpServletRequest request)  
	        throws Exception {  
	    if (request == null) {  
	        throw (new Exception("getIpAddr method HttpServletRequest Object is null"));  
	    }  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip= request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {  
	        ip= request.getRemoteAddr();  
	    }  
	  

	  
	    return ip;  
	}

	public static String getFirstIP(final HttpServletRequest request) throws Exception {
		
		String ip=getIpAddr(request);
		// 多个路由时，取第一个非unknown的ip  
	    final String[] arr = ip.split(",");  
	    for (final String str : arr) {  
	        if (!"unknown".equalsIgnoreCase(str)) {  
	            ip = str;  
	            break;  
	        }  
	    }
		return ip;
	}
	
	

	
	
	
}
