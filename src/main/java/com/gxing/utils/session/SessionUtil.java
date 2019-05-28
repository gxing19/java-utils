package com.gxing.utils.session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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