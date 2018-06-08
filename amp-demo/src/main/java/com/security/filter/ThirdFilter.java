package com.security.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @category Class description
 * @author heyingcheng
 * @email horay_hyc@qq.com
 * @date 2017/12/18 17:42
 */
public class ThirdFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("third filter init");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		System.out.println("third filter start");
		long start = System.currentTimeMillis();
		filterChain.doFilter(servletRequest, servletResponse);
		long end = System.currentTimeMillis();
		System.out.println("third filter 耗时：" + (end - start));
		System.out.println("third filter finish");
	}

	@Override
	public void destroy() {
		System.out.println("third filter destroy");
	}

}
