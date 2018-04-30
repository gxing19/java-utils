package com.java.util.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class SessionUtil {
    public static HttpServletRequest getRequest(){  
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();  
        return requestAttributes == null ? null : requestAttributes.getRequest();  
    }  
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static Object getUserInfo() {
		return (Object) getSession().getAttribute("user");
	}
}