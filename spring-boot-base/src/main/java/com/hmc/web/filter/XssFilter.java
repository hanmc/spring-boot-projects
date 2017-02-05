package com.hmc.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


/**
 * 跨站脚本过滤器
 * request值是不能直接用setAttribute改的，一般都是通过wraper类复写来改
 * @author wuxiaozeng
 *
 */
public class XssFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;


		HttpServletRequestWrapper requestWrapper = null;
		
			requestWrapper = new MirageRequestWrapper(httpServletRequest);

		chain.doFilter(requestWrapper, httpServletResponse);

	}

	@Override
	public void destroy() {

	}

}
