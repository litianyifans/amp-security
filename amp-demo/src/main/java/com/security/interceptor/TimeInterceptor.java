package com.security.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @category Class description
 * @author heyingcheng
 * @email horay_hyc@qq.com
 * @date 2017/12/18 21:35
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		System.out.println("preHandle");
		request.setAttribute("startTime", System.currentTimeMillis());
		System.out.println(((HandlerMethod) o).getBean().getClass().getName());
		System.out.println(((HandlerMethod) o).getMethod().getName());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		Long startTime = (Long) request.getAttribute("startTime");
		Long endTime = System.currentTimeMillis();
		System.out.println("time postHandle interceptor 耗时：" + (endTime - startTime));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
		System.out.println("afterCompletion");
		Long startTime = (Long) request.getAttribute("startTime");
		Long endTime = System.currentTimeMillis();
		System.out.println("time afterCompletion interceptor 耗时：" + (endTime - startTime));
		System.out.println("e is " + e);
	}
}
