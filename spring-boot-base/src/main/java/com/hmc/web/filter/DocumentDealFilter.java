package com.hmc.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 支付平台报文处理Filter<p>
 * <pre>
 * 包括：
 * 1,验签，将验签结果放入signdata中
 * 
 * <pre>
 * 
 * @author hanmc
 * @date 2016-4-3
 */
public class DocumentDealFilter implements Filter {

	private List<String> reqeusts = new ArrayList<String>();

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String servletPath = httpRequest.getServletPath();
		String docType = httpRequest.getParameter("type");
		
		
		if(reqeusts.contains(servletPath) && "X".equalsIgnoreCase(docType)){
			chain.doFilter(new DcoumentDealWrapper(httpRequest),response); 
		}else{
			chain.doFilter(request,response); 
		}
		

	}

	public void init(FilterConfig fConfig) throws ServletException {
		reqeusts.add("/tradeQuery.do");
		reqeusts.add("/tradeRefund.do");
	}
	
	public DocumentDealFilter() {
	}

	public void destroy() {
	}
}
