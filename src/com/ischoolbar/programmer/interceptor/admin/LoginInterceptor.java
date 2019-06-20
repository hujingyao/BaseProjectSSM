package com.ischoolbar.programmer.interceptor.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
/*
 * 
 * 后台登陆拦截器
 */


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		String requestURI = request.getRequestURI();
		System.out.println("链接"+requestURI+"进入拦截器");
  Object admin= request.getSession().getAttribute("admin");
		System.out.println(admin);
//		if(admin == null) {
//			response.sendRedirect(request.getServletContext().getContextPath()+"system/login");
//			return false;
//		}
//		
		System.out.println(admin);
		
		return false;//true表示可以访问
	}

}
