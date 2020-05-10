package com.jinalim.admin.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 지정된 컨트롤러의 동작 이후에 처리,
	 * spring MVC의 front Controller인 dispatcherServlet이 화면을 처리하기 전에 동작
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post handle................");
		
		Object result = modelAndView.getModel().get("result");
		
		if(result != null) {
			request.getSession().setAttribute("result", result);
			response.sendRedirect("/doA");
		}
	}
	
	/**
	 * 지정된 컨트롤러의 동장 이전에 가로채는 역할
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * handler는 현재 실행하려는 메서드 자체를 의미,
		 * 이를 활용하면 현재 실행되는 컨트롤러를 파악하거나,
		 * 추가적인 메서드를 실행하는 등의 작업이 가능,
		 */
		System.out.println("pre handle................");
		
		// handler는 HandlerMethod 타입으로 캐스팅한 후 원래의 메소드와 객체(빈)을 확인
		HandlerMethod method = (HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		System.out.println("Bean => " + method.getBean());
		System.out.println("Method => " + methodObj);
		
		return true;
	}

}
